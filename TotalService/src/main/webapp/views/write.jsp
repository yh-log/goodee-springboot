<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Login page</title>
<link rel="stylesheet" href="resources/css/common.css" type="text/css">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<style>
	.preview{
		width: 180px;
	}
</style>
</head>
<body>
	<h2>글쓰기</h2>
	<form action="write" method="post" enctype="multipart/form-data">
		<table>
			<tr>
				<th>작성자</th>
				<td><input type="text" name="user_name" value="${sessionScope.loginId}" readonly/></td>
			</tr>
			<tr>
				<th>제목</th>
				<td><input type="text" name="subject"/></td>
			</tr>
			<tr>
				<th>내용</th>
				<td><textarea name="content"></textarea></td>
			</tr>
			<tr>
				<th>파일</th>
				<td>
					<input type="file" name="files" multiple="multiple" onchange="readFile(this)"/>
					<div id="img_list"></div>
				</td>
			</tr>
			<tr>
				<th colspan="2">
					<button>글쓰기</button>
				</th>
			</tr>
		</table>
	</form>
</body>
<script>
	function readFile(input){
		var reader;
	  $('#img_list').empty();
	  
	  for(var file of input.files){
	  	reader = new FileReader();
	    reader.readAsDataURL(file);
	    reader.onload = function(e){
	    	$('#img_list').append('<img class="preview" src="'+e.target.result+'"/>');
	    }
	  }
	}
</script>

</html>