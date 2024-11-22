<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<style>
	table, th, td{
		border: 1px solid black;
		border-collapse:collapse;
		padding:5px 10px;
	}
	#logout{
		float: right;
	}
</style>
</head>
<body>
	<div style="display: ruby;"> 
		<h3>리스트</h3>
		<button onclick="location.href='write.go'">글쓰기</button> 
	</div>
	<button id="logout" onclick="location.href='logout.do'">로그아웃</button> 
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
		$.ajax({
			type: 'POST',
			url: 'list.ajax',
			data: {},
			dataType: 'JSON',
			success: function(response){
				pringList(response.result);
			},error: function(e){
				console.log('리스트 에러 => ', e);
			}
		});
	}
	
	function pringList(result){
		
		var content = '';
		
		result.forEach(function(view, idx){
			content +='<tr>';
			content +='<td>'+view.idx+'</td>';
			
			content +='<td><a href="detail/'+view.idx+'">'+view.subject+'</button></td>';
			
			content +='<td>'+view.user_name+'</td>';
			content +='<td>'+view.bHit+'</td>';
			content +='<td>'+view.reg_date+'</td>';
			content +='<td><button onclick="boardDelete('+view.idx+')">삭제</button></td>';
			content +='</tr>';
		});
		$('#list').html(content);
	}
	
	function boardDelete(idx){
		console.log('idx => ', idx);
		$.ajax({
			type: 'DELETE',
			url: 'delete.ajax',
			data: {'idx':idx},
			dataType: 'JSON',
			success: function(response){
				if(response){
					alert('삭제에 성공했습니다.');
					list();
				}
			},error: function(e){
				console.log('삭제 에러 => ', e);
			}
		});
	}
	
	
	

</script>
</html>