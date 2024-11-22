package kr.co.gudi.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.DefaultUriBuilderFactory.EncodingMode;

import reactor.core.publisher.Mono;

@Service
public class ApiService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	private final String key = "UddzTt5gm3RvChbgUjWt9blg3VVJu0IF4TfEULdi140e%2F1Aj%2BWMrgitUyyxDwYDl8tNNE9uJgFFnnp3kUun%2BGA%3D%3D";
	private final String url = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/";
	
	// Encoding key (인코딩 된 키)
	// UddzTt5gm3RvChbgUjWt9blg3VVJu0IF4TfEULdi140e%2F1Aj%2BWMrgitUyyxDwYDl8tNNE9uJgFFnnp3kUun%2BGA%3D%3D

	// Decoding key (디코딩 된 키)
	// UddzTt5gm3RvChbgUjWt9blg3VVJu0IF4TfEULdi140e/1Aj+WMrgitUyyxDwYDl8tNNE9uJgFFnnp3kUun+GA==

	public Map<String, Object> apiCall(Map<String, String> params) {
		
		// WebClient 사용을 위해 선언
	//	WebClient client = WebClient.create();
		
		String baseUrl = url+params.get("req");
		params.put("serviceKey", key);
		params.remove("req");
		
		// key가 get방식 이므로 url에 노출되어서 나가게 된다.
		// 이때 변형이 생기므로 사전에 처리를 해줘야 한다.
		DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(baseUrl); // base url을 넣어준다.
		factory.setEncodingMode(EncodingMode.VALUES_ONLY);
		
		WebClient client = WebClient.builder().uriBuilderFactory(factory).baseUrl(baseUrl).build();
		
		String param = "?";
		
		// 엔트리 셋, 키 셋을 이용하는 방법 등이 있음
		// key
		for (String key : params.keySet()) {
			param += key + "=" + params.get(key) + "&";
		}
		
		
		// [문제] for문으로만 돌리기에는 문제가 있음!! 
		// &req=getUltraSrtNcst& -> 이 내용이 같이 붙어서 들어감 (그리고 끝에 &가 붙음)
		// 서비스 키가 없음
		// 시실 & 가 붙여 나가도 상관은 없다. 그런데 보기 좋아야 하니까 짜르는게 좋다 (UI 적으로 볼 때)
		
		param = param.substring(0, param.length() -1);
//		param = param.substring(0, param.lastIndexOf("&"));
		
		logger.info("param => " + param);
		logger.info(baseUrl + param); // 파라메터로 올바른 url이 나오고 있는지 확인
		Mono<Map> mono = client.get().uri(param).retrieve().bodyToMono(Map.class); // 한 건이니까 mono로 가주고 Map 형태로 받아온다.
		
//		Map<String, Object> map = mono.block(); // 효율성이 떨어지기 때문에 비동기로 처리 필요
		Map<String, Object> map = mono.flux().toStream().findFirst().get(); // 스트림으로 해서 처음 것만 가져와서 처리해라
		
		logger.info("map => " + map);
		
		return map;
	}

}
