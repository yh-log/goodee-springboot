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
		<h3>회원 리스트</h3>
		<button onclick="location.href='writeForm'">글쓰기</button>
		<table>
			<thead>
				<tr>
					<th>게시판 번호</th>
					<th>제목</th>
					<th>작성자</th>
					<th>작성일</th>
					<th>조회수</th>
					<th>삭제</th>
				</tr>
			</thead>
			
			<tbody>
			<c:forEach items="${list}" var="bbs">
				<tr>
					<td>${bbs.idx}</td>
					<td><a href="detail?idx=${bbs.idx}">${bbs.subject}</a></td>
					<td>${bbs.user_name}</td>
					<td>${bbs.reg_date}</td>
					<td>${bbs.bHit}</td>
					<td><a href="del?idx=${bbs.idx}">삭제</a></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</body>
	<script>

	</script>
</html>