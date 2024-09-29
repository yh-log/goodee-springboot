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
	
	@Value("${user.id}") String user_id;
	@Value("${user.pw}") String user_pw;
	@Value("${user.ip}") String user_ip;
	
	public Map<String, Object> login(MemberDTO memberDto, String ip, String opt) {
		Map<String, Object> resultMap = new HashMap<>();
			
		boolean isSuccess = false;
		boolean isAdmin = false;
		
		if(opt.equals("admin")) {
			if(memberDto.getId().equals(user_id) && memberDto.getPw().equals(user_pw) && ip.equals(user_ip)) {
				isSuccess = true;
				isAdmin = true;
			}
		}else {
			if(member_dao.login(memberDto)>0) {
				isSuccess = true;
			}
		}
		
		resultMap.put("isSuccess", isSuccess);
		resultMap.put("isAdmin", isAdmin);
		
		return resultMap;
	}

	public int overlay(String id) {
		return member_dao.overlay(id);
	}

	public boolean join(MemberDTO memberDto) {

		return member_dao.join(memberDto)>0? true:false;
	}

	public Map<String, Object> memberList(int page, int cnt) {
		Map<String, Object> resultMap = new HashMap<>();
		
		int limit = cnt; // 보여줄 데이터 수
		int offset = (page-1)*cnt; // 몇번부터 보여줄지
		
		int totalPage = member_dao.totalPage(cnt);
		
		resultMap.put("totalPage", totalPage);
		resultMap.put("list", member_dao.memberList(limit, offset));
		
		return resultMap;
	}

	public MemberDTO memberDetail(String id) {
		
		MemberDTO dto = member_dao.memberDetail(id);
		
		return dto;
	}

	public void update(Map<String, String> param) {
		int row = member_dao.update(param);
		logger.info("update row : " + row);
		
	}

	public void delete(String id) {
		int row = member_dao.delete(id);
		logger.info("row : " + row);
	}
	
}
