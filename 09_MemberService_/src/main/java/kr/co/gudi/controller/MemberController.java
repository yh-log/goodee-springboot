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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.gudi.dto.MemberDTO;
import kr.co.gudi.service.MemberService;

	
@Controller
public class MemberController {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired MemberService service; 
	
	@RequestMapping(value = "/")
	public String loginForm() {
		logger.info("로그인 페이지가 나오나요?");
		return "login";
	}
	
	
	@RequestMapping(value = "/joinForm")
	public String joinForm() {
		logger.info("조인페이지");
		return "join";
	}
	
	@RequestMapping(value = "/join")
	public String join(@RequestParam Map<String, String> param, Model model) {
		
		logger.info("param : {}", param);
		String msg = service.join(param);
		model.addAttribute("result", msg);
		
		return "login";
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public String login(String id, String pw, Model model, HttpSession session) {
		String page = "login";
		logger.info("login 요청 :" + id + "/" +  pw);
		
		if(service.login(id,pw)) {
			page = "redirect:/list";
			session.setAttribute("loginId", id);
		}else {
			model.addAttribute("result", "아이디 또는 비밀번호를 확인하세요");
		}
		
		return page;
	}
	
	@RequestMapping(value="/list")
	public String list(Model model) {
		
		// 여러개의 값을 가져오는거니까 무조건 List로 가져와야 한다.
		List<MemberDTO> list = service.list();
		model.addAttribute("list", list);
		
		return "list";
	}
	
	@RequestMapping(value="/detail")
	public String detail(Model model, String id) {
		logger.info("detail : " + id);
		String page = "redirect:/list";
		MemberDTO dto = service.detail(id);
		
		if(dto != null) {
			page = "detail";
			model.addAttribute("info", dto);
		}
		return page;
	}
	
	@RequestMapping(value="/del")
	public String del(String id) {
		logger.info("del id : " + id);
		service.del(id);
		return "redirect:/list";
	}

}
