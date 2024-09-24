<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/common.css" type="text/css">

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/js/bootstrap.min.js"></script>
    <script src="js/jquery.twbsPagination.js" type="text/javascript"></script>

</head>
<body>
	<!-- <button onclick="location.href='writeForm'">글쓰기</button> -->
	<h2>리스트</h2>
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
		
		<tr> 
			<th colspan="6">
				<div class="container">
   					<nav aria-label="Page navigation">
        				<ul class="pagination" id="pagination"></ul>
    				</nav>
				</div>
			</th>
		</tr>

	</table>
</body>
<script>
	
	var showPage = 1;
	pageCall(showPage);
	
	function pageCall(page){
		$.ajax({
			type: 'GET',
			url: 'list.ajax',
			data: {
				'page':page,
				'cnt':20,
			},
			dataType: 'JSON',
			
			success: function(data){
				console.log(data);
				listPrint(data.list);
				
				$('#pagination').twbsPagination({
					startPage: 1, 
	        		totalPages: data.totalPage, 
	        		visiblePages: 5, 
	        		onPageClick: function(evt, page){
	        			//console.log(evt);
	        			//console.log(page);
						pageCall(page);
					}
				});
			},error: function(e){
				console.log(e);
			}
		})
	}
	
	function listPrint(list){
		var content = '';
		
		for (var item of list) {
			content += '<tr>';
			content += '<td>'+item.idx+'</td>';
			content += '<td>'+item.subject+'</td>';
			content += '<td>'+item.user_name+'</td>';
			content += '<td>'+item.bHit+'</td>';
			content += '<td>'+item.reg_date+'</td>';
			content += '<td><a hrdf="#">삭제</a></td>';
			content += '</tr>';
		}
		$('#list').html(content);
	}

</script>
</html>