package ko.co.gudi.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ko.co.gudi.dao.MemberDAO;
import ko.co.gudi.dto.MemberDTO;

public class MemberService {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	public boolean login(String id, String pw) {
		
		logger.info("로그인 요청 service");
		MemberDAO dao = new MemberDAO(); // DB 작업 처리를 위해 dao 객체 생성
		try {
			return dao.login(id,pw);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public String join(Map<String, String> param) throws SQLException {

		logger.info("회원가입 요청 : service");
		
		
		MemberDAO dao = new MemberDAO(); // DB에 접근하기 위해 dao 생성
		
		String msg = "회원가입에 실패했습니다.";
		
		if(dao.join(param)==1) { // insert 된 행이 1개가 나오면 성공한거니까 성공 문구 전달
			msg = "회원가입에 성공했습니다.";
		}
		return msg;
	}

	public List<MemberDTO> list() {
		
		MemberDAO dao = new MemberDAO();
		
		try {
			return dao.list();
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<MemberDTO>(); // 여기서 객체로 만들어서 넘겨주는 거니까 null 이 안된다.
		}
	}

	public MemberDTO detail(String id) {
		
		logger.info("detail 정보 보기 service");
		MemberDAO dao = new MemberDAO();
		
		MemberDTO dto = new MemberDTO();
		try {
			dto = dao.detail(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return dto;
	}

	public String del(String id) {
		
		logger.info("삭제요청 service");
		
		MemberDAO dao = new MemberDAO();
		String msg = "삭제 실패";
		
		try {
			if(dao.del(id)==1) {
				msg = "삭제 성공";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return msg;
		
	}


}
