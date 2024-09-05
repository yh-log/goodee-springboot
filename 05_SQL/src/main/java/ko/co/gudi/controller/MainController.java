package ko.co.gudi.controller;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ko.co.gudi.service.MainService;

@Controller
public class MainController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String main() {
		logger.info("메인페이지");
		
		return "main";
	}
	
	@RequestMapping(value="/dbConnect")
	public String dbConnect(Model model) {
		logger.info("db요청");
		MainService service = new MainService();
		String msg = service.dbConnect();
		model.addAttribute("msg", msg);
		return "main";
	}
	
	@RequestMapping(value="/stmt")
	public String stmt(Model model) {
		logger.info("테이블 생성 요청");
		MainService service = new MainService();
		String msg = "";
		try {
			msg = service.stmt();
		} catch (SQLException e) {
			e.printStackTrace();
			msg = "데이터 처리 중 문제가 발생 했습니다.";
		}
		model.addAttribute("msg", msg);
		
		return "main";
	}
	
	@RequestMapping(value="/pstmt")
	public String patmt(Model model) {
		logger.info("insert 요청");
		MainService service = new MainService();
		String msg = "";
		try {
			msg = service.pstmt();
		} catch (Exception e) {
			e.printStackTrace();
			msg = "데이터 처리 중 문제가 발생했습니다.";
			msg = e.toString(); // 이거 쓰면 안됨!! 외부에 노출되니까
		}
		model.addAttribute("msg", msg);
		return "main";
	}
	
	@RequestMapping(value="/resultSet")
	public String resultSet(Model model) {
		logger.info("slelect 요청!");
		
		String msg = "";
		
		MainService service = new MainService();
		
		try {
			msg = service.resultSet();
		} catch (SQLException e) {
			e.printStackTrace();
			msg = "데이터 처리 중 문제가 발생 했습니다.";
		}
		model.addAttribute("msg", msg);
		return "main";
	}
}
