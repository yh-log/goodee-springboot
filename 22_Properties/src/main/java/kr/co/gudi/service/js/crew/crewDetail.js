
	var loginId = $('input[name="loginId"]').val();
	var code_name = '';
	var crewLeader = '';
	var application = '';
	var crewone = [];
	var is_recruit = '';
	var likeCrew = 'N';
	var loginCheckLeader = '';
    var crew_idx = $('input[name="crew_idx"]').val();
	

	$(document).ready(function () {
	    crewMemberList();
	    crewDetail();
	    likeList();
	});
	
	function crewDetail() {
	    $.ajax({
	        type: 'POST',
	        url: '/crew/detail',
	        data: { 'crew_idx': crew_idx },
	        dataType: 'JSON',
	        success: function (response) {
	            if (response.success) {
	                var result = response.result;
	                
	                if(result.is_recruit === 'N'){
	                	is_recruit = result.is_recruit;
	                	$('#crew-btn-01').html('크루 모집완료');
	                	$('#crew-btn-01').css({'border' : '1px solid var(--btn-bd-g)', 'color' : 'var(--btn-bd-g)', 'background' : '#fff'});
	                }
	                
	                // 이미지 업데이트
	                if (result.img_new) {
	                    $('#crew-img').attr('src', '/photo/' + result.img_new);
	                }
	                var day = result.days; // 예: "mon"
	
	             // replace를 사용해서 변환
	             day = day.replace('mon', '월')
	                      .replace('tue', '화')
	                      .replace('wen', '수')
	                      .replace('thu', '목')
	                      .replace('fri', '금')
	                      .replace('sat', '토')
	                      .replace('sun', '일');
	
	                // 크루명, 소개, 안내사항 등 업데이트
	                $('#crew-name').text(result.crew_name);
	                $('#crew-content').html(result.content);
	                $('#crew-address').text(result.address);
	                $('#crew-member').text(result.member);
	                $('#crew-days').text(day);
	                $('#crew-minute').text(result.minute);
	                $('#crew-distance').text(result.distance);
	                $('#crew-location').text(result.shortsido + ' ' + result.sigungu);
	            }
	        },
	        error: function (e) {
	            console.log('에러 발생 => ', e);
	        }
	    });
	}
	    
	    function crewMemberList(){
			console.log('크루 회원 리스트 요청');
	        $.ajax({
	            type: 'POST',
	            url: '/crew/memberList',
	            data: { 'crew_idx': crew_idx },
	            dataType: 'JSON',
	            success: function (response) {
	                console.log('회원 데이터 받아옴 => ', response);
	                
	                console.log('신청 회원 리스트 =>', response.application);
	                
	                if (response.success) {
	                	
	                    var result = response.result;
	                    var application = response.application;
	                    
	                    var profileImg = '';
	                    
	                    var genderImg = '';
	                    var content = '';

	                    result.forEach(function(item, idx){
	                    	
	                    	if(item.image){
		                    	profileImg = '<img alt="profileImg" src="/photo/' + item.image + '" onerror="this.src=\'/resources/img/common/profile.png\'" id="profileImg" width="32px" />';
		                    }else{
		                    	profileImg = '<img alt="profileImg" src="/resources/img/common/profile.png" onerror="this.src=\'/resources/img/common/profile.png\'" id="profileImg" width="32px" />';
		                    }
	                    	
	                    	if(item.is_leader === 'Y' && item.icon_image != null && item.icon_image !== ''){
	            				$('.profile-box2').css({
	            				    'background': 'url(/resources/img/icon/' + item.icon_image + ') center center / 100% 100% no-repeat'
	            				});
	            			}
	                    	
	                    	// 성별 체크 -> 이미지 변환
	                    	if(item.gender === '남'){
	                    		genderImg = '<img src="/resources/img/common/ico_male.png" width="9px" class="genderImg"/>';
	                    	}else{
	                    		genderImg = '<img src="/resources/img/common/ico_female.png" width="9px" class="genderImg"/>';
	                    	}
	                    	
	                    	// 크루장 체크 -> 회원 리스트 노출 내용
		                    if(item.is_leader === 'Y'){
		                    	crewLeader = item.id;
		                    	console.log('반복문 안에서 =>',crewLeader);
		                    	$('#leaderprofile').html('<a class="user" style="cursor: pointer;"  data-id="' + item.id + '"><div class="leaderjb">' + profileImg + ' ' + item.nickname + ' / ' + genderImg + ' / ' + '크루장' + '</div></a>');
		                    }else{
		                    	crewone.push(item.id); // 배열에 크루원 id 넣기
		                    	content += '<a class="user" style="cursor: pointer;"  data-id="' + item.id + '"><div class="testeee">' + profileImg + ' ' + item.nickname + ' / ' + genderImg + ' / ' + '크루원';
		                    	content += '<div class="profile-box23" style="background: url(/resources/img/icon/' + item.icon_image + ') center center / 100% 100% no-repeat;"></div>';
		                    	content += '</div></a>';
		                    	
		                    
		                    }
		                    
		                    $('#crew-member-profile').html(content);
							console.log(item);
	                    });
	                    
	                    // 크루장 체크 => 보여지는 내용 변환
	                    if(loginId === null || loginId === ''){
	                    	$('#crewLeaderCheck').html('1:1채팅');
	                    	loginCheckLeader = 'N';
	                    	
	                    }else{
	                    	if(loginId === crewLeader){
	                    		console.log('크루장임');
						    	$('.btn03-s1').css('visibility', 'visible');
	                    		$('#crewLeaderCheck').html('크루관리');
	                    		loginCheckLeader = 'L';
	                    	}else{
	                    		console.log('크루원 또는 일반 회원임');
						    	console.log('loginId : crewLeader', loginId, ':', crewLeader);
	                    		$('#crewLeaderCheck').html('1:1채팅');
	                    		loginCheckLeader = 'C';
	                    	}
	                    }
					    
					    // 신청 버튼 체크 함수
					    crewApplication(application, result);
	                    
					    // 로그인 안했거나 크루원이 아니면
					    $('.crewAccess').click(function() {
						    if (loginId != null && loginId !== '') {
						        // loginId가 result.id와 다르고, 또한 crewLeader와 다를 때만 접근 차단
						        if (crewone.includes(loginId) || loginId == crewLeader) {
						            location.href = "/crewNoticeList/" + crew_idx;
						        } else if($('input[name="adminYn"]').val() === 'Y'){
						        	location.href = "/crewNoticeList/" + crew_idx;
						        }else {
						            // 접근 차단
						            $('.crewAccess').attr('href', 'javascript:void(0);');
						            console.log('로그인 했지만 크루원이나 크루장만 접근 가능합니다.');
						            layerPopup('크루원만 접근 가능합니다.', '확인', false,applBtn2Act ,applBtn2Act);
						        }
						    } else {
						        // 로그인하지 않은 경우
						        $('.crewAccess').attr('href', 'javascript:void(0);');
						        console.log('로그인 안했거나 크루원 아님');
						        layerPopup('크루원만 접근 가능합니다.', '확인', false,applBtn2Act ,applBtn2Act);
						    }
					    });
					    
					    $('#crewChatLocation').click(function() {
					        if (loginId != null && loginId !== '') {
					            // loginId가 result.id와 다르고, 또한 crewLeader와 다를 때만 접근 차단
					            if (crewone.includes(loginId) || loginId === crewLeader) {
					                	openCrewChat(); // 채팅 열기
					            } else {
					                // 접근 차단
					                console.log('로그인 했지만 크루원이나 크루장만 접근 가능합니다.');
					                layerPopup('크루원만 접근 가능합니다.', '확인', false, applBtn2Act, applBtn2Act);
					                return false; // 이벤트 전파 차단
					            }
					        } else {
					            // 로그인하지 않은 경우
					            console.log('로그인 안했거나 크루원 아님');
					            layerPopup('크루원만 접근 가능합니다.', '확인', false, applBtn2Act, applBtn2Act);
					            return false; // 이벤트 전파 차단
					        }
					    });
					    
	                }
	            },
	            error: function (e) {
	                console.log('에러 발생 => ', e);
	            }
	        });
	    }
	    
	  
	    
	    
	function likeList(){
		$.ajax({
			type: 'POST',
			url: '/crew/likeIs',
			data: {'loginId': loginId,
					'crew_idx' : crew_idx},
			dateType: 'JSON',
			success: function(response){
				
				if(response.success){
					likeCrew = 'Y';
	    			console.log('좋아요 리스트에서 회원 있음');
	    			$('#likeImg').attr('src', '/resources/img/common/ico_heart_act.png');
				}else{
					likeCrew = 'N';
					console.log('좋아요 리스트에서 회원 없음');
					$('#likeImg').attr('src', '/resources/img/common/ico_heart_no_act.png');
				}
				// DB에서 찾아온 count가 1이면 하트로 0이면 빈 하트로
			},error: function(e){
				console.log('좋아요 찾다가 =>', e);
			}
		});
	}

	function crewApplication(application, result){
		
		// 신청 리스트에서 로그인 ID와 일치하는 신청 내역이 있는지 확인
        var isApplied = application.some(app => app.id === loginId);
		 
		 if(is_recruit != 'N'){
	        // 로그인 여부에 따라 버튼 텍스트 변경
	        if (loginId == null || loginId === '') { // 로그인 x  => 완료
		        $('#crew-btn-01').removeAttr('onclick');
	            $('#crew-btn-01').html('러닝크루 신청하기');
	        	$('#crew-btn-01').click(function(){
	        		layerPopup('로그인이 필요한 서비스입니다.', '로그인 하기', '취소',loginPageLocation ,applBtn2Act);
	        		event.preventDefault(); 
	        	});
	        	
	        
	        } else if(loginId != null && loginId === crewLeader){ // 크루장 => 완료
	        	$('#crew-btn-01').html('러닝크루 신청하기');
	        	
	        	$('#crew-btn-01').click(function(){
	        		removeAlert();
		        	layerPopup('크루장은 신청이 불가능합니다.', '확인', false,applBtn2Act ,applBtn2Act);
	        	});
	        	
	        	
	        } else if (crewone.includes(loginId)) { // 로그인 o + 크루원 => 완료
	            $('#crew-btn-01').html('크루 탈퇴하기');
	            $('#crew-btn-01').css({'border' : '1px solid var(--main-color)', 'color' : 'var(--main-color)', 'background' : '#fff'});
	            $('#crew-btn-01').click(function(){
	            	removeAlert();
		        	layerPopup('크루에서 탈퇴하시겠습니까?', '확인', '취소' ,crewMemberUpdate ,applBtn2Act);
		        	code_name = 'C105';
	        	});
	            
	            
	        } else if (isApplied) { // 로그인 o, 신청자 => 완료
	            $('#crew-btn-01').html('신청 취소하기');
	        	$('#crew-btn-01').css({'border' : '1px solid var(--main-color)', 'color' : 'var(--main-color)', 'background' : '#fff'});
	        	$('#crew-btn-01').click(function(){
		        	removeAlert();
		        	layerPopup('가입 신청을 취소하시겠습니까?', '확인', '취소' ,crewMemberUpdate ,applBtn2Act);
		        	code_name = 'C104';
	        	});
	        	
	        	
	        } else { // 로그인 o, 크루원 x => 완료
	            $('#crew-btn-01').html('러닝크루 신청하기');
	        	$('#crew-btn-01').click(function(){
	        		removeAlert();
		        	layerPopup('크루를 신청하시겠습니까?', '확인', '취소' ,crewMemberUpdate ,applBtn2Act);
		        	code_name = 'C100';
	        	});
	        }
			 
		 }
		 
		
		
	}
	
	// 팝업 취소
	function applBtn2Act() {
	    removeAlert(); 
	}
    
	// 크루 버튼 클릭 시 신청, 취소, 탈퇴 요청 함수
	function crewMemberUpdate(){
		loading();
		$.ajax({
			type: 'POST',
			url: '/crew/applicationWrite',
			data: {'loginId' : loginId,
					'crew_idx' : crew_idx,
					'code_name' : code_name},
			dataType: 'JSON',
			success: function(response){
				console.log('성공?????');
				
				if(response.success){
					removeAlert();
					layerPopup(response.msg + ' 완료되었습니다.', '확인', '', function() {
	                    applBtn2Act();
	                    loadingComplete();
	                    location.reload(); // 페이지 새로고침
	                }, function() {
	                    applBtn2Act();
	                    loadingComplete();
	                    location.reload(); // 페이지 새로고침
	                });
	            } else {
	                removeAlert();
	                layerPopup(response.msg + ' 미완료되었습니다.', '확인', false, applBtn2Act, applBtn2Act);
	            	loadingComplete();
	            }
				
			},error: function(e){
				console.log('버튼 요청 시 에러남 =>', e);
				loadingComplete();
			}
		});
		
	}

	function like(){
		console.log('좋아요 버튼');
		
		if(loginId != null && loginId != ''){
			$.ajax({
				type: 'POST',
				url: '/crew/likeRequest',
				data: {'loginId': loginId,
						'crew_idx' : crew_idx,
						'likeCrew' : likeCrew},
				dataType: 'JSON',
				success: function(response){
					console.log('좋아요 눌림');
					console.log(response.like);
					if(response.success){
						likeList();
					}
					
					
					
				},error: function(e){
					console.log('좋아요 에러 => ', e);
				}
			});
			
		}else{
			layerPopup('로그인이 필요한 서비스입니다.', '로그인 하기', '취소',loginPageLocation ,applBtn2Act);
		}
	}
	
	function crewChat_Admin(){
		if(loginCheckLeader === 'N'){
			layerPopup('로그인이 필요한 서비스입니다.', '로그인 하기', '취소',loginPageLocation ,applBtn2Act);
		}else if(loginCheckLeader === 'C'){
			
			//location.href='#';
			// 채팅방 열기
			openCrewLeaderChat();

		}else{
			location.href="/crewManagerList/"+$('input[name="crew_idx"]').val();
			console.log('크루장 관리 페이지 이동');
		}
	}
	
	
	function loginPageLocation(){
		location.href='/loginView'; // 로그인 페이지로 수정 필요
	}
	
	function crewUpdate(){
		location.href="/crewUpdate/"+ $('input[name="crew_idx"]').val(); // 크루수정페이지로 수정 필요
	}
	
	
    function crewDelete(){
    	
    	console.log('delete?');
    	loading();
    	$.ajax({
    		type: 'DELETE',
    		url: '/crew/delete',
    		data: {'crew_idx' : $('input[name="crew_idx"]').val()},
    		dataType: 'JSON',
    		success: function(response){
    			if(response.success){
					removeAlert();
					layerPopup('완료되었습니다.', '확인', '', function() {
						console.log('삭제성공');
	                    applBtn2Act();
	                    loadingComplete();
	                    location.href = '/crewList' // 크루 리스트 페이지로 이동
	                }, function() {
	                    applBtn2Act();
	                    loadingComplete();
	                    location.href = '/loginView' 
	                });
    			}else{
    				alert('크루 삭제 실패');
    				loadingComplete();
    			}
    		},error: function(e){
    			console.log('삭제 에러 => ',e);
    			loadingComplete();
    		}
    		
    	});
    	
    }
    
	// 클릭시 운동프로필 레이어 팝업
	$(document).on('click','.user',function(){
	    var toUserId = $(this).data('id');
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
	$(document).on('click','#profilePopup .close',function(){
       document.getElementById("profilePopup").style.display = "none";
   });	
	
