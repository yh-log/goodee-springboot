package kr.co.gudi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.gudi.vo.BoardVO;

@Mapper
public interface BoardMapper {

	List<BoardVO> list();

	int write(BoardVO vo);

	int fileWrite(String new_fileName, String fileName, int idx);


}
