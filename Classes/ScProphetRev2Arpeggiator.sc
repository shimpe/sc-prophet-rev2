ScProphetRev2Arpeggiator {
	classvar gPATTERNS;
	classvar gTRACKS;

	var <>prophet;

	var <>globalenablebutton;
	var <>quantizetfield;

	var <>lowtrigtfield;
	var <>lowusenotebutton;
	var <>hightrigtfield;
	var <>highusenotebutton;
	var <>refusenotebutton;
	var <>refnotetfield;

	var <>notecontrols;

	var <>tracker;

	*new {
		|prophet|
		^super.new.init(prophet);
	}

	*initClass {
		gPATTERNS = 4;
		gTRACKS = 4;
	}

	init {
		this.prophet = prophet;
	}

	asView {
		var layouthelper = VLayout();

		this.globalenablebutton = CheckBox().string_("Enable Arpeggiator");

		this.quantizetfield = TextField();

		this.lowtrigtfield = TextField().string_("c2");
		this.lowusenotebutton = Button().string_("Use last played note").action_({
			{
				this.lowtrigtfield.string_(this.tracker.getLowestLastPlayedNote);
			}.defer;
		});
		this.hightrigtfield = TextField().string_("c3");
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
		this.refnotetfield = TextField().string_("c4");
		this.tracker = ScProphetRev2LastPlayedNotesTracker.new(this.prophet, "Arp");

		this.notecontrols = ();
		gPATTERNS.do({
			|i|
			var key = (i+1).asSymbol;
			this.notecontrols[key] = ();
			this.notecontrols[key][\enablecheckbox] = CheckBox().string_(""++(i+1)++" note pattern");
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
						this.notecontrols[key][trackkey][\score].string_(newValue);
					}.defer;
				});
				this.notecontrols[key][trackkey][\score] = TextField();
				this.notecontrols[key][trackkey][\duration] = TextField().enabled_(false);
			});

			layouthelper = layouthelper.add(HLayout(notecontrols[key][\enablecheckbox], nil));
			gTRACKS.do({
				| track |
				var trackkey = (track+1).asSymbol;
				layouthelper = layouthelper.add(HLayout(
					notecontrols[key][trackkey][\addbutton],
					notecontrols[key][trackkey][\score],
					notecontrols[key][trackkey][\duration],
					nil));
			});
		});
		layouthelper = layouthelper.add(this.tracker.asLayout);
		layouthelper = layouthelper.add(nil);

		^View().layout_(
			VLayout(
				HLayout(
					this.globalenablebutton,
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