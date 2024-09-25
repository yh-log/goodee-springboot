package kr.co.gudi.service;

import java.io.File;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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
		result.put("totalpages", totalpage);
		result.put("currPage", page);
		result.put("list",dao.list(limit,offset));
		
		return result;
	}

	public int write(Map<String, String> param, MultipartFile[] files) {
		BoardDTO dto = new BoardDTO();
		dto.setUser_name(param.get("user_name"));
		dto.setSubject(param.get("subject"));
		dto.setContent(param.get("content"));
		int row = dao.write(dto);
		int idx = dto.getIdx();
		if (idx>0 && row>0) {
			saveFile(files,idx);
		}
		return idx;
	}

	private void saveFile(MultipartFile[] files, int idx) {
	
			try {
				logger.info("file length : "+files.length);				
				for (MultipartFile file : files) {
					logger.info("file 비어있나? : "+file.isEmpty());					
					String ori_filename = file.getOriginalFilename();
					logger.info("파일명 : "+ori_filename);
					
					int pos = ori_filename.lastIndexOf(".");
					
					if(pos>=0) {
						String ext = "";
						// ori_filename.lastIndexOf(".") 수행시 -1이 나오는데 이걸로 서브스트링이 되나?					
						ext = ori_filename.substring(pos);						
						String new_filename = UUID.randomUUID()+ext;		
						Path path = Paths.get("C:/upload/"+new_filename);
						byte[] arr = file.getBytes();
						Files.write(path,arr);
						dao.fileWrite(idx,ori_filename,new_filename);						
					}					
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		
	}

	public void detail(String idx, Model model, boolean flag) {
		BoardDTO dto = dao.detail(idx);
		if(flag) {
			dao.bhit(idx);
		}		
		List<FilesDTO> files = dao.files(idx);
		model.addAttribute("info",dto);
		model.addAttribute("files",files);
	}

	public void del(String idx) {
		List<FilesDTO> fileList = dao.files(idx);
		int row = dao.del(idx);
		if (row>0) {
			for (FilesDTO DTO : fileList) {
				File file = new File("C:/upload/"+DTO.getNew_filename());
				if (file.exists()) {
					file.delete();
				}
			}
		}		
	}

	public ResponseEntity<Resource> download(String fileName) {
		
		Resource resource = new FileSystemResource("C:/upload/"+fileName);		
		HttpHeaders header = new HttpHeaders();
				
		try {
			String enc_name = URLEncoder.encode(dao.getFileName(fileName), "UTF-8");
			header.add("content-type", "application/octet-stream");
			header.add("content-Disposition", "attachment;filename=\""+enc_name+"\"");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	
		// body, header, 상태코드
		return new ResponseEntity<Resource>(resource,header,200);
	}

	public int updateajax(Map<String, String> param, MultipartFile[] files) {

		int row = dao.updateajax(param);
		int idx = Integer.parseInt(param.get("idx"));
		logger.info("받아온 idx 값 : "+idx);
		if (row > 0 && idx > 0) {
			saveFile(files,idx);
		}
		return idx;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
