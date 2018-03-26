PatchDumper {

	var <>rev2;
	var <>rev2_nrpn_globals;
	var <>note_name;

	*new {
		^super.new.init();
	}

	init {
	}

	lut {
		| constant, offset=0, mask = 16rFFFFFFFF, norange = false |
		/*
		if (mask != -1) {
		("constant "++constant).postln;
		("offset "++offset).postln;
		("mask "++mask).postln;
		("norange "++norange).postln;
		};
		*/
		if (this.rev2.isNil) {
			^"rev2 internal datastructure not initialized";
		};

		if (this.rev2[constant+offset].isNil) {
			^"??"
		} /* else */ {
			if (this.rev2[constant+offset][\curval].isNil) {
				^("UNKNOWN curval at idx "++(constant+offset));
			} {
				if (this.rev2[constant+offset][\hint].notNil) {
					if (this.rev2[constant+offset][\hint][(this.rev2[constant+offset][\curval] & mask)].isNil) {
						^("Warning: lut couldn't translate value "++(this.rev2[constant+offset][\curval] & mask)++
							" for nrpn constant "++(constant+offset)++". Perhaps you need to specify a mask in call to lut.");
					} {
						^(this.rev2[constant+offset][\hint][(this.rev2[constant+offset][\curval] & mask)]);
					}
				} {
					var unit = "";
					if (this.rev2[constant+offset][\unit].notNil) {
						unit = this.rev2[constant+offset][\unit];
					};
					if (norange) {
						^(this.rev2[constant+offset][\curval].asString ++ unit);
					} {
						var percentage = this.rev2[constant+offset][\curval].linlin(this.rev2[constant+offset][\min], this.rev2[constant+offset][\max], 0, 100);
						^(this.rev2[constant+offset][\curval].asString ++ unit ++ " ("++percentage.round(1)++"% of range)");
					};
				};
			};
		};
		^"!!";
	}


	charlut {
		| constant, offset |
		var result;
		if (this.rev2[constant+offset][\curval].isNil) {
			^("UNKNOWN character at idx "++(constant+offset));
		} {
			^((this.rev2[constant+offset][\curval]).asAscii);
		};
	}

	patch_explainer {
		| rv2, nrpnglobals |
		var explanation = [];
		var cLAYER_MODE = 4190;
		var cOSC1_FREQ_A = 0;
		var cOSC1_FINE_A = 1;
		var cOSC1_SHAPE_A = 2;
		var cOSC1_GLIDE_A = 3;
		var cOSC1_KBD_A = 4;
		var cOSC2_FREQ_A = 5;
		var cOSC2_FINE_A = 6;
		var cOSC2_SHAPE_A = 7;
		var cOSC2_GLIDE_A = 8;
		var cOSC2_KBD_A = 9;
		var cSYNC_A = 10;
		var cGLIDE_A = 11;
		var cSLOP_A = 12;
		var cOSC_MIX_A = 13;
		var cOSC1_NOISE_A = 14;
		var cLPF_CUTOFF = 15;
		var cLPF_RESONANCE = 16;
		var cLPF_KBD_TRACKING = 17;
		var cLPF_AUDIO_MOD = 18;
		var cLPF_POLES = 19;
		var cLPF_ENV_AMT = 20;
		var cLPF_ENV_VEL = 21;
		var cLPF_ENV_DLY = 22;
		var cLPF_ENV_ATT = 23;
		var cLPF_ENV_DEC = 24;
		var cLPF_ENV_SUS = 25;
		var cLPF_ENV_REL = 26;
		var cPAN_SPREAD = 28;
		var cPRG_VOLUME = 29;
		var cAMP_ENVAMT = 30;
		var cAMP_VELOCITY= 31;
		var cAMP_DLY = 32;
		var cAMP_ATT = 33;
		var cAMP_DEC = 34;
		var cAMP_SUS = 35;
		var cAMP_REL = 36;
		var cLFO1_FREQ = 37;
		var cLFO1_SHAPE = 38;
		var cLFO1_AMT = 39;
		var cLFO1_DEST = 40;
		var cLFO1_CLKSYNC = 41;

		var cENV3_DST = 57;
		var cENV3_AMT = 58;
		var cENV3_VEL = 59;
		var cENV3_DEL = 60;
		var cENV3_ATT = 61;
		var cENV3_DEC = 62;
		var cENV3_SUS = 63;
		var cENV3_REL = 64;

		var cMOD1_SRC = 65;
		var cMOD1_AMT = 66;
		var cMOD1_DST = 67;


		var cENV3_RPT = 97;
		var cOSC1_NOTERESET = 99;
		var cOSC1_SHAPEMOD_A = 102;
		var cOSC2_NOTERESET = 104;
		var cLFO1_KEYSYNC = 105;
		var cOSC1_SUBOSC = 110;
		var cGLIDE = 111;
		var cPITCHBENDRANGE = 113;
		var cPANMODE = 114;

		var cMODWHEEL_AMT = 116;
		var cMODWHEEL_DST = 117;
		var cPRESSURE_AMT = 118;
		var cPRESSURE_DST = 119;
		var cBREATH_AMT = 120;
		var cBREATH_DST = 121;
		var cVELOCITY_AMT = 122;
		var cVELOCITY_DST = 123;
		var cFOOTCTRL_AMT = 124;
		var cFOOTCTRL_DST = 125;

		var cFX_ONOFF = 153;
		var cFX_SELECT = 154;
		var cFX_MIX = 155;
		var cFX_PARAM1 = 156;
		var cFX_PARAM2 = 157;
		var cFX_CLKSYNC = 158;

		var cABMODE = 163;

		var cUNISON_DETUNE = 167;
		var cUNISON_MODE = 168;
		var cKEYMODE = 170;
		var cSPLITPOINT = 171;
		var cARP_OFFON = 172;
		var cARP_MODE = 173;
		var cARP_RANGE = 174;
		var cARP_CLK_DIV = 175;
		var cARP_RPTS = 177;
		var cARP_RELATCH = 178;
		var cBPM_TEMPO = 179;

		var cGATED_SEQMODE = 182;
		var cGATED_SEQPOLY = 183;
		var cSEQ1_DST = 184; // 185, 186, 187 for tracks 2,3,4

		var cSEQ1_GATED_STEP1_TRACK1 = 192;
		var cSEQ1_POLY_NOTE1_STEP1 = 276;
		var cSEQ1_POLY_NOTE1_VEL1 = 340;

		var cLAYERA_CHAR1 = 20000;

		var analyze_globals = {
			var explanation = [];
			explanation = explanation.add("GLOBALS");
			explanation = explanation.add("*******");
			this.rev2_nrpn_globals.keys.do({
				|key|
				explanation = explanation.add((this.rev2[key][\name]++" = "++this.lut(key, 0, norange:true)));
			});
			explanation;
		};

		var analyze_header = {
			| offset |
			var explanation = [];
			var name = "";

			20.do({
				|i|
				name = name ++ this.charlut(cLAYERA_CHAR1 + i, offset);
			});
			explanation = explanation.add("Patch name: "++name);
			explanation = explanation.add("Program Volume is set to "+this.lut(cPRG_VOLUME, offset));
			explanation = explanation.add("Unison mode is set to "++this.lut(cUNISON_MODE, offset)++" with detune set to "++this.lut(cUNISON_DETUNE, offset)++".");
			explanation = explanation.add("Glide is set to "++this.lut(cGLIDE, offset)++". Pitch bend range is set to "++this.lut(cPITCHBENDRANGE, offset)++".");
			explanation = explanation.add("Pan mode is set to "++this.lut(cPANMODE,offset)++". Key mode is set to "++this.lut(cKEYMODE, offset)++".");
			if (offset < 2048) {
				explanation = explanation.add("A/B mode is set to "++this.lut(cABMODE,offset)++".");
				explanation = explanation.add("Split point is set to "++this.lut(cSPLITPOINT, offset)++".");
			};
			explanation;
		};

		var analyze_osc = {
			| offset |
			var explanation = [];
			explanation = explanation.add("The oscillator section");
			explanation = explanation.add("**********************");
			explanation = explanation.add("First oscillator is set to "++this.lut(cOSC1_SHAPE_A, offset)++" with frequency "++this.lut(cOSC1_FREQ_A, offset)++
				", fine setting "++this.lut(cOSC1_FINE_A, offset)++" and shape mod "++this.lut(cOSC1_SHAPEMOD_A, offset)++".");
			explanation = explanation.add("Osc1 Glide is set to "++this.lut(cOSC1_GLIDE_A, offset)++" and keyboard control is "++this.lut(cOSC1_KBD_A, offset)++".");
			explanation = explanation.add("Suboscillation is set to "++this.lut(cOSC1_SUBOSC, offset)++". Noise level is set to "++this.lut(cOSC1_NOISE_A, offset));
			explanation = explanation.add("Osc 1 note reset is set to "++this.lut(cOSC1_NOTERESET, offset)++".");
			explanation = explanation.add("");
			explanation = explanation.add("Second oscillator is set to "++this.lut(cOSC2_SHAPE_A, offset)++" with frequency "++
				this.lut(cOSC2_FREQ_A, offset)++", fine setting "+this.lut(cOSC2_FINE_A, offset)++".");
			explanation = explanation.add("Osc2 Glide is set to "++this.lut(cOSC2_GLIDE_A, offset)++" and KBD is "++this.lut(cOSC2_KBD_A, offset)++".");
			explanation = explanation.add("Osc 2 note reset is set to "++this.lut(cOSC2_NOTERESET, offset)++".");
			explanation = explanation.add("");
			explanation = explanation.add("General oscillator parameters: Slop is set to "++this.lut(cSLOP_A, offset)++" and OSC mix is set to "++this.lut(cOSC_MIX_A, offset)++".");
			explanation = explanation.add("Glide is set to "++this.lut(cGLIDE_A, offset)++", and SYNC is set to "++this.lut(cSYNC_A, offset)++".");
			explanation;
		};

		var analyze_lowpassfilter = {
			| offset |
			var explanation = [];
			explanation = explanation.add("The low-pass filter");
			explanation = explanation.add("*******************");
			explanation = explanation.add("The filter is set to "++this.lut(cLPF_POLES,offset)++" mode. Cut-off frequency is set to "++this.lut(cLPF_CUTOFF, offset)++".");
			explanation = explanation.add("Resonance is set to "++this.lut(cLPF_RESONANCE,offset)++". Keyboard tracking is "++this.lut(cLPF_KBD_TRACKING, offset));
			explanation = explanation.add("Audio Mod is set to "++this.lut(cLPF_AUDIO_MOD,offset));
			explanation = explanation.add("");
			explanation = explanation.add("The filter uses an envelope with the following characteristics:");
			explanation = explanation.add("Envelope amount: "++this.lut(cLPF_ENV_AMT,offset)++", velocity: "++this.lut(cLPF_ENV_VEL,offset)++".");
			explanation = explanation.add("Envelope delay: "++this.lut(cLPF_ENV_DLY,offset)++".");
			explanation = explanation.add("Attack time: "++this.lut(cLPF_ENV_ATT,offset)++", Decay time: "++this.lut(cLPF_ENV_DEC, offset)++",");
			explanation = explanation.add("Sustain level: "++this.lut(cLPF_ENV_SUS,offset)++", Release time: "++this.lut(cLPF_ENV_REL,offset));
			explanation;
		};

		var analyze_amplifier = {
			| offset |
			var explanation = [];
			explanation = explanation.add("The amplifier section");
			explanation = explanation.add("*********************");
			explanation = explanation.add("Attack time: "++this.lut(cAMP_ATT,offset)++", Decay time: "++this.lut(cAMP_DEC,offset));
			explanation = explanation.add("Sustain level: "++this.lut(cAMP_SUS,offset)++", Release time: "++this.lut(cAMP_REL,offset)+".");
			explanation = explanation.add("Delay: "++this.lut(cAMP_DLY,offset)++", Pan spread: "++this.lut(cPAN_SPREAD,offset));
			explanation = explanation.add("Env amount is set to "++this.lut(cAMP_ENVAMT,offset)++" and velocity is set to "++this.lut(cAMP_VELOCITY, offset));
			explanation;
		};

		var analyze_lfo = {
			| offset |
			var explanation = [];

			explanation = explanation.add("LFOs");
			explanation = explanation.add("****");

			4.do({
				| i |
				var dest = this.lut(cLFO1_DEST+(5*i),offset,mask:127);
				if (dest == "Off") {
					explanation = explanation.add("LFO "++(i+1)++" is not used.");
				} /* else */ {
					explanation = explanation.add("LFO "++(i+1)++" is set to "++this.lut((cLFO1_SHAPE + (i*5)),offset)++
						" with frequency "++this.lut((cLFO1_FREQ + (i*5)),offset)++".");
					explanation = explanation.add("Destination is "++this.lut(cLFO1_DEST+(5*i),offset,mask:127)++" with amount "
						++this.lut((cLFO1_AMT + (i*5)), offset)++".");
					explanation = explanation.add("Clock sync is "++this.lut(cLFO1_CLKSYNC+(5*i),offset)++", and key sync is set to "++this.lut(cLFO1_KEYSYNC, offset)++".");
				};
				explanation = explanation.add("");
			});

			explanation;
		};

		var analyze_modulations = {
			| offset |
			var explanation = [];
			var tempdest;
			explanation = explanation.add("Modulations");
			explanation = explanation.add("***********");
			8.do({
				| i |
				var dest = this.lut((cMOD1_DST + (i*3)), offset, 127);
				if (dest == "Off") {
					explanation = explanation.add("Modulation "++(i+1)++" is not used.");
				} /* else */ {
					explanation = explanation.add("Modulation "++(i+1)++" connects '"++this.lut((cMOD1_SRC + (i*3)), offset, 127)++"' to '"++
						this.lut((cMOD1_DST + (i*3)), offset, 127)++"' with an amount of "++this.lut((cMOD1_AMT + (i*3)), offset));
				}
			});

			tempdest = this.lut(cMODWHEEL_DST, offset,mask:127);
			if (tempdest == "Off") {
				explanation = explanation.add("ModWheel is not mapped to anything.");
			} /* else */ {
				explanation = explanation.add("ModWheel connects to "++tempdest++" with amount "++this.lut(cMODWHEEL_AMT,offset));
			};

			tempdest = this.lut(cPRESSURE_DST, offset,mask:127);
			if (tempdest == "Off") {
				explanation = explanation.add("Pressure (aftertouch) is not mapped to anything.");
			} /* else */ {
				explanation = explanation.add("Pressure (aftertouch) connects to "++tempdest++" with amount "++this.lut(cPRESSURE_AMT,offset));
			};

			tempdest = this.lut(cBREATH_DST, offset,mask:127);
			if (tempdest == "Off") {
				explanation = explanation.add("Breath controller is not mapped to anything.");
			} /* else */ {
				explanation = explanation.add("Breath controller connects to "++tempdest++" with amount "++this.lut(cBREATH_AMT,offset));
			};

			tempdest = this.lut(cVELOCITY_DST, offset,mask:127);
			if (tempdest == "Off") {
				explanation = explanation.add("Velocity is not mapped to anything.");
			} /* else */ {
				explanation = explanation.add("Velocity connects to "++tempdest++" with amount "++this.lut(cVELOCITY_AMT,offset));
			};

			tempdest = this.lut(cFOOTCTRL_DST, offset,mask:127);
			if (tempdest == "Off") {
				explanation = explanation.add("Foot controller is not mapped to anything.");
			} /* else */ {
				explanation = explanation.add("Foot controller connects to "++tempdest++" with amount "++this.lut(cFOOTCTRL_AMT,offset));
			};

			explanation;
		};

		var analyze_auxiliary_envelope = {
			| offset |
			var explanation = [];
			var tempdest = this.lut(cENV3_DST, offset,mask:127);
			explanation = explanation.add("Auxiliary envelope");
			explanation = explanation.add("******************");
			if (tempdest == "Off") {
				explanation = explanation.add("Env 3 is not used.");
			} {
				var repeat;
				explanation = explanation.add("Env 3 is connected to "++this.lut(cENV3_DST, offset,mask:127)++" with an amount "++this.lut(cENV3_AMT, offset));
				explanation = explanation.add("Velocity is set to "++this.lut(cENV3_VEL, offset)++", and delay is set to "++this.lut(cENV3_DEL, offset));
				explanation = explanation.add("Attack time is "++this.lut(cENV3_ATT, offset)++", decay time is "++this.lut(cENV3_DEC,offset)++", sustain level is "
					++this.lut(cENV3_SUS, offset)++" and release is set to "++this.lut(cENV3_REL, offset));
				repeat = this.lut(cENV3_RPT, offset);
				if (repeat == "Off") {
					explanation = explanation.add("Env 3 is set to NOT repeat.");
				} {
					explanation = explanation.add("Env 3 is set to REPEAT.");
				};
			};
		};

		var analyze_fx = {
			| offset |
			var explanation = [];
			var onoff = this.lut(cFX_ONOFF, offset);
			explanation = explanation.add("Effects");
			explanation = explanation.add("*******");
			if (onoff == "Off") {
				explanation = explanation.add("No effects are used.");
			} {
				explanation = explanation.add("Effects are on.");
				explanation = explanation.add("The "++this.lut(cFX_SELECT, offset)++" effect is selected. FX Mix is set to "++this.lut(cFX_MIX,offset));
				explanation = explanation.add("FX Param 1 is "++this.lut(cFX_PARAM1, offset)++" and FX Param 2 is set to "++this.lut(cFX_PARAM2, offset));
				explanation = explanation.add("FX Clock Sync is set to "++this.lut(cFX_CLKSYNC, offset));
			};

			explanation;
		};

		var analyze_arp = {
			| offset |
			var explanation = [];
			var onoff = this.lut(cARP_OFFON, offset);
			explanation = explanation.add("Arpeggiator");
			explanation = explanation.add("***********");
			if (onoff == "Off") {
				explanation = explanation.add("Arpeggiator is not used. But once it is enabled, it is configured as follows:");
			} {
				explanation = explanation.add("Arpeggiator is on by default.");
			};
			explanation = explanation.add("Mode is set to "++this.lut(cARP_MODE, offset));
			explanation = explanation.add("Range is set to "++this.lut(cARP_RANGE, offset)++". Clock DIV is set to "++this.lut(cARP_CLK_DIV,offset)++".");
			explanation = explanation.add("Repeats is set to "++this.lut(cARP_RPTS, offset)++". Arp relatch is set to "++this.lut(cARP_RELATCH, offset)++".");
			explanation = explanation.add("BPM tempo is set to "++this.lut(cBPM_TEMPO, offset)++".");
			explanation;
		};

		var analyze_sequencer = {
			| notenames, offset |
			var explanation = [];
			explanation = explanation.add("Sequencer");
			explanation = explanation.add("*********");
			explanation = explanation.add("Sequencer mode is set to "++this.lut(cGATED_SEQMODE, offset)++". Sequencer is set to "++this.lut(cGATED_SEQPOLY, offset)++".");
			4.do({
				| i |
				var dest;
				explanation = explanation.add("    Gated Sequencer track "++(i+1));
				explanation = explanation.add("    ***********************");
				dest = this.lut((cSEQ1_DST + i), offset, 127);
				if (dest == "Off") {
					explanation = explanation.add("    Not used.");
				} {
					var notes = [];
					explanation = explanation.add("    Destination: "++dest);
					notes = 16.collect({
						|j|
						var nrpn = (cSEQ1_GATED_STEP1_TRACK1 + (i*16) + j);
						this.lut(nrpn, offset, norange:true);
					});
					explanation = explanation.add("    "++notes.join(", "));
				};
			});

			explanation = explanation.add("");

			6.do({
				| i |
				var notes = [];
				var velocities = [];
				explanation = explanation.add("    Poly Sequencer track "++(i+1));
				explanation = explanation.add("    **********************");
				notes = 64.collect({
					| j |
					var nrpn = (cSEQ1_POLY_NOTE1_STEP1 + (i*(64+64)) + j);
					if ((this.lut(nrpn, offset, norange:true).asInt & 128) != 0) {
						notenames[(this.lut(nrpn, offset, norange:true).asInt & 127).asInt]++"_";
					} {
						notenames[(this.lut(nrpn, offset, norange:true).asInt)]++" ";
					};
				});
				velocities = 64.collect({
					| j |
					var nrpn = (cSEQ1_POLY_NOTE1_VEL1 + (i*(64+64)) + j);
					if ((this.lut(nrpn, offset, norange:true).asInt & 128) != 0) {
						(this.lut(nrpn, offset, norange:true).asInt & 127);
					} {
						"--";
					};
				});
				explanation = explanation.add("    notes: "++notes.join(", "));
				explanation = explanation.add("    veloc: "++velocities.join(", "));
				explanation
			});


			explanation;
		};

		var analysis_header = Dictionary.newFrom([
			0 : "* Layer A analysis *",
			2048: "* Layer B analysis *"
		]);

		this.note_name = ["C0 ", "C#0", "D0 ", "D#0", "E0 ", "F0 ", "F#0", "G0 ", "G#0", "A0 ", "A#0", "B0 ",
			"C1 ", "C#1", "D1 ", "D#1", "E1 ", "F1 ", "F#1", "G1 ", "G#1", "A1 ", "A#1", "B1 ",
			"C2 ", "C#2", "D2 ", "D#2", "E2 ", "F2 ", "F#2", "G2 ", "G#2", "A2 ", "A#2", "B2 ",
			"C3 ", "C#3", "D3 ", "D#3", "E3 ", "F3 ", "F#3", "G3 ", "G#3", "A3 ", "A#3", "B3 ",
			"C4 ", "C#4", "D4 ", "D#4", "E4 ", "F4 ", "F#4", "G4 ", "G#4", "A4 ", "A#4", "B4 ",
			"C5 ", "C#5", "D5 ", "D#5", "E5 ", "F5 ", "F#5", "G5 ", "G#5", "A5 ", "A#5", "B5 ",
			"C6 ", "C#6", "D6 ", "D#6", "E6 ", "F6 ", "F#6", "G6 ", "G#6", "A6 ", "A#6", "B6 ",
			"C7 ", "C#7", "D7 ", "D#7", "E7 ", "F7 ", "F#7", "G7 ", "G#7", "A7 ", "A#7", "B7 ",
			"C8 ", "C#8", "D8 ", "D#8", "E8 ", "F8 ", "F#8", "G8 ", "G#8", "A8 ", "A#8", "B8 ",
			"C9 ", "C#9", "D9 ", "D#9", "E9 ", "F9 ", "F#9", "G9 ", "G#9", "A9 ", "A#9", "B9 ",
			"C10"];

		this.rev2 = rv2;
		this.rev2_nrpn_globals = nrpnglobals;

		explanation = explanation.addAll(analyze_globals.());
		[0, 2048].do({
			| offset |
			explanation = explanation.add("");
			explanation = explanation.add("********************");
			explanation = explanation.add(analysis_header[offset]);
			explanation = explanation.add("********************");
			explanation = explanation.add("");
			explanation = explanation.addAll(analyze_header.(offset));
			explanation = explanation.add("");
			explanation = explanation.addAll(analyze_osc.(offset));
			explanation = explanation.add("");
			explanation = explanation.addAll(analyze_lowpassfilter.(offset));
			explanation = explanation.add("");
			explanation = explanation.addAll(analyze_amplifier.(offset));
			explanation = explanation.add("");
			explanation = explanation.addAll(analyze_lfo.(offset));
			explanation = explanation.add("");
			explanation = explanation.addAll(analyze_modulations.(offset));
			explanation = explanation.add("");
			explanation = explanation.addAll(analyze_auxiliary_envelope.(offset));
			explanation = explanation.add("");
			explanation = explanation.addAll(analyze_fx.(offset));
			explanation = explanation.add("");
			explanation = explanation.addAll(analyze_arp.(offset));
			explanation = explanation.add("");
			explanation = explanation.addAll(analyze_sequencer.(this.note_name, offset));
			explanation = explanation.add("");
		});

		^explanation;
	}

}