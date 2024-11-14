package kr.co.gudi.dao;

import org.apache.ibatis.annotations.Mapper;

import kr.co.gudi.dto.BoardDTO;
import kr.co.gudi.dto.FileDTO;

@Mapper
public interface BoardMapper {

	int submitPost(BoardDTO boardDto);

	void fileWrite(FileDTO img);

	/*
	 * void insertBoard(BoardDTO board);
	 * 
	 * void insertFile(FileDTO fileDTO);
	 */
}

