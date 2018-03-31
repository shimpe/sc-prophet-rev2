// initialize system
(
~synth = ScProphetRev2.new;
~synth.connect;
~sysexstream = nil;
~sysexstream2 = nil;
)

// run the following twice. Between runs, change single parameter and save in preset location U4 P1
(
var u4 = 3;
var p1 = 0;
~synth.get_patch_from_synth(u4, p1, {
	if (~sysexstream.isNil) {
		"Storing sysex as reference.".postln;
		~sysexstream = ~synth.last_sysex_stream.copy();
	} {
		"Comparing new sysex to previous sysex.".postln;
		~sysexstream2 = ~synth.last_sysex_stream.copy();
		~sysexstream.do({
			|el, idx|
			if (el != ~sysexstream2[idx]) {
				("Streams differ at position "++idx++" (stream 1= "++el++ "; stream 2= "++~sysexstream2[idx]).postln;
			}
		});
		~sysexstream = nil;
		~sysexstream2 = nil;
	};
});
)

