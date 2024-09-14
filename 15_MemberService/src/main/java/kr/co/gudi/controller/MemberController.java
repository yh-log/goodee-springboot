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
	@Autowired MemberService member_service;
	
	@RequestMapping(value="/")
	public String home() {
		return "login";
	}
	
	@RequestMapping(value="/login.do", method = RequestMethod.POST)
	public String login(String id, String pw, Model model, HttpSession session) {
		
		if(member_service.login(id,pw)) {
			session.setAttribute("loginId", id);
		}else {
			model.addAttribute("result", "아이디/비밀번호를 확인하세요");
		}
		return "login";
	}
	
	@RequestMapping(value="join.go")
	public String join() {
		return "join";
	}
	
	@RequestMapping(value="join.do", method = RequestMethod.POST)
	public String join(@RequestParam Map<String, String> params, Model model) {
		String msg = member_service.join(params);
		
		if(msg != null) {
			model.addAttribute("result", msg);
		}
		return "login";
	}
	
	
	
	

}
