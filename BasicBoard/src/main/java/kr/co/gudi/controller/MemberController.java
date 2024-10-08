package kr.co.gudi.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.gudi.dto.MemberDTO;
import kr.co.gudi.service.MemberService;

@Controller
public class MemberController {

	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired MemberService member_service;
	
	@RequestMapping(value="/")
	public String home() {
		return "boardList";
	}

	@GetMapping(value="/loginView")
	public String loginView() {
		return "login";
	}
	
	@PostMapping(value="/login")
	@ResponseBody
	public Map<String, Object> login(@ModelAttribute MemberDTO memberDto, HttpSession session){
		
		logger.info("login info : " + memberDto.toString());
		
		Map<String, Object> resultMap = new HashMap<>();
		
		boolean success = false;
		String nextPage = "login";
		
		if(member_service.login(memberDto)) {
			success = true;
			nextPage = "/";
			session.setAttribute("loginId", memberDto.getId());
		}
		resultMap.put("success", success);
		resultMap.put("nextPage", nextPage);
		
		return resultMap;
	}
	
	@GetMapping(value="/logout")
	public String logout(HttpSession session) {
		
		session.removeAttribute("loginId");
		
		return "boardList";
	}
	
	
}
