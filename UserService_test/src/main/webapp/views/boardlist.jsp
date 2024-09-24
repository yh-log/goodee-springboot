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
	<h2>List</h2>
	<button onclick='location.href="writeForm"'>글쓰기</button>
	<div class='container'>
	<table>
		<thead>
			<tr>
				<th>no</th>
				<th>user</th>
				<th>Title</th>
				<th>조회수</th>
				<th>Data</th>
				<th>Delete</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${boardlist}" var="board"> <!-- C태그는 돔 객체여서 자식 요소로 인정 x -->
			<tr>
				<td class="cen">${board.idx}</td>
				<td>${board.user_name}</td>
				<td><a href="detail?idx=${board.idx}">${board.subject}</a></td>
				<td class="cen">${board.bHit}</td>
				<td>${board.reg_date}</td>
				<td><button onclick='location.href ="boarddel?idx=${board.idx}"'>Delete</button></td> 
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
</body>
</html>