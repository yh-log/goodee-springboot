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
<jsp:include page="admin_loginbox.jsp"/>
<h3>환영합니다</h3>
<form>
	<table>
	<caption>유저정보</caption>
	<tr>
		<th>ID</th>
		<td>${info.id}</td>
	</tr>
	<tr>
		<th>PW</th>
		<td>${info.pw}</td>
	</tr>
	<tr>
		<th>NAME</th>
		<td>${info.name}</td>
	</tr>
	<tr>
		<th>AGE</th>
		<td>${info.age}</td>
	</tr>
	<tr>
	<th>GENDER</th>
	<td>${info.gender}</td>
	</tr>
	<tr>
		<th>EMAIL</th>
		<td>${info.email}</td>
	</tr>
	<tr>
		<th colspan = "2">
		<a href="user_update.go?id=${info.id}">수정하기</a> <!-- 회원이 아니라 게시글 업데이트로 가고있음 (회원 업데이트는 x) -->
		</th>
	</tr>

	<tr id="boardTable">
	
		<!-- 버튼 태그엔 submit 기능이 있지만 type="button" 을 넣으면 사라진다 -->
	<!--	<th colspan =2><button>회원가입</button></th>-->
			
	</tr>
	</table>
</form>
</body>
<script>


</script>
</html>