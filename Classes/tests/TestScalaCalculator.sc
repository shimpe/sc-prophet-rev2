Test_Calc : UnitTest {
	test_toCents {
		var c = ScalaCalculator();
		this.assertEquals(c.pr_toCents((\type:\cents, \value:123)), 123);
		this.assertEquals(c.pr_toCents((\type:\ratio, \value:1, \value2:2)), 1200.neg);
	}
	test_toRatio {
		var c = ScalaCalculator();
		this.assertEquals(c.pr_toRatio((\type:\cents, \value:600)), 2.sqrt);
		this.assertEquals(c.pr_toRatio((\type:\ratio, \value:1, \value2:2)), 0.5);
	}
	test_centToRatio {
		var c = ScalaCalculator();
		this.assertEquals(c.pr_centToRatio(1200), 2);
		this.assertEquals(c.pr_centToRatio(0), 1);
		this.assertEquals(c.pr_centToRatio(1200.neg), 0.5);
	}
}

Test11_19 : UnitTest {
	test_scl_std {
		var c = ScalaCalculator();
		var keytofreq;
		var expected;

		c.sclContents_([
			"! 11-19-gould.scl",
			"!",
			"11 out of 19-tET, Mark Gould (2002)",
			" 11",
			"!",
			" 126.31579",
			" 252.63158",
			" 315.78947",
			" 442.10526",
			" 568.42105",
			" 694.73684",
			" 757.89474",
			" 884.21053",
			" 1010.52632",
			" 1136.84211",
			" 2/1"
		]);
		c.kbmContents_([
			"! 11.kbm",
			"! Example keyboard mapping for a 11-tone scale",
			"! One tone is duplicated. Another one may be more convenient.",
			"!",
			"! Size of map. The pattern repeats every so many keys:",
			"12",
			"! First MIDI note number to retune:",
			"0",
			"! Last MIDI note number to retune:",
			"127",
			"! Middle note where the first entry of the mapping is mapped to:",
			"60",
			"! Reference note for which frequency is given:",
			"60",
			"! Frequency to tune the above note to (floating point e.g. 440.0):",
			"261.6256",
			"! Scale degree to consider as formal octave (determines difference in pitch ",
			"! between adjacent mapping patterns):",
			"11",
			"! Mapping.",
			"! The numbers represent scale degrees mapped to keys. The first entry is for",
			"! the given middle note, the next for subsequent higher keys.",
			"0",
			"1",
			"2",
			"3",
			"4",
			"5",
			"6",
			"7",
			"8",
			"9",
			"10",
			"10"
		]);

		c.pr_parseScl;
		this.assertEquals(c.sclInfo[\description].compare("11 out of 19-tET, Mark Gould (2002)"), 0);
		this.assertEquals(c.sclInfo[\notes], 11);
		this.assertEquals(c.sclInfo[\octavefactor], ( \type: \ratio, \value2: 1, \value: 2 ) );
		this.assertEquals(c.sclInfo[\tuning][\0], ( \type: \cents, \value: 0 ) );
		this.assertEquals(c.sclInfo[\tuning][\1], ( 'type': \cents, 'value': 126.31579 ) );
		this.assertEquals(c.sclInfo[\tuning][\2], ( \type: \cents, \value: 252.63158 ) );
		this.assertEquals(c.sclInfo[\tuning][\3], ( \type: \cents, \value: 315.78947 ) );
		this.assertEquals(c.sclInfo[\tuning][\4], ( \type: \cents, \value: 442.10526 ) );
		this.assertEquals(c.sclInfo[\tuning][\5], ( \type: \cents, \value: 568.42105 ) );
		this.assertEquals(c.sclInfo[\tuning][\6], ( \type: \cents, \value: 694.73684 ) );
		this.assertEquals(c.sclInfo[\tuning][\7], ( \type: \cents, \value: 757.89474 ) );
		this.assertEquals(c.sclInfo[\tuning][\8], ( \type: \cents, \value: 884.21053 ) );
		this.assertEquals(c.sclInfo[\tuning][\9], ( \type: \cents, \value: 1010.52632 ) );
		this.assertEquals(c.sclInfo[\tuning][\10], ( \type: \cents, \value: 1136.84211 ) );

		c.pr_parseKbm;
		this.assertEquals(c.kbmInfo[\mapsize], 12);
		this.assertEquals(c.kbmInfo[\minnote], 0);
		this.assertEquals(c.kbmInfo[\maxnote], 127);
		this.assertEquals(c.kbmInfo[\degree0note], 60);
		this.assertEquals(c.kbmInfo[\reffreqnote], 60);
		this.assertEquals(c.kbmInfo[\reffreq], 261.6256);
		this.assertEquals(c.kbmInfo[\octavedegree], 11);
		this.assertEquals(c.kbmInfo[\mapping][\0], \0);
		this.assertEquals(c.kbmInfo[\mapping][\1], \1);
		this.assertEquals(c.kbmInfo[\mapping][\2], \2);
		this.assertEquals(c.kbmInfo[\mapping][\3], \3);
		this.assertEquals(c.kbmInfo[\mapping][\4], \4);
		this.assertEquals(c.kbmInfo[\mapping][\5], \5);
		this.assertEquals(c.kbmInfo[\mapping][\6], \6);
		this.assertEquals(c.kbmInfo[\mapping][\7], \7);
		this.assertEquals(c.kbmInfo[\mapping][\8], \8);
		this.assertEquals(c.kbmInfo[\mapping][\9], \9);
		this.assertEquals(c.kbmInfo[\mapping][\10], \10);
		this.assertEquals(c.kbmInfo[\mapping][\11], \10);

		keytofreq = c.keyToFreq();
		expected = (
			0: 8.175800,
			1: 8.794631,
			2: 9.460302,
			3: 9.811800,
			4: 10.554461,
			5: 11.353334,
			6: 12.212675,
			7: 12.666437,
			8: 13.625167,
			9: 14.656464,
			10: 15.765820,
			11: 15.765820,
			12: 16.351600,
			13: 17.589262,
			14: 18.920604,
			15: 19.623600,
			16: 21.108921,
			17: 22.706668,
			18: 24.425349,
			19: 25.332874,
			20: 27.250334,
			21: 29.312928,
			22: 31.531641,
			23: 31.531641,
			24: 32.703200,
			25: 35.178524,
			26: 37.841208,
			27: 39.247199,
			28: 42.217843,
			29: 45.413336,
			30: 48.850698,
			31: 50.665748,
			32: 54.500668,
			33: 58.625856,
			34: 63.063281,
			35: 63.063281,
			36: 65.406400,
			37: 70.357049,
			38: 75.682415,
			39: 78.494398,
			40: 84.435685,
			41: 90.826672,
			42: 97.701396,
			43: 101.331496,
			44: 109.001336,
			45: 117.251712,
			46: 126.126562,
			47: 126.126562,
			48: 130.812800,
			49: 140.714098,
			50: 151.364830,
			51: 156.988797,
			52: 168.871371,
			53: 181.653344,
			54: 195.402792,
			55: 202.662992,
			56: 218.002673,
			57: 234.503423,
			58: 252.253125,
			59: 252.253125,
			60: 261.625600,
			61: 281.428195,
			62: 302.729660,
			63: 313.977593,
			64: 337.742742,
			65: 363.306688,
			66: 390.805584,
			67: 405.325984,
			68: 436.005346,
			69: 469.006846,
			70: 504.506249,
			71: 504.506249,
			72: 523.251200,
			73: 562.856390,
			74: 605.459321,
			75: 627.955187,
			76: 675.485483,
			77: 726.613376,
			78: 781.611168,
			79: 810.651968,
			80: 872.010691,
			81: 938.013692,
			82: 1009.012499,
			83: 1009.012499,
			84: 1046.502400,
			85: 1125.712780,
			86: 1210.918641,
			87: 1255.910374,
			88: 1350.970966,
			89: 1453.226751,
			90: 1563.222336,
			91: 1621.303937,
			92: 1744.021383,
			93: 1876.027384,
			94: 2018.024997,
			95: 2018.024997,
			96: 2093.004800,
			97: 2251.425561,
			98: 2421.837282,
			99: 2511.820747,
			100: 2701.941933,
			101: 2906.453503,
			102: 3126.444673,
			103: 3242.607874,
			104: 3488.042766,
			105: 3752.054768,
			106: 4036.049995,
			107: 4036.049995,
			108: 4186.009600,
			109: 4502.851122,
			110: 4843.674564,
			111: 5023.641494,
			112: 5403.883866,
			113: 5812.907005,
			114: 6252.889346,
			115: 6485.215747,
			116: 6976.085531,
			117: 7504.109537,
			118: 8072.099989,
			119: 8072.099989,
			120: 8372.019200,
			121: 9005.702244,
			122: 9687.349129,
			123: 10047.282989,
			124: 10807.767732,
			125: 11625.814010,
			126: 12505.778692,
			127: 12970.431495
		);

		128.do({
			| key |
			if (expected[key].notNil) {
				key.debug("key");
				this.assertFloatEquals(keytofreq[key], expected[key]);
			};
		})
	}


	test_scl_bigmap {
		var c = ScalaCalculator();
		var keytofreq;
		var expected;

		c.sclContents_([
			"! 11-19-gould.scl",
			"!",
			"11 out of 19-tET, Mark Gould (2002)",
			" 11",
			"!",
			" 126.31579",
			" 252.63158",
			" 315.78947",
			" 442.10526",
			" 568.42105",
			" 694.73684",
			" 757.89474",
			" 884.21053",
			" 1010.52632",
			" 1136.84211",
			" 2/1"
		]);
		c.pr_parseScl;

		c.kbmContents_([
			"! 12-19.kbm",
			"! Twelve out of 19-tone keyboard mapping",
			"!",
			"! Size of map (greater than or equal to the number of notes in the scale ",
			"! to be mapped). The pattern repeats every so many keys:",
			"12",
			"! First MIDI note number to retune:",
			"0",
			"! Last MIDI note number to retune:",
			"127",
			"! Middle note where the first entry of the mapping is mapped to:",
			"60",
			"! Reference note for which frequency is given:",
			"69",
			"! Frequency to tune the above note to (floating point e.g. 440.0):",
			"440.0",
			"! Scale degree to consider as formal octave (determines difference in pitch ",
			"! between adjacent mapping patterns):",
			"19",
			"! Mapping.",
			"! The numbers represent scale degrees mapped to keys. The first entry is for",
			"! the given middle note, the next for subsequent higher keys.",
			"! For an unmapped key, put in an \"x\". At the end, unmapped keys may be left out.",
			"0",
			"1",
			"3",
			"5",
			"6",
			"8",
			"9",
			"11",
			"12",
			"14",
			"16",
			"17"
		]);
		c.pr_parseKbm;

		keytofreq = c.keyToFreq();
		expected = (
			0: 0.462211,
			1: 0.497196,
			2: 0.554700,
			3: 0.641850,
			4: 0.690432,
			5: 0.770285,
			6: 0.828589,
			7: 0.924422,
			8: 0.994392,
			9: 1.109401,
			10: 1.283699,
			11: 1.380863,
			12: 1.540571,
			13: 1.657178,
			14: 1.848843,
			15: 2.139315,
			16: 2.218802,
			17: 2.567398,
			18: 2.761726,
			19: 3.081142,
			20: 3.314355,
			21: 3.697686,
			22: 4.278631,
			23: 4.437604,
			24: 5.134796,
			25: 5.523452,
			26: 6.162283,
			27: 6.875000,
			28: 7.395373,
			29: 8.557262,
			30: 8.875207,
			31: 10.269593,
			32: 11.046904,
			33: 12.324566,
			34: 13.750000,
			35: 14.790746,
			36: 17.114523,
			37: 17.750414,
			38: 20.539186,
			39: 22.914705,
			40: 24.649133,
			41: 27.500000,
			42: 29.581491,
			43: 34.229046,
			44: 35.500828,
			45: 41.078372,
			46: 45.829410,
			47: 49.298265,
			48: 55.000000,
			49: 59.162982,
			50: 68.458092,
			51: 76.375813,
			52: 82.156743,
			53: 91.658821,
			54: 98.596531,
			55: 110.000000,
			56: 118.325965,
			57: 136.916185,
			58: 152.751626,
			59: 164.313487,
			60: 183.317642,
			61: 197.193062,
			62: 220.000000,
			63: 254.564252,
			64: 273.832370,
			65: 305.503253,
			66: 328.626973,
			67: 366.635284,
			68: 394.386123,
			69: 440.000000,
			70: 509.128505,
			71: 547.664740,
			72: 611.006505,
			73: 657.253946,
			74: 733.270567,
			75: 848.474881,
			76: 880.000000,
			77: 1018.257009,
			78: 1095.329480,
			79: 1222.013011,
			80: 1314.507892,
			81: 1466.541134,
			82: 1696.949761,
			83: 1760.000000,
			84: 2036.514019,
			85: 2190.658959,
			86: 2444.026021,
			87: 2828.007531,
			88: 2933.082269,
			89: 3393.899522,
			90: 3520.000000,
			91: 4073.028038,
			92: 4381.317919,
			93: 4888.052043,
			94: 5656.015063,
			95: 5866.164538,
			96: 6787.799044,
			97: 7040.000000,
			98: 8146.056075,
			99: 9088.211989,
			100: 9776.104086,
			101: 11312.030125,
			102: 11732.329075,
			103: 13575.598088,
			104: 14080.000000,
			105: 16292.112151,
			106: 18176.423979,
			107: 19552.208171,
			108: 22624.060250,
			109: 23464.658150,
			110: 27151.196176,
			111: 30291.446917,
			112: 32584.224302,
			113: 36352.847958,
			114: 39104.416342,
			115: 45248.120500,
			116: 46929.316300,
			117: 54302.392353,
			118: 60582.893834,
			119: 65168.448603,
			120: 72705.695916,
			121: 78208.832685,
			122: 90496.241001,
			123: 100962.847555,
			124: 108604.784705,
			125: 121165.787669,
			126: 130336.897207,
			127: 145411.391831
		);

		128.do({
			| key |
			if (expected[key].notNil) {
				key.debug("key");
				this.assertFloatEquals(keytofreq[key], expected[key]);
			};
		})

	}

	test_emptyline {
		var c = ScalaCalculator();
		var keytofreq;
		var expected;

		c.sclContents_([
			"! schulter_10.scl",
			"!",
			"Margo Schulter, 13-limit tuning, TL 14-11-2007",
			" 10",
			"!",
			" 22/21",
			" 9/8",
			" 33/28",
			" 4/3",
			" 88/63",
			" 3/2",
			" 11/7",
			" 39/22",
			" 13/7",
			" 2/1",
			""
		]);
		c.pr_parseScl;

		c.kbmContents_([
			"! 10.kbm",
			"! Example keyboard mapping for a 10-tone scale",
			"! Two tones are duplicated. Other ones may be more convenient.",
			"!",
			"! Size of map. The pattern repeats every so many keys:",
			"12",
			"! First MIDI note number to retune:",
			"0",
			"! Last MIDI note number to retune:",
			"127",
			"! Middle note where the first entry of the mapping is mapped to:",
			"60",
			"! Reference note for which frequency is given:",
			"60",
			"! Frequency to tune the above note to (floating point e.g. 440.0):",
			"261.6256",
			"! Scale degree to consider as formal octave (determines difference in pitch ",
			"! between adjacent mapping patterns):",
			"10",
			"! Mapping.",
			"! The numbers represent scale degrees mapped to keys. The first entry is for",
			"! the given middle note, the next for subsequent higher keys.",
			"0",
			"1",
			"2",
			"3",
			"3",
			"4",
			"5",
			"6",
			"7",
			"8",
			"8",
			"9",
			""
		]);
		c.pr_parseKbm;
		expected = (
			0: 8.175800,
			1: 8.565124,
			2: 9.197775,
			3: 9.635764,
			4: 9.635764,
			5: 10.901067,
			6: 11.420165,
			7: 12.263700,
			8: 12.847686,
			9: 14.493464,
			10: 14.493464,
			11: 15.183629,
			12: 16.351600,
			13: 17.130248,
			14: 18.395550,
			15: 19.271529,
			16: 19.271529,
			17: 21.802133,
			18: 22.840330,
			19: 24.527400,
			20: 25.695371,
			21: 28.986927,
			22: 28.986927,
			23: 30.367257,
			24: 32.703200,
			25: 34.260495,
			26: 36.791100,
			27: 38.543057,
			28: 38.543057,
			29: 43.604267,
			30: 45.680660,
			31: 49.054800,
			32: 51.390743,
			33: 57.973855,
			34: 57.973855,
			35: 60.734514,
			36: 65.406400,
			37: 68.520990,
			38: 73.582200,
			39: 77.086114,
			40: 77.086114,
			41: 87.208533,
			42: 91.361321,
			43: 98.109600,
			44: 102.781486,
			45: 115.947709,
			46: 115.947709,
			47: 121.469029,
			48: 130.812800,
			49: 137.041981,
			50: 147.164400,
			51: 154.172229,
			52: 154.172229,
			53: 174.417067,
			54: 182.722641,
			55: 196.219200,
			56: 205.562971,
			57: 231.895418,
			58: 231.895418,
			59: 242.938057,
			60: 261.625600,
			61: 274.083962,
			62: 294.328800,
			63: 308.344457,
			64: 308.344457,
			65: 348.834133,
			66: 365.445283,
			67: 392.438400,
			68: 411.125943,
			69: 463.790836,
			70: 463.790836,
			71: 485.876114,
			72: 523.251200,
			73: 548.167924,
			74: 588.657600,
			75: 616.688914,
			76: 616.688914,
			77: 697.668267,
			78: 730.890565,
			79: 784.876800,
			80: 822.251886,
			81: 927.581673,
			82: 927.581673,
			83: 971.752229,
			84: 1046.502400,
			85: 1096.335848,
			86: 1177.315200,
			87: 1233.377829,
			88: 1233.377829,
			89: 1395.336533,
			90: 1461.781130,
			91: 1569.753600,
			92: 1644.503771,
			93: 1855.163345,
			94: 1855.163345,
			95: 1943.504457,
			96: 2093.004800,
			97: 2192.671695,
			98: 2354.630400,
			99: 2466.755657,
			100: 2466.755657,
			101: 2790.673067,
			102: 2923.562260,
			103: 3139.507200,
			104: 3289.007543,
			105: 3710.326691,
			106: 3710.326691,
			107: 3887.008914,
			108: 4186.009600,
			109: 4385.343390,
			110: 4709.260800,
			111: 4933.511314,
			112: 4933.511314,
			113: 5581.346133,
			114: 5847.124521,
			115: 6279.014400,
			116: 6578.015086,
			117: 7420.653382,
			118: 7420.653382,
			119: 7774.017829,
			120: 8372.019200,
			121: 8770.686781,
			122: 9418.521600,
			123: 9867.022629,
			124: 9867.022629,
			125: 11162.692267,
			126: 11694.249041,
			127: 12558.028800
		);

		keytofreq = c.keyToFreq();
		128.do({
			| key |
			if (expected[key].notNil) {
				key.debug("key");
				this.assertFloatEquals(keytofreq[key], expected[key]);
			};
		});
	}

	test_cornercase_singledegree {
		var c = ScalaCalculator();
		var keytofreq;
		var expected;
		c.sclContents_([
			"! quasi_9.scl",
			"!",
			"Quasi-Equal Enneatonic, Each \"tetrachord\" has 125 + 125 + 125 + 125 cents",
			" 9",
			"!",
			" 125.00000",
			" 250.00000",
			" 375.00000",
			" 500.00000",
			" 700.00000",
			" 825.00000",
			" 950.00000",
			" 1075.00000",
			" 2/1",
		]);
		c.pr_parseScl;
		c.kbmContents_([
			"! 9.kbm",
			"! Example keyboard mapping for a 9-tone scale",
			"! Two tones are duplicated. Other ones may be more convenient.",
			"!",
			"! Size of map. The pattern repeats every so many keys:",
			"12",
			"! First MIDI note number to retune:",
			"0",
			"! Last MIDI note number to retune:",
			"127",
			"! Middle note where the first entry of the mapping is mapped to:",
			"60",
			"! Reference note for which frequency is given:",
			"60",
			"! Frequency to tune the above note to (floating point e.g. 440.0):",
			"261.6256",
			"! Scale degree to consider as formal octave (determines difference in pitch ",
			"! between adjacent mapping patterns):",
			"9",
			"! Mapping.",
			"! The numbers represent scale degrees mapped to keys. The first entry is for",
			"! the given middle note, the next for subsequent higher keys.",
			"0"
		]);
		c.pr_parseKbm;
		expected = (
			0 : 261.6256,
			12: 261.6256,
			24: 261.6256,
			36: 261.6256,
			48: 261.6256,
			60: 261.6256,
			72: 261.6256,
			84: 261.6256,
			96: 261.6256,
			108: 261.6256,
			120: 261.6256
		);
		keytofreq = c.keyToFreq();
		128.do({
			| key |
			if (expected[key].notNil) {
				key.debug("key");
				this.assertFloatEquals(keytofreq[key], expected[key]);
			};
		});
	}

	test_linearmap {
		var c = ScalaCalculator();
		var keytofreq;
		var expected;
		c.sclContents_([
			"! bohlen-p.scl",
			"!",
			"See Bohlen, H. 13-Tonstufen in der Duodezime, Acustica 39: 76-86 (1978)",
			" 13",
			"!",
			" 27/25",
			" 25/21",
			" 9/7",
			" 7/5",
			" 75/49",
			" 5/3",
			" 9/5",
			" 49/25",
			" 15/7",
			" 7/3",
			" 63/25",
			" 25/9",
			" 3/1"
		]);
		c.pr_parseScl;
		c.kbmContents_([
			"! clinear.kbm",
			"! Linear mapping with Middle C on standard frequency.",
			"! Size:",
			"0",
			"! First MIDI note number to retune:",
			"0",
			"! Last MIDI note number to retune:",
			"127",
			"! Middle note where the first entry of the mapping is mapped to:",
			"60",
			"! Reference note for which frequency is given:",
			"60",
			"! Frequency to tune the above note to (floating point e.g. 440.0):",
			"261.625565301",
			"! Scale degree to consider as formal octave (0 means last scale degree):",
			"0"
		]);
		c.pr_parseKbm;
		expected = (
			0: 1.647931,
			1: 1.794414,
			2: 1.937967,
			3: 2.110231,
			4: 2.307104,
			5: 2.512180,
			6: 2.713154,
			7: 2.990690,
			8: 3.229945,
			9: 3.488341,
			10: 3.845173,
			11: 4.152787,
			12: 4.521923,
			13: 4.943794,
			14: 5.383242,
			15: 5.813901,
			16: 6.330693,
			17: 6.921311,
			18: 7.536539,
			19: 8.139462,
			20: 8.972070,
			21: 9.689836,
			22: 10.465023,
			23: 11.535519,
			24: 12.458360,
			25: 13.565770,
			26: 14.831381,
			27: 16.149726,
			28: 17.441704,
			29: 18.992078,
			30: 20.763934,
			31: 22.609617,
			32: 24.418386,
			33: 26.916210,
			34: 29.069507,
			35: 31.395068,
			36: 34.606556,
			37: 37.375081,
			38: 40.697310,
			39: 44.494144,
			40: 48.449179,
			41: 52.325113,
			42: 56.976234,
			43: 62.291801,
			44: 67.828850,
			45: 73.255158,
			46: 80.748631,
			47: 87.208522,
			48: 94.185204,
			49: 103.819669,
			50: 112.125242,
			51: 122.091930,
			52: 133.482431,
			53: 145.347536,
			54: 156.975339,
			55: 170.928703,
			56: 186.875404,
			57: 203.486551,
			58: 219.765475,
			59: 242.245894,
			60: 261.625565,
			61: 282.555611,
			62: 311.459006,
			63: 336.375727,
			64: 366.275791,
			65: 400.447294,
			66: 436.042609,
			67: 470.926018,
			68: 512.786108,
			69: 560.626211,
			70: 610.459652,
			71: 659.296425,
			72: 726.737681,
			73: 784.876696,
			74: 847.666832,
			75: 934.377019,
			76: 1009.127180,
			77: 1098.827374,
			78: 1201.341881,
			79: 1308.127827,
			80: 1412.778053,
			81: 1538.358324,
			82: 1681.878634,
			83: 1831.378957,
			84: 1977.889274,
			85: 2180.213044,
			86: 2354.630088,
			87: 2543.000495,
			88: 2803.131057,
			89: 3027.381541,
			90: 3296.482123,
			91: 3604.025644,
			92: 3924.383480,
			93: 4238.334158,
			94: 4615.074972,
			95: 5045.635902,
			96: 5494.136871,
			97: 5933.667821,
			98: 6540.639133,
			99: 7063.890263,
			100: 7629.001484,
			101: 8409.393170,
			102: 9082.144624,
			103: 9889.446368,
			104: 10812.076933,
			105: 11773.150439,
			106: 12715.002474,
			107: 13845.224916,
			108: 15136.907707,
			109: 16482.410614,
			110: 17801.003463,
			111: 19621.917398,
			112: 21191.670789,
			113: 22887.004453,
			114: 25228.179511,
			115: 27246.433872,
			116: 29668.339105,
			117: 32436.230800,
			118: 35319.451316,
			119: 38145.007421,
			120: 41535.674747,
			121: 45410.723120,
			122: 49447.231842,
			123: 53403.010389,
			124: 58865.752193,
			125: 63575.012368,
			126: 68661.013358,
			127: 75684.538534
		);
		keytofreq = c.keyToFreq();
		128.do({
			| key |
			if (expected[key].notNil) {
				key.debug("key");
				this.assertFloatEquals(keytofreq[key], expected[key]);
			};
		});
	}

	test_oldfreqs {
		var c = ScalaCalculator();
		var keytofreq;
		var expected;
		c.sclContents_([
			"! bohlen-p.scl",
			"!",
			"See Bohlen, H. 13-Tonstufen in der Duodezime, Acustica 39: 76-86 (1978)",
			" 13",
			"!",
			" 27/25",
			" 25/21",
			" 9/7",
			" 7/5",
			" 75/49",
			" 5/3",
			" 9/5",
			" 49/25",
			" 15/7",
			" 7/3",
			" 63/25",
			" 25/9",
			" 3/1"
		]);
		c.pr_parseScl;
		c.kbmContents_(nil);
		c.pr_parseKbm;
		expected = (
			23 : 8.69100503,
			24 : 9.580032,
			25 : 10.34643456,
			26 : 11.4048,
			27 : 12.317184,
			28 : 13.4120448,
			29 : 14.66331429,
			30 : 15.96672,
			31 : 17.2440576,
			32 : 18.77686272,
			33 : 20.52864,
			34 : 22.353408,
			35 : 24.14168064,
			36 : 26.6112,
			37 : 28.740096,
			38 : 31.68,
			39 : 34.2144,
			40 : 37.25568,
			41 : 40.73142857,
			42 : 44.352,
			43 : 47.90016,
			44 : 52.157952,
			45 : 57.024,
			46 : 62.0928,
			47 : 67.060224,
			48 : 73.92,
			49 : 79.8336,
			50 : 88,
			51 : 95.04,
			52 : 103.488,
			53 : 113.1428571,
			54 : 123.2,
			55 : 133.056,
			56 : 144.8832,
			57 : 158.4,
			58 : 172.48,
			59 : 186.2784,
			60 : 205.3333333,
			61 : 221.76,
			62 : 244.4444444,
			63 : 264,
			64 : 287.4666667,
			65 : 314.2857143,
			66 : 342.2222222,
			67 : 369.6,
			68 : 402.4533333,
			69 : 440,
			70 : 479.1111111,
			71 : 517.44,
			72 : 570.3703704,
			73 : 616,
			74 : 679.0123457,
			75 : 733.3333333,
			76 : 798.5185185,
			77 : 873.015873,
			78 : 950.617284,
			79 : 1026.666667,
			80 : 1117.925926,
			81 : 1222.222222,
			82 : 1330.864198,
			83 : 1437.333333,
			84 : 1584.36214,
			85 : 1711.111111,
			86 : 1886.145405,
			87 : 2037.037037,
			88 : 2218.106996,
			89 : 2425.044092,
			90 : 2640.603567,
			91 : 2851.851852,
			92 : 3105.349794,
			93 : 3395.061728,
			94 : 3696.844993,
			95 : 3992.592593,
			96 : 4401.005944,
			97 : 4753.08642,
			98 : 5239.292791,
			99 : 5658.436214,
			100 : 6161.408322,
			101 : 6736.233588,
			102 : 7335.009907,
			103 : 7921.8107,
			104 : 8625.971651,
			105 : 9430.727023,
			106 : 10269.01387,
			107 : 11090.53498,
			108 : 12225.01651
		);
		keytofreq = c.keyToFreq();
		128.do({
			| key |
			if (expected[key].notNil) {
				key.debug("key");
				this.assertFloatEquals(keytofreq[key], expected[key]);
			};
		});
	}

}

TestScalaCalculator {
	*new {
		^super.new.init();
	}

	*initClass {
		UnitTest.reportPasses_(true);
		UnitTest.passVerbosity_(UnitTest.brief);
	}

	init {
		// run all tests
		Test_Calc.run;
		//Test11_19.run;

		// run single tests
		Test11_19.runTest("Test11_19:test_scl_std");
		Test11_19.runTest("Test11_19:test_emptyline");
		Test11_19.runTest("Test11_19:test_linearmap");

		Test11_19.runTest("Test11_19:test_scl_bigmap"); // why scala has some different replies?
		//Test11_19.runTest("Test11_19:test_cornercase_singledegree"); // why scala uses no octave equivalence?
//		Test11_19.runTest("Test11_19:test_oldfreqs");
	}
}