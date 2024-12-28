package hello.servlet.web.frontcontroller.v3;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {
	
	private Map<String, ControllerV3> controllerMap = new HashMap<>();

	public FrontControllerServletV3() {
		initControllerMap();
	}

	private void initControllerMap() {
		controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
		controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
		controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String requestURI = request.getRequestURI();

		// 1. URL 매핑 정보를 확인
		ControllerV3 controller = controllerMap.get(requestURI);
		if (controller == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		// 2-0. 리퀘스트에 담긴 파라메터를 Map<paramName, value> 형식으로 만듬
		Map<String, String> paramMap = createParamMap(request);
		// 2-1. 컨트롤러에서 호출 : controller.process();
		ModelView mv = controller.process(paramMap);
		// 2-2. 컨트롤러 로직에서 ModelView에 "미완성된 뷰 이름" & "모델" 데이터를 담아 반환
		
		/*
		 * ViewResolver는 ModelView 객체에 담긴 String viewName(논리 이름)을 
		 * 기반으로(Controller 상에서 제공) 실제 브라우저 상에서 쓰이게 될 
		 * *실제 물리 위치인 URL 주소를 완성시킨다.
		 */
		String viewName = mv.getViewName();
		// 3. viewResolver 호출 : viewName 기반으로 MyView 객체 생성
		MyView view = viewResolver(viewName);		
		
		/*
		 * 4. myView의 render(model) 호출
		 * 	현재, request 속에는 아무런 파라메터도 존재하지 않음.
		 * 	대신, Controller 상에서 전달받은 ModelView의 modelMap<String, Object> 를 기반으로
		 *  "view.render() 안에서 실질적으로 request 객체 안에 파라메터를 설정한다. 
		 *  	ㄴ> request.setAttribute();"	 
		 */
		view.render(mv.getModel(), request, response);
		
	}

	// HttpServletRequest에서 파라미터 정보를 꺼내에 Map으로 변환한다.
	private Map<String, String> createParamMap(HttpServletRequest request) {
		Map<String, String> paramMap = new HashMap<>();
		request.getParameterNames().asIterator()
			.forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
		return paramMap;
	}

	// 컨트롤러가 반환한 논리 뷰 이름을 실제 물리 뷰 경로로 변경한다.
	private MyView viewResolver(String viewName) {
		return new MyView("/WEB-INF/views/" + viewName + ".jsp");
	}
}
