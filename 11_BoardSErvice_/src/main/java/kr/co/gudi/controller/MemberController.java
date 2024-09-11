package kr.co.gudi.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.gudi.service.MemberService;

@Controller
public class MemberController {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired 
	MemberService service;
	
	@RequestMapping(value = "/")
	public String loginForm() {
		
		return "login";
	}
	
	@RequestMapping(value = "/joinForm")
	public String joinForm() {
		return "join";
	}
	
	
	@RequestMapping(value = "/join",method = RequestMethod.POST)
	public String join(@RequestParam Map<String,String> param,Model model) {
		String msg ="";
		msg = service.join(param);
		model.addAttribute("result",msg);
		return "login";
	}
	
	@RequestMapping(value = "/login")
	public String login(Model model, String id, String pw, HttpSession session) {
		
		String page = "login";
		
		if(service.login(id,pw)) {
			page = "redirect:/list";
			session.setAttribute("loginId", id);
		}else {
			model.addAttribute("result", "아이디 또는 비밀번호를 확인하세요");
		}
		
		return page;
	}

}
