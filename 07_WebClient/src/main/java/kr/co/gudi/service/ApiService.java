package kr.co.gudi.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.BodyInserters.FormInserter;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class ApiService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	
	/*
	 * 본래 스프링에서는 HttpConnection 라이브러리를 사용했다.
	 * 이후 RestTemplate 이 나오고 다음으로 WebClient 가 나왔다.
	 * WebClient 는 Spring 5.0 이상 Boot 는 2.0 이상
	 * 장점 1 : Non-Blocking 방식(비동기) 이라 속도가 빠르다.
	 * 장점 2 : 다양한 메서드를 지원해준다
	 */
	
	
	public Map<String, String> getSend(String msg) {
		
		// 인터페이스라 new 사용 x
		// 기본 url 과 함께 WebClient 생성
		WebClient client = WebClient.create("http://localhost");
		
		// 1. 전송방식
		Mono<HashMap> mono = client.get()
		.uri("/return/"+msg) // 2. 추가 url (get 방식의 경우 여기에 파라미터를 넣어도 된다.)
		.retrieve() // 3. 전송 방식 중 하나
		.bodyToMono(HashMap.class); // 4. 반환 받고 싶은 타입 지정(요청 처리 방식 중 하나이다.)
		
		// retrieve() : 전송 후 보내온 바디값을 반환하는 메서드
		// exchange() : 전송 후 보내온 바디는 물론, 상태값과 header 값도 받아온다.
		
		// bodyToMono() : 1개의 단건 요청을 처리할 때 주로 사용한다. (동기방식)
		// bodyYoFlux() : n개의 여러 요청을 처리할 때 사용한다. (비동기방식)
		
		// mono 로 부터 데이터 가져오기
		HashMap<String, String> resp =  mono.block(); // block 를 사용하면 여기서 막아준다.
		logger.info("response => " + resp);
		
		return resp;
	}


	public ArrayList<HashMap<String, Object>> postSend(String cnt, String key) {
		
		// 1. webclient 호출
		WebClient client = WebClient.create("http://localhost");
		
		// post 방식으로 파라메터를 보내고 싶을 경우
		// MultiValueMap 은 같은 이름으로 여러개가 쌓인다. (기존 Map은 덮어쓴다.)
		//	-> 이건 java에서 제공하는게 아닌 Spring에서 제공해주는 것이다.
		FormInserter<String> form = BodyInserters.fromFormData("cnt", cnt);
		form.with("name", "김지원"); // MultiValueMap 을 사용하지 않고 추가로 파라메터를 넣고싶을 경우 사용
		
	
		Mono<ArrayList> mono = client.post().uri("/listReturn")
		.header("authorization", key) // 헤더에 무언가 넣고 싶을 경우 사용 (키, 값 여러개 넣을 수 있음)
		.body(form).retrieve().bodyToMono(ArrayList.class);
		
		
		
		// block() 은 동기방식 이기 때문에 효율성이 떨어진다
		// 그렇기 때문에 초기 test 용으로 사용하길 권고하고 있다.
		// ArrayList<HashMap<String, Object>> list = mono.block(); // bolack 하면 ArrayList가 나타나게 됨
		
		// flux : 비동기로 한번에 밀려들어오는 애 (순서 x 막 들어옴) -> 던진게 하나씩 들어올 수 있게 처리해줘야 함
		// mono : 하나가 포장되어 들어오는 형태 (하니씩 던짐)
		// 	toStream() : 데이터를 하나씩 빼는 형태
		// 		=> 스트림은 배열에서 하나씩 빼서 처리하는 클래스 (for문을 돌리는 것보다 깔끔하지만 속도는 for문보다 느리다.)
		// 하나로 던져서 주기 때문에 forEach로 뽑지 않고 하나만 (첫번째) 뽑아옴
		ArrayList<HashMap<String, Object>> list = mono.flux().toStream().findFirst().get(); 
		
		// 한건씩 던지는게 많으면 mono / 한번에 쭉 들어오는게 많으면 flux
		//	ex) 당첨자 만명에게 문자 보냄 - flux
		// 		오픈런 때문에 순차적으로 번호표를 줘야함 - mono
		// 즉 순차적이 필요할 땐 mono , 순서가 필요 없을때는 flux
		
		// 400 에러 : 파라미터 값이 잘못 들어갔을 때 나는 에러 ex) 학번, 이름을 보내야 하는데 이름만 보낸 경우...
		
		return list;
	}


	public List<HashMap<String, Object>> fluxTest() {
		
		WebClient client = WebClient.create("http://localhost");
		
		// 파라미터 만들기
		HashMap<String, Object> json = new HashMap<>();
		json.put("age", 30);
		json.put("name", "kim");
		json.put("married", false);
		json.put("scores", new int[] {30,40,50,60,70,80,90,100});
		
		// 위와 같이 복잡한 json 형태를 전송할 경우 bodyValue 를 사용한다.
		// 일반적인 post 방식에는 body() 를 사용
		// 복잡한 json형태는 body를 실어서 보내야 하기 때문에 post 사용
		List<HashMap<String, Object>> list = client.post().uri("/fluxReturn").bodyValue(json)
		.retrieve().bodyToFlux(HashMap.class).toStream().collect(Collectors.toList());
		// collect : 모으다 (한꺼번에 나오는 애들을 모아준다.)
		//		-> collector 에는 컬렉션 프레임워크를 사용할 수 있다.
		
		return list;
	}

}
