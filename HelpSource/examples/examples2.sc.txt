(
var parser = TheoryNoteParser.new;

~synth = ScProphetRev2.new;
~synth.connect;

fork {
	var duration = 20;
	~synth.get_patch_from_synth(4, 4,
		{ ~patch1 = ~synth.rev2.copy();
			~synth.get_patch_from_synth(4, 5, {
				~patch2 = ~synth.rev2.copy();
			});
	});
	10.wait;
	~patch1.postln;
	~patch2.postln;
	~morpher = PatchMorpher.new([~patch1, ~patch2], (0..169), ~synth);
	~morpher.morph(0, 1, duration, 1);
	duration.wait;
}

)

