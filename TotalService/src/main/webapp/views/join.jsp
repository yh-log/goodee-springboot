<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Join page</title>
<link rel="stylesheet" href="resources/css/common.css" type="text/css">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<style>
</style>
</head>
<body>
	<h3>회원가입</h3>
	<form action="join" method="post">
		<table>
			<tr>
				<th>ID</th>
				<td>
					<input type="text" name="id"/>
					<button type="button" onclick="overlay()">중복체크</button>
					<p id="idCheck"></p>
				</td>
			</tr>
			<tr>
				<th>PW</th>
				<td>
					<input type="password" name="pw" id="pw1"/>
					<input type="password" id="pw2" oninput="pwConfirm()"/>
					<p id="pwCheck"></p>
				</td>
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
					<button type="button" onclick="join()">회원가입</button>
				</th>
			</tr>
		</table>
	</form>
</body>
<script>
	var idCheck = false; // id
	var pwCheck = false; // pw
	
	function join(){
		if(idCheck){
			if(pwCheck){
				$('form').submit();
			}else{
				alert('비밀번호 불일치');
			}
		}else{
			alert('아이디 중복체크를 진행하세요');
		}
	}
	
	
	function overlay(){
		var id = $('input[name=id]').val();
		
		$.ajax({
			type: 'GET',
			url: 'overlay',
			data: {'id':id},
			dataType: 'JSON',
			success: function(data){
				if(data.overlay>0){
					$('#idCheck').html('이미 사용중인 아이디 입니다.');
				}else{
					$('#idCheck').html('사용 가능한 아이디 입니다.');
					idCheck = true;
				}
			}
		});
	}
	
	function pwConfirm(){
		if($('#pw1').val() == $('#pw2').val()){
			pwCheck = true;
			$('#pwCheck').html('비밀번호 일치').css({'color': 'green'});
		}else{
			$('#pwCheck').html('비밀번호 불일치').css({'color': 'red'});
		}
	}
	
</script>
</html>