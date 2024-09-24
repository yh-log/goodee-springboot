<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/common.css" type="text/css">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<style>

</style>
</head>
<body>
	<h2>List</h2>
	<button onclick="location.href='writeForm'">글쓰기</button>
	<table>
		<thead>
			<tr>
				<th>글번호</th>
				<th>제목</th>
				<th>작성자</th>
				<th>조회수</th>
				<th>작성일</th>
				<th>삭제</th>
			</tr>
		</thead>
		<tbody id="list"></tbody>
	</table>
</body>
<script>

 	$.ajax({
		type: 'GET',
		url: 'list.ajax',
		data: {},
		dataType: 'JSON',
		success: function(data){
			console.log(data);
			drawList(data.list);
		},error: function(e){
			console.log(e);
		}
	});
	
	function drawList(list){
		var content = '';
		list.forEach(function(item, idx){
			content += '<tr>';
			content += '<td>'+item.idx+'</td>';
			content += '<td>'+item.subject+'</td>';
			content += '<td>'+item.user_name+'</td>';
			content += '<td>'+item.bHit+'</td>';
			content += '<td>'+item.reg_date+'</td>';
			content += '<td><button>삭제<button></td>'
			content += '</tr>';
			console.log(item.idx, item.user_name, item.subject, item.bHit);
		});
		$('#list').html(content);
	} 

</script>
</html>