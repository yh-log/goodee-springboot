package ko.co.gudi.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

// 컨트롤러의 역할을 할 수 있도록 뭘 해줘야 한다.

@Controller // controller 라는 표시
public class MainController {
	
	// context 경로를 제외한 나머지 -> 설정에 의해 앞에 home 앞에 views 와 뒤에 .jsp 가 붙게 된다.
	@RequestMapping(value="/") // "/" 라는 요청이 오면 아래 메서드와 매핑시켜라
	public String home() {
		return "home"; // views/home.jsp 로 가라 -> 우리가 properties 에 앞에 views를 뒤에 jsp 를 붙이도록 했다.
	}
	
//	@RequestMapping(value="/login")
//	public String loginView() {
//		return "login";
//	}
	
	@RequestMapping("/message")
	public String message(HttpServletRequest req, Model model) { // 여기는 request 객체일 뿐
		String param = req.getParameter("param"); // param 이라는 파라미터를 가져와서 param에 넣어라
		System.out.println("param : " + param);
		// 파라미터를받아서 view 에 보낸다.
		Object msg="";
		if (param == null) {
			msg = "Invalid Type";
		} else if(param.equals("greeting")) {
			msg = "안녕하세요";
		} else {
			msg = new Date();
		}
		model.addAttribute("msg", msg);
		
		return "home";
// http://localhost:8080/01_Start/message?param=greeting (?가 있으면 그 뒤는 파라미터 = 은 param에 해당하는 값이 뒤에 값
	}
	
}
