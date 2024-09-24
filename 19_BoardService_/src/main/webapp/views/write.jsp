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
				<td><input type="file" name="files" multiple="multiple" onchange="readFile(this)"/>
				<div id="img_list"></div></td>
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

		var form = new FormData($('form')[0]); 
		
		$.ajax({
			type: 'POST',
			url: 'write.ajax',
			enctype: 'multipart/form-data', 
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

	function readFile(input){
		console.log(input.files); 
		
		var reader;
		$('#img_list').empty(); 
		
		for (var file of input.files) { 
			reader = new FileReader(); 
			reader.readAsDataURL(file); 
			reader.onload = function(e){ 
				//console.log(e);
				$('#img_list').append('<img class="preview" src="'+e.target.result+'"/>');
			}
		}
	}

</script>
</html> 

<%-- <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- controller 에 css 라는 요청이 없으면 static 폴더 밑에 css 찾아라 -->
<link rel="stylesheet" href="css/common.css" type="text/css">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<style>

</style>
</head>
<body>
	<!-- 파일 업로드 요청 조건 -->
	<!-- 1. 반드시 post 로 보낼것 -->
	<!-- 2. enctype = "multipart/form-data" 로 해줘야 한다. -->
	<form action="write.do" method="post" enctype="multipart/form-data">
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
					<input type="text" name="subject"/>
				</td>
			</tr>
			<tr>
				<th>내용</th>
				<td>
					<textarea name="content"></textarea>
				</td>
			</tr>
			<tr>
				<td>이미지</td>
				<th><input type="file" name="files" multiple="multiple"/></th>
			</tr>
			<tr>
				<th colspan = "2">
				  <input type="submit" value="글쓰기"/>
				</th>
			</tr>
		</table>
	</form>
</body>
<script>

</script>
</html> --%>