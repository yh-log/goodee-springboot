package kr.co.gudi.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.co.gudi.dao.BoardMapper;
import kr.co.gudi.dto.BoardDTO;
import kr.co.gudi.dto.FileDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired BoardMapper boardMapper;

	public Map<String, Object> saveFile(MultipartFile file) throws IllegalStateException, IOException {
		
		logger.info("file : " + file.getOriginalFilename());
		
		Map<String, Object> resultFileMap = new HashMap<>();
		
		// 파일 경로
		String uploadDir = "C:/uploadTemporary/";
		File dir = new File(uploadDir);
		
		if(!dir.exists()) {
			dir.mkdirs();
		}
		
		String ori_filename = file.getOriginalFilename();
		String ext = ori_filename.substring(ori_filename.lastIndexOf("."));
		String new_filename = UUID.randomUUID().toString() + ext;
		
		resultFileMap.put("ori_filename", ori_filename);
		resultFileMap.put("new_filename", "/photo-temp/" + new_filename);
		
		File targetFile = new File(uploadDir + new_filename);
		file.transferTo(targetFile);
		
		return resultFileMap;
	}

	public boolean sumbitPost(BoardDTO boardDto) {
		
		boolean success = false;
		
		int row = boardMapper.submitPost(boardDto);
		
		if(row>0) {
			int board_idx = boardDto.getIdx();
				
			List<FileDTO> imgs = boardDto.getImgs();
			if(imgs.size() > 0) {
				for (FileDTO img : imgs) {
	//					String cleanedImg = img.replaceAll("[\\[\\]\"]", "");
//					String new_filename = img.getNew_filename();

					img.setIdx(board_idx);
					fileWrite(img);
					
				}
			}
			
			
			
//			fileWrite(board_idx, boardDto.getImgs());
			success= true;
		}
		
		return success;
		
	}

	private void fileWrite(FileDTO img) {
		
		logger.info("파일까지 가는 경로 가능하냐!!");
		
		// 복사할 파일
		File srcFile = new File("C:/uploadTemporary/"+img.getNew_filename());
		// 목적지 (파일명을 그대로 복사하기 위해 파일명 붙여줌
		File descDir = new File("C:/upload/"+img.getNew_filename());
		
		try {
			// Directory 는 폴더 복사 , File 은 파일 복사
			FileUtils.copyFile(srcFile, descDir);
			logger.info("복사 되었니?");
			
			
			
			// todo - 요게 없어서 그래요~ㅋㅋㅋ
			boardMapper.fileWrite(img);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
		
	}


}
