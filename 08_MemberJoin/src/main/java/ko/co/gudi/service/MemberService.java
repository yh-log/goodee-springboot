package ko.co.gudi.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ko.co.gudi.dao.MemberDAO;

// service 클래스가 autowired 되려면 @Service 어노테이션이 필요하다.
@Service
public class MemberService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired MemberDAO dao;

	public String join(Map<String, String> param) {
		logger.info("service param : {}", param);
		int row = dao.join(param);
		
		String msg = "회원 가입에 실패 했습니다.";
		
		if(row == 1) {
			msg = "회원 가입에 성공 했습니다.";
		}
		
		return msg;
	}
	// DAO 에서 할 내용
	// 1. 정보를 가지고 Connection 얻어오기
	// 2. 쿼리문 준비
	// 3. PreparedStatement 생성하기
	// 4. ? 가 있으면 ? 대응
	// 5. executeUpdate() 또는 executeQurey() 실행하기
	// 6. 결과값이 select 인 경우 rs를 통해 값 꺼내오기
	// 7. 자원 반납

}
