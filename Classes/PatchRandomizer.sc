PatchRandomizer {
	var <>startpatch;
	var <>nlist;
	var <>hwsynth;

	*new {
		| startpatch, nlist, hwsynth |
		^super.new.init(startpatch, nlist, hwsynth);
	}

	init {
		| sp, nl, hw |
		this.startpatch = sp;
		this.nlist = nl;
		this.hwsynth = hw;
	}

	randomize {
		this.nlist.do({
			| key, idx |
			if (this.startpatch[key].notNil) {
				if (this.startpatch[key][\curval].notNil) {
					this.hwsynth.sendNRPN(key, this.startpatch[key][\min].rrand(this.startpatch[key][\max]), 1, true);
				};
			};
		});
	}
}