package kr.co.gudi.service;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CrawlService {

	Logger logger = LoggerFactory.getLogger(getClass());

	String divar_id = "webdriver.chrome.driver"; // 무조건 이거다.
	String divar_path = "C:/chromedriver.exe";
	
	WebDriver driver = null;
	ChromeOptions options = null;
	
	// 여러번 사용하기 때문에 생성자로 선언
	public CrawlService() {
		System.setProperty(divar_id, divar_path);
		options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*"); // 원격으로 들어올 때 허용해줘라 (크로스 도메인 처리할 때처럼
	}
	
	public String connect(String url) throws InterruptedException { // 스레드 정지시킬 때 사용 (스레드 슬립이 무조건 있어야 함)
		// 시스템에 사용할 크롬 드라이버 (셀레니움이 사용할 브라우저)를 등록하고
		driver = new ChromeDriver(options); // 옵션을 적용하여 드라이버 생성
		driver.get(url); // 특정 url의 페이지를 가져온다.
		String result = driver.getPageSource(); // 가져온 페이지의 소스를 확인
		
		Thread.sleep(1); // 브라우저 꺼지는지 확인하기 위해 스레드 슬립 사용
		
		// 시스템 용 브라우저를 다 쓰고 나면 닫아줘야 한다.
		driver.close();
		
		return result;
	}
	

	
	
	
}
