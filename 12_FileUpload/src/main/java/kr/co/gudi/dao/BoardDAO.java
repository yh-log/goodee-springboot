package kr.co.gudi.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

import kr.co.gudi.dto.BoardDTO;
import kr.co.gudi.dto.FileDTO;

@Mapper
public interface BoardDAO {

	List<BoardDTO> list();

	BoardDTO detail(int idx);

	int write(BoardDTO dto);

	int del(int idx);

	void upHit(int idx);

	int update(Map<String, String> params);

	void filewrite(int idx, String ori_filename, String new_filename);

	List<FileDTO> files(int idx);

	

}
