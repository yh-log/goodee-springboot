package kr.co.gudi.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.gudi.dto.BoardDTO;
import kr.co.gudi.service.BoardService;

@Controller
public class EditorController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired BoardService board_service;
	
	/**
	 * author yh.kim (24.11.18)
	 * 메인 이동
	 */
	@GetMapping(value="/")
	public String home() {
		return "basic";
	}

	/**
	 * author yh.kim (24.11.19)
	 * 게시글 입력(파일 포함:base64)
	 */
	@PostMapping(value="/write.do")
	public ModelAndView write(@RequestParam Map<String, String> param) {
		
		logger.info("param : {} " + param.get("subject"));
		logger.info("param : {} " + param.get("user_name"));
		logger.info("param : {} " + param.get("content").length());
		
		if(board_service.write(param)) {
			return new ModelAndView("list");
		};
		
		return new ModelAndView("basic");
	}
	
	/**
	 * author yh.kim (24.11.19)
	 * 리스트 출력
	 */
	@PostMapping(value="/list.ajax")
	@ResponseBody
	public Map<String, Object> list(){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", board_service.list());
		
		return resultMap;
	}
	
	/**
	 * 리스트 페이지 이동
	 */
	@GetMapping(value="/list.go")
	public String listView() {
		return "list";
	}

	/**
	 * 디테일(상세) 페이지 이동
	 */
	@GetMapping(value="/detail.go")
	public ModelAndView detail(String idx) {
		logger.info("idx => " + idx);
		String code = "detail";
		return board_service.detail(idx, code);
	}

	/*
	 * 업데이트 페이지 이동 (서비스 - 디테일 메서드 활용)
	 */
	@GetMapping(value="/update.go")
	public ModelAndView update(String idx) {
		logger.info("idx => " + idx);
		String code = "update";
		return board_service.detail(idx, code);
	}
	
	/*
	 * 업데이트 진행
	 */
	@PostMapping(value="/update.do")
	public String update(@RequestParam Map<String, String> param) {
		logger.info("param => {} "+ param);
		String page = board_service.update(param);
		return page;
	}
	
}
