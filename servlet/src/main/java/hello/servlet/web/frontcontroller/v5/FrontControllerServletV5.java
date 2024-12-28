package hello.servlet.web.frontcontroller.v5;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

	// ControllerV3, ControllerV4 같은 인터페이스 대신 아무 값이나 받을 수 있는 Object로 변경되었다.
	private final Map<String, Object> handlerMappingMap = new HashMap<>();
	private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

	public FrontControllerServletV5() {
		initHandlerMappingMap();
		initHandlerAdapters();
	}

	private void initHandlerMappingMap() {
		handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
		handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
		handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());
	
		 //V4 추가
	    handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
	    handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
	    handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
	}

	private void initHandlerAdapters() {
		handlerAdapters.add(new ControllerV3HandlerAdapter());
		handlerAdapters.add(new ControllerV4HandlerAdapter());
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 1. 핸들러(컨트롤러) 조회 : 핸들러 매핑 정보 -> handlerMappingMap.get(request.URI)
		// 	ㄴ> 해당 HTTP 리퀘스트를 처리할 수 있는 컨트롤러가 있는지 룩업하는 과정.
		Object handler = getHandler(request);
		if(handler == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		// 2. 핸들러를 처리할 수 있는 어댑터 조회
		MyHandlerAdapter adapter = getHandlerAdapter(handler);
		
		// 3. handle(handler)
		// 4. controller = (ControllerVX) handler
		//       ㄴ> controller.process 호출 
 		// 				ㄴ> 5. ModelView 반환
 		ModelView mv = adapter.handle(request, response, handler);
 		
 		// 6. viewResolver 호출 & 7. MyView 반환
 		MyView view = viewResolver(mv.getViewName());
 		
 		// 8. render(model) 호출
 		view.render(mv.getModel(), request, response);
	}

	// handlerMappingMap에서 URL에 매핑된 핸들러(컨트롤러) 객체를 찾아서 반환한다.
	private Object getHandler(HttpServletRequest request) {
		String requestURI = request.getRequestURI();
		return handlerMappingMap.get(requestURI);
	}

	// handler를 처리할 수 있는 어댑터를 adapter.supports(handler)를 통해 찾는다.
	private MyHandlerAdapter getHandlerAdapter(Object handler) {
		return handlerAdapters.stream()
				.filter(adapter -> adapter.supports(handler))
				.findAny()
				.orElseThrow(() -> new IllegalArgumentException("handler adapter를 찾을 수 없습니다. handler=" + handler));
	}

	private MyView viewResolver(String viewName) {
		return new MyView("/WEB-INF/views/" + viewName + ".jsp");
	}

}