
	var currentPage = 1;
	var isLoading = false;
	var hasMoreData = true;
	var filtering = [];
	var likeCrew = [];
	var crewLikeStatus = {}; // 각 크루별 idx 와 y/n 정보를 담음 ex) 1 : Y, 2 : N ...
	
	var loginId = $('input[name="loginId"]').val();
	console.log(loginId, '');
	
	
	// 크루가 홀수일 때 마지막 crowBox 왼쪽 정렬
	$(document).ready(function() {
		if (loginId) {
		    // 로그인된 경우 좋아요한 크루 데이터를 먼저 가져옴
		    fetchLikedCrews();
		}
		
		crewList(currentPage); // 1 전달 (현재 페이지)
		
	    // IntersectionObserver 설정
	    var observer = new IntersectionObserver(function(entries) {
	        if (entries[0].isIntersecting && !isLoading) {
	        	console.log('loading 에 도착');
	            currentPage++; // 다음 페이지 요청
	            crewList(currentPage); // 페이지 호출
	        }
	    });
		
	    observer.observe(document.getElementById('loading')); // 감시할 요소 지정
	    
	    $('input[name="tag_idx_list"]').on('click', function() {
	        currentPage = 1; // 페이지 초기화
	        hasMoreData = true;
	        $('.crewListBox').empty(); // 기존 목록 초기화
	        updateFiltering(); // 필터링 배열 업데이트
	        crewList(currentPage); // 필터링된 리스트 요청
	        
	    });
	
	});
	
	// 필터 배열
	function updateFiltering() {
	    filtering = $('input[name="tag_idx_list"]:checked').map(function() {
	        return $(this).val();
	    }).get(); // 체크된 값만 배열로 가져옴
	    console.log('필터 =>', filtering);
	}
	
	
	
	
	
	// 크루리스트 불러오기
	function crewList(page){
		$('#loading').css('opacity', '1');
	    isLoading = true;
		// 필터링된 값을 배열로 업데이트하는 함수
		 updateFiltering();
	
		console.log("filtering > ", filtering)
	  	$.ajax({
			type: 'GET',
			async: true, // 비동기 방식이지만 데이터를 동기로 기다렸다가 다음 요청 보내기
			url: '/crew/list',
			data: $.param({'filtering': filtering, 'page': page, 'pageSize': 10 }, true),
			dataType: 'JSON',
			success: function(response){
				var result = response.result;
				console.log('오는값 => ',result);
				
				crewListPrint(result);
				if (loginId) updateHeartIcons(result);  // 로그인한 경우 좋아요 아이콘 업데이트
				
				
			},error: function(e){
				console.log('에러 => ', e);
				$('#loading').hide();
	            isLoading = false;
			}
		}); 
		
	}
	
	function crewListPrint(result){
		
		var content = '';
		var btn_style = '';
		
		if(result.length > 0){
			result.forEach(function(item, idx){
				
				var day = item.days.replace(/mon|tue|wen|thu|fri|sat|sun/gi, function(match) {
	                return { mon: '월', tue: '화', wen: '수', thu: '목', fri: '금', sat: '토', sun: '일' }[match.toLowerCase()];
	            });
					
				var is_recruit = '';
				
				if(item.is_recruit === 'Y'){
					is_recruit = '모집중';
					btn_style = '<div class="crewStatus01"><span id="is_recruit">'+is_recruit+'</span></div>';
				}else{
					is_recruit = '모집완료';
					btn_style = '<div class="crewStatus02"><span id="is_recruit">'+is_recruit+'</span></div>';
				}
				
				var imgElem = '';
				if(item.img_new === null || item.img_new === ''){
					imgElem = '/resources/img/crew/crewImg300.png';
				}else{
					imgElem = '/photo/'+item.img_new;
				}
				
	            // 태그 처리
	            var tagNamesArray = item.tag_names ? item.tag_names.split(',') : [];
	            var displayedTags = '';
	            
	            tagNamesArray.slice(0, 3).forEach(function(tag, index) {
	                var styleClass = index === 1 ? 'highlight-tag' : 'normal-tag';
	                displayedTags += '<span class="tag ' + styleClass + '">' + tag + '</span>';
	            });
	            
	            
	
				content += '<div class="crewBox" onclick="crewDetail('+item.crew_idx+')">';
				content += '<div class="crewImg"><img class="crew-img" id="crew-image" src="' + imgElem + '" onerror="this.src=\'/resources/img/crew/crewImg300.png\'"/>';
				// 좋아요 이미지
				content += '<div onclick="crewLikeCheck('+item.crew_idx+')"><img id="crewLikes-'+item.crew_idx+'" class="crew-like" src="/resources/img/common/ico_heart_no_act.png"/></div></div>';
				// 크루 tag
				content += '<div class="crewContentBox"><div class="tagBox2">' + displayedTags + '</div>';
				
				content += '<div class="crewName" id="crew-name">'+item.crew_name+'</div>';
				content += '<div class="crewInfo-01">';
				content += '<img src="/resources/img/crew/img01.png" width="10px" class="imglayout"/>';
				content += '<span id="crew-location">'+ item.shortsido + '&nbsp;' +item.sigungu +'</span>  &nbsp; &nbsp; &nbsp;';
				content += '<img src="/resources/img/crew/img03.png" width="14px" class="imglayout"/>';
				content += '<span id="crew-days">'+day+'</span>';
				content += '</div>';
				content += '<div class="crewInfo-02">';
				content += '<div class="inone">';
				content += '<img src="/resources/img/crew/img02.png" width="13px" class="imglayout"/>';
				content += '<span class="current_member" id="current_member">'+ item.current_member +'</span> / <span class="member" id="member">'+ item.member +'</span></div>';
				content += btn_style;
				content += '</div>';
				content += '</div>';
				content += '</div>';
				
				var is_recruit = item.is_recruit;
			});
			
			$('.crewListBox').append(content);
			$('#loading').css('opacity', '0'); // loading 요소를 투명하게 설정
	        isLoading = false; // 로딩 상태 해제
	        
	        
		    var crewBoxes = $('.crewListBox .crewBox');
		    var crewListBox = $('.crewListBox');
		    // crewBox가 홀수일 때 마지막 요소에 left-align-last 클래스를 추가하고, 전체 왼쪽 정렬
		    if (crewBoxes.length % 2 !== 0) {
		        crewBoxes.last().addClass('left-align-last');
		        crewListBox.css('justify-content', 'flex-start'); // 전체를 왼쪽 정렬
		    } else {
		        crewListBox.css('justify-content', 'center'); // 짝수일 때는 중앙 정렬 유지
		    }
			
		}else{
			hasMoreData = false;
			$('#loading').text('모든 크루 정보를 불러왔습니다.').css('opacity', '1');
		}
	}
	
	
	function crewDetail(crew_idx){
		console.log('이동 =>', crew_idx)
		location.href="/crewDetail/"+crew_idx; 
			
	}
	
	var checkboxes = document.querySelectorAll('#tagFilter input[type="checkbox"]');
	
	checkboxes.forEach(function(checkbox){
	  checkbox.addEventListener('click', function() {
	    if (this.checked) {
	      this.parentElement.classList.add('checked');
	    } else {
	      this.parentElement.classList.remove('checked');
	    }
	  });
	});
	
	
	function fetchLikedCrews(){
		$.ajax({
			type: 'POST',
			url: 'crew/likeCrew',
			data: {'id' : loginId},
			dataType: 'JSON',
			success: function(response){
				console.log(response);
				var result = response.result;
				result.forEach(function(item){
					likeCrew.push(item.crew_idx);
				});
			},error: function(e){
				console.log('관심 크루 에러 => ', e);
			}
		});
	}
	
	function updateHeartIcons(result) {
	
		console.log("likeCrew 배열 확인:", likeCrew);
	    result.forEach(function(item) {
	        // crew_idx 타입 확인
	        // console.log("item.crew_idx:", item.crew_idx, "타입:", typeof item.crew_idx);
	
	         if (likeCrew.includes(item.crew_idx)) {
	        	 $('#crewLikes-'+item.crew_idx).attr('src', '/resources/img/common/ico_heart_act.png');
	        	 console.log('오니?');
	        	 crewLikeStatus[item.crew_idx] = 'Y';
	        } else {
	        	crewLikeStatus[item.crew_idx] = 'N';
	        	console.log('여기는');
	        	$('#crewLikes-'+item.crew_idx).attr('src', '/resources/img/common/ico_heart_no_act.png');
	        } 
	    });
	
	}	 
	
	function crewLikeCheck(crew_idx){
		console.log('좋아요 이벤트 실행됨?');
		event.stopPropagation(); // 이벤트 전파 방지
	    event.preventDefault(); // 상위 태그 이벤트 중지
	    
	    if(loginId){
	    	
	    	var currentStatus = crewLikeStatus[crew_idx] || 'N'; // crew_idx 별 값 가져오기
	    	
	    	if(currentStatus === 'Y'){
	    		console.log('좋아요 취소 신청');
	    		//crewLikeChange(crew_idx, currentStatus);
	    		 console.log('좋아요 취소 신청');
        	    crewLikeChange(crew_idx, currentStatus, function() {
                // 좋아요 취소 후 이미지 업데이트
                $('#crewLikes-' + crew_idx).attr('src', '/resources/img/common/ico_heart_no_act.png');
                crewLikeStatus[crew_idx] = 'N'; // 상태 업데이트
                });
	    	}else{
	    		//console.log('좋아요 등록 신청');
	    		//crewLikeChange(crew_idx, currentStatus); // 'N'
	    		 console.log('좋아요 등록 신청');
       		     crewLikeChange(crew_idx, currentStatus, function() {
                // 좋아요 등록 후 이미지 업데이트
                $('#crewLikes-' + crew_idx).attr('src', '/resources/img/common/ico_heart_act.png');
                crewLikeStatus[crew_idx] = 'Y'; // 상태 업데이트
            });
	    	}
	    }else{
	    	layerPopup('로그인이 필요한 서비스입니다.', '로그인 하기', '취소', loginPageLocation, applBtn2Act);
	    }
	}
	
	function loginPageLocation(){
		location.href='/loginView'; 
	}
	
	// 팝업 취소
	function applBtn2Act() {
	    removeAlert(); 
	}	
	
	
	function crewLikeChange(crew_idx, currentStatus){ // 선택된 크루의 idx 와 상태값(y/n) 가져옴
		console.log('잘 실행? : crew_idx => ', crew_idx);
		console.log('해당 크루 좋아요 상태 값 => ', currentStatus);
		
		$.ajax({
			type: 'POST',
			url: 'crew/likeRequest',
			data: {'loginId' : loginId,
				'crew_idx' : crew_idx,
				'likeCrew' : currentStatus},
			dataType: 'JSON',
			success: function(response){
				var result = response.result;
				if(response.success){
					likeCrew.length = 0;
					fetchLikedCrews();
					crewList(currentPage);
					console.log('좋아요 상태 변경 성공');
				}
			},error: function(e){
				console.log('좋아요 상태 변경 중 에러 => ', e);
			}
		});
		
	}