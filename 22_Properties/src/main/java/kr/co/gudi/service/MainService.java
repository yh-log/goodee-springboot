package kr.co.gudi.service;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import kr.co.gudi.dao.MainDAO;

@Service
// classpath : 실제로 동작되는 프로그램의 경로를 의미한다.
@PropertySource("classpath:super_admin.properties")
public class MainService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired MainDAO main_dao;

	@Value("${user.id}") private String user_id;
	@Value("${user.pw}") private String user_pw;
	@Value("${user.ip}") private String user_ip;
	
	public boolean login(String id, String pw, String ip, String opt) {
		
		boolean success = false;
		
		if(opt.equals("admin")) {
			// super_admin.properties를 이용
			logger.info(user_id +" / "+ user_pw+" / "+user_ip);
			if(id.equals(user_id) && pw.equals(user_pw) && ip.equals(user_ip)) {
				success = true;
			}
		}else {
			if(main_dao.login(id, pw) != null){
				success = true;
			}
		}
		
		return success;
	}
}
