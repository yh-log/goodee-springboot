package kr.co.gudi.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.gudi.dto.BoardDTO;
import kr.co.gudi.dto.FilesDTO;

@Mapper
public interface BoardDAO {

	List<BoardDTO> list();

	BoardDTO detail(String idx);

	int bhit(String idx);

	List<FilesDTO> files(String idx);

	int write(BoardDTO dto);

	int fileWrite(String ori_filename, String new_filename, int idx);
	
	
}
