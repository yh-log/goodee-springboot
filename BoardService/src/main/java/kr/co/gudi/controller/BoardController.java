package kr.co.gudi.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import kr.co.gudi.dto.BoardDTO;
import kr.co.gudi.dto.MemberDTO;
import kr.co.gudi.service.BoardService;

@Controller
public class BoardController {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired BoardService board_service;
	
	@RequestMapping(value="/board_list")
	public String board_list(Model model, HttpSession session) {
		String page = "";
		
		if(session.getAttribute("loginId") != null) {
			List<BoardDTO> list = board_service.board_list();
			model.addAttribute("boardlist", list);
			page = "boardlist";
		}else {
			model.addAttribute("result", "로그인이 필요한 서비스입니다.");
			page = "login";
		}
		return page;
	}

	@RequestMapping(value="/writeForm")
	public String writeForm(Model model, HttpSession session) {
		String page = "";
		
		if(session.getAttribute("loginId") != null) {
			page = "write";
		}else {
			model.addAttribute("result", "로그인이 필요한 서비스입니다.");
			page = "login";
		}
		return page;
	}
	
	@RequestMapping(value="/write", method = RequestMethod.POST)
	public String write(MultipartFile[] files, @RequestParam Map<String, String> params, Model model, HttpSession session) {
		String page = "";
		
		if(session.getAttribute("loginId") != null) {
			page = board_service.write(files, params);
		}else {
			model.addAttribute("result", "로그인이 필요한 서비스입니다.");
			page = "login";
		}
		
		return page;
		
	}
	
	@RequestMapping(value="/detail")
	public String detail(int idx, Model model, HttpSession session) {
		String page = "";
		
		if(session.getAttribute("loginId") != null) {
			board_service.detail(idx, model);
			page = "detail";
		}else {
			model.addAttribute("result", "로그인이 필요한 서비스입니다.");
			page = "login";
		}
		
		return page;
	}
	
	@RequestMapping(value="/board_del")
	public String board_del(int idx, Model model, HttpSession session) {
		String page = "";
		
		if(session.getAttribute("loginId") != null) {
			board_service.board_del(idx);
			page = "redirect:/board_list";
		}else {
			model.addAttribute("result", "로그인이 필요한 서비스입니다.");
			page = "login";
		}
		
		return page;
	}
	
	@RequestMapping(value="/download")
	public ResponseEntity<Resource> download(String new_filename, String ori_filename){
		
		return board_service.download(new_filename, ori_filename);
	}
	

}
