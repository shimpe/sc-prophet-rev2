EdoCalculator {
	var <>octaves;
	var <>steps;
    var <>refnote;
	var <>reffreq;

	*new {
		| octaves=1, steps=12, refnote="a4", reffreq=440.0 |
		^super.new.init(octaves, steps, refnote, reffreq);
	}

	init {
		| octaves=1, steps=12, refnote="a4", reffreq=440.0 |
		this.octaves = octaves.asFloat;
		this.steps = steps.asFloat;
		this.refnote = refnote.asString;
		this.reffreq = reffreq.asFloat;
	}

	keyToFreq {
		var ref = Panola("").noteToMidi(this.refnote);
		var reffreq = ();
		var octaves = this.octaves;
		var steps = this.steps;
		if (octaves < 1) {
			octaves = 1;
			steps = this.steps/this.octaves;
		};
		(0..128).do({
			| n |
			if (n < ref) {
				var diff = ref - n;
				reffreq[n] = this.reffreq/((octaves*2)**(diff/steps));
			} {
				if (n > ref) {
					var diff = n - ref;
					reffreq[n] = this.reffreq * ((octaves*2)**(diff/steps));
				} {
					reffreq[n] = this.reffreq;
				};
			};
		});
		^reffreq;
	}
}