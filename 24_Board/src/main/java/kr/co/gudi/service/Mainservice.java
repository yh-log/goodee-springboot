package kr.co.gudi.service;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import kr.co.gudi.dao.Maindao;
import kr.co.gudi.dto.Bbsdto;
import kr.co.gudi.dto.Maindto;

@Service
@PropertySource("classpath:super_admin.properties")
public class Mainservice {

	@Autowired Maindao main_dao;
	Logger logger = LoggerFactory.getLogger(getClass());
	
	 @Value("${user.id}") private String user_id;
	 @Value("${user.pw}") private String user_pw;
	 @Value("${user.ip}") private String user_ip;

	public void join(Map<String, String> params) {
		
		main_dao.join(params);
		
	}

	public int check(String id) {
		
		return main_dao.check(id);
	}

	public String login(Map<String, String> param) {
		String page ="";
		
		String id = param.get("id");
		String pw = param.get("pw");
		String opt = param.get("option");
		String ip = param.get("ip"); // 컨트롤러에서 넘겨준 값
		
		if(opt.equals("admin")) {
			if(user_ip.equals(ip)&&user_pw.equals(pw)&&user_id.equals(id)) {
				page = "admin";
			}
		}
		else{
			if(main_dao.login(param)!=null) {
				page = "user";
			}
		}
		
		
		return page;
	}

	public int allCount(int cnt_) {
		
		return main_dao.allCount(cnt_);
	}

	public List<Maindto> u_list(int limit, int offset) {
		
		return main_dao.u_list(limit,offset);
	}

	public Maindto u_detail(String id) {
		// TODO Auto-generated method stub
		return main_dao.u_detail(id);
	}

	public int u_del(String id) {
		
		return main_dao.u_del(id);
	}

	public void update(Map<String, String> param) {
		int row = main_dao.update(param);
		logger.info("update row : " + row);
	}

	

	
	
	
}
