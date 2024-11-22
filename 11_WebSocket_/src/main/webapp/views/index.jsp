<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
 <script src="https://code.jquery.com/jquery-3.7.1.slim.min.js"></script>
 <style>
 		#popup{
 			display: none;
 			position: absolute;
 			width: 200px;
 			height: 50px;
 			border: 1px solid black;
 			bottom: 1%;
 			right: 1%;
 		}
 		
 		div.bg{
 			position: absolute;
 			width: 90%;
 			height: 15px;
 			border: 1px solid gray;
 			top: 10%;
 			left: 4%;
 			border-radius: 5px;
 		}
 		
 		div.bar{
 			height: 15px;
 			background-color: #4641D9;
 			font-size: 12px;
 			font-weight: 500;
 			color : #fff;
 			text-align: center;
 		}

 </style>
</head>
<body>
	<button onclick="socketOn()">웹소켓 연결</button>
	<button onclick="sendMsg('start')">다운로드 시작</button>
	<button onclick="disConn()">연결 끊기</button>
	<div id="popup">
		<div id="msg">
			<div class="bg">
				<div class="bar">0%</div>
			</div>
		</div>
	</div>
</body>
<script>
	// 웹 소켓 사용을 위해 웹 소켓 객체가 필요
	var webSocket = null;
	
	// 내장 톰켓으로는 사용이 불가능하다. (외장에서 WS 프로토콜을 받지 못해서 사용 불가능 / socketJS를 사용하면 쓸 수 있다. (라이브러리))
	//	-> 웹소켓이 불안전 하기 때문에 http 라이브러리를 같이 사용해 줘야하는 것이 이유
	function socketOn(){
		console.log('실행');
			
		var url = "ws://localhost:8080/11_WebSocket_/chat"; // ws 프로토콜을 사용하기 때문에 http x / ws o
		// webSocket 객체화
		webSocket = new WebSocket(url);
		// js 의 클래스를 호출하는 것 (원래 없었는데 사이즈가 커지면서 추가적으로 도입됨 (그래서 앞에서 대문자))
	
		// 객체화 하면 켜졌을 때 이벤트, 꺼졌을 때 이벤트, 내가 보냈을 때 이벤트, 들어올 때 이벤트 가 오버라이딩 되어야 한다. (그렇기만 js에는 기능이 없기 때문에 직접 등록해줘야 한다.)
		
		// 1. 웹 소켓이 연결되었을 때 (onopen -> 이벤트)
		//	(client <- server)
		webSocket.onopen = function(evt){ // 데이터 또는 이벤트 객체를 준다.
				console.log('웹 소켓 연결  ', evt);
		}
		
		// 2. 웹 소켓이 끊겼을 때
		//	(client <- server)
		webSocket.onclose = function(evt){ // 데이터 또는 이벤트 객체를 준다.
				console.log('웹 소켓 종료  ', evt);
		}
		
		// 3. 메시지 수신 시
		//	(client <- server)
		webSocket.onmessage = function(evt){ // 데이터 또는 이벤트 객체를 준다.
			//	console.log('메시지 수신  ', evt);
				
				 $('.bar').css('width', evt.data);
		 	  	 $('.bar').html(evt.data);
				
		 	  	 // 데이터가 온 후 에 alert를 띄우는게 아니라 표시에 된 후에 alert를 띄어야 한다.
						 	  	 
/* 				if($('.bar').css('width') === '100%'){ // css가 100인지를 확인해줘야 한다. (alert가 먼저 실행되어버려서, 넘어오는 값이 적용보다 더 빠르니까)
					if(evt.data === '100%'){
							alert('완료되었습니다.');
							$('#popup').hide();
					}	
				} */
			    
				
				if( $('.bar').html()  === '100%'){
						// 이렇게 강제로 시간차를 줘야 100% 노출 후 볼 수 있음 (근데 이렇게까지 하지 말아라..!)
						setTimeout(() => {
								alert('다운로드 완료'); // confirm 도 안됨
								$('#popup').hide();
						}, 10);
				}
		}

	}
	
		// 4. 웻 소켓 메시지 보내기 (4, 5이 http 통신에서는 불가능한 내용)
		//	(client -> server)
		function sendMsg(msg){
			$('#popup').show();
				webSocket.send(msg);
				console.log(msg);
		}
		
		// 5.  웹 소켓 종료
		//	(client -> server)
		function disConn(){
				webSocket.close();
		}
</script>
</html>