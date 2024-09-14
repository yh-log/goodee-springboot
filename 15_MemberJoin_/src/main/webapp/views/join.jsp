<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
		<style>
			table, th, td{
				border: 1px solid black;
				border-collapse: collapse;
				padding: 5px 10px;
			}
		</style>
	</head>
	<body>
		<form action="join.do" method="post">
			<table>
				<caption>회원가입</caption>
				<tr>
					<th>ID</th>
					<td>
						<input type="text" name="id"/>
						<!-- button 태그는 원래 submit 역할을 수행한다.
						하지만 type="button" 을 넣으면 버튼 역할만 수행한다. -->
						<button type="button" id="overlay">중복체크</button>
						<p id="result"></p>
					</td>
				</tr>
				
				<tr>
					<th>PW</th>
					<td>
						<input type="text" name="pw"/>
					</td>
				</tr>
				
				<tr>
					<th>NAME</th>
					<td>
						<input type="text" name="name"/>
					</td>
				</tr>
				
				<tr>
					<th>AGE</th>
					<td>
						<input type="text" name="age"/>
					</td>
				</tr>
				
				<tr>
					<th>GENDER</th>
					<td>
						<input type="radio" name="gender" value="남"/> 남
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="gender" value="여"/> 여
					</td>
				</tr>
				
				<tr>
					<th>EMAIL</th>
					<td>
						<input type="text" name="email"/>
					</td>
				</tr>
				
				<tr>
					<th colspan="2">
						<input type="submit" value="회원가입"/>
					</th>
				</tr>
			</table>
		</form>
	</body>
	<script>
		var msg = '${result}';
		if(msg != ''){
			alert(msg);
		}
		
		$('#overlay').click(function(){
			var id = $('input[name="id"]').val();
			console.log(id);
			$.ajax({
				type: 'get',
				url: 'overlay.do',
				data: {'id':id}, // id라는 이름으로 id 변수를 넣겠다.
				dataType: 'JSON',
				success: function(data){
					console.log(data);
					if(data.overlay>0){
						$('#result').html(id + '는 이미 사용중입니다.');
						$('#result').css({'color':'red'});
					}else{
						$('#result').html(id + '는 사용가능합니다.');
						$('#result').css({'color':'green'});
					}
				},
				error:function(e){
					console.log(e);
				}
				
			})
		});
	</script>
</html>