package kr.co.gudi.service;

import java.util.List;
import java.util.Map;

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
	public String join(Map<String, String> params) {
		
		logger.info("params : {}", params);
		
		int row = member_dao.join(params);
		String msg = "회원가입에 실패했습니다.";
		
		if(row > 0) {
			msg = "회원가입에 성공했습니다.";
		}
		return msg;
	}
	public boolean login(String id, String pw) {
		
		int row = member_dao.login(id,pw);
		
		return row>0? true : false;
	}
	public List<MemberDTO> member_list() {
		
		List<MemberDTO> list = member_dao.member_list();
		
		return  list;
	}
	public void member_del(String id) {
		int row = member_dao.member_del(id);
		logger.info("삭제된 행 수 : " + row);
	}
	public Object overlay(String id) {
		return member_dao.overlay(id);
	}

}
