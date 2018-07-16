(
var c = ScalaCalculator();
c.sclContents_([
	"! meanquar.scl",
	"!",
	"1/4-comma meantone scale. Pietro Aaron's temperament (1523)",
	"12",
	"!",
	"76.04900",
	"193.15686 with a comment",
	"3",
	"5/4",
	"    503.42157   space in front",
	"|-4 4 -1 > and a comment",
	"  | -4/5 6/-3 2 -1 > with negative exponents and space in front",
	"25/16",
	"889.73529",
	"-5/6", // expect error here
	"1082.89214",
	"2/1"
]);
c.pr_parseScl;
c.sclInfo[\octavefactor].debug("octave factor");
c.sclInfo[\tuning].keysValuesDo({
	|key|
	key.debug("key");
	c.sclInfo[\tuning][key].debug("value");
});

c.kbmContents_([
	"! 6.kbm",
	"! Keyboard mapping for a 6-tone scale on white keys: (BC)DEFGA",
	"! Keys B and C get the same note.",
	"!",
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
	"6",
	"! Mapping.",
	"! The numbers represent scale degrees mapped to keys. The first entry is for",
	"! the given middle note, the next for subsequent higher keys.",
	"! For an unmapped key, put in an \"x\". At the end, unmapped keys may be left out.",
	"0",
	"x",
	"1",
	"x",
	"2",
	"3",
	"x",
	"4",
	"x",
	"5",
	"x",
	"6"
]);
c.pr_parseKbm;
c.kbmInfo[\mapping].debug("mapping");
c.kbmPath_("test");
c.pr_parseKbm;
c.kbmInfo.debug("info");
)


(
c = ScalaCalculator();
c.sclContents_([
	"! test.scl",
	"!",
	"test scale",
	"2",
	"!",
	"600.0",
	"2/1"
]);
c.kbmContents_([
	"! 2.kbm",
	"! Keyboard mapping for a 2-tone scale",
	"!",
	"2",
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
	"2",
	"! Mapping.",
	"! The numbers represent scale degrees mapped to keys. The first entry is for",
	"! the given middle note, the next for subsequent higher keys.",
	"! For an unmapped key, put in an \"x\". At the end, unmapped keys may be left out.",
	"0",
	"1"
]);
c.pr_parseScl;
c.sclInfo.debug("sclInfo");
c.pr_parseKbm;
d = c.calculateKeyToFreq;
[60, 61, 62, 63, 64, 65, 66, 67, 68, 69].do({
	|val|
	d[val.asSymbol].debug(val);
});

)

(
c = ScalaCalculator();
c.sclContents_([
	"! test.scl",
	"!",
	"test scale",
	"3",
	"!",
	"200.0",
	"600.0",
	"2/1"
]);
c.kbmContents_([
	"! 2.kbm",
	"! Keyboard mapping for a 2-tone scale",
	"!",
	"2",
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
	"2",
	"! Mapping.",
	"! The numbers represent scale degrees mapped to keys. The first entry is for",
	"! the given middle note, the next for subsequent higher keys.",
	"! For an unmapped key, put in an \"x\". At the end, unmapped keys may be left out.",
	"0",
	"2"
]);
c.pr_parseScl;
c.sclInfo.debug("sclInfo");
c.pr_parseKbm;
d = c.calculateKeyToFreq;
[0, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 127, 128].do({
	|val|
	d[val.asSymbol].debug(val);
});

)


(
c = ScalaCalculator();
c.sclContents_([
	"! meanquar.scl",
	"!",
	"1/4-comman meantone scale. Pietro Aaron'stemp. (1523). 6/5 beats twice 3/2",
	"12",
	"!",
	"76.04900",
	"193.15686",
	"310.26471",
	"5/4",
	"503.42157",
	"579.47057",
	"696.57843",
	"25/16",
	"889.73529",
	"1006.84314",
	"1082.89214",
	"2/1"
]);
c.kbmContents_([
	"! miller.kbm",
	"! Herman Miller's 24-tone keyboard mapping",
	"! Keyboard mapping with naturals and flats on the white keys and sharps",
	"! on the black keys, tuned to C=256 Hz. Most useful for extended meantone",
	"! scales, also works well for 24-TET with the quarter-tones on the black",
	"! keys. Scales used with this mapping should have 24 scale degrees as",
	"! follows:",
	"!",
	"! C C# Db Cx D D# Eb Dx E E# F F# Gb Fx G G# Ab Gx A A# Bb Ax B B#",
	"!",
	"! Size of map (greater than or equal to the number of notes in the scale",
	"! to be mapped). The pattern repeats every so many keys:",
	"61",
	"! First note to retune:",
	"36",
	"! Last note to retune:",
	"96",
	"! Middle note where the first entry of the mapping is mapped to:",
	"36",
	"! Reference note for which frequency is given:",
	"36",
	"! Frequency to tune the above note to (floating point e.g. 440.0):",
	"128",
	"! Scale degree to consider as formal octave (determines difference in pitch",
	"! between adjacent mapping patterns):",
	"24",
	"! Mapping.",
	"! The numbers represent scale degrees mapped to keys. The first entry is for",
	"! the given middle note, the next for subsequent higher keys.",
	"! For an unmapped key, put in an \"x\". At the end, unmapped keys may be left out.",
	"0",
	"1",
	"2",
	"3",
	"4",
	"6",
	"7",
	"8",
	"9",
	"10",
	"11",
	"12",
	"14",
	"15",
	"16",
	"17",
	"18",
	"20",
	"21",
	"22",
	"23",
	"24",
	"25",
	"26",
	"28",
	"29",
	"30",
	"31",
	"32",
	"34",
	"35",
	"36",
	"37",
	"38",
	"39",
	"40",
	"42",
	"43",
	"44",
	"45",
	"46",
	"48",
	"49",
	"50",
	"51",
	"52",
	"53",
	"54",
	"56",
	"57",
	"58",
	"59",
	"60",
	"62",
	"63",
	"64",
	"65",
	"66",
	"67",
	"68",
	"70",
	"71"
]);
c.pr_parseScl;
c.sclInfo.debug("sclInfo");
c.pr_parseKbm;
c.kbmInfo.debug("kbmInfo");
d = c.calculateKeyToFreq;
[0, 36, 37, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 127, 128].do({
	|val|
	d[val.asSymbol].debug(val);
});
)