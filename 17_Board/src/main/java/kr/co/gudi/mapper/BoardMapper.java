package kr.co.gudi.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.gudi.vo.BoardVO;

@Mapper
public interface BoardMapper {

	List<BoardVO> list();

	BoardVO detail(int idx);

	int write(BoardVO vo);

	int upHit(int idx);

	int fileWrite(String new_filename, String ori_filename, int idx);

}
