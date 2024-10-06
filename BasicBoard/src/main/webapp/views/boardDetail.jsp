<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Board Detail></title>
<link rel="stylesheet" href="/css/common.css" type="text/css">

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<style>

</style>
</head>
<body>
	<h3>상세 보기</h3>
	<a href="/updateView/${detail.idx}"><button>수정</button></a>
	<a href="/delete/${detail.idx}"><button>삭제</button></a>
	<div>
		<table>
			<tr>
				<th>no</th>
				<td>${detail.idx}</td>
			</tr>
			<tr>
				<th>작성자</th>
				<td>sessionScope.loginId 받을 예정</td>
			</tr>
			<tr>
				<th>제목</th>
				<td>${detail.subject}</td>
			</tr>
			<tr>
				<th>내용</th>
				<td>${detail.content}</td>
			</tr>
			<tr>
				<th>이미지</th>
				<td>
					<c:if test="${not empty files}">
						<c:if test="${files.size()>0}">
							<c:forEach var="file" items="${files}">
								<a href="/download?new_filename=${file.new_filename}&ori_filename=${file.ori_filename}&type=${file.type}">
									<img width="300px" alt="${file.ori_filename}" src="/photo/${file.new_filename}">
								</a>
							</c:forEach>
						</c:if>
					</c:if>
					<c:if test="${empty files}">
						파일이 없습니다.
					</c:if>
				</td>
			</tr>
		</table>
	</div>
	<br/>
	<div>
		<form>
			<table>
				<thead id="commentList"></thead>
				<tr>
					<th>댓글작성</th>
					<td><input type="text" name="member_id"/></td>
					<td><input type="text" name="comment"/><button type="button" onclick="commentSave()">작성</button></td>
				</tr>
			</table>
		</form>
		<span><button type="button" onclick="likes()">좋아요</button></span>
	</div>
</body>
<script>

	commentList();


	function commentSave(){
		
		var member_id = $('input[name="member_id"]').val(); // ${sessionScope.loginId}
		var comment = $('input[name="comment"]').val();
		var board_idx = ${detail.idx};
		
		console.log(comment);
		console.log(board_idx);
		
		//alert('댓글작성');
		$.ajax({
			type: 'POST',
			url: '/addComment',
			data: {
				'member_id' : member_id,
				'comment' : comment,
				'board_idx' : board_idx
			},dataType: 'JSON',
			success: function(data){
				console.log(data);
				commentList();
				
				// 댓글 작성 후 입력 필드 초기화
				$('input[name="member_id"]').val('');
				$('input[name="comment"]').val('');
				
			},error: function(e){
				console.log(e);
			}
		});
	}
	
//			contentType: 'application/x-www-form-urlencoded; charset=UTF-8', 

	function commentList(){
		var board_idx = ${detail.idx};
		
		$.ajax({
			type: 'GET',
			url: '/commentList',
			data: {'board_idx': board_idx},
			dataType: 'JSON',
			success: function(data){
				printComment(data.comment);
			},error: function(e){
				console.log(e);
			}
		});
		
	}
	
	function printComment(comment){
		var content = '';
		
		comment.forEach(function(item, idx){
			content += '<tr>';
			content += '<th>댓글</th>';
			content += '<td>'+item.member_id+'</td>';
			content += '<td>'+item.comment+'</td>';
			content += '<td>'+item.reg_date+'</td>';
			content += '</tr>';
		})
		$('#commentList').html(content);
	}

</script>
</html>