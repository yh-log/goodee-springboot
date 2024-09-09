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

import ko.co.gudi.service.MemberService;
import ko.co.gudi.dto.MemberDTO;

@Controller
public class MemberController {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	
	@RequestMapping(value="/")
	public String loginForm() {
		return "login";
	}
	
	@RequestMapping(value="/joinForm")
	public String joinForm() {
		return "join";
	}
	
	@RequestMapping(value="/join", method = RequestMethod.POST)
	public String join(Model model, @RequestParam Map<String,String> param) { // 키 : 값 형태로 이루어져 있기 때문에 Map 형태로 받을 수 있다.
		logger.info("param : {}", param);
		
		MemberService service = new MemberService(); // 서비스에게 일을 시키기 위해 객체 선언
		String msg = "";
		try {
			msg = service.join(param); // 서비스에 파라미터 넘겨주고, 
		} catch (SQLException e) {
			msg = "회원가입중 문제가 발생 했습니다.";
			e.printStackTrace();
		}
		model.addAttribute("result", msg); // 서비스에서 리턴받은 msg 값을 login.jsp 에 보냄
		
		return "login"; // 회원가입 완료 시 login 페이지로 돌아감
	}
	
	@RequestMapping(value="/login") // 이 사람이 로그인을 한 사람인지를 알아야 하니까 session을 사용해준다.
	public String login(String id, String pw, Model model, HttpSession session) { // 받을 파라미터가 2개 이하인 경우 그냥 받아주는게 좋다 + 세션 사용을 위해 HttpSession 선언
		
		String page = "login"; // 성공/실패에 따라서 가야할 페이지가 달라질 경우...
		logger.info(id+"/"+pw);		
		MemberService service = new MemberService();
		
		if(service.login(id,pw)) { // 서비스에서 받은 값이 true면 아래 list() 로 ridirect 시킨다.
			page = "redirect:/list";//sendRedirect 를 사용(특정 controller 호출)
			session.setAttribute("loginId", id); // session 에는 id 값을 저장하고 있도록
		}else {
			model.addAttribute("result", "아이디 또는 비밀번호를 확인 하세요!"); // id/pw가 일치하지 않으면 해당 문구를 전달
		}
		
		return page;//jsp 페이지로 보낼때 -> 성공했을 경우 list()로 이동 실패했을 경우 login 페이지로 이동
	}
	
	@RequestMapping(value="/list") // 로그인에 성공하면 list로 온다.
	public String list(Model model) {
		logger.info("list 요청!!!!!");
		MemberService service = new MemberService(); // 서비스에게 일을 시키기 위해 객체 선언
		
		// dto에서 꺼내온 값이 담겨있는 list를 넣어준다.
		List<MemberDTO> list = service.list(); // 다양한 값이 담겨 오기 때문에 List 타입으로 받아준다.
		model.addAttribute("list", list); // list에 대한 값을 넘겨서 list 페이지에서 보여준다.
		
		return "list";
	}
	
	@RequestMapping(value="/del") // list 페이지에서 del 을 누르면 여기로 이동
	public String del(String id) {
		logger.info("삭제할 id : " + id);
		MemberService service = new MemberService(); // 서비스한테 넘기기 위해 객체 생성
		service.del(id); // 반환 받아도 보낼 때가 없으니까 반환 받지 x
		
		// 삭제 성공 했으면 다시 list로 
		return "redirect:/list"; // redirect 는 reponse 객체라서 메세지 못보냄 (할수는 있는데 정식 방법은 아님..!!)
	}
	
	@RequestMapping(value="/detail") // 사용자의 이름을 누르면 해당 메서드 실행
	public String detail(String id, Model model) {
		String page = "redirect:/list"; 
		logger.info("상세보기 할 ID : " + id);
		
		MemberService service = new MemberService(); // 서비스한테 보내기 위해 객체 생성
		// dao한테 요청 할 쿼리문 : SELECT * FROM member WHERE id = ?; → 하나니까 list에 담을 필요는 없음
		
		MemberDTO dto = service.detail(id); // dto에 있는 값을 가져올 거니까 DTO 타입의 변수에 service에서 받아온 값 넣기
		
		if(dto != null) { // 값이 있으면 페이지를 detail로 옮기고
			page = "detail";
			model.addAttribute("info", dto); // dto에 담긴 정보를 보여준다.
		}
		return page; // 성공했을 때는 list()로 이동해서 list 페이지로 간다.
		
	}
	
	@RequestMapping(value="/logout")
	public String logout(HttpSession session, Model model) { // 세션은 컨트롤러에서만 받아올 수 있다.
		
		session.removeAttribute("loginId");
		
		model.addAttribute("result", "로그아웃 되었습니다.");
		
		return "login";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
