package kr.co.gudi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.gudi.service.CrawlService;

@Controller
public class CrawlController {

	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired CrawlService crawlService;
	
	@GetMapping(value="/connect.do")
	public String connect(Model model) throws InterruptedException {
		String source = crawlService.connect("https://www.naver.com");
		model.addAttribute("elem", source);
		
		return "result";
	}
	
	@GetMapping(value="/getElem.do")
	public String getElem(Model model) {
		
		// 클릭기능이있어서 해당 버튼을 클릭하게 해서 페이지 이동할 수 있음
		String source = crawlService.getElem("https://softeer.ai/careers/jobopening");
		model.addAttribute("elem", source);
		
		
		return "result";
	}
	
	@GetMapping(value="/event.do")
	public String event(Model model) {
		crawlService.event("https://gdlms.cafe24.com/");
		
		return "result";
		
	}
		
}
