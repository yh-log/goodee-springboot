package kr.co.gudi.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import kr.co.gudi.dao.MemberDAO;
import kr.co.gudi.dto.MemberDTO;

@Service
@PropertySource("classpath:super_admin.properties")
public class MemberService {

	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired MemberDAO member_dao;
	
	@Value("${user.id}") private String user_id;
	@Value("${user.pw}") private String user_pw;
	@Value("${user.ip}") private String user_ip;
	
	public Object overlay(String id) {
		
		int row = member_dao.overlay(id);
		return row;
	}

	public boolean join(Map<String, String> param) {
		
		int row = member_dao.join(param);
		logger.info("insert : "+row);
		
		return row>0? true : false;
	}

	public boolean login(String id, String pw, String ip, String opt) {
		
		boolean success = false;
		
		// 관리자의 opt value="admin"
		if(opt.equals("admin")) {
			if(ip.equals(user_ip) && pw.equals(user_pw) && id.equals(user_id)) {
				if(member_dao.login(id,pw)>0) {
					success = true;
				}
			}
		// 일반회원 로그인
		}else {
			if(member_dao.login(id, pw)>0) {
				success = true;
			}
		}
		
		
		return success;
	}

	public Map<String, Object> memberList(int page_, int cnt_) {

		int limit = cnt_;
		int offset = (page_-1)*cnt_;
		
		int totalPage = member_dao.totalPage(limit);
		
		Map<String, Object> memberMap = new HashMap<String, Object>();
		memberMap.put("totalPage", totalPage);
		memberMap.put("list", member_dao.list(limit, offset));
		
		return memberMap;
	}

	public MemberDTO detail(String id) {
		return member_dao.detail(id);
	}
	
}
