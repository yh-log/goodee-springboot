package kr.co.gudi.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.gudi.dto.MemberDTO;
import kr.co.gudi.service.MemberService;

@Controller
public class MemberController {

	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired MemberService member_service;
	
	@RequestMapping(value="/")
	public String loginForm() {
		return "login";
	}
	
	@RequestMapping(value="/joinForm")
	public String joinForm() {
		return "join";
	}
	
	@RequestMapping(value="/join")
	public String join(@RequestParam Map<String, String> params, Model model) {
		logger.info("params : {}", params);
		
		String msg = member_service.join(params);
		model.addAttribute("result", msg);
		
		return "login";
	}
	
	@RequestMapping(value="/login")
	public String login(String id, String pw, Model model, HttpSession session) {
		String page = "login";
		logger.info("id / pw : " + id + " / " + pw);
		
		if(member_service.login(id,pw)) {
			session.setAttribute("loginId", id);
			if(session.getAttribute("loginId").equals("admin")) {
				page = "redirect:/list";
			}else {
				page = "redirect:/boardlist";
			}
		}else {
			model.addAttribute("result", "아이디/비밀번호를 확인해주세요");
		}
		return page;
	}
	
	@RequestMapping(value="/list")
	public String memberlist(Model model, HttpSession session) {
		String page = "login";
		
		List<MemberDTO> list = member_service.memberlist();
		if(session.getAttribute("loginId").equals("admin")) {
			model.addAttribute("list", list);
			page = "memberlist";
		}
		
		return page;
	}
	
	@RequestMapping(value="memberdel")
	public String memberdel(String id, Model model) {
		logger.info(id);
		String msg = member_service.memberdel(id);
		
		return "redirect:/memberlist";
	}
	
}
