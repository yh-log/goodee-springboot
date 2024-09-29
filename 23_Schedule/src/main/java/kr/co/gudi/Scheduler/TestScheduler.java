package kr.co.gudi.Scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
public class TestScheduler {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	// 스케쥴러는 프로그램과 생명주기(라이프 사이클)를 함께 한다.
	// 	-> 켜질 때 같이 켜지고 꺼질 때 같이 꺼진다. (ex. 데몬스레드=메인스레드와 생명주기가 같다 / 워크스레드=메인스레드가 죽어도 돌아감)
	// 		-> 스케쥴러도 뜯어보면 스레드로 만들어 졌다 그렇다는 것은 = 데몬스레드로 만들어짐
	
	// 각각의 메서드에 @Scheduler 를 통해서 특정 주기마다 해당 메서드가 실행되도록 한다.

//	@Scheduled(fixedDelay = 1000)
//	public void fixedDelay() {
//		logger.info("작업이 끝나고, 1초 후 실행");
//		try {
//			Thread.sleep(2000); 
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Scheduled(fixedRate = 1000)
//	public void fixedRate() {
//		logger.info("1초 마다 실행");
//	}
	
	// 초 분 시 일 월 요일 년도(생략가능)
	// 요일은 요일 앞 3개 알파벳
	// 0 부터 시작해서 5씩 증가 될 때 마다
	// cron 또는 crontab 으로 검색하면 사용법이 나온다
	@Scheduled(cron = "0/5 * * * * MON-FRI") // 1년 365일 계속 돌아간다는 의미
	public void cron() {
		logger.info("5초 마다 실행");
	}
	
	
	
}
