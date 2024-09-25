package kr.co.gudi.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.gudi.service.TeamService;

@Controller
public class TeamController {

	@Autowired TeamService team_service;
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping(value="/")
	public String home() {
		return "list";
	}
	
	/*
	 * @GetMapping(value="/list.ajax")
	 * @ResponseBody public Map<String, Object> list(){
	 * Map<String, Object> map = new HashMap<String, Object>();
	 * List<Map<String, Object>> list = team_service.list();
	 * map.put("list", list);
	 * return map; }
	 */
	
	@GetMapping(value="/list.ajax")
	@ResponseBody
	public Map<String, Object> list(){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", team_service.list());
		
		return map;
	}
	
	@RequestMapping(value="/update/{col}/{val}/{no}.ajax")
	public String update(@PathVariable Map<String, String> params) {
			
//			String col, @PathVariable String val, @PathVariable String no) {
//		logger.info("col : " + col);
//		logger.info("val : " + val);
//		logger.info("no : " + no);
		
		logger.info("params : " + params);
		
		team_service.update(params);
		
		return "redirect:/";
	}
	
	
	
}
