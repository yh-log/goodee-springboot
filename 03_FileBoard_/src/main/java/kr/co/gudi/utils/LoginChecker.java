package kr.co.gudi.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

// 이 클래스는 인터셉터에서 객체화 해서 쓸래?
// @AutoWierd 로 끌어 당겨 쓰려면 @Componenet 선언
@Component
public class LoginChecker implements HandlerInterceptor {

	Logger logger = LoggerFactory.getLogger(getClass());

	// 컨트롤러 들어가기 전에 체크
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		logger.info("===PRE HANDLER===");
		
		HttpSession session = request.getSession();
		if(session.getAttribute("loginId") == null) {
			
			String ctx = request.getContextPath(); // context 경로 뽑아오기
			logger.info("경로 => " + ctx);
			
			response.sendRedirect(ctx);
		}
		
		
		// 만약 통과가 안되면 response 나 request를 이용해 다른 페이지로 이동시킨다.
		return true; // true 면 컨트롤러로 접근 가능 / false 면 컨트롤러로 못가고 그냥 하얀 화면만 켜진다.
	}

	// 컨트롤러를 지나 뷰에 가기 전에 체크
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		logger.info("===POST HANDLER===");
		// 뷰에 전달할 내용을 ModelAndView에 담아 보낼 수 있다.
	}
	
	
	
}
