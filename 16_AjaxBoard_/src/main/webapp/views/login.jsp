<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
 <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<style>
	table,td,th{
		border : 1px solid black;
		border-collapse: collapse;
		padding :5px 10px;
	}


</style>

</head>
<body>
		<table>
			<tr>
				<th>ID</th>
				<td><input type = "text" name = "id"/></td>
			</tr>
			<tr>
				<th>PW</th>
				<td><input type = "password" name = "pw"/></td>
			</tr>
			<tr>
				<th colspan = "2">
					<button id="login">LOGIN</button>
					<a href = "join.go">회원가입</a>
				</th>
			</tr>
		</table>

</body>
<script>

	$('#login').on('click', function(){
		var id = $('input[name="id"]').val();
		var pw = $('input[name="pw"]').val();
		console.log(id + "/" + pw); // 이벤트가 잘 실행되는지, 값이 잘 들어오는지 체크
		
		$.ajax({
			type: 'post',
			url: 'login.ajax',
			data: {'id':id, 'pw':pw},
			dataType: 'JSON',
			success: function(data){
				console.log(data);
				if(data.login){
					alert(id + '님 반갑습니다.');
					location.href='list.go';
				}else{
					alert('아이디 또는 비밀번호를 확인해주세요.');
				}
			},
			error: function(e){
				console.log(e);
			}
		});
	});


	var msg = "${result}";  //스크립트에 쓸경우는 "'에 감싸준다
	if(msg !=''){
		alert(msg); //회원가입했을때 로그인 페이지로 이동 + 뭔가 내용이 있으면 alert로 뜨게
	}
	
</script>
</html>