package kr.co.gudi.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.gudi.dto.BoardDTO;
import kr.co.gudi.dto.FileDTO;


@Mapper
public interface BoardDAO {

	List<BoardDTO> list();

	int write(BoardDTO dto);

	BoardDTO detail(String idx);

	int uphit(String idx);

	int fileWrite(int i, String fileName, String newFileName);

	List<FileDTO> files(String idx);

}
