package kr.co.gudi.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.co.gudi.dao.BoardDAO;
import kr.co.gudi.dto.BoardDTO;
import kr.co.gudi.dto.FileDTO;

@Service
public class BoardService {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired BoardDAO board_dao;
	
	public List<BoardDTO> list() {
		return board_dao.list();
	}

	public boolean boardDelete(int idx) {
		if(board_dao.boardDelete(idx) > 0) {
			return true;
		}
		return false;
	}

	public int write(BoardDTO boardDto, MultipartFile[] files) {
		int idx = board_dao.write(boardDto);
		logger.info("idx 야 ==> " + idx);
		if(idx > 0) {
			fileUpload(files, boardDto);
			return idx;
		}
		return 0;
	}

	private void fileUpload(MultipartFile[] files, BoardDTO boardDto) {
		if(files.length > 0) {
			for (MultipartFile file : files) {
				String ori_filename = file.getOriginalFilename();
				String ext = file.getOriginalFilename().substring(ori_filename.lastIndexOf("."));
				String new_filename = UUID.randomUUID() + ext;
				
				boardDto.setOri_filename(ori_filename);
				boardDto.setNew_filename(new_filename);
				
				try {
					byte[] arr = file.getBytes();
					Path path = Paths.get("C:/upload/"+new_filename);
					Files.write(path, arr);
					board_dao.fileWrite(boardDto);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
	}

	public List<BoardDTO> detail(int idx) {
		
		int hrow = board_dao.bHitUpdate(idx);
		logger.info("조회수up => " + hrow);
		return board_dao.detail(idx);
	}

}
