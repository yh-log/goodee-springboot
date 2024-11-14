	var crew_idx = '';
	var loginId = $('input[name="loginId"]').val();

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


    var dayCheckboxes = [];  // 선택된 요일 체크박스를 추적할 배열

    // 모든 체크박스에 change 이벤트 리스너 추가
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

    
    var tagCheckboxes = [];  // 선택된 태그 체크박스를 추적할 배열

	// 부모 요소인 #tagFilters에 이벤트 위임
	$('#tagFilters').on('change', 'input[name="tag_idx_list"]', function () {
	    if ($(this).is(':checked')) {
	        if (tagCheckboxes.length >= 3) {
	            var firstChecked = tagCheckboxes.shift(); // 배열에서 첫 번째 체크박스를 제거
	            $(firstChecked).prop('checked', false); // 첫 번째 체크박스 해제
	            $(firstChecked).parent().removeClass('checked'); // 해당 체크박스의 label에서 'checked' 클래스 제거
	        }
	        tagCheckboxes.push(this); // 새로운 체크박스를 배열에 추가
	        $(this).parent().addClass('checked'); // 현재 체크박스의 label에 'checked' 클래스 추가
	    } else {
	        tagCheckboxes = tagCheckboxes.filter(item => item !== this); // 선택 해제된 체크박스를 배열에서 제거
	        $(this).parent().removeClass('checked'); // label에서 'checked' 클래스 제거
	    }
	});
	    
    function writeCheck(){
    	
    	if($('input[name="crew_name"]').val() !== '' && $('input[name="crew_img"]').val() !== '' && dayCheckboxes.length >= 1 && tagCheckboxes.length >= 1 && $('input[name="member"]').val() !== '' && $('input[name="minute"]').val() !== '' && $('input[name="distance"]').val() !== '' && $('input[name="address"]').val() !== ''){
    		layerPopup('크루를 등록하시겠습니까?', '확인', '취소', submitPost, applBtn2Act)
    		console.log('글 전송 함수 실행');
    	}else{
    		layerPopup('필수 정보를 입력해주세요', '확인', false, applBtn2Act, applBtn2Act);
    	}
    	
    }

    function submitPost() {
    	loading();
        // formData 생성
        var formData = new FormData($('form')[0]);

        var content = $('#summernote').summernote('code');

        var fileInput = $('input[type="file"]')[0]; // 파일 input에서 파일 가져오기
        if (fileInput.files.length > 0) {
            formData.append('crew_img', fileInput.files[0]); // 파일 데이터 추가
            console.log(fileInput);
        }

        formData.append('id', loginId); 
        formData.append('content', content);  // summernote의 HTML 내용 추가 (이미지 포함)

        formData.append('address', roadAddr); // 화면에 출력
        formData.append('sigungu', sigungu);
        formData.append('sido', sido);
        formData.append('shortsido', shortsido);

        console.log('tagCheckboxes=>', tagCheckboxes);
        console.log('roadAddr =>', roadAddr);
        console.log('sigungu =>', sigungu);
        console.log('sido =>', sido);
        console.log('shortsido =>', shortsido);

        var selectedTags = "";
        $('input[name="tag_idx_list"]:checked').each(function () {
            selectedTags += $(this).val() + ",";
        });

        selectedTags = selectedTags.slice(0, -1);

        // tag_idx_list 다시 set
        formData.set('tag_idx_list', selectedTags);

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
            type: 'POST',
            url: '/crew/write',  // 서버에 전송할 URL
            data: formData,  // formData 객체 전송
            contentType: false,  // formData 사용 시 false로 설정
            processData: false,  // formData 사용 시 false로 설정
            enctype: 'multipart/form-data',  // multipart/form-data 사용
            success: function (response) {
                if(response.success){
                	console.log('크루 생성 성공');
                	removeAlert();
                	loadingComplete();
                	layerPopup('크루 등록이 완료되었습니다.', '확인', false, locationCrewList, locationCrewList); 
                }
            },
            error: function (e) {
                console.log('글 전송 에러:', e);
                loadingComplete();
            }
        });
    }
    
    function locationCrewList(){
    	removeAlert(); 
    	location.href="/crewList";
    }
    
    
	// 팝업 취소
	function applBtn2Act() {
	    removeAlert(); 
	}