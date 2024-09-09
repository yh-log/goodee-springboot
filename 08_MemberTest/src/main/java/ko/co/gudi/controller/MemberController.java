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

import ko.co.gudi.dto.MemberDTO;
import ko.co.gudi.service.MemberService;

@Controller
public class MemberController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping(value="/")
	public String home() {
		return "login";
	}
	
	@RequestMapping(value="joinForm")
	public String joinForm() {
		return "join";
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public String login(String id, String pw, Model model, HttpSession session) { // 사용자에게 받은 id와 pw 
		
		logger.info("로그인 요청 아이디 : ", id);
		
		String msg = "로그인에 실패했습니다.";
		
		String page = "login";
		
		MemberService service = new MemberService(); // 서비스에 요청하기 위해 객체 생성
		
		if(service.login(id, pw)) { // 성공 여부만 알면 되기 때문에 boolean 값으로 받음
			page = "redirect:/list"; // 로그인 성공 시 list()로 이동 (특정 controller 호출)
			// 세션 추가 (나중에)
			
			msg = "로그인에 성공했습니다.";
			model.addAttribute("login", msg);
			session.setAttribute("loginId", id);
			System.out.println("sysout loginId " + session.getAttribute("loginId"));
		}
		
		return page; // 로그인 성공/실패에 따라 다른 페이지로 이동
	}
	
	@RequestMapping(value="/join")
	public String join(Model model, @RequestParam Map<String, String> param) {
		
		MemberService service = new MemberService();
		
		logger.info("param : {}", param); // 회원가입으로 들어오는 값 확인
		
		String msg = "";
		
		try {
			msg = service.join(param);// 컨트롤러 요청시에 들어온 request 정보들을 서비스로 넘겨준다.
		} catch (SQLException e) {
			e.printStackTrace();
			msg = "회원가입 중 문제가 발생했습니다.";
		} 
		
		model.addAttribute("result", msg);
		return "login";
	}
	
	
	@RequestMapping(value="/list")
	public String list(Model model, HttpSession session) {
		logger.info("리스트 요청 controller");
		logger.info("loginId > {} ", session.getAttribute("loginId"));
		MemberService service = new MemberService();
//		List<MemberDTO> list; // new 로 생성하지 않아서 생성이 안되어서 에러가 남
//		list = service.list();
		//size 0체크
		List<MemberDTO> list = service.list(); // List 사이즈 0 체크해주면 좋음! 만약 Service에서 객체를 안만들었으면 null
		// -> 프론트에서 조회된 데이터가 없습니다 이런 안내가 나오게 하면 좋음!
		model.addAttribute("list", list);
		
		return "list";
	}
	
	@RequestMapping(value="/detail")
	public String detail(String id, Model model) {
		
		logger.info("상세 페이지 이동 controller");
		
		String page = "redirect:/list"; // 실패 시 돌아갈 곳
		
		MemberService service = new MemberService(); // select 로 받아와야 한다.
		// SELECT * FROM member WHERE id = ?;
		
		MemberDTO dto = service.detail(id);
		
		if(dto != null) {
			page = "detail";
			model.addAttribute("info", dto);
		}
		
		return page;
		
	}
	
	@RequestMapping(value="/del")
	public String del(String id, Model model) {
		
		logger.info("삭제 요청 아이디 : {}", id);
		
		
		// 쿼리문 : DELETE FROM member WHERE id = ?;
		MemberService service = new MemberService();
		
		String msg = service.del(id);
		if(msg != null) {
			String page = "redirect:/list";
			model.addAttribute("deldap", msg); // 왜 경고창이 안나올까....
			
		}
		
		service.del(id);
		
		
		return "login";
	}
	
	@RequestMapping(value="/logout")
	public String logout(HttpSession session, Model model) {
		session.removeAttribute("loginId");
		model.addAttribute("result", "로그아웃되었습니다.");
		
		return "login";
	}
	
	
	
	
}
