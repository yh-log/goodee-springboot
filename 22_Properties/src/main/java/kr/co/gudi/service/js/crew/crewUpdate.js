	var loginId = $('input[name="loginId"]').val();
	var dayCheckboxes = [];
	var tagCheckboxes = [];
	var crew_idx = $('input[name="crew_idx"]').val();
	var leaderId = $('input[name="leaderId"]').val();
	$(document).ready(function(){
		if(leaderId !== loginId){
			alert('크루장만 접근 가능합니다');
			location.href='/';
		}else{
			if(crew_idx != null && crew_idx !== ''){
				crewUpdateView();
			}
		}
	
	}); // document
	
	
	tagResult();
	function tagResult(){
		$.ajax({
			type: 'GET',
			url: '/crew/tagResult',
			data: {},
			dataType: 'JSON',
			success: function(response){
				console.log(response.tag);
				
				var tag = response.tag;
				
				var content = '';
				tag.forEach(function(item, idx){
					var tag_img = '';
					if(item.tag_idx == 3){
						tag_img = '<img src="/resources/img/common/ico_male.png" width="9px" class="tagImg-01"/>';
					}else if(item.tag_idx == 4){
						tag_img = '<img src="/resources/img/common/ico_female.png" width="9px" class="tagImg-01"/>';
					}else if(item.tag_idx == 5){
						tag_img = '<img src="/resources/img/common/ico_male.png" width="9px" class="tagImg-01"/><img src="/resources/img/common/ico_female.png" width="9px" class="tagImg-01"/>';
					}
				
					content += '<label>';
					content += '<input type="checkbox" name="tag_idx_list" value="' + item.tag_idx + '">';
					content += tag_img + item.tag_name;
					content += '</label>';
				});
				
				$('#tagFilters').append(content);
				
			},error: function(e){
				console.log('태그 가져오던 중 에러 => ', e);
			}
		})
	
	}
	
	// 크루 정보 불러오기
	function crewUpdateView(){
		var crew_idx = $('input[name="crew_idx"]').val();
		console.log('실행됨');
		console.log('idx?? => ', crew_idx);
		$.ajax({
			type: 'POST',
			url: '/crew/updateView',
			data: {'crew_idx' : crew_idx}, // todo - 변경 필요
			dataType: 'JSON',
			enctype: 'multipart/form-data',
			success: function(response){
				var result = response.result;
				console.log('받아온 데이터 => ', response);
				
				// 크루 이미지
				if(result.img_new != null && result.img_new !== ''){
					$('#crew_img').attr('src', '/photo/'+result.img_new);
				}else{
					$('#crew_img').attr('src', '/resources/img/crew/crewImg300.png');
				}
				
				// 크루 이름
				$('input[name="crew_name"]').val(result.crew_name);
				
				// 태그
				if (result.tag_idxs) {
		            initializeTags(result.tag_idxs); // 서버에서 받은 태그 데이터로 초기 설정
		        }
				
				// 요일
				var days = result.days.split(',');
				$('input[name="days"]').each(function(){
					var dayValue = $(this).val();
					if(days.includes(dayValue)){
						$(this).prop('checked', true);
						dayCheckboxes.push(this); // 요일 체크 배열에 데이터 넣어줌
					}
				});
				
				// 인원
				$('input[name="member"]').val(result.member);
				// 운동 강도 (분/km)
				$('input[name="minute"]').val(result.minute);
				$('input[name="distance"]').val(result.distance);
				// 지역
				$('input[name="address"]').val(result.address);
				// 크루설명
				var content = result.content;
				$('#summernote').summernote('code', content);
				
			},error: function(e){
				console.log('에러 => ', e);
			}
		});
	}
	
	function initializeTags(tag_idxs) {
	    var tags = tag_idxs.split(','); // 서버에서 받은 태그 목록을 배열로 변환

	    $('input[name="tag_idx_list"]').each(function() {
	        if (tags.includes($(this).val())) {
	            $(this).prop('checked', true); // 체크박스에 체크 설정
	            $(this).parent().addClass('checked'); // 부모 label에 checked 클래스 추가
	            tagCheckboxes.push(this); // 배열에 추가하여 추적
	        }
	    });
	}

	
	// 크루 대표 이미지 미리보기
    function readFile(input) {
        var reader;
        $('#img_miri').empty();

        for (var file of input.files) {
            reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = function (e) {
                $('#img_miri').append('<img class="preview" width="300px" height="200px" src="' + e.target.result + '"/>');
            }
        }
    }
	
	// 요일 체크
	$('input[name="days"]').on('change', function () {
        if ($(this).is(':checked')) {
            if (dayCheckboxes.length >= 2) {
                var firstChecked = dayCheckboxes.shift();
                $(firstChecked).prop('checked', false);
            }
            dayCheckboxes.push(this);
        } else {
            dayCheckboxes = dayCheckboxes.filter(item => item !== this);
        }
    });

    
	// 부모 요소에 이벤트 위임
	$('#tagFilters').on('change', 'input[name="tag_idx_list"]', function () {
	    var $label = $(this).parent(); // 부모 label 요소 참조
	
	    if ($(this).is(':checked')) {
	        if (tagCheckboxes.length >= 3) {
	            // 이미 3개가 선택된 경우, 배열의 첫 번째 항목을 해제
	            var firstChecked = tagCheckboxes.shift(); // 배열에서 첫 번째 항목 제거
	            $(firstChecked).prop('checked', false); // 체크 해제
	            $(firstChecked).parent().removeClass('checked'); // checked 클래스 제거
	        }
	        tagCheckboxes.push(this); // 새로운 체크박스를 배열에 추가
	        $label.addClass('checked'); // 현재 체크박스의 부모 label에 checked 클래스 추가
	    } else {
	        // 체크 해제된 경우 배열에서 해당 항목 제거
	        tagCheckboxes = tagCheckboxes.filter(item => item !== this);
	        $label.removeClass('checked'); // 부모 label에서 checked 클래스 제거
	    }
	});
		
		
	function writeCheck(){

    	if($('input[name="crew_name"]').val() !== '' && $('#crew_img').attr('src') !== '' && dayCheckboxes.length >= 1 && tagCheckboxes.length >= 1 && $('input[name="member"]').val() !== '' && $('input[name="minute"]').val() !== '' && $('input[name="distance"]').val() !== '' && $('input[name="address"]').val() !== ''){
    		layerPopup('크루를 수정하시겠습니까?', '확인', '취소', submitUpdatePost, applBtn2Act)
    		console.log('글 전송 함수 실행');
    	}else{
    		layerPopup('필수 정보를 입력해주세요', '확인', false, applBtn2Act, applBtn2Act);
    	}
    	
    }	
		
		
		
	// 수정 데이터 전송
    function submitUpdatePost() {
  		loading();
        var formData = new FormData($('form')[0]);
        var content = $('#summernote').summernote('code'); // summernote 코드

        var fileInput = $('input[type="file"]')[0]; // 파일 input에서 파일 가져오기
        if (fileInput.files.length > 0) {
            formData.append('crew_img', fileInput.files[0]); // 파일 데이터 추가
            console.log(formData.crew_img);
        }

        formData.append('id', loginId); 
        
        formData.append('content', content);  // summernote의 HTML 내용 추가 (이미지 포함)
		
        if(roadAddr != null && roadAddr !== ''){
	        formData.append('address', roadAddr); // 화면에 출력
	        formData.append('sigungu', sigungu);
	        formData.append('sido', sido);
        }
        if(shortsido != null && shortsido !== ''){
        	formData.append('shortsido', shortsido);
        }
        
        var selectedTags = "";

        tagCheckboxes.forEach(function(checkbox) {
            selectedTags += $(checkbox).val() + ",";
        });

        selectedTags = selectedTags.slice(0, -1); // 마지막 콤마 제거

        // formData에 tag_idx_list 값을 설정
        formData.set('tag_idx_list', selectedTags);
        console.log('전송할 태그들 =>', selectedTags);
        
        
        

        // 게시글 에디터 이미지 검증을 위한 코드
        var tempDom = $('<div>').html(content);
        var imgsInEditor = [];

        // 에디터의 이미지 태그에서 new_filename을 추출해 배열에 추가
        tempDom.find('img').each(function () {
            var src = $(this).attr('src');
            if (src && src.includes('/photo-temp/')) {  // 경로 검증을 위해 추가
                var filename = src.split('/').pop();  // 파일명만 추출
                imgsInEditor.push(filename);  // 에디터에 있는 이미지의 new_filename 추출
            }
        });

        // new_filename과 일치하는 항목만 필터링
        var finalImgs = tempImg.filter(function (temp) {
            return imgsInEditor.includes(temp.img_new);  // 에디터에 있는 파일과 tempImg의 new_filename 비교
        });

        console.log("최종 전송할 이미지 쌍:", finalImgs);

        // 최종 이미지 파일명 배열을 JSON으로 변환하여 추가
        formData.append('imgsJson', JSON.stringify(finalImgs));  // new_filename과 일치하는 값만 전

        $.ajax({
            type: 'PUT',
            url: '/crew/update',  // 서버에 전송할 URL
            data: formData,  // formData 객체 전송
            contentType: false,  // formData 사용 시 false로 설정
            processData: false,  // formData 사용 시 false로 설정
            enctype: 'multipart/form-data',  // multipart/form-data 사용
            success: function (response) {
                if(response.success){
	                removeAlert();
	                layerPopup('크루 수정이 완료되었습니다.', '확인', false, locationHrdf, locationHrdf);
                	loadingComplete();
                }
            },
            error: function (e) {
                console.log('글 수정 에러:', e);
                loadingComplete();
            }
        });
    }
    
    function locationHrdf(){
    	location.href="/crewDetail/"+crew_idx; // todo - 수정 필요
    }

	// 팝업 취소
	function applBtn2Act() {
	    removeAlert(); 
	}