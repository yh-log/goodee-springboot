	var notice_idx = $('input[name="notice_idx"]').val();
	var crew_idx = $('input[name="crew_idx"]').val();
	console.log(notice_idx);
	
	$(document).ready(function() {
	    // 서버에서 가져온 content 값을 에디터에 삽입
	    var content = '<c:out value="${result.content}" escapeXml="false" />';
	    if (content) {
	        // summernote가 초기화된 후에만 내용을 설정
	        $('#summernote').summernote('code', content);
	    }
	    
	    var priorityValue = ${result.priority}; // 서버에서 전달된 priority 값
	    
	    if (priorityValue === 1 || priorityValue === 2 || priorityValue === 3) {
	        $('#priorityOption').css('visibility', 'visible');
	    } else {
	        $('#priorityOption').css('visibility', 'hidden');
	    }
	    
	    $('#priorityChack').on('change', function(){
			$('#priorityOption').css('visibility', 'visible');
	    });
	    
	    $('#checkReturn').on('change', function(){
			$('#priorityOption').css('visibility', 'hidden');
	    });
	});	

	var loginId = $('input[name="loginId"]').val();
	var overlayCheck = 'Y';
	var notice_idx = '';

	function sendUpdatePost(){
		if(overlayCheck === 'Y'){
			layerPopup('공지사항을 수정하시겠습니까?', '확인', '취소', updatePost, applBtn2Act);
		}else{
			layerPopup('기존 공지 순위를 변경하시겠습니까?', '확인', '취소', updatePriority, applBtn2Act);
		}
	}
	
    function updatePost() {
        var formData = new FormData($('form')[0]); 

        var content = $('#summernote').summernote('code');
        
        var priority = $('#priorityOption').val();
        priority = priority.replace(/^,|,$/g, '');
        

        formData.append('id', loginId); 
        formData.append('content', content); 
		formData.append('priority', priority); 
		
		var crew_idx = $('input[name="crew_idx"]').val(); // Hidden input에서 값 가져오기
	    var notice_idx = $('input[name="notice_idx"]').val();
		
        formData.append('crew_idx', crew_idx);
		formData.append('notice_idx', notice_idx);
		
		
        var tempDom = $('<div>').html(content);
        var imgsInEditor = [];

        tempDom.find('img').each(function () {
            var src = $(this).attr('src');
            if (src && src.includes('/photo-temp/')) {  
                var filename = src.split('/').pop();  
                imgsInEditor.push(filename);  
            }
        });

        var finalImgs = tempImg.filter(function (temp) {
            return imgsInEditor.includes(temp.img_new);  
        });

        formData.append('imgsJson', JSON.stringify(finalImgs));  

	    $.ajax({
	        type: 'POST',
	        url: '/crew/sendNoticeUpdate', 
	        data: formData,  
	        contentType: false, 
	        processData: false,  
	        enctype: 'multipart/form-data', 
	        success: function (response) {
	            if(response.success){
	            	removeAlert();
	            	layerPopup('공지사항 수정이 완료되었습니다.', '확인',false, locationHref ,locationHref);
	            }
	        },
	        error: function (e) {
	            console.log('글 전송 에러:', e);
	            removeAlert();
	        }
	    });
       
        
    }
	
	function updatePriority(){
		var priority = $('#priorityOption').val();
		var crew_idx = $('input[name="crew_idx"]').val();
		var notice_idx = notice_idx;
		
		$.ajax({
			type: 'PUT',
    		url: '/crew/noticePriorityUpdate',
    		data: {'crew_idx' : crew_idx,
    				'priority' : priority},
    		dataType: 'JSON',
    		success: function(response){
    			if(response.success){
	    			submitPost();
    			}
    			
    		},error: function(e){
    			console.log('순위 수정 중 에러 => ', e);
    		}
		});
		
	}
    
	
	
	
    
    
    $('#priorityOption').on('change', function(){
    	
    	 var priority = $('#priorityOption').val();
    	 console.log(priority);
    	 
    	 var crew_idx = $('input[name="crew_idx"]').val();
    	 console.log(crew_idx);
    	 
    	 if(priority === 'pr1' || priority === 'pr2' || priority === 'pr3'){
	    	 $.ajax({
	    		type: 'POST',
	    		url: '/crew/priorityOverlay',
	    		data: {'crew_idx' : crew_idx,
	    				'priority' : priority},
	    		dataType: 'JSON',
	    		success: function(response){
	    			console.log(response);
	    			console.log(response.notice_idx);
	    			
	    			if(response.count > 0){
	    				$('#priorityOverlay').css('visibility', 'visible');
	    				$('#priorityOverlay').html('이미 사용중인 순위입니다.');
	    				$('#priorityOverlay').css('color', '#666666');
	    				overlayCheck = 'N';
	    				notice_idx = response.notice_idx;
	    			}else{
	    				$('#priorityOverlay').css('visibility', 'visible');
	    				$('#priorityOverlay').html('사용 가능한 순위입니다.');
	    				$('#priorityOverlay').css('color', '#FF903F');
	    				overlayCheck = 'Y';
	    			}
	    			
	    		},error: function(e){
	    			console.log('중복체크 중 에러 => ', e);
	    		}
	    	 });
    	 }
    });
    
    function locationHref(){
    	location.href = '/crewNoticeDetail/'+$('input[name="notice_idx"]').val();
    }
    
	// 팝업 취소
	function applBtn2Act() {
	    removeAlert(); 
	}
	