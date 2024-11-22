package kr.co.gudi.controller;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ServerEndpoint(value="/chat") // @Controller 말고 @ServerEndpoint() 어노테이션을 선언해준다.
public class WebSocketController {

		Logger logger = LoggerFactory.getLogger(getClass());
		
		/*
		 * 열렸을 때
		 */
		@OnOpen
		public void onOpen(Session session) { // OnOpen 은 접속했을 때 session을 보내준다. (http 세션은 저장하는 공간이지만 웹 소켓에서 세션은 소켓의 역할을 한다)
				// WebSocket에서 session 은 소켓과 같은 존재이다.
				// 즉 세션을 가지고 전송을 키켜줄 수 있다.
				logger.info("접속한 세션 id => " + session.getId()); // 이렇게 하면 단순한 숫자만 나온다.
		}
		
		/*
		 * 닫혔을 때
		 */
		@OnClose
		public void onClose(Session session) {
				logger.info("종료한 세션 id => " + session.getId());
		}
		
		/*
		 * 에러 발생 시
		 */
		@OnError
		public void onError(Session session, Throwable e) {
				// Throwable : 어떤 에러인지 알기 위해 추가
				logger.info("문제 발생 세션 id => " + session);
				logger.info(e.toString());
		}
		
		/*
		 * 메시지 받았을 때
		 */
		@OnMessage
		public void onMsg(Session session, String msg) throws Exception {
				logger.info("전달 받은 메시지 => " + msg + "메시지 보낸 id => " + session);
				
				// 보내준 % 로 view에서 bar를 움직여줘야 함
				for(int i = 1; i <= 100; i++) {
						Thread.sleep(50);
						sendMsg(session, i + "%");
				}
				
		}
		
		// 메시지 보내기
		public void sendMsg(Session session, String msg) throws IOException {
				session.getBasicRemote().sendText(msg);
				logger.info("보낸 메시지 " + msg);
		}
		
}
