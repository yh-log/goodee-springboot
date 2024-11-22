package kr.co.gudi.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EncodeController {

	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired PasswordEncoder encoder;
	
	
	private String hash = "";
	
	@GetMapping(value="/encode/{msg}")
	public Map<String, String> encode(@PathVariable String msg){
		logger.info("plain text(평문) : " + msg);
		Map<String, String> map = new HashMap<String, String>();
		map.put("plain", msg);
		
		// 평문을 암호화 
		// 암호화 시 salt 라는 무작위 값을 넣어서 같은 값을 넣어도 각각 다른 해쉬값이 나오도록 한다.
		// 암호를 확인하기 위해서는 spring security 에서 제공하는 메서드를 사용해야 한다.
		
		hash = encoder.encode(msg);
		logger.info("encoded text : " + hash);
		map.put("hash", hash);
		
		return map;
	}
	
	@GetMapping(value="/decode/{msg}")
	public Map<String, Boolean> decode(@PathVariable String msg){
		
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		logger.info("plain text : " + msg);
		
		boolean match = encoder.matches(msg, hash);
		map.put("success", match);
		
		return map;
	}
}
