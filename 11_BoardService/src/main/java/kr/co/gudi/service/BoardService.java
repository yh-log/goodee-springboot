package kr.co.gudi.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.gudi.dao.BoardDAO;

@Service
public class BoardService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired BoardDAO dao;

	public String join(Map<String, String> param) {
		
		String msg = "회원가입에 실패했습니다.";
		
		if(dao.join(param)) {
			msg = "회원가입에 성공했습니다.";
		}
		
		return msg;
	}

	public boolean login(String id, String pw) {
		String loginId = dao.login(id,pw);
		boolean success = false;
		
		if(loginId != null) {
			success = true;
		}
		return success;
	}

}
