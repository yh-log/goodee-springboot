package kr.co.gudi.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.gudi.mapper.Membermapper;


@Service
public class MemberService {
@Autowired Membermapper member_mapper;
Logger logger = LoggerFactory.getLogger(getClass());



	public boolean login(String id, String pw) {
		logger.info("서비스가 받아온 값 : " + id + "," + pw);
		boolean success = false;
		
		if(member_mapper.login(id,pw)>0){
			success = true;
		}
		return success;
		
	}



	public boolean join(Map<String, String> params) {
		logger.info("서비스가 받아온 값 : {}",params);
		boolean success = false;
		if(member_mapper.join(params)>0) {
			success = true;
		}
		return success;
	}


	public int overlay(String id) {
		logger.info("overlay check2:"+id);
		return member_mapper.overlay(id);
	}



	public List<HashMap<String, Object>> list() {
		return member_mapper.list();
	}
}
