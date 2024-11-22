package kr.co.gudi.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ServerEndpoint(value="/chat/{userId}")
public class WebSocketController {
	
		Logger logger = LoggerFactory.getLogger(getClass());

		static Map<String, Session> userList = new HashMap<String, Session>();
		
		@OnOpen
		public void onOpen(Session session, @PathParam(value = "userId") String userId){
				// nick 는 중복 x 
				// session 을 저장해서 각각 세션에 보내줘야 하기 때문에 세션을 저장하고 nick는 중복이 x
				logger.info("접속 세션 => " + session + " nick => " + userId);
				
				// 중복되는 유저가 있으면 안된다. 그래서 중복되는지를 검사해줘야 한다.
				if(userList.keySet().contains(userId)){
					  sendMsg(session, userId + " 는 이미 사용중인 닉네임 입니다.");
				}else {
					  userList.put(userId, session);
					
				}
				logger.info("회원 리스트 => " + userList.keySet());
		}
		
		@OnClose
		public void onClose(Session session) {
				logger.info("끊긴 세션 => " + session.getId());
				
				String closeId = session.getId();
//				Set<Entry<String, Session>> enrtys = userList.entrySet();
				for (Entry<String, Session> entry : userList.entrySet()) {
						if(entry.getValue().getId().equals(closeId)) {
							  userList.remove(entry.getKey());
							  break; // 1번째가 100번째가 10000번재가 삭제될 수도 있기 때문에 끝나고 나면 꼭 빠져나가줘야 한다.!
						}
				
				}
				
				logger.info("회원 리스트 => " + userList.keySet());
		}
		
		@OnError
		public void onError(Session session, Throwable e) {
				logger.info("에러난 세션 => " + session.getId());
				logger.info(e.toString());
		}
		
		/**
		 *  메시지 수신 시
		 */
		@OnMessage
		public void onMessage(Session session, String msg) {
				logger.info("받은 메시지 => " + msg + " : 보낸 세션 => " + session.getId());
		}
		
		/*
		 * 개별 메세지 전송 (단체 채팅에서 1명에게만 보내는 경우) 
		 */
		public void sendMsg(Session session, String msg) {
				try {
					session.getBasicRemote().sendText(msg);
					logger.info("보낸 메시지 " + msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

}
