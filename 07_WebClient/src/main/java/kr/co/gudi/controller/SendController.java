package kr.co.gudi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.gudi.service.ApiService;

@RestController
public class SendController {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	private final ApiService apiService;
	
	public SendController(ApiService apiService) {
		this.apiService = apiService;
	}
	
//	@RequestMapping(value="/")
//	public String home() {
//		return "/[method]/send/[msg] 로 요청을 보내 보세요!";
//	}
	
	@GetMapping(value="/get/{msg}")
	public Map<String, String> getSend(@PathVariable String msg){
		logger.info("(se) 전송 메시지" + msg);
		return apiService.getSend(msg);
	}
	
	@PostMapping(value="/post/send/{cnt}")
	public ArrayList<HashMap<String, Object>> postSend(@PathVariable String cnt,
			@RequestHeader Map<String, String> header){
		
		logger.info("cnt => " + cnt);
		logger.info("header => " + header); // header는 map 형태와 유사하다.
		
		logger.info("key => " + header.get("authorization")); // 대소문자 구분 x 소문자로만 나옴
		String key = header.get("authorization");
		return apiService.postSend(cnt, key);
	}
	
	@GetMapping(value="/get/flux")
	public List<HashMap<String, Object>> fluxTest(){
		
		
		return apiService.fluxTest();
	}
}
