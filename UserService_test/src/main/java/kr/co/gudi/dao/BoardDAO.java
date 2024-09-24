package kr.co.gudi.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

import kr.co.gudi.dto.BoardDTO;
import kr.co.gudi.dto.FileDTO;

@Mapper
public interface BoardDAO {

	List<BoardDTO> boardlist();

	BoardDTO detail(int idx);

	int upload(BoardDTO dto);

	int boarddel(int idx);
	
	int uphit(int idx);

	int update(Map<String, String> param);

	int fileUpload(String new_filename, String ori_filename, int idx);

	List<FileDTO> fileList(int idx);
	
	

}
