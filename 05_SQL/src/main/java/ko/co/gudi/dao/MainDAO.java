package ko.co.gudi.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainDAO {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	// DB는 여기서만 사용해서 private 적용 (이건 판단에 따라 자유롭게 적용!)
	private Connection conn = null; // 다른 패키지에서도 쓸거면 public, 여기서만 사용하게 할거면 private를 작성
	
	// DAO 를 객체화 한다는 것은 DB를 쓴다는 의미이므로, 생성과 동시에 DB접속을 해주자
	public MainDAO() {
		String id = "web_user";
		String pw = "pass";
		String url = "jdbc:mariadb://localhost:3306/mydb";
		String dirver = "org.mariadb.jdbc.Driver";
		
		try {
			Class.forName(dirver); // DB랑 java랑 연결
			conn = DriverManager.getConnection(url, id, pw); 
			logger.info("Connection : {}", conn);
//			conn.close(); // 생성과 동시에 닫아주면 안되니까 지워준다.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean dbConnect() {
		return conn == null? false : true;
	}

	public boolean stmt() throws SQLException {
		// 1. DB접속 -> 위에서 생성자로 완료!
		// 2. 쿼리문 준비
		String sql = "create table member("
				+ "	ID varchar(50) primary key,"
				+ "	PW varchar (100),"
				+ "	NAME varchar(20),"
				+ "	AGE int(4),"
				+ "	GENDER varchar(4),"
				+ "	EMAIL varchar(100)"
				+ ")";
		
		boolean success = false;
		// 3. Statement 또는 PreparedStatement 준비
		try {
			Statement stmt = conn.createStatement();
		// 4. 쿼리 실행 ( executeUpdate() or executeQuery() )
			int row = stmt.executeUpdate(sql);
			logger.info("이 쿼리문을 실행해서 영향받은 데이터 갯수 : " + row); 
			success = true;
		} catch (Exception e) { // SQLException 발생 → 다형성 이용 (모든 Exception을 하나의 catch에 걸리 수 있게)
			e.printStackTrace();
		}finally {
			conn.close();
		}
		return success;
	}

	public int pstmt() throws SQLException {
		
		int row = 0;
		
		// 1. 쿼리문 준비
		String sql = "INSERT INTO member(ID,PW,NAME,AGE,GENDER,EMAIL)VALUES(?,?,?,?,?,?)"; // 이 ? 는 저장하고 있다는 의미
		
		PreparedStatement ps = conn.prepareStatement(sql); // 2-1. 실행 객체 준비
		
		// 2-2. ? 대응을 해줘야 한다. (? 가 뭔지 모르니까 실행하면 에러 발생한다.)
		ps.setString(1, "admin"); // 1번째 물음표에 "admin" 이라는 문자열을 넣는다.
		ps.setString(2, "pass");
		ps.setString(3, "홍길동");
		ps.setInt(4, 20); // setString를 해도 들어가는 경우도 있다! 모르면 일단 String로 하자..!
		ps.setString(5, "남자");
		ps.setString(6, "abc@naver.com");
		
		row = ps.executeUpdate(); // 3. 쿼리 실행
		ps.close(); // 4. 자원 반납
		conn.close(); 
		
		
		return row;
	}

	public int resultSet() throws SQLException {
		
		logger.info("select DAO 요청");
		int row = 0;

		// 1. 쿼리문 준비
		String sql = "SELECT * FROM member";
		
		// 2-1. 실행 객체 준비
		PreparedStatement ps = conn.prepareStatement(sql);
		
		// 2-2. ? 가 있으면 대응
		
		// 3. 쿼리 실행
		ResultSet rs = ps.executeQuery(); // ResultSet : 무언가를 보여주는 것 (안에 데이터 덩어리가 들어가 있다.) set은 컬렉션프레임워크에서 배운 그 set (순서x, 중복x) - hasNext, Next 등등.. => 데이터를 뽑으려면 이걸 해줘야 한다.
//		row = rs.getRow();
		
		
		// select이므로 ResultSet 으로 부터 데이터를 가져와야 한다.
		while(rs.next()) {
			String id = rs.getString("ID");
			String pw = rs.getString(2); // column index 를 사용하려면 내가 원하는 컬럼이 몇번째인지 알아야 한다.
			String name = rs.getString("NAME");
			int age = rs.getInt("AGE");
			String gender = rs.getString("GENDER");
			String email = rs.getString("EMAIL");
			logger.info(id + "/" + pw + "/" + name + "/" + age + "/" + gender + "/" + email);
			row++;
		}
		
		// 4. 자원 반납
		rs.close();
		ps.close();
		conn.close();
		
		return row;
	}
	
	



}
