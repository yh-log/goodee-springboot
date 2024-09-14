package kr.co.gudi.controller;

import java.util.List;
import java.util.Map;

import javax.print.attribute.standard.MultipleDocumentHandling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
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
		String page = "";
		
		board_service.detail(idx, model);
		
			page = "detail";
		
		return page;
	}
	@RequestMapping(value="writeForm")
	public String writeForm() {
		return "write";
	}
	
	@RequestMapping(value="write")
	public String write(MultipartFile[] files, @RequestParam Map<String, String> params, Model model) {
		
		logger.info("params : {}", params);
		board_service.write(params, files);
		
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
	
	@RequestMapping(value="/download")
	public ResponseEntity<Resource> download(String new_filename, String ori_filename) {
		logger.info("controller : " + ori_filename + "/" + new_filename);
		
		return board_service.download(new_filename, ori_filename);
	}
	
}
