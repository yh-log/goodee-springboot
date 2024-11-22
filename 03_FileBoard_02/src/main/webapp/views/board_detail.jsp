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
		<h2 class="title">게시글 보기</h2>
		<div class="subject">
			<h2>${info.subject}</h2>
		</div>
		<div class="etc">
			<p>
				<span class="gray">USER</span>${info.user_name}
				<span class="gray">WROTE WHEN</span>${info.reg_date}
				<span class="gray">VIEWS</span>${info.bHit}
			</p>
		</div>
		<div class="contexts">
			<p>${info.context}</p>
		</div>
		<div class="contexts">
			<c:if test="${files.size() > 0}">
				<c:forEach items="${files}" var="file">
					<p><img src="/photo/${file.new_filename}" alt="${file.ori_filename}" /></p>
				</c:forEach>
			</c:if>
		</div>
		<div class="list">
			<button onclick="location.href='board_delete.do?idx=${info.idx}'" class="full mainbtn">삭제하기</button>
		</div>
		<div class="list">
			<button onclick="location.href='board_list.go'" class="full subbtn">목록으로 돌아가기</button>
		</div>
	</div>
</body>
</html>