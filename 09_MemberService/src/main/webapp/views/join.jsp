<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<style>
	table{
		margin-left:auto; 
	    margin-right:auto;
	}
	table, th, td{
		border : 1px solid white;
		border-collapse : collapse;
		padding: 5px 10px;
	}	
	caption{
		font-weight: 600;
		font-size: 30;
		padding : 15px;
	}
	.co{
		background-color: black;
		color : white;
		width: 50px;
	}
	td{
		background-color: lightgray;
		width : 200px;
		
	}
	
	
	input{
		border : none;
	}
	input[type="submit"]{
		background-color: lightgray;
		border : 1px solid white;
		
	}
	body{
		background-color: lightgray;
	}
</style>
</head>
<body>
	<form action="join" method="post">
		<table>
			<caption>🖤회원가입🖤</caption>
			<tr>
				<th class="co">ID</th>
				<td><input type="text" name="id" placeholder="🙋‍♂️ 아이디" autofocus required/></td>
			</tr>
			<tr>
				<th class="co">PW</th>
				<td><input type="password" name="pw" placeholder="❓ 비밀번호"/></td>
			</tr>
			<tr>
				<th class="co">NAME</th>
				<td><input type="text" name="name" placeholder="✨ 이름"/></td>
			</tr>
			<tr>
				<th class="co">AGE</th>
				<td><input type="text" name="age" placeholder="🙄 나이"/></td>
			</tr>
			<tr>
				<th class="co">GENDER</th>
				<td>
					<input type="radio" name="gender" value="남" checked/>남 🙎‍♂️
					&nbsp; &nbsp; &nbsp; &nbsp;
					<input type="radio" name="gender" value="여"/>여 🙎‍	
				</td>
			</tr>
			<tr>
				<th class="co">EMAIL</th>
				<td><input type="email" name="email" placeholder="💻 이메일"/></td>
			</tr>
			<tr>
				<th colspan="2">
					<input type="submit" value="회원가입"/>
				</th>
			</tr>
		</table>
	
	</form>
	
</body>
<script>

</script>
</html>