	
	var loginId = $('input[name="loginId"]').val();
	var firstPage = 1;
	var paginationInitialized = false;
	
	var leaderId = $('input[name="leaderId"]').val();
	$(document).ready(function(){
		if(leaderId !== loginId){
			alert('크루장만 접근 가능합니다');
			location.href='/';
		}else{
			pageCall(firstPage);
			
		}
	
	}); 
	
	
	
	// 검색 폼 제출 시 AJAX 호출
	$('#searchForm').on('submit', function(event) {
	    event.preventDefault();  // 폼 제출 기본 동작 중지
	    firstPage = 1;
	    paginationInitialized = false;
	    pageCall(firstPage);  // 검색어가 추가된 상태에서 호출
	});
	
    var crew_idx = $('input[name="crew_idx"]').val(); // 나중에 변경 필요

	function pageCall(page) {
	    var keyword = $('#searchKeyword').val();  // 검색어
	
	    $.ajax({
	        type: 'POST',
	        url: '/crew/applicationMemberList',
	        data: {
	        	'crew_idx' : crew_idx,
	            'page': page,
	            'cnt': 15,
	            'keyword': keyword  // 검색어
	        },
	        datatype: 'JSON',
	        success: function(response) {
	            console.log(response.result);
	            
	            console.log(response.result.totalpage);
	            
	            var result = response.result;
	            var totalCount = 15;
	            if(response.result){
	            	if(response.result.totalpage){
	            	}if(response.result.totalpage  >= 15){
		            	totalCount = response.result[0].totalpage;  // 총 게시글 수를 서버에서 가져옴
	            	}
	            	
	            }
	            
	            
	           // var totalCount = response.result[0].totalpage;  // 총 게시글 수를 서버에서 가져옴
	            var pageSize = 15;  // 한 페이지당 게시글 수
	            var totalPages = Math.ceil(totalCount / pageSize);  // 총 페이지 수 계산
	            console.log('총 페이지 수=> ', totalPages);
	            
	            
	            drawList(result);
	            
	            if(!paginationInitialized || keyword !== ''){
	            	$('#pagination').twbsPagination('destroy');
		            $('#pagination').twbsPagination({
		                startPage: page,
		                totalPages: totalPages,
		                visiblePages: 10,
		                initiateStartPageClick: false,
		                onPageClick: function(evt, page) {
		                    console.log('evt', evt);
		                    console.log('page', page);
		                    pageCall(page);
		                }
		            });
		            paginationInitialized = true;
	            }
	        },
	        error: function(e) {
	            console.log(e);
	        }
	    });
	}
	// 게시글 리스트
	function drawList(result) {
		var content ='';
		result.forEach(function(item,idx){
			
			if(item.gender === '남'){
        		genderImg = '<img src="/resources/img/common/ico_male.png" width="9px" class="genderImg"/>';
        	}else{
        		genderImg = '<img src="/resources/img/common/ico_female.png" width="9px" class="genderImg"/>';
        	}
			
	        var birth = item.birth;
	        //console.log("Birth value:", birth); // birth 값 확인
	        
	         // birth의 연도만 추출
	        var birthYear = parseInt(birth.split('-')[0], 10);

	        // 현재 연도에서 출생 연도를 빼고 나이대 계산
	        var ageGroup = Math.floor((new Date().getFullYear() - birthYear) / 10) * 10 + "대";
			
	        var profileImg = '';
	        var icon_img = '';
	        if(item.image != null && item.image != ''){
				profileImg = '/photo/'+item.image;
			}else{
				profileImg = '/resources/img/common/profile.png';
			}
			if(item.icon_image != null && item.icon_image !== ''){
				icon_img = 'background: url(/resources/img/icon/' + item.icon_image + ') center center / 100% 100% no-repeat;';
			}
	        
            content += '<tr>';
            content +='<td class="profileContainer"><img src="'+profileImg+'" width="32px" class="profileBox" onerror="this.src=\'/resources/img/common/profile.png\'" //>'+item.nickname;
			content += '<div class="profile-box2" style="' + icon_img + '"></div>'; // icon
			content += '</td>';
			content +='<td>'+ageGroup+'</td>';
			content +='<td>'+genderImg+ '&nbsp;' +item.gender+'</td>';
			content +='<td>'+item.create_date+'</td>'; // 신청일자
			content += '<td><button class="btn02-s" onclick="layerPopup(\'' + item.nickname + '님을 승인 하시겠습니까?\', \'승인\', \'취소\', function() { memberResult(\'' + item.id + '\', \'' + item.nickname + '\', \'Y\'); }, applBtn2Act)">승인</button>';



		 	content += '<button class="btn02-s" id="btn04-s" onclick="layerPopup(\'' + item.nickname + '님을 거절 하시겠습니까?\', \'거절\', \'취소\', function() { memberResult(\'' + item.id + '\', \'' + item.nickname + '\', \'N\'); }, applBtn2Act)">거절</button></td>'; 
	        content += '</tr>';

		});
		$('#list').html(content);
	}
	
	 	$('#loginPop').on('click',function(){
	 		
	 		
	 		
	 		if(!userId){
	 			layerPopup('로그인이 필요한 서비스 입니다.','로그인 페이지','닫기',secondBtn1Act,secondBtn1Act);	
	 		}else{
	 			location.href='runBoardWrite';
	 		}
	 		
	 	});
	 	
	 	
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
		
		// 팝업 취소
		function applBtn2Act() {
		    removeAlert(); 
		}
		
		
	function memberResult(id, nickname, value) {
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
	    console.log('실행됨>');
	    if(code_name === 'C101'){
	    console.log('실행??');
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
	                }
	            }, 
	            error: function(e){
	                console.log('멤버수 체크 중 에러 => ', e);
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
	                layerPopup(response.msg + ' 완료되었습니다.', '확인', false, applBtn2Act, applBtn2Act);
	                pageCall(firstPage);
	            } else {
	                removeAlert();
	                layerPopup(response.msg + ' 미완료되었습니다.', '확인', false, applBtn2Act, applBtn2Act);
	            }
	        },
	        error: function(e){
	            console.log('에러남 => ', e);
	        }
	    });
	} 
			