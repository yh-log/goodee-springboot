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

import kr.co.gudi.mapper.BoardMapper;
import kr.co.gudi.vo.BoardVO;
import kr.co.gudi.vo.FileVO;

@Service
public class BoardSedrvice {
	
	@Autowired BoardMapper board_mapper;
	
	Logger logger = LoggerFactory.getLogger(getClass());

	public List<BoardVO> list() {
		return board_mapper.list();
	}

	public String write(MultipartFile[] files, Map<String, String> params) {

		String page = "redirect:/list.do";		
		BoardVO vo = new BoardVO();
		vo.setUser_name(params.get("user_name"));
		vo.setSubject(params.get("subject"));
		vo.setContent(params.get("content"));		
		
		board_mapper.write(vo);		// 글쓰기
		int idx = vo.getIdx(); //방금 쓴 글의 번호 추출		
		if(idx>0) {//파일 업로드 작업수행
			fileSave(files, idx);
			page = "redirect:/detail.do?idx="+idx;
		}
				
		return page;
	}

	void fileSave(MultipartFile[] files, int idx) {		
		for (MultipartFile file : files) {
			// 1. 파일명 추출
			String ori_filename = file.getOriginalFilename();			
			// 2. 기존 파일의 확장자만 분리
			String ext = ori_filename.substring(ori_filename.lastIndexOf("."));
			// 3. 새파일명 생성			
			String new_filename = UUID.randomUUID()+ext;			
			
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
		// 1. fileList 가져오기
		List<FileVO> fileList = board_mapper.fileList(idx);		
		// 2. bbs 삭제
		if(board_mapper.del(idx)>0) {
			// 3. 파일 삭제
			for (FileVO vo : fileList) {
				File file = new File("C:/upload/"+vo.getNew_filename());
				if(file.exists()) {
					boolean success = file.delete();
					logger.info(vo.getNew_filename()+" : "+success);
				}
			}
		}
	}

	public ResponseEntity<Resource> download(String new_filename, String ori_filename) {
		//body
		Resource res = new FileSystemResource("C:/upload/"+new_filename);
		
		//header
		HttpHeaders header = new HttpHeaders();		
		header.add("content-type", "application/octet-stream");
		try {
			String filename = URLEncoder.encode(ori_filename, "UTF-8");
			header.add("content-Disposition", "attechment;filename=\""+filename+"\"");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}		
		
		//body, header, status
		return new ResponseEntity<Resource>(res, header, HttpStatus.OK);
	}

}






















