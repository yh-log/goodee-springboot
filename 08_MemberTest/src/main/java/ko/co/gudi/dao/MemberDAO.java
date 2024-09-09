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

import ko.co.gudi.dto.MemberDTO;

public class MemberDAO {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public MemberDAO() { // DAO 객체 생성 시 DB 연결을 자동으로 하기 위해 생성자 생성
		
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
		
		String sql = "SELECT * FROM member WHERE id = ? AND pw = ?"; // 실행할 쿼리문 작성
		
		boolean success = false; // 기본값은 false
		
		ps = conn.prepareStatement(sql); 
		// ? 표를 처리해줘야 함 (1번째 물음표에는 id를 주고 2번째 물음표에는 pw준다.)
		ps.setString(1, id);
		ps.setString(2, pw);
		
		// 쿼리 실행
		rs = ps.executeQuery();
		
		success = rs.next(); // next로 rs에 값이 있다면 true를 반환
		
		// 자원 닫기
		rs.close();
		ps.close();
		conn.close();
		
		return success;
	}

	public int join(Map<String, String> param) throws SQLException {
		
		int row = 0;
		
		String sql = "INSERT INTO member(id,pw,name,age,gender,email)"; // sql 쿼리 준비
		sql+="VALUES(?,?,?,?,?,?)";
		
		ps = conn.prepareStatement(sql);
		
		ps.setString(1, param.get("id"));
		ps.setString(2, param.get("pw"));
		ps.setString(3, param.get("name"));
		ps.setInt(4, Integer.parseInt(param.get("age")));
		ps.setString(5, param.get("gender"));
		ps.setString(6, param.get("email"));
		
		row = ps.executeUpdate();
		
		ps.close();
		conn.close();
		
		return row;
	}

	public List<MemberDTO> list() throws SQLException {
		
		logger.info("아니왜..");
		String sql = "SELECT id, name, age FROM member"; // ? 처리 필요
		
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		
		
		// 리스트 형태로 값을 가져올 것
		List<MemberDTO> list = new ArrayList<MemberDTO>();
		
		
		while(rs.next()) {
			MemberDTO dto = new MemberDTO();
			dto.setId(rs.getString("id"));
			dto.setName(rs.getString("name"));
			dto.setAge(rs.getInt("age"));
			list.add(dto);
			logger.info("list 값 {} :", list);
		}
		
		rs.close();
		ps.close();
		conn.close();
		
		return list;
		
	}

	public MemberDTO detail(String id) throws SQLException {
		
		logger.info("datail 요청 dao");

		String sql = "SELECT * FROM member WHERE id = ?";
		
		MemberDTO dto = null;
		
		ps = conn.prepareStatement(sql);
		ps.setString(1, id);
		
		rs = ps.executeQuery();
		
		if(rs.next()) { // rs가 있으면 dto에 있는 값을 바꿔서 그 값을 가져오는 거!!!
			dto = new MemberDTO();
			dto.setId(rs.getString("id"));
			dto.setPw(rs.getString("pw"));
			dto.setName(rs.getString("name"));
			dto.setAge(rs.getInt("age"));
			dto.setGender(rs.getString("gender"));
			dto.setEmail(rs.getString("email"));
		}
		
		rs.close();
		ps.close();
		conn.close();
		
		return dto;
	}

	public int del(String id) throws SQLException {
		
		String sql = "DELETE FROM member WHERE id = ?";
		
		ps = conn.prepareStatement(sql);
		ps.setString(1, id);
		
		int row = ps.executeUpdate();
		
		ps.close();
		conn.close();
		
		return row;
		
		
	}


}
