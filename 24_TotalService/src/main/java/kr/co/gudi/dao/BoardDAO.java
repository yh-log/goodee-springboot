package kr.co.gudi.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.gudi.dto.BoardDTO;

@Mapper
public interface BoardDAO {

	int totalPage(int limit);

	// list 라는 Map에 key 안에 list 타입의 BoardDTO 형태로 담긴다.
	List<BoardDTO> list(int limit, int offset);

}
