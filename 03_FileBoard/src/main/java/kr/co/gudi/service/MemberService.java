package kr.co.gudi.service;

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
	public boolean login(Map<String, String> param) {
		if(member_dao.login(param)>0) {
			return true;
		}
		return false;
	}
	public int join(MemberDTO memberDto) {
		return member_dao.join(memberDto);
	}
}
