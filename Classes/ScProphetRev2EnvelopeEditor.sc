ScProphetRev2EnvelopeEditor {
	var <>parent;
	var <>delegationcontrols;
	var <>controls;
	var <>controlspecstore;
	var <>keystore;
	var <>prophet;
	var <>controlbuilder;
	var <>nrpntable;
	var <>layer;
	var <>envview;
	var <>knobs;

	*new {
		|parent, delegationcontrols, controls, controlspecstore, keystore, prophet, controlbuilder, nrpntable, layer |

		^super.new.init(parent, delegationcontrols, controls, controlspecstore, keystore, prophet, controlbuilder, nrpntable, layer);
	}

	init {
		| parent, delegationcontrols, controls, controlspecstore, keystore, prophet, controlbuilder, nrpntable, layer |
		this.parent = parent;
		this.delegationcontrols = delegationcontrols;
		this.controls = controls;
		this.controlspecstore = controlspecstore;
		this.keystore = keystore;
		this.prophet = prophet;
		this.layer = layer;
		this.controlbuilder = controlbuilder;
		this.nrpntable = nrpntable;
		^this
	}

	asView {
		this.knobs = this.make_knobs(this.parent, this.delegationcontrols, this.controls, this.controlspecstore, this.keystore, this.prophet, this.controlbuilder, this.nrpntable, this.layer);
		this.envview = UserView().background_(Color.white);
		this.envview.drawFunc_({
			| uview |
			this.draw_envelopes(uview, minx:0, maxx:uview.bounds.width, miny:0, maxy:uview.bounds.height, margin:10,
				amp_dly:this.controls[\control_ampdelay].value.asInteger,
				amp_a:this.controls[\control_ampattack].value.asInteger,
				amp_d:this.controls[\control_ampdecay].value.asInteger,
				amp_s:this.controls[\control_ampsustain].value.asInteger,
				amp_r:this.controls[\control_amprelease].value.asInteger,
				amp_amt:this.controls[\control_ampenvamt].value.asInteger,
				lpf_dly:this.controls[\control_lpfdelay].value.asInteger,
				lpf_a:this.controls[\control_lpfattack].value.asInteger,
				lpf_d:this.controls[\control_lpfdecay].value.asInteger,
				lpf_s:this.controls[\control_lpfsustain].value.asInteger,
				lpf_r:this.controls[\control_lpfrelease].value.asInteger,
				lpf_amt:this.controls[\control_lpfenvamt].value.asInteger,
				aux_dly:this.controls[\control_auxdel].value.asInteger,
				aux_a:this.controls[\control_auxattack].value.asInteger,
				aux_d:this.controls[\control_auxdecay].value.asInteger,
				aux_s:this.controls[\control_auxsustain].value.asInteger,
				aux_r:this.controls[\control_auxrelease].value.asInteger,
				aux_amt:this.controls[\control_auxenvamt].value.asInteger
			);
		});

		^View().layout_(VLayout(this.envview, this.knobs));
	}

	make_knobs {
		| parent, delegationcontrols, controls, controlspecstore, keystore, prophet, bld, n, layer |
		var lutlay = layer;
		^VLayout(
			HLayout(
				StaticText().string_("choose a VCA Preset"),
				PopUpMenu().allowsReselection_(true).items_(["Bells", "Pluck", "Strings", "Piano/Bass", "Lead Gate", "Fast Pad", "Slow Pad", "Sequence"]).action_({
					| combo |
					if (combo.value == 0) {
						// bells
						controls[\delegating_control_ampdelay].valueAction_("0");
						controls[\delegating_control_ampattack].valueAction_("0");
						controls[\delegating_control_ampdecay].valueAction_("100");
						controls[\delegating_control_ampsustain].valueAction_("0");
						controls[\delegating_control_amprelease].valueAction_("90");
					};
					if (combo.value == 1) {
						// pluck
						controls[\delegating_control_ampdelay].valueAction_("0");
						controls[\delegating_control_ampattack].valueAction_("2");
						controls[\delegating_control_ampdecay].valueAction_("100");
						controls[\delegating_control_ampsustain].valueAction_("0");
						controls[\delegating_control_amprelease].valueAction_("53");
					};
					if (combo.value == 2) {
						// strings
						controls[\delegating_control_ampdelay].valueAction_("0");
						controls[\delegating_control_ampattack].valueAction_("89");
						controls[\delegating_control_ampdecay].valueAction_("0");
						controls[\delegating_control_ampsustain].valueAction_("127");
						controls[\delegating_control_amprelease].valueAction_("97");
					};
					if (combo.value == 3) {
						// piano/bass
						controls[\delegating_control_ampdelay].valueAction_("0");
						controls[\delegating_control_ampattack].valueAction_("3");
						controls[\delegating_control_ampdecay].valueAction_("100");
						controls[\delegating_control_ampsustain].valueAction_("0");
						controls[\delegating_control_amprelease].valueAction_("37");
					};
					if (combo.value == 4) {
						// lead gate
						controls[\delegating_control_ampdelay].valueAction_("0");
						controls[\delegating_control_ampattack].valueAction_("3");
						controls[\delegating_control_ampdecay].valueAction_("0");
						controls[\delegating_control_ampsustain].valueAction_("127");
						controls[\delegating_control_amprelease].valueAction_("30");
					};
					if (combo.value == 5) {
						// fast pad
						controls[\delegating_control_ampdelay].valueAction_("0");
						controls[\delegating_control_ampattack].valueAction_("65");
						controls[\delegating_control_ampdecay].valueAction_("0");
						controls[\delegating_control_ampsustain].valueAction_("127");
						controls[\delegating_control_amprelease].valueAction_("80");
					};
					if (combo.value == 6) {
						// slow pad
						controls[\delegating_control_ampdelay].valueAction_("0");
						controls[\delegating_control_ampattack].valueAction_("110");
						controls[\delegating_control_ampdecay].valueAction_("0");
						controls[\delegating_control_ampsustain].valueAction_("127");
						controls[\delegating_control_amprelease].valueAction_("100");
					};
					if (combo.value == 7) {
						// sequence
						controls[\delegating_control_ampdelay].valueAction_("0");
						controls[\delegating_control_ampattack].valueAction_("0");
						controls[\delegating_control_ampdecay].valueAction_("58");
						controls[\delegating_control_ampsustain].valueAction_("0");
						controls[\delegating_control_amprelease].valueAction_("0");
					};

			}), nil),
			HLayout(
				StaticText().string_("ENV"),
				TextField().enabled_(false).string_("delay"),
				TextField().enabled_(false).string_("attack"),
				TextField().enabled_(false).string_("decay"),
				TextField().enabled_(false).string_("sustain"),
				TextField().enabled_(false).string_("release"),
				TextField().enabled_(false).string_("amount"),
			),
			HLayout(
				StaticText().string_("VCA").stringColor_(Color.white).background_(Color.red),
				bld.make_delegating_textfield(parent, delegationcontrols, controls, controlspecstore, keystore, \ampdelay, prophet, n.str2num('AMP_DLY', lutlay)),
				bld.make_delegating_textfield(parent, delegationcontrols, controls, controlspecstore, keystore, \ampattack, prophet, n.str2num('AMP_ATT', lutlay)),
				bld.make_delegating_textfield(parent, delegationcontrols, controls, controlspecstore, keystore, \ampdecay, prophet, n.str2num('AMP_DEC', lutlay)),
				bld.make_delegating_textfield(parent, delegationcontrols, controls, controlspecstore, keystore, \ampsustain, prophet, n.str2num('AMP_SUS', lutlay)),
				bld.make_delegating_textfield(parent, delegationcontrols, controls, controlspecstore, keystore, \amprelease, prophet, n.str2num('AMP_REL', lutlay)),
				bld.make_delegating_textfield(parent, delegationcontrols, controls, controlspecstore, keystore, \ampenvamt, prophet, n.str2num('AMP_ENVAMT', lutlay), 127)
			),
			HLayout(
				StaticText().string_("LPF").stringColor_(Color.white).background_(Color.blue),
				bld.make_delegating_textfield(parent, delegationcontrols, controls, controlspecstore, keystore, \lpfdelay, prophet, n.str2num('LPF_ENV_DLY', lutlay)),
				bld.make_delegating_textfield(parent, delegationcontrols, controls, controlspecstore, keystore, \lpfattack, prophet, n.str2num('LPF_ENV_ATT', lutlay)),
				bld.make_delegating_textfield(parent, delegationcontrols, controls, controlspecstore, keystore, \lpfdecay, prophet, n.str2num('LPF_ENV_DEC', lutlay)),
				bld.make_delegating_textfield(parent, delegationcontrols, controls, controlspecstore, keystore, \lpfsustain, prophet, n.str2num('LPF_ENV_SUS', lutlay)),
				bld.make_delegating_textfield(parent, delegationcontrols, controls, controlspecstore, keystore, \lpfrelease, prophet, n.str2num('LPF_ENV_REL', lutlay)),
				bld.make_delegating_textfield(parent, delegationcontrols, controls, controlspecstore, keystore, \lpfenvamt, prophet, n.str2num('LPF_ENV_AMT', lutlay), 127)
			),
			HLayout(
				StaticText().string_("AUX").stringColor_(Color.white).background_(Color.green.darken(0.5)),
				bld.make_delegating_textfield(parent, delegationcontrols, controls, controlspecstore, keystore, \auxdel, prophet, n.str2num('ENV3_DEL', lutlay)),
				bld.make_delegating_textfield(parent, delegationcontrols, controls, controlspecstore, keystore, \auxattack, prophet, n.str2num('ENV3_ATT', lutlay)),
				bld.make_delegating_textfield(parent, delegationcontrols, controls, controlspecstore, keystore, \auxdecay, prophet, n.str2num('ENV3_DEC', lutlay)),
				bld.make_delegating_textfield(parent, delegationcontrols, controls, controlspecstore, keystore, \auxsustain, prophet, n.str2num('ENV3_SUS', lutlay)),
				bld.make_delegating_textfield(parent, delegationcontrols, controls, controlspecstore, keystore, \auxrelease, prophet, n.str2num('ENV3_REL', lutlay)),
				bld.make_delegating_textfield(parent, delegationcontrols, controls, controlspecstore, keystore, \auxenvamt, prophet, n.str2num('ENV3_AMT', lutlay), 127)),
			HLayout(
				bld.make_delegating_combobox(parent, delegationcontrols, controls, controlspecstore, keystore, \auxdst, prophet, n.str2num('ENV3_DST', lutlay), prophet.mod_dest_53),
				nil
			)
		);
	}


	drawenv {
		| parent, color, minx, maxx, miny, maxy, linewidth, delay, attack, decay, sustain, release, amount=1 |
		var left = 0;
		var right = 127*4;
		var bottom = -127;
		var epsilon= 0.01;
		var finalamount = amount.linlin(-127,127,-1,1);
		var top = 127;
		var scaled_sustain = sustain.linlin(0,127,0,1);
		var maphor = {
			| x |
			x.linlin(left, right, minx, maxx);
		};
		var mapver = {
			| y |
			y.linlin(bottom, top, ((miny+maxy)/2) + (maxy*finalamount.abs/5), ((miny+maxy)/2 - (maxy*finalamount.abs/5)));
		};

		var extreme_amount=127;
		finalamount = 1;

		Pen.width_(2);
		Pen.fillColor_(color.lighten(0.8));
		Pen.strokeColor_(color.lighten(0.8));

		Pen.line(maphor.(0)@mapver.(0), maphor.(delay)@mapver.(0));
		Pen.line(maphor.(delay)@mapver.(0), maphor.(delay+attack)@mapver.(extreme_amount));
		Pen.line(maphor.(delay+attack)@mapver.(extreme_amount), maphor.(delay+attack+decay)@mapver.(extreme_amount*scaled_sustain));
		Pen.line(maphor.(delay+attack+decay)@mapver.(extreme_amount*scaled_sustain), maphor.(3*127)@mapver.(extreme_amount*scaled_sustain));
		Pen.line(maphor.(3*127)@mapver.(extreme_amount*scaled_sustain), maphor.((3*127)+release)@mapver.(0));

		extreme_amount=127.neg;
		finalamount = 1.neg;

		Pen.line(maphor.(0)@mapver.(0), maphor.(delay)@mapver.(0));
		Pen.line(maphor.(delay)@mapver.(0), maphor.(delay+attack)@mapver.(extreme_amount));
		Pen.line(maphor.(delay+attack)@mapver.(extreme_amount), maphor.(delay+attack+decay)@mapver.(extreme_amount*scaled_sustain));
		Pen.line(maphor.(delay+attack+decay)@mapver.(extreme_amount*scaled_sustain), maphor.(3*127)@mapver.(extreme_amount*scaled_sustain));
		Pen.line(maphor.(3*127)@mapver.(extreme_amount*scaled_sustain), maphor.((3*127)+release)@mapver.(0));

		Pen.stroke;

		Pen.width_(linewidth);
		Pen.fillColor_(color);
		Pen.strokeColor_(color);
		finalamount = amount.linlin(-127,127,-1,1);

		Pen.line(maphor.(0)@mapver.(0), maphor.(delay)@mapver.(0));
		Pen.line(maphor.(delay)@mapver.(0), maphor.(delay+attack)@mapver.(amount));
		Pen.line(maphor.(delay+attack)@mapver.(amount), maphor.(delay+attack+decay)@mapver.(amount*scaled_sustain));
		Pen.line(maphor.(delay+attack+decay)@mapver.(amount*scaled_sustain), maphor.(3*127)@mapver.(amount*scaled_sustain));
		Pen.line(maphor.(3*127)@mapver.(amount*scaled_sustain), maphor.((3*127)+release)@mapver.(0));

		Pen.stroke;
	}

	draw_envelopes {
		| uview, minx, maxx, miny, maxy, margin,
		amp_dly, amp_a, amp_d, amp_s, amp_r, amp_amt,
		lpf_dly, lpf_a, lpf_d, lpf_s, lpf_r, lpf_amt,
		aux_dly, aux_a, aux_d, aux_s, aux_r, aux_amt |

		var linewidth = 5;

		this.drawenv(uview, Color.red,
			minx:minx+margin, maxx:maxx-margin,
			miny:miny+margin, maxy:miny+((maxy-miny)/3)-margin, linewidth:linewidth,
			delay:amp_dly, attack:amp_a, decay:amp_d, sustain:amp_s, release:amp_r, amount:amp_amt);
		Pen.stroke;
		this.drawenv(uview, Color.blue,
			minx:minx+margin, maxx:maxx-margin,
			miny:miny+((maxy-miny)/3)+margin, maxy:miny+(2*((maxy-miny)/3))-margin, linewidth:linewidth,
			delay:lpf_dly, attack:lpf_a, decay:lpf_d, sustain:lpf_s, release:lpf_r, amount:lpf_amt);
		Pen.stroke;
		this.drawenv(uview, Color.green.darken(0.5),
			minx:minx+margin, maxx:maxx-margin,
			miny:miny+(2*((maxy-miny)/3))+margin, maxy:miny+(3*((maxy-miny)/3))-margin, linewidth:linewidth,
			delay:aux_dly, attack:aux_a, decay:aux_d, sustain:aux_s, release:aux_r, amount:aux_amt);
		Pen.stroke;
	}
}
