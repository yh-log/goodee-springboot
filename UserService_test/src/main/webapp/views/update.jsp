<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/table.css" type="text/css">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<style>
</style>
</head>
<body>
	<h2>Update</h2>
	<div class='container'>
	<form action="update" method="post">
		<input type="hidden" name="idx" value="${detail.idx}"/> <!-- 데이터를 전송하기 위해 필요하지만 사용자에게 보여질 필요는 없기 때문에 hidden으로 설정 -->
		<table>
			<tr>
				<th>user</th>
				<td>
					<input type="text" name="user_name" value="${detail.user_name}" readonly/>
				</td>
			</tr>
			<tr>
				<th>Title</th>
				<td>
					<input type="text" name="subject" value="${detail.subject}"/>
				</td>
			</tr>
			<tr>
				<th>Content</th>
				<td>
					<textarea name="content">${detail.content}</textarea>
				</td>
			</tr>
			<tr>
				<th colspan = "2">
				  <input type="submit" value="Update"/>
				</th>
			</tr>
		</table>
	</form>
	<button onclick='location.href="boardlist"'>List Back</button>
	</div>
</body>
</html>