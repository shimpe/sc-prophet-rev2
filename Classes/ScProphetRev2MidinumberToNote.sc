ScProphetRev2MidinumberToNote {
	classvar chromatic_scale;
	classvar midi_to_note;

	*new {
		^super.new.init();
	}

	init {
		^this
	}

	*initClass {
		// initialize classvars
		var notenum = 0;
		midi_to_note = ();
		chromatic_scale = ["c", "c#", "d", "d#", "e", "f", "f#", "g", "g#", "a", "a#", "b"];
		11.do({
			| octave |
			chromatic_scale.do({
				| note |
				var o = octave - 1;
				midi_to_note[notenum] = note++o.asString;
				notenum = notenum+1;
			});
		});
	}

	midinumber_to_notename {
		| midinumber |
		^midi_to_note[midinumber.asInt];
	}
}