ScProphetRev2MidinumberToNote {
	classvar chromatic_scale;
	classvar flat_chromatic_scale;
	classvar midi_to_note;
	classvar midi_to_flat_note;

	*new {
		^super.new.init();
	}

	init {
		^this
	}

	*initClass {
		// initialize classvars
		var notenum = 0;
		var flatnotenum = 0;
		midi_to_note = ();
		midi_to_flat_note = ();
		chromatic_scale = ["c", "c#", "d", "d#", "e", "f", "f#", "g", "g#", "a", "a#", "b"];
		flat_chromatic_scale = ["c", "d-", "d", "e-", "e", "f", "g-", "g", "a-", "a", "b-", "b"];
		11.do({
			| octave |
			chromatic_scale.do({
				| note |
				var o = octave - 1;
				midi_to_note[notenum] = note++o.asString;
				notenum = notenum+1;
			});
			flat_chromatic_scale.do({
				| note |
				var o = octave - 1;
				midi_to_flat_note[flatnotenum] = note++o.asString;
				flatnotenum = flatnotenum+1;
			});
		});
	}

	midinumber_to_notename {
		| midinumber, prefer_flats=false |
		if (prefer_flats.not) {
			^midi_to_note[midinumber.asInt];
		} {
			^midi_to_flat_note[midinumber.asInt];
		};
	}
}