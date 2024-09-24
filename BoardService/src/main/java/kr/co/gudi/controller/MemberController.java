package kr.co.gudi.controller;

import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.gudi.dto.MemberDTO;
import kr.co.gudi.service.MemberService;

@Controller
public class MemberController {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired MemberService member_service;
	
	@RequestMapping(value="/")
	public String home() {
		return "login";
	}
	
	@RequestMapping(value="joinForm")
	public String joinForm() {
		return "join";
	}
	
	@RequestMapping(value="join", method = RequestMethod.POST)
	public String join(@RequestParam Map<String, String> params, Model model) {
		
		logger.info("회원 정보 : {}" , params);
		
		String msg = member_service.join(params);
		model.addAttribute("result", msg);
		
		return "login";
	}
	
	
	@RequestMapping(value="login", method = RequestMethod.POST)
	public String login(String id, String pw, Model model, HttpSession session) {
		String page = "";
		
		if(member_service.login(id,pw)) {
			session.setAttribute("loginId", id);
			if(session.getAttribute("loginId").equals("admin")) {
				page = "redirect:/member_list";
			}else {
				page = "redirect:/board_list";
			}
		}else {
			page = "login";
			model.addAttribute("result", "아이디, 비밀번호를 다시 확인하세요");
		}
		
		return page;
	}
	
	@RequestMapping(value="member_list")
	public String member_list(Model model, HttpSession session) {
		String page = "";
		
		if(session.getAttribute("loginId") != null) {
			List<MemberDTO> list = member_service.member_list();
			model.addAttribute("list", list);
			page = "memberlist";
		}else {
			model.addAttribute("result", "로그인이 필요한 서비스입니다.");
			page = "login";
		}
		
		return page;
	}
	
	@RequestMapping(value="member_del")
	public String member_del(String id, Model model, HttpSession session) {
		String page = "";
		logger.info("삭제 요청 id : " + id);
		
		if(session.getAttribute("loginId") != null) {
			member_service.member_del(id);
			page = "redirect:/member_list";
		}else {
			model.addAttribute("result", "로그인이 필요한 서비스입니다.");
			page = "login";
		}
		return page;
	}
	
	@RequestMapping(value="overlay")
	@ResponseBody
	public Map<String, Object> overlay(String id) {
		logger.info("overlay check : " + id);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("overlay", member_service.overlay(id));
		
		return map;
	}
}
