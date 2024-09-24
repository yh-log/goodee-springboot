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
		<h2>Join</h2>
		<form action="join" method="post" id="login-Form">
			<input type="text" name="id" placeholder="Id"/>
			<input type="password" name="pw" placeholder="Password"/>	
			<input type="text" name="name" placeholder="Name"/>
			<input type="text" name="age" placeholder="Age"/>
            <label for="remember-check">
                <input type="checkbox" id="gender-check">남
                 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="checkbox" id="gender-check">여
            </label>
			<input type="email" name="email" placeholder="Email"/>
			<input type="submit" value="Join"/>
		</form>
	</div>
</body>
</html>