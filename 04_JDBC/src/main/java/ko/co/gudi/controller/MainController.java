package ko.co.gudi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ko.co.gudi.service.MainService;

@Controller
public class MainController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping(value="/")
	public String main() {
		logger.info("main page 요청");
		return "main";
	}
	
	@RequestMapping(value="/dbConnect")
	public String dbConnect(Model model) {
		logger.info("dbConnect 요청");
		
		// 클래스를 끌고 오기 위해 먼저 객체화를 해준다.
		MainService service = new MainService();
		
		String msg = service.dbConnect();
		
		model.addAttribute("msg", msg);
		
		return "main";
	}
}
