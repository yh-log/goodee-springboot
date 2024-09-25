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
	<div class="login-wrapper">
		<h2>Join</h2>
		<form action="join" method="post" id="login-Form">
			<input type="text" name="id" placeholder="Id"/> 
			<!-- 중복체크 버튼을 누르면 결과가 span에 출력 -->
			<button type="button" id="overlay">중복체크</button>
			<span id="result"></span>
			
			<input type="text" name="pw" id="pw1" oninput="pwCheck()" placeholder="비밀번호">
			<input type="text" id="pw2" oninput="pwCheck()" placeholder="비밀번호 확인">
			<span id="pwConfirm">비밀번호를 입력하세요</span>
			
			<input type="text" name="name" placeholder="Name"/>
			<input type="text" name="nickname" placeholder="NickName"/>
			<input type="text" name="age" placeholder="Age"/>
			<input type="email" name="email" placeholder="Email"/>
			<button type="button" onclick="join()">Join</button>
		</form>
	</div>
</body>
<script>
	
	// 아이디 중복체크
	var overlayPass = false;
	var pwPass = false;

	function join(){
		if(overlayPass){
			if(pwPass){
			$('form').submit();
			}else{
				alert('비밀번호를 확인해주세요');	
			}
		}else{
			alert('아이디 중복체크를 진행하세요!');
		}
	}
	
	
	$('#overlay').click(function(){
		var id = $('input[name="id"]').val();
		$.ajax({
			type: 'GET',
			url: 'overlay',
			data: {'id':id},
			dataType: 'JSON',
			success: function(data){
				if(data.overlay > 0){
					$('#result').html(id + '는 이미 사용중입니다.');
				}else{
					overlayPass = true;
					$('#result').html('사용 가능한 id 입니다.');
				}
			},error: function(e){
				console.log(e);
			}
		});
	});
	
	function pwCheck(){
	    if($('#pw1').val() == $('#pw2').val()){
	        $('#pwConfirm').text('비밀번호 일치').css('color', 'green')
	        pwPass = true;
	    }else{
	        $('#pwConfirm').text('비밀번호 불일치').css('color', 'red')
	    }
	}
</script>
</html>