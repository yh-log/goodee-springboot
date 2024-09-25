<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="resources/css/common.css">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<style>
	
</style>
</head>
<body>

	<form action="list.do" method="get">
		<select name="opt">
			<option value="id">아이디</option>
			<option value="name">이름</option>
			<option value="email">이메일</option>
		</select>
		<input type="text" name="keyword" placeholder="검색어를 입력하세요"/>
		<button>검색</button>
	</form>



	<h3 class="inline">회원 리스트</h3>
	<button onclick="location.href='join.go'">회원가입</button>
	<table>
		<thead>
			<tr>
				<th>ID</th>
				<th>이름</th>
				<th>이메일</th>
				<th>삭제</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${list}" var="member"> <!-- C태그는 돔 객체여서 자식 요소로 인정 x -->
			<tr>
				<td>${member.id}</td>
				<td><a href="detail.do?id=${member.id}">${member.name}</a></td>
				<td>${member.email}</td>
				<td><a href="del.do?id=${member.id}">삭제</a></td> <!-- get 방식으로 보내겠다는 의미! -->
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="search_layer">
		<form action="multi.do" method="post">
			이름이 
				<ul>
					<li>
						<input type="text" name="userName"/>
						<!-- submit을 막기 위해 input type="button" 사용 -->
						<input type="button" value="또는" onclick="add(this)"/>
					</li>
				</ul>
			인 회원 찾기
			<button>찾기</button>
		</form>
	</div>
	
</body>
<script>

	function add(elem){
		// clone() : 해당 요소를 복제하는 함수

		$('ul').append('<li>'+$(elem).parent().html()+'</li>');
		//$(elem).parent().parent().append('<li>'+$(elem).parent().html()+'</li>');
	}

</script>
</html>