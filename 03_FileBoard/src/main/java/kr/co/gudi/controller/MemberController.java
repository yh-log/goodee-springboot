package kr.co.gudi.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import kr.co.gudi.dto.MemberDTO;
import kr.co.gudi.service.MemberService;

@RestController
public class MemberController {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired MemberService member_service;
	
	@RequestMapping(value="/")
	public ModelAndView mainView() {
		return new ModelAndView("login");
	}
	
	@PostMapping(value="/login.do")
	public Map<String, Object> login(@RequestParam Map<String, String> param) {
		
		logger.info("들어온 값 => {}" + param);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("success", member_service.login(param));
		
		return resultMap; 
	}
	
	@GetMapping(value="/join.go")
	public ModelAndView join() {
		return new ModelAndView("join");
	}
	
	@PostMapping(value="/join.do")
	public Map<String, Object> join(@ModelAttribute MemberDTO memberDto){
		logger.info("회원가입 데이터 => " + memberDto.toString());
		Map<String, Object> resultMap = new HashMap<>();
		if(member_service.join(memberDto)>0) {
			resultMap.put("success", true);
			resultMap.put("id", memberDto.getName());
		}
		return resultMap;
	}
	
	/**
	 * 로그아웃
	 */
	@RequestMapping(value="/logout.do")
	public ModelAndView logout(HttpSession session) {
		
		session.invalidate();
		
		return new ModelAndView("redirect:/");
	}
	
	

}
