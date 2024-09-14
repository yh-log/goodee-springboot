package kr.co.gudi.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.gudi.dao.MemberDAO;

@Service
public class MemberService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired MemberDAO member_dao;
	
	public boolean join(Map<String, String> params) {
		boolean success = false;
		
		if(member_dao.join(params) > 0) {
			success = true;
		}
		
		return success;
	}
	
	public boolean login(String id, String pw) {
		boolean success = false;
	
		if(member_dao.login(id, pw) > 0) {
			success = true;
		}
		
		return success;
	}

	public Object overlay(String id) {
		return member_dao.overlay(id);
	}
	
}
