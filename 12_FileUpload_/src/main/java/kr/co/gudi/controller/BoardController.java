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
import org.springframework.web.multipart.MultipartFile;

import kr.co.gudi.dto.BoardDTO;
import kr.co.gudi.service.BoardService;

@Controller
public class BoardController {
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired BoardService boardService;
	
	@RequestMapping(value="/")
	public String listform(Model model) {
		List<BoardDTO> list = boardService.list();
		logger.info("list: "+ list);
		model.addAttribute("list",list);
		return "list";
	}
	@RequestMapping(value="/list")
	public String list(Model model) {
		List<BoardDTO> list = boardService.list();
		logger.info("list: "+ list);
		model.addAttribute("list",list);
		return "list";
	}
	@RequestMapping(value="/writeForm")
	public String writeForm() {
		return "write";
	}
	@RequestMapping(value="/write") // 보내기로한 input에 file name과 맞춰줘야 함 (멀티풀로 받기로 했으면 배열로 받아줘야 한다.)
	public String write(MultipartFile[] files, Model model , @RequestParam Map<String,String> param) {

		logger.info("file coutn : " + files.length);
		
		boardService.write(param, files);
		
		return "redirect:/list";
	}
	
	@RequestMapping(value="/detail")
	public String detail(String idx ,Model model) {
		boardService.detail(idx, model);
		return "detail";
	}
	
}
