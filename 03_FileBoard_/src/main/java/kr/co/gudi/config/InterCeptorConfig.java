package kr.co.gudi.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.co.gudi.utils.LoginChecker;

// 설정을 위한 클래스 표시 (이게 없으면 일반 클래스로 인식한다.)
@Configurable
public class InterCeptorConfig implements WebMvcConfigurer {

	@Autowired LoginChecker checker;
	
	// 뭔가를 추가하거나 수정할 때 이파일만 건들면 되기 때문에 결합도가 낮아지는 효과가 있다.
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		List<String> excludeList = new ArrayList<String>();
		// 경로는 ** 어떤 값은 * 로 표시
		excludeList.add("/resources/**"); // resources 이후 어떤 경로던지
		excludeList.add("/member_*"); // member_ 이후 어떤 값이던지
		excludeList.add("/checkId.*");
		excludeList.add("/");
		
		// registry 언제 어떤 일을 할건지 등록
		// /** : 모두 다 체크한다는 의미
		registry.addInterceptor(checker) // 인터셉터에서 실행할 클래스 지정
		.addPathPatterns("/**") // 어떤 패턴의 URL에 대해서 할 것인가
		.excludePathPatterns(excludeList); // 예외처리할 페이지 등록 (어떤 패턴을 제외시킬 것인지)
		
		
		
	}
	
	

}
