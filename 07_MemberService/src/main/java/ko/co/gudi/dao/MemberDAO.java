//package ko.co.gudi.dao;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import ko.co.gudi.dto.MainDTO;
//
//public class MemberDAO {
//
//	Logger logger = LoggerFactory.getLogger(getClass());
//	Connection conn = null;
//	PreparedStatement ps = null;
//	ResultSet rs = null;
//	
//	public MemberDAO() {
//		logger.info("DB 접속 완료");
//		
//		String id = "web_user";
//		String pw = "pass";
//		String url = "jdbc:mariadb://localhost:3306/mydb";
//		String driver = "org.mariadb.jdbc.Driver";
//	
//		try {
//			Class.forName(driver);
//			conn = DriverManager.getConnection(url, id, pw);
//			logger.info("conn : {}", conn);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.info("DB 접속 실패");
//		}
//	}
//
//	public int join(Map<String, String> param) throws Exception {
//		
//		logger.info("회원가입 insert DAO");
//		int row = 0;
//		
//		String sql = "INSERT INTO member(ID,PW,NAME,AGE,GENDER,EMAIL)";
//				sql += "VALUES(?,?,?,?,?,?)";
//		
//		ps = conn.prepareStatement(sql);
//		
//		ps.setString(1, param.get("id"));
//		ps.setString(2, param.get("pw"));
//		ps.setString(3, param.get("name"));
//		ps.setInt(4, Integer.parseInt(param.get("age")));
//		ps.setString(5, param.get("gender"));
//		ps.setString(6, param.get("email"));
//		
//		row = ps.executeUpdate();
//		
//		ps.close();
//		conn.close();
//		
//		return row;
//	}
//
//	public boolean login(String id, String pw) throws SQLException {
//
//		logger.info("id/pw 검증 오류");
//		String sql = "SELECT ID FROM member WHERE ID = ? AND PW = ?";
//		boolean success = false;
//		
//		ps = conn.prepareStatement(sql);
//		ps.setString(1, id);
//		ps.setString(2, pw);
//		rs = ps.executeQuery();
//		
//		success = rs.next(); // true 인지 false 인지만 알면 되니까 바로 보내버리기
//		
//		rs.close();
//		ps.close();
//		conn.close();
//		
//		return success;
//	}
//
//	public Object list() throws SQLException {
//
//		String sql = "SELECT ID, NAME, AGE FROM member";
//		ps = conn.prepareStatement(sql);
//		rs = ps.executeQuery();
//		
//		List<MainDTO> list = new ArrayList<MainDTO>(); // 다형성 (while 문 안에 있으면 계속 초기화되기 때문에 밖에 선언)
//		
//		while(rs.next()) {
//			MainDTO dto = new MainDTO();
//			dto.setId(rs.getString("id")); 
//			dto.setName(rs.getString("name"));
//			dto.setAge(rs.getInt("age"));
//			list.add(dto);
//		}
//		
//		
//		return null;
//	}
//}

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
	
	public MemberDAO() {
		String id = "web_user";
		String pw = "pass";
		String url = "jdbc:mariadb://localhost:3306/mydb";
		String driver = "org.mariadb.jdbc.Driver";
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

	public int join(Map<String, String> param) throws SQLException {
		
		int row = 0;
		String sql = "INSERT INTO member(id,pw,name,age,gender,email)";
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
		
		return row; // insert 성공시 row = 1을 반환 실패 시 row = 0을 반환
	}

	public boolean login(String id, String pw) throws SQLException {
		
		String sql = "SELECT id FROM member WHERE id = ? AND pw = ?";
		boolean success = false;
		
		ps = conn.prepareStatement(sql);
		ps.setString(1, id);
		ps.setString(2, pw);
		rs = ps.executeQuery();		
		success = rs.next(); // id와 pw 검색 결과가 있으면 true를 전달함
		
		rs.close();
		ps.close();
		conn.close();
		
		return success;
	}

	public List<MemberDTO> list() throws SQLException {
		
		String sql = "SELECT id,name,age FROM member";
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		
		List<MemberDTO> list = new ArrayList<MemberDTO>(); // 다양한 형태의 값이 담겨있기 때문에 class로 받아줘야 해서 dto 타입의 List 객체 선언
		
		while(rs.next()) { // 다음 데이터가 없을 때까지 반복
			MemberDTO dto = new MemberDTO(); // dto에서 값을 받아오기 위해 dto 객체 선언
			dto.setId(rs.getString("id")); // dto에서 private로 만들어졌기 때문에 setId 를 받아오고, rs에 들어가 있는 id값을 넣어준다.
			dto.setName(rs.getString("name"));
			dto.setAge(rs.getInt("age"));
			list.add(dto); // list에 dto에 넣은 값들을 넣어준다.
		}
		
		rs.close();
		ps.close();
		conn.close();
				
		return list; // list에 넣은 dto의 값을 return 한다.
	}

	public void del(String id) throws SQLException {
		
		String sql = "DELETE FROM member WHERE id = ?"; // 해당 id에 해당하는 모든 정보 지우기
		
		ps = conn.prepareStatement(sql);
		ps.setString(1, id); // 받아온 id값을 주고
		int row = ps.executeUpdate(); // 쿼리 실행 (그에 맞는 정보 지우기 위한 쿼리문)
		logger.info("삭제된 갯수 : " + row);
		
		ps.close();
		conn.close();
		
		// 리턴해줄 값은 없어서 리턴 안함
	}

	public MemberDTO detail(String id) throws SQLException {
		
		String sql = "SELECT * FROM member WHERE id = ?"; // 아이디에 해당하는 사람의 모든 정보 출력하는 쿼리문
		
		ps = conn.prepareStatement(sql);
		ps.setString(1, id); // 받아온 아이디 값을 넣어서
		
		rs = ps.executeQuery(); // 쿼리문 실행 (select니까 executeQuery) 해서 rs에 넣어줌 (ResultSet)
		
		MemberDTO dto = null;
		
		// rs는 반드시 next를 해줘야 한다.
		if(rs.next()) {
			dto = new MemberDTO(); // DTO에서 값을 가져와야 하니까 객체 선언
			dto.setId(rs.getString("id")); // dto에서 setId를 변경할 건데 그거는 rs에 들어가 있는 id를 가져와서 할거다
			dto.setPw(rs.getString("pw")); //  -> rs에는 선택한 id에 해당하는 사람의 id가 들어가 있다.
			dto.setName(rs.getString("name")); // -> dto 에 name을 변경하는데 변경하는 값은 rs에 덩어리로 들어가 있는 데이터 중 name이다.
			dto.setAge(rs.getInt("age"));
			dto.setGender(rs.getString("gender"));
			dto.setEmail(rs.getString("email"));
			
		}
		
		rs.close();
		ps.close();
		conn.close();
		
		return dto;
	}

}
