package kr.co.gudi.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.gudi.service.JsoupService;

@Controller
public class JsoupController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired JsoupService jsoupService;
	
	@GetMapping(value="/connect.do")
	public String connect(Model model) throws IOException {
		
		String url = "https://sports.news.naver.com/wbaseball/record/index";
				// ?category=mlb&league=AL&year=2024"; // 뒤에는 바뀌는 내용이기 때문에 map에 별도로 넣어준다.
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("category", "mlb");
		param.put("league", "NL");
		param.put("year", "2024");
		
		// 특정 url에 연결.데이터 추가.method방식 (일반 예외 처리 필요 - 실행 전에 알 수 있는)
		//	 IOException 외부와 연결하기 때문에 네트워크에 문제 생길 경우 예외가 발생할 수 있어서 예외 처리 필요
		// html을 받아오는 거고 html 은 document 라고 한다. 그래서 받아줄 때도 Document 로 받아준다.
		Document doc =  Jsoup.connect(url).data(param).get();
		
		logger.info("doc => " + doc);
		model.addAttribute("elem", doc);
		
		return "resultAll";
	}
	
	@GetMapping(value="/getElem.do")
	public ModelAndView getElem() throws IOException {
		var url = "https://www.itworld.co.kr/howto/";
		Document doc = Jsoup.connect(url).data("page", "2").get();
		return jsoupService.getElem(doc);
	}
	
	@GetMapping(value="/softeer.do")
	public ModelAndView softeer() throws IOException {
		
		String url = "https://softeer.ai/careers/jobopening";
		Document doc = Jsoup.connect(url).data("page", "2").get();
		
		return jsoupService.softeer(doc);
	}
}
