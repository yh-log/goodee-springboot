package ko.co.gudi.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ko.co.gudi.dto.UserDTO;
import ko.co.gudi.service.UserService;

@Controller
public class UserController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping(value="/")
	public String home() {
		logger.info("home page");
		return "home";
	}
	
	@RequestMapping(value="/loginForm")
	public String loginForm() {
		logger.info("login 페이지로 이동");
		return "login";
	}
	
	@RequestMapping(value="/joinForm")
	public String joinForm() {
		logger.info("join 페이지로 이동");
		return "join";
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public String login(String id, String pw, Model model, HttpSession session) {
		logger.info("login 요청 controller");
		
		String page = "login"; //로그인 실패 시 login 페이지로 이동
		
		UserService service = new UserService();
		
		if(service.login(id, pw)) {
			page = "redirect:/list"; // 로그인에 성공하면 list로 이동
			session.setAttribute("loginId", id);
		}
		
		model.addAttribute("msg", "로그인에 실패했습니다."); // 추후 alert로 안내할 문구
		
		return page;
	}
	
	@RequestMapping(value="/join", method = RequestMethod.POST)
	public String join(Model model, @RequestParam Map<String, String> map) {
		logger.info("회원가입 요청 controller");
		
		UserService service = new UserService();
		
		if(service.join(map)) {
			model.addAttribute("msg", "회원가입에 성공했습니다.");
		} else {
			model.addAttribute("msg", "회원가입에 실패했습니다.");
		}
		
		
		return "login"; // 회원가입 여부와 관계 없이 로그인 페이지로 이동
	}
	
	@RequestMapping(value="/list")
	public String list(Model model) {
		
		logger.info("list 요청 controller");
		String page = "";
		
		UserService service = new UserService();
		
		List<UserDTO> list = service.list();
		
		if(list.size() != 0) {
			page = "list";
			logger.info("list 요청 최종 완료");
			model.addAttribute("list", list);
		} else {
			page = "login";
			logger.info("list 요청 최종 실패");
		}
		return page;
	}
	
	@RequestMapping(value="/del")
	public String del(String id) {
		
		UserService service = new UserService();
		if(service.del(id)) {
			logger.info("최종 삭제 성공");
		} else {
			logger.info("최종 삭제 실패");
		}
		
		return "redirect:/list";
	}
	
	@RequestMapping(value="/detail")
	public String detail(String id, Model model) {
		String page = "";
		logger.info("상세정보 요청 controller");
		logger.info("controller id : {}", id);
		
		UserService service = new UserService();
		
		UserDTO dto;
		try {
			dto = service.detail(id);
			if(dto != null) {
				page = "detail";
				model.addAttribute("detail", dto);
			} else {
				page = "redirect:/list";
			}
		} catch (SQLException e) {
			logger.info("상세정보 요청 최종 실패");
			e.printStackTrace();
		}
		
		
		return page;
	}
	
	@RequestMapping(value="logout")
	public String logout(HttpSession session, Model model) {
		
		session.removeAttribute("loginId");
		model.addAttribute("", "로그아웃 되었습니다.");
		
		return "login";
	}
	
	@RequestMapping(value="/change", method = RequestMethod.POST) // change 를 누르면 update
	public String change(@RequestParam Map<String, String> map,  Model model){
		logger.info("change : {}", map);
		
		// UPDATE user SET name = ? WHERE id = ?
		
		UserService service = new UserService();
		UserDTO dto;
		try {
			dto = service.change(map);
			model.addAttribute("change", dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return "redirect:/list";
	}
	
}
