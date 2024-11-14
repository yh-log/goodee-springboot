
	/* 신고하기 버튼 이벤트 */
	$('#reportBoard').on('click',function(){
		console.log('신고하기 버튼');
		var unlike_id = $('.user').text();
		var userId = $("input[name='reput']").val();
		var code_name = $("input[name='code_name']").val();
		var board_idx = $("input[name='faker']").val();
		console.log('신고한', unlike_id);
		console.log('신고받은', userId);
		console.log('코드네임', code_name);
		console.log('설마이게...?', board_idx);
		
			
		reportBoard();
	});
	
	function reportBoard(){
		
		var formData = new FormData($('form')[0]); 
		
		var fileInput = $('input[type="file"]')[0]; // 파일 input에서 파일 가져오기
        if(fileInput.files.length > 0) {
            formData.append('report_img', fileInput.files[0]); // 파일 데이터 추가
            console.log(fileInput);
        }
		
		var code_name = $("input[name='code_name']").val();
		
		var subject = $("input[name='subject']").text();
        var content = $("input[name='content']").text();
        var unlike_id = $('.user').text();
		var userId = $("input[name='reput']").val();
		var board_idx = $("input[name='faker']").val();
		var url = '/runBoardDetail/'+board_idx;
		var path = '/freeBoardDetail/'+board_idx;
		
		console.log('경로 : ',url);
		
		console.log('제목', subject);
		console.log('내용', content);
		
		if(code_name == 'B100'){
			formData.append('url',url);
		}else{
			formData.append('url',path);
		}
         
         
          
        formData.append('id',userId);  
        formData.append('unlike_id',unlike_id);   
		formData.append('subject',subject);
		formData.append('content',content);
		
		$.ajax({
            type: 'POST',
            url: '/reportForm',             // 서버에 전송할 URL
            data: formData,               // formData 객체 전송
            contentType: false,          // formData 사용 시 false로 설정
            processData: false,         // formData 사용 시 false로 설정
            enctype: 'multipart/form-data',     // multipart/form-data 사용
            success: function (response) {
                console.log('글 전송 성공:', response);
                document.getElementById("reportPopup").style.display = "none";
            },
            error: function (e) {
                console.log('신고 전송 에러:', e);
            }
        });
		
	}
	
	
	/* 취소하기 버튼 이벤트 */
	$('#cancle').on('click',function(){
		console.log('취소하기 버튼');
		document.getElementById("reportPopup").style.display = "none";
	});
