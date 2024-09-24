<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/table.css" type="text/css">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<style>
</style>
</head>
	<h2>List</h2>
	<div class='container'>
	<table>
			<tr>
				<th>no</th>
				<td>${detail.idx}</td>
			</tr>
			<tr>
				<th>user</th>
				<td>${detail.user_name}</td>
			</tr>
			<tr>
				<th>Title</th>
				<td>${detail.subject}</td>
			</tr>
			<tr>
				<th>Content</th>
				<td>${detail.content}</td>
			</tr>
			<c:if test="${files.size()>0}">
				<c:forEach items="${files}" var="file">
					<tr>
						<th>Img</th>
						<td>
							<a href="download?new_filename=${file.new_filename}&ori_filename=${file.ori_filename}">
								<img width="500" src="/photo/${file.new_filename}" alt="${file.ori_filename}"/>
							</a>
						</td>
					</tr>
				</c:forEach>
			</c:if>
			
			<tr>
				<th>Data</th>
				<td>${detail.reg_date}</td>
			</tr>
			<tr>
				<td colspan="2">
				<button><a href="updateForm?idx=${detail.idx}">Update</a></button>
			</tr>
	</table>
	<button onclick='location.href="board_list"'>Back</button>
	</div>
</body>
</html>