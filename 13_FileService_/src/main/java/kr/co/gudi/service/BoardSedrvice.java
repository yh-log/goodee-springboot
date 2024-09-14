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
import org.springframework.web.multipart.MultipartFile;

import kr.co.gudi.mapper.BoardMapper;
import kr.co.gudi.vo.BoardVO;

@Service
public class BoardSedrvice {
	
	@Autowired BoardMapper board_mapper;
	
	Logger logger = LoggerFactory.getLogger(getClass());

	public List<BoardVO> list() {
		return board_mapper.list();
	}

	public void write(Map<String, String> params, MultipartFile[] files) {
		
		String page = "redirect:/list.do";
		
		logger.info("params : {} ", params);
		logger.info("files : {}"+ files);
		
		BoardVO vo = new BoardVO();
		vo.setUser_name(params.get("user_name"));
		vo.setSubject(params.get("subject"));
		vo.setContent(params.get("content"));
		
		if(board_mapper.write(vo)>0) {
			int idx = vo.getIdx();
			fileSave(files, idx);
			page = "redirect:/detail.do?idx="+idx;
		}
	}
	
	void fileSave(MultipartFile[] files, int idx) {
		for (MultipartFile file : files) {
			
			try {
				String fileName = file.getOriginalFilename();
				String ext = fileName.substring(fileName.lastIndexOf("."));
				String new_fileName = UUID.randomUUID().toString() + ext;
				
				byte[] arr = file.getBytes();
				Path path = Paths.get("C:/upload/"+new_fileName);
				Files.write(path, arr);
				board_mapper.fileWrite(fileName, new_fileName, idx);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}


}






















