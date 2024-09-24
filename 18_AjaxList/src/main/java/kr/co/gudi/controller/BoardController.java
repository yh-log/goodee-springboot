package kr.co.gudi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.gudi.service.BoardService;

@Controller
public class BoardController {

	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired BoardService board_service;
	
	@GetMapping(value="/")
	public String home() {
		return "list";
	}
	
	@GetMapping(value="/list.ajax")
	@ResponseBody
	public Map<String, Object> list(){
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<HashMap<String, Object>> list = board_service.list();
		
		map.put("list", list);
		
		return map;
	}
	
}
