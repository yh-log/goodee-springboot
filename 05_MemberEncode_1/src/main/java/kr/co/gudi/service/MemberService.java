package kr.co.gudi.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import kr.co.gudi.dao.MemberDAO;

@Service
public class MemberService {

	private final MemberDAO memberDAO;
	
	public MemberService(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}


	public int join(Map<String, String> param) {
		
		
		return memberDAO.join(param);
	}


	public int login(String id, String pw) {
		
		
		return memberDAO.login(id, pw);
	}


	public String pwCheck(String id) {
		return memberDAO.pwCheck(id);
	}


}
