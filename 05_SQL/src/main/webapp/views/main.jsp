<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<style>
	table, th, td{
		border: 1px solid black;
		border-collapse : collapse;
		padding: 5px 10px
	}
</style>
</head>
<body>
	<h3>메인 페이지</h3>
	<a href="dbConnect">DB 접속</a> 
	<h3>${msg}</h3>
	<table>
		<caption>mamber 테이블 생성 쿼리 작성</caption>
		<thead>
			<tr>
				<th>FIELD</th>
				<th>TYPE</th>
				<th>SIZE</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>ID</td>
				<td>VARCHAR</td>
				<td>50</td>
			</tr>
			<tr>
				<td>PW</td>
				<td>VARCHAR</td>
				<td>100</td>
			</tr>
			<tr>
				<td>NAME</td>
				<td>VARCHAR</td>
				<td>20</td>
			</tr>
			<tr>
				<td>AGE</td>
				<td>INT</td>
				<td>4</td>
			</tr>
			<tr>
				<td>GENDER</td>
				<td>VARCHAR</td>
				<td>4</td>
			</tr>
			<tr>
				<td>EMAIL</td>
				<td>VARCHAR</td>
				<td>100</td>
			</tr>
		</tbody>
		
	</table>
	
	
	
	
	<a href="stmt">statement 예제</a> <br/>
	<a href="pstmt">preparedStatement 예제</a> <br/>
	<a href="resultSet">resultSet 예제</a> <br/>
</body>
<script>

</script>
</html>