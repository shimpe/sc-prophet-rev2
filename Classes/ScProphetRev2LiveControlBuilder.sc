ScProphetRev2LiveControlBuilder {
	*new {
		^super.new.init();
	}

	init {
	}

	make_checkbox {
		| parent, patchdumper, controls, controlspecstore, keystore, key, prophet, nrpn, label |
		var finalkey = ("control_"++key).asSymbol;
		var default;
		//("make checkbox: "++label).postln;
		keystore[finalkey] = {
			patchdumper.init(prophet.rev2, prophet.last_sysex_stream);
			patchdumper.lut(nrpn, 0, 1, norange:true, midivalue:true, includeunit:false).asInt;
		};
		//("looked up initial value").postln;
		controlspecstore[finalkey] = (\type:\checkbox, \nrpn:nrpn);
		//("stored spec").postln;
		default = keystore[finalkey].();
		//("got initial value").postln;
		controls[finalkey] = CheckBox.new(parent, Rect(), label).action_({
			| cb |
			{ prophet.sendNRPN(nrpn, if (cb.value) {1} {0} ); }.defer;
		}).value_(default);
		//("defined control").postln;
		prophet.makeNRPNResponder(nrpn, {
			| value, nrpn |
			{ controls[finalkey].value_(value) }.defer;
		});
		//("defined reply").postln;
		^controls[finalkey];
	}

	make_labeled_combobox {
		| parent, patchdumper, delegationcontrols, controls, controlspecstore, keystore, key, prophet, nrpn, label, items |
		var labelkey = ("label_"++key.asString).asSymbol;
		var combokey = ("control_"++key.asString).asSymbol;
		var defaultidx;
		//("set up keys").postln;
		keystore[combokey] = {
			patchdumper.init(prophet.rev2, prophet.last_sysex_stream);
			patchdumper.lut(nrpn, 0, 127, norange:true, midivalue:true, includeunit:false).asInt;
		};
		//("looked up initial value").postln;
		controlspecstore[combokey] = (\type:\combo, \items:items, \nrpn:nrpn);
		//("stored control spec").postln;
		defaultidx = keystore[combokey].();
		//("got default value").postln;
		controls[labelkey] = StaticText.new(parent, Rect()).string_(label);
		controls[combokey] = PopUpMenu.new(parent, Rect()).items_(items).action_({
			| combo |
			{
				prophet.sendNRPN(nrpn, combo.value);
				if (delegationcontrols[combokey].notNil) { controls[delegationcontrols[combokey]].value_(combo.value); parent.refresh; };
			}.defer;

		}).value_(defaultidx).allowsReselection_(true);
		//("defined controls").postln;
		prophet.makeNRPNResponder(nrpn, {
			| value, nrpn |
			{ controls[combokey].value_(value) }.defer;
		});
		//("defined reply").postln;
		^controls[combokey];
	}

	make_delegating_combobox {
		| parent, delegationcontrols, controls, controlspecstore, keystore, delegatetokey, prophet, nrpn, items |
		var combokey = ("control_"++delegatetokey).asSymbol;
		var ownkey = ("delegating_control_"++delegatetokey).asSymbol;
		var defaultidx = keystore[combokey].();
		keystore[ownkey] = {
			var val = keystore[combokey].(); // delegate to master control
			val;
		};
		controls[ownkey] = PopUpMenu.new(parent, Rect()).items_(items).action_({
			| combo |
			{ controls[combokey].valueAction_(combo.value).value_(defaultidx).allowsReselection_(true) }.defer;
		});
		prophet.makeNRPNResponder(nrpn, {
			| value, nrpn |
			{ controls[ownkey].value_(value) }.defer;
		});
		delegationcontrols[combokey] = ownkey;
		^controls[ownkey];
	}

	make_labeled_textfield {
		| parent, patchdumper, delegationcontrols, controls, controlspecstore, keystore, key, prophet, nrpn, label, min, max, nrpnoffset=0 |
		var labelkey = ("label_"++key.asString).asSymbol;
		var textfieldkey = ("control_"++key.asString).asSymbol;
		var default;
		keystore[textfieldkey] = {
			patchdumper.init(prophet.rev2, prophet.last_sysex_stream);
			patchdumper.lut(nrpn, 0, -1, norange:true, midivalue:false, includeunit:false).asInt;
		};
		controlspecstore[textfieldkey] = (\type:\textfield, \min:min, \max:max, \nrpn:nrpn, \nrpnoffset:nrpnoffset);
		default = keystore[textfieldkey].();
		controls[labelkey] = StaticText.new(parent, Rect()).string_(label);
		controls[textfieldkey] = TextField.new(parent, Rect()).action_({
			| combo |
			if ((combo.value.asInt) < (min.asInt)) {
				combo.value_(min.asInt);
			};
			if ((combo.value.asInt) > (max.asInt)) {
				combo.value_(max.asInt);
			};
			{
				prophet.sendNRPN(nrpn, combo.value.asInt+nrpnoffset);
				if (delegationcontrols[textfieldkey].notNil) { controls[delegationcontrols[textfieldkey]].value_(combo.value.asInt); parent.refresh;};
			}.defer;
		}).value_(default);
		prophet.makeNRPNResponder(nrpn, {
			| value, nrpn |
			{ controls[textfieldkey].value_(value.asInt-nrpnoffset); }.defer;
		});
		^controls[textfieldkey];
	}

	make_delegating_textfield {
		| parent, delegationcontrols, controls, controlspecstore, keystore, delegatetokey, prophet, nrpn, nrpnoffset=0 |
		var textfieldkey = ("control_"++delegatetokey).asSymbol;
		var ownkey = ("delegating_control_"++delegatetokey).asSymbol;
		var default = keystore[textfieldkey].();
		var min = controlspecstore[textfieldkey][\min];
		var max = controlspecstore[textfieldkey][\max];
		keystore[ownkey] = {
			var val = keystore[textfieldkey].();
			{ parent.refresh; }.defer;
			val;
		};
		controls[ownkey] = TextField.new(parent, Rect()).action_({
			| tfield |
			if ((tfield.value.asInt) < (min.asInt)) {
				tfield.value_(min.asInt);
			};
			if ((tfield.value.asInt) > (max.asInt)) {
				tfield.value_(max.asInt);
			};
			{
				controls[textfieldkey].valueAction_(tfield.value);
				parent.refresh;
			}.defer;
		}).value_(default);
		prophet.makeNRPNResponder(nrpn, {
			| value, nrpn |
			{
				controls[ownkey].value_(value.asInt-nrpnoffset);
				parent.refresh;
			}.defer;
		});
		delegationcontrols[textfieldkey] = ownkey;
		^controls[ownkey];
	}

	make_label {
		| parent, controls, key, label |
		var labelkey = ("label_"++key).asSymbol;
		controls[labelkey] = StaticText.new(parent, Rect()).string_(label).font_(Font("Monaco", 16)).background_(Color.yellow);
		^controls[labelkey];
	}

	make_nudge_button {
		| parent, controls, controlspecstore, key, label, listofcontrolids |
		var buttonkey = ("button_"++key).asSymbol;
		controls[buttonkey] = Button.new(parent, Rect()).string_(label).states_([
			["Nudge", Color.black, Color.yellow.lighten(0.7)]
		]).action_({
			listofcontrolids.do({
				| id, index |
				var controlkey = ("control_"++id).asSymbol;
				var spec = controlspecstore[controlkey];
				if (spec[\type] == \checkbox) {
					if (0.3.coin) {
						controls[controlkey].valueAction_(0.rrand(1));
					};
				};
				if (spec[\type] == \combo) {
					if (0.3.coin) {
						var oldvalue = controls[controlkey].value.asInt;
						var perturbation = (-3).rrand(3);
						var finalvalue = oldvalue + perturbation;
						if (finalvalue < 0) {
							finalvalue = 0;
						};
						if (finalvalue > ((spec[\items].size)-1)) {
							finalvalue = (spec[\items].size)-1;
						};
						controls[controlkey].valueAction_(finalvalue);
					};
				};
				if (spec[\type] == \textfield) {
					if (0.3.coin) {
						var oldvalue = controls[controlkey].value.asInt;
						var perturbation = (-3).rrand(3);
						var finalvalue = oldvalue + perturbation;
						if (finalvalue < spec[\min]) {
							finalvalue = spec[\min];
						};
						if (finalvalue > spec[\max]) {
							finalvalue = spec[\max];
						};
						controls[controlkey].valueAction_(finalvalue);
					};
				};
			});
		});
		^controls[buttonkey];
	}

	make_randomize_button {
		| parent, controls, controlspecstore, key, label, listofcontrolids |
		var buttonkey = ("button_"++key).asSymbol;
		controls[buttonkey] = Button.new(parent, Rect()).string_(label).states_([
			["Randomize", Color.black, Color.red.lighten(0.7)]
		]).action_({
			listofcontrolids.do({
				| id, index |
				var controlkey = ("control_"++id).asSymbol;
				var spec = controlspecstore[controlkey];
				if (spec[\type] == \checkbox) {
					controls[controlkey].valueAction_(0.rrand(1));
				};
				if (spec[\type] == \combo) {
					controls[controlkey].valueAction_(0.rrand(spec[\items].size-1));
				};
				if (spec[\type] == \textfield) {
					controls[controlkey].valueAction_((spec[\min]).rrand(spec[\max]));
				};
			});
		});
		^controls[buttonkey];
	}


	make_rnd_buttons {
		| parent, controls, controlspecstore, key, listofcontrolids |
		var key1 = ("perturb"++key).asSymbol;
		var key2 = ("randomize"++key).asSymbol;
		this.make_nudge_button(parent, controls, controlspecstore, key1, "Nudge", listofcontrolids);
		this.make_randomize_button(parent, controls, controlspecstore, key2, "Randomize", listofcontrolids);
	}

}