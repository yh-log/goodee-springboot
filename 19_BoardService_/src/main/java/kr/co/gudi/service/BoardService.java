package kr.co.gudi.service;

import java.io.File;
import java.io.IOException;
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
	@Autowired BoardDAO dao;
		
	public Map<String, Object> list(int page, int cnt) {
		
		int limit = cnt;
		int offset = (page-1) * cnt;
		int totalpage = dao.count(cnt);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("totalPages", totalpage);
		result.put("currPage", page);
		result.put("list",dao.list(limit,offset));
		
		return result;
	}

	@Transactional
	public void detail(String idx, Model model) {
		
		logger.info("detail" + idx);
		
		BoardDTO dto = dao.detail(idx);
		dao.bHit(idx);
		List<FilesDTO> files = dao.files(idx);
		
		model.addAttribute("info", dto);
		model.addAttribute("files", files);
		
	}

	public int write(Map<String, String> param, MultipartFile[] files) {
		
		BoardDTO dto = new BoardDTO();
		dto.setUser_name(param.get("user_name"));
		dto.setSubject(param.get("subject"));
		dto.setContent(param.get("content"));
		
		int row = dao.write(dto);
		logger.info("inset 한 수 : " + row);
		
		int idx = dto.getIdx();
		logger.info("방금 insert한 idx : " + idx);
		
		if(idx>0&&row>0) { 
			saveFile(files, idx);
		}
		
		return idx;
	}

	private void saveFile(MultipartFile[] files, int idx) {
		try { 
			for (MultipartFile file : files) {
				if(!file.isEmpty()) {
					String ori_filename = file.getOriginalFilename();
					String ext = ori_filename.substring(ori_filename.lastIndexOf("."));
					String new_filename = UUID.randomUUID()+ext;
					
					byte[] arr = file.getBytes();
					Path path = Paths.get("C:/upload/"+new_filename);
					Files.write(path, arr);
					dao.fileWrite(new_filename, ori_filename, idx);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void del(String idx) {
		List<FilesDTO> fileList = dao.files(idx);		
		if(dao.del(idx)>0) {
			for (FilesDTO dto : fileList) {
				File file = new File("C:/upload/"+dto.getNew_filename());
				if(file.exists()) {
					boolean success = file.delete();
					logger.info(dto.getNew_filename()+" : "+success);
				}
			}
		}
	}

//	public String write(Map<String, String> param, MultipartFile[] files) {
//
//		String page = "redirect:/list.do";
//		
//		BoardDTO dto = new BoardDTO();
//		dto.setUser_name(param.get("user_name"));
//		dto.setSubject(param.get("subject"));
//		dto.setContent(param.get("content"));
//		
//		int idx =  dao.write(dto);
//		
//		if(idx > 0) {
//			fileSave(files, idx);
//			page = "redirect:/detail?idx="+idx;
//		}
//		
//		return page;
//	}
//
//	private void fileSave(MultipartFile[] files, int idx) {
//		
//		try {
//			for (MultipartFile file : files) {
//				if(!file.isEmpty()) {
//					String ori_filename = file.getOriginalFilename();
//					String ext = ori_filename.substring(ori_filename.lastIndexOf("."));
//					String new_filename = UUID.randomUUID() + ext;
//					
//					byte[] arr = file.getBytes();
//					Path path = Paths.get("C:/upload/"+new_filename);
//					Files.write(path, arr);
//					dao.fileWrite(new_filename, ori_filename, idx);
//				}	
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//	}
	
}
