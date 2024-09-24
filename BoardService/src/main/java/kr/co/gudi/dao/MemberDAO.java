package kr.co.gudi.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.gudi.dto.MemberDTO;

@Mapper
public interface MemberDAO {

	int join(Map<String, String> params);

	int login(String id, String pw);

	List<MemberDTO> member_list();

	int member_del(String id);

	int overlay(String id);

}
