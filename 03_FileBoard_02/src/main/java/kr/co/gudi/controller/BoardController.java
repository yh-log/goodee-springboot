package kr.co.gudi.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import kr.co.gudi.service.BoardService;

@RestController
public class BoardController {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	// 2.7.17은 필드주입을 사용해도 괜찮지만 이전 버전에서는 사용하지 않아야 하는 상황도 있다. (구버전)
	//@Autowired BoardService board_service; // 필드 주입 : 특정한 빈 (객체)을 변수 (멤버필드)에 넣는다고 하여 필드 주입이라고 부른다.
	
	
	/**
	 * 필드 주입은 2.7.17 에서는 사용해도 된다.
	 * 하지만 이전 버전에서는 특정 상황(숨환참조)에서 문제가 된다.
	 *  	순환참조 : 서로가 서로를 참조하고 있기 때문에 한 쪽에서 변화가 감지되면 무한으로 실행되는 현상
	 * 필드 주입은 순환참조를 프로그램이 실행되 봐야 알 수 있다. (미리 알지 못하고 실행해 봐야 알 수 있다. 다른 방법은 미리 알 수 있다(빨간줄))
	 * 그래서 필드주입 대신 생성자 주입을 권고하고 있다.
	 * 
	 * 순환참조는 A 서비스와 B 서비스가 서로 호출할 때 발생한다. (우리는 MVC 패턴을 사용해서 지금까지 안타나난거임)
	 * 그래서 Controller → Service → DAO 순으로 호출이 되도록 설계하면 순환참조를 막을 수 있다. 
	 * 		(A 가 B 를 부를 때 B 는 절대 A를 부르지 않도록 설계해야 한다.)
	 */
	
	
	// [생성자 주입]
	// final 로 움직이지 못하게 고정을 시켜둠 (생성자 외에는 초기화할 수 없음) / static이 붙으면 아예 못바꾸니까 상수
	private final BoardService board_service;
	
	// 생성자 만들기
	public BoardController(BoardService board_service) {
		this.board_service = board_service;
	}
	
	
	
	
	// 게시글 목록
	@RequestMapping (value = "/board_list.go")
	public ModelAndView list(HttpSession session) {
		return new ModelAndView("board_list");
	}
	
	@RequestMapping (value = "/board_list.ajax")
	public Map<String, Object> list(String cnt) {
		return board_service.list(Integer.parseInt(cnt));
	}
	
	// 상세보기
	@RequestMapping (value = "/board_detail.go")
	public ModelAndView list(String idx, Model model, HttpSession session) {
		board_service.detail(idx, model);
		return new ModelAndView("board_detail");
	}
	
	// 게시글 쓰기
	@RequestMapping (value = "/board_write.go")
	public ModelAndView write(Model model, HttpSession session) {
		return new ModelAndView("board_write");
	}

	@RequestMapping (value = "/board_write.do")
	public ModelAndView write(@RequestParam Map<String, String> params) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("board_write.go");
		int idx = board_service.write(params);
		
		if(idx > 0) {
			mav.setViewName("redirect:/board_detail.go?idx="+idx);
		}
		
		return mav;
	}
	
	// 게시글 삭제
	@RequestMapping (value = "/board_delete.do")
	public ModelAndView delete(String idx) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/board_detail.go?idx="+idx);
		
		if (board_service.delete(idx)) {
			mav.setViewName("redirect:/board_list.go");
		}
		
		return mav;
	}

}