package ko.co.gudi.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ko.co.gudi.service.JoinService;

@Controller
public class JoinController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping(value="/")
	public String home() {
		return "join";
	}
	
	@RequestMapping(value="join", method = RequestMethod.POST)
	public String join(Model model, HttpServletRequest req) { 
		logger.info("회원가입 controller");
		
		JoinService service = new JoinService();
		String msg = "";
		try {
			msg = service.join(req);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("회원가입 실패");
		}
		
		model.addAttribute("msg", msg);
		
		return "join";
	}
	
//	@RequestMapping(value="login")
//	public String login() {
//		
//	}
	
}
