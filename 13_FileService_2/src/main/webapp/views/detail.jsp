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
		<table>
			<caption>상세보기</caption>
			<tr>
				<th>작성자</th>
				<td>${info.user_name}
				</td>
			</tr>
			<tr>
				<th>제목</th>
				<td>${info.subject}
				</td>
			</tr>
			<tr>
				<th>내용</th>
				<td>${info.content}	</td>
			</tr>

			<c:if test="${files.size()>0}">
			<tr>	
				<th>이미지</th>
				<td>
				<!-- /photo 라는 컨텍스트 요청이 있으면 C:/upload 로 연결하도록 설정 되어야 한다.(server.xml)-->
				<!-- <Context docBase="C:/upload" path="/photo" />  -->
				<c:forEach items="${files}" var="file">
					<a href="download.do?new_filename=${file.new_filename}&ori_filename=${file.ori_filename}">
						<img width="500" alt="${file.ori_filename}" src="/photo/${file.new_filename}">											
					</a>
					<br/>
				</c:forEach>				
				</td>		
			</tr>
			</c:if>			

			<tr>
				<th colspan = "2">
					<a href="list.do">리스트</a>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="update.go?idx=${info.idx}">수정</a>
				</th>
			</tr>
		</table>
</body>
<script>

</script>
</html>









