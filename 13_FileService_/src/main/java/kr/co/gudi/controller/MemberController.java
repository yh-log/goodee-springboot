package kr.co.gudi.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.gudi.service.MemberService;

@Controller
public class MemberController {
	
	@Autowired MemberService member_service;
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	// url 규칙은 보통 팀별, 회사별로 정한다.
	// .go, .move 등은 페이지 이동을 의미
	// .do, .action 등은 실행을 의미
	@RequestMapping(value={"/","/login.go"})
	public String login() {
		return "login";
	}
	
	@RequestMapping(value="/login.do", method = RequestMethod.POST)
	public String login(String id, String pw, 
			Model model, HttpSession session) {		
		String page = "login";		
		if(member_service.login(id,pw)) {
			session.setAttribute("loginId", id);
			page = "redirect:/list.do";
		}else {
			model.addAttribute("result", "아이디 또는 비밀번호를 확인해 주세요");
		}				
		return page;
	}

}



















