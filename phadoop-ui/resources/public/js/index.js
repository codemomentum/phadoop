$(function(){
	var MODE = "javascript";

	Messenger.options = {
		extraClasses: 'messenger-fixed messenger-on-bottom',
		theme: 'future'
	};

	// activate codemirror
	var mapperEditor = CodeMirror.fromTextArea(document.getElementById("mapperCode"), {
		theme:				"solarized light",
		lineNumbers:		true,
		matchBrackets:		true,
		continueComments:	"Enter",
		mode:				"javascript"
	});
	var reducerEditor = CodeMirror.fromTextArea(document.getElementById("reducerCode"), {
		theme:				"solarized light",
		lineNumbers:		true,
		matchBrackets:		true,
		continueComments:	"Enter",
		mode:				"javascript"
	});

	$(".CodeMirror").addClass("span6");

	function changeMode(mode) {
		MODE = mode;
		mapperEditor.setOption("mode", MODE);
		reducerEditor.setOption("mode", MODE);
	}

	function validateForm() {
		var errorMsg = "";

		if(!$.trim($("#inputPath").val()).length) {
			errorMsg += "Input path cannot be empty! ";
		}
		if(!$.trim($("#outputPath").val()).length) {
			errorMsg += "Output path cannot be empty! ";
		}
		if(!$.trim(mapperEditor.getValue()).length) {
			errorMsg += "Mapper code cannot be empty! ";
		}
		if(!$.trim(reducerEditor.getValue()).length) {
			errorMsg += "Reducer code cannot be empty! ";
		}

		if(errorMsg) {
			Messenger().post({
				message: errorMsg,
				type: "error",
				showCloseButton: true,
				hideAfter: 10,
				id: "validation-error"
			});
		}
	}

	// bind event handlers
	$("#jsModeBtn").click(function(){
		changeMode("javascript");
		$("button.modeChanger").removeClass("active");
		$(this).addClass("active");
	});

	$("#pyModeBtn").click(function(){
		changeMode("python");
		$("button.modeChanger").removeClass("active");
		$(this).addClass("active");
	});

	$("#submitBtn").click(function(){
		validateForm();
	});
});