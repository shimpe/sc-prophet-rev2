Test11_19 : UnitTest {
	test_scl_11_19_gould_kbm_11 {
		c = ScalaCalculator();
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
		c.kbmContents_({
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
		});
		c.pr_parseScl;
		c.pr_parseKbm;

		this.assertEquals(c.sclInfo[\description], "11 out of 19-tET, Mark Gould (2002)", "", false);
		this.assertEquals(c.sclInfo[\notes], 11, "", false);
		this.assertEquals(c.sclInfo[\octavefactor], (\value:1200, \type: \cents), "", false);
		this.assertEquals(c.sclInfo[\tuning][\0], (), "", false);
	}
}

TestScalaCalculator {
	*new {
		^super.new.init();
	}

	init {
		Test11_19.run;
	}
}
}