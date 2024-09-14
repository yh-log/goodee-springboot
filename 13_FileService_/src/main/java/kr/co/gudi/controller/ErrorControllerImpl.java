package kr.co.gudi.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorControllerImpl implements ErrorController {
	
	@RequestMapping(value="/error")
	public String error(Model model, HttpServletRequest req) {
		
		int code = (int) req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		model.addAttribute("code", code);
		
		String msg = "알수 없는 오류";
		
		switch (code) {
			case 400:
				msg = "입력 값의 갯수가 맞지 않아요";
				break;
				
			case 404:
				msg = "해당 요청 및 페이지를 찾을 수 없음";
				break;
				
			case 405:
				msg = "요청한 메서드와 받는 메서드가 일치하지 않음";
				break;
				
			case 500:
				msg = "서버 처리중 예외 발생";
				break;
		}
		
		model.addAttribute("msg", msg);		
		
		return "error";
	}

}
