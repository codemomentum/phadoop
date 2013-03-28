$(function () {
    var MODE = "javascript";

    Messenger.options = {
        extraClasses: 'messenger-fixed messenger-on-bottom',
        theme: 'future'
    };

    // activate codemirror
    var mapperEditor = CodeMirror.fromTextArea(document.getElementById("mapperCode"), {
        theme: "solarized light",
        lineNumbers: true,
        matchBrackets: true,
        continueComments: "Enter",
        mode: "javascript"
    });
    var reducerEditor = CodeMirror.fromTextArea(document.getElementById("reducerCode"), {
        theme: "solarized light",
        lineNumbers: true,
        matchBrackets: true,
        continueComments: "Enter",
        mode: "javascript"
    });

    $(".CodeMirror").addClass("span6");

    function changeMode(mode) {
        MODE = mode;
        mapperEditor.setOption("mode", MODE);
        reducerEditor.setOption("mode", MODE);
    }

    function validateForm() {
        var errorMsg = "";

        if (!$.trim($("#inputPath").val()).length) {
            errorMsg += "Input path cannot be empty! ";
        }
        if (!$.trim($("#outputPath").val()).length) {
            errorMsg += "Output path cannot be empty! ";
        }
        if (!$.trim(mapperEditor.getValue()).length) {
            errorMsg += "Mapper code cannot be empty! ";
        }
        if (!$.trim(reducerEditor.getValue()).length) {
            errorMsg += "Reducer code cannot be empty! ";
        }

        if (errorMsg) {
            Messenger().post({
                message: errorMsg,
                type: "error",
                showCloseButton: true,
                hideAfter: 10,
                id: "validation-error"
            });
        }

        return !errorMsg;
    }

    // bind event handlers
    $("#jsModeBtn").click(function () {
        changeMode("javascript");
        $("button.modeChanger").removeClass("active");
        $(this).addClass("active");
    });

    $("#pyModeBtn").click(function () {
        changeMode("python");
        $("button.modeChanger").removeClass("active");
        $(this).addClass("active");
    });

    $("#rbModeBtn").click(function () {
        changeMode("ruby");
        $("button.modeChanger").removeClass("active");
        $(this).addClass("active");
    });

    $("#submitBtn").click(function () {
        if (validateForm()) {
            var mext = "js";
            if (MODE === "javascript") {
                mext = "js";
            } else if (MODE === "python") {
                mext = "py";
            } else if (MODE === "ruby") {
                mext = "rb";
            }
            var rext = "js";
            if (MODE === "javascript") {
                rext = "js";
            } else if (MODE === "python") {
                rext = "py";
            } else if (MODE === "ruby") {
                rext = "rb";
            }
            $.post("/job", {
                input_path: $.trim($("#inputPath").val()),
                output_path: $.trim($("#outputPath").val()),
                mapper_code: $.trim(mapperEditor.getValue()),
                reducer_code: $.trim(reducerEditor.getValue()),
                mapper_extension: mext,
                reducer_extension: rext
            }).done(function (data) {
                    Messenger().post({
                        message: "Map-Reduce job submitted with id '" + "_id" + "'.",
                        type: "success",
                        showCloseButton: true,
                        hideAfter: 10,
                        id: "submit-result"
                    });
                }).fail(function () {
                    Messenger().post({
                        message: "Could not submit map-reduce job.",
                        type: "error",
                        showCloseButton: true,
                        hideAfter: 10,
                        id: "submit-result"
                    });
                });
        }
    });
});