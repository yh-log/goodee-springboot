package kr.co.gudi.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.xml.transform.Source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import kr.co.gudi.dto.CommentDTO;
import kr.co.gudi.dto.FileDTO;

@Service
public class BoardService {

	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired BoardDAO board_dao;
	
	@Value("${spring.servlet.multipart.location}") private String root;
	
	public Map<String, Object> boardList(int page, int cnt) {
		
		int limit = cnt;
		int offset = (page-1)*cnt;
		
		int totalPage = board_dao.totalPage(limit);
		logger.info("totalPage : "+totalPage);
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("totalPage", totalPage);
		resultMap.put("list", board_dao.boardList(limit,offset));
		
		return resultMap;
	}
	public int write(MultipartFile[] files, BoardDTO boardDto) {
		
		int row = board_dao.write(boardDto);
		int idx = boardDto.getIdx(); // 받아온 값으로부터 추출해야 한다!
		
		logger.info("insert 된 행 수 : "+row);
		logger.info("받아온 idx : "+idx);
		
		if(idx > 0) {
			fileSave(files, idx);
		}
		
		return idx;
	}
	
	private void fileSave(MultipartFile[] files, int idx) {
		for (MultipartFile file : files) {
			if(!file.isEmpty()) {
				
				String ori_filename = file.getOriginalFilename();
				String type = file.getContentType(); // file의 타입 가져오기
				String ext = ori_filename.substring(ori_filename.lastIndexOf("."));
				String new_filename = UUID.randomUUID()+ext;
				
				try {
					byte[] arr = file.getBytes();
					Path path = Paths.get(root+new_filename);
					Files.write(path, arr);
					board_dao.fileWrite(idx, ori_filename, new_filename, type);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}
	}
	
	@Transactional
	public void boardDetail(int idx, Model model) {
		
		int bHit = board_dao.bHitUp(idx);
		logger.info("조회수 up : "+bHit);
		
		model.addAttribute("detail", board_dao.boardDetail(idx));
		model.addAttribute("files", board_dao.fileDetail(idx));
		
	}
	public int addComment(CommentDTO commentDto) {
		
		int row = board_dao.addComment(commentDto);
		int com_idx = 0;
		
		if(row>0) {
			com_idx = commentDto.getCom_idx();
		}
		
		return com_idx;
	}
	
	public List<CommentDTO> commentList(CommentDTO commentDto) {
		
		return board_dao.commentList(commentDto);
	}
	public ResponseEntity<Resource> download(FileDTO fileDto) {
		
		HttpHeaders header = new HttpHeaders();
		Resource body = new FileSystemResource(root + fileDto.getNew_filename());
		
		// 파일이 해당 경로에 없을 경우!
		if(!body.exists()) {
			 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		header.add(HttpHeaders.CONTENT_TYPE, fileDto.getType());
		try {
			// 원래 파일 명 인코딩
			String encode = URLEncoder.encode(fileDto.getOri_filename(), "UTF-8");
			header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\""+encode+"\"");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(body, header, HttpStatus.OK);
	}
	
	public boolean deleteFile(FileDTO fileDto) {
		
		if(board_dao.deleteFile(fileDto) > 0) {
			return true;
		}
		return false;
	}
	
	public boolean boardUpdate(MultipartFile[] files, BoardDTO boardDto) {

		if(board_dao.boardUpdate(boardDto) > 0) {
			
			int idx = boardDto.getIdx();
			
			logger.info("updqte idx : " + idx);
			
			fileSave(files, idx);
			
			return true;
			
		}
		
		return false;
	}
	public void delete(int idx) {
		int row = board_dao.delete(idx);
		logger.info("delete row : " + row);
	}
	
}
