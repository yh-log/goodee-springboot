<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<style>
	table, th, td{
		border: 1px solid black;
		border-collapse:collapse;
		padding:5px 10px;
	}
</style>
</head>
<body>
	<h3>로그인</h3>
	<table>
		<tr>
			<td>
				ID : <input type="text" name="id"/> <br/>
			</td>
		</tr>
		<tr>
			<td>
				PW : <input type="password" name="pw"/>
			</td>
		</tr>
		<tr>
			<td>
				<button onclick="login()">로그인</button>
				<button onclick="location.href='join.go'">회원가입</button>
			</td>
		</tr>
	</table>
</body>
<script>
	function login(){
		$.ajax({
			type: 'POST',
			url: 'login.do',
			data: {'id' : $('input[name="id"]').val(),
				'pw' : $('input[name="pw"]').val()},
			dataType: 'JSON',
			success: function(response){
				if(response){
					alert('로그인 성공');
					location.href="list.go";
				}else{
					alert('아이디 또는 비밀번호를 확인하세요');
				}
			},error: function(e){
				console.log('로그인 에러 => ', e);
			}
		});
	}
</script>
</html>