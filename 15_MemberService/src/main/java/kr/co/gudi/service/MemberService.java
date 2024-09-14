package kr.co.gudi.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.gudi.mapper.MemberMapper;

@Service
public class MemberService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired MemberMapper member_mapper;
	public boolean login(String id, String pw) {
		
		int cnt = member_mapper.login(id,pw);
		logger.info("조건을 만족하는 회원 수 : " + cnt);
		
		return cnt>0? true : false;
	}
	
	public String join(Map<String, String> params) {
		
		int row = member_mapper.join(params);
		
		return row>0? "회원가입에 성공했습니다" : "회원가입에 실패했습니다.";
	}

}
