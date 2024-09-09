package ko.co.gudi.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.internal.compiler.classfmt.NonNullDefaultAwareTypeAnnotationWalker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ko.co.gudi.dao.UserDAO;
import ko.co.gudi.dto.UserDTO;

public class UserService {
	Logger logger = LoggerFactory.getLogger(getClass());

	public boolean login(String id, String pw) {
		logger.info("login service");
		
		UserDAO dao = new UserDAO();
		boolean result = false;
		
		try {
			if(dao.login(id, pw)) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public boolean join(Map<String, String> map) {
		logger.info("회원가입 요청 service");

		boolean result = false;
		UserDAO dao = new UserDAO();
		try {
			if(dao.join(map) > 0) {
				result = true;
			}
		} catch (SQLException e) {
			logger.info("회원가입 실패 service");
			e.printStackTrace();
			
		}
		return result;
	}

	public List<UserDTO> list() {

		logger.info("list 요청 service");
		UserDAO dao = new UserDAO();
		
		try {
			return dao.list();
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<UserDTO>();
		}
		
		
	}

	public boolean del(String id) {
		
		UserDAO dao = new UserDAO();
		boolean result = false;
		
		try {
			if(dao.del(id) == 1) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.info("삭제 실패 service");
		}
		
		return result;
	}

	public UserDTO detail(String id) throws SQLException {
		
		logger.info("서비스는 실행됨??");
		logger.info("service id : {}", id);
		UserDAO dao = new UserDAO();
		UserDTO dto = dao.detail(id);
		
		return dto;
	}

	public UserDTO change(Map<String, String> map) throws SQLException {
		
		UserDAO dao = new UserDAO();
		
		UserDTO dto = dao.change(map);
		
		return dto;
	}



}
