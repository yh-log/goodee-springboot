
	var notice_idx = $('input[name="notice_idx"]').val();
	var loginId = $('input[name="loginId"]').val();
	var nickname = $('input[name="userName"]').val();
	var admin = $('input[name="admin"]').val();
	var icon_image = $('input[name="icon_image"]').val();
	
	if (loginId === null || loginId === '') {
	    loginId = 'a';
	}
	var crew_idx = '';

	$.ajax({
		type: 'POST',
		url: '/crew/noticeDetail',
		data: {'notice_idx' : notice_idx},
		dataType: 'JSON',
		success: function(response){
			console.log('콘솔아 등장 해줘');
			console.log('데이터 잘 받아옴 => ', response);
			console.log('닉네임 잘 받아옴 => ', response.nickname);
			
			console.log('아이콘 받아옴? =>',icon_image);
			
			var result = response.result;
			crew_idx = result.crew_idx;
			//var nickName = response.nickname
			
			$('#subject').html(result.subject);
			
			if(result.image != null && result.image != ''){
				$('#profileImg').attr('src', '/photo/'+result.image);
				
			}
			if(result.icon_image != null && result.icon_image !== ''){
				$('.profile-box2').css({
				    'background': 'url(/resources/img/icon/' + result.icon_image + ') center center / 100% 100% no-repeat'
				});
			}

 			$('#leaderId').html('<a class="user" style="cursor: pointer; color: #333"  data-id="' + result.id + '">' + result.nickname + '</a>'); 
			$('#content').html(result.content);
			$('#hit').html(result.hit);
			$('#create_date').html(result.create_date);
			
			if(admin == 'Y'){
				$('#name').html('<div class="profile-area"><div class="profile-img" style="background: url(/resources/img/common/admin_profile.png) center center / cover no-repeat;"></div></div>'+loginId);
			}else{
				$('#name').html('<div class="profile-area"><div class="profile-img" style="background: url(/resources/img/common/profile.png) center center / cover no-repeat;"></div><div class="profile-box" style="background: url(/resources/img/icon/'+icon_image+') center center / 100% 100% no-repeat;"></div></div>'+nickname);
			}
			
			if(loginId === result.id){
				$('.btn01-m').css('visibility', 'visible');
				$('.btn03-m').css('visibility', 'visible');
			}
			
			console.log('loginId : id', loginId, result.id);
		},error: function(e){
			console.log('받아오던 중 에러 => ', e);
		}
	});
	
	function crewNoticeDelete(){
		
		console.log('삭제 실행');
		
		$.ajax({
			type: 'DELETE',
			url: '/crew/noticeDelete',
			data: {'notice_idx' : notice_idx},
			dataType: 'JSON',
			success: function(response){
				if(response.success){
					removeAlert();
	            	layerPopup('공지사항이 삭제되었습니다.', '확인',false, locationHref ,locationHref);
				}
			},error: function(e){
				removeAlert();
				console.log('삭제 중 에러 => ', e);
			}
		});
	}
	
	function locationHref(){
		location.href="/crewNoticeList/"+crew_idx;
	}
	
	function crewNoticeListView(){
		location.href='/crewNoticeList/'+crew_idx;
	}

	// 팝업 취소
	function applBtn2Act() {
	    removeAlert(); 
	}
			
	// 클릭시 운동프로필 레이어 팝업
	$(document).on('click','.user',function(){
	    var toUserId = $(this).data('id');
	   // console.log('toUserId',toUserId);
	    openProfile(toUserId);
	});
	
	
	// 운동프로필 레이어 팝업 열기
	function openProfile(toUserId){
		var modal = document.getElementById("profilePopup");
	    var PopupBody = document.getElementById("PopupBody");
		
	    // AJAX 요청
	    var xhr = new XMLHttpRequest();
	    xhr.open("GET", "/mate/"+toUserId, true);
	    xhr.onreadystatechange = function() {
	        if (xhr.readyState === 4 && xhr.status === 200) {
	            PopupBody.innerHTML = xhr.responseText; // 응답을 모달에 넣기
	            modal.style.display = "block"; // 모달 열기
	            
	         	// JS 파일을 동적으로 로드
	            var script = document.createElement('script');
	            script.src = '/resources/js/profileDetail.js'; 
	            document.body.appendChild(script);
	        }
	    };
	    xhr.send();
	}
	
	// 팝업 닫기
	document.getElementsByClassName("close")[0].onclick = function() {
	    document.getElementById("profilePopup").style.display = "none";
	};	
	

	
	function toggleActions(comment_idx) {
	    $('#actions-' + comment_idx).toggle(); // 버튼 표시/숨김 토글
	}
	
	function comment() {
		
		var content = $('input[class="tex"]').val();
		console.log('댓굴 내용 : ',content);
		var notice_idx = $('input[name="notice_idx"]').val();
		var nickname = $('#name').text();
		
		$.ajax({
			type:'POST',
			url:'/noticeComment',
			data:{'notice_idx':notice_idx, 
				'content':content, 
				'nickname':nickname},
			dataType:'JSON',
			success:function(data){
				console.log('댓글 등록',data);
				commentCall();
			},
			error:function(e){
				console.log('댓글 등록 오류',e);
			}
		})
		
	}
	
	function update(comment_idx) {
		
		console.log('댓글no : ',comment_idx);
		
		var commentContent = $("#sort-update" + comment_idx + " .coco").text();
		console.log('눌렀을때 나와?',commentContent);
		var commentAuthor = $("#sort-update" + comment_idx + " .nick").text();
		var commentDate = $("#sort-update" + comment_idx + " .date").text();

			 // 댓글 내용, 작성자, 날짜를 포함한 편집 필드 생성
		var editField = '<div class="edit-container">';
			  editField += '<input type="text" class="tex" value="' + commentContent + '" id="editContent' + comment_idx + '" style="width:800px;" />';
			  editField += '<button class="btn01-s" onclick="saveComment(' + comment_idx + ')">저장</button>';
			  editField += '<button class="btn03-s" onclick="commentCall();">취소</button>';
			  editField += '</div>';

	    // 기존 댓글을 숨기고 편집 필드를 삽입
	    $("#sort-update" + comment_idx + " .coco").html(editField);
				
	}
	
	function saveComment(comment_idx) {
		 // 수정된 댓글 내용 가져오기
		 console.log('선택한 댓글번호 : ',comment_idx);
	    var updatedContent = $("#editContent" + comment_idx).val();
		 console.log('수정내용 : ',updatedContent);
		 var nickname = $('.nick').text();

	    // AJAX 요청으로 수정된 내용 서버에 전송
	    $.ajax({
	        type: 'POST',
	        url: '/updateNoticeComment',  // 서버의 댓글 수정 처리 경로
	        data: JSON.stringify({ comment_idx: comment_idx, content: updatedContent ,nickname:nickname}),
	        contentType: 'application/json',
	        dataType: 'JSON',
	        success: function(data) {
	            if (data.success) {
	            	// 불러오기
	            	commentCall();
	            }	               
	        },
	        error: function(error) {
	            console.log("댓글 수정 오류:", error);
	            alert("오류가 발생했습니다.");
	        }
	    });
	}
	
	
	function del(comment_idx) {
		
		console.log('크루댓글삭제버튼 : ',comment_idx);
		
		$.ajax({
	        type: 'POST',
	        url: '/noticeCommentDel/'+comment_idx,  // 서버의 댓글 삭제 처리 경로
	        contentType: 'application/json',
	        dataType: 'json',
	        success: function(data) {
	            if (data.success) {
	                // 삭제 성공 시 댓글을 "삭제된 댓글입니다."로 업데이트
	                $("#sort-update" + comment_idx + " .coco").text("(삭제된 댓글입니다.)");
	                // 수정, 삭제, 신고 버튼 숨기기
	                $("#sort-update" + comment_idx + " .ard").hide();
	                
	            } 
	        },
	        error: function(error) {
	            console.log("댓글 삭제 오류:", error);
	            alert("오류가 발생했습니다.");
	        }
	    });
	}
	
	// 크루 댓글 신고
	function report(comment_idx) {
	 		layerPopup('정말 신고 하시겠습니까?','신고','취소' ,function (){secondBtn4Act(comment_idx)} , applBtn2Act);
	}
	
	
	
	
	function secondBtn4Act(comment_idx) {
 		// 두번째팝업 4번버튼 클릭시 수행할 내용
 	    console.log('두번째팝업 4번 버튼 동작');
 	   
 	    console.log('동작시 가ㅣ고외?',comment_idx);
 	   	notice(comment_idx);
 	    removeAlert();
 	}
	
	
	
	
	function notice(comment_idx){
		
		var modal = document.getElementById("reportPopup");
	    var PopupBody = document.getElementById("reportPopupBody");
	    var userId = loginId;
	    
		console.log('가지고와번호?',comment_idx);
		console.log('가지고와? 아이디',userId);
	    // AJAX 요청 데이터 넣을때 해당 게시판 idx 값 넣기!!!!
	    var xhr = new XMLHttpRequest();
	    xhr.open("GET", "/notice/"+comment_idx, true);
	    xhr.onreadystatechange = function() {
	        if (xhr.readyState === 4 && xhr.status === 200) {
	            PopupBody.innerHTML = xhr.responseText; // 응답을 모달에 넣기
	            modal.style.display = "block"; // 모달 열기
	            
	         	// JS 파일을 동적으로 로드
	         	
	            var script = document.createElement('script');
	            script.src = '/resources/js/reportNoticeComment.js'; 
	            document.body.appendChild(script);
	            
	        }
	    };
	    xhr.send();
	}
	
	// 팝업 닫기
	$(document).on('click','#profilePopup .close',function(){
       document.getElementById("profilePopup").style.display = "none";
   });
	

