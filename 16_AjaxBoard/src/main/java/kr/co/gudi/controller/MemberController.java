package kr.co.gudi.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.gudi.service.MemberService;

@Controller
public class MemberController {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired MemberService member_service;
	
	@RequestMapping(value="/")
	public String home() {
		return "login";
	}
	
	@RequestMapping(value="/join.go")
	public String join() {
		return "join";
	}
	
	@RequestMapping(value="/login.do", method = RequestMethod.POST)
	public String login(String id, String pw, Model model, HttpSession session) {
		String page = "login";
		
		int row = member_service.login(id, pw);
		
		if(row > 0){
			session.setAttribute("loginId", id);
			model.addAttribute("result", "로그인이 완료되었습니다.");
		}else {
			model.addAttribute("result", "아이디/비밀번호를 확인해주세요");
		}
		
		return page;
	}
	
	
	@RequestMapping(value="/join.do", method = RequestMethod.POST)
	public String join(@RequestParam Map<String, String> params, Model model) {
		String page = "login";
		logger.info("params : {}", params);
	
		
		if(member_service.join(params)) {
			model.addAttribute("result", "회원가입이 완료되었습니다.");
		}else {
			model.addAttribute("result", "회원가입에 실패했습니다.");
		}
		return page;
	}
	
	@GetMapping(value="/overlay.do")
	@ResponseBody
	public Map<String, Object> overlay(String id){
		
		logger.info("overlay : " + id);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("overlay", member_service.overlay(id));
		return map;
	}
	
}
