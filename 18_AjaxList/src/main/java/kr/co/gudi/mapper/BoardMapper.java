package kr.co.gudi.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {

	List<HashMap<String, Object>> list();

}
