package kr.co.gudi.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.gudi.service.MainService;

@Controller
public class MainController {
	
	
	// application.properties 에서 가져올 설정
	@Value("${upload.path}") String path;
	
	@Value("${spring.application.name}") String name;
	@Value("${spring.mvc.view.prefix}") String prefix;
	@Value("${spring.mvc.view.suffix}") String suffix;
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired MainService main_service;

	@RequestMapping(value="/")
	public String main() {
		logger.info(path);
		logger.info(name);
		logger.info(prefix);
		logger.info(suffix);
		return "main";
	}
	
	@PostMapping(value="/login.do")
	public String login(HttpServletRequest req, Model model) {
		// id, pw, ip 를 비교해야 하는데 ip는 입력하지 않았다.
		// ip를 request 에서 직접 뽑아내서 사용 (jsp 첫 시간에 배움) request.getRemoteAddr() => request 객체가 있으면 파라미터도 받을 수 있다.(따로 받을수도 있고)
		// request 로 받으면 한글이 깨질수도 있다. (부트에서는 안그러긴 하지만 인코딩이 필요할 수 있다.)
		
		String page = "main";
		String msg = "아이디 또는 비밀번호를 확인하세요";
		
		String id = req.getParameter("id");
		String pw = req.getParameter("pw");
		String ip = req.getRemoteAddr();
		String opt = req.getParameter("option");
		
		logger.info(id + " / " +pw + " / " + ip + " / " + opt);
		
		if(main_service.login(id,pw,ip,opt)) {
			HttpSession session = req.getSession(); // 이렇게도 가져올 수 있다.
			session.setAttribute("loginId", id);
			session.setAttribute("perm", opt);
			// 세션에서 2가지를 검사한다. (로그인 했는지 -> 관리자인지 일반회원인지)
			
			msg = "로그인에 성공했습니다.";
		}
		
		model.addAttribute("msg", msg);
		
		return page;
	}
	
	
}
