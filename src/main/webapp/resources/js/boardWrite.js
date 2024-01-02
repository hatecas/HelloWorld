
$(document).ready(function() {
		
		// 여러파일 업로드
		function multiFiles(input) {
			  var previewContainer = document.getElementById('preview-container');

			  if (input && input.length) {
			    for (var i = 0; i < input.length; i++) {
			      (function(file) {
			        var reader = new FileReader();
			        
			        reader.onload = function(e) {
			       	  var newDiv = document.createElement("div");
					      newDiv.className = "image-container";
					      newDiv.style.position = "relative";
			          var newDiv2 = document.createElement("div");
			          newDiv2.className = "name-container";
								
			          // 파일이미지
			          var newImg = document.createElement("img");
			          newImg.src = e.target.result;
			          newImg.style.width = "50%"; // 이미지 크기 설정
			          newImg.style.height = "auto";
			          newImg.style.marginBottom = "20px";

			          // 파일 이름을 표시하는 텍스트 노드 생성
			          var imageName = document.createTextNode(file.name);
			          
			          // 취소버튼
			          var newBtn = document.createElement("button");
			          newBtn.className="removeBtn";
			          newBtn.style.position = "absolute";
			          newBtn.style.top = "0";
			          newBtn.style.right = "0";
			          newBtn.onclick = function() {
						if(confirm("선택된 사진을 삭제하시겠습니까?")==true){
							previewContainer.removeChild(newDiv);
							 removeFile(document.querySelector('.fileUpload'));
						}else{
							return false;
						}
		          };

		          // div 안에 img와 텍스트 노드 추가
		          newDiv.appendChild(newDiv2);
		          newDiv.appendChild(newBtn);
		          newDiv2.appendChild(imageName); 
		          newDiv.appendChild(newImg);

		          // 생성한 div를 preview-container에 추가
		          previewContainer.appendChild(newDiv);
		        };

		        reader.readAsDataURL(file); // 파일 읽기 시작
		      })(input[i]);
		    }
		  }
		}

		var oEditors=[];
		
		nhn.husky.EZCreator.createInIFrame({
			oAppRef : oEditors,
			elPlaceHolder : "txtContent",
			sSkinURI : "../../../../resources/smarteditor2/SmartEditor2Skin.html",
			fCreator : "createSEditor2"
		});
});