<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<!-- 1. 반드시 post로 보낼 것 (파일명이 길어지면 get방식은 갯수제한이 있기 때문에 파일이 깨질 수 있다.) -->
<!-- 2. enctype = "multipart/form-data" -->
	<form action="write" method="post" enctype="multipart/form-data">
		<table>
			<caption>글쓰기</caption>
			<tr>
				<th>작성자</th>
				<td>
					<input type="text" name="user_name" />
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
				<th>이미지</th>
				<td><input type="file" name="files" multiple="multiple"/></td>
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
</html>