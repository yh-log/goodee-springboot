package kr.co.gudi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import kr.co.gudi.service.ApiService;

@RestController
public class ReceiveController {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	private final ApiService apiService;
	
	public ReceiveController(ApiService apiService) {
		this.apiService = apiService;
	}
	
	@GetMapping(value="/return/{msg}")
	public Map<String, String> getReturn(@PathVariable String msg){
		
		logger.info("받은 msg (re) => " + msg);
		Map<String, String> resp = new HashMap<String, String>();
		resp.put("(re) msg", "당신이 보낸 메세지 : " + msg);
		resp.put("(re) 수신한 사람 이름 : ", "김지원");
		return resp;
	}
	
	// 네트워크는 문자열만 받는게 원칙이지만
	// 신버전에서는 int도 받을 수 있다. (보내는 값이 int니까 int로 받아봄)
	@PostMapping(value="/listReturn")
	public ArrayList<HashMap<String, Object>> postReturn(int cnt, String name,
			@RequestHeader HashMap<String, String> header){
		
		logger.info("받은 cnt => " + cnt);
		logger.info("받은 header => " + header);
		logger.info("발신자 => " + name);
		
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object> map = null;
		
		for(int i = 0; i < cnt; i++) {
			map = new HashMap<String, Object>();
			map.put("no", i);
			map.put("data", "data-"+i);
			map.put("salaty", i*10000);
			map.put("sender", name);
			map.put("receiver", "김지원");
			list.add(map);
		}
		
		return list;
	}
	
	// 복잡한 형태로 받을 때에는 RequestBody 형태로 받아야 함
	@PostMapping(value="/fluxReturn")
	public List<HashMap<String, Object>> fluxReturn(@RequestBody HashMap<String, Object> param){
		
		logger.info("param => {} " + param);
		
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object> map = null;
		
		for(int i = 0; i <= 10; i++) {
			map = new HashMap<String, Object>();
			map.put("no", i);
			map.put("data", "data-"+i);
			map.put("salaty", i*10000);
			map.put("sender", param.get("name"));
			map.put("receiver", "김지원");
			list.add(map);
		}
		
		return list;
	}
	
	// 통신 부분은 예민하다. (다른 회사와 회사와의 서버 통신이 이루어지기 때문에)
	// 그래서 신입에게는 잘 안시키지만 언젠가는 할 수 있어야 한다.


}
