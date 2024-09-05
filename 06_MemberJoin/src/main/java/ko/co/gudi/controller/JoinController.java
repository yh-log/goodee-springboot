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
	
	
	@RequestMapping(value="/join", method = RequestMethod.POST)
	public String join(HttpServletRequest req, Model model) {
		logger.info("회원가입 controller");
		
		JoinService service = new JoinService();
		
		try {
			String result = service.join(req); // controller 에 들어온 request 정보를 service 에 넘겨준다.
			model.addAttribute("result", result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("회원가입이 실패했습니다. controller");
		}		
		
		return "resultSet";
	}
}
