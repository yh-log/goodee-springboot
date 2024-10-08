<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Board List</title>
<link rel="stylesheet" href="/css/common.css" type="text/css">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/css/bootstrap.min.css">

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/js/bootstrap.min.js"></script>
<script src="js/jquery.twbsPagination.js" type="text/javascript"></script>

<style>

</style>
</head>
<body>
	<h3>게시판</h3>
	<h1>${sessionScope.loginId}</h1>
	<button style="float: right; width : 100px; height : 50px" id="authButton" onclick="authClick()">로그인</button>
	<select id="pageNumber">
		<option value="5">5</option>
		<option value="10">10</option>
		<option value="15">15</option>
		<option value="20" selected>20</option>
	</select>
	<a href="writeForm"><button type="button">글쓰기</button></a>
	<div>
		<form>
			<table>
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
		</form>
	</div>

</body>
<script>

	var firstPage = 1;
	var paging = false;
	
	printPage(firstPage);
	
	$('#pageNumber').change(function(){
		$('#pagination').twbsPagination('destroy');
		paging = false;
		printPage(firstPage);
	});
	
	function printPage(page){
		$.ajax({
			type: 'GET',
			url: '/boardList',
			data: {
				'page' : page,
				'cnt' : $('#pageNumber').val()
			},
			dataType: 'JSON',
			success: function(data){
				console.log(data);
				printList(data.list);
				
				$('#pagination').twbsPagination({
					startPage: 1,
					totalPages: data.totalPage,
					visiblePages: 5,
					onPageClick: function(event, page){
						
						if(paging){
							printPage(page);
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
			content += '<td><a href="/boardDetail/'+item.idx+'">'+item.subject+'</a></td>';
			content += '<td>'+item.user_name+'</td>';
			content += '<td>'+item.bHit+'</td>';
			content += '<td>'+item.reg_date+'</td>';
			content += '</tr>'
		})
		$('#list').html(content);
	}
	
	
	// 페이지 로드 시 로그인 상태 체크
	var loginId = "${sessionScope.loginId}";  // 세션에서 loginId 값을 가져옴
	console.log(loginId);

	// null이 아니고 공백이 아닐 경우 true
	var isLogin = loginId != null && loginId !== "";  // 로그인 상태 확인

	if (isLogin) {
	    $('#authButton').text('로그아웃');
	} else {
	    $('#authButton').text('로그인');
	}
	
 	
	if(isLogin){ // 로그인 한 상태일 경우
		$('#authButton').text('로그아웃');
	}
	
	function authClick() {
	    if (isLogin) {
	        // 로그아웃 버튼을 누르면 로그아웃 URL로 이동
	        location.href = "/logout";
	        
	    } else {
	        // 로그인 페이지로 이동
	        location.href = "/loginView";
	    }
	}

	

	
	
</script>
</html>