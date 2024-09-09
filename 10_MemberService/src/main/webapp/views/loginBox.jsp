<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="login"></div>
<script>
	var loginId = '${sessionScope.loginId}'; // loginId에 session에 있는 loginId를 담아주고 (controller에서 model로 받아옴)
	// sessionScope : 세션에서 값 꺼내올 때 사용하는 거... 원래는 session.getAtt... 인데 저렇게 줄일 수 있다.
	if(loginId == ''){ // 로그인이 공백 = 로그인이 안되어 있으면
		alert('로그인이 필요한 서비스 입니다.');
		location.href='./'; // 메인 페이지로 돌려보냄 (로그인 페이지)
	}else{ // 로그인이 되어있으면
		$('#login').html('안녕하세요' + loginId + '님, <a href="logout">[로그아웃]</a>'); 
		// 로그아웃을 하면 다시 로그인 페이지로 가는거니까.. (session도 지워줘야 함)
		
	}
</script>