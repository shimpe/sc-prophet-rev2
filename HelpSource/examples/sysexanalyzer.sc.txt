// initialize system
(
~synth = ScProphetRev2.new;
~synth.connect;
~sysexstream = nil;
~sysexstream2 = nil;
)

// run the following twice. Between runs, change single parameter and save in preset location U1 P7
(
var u1 = 0;
var p7 = 6;
~synth.get_patch_from_synth(u1, p7, {
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
		"storing new sysex stream as reference".postln;
		~sysexstream = ~sysexstream2.copy();
		~sysexstream2 = nil;
	};
});
)

