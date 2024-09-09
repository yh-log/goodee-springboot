package ko.co.gudi.controller;

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

import ko.co.gudi.dto.MemberDTO;
import ko.co.gudi.service.MemberService;

@Controller
public class MemberController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired MemberService service;
	
	@RequestMapping(value="/")
	public String loginForm() {
		return "login";
	}
	
	@RequestMapping(value="/joinForm")
	public String joinForm() {
		return "join";
	}
	
	@RequestMapping(value="/join", method = RequestMethod.POST)
	public String join(@RequestParam Map<String, String> param, Model model) {
		logger.info("controller param : {}", param);
		
		String msg = service.join(param);
		model.addAttribute("result", msg);
		
		return "login";
	}
	
	@RequestMapping(value="/login")
	public String login(String id, String pw, Model model, HttpSession session) {
		logger.info("login :" + id + "/" + pw);
		String page = "login";

		
		if(service.login(id,pw)) {
			page = "redirect:/list";
			session.setAttribute("loginId", id);
		}
		return page;
	}
	
	@RequestMapping(value="/list")
	public String list(Model model) {
		
		List<MemberDTO> list = service.list();
		model.addAttribute("list", list);
		
		return "list";
	}
	
	@RequestMapping(value="/detail")
	public String detail(String id, Model model) {
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
		service.del(id);
		return "redirect:/list";
	}
	
	@RequestMapping(value="/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("loginId");
		return "login";
		
	}
	
	
}
