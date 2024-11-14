package kr.co.gudi.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.co.gudi.dao.BoardDAO;
import kr.co.gudi.dto.BoardDTO;

@Service
public class BoardService {
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired BoardDAO boardDao;
	
	public List<BoardDTO> getList(String page, String cnt) {
		int offset = Integer.parseInt(cnt);
		int limit = (Integer.parseInt(page) - 1) * offset;
		
		return boardDao.getList(limit, offset);
	}
	public boolean write(BoardDTO boardDto, MultipartFile[] files) {
		if(boardDao.write(boardDto) > 0) {
			
			return true;
		}
		return false;
	}
	
}
