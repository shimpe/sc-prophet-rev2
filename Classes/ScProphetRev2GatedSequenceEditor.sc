ScProphetRev2GatedSequenceEditor {
	var <>parent;
	var <>patchdumper;
	var <>steps;
	var <>delegationcontrols;
	var <>controls;
	var <>specstore;
	var <>keystore;
	var <>controlbuilder;
	var <>prophet;
	var <>nrpntable;
	var <>layer;

	*new {
		| parent, patchdumper, steps, delegationcontrols, controls, specstore, keystore, prophet, controlbuilder, nrpntable, layer |
		^super.new.init(parent, patchdumper, steps, delegationcontrols, controls, specstore, keystore, prophet, controlbuilder, nrpntable, layer);
	}

	init {
		|parent, patchdumper, steps, delegationcontrols, controls, specstore, keystore, prophet, controlbuilder, nrpntable, layer|
		this.parent = parent;
		this.patchdumper = patchdumper;
		this.steps = steps;
		this.delegationcontrols = delegationcontrols;
		this.controls = controls;
		this.specstore = specstore;
		this.keystore = keystore;
		this.controlbuilder = controlbuilder;
		this.prophet = prophet;
		this.nrpntable = nrpntable;
		this.layer = layer;
		^this;
	}

	asView {
		^View().layout_(this.make_gated_sequencer_controls(
			this.parent,
			this.patchdumper,
			this.delegationcontrols,
			this.controls,
			this.specstore,
			this.keystore,
			this.prophet,
			this.controlbuilder,
			this.nrpntable,
			this.layer));
	}

	make_seq_combos {
		| parent, patchdumper, delegationcontrols, controls, specstore, keystore, prophet, bld, nrpntable, layer |
		bld.make_labeled_combobox(parent, patchdumper,
			delegationcontrols, controls, specstore, keystore,
			\seq1destcombo, prophet, nrpntable.str2num('SEQ1_DST', layer),
			"Gated Sequencer Destination Track 1", prophet.mod_dest_53);
		bld.make_rnd_buttons(parent, controls, specstore, \seq1, [\seq1destcombo, \plotter1]);
		bld.make_labeled_combobox(parent, patchdumper,
			delegationcontrols, controls, specstore, keystore,
			\seq2destcombo, prophet, nrpntable.str2num('SEQ2_DST', layer),
			"Gated Sequencer Destination Track 2", prophet.mod_dest_54);
		bld.make_rnd_buttons(parent, controls, specstore, \seq2, [\seq2destcombo, \plotter2]);
		bld.make_labeled_combobox(parent, patchdumper,
			delegationcontrols, controls, specstore, keystore,
			\seq3destcombo, prophet, nrpntable.str2num('SEQ3_DST', layer),
			"Gated Sequencer Destination Track 3", prophet.mod_dest_53);
		bld.make_rnd_buttons(parent, controls, specstore, \seq3, [\seq3destcombo, \plotter3]);
		bld.make_labeled_combobox(parent, patchdumper,
			delegationcontrols, controls, specstore, keystore,
			\seq4destcombo, prophet, nrpntable.str2num('SEQ4_DST', layer),
			"Gated Sequencer Destination Track 4", prophet.mod_dest_54);
		bld.make_rnd_buttons(parent, controls, specstore, \seq4, [\seq4destcombo, \plotter4]);
	}

	make_plotter {
		| name, basenrpn, parent, delegationcontrols, controls, specstore, keystore, key, prophet, nrpntable, layer |
		var gSEQSTEPS = this.steps;
		var plotkey = ("control_"++key.asString).asSymbol;
		var defaultvalues;
		var nrpn = nrpntable.str2num(basenrpn, layer);

		keystore[plotkey] = {gSEQSTEPS.collect({
			|step|
			this.patchdumper.init(prophet.rev2, prophet.last_sysex_stream);
			this.patchdumper.lut(nrpn+step, 0, 127, norange:true, midivalue:true, includeunit:false).asInteger;
		})};

		specstore[plotkey] = (\type:\plot, \nrpn:nrpn, \steps:gSEQSTEPS, \specs:ControlSpec(0, 127, \lin, 1, 0, "steps"), \domainspecs:ControlSpec(0, 15, \lin, 1, 0, "steps"), \prophet:prophet);

		defaultvalues = keystore[plotkey].();
		//("defaultvalues: "++defaultvalues).postln;
		controls[plotkey] = Plotter(name, Rect(), parent)
		.plotMode_(\bars)
		.editMode_(true)
		.value_(defaultvalues)
		.specs_(ControlSpec(0, 127, \lin, 1, 0, "steps"))
		.domainSpecs_(ControlSpec(0, 15, \lin, 1, 0, "steps"))
		.editFunc_({
			|plotter, plotIndex, i, value |
			{
				var finalnrpn = nrpn + i;
				prophet.sendNRPN(finalnrpn, value.round(1).asInteger);
				controls[plotkey].interactionView.refresh;
			}.defer;
		}).setProperties(\plotColor, Color.blue);

		gSEQSTEPS.do({
			|step|
			prophet.makeNRPNResponder(nrpn+step, {
				| value, nrpn |
				{
					//("DATA: "++controls[plotkey].data++" step: "++step++" nrpn: "++nrpn++" value: "++value++" plotkey: "++plotkey).postln;
					controls[plotkey].data[0][step] = value;
					controls[plotkey].interactionView.refresh;
				}.defer;
			});
		});
		^controls[plotkey];
	}

	make_plotters {
		| parent, delegationcontrols, controls, specstore, keystore, prophet, nrpntable, layer |
		this.make_plotter("P1", 'SEQ1_GATED_STEP1_TRACK1', parent, delegationcontrols, controls, specstore, keystore, \plotter1, prophet, nrpntable, layer);
		this.make_plotter("P2", 'SEQ1_GATED_STEP1_TRACK2', parent, delegationcontrols, controls, specstore, keystore, \plotter2, prophet, nrpntable, layer);
		this.make_plotter("P3", 'SEQ1_GATED_STEP1_TRACK3', parent, delegationcontrols, controls, specstore, keystore, \plotter3, prophet, nrpntable, layer);
		this.make_plotter("P4", 'SEQ1_GATED_STEP1_TRACK4', parent, delegationcontrols, controls, specstore, keystore, \plotter4, prophet, nrpntable, layer);
	}

	make_gated_sequencer_controls {
		| parent, patchdumper, delegationcontrols, controls, specstore, keystore, prophet, bld, nrpntable, layer |

		this.make_seq_combos(parent, patchdumper, delegationcontrols, controls, specstore, keystore, prophet, bld, nrpntable, layer);
		this.make_plotters(parent, delegationcontrols, controls, specstore, keystore, prophet, nrpntable, layer);

		^VLayout(
			HLayout(controls[\label_seq1destcombo], controls[\control_seq1destcombo], controls[\button_perturbseq1], controls[\button_randomizeseq1], nil),
			HLayout(controls[\control_plotter1].interactionView),
			HLayout(controls[\label_seq2destcombo], controls[\control_seq2destcombo], controls[\button_perturbseq2], controls[\button_randomizeseq2], nil),
			HLayout(controls[\control_plotter2].interactionView),
			HLayout(controls[\label_seq3destcombo], controls[\control_seq3destcombo], controls[\button_perturbseq3], controls[\button_randomizeseq3], nil),
			HLayout(controls[\control_plotter3].interactionView),
			HLayout(controls[\label_seq4destcombo], controls[\control_seq4destcombo], controls[\button_perturbseq4], controls[\button_randomizeseq4], nil),
			HLayout(controls[\control_plotter4].interactionView)
		);
	}

	automationView {
		^View().layout_(this.make_automation_layout(this.make_automation_controls(this.controls, this.controlbuilder, this.layer)));
	}

	make_automation_layout {
		|  controls |
		var groupkeys = ["label_rndgrp_", "control_rndgrp_onoff_", "control_rndgrp_nudgerandomize_",
			"label_rndgrp_period_", "control_rndgrp_period_"];
		var hlayoutlist = ["seq1", "seq2", "seq3", "seq4"].collect({
			| detailkey |
			var group = groupkeys.collect({ |key| controls[(key++detailkey).asSymbol] });
			group = group.add(nil);
			HLayout(*group);
		});
		hlayoutlist = hlayoutlist.add(nil);
		^VLayout(*hlayoutlist);
	}

	make_automation_controls {
		| controls, bld, layer |
		bld.make_automation_control(controls, "Gated Seq Track 1", "seq1", layer);
		bld.make_automation_control(controls, "Gated Seq Track 2", "seq2", layer);
		bld.make_automation_control(controls, "Gated Seq Track 3", "seq3", layer);
		bld.make_automation_control(controls, "Gated Seq Track 4", "seq4", layer);
		^controls;
	}

}

