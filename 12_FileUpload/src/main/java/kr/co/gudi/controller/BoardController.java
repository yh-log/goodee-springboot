package kr.co.gudi.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.gudi.dto.BoardDTO;
import kr.co.gudi.service.BoardService;

@Controller
public class BoardController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired BoardService board_service;
	
	@RequestMapping(value="/")
	public String home() {
		return "index";
	}
	
	@RequestMapping(value="/list")
	public String list(Model model) {
		
		String page = "index";
		
		List<BoardDTO> list = board_service.list();
		
		if(list != null) {
			page = "list";
			model.addAttribute("list", list);
		}
		
		return page;
	}
	
	@RequestMapping(value="/detail")
	public String detail(int idx, Model model) {
		
		logger.info("idx : " + idx);
		String page = "redirect:/list";
		
		BoardDTO dto = null;
		dto = board_service.detail(idx);
		
		if(dto != null) {
			page = "detail";
			model.addAttribute("info", dto);
		}
		
		return page;
	}
	@RequestMapping(value="writeForm")
	public String writeForm() {
		return "write";
	}
	
	@RequestMapping(value="write")
	public String write(@RequestParam Map<String, String> params, Model model) {
		
		logger.info("params : {}", params);
		
		String msg = board_service.write(params);
		model.addAttribute("msg", msg);
		
		return "redirect:/list";
	}
	
	@RequestMapping(value="del")
	public String del(int idx, Model model) {
		board_service.del(idx);
		return "redirect:/list";
	}
	
	@RequestMapping(value="/updateForm")
	public String updateForm(Model model, int idx) {
		BoardDTO dto = board_service.updateForm(idx);
		model.addAttribute("info", dto);
		return "update";
	}
	
	@RequestMapping(value="/update")
	public String update(@RequestParam Map<String, String> params) {
		logger.info("params : {}", params);
		board_service.update(params);
		return "redirect:/detail?idx=" + params.get("idx");
	}
	
}
