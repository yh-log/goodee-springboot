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
    
    
    <style>
        body {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            display: flex;
            flex-direction: column;
            min-height: 100vh;
        }

        .container {
            width: 100%;
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
            flex-grow: 1;
        }

        .header {
            text-align: center;
            margin-bottom: 50px;
        }

        .form-container {
            width: 80%;
            margin: 0 auto;
            padding: 40px;
            border: 1px solid #ddd;
            border-radius: 10px;
            background-color: white;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        label {
            display: block;
            font-size: 16px;
            margin-bottom: 10px;
            color: #333;
        }

        input[type="text"], select, textarea {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }

        .buttons {
            display: flex;
            justify-content: space-between;
            margin-top: 20px;
        }

        button {
            padding: 10px 20px;
            font-size: 16px;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .cancel-btn {
            background-color: #aaa;
        }

        .submit-btn {
            background-color: #fb7e3a;
        }

        .submit-btn:hover {
            background-color: #f56320;
        }

        .cancel-btn:hover {
            background-color: #888;
        }

        footer {
            width: 100%;
            background-color: #f8f9fa;
            padding: 40px;
            text-align: left;
            margin-top: auto;
            display: flex;
            justify-content: space-between;
            align-items: start;
        }

        footer .footer-container {
            display: flex;
            justify-content: space-between;
            width: 100%;
            max-width: 1200px;
            margin: 0 auto;
        }

        footer .logo {
            font-size: 28px;
            font-weight: bold;
        }

        footer .footer-section {
            margin-left: 40px;
        }

        footer .footer-section h4 {
            font-size: 18px;
            font-weight: bold;
            margin-bottom: 10px;
        }

        footer .footer-section p {
            color: #777;
            font-size: 14px;
            margin-bottom: 5px;
        }

        footer .footer-section a {
            text-decoration: none;
            color: #333;
            font-size: 14px;
        }

        footer .footer-section a:hover {
            color: #fb7e3a;
        }

        .top-menu {
            background-color: #ffffff;
            padding: 10px;
            box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1);
            position: fixed;
            top: 0;
            width: 100%;
            z-index: 1000;
        }

        .top-menu ul {
            list-style-type: none;
            padding: 0;
            margin: 0;
            display: flex;
            justify-content: center;
            gap: 30px;
        }

        .top-menu li {
            display: inline;
            font-size: 18px;
            color: #333;
        }

        .logo {
            font-size: 28px;
            font-weight: bold;
            margin-right: 50px;
        }

        .top-menu li a {
            text-decoration: none;
            color: #333;
        }

        .top-menu li a:hover {
            color: #fb7e3a;
        }

        .content-padding {
            padding-top: 100px;
        }

    </style>
</head>
<body>

<!-- 헤더 영역 -->
<div class="top-menu">
    <div class="container">
        <span class="logo">ARCHIVO</span>
        <ul>
            <li><a href="#">러닝크루</a></li>
            <li><a href="#">러닝메이트</a></li>
            <li><a href="#">게시판</a></li>
            <li><a href="#">아이콘몰</a></li>
            <li><a href="#">문의하기</a></li>
        </ul>
    </div>
</div>

<!-- 컨텐츠 영역 -->
<div class="container content-padding">
    <div class="header">
        <h1>크루 공지사항 등록</h1>
    </div>

    <div class="form-container">
        <form>
            <label for="title">제목</label>
            <input type="text" id="title" name="title" placeholder="제목을 입력하세요">

            <label for="priority">공지 순위</label>
            <select id="priority" name="priority">
            	<option value="0">일반</option>
                <option value="1">1 순위</option>
                <option value="2">2 순위</option>
                <option value="3">3 순위</option>
            </select>




            <label for="description">크루 설명</label>
     <!--        <textarea id="description" name="description" rows="10"></textarea> -->

			<div class="post-form">
				<textarea name="postContent" id="summernote"></textarea>
			</div>  


            <div class="buttons">
                <button type="button" class="cancel-btn">등록 취소하기</button>
                <button type="submit" class="submit-btn">공지사항 등록</button>
            </div>
        </form>
    </div>
</div>

<!-- 푸터 영역 -->
<footer>
    <div class="footer-container">
        <div class="logo">ARCHIVO</div>
        <div class="footer-section">
            <h4>MENU</h4>
            <p><a href="#">러닝크루</a></p>
            <p><a href="#">러닝메이트</a></p>
            <p><a href="#">게시판</a></p>
            <p><a href="#">아이콘몰</a></p>
            <p><a href="#">문의하기</a></p>
        </div>
        <div class="footer-section">
            <h4>MY</h4>
            <p><a href="#">나의 크루</a></p>
            <p><a href="#">나의 러닝메이트</a></p>
            <p><a href="#">대화방</a></p>
            <p><a href="#">나의 프로필</a></p>
        </div>
        <div class="footer-section">
            <h4>CUSTOMER CENTER</h4>
            <p>E-MAIL : info@korbit.co.kr | TEL : 070-0707-0707</p>
            <p>운영시간 : 평일 09:00-18:00 (주말 및 공휴일 휴무)</p>
            <p>주소 : 서울 강남구 은달래로 19 스퀘어9</p>
        </div>
    </div>
    <div class="footer-container">
        <p>Copyright © ARCHIVO All Rights Reserved.</p>
    </div>
</footer>

</body>

<script>	
	$('#summernote').summernote({
	      
		  // 에디터 크기 설정
		  height: 800,
		  // 에디터 한글 설정
		  lang: 'ko-KR',
		  // 에디터에 커서 이동 (input창의 autofocus라고 생각하시면 됩니다.)
		  toolbar: [
			    // 글자 크기 설정
			    ['fontsize', ['fontsize']],
			    // 글자 [굵게, 기울임, 밑줄, 취소 선, 지우기]
			    ['style', ['bold', 'italic', 'underline','strikethrough', 'clear']],
			    // 글자색 설정
			    ['color', ['color']],
			    // 표 만들기
			    ['table', ['table']],
			    // 서식 [글머리 기호, 번호매기기, 문단정렬]
			    ['para', ['ul', 'ol', 'paragraph']],
			    // 줄간격 설정
			    ['height', ['height']],
			    // 이미지 첨부
			    ['insert',['picture']]
			  ],
			  // 추가한 글꼴
			fontNames: ['Arial', 'Arial Black', 'Comic Sans MS', 'Courier New','맑은 고딕','궁서','굴림체','굴림','돋음체','바탕체'],
			 // 추가한 폰트사이즈
			fontSizes: ['8','9','10','11','12','14','16','18','20','22','24','28','30','36','50','72','96'],
	        // focus는 작성 페이지 접속시 에디터에 커서를 위치하도록 하려면 설정해주세요.
			focus : true,
	        // callbacks은 이미지 업로드 처리입니다.
			callbacks : {                                                    
				onImageUpload : function(files, editor, welEditable) {   
	                // 다중 이미지 처리를 위해 for문을 사용했습니다.
					for (var i = 0; i < files.length; i++) {
						imageUploader(files[i], this);
					}
				}
			}
			
	  });
  
  
  
	function imageUploader(file, el) {
		var formData = new FormData();
		formData.append('file', file);
	  
		$.ajax({                                                              
			data : formData,
			type : "POST",
	        // url은 자신의 이미지 업로드 처리 컨트롤러 경로로 설정해주세요.
			url : '/image-upload',  
			contentType : false,
			processData : false,
			enctype : 'multipart/form-data',                                  
			success : function(data) {   
				$(el).summernote('insertImage', "${pageContext.request.contextPath}/assets/images/upload/"+data, function($image) {
					$image.css('width', "100%");
				});
	            // 값이 잘 넘어오는지 콘솔 확인 해보셔도됩니다.
				console.log(data);
			}
		});
	}
	
	

</script>

</html>

 