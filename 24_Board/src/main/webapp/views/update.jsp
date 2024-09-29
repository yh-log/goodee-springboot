<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- controller 에 css 라는 요청이 없으면 static 폴더 밑에 css 찾아라 -->
<link rel="stylesheet" href="css/common.css" type="text/css">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<style>

</style>
</head>
<body>
<jsp:include page="loginbox.jsp"/>
	<form action="update.do" method="post" enctype="multipart/form-data">
	<!--  idx 가 안넘어가고 있음!! -->
		<input type="text" name="idx" value="${info.idx}" hidden/>
		<table>
			<caption>수정 상세보기</caption>
			<tr>
				<th>작성자</th>
				<td>
					<input type="text" name="user_name" value="${sessionScope.loginid}" readonly="readonly"/>
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
					<input type="text" name="content" value="${info.content}"/>
				</td>
			</tr>
			<c:if test="${files.size()>0}">
			<tr>
				<th>이미지</th>
				<td>
				<!-- /photo라는 context요청이 있으면 C:/upload로 연결하도록 설정되어야 함(server.xml) -->
					<c:forEach items="${files}" var="file">
							<img width="300px" alt="${file.ori_filename}" src="/photo/${file.new_filename}"><br/>
					</c:forEach>	
				</td>
			</tr>
			</c:if>
			<tr>
				<th>이미지업데이트</th>
				<th><input type="file" name="files" multiple="multiple"/></th>
			</tr>
			

			<tr>
				<th colspan = "2">
					<button>수정완료</button>
					<!-- <a href="update.do?idx=${info.idx}">수정</a> = 이런식으로 들어가면 405 발생함-->
				</th>
			</tr>
		</table>
	</form>
</body>
<script>
	
	
	
</script>
</html>