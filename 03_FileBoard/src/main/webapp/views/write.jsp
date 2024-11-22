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
	<h3>글쓰기</h3>
	<form onsubmit="return false;">
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
				<th>파일</th>
				<td><input type="file" name="files" multiple="multiple"/></td>
			</tr>
		</table>
	</form>
	<button type="button" onclick="boardwrite()">글작성</button>
</body>
<script>
	function boardwrite(){
		console.log('실행');
		var formData = new FormData($('form')[0]);
		$.ajax({
			type: 'POST',
			url: '/write.ajax',
			data: formData,
			processData: false, 
	        contentType: false, 
	        enctype: 'multipart/form-data',
	        success: function(response){
	        	if(response){
		        	console.log('글 작성 성공');
		        	location.href = response.page;
	        	}else{
	        		console.log('실패');
	        	}
	        },error: function(e){
	        	console.log('글작성 중 에러 => ', e);
	        }
		});
	}
</script>
</html>