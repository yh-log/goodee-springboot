package kr.co.gudi.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

import kr.co.gudi.dto.BoardDTO;

@Mapper
public interface BoardDAO {

	List<BoardDTO> boardlist();

	BoardDTO detail(int idx);

	int upload(Map<String, String> params);

	int boarddel(int idx);
	
	int uphit(int idx);

	int update(Map<String, String> param);
	
	

}
