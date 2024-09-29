package kr.co.gudi.service;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.co.gudi.dao.Boarddao;
import kr.co.gudi.dto.Bbsdto;
import kr.co.gudi.dto.Filedto;

@Service

public class Boardservice {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired Boarddao board_dao;
	@Value("${spring.servlet.multipart.location}") private String root;
		
	
	
	public List<Bbsdto> list() {
	
		// 일단 들어오는지 확인 
		logger.info("file root : " + root);
		return board_dao.list();
	}
		
	
		
	public int write(MultipartFile[] files,Map<String, String> param) {
		
		Bbsdto b_dto = new Bbsdto();
		b_dto.setContent(param.get("content"));
		b_dto.setSubject(param.get("subject"));
		b_dto.setUser_name(param.get("user_name"));
		
		if(board_dao.write(b_dto)>0) {
			int idx = b_dto.getIdx();
			fileSava(files, idx);
		}
		return 0;
	}

	private void fileSava(MultipartFile[] files, int idx) {
		for(MultipartFile file : files) {
			if(!file.isEmpty()) {
				try {
					String filename = file.getOriginalFilename();
					int pos = filename.lastIndexOf(".");
					
					if(pos>=0) {
						String ext = filename.substring(filename.lastIndexOf("."));
						String newfilename = UUID.randomUUID().toString()+ext;
						byte[] arr = file.getBytes();
						
						Path path = Paths.get(root+newfilename);
						
						Files.write(path, arr);
						board_dao.fileWrite(idx,filename,newfilename);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

	public int b_del(String idx) {// 파일 삭제
		return board_dao.b_del(idx);
		
	}


	@Transactional
	public Bbsdto b_detail(String idx) { // 조회수 올리기
		board_dao.bHit(idx);
		return board_dao.b_detail(idx);
	}



	public List<Filedto> files(String idx) {
		
		return board_dao.files(idx);
	}



	public ResponseEntity<Resource> download(String filename) {
		
		Resource resource = new FileSystemResource(root+filename);
		
		HttpHeaders header = new HttpHeaders();
		
		try {
			String enc_name = URLEncoder.encode(board_dao.getfilename(filename),"UTF-8");
			header.add("content-type", "application/octet-stream");
			header.add("content-Disposition", "attachment;filename=\""+enc_name+"\"");
		
		
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<Resource>(resource,header,200);
	}
	
	
	// 내장 톰켓을 사용했을 때..
	public ResponseEntity<Resource> getImg(String filename) {
		
		Resource resource = new FileSystemResource(root+filename);
		HttpHeaders header = new HttpHeaders();
		
		try {
			// 다운로드 받는게 아니니까 파일명, 등등 필요 없음
			// content type 이 octec=.. 가 아니라 img, 등이 필요하고 그 정보가 없으면
			//	▼ 이렇게 가져올 수 있다. (DB에서도 가져올 수 있다.) 저장해두면
			//		▶ DB를 쓰면 try - catch 를 안해도 된다.
			String type = Files.probeContentType(Paths.get(root+filename));
			header.add("content-type", type); // image/jpg, image/png 형태로 데이터 타입을 넣어줘야 한다. (DB에 저장하고 있으면 그 값을 넣으면 된다.)

		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<Resource>(resource,header,200);
	}



//			logger.info("empty : "+file.isEmpty());
//			logger.info("file name : " + file.getOriginalFilename());
//			logger.info("file type : "+ file.getContentType());
	public void update(Map<String, String> params, MultipartFile[] files) {
		
		// 1. 글 수정
		board_dao.update(params);
		
		// 2. 업로드할 파일이 있다면 업로드
		int idx = Integer.parseInt(params.get("idx"));
		fileSava(files, idx);
		
	}
	

}	