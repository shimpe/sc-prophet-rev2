(
~synth = ScProphetRev2.new;
~synth.connect;
~synth.select_bank(5);
~synth.select_patch_on_current_bank(3);
~synth.select_patch_by_id("F2", "P5");
)