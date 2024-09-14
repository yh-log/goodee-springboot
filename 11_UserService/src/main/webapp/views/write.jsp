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
	<div class="login-wrapper">
		<h2>Join</h2>
		<form action="upload" method="post" id="login-Form"  enctype="multipart/form-data">
		<table>
			<tr>
				<th>ID</th>
				<td>
					<input type="text" name="user_name" value="${sessionScope.loginId}" readonly/>
				</td>
			</tr>
			<tr>
				<th>제목</th>
				<td>
					<input type="text" name="subject" placeholder="Title.."/>
				</td>
			</tr>
			<tr>
				<th>내용</th>
				<td>
					<input type="text" name="content" placeholder="작성.."/>
				</td>
			</tr>
			<tr>
				<th>이미지</th>
				<td>
					<input type="file" name="files" multiple/>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<button>게시</button>
				</td>
			</tr>
		</table>
		</form>
	</div>
</body>
</html>