package kr.co.gudi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	// 사용할 암호화 클래스 등록
	// 		-> 이걸 해줘야 어느 클래스에던지 @Autowired 해서 BCryptPasswordEncoder 를 사용할 수 있다.
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	public SecurityFilterChain filterChanin(HttpSecurity http) throws Exception {
		// http는 스프링 시큐리티에서 사용하거나 사용하지 않을 기능에 대한 정의를 해줄 것이다.
		http.httpBasic().disable(); // 기본 기능을 disable (사용x)
		return http.build();
	}
}
