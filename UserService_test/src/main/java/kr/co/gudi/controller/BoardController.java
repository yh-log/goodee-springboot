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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import kr.co.gudi.dto.BoardDTO;
import kr.co.gudi.service.BoardService;

@Controller
public class BoardController {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired BoardService board_service;
	
	@RequestMapping(value="/boardlist")
	public String boardlist(Model model, HttpSession session) {
		String page = "";
		
		if(session.getAttribute("loginId") == null) {
			page = "login";
			model.addAttribute("result", "로그인이 필요한 서비스입니다.");
		}else {
			List<BoardDTO> list = board_service.boardlist();
			model.addAttribute("boardlist", list);
			page = "boardlist";
		}
		return page;
	}
	
	@RequestMapping(value="detail")
	public String detail(int idx, HttpSession session, Model model) {
		String page = "";
		
		if(session.getAttribute("loginId") == null) {
			page = "login";
			model.addAttribute("result", "로그인이 필요한 서비스입니다.");
		}else {
			board_service.detail(idx, model);
			page = "detail";
		}
		return page;
	}
	
	@RequestMapping(value="/writeForm")
	public String writeForm() {
		return "write";
	}
	
	@RequestMapping(value="/upload")
	public String upload(MultipartFile[] files ,@RequestParam Map<String, String> params, Model model, HttpSession session) {
		String page = "";
		logger.info("params {} : ", params);
		
		if(session.getAttribute("loginId")==null) {
			page="login";
			model.addAttribute("result", "로그인이 필요한 서비스입니다.");
		}else {
			page = board_service.upload(params, files);
		}
		return page;
	}
	
	@RequestMapping(value="boarddel")
	public String boarddel(int idx) {
		board_service.boarddel(idx);
		return "redirect:/boardlist";
	}
	
	@RequestMapping(value="/updateForm")
	public String updateFomr(HttpSession session, int idx, Model model) {
		String page = "";
		
		logger.info("idx :{} ", idx);
		
		if(session.getAttribute("loginId")==null) {
			page="login";
			model.addAttribute("result", "로그인이 필요한 서비스입니다.");
		}else {
			BoardDTO dto = board_service.updateForm(idx);
			model.addAttribute("detail", dto);
			page = "update";
		}
		return page;
	}
	
	@RequestMapping(value="/update")
	public String update(@RequestParam Map<String, String> param, HttpSession session, Model model) {
		
		String page = "";
		if(session.getAttribute("loginId")==null) {
			page="login";
			model.addAttribute("result", "로그인이 필요한 서비스입니다.");
		}else {
			board_service.update(param);
			page = "redirect:/boardlist";
		}
		return page;
	}
	
}
