<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Member Update Page</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
	<div class="login-wrapper">
		<h2>회원정보</h2>
		<form id="login-Form">
			<input type="text" name="id" value="${info.id}" readonly/> 
			<input type="text" name="pw" id="pw1" oninput="pwCheck()" value="${info.pw}">
			<input type="text" id="pw2" oninput="pwCheck()" placeholder="비밀번호 확인">
			<span id="pwConfirm">수정할 비밀번호를 입력하세요</span>
			
			<input type="text" name="name" placeholder="Name" value="${info.name}"/>
			<input type="text" name="nickname" placeholder="NickName" value="${info.nickname}"/>
			<input type="text" name="age" placeholder="Age" value="${info.age}"/>
			<input type="email" name="email" placeholder="Email" value="${info.email}"/>
			<button type="button" onclick="update()">수정</button>
		</form>
	</div>
</body>

<script>
	
	var pwPass = false;
	
	function pwCheck(){
		if($('#pw1').val() == $('#pw2').val()){
			$('#pwConfirm').text('비밀번호 일치').css('color', 'green')
			pwPass = true;
		}else{
			$('#pwConfirm').text('비밀번호 불일치').css('color', 'red')
		}
	}
	
	function update(e){
		if(pwPass == false){
			alert('비밀번호가 일치하지 않습니다.');
				return;
		}
		
		var param = {id:'${info.id}'}; // id값
		
		$('form input').each(function(idx,item){
			if($(item)[0].defaultValue != $(item)[0].Value){ // 변경된 값만 key : value 에 담기
				param[$(item).attr('name')] = $(item).val();
			}
		});
		
		fetch('${pageContext.request.contextPath}'+'/member/'+'${param.id}', {
			method: 'PUT',
			headers: {'Content-Type':'application/json'},
			body: JSON.stringify(param)
		})
		.then(response => {
			if(response.ok){
				alert('회원정보가 수정되었습니다.');
			}else{
				alert('회원정보 수정에 실패했습니다.');
			}
		})
		.catch(error => {
			console.log(error);
		})
	}
</script>
</html>