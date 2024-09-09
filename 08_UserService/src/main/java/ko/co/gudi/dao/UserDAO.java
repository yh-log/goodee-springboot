package ko.co.gudi.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ko.co.gudi.dto.UserDTO;

public class UserDAO {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	public UserDAO() {
		
		String id = "web_user";
		String pw = "pass";
		String url = "jdbc:mariadb://localhost:3306/mydb";
		String driver = "org.mariadb.jdbc.Driver";
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			logger.info("DB 접속 성공");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("DB 접속 실패");
		}
	}

	public boolean login(String id, String pw) throws SQLException {
		
		logger.info("login 요청 DAO");
		String sql = "SELECT * FROM user WHERE id = ? AND pw = ?";
		ps = conn.prepareStatement(sql);
		ps.setString(1, id);
		ps.setString(2, pw);
		
		rs = ps.executeQuery();
		if(rs.next()){
			return true;
		}
		
		rs.close();
		ps.close();
		conn.close();
		
		return false;
	}

	public int join(Map<String, String> map) throws SQLException {
		
		logger.info("회원가입 요청 DAO");
		String sql = "INSERT INTO user(id,pw,name,age,gender,email)";
			   sql+="VALUES(?,?,?,?,?,?)";
	    
	    ps = conn.prepareStatement(sql);
	    
	    ps.setString(1, map.get("id"));
		ps.setString(2, map.get("pw"));
		ps.setString(3, map.get("name"));
		ps.setInt(4, Integer.parseInt(map.get("age")));
		ps.setString(5, map.get("gender"));
		ps.setString(6, map.get("email"));
		
		int row = ps.executeUpdate();
		
		ps.close();
		conn.close();
		
		return row;
	}

	public List<UserDTO> list() throws SQLException {
		
		String sql = "SELECT id, name, age FROM user";
		
		ps = conn.prepareStatement(sql);
		
		List<UserDTO> list = new ArrayList<UserDTO>();
		
		rs = ps.executeQuery();
		
		while(rs.next()) {
			UserDTO dto = new UserDTO();
			dto.setId(rs.getString("id"));
			dto.setName(rs.getString("name"));
			dto.setAge(rs.getInt("age"));
			list.add(dto);
		}
		
		rs.close();
		ps.close();
		conn.close();
		
		return list;
	}

	public int del(String id) throws SQLException {
		
		String sql = "DELETE FROM user WHERE id = ?";
		
		ps = conn.prepareStatement(sql);
		ps.setString(1, id);
		
		int row = ps.executeUpdate();
		
		ps.close();
		conn.close();
		
		return row;
	}

	public UserDTO detail(String id) throws SQLException {
		logger.info("DAO id : {}", id);
		String sql = "SELECT * FROM user WHERE id = ?";
		UserDTO dto = new UserDTO();
		
		ps = conn.prepareStatement(sql);
		ps.setString(1, id);
		
		rs = ps.executeQuery();
		
		if(rs.next()) {
			dto.setId(rs.getString("id"));
			dto.setPw(rs.getString("pw"));
			dto.setName(rs.getString("name"));
			dto.setAge(rs.getInt("age"));
			dto.setGender(rs.getString("gender"));
			dto.setEmail(rs.getString("email"));
		}
		
		logger.info("값은 들어오나??");
		
		rs.close();
		ps.close();
		conn.close();
		
		return dto;
	}

	public UserDTO change(Map<String, String> map) throws SQLException {

		String sql = "UPDATE user SET name = ?, age = ?, email = ? WHERE id = ?";
		
		UserDTO dto = new UserDTO();
		
		ps = conn.prepareStatement(sql);
		ps.setString(1, map.get("name"));
		ps.setInt(2, Integer.parseInt(map.get("age")));
		ps.setString(3, map.get("email"));
		ps.setString(4, map.get("id"));
		
		rs = ps.executeQuery(); // 업데이트 된 row 수가 rs로 나옴
		
		if(rs.next()) {
			dto.setName(rs.getString("name"));
			dto.setAge(rs.getInt("age"));
			dto.setEmail(rs.getString("email"));
		}
		
		rs.close();
		ps.close();
		conn.close();
		
		return dto;
	}


}
