<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/common.css" type="text/css">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<style>

</style>
</head>
<body>
	<form action="write" method="post" enctype="multipart/form-data">
		<table>
			<caption>글쓰기</caption>
			<tr>
				<th>작성자</th>
				<td>
					<input type="text" name="user_name" value="admin" readonly/>
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
				<td>
					<input type="file" name="files" multiple/>
				</td>
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