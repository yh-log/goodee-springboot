<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>  <!-- JSP 파일을 UTF-8로 인코딩 -->
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />  <!-- 문서의 문자 인코딩을 UTF-8로 설정 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />  <!-- 최신 브라우저 엔진을 사용하도록 설정 -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />  <!-- 반응형 웹 페이지를 위한 설정 -->
    <title>Document</title>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script> <!-- jQuery를 사용하기 위한 CDN 링크 -->
	<!-- Daum 우편번호 API -->
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<style>
		input[type="text"]{
			width: 50%;
		}
		
	</style>


</head>
<body>

	<h2>다음 지도 api 사용</h2>
	
	<input type="text" id="sample4_roadAddress" placeholder="도로명주소">
	<input type="button" onclick="search()" value="주소 찾기"><br/>

</body>


<script>


	
	function search(){
		var themeObj = {
			postcodeTextColor: "#FB7E3A", //우편번호 글자색 수정
		};	
		new daum.Postcode({
			theme: themeObj,
		    oncomplete: function(data) {
		        
		        // 도로명 주소
		        var roadAddr = data.roadAddress;
		        $('#sample4_roadAddress').val(roadAddr);
		        
		        // 구, 동
		        var sigungu = data.sigungu;
		        
		        // 시/도
		        var sido = data.sido; // 나중에 앞에 2글자만 짤라서 사용

		        // console.log("data =>", data);
		        console.log("roadAddr =>", roadAddr);
		        console.log("sido =>", sido);
		        console.log("sigungu =>", sigungu);
		        
		        var shotsido = sido.substring(0, 2);
		        console.log("shotsido =>", shotsido);
		        
		        
		    }
		}).open();
	};

</script>

</html>
