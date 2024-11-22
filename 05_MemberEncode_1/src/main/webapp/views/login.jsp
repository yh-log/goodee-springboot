<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<style>

table,th,td{
   border: 1px solid black;
   border-collapse: collapse;
   padding:  5px 10px;
}   
   
</style>
</head>
<body>
   <form action="login.do" method="post">
   <table>
   	  <caption>로그인 페이지</caption>
      <tr>
	      <th>ID</th>
	      <td><input type="text" name="id" value=""/></td>
      </tr>
      
      <tr>
	      <th>pw</th>
	      <td><input type="password" name="pw" value=""/></td>
      </tr>
      
      <tr>
	      <th colspan="2">
	      <button>로그인</button>
	      <a href = "join.go">회원가입</a>
	      </th>
  	  </tr>
      
   </table>
   
   </form>   
      
</body>

<script>
	var msg = '${msg}';
	if (msg !== '') {
	    alert(msg);
	}
</script>

</html>