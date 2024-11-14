
	
	


	/* 메이트기능 활성화 버튼 이벤트 */
	$('.btn-mate-on').on('click',function(){
		layerPopup('러닝메이트를 기능을 활성화 하시겠습니까?','활성화','취소', mateOnBtn1Act, mateOnBtn2Act);
	});
	
	function mateOnBtn1Act() {
		$.ajax({
	    	type:'POST',
			url:'/mateOn',
			data:{},
			dataType:'JSON',
			success:function(data){
				// 신청완료 팝업
				if(data.success){
					removeAlert(); // 기존 confirmBox 닫기
			    	layerPopup('러닝메이트 기능이 활성화되었습니다.', '확인', false, applBtn2Act, applBtn2Act);
					updateCont();
				}else{
					removeAlert();  // 기존 confirmBox 닫기
					layerPopup('러닝메이트 기능 활성화에 실패했습니다.', '재시도', '확인', mateOnBtn1Act, applBtn2Act);
				}
			},
			error:function(e){
				removeAlert();  // 기존 confirmBox 닫기
				layerPopup('러닝메이트 기능 활성화에 실패했습니다.', '재시도', '확인', mateOnBtn1Act, applBtn2Act);
			}
	    });
	}
	
	function mateOnBtn2Act() {
		removeAlert(); // 기존 confirmBox 닫기
	}
	
	
	/* 메이트 신청하기 버튼 이벤트 */
	$('.btn-mate-appl').on('click',function(){
		var userName = $('.user-info .user-name').text();
		console.log("userName@",userName);
		layerPopup(userName+'님께 러닝메이트를 신청하시겠습니까?','신청','취소' ,applBtn1Act, applBtn2Act);
		
	});
	
	function applBtn1Act() {
	    // 1번버튼 클릭시 수행할 내용
	    $.ajax({
	    	type:'POST',
			url:'/mateAppliaction/',
			data:{
				toUserId: $('input[name="id"]')[0].defaultValue
			},
			dataType:'JSON',
			success:function(data){
				// 신청완료 팝업
				if(data.success){
					
					removeAlert(); // 기존 confirmBox 닫기
			    	layerPopup('운동메이트 신청이 완료되었습니다.', '확인','내 운동메이트로 이동',appl2Btn1Act , appl2Btn2Act);
					updateCont();
				}else{
					removeAlert();  // 기존 confirmBox 닫기
					layerPopup('운동메이트 신청 실패하였습니다.', '재신청','취소',applBtn1Act , applBtn2Act);
				}
			},
			error:function(e){
				removeAlert();  // 기존 confirmBox 닫기
				layerPopup('운동메이트 신청 실패하였습니다.', '재신청','취소',applBtn1Act , applBtn2Act);
			}
	    });
	    
	}
	
	function applBtn2Act() {
	    // 취소 버튼 클릭시
	    removeAlert(); // 기존 confirmBox 닫기
	}
	
	function appl2Btn1Act() {
	    // 신청완료 팝업 - 확인 버튼 클릭시
	     removeAlert(); // 기존 confirmBox 닫기
	}
	function appl2Btn2Act() {
	    // 신청완료 팝업 - 내 운동메이트로 이동 버튼 클릭시
	    removeAlert();
	    location.href='/myMateListView'; 
	}
	
	// 차단하기 버튼 클릭시
	function blockBtnAct(){
		$.ajax({
			type:'POST',
			url:'/mateBlock',
			data:{
				toUserId: $('input[name="id"]')[0].defaultValue
			},
			dataType:'JSON',
			success:function(data){
				if(data.success){				
					removeAlert();
					layerPopup('차단 하였습니다.', '확인',false,cancleBtnAct,cancleBtnAct);
					updateCont();
				}else{
					removeAlert();
					layerPopup('차단 실패하였습니다.', '차단 재시도','취소',blockBtnAct,cancleBtnAct);
				}
			},
			error:function(e){
			}
		});
	}
	
	// 차단하기 취소 버튼 클릭시
	function cancleBtnAct(){
		 removeAlert();
	}
	
	// 차단 해제하기 버튼 클릭시
	function unblockBtnAct(){
		$.ajax({
			type:'POST',
			url:'/mateUnblock/'+ $('input[name="id"]')[0].defaultValue,
			dataType:'JSON',
			success:function(data){
				if(data.success){		
					removeAlert();
					layerPopup('차단이 해제되었습니다.', '확인',false,cancleBtnAct,cancleBtnAct);
					updateCont();
				}else{
					removeAlert();
					layerPopup('차단해제 실패하였습니다.', '해제 재시도','취소',unblockBtnAct,cancleBtnAct);
				}
			},
			error:function(e){
			}
		});
	}
	
	// 좋아요 기능
	function like(){
		$.ajax({
			type:'POST',
			url:'/toggleLike',
			data:{
				toUserId: $('input[name="id"]')[0].defaultValue
			},
			dataType:'JSON',
			success:function(data){
				if(data.isLiked){					
					$('.btn-like img').attr('src','/resources/img/common/ico_heart_act.png');
				}else{
					$('.btn-like img').attr('src','/resources/img/common/ico_heart_no_act.png');					
				}
			},
			error:function(e){
				console.log(e);
			}
		});
	}
	
	
	//ajax 재요청
	function updateCont(){
		// AJAX 요청
	    var xhr = new XMLHttpRequest();
	    xhr.open("GET", "/mate/"+$('input[name="id"]')[0].defaultValue, true);
	    xhr.onreadystatechange = function() {
	        if (xhr.readyState === 4 && xhr.status === 200) {
	            PopupBody.innerHTML = xhr.responseText; // 응답을 모달에 넣기
	            
	        }
	    };
	    xhr.send();
	}
	

	

	
	// 채팅방 열기
	function chat(id,unlikeId){
		$.ajax({
			type:'GET',
			url:'/chat/'+id+'/'+unlikeId,
			data:{},
			dataType:'JSON',
			success:function(data){
				console.log(data.roomNum);
				openChat(data.roomNum);
			},
			error:function(e){
				console.log(e);
			}
		});
	}
	
	function openChat(roomNum) {
	    // 새 창의 URL
	    var url = '/chat/'+roomNum;
	
	    // 새 창의 크기와 위치 설정
	    var width = 400;
	    var height = 700;
	    var left = (screen.width - width) / 2;
	    var top = (screen.height - height) / 2;
	
	    // 새 창을 열고, 크기와 위치 설정
	    window.open(url, '_blank', `width=${width},height=${height},left=${left},top=${top},resizable=no,scrollbars=no,status=no,menubar=no,location=no`);
	}

