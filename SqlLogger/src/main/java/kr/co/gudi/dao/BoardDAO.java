package kr.co.gudi.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.gudi.dto.BoardDTO;

@Mapper
public interface BoardDAO {

	List<BoardDTO> getList(int limit, int offset);

	int write(BoardDTO boardDto);

}
