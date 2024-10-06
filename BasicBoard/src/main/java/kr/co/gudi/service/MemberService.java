package kr.co.gudi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import kr.co.gudi.dao.MemberDAO;

@Service
@PropertySource("classpath:super_admin.properties")
public class MemberService {

	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired MemberDAO member_dao;
	
}
