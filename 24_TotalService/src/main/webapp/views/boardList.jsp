<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>List page</title>
<link rel="stylesheet" href="resources/css/common.css" type="text/css">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/js/bootstrap.min.js"></script>
<script src="resources/js/jquery.twbsPagination.js" type="text/javascript"></script>

<style>
</style>
</head>
<body>
	<h2>List</h2>
	<button onclick="memberList()">회원 리스트</button>
	
	<select id="pageNum">
		<option value="5">5</option>
		<option value="10">10</option>
		<option value="15">15</option>
		<option value="20" selected>20</option>
	</select>
	
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
		<!-- 페이징 사용을 위해 ajax 사용 -->
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
	
	var firstPage = 1;
	
	pageCall(firstPage);
	
	// 보여줄 게시글 수 변경 시 기존 페이징 함수 파괴
	$('#pageNum').change(function() {
		$('#pagination').twbsPagination('destroy');
		pageCall(firstPage);
	});

	function pageCall(page, isPaginationClicked = false) {
		$.ajax({
			type: 'GET',
			url: 'list.do',
			data: {
				'page': page,
				'cnt': $('#pageNum').val(),
			},
			dataType: 'JSON',
			success: function(data) {
				listPrint(data.list);

				// 페이징이 처음 초기화될 때만 실행
				if (!isPaginationClicked) {
					$('#pagination').twbsPagination({
						startPage: page,
						totalPages: data.totalPage,
						visiblePages: 5,
						onPageClick: function(evt, page) {
							console.log('evt : ', evt);
							console.log('page : ', page);
							// 페이지 클릭 시 pageCall을 호출하지만, 페이징이 클릭된 것이므로 추가 초기화 방지
							pageCall(page, true);
						}
					});
				}
			},
			error: function(e) {
				console.log(e);
			}
		});
	}
	
	function listPrint(list){
		var content = '';
		
		list.forEach(function(item, idx){
			content += '<tr>'
			content += '<td>' + item.idx +'</td>'
			content += '<td>' + item.subject +'</td>'
			content += '<td>' + item.user_name +'</td>'
			content += '<td>' + item.bHit +'</td>'
			content += '<td>' + item.reg_date +'</td>'
			content += '</tr>'
		})
		
		$('#list').html(content);
	}
	
	function memberList(){
		location.href='member.go';
	}

</script>
</html>