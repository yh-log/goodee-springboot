<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet" type="text/css" href="resources/css/common.css" />
	<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
	<div class="contents narrow">
		<h2 class="title">로그인</h2>
		
		<div class="msg">
			<p>${msg}</p>
		</div>
		
		<div class="list">
			<h3 class="capt">Identification</h3>
			<p><input type="text" class="full" name="id" value="${id}" placeholder="아이디를 입력하세요." /></p>
		</div>
		<div class="list">
			<h3 class="capt">Passwords</h3>
			<p><input type="password" class="full" name="pw" placeholder="비밀번호를 입력하세요." /></p>
		</div>
		
		<div class="list">
			<button id="login" class="full mainbtn">로그인</button>
		</div>
		
		<div class="list">
			<button onclick="location.href='member_join.go'" class="full subbtn">회원가입</button>
		</div>
	</div>
</body>
<script src="resources/js/member_login.js"></script>
</html>