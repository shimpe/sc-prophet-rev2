ScProphetRev2Studio {
	var <>u;
	var <>w;
	var <>parent;
	var <>condition;
	var <>p;
	var <>n;
	var <>d;
	var <>bld;
	var <>controls;
	var <>key_to_default;
	var <>specstore;
	var <>delegation_controls;
	var <>parameters;
	var <>envelopeviewers;
	var <>gatedsequencer;
	var <>midilooper;
	var <>midilooperview;
	var <>tabrow;
	var <>tablayout;

	*new {
		| parent, prophet |
		^super.new.init(parent, prophet);
	}

	cleanUpFunc {
		^this.midilooper.cleanUpFunc();
	}

	init {
		|parent, prophet|
		this.parent = parent;
		this.p = prophet;
		^this
	}

	asView {
		this.n = NrpnTable.new;
		this.d = PatchDumper.new;
		this.bld = ScProphetRev2LiveControlBuilder.new;
		this.controls = Dictionary.new;
		this.key_to_default = Dictionary.new;
		this.specstore = Dictionary.new;
		this.delegation_controls = Dictionary.new;
		this.controls["A"] = IdentityDictionary.new;
		this.controls["B"] = IdentityDictionary.new;
		this.key_to_default["A"] = IdentityDictionary.new;
		this.key_to_default["B"] = IdentityDictionary.new;
		this.specstore["A"] = ();
		this.specstore["B"] = ();
		this.delegation_controls["A"] = IdentityDictionary.new;
		this.delegation_controls["B"] = IdentityDictionary.new;

		this.p.makeNRPNResponder(this.n.str2num('LAYER A/B SWITCH', "A") /* sent when changing program */, { | value, parnum |
			this.p.get_current_patch_state(completionHandler:{
				// loop over all controls and set all values; also refresh the plotviews
				["A", "B"].do({
					|layer|
					this.key_to_default[layer].keysValuesDo({
						| key, value, i |
						{
							this.controls[layer][key].value_(this.key_to_default[layer][key].());
							if (key.asString.find("plotter").notNil) {
								this.controls[layer][key].specs_(this.specstore[layer][key][\specs]).domainSpecs_(this.specstore[layer][key][\domainspecs]);
								this.controls[layer][key].interactionView.refresh;
							}
						}.defer;
					});
				});
			});
		});

		this.parameters = View().layout_(HLayout(
			ScProphetRev2ParametersPane.new(this.parent, this.delegation_controls["A"],
				this.controls["A"], this.specstore["A"], this.key_to_default["A"],
				this.p, this.bld, this.n, "A"),
			nil,
			ScProphetRev2ParametersPane.new(this.parent, this.delegation_controls["B"],
				this.controls["B"], this.specstore["B"], this.key_to_default["B"],
				this.p, this.bld, this.n, "B")));

		this.envelopeviewers = View().layout_(HLayout(
			ScProphetRev2EnvelopeEditor.new(this.parent, this.delegation_controls["A"],
				this.controls["A"], this.specstore["A"], this.key_to_default["A"],
				this.p, this.bld, this.n, "A"),
			ScProphetRev2EnvelopeEditor.new(this.parent, this.delegation_controls["B"],
				this.controls["B"], this.specstore["B"], this.key_to_default["B"],
				this.p, this.bld, this.n, "B")));

		this.gatedsequencer = View().layout_(HLayout(
			ScProphetRev2GatedSequenceEditor.new(this.parent,
				this.d, 16, this.delegation_controls["A"],
				this.controls["A"], this.specstore["A"], this.key_to_default["A"],
				this.p, this.bld, this.n, "A"),
			ScProphetRev2GatedSequenceEditor.new(this.parent,
				this.d, 16, this.delegation_controls["B"],
				this.controls["B"], this.specstore["B"], this.key_to_default["B"],
				this.p, this.bld, this.n, "B")));

		this.midilooper = ScProphetRev2MidiLooper.new(this.p, 16);
		this.midilooperview =  View().layout_(HLayout(this.midilooper));

		this.tablayout=StackLayout(this.parameters, this.envelopeviewers, this.gatedsequencer, this.midilooperview);

		this.tabrow = HLayout(
			Button().string_("Parameters").action_({ this.tablayout.index = 0; }),
			Button().string_("Envelopes").action_({this.tablayout.index = 1;}),
			Button().string_("Gated Sequencer").action_({this.tablayout.index = 2;}),
			Button().string_("Poly Seq On Steroids").action_({this.tablayout.index = 3;})
		);

		^View().layout_(VLayout(this.tabrow, this.tablayout));
	}

}