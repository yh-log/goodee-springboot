package kr.co.gudi.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.gudi.mapper.BoardMapper;

@Service
public class BoardService {

	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired BoardMapper board_mapper;
	
	public Map<String, Object> list(int page1, int cnt1) {
		
		int cnt = cnt1;
		int page = (page1-1)*cnt1;
		
		int totalPages = board_mapper.allCount(cnt);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("totalPage", totalPages);
		
		map.put("list", board_mapper.list(cnt, page));
		
		
		return map;
	}
	
	
}
