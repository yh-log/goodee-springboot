package kr.co.gudi.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.gudi.dto.Bbsdto;
import kr.co.gudi.dto.Filedto;

@Mapper
public interface Boarddao {
	int write(Bbsdto b_dto);
	
	List<Bbsdto> list();
	
	int b_del(String idx);
	
	Bbsdto b_detail(String idx);
	
	int fileWrite(int idx, String filename, String newfilename);
	
	List<Filedto> files(String idx);
	
	String getfilename(String filename); // 본래이름 가져오기
	
	int bHit(String idx);
	
	int update(Map<String, String> params);
	
}
