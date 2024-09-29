package kr.co.gudi.controller;

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
	

	// 세션체크 추가해야 함!!
	@GetMapping(value="/list.do")
	@ResponseBody
	public Map<String, Object> getBoardList(String page, String cnt){
		
		logger.info(page + " / " + cnt);
		
		int page_ = Integer.parseInt(page);
		
		int cnt_ = Integer.parseInt(cnt);
		
		return board_service.list(page_, cnt_);
	}
}
