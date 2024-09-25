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
	<form action="update.do" method="post">
		<table>
			<caption>${info.id} 님의 정보</caption>
			<tr>
				<th>ID</th>
				<td><input type="text" name="id" value="${info.id}" readonly="readonly"/></td>
			</tr>
			<tr>
				<th>PW</th>
				<td><input type="password" name="pw" value="${info.pw}"/></td>
			</tr>
			<tr>
				<th>NAME</th>
				<td><input type="text" name="name" value="${info.name}"/></td>
			</tr>
			<tr>
				<th class="co">AGE</th>
				<td><input type="text" name="age" value="${info.age}"/></td>
			</tr>
			<tr>
				<th class="co">GENDER</th>
				<td>
					<input type="radio" name="gender" value="남" 
					<c:if test="${info.gender eq '남'}">checked</c:if>
					/>남
					&nbsp; &nbsp; &nbsp; &nbsp;
					<input type="radio" name="gender" value="여"
					<c:if test="${info.gender eq '여'}">checked</c:if>
					/>여
				</td>
			</tr>
			<tr>
				<th class="co">EMAIL</th>
				<td><input type="email" name="email" value="${info.email}"/></td>
			</tr>
			<tr>
				<th colspan="2">
					<input type="button" value="수정요청" onclick="update()"/>
				</th>
			</tr>
		</table>
	
	</form>
	
</body>
<script>
	var msg = "${msg}";
	if(msg != ''){
		alert(msg);
	}
	
	function update(){
		// id,pw,name,age,gender,email 중에서 변경이 생긴 값만 파라미터로 전달
		// 절대로 변경이 생기지 않지만 반드시 들어가야 하는 것 = id 
		var param = {id:'${info.id}'};
		
		$('form input').each(function(idx,item){
			//console.log($(item));
			
			var type = $(item).attr('type');
			if(type == 'radio'){
				//console.log($(item));
				if($(item)[0].defaultChecked != $(item)[0].checked && $(item)[0].checked){
					param[$(item).attr('name')] = $(item).val();
				}
			}else{
				//console.log($(item)[0].defaultValue, $(item)[0].value); // .[0]로 인덱스 지정해줘도 됨 (js 꺼라서)
				if($(item)[0].defaultValue != $(item)[0].value){
					param[$(item).attr('name')] = $(item).val(); // item 에 name을 key 로 value 를 값으로 
				}
			}
		});
		
		console.log(param);
		
		$.ajax({
			type: 'POST',
			url: 'update.ajax',
			data: param,
			dataType: 'JSON',
			success: function(data){
				console.log(data);
				if(data.success){
					alert('수정에 성공했습니다.');
					location.href='detail.do?id=${info.id}';
				}else{
					alert('수정에 실패했습니다.');
				}
			},error: function(e){
				console.log(e);
			}
		})
		
		
	}
</script>
</html>