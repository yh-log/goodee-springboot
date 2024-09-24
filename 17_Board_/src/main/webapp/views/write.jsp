<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/common.css" type="text/css">
<!-- controller에 css라는 요청이 없으면 static 폴더 밑에 css를 찾아라 -->
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<style>
	img.preview{
		width: 100px;
		magin: 3px;
		cursor: pointer;
	}
</style>
</head>
<body>
	<form>
	<!-- <form action="write.do" method="post" enctype="multipart/form-data"> --> <!-- ajax를 사용하기 때문에 action method 등 사용 x -->
		<table>
			<caption>글쓰기</caption>
			<tr>
				<th>작성자</th>
				<td>
					<input type="text" name="user_name"/>
				</td>
			</tr>
			<tr>
				<th>제목</th>
				<td>
					<input type="text" name="subject" />
				</td>
			</tr>
			<tr>
				<th>내용</th>
				<td>
					<textarea name="content"></textarea>
				</td>
			</tr>
			<tr>
				<th>파일</th> <!-- file 업로드 시 미리보기 제공해주는 코드 -->
				<td><input type="file" name="files" multiple="multiple" onchange="readFile(this)"/>
				<div id="img_list"></div></td>
			</tr>
			<tr>
				<th colspan = "2">
				  <input type="button" value="글 쓰기" onclick="save()"/><!-- ajax를 사용하기 때문에 submit 사용 x-->
				</th>
			</tr>
		</table>
	</form>
</body>
<script>

	function save(){
		
		// ajax로 파일을 업로드 하기 위해서는 FormData 객체가 필요하다.
		// IE8 버전에서는 동작하지 않으니, 가급적이면 사용하지 않는것이 좋다. (모바일페이지는 이런 구형브라우저를 신경쓰지 않아도 된다. 2011년에 쓴 IE8을 쓰는 pc는 있어도 핸드폰은 없으니까(그리고 모바일은 원격 업로드를 해줘서 더 그럼))

		var form = new FormData($('form')[0]); // FormData는 자바스크립트 객체이다. / 셀렉터는 무조건 배열로 가져온다 => 그래서 자바에서 쓸 때에는 반드시 인덱스를넣어줘야 한다.
		
		$.ajax({
			type: 'POST',
			url: 'write.ajax',
			enctype: 'multipart/form-data', // file 이기 때문에 지정을 해줘야 한다.
			processData: false,
			contentType: false,
			data: form,
			dataType: 'JSON',
			success: function(data){
				console.log(data);
				if(data.success){
					location.href=data.link;
				}
			},error: function(e){
				console.log(e);
			}
		});
	}









// => 이렇게 한번에 글과 파일을 다 보내주는 경우 ajax를 쓸 필요는 없다.
// 		하지만 글은 글대로 올리고 파일은 나중에 올리는 경우에는 ajax가 필요하다.
//			-> 하지만 권고하지는 않음 (구형에서 안됨!)


// 이거는 ajax와 관계 없이 미리보기를 제공하는 기능 
	function readFile(input){
		console.log(input.files); // .files : 파일에 대한 정보를 알 수 있다.(이름, type, size)
		
		var reader;
		$('#img_list').empty(); // 이미지를 다시 업로드 할 경우 삭제되고 다시 올릴 수 있도록 설정
		
		for (var file of input.files) { // forof 를 사용 (file이 여러개이기 때문에 for문 사용)
			reader = new FileReader(); // 파일리더 : 파일객체부터 바이너리를 읽어올 수 있는 객체
			reader.readAsDataURL(file); // 파일객체로부터 DATA-URL 형태로 바이너리를 읽어온다. (바이너리는 눈으로 볼 수 없지만 이건 볼 수 있다.)
			reader.onload = function(e){ // 파일 리더가 파일을 다 읽었다면..
				//console.log(e);
				$('#img_list').append('<img class="preview" src="'+e.target.result+'"/>');
			}
		}
	}

</script>
</html>