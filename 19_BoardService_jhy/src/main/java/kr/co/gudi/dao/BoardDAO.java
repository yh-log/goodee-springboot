package kr.co.gudi.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

import kr.co.gudi.dto.BoardDTO;
import kr.co.gudi.dto.FilesDTO;

@Mapper
public interface BoardDAO {

	List<BoardDTO> list(int limit, int offset);

	int count(int cnt);

	int fileWrite(int idx, String ori_filename, String new_filename);

	int write(BoardDTO dto);

	BoardDTO detail(String idx);

	int bhit(String idx);

	List<FilesDTO> files(String idx);

	int del(String idx);

	String getFilfName(String fileName);

	int update(Map<String, String> param);

	int fileDel(String idx);






}
