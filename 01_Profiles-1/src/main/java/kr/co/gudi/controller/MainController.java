package kr.co.gudi.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.classic.Logger;

//@Controller
@RestController // 이걸 쓰면 해당 클래스 내부의 모든 메서드는 @ResponseBody 효과를 갖게 된다.
public class MainController {
	
	@Value("${prop.name}") String name;
	@Value("${db.addr}") String addr;
	@Value("${db.username}") String username;
	@Value("${db.password}") String password;
	

	Logger logger = (Logger) LoggerFactory.getLogger(getClass());
	
	@RequestMapping(value="/")
	// @ResponseBody
	public Map<String, String> main(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("msg", "Hello Spring Boot");
		map.put("name", name);
		map.put("addr", addr);
		map.put("username", username);
		map.put("password", password);
		
		return map;
	}
	
	// 1. STS - 내장 톰켓
	// 일단 Spring Boot App 을 실행한 후
	// Run > RunConfiguration > Spting Boot App > profile 을 dev로 선택
	
	// 2. STS - 외장 톰켓
	// Server 폴더에 catalina.properties 파일 열기
	// spring.profiles.active=dev
	
	// 3. 진짜 톰켓 
	// conf 폴더에 catalina.properties 파일 열기
	// spring.profiles.active=dev

	// war 직접 실행시
	// java-jar ROOT.war --spring.profiles.active=dev
			
}
