PatchMorpher {

	var <>patchlist;
	var <>nrpnlist_analog;
	var <>nrpnlist_discrete;
	var <>patchsubsetlist_analog;
	var <>patchsubsetlist_discrete;
	var <>cache;
	var <>hwsynth;
	var <>waittime;
	var <>debug_key;

	*new {
		| patchlist, nrpnlist_analog, nrpnlist_discrete, hwsynth|
		^super.new.init(patchlist, nrpnlist_analog, nrpnlist_discrete, hwsynth);
	}

	init {
		| plist, nrpnlist_a, nrpnlist_d, hwsynth |

		this.debug_key = nil; // NrpnTable().str2num('VELOCITY_AMT', "A");

		this.patchlist = plist.deepCopy();
		this.nrpnlist_analog = nrpnlist_a;
		this.nrpnlist_discrete = nrpnlist_d;
		this.hwsynth = hwsynth;
		this.patchsubsetlist_analog = [];
		this.patchsubsetlist_discrete = [];
		this.waittime = 0.01;
		this.patchlist.do({
			| patch, idx |
			this.patchsubsetlist_analog = this.patchsubsetlist_analog.add(Dictionary.new(n:this.nrpnlist_analog.size));
			this.nrpnlist_analog.do({
				| key |
 				if (patch[key].notNil) {
					if ((patch[key][\curval]).notNil) {
						var newevnt = (\curval : patch[key][\curval]);
						this.patchsubsetlist_analog[idx][key] = newevnt.copy();
						if (key == this.debug_key) {
							this.patchsubsetlist_analog[idx][key].debug("this.patchsubsetlist_analog["++idx++"]["++key++"]");
						};
					};
				};
			});
			this.patchsubsetlist_discrete = this.patchsubsetlist_discrete.add(Dictionary.new(n:this.nrpnlist_discrete.size));
			this.nrpnlist_discrete.do({
				| key |
				if (patch[key].notNil) {
					if ((patch[key][\curval]).notNil) {
						var newevnt = (\curval : patch[key][\curval]);
						this.patchsubsetlist_discrete[idx][key] = newevnt.copy();
						if (key == this.debug_key) {
							this.patchsubsetlist_discrete[idx][key].debug("this.patchsubsetlist_discrete["++idx++"]["++key++"]");
						};
					};
				};
			});
		});
		this.reset_cache();
	}

	reset_cache {
		cache = nil;
	}

	blend_analog {
		| factor |
		//("FACTOR = " + factor).debug;
		this.patchsubsetlist_analog[0].blend(this.patchsubsetlist_analog[1], factor);
		^this.patchsubsetlist_analog[0].blend(this.patchsubsetlist_analog[1], factor);
	}

	blend_discrete {
		| factor |
		var index = if (factor > 0.5) { 1 } { 0 };
		^this.patchsubsetlist_discrete[index];
	}

	morph {
		| fromfactor=0, tofactor=1.0, dur_seconds=20, steps_per_second=1, channel=1  |
		fork {
			(dur_seconds*steps_per_second).do({
				| value, idx |
				var blended = this.blend_analog(value.linlin(0,dur_seconds*steps_per_second-1,0,1));
				var blended2 = this.blend_discrete(value.linlin(0, dur_seconds*steps_per_second-1,0,1));
				var compensation = 0;

				//"*****************************************************************************************".postln;

				blended.keys.do({
					|key, idx2|
					if (this.cache.notNil) {
						if (this.cache[key] != blended[key]) {
							var value_to_send = blended[key][\curval].round(1).asInteger;
							if (key == this.debug_key) {
								value_to_send.debug("value_to_send, key="+key);
							};
							this.hwsynth.sendNRPN(key, value_to_send, channel, false);
							compensation = compensation + this.waittime;
							this.waittime.wait;
						};
					};
				});

				blended2.keys.do({
					|key, idx|
					if (this.cache.notNil) {
						if (this.cache[key] != blended2[key]) {
							var value_to_send = blended2[key][\curval].round(1).asInteger;
							if (key == this.debug_key) {
								value_to_send.debug("value_to_send (2), key="+key);
							};
							this.hwsynth.sendNRPN(key, value_to_send, channel, false);
							compensation = compensation + this.waittime;
							this.waittime.wait;
						};
					};
				});
				//".".postln;
				this.cache = blended.copy();
				this.cache.merge(blended2.copy(), { | a, b | if (a.notNil) {a} {b}; });
				((1.0/steps_per_second) - compensation).wait;
			});
		}
	}
}