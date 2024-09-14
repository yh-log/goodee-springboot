package kr.co.gudi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.gudi.mapper.MemberMapper;

@Service
public class MemberService {
	
	@Autowired MemberMapper member_mapper;
	
	Logger logger = LoggerFactory.getLogger(getClass());

	public boolean login(String id, String pw) {		
		int cnt = member_mapper.login(id,pw);
		logger.info("조건을 만족하는 회원 수 : "+cnt);		
		return cnt>0 ? true : false;
	}

}
