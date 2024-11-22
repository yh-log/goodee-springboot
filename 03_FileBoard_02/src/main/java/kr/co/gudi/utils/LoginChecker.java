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
//@Autowierd 로 끌어당겨 쓰려면 @Componenet
@Component
public class LoginChecker implements HandlerInterceptor {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	// 컨트롤러에 들어가기 전에 체크
	@Override
	public boolean preHandle(HttpServletRequest request, 
				HttpServletResponse response, Object handler)
			throws Exception {
		
		logger.info("====PRE HANDLER====");
		// 만약 통과가 안되면 response 나 request 를 이용해 다른 페이지로 이동시킨다.
		HttpSession session = request.getSession();
		
		if(session.getAttribute("loginId") == null) {			
			String ctx = request.getContextPath();
			logger.info("context path : "+ctx);
			response.sendRedirect(ctx);
		}
				
		return true;// true : 컨트롤러로 접근/ false : 못가고 그냥 하얀화면
	}

	// 컨트롤러를 지나 뷰에 가기 전에 체크
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler,	ModelAndView modelAndView) throws Exception {
		logger.info("====POST HANDLER====");
		// view 에 전달할 내용을 ModelAndView 에 담아 보낼 수 있다.
		
		HttpSession session = request.getSession();
		
		String msg = "<p>안녕하세요,"+session.getAttribute("loginId")+"님!";
		msg += "<a href=\"member_logout.do\" class=\"btn minbtn\">로그아웃</a></p>";
		modelAndView.addObject("loginBox", msg);
	}
	
	

}










