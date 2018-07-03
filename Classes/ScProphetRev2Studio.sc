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
	var <>savename;
	var <>location;
	var <>ppane1;
	var <>ppane2;
	var <>gseq1;
	var <>gseq2;

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

		this.ppane1 = ScProphetRev2ParametersPane.new(this.parent, this.delegation_controls["A"],
				this.controls["A"], this.specstore["A"], this.key_to_default["A"],
				this.p, this.bld, this.n, "A");
		this.ppane2 = ScProphetRev2ParametersPane.new(this.parent, this.delegation_controls["B"],
				this.controls["B"], this.specstore["B"], this.key_to_default["B"],
				this.p, this.bld, this.n, "B");

		this.parameters = View().layout_(HLayout(
			this.ppane1,
			nil,
			this.ppane2));

		this.envelopeviewers = View().layout_(HLayout(
			ScProphetRev2EnvelopeEditor.new(this.parent, this.delegation_controls["A"],
				this.controls["A"], this.specstore["A"], this.key_to_default["A"],
				this.p, this.bld, this.n, "A"),
			ScProphetRev2EnvelopeEditor.new(this.parent, this.delegation_controls["B"],
				this.controls["B"], this.specstore["B"], this.key_to_default["B"],
				this.p, this.bld, this.n, "B")));

		this.gseq1 = ScProphetRev2GatedSequenceEditor.new(this.parent,
				this.d, 16, this.delegation_controls["A"],
				this.controls["A"], this.specstore["A"], this.key_to_default["A"],
				this.p, this.bld, this.n, "A");
		this.gseq2 = ScProphetRev2GatedSequenceEditor.new(this.parent,
				this.d, 16, this.delegation_controls["B"],
				this.controls["B"], this.specstore["B"], this.key_to_default["B"],
				this.p, this.bld, this.n, "B");
		this.gatedsequencer = View().layout_(HLayout(this.gseq1,this.gseq2));
		this.midilooper = ScProphetRev2MidiLooper.new(this.p, 16);
		this.midilooperview = View().layout_(HLayout(this.midilooper));

		this.tablayout=StackLayout(this.parameters,
			View().layout_(
				HLayout(
					VLayout(this.ppane1.automationView, this.gseq1.automationView),
					VLayout(this.ppane2.automationView, this.gseq2.automationView))),
			this.envelopeviewers,
			this.gatedsequencer,
			this.midilooperview);

		this.location = TextField().string_(Platform.userHomeDir +/+ "ScRev2Presets");
		this.savename = TextField().string_("test");
		this.tabrow = VLayout(
			HLayout(
				Button().string_("Parameters").action_({ this.tablayout.index = 0; }),
				Button().string_("Automation").action_({ this.tablayout.index = 1; }),
				Button().string_("Envelopes").action_({this.tablayout.index = 2;}),
				Button().string_("Gated Sequencer").action_({this.tablayout.index = 3;}),
				Button().string_("Poly Seq On Steroids").action_({this.tablayout.index = 4;})
			),
			HLayout(
				StaticText().string_("Location"),
				this.location,
				StaticText().string_("Name"),
				this.savename,
				Button().string_("Quick Save to Disk").action_({
					| button |
					var fl;
					var filename = this.location.value +/+ this.savename.value ++ ".preset";
					if (PathName.new(this.location.value).isFolder.not) {
						try {
							File.mkdir(this.location.value);
						} {
							| error |
							"Error creating location folder "++this.location.value++" ("++error++")";
						};
					};

					{
						fl = File.new(filename, "w");
						["A", "B"].do({
							|layer|
							this.controls[layer].keys.do({
								|key|
								if (key.asString.find("control") == 0) {
									fl.write(layer);
									fl.write(" : ");
									fl.write(key.asString);
									fl.write(" : ");
									fl.write(controls[layer][key].value.asString);
									fl.write("\n");
								};
							});
						});
					}.protect({
						fl.close;
					});
				}),
				Button().string_("Quick Load from Disk").action_({
					var folder = this.location.value;
					var filename = FileDialog.new(
						okFunc:{
							| filename |
							var fl = File.new(filename, "r");
							var lines = fl.readAllString.split($\n);
							lines.do({
								| line |
								if (line.stripWhiteSpace != "") {
									var lay_control_value = line.split($:);
									var layer = lay_control_value[0].stripWhiteSpace;
									var key = lay_control_value[1].stripWhiteSpace.postln;
									var value = lay_control_value[2].stripWhiteSpace;
									var val = value.asInt;
									var symbolkey = key.asSymbol;
									if (key.find("plotter").isNil) {
										if (value.compare("false") == 0) {
											val = false;
										};
										if (value.compare("true") == 0) {
											val = true;
										};
										{
											this.controls[layer][symbolkey].valueAction_(val);
										}.defer;
									} {
										var values = value.copyRange(1, value.size-2).split($,).collect({ |el| el.asInt;});
										{
											this.controls[layer][symbolkey]
											   .value_(values)
											   .specs_(this.specstore[layer][symbolkey][\specs])
											   .domainSpecs_(this.specstore[layer][symbolkey][\domainspecs]);
											this.controls[layer][symbolkey].interactionView.refresh;
										}.defer;
									};
								};
							});
						},
						cancelFunc:{},
						fileMode:1,
						acceptMode:0,
						stripResult:true,
						path:folder
					);
				}),
				nil,
		));

		^View().layout_(VLayout(this.tabrow, this.tablayout));
	}

}