MidiPacker {

	*new {
		^super.new.init();
	}

	init {

	}

	midi_unpack {
		| bytes |
		var by_eight = bytes.clump(8);
		^by_eight.collect({
			| eightbytes |
			var result;
			var unpacked = [0, 0, 0, 0, 0, 0, 0];
			7.do({
				| i |
				if (eightbytes[i+1].isNil.not) {
					unpacked[i] = (eightbytes[i+1] | ((eightbytes[0] & (1<<(6-i))) << (i+1)));
				} /* else */ {
					unpacked[i] = nil;
				}
			});
			result = [];
			unpacked.do({
				| el |
				//el.postln;
				if (el.isNil.not) {
					result = result.add(el);
				};
			});
			result
		}).flat;
	}

	midi_pack {
		| list_of_num |
		var by_seven = list_of_num.clump(7);
		^by_seven.collect({
			| seven_bytes |
			var result;
			var packed = [nil, nil, nil, nil, nil, nil, nil, nil];
			7.do({
				| i |
				if (seven_bytes[i].isNil.not) {
					if (packed[0].isNil) {
						packed[0] = 0;
					};
					packed[0] = packed[0] | ((seven_bytes[i] & 128) >> (i+1));
					packed[i+1] = (seven_bytes[i] & 127);
				}
			});
			result = [];
			packed.do({
				| el |
				//el.postln;
				if (el.isNil.not) {
					result = result.add(el);
				};
			});
			result
		}).flat;
	}
}