Test11_19 : UnitTest {
	test_scl_11_19_gould_kbm_11 {
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
			this.assertFloatEquals(keytofreq[key], expected[key]);
		})
	}

	test_scl_11_19_gould_kbm_19 {
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
			this.assertFloatEquals(keytofreq[key], expected[key], onFailure:key.debug("key"));
		})
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
		Test11_19.run;
	}
}
