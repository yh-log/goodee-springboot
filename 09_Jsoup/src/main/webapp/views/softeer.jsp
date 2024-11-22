<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	ul{
		list-style-type: none;
		border: 1px solid gray;
		width: 800px;
		padding-bottom: 10px;
	}
	li{
		font-size: 13px;
		margin-left: -30px;
	}
	.tag{
		color : gray;
		margin: 5px 0px 5px -30px;
	}
	.title{
		font-weight : 600;
		font-size: 16px;
		margin: 5px 0px 15px -30px;
	}
	a{
		text-decoration: none;
		color : black;
	}
	
	.date{
		color : gray;
		text-align: right;
   	    margin-right: 10px;
	}
</style>

</head>
<body>
	<c:forEach items="${elem}" var="item">
		<ul>
			<li class="title">
				<a href="${item.link}">${item.title}</a>
			</li>
			<li>${item.content}</li>
			<li class="tag">${item.tags}</li>
			<li class="date">${item.reg_date}</li>
		</ul>
	</c:forEach>
</body>
</html>