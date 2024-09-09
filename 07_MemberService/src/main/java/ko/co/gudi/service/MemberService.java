package ko.co.gudi.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ko.co.gudi.dao.MemberDAO;
import ko.co.gudi.dto.MemberDTO;

public class MemberService {

   Logger logger = LoggerFactory.getLogger(getClass());
   
   public String join(Map<String, String> param) throws SQLException {
      
      MemberDAO dao = new MemberDAO(); // DB 에 값을 저장하기 위해 DAO 객체 선언
      String msg = "회원가입에 실패 했습니다."; // 실패했을 경우
      if(dao.join(param)==1) { // 성공하면 행이 1개 생기므로 성공했을 경우
         msg = "회원가입에 성공 했습니다."; // 문구 보냄
      }
      
      return msg;
   }

   public boolean login(String id, String pw) {      
	   MemberDAO dao = new MemberDAO();
      try {
         return dao.login(id, pw); // dao 에서 받은 값이 true면 넘기고
      } catch (SQLException e) {
         e.printStackTrace();
         return false; // 예외 발생 시 false 넘김
        }
   }

   public List<MemberDTO> list() {      
	   MemberDAO dao = new MemberDAO();
      try {
         return dao.list(); // dao 에서 받은 값이 true면 컨트롤러에게 true를 넘긴다.
      } catch (SQLException e) {
         e.printStackTrace();
         return new ArrayList<MemberDTO>(); // 그냥 아무것도 안넘기면 nullpointException이 발생하기 때문에 그냥 빈 List를 넘겨준다.
      }
   }

	public void del(String id) {
		MemberDAO dao = new MemberDAO(); // DB니까 dao한테 넘기기 위해 객체 생성
		try {
			dao.del(id); // true면 넘어가게
		} catch (SQLException e) {
			e.printStackTrace(); // 아니면 에러 메시지
		}
		
		// 리턴해줄 값은 없으니 리턴 안함
	}
	
	public MemberDTO detail(String id) {
	
		MemberDTO dto = null; // DTO에 있는 값을 가져올 거라서 DTO 타입의 변수 선언
		
		MemberDAO dao = new MemberDAO(); // dao 를 호출
		
		try {
			dto = dao.detail(id); // dao에서 가져온 값을 dto에 넣어준다.
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return dto;
	}
	
	
}