package kr.co.gudi.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.gudi.dto.BoardDTO;

@Mapper
public interface BoardDAO {

	List<BoardDTO> list();

	BoardDTO detail(int idx);

	boolean write(Map<String, String> params);

	void del(int idx);

	void upHit(int idx);

	int update(Map<String, String> params);

	

}
