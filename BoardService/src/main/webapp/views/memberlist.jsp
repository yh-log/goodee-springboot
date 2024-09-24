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
	<h2>Member List</h2>
	<table class="container">
		<thead>
			<tr>
				<th>ID</th>
				<th>Password</th>
				<th>Name</th>
				<th>Age</th>
				<th>Gender</th>
				<th>Email</th>
				<th>Delete</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}" var="member">
				<tr>
					<td>${member.id}</td>
					<td>${member.pw}</td>
					<td>${member.name}</td>
					<td>${member.age}</td>
					<td>
					<input type="radio" name="gender" value="남" 
					<c:if test="${info.gender eq '남'}">checked</c:if>
					/>남
					&nbsp; &nbsp; &nbsp; &nbsp;
					<input type="radio" name="gender" value="여"
					<c:if test="${info.gender eq '여'}">checked</c:if>
					/>여
						</td>
					<td>${member.email}</td>
					<td><button><a href="member_del?id=${member.id}">Delete</a></button></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
</body>
</html>