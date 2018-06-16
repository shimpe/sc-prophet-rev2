(
s.waitForBoot({
	var tracks;
	var midichannels = 16.collect({0});
	var midichannelctrls, mutecheckboxes, durationtextfields;
	var textfields;
	var layout, allcontrols;
	var ffield, pfield;
	var nrpntable = NrpnTable.new;
	var nrpncache = ();

	Pdef.clear;
	Pdef.removeAll;

	~transpose = 0;
	~last_used_path = Platform.userHomeDir;

	w = Window.new("test", Rect(0,0,800,300));
	p = ScProphetRev2();
	p.connect;

	CmdPeriod.add({
		16.do({
			|i|
			Pdef(i.asSymbol).stop;
		});
		Pdef.clear;
		Pdef.removeAll;
		p.all_notes_off;
	});

	mutecheckboxes = 16.collect({
		|i|
		CheckBox().string_("Mute").value_(false).action_({
			|cbox|
			var player = Pdef(i.asSymbol).player;
			if (cbox.value) {
				if (player.notNil) {
					Pdef(i.asSymbol).player.mute;
				};
				textfields[i].background_(Color.gray.lighten(0.95));
				midichannelctrls[i].background_(Color.gray.lighten(0.95));
				durationtextfields[i].background_(Color.gray.lighten(0.95));
			} {
				if (player.notNil) {
					Pdef(i.asSymbol).player.unmute;
				};
				textfields[i].background_(Color.white);
				midichannelctrls[i].background_(Color.white);
				durationtextfields[i].background_(Color.white);
			};
		});
	});

	midichannelctrls = 16.collect({
		|i|
		TextField().value_(0).action_({
			|tfield|
			midichannels[i] = tfield.value;
		});
	});

	durationtextfields = 16.collect({
		TextField().enabled_(false).value_(0);
	});

	textfields = 16.collect({
		|i|
		TextField().action_({
			|tfield|
			textfields.do({
				|tf, i|
				if ((tf.value.stripWhiteSpace.compare("") != 0) && (mutecheckboxes[i].value.not)) {
					var pat, transppat ;
					var pan = Panola.new(tf.value);
					var dur = pan.totalDuration;
					var midipattern, withnrpnpattern;
					durationtextfields[i].value_(dur);
					midipattern = pan.asMidiPbind(p.midi_out, midichannelctrls[i].value.asInt);
					withnrpnpattern = Pbindf(midipattern,
						\nrpntype, pan.customPropertyPatternArgs("nrpn"),
						\sendnrpn, Pfunc({
							|event|
							{
								if ((event[\nrpntype] != []) && mutecheckboxes[i].value.not) {
									if (nrpncache[event[\nrpntype]] != event[\nrpn]) {
										nrpncache[event[\nrpntype]] = event[\nrpn];
										p.sendNRPN(nrpntable.str2num(event[\nrpntype][0].asSymbol,
											event[\nrpntype][1]),
										event[\nrpn].asInt,
										midichannelctrls[i].value.asInt);

									};
							}}.defer;
							1;
					}));
					pat = Pn(withnrpnpattern, inf); // repeat endlessly
					transppat = Padd(\midinote, Pfunc({~transpose}), pat); // transpose as requested
					Pdef(i.asSymbol, transppat).quant_(dur).play;
				} {
					Pdef(i.asSymbol).stop;
				};
			});
		});
	});

	ffield = TextField().string_("5").action_({
		| tfield |
		if (tfield.value > 4) {
			p.select_patch_by_id(bank:"F"++tfield.value,
				name:"P"++pfield.value,
				channel:midichannelctrls[0].value.asInt);
		} {
			p.select_patch_by_id(bank:"U"++tfield.value,
				name:"P"++pfield.value,
				channel:midichannelctrls[0].value.asInt);
		};
	});

	pfield = TextField().string_("27").action_({
		| tfield |
		p.select_patch_by_id(bank:"F"++ffield.value,
			name:"P"++tfield.value,
			channel:midichannelctrls[0].value.asInt);
	});

	layout = [
		HLayout(
			StaticText().string_("Program U(1-4)/F(5-8)"),
			ffield,
			StaticText().string_("P (1-128)"),
			pfield,
			Button().string_("Save").action_({
				| button |
				FileDialog.new(
					okFunc:{
						| path |
						var file;
						//("path class: "++path.class++" path: "++path).postln;
						file = File.new(path[0], "w");
						file.write("1 /*File format version*/\n");
						file.write(ffield.value++" /*Bank*/\n");
						file.write(pfield.value++" /*Program*/\n");
						16.do({
							|i|
							file.write(mutecheckboxes[i].value.asString++" /*mute "++i.asString++"*/\n");
							file.write(midichannelctrls[i].value.asString++" /*midi channel "++i.asString++"*/\n");
							file.write(textfields[i].value.asString++" /*panola spec "++i.asString++"*/\n");
						});
						file.write(~transpose.asString++" /*transposition*/\n");
						file.close;
						~last_used_path = PathName.new(path[0]).folderName;
					},
					cancelFunc:{
					},
					fileMode:0,
					acceptMode:1,
					stripResult:false,
					path:~last_used_path
				);
			}),
			Button().string_("Load").action_({
				| button |
				FileDialog.new(
					okFunc:{
						| path |
						var contents = File.new(path[0], "r").readAllString.split($\n);
						var temp_mute = 16.collect({false});
						contents.do({
							|c, idx |
							var offset = c.find("/*");
							if (c.find("Bank").notNil) {
								ffield.value_(c.keep(offset).stripWhiteSpace);
							};
							if (c.find("Program").notNil) {
								pfield.value_(c.keep(offset).stripWhiteSpace);
							};
							if (c.find("mute").notNil) {
								var idxoffsetstart = offset + ("/*mute ".size);
								var idxoffsetstop = (c.size - c.find("*/"));
								var idx = c.drop(idxoffsetstart).drop(idxoffsetstop.neg).asInt;
								var value = c.keep(offset);
								mutecheckboxes[idx].valueAction_(false);
								if (value.find("true").notNil) {
									temp_mute[idx] = true;
								} {
									temp_mute[idx] = false;
								};
							};
							if (c.find("midi channel ").notNil) {
								var idxoffsetstart = offset + ("/*midi channel ".size);
								var idxoffsetstop = (c.size - c.find("*/"));
								var idx = c.drop(idxoffsetstart).drop(idxoffsetstop.neg).asInt;
								var value = c.keep(offset);
								midichannelctrls[idx].valueAction_(value);
							};
							if (c.find("panola spec ").notNil) {
								var idxoffsetstart = offset + ("/*panola spec ".size);
								var idxoffsetstop = (c.size - c.find("*/"));
								var idx = c.drop(idxoffsetstart).drop(idxoffsetstop.neg).asInt;
								var value = c.keep(offset);
								textfields[idx].valueAction_(value);
							};
						});
						temp_mute.do({
							| value, idx |
							mutecheckboxes[idx].valueAction_(value);
						});
						~last_used_path = PathName.new(path[0]).folderName;
					},
					cancelFunc:{
					},
					fileMode:1,
					acceptMode:0,
					path:~last_used_path
				);
			}),
			nil
		),

		VLayout(
			*16.collect({
				|i|
				HLayout([mutecheckboxes[i], stretch:1],
					[midichannelctrls[i], stretch:1],
					[textfields[i], stretch:15],
					[durationtextfields[i], stretch:1])
			});
		);
	];

	layout = layout.add(
		HLayout(
			CheckBox().string_("(Un)Mute all").value_(false).action_({
				|cbox|
				mutecheckboxes.do({
					|cb|
					cb.valueAction_(cbox.value);
				});
			}),
			StaticText().string_("Transpose"),
			TextField().string_("0").action_({
			| tfield |
				~transpose = tfield.value.asFloat;
			});
		);
	);

	textfields[0].focus;

	w.layout = VLayout(
		*layout;
	);

	w.onClose_({
		16.do({
			|i|
			Pdef(i.asSymbol).stop;
		});
		Pdef.clear;
		Pdef.removeAll;
		p.all_notes_off;
	});

	w.front;
});
)


p.all_notes_off
