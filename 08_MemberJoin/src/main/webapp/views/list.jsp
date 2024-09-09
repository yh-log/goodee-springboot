<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<style>
	table, th, td{
		border: 1px solid black;
		border-collapse: collapse;
		padding: 5px 10px;
	}	
</style>
</head>
<body>
	<jsp:include page="loginBox.jsp"/>
	<h3>회원 리스트</h3>
	
	<table>
		<thead>
			<tr>
				<th>ID</th>
				<th>이름</th>
				<th>나이</th>
				<th>삭제</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${list}" var="member"> <!-- C태그는 돔 객체여서 자식 요소로 인정 x -->
			<tr>
				<td>${member.id}</td>
				<td><a href="detail?id=${member.id}">${member.name}</a></td>
				<td>${member.age}</td>
				<td><a href="del?id=${member.id}">삭제</a></td> <!-- get 방식으로 보내겠다는 의미! -->
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
<script>

</script>
</html>