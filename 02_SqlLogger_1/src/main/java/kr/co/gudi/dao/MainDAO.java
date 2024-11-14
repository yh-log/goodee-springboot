package kr.co.gudi.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.gudi.dto.MainDTO;

@Mapper
public interface MainDAO {

	List<MainDTO> list();

	int write(Map<String, Object> params);

}
