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