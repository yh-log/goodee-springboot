package kr.co.gudi.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Membermapper {

	int login(String id, String pw);
	
	int join(Map<String, String> params);
	int overlay(String id);

	List<HashMap<String, Object>> list();
	
}
