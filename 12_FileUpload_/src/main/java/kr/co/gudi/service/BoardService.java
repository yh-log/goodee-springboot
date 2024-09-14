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
	@Autowired BoardDAO boardDAO;

	public List<BoardDTO> list() {
		return boardDAO.list();
	}

	// 글이 작성되면 이미지가 저장되도록
	public void write(Map<String, String> param, MultipartFile[] files) {
	
		// insert 와 동시에 방금 넣은 key값을 가져오는 방법 (mybatis에서 제공해준다.) = generted key (무조건 제공해주는 기능!)
		// 몇가지 주의사항이 있다.
		// 주의사항 1. 파라미터가 DTO 여야 한다
		BoardDTO dto = new BoardDTO();
		dto.setUser_name(param.get("user_name"));
		dto.setSubject(param.get("subject"));
		dto.setContent(param.get("content"));
		// 주의사항 2. mybatis에서 설정을 해줘야 한다.
		
		if(boardDAO.write(dto)>0) { // 글 쓰기
			
			// 주의사항 3. insert 후 DTO에서 꺼내 쓸 것
			int idx = dto.getIdx();
			logger.info("방금 insert 한 idx 값 : " + idx);
			
			
			// 파일 저장 (nio 방식 사용)
			for (MultipartFile file : files) {
				try {
					/*이름 변경*/
					// 2. 기존 파일명 확보 (image.png)
					String fileName = file.getOriginalFilename();
					// . < 등은 그냥 사용할 수 없다. (그냥 사용하면 인식x)
//				String ext = fileName.split("\\.")[1];
					
					String ext = fileName.substring(fileName.lastIndexOf("."));
					
					logger.info(ext);
					
					// 1. 변경할 이름 생성(13DF246ABD) → 확장자도 바뀌기 때문에 문제가 생긴다. (해쉬화, 암호화 한다고도 한다.)
					String newFileName = UUID.randomUUID().toString() + ext; //UUID 는 UUID라는 객체를 반화하기 때문에 문자로 변환해줘야 한다.
					logger.info(fileName + "->" + newFileName);
					
					// 1. 바이트 추출
					byte[] arr = file.getBytes();
					// 2. 저장 경로 지정
					Path path = Paths.get("C:/upload/"+newFileName);
					// 3. 파일 쓰기
					Files.write(path, arr);
					
					//4. 파일의 저장 정보를 DB에 INSERT 해야 한다. (쓰기까지 다 했으니까)
					boardDAO.fileWrite(idx,fileName, newFileName);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
			}
			
		}
	}
	@Transactional
	public void detail(String idx, Model model) {
		boardDAO.uphit(idx);
		
		BoardDTO bbs = boardDAO.detail(idx);
		List<FileDTO> files = boardDAO.files(idx);
		
		model.addAttribute("info", bbs);
		model.addAttribute("files", files);
	}

	public void del(String idx) {
		// bbs 삭제 -> files 도 삭제 -> 파일 삭제는 어떻게? (이름도 지워짐)
		// 특정 파일을 식별할 수 있는 key(idx) 를 삭제 전에 얻어낸다. -> bbs 삭제 -> file 삭제
		
		// 1. idx를 가지고 files 의 new_filename을 알아내야 한다. (DB가 지워진 후에 컴퓨터에 있는 사진을 삭제하기 위함)
		List<FileDTO> fileList = boardDAO.files(idx); // 이미 있던 메서드 활용
		
		// 2. bbs에 있는 데이터 삭제 -> files 데이터도 삭제됨
		int row = boardDAO.del(idx);
		logger.info("delete row : " + row);
		if(row>0) { // 3. DB에서 삭제 성공 -> 컴퓨터에 있는 file 삭제
			for (FileDTO dto : fileList) { // 리스트로 받아왔기때문에 for문으로 꺼내주기
				File file = new File("C:/upload/" + dto.getNew_filename()); // File 객체 활용
				if(file.exists()) {
					boolean success = file.delete();
					logger.info(dto.getNew_filename() + " delete : " + success);
				}
			}
		}
	}

	public ResponseEntity<Resource> download(String new_filename, String ori_filename) {
		
		// Header (보낼 컨텐트 타입이 들어가야 한다, 형태(문자|파일), 파일일 경우 파일명이 들어가야 한다.)
		HttpHeaders header = new HttpHeaders();
		
		// 본문 = FileSystem을 이용해 특정 위치의 파일을 가져옴
		Resource res = new FileSystemResource("C:/upload/" + new_filename);
		
		try {
			// content-type : image, text, binary ...
			// application/octet-stream = binary
			header.add("content-type", "application/octet-stream");
			// 한글 파일명은 깨지기 때문에 특수한 처리를 해줘야 한다.
			String encode_name = URLEncoder.encode(ori_filename, "UTF-8");
			// content-Disposition : 형태(문자 inline |파일 attachment) 지정 -> 파일일 경우 내가 저장할파일명을 지정
			header.add("content-Disposition", "attachment;filename=\"" + encode_name +"\""); // 파일 name은 "" 로 감싸줘야 하기 때문에 이스케이프 문자 사용
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		// body(본문) header(사전정보) 상태값(200,400,500.... 에러) 에러를 보내면 에러라고 뿌리기 때문에 잘 안쓴다
		return new ResponseEntity<Resource>(res, header, HttpStatus.OK); // 아무일 없을 때 ok(정상) 이라고 하기로 함
	}
}
