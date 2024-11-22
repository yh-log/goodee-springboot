<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<style>
	table, th, td{
		border: 1px solid black;
		border-collapse:collapse;
		padding:5px 10px;
	}
	
	textarea{
		width: 500px;
   		height: 200px;
	}
</style>
</head>
<body>
	<h3>상세보기</h3>
	<input type="hidden" name="idx" value="${idx}"/>
	<table>
		<tr>
			<th>작성자</th>
			<td><input type="text" name="user_name"/></td>
		</tr>
		<tr>
			<th>제목</th>
			<td><input type="text" name="subject"/></td>
		</tr>
		<tr>
			<th>내용</th>
			<td><textarea name="content"></textarea></td>
		</tr>
		<tr>
			<th>이미지</th>
			<td id="image"></td>
		</tr>
	</table>
	<button type="button" onclick="location.href='/list.go'">리스트</button>
</body>
<script>
	$(document).ready(function() {
		console.log('실행');
	    var idx = $('input[name="idx"]').val();
	    console.log('idx => ', idx);
	
	    if (idx) {
	        boardDetail(idx);
	    } else {
	        console.log('idx 값이 설정되지 않았습니다.');
	    }
	});
	
	function boardDetail(idx){
		console.log('실행');
		console.log('idx 값: ', idx);
		$.ajax({
			type: 'GET',
			url: '/detail.ajax',
			data: {'idx': idx},
			dataType: 'JSON',
			success: function(response){
				var result = response.result[0];
				console.log(response);
				if(response.success){
					
					// 게시글은 [0] 파일만 for문 돌려서 넣기
					$('input[name="user_name"]').val(result.user_name);
					$('input[name="subject"]').val(result.subject);
					$('textarea[name="content"]').val(result.content);
					
 					var content = '';
					if(response.result.length > 0){
						console.log('돌아?');
						response.result.forEach(function(item, idx){
							content += '<img src="/photo/'+item.new_filename+'" width="200px" onclick="downloadImg(\''+item.new_filename+'\')" />';
						})
						$('#image').append(content);
					}
					 
					
					
				}else{
					alert('데이터를 불러오는데 실패했습니다.');
				}
			},error: function(e){
				console.log('상세 에러 => ', e);
			}
		});
	} 

	function downloadImg(file){
		console.log('눌림 => ', file);
/* 		
		$.ajax({
			type: 'GET',
			url: '/downloadImg',
			data: {'file':file},
			dataType: 'JSON',
			success: function(result){
				if(result){
					alert('다운로드에 성공했습니다');
				}else{
					alert('다운로드에 실패했습니다.')
				}
			},error : function(e){
				console.log('다운로드 에러 => ', e);
			}
		}); */
	}
	
</script>
</html>