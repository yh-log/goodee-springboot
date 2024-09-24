package kr.co.gudi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.co.gudi.service.BoardService;

@Controller
public class BoardController {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired BoardService ser;
	
	@RequestMapping("/")
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
	
	@RequestMapping(value="/writeForm")
	public String write() {
		String page = "write";
		return page;
	}
	
	@PostMapping(value="/write.ajax")
	@ResponseBody
	public Map<String, Object> write(MultipartFile[] files,@RequestParam Map<String, String> param){
		boolean success = false;
		int idx = ser.write(param,files);
		String page = "";
		if (idx != 0) {
			success = true;
			page = "./detail.go?idx="+idx;
		}
		Map<String ,Object> result = new HashMap<String, Object>();
		result.put("success", success);
		result.put("link", page);
		return result;
	}
	
	@RequestMapping(value="detail.go")
	public String detail(String idx, Model model, boolean flag) {
		ser.detail(idx,model,true);
		return "detail";
	}
	
	@RequestMapping(value="/del")
	public String del(String idx) {
		ser.del(idx);
		String page = "redirect:/";
		
		return page;
	}
	
	@RequestMapping(value="/download.do")
	public ResponseEntity<Resource> download(String fileName) {
		logger.info("downlasd fildName : "+fileName);
		return ser.download(fileName);
	}
	
	@RequestMapping(value="/update.go")
	public String updatd(String idx, Model model) { // 원래는 이 뒤에 플래그 값을 줘서 업데이트를 할지 말지를 넣어준다
		
		ser.detail(idx,  model, false);
		
		return "updateForm";
	}
	
	@RequestMapping(value="/update.ajax")
	@ResponseBody
	public Map<String, Object> update(MultipartFile[] files ,@RequestParam Map<String, String> param) {
		
		logger.info("param : {}" , param);
		
		String idx = ser.update(files, param);
		String page = "";
		boolean success = false;
		
		if(idx  != null) {
			page = "detail.go?idx="+idx;
			success = true;
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", success);
		result.put("link", page);
		
		
		return result;
	}
	
	
}
