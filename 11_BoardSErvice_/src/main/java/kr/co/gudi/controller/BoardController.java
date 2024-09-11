package kr.co.gudi.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.gudi.dto.BoardDTO;
import kr.co.gudi.service.BoardService;

@Controller
public class BoardController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired BoardService board_service;
	
	@RequestMapping(value="/list")
	public String list(Model model, HttpSession session) {
		
		String page = "login"; // 실패하면 login 페이지로
		
		List<BoardDTO> list = board_service.list();
		
		BoardDTO board_dto = new BoardDTO();
		
		if(session.getAttribute("loginId") == null) {
			logger.info("list : {}", list);
			model.addAttribute("result", "로그인이 필요한 서비스 입니다.");
		}else {
				page = "list";
				model.addAttribute("list", list);
		}
		return page;
	}
	
	@RequestMapping(value="/detail")
	public String detail(int idx, Model model, HttpSession session) {
		
		String page = "login";
		
		if(session.getAttribute("loginId") == null) {
			model.addAttribute("result", "로그인이 필요한 서비스입니다.");
		}else {
			BoardDTO board_dto = null;
			page = "redirect:/list";
			board_dto = board_service.detail(idx);
			
			if(board_dto != null) {
				page = "detail";
				model.addAttribute("info", board_dto);
			}
		}
		return page;
	}
	
	@RequestMapping(value="/updateForm")
	public String updateForm(Model model, int idx, HttpSession session) {
		
		BoardDTO board_dto = board_service.updateForm(idx);
		model.addAttribute("info", board_dto);
		return "update";
	}
	
	// 나중에 다 세션처리 해줘야 함!!
	@RequestMapping(value="/update")
	public String update(@RequestParam Map<String, String> params) {
		logger.info("params : {}", params);
		board_service.update(params);
		return "redirect:/detail?idx=" + params.get("idx"); //수정에 성공해도 실패해도 idx값은 보내준다.
	}
	
	@RequestMapping(value="/del")
	public String del(int idx) {
		board_service.del(idx);
		return "redirect:/list";
	}
	
	@RequestMapping(value="/writeForm")
	public String writeForm() {
		return "write";
	}
	
	@RequestMapping(value="/write", method = RequestMethod.POST)
	public String write(@RequestParam Map<String, String> params , Model model, HttpSession session) {
		
		logger.info("params : {}", params);
		String page = "login";
		if(session.getAttribute("loginId") == null) {
			model.addAttribute("result", "로그인이 필요한 서비스입니다.");
		}else {
			page = "redirect:/list";
			board_service.write(params);
		}
		return page;
	}
	
	// Spring Boot 에서는 에러 발생 시 /error 라는 요청을 호출하게 되어있다.
	// 하지만 이 요청은 ErrorController 라는 인터페이스에 기본적으로 내장되어있다.
	// 그래서 error 라는 요청을 수행하기 위해서는 위 인터페이스를 구현 받아야 한다.
//	
//	@RequestMapping(value="/error")
//	public String error() {
//		return "error";
//	}
	
//	@RequestMapping(value="/upload")
//	public String upload(@RequestParam Map<String, String> param, Model model) {
//		String msg = board_service.upload(param);
//		model.addAttribute("msg", msg);
//		return "redirect:/list";
//	}
	
//	@RequestMapping(value="/upload")
//	public String upload(Model model, HttpSession session, String user_name) {
//		String msg = board_service.upload();
//		
//		
//		if()
//		
//		return "redirect:/list";
//	}
	
//	@RequestMapping(value="/hit")
//	public String hit(int idx, Model model, HttpSession session) {
//		
//		int hit = board_service.hit(idx);
//		
//		if(session != null) {
//			model.addAttribute("board", hit);
//		}
//		
//		return "detail";
//	}
	
	
}
