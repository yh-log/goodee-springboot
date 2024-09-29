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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.co.gudi.dto.Bbsdto;
import kr.co.gudi.dto.Filedto;
import kr.co.gudi.dto.Maindto;
import kr.co.gudi.service.Boardservice;

@Controller
public class Boardcontroller {
	@Autowired Boardservice board_ser;
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping(value="/list")
	public String list(Model model) {
	//	Map<String,Object> result = new HashMap<String, Object>();
	//	result.put("list", list);
		List<Bbsdto> list = board_ser.list();
		model.addAttribute("list", list);
		
		
		return "list";
		
	}
	

	@RequestMapping(value="/write.go")
	public String writego() {
		
		return "write";
	}
	
	@RequestMapping(value="/write.do")
	public String write(MultipartFile[] files,@RequestParam Map<String,String> param) {
		
		board_ser.write(files,param);
		
		
		
		return "redirect:/list";
	}
	
	@RequestMapping(value="/b_del")
	public String write(String idx) {
		
			board_ser.b_del(idx);
	
		return "redirect:/list";
	}
	
	@RequestMapping(value="/b_detail")
	@Transactional
	public String b_detail(String idx, Model model) {
		List<Filedto> files = board_ser.files(idx);
		Bbsdto b_dto = board_ser.b_detail(idx);
		model.addAttribute("files", files);
		model.addAttribute("info", b_dto);
		
		return "b_detail";
	}
	
	@RequestMapping(value="/download.do")
	public ResponseEntity<Resource> download(String filename){
		
		return board_ser.download(filename);
		
	}
	@RequestMapping(value="/update.go")
//	@Transactional -> select 에는 취소라는 개념이 없어서 트렌젝션도 없다.
	public String update(String idx, Model model) {
		List<Filedto> files = board_ser.files(idx);
		Bbsdto b_dto = board_ser.b_detail(idx);
		model.addAttribute("files", files);
		model.addAttribute("info", b_dto);
		
		return "update";
	}
	
	@PostMapping(value="/update.do")
	public String update(MultipartFile[] files, @RequestParam Map<String, String> params) {
		
		logger.info("params : {}", params);
		logger.info("files : {} "+files[0].isEmpty());
		
		board_ser.update(params, files);
		
		return "redirect:/b_detail?idx="+params.get("idx");
	}
	
	@RequestMapping(value="/photo/{fileName}")
	public ResponseEntity<Resource> photo(@PathVariable String fileName) {
		logger.info("fileName : " + fileName);
		return board_ser.getImg(fileName);
	}
	
	
	
	
}
