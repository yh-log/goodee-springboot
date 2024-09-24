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
		width:100px;
		margin:3px;
		cursor: pointer;
	}
		
</style>
</head>
<body>
	<!-- <form action="write.do" method="post" enctype="multipart/form-data"> -->
	<form>
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
				<th>파일</th>
				<td>
					<input type="file" name="files" multiple="multiple" onchange="readFile(this)"/>
					<div id="img_list"></div>
				</td>
			</tr>
			
			<tr>
				<th colspan = "2">
				  <input type="button" value="글 쓰기" onclick="save()"/>
				</th>
			</tr>
		</table>
	</form>
</body>
<script>
	function save(){
		
		
		// ajax 로 파일을 업로드 하기 위해서는 FormData 객체가 필요 하다.
		// IE8 버전에서는 동작하지 않으니, 가급적이면 사용하지 않는것이 좋다.
		var form = new FormData($('form')[0]);
		
		
		$.ajax({
			type:'POST',
			url:'write.ajax',
			enctype:'multipart/form-data',
			processData:false,
			contentType:false,
			data:form,
			dataType:'JSON',
			success:function(data){
				console.log(data);
				if (data.success) {
					location.href = data.link
				}
			},
			error:function(e){
				console.log(e);
			}
		});
	}



	function readFile(input){
		console.log(input.files);
		var reader;
		$('#img_list').empty();
		
		
		for (var file of input.files) {
			reader = new FileReader(); // 파일리더,파일객체로 부터 바이너리를 읽어올수 있는 객체
			reader.readAsDataURL(file); //파일객체로 부터 DATA-URL 형태로 바이너리를 읽어온다.
			reader.onload = function(e){// 리더가 파일을 다 읽었다면...
			//	console.log(e);
			$('#img_list').append('<img class="preview" src="'+e.target.result+'"/>');
			}
		}
	}

</script>

</html>