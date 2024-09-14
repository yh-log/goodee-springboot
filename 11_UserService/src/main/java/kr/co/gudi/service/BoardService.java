package kr.co.gudi.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.co.gudi.dao.BoardDAO;
import kr.co.gudi.dto.BoardDTO;

@Service
public class BoardService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired BoardDAO board_dao;
	
	public List<BoardDTO> boardlist() {
		
		return board_dao.boardlist();
	}

	@Transactional
	public BoardDTO detail(int idx) {
		board_dao.uphit(idx);
		return board_dao.detail(idx);
	}

	public void upload(Map<String, String> params, MultipartFile[] files) {
		int row = board_dao.upload(params);
		logger.info("params {} : ", params);
		logger.info("insert된 행 : " + row);
		
	}

	public void boarddel(int idx) {
		int row = board_dao.boarddel(idx);
		logger.info("delete row : " + row);
	}

	public BoardDTO updateForm(int idx) {
		
		logger.info("idx :{} ", idx);
		return board_dao.detail(idx);
	}

	public void update(Map<String, String> param) {
		int row = board_dao.update(param);
		logger.info("update row : " + row);
	}

}
