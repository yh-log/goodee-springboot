package kr.co.gudi.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.gudi.dto.MemberDTO;

@Mapper
public interface MemberDAO {

	int login(MemberDTO memberDto);

	int overlay(String id);

	int join(MemberDTO memberDto);

	int totalPage(int cnt);

	List<MemberDTO> memberList(int limit, int offset);

	MemberDTO memberDetail(String id);

	int update(Map<String, String> param);

	int delete(String id);

}
