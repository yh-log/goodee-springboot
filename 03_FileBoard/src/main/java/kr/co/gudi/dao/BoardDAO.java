package kr.co.gudi.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.gudi.dto.BoardDTO;
import kr.co.gudi.dto.FileDTO;

@Mapper
public interface BoardDAO {

	List<BoardDTO> list();

	int boardDelete(int idx);

	int write(BoardDTO boardDto);

	List<BoardDTO> detail(int idx);

	int bHitUpdate(int idx);

	int fileWrite(BoardDTO boardDto);


}
