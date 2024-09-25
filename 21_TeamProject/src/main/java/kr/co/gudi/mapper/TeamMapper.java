package kr.co.gudi.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.gudi.vo.TeamVO;

@Mapper
public interface TeamMapper {

	List<TeamVO> list();

	int update(Map<String, String> params);
	
//	Map<String, Object> list();

}
