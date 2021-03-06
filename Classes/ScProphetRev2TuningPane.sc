ScProphetRev2TuningPane {

	var <>parent;
	var <>patchdumper;
	var <>delegation_controls;
	var <>controls;
	var <>controlspecstore;
	var <>keystore;
	var <>prophet;
	var <>builder;
	var <>nrpntable;

	var <>sysexfiletfield;
	var <>sysexfilebutton;
	var <>sysexsendbutton;
	var <>scalafiletfield;
	var <>scalafilebutton;
	var <>scalanumstepslabel;
	var <>scaladescription;
	var <>kbmfiletfield;
	var <>kbmfilebutton;
	var <>mappingreference;
	var <>scalasendbutton;
	var <>scalalistbutton;
	var <>tunename;

	var <>selfoctavetfield;
	var <>selfpartstfield;
	var <>selfrefnote;
	var <>selfreffreq;
	var <>selflistbutton;
	var <>selfsendbutton;

	var <>tracker;

	var <>textview;

	*new {
		| parent, patchdumper, delegation_controls, controls, controlspecstore, keystore, prophet, builder, nrpntable |
		^super.new.init(parent, patchdumper, delegation_controls, controls, controlspecstore, keystore, prophet, builder, nrpntable);
	}

	init {
		|parent, patchdumper, delegation_controls, controls, controlspecstore, keystore, prophet, builder, nrpntable|
		this.parent = parent;
		this.patchdumper = patchdumper;
		this.delegation_controls = delegation_controls;
		this.controls = controls;
		this.controlspecstore = controlspecstore;
		this.keystore = keystore;
		this.prophet = prophet;
		this.builder = builder;
		this.nrpntable = nrpntable;
		this.tracker = ScProphetRev2LastPlayedNotesTracker.new(this.prophet, "Tune");
		this.tracker.setShowMidiNums(true);
		this.tracker.showrhythm_(false);
		this.tracker.updateMidiHandlers; // need to refresh the midi handlers as they are different if showrhythm is false
		^this
	}

	pr_getDisplayText {
		| keytofreq, keytodeg, keytocents, colorregions |
		var displaytext = "";
		128.do({
			|i|
			if (keytofreq[i].notNil) {
				if (keytofreq[i].notNil) {
					var midinum = keytofreq[i].cpsmidi;
					var colorneeded = ((midinum < 0) || (midinum > 127));
					if  (colorneeded) {
						colorregions = colorregions.add([displaytext.size]);
					};
					if (keytodeg.notNil && keytocents.notNil) {
						displaytext = displaytext ++ i ++ ":\t " ++ keytofreq[i].asStringPrec(8) ++ "\t\t midi number: " ++ "\t " ++ midinum.asStringPrec(8) ++ "\t\t mapped degree: " ++ keytodeg[i] ++ "\t cents for degree: " ++ keytocents[i] ++ "\n";
					} {
						displaytext = displaytext ++ i ++ ":\t " ++ keytofreq[i].asStringPrec(8) ++ "\t\t midi number: " ++ "\t " ++ midinum.asStringPrec(8) ++ "\n"
					};
					if (colorneeded) {
						colorregions[colorregions.size-1] = colorregions[colorregions.size-1].add(displaytext.size);
					};
				} {
					displaytext = displaytext ++ i ++ ":\t ------\n";
				};
			}{
				displaytext = displaytext ++ i ++ ":\t ------\n";
			};
		});
		^(\txt:displaytext, \regions:colorregions);
	}

	asView {
		this.sysexfiletfield = TextField().string_(PathName(ScProphetRev2.class.filenameSymbol.asString).parentPath +/+ "tunings" +/+ "sysex" +/+ "Rev2_Tunings1-16.syx");
		this.sysexfilebutton = Button().string_("Select .syx file").action_({
			| button |
			FileDialog.new(
				okFunc:{
					| path |
					{ this.sysexfiletfield.string_(path[0]); }.defer;
				},
				cancelFunc:{
				},
				fileMode:1,
				acceptMode:0,
				path:(PathName(ScProphetRev2.class.filenameSymbol.asString).parentPath +/+ "tunings" +/+ "sysex")
			);
		});
		this.sysexsendbutton = Button().string_("Send to synth").action_({
			{
				var contents = File.new(this.sysexfiletfield.value, "rb");
				var array = Int8Array();
				contents.do({|el,idx| array = array.add(el.ascii); });
				this.prophet.midi_out.sysex(array);
			}.defer;
		});
		this.scalafiletfield = TextField();
		this.scalanumstepslabel = StaticText();
		this.scaladescription = StaticText();
		this.scalafilebutton = Button().string_("Select .scl file").action_({
			| button |
			FileDialog.new(
				okFunc:{
					| path |
					{
						var calc = ScalaCalculator();
						this.scalafiletfield.string_(path[0]);
						calc.load(path[0], nil);
						if (calc.getNoOfScaleSteps.notNil) {
							this.scalanumstepslabel.string_("No of scale steps: "++calc.getNoOfScaleSteps.asString);
							this.scaladescription.string_(calc.getDescription);
						};
					}.defer;
				},
				cancelFunc:{
				},
				fileMode:1,
				acceptMode:0,
				path:(PathName(ScProphetRev2.class.filenameSymbol.asString).parentPath +/+ "tunings" +/+ "scl")
			);
		});
		this.kbmfiletfield = TextField();
		this.mappingreference = StaticText();
		this.kbmfilebutton = Button().string_("Select .kbm file").action_({
			| button |
			FileDialog.new(
				okFunc:{
					| path |
					{
						var calc = ScalaCalculator();
						var note = MidinumberToNote();
						var displaystring = "";
						this.kbmfiletfield.string_(path[0]);
						calc.load(nil, path[0]);
						displaystring = note.midinumber_to_notename(calc.getReferenceNote, false);
						displaystring = displaystring + calc.getReferenceFrequency.asStringPrec(10);
						displaystring = displaystring + "Hz";
						this.mappingreference.string_(displaystring);
					}.defer;
				},
				cancelFunc:{
				},
				fileMode:1,
				acceptMode:0,
				path:(PathName(ScProphetRev2.class.filenameSymbol.asString).parentPath +/+ "tunings" +/+ "kbm")
			);
		});
		this.scalasendbutton = Button().string_("Send to active tuning bank").action_({
			| button |
			{
				var calc = ScalaCalculator();
				var sclpath = this.scalafiletfield.value;
				var kbmpath = this.kbmfiletfield.value;
				var tuneindex = this.controls[\control_tuning].value;
				var tunename = this.tunename.value;
				var keytofreq;
				if (sclpath.isNil || (sclpath.compare("") == 0) || File.exists(sclpath).not) {
					"No valid scl file specified. Fallback to default.".warn;
					sclpath = nil;
				};
				if (kbmpath.isNil || (kbmpath.compare("") == 0) || File.exists(kbmpath).not) {
					"No keyboard mapping specified. Fallback to default.".warn;
					kbmpath = nil;
				};
				calc.load(sclpath, kbmpath);
				keytofreq = calc.keyToFreq;
				if (keytofreq.notNil) {
					var freqtable = [];
					128.do({
						|i|
						if (keytofreq[i].notNil) {
							if (keytofreq[i].notNil) {
								freqtable = freqtable.add(keytofreq[i]);
							} {
								freqtable = freqtable.add(nil);
							};
						} {
							freqtable = freqtable.add(nil);
						};
					});
					"Sending now!".warn;
					this.prophet.send_tuning_to_synth(tuneindex, tunename, freqtable);
					"Done sending!".warn;
				} {
					"Meh. Couldn't calculate the midi key to frequency table. Bailing out.".error;
				};
			}.defer;
		});
		this.scalalistbutton = Button().string_("List calculated frequencies").action_({
			| button |
			{
				var calc = ScalaCalculator();
				var sclpath = this.scalafiletfield.value;
				var kbmpath = this.kbmfiletfield.value;
				var tuneindex = this.controls[\control_tuning].value;
				var tunename = this.tunename.value;
				var keytofreq;
				var keytodeg;
				var keytocents;
				if (sclpath.isNil || (sclpath.compare("") == 0) || File.exists(sclpath).not) {
					"No valid scl file specified. Fallback to default.".warn;
					sclpath = nil;
				};
				if (kbmpath.isNil || (kbmpath.compare("") == 0) || File.exists(kbmpath).not) {
					"No keyboard mapping specified. Fallback to default.".warn;
					kbmpath = nil;
				};
				calc.load(sclpath, kbmpath);
				keytofreq = calc.keyToFreq;
				keytodeg = calc.keyToDeg;
				keytocents = calc.keyToCents;
				if (keytofreq.notNil) {
					var result = this.pr_getDisplayText(keytofreq, keytodeg, keytocents);
					this.textview.string_(result[\txt]);
					result[\regions].do({
						|reg|
						this.textview.setStringColor(Color.blue, reg[0], reg[1]-reg[0]);
					});
				} {
					"Meh. Couldn't calculate the midi key to frequency table. Bailing out.".error;
				};
			}.defer;
		});

		this.tunename = TextField().string_("Custom Tuning I");
		this.textview = TextView();

		this.selfoctavetfield = TextField().string_("1");
		this.selfpartstfield = TextField().string_("12");
		this.selfrefnote = TextField().string_("a4");
		this.selfreffreq = TextField().string_("440");
		this.selflistbutton = Button().string_("List calculated frequencies").action_({
			| lbutton |
			var ec = EdoCalculator(this.selfoctavetfield.value.asFloat,
				this.selfpartstfield.value.asFloat,
				this.selfrefnote.value.asString,
				this.selfreffreq.value.asFloat);
			var keytofreq = ec.keyToFreq;
			var result = this.pr_getDisplayText(keytofreq, nil, nil);
			this.textview.string_(result[\txt]);
			result[\regions].do({
				|reg|
				this.textview.setStringColor(Color.blue, reg[0], reg[1]-reg[0]);
			});
		});
		this.selfsendbutton = Button().string_("Send to active tuning bank").action_({
			| sbutton |
			var ec = EdoCalculator(this.selfoctavetfield.value.asFloat,
				this.selfpartstfield.value.asFloat,
				this.selfrefnote.value.asString,
				this.selfreffreq.value.asFloat);
			var keytofreq = ec.keyToFreq;
			var tuneindex = this.controls[\control_tuning].value;
			var freqtable = [];
			if (keytofreq.notNil) {
				128.do({
					|i|
					if (keytofreq[i].notNil) {
						if (keytofreq[i].notNil) {
							freqtable = freqtable.add(keytofreq[i]);
						} {
							freqtable = freqtable.add(nil);
						};
					} {
						freqtable = freqtable.add(nil);
					};
				});
				"Sending now!".warn;
				this.prophet.send_tuning_to_synth(tuneindex, ""++this.selfpartstfield.value++"steps/"++ this.selfoctavetfield.value ++ "oct EDO", freqtable);
				"Done sending!".warn;
			};
		});


		this.builder.make_labeled_combobox(
			this.parent,
			this.patchdumper,
			this.delegation_controls,
			this.controls,
			this.controlspecstore,
			this.keystore,
			\tuning,
			this.prophet,
			this.nrpntable.str2num('ALT_TUNING'),
			"Select Active Tuning",
			[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17]);
		^View().layout_(
			VLayout(
				HLayout(
					StaticText().string_("Synth settings").font_(Font("Monaco", 16)).background_(Color.yellow),
					nil
				),
				HLayout(
					controls[\label_tuning],
					controls[\control_tuning],
					nil
				),
				HLayout(
					StaticText().string_("Send sysex file with tunings (use this to reset tunings to factory defaults!)").font_(Font("Monaco", 16)).background_(Color.yellow),
					nil
				),
				HLayout(
					this.sysexfiletfield,
					this.sysexfilebutton,
					nil
				),
				HLayout(
					this.sysexsendbutton,
					nil
				),
				HLayout(
					StaticText().string_("Send scala scale and keyboard mapping. Leave mapping empty for default.").font_(Font("Monaco", 16)).background_(Color.yellow),
					nil
				),
				HLayout(
					this.scalafiletfield,
					this.scalafilebutton,
					this.scalanumstepslabel,
					nil
				),
				HLayout(
					this.scaladescription
				),
				HLayout(
					this.kbmfiletfield,
					this.kbmfilebutton,
					this.mappingreference,
					nil
				),
				HLayout(
					StaticText().string_("Name your tuning (max. 16 chars)"),
					this.tunename,
					nil
				),
				HLayout(
					this.scalalistbutton,
					this.scalasendbutton,
					nil
				),
				HLayout(
					StaticText().string_("Or generate an EDO tuning on the fly").font_(Font("Monaco", 16)).background_(Color.yellow),
					nil
				),
				HLayout(
					StaticText().string_("Divide"),
					this.selfoctavetfield,
					StaticText().string_("octaves into"),
					this.selfpartstfield,
					StaticText().string_("equal parts, and tune note"),
					this.selfrefnote,
					StaticText().string_("to"),
					this.selfreffreq,
					StaticText().string_("Hz"),
					nil
				),
				HLayout(
					this.selflistbutton,
					this.selfsendbutton,
					nil,
				),
				HLayout(
					this.textview;
				),
				HLayout(
					this.tracker
				)
			)
		);
	}
}