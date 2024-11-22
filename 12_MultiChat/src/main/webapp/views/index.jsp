<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
 <script src="https://code.jquery.com/jquery-3.7.1.slim.min.js"></script>
 <style>
 		#monitor{
 				margin: 5px;
 				width: 70%;
 				height: 700px;
 				overflow: auto; /*자동으로 스크롤 생김 (데이터 넘치면)*/
 				border: 1px solid black;
 		}
 		input[type="button"]{
 				
 		}
 </style>
</head>
<body>
		<h3>웹 소켓 페이지</h3>
		대화명 : <input type="text" id="nick"/> <input id="login" type="button" value="로그인"/>
		<!-- contenteditable="true" : div 안에 글자를 넣을 수 있음 -->
		<div id="monitor" contenteditable="true"></div>
		<div>
				메시지 : <input type="text" id="msg"/>
				<input type="button" value="전송" onclick="sendMsg()"/>
				<input type="button" value="나가기" onclick="disConn()"/>
		</div>
</body>
<script>

		var webSocket = null;
		
		var monitor = $('#monitor')[0]; // 자바스크립트 객체로 사용하기 위해 [0] 을 넣어줌

		$('#login').click(function(){
				// 웹 소켓 생성 및 접속
				var url = "ws://localhost:8080/12_MultiChat/chat";
				webSocket = new WebSocket(url + "/" + $('#nick').val());
				
				// 소켓 연결할 때 
				webSocket.onopen = function(evt){
						console.log('연결');
						$('#login').attr('disabled', 'true'); // true 없이 해도 됨
				}
				
				// 소켓 접속 끊어졌을 때
				webSocket.onclose = function(evt){
						console.log('연결 끊김');
						$('#login').attr('disabled', 'false'); // false 없이 해도 됨
				}
				
				// 메시지 수신했을 때
				webSocket.onmessage = function(evt){
						console.log('메시지 수신 => ', evt.data);
						monitor.innerHTML += evt.data + '<br/>';
				}
				
		});
		
		
		// 전송 기능
		function sendMsg(){
				var msg = $('input[id="msg"]').val();
				console.log('보낸 메시지 => ', msg);
				webSocket.send(msg);
				$('#monitor').append('<p> ' + msg + '</p>');
			
		}
		
		// 나가기 기능
		function disConn(){
				webSocket.close();
		}
		
</script>
</html>