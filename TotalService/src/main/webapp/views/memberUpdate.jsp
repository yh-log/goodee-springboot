<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Board Detail</title>
<link rel="stylesheet" href="resources/css/common.css" type="text/css">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<style>

</style>
</head>
<body>
	<form action="memberUpdate.do" method="post">
		<table>
			<caption>리스트 수정</caption>
			<tr>
				<th>ID</th>
				<td><input type="text" name="id"  value="${info.id}"/></td>
			</tr>
			<tr>
				<th>PW</th>
				<td><input type="text" name="pw" value="${info.pw}"/></td>
			</tr>
			<tr>
				<th>NAME</th>
				<td><input type="text" name="name" value="${info.name}"/></td>
			</tr>
			<tr>
				<th>AGE</th>
				<td><input type="text" name="age" value="${info.age}"/></td>
			</tr>
			<tr>
				<th>GENDER</th>
				<td>
					<input type="radio" name="gender" value="남" 
					<c:if test="${info.gender eq '남'}">checked</c:if> />남
					&nbsp; &nbsp; &nbsp; &nbsp;
					<input type="radio" name="gender" value="여"
					<c:if test="${info.gender eq '여'}">checked</c:if> />여
				</td>
			</tr>
			<tr>
				<th>EMAIL</th>
				<td><input type="email" name="email" value="${info.email}"/></td>
			</tr>
			<tr>
				<th colspan="2">
					<a href="./memberListView"> 리스트</a>
					<button>수정하기</button>
				</th>
			</tr>
		</table>
	
	</form>
</body>
<script>

	function delete(){
		location.href='delete.go?id=${info.id}';
	}
</script>