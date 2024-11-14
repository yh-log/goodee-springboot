
	var loginId = $('input[name="loginId"]').val();
	var crewLeader = '';
	var crew_idx = $('input[name="crew_idx"]').val();
	var crewone = [];
	var leaderId = $('input[name="leaderId"]').val();
	var firstPage = 1;
	
	$(document).ready(function(){
		if(leaderId !== loginId){
			alert('크루장만 접근 가능합니다');
			location.href='/';
		}else{
			crewDetail(); 
	   		crewMemberList();
			
		}
	
	}); 

	
	function crewMemberList() {
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
	                
	                crewApplicationMember(application);

	                // 프로필 이미지 기본값 설정 (추가 조건이 있으면 적용 가능)
	                var profileImg = '';
	                var genderImg = '';
	                var content = '';
	                var iconBox = '';

	                result.forEach(function(item, idx) {
	                    // 성별에 따른 이미지 설정
	                    genderImg = item.gender === '남' 
	                        ? '<img src="/resources/img/common/ico_male.png" width="9px" class="genderImg"/>'
	                        : '<img src="/resources/img/common/ico_female.png" width="9px" class="genderImg"/>';

	                    
	                    if(item.image){
	                    	profileImg = '<img alt="profileImg" src="/photo/' + item.image + '" onerror="this.src=\'/resources/img/common/profile.png\'" class="profileImg" width="32px" />';
	                    }else{
	                    	profileImg = '<img alt="profileImg" src="/resources/img/common/profile.png" onerror="this.src=\'/resources/img/common/profile.png\'" class="profileImg" width="32px" />';
	                    }    
	                        
	                        
	                    var member_icon = '';
	                    if(item.is_leader === 'Y' && item.icon_image != null && item.icon_image !== ''){
            				iconBox = '<div class="profile-box2" style="background: url(/resources/img/icon/'+item.icon_image+') center center / 100% 100% no-repeat;"></div>';
            			}else if(item.icon_image != null && item.icon_image !== ''){
							member_icon = '<div class="profile-box3" style="background: url(/resources/img/icon/'+item.icon_image+') center center / 100% 100% no-repeat;"></div>';
						}
	                        
	                        
	                    // 크루장 체크
	                    if (item.is_leader === 'Y') {
	                        crewLeader = item.id;
	                        /* console.log('반복문 안에서 리더 id 체크 =>', crewLeader); */
	                        $('#leaderprofile').html('<div class="leaderjb">' + profileImg + iconBox
	                        		+ '<a class="user" style="cursor: pointer;"  data-id="' + item.id + '">' 
	                        		+ item.nickname + ' / ' 
	                            + genderImg + ' / ' + '크루장</div></a>');
	                        $('.profileImg').attr('class', 'profileImg_01');
	                    } else {
	                        crewone.push(item.id); // 배열에 크루원 id 추가
	                        content += '<div class="testeee"><input class="basictex" type="checkbox" name="crew_member" data-id="'+item.id+'"/>' +
	                        '<a class="user" style="cursor: pointer;"  data-id="' + item.id + '">' 
	                            + profileImg + member_icon + ' ' + item.nickname + ' / ' + genderImg + ' / '  + item.create_date + '</div></a>';
	                    }
	                });

	                $('#crew-member-profile').html(content);
	            }
	        },
	        error: function (e) {
	            console.log('에러 발생 => ', e);
	        }
	    });
	}
	
	function crewDetail() {
        console.log('크루 데이터 요청');
        
        $.ajax({
            type: 'POST',
            url: '/crew/detail',
            data: { 'crew_idx': crew_idx },
            dataType: 'JSON',
            success: function (response) {
                console.log('데이터 받아옴 => ', response);
                if (response.success) {
                    // 받아온 데이터를 HTML에 반영
                    var result = response.result;
                    
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
                    $('#crew-name').html('<a href="/crewDetail/'+crew_idx+'">'+result.crew_name+'</a>');
                    $('#crew-content').html(result.content);
                    $('#crew-address').text(result.address);
                    $('#crew-member').text(result.member);
                    $('#crew-days').text(day);
                    $('#crew-minute').text(result.minute);
                    $('#crew-distance').text(result.distance);
                    
                }
            },
            error: function (e) {
                console.log('에러 발생 => ', e);
            }
        });
    }
    
	function crewApplicationMember(application) {
		var profileImg = '<img src="/resources/img/common/profile.png" width="32px" class="profileImg03"/>';
        var genderImg = '';
        var content = '';
        
        console.log('신청자 리스트 함수 실행');
	    
        application.slice(0, 5).forEach(function(item, idx) {
	        // 성별에 따른 이미지 설정
	        var genderImg = item.gender === '남' 
	            ? '<img src="/resources/img/common/ico_male.png" width="9px" class="genderImg"/>'
	            : '<img src="/resources/img/common/ico_female.png" width="9px" class="genderImg"/>';

	        if(item.image){
            	profileImg = '<img alt="profileImg" src="/photo/' + item.image + '" onerror="this.src=\'/resources/img/common/profile.png\'" class="profileImg03" width="32px" />';
            }else{
            	profileImg = '<img alt="profileImg" src="/resources/img/common/profile.png" onerror="this.src=\'/resources/img/common/profile.png\'" class="profileImg03" width="32px" />';
            }
	        
	        var iconBox2 = '';
	        if(item.icon_image != null && item.icon_image !== ''){
				iconBox2 = '<div class="profile-box21" style="background: url(/resources/img/icon/'+item.icon_image+') center center / 100% 100% no-repeat;"></div>';
			}
	            
	        // 프로필 이미지와 닉네임, 성별 및 날짜 정보 추가
	        content += '<div class="testeee">' + profileImg + iconBox2 + '<a class="user" style="cursor: pointer;"  data-id="' + item.id + '">' + item.nickname + ' / ' + genderImg + ' / ' + item.create_date + '</a>'
	            + '<div class="btn-sty06"> <button class="btn02-s" onclick="layerPopup(\'' + item.nickname + '님을 승인 하시겠습니까?\', \'승인\', \'취소\', function() { memberResult(\'' + item.id + '\', \'' + item.nickname + '\', \'Y\'); }, applBtn2Act)">승인</button>'
	            + '<button class="btn02-s" id="btn04-s" onclick="layerPopup(\'' + item.nickname + '님을 거절 하시겠습니까?\', \'거절\', \'취소\', function() { memberResult(\'' + item.id + '\', \'' + item.nickname + '\', \'N\'); }, applBtn2Act)">거절</button></div></div>';
	    });

	    $('#crewApplicationMemberList').html(content);
	}
	
	function memberResult(id, nickname, value) {
		loading();
	    console.log('ID:', id);
	    console.log('Nickname:', nickname);
	    console.log('value:', value);
	    
	    var crew_idx = $('input[name="crew_idx"]').val();
	    var code_name = '';
	    
	    if(value === 'Y'){
	        code_name = 'C101';
	    } else {
	        code_name = 'C102';
	    }
	
	    console.log('code_name : ', code_name);			
	    if(code_name === 'C101'){
	        $.ajax({
	            type: 'GET',
	            url: '/crew/memberFullCheck',
	            data: {'crew_idx' : crew_idx},
	            dataType: 'JSON',
	            success: function(response){
	                if(response){
	                    memberResultUpdate(id, nickname, code_name);
	                } else {
	                	removeAlert();
	                    layerPopup('크루원이 가득 찼습니다.', '확인', false, applBtn2Act, applBtn2Act);
	                    loadingComplete();
	                }
	            }, 
	            error: function(e){
	                console.log('멤버수 체크 중 에러 => ', e);
	                loadingComplete();
	            }
	        });
	    } else {
	        memberResultUpdate(id, nickname, code_name);
	    }
	}
	
	function memberResultUpdate(id, nickname, code_name){
	    $.ajax({
	        type: 'POST',
	        url: '/crew/applicationWrite',
	        data: {
	            'loginId': id,
	            'crew_idx': crew_idx,
	            'code_name': code_name
	        },
	        dataType: 'JSON',
	        success: function(response){
	            console.log('성공');
	            
	            if(response.success){
	                removeAlert();
	                crewMemberList();
	                layerPopup(response.msg + ' 완료되었습니다.', '확인', false, applBtn2Act, applBtn2Act);
	                loadingComplete();
	            } else {
	                removeAlert();
	                layerPopup(response.msg + ' 미완료되었습니다.', '확인', false, applBtn2Act, applBtn2Act);
	                loadingComplete();
	            }
	        },
	        error: function(e){
	            console.log('에러남 => ', e);
	            loadingComplete();
	        }
	    });
	} 
	
	var crew_idx = $('input[name="crew_idx"]').val();
	
	// 선택된 체크박스의 ID 배열을 가져오는 함수
	function getSelectedIds() {
	    var selectedIds = [];
	    $('input[name="crew_member"]:checked').each(function() {
	        selectedIds.push($(this).data('id')); // 체크된 체크박스의 data-id 값을 배열에 추가
	    });
	    return selectedIds;
	}

	function crewAdminUpdate() {
	    var selectedIds = getSelectedIds(); // 선택된 체크박스의 ID 배열 가져오기
	    
	    if (selectedIds.length === 0) {
	        // 선택된 체크박스가 없을 때 알림
	        alert("권한을 양도할 회원을 선택해 주세요.");
	    } else if (selectedIds.length > 1) {
	        // 1개 이상의 ID가 선택된 경우 알림
	        layerPopup('권한은 한명에게만 양도할 수 있습니다.', '확인', false, applBtn2Act, applBtn2Act);
	    } else {
	        // 선택된 ID가 1개인 경우에만 권한 양도 팝업 띄우기
	        layerPopup('권한을 양도하시겠습니까?', '확인', '취소', function() {
	            crewAdminOverlay(selectedIds[0]); // 선택된 ID를 서버에 전송
	        }, applBtn2Act);
	    }
	}
	
	function crewAdminOverlay(memberId){
		loading();
		console.log('중복 체크 해야하는 id =>', memberId);
		$.ajax({
			type: 'GET',
			url: '/crew/adminOverlay',
			data: {'id' : memberId,
				'crew_idx' : crew_idx},
			dataType: 'JSON',
			success: function(response){
				if(response){
					sendCrewAdminUpdate(memberId);
				}else{
					removeAlert();
					layerPopup('이미 요청한 크루원입니다.', '확인', false, applBtn2Act, applBtn2Act);
					loadingComplete();
				}
			},error: function(e){
				console.log('권한 중복 체크 중 에러 =>', e);
				loadingComplete();
			}
		})
	}

	
	
	function sendCrewAdminUpdate(memberId){
		
		var selectedIds = getSelectedIds();
	    
	    var leader = crewLeader;
	    console.log('선택된 ID:', selectedIds[0]); // 콘솔에 선택된 ID 출력
	    console.log('리더 아이디 => ', leader);
  		$.ajax({
			type: 'POST',
			url : '/crew/AdminUpdate',
			data: { 'id' : memberId,
					'leader' : leader,
					'crew_idx' : crew_idx}, // JSON 형태로 전송
			dataType: 'JSON',
			success: function(response){
				if(response.success){
					removeAlert();
					layerPopup('권한 양도 요청이 완료되었습니다.', '확인',false,applBtn2Act,applBtn2Act);
					loadingComplete();
				}else{
					removeAlert();
					layerPopup('권한 양도 요청이 미완료되었습니다.', '확인',false,applBtn2Act,applBtn2Act);
					loadingComplete();
				}
			}, error: function(e){
				console.log('권한 전송 중 에러 => ', e);
				loadingComplete();
			}
		});  
		
	}
	
	function crewExpel(){
		var crew_idx = $('input[name="crew_idx"]').val();
		var selectedIds = getSelectedIds();
		
		$.ajax({
			type: 'POST',
			url: '/crew/crewExpel',
			traditional: true,
			data: { 'ids' : selectedIds,
				'crew_idx' : crew_idx},
			dataType: 'JSON',
			success: function(response){
				if(response.success){
					// loadingComplete()
					removeAlert();
					layerPopup('크루원 퇴출이 완료되었습니다.', '확인',false,applBtn2Act,applBtn2Act);
					crewMemberList();
				}else{
					removeAlert();
					layerPopup('크루원 퇴출이 미완료되었습니다.', '확인',false,applBtn2Act,applBtn2Act);
				}
			}, error: function(e){
				console.log('권한 전송 중 에러 => ', e);
			}
		});
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
	$(document).on('click','#profilePopup .close',function(){
       document.getElementById("profilePopup").style.display = "none";
   });	
	
	// 크루장 채팅리스트
	$.ajax({
		type: 'GET',
		url: '/crewLdchatList/'+crew_idx,
		dataType: 'JSON',
		success: function(data){	
			console.log(data);
			drawChatList(data);
		},
		error: function(e){
			console.log(e);
		}
	});
	
	function drawChatList(list){
		var chatCont = '';
		list.forEach(function(item,idx){
			
			if(idx<5){

				var createDateTime = item.create_date;
				console.log("createDateTime : ", createDateTime);
				var createDate = createDateTime.split('T')[0];
				var createDateList = createDate.split('-');
				var createDateForm = createDateList[0]+'. '+createDateList[1]+'. '+createDateList[2];

				chatCont += '<div class="chat-list">';
				chatCont += '<div class="line">';      	
			 	chatCont += '<div class="profile-area">'; 	  	
               if(item.image != null){
            	   chatCont += '<div class="profile-img" style="background: url(/photo/'+item.image+') center center / cover no-repeat;"></div>';
               }else{  
            	   chatCont += '<div class="profile-img"  style="background: url(/resources/img/common/profile.png) center center / cover no-repeat;"></div>';
               }
		        chatCont += '<div class="profile-box" style="background: url(/resources/img/icon/'+item.icon_image+') center center / 100% 100% no-repeat;"></div>';
               chatCont += '</div>';
        	
               chatCont += '<span class="name">'+item.nickname+'</span><span class="bar">/</span>';
               if(item.genger == '남'){            	   
	               chatCont += '<img class="ico-gender" src="/resources/img/common/ico_male.png" alt="남성"/>';
               }else{
	               chatCont += '<img class="ico-gender" src="/resources/img/common/ico_female.png" alt="남성"/>';	            	   
               }
               chatCont += '<span class="bar">/</span><span>'+createDateForm+'</span>';
               chatCont += '<div class="btn-chat btn02-s" onclick="openCrewManagerChat(\'' + item.id + '\')">채팅하기</div>';
	            
               chatCont += '</div></div>';
			}
		    
		});
		$('.crew-chat-list').append(chatCont);
	}
	