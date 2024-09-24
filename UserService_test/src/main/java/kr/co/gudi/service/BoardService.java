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
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import kr.co.gudi.dao.BoardDAO;
import kr.co.gudi.dto.BoardDTO;
import kr.co.gudi.dto.FileDTO;

@Service
public class BoardService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired BoardDAO board_dao;
	
	public List<BoardDTO> boardlist() {
		
		return board_dao.boardlist();
	}

	@Transactional
	public void detail(int idx, Model model) {
		board_dao.uphit(idx);
		BoardDTO dto = board_dao.detail(idx);
		List<FileDTO> files = board_dao.fileList(idx);
		
		model.addAttribute("detail", dto);
		model.addAttribute("files", files);
	}
	
	@Transactional
	public String upload(Map<String, String> params, MultipartFile[] files) {

		String page = "redirect:/board_list";
		
		logger.info("params {} : ", params);
		
		BoardDTO dto = new BoardDTO();
		dto.setUser_name(params.get("user_name"));
		dto.setSubject(params.get("subject"));
		dto.setContent(params.get("content"));
		
		board_dao.upload(dto);
		
		int idx = dto.getIdx();
		if(idx > 0) {
			filesave(files,idx);
			page = "redirect:/detail?idx="+idx;
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
				board_dao.fileUpload(new_filename, ori_filename, idx);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
