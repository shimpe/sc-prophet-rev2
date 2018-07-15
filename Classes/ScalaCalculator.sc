ScalaCalculator {
	classvar primeFactors;

	var <>sclPath;
	var <>kbmPath;
	var <>sclContents;
	var <>kbmContents;
	var <>tuneTable;

	var <>sclInfo;
	var <>kbmInfo;

	*new {
		^super.new.init();
	}

	*initClass {
		primeFactors = [2,3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73,79,83,89,97,101,103,107,
			109,113,127,131,137,139,149,151,157,163,167,173,179,181,191,193,197,199,211,223,227,
			229,233,239,241,251,257,263,269,271,277,281,283,293,307,311,313,317,331,337,347,349,
			353,359,367,373,379,383,389,397,401,409,419,421,431,433,439,443,449,457,461,463,467,
			479,487,491,499,503,509,521,523,541,547,557,563,569,571,577,587,593,599,601,607,613,
			617,619,631,641,643,647,653,659,661,673,677,683,691,701,709,719,727,733,739,743,751,
			757,761,769,773,787,797,809,811,821,823,827,829,839,853,857,859,863,877,881,883,887,
			907,911,919,929,937,941,947,953,967,971,977,983,991,997,1009,1013,1019,1021,1031,1033,
			1039,1049,1051,1061,1063,1069,1087,1091,1093,1097,1103,1109,1117,1123,1129,1151,1153,
			1163,1171,1181,1187,1193,1201,1213,1217,1223,1229,1231,1237,1249,1259,1277,1279,1283,
			1289,1291,1297,1301,1303,1307,1319,1321,1327,1361,1367,1373,1381,1399,1409,1423,1427,
			1429,1433,1439,1447,1451,1453,1459,1471,1481,1483,1487,1489,1493,1499,1511,1523,1531,
			1543,1549,1553,1559,1567,1571,1579,1583,1597,1601,1607,1609,1613,1619,1621,1627,1637,
			1657,1663,1667,1669,1693,1697,1699,1709,1721,1723,1733,1741,1747,1753,1759,1777,1783,
			1787,1789,1801,1811,1823,1831,1847,1861,1867,1871,1873,1877,1879,1889,1901,1907,1913,
			1931,1933,1949,1951,1973,1979,1987,1993,1997,1999,2003,2011,2017,2027,2029,2039,2053,
			2063,2069,2081,2083,2087,2089,2099,2111,2113,2129,2131,2137,2141,2143,2153,2161,2179,
			2203,2207,2213,2221,2237,2239,2243,2251,2267,2269,2273,2281,2287,2293,2297,2309,2311,
			2333,2339,2341,2347,2351,2357,2371,2377,2381,2383,2389,2393,2399,2411,2417,2423,2437,
			2441,2447,2459,2467,2473,2477,2503,2521,2531,2539,2543,2549,2551,2557,2579,2591,2593,
			2609,2617,2621,2633,2647,2657,2659,2663,2671,2677,2683,2687,2689,2693,2699,2707,2711,
			2713,2719,2729,2731,2741,2749,2753,2767,2777,2789,2791,2797,2801,2803,2819,2833,2837,
			2843,2851,2857,2861,2879,2887,2897,2903,2909,2917,2927,2939,2953,2957,2963,2969,2971,
			2999,3001,3011,3019,3023,3037,3041,3049,3061,3067,3079,3083,3089,3109,3119,3121,3137,
			3163,3167,3169,3181,3187,3191,3203,3209,3217,3221,3229,3251,3253,3257,3259,3271,3299,
			3301,3307,3313,3319,3323,3329,3331,3343,3347,3359,3361,3371,3373,3389,3391,3407,3413,
			3433,3449,3457,3461,3463,3467,3469,3491,3499,3511,3517,3527,3529,3533,3539,3541,3547,
			3557,3559,3571,3581,3583,3593,3607,3613,3617,3623,3631,3637,3643,3659,3671,3673,3677,
			3691,3697,3701,3709,3719,3727,3733,3739,3761,3767,3769,3779,3793,3797,3803,3821,3823,
			3833,3847,3851,3853,3863,3877,3881,3889,3907,3911,3917,3919,3923,3929,3931,3943,3947,
			3967,3989,4001,4003,4007,4013,4019,4021,4027,4049,4051,4057,4073,4079,4091,4093,4099,
			4111,4127,4129,4133,4139,4153,4157,4159,4177,4201,4211,4217,4219,4229,4231,4241,4243,
			4253,4259,4261,4271,4273,4283,4289,4297,4327,4337,4339,4349,4357,4363,4373,4391,4397,
			4409,4421,4423,4441,4447,4451,4457,4463,4481,4483,4493,4507,4513,4517,4519,4523,4547,
			4549,4561,4567,4583,4591,4597,4603,4621,4637,4639,4643,4649,4651,4657,4663,4673,4679,
			4691,4703,4721,4723,4729,4733,4751,4759,4783,4787,4789,4793,4799,4801,4813,4817,4831,
			4861,4871,4877,4889,4903,4909,4919,4931,4933,4937,4943,4951,4957,4967,4969,4973,4987,
			4993,4999,5003,5009,5011,5021,5023,5039,5051,5059,5077,5081,5087,5099,5101,5107,5113,
			5119,5147,5153,5167,5171,5179,5189,5197,5209,5227,5231,5233,5237,5261,5273,5279,5281,
			5297,5303,5309,5323,5333,5347,5351,5381,5387,5393,5399,5407,5413,5417,5419,5431,5437,
			5441,5443,5449,5471,5477,5479,5483,5501,5503,5507,5519,5521,5527,5531,5557,5563,5569,
			5573,5581,5591,5623,5639,5641,5647,5651,5653,5657,5659,5669,5683,5689,5693,5701,5711,
			5717,5737,5741,5743,5749,5779,5783,5791,5801,5807,5813,5821,5827,5839,5843,5849,5851,
			5857,5861,5867,5869,5879,5881,5897,5903,5923,5927,5939,5953,5981,5987,6007,6011,6029,
			6037,6043,6047,6053,6067,6073,6079,6089,6091,6101,6113,6121,6131,6133,6143,6151,6163,
			6173,6197,6199,6203,6211,6217,6221,6229,6247,6257,6263,6269,6271,6277,6287,6299,6301,
			6311,6317,6323,6329,6337,6343,6353,6359,6361,6367,6373,6379,6389,6397,6421,6427,6449,
			6451,6469,6473,6481,6491,6521,6529,6547,6551,6553,6563,6569,6571,6577,6581,6599,6607,
			6619,6637,6653,6659,6661,6673,6679,6689,6691,6701,6703,6709,6719,6733,6737,6761,6763,
			6779,6781,6791,6793,6803,6823,6827,6829,6833,6841,6857,6863,6869,6871,6883,6899,6907,
			6911,6917,6947,6949,6959,6961,6967,6971,6977,6983,6991,6997,7001,7013,7019,7027,7039,
			7043,7057,7069,7079,7103,7109,7121,7127,7129,7151,7159,7177,7187,7193,7207,7211,7213,
			7219,7229,7237,7243,7247,7253,7283,7297,7307,7309,7321,7331,7333,7349,7351,7369,7393,
			7411,7417,7433,7451,7457,7459,7477,7481,7487,7489,7499,7507,7517,7523,7529,7537,7541,
			7547,7549,7559,7561,7573,7577,7583,7589,7591,7603,7607,7621,7639,7643,7649,7669,7673,
			7681,7687,7691,7699,7703,7717,7723,7727,7741,7753,7757,7759,7789,7793,7817,7823,7829,
			7841,7853,7867,7873,7877,7879,7883,7901,7907,7919];
	}

	init {
		^this;
	}

	load {
		| sclPathStr, kbmPathStr=nil |
		this.sclPathStr = sclPathStr.standardizePath;
		this.kbmPathStr = kbmPathStr.standardizePath;

		if (sclPath.notNil) {
			File.use(this.sclPathStr, "r", { | f | this.sclContents = f.readAllString.split($\n) });
		} {
			this.pr_setDefaultScl;
			"Valid scl file must be specified. Using default settings.".warning;
		};

		if (this.kbmPath.notNil) {
			File.use(this.kbmPathStr, "r", { | f | this.kbmContents = f.readAllString.split($\n) });
		} {
			"Using default keyboard mapping.".warn;
			this.pr_setDefaultKbm;
		};

		this.pr_parseScl;
		this.pr_parseKbm;
	}

	pr_parseScl {
		var nonCommentLines = 0;
		var lastentry = nil;
		this.sclInfo = (
			\description: "",
			\notes: 0,
			\octavefactor: (\value:2, \value2:1, \type: \ratio),
			\tuning : (
				\0 : (\value : 1, \value2: 1, \type: \ratio)
			)
		);
		this.sclContents.do({
			| ln, filelineindex |
			var line = ln.stripWhiteSpace;
			if (line[0] == $!) {
				// comment line - ignore
			} {
				nonCommentLines = nonCommentLines + 1;
				if (nonCommentLines == 1) {
					this.sclInfo[\description] = ln;
				};
				if (nonCommentLines == 2) {
					this.sclInfo[\notes] = ln.asInteger;
				};
				if (nonCommentLines > 2) {
					var cline = this.pr_cleanupLine(line);
					var splitline = cline.split($ );
					var firstentry = splitline[0];
					var type = \cents;
					var value = 0;
					var value2 = nil;
					var found = false;
					if (found.not && firstentry.find("|").notNil && firstentry.find(">").notNil) {
						var primefactors = firstentry.copyRange(firstentry.find("|")+1, firstentry.find(">")-1).split($@);
						found = true;
						type = \ratio;
						value = 1;
						value2 = 1;
						primefactors.do({
							| factor, idx |
							if (factor.find("/").notNil) {
								var splitfactor = factor.split($/);
								var num = splitfactor[0].asInteger;
								var den = splitfactor[1].asInteger;
								if (den != 0) {
									if ((num*den) <= 0) {
										value2 = value2 * primeFactors[idx].pow(num.abs / den.abs);
									} {
										value = value * primeFactors[idx].pow(num / den);
									};
								} {
									value = 0;
									value2 = 1;
									("Division by zero in scala file " ++ this.sclPath ++ " on line "++(filelineindex+1)).error;
									ln.error;
								};
							} {
								if (factor.asInteger < 0) {
									value2 = value2 * primeFactors[idx].pow(factor.asInteger.neg);
								} {
									value = value * primeFactors[idx].pow(factor.asInteger);
								};
							};
						});
					};
					if (firstentry.find(".").notNil) {
						type = \cents;
						value = firstentry.asFloat;
						value2 = nil;
						found = true;
					};
					if (found.not && firstentry.find("/").notNil) {
						var splitentry = firstentry.split($/);
						type = \ratio;
						value = splitentry[0].asInteger;
						value2 = splitentry[1].asInteger;
						if (value*value2 < 0) {
							("Negative ratios not allowed in scala file " ++ this.sclPath ++ " on line "++(filelineindex+1)).error;
						};
						found = true;
					};
					if (found.not) {
						type = \ratio;
						value = firstentry.asInteger;
						value2 = 1;
						found = true;
					};
					lastentry = (nonCommentLines-2).asSymbol;
					this.sclInfo[\tuning][lastentry] = (\value:value, \value2:value2, \type: type);

				};
			};
		});
		this.sclInfo[\octavefactor] = this.sclInfo[\tuning][lastentry];
		this.sclInfo[\tuning][lastentry] = nil;
	}

	pr_setDefaultScl {
		this.sclInfo[\description] = "12 tone Equal Temperament";
		this.sclInfo[\notes] =  12;
		this.sclInfo[\octavefactor] = (\value:1200, \type: \cents);
		this.sclInfo[\tuning] = (
			\0 : (\value : 0, \type: \cents),
			\1 : (\value : 100, \type: \cents),
			\2 : (\value : 200, \type: \cents),
			\3 : (\value : 300, \type: \cents),
			\4 : (\value : 400, \type: \cents),
			\5 : (\value : 500, \type: \cents),
			\6 : (\value : 600, \type: \cents),
			\7 : (\value : 700, \type: \cents),
			\8 : (\value : 800, \type: \cents),
			\9 : (\value : 900, \type: \cents),
			\10 : (\value : 1000, \type: \cents),
			\11 : (\value : 1100, \type: \cents));
	}

	pr_parseKbm {
		this.kbmInfo = ();
		if (this.kbmContents.isNil) {
			this.pr_setDefaultKbm();
			"No keyboard mapping loaded. Using default keyboard mapping.".warn;
		} {
			var nonCommentLines = 0;
			this.kbmContents.do({
				| ln, filelineindex |
				var line = ln.stripWhiteSpace;
				if (line[0] == $!) {
					// comment line - ignore
				} {
					nonCommentLines = nonCommentLines + 1;
					if (nonCommentLines == 1) {
						this.kbmInfo[\mapsize] = line.asInteger;
					};
					if (nonCommentLines == 2) {
						this.kbmInfo[\minnote] = line.asInteger;
					};
					if (nonCommentLines == 3) {
						this.kbmInfo[\maxnote] = line.asInteger;
					};
					if (nonCommentLines == 4) {
						this.kbmInfo[\degree0note] = line.asInteger;
					};
					if (nonCommentLines == 5) {
						this.kbmInfo[\reffreqnote] = line.asInteger;
					};
					if (nonCommentLines == 6) {
						this.kbmInfo[\reffreq] = line.asFloat;
					};
					if (nonCommentLines == 7) {
						this.kbmInfo[\octavedegree] = line.asInteger;
					};
					if (nonCommentLines > 7) {
						if (this.kbmInfo[\mapping].isNil) {
							this.kbmInfo[\mapping] = ();
						};
						if (line.find("x").isNil) {
							this.kbmInfo[\mapping][(nonCommentLines-8).asSymbol] = line.asInteger.asSymbol;
						} {
							this.kbmInfo[\mapping][(nonCommentLines-8).asSymbol] = nil;
						};
					};
				};
			});
			if (this.kbmInfo[\mapsize] == 0) {
				// map size = 0: generate linear mapping
				128.do({
					|num|
					var key = num.asSymbol;
					// not sure if the next block is correct...
					this.kbmInfo[\mapsize] = this.kbmInfo[\octavedegree]; //??
					if (this.kbmInfo[\octavedegree] == 0) {
						this.pr_setDefaultKbm;
					};
					this.kbmInfo[\mapping][key] = key;
				});
			} {
				// map size != 0: skip all not used degrees
				this.kbmInfo[\mapsize].do({
					|num|
					var key = num.asSymbol;
					if (this.kbmInfo[\mapping][key].isNil) {
						this.kbmInfo[\mapping][key] = nil;
					};
				});
			};
		};
	}

	pr_setDefaultKbm {
		this.kbmInfo[\mapsize] = 12;
		this.kbmInfo[\minnote] = 0;
		this.kbmInfo[\maxnote] = 127;
		this.kbmInfo[\degree0note] = 60;
		this.kbmInfo[\reffreqnote] = 69;
		this.kbmInfo[\reffreq] = 440.0;
		this.kbmInfo[\octavedegree] = 12;
		this.kbmInfo[\mapping] = (
			\0 : \0,
			\1 : \1,
			\2 : \2,
			\3 : \3,
			\4 : \4,
			\5 : \5,
			\6 : \6,
			\7 : \7,
			\8 : \8,
			\9 : \9,
			\10 :\10,
			\11 :\11
		);
	}

	calculateKeyToFreq {
		var keytofreq = ();
		var deg0note = this.kbmInfo[\degree0note];
		var refnote = this.kbmInfo[\reffreqnote];
		var refdegree;
		var reftuning;
		var virtualreffreq;
		var deg0 = deg0note % this.kbmInfo[\mapsize];

		// first assign logical (consecutive) and physical (remapped) degrees to each note
		this.kbmInfo[\mapsize].do({
			| degree |
			var octave = 0;
			var key = deg0 - this.kbmInfo[\mapsize] + degree;
			if (key < 0) { key = key + this.kbmInfo[\mapsize]; };
			while ({key < 128}, {
				if ((key <= this.kbmInfo[\maxnote]) && (key >= this.kbmInfo[\minnote])) {
					if (keytofreq[key.asSymbol].isNil) { keytofreq[key.asSymbol] = (); };
					keytofreq[key.asSymbol][\logicaldegree] = degree;
					keytofreq[key.asSymbol][\physicaldegree] = this.kbmInfo[\mapping][degree.asSymbol];
					keytofreq[key.asSymbol][\octave] = octave;
				};
				key = key + this.kbmInfo[\mapsize];
				octave = octave + 1;
			});
		});

		// look up tuning for (mapped) fixed frequency degree
		refdegree = keytofreq[refnote.asSymbol][\physicaldegree];
		reftuning = this.sclInfo[\tuning][refdegree];
		if (reftuning.notNil) {
			var ratio;
			if (reftuning[\type] == \cents) {
				ratio = 2.pow(reftuning[\value]/1200);
			} {
				ratio = reftuning[\value]/reftuning[\value2];
			};

			virtualreffreq = this.kbmInfo[\reffreq]/ratio; // undo the transformation associated to current physical degree because
			                                               // after mapping degrees, ref note is not guaranteed to get phys degree 0

			// for each key, get physical degree, use it to look up ratio, apply it to the virtualreffreq, and compensate for octave
			128.do({
				| key |
				if ((key <= this.kbmInfo[\maxnote]) && (key >= this.kbmInfo[\minnote])) {
					var physdeg = keytofreq[key.asSymbol][\physicaldegree];
					if (physdeg.notNil) {
						var tuning = this.sclInfo[\tuning][physdeg];
						var ratio;
						var freq;
						var octavediff;
						if (tuning[\type] == \cents) {
							ratio = 2.pow(tuning[\value]/1200);
						} {
							ratio = tuning[\value] / tuning[\value2];
						};
						freq = virtualreffreq * ratio;

						octavediff = keytofreq[refnote.asSymbol][\octave] - keytofreq[key.asSymbol][\octave];
						if (octavediff > 0) {
							var loops = octavediff.abs;
							var ratio;
							var octavefactor = this.sclInfo[\octavefactor];
							if (octavefactor[\type] == \cents) {
								ratio = 2.pow(octavefactor[\value]/1200);
							} {
								ratio = octavefactor[\value]/octavefactor[\value2];
							};
							loops.do({
								freq = freq / ratio;
							});
							keytofreq[key.asSymbol][\freq] = freq;
						} {
							var loops = octavediff.abs;
							var ratio;
							var octavefactor = this.sclInfo[\octavefactor];
							if (octavefactor[\type] == \cents) {
								ratio = 2.pow(octavefactor[\value]/1200);
							} {
								ratio = octavefactor[\value] / octavefactor[\value2];
							};
							loops.do({
								freq = freq * ratio;
							});
							keytofreq[key.asSymbol][\freq] = freq;
						};
					};
				};
			});

			^keytofreq;

		} {
			"Reference note tuning could not be looked up. Are .scl and .kbm files ok?".error;
			^nil;
		};
	}

	pr_cleanupLine {
		| line |
		var cline = line.copy();
		var cline2;
		var mode = \outside;
		while ({cline.find("  ").notNil}, {
			cline = cline.replace("  ", " ");
		});
		while ({cline.find(" /").notNil}, {
			cline = cline.replace(" /", "/");
		});
		while ({cline.find("/ ").notNil}, {
			cline = cline.replace("/ ", "/");
		});
		while ({cline.find(" >").notNil}, {
			cline = cline.replace(" >", ">");
		});
		while ({cline.find("| ").notNil}, {
			cline = cline.replace("| ", "|");
		});
		cline = cline.replace("\t", " ");
		cline = cline.stripWhiteSpace;

		// substitute spaces inside | ... > with @ signs so they aren't split
		cline2 = cline.collect({
			| chr |
			if (chr == $|) {
				mode = \inside;
			};
			if (chr == $>) {
				mode = \outside;
			};
			if (mode == \outside) {
				chr
			} {
				if (chr == $ ) {
					$@
				} {
					chr
				};
			};
		});
		^cline2;
	}

}