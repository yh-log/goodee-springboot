package kr.co.gudi.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.gudi.dao.BoardDAO;
import kr.co.gudi.dto.BoardDTO;

@Service
public class BoardService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired BoardDAO board_dao;

	public List<BoardDTO> list() {
		return board_dao.list();
	}
	
	@Transactional
	public BoardDTO detail(int idx) {
		board_dao.upHit(idx);
		return board_dao.detail(idx);
	}

	public String write(Map<String, String> params) {
		
		String msg ="업로드에 실패했습니다.";
		
		if(board_dao.write(params)) {
			msg = "업로드에 성공했습니다.";
		}
		return msg;
	}

	public void del(int idx) {
		board_dao.del(idx);
	}

	public void update(Map<String, String> params) {
		int row = board_dao.update(params);
		logger.info("row : " + row);
	}

	public BoardDTO updateForm(int idx) {
		return board_dao.detail(idx);
	}

}
