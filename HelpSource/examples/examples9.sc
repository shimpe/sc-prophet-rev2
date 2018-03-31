(
~synth = ScProphetRev2.new;
~synth.connect;
)
(
~banks = ["U1", "U2", "U3", "U4", "F1", "F2", "F3", "F4"];
fork {
	"Waiting 2 seconds to get the next patch.".postln;
	2.wait;
	(4..7).do({
		|bank|
		(0..127).do({
			| patch |
			var b = ~banks[bank];
			var p = "P"++(patch+1);
			("Processing patch "++b++p).postln;
			~synth.get_patch_from_synth(bank, patch, {
				~synth.get_global_parameters_from_synth({
					~expl = TemplateFiller.new(~synth.rev2);
					~expl.generate(b, p, "/home/shimpe/documents/rev2presets/"++"patch-"++b++"-"++p++".tex");
				});
			});
			"Waiting 1 seconds to get the next patch.".postln;
			1.wait;
		});
	});
	"Finished.".postln;
};
)
