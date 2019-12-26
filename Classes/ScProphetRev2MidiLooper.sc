ScProphetRev2MidiLooper {
	var <>gTRACKS;
	var <>midichannels;
	var <>nrpntable;
	var <>nrpncache;
	var <>prophet;
	var <>transpose;
	var <>last_used_path;
	var <>mutecheckboxes;
	var <>textfields;
	var <>midichannelctrls;
	var <>durationtextfields;
	var <>noterecordbuttons;
	var <>ffield;
	var <>pfield;
	var <>layout;
	var <>numtonote;
	var <>tracker;

	*new {
		|prophet, tracks=16|
		^super.new.init(prophet, tracks);
	}

	init {
		| prophet, tracks|
		this.prophet = prophet;
		this.gTRACKS = tracks;
		this.midichannels = gTRACKS.collect({0});
		this.nrpntable = NrpnTable.new;
		this.nrpncache = ();
		this.transpose = 0;
		this.last_used_path = Platform.userHomeDir;
		this.tracker = ScProphetRev2LastPlayedNotesTracker(prophet, "poly");

		this.mutecheckboxes = gTRACKS.collect({
			|i|
			CheckBox().string_("Mute").value_(false).action_({
				|cbox|
				var player = Pdef(this.pr_i2key(i)).player;
				if (cbox.value) {
					if (player.notNil) {
						if (player.muteCount == 0) {
							//"Mute".postln;
							player.mute;
						};
					} /*{
					"warning: trying to mute nil player".postln;
					}*/;
					this.textfields[i].background_(Color.gray.lighten(0.95));
					this.midichannelctrls[i].background_(Color.gray.lighten(0.95));
					this.durationtextfields[i].background_(Color.gray.lighten(0.95));
				} {
					if (player.notNil) {
						if (player.muteCount != 0) {
							//"Unmute".postln;
							player.unmute;
						};
					} /*{
					"warning: trying to mute nil player".postln;
					}*/;
					this.textfields[i].background_(Color.white);
					this.midichannelctrls[i].background_(Color.white);
					this.durationtextfields[i].background_(Color.white);
				};
			});
		});

		this.midichannelctrls = this.gTRACKS.collect({
			|i|
			TextField().value_(0).action_({
				|tfield|
				this.midichannels[i] = tfield.value;
			});
		});

		this.durationtextfields = this.gTRACKS.collect({
			TextField().enabled_(false).value_(0);
		});

		this.noterecordbuttons = this.gTRACKS.collect({
			|i|
			Button().string_("Add last played note").action_({
				{
					var currValue = this.textfields[i].value;
					var newValue;
					if (currValue.stripWhiteSpace.compare("") != 0) {
						if (currValue[currValue.size-1] != $ ) {
							currValue = currValue ++ $ ;
						};
					};
					newValue = currValue ++ this.tracker.getLastPlayedNotes;
					this.textfields[i].string_(newValue);
				}.defer;
			});
		});

		this.textfields = this.gTRACKS.collect({
			|i|
			TextField().action_({
				|tfield|
				this.textfields.do({
					|tf, i|
					if ((tf.value.stripWhiteSpace.compare("") != 0) && (this.mutecheckboxes[i].value.not)) {
						var pat, transppat;
						var pan = Panola.new(tf.value);
						var dur = pan.totalDuration;
						var midipattern, withnrpnpattern;
						this.durationtextfields[i].value_(dur.round(1e-4));
						midipattern = pan.asMidiPbind(this.prophet.midi_out, this.midichannelctrls[i].value.asInteger);
						withnrpnpattern = Pbindf(midipattern,
							\nrpntype, pan.customPropertyPatternArgs("nrpn"),
							\sendnrpn, Pfunc({
								|event|
								{
									if ((event[\nrpntype] != []) && this.mutecheckboxes[i].value.not) {
										if (this.nrpncache[event[\nrpntype]] != event[\nrpn]) {
											this.nrpncache[event[\nrpntype]] = event[\nrpn];
											this.prophet.sendNRPN(this.nrpntable.str2num(event[\nrpntype][0].asSymbol,
												event[\nrpntype][1]),
											event[\nrpn].asInteger,
											this.midichannelctrls[i].value.asInteger);

										};
								}}.defer;
								1;
						}));
						pat = Pn(withnrpnpattern, inf); // repeat endlessly
						transppat = Padd(\midinote, Pfunc({this.transpose}), pat); // transpose as requested
						Pdef(this.pr_i2key(i), transppat).quant_(dur).play;
					} {
						this.durationtextfields[i].value_(0);
						Pdef(this.pr_i2key(i)).stop;
					};
				});
			});
		});

		this.ffield = TextField().string_("5").action_({
			| tfield |
			if (tfield.value > 4) {
				this.prophet.select_patch_by_id(bank:"F"++(tfield.value.asInteger-4).asString,
					name:"P"++this.pfield.value,
					channel:this.midichannelctrls[0].value.asInteger);
			} {
				this.prophet.select_patch_by_id(bank:"U"++tfield.value,
					name:"P"++this.pfield.value,
					channel:this.midichannelctrls[0].value.asInteger);
			};
		});

		this.pfield = TextField().string_("27").action_({
			| tfield |
			if (ffield.value > 4) {
				this.prophet.select_patch_by_id(bank:"F"++(this.ffield.value.asInteger-4).asString,
					name:"P"++tfield.value,
					channel:this.midichannelctrls[0].value.asInteger);
			} {
				this.prophet.select_patch_by_id(bank:"U"++this.ffield.value,
					name:"P"++tfield.value,
					channel:this.midichannelctrls[0].value.asInteger);
		};	});

		this.layout = [
			HLayout(
				StaticText().string_("Program U(1-4)/F(5-8)"),
				this.ffield,
				StaticText().string_("P (1-128)"),
				this.pfield,
				Button().string_("Save").action_({
					| button |
					FileDialog.new(
						okFunc:{
							| path |
							var file;
							//("path class: "++path.class++" path: "++path).postln;
							file = File.new(path[0], "w");
							file.write("1 /*File format version*/\n");
							file.write(this.ffield.value++" /*Bank*/\n");
							file.write(this.pfield.value++" /*Program*/\n");
							16.do({
								|i|
								file.write(this.mutecheckboxes[i].value.asString++" /*mute "++i.asString++"*/\n");
								file.write(this.midichannelctrls[i].value.asString++" /*midi channel "++i.asString++"*/\n");
								file.write(this.textfields[i].value.asString++" /*panola spec "++i.asString++"*/\n");
							});
							file.write(this.transpose.asString++" /*transposition*/\n");
							file.close;
							this.last_used_path = PathName.new(path[0]).folderName;
						},
						cancelFunc:{
						},
						fileMode:0,
						acceptMode:1,
						stripResult:false,
						path:this.last_used_path
					);
				}),
				Button().string_("Load").action_({
					| button |
					FileDialog.new(
						okFunc:{
							| path |
							var contents = File.new(path[0], "r").readAllString.split($\n);
							var temp_mute = this.gTRACKS.collect({false});
							this.gTRACKS.do({
								|i|
								Pdef(this.pr_i2key(i)).stop;
							});
							contents.do({
								|c, idx |
								var offset = c.find("/*");
								if (c.find("Bank").notNil) {
									ffield.valueAction_(c.keep(offset).stripWhiteSpace);
								};
								if (c.find("Program").notNil) {
									pfield.valueAction_(c.keep(offset).stripWhiteSpace);
								};
								if (c.find("mute").notNil) {
									var idxoffsetstart = offset + ("/*mute ".size);
									var idxoffsetstop = (c.size - c.find("*/"));
									var idx = c.drop(idxoffsetstart).drop(idxoffsetstop.neg).asInteger;
									var value = c.keep(offset);
									{this.mutecheckboxes[idx].valueAction_(false);}.defer;
									if (value.find("true").notNil) {
										temp_mute[idx] = true;
									} {
										temp_mute[idx] = false;
									};
								};
								if (c.find("midi channel ").notNil) {
									var idxoffsetstart = offset + ("/*midi channel ".size);
									var idxoffsetstop = (c.size - c.find("*/"));
									var idx = c.drop(idxoffsetstart).drop(idxoffsetstop.neg).asInteger;
									var value = c.keep(offset);
									{ this.midichannelctrls[idx].valueAction_(value); }.defer;
								};
								if (c.find("panola spec ").notNil) {
									var idxoffsetstart = offset + ("/*panola spec ".size);
									var idxoffsetstop = (c.size - c.find("*/"));
									var idx = c.drop(idxoffsetstart).drop(idxoffsetstop.neg).asInteger;
									var value = c.keep(offset);
									{ this.textfields[idx].valueAction_(value); }.defer;
								};
							});
							temp_mute.do({
								| value, idx |
								{ this.mutecheckboxes[idx].valueAction_(value); }.defer;
							});
							this.last_used_path = PathName.new(path[0]).folderName;
						},
						cancelFunc:{
						},
						fileMode:1,
						acceptMode:0,
						path:this.last_used_path
					);
				}),
				nil
			),

			VLayout(
				*this.gTRACKS.collect({
					|i|
					HLayout(
						[StaticText().string_((i+1).asDigits(10,2).join("")), stretch:1],
						[this.noterecordbuttons[i], stretch:1],
						[this.mutecheckboxes[i], stretch:1],
						[this.midichannelctrls[i], stretch:1],
						[this.textfields[i], stretch:15],
						[this.durationtextfields[i], stretch:1])
				});
			);
		];

		this.layout = this.layout.add(
			VLayout(
				HLayout(
					CheckBox().string_("(Un)Mute all").value_(false).action_({
						|cbox|
						this.mutecheckboxes.do({
							|cb|
							cb.valueAction_(cbox.value);
						});
					}),
					StaticText().string_("Transpose"),
					TextField().string_("0").action_({
						| tfield |
						this.transpose = tfield.value.asFloat;
					});
				),
				this.tracker.asLayout
			)
		);

		^this;
	}

	asView{
		^View().layout_(VLayout(*this.layout));
	}

	pr_i2key {
		|i|
		^("ea5db70dd42e1b78fa6fa3f1ad2d754a87b0d747_"++i).asSymbol;
	}

	cleanUpFunc {
		^{
			this.gTRACKS.do({
				|i|
				Pdef(this.pr_i2key(i)).stop;
				Pdef.all.removeAt(this.pr_i2key(i));
			});
			this.prophet.all_notes_off;
		}
	}
}
