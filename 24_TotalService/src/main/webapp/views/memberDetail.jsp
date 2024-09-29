<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Member Detail page</title>
<link rel="stylesheet" href="resources/css/common.css" type="text/css">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<style>
</style>
</head>
<body>
	<h2>회원 상세정보</h2>
	<form>
	
		<table>
			<tr>
				<th>ID</th>
				<td><input type="text" name="id" value="${info.id}" readonly/></td>
			</tr>
			<tr>
				<th>PW</th>
				<td><input type="password" name="pw" value="${info.pw}"/></td>
			</tr>
			<tr>
				<th>NAME</th>
				<td><input type="password" name="pw" value="${info.pw}"/></td>
			</tr>
			<tr>
				<th>AGE</th>
				<td><input type="text" name="age" value="${info.age}"/></td>
			</tr>
			<tr>
				<th>GENDER</th>
				<td>
					<input type="radio" name="gender" value="남"
						<c:if test="${info.gender eq '남'}">checked</c:if>/>남
					
					<input type="radio" name="gender" value="여"
					<c:if test="${info.gender eq '여'}">checked</c:if>/>여		
				</td>
			</tr>
			<tr>
				<th>EMAIL</th>
				<td><input type="email" name="email" value="${info.email}"/></td>
			</tr>
			<tr>
				<th colspan="2">
					<button type="button" onclick="update()">수정</button>
					<button type="button" onclick="delete()">삭제</button>
				</th>
			</tr>
		</table>
	</form>
	
</body>
<script>
	function update(){
		var param = {id : '${info.id}'};
		
		$('form input').each(function(item, idx){
			if($(item).attr('type') == 'radio'){
				if($(item)[0].defaultChecked != $(item)[0].Checked){
					param[$(item).attr('name')] = $(item).val();
				}				
			}else{
				if($(item)[0].defaultValue != $(item)[0].Value){
					param[$(item).attr('name')] = $(item).val();
				}
			}
		});
		
		$.ajax({
			type: 'POST',
			url: 'update',
			data: param,
			dataType: 'JSON',
			success: function(data){
				if(data.success){
					alert('수정되었습니다.');
					location.href = '${pageContext.request.contextPath}'+'/detail/' +${info.id}+'"></a>'
				}else{
					alert('수정에 실패했습니다.');
				}
			},error: function(e){
				console.log(e);
			}
		});
	}
	
	function delete(){
		location.href = '${pageContext.request.contextPath}'+'/detail/' +${info.id}+'"></a>'
	}
	
</script>
</html>