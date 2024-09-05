package ko.co.gudi.service;

import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ko.co.gudi.dao.JoinDAO;

public class JoinService {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	// String id, String pw, String name, String age, String gender, String email
	
	public String join(HttpServletRequest req) throws SQLException {
		
		logger.info("회원가입 service");	
		
		JoinDAO dao = new JoinDAO();
		int row = dao.join(req); // controller 로 부터 넘어온 request 정보를 다시 dao 에 넘겨준다.
		
		String msg = "회원가입에 실패했습니다.";
		
		if(row >0) {
			msg = "회원가입에 성공했습니다.";
		}
		
		return msg;
	}

}
