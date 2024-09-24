package kr.co.gudi.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.co.gudi.dto.BoardDTO;
import kr.co.gudi.service.BoardService;

@Controller
public class BoardController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired BoardService bd_service;
	
	@RequestMapping(value= {"/","/list.do"})
	public String list(Model model) {
		logger.info("list 컨트롤러");
		List<BoardDTO> list = bd_service.list();
		model.addAttribute("list", list);
		return "list";
	}
	
	@RequestMapping(value="/detail.go")
	public String detail(String idx, Model model) {
		bd_service.detail(idx, model);
		logger.info("detail 컨트롤러");
		return "detail";
	}
	
	@RequestMapping(value="/writeForm")
	public String wrtieForm() {
		return "write";
	}
	
	@PostMapping(value="/write.ajax")
	@ResponseBody
	public Map<String, Object> write(MultipartFile[] files ,@RequestParam Map<String, String> param) {
		
		logger.info("upload file : "+files.length);
		
		boolean success = false;
		
		int idx = bd_service.write(param, files);
		
		String page = "";
		
		if(idx != 0) {
			success = true;
			page = "./detail.go?idx="+idx;
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", success);
		result.put("link", page);
		
		return result;
	}
	
}
