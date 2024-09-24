package kr.co.gudi.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.gudi.dto.BoardDTO;
import kr.co.gudi.dto.FileDTO;

@Mapper
public interface BoardDAO {

	List<BoardDTO> board_list();

	int write(BoardDTO dto);

	int fileWrite(String new_filename, String ori_filename, int idx);

	int upHit(int idx);

	BoardDTO detail(int idx);

	List<FileDTO> fileList(int idx);

	int board_del(int idx);

}
