package hello.servlet.web.springmvc.old;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * 스프링MVC 구조
 * 
 * 1. 핸들러 조회 : 핸들러 매핑을 통하여 요청 URL에 매핑된 핸들러(컨트롤러)를 조회한다.
 * 2. 핸들러 어댑터 조회 : 핸들러를 실행할 수 있는 핸들러 어댑터를 조회한다.
 * 3. 핸들러 어댑터 실행 : 핸들러 어댑터(Interface)를 실행한다.
 * 4. 핸들러 실행 : 핸들러 어댑터가 실제 핸들러를 실행한다. -> adapter.handle(Object handler)
 * 5. ModelAndView 반환 : 핸들러 어댑터는 핸들러가 반환하는 정보를 ModelAndView로 변환해서 반환한다. (modelData & viewName)
 * 6. viewResolver 호출 : 뷰 리졸버를 찾고 실행한다.
 * 7. View 반환 : 뷰 리졸버는 View 의 논리 이름을 물리 이름으로 바꾸고, 렌더링 역할을 담당하는 뷰 객체를 반환한다.
 * 8. 뷰 렌더링 : 뷰를 통해서 뷰를 렌더링 한다.
 */

/*
 * 아래의 컨트롤러가 호출되기 위해서는 2가지가 필요하다.
 * 
 * -> 1. 핸들러 매핑으로 핸들러 조회 : HandlerMapping을 순서대로 실행해서, 핸들러를 찾는다
 * 			이 경우, 빈 이름으로 핸들러를 찾아야 하기 때문에 이름 그대로 빈 이름으로 핸들러를 찾아주는
 * 			BeanNameUrlHandlerMapping 가 실행에 성공하고 핸들러인 OldController를 반환한다.
 * 
 *  	ex : Object handler = getHandler(request) 
 *  	     handler(Controller) <- OldController 
 *    
 * -> 2. 핸들러 어댑터 조회 : handlerAdapter의 supports()를 순서대로 호출한다.
 * 			SimpleControllerHandlerAdapter가 Controller 인터페이스를 지원하므로 대상이 된다.
 * 
 * 		ex : HandlerAdapter adapter = getHandlerAdapter(handler : OldController)
 * 				
 * -> 3. 핸들러 어댑터 실행 : 디스패처 서블릿이 조회한 SimpleControllerHandlerAdapter를 실행하면서
 * 			핸들러 정보도 함께 넘어간다. SimpleControllerHandlerAdapter는 
 * 			핸들러인 OldController를 내부에서 실행하고, 그 결과(ModelAndView)를 반환한다.
 * 
 * 		ex : ModelView mv = adapter.handler(request, response, handler)
 * 
 * 따라서 어댑터 OldController를 실행하면서 사용된 객체는 다음과 같다
 * 1. HandlerMapping = BeanNameUrlHanlderMapping
 * 2. HandlerAdapter = SimpleControllerHandlerAdapter
 * 
 */

@Component("/springmvc/old-controller")
/*
 * @Component("/springmvc/old-controller")
 * 		ㄴ> /springmvc/old-controller 라는 이름의 스프링 빈으로 등록되었음.
 * 			스프링 빈의 이름으로 URL 매핑을 할 것임.
 */
public class OldController implements Controller {

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("OldController.handleRequest");
		return new ModelAndView("new-form");
	}

	
	
}
