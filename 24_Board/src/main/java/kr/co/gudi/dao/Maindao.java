package kr.co.gudi.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.gudi.dto.Bbsdto;
import kr.co.gudi.dto.Maindto;

@Mapper
public interface Maindao {

	int join(Map<String, String> params);

	int check(String id);
	int allCount(int cnt_);
	
	String login(Map<String, String> param);

	

	List<Maindto> u_list(int limit, int offset);

	Maindto u_detail(String id);

	int u_del(String id);

	int update(Map<String, String> param);

	

	
	
}
