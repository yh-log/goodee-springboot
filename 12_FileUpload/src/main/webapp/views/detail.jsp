<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/common.css" type="text/css">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<style>
	a{
		text-decoration-line: none;
		color: black;
	}
</style>
</head>
<body>
		<table>
			<caption>상세보기</caption>
			<tr>
				<th>작성자</th>
				<td>${info.user_name}</td>
			</tr>
			<tr>
				<th>제목</th>
				<td>${info.subject}</td>
			</tr>
			<tr>
				<th>세부내용</th>
				<td>${info.content}</td>
			</tr>
			<c:if test="${files.size() > 0}">
				<tr>
					<th>이미지</th>
					<td>
						<c:forEach items="${files}" var="file">
							<a href="download?new_filename=${file.new_filename}&ori_filename=${file.ori_filename}">
							<img width="500" alt="${file.ori_filename}" src="/photo/${file.new_filename}"/>
							</a>
						</c:forEach>
					</td>
				</tr>
			</c:if>
			<tr>
				<th>작성일</th>
				<td>${info.reg_date}</td>
			</tr>
			<tr>
				<th colspan="2">
					<a href="list">리스트</a>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="updateForm?idx=${info.idx}">수정</a>
				</th>
			</tr>
		</table>
</body>
</html>