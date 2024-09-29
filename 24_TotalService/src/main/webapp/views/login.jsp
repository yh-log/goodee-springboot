<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Login page</title>
<link rel="stylesheet" href="resources/css/common.css" type="text/css">
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
				<th colspan="2">
					<input type="radio" name="option" value="user" checked="checked"/> 일반 사용자
					<input type="radio" name="option" value="admin"/> 관리자
				</th>
			</tr>
			<tr>
				<th colspan = "2">
					<button>LOGIN</button>
				</th>
			</tr>
		</table>
	</form>
	<a href="join.go">회원가입</a>
</body>
<script>

	var msg = '${result}';
	if(msg != ''){
		alert(msg);
		location.href='listView';
	}
	
</script>
</html>