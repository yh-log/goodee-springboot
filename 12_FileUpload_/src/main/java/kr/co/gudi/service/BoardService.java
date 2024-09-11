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
}
