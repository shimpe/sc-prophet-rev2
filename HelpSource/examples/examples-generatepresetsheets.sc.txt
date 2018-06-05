// first run this and wait until connected
(
~synth = ScProphetRev2.new;
~synth.connect;
)

// then run this
(
~banks = ["U1", "U2", "U3", "U4", "F1", "F2", "F3", "F4"];
~collectedpatches = [];
~filecontents = [];
fork {
	var prepath = PathName(TemplateFiller.class.filenameSymbol.asString).parentPath +/+ "template" +/+ "template-preamble.tex";
	var postpath = PathName(TemplateFiller.class.filenameSymbol.asString).parentPath +/+ "template" +/+ "template-postamble.tex";
	var outputpath = PathName("/home/shimpe/documents/music/rev2presets/output");
	var pngpath = PathName(TemplateFiller.class.filenameSymbol.asString).parentPath +/+ "template" +/+ "rev2.png";
	var templatefile = PathName(TemplateFiller.class.filenameSymbol.asString).parentPath +/+ "template" +/+ "template.tex";
	var cond = Condition.new;

	OSCdef.freeAll;
	CCResponder.removeAll;

	// add latex preamble
	~collectedpatches = ~collectedpatches.add(File.readAllString(prepath));

	// add disassembled patches for all factory presets
	(4..7).do({ // banks 4,5,6,7 = F1-F4 (use 0..3 for U1-U4)
		|bank|
		(0..127).do({ // (0..127) = programs 1..128
			| patch |
			var b = ~banks[bank];
			var p = "P"++(patch+1);
			("Processing patch "++b++p).postln;
			~synth.get_patch_from_synth(bank, patch, {
				"Got patch".postln;
				~synth.get_global_parameters_from_synth({
					~expl = TemplateFiller.new(~synth.rev2, ~synth.last_patch_sysex_stream);
					//~copyrev = ~synth.rev2.copy();
					//~copysysex = ~synth.last_patch_sysex_stream.copy();
					~collectedpatches = ~collectedpatches.add(~expl.generate_string(b, p, templatefile));
					cond.unhang; // release lock when finished
				});
			});
			cond.wait; // wait until previous iteration finished
		});
	});

	// add an empty sheet
	~collectedpatches = ~collectedpatches.add(~expl.generate_string("", "", templatefile, lookupvalues:false));

	// add post-amble
	~collectedpatches = ~collectedpatches.add(File.readAllString(postpath));

	"Copying image.".postln;

	if (File.exists((outputpath +/+ "rev2.png").fullPath).not) {
		File.copy(pngpath.fullPath, (outputpath +/+ "rev2.png").fullPath);
	} {
		"Skip copy of image file because it exists already".postln;
	};

	"Writing to file.".postln;

	File.use((outputpath +/+ "specsheets.tex").fullPath, "w", { |f|
		~collectedpatches.do({
			|el|
			f.write(el);
		});

	});

	"Finished".postln;
};
)
