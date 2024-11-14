 <!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>크루 공지사항 등록</title>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    
    <!-- 썸머노트 적용을 위함 -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
	
	<!-- include summernote css/js -->
	<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
	<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>
	
	<script src="/js/summernote.js"></script>
	<script src="/js/mapapi.js"></script>
    
    <!-- Daum 우편번호 API -->
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
   
	<style>
   		div{
   			padding: 5px;
   		}
    </style>
    
    
</head>
<body>

	<h3>summernote Test</h3>
	<form id="myForm">
		<div>크루명  <input type="text" name="subject"/></div>
		<div>이미지 <input type="file" name="crewImg"/></div>
		<div>태그
			<input type="checkbox" name="tags" value="1"/>1번 태그
			<input type="checkbox" name="tags" value="2"/>2번 태그
			<input type="checkbox" name="tags" value="3"/>3번 태그
			<input type="checkbox" name="tags" value="4"/>4번 태그
			<input type="checkbox" name="tags" value="5"/>5번 태그
			<input type="checkbox" name="tags" value="6"/>6번 태그 <br/> <!-- 여기까지 첫번째 줄에 위치 -->
			<input type="checkbox" name="tags" value="7"/>7번 태그
			<input type="checkbox" name="tags" value="8"/>8번 태그
			<input type="checkbox" name="tags" value="9"/>9번 태그
			<input type="checkbox" name="tags" value="10"/>10번 태그
			<input type="checkbox" name="tags" value="11"/>11번 태그
		</div>
		<div>요일
			<input type="checkbox" name="days" value="mon"/>월
			<input type="checkbox" name="days" value="tue"/>화
			<input type="checkbox" name="days" value="wed"/>수
			<input type="checkbox" name="days" value="thu"/>목
			<input type="checkbox" name="days" value="fri"/>금
			<input type="checkbox" name="days" value="sat"/>토
			<input type="checkbox" name="days" value="sun"/>일
		</div>
		<div>인원 <input type="number" name="person" min="2" value="2"/></div>
		
		<div>운동 강도
			<input type="number" name="minute" value="1" class="intensity"/> 분 / <input type="number" name="km" value="1" class="intensity"/> km
		</div>
		
		<div> 지역
			<input type="text" id="sample4_roadAddress" placeholder="도로명주소">
			<input type="button" onclick="search()" value="주소 찾기"><br/>
		</div>
		
		<div class="post-form">
			<textarea name="postContent" id="summernote" maxlength="10000" ></textarea>
		</div>
		<a href="#"><button type="button">등록 취소하기</button></a>
		<button type="button" onclick="submitPost()">러닝크루 등록</button>
		
	</form>

</body>

<script>
	
	   var dayCheckboxes = [];  // 선택된 요일 체크박스를 추적할 배열

	    // 모든 체크박스에 change 이벤트 리스너 추가
	    $('input[name="days"]').on('change', function() {
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

		    // 모든 체크박스에 change 이벤트 리스너 추가
		    $('input[name="tag-button"]').on('change', function() {
		        if ($(this).is(':checked')) {
		            if (tagCheckboxes.length >= 3) {
		                var firstChecked = tagCheckboxes.shift();
		                $(firstChecked).prop('checked', false);
		            }
		            tagCheckboxes.push(this);
		        } else {
		            tagCheckboxes = tagCheckboxes.filter(item => item !== this);
		        }
		    });
		   
	
    function submitPost() {
    	// formData 생성
    	var formData = new FormData($('form')[0]); 
		var content =  $('#summernote').summernote('code');
    	
    	
    	formData.append('user_name', 'admin'); // 세션값 체크해서 넣어줘야 함!
    	formData.append('subject', $('#subject').val());  // 제목 추가
    	formData.append('content', content);  // summernote의 HTML 내용 추가 (이미지 포함)
    	
        formData.append('roadAddr', roadAddr); // 화면에 출력
        formData.append('sigungu', sigungu);
        formData.append('sido', sido);
        formData.append('shotsido', shotsido);
      
    	
        // 게시글 에디터 이미지 검증을 위한 코드
	    var tempDom = $('<div>').html(content);
	    var imgsInEditor = [];

	    // 에디터의 이미지 태그에서 new_filename을 추출해 배열에 추가
	    tempDom.find('img').each(function() {
	        var src = $(this).attr('src');
	        if (src && src.includes('/photo-temp/')) {  // 경로 검증을 위해 추가
	            var filename = src.split('/').pop();  // 파일명만 추출
	            imgsInEditor.push(filename);  // 에디터에 있는 이미지의 new_filename 추출
	        }
	    });

	    // new_filename과 일치하는 항목만 필터링
	    var finalImgs = tempImg.filter(function(temp) {
	        return imgsInEditor.includes(temp.new_filename);  // 에디터에 있는 파일과 tempImg의 new_filename 비교
	    });
	    
	    console.log("최종 전송할 이미지 쌍:", finalImgs);

	    
	    // 최종 이미지 파일명 배열을 JSON으로 변환하여 추가
	    formData.append('imgsJson', JSON.stringify(finalImgs));  // new_filename과 일치하는 값만 전

 	    $.ajax({
	        type: 'POST',
	        url: '/file/submit',  // 서버에 전송할 URL
	        data: formData,  // formData 객체 전송
	        contentType: false,  // formData 사용 시 false로 설정
	        processData: false,  // formData 사용 시 false로 설정
	        enctype: 'multipart/form-data',  // multipart/form-data 사용
	        success: function(response) {
	            console.log('글 전송 성공:', response);
	        },
	        error: function(e) {
	            console.log('글 전송 에러:', e);
	        }
	    }); 
    }

</script>


</html>