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
<!-- 1. 반드시 post로 보낼것 -->
<!-- 2. enctype= "multipart/fome-data" -->
<jsp:include page="loginbox.jsp"/>
	<h3>환영합니다 ${loginid} 님 </h3>
	
	<form action="write.do" method="post" enctype="multipart/form-data">
		<table>
			<caption>글쓰기</caption>
			<tr>
				<th>작성자</th>
				<td>
					<input type="text" name="user_name" value="${loginid}" readonly="readonly"/>
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
</html>