package kr.co.gudi.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.gudi.dto.BoardDTO;
import kr.co.gudi.dto.FileDTO;

@Mapper
public interface BoardDAO {

	int totalPage(int limit);

	List<BoardDTO> list(int limit, int offset);

	int write(BoardDTO boardDto);

	int fileWrite(String ori_filename, String new_filename, int idx);

	int bHit(int idx);

	BoardDTO boardDetail(int idx);

	List<FileDTO> files(int idx);

	int boardUpdate(Map<String, Object> param);

	int boarddelete(int idx);


}
