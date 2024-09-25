<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="resources/css/common.css">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<style>

</style>
</head>
<body>
	<form action="join.do" method="post">
		<table>
			<caption>🖤회원가입🖤</caption>
			<tr>
				<th>ID</th>
				<td><input type="text" name="id" /></td>
			</tr>
			<tr>
				<th>PW</th>
				<td><input type="password" name="pw" /></td>
			</tr>
			<tr>
				<th>NAME</th>
				<td><input type="text" name="name" /></td>
			</tr>
			<tr>
				<th class="co">AGE</th>
				<td><input type="text" name="age" /></td>
			</tr>
			<tr>
				<th class="co">GENDER</th>
				<td>
					<input type="radio" name="gender" value="남"/>남
					&nbsp; &nbsp; &nbsp; &nbsp;
					<input type="radio" name="gender" value="여"/>여
				</td>
			</tr>
			<tr>
				<th class="co">EMAIL</th>
				<td><input type="email" name="email"/></td>
			</tr>
			<tr>
				<th colspan="2">
					<input type="submit" value="회원가입"/>
				</th>
			</tr>
		</table>
	
	</form>
	
</body>
<script>
	var msg = "${msg}";
	if(msg != ''){
		alert(msg);
	}
</script>
</html>