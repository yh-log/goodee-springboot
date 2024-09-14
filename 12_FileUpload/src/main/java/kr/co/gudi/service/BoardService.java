package kr.co.gudi.service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	public List<BoardDTO> list() {
		return board_dao.list();
	}
	
	@Transactional
	public void detail(int idx, Model model) {
		board_dao.upHit(idx);
		
		BoardDTO bbs = board_dao.detail(idx);
		
		List<FileDTO> list = board_dao.files(idx);
		
		model.addAttribute("info", bbs);
		model.addAttribute("files", list);
		
	}

	public void write(Map<String, String> params, MultipartFile[] files) {
		
		BoardDTO dto = new BoardDTO();
		dto.setUser_name(params.get("user_name"));
		dto.setSubject(params.get("subject"));
		dto.setContent(params.get("content"));
		
		if(board_dao.write(dto)>0) {
			int idx = dto.getIdx();
			for (MultipartFile file : files) {
				try {
					String ori_filename = file.getOriginalFilename();
					String ext = ori_filename.substring(ori_filename.lastIndexOf("."));
					String new_filename = UUID.randomUUID().toString()+ext;
			
					byte[] arr = file.getBytes();
					Path path = Paths.get("C:/upload/"+new_filename);
					Files.write(path, arr);
					board_dao.filewrite(idx, ori_filename, new_filename);
				
					logger.info(ori_filename + "/" + new_filename);
			
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Transactional
	public void del(int idx) {
		List<FileDTO> list = board_dao.files(idx);
		
		int row = board_dao.del(idx);
		if(row>0) {
			for (FileDTO dto : list) {
				File file = new File("C:/upload/"+dto.getNew_filename());
				if(file.exists()) {
					boolean success = file.delete();
					logger.info("삭제 : " + success);
				}
			}
		}
		
	}

	public void update(Map<String, String> params) {
		int row = board_dao.update(params);
		logger.info("row : " + row);
	}

	public BoardDTO updateForm(int idx) {
		return board_dao.detail(idx);
	}

	public ResponseEntity<Resource> download(String new_filename, String ori_filename) {

		logger.info("service : " + ori_filename + "/" + new_filename);
		
		HttpHeaders header = new HttpHeaders();
		
		Resource body = new FileSystemResource("C:/upload/"+new_filename);
		
		
		try {
			header.add("content-type", "application/octet-stream");
			String encode = URLEncoder.encode(ori_filename,"UTF-8");
			header.add("content-Disposition", "attachment;filename=\""+encode+"\"");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return new ResponseEntity<Resource>(body, header, HttpStatus.OK);
	}

}
