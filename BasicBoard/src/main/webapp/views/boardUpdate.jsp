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
	<h3>수정</h3>

	<div>
		<form>
			<table>
				<tr>
					<th>no</th>
					<td><input type="text" name="idx" value="${detail.idx}" readonly="readonly"></td>
				</tr>
				<tr>
					<th>작성자</th>
					<td><input type="text" name="user_name" value="${detail.user_name}" readonly="readonly"></td>
				</tr>
				<tr>
					<th>제목</th>
					<td><input type="text" name="subject" value="${detail.subject}"/></td>
				</tr>
				<tr>
					<th>내용</th>
					<td><input type="text" name="content" value="${detail.content}"/></td>
				</tr>
				<tr>
					<th>이미지</th>
					<td><input type="file"  name="files" multiple="multiple"/></td>
				</tr>
				<tr>
					<th>기존 이미지</th>
					<td>
						<c:if test="${not empty files}">
							<c:if test="${files.size()>0}">
								<c:forEach var="file" items="${files}">
									<div data-filename="${file.new_filename}" class="photo">
										<img width="150px" alt="${file.ori_filename}" src="/photo/${file.new_filename}" >
									
										<img src="/img/ximg.png" class="delete_btn" onclick="deleteImg('${file.new_filename}')"/>

									</div>
								</c:forEach>
							</c:if>
						</c:if>
						<c:if test="${empty files}">
							파일이 없습니다.
						</c:if>
					</td>
				</tr>
				<tr>
					<th colspan="2">
						<button type="button" onclick="boardUpdate()">수정하기</button>
					</th>
				</tr>
			</table>
		</form>
	</div>
	<br/>
</body>

<script>
	function boardUpdate(){
		
		var form = new FormData($('form')[0]);
		
		// FormData의 모든 key-value 쌍을 출력
		for (var pair of form.entries()) {
		    console.log(pair[0] + ': ' + pair[1]); // key와 value를 출력
		}
		
  		$.ajax({
			type: 'PUT',
			url: "/boardUpdate",
			data: form,
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
	
	function deleteImg(new_filename){
		//console.log(file);
		
		if(confirm("삭제하시겠습니까?")){
			
			$.ajax({
				type: 'DELETE',
				url: '/deleteFile',
				data: {'new_filename' : new_filename},
				dataType: 'JSON',
				success: function(data){
					if(data.success){
						// 삭제 성공 시 해당 이미지를 포함하는 div 요소 삭제
						$('div[data-filename="' + new_filename + '"]').remove();
					}
				},error: function(e){
					console.log(e);
				}
			});
			
		}
		
		
	}

</script>

</html>