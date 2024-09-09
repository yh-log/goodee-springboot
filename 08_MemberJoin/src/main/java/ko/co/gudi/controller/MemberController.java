package ko.co.gudi.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ko.co.gudi.service.MemberService;

@Controller
public class MemberController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	// 객체를 복사해서 공통영역(static) 에 저장하는 방식을 사용 (싱클톤 패턴)
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
	public String join(@RequestParam Map<String, String> param, Model model) { // 컬렉션 프레임워크로 받을 때는 무조건 requestParam 이 있어야 한다.
		logger.info("param : {}", param);
		String msg = service.join(param);
		model.addAttribute("result", msg);
		
		return "login";
	}
}
