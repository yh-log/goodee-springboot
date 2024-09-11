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
		<form action="join" method="post">
			<table>
				<tr>
					<th>ID</th>
					<td>
						<input type="text" name="id" value="${info.id}"/>
					</td>
				</tr>
				
				<tr>
					<th>PW</th>
					<td>
						<input type="text" name="pw" value="${info.pw}"/>
					</td>
				</tr>
				
				<tr>
					<th>NAME</th>
					<td>
						<input type="text" name="name" value="${info.name}"/>
					</td>
				</tr>
				
				<tr>
					<th>AGE</th>
					<td>
						<input type="text" name="age" value="${info.age}"/>
					</td>
				</tr>
				
				<tr>
					<th>GENDER</th>
					<td>
						<input type="radio" name="gender" value="남"
						<c:if test="${info.gender eq '남'}">checked</c:if>						
						/> 남
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="gender" value="여"
						<c:if test="${info.gender eq '여'}">checked</c:if>
						/> 여
					</td>
				</tr>
				
				<tr>
					<th>EMAIL</th>
					<td>
						<input type="text" name="email" value="${info.email}"/>
					</td>
				</tr>
				
				<tr>
					<th colspan="2">
						<a href="list">리스트</a>
					</th>
				</tr>
			</table>
		</form>
	</body>
	<script>

	</script>
</html>