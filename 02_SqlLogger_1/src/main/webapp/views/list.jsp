<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
</head>
<body>
	<button onclick="location.href='write.go'">글쓰기</button>
	<table>
		<thead>
			<tr>
				<th>글번호</th>
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

page();

	function page() {
		$.ajax({
			type:'GET',
			url:'list',
			data:{},
			dataType:'JSON',
			success:function(data){
				console.log(data.list);
				
				if(data.list.length>0){
					drawList(data.list);
				}else{
					alert('작성된 데이터가 없습니다.');
				}
			},
			error:function(e){
				console.log(e);
			}
		});	
	}
	
	function drawList(list) {
		var content='';
		list.forEach(function(view,idx){
			content +='<tr>';
			content +='<td>'+view.idx+'</td>';
			content +='<td>';
			content += '<td>'+view.subject+'</td>';
			content +='<td>'+view.user_name+'</td>';
			content +='<td>'+view.bHit+'</td>';
			content +='<td>'+view.reg_date+'</td>';
			content +='</tr>';
		})
		$('#list').html(content);
	}

</script>
</html>