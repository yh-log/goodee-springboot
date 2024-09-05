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
	Connection conn = null;
//	PreparedStatement ps = null;
	
	public JoinDAO() {
		
		logger.info("DB 접속 완료");
		
		String id = "web_user";
		String pw = "pass";
		String url = "jdbc:mariadb://localhost:3306/mydb";
		String driver = "org.mariadb.jdbc.Driver";
	
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			logger.info("conn : {}", conn);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("DB 접속 실패");
		}
		
	}

	public int join(HttpServletRequest req) throws SQLException {
		
		logger.info("회원가입 insert DAO");
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
