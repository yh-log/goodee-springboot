package kr.co.gudi.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
public class JsoupService {

	Logger logger = LoggerFactory.getLogger(getClass());

	public ModelAndView getElem(Document doc) {
		
		ModelAndView mav = new ModelAndView("result");
		
		// 요소를 가져오는 2가지 방법 (js, css)
		// ex. 아이디가 test인 요소를 가져올 경우
		// 1번 방식 (js 방식)
		//	doc.getElementById("test");
		// 2번 방식 (css selector 방식)
		//	doc.select("#test");
		
		// div 중에서 class가 node-list인 요소를 가져와라 (해당 페이지에서 html 요소로 찾아줌)
		Elements elems = doc.select("div.node-list");
		logger.info("size : " + elems.size()); // 1개
		
		Element elem = elems.get(0);
		Elements cardList = elem.select("div.card");
		logger.info("card size : " + cardList.size());
		
		String title = "";
		String link = "";
		String content = "";
		String reg_date = "";
//		Elements tags;
//		String tags = ""; // #xxx #xxx 형태로
//		String[] tag;
		String tagName = "";
		
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		Map<String, String> map = null;
		
		// for 문안에 변수를 선언해주면 메모리 낭비됨
		for (Element card : cardList) {
			map = new HashMap<String, String>(); // for문이 돌 때마다 새로운 맵 생성
			title = card.select("h5.card-title a").html();
			// http://www.itwolrd.co.kr
			link = card.select("h5.card-title a").attr("href");
			// p 태그 중에서 class="card-text" 중에서 class=".crop-text-2" 인 녀석 가져와
			content = card.select("p.card-text.crop-text-2").html();
			
			// 1. 문자열 하나씩 자르기 (DB에 넣을 때 필요할 듯)
//			tags = card.select("span.d-flex").text();
//			tag = tags.split(" ");
//			for (String tagVal : tag) {
//				tagName += "#" + tagVal + " ";
//			}
			
			// 2. tags 를 Elements로 바꿔줌
			tagName = ""; // for 문이 한번 돌고 나면 초기화 시켜줌
			for (Element tag : card.select("a.badge.bg-tag")) {
				tagName += "#" + tag.html() + " ";
			}

			// 작성일
			reg_date = card.select(".font-color-5").text();
			
			map.put("title", title);
			map.put("link", "http://www.itworld.co.kr/" + link);
			map.put("content", content);
			map.put("tags", tagName);
			map.put("reg_date", reg_date);
			list.add(map);
		}
		
		mav.addObject("elem", list);
		
		return mav;
	}

	public ModelAndView softeer(Document doc) {
		
		ModelAndView mav = new ModelAndView("softeer");
		Elements elems = doc.select("div.con__body");
		logger.info("size : " + elems.size());
		
		Element elem = elems.get(0);
		Elements list = elem.select("div.item-base");
		logger.info("list size : " + list.size());
		
		String subject = ""; // 공고 분류
		String title = ""; // 공고 제목
		String status = ""; // 공고 상태
//		String info = ""; // 공고 정보
//		String reg_date = ""; // 공고 기간
		Map<String, Object> map = null;
		for (Element item : list) {
			map = new HashMap<String, Object>();
			
			subject = item.select("div.item-status").text();
			title  = item.select("p.item-tit").html();
			
			status = "";
			for (Element info : item.select("div.item-info")) {
				status += "=" + info.text() + " ";
			}
			
			logger.info("subject => " + subject);
			logger.info("title => " + title);
			logger.info("status => " + status);
			
		}
		
		mav.addObject("elem", elems);
		return null;
	}
	
}
