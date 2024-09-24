package kr.co.gudi.controller;

import java.util.HashMap;
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

import kr.co.gudi.service.BoardService;

@Controller
public class BoardController {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired BoardService ser;
	
	@RequestMapping({"/","/list.do"})
	public String listgo() {
		return "list";
	}
	
	@RequestMapping("/list.ajax")
	@ResponseBody
	public Map<String, Object> list(String page,String cnt){
		
		int page_ = Integer.parseInt(page);
		int cnt_ = Integer.parseInt(cnt);
	
		return ser.list(page_, cnt_);
	}
	
	@RequestMapping(value="/detail.go")
	public String detail(String idx, Model model){
		
		ser.detail(idx, model);
		logger.info("detail" + idx);
		
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
		
		int idx = ser.write(param, files);
		
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
	
//	@PostMapping(value="/write.do")
//	public String write(MultipartFile[] files, @RequestParam Map<String, String> param) {
//		
//		return ser.write(param, files);
//	}
//	
	
	@RequestMapping(value="/del")
	public String del(Model model, String idx) {
		
		logger.info("delete" + idx);
		ser.del(idx);
		
		return "list";
	}
	
	
	
	
	
	
	
}