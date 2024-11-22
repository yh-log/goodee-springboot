<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Board List</title>

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<style>
	table, th, td{
		border: 1px solid black;
		border-collapse:collapse;
		padding:5px 10px;
	}
</style>
</head>
<body>
	<h3>list</h3>
	<table>
		<thead>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>작성자</th>
				<th>조회수</th>
				<th>작성일</th>
			</tr>
		</thead>
		<tbody id="list">
		
		</tbody>
	</table>
</body>
<script>

list();

function list(){
	console.log('실행');
	$.ajax({
		type: 'POST',
		url: 'boardlist',
		data: {},
		dataType: 'JSON',
		success: function(response){
			console.log(response);
		},error: function(e){
			console.log('리스트 에러 => ', e);
		}
	});
	
}
</script>
</html>