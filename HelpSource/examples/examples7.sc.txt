(
~synth = ScProphetRev2.new;
~expl = PatchDumper.new;
~synth.connect;
~synth.get_patch_from_synth(6, 120, {
	~synth.get_global_parameters_from_synth({
		~expl.patch_explainer(~synth.rev2, ~synth.rev2_nrpn_globals).join("\n").postln;
	});
});
)

