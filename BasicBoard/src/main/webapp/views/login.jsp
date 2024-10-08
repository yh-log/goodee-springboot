<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<link rel="stylesheet" href="css/common.css" type="text/css">

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<style>

</style>
</head>
<body>
	<div>
		<form>
			<table>
				<tr>
					<th>아이디</th>
					<td><input type="text" name="id"/></td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td><input type="password" name="pw"/></td>
				</tr>
				<tr>
					<th colspan="2">
						<button type="button" onclick="login()">로그인</button>
					</th>
				</tr>
			</table>
		</form>
	</div>
	<div>
		<a href="findUserView"><button>아이디/비밀번호 찾기</button></a>
	</div>
</body>
<script>
	function login(){
		$.ajax({
			type: 'POST',
			url: '/login',
			data: {
				'id': $('input[name="id"]').val(),
				'pw' : $('input[name="pw"]').val()},
			dataType: 'JSON',
			success: function(data){
				console.log('로그인 성공');
				
				if(data.success){
					window.location.href=data.nextPage; 
					console.log(data.nextPage);
					console.log('login success');
				}else{
					alert('아이디 비밀번호를 확인하세요');
					location.href=data.nextPage;
				}
				
			},error: function(e){
				console.log(e);
			}
		});
	}

</script>


</html>