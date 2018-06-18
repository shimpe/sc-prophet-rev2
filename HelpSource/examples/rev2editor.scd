(
s.waitForBoot({
	var w = Window.new("ScProphetRev2Studio", Rect(0,0,1800,1200));
	var condition;
	var p = ScProphetRev2.new;
	var studio;

	p.connect;

	condition = Condition.new;
	p.get_current_patch_state(completionHandler:{condition.unhang;}); // if this hangs up, check if midi configured to use alsa sequencer
	condition.wait;

	OSCdef.freeAll;
	CCResponder.removeAll;

	studio = ScProphetRev2Studio.new(w, p);
	w.layout_(VLayout(studio));
	w.front;
	w.onClose_(studio.cleanUpFunc.value)
});
)
