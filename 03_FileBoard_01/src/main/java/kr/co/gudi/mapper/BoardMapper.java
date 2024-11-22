package kr.co.gudi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.gudi.vo.BoardVO;
import kr.co.gudi.vo.FilesVO;

@Mapper
public interface BoardMapper {

	// 게시글 목록
	List<BoardVO> list(int limit);
	
	// 상세 보기
	void upHit(String idx);
	BoardVO detail(String idx);
	List<FilesVO> files(String idx);

	// 게시글 쓰기
	int write(BoardVO vo);
	
	// 게시글 삭제
	int delete(String idx);

}