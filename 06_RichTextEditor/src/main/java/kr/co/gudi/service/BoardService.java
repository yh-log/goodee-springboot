package kr.co.gudi.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import kr.co.gudi.dao.BoardDAO;
import kr.co.gudi.dto.BoardDTO;

@Service
public class BoardService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired BoardDAO board_dao;

	/**
	 * author yh.kim (24.11.19)
	 * 게시글 작성
	 */
	public boolean write(Map<String, String> param) {
		if(board_dao.write(param) > 0) {
			return true;
		}
		return false;
	}

	/**
	 * author yh.kim (24.11.19)
	 * 리스트 출력
	 */
	public List<BoardDTO> list() {
		return board_dao.list();
	}

	/**
	 * detail 페이지 또는 update 페이지 이동
	 * 받아오는 데이터 동일
	 * code = detail 일 경우 detail 페이지
	 * code = update 일 경우 update 페이지
	 */
	// 코드 중복 최소화를 위해 detail과 update를 code로 분류해서 같은 메서드 활용
	public ModelAndView detail(String idx, String code) {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName(code);
		
		Map<String, Object> map = board_dao.detail(idx);
		mav.addObject("info", map);
		return mav;
	}

	public String update(Map<String, String> param) {
		int row = board_dao.update(param);
		logger.info("update row => " + row);
		if(row > 0) {
			return "redirect:/detail.go?idx="+param.get("idx");
		}
		return "redirect:/list.go";
	}
}
