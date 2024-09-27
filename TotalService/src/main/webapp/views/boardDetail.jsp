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
</style>
</head>
<body>
	<h2>게시글 상세보기</h2>
	<input type="text" name="idx" value="${result.idx}" hidden/>
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
				<!-- /photo 라는 컨텍스트 요청이 있으면 C:/upload 로 연결하도록 설정 되어야 한다.(server.xml)-->
				<!-- <Context docBase="C:/upload" path="/photo" />  -->
					<c:forEach items="${files}" var="file">
						<a href="download.do?new_filename=${file.new_filename}&ori_filename=${file.ori_filename}">
							<img width="300" alt="${file.ori_filename}" src="/photo/${file.new_filename}">									
						</a><br/>
					</c:forEach>				
					</td>		
				</tr>
			</c:if>	
			<tr>
				<th colspan="2">
					<button onclick="update()">수정하기</button>
					<button onclick="list()">리스트</button>
				</th>
			</tr>
			
	</table>
</body>
<script>
	function update(){
		location.href='/TotalService/updateView/'+'${result.idx}';
	}
	
	function list(){
		location.href= '/TotalService/boardListView';
	}
</script>
</html>