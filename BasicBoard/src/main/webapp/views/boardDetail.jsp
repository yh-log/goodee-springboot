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
	<a href="/updateView/${detail.idx}"><button class="hid">수정</button></a>
	<a href="/delete/${detail.idx}"><button class="hid">삭제</button></a>
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
					<td><input type="text" name="member_id" value="${sessionScope.loginId}" readonly="readonly"/></td>
					<td><input type="text" name="comment"/><button type="button" id="commentBtn" onclick="commentSave()">작성</button></td>
				</tr>
			</table>
		</form>
		<span>
			<img src="/img/likeFalse.png" width="20px" id="likeImg"/>
			<span id="likeNum"></span>
			<button type="button" onclick="likes()" id="likeBtn">좋아요</button>
		</span>
	</div>
</body>
<script>

  	
	commentList();


	function commentSave(){
		
		var member_id = "${sessionScope.loginId}"; // ${sessionScope.loginId}
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
	
	
	var loginId = "${sessionScope.loginId}";  // 세션 값을 가져옴

	// loginId가 null이거나 빈 문자열일 경우
	if (loginId == null || loginId == '') {
	    // input 태그를 disabled 상태로 설정
	    $('input[name="member_id"]').attr('disabled', true);
	    $('input[name="comment"]').attr('disabled', true);
	    $('#commentBtn').attr('disabled', true);
	    $('#likeBtn').attr('disabled', true);
	    
	    $('.hid').attr('hidden', true);
	    
	}
	
	// 서버로 넘길 정보 (id, 좋아요 눌림, 게시글 넘버)
	
	function likes() {
	    var likeImg = $('#likeImg'); // likeImg 변수 정의
	    var currentSrc = likeImg.attr('src'); // 이미지 src 속성 가져오기
	    
	    var member_id = "${sessionScope.loginId}";
	    
	    // 기존 src 속성 값을 가져와서 현재와 동일하면 바꾸고, 다르면 기존 이미지로
	    if (currentSrc == '/img/likeFalse.png') {
	        likeImg.attr('src', '/img/likeTrue.png');
	        
	        $.ajax({
	        	type: 'POST',
	        	url: '/boardLikes'
	        	data: {'board_idx' : ${detail.idx},
	        			'member_id' : member_id
	        	},
	        	dataType: 'JSON',
	        	success: function(data){
			        console.log('좋아요');
	        	},error: function(e){
	        		console.log(e);
	        	}
	        });
	        
	    } else {
	        likeImg.attr('src', '/img/likeFalse.png');
	        
	        $.ajax({
	        	type: 'DELETE',
	        	url: '/boardLikes'
	        	data: {'board_idx' : ${detail.idx},
	        			'member_id' : member_id
	        	},
	        	dataType: 'JSON',
	        	success: function(data){
			        console.log('좋아요 취소');
	        	},error: function(e){
	        		console.log(e);
	        	}
	        });
	    }
	}
	

</script>
</html>