package kr.co.gudi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.gudi.vo.BoardVO;
import kr.co.gudi.vo.FileVO;

@Mapper
public interface BoardMapper {

	List<BoardVO> list();

	int write(BoardVO vo);

	int fileWrite(String new_filename, String ori_filename, int idx);

	int bHit(String idx);

	BoardVO detail(String idx);

	List<FileVO> fileList(String idx);

	int del(String idx);

}
