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
<style>
	img.icon{
		width:25px;
	}
</style>
</head>
<body>
	<a href="write.go"><button>글쓰기</button></a>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<select id="pagePerNum">
		<option value="5">5</option>
		<option value="10">10</option>
		<option value="15">15</option>
		<option value="20" selected>20</option>
		
	</select>
	개 씩 보기
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
		<tbody id="list">
	
		</tbody>
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
var show = 1;

pageCall(show);

$('#pagePerNum').change(function(){
	$('#pagination').twbsPagination('destroy'); //기존에 만들어진 페이징을 파괴
	pageCall(show);
});

	function pageCall(page) {
		$.ajax({
			type:'GET',
			url:'list.ajax',
			data:{
				'page':page,
				'cnt':$('#pagePerNum').val()
			},
			datatype:'JSON',
			success:function(data){
				console.log(data);
				drawList(data.list)
				console.log('총페이지 =>', data.list.totalpage);
				
				$('#pagination').twbsPagination({ // 페이징 객체 만들기
					startPage:1, 
            		totalPages: 700, // todo - 나중에 수정 필요
            		visiblePages:5,
            		onPageClick:function(evt,page){
            			console.log('evt',evt); 
            			console.log('page',page); 
            			pageCall(page);
            		}
				});
			},
			error:function(e){
				console.log(e);
			}
		});
	}
	function drawList(list) {
		var content ='';
		list.forEach(function(view,idx){ 
			content +='<tr>';
			content +='<td>'+view.idx+'</td>';
			
			content +='<td>';
			content += '<a href="detail.go?idx='+view.idx+'">'+view.subject+'<a/></td>';
			
			content +='<td>'+view.user_name+'</td>';
			content +='<td>'+view.bHit+'</td>';
			content +='<td>'+view.reg_date+'</td>';
			content +='<td><a href="del?idx='+view.idx+'">삭제<a/></td>';
			content +='</tr>';
		});
		$('#list').html(content);
	}
	

	

</script>
</html>