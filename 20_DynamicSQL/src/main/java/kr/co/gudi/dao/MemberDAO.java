package kr.co.gudi.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.gudi.dto.MemberDTO;

@Mapper
public interface MemberDAO { 
	// 인터페이스 : 규격을 잡아준다.
	// xml은 java에서 인식할 수 없기 때문에 중간다리 역할을 해준다.

	List<MemberDTO> list(Map<String, String> param);

	int join(MemberDTO dto);

	List<MemberDTO> listin(List<String> userName);

	MemberDTO detail(String id);

	int update(Map<String, String> params);

}
