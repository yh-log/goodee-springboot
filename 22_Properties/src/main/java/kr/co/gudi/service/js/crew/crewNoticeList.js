	var firstPage = 1;
	var paginationInitialized = false;
	
	
	var loginId = $('input[name="loginId"]').val();
	
    var crew_idx = $('input[name="crew_idx"]').val();
	
	$(document).ready(function () {

		pageCall(firstPage);
	    console.log('crew_idx =>', crew_idx);
	    leaderCheck();

	});
	
	
	// 검색 폼 제출 시 AJAX 호출
	$('#searchForm').on('submit', function(event) {
	    event.preventDefault();  // 폼 제출 기본 동작 중지
	    firstPage = 1;
	    paginationInitialized = false;
	    pageCall(firstPage);  // 검색어가 추가된 상태에서 호출
	});
	

	function pageCall(page) {
		var option = $('#searchOption').val();
	    var keyword = $('#searchKeyword').val();  // 검색어
	    
	    $.ajax({
	        type: 'POST',
	        url: '/crew/noticeList',
	        data: {
	        	'crew_idx' : crew_idx,
	            'page': page,
	            'cnt': 15,
	            'option': option,
	            'keyword': keyword  // 검색어
	        },
	        datatype: 'JSON',
	        success: function(response) {
	            console.log(response.result);
	            
	            if (response.length > 0) {
	                // response 배열로부터 데이터 리스트 접근
	            }
	            
	            var result = response.result;
	            
	            var totalCount = 15;
	            if(response.result){
	            	if(response.result.totalpage){
	            	}if(response.result.totalpage  >= 15){
		            	totalCount = response.result[0].totalpage;  // 총 게시글 수를 서버에서 가져옴
	            	}
	            	
	            }
	            
	            var pageSize = 15;  // 한 페이지당 게시글 수
	            var totalPages = Math.ceil(totalCount / pageSize);  // 총 페이지 수 계산
	            console.log('총 페이지 수=> ', totalCount);
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
			var priority = item.priority; // 순위
			var noticeBtn = '';
			
			if(item.priority == 1 || item.priority == 2 || item.priority == 3){
				console.log('필독 1번 =>', item.notice_idx);
				noticeBtn = '<button class="btn02-s">필독공지</button>';
			}else{
				console.log('일반 공지');
				noticeBtn = item.notice_idx;
			}
			
			$('#crew_name').html('<a style="color: #333" href="/crewDetail/' + item.crew_idx + '">' + item.crew_name + '</a>');
			
            content += '<tr>';
            
            content += '<td>'+noticeBtn+'</td>';
            content += '<td class="subjectTable"><a style="color: #333" href="/crewNoticeDetail/'+item.notice_idx+'">'+item.subject+'</a></td>';
            content += '<td><a class="user" style=" cursor: pointer; color: #333"  data-id="' + item.id + '">'+item.nickname+'</a></td>';
            content += '<td>'+item.hit+'</td>';
            content += '<td>'+item.create_date+'</td>';
	        content += '</tr>';

		});
		$('#list').html(content);
	}
	
	function leaderCheck(){
		$.ajax({
			type: 'POST',
			url: '/crew/leaderCheck',
			data: {'crew_idx' : crew_idx},
			dataType: 'JSON',
			success: function(response){
				// 리더인지 체크해서 버튼 숨기고, 보이기
				
				console.log(response);
	            if(loginId === response.leaderId){
	            	console.log('id : id', loginId, response.leaderId);
	            	$('.btn01-l').css('visibility', 'visible');
	            }
			},error: function(e){
				console.log('크루장 가져오는 중 에러 => ', e);
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