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
	@Autowired BoardMapper boardMapper;
	
	public Map<String, Object> list(int page, int cnt) {
		
		logger.info("현재 페이지 : " + page);
		logger.info("한 페이지에 보여줄 갯수 : " + cnt);
		
		int limit = cnt;
		int offset = (page-1) * cnt; // 1페이지일 때 (1씩 빼준다)
		// → 처음 시작은 0이여야 하니까 0*20 으로 시작하고, 2 페이지 일때는 -1을 해서 1*20 = 20 형태로 해준다.
		
		int totalPages = boardMapper.allCount(cnt);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("totalPage", totalPages);
		// 나중에 필요할 수도 있으니까 만들어준다.
		result.put("currPage", page);
		result.put("list", boardMapper.list(limit, offset));
		
		return result;
	}
	
	
}
