PatchMorpher {

	var <>patchlist;
	var <>nrpnlist;
	var <>patchsubsetlist;

	var <>cache;
	var <>hwsynth;

	*new {
		| patchlist, nrpnlist, hwsynth|
		^super.new.init(patchlist, nrpnlist, hwsynth);
	}

	init {
		| plist, nlist, hwsynth |
		this.patchlist = plist;
		this.nrpnlist = nlist;
		this.hwsynth = hwsynth;
		this.patchsubsetlist = [];
		patchlist.do({
			| patch, idx |
			this.patchsubsetlist = this.patchsubsetlist.add(Dictionary.new(n:nlist.size));
			nlist.do({
				| key |
				key.postln;
				if (patch[key].notNil) {
					if ((patch[key][\curval]).notNil) {
						var newevnt = (\curval : (patch[key][\curval]));
						this.patchsubsetlist[idx][key] = newevnt.copy();
					};
				};
			});
		});
		this.reset_cache();
	}

	reset_cache {
		cache = nil;
	}

	blend {
		| factor |
		^this.patchsubsetlist[0].blend(this.patchsubsetlist[1], factor);
	}

	morph {
		| fromfactor=0, tofactor=1.0, dur_seconds=20, steps_per_second=1, channel=1  |
		fork {
			(dur_seconds*steps_per_second).do({
				| value, idx |
				var blended = this.blend(value.linlin(0,dur_seconds*steps_per_second,0,1)).postln;
				blended.keys.do({
					|key, idx|
					if (this.cache.notNil) {
						if (this.cache[key] != blended[key]) {
							this.hwsynth.sendNRPN(key, blended[key][\curval].round(1).asInteger, channel, true);
							0.01.wait;
						};
					};
				});
				this.cache = blended.copy();
				(1.0/steps_per_second).wait;
			});
		}
	}
}