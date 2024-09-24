package kr.co.gudi.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.co.gudi.mapper.BoardMapper;
import kr.co.gudi.vo.BoardVO;

@Service
public class BoardService {

	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired BoardMapper board_mapper;
	
	public List<BoardVO> list() {
		return board_mapper.list();
	}

	@Transactional
	public BoardVO detail(int idx) {
		
		board_mapper.upHit(idx);
		
		return board_mapper.detail(idx);
	}

	public String write(Map<String, String> param, MultipartFile[] files) {
		
		String page = "redirect:/list.do";
		
		BoardVO vo = new BoardVO();
		vo.setUser_name(param.get("user_name"));
		vo.setSubject(param.get("subject"));
		vo.setContent(param.get("content"));
		
		board_mapper.write(vo);
		
		int idx = vo.getIdx();
		if(idx>0) {
			filesave(files, idx);
			page = "redirect:/detail.do?idx="+idx;
		}
		return page;
	}

	void filesave(MultipartFile[] files, int idx) {
		
		for (MultipartFile file : files) {
			
			String ori_filename = file.getOriginalFilename();
			String ext = ori_filename.substring(ori_filename.lastIndexOf("."));
			String new_filename = UUID.randomUUID() + ext;
			
			try {
				byte[] arr = file.getBytes();
				Path path = Paths.get("C:/upload/"+new_filename);
				Files.write(path, arr);
				board_mapper.fileWrite(new_filename, ori_filename, idx);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
}
