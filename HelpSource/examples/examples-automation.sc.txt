(
var arpeggio, cutoff, slop, jointpattern;
var player;
var parser = TheoryNoteParser.new;
~synth = ScProphetRev2.new;
~synth.connect;
~synth.get_patch_from_synth(4, 0, { ~synth.patch_explainer.join("\n").postln; });

arpeggio = Pbind(
	\type, \midi,
	\midicmd, \noteOn,
	\midiout, ~synth.midi_out,
	\chan, 1,
	\midinote, Pseq([
		Pseq(parser.asMidi("c2 e2 g2 b2 c3 b2 g2 e2")+24, 4),
		Pseq(parser.asMidi("d2 e2 g2 b2 d3 b2 g2 d2")+24, 4),
	], inf),
	\dur, Pseq([0.1],inf),
	\amp, Pbrown(0.3, 0.6, 0.05, inf),
);

cutoff = Pbind(
	\instrument, \default,
	\amp, 0,
    \number, 15,
	\value, Pseq([Pseries(20, 1, length:107),
	              Pseries(127, 1.neg, length:107)], inf).trace,
	\dur, 0.05,
	\channel, 1,
	\receiver, Pfunc({ | event | ~synth.sendNRPN(event[\number], event[\value], event[\channel])}),
);

slop = Pbind(
	\instrument, \default,
	\amp, 0,
	\number, 12,
	\value, Pseq([Pseries(0, 1, length:50),
	              Pseries(50, 1.neg, length:50)], inf).trace,
	\dur, 0.1,
	\channel, 1,
	\receiver, Pfunc({ | event | ~synth.sendNRPN(event[\number], event[\value], event[\channel])}),
);

/*
jointpattern = Ppar([cutoff, slop], inf);
~player = jointpattern.play;
*/

)


~player.stop
