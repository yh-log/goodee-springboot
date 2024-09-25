package kr.co.gudi.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import kr.co.gudi.dao.MemberDAO;
import kr.co.gudi.dto.MemberDTO;

@Service
public class MemberService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired MemberDAO member_dao;
	
	public List<MemberDTO> list(Map<String, String> param) {
		return member_dao.list(param);
	}

	public String join(MemberDTO dto, Model model) {

		String page = "join";
		
		logger.info("email : " + dto.getEmail()); // null이 아니라 공백으로 들어온다.
		
		int row = member_dao.join(dto);
		
		if(row > 0) {
			page = "redirect:/list.do";
		}else {
			// form 방식의 단점! (실패했을 때 기존 입력 값이 날아간다., ajax를 쓰면 기존 내용이 남아있을 수 있다.)
			model.addAttribute("msg", "회원가입에 실패했습니다.");
		}
		
		return page;
	}

	public void multi(List<String> userName, Model model) {
		List<MemberDTO> list = member_dao.listin(userName);
		model.addAttribute("list", list);
	}

	public MemberDTO detail(String id) {
		return member_dao.detail(id);
	}

	public Map<String, Object> update(Map<String, String> params) {
		
		int row = member_dao.update(params);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", row>0?true:false);
		
		return map;
	}

}
