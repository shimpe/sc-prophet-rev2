(
~synth = ScProphetRev2.new;
~synth.connect;
fork {
	inf.do({
		~synth.get_patch_from_synth(4, 4, {
			var ran = PatchRandomizer.new(~synth.rev2, (0..169), ~synth);
			ran.randomize();
		});
		5.wait;
	});
};
)

