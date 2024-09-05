package ko.co.gudi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping(value="/") // method를 지정안해두면 get, post를 모두 받는다는 의미
	public String home() {
		logger.info("home.jsp 요청");
		return "home";
	}
	
	// post 메서드만 받겠다.
	// 이때 맞지 않는 메서드(get 등)로 요청을 보내요면 405 에러가 발생한다.
	@RequestMapping(value="/calc", method = RequestMethod.POST)
	public String calc(Model model, String su1, String su2, String oper) {
		logger.info(su1 + oper + su2);
		int num1 = Integer.parseInt(su1);
		int num2 = Integer.parseInt(su2);
		int result = 0;
		
		switch (oper) {
		case "+": 
			result = num1 + num2;
			break;
		case "-": 
			result = num1 - num2;
			break;
		case "*": 
			result = num1 * num2;
			break;
		case "/": 
			result = num1 / num2;
			break;
		}
		logger.info(su1 + oper + su2);
		
		model.addAttribute("result", result);
		model.addAttribute("num1", su1);
		model.addAttribute("num2", su2);
		model.addAttribute("oper", oper);
		
		return "result";
	}
}
