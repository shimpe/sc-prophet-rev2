(
s.waitForBoot({
	var prophet = ScProphetRev2.new;
	var livecoder;
	var window;
	prophet.connect;
	livecoder = ScProphetRev2MidiLooper.new(prophet, 16);
	window = Window.new("Live Coding in Panola syntax", Rect(0,0,1800, 300));
	window.layout = VLayout(livecoder);
	window.front;
	window.onClose_({livecoder.cleanUpFunc});
});
)