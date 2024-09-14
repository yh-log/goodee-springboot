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
import org.springframework.web.multipart.MultipartFile;

import kr.co.gudi.service.BoardSedrvice;
import kr.co.gudi.vo.BoardVO;

@Controller
public class BoardController {
	
	@Autowired BoardSedrvice board_service;
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping(value="/list.do")
	public String list(HttpSession session, Model model) {
		String page = "login";
		
		if(session.getAttribute("loginId")!= null) {
			page = "list";
			List<BoardVO> list = board_service.list();
			model.addAttribute("list", list);
		}else {
			model.addAttribute("result", "로그인이 필요한 서비스 입니다.");
		}
				
		return page;
	}
	
	@RequestMapping(value="/write.go")
	public String write(HttpSession session, Model model) {
		String page = "login";		
		
		if(session.getAttribute("loginId")!= null) {
			page = "write";
		}else {
			model.addAttribute("result", "로그인이 필요한 서비스 입니다.");
		}				
		return page;
	}
	
	@RequestMapping(value="/write.do", method = RequestMethod.POST)
	public String write(MultipartFile[] files, @RequestParam Map<String, String> params, Model model, HttpSession session) {
		String page = "login";
		logger.info("params : {} ", params);
		logger.info("files : {}"+ files);
		
		if(session.getAttribute("loginId") == null) {
			model.addAttribute("result", "로그인이 필요한 페이지입니다.");
		}else {
			board_service.write(params, files);
			page = "redirect:/list";
		}
		
		return page;
	}
	
	@RequestMapping(value="/detail.do")
	public String detail() {
		
		return "";
	}
	
	@RequestMapping(value="/del.do")
	public String del() {
	
		return "";
	}
	
//	@RequestMapping(value="/download.do")
	
	

}








