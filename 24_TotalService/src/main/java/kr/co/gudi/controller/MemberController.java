package kr.co.gudi.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	
	// 회원가입 이동
	@GetMapping(value="/join.go")
	public String joinView() {
		return "join";
	}
	
	// 회원가입
	@PostMapping(value="/join.do")
	public String join(@RequestParam Map<String, String> param, Model model) {
		
//		boolean success = false;
		
		logger.info("param: {} ", param);
		
		if(member_service.join(param)) {
//			success = true;
			model.addAttribute("msg", "회원가입 성공");
		}
		
//		Map<String, Object> joinResult = new HashMap<String, Object>();
//		joinResult.put("success", success);
		
		return "login";
	}
	
	// 아이디 중복체크
	@GetMapping(value="/overlay")
	@ResponseBody
	public Map<String, Object> overlay(String id){
		
		logger.info(id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("overlay", member_service.overlay(id));
		
		return map;
	}
	
	// 로그인 기능 (관리자와 일반회원 구분 필요 / 세션 저장 필요)
	@PostMapping(value="/login.do")
	public String login(HttpServletRequest req, Model model, HttpSession session) {
		
		String page = "login";
		String msg = "아이디 또는 비밀번호를 확인해주세요";
		
		String id = req.getParameter("id");
		String pw = req.getParameter("pw");
		String ip = req.getRemoteAddr();
		String opt = req.getParameter("option");
		
		//logger.info(id+ " / " +pw+" / " +ip+" / " +opt);
		
		if(member_service.login(id,pw,ip,opt)) {
//			HttpSession session = req.getSession();
			session.setAttribute("loginId", id);
			session.setAttribute("perm", opt);
			
			msg = "로그인에 성공했습니다.";
			page = "boardList";
		}
	
		model.addAttribute("msg", msg);
		
		return page;
	}
	
	@RequestMapping(value="/member.go")
	public String memberList() {
		return "memberList";
	}
	
	@GetMapping(value="/memberList.do")
	@ResponseBody
	public Map<String, Object> memberList(String page, String cnt){
		
		int page_ = Integer.parseInt(page);
		int cnt_ = Integer.parseInt(cnt);
		
		return member_service.memberList(page_, cnt_);
		
	}
	
	@RequestMapping(value="/detail/{id}")
	public String detail(@PathVariable("id") String id, Model model) {
		
		model.addAttribute("info", member_service.detail(id));
		
		return "memberDetail";
	}
	
//	@PostMapping(value="/update")
//	public 
	
}
