function addDiary() {
    console.log("버튼 클릭됨");
    
    var oEditors=[];
	nhn.husky.EZCreator.createInIFrame({
		oAppRef : oEditors,
		elPlaceHolder : "txtContent",
		sSkinURI : "../../../../resources/smarteditor2/SmartEditor2Skin.html",
		fCreator : "createSEditor2"
	});
    
    let userNickname = $("#hiddenUserNickname").val();
    let title = $("#diaryTitle").val();
    oEditors.getById["txtContent"].exec("UPDATE_CONTENTS_FIELD", []);
    let content = document.getElementById("txtContent").value.replace("\r\n", "");
    console.log(userNickname);
    console.log(title);
    console.log(content);
    
    document.getElementById('diaryHiddenTitle').value = document.getElementById('diaryTitle').value;

    console.log(document.getElementById('diaryHiddenUserNickname').value);
};

