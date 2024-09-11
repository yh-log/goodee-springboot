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
		
		String msg = "회원가입에 실패했습니다.";
		
		if(member_dao.join(params) == 1) {
			msg = "회원가입에 성공했습니다.";
		}
		
		return msg;
	}

	public boolean login(String id, String pw) {
		return member_dao.login(id,pw) == null? false : true;
	}

	public List<MemberDTO> memberlist() {
		return member_dao.memberlist();
	}

	public String memberdel(String id) {
		
		String msg = "삭제에 실패했습니다.";
		int row = member_dao.memberdel(id);
		
		if(row == 1) {
			msg = "삭제에 성공했습니다.";
		}
		
		return msg;
	}

}
