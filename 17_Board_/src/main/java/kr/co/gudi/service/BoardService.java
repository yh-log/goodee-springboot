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
import kr.co.gudi.dto.FilesDTO;

@Service
public class BoardService {

	Logger logger = LoggerFactory.getLogger(getClass());
		
	@Autowired BoardDAO bd_dao;

	public List<BoardDTO> list() {
		return bd_dao.list();
	}

	
	@Transactional
	public void detail(String idx, Model model) {
		logger.info(idx);
		BoardDTO det = bd_dao.detail(idx);
		bd_dao.bhit(idx);
		List<FilesDTO> files = bd_dao.files(idx);
		model.addAttribute("info", det);
		model.addAttribute("files", files);
		
	}


	public int write(Map<String, String> param, MultipartFile[] files) {
		
		BoardDTO dto = new BoardDTO();
		dto.setUser_name(param.get("user_name"));
		dto.setSubject(param.get("subject"));
		dto.setContent(param.get("content"));
		
		// 1. 글 저장
		int row = bd_dao.write(dto);
		logger.info("inset 한 수 : " + row);
		
		int idx = dto.getIdx();
		logger.info("방금 insert한 idx : " + idx);
		
		if(idx>0&&row>0) { // 완벽하게 성공했을 경우에만 file 저장 실행
			saveFile(files, idx);
		}
		
		
		
		return idx;
		
	}


	private void saveFile(MultipartFile[] files, int idx) {
		// 2. 글 저장 후 파일 저장
		/* a. 파일명 추출
		 * b. 새파일명 생성
		 * c. 파일 저장
		 * */
		try { // try가 로직 안에 있으면 3개 중 하나가 실패해도 나머지는 올라감 (이건 개발자의 판단)
			for (MultipartFile file : files) {
				String ori_filename = file.getOriginalFilename();
				String ext = ori_filename.substring(ori_filename.lastIndexOf("."));
				String new_filename = UUID.randomUUID()+ext;
				
				byte[] arr = file.getBytes();
				Path path = Paths.get("C:/upload/"+new_filename);
				Files.write(path, arr);
				// 3. 파일 저장이 되면 DB에 저장(files 테이블에 insert)
				bd_dao.fileWrite(new_filename, ori_filename, idx);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
}
