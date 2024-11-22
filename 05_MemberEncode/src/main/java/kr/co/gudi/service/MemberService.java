package kr.co.gudi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.gudi.dao.MemberDAO;
import kr.co.gudi.dto.MemberDTO;

@Service
public class MemberService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired MemberDAO member_dao;
	public String login(String id, String pw) {
		
		int row = member_dao.login(id, pw);
		if(row>0) {
			return id + "님 로그인에 성공했습니다.";
		}
		
		return "로그인에 실패했습니다.";
	}
	public int join(MemberDTO memberDto) {
		return member_dao.join(memberDto);
	}

}
