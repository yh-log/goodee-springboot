package kr.co.gudi.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.gudi.dto.MemberDTO;

@Mapper
public interface MemberDAO {

	int overlay(String id);

	int join(Map<String, String> param);

	int login(String id, String pw);

	int totalPage(int limit);

	List<MemberDTO> list(int limit, int offset);

	MemberDTO detail(String id);

}
