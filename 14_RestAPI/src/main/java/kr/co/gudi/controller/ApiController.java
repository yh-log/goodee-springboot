package kr.co.gudi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.gudi.dto.UserInfo;

// js는 보안이 취약하다. (그런데 서버 내용을 js에 주라고 하니.. )
// 보안상 문제가 많이 생기니까 url이 다를 경우 자바스크립트와의 통신을 차단시킨다.
// 해결방법 1) view 페이지가 서버에 있으면 된다. (기존에 쓰던 방식)
// 해결방법 2) 서버에서 허용을 해줘야 한다. -> @CrossOrigin

@CrossOrigin
@Controller
public class ApiController {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/* 우리가 그동안 써왔던 방식은 request 객체를 이용한 forward 방식이었다. (forward는 요청한 사람이 아닌 누구에게도 줄 수 있다. 그래서 순수하게 요청/응답 개념이 아니라고 볼 수 있다.)
	 * 그래서 model에 값을 넣고 페이지 이름을 반환하여 특정 페이지로 데이터를 보내는 형태
	 * 
	 * REST 방식은 response를 이용한 방식이다. (요청을 보낸 사람에게만 응답할 수 있다.)
	 * 그래서 요청을 한 페이지로 돌아가고(redirect 를 사용하지 않는 이상은),
	 * 보여줄 내용을 그려서 보낸다. (직접 보낼 수 없기 때문에 write로 글씨를 써줄수는 있다.) = 말 그대로 그려서 보낸다 페이지에
	 * */
	
	/* 일반적으로 Restful Service 에서는 다음과 같은 method 를 사용한다. (통상적인 약속)
	 * GET		: 특정 데이터를 조회 할때 
	 * POST 	: 특정 데이터를 요청 할때
	 * DELETE 	: 특정 데이터를 삭제 할때
	 * PUT 		: 특정 데이터를 수정 할때
	 * PATCH 	: 특정 데이터를 일부 수정 할때 
	 * ...
	 * */
	
	@RequestMapping(value="/", method = RequestMethod.GET)
//	@ResponseBody // 이걸 넣으면 이제부터 응답을 response 로 하게 된다.
	public String main() {
		return "{\"msg\":\"rest api 형태의 답변입니다.\"}";
	}

	// 복수개의 메서드를 받는것도 가능하다.
	@RequestMapping(value="/map", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> map() { // object로 받으면 우리는 casting을 해줬지만 그냥 java와 소통할 때 이야기고, 지금은 다른 애들한테 던지는거니까 상관없다.
		logger.info("요청에 대해 JSON 형태로 보내주기");
		// 위처럼 문자열로 JSON 형태를 만들어 보낼수도 있지만
		// java에서 json과 비슷한 데이터 타입을 활용해 보낼수도 있다. (HashMap 을 -> JSON 형태로 바꿔줬다 : jackson core 라는 라이브러리가 필요)
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		map.put("msg", "map 형태로 반환 성공");
		
		return map;
	}
	
	// method를 명시하지 않으면 모든 method 형태를 다 받는다. (보안상 좋지 않기 때문에 중요한 호출은 이렇게 하면 안됨!)
	@RequestMapping(value="/object")
	@ResponseBody
	public UserInfo object() {
		
		UserInfo info = new UserInfo();
		info.setId("tester");
		info.setName("Lee");
		info.setAge(55);
		info.setSuccess(true);
		
		
		return info;
	}
	
	//@RequestMapping(value="/list", method = RequestMethod.GET)
	// XXXMapping 을 지원해준다.
	@GetMapping(value="/list") // GET 만 받겠다는 의미 ※ 단점 : 복수개를 넣을 수 없다. (Spring version이 낮을 경우 사용 못한다.)
	@ResponseBody
	public List<String> list(){
		
		List<String> list = new ArrayList<String>();
		list.add("하나");
		list.add("둘");
		list.add("셋");
		
		return list;
	}
	
	//get?lec=java&no=1 -> get/java/1 (이렇게 쓰는걸 선호한다. 무조건 가능한건 아니다! 상황에 따라서 다르다)
	// get?lec=java&no=1 <- 일반적으로 사용하는 방식으로 매개변수를 통해 가져올 수 있다.
	@GetMapping(value="/get")
	@ResponseBody
	public String param(String lec, String no) {
		
		return "{\"msg\":\""+lec+"과목의"+no+ "번째 글 내용\"}";
	}
	
	// 위 파라미터 방식과 아래 url 방식을 섞어서도 사용할 수 있지만 잘 사용하지는 않는다. 
	
	// get/java/1 <- 파라메터가 url에 포함되어있는 경우
	@GetMapping(value="/get/{lec}/{no}") // 매칭되게 해주기 위해 PathVariable 어노테이션을 사용해줘야 한다.
	@ResponseBody
	public String urlPath(@PathVariable String lec, @PathVariable String no) {
		return "{\"msg\":\""+lec+"과목의"+no+ "번째 글 내용\"}";
	}
	
	@GetMapping(value="/strMap")
	@ResponseBody
	public Map<String, Object> strMap() throws Exception{

		// java에서 string을 map 형태로 바꿔서 보낼 경우 
		String json = "{\"no\":1, \"msg\" : \"Map 변환 완료\", \"name\":\"김지원\"}";
		// String 을 Map 으로 바꾸려면 라이브러리가 필요하다. (기본제공x)
		
		ObjectMapper mapper = new ObjectMapper(); // map 말고 list class 등으로도 바꿀 수 있다.
		// (json 문자열, 변환하고 싶은 클래스)
		// 받는 클래스가 제너릭이 있는데, 변환할 클래스에 제너릭이 없으면 명시하라고 경고를 날린다.
		// 변한할 때 제너릭이 명시되어 있어 변환 시 효율적이다.
//		HashMap<String, Object> map = mapper.readValue(json, new TypeReference<HashMap<String, Object>>() {});
		HashMap<String, Object> map = mapper.readValue(json, HashMap.class); // 노란줄 상관 없고 효율 안중요하면 그냥 써도 됨
		
		return map;
	}
	
	@GetMapping(value="/strObj")
	@ResponseBody
	public UserInfo strObj() throws Exception {
		
		String json = "{\"id\": \"admin\", \"name\" : \"김지훈\", \"age\": 55, \"success\":true}";
		ObjectMapper mapper = new ObjectMapper();
		// 제너릭이 없으면 시비걸지 않는다.
		UserInfo info = mapper.readValue(json, UserInfo.class); // 제너릭이 없으면 당연 경고도 없다!
		
		return info;
	}
	
	
	
}
