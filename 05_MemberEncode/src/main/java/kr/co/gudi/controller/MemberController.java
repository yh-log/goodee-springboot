package kr.co.gudi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.gudi.dto.MemberDTO;
import kr.co.gudi.service.MemberService;

@Controller
public class MemberController {

	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired MemberService member_service;
	
	@RequestMapping(value="/")
	public String login() {
		return "login";
	}
	
	@PostMapping(value="/login.do")
	public String login(@RequestParam String id, @RequestParam String pw, Model model) {
		
		String msg = member_service.login(id, pw);
		model.addAttribute("msg", msg);
		
		return "login";
	}
	
	@GetMapping(value="/join.go")
	public String join() {
		return "join";
	}
	
	@PostMapping(value="/join.do")
	public String join(@ModelAttribute MemberDTO memberDto, Model model) {
		
		logger.info("dto => " + memberDto.toString());
		
		if(member_service.join(memberDto) > 0) {
			model.addAttribute("msg", memberDto.getId() + "님 회원가입 완료되었습니다.");
			return "login"; // 성공 -> 로그인 페이지로
		}
		
		return "join"; 
	}
	
}
