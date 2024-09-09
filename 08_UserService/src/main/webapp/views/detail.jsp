<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<style>
	table, th, td{
		border: 1px solid;
		border-collapse : collapse;
		padding : 10px 5px;
	}
</style>
</head>
<body>
	<form action="change" method="post">
	<table>
		<tr>
			<th>ID</th>
			<td><input type="text" name="id" value="${detail.id}" readonly/></td>
		</tr>
		<tr>
			<th>PW</th>
			<td><input type="password" name="pw" value="${detail.pw}" readonly/></td>
		</tr>
		<tr>
			<th>NAME</th>
			<td><input type="text" name="name" value="${detail.name}"/></td>
		</tr>
		<tr>
			<th>AGE</th>
			<td><input type="text" name="age" value="${detail.age}"/></td>
		</tr>
		<tr>
			<th>GENDER</th>
			<td> 
				<input type="radio" name="gender" value="남" readonly
				<c:if test="${detail.gender eq '남'}">checked</c:if>/>남
				<input type="radio" name="gender" value="여" readonly
				<c:if test="${detail.gender eq '여'}">checked</c:if>/>여
			</td>
		</tr>
		<tr>
			<th>EMAIL</th>
			<td><input type="email" name="email" value="${detail.email}"/></td>
		</tr>
		<tr>
			<th colspan="2">
				<a href="list"><button>list로 돌아가기</button></a>
				<a href="change"><button>수정/저장하기</button></a>
			</th>
		</tr>
	</table>
	</form>
</body>
</html>