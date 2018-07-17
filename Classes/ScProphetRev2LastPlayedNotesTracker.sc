ScProphetRev2LastPlayedNotesTracker {
	var <>prophet;
	var <>name;
	var <>numtonote;
	var <>noteontracker;
	var <>lastplayednotes;
	var <>notefield;
	var <>noterecordrhythm;
	var <>noterecordmultiplier;
	var <>preferflats;
	var <>showmidinums;
	var <>numtonote;

	*new {
		|prophet, name|
		^super.new.init(prophet, name);
	}

	init {
		|prophet, name|
		this.prophet = prophet;
		this.name = name;
		this.noteontracker = ();
		this.lastplayednotes = ();
		this.numtonote = MidinumberToNote();
		MIDIdef.noteOn(
			(this.name++"noteontracker").asSymbol, {
				| vel, noteNum, chan, src |
				{
					var sortedkeys;
					var displaystring = "";
					var numbernotes = 0;
					if (vel == 0) {
						this.noteontracker[noteNum] = false;
					} {
						this.noteontracker[noteNum] = true;
					};
					sortedkeys = this.noteontracker.keys.asList.sort;
					this.lastplayednotes = ();
					sortedkeys.do({
						| key |
						if (this.noteontracker[key] == true) {
							var flats = this.preferflats.value;
							var notename;
							this.lastplayednotes[key] = true;
							if (this.showmidinums.value) {
								notename = key.asString;
							} {
								notename = this.numtonote.midinumber_to_notename(key, flats);
							};
							if (displaystring.compare("") == 0) {
								// first note
								var triola = this.noterecordmultiplier.item.compare("triola") == 0;
								var selectedItem = "";
								if (this.noterecordrhythm.item.compare("1") == 0) {
									selectedItem = "1";
								} {
									selectedItem = this.noterecordrhythm.item.copyRange(2,5);
								};
								if (triola) {
									displaystring = displaystring ++ notename ++ "_" ++ selectedItem ++ "*2/3";
								} {
									displaystring = displaystring ++ notename ++ "_" ++ selectedItem;
								}
							} {
								// if previous notes present already
								displaystring = displaystring ++ " " ++ notename;
							};
							numbernotes = numbernotes+1;
						}
					});
					if (numbernotes > 1) {
						displaystring = "<" ++ displaystring ++ ">";
					};
					this.notefield.string_(displaystring);
				}.defer;
			}
		);

		MIDIdef.noteOff(
			(this.name++"noteofftracker").asSymbol, {
				| vel, noteNum, chan, src |
				this.noteontracker[noteNum] = false;
			}
		);
	}

	asView {
		^View().layout_(this.asLayout);
	}

	asLayout {
		this.notefield = TextField().string_("").enabled_(false);
		this.noterecordrhythm = PopUpMenu().items_(["1", "1/2", "1/4", "1/8", "1/16" , "1/32", "1/64", "1/128"]);
		this.noterecordmultiplier = PopUpMenu().items_(["normal", "triola"]);
		this.preferflats = CheckBox().string_("Use flats").value_(false);
		this.showmidinums = CheckBox().string_("Show midi numbers").value_(false);
		^HLayout(
			StaticText().string_("Last notes played"),
			this.notefield,
			StaticText().string_("Rhythm"),
			this.noterecordrhythm,
			this.noterecordmultiplier,
			this.preferflats,
			this.showmidinums
		);
	}

	getNoteOnTracker {
		^this.noteontracker;
	}

	getLastPlayedNotes {
		^this.notefield.value;
	}

	getLowestLastPlayedNote {
		var flats = this.preferflats.value;
		this.noteontracker.keys.asList.sort.do({
			| key |
			if (this.lastplayednotes[key] == true) {
				^this.numtonote.midinumber_to_notename(key, flats);
			}
		});
		^this.numtonote.midinumber_to_notename(69, flats);
	}
}