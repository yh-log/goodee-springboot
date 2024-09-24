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
	
	
	public int login(String id, String pw) {
		return member_mapper.login(id,pw);
	}


	public boolean join(Map<String, String> params) {
		
		int row = member_mapper.join(params);
		
		return row>0? true : false;
	}


	public Object overlay(String id) {
		return member_mapper.overlay(id);
	}

}
