package kr.co.gudi.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

	// 로그인
	int login(String id, String pw);

	// 회원 가입
	int checkId(String id);
	int join(Map<String, String> params);

}