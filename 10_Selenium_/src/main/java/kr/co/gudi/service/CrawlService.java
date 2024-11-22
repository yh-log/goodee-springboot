package kr.co.gudi.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CrawlService {
	Logger logger = LoggerFactory.getLogger(getClass());

	String driver_id = "webdriver.chrome.driver";
	String driver_path = "C:/chromedriver.exe"; // C드라이브에 chromedriver.exe 넣어두기
	
	WebDriver driver = null;
	ChromeOptions options = null;
	
	public CrawlService() {
		// 시스템에 사용할 크롬드라이버(셀레니움이 사용할 브라우저)를 등록 하고
		System.setProperty(driver_id, driver_path);
		// 옵션을 활성화 시킨다.
		options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*"); // 403forbidden떠서 작성해줌 (cross-origin설정같은거?)
	}
	
	public String connect(String url) throws InterruptedException {
	
		driver = new ChromeDriver(options); // 옵션을 적용하여 드라이버 생성
		driver.get(url); //특정 url의 페이지를 가져온다.
		String result = driver.getPageSource(); // 가져온 페이지의 소스를 확인
		
		Thread.sleep(1); // 일부러 속도지연(driver.close)을 위해 넣었음.
		
		// 시스템용 브라우저를 다 쓰고 나면 닫아줘야 한다.
		driver.close();
		return result;
	}

	public String getElem(String url) {
		
		
		driver = new ChromeDriver(options); 
		// 창 크기가 마음대로 크거나 작게 나오기 때문에 설정을 추가해줌
		// 창을 최대로 만든다. (창 크기에 따라서 모바일 버전으로 볼 수 있기 때문에)
		driver.manage().window().maximize();; // 무조건 큰 창으로 열어라
		driver.get(url); // 특정 url의 페이지를 가져온다.
		String result = "";
//				driver.getPageSource();
		// 비동기로 움직이면 예상하지 못한 결과가 나오기 때문에 기다렸다가 움직여야 한다. (창이 확실하게 열린 후에 이벤트가 들어가야 한다.)

		// 원하는 요소 가져오기 (driver 로 부터 (driver = 브라우저)
		// findElement(null) : 하나만 가져옴 (여러개 있어도 첫번째 내용만) / findElements(null) : 여러개 가져옴 (반환 타입 List)
		List<WebElement> list = driver.findElements(By.cssSelector("div.item-container div.item-list")); // id가 명확하게 있으면 By.id 사용해도된다.
		logger.info("list => {} " + list); //div.item-base
		
		String title = "";
		String link = "";
		String status = "";
		String career = "";
		String co = ""; // 어느 회사에서 뽑는지
		String form = ""; // 고용형태
		String edu = ""; // 학력
		String period = ""; // 기간
		
		
		for (WebElement elem : list) {
			
			link = elem.findElement(By.tagName("a")).getAttribute("href");
			co = elem.findElement(By.cssSelector("a div.item-status i")).getText();
			title = elem.findElement(By.cssSelector("a p.item-tit")).getText();
			List<WebElement> dl = elem.findElements(By.cssSelector("a div.item-info dl.item__data"));
			
			status = dl.get(0).findElement(By.cssSelector("dt.data__tit")).getText(); // 모집상태
			// tagName("dd").getText() 로도 가져올 수 있음
			career = dl.get(1).findElement(By.cssSelector("dd.data__txt")).getText(); // 경력
			form = dl.get(2).findElement(By.cssSelector("dd.data__txt")).getText(); // 고용형태 
			edu = dl.get(3).findElement(By.cssSelector("dd.data__txt")).getText();; // 학력
			period = dl.get(4).findElement(By.cssSelector("dd.data__txt")).getText();; // 기간
			
			// 너무 느리기 때문에 뜯어와서 보여주는 기능에는 적합하지 않고, 주기적으로 정보를 모아 가져오는 기능에 적합하다.
			// 또는 사용자의 액션이 있어야만 가능한 부분을 해줘야 한다.
			
			logger.info("link => " + link);
			logger.info("co => " + co);
			logger.info("title => " + title);
			logger.info("status  => " + status);
			logger.info("career => " + career);
			logger.info("form => " + form);
			logger.info("edu => " + edu);
			logger.info("period => " + period);
			logger.info("====================================================");
//			logger.info("getText => " + elem.getText());
//			logger.info("to String => " + elem.toString());
		}
		
		
		return "";
	}

	public void event(String url) {
		
		driver = new ChromeDriver(options); // 드라이버 생성
		driver.get(url); // url 설정
		driver.manage().window().maximize(); // 창 크기 최대로 지정
		
		driver.findElements(By.cssSelector("td.h5.pointer")).get(2).click();;
		driver.findElement(By.name("strUid")).sendKeys("qtgks9");; // input 태그의 name (체크박스 외에 중복 불가)
		driver.findElement(By.name("strPassword")).sendKeys("dlgus1104!");
		
		try {
			Thread.sleep(1000); // 너무 빠르면 인식 못하는 경우가 있기 때문에 사람 처럼 보이기 위해 딜레이를 걸어줌
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		driver.findElement(By.cssSelector("button[type='submit']")).click(); // .submit() 을 해도 안..된다
		
		// JavascriptExecutor : java와 javsScript를 연결해주는 (js를 동작하게 해주는)
		// js가 보안이 떨어진다는 이유 -> 이렇게 원하는 동작을 시킬 수 있다.
		// 그래서 이런 기능을 이용해서 주기적으로 뭘 클릭하는 행동을 시키거나 하는 등의 기능을 추가할 수 있다.
		// 주로 검색엔진을 사용하는 곳에서 많이 사용한다. (ex. 논문 사이트, 가격 비교 사이트, 토스에서도 사용하다가 문제된 적 있다.)
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("alert('안녕하세요!')");
	}
	
	
}
