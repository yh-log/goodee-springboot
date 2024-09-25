<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
 <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

<style>
	table, th, td{
	border : 1px solid black;
	border-collapse: collapse;
	padding : 5px 10px;
	}
</style>

</head>
<body>
<form action="join.do" method = "post">
	<table>
	<caption>회원가입</caption>
		<tr>
			<th>ID</th>
			<td>
				<input type = "text" name = "id">
				<button type="button" id ="overlay">중복체크</button>		
				<p id = "result"></p>
			</td>
		</tr>
		<tr>
			<th>PW</th>
			<td><input type = "password" name = "pw"> </td>
		</tr>
		<tr>
			<th>NAME</th>
			<td><input type = "text" name = "name"> </td>
		</tr>
		<tr>
			<th>AGE</th>
			<td><input type = "text" name = "age"> </td>
		</tr>
		<tr>
		<th>GENDER</th>
				<td><input type = "radio" name = "gender"  value = "남">남자 
				<input type = "radio" name = "gender"  value = "여">여자</td>
		</tr>
		<tr>
			<th>EMAIL</th>
			<td><input type = "text" name = "email"> </td>
		</tr>
		<tr>
		<!-- button 태그엔 submit 기능이 있지만 type="button" 처리하면 그 기능은 사라진다. -->
			<th colspan =2>
				<button type="button" onclick="join()">회원가입</button>
			</th>
		</tr>
	</table>
</form>
</body>
<script>

	var overlayPass = false;

	function join(){
		if(overlayPass == true && pwPass == true){
		console.log('서버에 회원가입 요청');
		$('form').submit(); // form을 서버로 던지는 submit 역할을 해주는 함수!
		}else{
			alert('아이디 중복체크를 진행하세요!');
		}
	}



	$('#overlay').click(function(){
		var id = $('input[name= "id"]').val();
		console.log(id);
		$.ajax({
			type:'get',
			url:'overlay.ajax',
			data:{'id':id},
			dataType:'JSON',
			success:function(data){
				console.log(data);
				
				if(data.overlay>0){                              
					$("#result").html(id+'는 이미 사용중입니다.');
					$("#result").css({'color' : 'red'});
				}else{
					overlayPass = true;
					$("#result").html(id+'는 사용 가능합니다.');
					$("#result").css({'color' : 'green'});
				} 
			},
			error:function(e){
				console.log(e);
			}
		});
	});
</script>
</html>