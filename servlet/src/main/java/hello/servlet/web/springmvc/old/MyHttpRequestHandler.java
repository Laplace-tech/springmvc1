package hello.servlet.web.springmvc.old;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * 1. 핸들러 매핑으로 핸들러(컨트롤러) 조회
 * 	ㄴ> HandlerMapping을 순서대로 실행해서 해당 HTTP 리퀘스트로 들어온 URL를 처리할 수 있는
 *     핸들러를 찾는다. 이 경우에는 "빈 이름"으로 핸들러를 찾아야 하기 때문에, 
 *     이름 그대로 빈 이름으로 핸들러를 찾아주는 BeanNameUrlHandlerMapping이
 *     MyHttpRequestHandler를 찾기 위한 수단으로서 룩업에 성공한다. 
 *     
 *     Object handler = getHandler(request); : MyHttpRequestHandler
 *     						ㄴ> { return handlerMappingMap.get(requestURI); }
 *     
 * 2. 주어진 핸들러를 처리할 수 있는 핸들러 어댑터 조회
 * 	ㄴ> HandlerAdapter의 supports()를 순서대로 호출한다. 
 * 	   HttpRequestHandlerAdapter가 HttpRequestHandler 인터페이스를 구현하므로 
 *     (class HttpRequestHandlerAdapter implements HandlerAdapter)
 * 	   핸들러 어댑터의 대상이 된다. 
 * 
 * 	  HandlerAdapter adapter = getHandlerAdapter(handler : MyHttpRequestHandler); : HttpRequestHandlerAdapter
 * 								   ㄴ> { return handlerAdapters.forEach&filter(isSupport(handler)); }
 * 	   
 * 3. 핸들러 어댑터 실행
 * 	ㄴ> 디스패처 서블릿이 조회한 HttpRequestHandlerAdapter를 실행하면서 
 * 	   핸들러(handler : MyHttpRequestHandler) 정보도 함께 넘겨준다. 
 * 	   HttpRequestHandlerAdapter가 MyHttpRequestHandler를 내부에서 실행하고,
 *     그 결과를 반환한다.
 * 
 * 	   ModelAndView mv = adapter.handle(request, response, handler:MyHttpRequestHandler)
 *     
 *     따라서 어댑터 MyHttpRequestHandler 를 실행하면서 사용된 객체는 다음과 같다.
 *     1. HandlerMapping = BeanNameUrlHanlderMapping
 *     		ㄴ> HTTP 리퀘스트의 URL 정보로 어느 핸들러로 매핑시킬 지를 처리함.
 *     2. HandlerAdapter = HttpRequestHandelrAdapter
 * 
 */
@Component("/springmvc/request-handler")
public class MyHttpRequestHandler implements HttpRequestHandler {

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("MyHttpRequestHandler.handleRequest");
	}

}
