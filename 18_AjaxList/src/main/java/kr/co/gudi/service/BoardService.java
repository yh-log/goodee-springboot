package kr.co.gudi.service;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.gudi.mapper.BoardMapper;

@Service
public class BoardService {

	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired BoardMapper board_mapper;
	
	public List<HashMap<String, Object>> list() {
		return board_mapper.list();
	}
	
}
