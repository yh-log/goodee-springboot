<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Member List</title>
<link rel="stylesheet" href="resources/css/common.css" type="text/css">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/css/bootstrap.min.css">

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/js/bootstrap.min.js"></script>
<script src="resources/js/jquery.twbsPagination.js" type="text/javascript"></script>

<style>
	textarea{
	width:550px;
	height:200px;
	resize:none;
}
</style>
</head>
<body>
	<h2>Member List</h2>
	<a href="./boardListView">게시글 리스트</a>
	<select id="pageNum">
		<option value="5">5</option>
		<option value="10">10</option>
		<option value="15" selected>15</option>
	</select>
	<table>
		<thead>
			<th>ID</th>
			<th>NAME</th>
			<th>EMAIL</th>
		</thead>
		<tbody id="list"></tbody>
		<tr>
			<th colspan="3">
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
	// 페이징을 위해 ajax 사용
	var firstPage = 1;
	var paging = false;
	
	pageCall(firstPage);
	
	$('#pageNum').change(function(){
		$('#pagination').twbsPagination('destroy');
		// 여기에서 함수를 다시 실행해줘야 하나..?
		paging = false;
		pageCall(firstPage);
	});
	
	function pageCall(page){
		$.ajax({
			type: 'GET',
			url: 'memberList',
			data: {
				'page' : page,
				'cnt' : $('#pageNum').val()
			},
			dataType: 'JSON',
			success: function(data){
				printList(data.list);
				
				$('#pagination').twbsPagination({
					startPage: 1,
					totalPages: data.totalPage,
					visiblePages: 5,
					onPageClick: function(event, page){
						if(paging) {
							pageCall(page);
						}
						paging = true;
					}
				});
			},error: function(e){
				console.log(e);
			}
		});
	}
	
	
	function printList(list){
		var content = '';
		
		list.forEach(function(item, idx){
			content += '<tr>';
			content += '<td><a href="memberDetail.go?id='+item.id+'">'+item.id+'</a></td>';
			content += '<td>'+item.name+'</td>';
			content += '<td>'+item.email+'</td>';
			content += '</tr>'
		});
		
		$('#list').html(content);
	}
	
</script>
</html>