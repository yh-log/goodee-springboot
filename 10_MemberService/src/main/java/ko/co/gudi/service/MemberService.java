package ko.co.gudi.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ko.co.gudi.dao.MemberDAO;
import ko.co.gudi.dto.MemberDTO;

@Service
public class MemberService {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired MemberDAO dao;

	public String join(Map<String, String> param) {
		logger.info("service param : {}", param);
		
		int row = dao.join(param);
		String msg = "회원가입에 실패했습니다.";
		
		if(row == 1) {
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

	public List<MemberDTO> list() {
		return dao.list();
	}

	public MemberDTO detail(String id) {
		return dao.detail(id);
	}

	public void del(String id) {
		dao.del(id);
	}
	
}
