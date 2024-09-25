package kr.co.gudi.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.gudi.dto.MemberDTO;
import kr.co.gudi.service.MemberService;

@Controller
public class MemberController {

	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired MemberService member_service;
	
	@GetMapping(value="/")
	public String home() {
		return "login"; // 추후 메인 페이지 생성 시 해당 페이지로 이동
	}
	
	@GetMapping(value="/join")
	public String join() {
		return "join";
	}
	
	@PostMapping(value="/join")
	public String join(@RequestParam Map<String, String> param, Model model) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result_message", member_service.join(param));
		
		model.addAttribute("resultMap", resultMap);
		
		return "login"; // 추후 메인 페이지 생성 시 해당 페이지로 이동
	}
	
	@GetMapping(value="/overlay")
	@ResponseBody
	public Map<String, Object> overlay(String id){
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("overlay", member_service.overlay(id));
		
		return result;
	}
	
	@PostMapping(value="/login")
//	@ResponseBody
	public Map<String, Object> login(String id, String pw, Model model ,HttpSession session) {
		Map<String, Object> resultMap = new HashMap<>();

		String page = "";
		if(member_service.login(id, pw)) {
			session.setAttribute("loginId", id);
			if(session.getAttribute("loginId").equals("admin")) {
//				page = "redirect:/member_list";
				resultMap.put("result_code", "success");
				resultMap.put("result_message", "");
				resultMap.put("redirect_url", "/member");
			}else {
				resultMap.put("result_code", "success");
				resultMap.put("result_message", "");
				resultMap.put("redirect_url", "/board");
//				page = "redirect:/board_list";
			}
		}else {
			//api
			// {"result_code": "", "result_message": "", "redirect_url" : ""} - get
			// {"result_code": ""}; - post
//			page = "login";
			resultMap.put("result_code", "fail");
			resultMap.put("result_message", "아이디/비밀번호를 확인해주세요.");
			resultMap.put("redirect_url", "/login");
//			model.addAttribute("result_message", "아이디/비밀번호를 확인해주세요.");
			// result_code : fail
			// result_message : "아이디
		}
		resultMap.put("id", id);
		model.addAttribute("resultMap", resultMap);
		
		System.out.println(model.toString());
		return resultMap;
	}
	
//	@GetMapping(value="/memberList")
//	@ResponseBody
//	public Map<String, Object> memberList(@RequestParam(value = "page", defaultValue = "1") String page, 
//										  @RequestParam(value = "page", defaultValue = "20")String cnt) {
//		
//		logger.info("page, cnt"+page+cnt);
//		
//		int page_ = Integer.parseInt(page);
//		int cnt_ = Integer.parseInt(cnt);
//		
//		return member_service.memberList(page_, cnt_);
//	}
	
	// list 화면
	@GetMapping(value="/member")
	public String memberView() {
		return "memberList";
	}
	
	// list조회
	@GetMapping(value="/member/list")
	@ResponseBody
	public Map<String, Object> getMemberList(String page,  String cnt) {
		logger.info("page, cnt"+page+cnt);
		
		int page_ = Integer.parseInt(page);
		int cnt_ = Integer.parseInt(cnt);
		
		return member_service.memberList(page_, cnt_);
		
	}

//	// 상세보기 화면
//	@GetMapping(value="/member/{id}")
////	@ResponseBody -> 값이 나오는지 먼저 확이 (잘 넘어가는지, 잘 연결하고 있는지..)
//	public String memberDelete(@PathVariable String id) {
////		return id;
//		return "memberList";
//	}
	
	
	// ResponseEntity<String> : 본문, HTTP 상태 코드 설정 가능
	
	@DeleteMapping(value="/member/{id}")
	public ResponseEntity<String> deleteMember(@PathVariable String id){
		
		logger.info(id);
		
		try {
			// 성공하면 try 에서 ok 결과 전송
			member_service.deleteMember(id);
			return new ResponseEntity<String>("삭제 성공", HttpStatus.OK);
			
		}catch(Exception e) {
			// 실패하면 catch 에 걸려서 에러 상태 전송
			return new ResponseEntity<String>("삭제 실패", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	// Mapping 경로에 있는 id 와 파라미터로 들어오는 id가 같은 값임을 명시하기 위해 사용
	@GetMapping(value="/member/{id}")
	public String detailView(@PathVariable("id") String id, Model model) {
		
		MemberDTO member = member_service.detailView(id);
		
		model.addAttribute("info", member);
		
		return "memberUpdate";
		
	}
	
	@PutMapping(value="/member/{id}")
	public ResponseEntity<String> getMemberUpdate(
			@PathVariable("id") String id, @RequestBody MemberDTO param){
		
		boolean updateSuccess = member_service.getMemberUpdate(id, param);
		
		if(updateSuccess) {
			return new ResponseEntity<String>("회원정보가 수정되었습니다.", HttpStatus.OK);
		}else{
			return new ResponseEntity<String>("회원정보 수정에 실패했습니다.", HttpStatus.BAD_REQUEST);
		}
		
	}
	
	
}
