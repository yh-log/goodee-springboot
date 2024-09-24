<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<style>
	table,td,th{
		border : 1px solid black;
		border-collapse: collapse;
		padding :5px 10px;
	}
</style>
</head>
<body>
	<button onclick="location.href='insert.go'">입사자 등록</button>
	<table>
		<thead>
			<tr>
				<th>ID</th>
				<th>이름</th>
				<th>나이</th>
				<th>이메일</th>
			</tr>
		</thead>
		<tbody id="list">
		</tbody>
	</table>
</body>
<script>
	$.ajax({
		// 1. 세션 있는지 (여부에 따라 상태값을 준다 -1 / 1) *모델에 담아준게 아니라 EL태그로 받을 수 없고 js로 그려줘야 함
		type: 'GET',
		url: 'list.ajax',
		data: {}, // 보내는 파라미터가 없어서 빈 상태로 보낸다.
		dataTyle: 'JSON',
		success: function(data){
			console.log(data);
			if(data.login){
				drawList(data.list); // 여기서 하면 너무 길어지니까 별도로 함수를 만들어 준다.
			}else{
				alert('로그인이 필요한 서비스입니다.');
				location.href='./';
			}
		}, error: function(e){
			console.log(e);
		}
	});
	
	function drawList(list){
		var content = '';
		list.forEach(function(item,idx){
			content += '<tr>';
			content += '<td>'+item.id+'</td>';
			content += '<td>'+item.name+'</td>';
			content += '<td>'+item.age+'</td>';
			content += '<td>'+item.email+'</td>';
			content += '</tr>';
			console.log(item.id, item.age, item.name, item.email); // 각 item 들이 td이고 이 console로 만든 한 줄이 tr이 된다.
		});
		$('#list').html(content);
	}
</script>
</html>