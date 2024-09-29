<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="login"></div>

<script>
	var loginid = '${sessionScope.loginid}';
	
	if(loginid == 'admin'){
			$('#login').html('안녕하세요'+loginid+'님');
	}
	else{
		alert('이 용 불 가 서비스');
		location.href='./'	
	}
</script>

