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
	<form action="join" method="post">
		<table>
			<tr>
				<th>ID</th>
				<td><input type="text" name="id"/></td>
			</tr>
			<tr>
				<th>PW</th>
				<td><input type="password" name="pw"/></td>
			</tr>
			<tr>
				<th>NAME</th>
				<td><input type="text" name="name"/></td>
			</tr>
			<tr>
				<th>AGE</th>
				<td><input type="text" name="age"/></td>
			</tr>
			<tr>
				<th>GENDER</th>
				<td>
					<input type="radio" name="gender" value="남"/>남
					<input type="radio" name="gender" value="여"/>여
				</td>
			</tr>
			<tr>
				<th>EMAIL</th>
				<td><input type="email" name="email"/></td>
			</tr>
			<tr>
				<th colspan="2">
					<button>회원가입</button>
				</th>
			</tr>
		</table>
	
	</form>
</body>
</html>