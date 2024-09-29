package kr.co.gudi.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.gudi.dao.BoardDAO;

@Service
public class BoardService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired BoardDAO board_dao;
	public Map<String, Object> list(int page_, int cnt_) {
		
		int limit = cnt_;
		int offset = (page_-1) *cnt_;
		
		logger.info(limit + " / " + offset);
		
		int totalPage = board_dao.totalPage(limit);
		
		Map<String, Object> listMap = new HashMap<String, Object>();
		
		listMap.put("totalPage", totalPage);
		listMap.put("list", board_dao.list(limit, offset));
		
		return listMap;
	}

}
