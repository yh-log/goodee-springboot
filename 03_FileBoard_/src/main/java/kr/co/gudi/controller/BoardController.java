package kr.co.gudi.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import kr.co.gudi.service.BoardService;

@RestController
public class BoardController {
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired BoardService board_service;
	
	// 게시글 목록
	@RequestMapping (value = "/board_list.go")
	public ModelAndView list(HttpSession session) {
		return new ModelAndView("board_list");
	}
	
	@RequestMapping (value = "/board_list.ajax")
	public Map<String, Object> list(String cnt) {
		return board_service.list(Integer.parseInt(cnt));
	}
	
	// 상세보기
	@RequestMapping (value = "/board_detail.go")
	public ModelAndView list(String idx, Model model, HttpSession session) {
		board_service.detail(idx, model);
		return new ModelAndView("board_detail");
	}
	
	// 게시글 쓰기
	@RequestMapping (value = "/board_write.go")
	public ModelAndView write(Model model, HttpSession session) {
		return new ModelAndView("board_write");
	}

	@RequestMapping (value = "/board_write.do")
	public ModelAndView write(@RequestParam Map<String, String> params) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("board_write.go");
		int idx = board_service.write(params);
		
		if(idx > 0) {
			mav.setViewName("redirect:/board_detail.go?idx="+idx);
		}
		
		return mav;
	}
	
	// 게시글 삭제
	@RequestMapping (value = "/board_delete.do")
	public ModelAndView delete(String idx) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/board_detail.go?idx="+idx);
		
		if (board_service.delete(idx)) {
			mav.setViewName("redirect:/board_list.go");
		}
		
		return mav;
	}

}