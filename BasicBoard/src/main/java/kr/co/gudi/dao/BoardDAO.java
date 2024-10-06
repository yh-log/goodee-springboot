package kr.co.gudi.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.gudi.dto.BoardDTO;
import kr.co.gudi.dto.CommentDTO;
import kr.co.gudi.dto.FileDTO;

@Mapper
public interface BoardDAO {

	int totalPage(int limit);

	List<BoardDTO> boardList(int limit, int offset);

	int write(BoardDTO boardDto);

	int fileWrite(int idx, String ori_filename, String new_filename, String type);


	int bHitUp(int idx);
	
	BoardDTO boardDetail(int idx);
	
	List<FileDTO> fileDetail(int idx);

	int addComment(CommentDTO commentDto);

	List<CommentDTO> commentList(CommentDTO commentDto);

	int deleteFile(FileDTO fileDto);

	int boardUpdate(BoardDTO boardDto);

	int delete(int idx);

}
