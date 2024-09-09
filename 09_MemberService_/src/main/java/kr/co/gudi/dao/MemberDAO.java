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

	int del(String id);
	
//	HikariPool-1 : Pool 이라는 개념(하나씩 만들어서 해야하는데, 하나씩 만드는게 비효율적이라 미리 만들어두고 기다렸다가 빌려주고 받는 개념)
//	→ SpringBoot에서 지원하는 기능
//	: Spring Boot 에서 제공해주는 커넥션 풀 이름
//		*커넥션 풀 : 커넥션을 미리 만들어두고 빌려주고 받는 개념 (thread pool 처럼)
	

}
