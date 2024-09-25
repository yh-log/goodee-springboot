<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Login Page</title>
<link rel="stylesheet" href="resources/css/table.css">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/css/bootstrap.min.css">

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/js/bootstrap.min.js"></script>
<script src="resources/js/jquery.twbsPagination.js" type="text/javascript"></script>

</head>
<body>
	<button onclick="location.href='write'">Write</button>
	<select id="pagePerNum">
		<option value="5">5</option>
		<option value="10">10</option>
		<option value="15">15</option>
		<option value="20" selected>20</option>
	</select>
	
	<h2>Board List</h2>
	<table class="container">
		<thead>
			<tr>
				<th>no</th>
				<th>제목</th>
				<th>작성자</th>
				<th>조회수</th>
				<th>작성일</th>
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
	
	var defaultpage = 1;
	pageCall(defaultpage);
	
	$('#pagePerNum').change(function(){
		$('#pagination').twbsPagination('destroy');
		pageCall(show);
	})
	
	function pageCall(page){
		$.ajax({
			type: 'GET',
			url: "${pageContext.request.contextPath}" + '/board/list',
			data: {
				'page':page,
				'cnt': $('#pagePerNum').val()
			},
			dataType: 'JSON',
			success: function(data){
				drawList(data.list);
				
				$('#pagination').twbsPagination({
					startPage:1, 
            		totalPages:data.totalpages, // 서버로 부터 받아올 값
            		visiblePages:5,
            		onPageClick:function(evt,page){
            			console.log('evt',evt); 
            			console.log('page',page); 
            			pageCall(page); // 현재 페이지를 서버로 넘겨준다.
            		} 
				});
			},error: function(e){
				console.log(e);
			}
		})
		
	}
	
	function drawList(list){
		var content = '';
		
		list.forEach(function(item, idx){
			content += '<tr>';
			content += '<td>'+item.idx+'</td>';
			content += '<td>'+item.subject+'</td>';
			content += '<td>'+item.user_name+'</td>';
			content += '<td>'+item.bHit+'</td>';
			content += '<td>'+item.reg_date+'</td>';
			content += '</tr>'
		})
		$('#list').html(content);
		
	}


</script>
</html>