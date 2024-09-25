<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/common.css" type="text/css">
<!-- controller에 css라는 요청이 없으면 static 폴더 밑에 css를 찾아라 -->
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<style>
	img.preview{
		width:100px;
		margin:3px;
		cursor: pointer;
	}
		
</style>
</head>
<body>
	<form>
	<input name="idx" type="hidden" value="${info.idx}">
		<table>
			<caption>글쓰기</caption>
			<tr>
				<th>작성자</th>
				<td>
					<input type="text" name="user_name" value="${info.user_name}"/>
				</td>
			</tr>
			<tr>
				<th>제목</th>
				<td>
					<input type="text" name="subject" value="${info.subject}"/>
				</td>
			</tr>
			<tr>
				<th>내용</th>
				<td>
					<textarea name="content">${info.content}</textarea>
				</td>
			</tr>
			<c:if test="${files.size()>0}">
			<tr>
				<th>이미지</th>
				<td>
				<!-- /photo라는 context요청이 있으면 C:/upload로 연결하도록 설정되어야 함(server.xml) -->
					<c:forEach items="${files}" var="file">
						<img width="500" alt="${file.ori_filename}" src="/photo/${file.new_filename}" onclick="download('${file.new_filename}')"><br/>						
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
				<th colspan = "2">
				  <input type="button" value="수정하기" onclick="save()"/>
				</th>
			</tr>
		</table>
	</form>
</body>
<script>
	function save(){	
		
		// ajax 로 파일을 업로드 하기 위해서는 FormData 객체가 필요 하다.
		// IE8 버전에서는 동작하지 않으니, 가급적이면 사용하지 않는것이 좋다.
		var form = new FormData($('form')[0]);
		
		$.ajax({
			type:'POST',
			url:'update.ajax',
			enctype:'multipart/form-data',
			processData:false,
			contentType:false,
			data:form,
			dataType:'JSON',
			success:function(data){
				console.log(data);
				if (data.success) {
					location.href = data.link
				}
			},
			error:function(e){
				console.log(e);
			}
		});
	}



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