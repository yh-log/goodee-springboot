package kr.co.gudi.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.gudi.dao.MemberDAO;
import kr.co.gudi.dto.MemberDTO;

@Service
public class MemberService {

	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired MemberDAO member_dao;
	public String join(Map<String, String> param) {
		
		int row = member_dao.join(param);
		String msg = "회원가입에 실패했습니다.";
		if(row > 0) {
			msg = "회원가입에 성공했습니다.";
		}
		return msg;
	}
	public Object overlay(String id) {
		
		int row = member_dao.overlay(id);
		
		return row;
	}
	
	public boolean login(String id, String pw) {
		
		boolean success = false;
		
		if(member_dao.login(id, pw)>0) {
			success=true;
		}

		return success;
	}
	public Map<String, Object> memberList(int page_, int cnt_) {

		int limit = cnt_;
		int offset = (page_-1)*cnt_;
		
		// 전체 페이지 수 
		int totalpages = member_dao.allCount(cnt_);
		
		Map<String, Object> listMap = new HashMap<String, Object>();
		listMap.put("totalpages", totalpages);
		listMap.put("list", member_dao.memberList(limit, offset));
		
		
		
		return listMap;
	}
	public void deleteMember(String id) {
		
		member_dao.deleteMamber(id);
		
	}
	public MemberDTO detailView(String id) {
		return member_dao.detailView(id);
	}
	public boolean getMemberUpdate(String id, MemberDTO param) {
		
		return member_dao.getMemberUpdate(id, param)>0? true : false;
	}
	
	
}
