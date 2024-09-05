package ko.co.gudi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ko.co.gudi.dao.MainDAO;

public class MainService {
	
	// 이클립스 에서는 복붙하면 inport 문도 같이 따라온다.
	Logger logger = LoggerFactory.getLogger(getClass());

	public String dbConnect() {
		
		MainDAO dao = new MainDAO();
		boolean success =  dao.dbConnect();
		return success ? "DB접속에 성공했습니다." : "DB접속에 실패했습니다."; // success 가 true면 앞에 문자열 false면 뒤에 문자열 반환 (당연히 축약하지 않고 사용해도 된다.)
	}
}
