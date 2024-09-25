package kr.co.gudi.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.gudi.dto.MemberDTO;

@Mapper
public interface MemberDAO {

	int join(Map<String, String> param);

	int overlay(String id);

	int login(String id, String pw);

	int allCount(int cnt_);

	List<MemberDTO> memberList(int limit, int offset);

	int deleteMamber(String id);

	MemberDTO detailView(String id);

	int getMemberUpdate(String id, MemberDTO param);

}
