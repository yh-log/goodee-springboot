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

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChanin(HttpSecurity http) throws Exception {
		// 403 : 권한 없음
		// csrf() 추가 -> disable() 로 기능 꺼주기
		http.httpBasic().disable().csrf().disable();
		
		// CSRF (Cross Site Request Forgety) : 서로 다른 사이트에서 오는 요청에 대한 공격(변조)
		// A 사이트를 가려고 하는데, 실수로 B 사이트로 가게 되었을 때 B에서 A에게 요청을 보낸다.
		// 		ex) naver 로 가려고 했는데 naven 으로 오타를 내서 이동했는데 이동을 잘 갔다. 
		//			여기서 로그인 시도를 하면 naver에 요청을 보내고 로그인에 성공하면 해킹에 걸린다.
		
		// => 그래서 A의 입장에서는 자신에게 온 요청이 A의 것인지 확인해야 한다,
		//	  그래서 csrf 토큰 이라는 녀석을 지속적으로 갱신하고, 요청시 이 토큰을 확인하려고 한다.
		//	  그런데 요청을 보낼 때 이 csrf 토큰이 없거나 틀리면 권한 없음(403) 으로 간주한다.
		
		// 그래서 시큐리티를 사용하려면 이걸 처리해줘야 한다.
		
		return http.build();
	}
	
}
