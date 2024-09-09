package ko.co.gudi.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ko.co.gudi.service.MemberService;

@Controller
public class MemberController {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired MemberService service;
	
	@RequestMapping(value="/")
	public String loginForm() {
		return "login";
	}
	
	@RequestMapping(value="joinForm")
	public String joinForm() {
		return "join";
	}
	
	@RequestMapping(value="join", method = RequestMethod.POST)
	public String join(@RequestParam Map<String, String> param, Model model) {
		
		logger.info("param : {}", param);
		
		String msg = service.join(param);
		
		model.addAttribute("result", msg);
		
		return "login";
	}
	
//	@RequestMapping(value="login")
//	public String login() {
//		String page = "redirect:/list";
//		
//		return "page";
//	}
	
}
