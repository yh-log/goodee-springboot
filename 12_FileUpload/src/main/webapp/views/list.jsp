<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/common.css" type="text/css">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<style>
	.cen{
		text-align: center;
	}
	a{
		text-decoration-line: none;
		color: black;
	}
</style>
</head>
<body>
		<h3>게시글 리스트</h3>
	<button onclick="location.href='writeForm'">글 쓰기</button> 
	<table>
		<thead>
			<tr>
				<th>글번호</th>
				<th>회원명</th>
				<th>제목</th>
				<th>조회수</th>
				<th>작성일</th>
				<th>삭제</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${list}" var="board"> <!-- C태그는 돔 객체여서 자식 요소로 인정 x -->
			<tr>
				<td class="cen">${board.idx}</td>
				<td>${board.user_name}</td>
				<td><a href="detail?idx=${board.idx}">${board.subject}</a></td>
				<td class="cen">${board.bHit}</td>
				<td>${board.reg_date}</td>
				<td><button><a href="del?idx=${board.idx}">삭제</a></button></td> <!-- get 방식으로 보내겠다는 의미! -->
			</tr>
		</c:forEach>
		</tbody>
	</table>

</body>
<script>
	var msg = '${msg}';
	if(msg != ''){
		alert(msg);
	}
</script>
</html>