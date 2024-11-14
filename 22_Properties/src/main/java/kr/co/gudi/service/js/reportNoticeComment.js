
	/* 신고하기 버튼 이벤트 */
	$('#notice').on('click',function(){
	
		console.log('공지댓글 신고하기');
		var unlike_id = $("input[name='unlike_id']").val();
		
		var id = $("input[name='user_id']").val();
				
		console.log('신고한', unlike_id);
		
		console.log('신고받은', id);
		
		var notice_idx = $("input[name='zeus']").val();
		
		console.log('공지번호 : ',notice_idx);
		
		var url = '/crewNoticeDetail/'+notice_idx;
			
		console.log('경로 : ',url);
		
	
		var formData = new FormData($('form')[0]); 
		
		var fileInput = $('input[type="file"]')[0]; // 파일 input에서 파일 가져오기
        if(fileInput.files.length > 0) {
            formData.append('report_img', fileInput.files[0]); // 파일 데이터 추가
            console.log(fileInput);
        }
		
		
		var subject = $("input[name='subject']").text();
        var content = $("input[name='content']").text();
        
		var unlike_id = $("input[name='unlike_id']").val();
		
		var id = $("input[name='user_id']").val();
		
		
		
		formData.append('url',url);
		
        formData.append('id',id);  
        formData.append('unlike_id',unlike_id);   
		formData.append('subject',subject);
		formData.append('content',content);
		
		
		$.ajax({
            type: 'POST',
            url: '/reportNoticeComment',             // 서버에 전송할 URL
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
		
	
	
	});
	

	
	
	
	
	
	
	/* 취소하기 버튼 이벤트 */
	$('#cancle').on('click',function(){
		console.log('취소하기 버튼');
		document.getElementById("reportPopup").style.display = "none";
	});
