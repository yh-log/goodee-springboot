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
	<form action="update" method="post">
		<input type="hidden" name="idx" value="${info.idx}"/> <!-- 데이터를 전송하기 위해 필요하지만 사용자에게 보여질 필요는 없기 때문에 hidden으로 설정 -->
		<table>
			<caption>수정하기</caption>
			<tr>
				<th>작성자</th>
				<td>
					<input type="text" name="user_name" value="${info.user_name}" readonly/>
				</td>
			</tr>
			<tr>
				<th>제목</th>
				<td>
					<input type="text" name="subject" value="${info.subject}"/>
				</td>
			</tr>
			<tr>
				<th>내용</th>
				<td>
					<textarea name="content">${info.content}</textarea>
				</td>
			</tr>
			<tr>
				<th colspan = "2">
				  <input type="submit" value="수정"/>
				</th>
			</tr>
		</table>
	</form>
</body>
<script>

</script>
</html>