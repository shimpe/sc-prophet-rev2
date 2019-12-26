ScProphetRev2Arpeggiator {
	classvar gPATTERNS;
	classvar gTRACKS;

	var <>prophet;
	var <>quantizetfield;

	var <>globalenable;

	var <>lowtrigtfield;
	var <>lowusenotebutton;
	var <>hightrigtfield;
	var <>highusenotebutton;
	var <>refusenotebutton;
	var <>refnotetfield;

	var <>notecontrols;

	var <>tracker;

	var <>scheduledForTermination;
	var <>patCombined;

	var <>numnotesinrange;
	var <>lowestnote;
	var <>panola;
	var <>nrpncache;
	var <>nrpntable;

	*new {
		|prophet, nrpntable|
		^super.new.init(prophet, nrpntable);
	}

	*initClass {
		gPATTERNS = 4;
		gTRACKS = 4;
	}

	pr_nn2key {
		| nn |
		^("arpa2b24810f536d59d31ed7aabc134fb4e4e213e6c"++nn).asSymbol;
	}

	pr_i2key {
		|i|
		^("arp74327ee8b2e9993eaedc8e15939a1e2b1bd63e8a"++i).asSymbol;
	}

	cleanUpFunc {
		Tdef(\checknotes).stop;
		Pdef(\overallarpeggiopattern).stop;
	}

	init {
		| prophet, nrpntable |
		this.prophet = prophet;
		this.nrpntable = nrpntable;
		this.panola = Panola("a4");
		this.numnotesinrange = 0;
		this.lowestnote = 0;
		this.nrpncache = ();
		Tdef(\checknotes, {
			loop {
				{
					if (this.globalenable.value) {
						var minnote = this.panola.noteToMidi(this.lowtrigtfield.value);
						var maxnote = this.panola.noteToMidi(this.hightrigtfield.value);
						var numnotesinrange = this.tracker.getNumberOfLastPlayedNotesInRange(minnote, maxnote);
						if (numnotesinrange > 0) {
							var lowestnote = this.tracker.getLowestLastPlayedNoteInRange(minnote, maxnote);
							if ((this.numnotesinrange != numnotesinrange) || (lowestnote != this.lowestnote)) {
								var listofsubpatterns;
								var copynotes = numnotesinrange;
								var midiout = this.prophet.midi_out;
								this.numnotesinrange = numnotesinrange;
								this.lowestnote = lowestnote;
								numnotesinrange.debug("num notes in range: ");
								lowestnote.debug("lowest note in range: ");

								// if 3 note pattern not activated, try 2 note patterns, etc
								while ({((copynotes > 0) && (this.notecontrols[copynotes.asSymbol][\enablecheckbox].value.not))}, {
									copynotes = copynotes-1;
								});
								if (copynotes > 0) {
									var patternkey = copynotes.asSymbol;
									if (this.notecontrols[patternkey][\enablecheckbox].value)
									{
										var maxNoOfEvents = 0;
										gTRACKS.do({
											|track|
											var trackkey = (track+1).asSymbol;
											var score = this.notecontrols[patternkey][trackkey][\score].value;
											if (score.stripWhiteSpace.compare("") != 0) {
												var noevt;
												var pan = Panola.new(score);
												noevt = pan.getNoOfEvents;

												if (noevt > maxNoOfEvents) {
													maxNoOfEvents = noevt;
												};
											};
										});

										maxNoOfEvents.debug("max no of events");

										listofsubpatterns = gTRACKS.collect({
											| track |
											var trackkey = (track+1).asSymbol;
											var score = this.notecontrols[patternkey][trackkey][\score].value;
											if (score.stripWhiteSpace.compare("") != 0) {
												var pan = Panola.new(score);
												var dur = pan.totalDuration;
												var pat;
												var midichannel = this.notecontrols[patternkey][trackkey][\midichannel].value.asInteger;
												var midipattern = pan.asMidiPbind(midiout, midichannel);
												var transppat;
												var withnrpnpattern = Pbindf(midipattern,
													\nrpntype, pan.customPropertyPatternArgs("nrpn"),
													\sendnrpn, Pfunc({
														|event|
														{
															if (event[\nrpntype] != []) {
																if (this.nrpncache[event[\nrpntype]] != event[\nrpn]) {
																	this.nrpncache[event[\nrpntype]] = event[\nrpn];
																	this.prophet.sendNRPN(this.nrpntable.str2num(
																		event[\nrpntype][0].asSymbol,
																		event[\nrpntype][1]),
																	event[\nrpn].asInteger,
																	midichannel);

																};
														}}.defer;
														1;
												}));
												var quant = this.quantizetfield.value.asInteger;
												var refnote = this.refnotetfield.value;
												var refnotemidi = this.panola.noteToMidi(refnote);
												var lowestnotemidi = this.panola.noteToMidi(lowestnote);
												pat = Pn(withnrpnpattern, inf); // repeat endlessly
												transppat = Padd(\midinote, Pfunc({
													lowestnotemidi - refnotemidi;
												}), pat); // transpose as requested
												Pdef(this.pr_i2key(track), transppat).condition_({ |val,i| ((i % maxNoOfEvents) == 0) || ((i % maxNoOfEvents) == 1); });
											} {
												Pdef(this.pr_i2key(track)).stop;
												nil;
											};
										}).reject({ | item, i| item.isNil; });
										if (listofsubpatterns.size != 0 ) {
											listofsubpatterns.debug("list of subpatterns");
											Pdef(\overallarpeggiopattern, Ppar(listofsubpatterns, inf)).play;
										} {
											Pdef(\overallarpeggiopattern).stop;
										};
									};
								};
							};
						};
					};
				}.defer;
				0.1.wait;
			}
		}).play;
	}

	asView {
		var layouthelper = VLayout();
		this.quantizetfield = TextField();
		this.globalenable = CheckBox().string_("Enable Arpeggiator").action_({
			| cbox |
			if (cbox.value.not) {
				gTRACKS.do({
					| track |
					Pdef(this.pr_i2key(track)).stop;
				});
			};
		});
		this.lowtrigtfield = TextField().string_("c2");
		this.lowusenotebutton = Button().string_("Use last played note").action_({
			{
				this.lowtrigtfield.string_(this.tracker.getLowestLastPlayedNote);
			}.defer;
		});
		this.hightrigtfield = TextField().string_("e3");
		this.highusenotebutton = Button().string_("Use last played note").action_({
			{
				this.hightrigtfield.string_(this.tracker.getLowestLastPlayedNote);
			}.defer;
		});
		this.refusenotebutton = Button().string_("Use last played note").action_({
			{
				this.refnotetfield.string_(this.tracker.getLowestLastPlayedNote);
			}.defer;
		});
		this.refnotetfield = TextField().string_("c2");
		this.tracker = ScProphetRev2LastPlayedNotesTracker.new(this.prophet, "Arp");

		this.notecontrols = ();
		gPATTERNS.do({
			|i|
			var key = (i+1).asSymbol;
			this.notecontrols[key] = ();
			this.notecontrols[key][\enablecheckbox] = CheckBox().string_("React to "++(i+1)++" note pattern").action_({

			});
			gTRACKS.do({
				| track |
				var trackkey = (track+1).asSymbol;
				this.notecontrols[key][trackkey] = ();
				this.notecontrols[key][trackkey][\addbutton] = Button().string_("Add last played").action_({
					{
						var currValue = this.notecontrols[key][trackkey][\score].value;
						var newValue;
						if (currValue.stripWhiteSpace.compare("") != 0) {
							if (currValue[currValue.size-1] != $ ) {
								currValue = currValue ++ $ ;
							};
						};
						newValue = currValue ++ this.tracker.getLastPlayedNotes;
						this.notecontrols[key][trackkey][\score].valueAction_(newValue);
					}.defer;
				});
				this.notecontrols[key][trackkey][\midichannel] = TextField().string_("0");
				this.notecontrols[key][trackkey][\score] = TextField().action_({
					|tf|
					var tfcontents = tf.value.stripWhiteSpace;
					if (tfcontents.compare("") != 0) {
						var pan = Panola.new(tfcontents);
						var dur = pan.totalDuration;
						this.notecontrols[key][trackkey][\duration].value_(dur.round(1e-4));
					}
				});
				this.notecontrols[key][trackkey][\duration] = TextField().enabled_(false);
			});

			layouthelper = layouthelper.add(HLayout(notecontrols[key][\enablecheckbox], nil));
			gTRACKS.do({
				| track |
				var trackkey = (track+1).asSymbol;
				layouthelper = layouthelper.add(HLayout(
					[notecontrols[key][trackkey][\addbutton], stretch:1],
					[StaticText().string_("Midi Ch"), stretch:0.5],
					[notecontrols[key][trackkey][\midichannel], stretch:1],
					[StaticText().string_("Score"), stretch:0.5],
					[notecontrols[key][trackkey][\score], stretch:15],
					[StaticText().string_("Duration"), stretch:0.5],
					[notecontrols[key][trackkey][\duration], stretch:1],
					nil));
			});
		});
		layouthelper = layouthelper.add(this.tracker.asLayout);
		layouthelper = layouthelper.add(nil);

		^View().layout_(
			VLayout(
				HLayout(
					this.globalenable,
					StaticText().string_("Quantization (2 = half note, 4 = quarter note, etc)"),
					this.quantizetfield.string_("4"),
					nil),
				HLayout(
					StaticText().string_("Lowest trigger note"),
					this.lowusenotebutton,
					this.lowtrigtfield,
					StaticText().string_("Highest trigger note"),
					this.highusenotebutton,
					this.hightrigtfield,
					nil),
				HLayout(
					StaticText().string_("Reference note"),
					this.refusenotebutton,
					this.refnotetfield,
					nil),
				*layouthelper
			);
		);
	}
}
