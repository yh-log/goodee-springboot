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
	
	public Map<String, Object> boardList(int page, int cnt) {
		Map<String, Object> resultMap = new HashMap<>();
		
		int limit = cnt;
		int offset = (page-1)*cnt;
		
		int totalPage = board_dao.totalPage(limit);
		
		resultMap.put("totalPage", totalPage);
		resultMap.put("list", board_dao.list(limit, offset));
		
		return resultMap;
	}

	public boolean write(MultipartFile[] files, BoardDTO boardDto) {

		int row = board_dao.write(boardDto);
		
		logger.info("insert : " + row);
		
		if(row>0) {
			int idx = boardDto.getIdx();
			filesava(idx, files);
		}
		
		return false;
	}

	private void filesava(int idx, MultipartFile[] files) {
		
		if(files != null) {
			try {
				for (MultipartFile file : files) {
				
					String ori_filename = file.getOriginalFilename();
					String ext = ori_filename.substring(ori_filename.lastIndexOf("."));
					String new_filename = UUID.randomUUID() + ext;
				
					byte[] arr = file.getBytes();
					Path path = Paths.get("C:/upload/"+new_filename);
					Files.write(path, arr);
					board_dao.fileWrite(ori_filename, new_filename, idx);
				}
			} catch (IOException e) {
					e.printStackTrace();
			}
		}
	}

	@Transactional
	public boolean boardDetail(int idx, Model model) {
		
		logger.info("idx : "+idx);
		boolean success = false;
		
		//조회수
		board_dao.bHit(idx);
		
		BoardDTO dto = board_dao.boardDetail(idx);
		
		if(dto != null) {
			model.addAttribute("result", dto);
			List<FileDTO> files = board_dao.files(idx);
			if(files != null) {
				model.addAttribute("files", files);
			}
			success = true;
		}
		
		
		return success;
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

	public Object boardUpdate(Map<String, Object> param, MultipartFile[] files) {
		
		int row = board_dao.boardUpdate(param);
		String idxStr = String.valueOf(param.get("idx"));
		int idx = Integer.parseInt(idxStr);
		
		logger.info("받아온 idx : "+ idx);
		if(row>0 && idx>0) {
			if(files != null) {
				filesava(idx, files);
			}
		}
		
		
		return idx;
	}

	public void boarddelete(int idx) {
		int row = board_dao.boarddelete(idx);
		logger.info("delete : "+row);
	}


}
