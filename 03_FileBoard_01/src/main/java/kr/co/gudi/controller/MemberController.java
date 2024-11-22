package kr.co.gudi.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import kr.co.gudi.service.MemberService;

@RestController
public class MemberController {
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired MemberService member_service;
	
	// 로그인
	@RequestMapping (value = {"/", "/member_login.go"})
	public ModelAndView login() {
		return new ModelAndView("member_login");
	}
	
	@RequestMapping (value = "/member_login.ajax")
	public Map<String, Object> login(String id, String pw, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (member_service.login(id, pw)) {
			session.setAttribute("loginId", id);
			result.put("login", true);
		} else {
			result.put("login", false);
		}
		return result;
	}
	
	// 로그아웃
	@RequestMapping (value = "/member_logout.do")
	public ModelAndView logout(Model model, HttpSession session) {
		session.removeAttribute("loginId");
		model.addAttribute("msg", "로그아웃 되었습니다.");
		return new ModelAndView("member_login");
	}
	
	// 회원 가입
	@GetMapping (value = "/member_join.go")
	public ModelAndView join() {
		return new ModelAndView("member_join");
	}
	
	@RequestMapping (value = "/checkId.ajax")
	public Map<String, Object> checkId(String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("checkId", member_service.checkId(id));
		return result;
	}
	
	@RequestMapping (value = "/member_join.do")
	public ModelAndView join(@RequestParam Map<String, String> params, Model model) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("member_join");
		if (member_service.join(params)) {
			model.addAttribute("msg", params.get("id")+"님, 환영합니다. 로그인하세요.");
			model.addAttribute("id", params.get("id"));
			mav.setViewName("member_login");
		} else {
			model.addAttribute("msg", "회원 가입 과정에 문제가 발생했습니다. 다시 시도하세요.");
		}
		return mav;
	}

}