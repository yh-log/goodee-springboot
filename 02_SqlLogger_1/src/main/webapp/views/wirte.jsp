<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
</head>
<body>
	<h3>글쓰기</h3>
	<form action="write.do" method="post">
		<table>
			<tr>
				<th>제목</th>
				<td><input type="text" name="subject"/></td>
			</tr>
			<tr>
				<th>작성자</th>
				<td><input type="text" name="user_name"/></td>
			</tr>
			<tr>
				<th>내용</th>
				<td><textarea name="content"></textarea></td>
			</tr>
			<tr>
				<th colspan="2">
					<button>저장</button>
				</th>
			</tr>		
		</table>
	</form>
</body>
<script>
</script>
</html>