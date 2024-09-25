package kr.co.gudi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	
	@RequestMapping(value= {"/","/list.do"})
	public String home(Model model, @RequestParam Map<String, String> param) {

		// 받겠다고 했는데 안보내주는 경우 문제가 발생할 수 있음 
		logger.info("param : {}", param);
		
		
		List<MemberDTO> list = member_service.list(param);
		model.addAttribute("list", list);
		return "list";
	}
	
	@RequestMapping(value="/join.go")
	public String join() {
		return "join";
	}
	
	// 파라미터를 DTO 로 받고 싶다면..
	// 1. post 로 받아야 한다.
	// 2. 보내오는 데이터 타입과, 받는 DTO 타입이 일치해야 한다. (만약 age가 int로 되어있으면 뷰에서는 String으로 보내기 때문에(type="text"로 되어있음) 에러가 발생한다)
	@PostMapping(value="/join.do")
	public String join(MemberDTO dto, Model model) {
		// model을 인터페이스라 값을 서비스에서 보낼거면 model 차제를 넘겨줘야 함 (인터페이스라 객체화할 수 없음)
		return member_service.join(dto, model);
	}
	
	@PostMapping(value="/multi.do") //HttpServletRequest req 도 있음!
	public String multi(Model model, 
			// String[] userName
			@RequestParam List<String> userName) {
		
		// 컬렉션프레임워크의 장점 (배열처럼 for문을 안돌려도 된다.)
		logger.info("userName : "+userName);
		
		/*
		 * for (String user : userName) { logger.info(user); }
		 */
		
		member_service.multi(userName, model);
		
		return "list";
	}
	
	@RequestMapping(value="/detail.do")
	public String detail(String id, Model model) {
		
		MemberDTO dto = member_service.detail(id);
		model.addAttribute("info", dto);
		return "detail";
	}
	
	@PostMapping(value="/update.ajax")
	@ResponseBody
	public Map<String, Object> update(@RequestParam Map<String, String> params){
		
		logger.info("params : {}", params);
		Map<String, Object> result = member_service.update(params);
		
		return result;
	}
	
}
