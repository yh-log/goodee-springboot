<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Login Page</title>
<link rel="stylesheet" href="resources/css/common.css">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
	<div class="login-wrapper">
		<h2>Login</h2>
		<form action="login" method="post" id="login-Form">
			<input type="text" name="id" placeholder="Id" />
			<input type="password" name="pw" placeholder="Password" />
			<input type="submit" value="Login"/>
		</form>
		<button onclick="location.href='join'">Join</button>
	</div>
	<div class="login-wrapper" id="box">
		<span class="input-wrap">
			<input type="checkbox" id="checkId" name="checkId"/>
			<label for="checkId"><span></span></label>
			아이디 저장
		</span>
		<ul> <!-- 추후 기능 구현 -->
			<li id="idSearch"  style="cursor: pointer">아이디 찾기</li>
			<li  id="pwSearch"  style="cursor: pointer">비밀번호 찾기</li>
		</ul>
	</div>	

</body>
<script>
	var msg = '${resultMap.result_message}'; // 서버로 부터 받아올 값
	var result_code = '${resultMap.result_code}';
	
	// 공백이 아닐 경우 alert 메시지 출력
	/* if(msg != ''){
		alert(msg);
	} */

	if(result_code == "success"){
		location.href= "${pageContext.request.contextPath}" + "${resultMap.redirect_url}";
	} else if(result_code == "fail"){
		alert(msg);
	}	
	
// id 기억하기 (checkbox)

	
	
</script>

</html>
