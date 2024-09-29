package kr.co.gudi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.gudi.dto.Maindto;
import kr.co.gudi.service.Mainservice;

@Controller
public class Maincontroller {
	
	
	 Logger log = LoggerFactory.getLogger(Maincontroller.class);

	@Autowired Mainservice main_ser;
	
	@RequestMapping(value="/")
	public String main() {
		return "main";
	}
	@RequestMapping(value="/u_list")
	public String u_list() {
	
		return "u_list";
	}
	
	
	@RequestMapping(value="/join.go")
	public String join() {
		
		return "join";
		
	}
	
	@RequestMapping(value="/join.do")
	public String joindo(@RequestParam Map<String, String> params) {
			
		main_ser.join(params);
		
		return "redirect:/";
	}
	
	@GetMapping(value="overlay.ajax")
	@ResponseBody
	public Map<String, Object> check(String id){
		
		Map<String,Object> map = new HashMap<String, Object>();
		
		int overlay = main_ser.check(id);
		
		map.put("overlay", overlay);
		
		
		return map;
	}
	
	@PostMapping(value="/login.do")
	public String login(@RequestParam Map<String,String> param,HttpServletRequest req, Model model,HttpSession session) {
		
		String page ="main";
		String msg="아이디 또는 비밀번호 확인";
		// 한글깨짐 확인하기
		String ip=req.getRemoteAddr();
		param.put("ip", ip);
		
		
		if(main_ser.login(param).equals("admin")) {
			page = "u_list";
			msg ="관리자로그인";
			session.setAttribute("loginid", "admin");
		}
		else if(main_ser.login(param).equals("user")){
			page ="redirect:/list";
			msg ="유저로그인";
			session.setAttribute("loginid", param.get("id"));
		}
			
		
		model.addAttribute("msg", msg);
		
		return page;
		
		
	}
	
	@GetMapping(value="/list.ajax")
	@ResponseBody
	public Map<String,Object> admin_list(String page, String cnt){
		
		int page_ = Integer.parseInt(page);
		int cnt_ = Integer.parseInt(cnt);
		int limit = cnt_;
		int offset = cnt_*(page_-1);
		int totalPages = main_ser.allCount(cnt_);
		
		Map<String,Object> result = new HashMap<String, Object>();
		List<Maindto> list =  main_ser.u_list(limit, offset);
		
		result.put("totalPages", totalPages);
		result.put("currpage", page);
		result.put("list", list);

		
		return result;
	}
	
	@RequestMapping(value="/u_detail")
	public String u_detail(String id,Model model) {
		
		Maindto m_dto =	main_ser.u_detail(id);
		
		model.addAttribute("info", m_dto);
		
		return "u_detail";
	}
	
	@RequestMapping(value="/u_del")
	public String u_del(String id) {
		
		int row = main_ser.u_del(id);
		
		return "redirect:/u_list";
	}
	
	@RequestMapping(value="/user_update.go")
	public String update(String id, Model model) {
		
		Maindto m_dto =	main_ser.u_detail(id);
		if(m_dto != null) {
			model.addAttribute("info", m_dto);
			return "u_update";
		}
		return "u_list";
	}

	@PostMapping(value="/user_update.do")
	public String update(@RequestParam Map<String, String> param) {
		
		log.info("param : {}", param);
		
		main_ser.update(param);
		
		return "redirect:/u_detail?id="+param.get("id");
	}
	
	
}
