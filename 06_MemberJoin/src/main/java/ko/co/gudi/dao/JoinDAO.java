package ko.co.gudi.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JoinDAO {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	private Connection conn = null;
//	PreparedStatement ps = null; // 계속 사용한다면 Connection 처럼 멤버변수로 선언해줘도 된다.
	
	public JoinDAO() { // DB 연결 생성자
		String id = "web_user";
		String pw = "pass";
		String url = "jdbc:mariadb://localhost:3306/mydb";
		String dirver = "org.mariadb.jdbc.Driver";
		
		try {
			Class.forName(dirver); // DB랑 java랑 연결
			conn = DriverManager.getConnection(url, id, pw); 
			logger.info("Connection : {}", conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

	// 테이블은 생성이 되어 있으므로, insert 문만 실행
	public int join(HttpServletRequest req) throws SQLException {
		logger.info("insert DAO 요청");
		
		int row = 0;
		
		String id = req.getParameter("id");
		String pw = req.getParameter("pw");
		String name = req.getParameter("name");
		String age = req.getParameter("age");
		String gender = req.getParameter("gender");
		String email = req.getParameter("email");		
		
		int su = Integer.parseInt(age);

		String sql = "INSERT INTO member(ID,PW,NAME,AGE,GENDER,EMAIL)VALUES(?,?,?,?,?,?)";
		
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ps.setString(1, id);
		ps.setString(2, pw);
		ps.setString(3, name);
		ps.setInt(4, su);
		ps.setString(5, gender);
		ps.setString(6, email);
		
		row = ps.executeUpdate();
		
		ps.close();
		conn.close();
		
		return row;
	}
}
