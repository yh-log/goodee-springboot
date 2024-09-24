package kr.co.gudi.service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
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
	public List<BoardDTO> board_list() {
		
		List<BoardDTO> boardlist = board_dao.board_list();
		
		return boardlist;
	}
	public String write(MultipartFile[] files, Map<String, String> params) {

		String page = "redirect:/board_list";
		
		BoardDTO dto = new BoardDTO();
		dto.setUser_name(params.get("user_name"));
		dto.setSubject(params.get("subject"));
		dto.setContent(params.get("content"));
		
		board_dao.write(dto);
		int idx = dto.getIdx();
		
		if(idx>0) {
			fileSave(files, idx);
			page = "redirect:/detail?idx="+idx;
		}
		
		return page;
	}
	private void fileSave(MultipartFile[] files, int idx) {
		
		for (MultipartFile file : files) {
			
			String ori_filename = file.getOriginalFilename();
			String ext = ori_filename.substring(ori_filename.lastIndexOf("."));
			String new_filename = UUID.randomUUID().toString() + ext;
			
			try {
				byte[] arr = file.getBytes();
				Path path = Paths.get("C:/upload/"+new_filename);
				Files.write(path, arr);
				board_dao.fileWrite(new_filename, ori_filename, idx);
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	@Transactional
	public void detail(int idx, Model model) {

		board_dao.upHit(idx);
		
		BoardDTO dto = board_dao.detail(idx);
		
		List<FileDTO> files = board_dao.fileList(idx);
		
		model.addAttribute("detail", dto);
		model.addAttribute("files", files);
		
		
	}
	
	public void board_del(int idx) {
		
		List<FileDTO> fileList = board_dao.fileList(idx); // 파일 정보 가져오기
		
		if(board_dao.board_del(idx)>0) { // 글 삭제
			for (FileDTO dto : fileList) {
				File file = new File("C:/upload/"+dto.getNew_filename());
				if(file.exists()) {
					boolean success = file.delete();
					logger.info("파일 삭제 : " + success);
				}
			}
		}
		
	}
	public ResponseEntity<Resource> download(String new_filename, String ori_filename) {
		
		Resource body = new FileSystemResource("C:/upload/"+new_filename);
		
		HttpHeaders header = new HttpHeaders();
		header.add("content-type", "application/octet-stream");
		try {
			String filename = URLEncoder.encode(ori_filename, "UTF-8");
			header.add("content-Disposition", "attechment;filename=\""+filename+"\"");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<Resource>(body, header, HttpStatus.OK);
	}

}
