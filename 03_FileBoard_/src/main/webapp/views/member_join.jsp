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
		<h2 class="title">회원가입</h2>
		<div class="msg">
			<p>${msg}</p>
		</div>
		<form action="member_join.do" method="post">
			<div class="list">
				<h3 class="capt">ID</h3>
				<p><input type="text" class="full" name="id"/></p>
				<p>
					<button type="button" id="checkId" class="min minbtn">중복 검색</button>
					<span class="checkResult min"></span>
				</p>
			</div>
			<div class="list">
				<h3 class="capt">Passwords</h3>
				<p><input type="password" class="full" name="pw" /></p>
			</div>
			<div class="list">
				<h3 class="capt">Name</h3>
				<p><input type="text" class="full" name="name" /></p>
			</div>
			<div class="list">
				<h3 class="capt">Age</h3>
				<p><input type="text" class="full" name="age" /></p>
			</div>
			<div class="list">
				<h3 class="capt">Gender</h3>
				<p>
					<input type="radio" name="gender" value="남" /> 남
					&nbsp; &nbsp; &nbsp;
					<input type="radio" name="gender" value="여" checked /> 여
				</p>
			</div>
			<div class="list">
				<h3 class="capt">E-mail</h3>
				<p><input type="text" class="full" name="email" /></p>
			</div>
			<div class="list">
				<button type="button" class="full mainbtn" onclick="join()">회원 가입</button>
			</div>
		</form>
	</div>
</body>
<script src="resources/js/member_join.js"></script>
</html>