<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<style>
	table, th, td{
		border: 1px solid black;
		border-collapse:collapse;
		padding:5px 10px;
	}
</style>
</head>
<body>
	<h3>회원가입</h3>
	<form>
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
				</th>
			</tr>
		</table>
	</form>
	<button onclick="join()">회원가입</button>
</body>
<script>
	function join(){
		console.log('회원가입');
		var formData = new FormData($('form')[0]);
		
		$.ajax({
			type: 'POST',
			url: 'join.do',
			data: formData,
			dataType: 'JSON',
			contentType: false, 
			processData: false, 
			success: function(response){
				if(response.success){
					alert(response.id + ' 님 회원가입이 완료되었습니다.');
					// location.href="/";
				}else{
					alert('회원가입에 실패했습니다.');
				}
			},error: function(e){
				console.log('회원가입 에러 => ', e);
			}
		});
	}
</script>
</html>