package kr.co.gudi.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.gudi.dao.MainDAO;
import kr.co.gudi.dto.MainDTO;

@Service
public class MainService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired MainDAO mainDao;
	public List<MainDTO> list() {
		
		return mainDao.list();
	}

	public void write(Map<String, Object> param) {
		mainDao.write(param);
	}

}
