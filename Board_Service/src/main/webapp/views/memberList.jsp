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

	<select id="pagePerNum"> <!-- 이벤트 걸어서 서버로 넘긴다 AJAX 로 VALUE 값을 -->
		<option value="5" selected>5</option>
		<option value="10" >10</option>
		<option value="15">15</option>
	</select>

	<h2>Member List</h2>
	<table class="container">
		<thead>
			<tr>
				<th>ID</th>
				<th>Password</th>
				<th>Name</th>
				<th>NicName</th>
				<th>Age</th>
				<th>Email</th>
				<th>Delete</th>
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
	
	pageCall(defaultpage); // 페이지 실행하면 함수 실행
	
	// 기존에 만들어 둔 페이징 초기화
	$('#pagePerNum').change(function(){
		$('#pagination').twbsPagination('destroy');
		pageCall(show);
	})

	/* url: 'member/1 -> post'
	url: member/1 -> delete
	url: member/1 -> put */
	function pageCall(page){
		$.ajax({
			type: 'GET',
			url: '${pageContext.request.contextPath}' + '/member/list',
			data: {
				'page' : page,
				'cnt' : $('#pagePerNum').val() // select 로 들어오는 값으로 보여줄 페이지 설정
			},
			dataType: 'JSON',
			success: function(data){
				console.log("data > ", data);
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

	// list 불러오기 (회원정보)
	function drawList(list){
		var content = '';
		
		list.forEach(function(item, idx){
			content += '<tr>';
			/* content += '<td>'+item.id+'</td>'; */
			content += '<td><a href="' + '${pageContext.request.contextPath}'+'/member/' +item.id+'">' +item.id + '</a></td>'
			content += '<td>'+item.pw+'</td>';
			content += '<td>'+item.name+'</td>';
			content += '<td>'+item.nicname+'</td>';
			content += '<td>'+item.age+'</td>';
			content += '<td>'+item.email+'</td>';
			// 삭제 버튼 클릭 시 삭제 요청
			content += '<td><button onclick="deleteMember(\'' + item.id + '\'); return false;">삭제</button></td>';
			content += '</tr>'
		});
		$('#list').html(content);
	}
	
 	function deleteMember(id){
		if(confirm('정말로 삭제하시겠습니까?')){
			fetch('${pageContext.request.contextPath}'+'/member/'+id, {
				method: 'DELETE'
				}) // 서버로 DELETE 요청 전송
			// fetch 는 비동기 요청으로 요청 완료 후 무엇을 할 지 처리
			// response : 서버에서 반환된 응답 객체 (정상처리 시 response.ok = true)
			.then(response => {
				if(response.ok){
					alert('삭제되었습니다.');
					pageCall(defaultpage);
				}else{
					alert('삭제에 실패했습니다.');
				}
			})
			// 에러 발생 시 호출 및 처리
			.catch(error => {
				console.log(error);
			});
		}
	}

</script>


</html>