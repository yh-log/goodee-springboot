package kr.co.gudi.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import kr.co.gudi.service.BoardService;
import kr.co.gudi.vo.BoardVO;

@Controller
public class BoardController {

	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired BoardService board_service;
	
	@RequestMapping(value="/")
	public String home() {
		return "redirect:/list.do";
	}
	
	@RequestMapping(value="/list.do")
	public String list(Model model) {
		
		List<BoardVO> list = board_service.list();
		model.addAttribute("list", list);
		
		return "list";
	}
	
	@GetMapping(value="/detail.do")
	public String detail(int idx, Model model) {
		String page = "redirect:/list.do";
		
		BoardVO info = board_service.detail(idx);
		if(info != null) {
			page = "detail";
			model.addAttribute("info", info);
		}
		return page;
	}
	
	@GetMapping(value="/write.go")
	public String write() {
		return "write";
	}
	
	@PostMapping(value="/write.do")
	public String write(@RequestParam Map<String, String> param, MultipartFile[] files,Model model) {
		
		String page = board_service.write(param,files);
		
		return page;
	}

	
}
