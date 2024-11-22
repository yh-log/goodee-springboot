<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet" type="text/css" href="resources/css/common.css" />
	<link rel="stylesheet" type="text/css" href="resources/css/board.css" />
	<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
	<style></style>
</head>
<body>
	<div class="header full">
		<p>안녕하세요, ${sessionScope.loginId}님! <a href="member_logout.do" class="btn minbtn">로그아웃</a></p>
	</div>
	<div class="contents full">
		<h2 class="title">게시글 쓰기</h2>
		<div class="msg">
			<p>${msg}</p>
		</div>
		<form action="board_write.do" method="post">
			<div class="list">
				<h3 class="capt">Name</h3>
				<p><input type="text" class="full" name="user_name" value="${sessionScope.loginId}" /></p>
			</div>
			<div class="list">
				<h3 class="capt">Subject</h3>
				<p><input type="text" class="full" name="subject" value="title" placeholder="제목을 입력하세요." /></p>
			</div>
			<div class="list">
				<h3 class="capt">Contents</h3>
				<p>
					<textarea class="full" name="context" rows="20" cols="79" placeholder="내용을 입력하세요.">Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface without relying on meaningful content.</textarea>
				</p>
			</div>
			<div class="list">
				<input type="button" onclick="save()" value="작성하기" class="full mainbtn" />
			</div>
			<div class="list">
				<button type="button" onclick="location.href='redirect:/board_list.go'" class="full subbtn">목록으로 돌아가기</button>
			</div>
		</form>
	</div>
</body>
<script src="resources/js/board_write.js"></script>
</html>