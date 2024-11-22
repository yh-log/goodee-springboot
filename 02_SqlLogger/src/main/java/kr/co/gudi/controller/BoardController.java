package kr.co.gudi.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.gudi.service.BoardService;

@Controller
public class BoardController {

	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired BoardService board_service;
	
	@RequestMapping(value="/")
	public String main() {
		return "list";
	}
	
	@PostMapping(value="/boardlist")
	@ResponseBody
	public Map<String, Object> list(){
		logger.info("실행?");
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("result", board_service.boardlist());
		return resultMap; 
	}
	
}
