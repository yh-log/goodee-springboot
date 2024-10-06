package kr.co.gudi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.gudi.service.MemberService;

@Controller
public class MemberController {

	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired MemberService member_service;
	
	@RequestMapping(value="/")
	public String home() {
		return "boardList";
	}
	
	
}
