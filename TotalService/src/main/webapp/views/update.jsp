<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Board Detail</title>
<link rel="stylesheet" href="resources/css/common.css" type="text/css">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<style>
	textarea{
		width:550px;
		height:200px;
		resize:none;
	}

	img.preview{
		width:100px;
		margin:3px;
		cursor: pointer;
	}
</style>
</head>
<body>
	<h2>게시글 상세보기</h2>
	<form action="boardUpdate" method="post" enctype="multipart/form-data">
		<input type="number" name="idx" value="${result.idx}" hidden/>
		<table>
			<tr>
				<th>작성자</th>
				<td><input type="text" name="user_name" value="${sessionScope.loginId}" readonly/></td>
			</tr>
			<tr>
				<th>제목</th>
				<td><input type="text" name="subject" value="${result.subject}"/></td>
			</tr>
			<tr>
				<th>내용</th>
				<td><textarea name="content">${result.content}</textarea></td>
			</tr>
	
			<c:if test="${files.size()>0}">
			<tr>
				<th>이미지</th>
				<td>
					<c:forEach items="${files}" var="file">
						<img width="500" alt="${file.ori_filename}" src="/photo/${file.new_filename}">
					<br/>						
					</c:forEach>
				</td>
			</tr>
			</c:if>
			<tr>
				<th>파일</th>
				<td>
					<input type="file" name="files" multiple="multiple" onchange="readFile(this)"/>
					<div id="img_list"></div>
				</td>
			</tr>
				<tr>
					<th colspan="2">
					<button>수정하기</button>
						<!-- <button onclick="update()">수정하기</button> -->
					</th>
				</tr>
				
		</table>
	</form>
</body>
<script>
/* 	function update(){
		
		console.log('눌리나');
		
	    // form 객체 생성
	    var form = new FormData($('form')[0]);
	    
	    for (const x of form.entries()) { console.log(x); }; 
		
		$.ajax({
			type: 'POST',
			url: 'boardUpdate',
			enctype:'multipart/form-data',
			processData:false,
			contentType:false,
			data: form,
			dataType: 'JSON',
			success: function(data){
				console.log('실행?');
			},error: function(e){
				console.log(e);
			}
		});
		
	} */
	
	
	function readFile(input){
		console.log(input.files);
		var reader;
		$('#img_list').empty();
		
		
		for (var file of input.files) {
			reader = new FileReader(); // 파일리더,파일객체로 부터 바이너리를 읽어올수 있는 객체
			reader.readAsDataURL(file); //파일객체로 부터 DATA-URL 형태로 바이너리를 읽어온다.
			reader.onload = function(e){// 리더가 파일을 다 읽었다면...
			//	console.log(e);
			$('#img_list').append('<img class="preview" src="'+e.target.result+'"/>');
			}
		}
	}
	

</script>
</html>