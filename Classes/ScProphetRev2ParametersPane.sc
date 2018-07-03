ScProphetRev2ParametersPane {
	var <>parent;
	var <>delegationcontrols;
	var <>controls;
	var <>specstore;
	var <>keystore;
	var <>prophet;
	var <>controlbuilder;
	var <>nrpntable;
	var <>layer;

	*new {
		|parent, delegationcontrols, controls, specstore, keystore, prophet, controlbuilder, nrpntable, layer |
		^super.new.init(parent, delegationcontrols, controls, specstore, keystore, prophet, controlbuilder, nrpntable, layer);
	}

	init {
		| parent, delegationcontrols, controls, specstore, keystore, prophet, controlbuilder, nrpntable, layer |
		this.parent = parent;
		this.delegationcontrols = delegationcontrols;
		this.controls = controls;
		this.specstore = specstore;
		this.keystore = keystore;
		this.prophet = prophet;
		this.controlbuilder = controlbuilder;
		this.nrpntable = nrpntable;
		this.layer = layer;
		^this;
	}

	asView {
		^View().layout_(this.make_parameter_layout(this.make_parameter_controls(this.parent, this.delegationcontrols,
			this.controls, this.specstore, this.keystore, this.prophet, this.controlbuilder, this.nrpntable, this.layer)));
	}

	automationView {
		^View().layout_(this.make_automation_layout(this.make_automation_controls(this.controls, this.controlbuilder, this.layer)));
	}

	make_parameter_controls {
		| parent, delegationcontrols, controls, specstore, keystore, prophet, bld, n, layer |
		var d = PatchDumper.new(this.prophet.rev2, this.prophet.last_patch_sysex_stream);
		bld.make_checkbox(parent, d, controls, specstore, keystore,  \unison, prophet, n.str2num('UNISON_OFFON', layer), "Unison");
		bld.make_labeled_combobox(parent, d, delegationcontrols, controls, specstore, keystore,  \voices, prophet, n.str2num('UNISON_MODE', layer), "Voices", prophet.unison_mode);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \detune, prophet, n.str2num('UNISON_DETUNE', layer), "Detune", 0, 16);

		bld.make_label(parent, controls, \osc1, "Oscillators 1&2");
		bld.make_rnd_buttons(parent, controls, specstore, \osc, [\osc1freq, \osc1fine, \osc1shape, \osc1mod, \osc1sub, \osc1noise, \osc1glide, \osc1keyb, \sync, \osc2freq, \osc2fine, \osc2shape, \osc2mod, \oscmix, \slop, \osc2glide, \osc2keyb]);

		bld.make_labeled_combobox(parent, d, delegationcontrols, controls, specstore, keystore,  \osc1freq, prophet, n.str2num('OSC1_FREQ', layer), "Freq", prophet.note_name);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \osc1fine, prophet, n.str2num('OSC1_FINE', layer), "Fine Tune", -50, 50, 50);
		bld.make_labeled_combobox(parent, d, delegationcontrols, controls, specstore, keystore,  \osc1shape, prophet, n.str2num('OSC1_SHAPE', layer), "Shape", prophet.osc_shape);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \osc1mod, prophet, n.str2num('OSC1_SHAPEMOD', layer), "Shape Mod", 0, 99);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \osc1sub, prophet, n.str2num('OSC1_SUBOSC', layer), "Sub Oct", 0, 127);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \osc1noise, prophet, n.str2num('OSC1_NOISE', layer), "Noise", 0, 127);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \osc1glide, prophet, n.str2num('OSC1_GLIDE', layer), "Glide", 0, 127);
		bld.make_checkbox(parent, d, controls, specstore, keystore,  \osc1keyb, prophet, n.str2num('OSC1_KBD', layer), "Keyb");
		bld.make_checkbox(parent, d, controls, specstore, keystore,  \sync, prophet, n.str2num('SYNC', layer), "Sync");

		bld.make_labeled_combobox(parent, d, delegationcontrols, controls, specstore, keystore,  \osc2freq, prophet, n.str2num('OSC2_FREQ', layer), "Freq", prophet.note_name);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \osc2fine, prophet, n.str2num('OSC2_FINE', layer), "Fine Tune", -50, 50, 50);
		bld.make_labeled_combobox(parent, d, delegationcontrols, controls, specstore, keystore,  \osc2shape, prophet, n.str2num('OSC2_SHAPE', layer), "Shape", prophet.osc_shape);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \osc2mod, prophet, n.str2num('OSC2_SHAPEMOD', layer), "Shape Mod", 0, 99);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \oscmix, prophet, n.str2num('OSC_MIX', layer), "Osc Mix", 0, 127);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \slop, prophet, n.str2num('SLOP', layer), "Slop", 0, 127);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \osc2glide, prophet, n.str2num('OSC2_GLIDE', layer), "Glide", 0, 127);
		bld.make_checkbox(parent, d, controls, specstore, keystore, \osc2keyb, prophet, n.str2num('OSC2_KBD', layer), "Keyb");

		bld.make_label(parent, controls, \lpf, "Low pass filter");
		bld.make_rnd_buttons(parent, controls, specstore, \lpf, [\cutoff, \resonance, \lpfenvamt, \lpfenvvel, \lpfkeyamt, \lpfaudiomod,
			\lpfpoles, /*\lpfdelay,*/ \lpfattack, \lpfdecay, \lpfsustain, \lpfrelease]);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \cutoff, prophet, n.str2num('LPF_CUTOFF', layer), "Cut Off", 0, 164);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \resonance, prophet, n.str2num('LPF_RESONANCE', layer), "Resonance", 0, 127);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \lpfenvamt, prophet, n.str2num('LPF_ENV_AMT', layer), "Env Amt", -127, 127, 127);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \lpfenvvel, prophet, n.str2num('LPF_ENV_VEL', layer), "Env Vel", 0, 127);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \lpfkeyamt, prophet, n.str2num('LPF_KBD_TRACKING', layer), "Key Amt", 0, 127);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \lpfaudiomod, prophet, n.str2num('LPF_AUDIO_MOD', layer), "Audio Mod", 0, 127);

		bld.make_checkbox(parent, d, controls, specstore, keystore,  \lpfpoles, prophet, n.str2num('LPF_POLES', layer), "4 Pole");
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \lpfdelay, prophet, n.str2num('LPF_ENV_DLY', layer), "Delay", 0, 127);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \lpfattack, prophet, n.str2num('LPF_ENV_ATT', layer), "Attack", 0, 127);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \lpfdecay, prophet, n.str2num('LPF_ENV_DEC', layer), "Decay", 0, 127);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \lpfsustain, prophet, n.str2num('LPF_ENV_SUS', layer), "Sustain", 0, 127);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \lpfrelease, prophet, n.str2num('LPF_ENV_REL', layer), "Release", 0, 127);

		bld.make_label(parent, controls, \amp, "Amplifier");
		bld.make_rnd_buttons(parent, controls, specstore, \amp, [\amppanspread, \ampenvamt, \ampvel, /*\ampdelay,*/ \ampattack, \ampdecay, \ampsustain, \amprelease]);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \amppanspread, prophet, n.str2num('PAN_SPREAD', layer), "Pan spread", 0, 127);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \ampenvamt, prophet, n.str2num('AMP_ENVAMT', layer), "Env Amt", 0, 127);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \ampvel, prophet, n.str2num('AMP_VELOCITY', layer), "Velocity", 0, 127);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \ampdelay, prophet, n.str2num('AMP_DLY', layer), "Delay", 0, 127);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \ampattack, prophet, n.str2num('AMP_ATT', layer), "Attack", 0, 127);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \ampdecay, prophet, n.str2num('AMP_DEC', layer), "Decay", 0, 127);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \ampsustain, prophet, n.str2num('AMP_SUS', layer), "Sustain", 0, 127);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \amprelease, prophet, n.str2num('AMP_REL', layer), "Release", 0, 127);

		bld.make_label(parent, controls, \lfos, "LFOs");
		bld.make_rnd_buttons(parent, controls, specstore, \lfos, [
			\lfo1shape, \lfo1freq, \lfo1amt, \lfo1dest, \lfo1clksync, \lfo1keysync,
			\lfo2shape, \lfo2freq, \lfo2amt, \lfo2dest, \lfo2clksync, \lfo2keysync,
			\lfo3shape, \lfo3freq, \lfo3amt, \lfo3dest, \lfo3clksync, \lfo3keysync,
			\lfo4shape, \lfo4freq, \lfo4amt, \lfo4dest, \lfo4clksync, \lfo4keysync,
		]);
		bld.make_labeled_combobox(parent, d, delegationcontrols,controls, specstore, keystore,  \lfo1shape, prophet, n.str2num('LFO1_SHAPE', layer), "1 Shape", prophet.lfo_shape);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \lfo1freq, prophet, n.str2num('LFO1_FREQ', layer), "Freq", 0, 150);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \lfo1amt, prophet, n.str2num('LFO1_AMT', layer), "Amount", 0, 127);
		bld.make_labeled_combobox(parent, d, delegationcontrols, controls, specstore, keystore,  \lfo1dest, prophet, n.str2num('LFO1_DEST', layer), "Destination", prophet.mod_dest_53);
		bld.make_checkbox(parent, d, controls, specstore, keystore,  \lfo1clksync, prophet, n.str2num('LFO1_CLKSYNC', layer), "Clk Sync");
		bld.make_checkbox(parent, d, controls, specstore, keystore,  \lfo1keysync, prophet, n.str2num('LFO1_KEYSYNC', layer), "Key Sync");

		bld.make_labeled_combobox(parent, d, delegationcontrols, controls, specstore, keystore,  \lfo2shape, prophet, n.str2num('LFO2_SHAPE', layer), "2 Shape", prophet.lfo_shape);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \lfo2freq, prophet, n.str2num('LFO2_FREQ', layer), "Freq", 0, 150);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \lfo2amt, prophet, n.str2num('LFO2_AMT', layer), "Amount", 0, 127);
		bld.make_labeled_combobox(parent, d, delegationcontrols, controls, specstore, keystore,  \lfo2dest, prophet, n.str2num('LFO2_DEST', layer), "Destination", prophet.mod_dest_53);
		bld.make_checkbox(parent, d, controls, specstore, keystore,  \lfo2clksync, prophet, n.str2num('LFO2_CLKSYNC', layer), "Clk Sync");
		bld.make_checkbox(parent, d, controls, specstore, keystore,  \lfo2keysync, prophet, n.str2num('LFO2_KEYSYNC', layer), "Key Sync");

		bld.make_labeled_combobox(parent, d, delegationcontrols, controls, specstore, keystore,  \lfo3shape, prophet, n.str2num('LFO3_SHAPE', layer), "3 Shape", prophet.lfo_shape);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \lfo3freq, prophet, n.str2num('LFO3_FREQ', layer), "Freq", 0, 150);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \lfo3amt, prophet, n.str2num('LFO3_AMT', layer), "Amount", 0, 127);
		bld.make_labeled_combobox(parent, d, delegationcontrols, controls, specstore, keystore,  \lfo3dest, prophet, n.str2num('LFO3_DEST', layer), "Destination", prophet.mod_dest_53);
		bld.make_checkbox(parent, d, controls, specstore, keystore,  \lfo3clksync, prophet, n.str2num('LFO3_CLKSYNC', layer), "Clk Sync");
		bld.make_checkbox(parent, d, controls, specstore, keystore,  \lfo3keysync, prophet, n.str2num('LFO3_KEYSYNC', layer), "Key Sync");

		bld.make_labeled_combobox(parent, d, delegationcontrols, controls, specstore, keystore,  \lfo4shape, prophet, n.str2num('LFO4_SHAPE', layer), "4 Shape", prophet.lfo_shape);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \lfo4freq, prophet, n.str2num('LFO4_FREQ', layer), "Freq", 0, 150);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \lfo4amt, prophet, n.str2num('LFO4_AMT', layer), "Amount", 0, 127);
		bld.make_labeled_combobox(parent, d, delegationcontrols, controls, specstore, keystore,  \lfo4dest, prophet, n.str2num('LFO4_DEST', layer), "Destination", prophet.mod_dest_53);
		bld.make_checkbox(parent, d, controls, specstore, keystore,  \lfo4clksync, prophet, n.str2num('LFO4_CLKSYNC', layer), "Clk Sync");
		bld.make_checkbox(parent, d, controls, specstore, keystore,  \lfo4keysync, prophet, n.str2num('LFO4_KEYSYNC', layer), "Key Sync");

		bld.make_label(parent, controls, \fx, "Effects");
		bld.make_rnd_buttons(parent, controls, specstore, \fx, [
			\fxselect, \fxmix, \fxclksync, \fxparam1, \fxparam2
		]);
		bld.make_checkbox(parent, d, controls, specstore, keystore,  \fxon, prophet, n.str2num('FX_ONOFF', layer), "On");
		bld.make_labeled_combobox(parent, d, delegationcontrols, controls, specstore, keystore,  \fxselect, prophet, n.str2num('FX_SELECT', layer), "Effect", prophet.fx_select);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \fxmix, prophet, n.str2num('FX_MIX', layer), "Mix", 0, 127);
		bld.make_checkbox(parent, d, controls, specstore, keystore,  \fxclksync, prophet, n.str2num('FX_CLKSYNC', layer), "Clk Sync");
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \fxparam1, prophet, n.str2num('FX_PARAM1', layer), "Param 1", 0, 127);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \fxparam2, prophet, n.str2num('FX_PARAM2', layer), "Param 2", 0, 127);

		bld.make_label(parent, controls, \arp, "Clock and arp");
		bld.make_rnd_buttons(parent, controls, specstore, \arp, [
			\arpon, \arpbpm, \arpdivide, \arpmode, \arprange, \arprepeats, \arprelatch
		]);
		bld.make_checkbox(parent, d, controls, specstore, keystore,  \arpon, prophet, n.str2num('ARP_OFFON', layer), "Arp On");
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \arpbpm, prophet, n.str2num('BPM_TEMPO', layer), "BPM", 30, 250);
		bld.make_labeled_combobox(parent, d, delegationcontrols, controls, specstore, keystore,  \arpdivide, prophet, n.str2num('ARP_CLK_DIV', layer), "Divide", prophet.divide);
		bld.make_labeled_combobox(parent, d, delegationcontrols, controls, specstore, keystore,  \arpmode, prophet, n.str2num('ARP_MODE', layer), "Mode", prophet.arp_mode);
		bld.make_labeled_combobox(parent, d, delegationcontrols, controls, specstore, keystore,  \arprange, prophet, n.str2num('ARP_RANGE', layer), "Range", prophet.arp_range);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \arprepeats, prophet, n.str2num('ARP_RPTS', layer), "Repeats", 0, 3);
		bld.make_checkbox(parent, d, controls, specstore, keystore,  \arprelatch, prophet, n.str2num('ARP_RELATCH', layer), "Relatch");

		bld.make_label(parent, controls, \seq, "Sequencer");
		bld.make_labeled_combobox(parent, d, delegationcontrols, controls, specstore, keystore,  \seqtype, prophet, n.str2num('SEQ_GATEDPOLY', layer), "Type", prophet.sequencer_type);
		bld.make_labeled_combobox(parent, d, delegationcontrols, controls, specstore, keystore,  \seqmode, prophet, n.str2num('GATED_SEQMODE', layer), "Type", prophet.sequencer_mode);

		bld.make_label(parent, controls, \aux, "Aux Envelope");
		bld.make_rnd_buttons(parent, controls, specstore, \aux, [
			\auxdst, \auxenvamt, \auxvel, /*\auxdel,*/ \auxrpt,
			\auxattack, \auxdecay, \auxsustain, \auxrelease
		]);
		bld.make_labeled_combobox(parent, d, delegationcontrols, controls, specstore, keystore,  \auxdst, prophet, n.str2num('ENV3_DST', layer), "Dest", prophet.mod_dest_53);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \auxenvamt, prophet, n.str2num('ENV3_AMT', layer), "Env Amt", -127, 127, 127);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \auxvel, prophet, n.str2num('ENV3_VEL', layer), "Vel", 0, 127);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \auxdel, prophet, n.str2num('ENV3_DEL', layer), "Delay", 0, 127);
		bld.make_checkbox(parent, d, controls, specstore, keystore,  \auxrpt, prophet, n.str2num('ENV3_RPT', layer), "Repeat");

		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \auxattack, prophet, n.str2num('ENV3_ATT', layer), "Attack", 0, 127);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \auxdecay, prophet, n.str2num('ENV3_DEC', layer), "Decay", 0, 127);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \auxsustain, prophet, n.str2num('ENV3_SUS', layer), "Sustain", 0, 127);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \auxrelease, prophet, n.str2num('ENV3_REL', layer), "Release", 0, 127);

		bld.make_label(parent, controls, \mod, "Mod Matrix");
		bld.make_rnd_buttons(parent, controls, specstore, \mod, [
			\mod1src, \mod1dst, \mod1amt,
			\mod2src, \mod2dst, \mod2amt,
			\mod3src, \mod3dst, \mod3amt,
			\mod4src, \mod4dst, \mod4amt,
			\mod5src, \mod5dst, \mod5amt,
			\mod6src, \mod6dst, \mod6amt,
			\mod7src, \mod7dst, \mod7amt,
			\mod8src, \mod8dst, \mod8amt,
		]);
		bld.make_labeled_combobox(parent, d, delegationcontrols, controls, specstore, keystore,  \mod1src, prophet, n.str2num('MOD1_SRC', layer), "1 Source", prophet.mod_source);
		bld.make_labeled_combobox(parent, d, delegationcontrols, controls, specstore, keystore,  \mod1dst, prophet, n.str2num('MOD1_DST', layer), "Dest", prophet.mod_dest_53);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \mod1amt, prophet, n.str2num('MOD1_AMT', layer), "Amount", -127, 127, 127);
		bld.make_labeled_combobox(parent, d, delegationcontrols, controls, specstore, keystore,  \mod5src, prophet, n.str2num('MOD5_SRC', layer), "5 Source", prophet.mod_source);
		bld.make_labeled_combobox(parent, d, delegationcontrols, controls, specstore, keystore,  \mod5dst, prophet, n.str2num('MOD5_DST', layer), "Dest", prophet.mod_dest_53);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \mod5amt, prophet, n.str2num('MOD5_AMT', layer), "Amount", -127, 127, 127);

		bld.make_labeled_combobox(parent, d, delegationcontrols, controls, specstore, keystore,  \mod2src, prophet, n.str2num('MOD2_SRC', layer), "2 Source", prophet.mod_source);
		bld.make_labeled_combobox(parent, d, delegationcontrols, controls, specstore, keystore,  \mod2dst, prophet, n.str2num('MOD2_DST', layer), "Dest", prophet.mod_dest_53);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \mod2amt, prophet, n.str2num('MOD2_AMT', layer), "Amount", -127, 127, 127);
		bld.make_labeled_combobox(parent, d, delegationcontrols, controls, specstore, keystore,  \mod6src, prophet, n.str2num('MOD6_SRC', layer), "6 Source", prophet.mod_source);
		bld.make_labeled_combobox(parent, d, delegationcontrols, controls, specstore, keystore,  \mod6dst, prophet, n.str2num('MOD6_DST', layer), "Dest", prophet.mod_dest_53);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \mod6amt, prophet, n.str2num('MOD6_AMT', layer), "Amount", -127, 127, 127);

		bld.make_labeled_combobox(parent, d, delegationcontrols, controls, specstore, keystore,  \mod3src, prophet, n.str2num('MOD3_SRC', layer), "3 Source", prophet.mod_source);
		bld.make_labeled_combobox(parent, d, delegationcontrols, controls, specstore, keystore,  \mod3dst, prophet, n.str2num('MOD3_DST', layer), "Dest", prophet.mod_dest_53);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \mod3amt, prophet, n.str2num('MOD3_AMT', layer), "Amount", -127, 127, 127);
		bld.make_labeled_combobox(parent, d, delegationcontrols, controls, specstore, keystore,  \mod7src, prophet, n.str2num('MOD7_SRC', layer), "7 Source", prophet.mod_source);
		bld.make_labeled_combobox(parent, d, delegationcontrols, controls, specstore, keystore,  \mod7dst, prophet, n.str2num('MOD7_DST', layer), "Dest", prophet.mod_dest_53);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \mod7amt, prophet, n.str2num('MOD7_AMT', layer), "Amount", -127, 127, 127);

		bld.make_labeled_combobox(parent, d, delegationcontrols, controls, specstore, keystore,  \mod4src, prophet, n.str2num('MOD4_SRC', layer), "4 Source", prophet.mod_source);
		bld.make_labeled_combobox(parent, d, delegationcontrols, controls, specstore, keystore,  \mod4dst, prophet, n.str2num('MOD4_DST', layer), "Dest", prophet.mod_dest_53);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \mod4amt, prophet, n.str2num('MOD4_AMT', layer), "Amount", -127, 127, 127);
		bld.make_labeled_combobox(parent, d, delegationcontrols, controls, specstore, keystore,  \mod8src, prophet, n.str2num('MOD8_SRC', layer), "8 Source", prophet.mod_source);
		bld.make_labeled_combobox(parent, d, delegationcontrols, controls, specstore, keystore,  \mod8dst, prophet, n.str2num('MOD8_DST', layer), "Dest", prophet.mod_dest_53);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \mod8amt, prophet, n.str2num('MOD8_AMT', layer), "Amount", -127, 127, 127);

		bld.make_label(parent, controls, \controls, "Controls");
		bld.make_labeled_combobox(parent, d, delegationcontrols, controls, specstore, keystore,  \modwheeldst, prophet, n.str2num('MODWHEEL_DST', layer), "Mod wheel dst", prophet.mod_dest_53);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \modwheelamt, prophet, n.str2num('MODWHEEL_AMT', layer), "Amount", -127, 127, 127);
		bld.make_labeled_combobox(parent, d, delegationcontrols, controls, specstore, keystore,  \pressuredst, prophet, n.str2num('PRESSURE_DST', layer), "Pressure dst", prophet.mod_dest_53);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \pressureamt, prophet, n.str2num('PRESSURE_AMT', layer), "Amount", -127, 127, 127);
		bld.make_labeled_combobox(parent, d, delegationcontrols, controls, specstore, keystore,  \breathdst, prophet, n.str2num('BREATH_DST', layer), "Breath dst", prophet.mod_dest_53);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \breathamt, prophet, n.str2num('BREATH_AMT', layer), "Amount", -127, 127, 127);
		bld.make_labeled_combobox(parent, d, delegationcontrols, controls, specstore, keystore,  \velocitydst, prophet, n.str2num('VELOCITY_DST', layer), "Velocity dst", prophet.mod_dest_53);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \velocityamt, prophet, n.str2num('VELOCITY_AMT', layer), "Amount", -127, 127, 127);
		bld.make_labeled_combobox(parent, d, delegationcontrols, controls, specstore, keystore,  \footdst, prophet, n.str2num('FOOTCTRL_DST', layer), "Foot control dst", prophet.mod_dest_53);
		bld.make_labeled_textfield(parent, d, delegationcontrols, controls, specstore, keystore,  \footamt, prophet, n.str2num('FOOTCTRL_AMT', layer), "Amount", -127, 127, 127);

		^controls;
	}

	make_parameter_layout {
		| controls |
		^VLayout(
			HLayout(
				controls[\control_unison],
				*([\voices, \detune].collect({
					|key|
					[("label_"++key.asString).asSymbol, ("control_"++key.asString).asSymbol]
				}).flatten.collect({ | el | controls[el] })).add([nil]),
			),
			HLayout(
				controls[\label_osc1],
				controls[\button_perturbosc],
				controls[\button_randomizeosc],
				[nil]
			),
			HLayout(
				controls[\label_osc1freq],
				*([\osc1freq, \osc1fine, \osc1shape, \osc1mod, \osc1sub, \osc1noise, \osc1glide].collect({
					|key|
					[("label_"++key.asString).asSymbol, ("control_"++key.asString).asSymbol]
				}).flatten.collect({ | el | controls[el] })).add(controls[\control_osc1keyb]).add([nil]),
			),
			HLayout(
				controls[\control_sync],
				[nil]
			),
			HLayout(
				*([\osc2freq, \osc2fine, \osc2shape, \osc2mod, \oscmix, \slop, \osc2glide].collect({
					|key|
					[("label_"++key.asString).asSymbol, ("control_"++key.asString).asSymbol]
				}).flatten.collect({ | el | controls[el] })).add(controls[\control_osc2keyb]).add([nil]),
			),
			HLayout(
				controls[\label_lpf],
				controls[\button_perturblpf],
				controls[\button_randomizelpf],
				[nil]
			),
			HLayout(
				*([\cutoff, \resonance, \lpfenvamt, \lpfenvvel, \lpfkeyamt, \lpfaudiomod].collect({
					|key|
					[("label_"++key.asString).asSymbol, ("control_"++key.asString).asSymbol]
				}).flatten.collect({ | el | controls[el] })).add([nil]),
			),
			HLayout(
				controls[\control_lpfpoles],
				*([\lpfdelay, \lpfattack, \lpfdecay, \lpfsustain, \lpfrelease].collect({
					|key|
					[("label_"++key.asString).asSymbol, ("control_"++key.asString).asSymbol]
				}).flatten.collect({ | el | controls[el] })).add([nil]),
			),
			HLayout(
				controls[\label_amp],
				controls[\button_perturbamp],
				controls[\button_randomizeamp],
				[nil]
			),
			HLayout(
				*([\amppanspread, \ampenvamt, \ampvel, \ampdelay].collect({
					|key|
					[("label_"++key.asString).asSymbol, ("control_"++key.asString).asSymbol]
				}).flatten.collect({ | el | controls[el] })).add([nil]),
			),
			HLayout(
				*([\ampattack, \ampdecay, \ampsustain, \amprelease].collect({
					|key|
					[("label_"++key.asString).asSymbol, ("control_"++key.asString).asSymbol]
				}).flatten.collect({ | el | controls[el] })).add([nil]),
			),
			HLayout(
				controls[\label_lfos],
				controls[\button_perturblfos],
				controls[\button_randomizelfos],

				[nil]
			),
			HLayout(
				*([\lfo1shape, \lfo1freq, \lfo1amt, \lfo1dest].collect({
					|key|
					[("label_"++key.asString).asSymbol, ("control_"++key.asString).asSymbol]
				}).flatten.collect({ | el | controls[el] })).add(controls[\control_lfo1clksync]).add(controls[\control_lfo1keysync]).add([nil]),
			),
			HLayout(
				*([\lfo2shape, \lfo2freq, \lfo2amt, \lfo2dest].collect({
					|key|
					[("label_"++key.asString).asSymbol, ("control_"++key.asString).asSymbol]
				}).flatten.collect({ | el | controls[el] })).add(controls[\control_lfo2clksync]).add(controls[\control_lfo2keysync]).add([nil]),
			),
			HLayout(
				*([\lfo3shape, \lfo3freq, \lfo3amt, \lfo3dest].collect({
					|key|
					[("label_"++key.asString).asSymbol, ("control_"++key.asString).asSymbol]
				}).flatten.collect({ | el | controls[el] })).add(controls[\control_lfo3clksync]).add(controls[\control_lfo3keysync]).add([nil]),
			),
			HLayout(
				*([\lfo4shape, \lfo4freq, \lfo4amt, \lfo4dest].collect({
					|key|
					[("label_"++key.asString).asSymbol, ("control_"++key.asString).asSymbol]
				}).flatten.collect({ | el | controls[el] })).add(controls[\control_lfo4clksync]).add(controls[\control_lfo4keysync]).add([nil]),
			),
			HLayout(
				controls[\label_fx],
				controls[\button_perturbfx],
				controls[\button_randomizefx],
				[nil]
			),
			HLayout(
				controls[\control_fxon],
				*([\fxselect, \fxmix].collect({
					|key|
					[("label_"++key.asString).asSymbol, ("control_"++key.asString).asSymbol]
				}).flatten.collect({ | el | controls[el] })).add([nil]),
			),
			HLayout(
				controls[\control_fxclksync],
				*([\fxparam1, \fxparam2].collect({
					|key|
					[("label_"++key.asString).asSymbol, ("control_"++key.asString).asSymbol]
				}).flatten.collect({ | el | controls[el] })).add([nil]),
			),
			HLayout(
				controls[\label_arp],
				controls[\button_perturbarp],
				controls[\button_randomizearp],
				[nil]
			),
			HLayout(
				controls[\control_arpon],
				*([\arpbpm, \arpdivide, \arpmode, \arprange, \arprepeats].collect({
					|key|
					[("label_"++key.asString).asSymbol, ("control_"++key.asString).asSymbol]
				}).flatten.collect({ | el | controls[el] })).add(controls[\control_arprelatch]).add([nil]),
			),
			HLayout(
				controls[\label_seq],
				[nil]
			),
			HLayout(
				*([\seqtype, \seqmode].collect({
					|key|
					[("label_"++key.asString).asSymbol, ("control_"++key.asString).asSymbol]
				}).flatten.collect({ | el | controls[el] })).add([nil]),
			),
			HLayout(
				controls[\label_aux],
				controls[\button_perturbaux],
				controls[\button_randomizeaux],
				[nil]
			),
			HLayout(
				*([\auxdst, \auxenvamt, \auxvel, \auxdel].collect({
					|key|
					[("label_"++key.asString).asSymbol, ("control_"++key.asString).asSymbol]
				}).flatten.collect({ | el | controls[el] })).add(controls[\control_auxrpt]).add([nil]),
			),
			HLayout(
				*([\auxattack, \auxdecay, \auxsustain, \auxrelease].collect({
					|key|
					[("label_"++key.asString).asSymbol, ("control_"++key.asString).asSymbol]
				}).flatten.collect({ | el | controls[el] })).add([nil]),
			),
			HLayout(
				controls[\label_mod],
				controls[\button_perturbmod],
				controls[\button_randomizemod],
				[nil]
			),
			HLayout(
				*([\mod1src, \mod1dst, \mod1amt, \mod5src, \mod5dst, \mod5amt].collect({
					|key|
					[("label_"++key.asString).asSymbol, ("control_"++key.asString).asSymbol]
				}).flatten.collect({ | el | controls[el] })).add([nil]),
			),
			HLayout(
				*([\mod2src, \mod2dst, \mod2amt, \mod6src, \mod6dst, \mod6amt].collect({
					|key|
					[("label_"++key.asString).asSymbol, ("control_"++key.asString).asSymbol]
				}).flatten.collect({ | el | controls[el] })).add([nil]),
			),
			HLayout(
				*([\mod3src, \mod3dst, \mod3amt, \mod7src, \mod7dst, \mod7amt].collect({
					|key|
					[("label_"++key.asString).asSymbol, ("control_"++key.asString).asSymbol]
				}).flatten.collect({ | el | controls[el] })).add([nil]),
			),
			HLayout(
				*([\mod4src, \mod4dst, \mod4amt, \mod8src, \mod8dst, \mod8amt].collect({
					|key|
					[("label_"++key.asString).asSymbol, ("control_"++key.asString).asSymbol]
				}).flatten.collect({ | el | controls[el] })).add([nil]),
			),
			HLayout(
				controls[\label_controls],
				[nil]
			),
			HLayout(
				*([\modwheeldst, \modwheelamt, \velocitydst, \velocityamt].collect({
					|key|
					[("label_"++key.asString).asSymbol, ("control_"++key.asString).asSymbol]
				}).flatten.collect({ | el | controls[el] })).add([nil]),
			),
			HLayout(
				*([\pressuredst, \pressureamt, \footdst, \footamt].collect({
					|key|
					[("label_"++key.asString).asSymbol, ("control_"++key.asString).asSymbol]
				}).flatten.collect({ | el | controls[el] })).add([nil]),
			),
			HLayout(
				*([\breathdst, \breathamt].collect({
					|key|
					[("label_"++key.asString).asSymbol, ("control_"++key.asString).asSymbol]
				}).flatten.collect({ | el | controls[el] })).add([nil]),
			),
			[nil]
		);
	}

	make_automation_layout {
		|  controls |
		var groupkeys = ["label_rndgrp_", "control_rndgrp_onoff_", "control_rndgrp_nudgerandomize_",
			"label_rndgrp_period_", "control_rndgrp_period_"];
		var hlayoutlist = ["osc", "lpf", "amp", "lfos", "fx", "arp", "aux", "mod"].collect({
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
		bld.make_automation_control(controls, "Oscillators 1&2", "osc", layer);
		bld.make_automation_control(controls, "Low pass filter", "lpf", layer);
		bld.make_automation_control(controls, "Amplifier", "amp", layer);
		bld.make_automation_control(controls, "LFOs", "lfos", layer);
		bld.make_automation_control(controls, "Effects", "fx", layer);
		bld.make_automation_control(controls, "Clock and arp", "arp", layer);
		bld.make_automation_control(controls, "Aux Envelope", "aux", layer);
		bld.make_automation_control(controls, "Mod Matrix", "mod", layer);
		^controls;
	}
}