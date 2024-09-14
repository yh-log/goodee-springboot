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
	<h3>로그인</h3>
	<form action="login.do" method = "post">
		<table>
			<tr>
				<th>ID</th>
				<td><input type="text" name="id" ></td>
			</tr>
			<tr>
				<th>PW</th>
				<td><input type="password" name="pw" ></td>
			</tr>
			<tr>
				<th colspan = "2">
					<button>LOGIN</button>
					<a href="joinForm">회원가입</a>
				</th>
			</tr>
		</table>
	</form>
</body>
<script>
	var msg = '${result}';
	if(msg != ""){
		alert(msg);
	}
</script>
</html>