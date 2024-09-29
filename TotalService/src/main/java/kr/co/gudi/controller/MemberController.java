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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
		return "login";
	}
	
	@PostMapping(value="/login")
	public String login(@ModelAttribute MemberDTO memberDto, HttpServletRequest req, Model model, HttpSession session) {
		
		Map<String, Object> resultMap = new HashMap<>();
		
		logger.info("DTO : {}", memberDto.toString());
		
		String ip = req.getRemoteAddr();
		String opt = req.getParameter("option");
		
		String page = "login";
		
		if((boolean) member_service.login(memberDto ,ip,opt).get("isSuccess")) {
			session.setAttribute("loginId", memberDto.getId());
			session.setAttribute("opt", opt);
			page = "boardListView";
			model.addAttribute("msg", "로그인에 성공했습니다.");
		}else {
			model.addAttribute("msg", "아이디/비밀번호를 확인해주세요");
		}
		
		return "redirect:/" + page;
	}
	
	@GetMapping(value="/join")
	public String joinView() {
		return "join";
	}
	
	@GetMapping(value="/overlay")
	@ResponseBody
	public Map<String, Object> overlay(String id){
		Map<String, Object> resultMap = new HashMap<>();
		
		resultMap.put("overlay", member_service.overlay(id));
		
		return resultMap;
	}
	
	@PostMapping(value="/join")
	public String join(MemberDTO memberDto, Model model) {
		
		// Map 으로 넘겨서 return 값이 true 이면 메세지를 alert로 출력..? -> 이따가 해보자!
		
		if(member_service.join(memberDto)) {
			model.addAttribute("msg", "회원가입에 성공했습니다.");
		}else {
			model.addAttribute("msg", "회원가입에 실패했습니다.");
		}
		
		return "login";
	}
	
	@GetMapping(value="/memberListView")
	public String memberListView(HttpSession session, Model model) {
		
		String page = "";
		
		if(session.getAttribute("loginId") != null && session.getAttribute("opt").equals("admin")) {
			page = "memberList";
		}else {
			page = "login";
			model.addAttribute("msg", "접근 불가능한 페이지 입니다.");
		}
		return page;
	}
	
	@GetMapping(value="/memberList")
	@ResponseBody
	public Map<String, Object> memberList(int page, int cnt){
		
		logger.info("페이지 : "+page+ cnt);
		
		return member_service.memberList(page,cnt);
	}
	
	@RequestMapping(value="/memberDetail.go")
	public String memberDetail(String id, Model model, HttpSession session) {
		
		if(!session.getAttribute("opt").equals("admin")) {
			return "redirect:/";
		}
		
		model.addAttribute("info", member_service.memberDetail(id));
		
		return "memberDetail";
	}
	
	@RequestMapping(value="/memberUpdate.go")
	public String memberUpdate(String id, Model model, HttpSession session) {
		
		if(!session.getAttribute("opt").equals("admin")) {
			return "redirect:/";
		}
		
		model.addAttribute("info", member_service.memberDetail(id));
		
		return "memberUpdate";
	}
	
	@PostMapping(value="/memberUpdate.do")
	public String update(@RequestParam Map<String, String> param, HttpSession session) {
		logger.info("params : {}", param);
		
		if(!session.getAttribute("opt").equals("admin")) {
			return "redirect:/";
		}
		
		
		member_service.update(param);
		return "redirect:/memberDetail.go?id=" + param.get("id");
		
	}
	
	@RequestMapping(value="delete.go")
	public String delete(String id, HttpSession session) {
		
		if(!session.getAttribute("opt").equals("admin")) {
			return "redirect:/";
		}
		
		logger.info("id: " + id);
		
		member_service.delete(id);
		
		return "memberList";
	}
	
	
}
