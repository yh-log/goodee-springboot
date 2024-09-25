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
			<button type="button" id="overlay">중복체크</button>
			<span id="result"></span>
			<input type="password" name="pw" placeholder="Password"/>
			<input type="text" name="name" placeholder="Name"/>
			<input type="text" name="age" placeholder="Age"/>
            <label for="remember-check">
                <input type="radio" id="gender-check">남
                 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="radio" id="gender-check">여
            </label>
			<input type="email" name="email" placeholder="Email"/>
			<input type="submit" value="Join"/>
			
		</form>
	</div>
</body>

<script>
	$('#overlay').click(function(){
		var id = $('input[name="id"]').val();
		console.log(id);
		$.ajax({
			type: 'get',
			url: 'overlay',
			data: {'id':id},
			dataType: 'JSON',
			success: function(data){
				console.log(data);
				if(data.overlay>0){
					$('#result').html(id + '는 이미 사용중입니다.');
					$('#result').css({'text-decoration': 'underline', 'text-decoration-color': '#FF7E7E'});
				}else{
					$('#result').html(id + '는 사용 가능합니다.');
				}
			},
			error: function(e){
				console.log(e);
			}
		})
	});

</script>

</html>