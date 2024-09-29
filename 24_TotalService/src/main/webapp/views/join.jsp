<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Join Page</title>
<link rel="stylesheet" href="resources/css/common.css">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
	<h3>회원가입</h3>
	<form action="join.do" method="post">
		<table>
			<tr>
				<th>ID</th>
				<td>
					<input type="text" name="id"/>
					<button type="button" id="overlay">중복체크</button>
					<span id="idCheck"></span>
				</td>
			</tr>
			<tr>
				<th>PW</th>
				<td><input type="password" name="pw" id="pw1"/></td>
			</tr>
			<tr>
				<th>비밀번호 확인</th>
				<td>
					<input type="password" id="pw2" oninput="pwCheck()"/>
					<p id="pwConfirm"></p>
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
	
	var idPass = false;
	var pwPass = false;
	
	// 아이디 중복체크 완료 후 회원가입이 가능하도록 진행
	
	function join(){
		if(idPass){
			if(pwPass){
				console.log('회원가입 요청');
				$('form').submit();
			}else{
				alert('비밀번호 일치를 확인하세요');
			}
		}else{
			alert('아이디 중복체크를 진행하세요');
		}
	}
	

	$('#overlay').click(function(){
		var id = $('input[name="id"]').val();
		console.log(id);
		
		$.ajax({
			data: 'GET',
			url: 'overlay',
			data: {'id':id},
			dataType: 'JSON',
			success: function(data){
				if(data.overlay>0){
					$('#idCheck').html('이미 사용중인 id 입니다.');
				}else{
					$('#idCheck').html('사용 가능한 id 입니다.');	
					idPass = true;
				}
			},error: function(e){
				console.log(e);
			}
		})
	})
	
	function pwCheck(){
		if($('#pw1').val() == $('#pw2').val()){
			$('#pwConfirm').text('비밀번호 일치').css('color', 'green')
	        pwPass = true;
	    }else{
	        $('#pwConfirm').text('비밀번호 불일치').css('color', 'red')
		}
	}

/* 	var result = '${joinResult.success}';
	
	if(result == true){
		alert('회원가입에 성공했습니다.');
		location.href = 'login.go';
	}else{
		alert('회원가입에 실패했습니다.');
	}
	 */
</script>

</html>