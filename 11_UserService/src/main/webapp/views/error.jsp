<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/common.css" type="text/css">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
	<h2>Error Page</h2>
	<h3>${code} Error : ${msg}</h3>
	<a href="./">로그인 페이지로 돌아가기</a>
</body>
</html>