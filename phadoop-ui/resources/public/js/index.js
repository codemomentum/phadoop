$(function(){
	var MODE = "javascript";

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
		// TODO
	});
});