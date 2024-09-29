<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="login"></div>

<script>
	var loginid = '${sessionScope.loginid}';
	
	if(loginid == ''){
		alert('로그인 필요한 서비스');
		location.href='./'
		console.log(loginid);
	}
	else{
		$('#login').html('안녕하세요'+loginid+'님');
		console.log(loginid);
	}
</script>

