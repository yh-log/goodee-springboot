package kr.co.gudi.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.gudi.dto.BoardDTO;
import kr.co.gudi.dto.FilesDTO;

@Mapper
public interface BoardDAO {

	List<BoardDTO> list(int limit, int offset);

	int count(int cnt);

	BoardDTO detail(String idx);

	int bHit(String idx);

	List<FilesDTO> files(String idx);

	int write(BoardDTO dto);

	int fileWrite(String new_filename, String ori_filename, int idx);

	int del(String idx);

}
