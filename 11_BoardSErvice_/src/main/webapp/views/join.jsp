<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- controller 에 css 라는요청이 없으면 static 폴더 밑에 css 찾아라 -->
<!-- 먼저 controller을 보고 static을 보고 그래도 없으면 404가 뜬다. -->
<link rel="stylesheet" href="css/common.css" type="text/css">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<style>

</style>
</head>
<body>
	<form action="join" method="post">
		<table>
			<caption>&#127818;회원가입</caption>
			<tr>
				<th>ID</th>
				<td>
					<input type="text" name="id" placeholder="아이디를 입력하세요."/>
				</td>
			</tr>
			<tr>
				<th>PW</th>
				<td>
					<input type="password" name="pw" placeholder="비밀번호를 입력하세요."/>
				</td>
			</tr>
			<tr>
				<th>NAME</th>
				<td>
					<input type="text" name="name" placeholder="이름을 입력하세요."/>
				</td>
			</tr>
			<tr>
				<th>AGE</th>
				<td>
					<input type="text" name="age" placeholder="나이를 입력하세요."/>
				</td>
			</tr>
			<tr>
				<th>GENDER</th>
				<td>
					<input type="radio" name="gender" value="남"/>남
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" name="gender" value="여"/>여
				</td>
			</tr>
			<tr>
				<th>EMAIL</th>
				<td>
					<input type="email" name="email" placeholder="이메일을 입력하세요."/>
				</td>
			</tr>
			<tr>
				<th colspan = "2">
				  <input type="submit" value="회원가입"/>
				</th>
			</tr>
		</table>
	</form>
</body>
<script>

</script>
</html>