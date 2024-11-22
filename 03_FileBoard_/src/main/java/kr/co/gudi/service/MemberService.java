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
	
	// 로그인
	public boolean login(String id, String pw) {
		boolean success = false;
		
		if (member_mapper.login(id, pw) == 1) {
			success = true;
		}
		
		return success;
	}

	// 회원 가입
	public boolean checkId(String id) {
		boolean duplicated = true;
		
		if (member_mapper.checkId(id) == 0 && id != "") {
			duplicated = false;
		}
		
		return duplicated;
	}

	public boolean join(Map<String, String> params) {
		boolean success = false;
		
		if (member_mapper.join(params) == 1) {
			success = true;
		}
		
		return success;
	}

}