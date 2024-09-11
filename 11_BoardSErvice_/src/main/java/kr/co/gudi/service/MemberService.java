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

	@Autowired
	MemberDAO dao;
	
	public String join(Map<String, String> param) {
		String msg = "회원가입에 실패 했습니다.";
		int row = dao.join(param);
		if (row == 1) {
			msg = "회원가입에 성공했습니다.";
		}
		
		return msg;
		
	}

	public boolean login(String id, String pw) {

		return dao.login(id,pw) == null? false : true;
	}
	
}
