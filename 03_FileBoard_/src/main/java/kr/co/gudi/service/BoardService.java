package kr.co.gudi.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import kr.co.gudi.mapper.BoardMapper;
import kr.co.gudi.vo.BoardVO;
import kr.co.gudi.vo.FilesVO;

@Service
public class BoardService {
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired BoardMapper board_mapper;
	
	// 게시글 목록
	public Map<String, Object> list(int cnt) {
		int limit = cnt;
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("list", board_mapper.list(limit));
		
		return result;
	}

	// 상세보기
	public void detail(String idx, Model model) {
		board_mapper.upHit(idx);
		BoardVO info = board_mapper.detail(idx);
		List<FilesVO> files = board_mapper.files(idx);
		
		model.addAttribute("info", info);
		model.addAttribute("files", files);
	}

	// 게시글 쓰기
	public int write(Map<String, String> params) {
		int idx = 0;
		
		BoardVO vo = new BoardVO();
		vo.setUser_name(params.get("user_name"));
		vo.setSubject(params.get("subject"));
		vo.setContent(params.get("context"));
		
		if (board_mapper.write(vo) == 1) {
			idx = vo.getIdx();
			logger.info("idx: "+idx);
		}
		
		return idx;
	}

	// 게시글 삭제
	public boolean delete(String idx) {
		boolean success = false;
		
		if (board_mapper.delete(idx) > 0) {
			success = true;
		}
		
		return success;
	}

}