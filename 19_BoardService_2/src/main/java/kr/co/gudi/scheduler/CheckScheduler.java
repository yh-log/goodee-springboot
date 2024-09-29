package kr.co.gudi.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import kr.co.gudi.dao.BoardDAO;

@EnableScheduling
@Component
public class CheckScheduler {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired BoardDAO board_dao;
	
	@Scheduled(cron = "0/5 * * * * MON-FRI")
	public void countChk() {
		int cnt = board_dao.allCount();
		logger.info("현재 게시글 수 : " + cnt);
	}
	
}
