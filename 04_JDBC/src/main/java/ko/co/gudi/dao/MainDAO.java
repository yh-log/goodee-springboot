package ko.co.gudi.dao;

import java.sql.Connection;
import java.sql.DriverManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainDAO {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	public boolean dbConnect() {
		
		// DB접속을 위해 준비할 것
		
		// 1. 접속에 필요한 정보 준비
		// 아이디, 비밀번호, 접속 url, class driver
		String id = "web_user";
		String pw = "pass";
		String url = "jdbc:mariadb://localhost:3306/mydb";
		String dirver = "org.mariadb.jdbc.Driver";
		
		Connection conn = null;
		boolean success = false; // try에 걸리면 true, catch에 덜리면 false 반환
		
		try {
			Class.forName(dirver); // 2. class driver 등록 (예외처리 필요 ClassNotFoundException)
			// 3. 드라이버 매니저에게 DB를 가져오도록 시킨다 (Connection)
			conn = DriverManager.getConnection(url, id, pw);
			logger.info("connection : {}", conn); // jsp에서는 그냥 입력하면 저 안에 있는 값을 가져오는데 자바에서 사용할 때에는 {} 을 넣어줘야 한다.
			// connection 을 가지고 DB에 해야할 일을 한다. (현재는 아무것도 할 일이 없다..)
			
			// 다 했으면 반납
			conn.close();
			success = true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return success;
	}
	
	
}
