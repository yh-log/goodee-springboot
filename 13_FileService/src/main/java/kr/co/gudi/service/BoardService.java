package kr.co.gudi.service;

import java.io.File;
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

import kr.co.gudi.mapper.BoardMapper;
import kr.co.gudi.vo.BoardVO;
import kr.co.gudi.vo.FileVO;

@Service
public class BoardService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired BoardMapper board_mapper;
	public List<BoardVO> list() {
		return board_mapper.list();
	}
	
	public String write(MultipartFile[] files, Map<String, String> params) {
		
		String page = "redirect:/list.do";
		
		BoardVO vo = new BoardVO();
		vo.setUser_name(params.get("user_name"));
		vo.setSubject(params.get("subject"));
		vo.setContent(params.get("content"));

		board_mapper.write(vo); // 글쓰기
		int idx = vo.getIdx();
		if(idx>0) {
			fileSave(files, idx);
			page = "redirect:/detail.do?idx="+idx;
		}
		
		return page;
	}
	void fileSave(MultipartFile[] files, int idx) {
		for (MultipartFile file : files) {
			// 1. 파일명 추출
			String ori_filename = file.getOriginalFilename();
			// 2. 기존 파일의 확장자만 분리하여 새파일명에 추가
			String ext = ori_filename.substring(ori_filename.lastIndexOf("."));
			// 3. 새파일명 생성
			String new_filename = UUID.randomUUID().toString() + ext;
			
			try {
				// 4. 파일 저장
				byte[] arr = file.getBytes();
				Path path = Paths.get("C:/upload/"+new_filename);
				Files.write(path, arr);
				
				// 5. 저장 내용 files 테이블에 insert
				board_mapper.fileWrite(new_filename, ori_filename, idx);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}

	@Transactional
	public void detail(String idx, Model model) {
		// 1. upHit
		board_mapper.bHit(idx);
		
		// 2. bbs 데이터 가져오기
		BoardVO vo = board_mapper.detail(idx);
		
		// 3. fileList 가져오기
		List<FileVO> files = board_mapper.fileList(idx);
		
		model.addAttribute("info", vo);
		model.addAttribute("files", files);
		
	}

	public void del(String idx) {
		List<FileVO> fileList = board_mapper.fileList(idx); // 해당 게시글에 idx 값 가져오기
		
		int row = board_mapper.del(idx);
		if(row > 0) {
			for (FileVO vo : fileList) {
				File file = new File("C:/upload/" + vo.getNew_filename());
				if(file.exists()) {
					boolean success = file.delete();
				}
			}
		}
	}

}
