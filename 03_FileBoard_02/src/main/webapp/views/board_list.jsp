<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet" type="text/css" href="resources/css/common.css" />
	<link rel="stylesheet" type="text/css" href="resources/css/board.css" />
	<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
	<div class="header full">
		${loginBox}
	</div>
	<div class="contents full">
		<h2 class="title">게시판</h2>
		<div class="msg">
			<p>${msg}</p>
		</div>
		<table class="full">
			<colgroup>
				<col width="80px" />
				<col width="auto" />
				<col width="120px" />
				<col width="80px" />
				<col width="120px" />
			</colgroup>
			<thead>
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>글쓴이</th>
					<th>조회수</th>
					<th>등록일</th>
				</tr>
			</thead>
			<tbody></tbody>
		</table>
		<button onclick="location.href='board_write.go'" class="full mainbtn">게시글 쓰기</button>
	</div>
</body>
<script src="resources/js/board_list.js"></script>
</html>