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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.gudi.service.MemberService;

@Controller
public class MemberController {

	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired MemberService member_service;
	
	@RequestMapping(value = {"/", "/login.go"})
	public String login(Model model) {
		return "login";
	}
	
	@RequestMapping(value = "/login.do")
	public String login(Model model, HttpSession session, String id, String pw) {
		String page = "redirect:/login.go";
		
		// login의 반환값을 Boolean으로 받아서, true라면.. 로그인성공!
		if(member_service.login(id, pw)) {
//			page = "redirect:/login.go";
			model.addAttribute("msg", "로그인 되었습니다!");
			session.setAttribute("loginId", id);
		}else {
			model.addAttribute("msg", "아이디 또는 비밀번호를 확인해주세요.");
		}
		
		return page;
	}
	
	
	
	
	@RequestMapping(value = "/join.go")
	public String join() {
		return "join";
	}
	
	@RequestMapping(value = "/join.do")
	public String join(HttpSession session, Model model, 
			@RequestParam Map<String, String> params) {
		
		String page = "join";
		if(member_service.join(params)) {
			page = "redirect:/login.go";
		}else {
			model.addAttribute("result", "회원가입에 실패했습니다.");
		}
		
		return page;
	}
	
	@GetMapping(value="/overlay.do")
	@ResponseBody
	public Map<String, Object> overlay(String id){
		
		logger.info("overlay check : " + id);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("overlay", member_service.overlay(id));
		return map;
	}
	
	
}
