package kr.co.gudi.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.gudi.service.BoardService;

@Controller
public class BoardController {

	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired BoardService board_service;
	
	@RequestMapping(value="/")
	public String home() {
		return "list";
	}
	
	@GetMapping(value="/list.ajax")
	@ResponseBody
	public Map<String, Object> list(String page, String cnt) {
		
		int page1 = Integer.parseInt(page);
		int cnt1 = Integer.parseInt(cnt);

		return board_service.list(page1, cnt1);
	}
	
	
}
