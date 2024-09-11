package kr.co.gudi.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.gudi.dto.MemberDTO;

@Mapper
public interface MemberDAO {

	int join(Map<String, String> param);

	String login(String id, String pw);

	List<MemberDTO> list();

	MemberDTO detail(String id);

	void del(String id);



}
