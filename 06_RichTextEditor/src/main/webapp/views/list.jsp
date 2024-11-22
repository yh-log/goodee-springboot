<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	
	a{
		text-decoration: none;
    	color: #3B00DB;
	}
</style>
</head>
<body>
	<h3>리스트</h3>
	<button onclick="location.href='/'">글쓰기</button> 
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
	list();
	
	function list(){
		$.ajax({
			type: 'POST',
			url: '/list.ajax',
			data: {},
			dataType: 'JSON',
			success: function(data){
				console.log('받아온 데이터 => ', data);
				printList(data.result);
			},error: function(e){
				console.log('리스트 에러 =>' , e);
			}
		});
	}
	
	function printList(result){
		var content = '';
		
		result.forEach(function(view, idx){
			content += '<tr>';
			content += '<td>'+view.idx+'</td>';
			content += '<td><a href="detail.go?idx='+view.idx+'">'+view.subject+'</button></td>';
			content += '<td>'+view.user_name+'</td>';
			content += '<td>'+view.bHit+'</td>';
			content += '<td>'+view.reg_date+'</td>';
			content +='<td><button onclick="boardDelete('+view.idx+')">삭제</button></td>';
			content += '</tr>';
		})
		$('#list').append(content);
	}
</script>
</html>