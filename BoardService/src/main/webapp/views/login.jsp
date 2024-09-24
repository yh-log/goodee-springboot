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
<body>
	
	<div class="login-wrapper">
		<h2>Login</h2>
		<form action="login" method="post" id="login-Form">
			<input type="text" name="id" placeholder="Id" value="hodu"/>
			<input type="password" name="pw" placeholder="Password" value="1111"/>
			<input type="submit" value="Login"/>
		</form>	
		<button onclick="location.href='joinForm'">Join</button>
	</div>

</body>
<script>
	var msg = '${result}';
	if(msg != ''){
		alert(msg);
	}
</script>
</html>