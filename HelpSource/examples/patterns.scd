(
~synth = ScProphetRev2.new;
~synth.connect;
~synth.select_patch_by_id("F2", "P2");
)

(
s.waitForBoot({
	var chords, melody, combined, cutoff_a, cutoff_b, slop_a, slop_b;
	var parser = TheoryNoteParser.new;
	var killnotes = { ~synth.all_notes_off; CmdPeriod.remove(killnotes); };
	var table = NrpnTable.new;

	CmdPeriod.add(killnotes);

	chords = Pbind(
		\type, \midi,
		\midicmd, \noteOn,
		\midiout, ~synth.midi_out,
		\chan, 0,
		\midinote, Pseq([
				Pseq(["d2 d4",
					"d2 d4 f4",
					"d2 d4 e4",
					"d2 d4 e4 g4",
					"d2 d4 f4",
					"d2 d4 f4 a4",
					"d2 d4 e4 g4",
					"d2 d4 f4 a4",
					"d2 d4 f4 a4 c5",
					"d2 e4 g4 a4 c5",
					"d2 e4 f4 bb4 d5",
					"d2 eb4 f4 bb4 c5",
				].collect({|el|parser.asMidi(el);}), 4),
			], inf),
		\dur, Pseq([4],inf),
		\legato, 1,
		\amp, Pbrown(0.3, 0.6, 0.05, inf),
	);

	melody = Pbind(
		\type, \midi,
		\midicmd, \noteOn,
		\midiout, ~synth.midi_out,
		\chan, 0,
		\midinote, Pseq(["a6", "b6", "c7", "e6", "f6", "g#6", "a6", "g6", "a6", "c7", "bb6", "ab6"].collect({|el|parser.asMidi(el);})),
		\dur, Pwrand([4/12, 4/6, 1], [9, 3, 1].normalizeSum, inf),
		\legato, 0.9,
		\amp, Pbrown(0.3, 0.5, 0.05, inf)
	);

	cutoff_a = Pbind(
		    \instrument, \default,
		    \amp, 0,
		    \number, table.str2num('LPF_CUTOFF', "A"),
		    \value, Pbrown(20, 127, 1),
		    \dur, 0.05,
		    \channel, 1,
		    \receiver, Pfunc({ | event | ~synth.sendNRPN(event[\number], event[\value], event[\channel])}),
	);

	cutoff_b = Pbind(
		\instrument, \default,
		\amp, 0,
		\number, table.str2num('LPF_CUTOFF', "B"),
		\value, Pbrown(20, 127, 1),
		\dur, 0.045,
		\channel, 1,
		\receiver, Pfunc({ | event | ~synth.sendNRPN(event[\number], event[\value], event[\channel])}),
	);

	slop_a = Pbind(
		\instrument, \default,
		\amp, 0,
		\number, table.str2num('SLOP', "A"),
		\value, Pbrown(0, 50, 1).trace,
		\dur, Pfunc({0.5.rrand(0.1);}),
		\channel, 1,
		\receiver, Pfunc({ | event | ~synth.sendNRPN(event[\number], event[\value], event[\channel])}),
	);

	slop_b = Pbind(
		\instrument, \default,
		\amp, 0,
		\number, table.str2num('SLOP', "B"),
		\value, Pbrown(0, 50, 1).trace,
		\dur, Pfunc({0.5.rrand(0.1);}),
		\channel, 1,
		\receiver, Pfunc({ | event | ~synth.sendNRPN(event[\number], event[\value], event[\channel])}),
	);

	combined = Ppar([chords, melody, cutoff_a, cutoff_b, slop_a, slop_b], 1);
	~player = combined.play(quant: TempoClock.default.beats + 1.0);
});

)

(
~synth.all_notes_off;
~player.stop;
)