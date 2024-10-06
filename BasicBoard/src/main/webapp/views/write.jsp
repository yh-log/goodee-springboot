<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Write Form</title>
<link rel="stylesheet" href="css/common.css" type="text/css">

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<style>

</style>
</head>
<body>
	<h2>글쓰기</h2>
	<div>
		<form>
			<table>
				<tr>
					<th>작성자</th><!-- ${sessionScope.loginId} -->
					<td><input type="text" name="user_name" value=""/></td>
				</tr>
				<tr>
					<th>제목</th>
					<td><input type="text" name="subject"/></td>
				</tr>
				<tr>
					<th>내용</th>
					<td><input type="text" name="content"/></td>
				</tr>
				<tr>
					<th>파일</th>
					<td><input type="file" name="files" multiple="multiple"/></td>
				</tr>
				<tr>
					<th colspan="2"><button type="button" onclick="save()">글쓰기</button></th>
				</tr>
			</table>
		</form>
	</div>
</body>
<script>
	
	
	function save(){
		
		var formData = new FormData($('form')[0]);
		
		$.ajax({
			type: 'POST',
			url: '/write',
			data: formData,
			dataType: 'JSON',
			processData: false,
			contentType: false,
			enctype: 'multipart/form-data',
			success: function(data){
				if(data.success){
					location.href=data.page; 
				}
			},error: function(e){
				console.log(e);
			}
		});
		
	}
</script>
</html>