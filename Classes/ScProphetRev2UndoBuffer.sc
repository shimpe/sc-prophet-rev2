ScProphetRev2UndoBuffer {
    var <>parent;
    var <>prophet;
    var <>temporaldict;

    var <>completeresetbutton;
    var <>startrecordingbutton;

    *new { | parent, prophet |
        ^super.new.init(parent, prophet);
    }

    init { | parent, prophet |
        this.parent = parent;
        this.prophet = prophet;
        this.temporaldict = TemporalDict();
    }

    asView {
        this.completeresetbutton = Button().string_("Complete Reset").action_({
            this.temporaldict.complete_reset();
        });
        this.startrecordingbutton = Button().states_([
            ["Start recording", Color.white, Color.black],
            ["Stop recording", Color.white, Color.red]]).action_({
            "toggle recording".postln;
        });
        ^View().layout_(VLayout(HLayout(nil, this.completeresetbutton, this.startrecordingbutton, nil), nil));
    }

}