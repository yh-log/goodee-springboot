package kr.co.gudi.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.gudi.service.ApiService;

@RestController
public class ApiController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired ApiService apiService;
	
	@RequestMapping(value="/apiCall.ajax")
	public Map<String, Object> apiCall(@RequestParam Map<String, String> params){
		
		logger.info("params : {} " + params);
		
		return apiService.apiCall(params);
	}
}
