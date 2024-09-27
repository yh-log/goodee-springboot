<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Board List</title>
<link rel="stylesheet" href="resources/css/common.css" type="text/css">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/css/bootstrap.min.css">

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/js/bootstrap.min.js"></script>
<script src="resources/js/jquery.twbsPagination.js" type="text/javascript"></script>

<style>
</style>
</head>
<body>
	<h2>게시글</h2>
	
	<button type="button" onclick="writeView()">글쓰기</button>
	<button type="button" onclick="memberList()">회원정보</button>
	
	<select id="pageNum">
		<option value="5">5</option>
		<option value="10">10</option>
		<option value="15">15</option>
		<option value="20" selected>20</option>
	</select>
	
	<table>
		<thead>
			<th>no</th>
			<th>제목</th>
			<th>게시자</th>
			<th>조회수</th>
			<th>게시일</th>
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

	function writeView(){
	//	location.href='${pageContext.request.contextPath}'+'/writeView';
		location.href= '/TotalService/writeView';
	}
	
	var firstPage = 1;
	var paging = false;
	
	pageCall(firstPage);
	
	$('#pageNum').change(function(){
		$('#pagination').twbsPagination('destroy');
		paging = false;
		pageCall(firstPage);
	});
	
	function pageCall(page){
		$.ajax({
			type: 'GET',
			url: 'boardList',
			data:{
				'page': page,
				'cnt': $('#pageNum').val()
			},dataType: 'JSON',
			success: function(data){
				printList(data.list);
				
				$('#pagination').twbsPagination({
					startPage: 1,
					totalPages: data.totalPage,
					visiblePages: 5,
					onPageClick: function(event, page){
						if(paging){
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
			content += '<td>'+item.idx+'</td>';
			content += '<td><a href="' + '${pageContext.request.contextPath}'+'/boardDetail/'+item.idx+'">'+item.subject+'</a></td>';
			content += '<td>'+item.user_name+'</td>';
			content += '<td>'+item.bHit+'</td>';
			content += '<td>'+item.reg_date+'</td>';
			content += '</tr>';
		});
		$('#list').html(content);
	}
	
	function memberList(){
		location.href='/TotalService/memberListView';
	}

</script>

</html>