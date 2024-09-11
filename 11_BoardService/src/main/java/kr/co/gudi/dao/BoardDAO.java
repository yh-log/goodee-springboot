package kr.co.gudi.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardDAO {

	boolean join(Map<String, String> param);
	
	String login(String id, String pw);
	
	

}
