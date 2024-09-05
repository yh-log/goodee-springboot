package ko.co.gudi.service;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ko.co.gudi.dao.MainDAO;

public class MainService {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	public String dbConnect() {
		
		MainDAO dao = new MainDAO();
		
		boolean success = dao.dbConnect();
		String msg = "DB접속에 실패했습니다.";
  		
		if(success = true) {
			msg = "DB접속에 성공했습니다.";
		} 
		return msg;
	}

	public String stmt() throws SQLException {
		
		MainDAO dao = new MainDAO();
		String msg = "테이블 생성에 실패했습니다.";
		if(dao.stmt()) {
			msg = "테이블 생성에 성공했습니다.";
		}
		
		return msg;
	}

	public String pstmt() throws SQLException{
		MainDAO dao = new MainDAO();
		
		String msg = "insert에 실패 했습니다.";
		int row = dao.pstmt();
		
		if(row > 0) {
			msg = "insert에" + row + "성공 했습니다.";
		}
		
		return msg;
	}

	public String resultSet() throws SQLException {
		
		logger.info("slelect service 요청!");
		String msg = "데이터 호출에 실패 했습니다.";
		
		MainDAO dao = new MainDAO();
		int cnt = dao.resultSet();
		if(cnt > 0) {
			msg = "데이터 호출에 성공 했습니다.";
		}
		
		return msg;
	}
}
