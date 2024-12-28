package hello.servlet.web.frontcontroller.v2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.controller.MemberFormControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberListControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberSaveControllerV2;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "frontControllerServletV2", urlPatterns = "/front-controller/v2/*")
public class FrontControllerServletV2 extends HttpServlet {

	private Map<String, ControllerV2> controllerMap = new HashMap<>();

	public FrontControllerServletV2() {
		initControllerMap();
	}
	
	private void initControllerMap() {
		controllerMap.put("/front-controller/v2/members/new-form", new MemberFormControllerV2());
		controllerMap.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
		controllerMap.put("/front-controller/v2/members", new MemberListControllerV2());
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		System.out.println("Request URI : " + requestURI);
		
		ControllerV2 controller = getController(requestURI, response);
		if(controller == null) {
			return;
		}
		
		/*
		 * 2. 컨트롤러 호출 : controller.process();
		 * 	각각의 컨트롤러가 dispatcher.forward()를 호출하는 대신 
		 * 	String viewPath가 들어있는 MyView 객체를 반환하게 하여 중복을 제거한다.
		 */
		MyView myView = controller.process(request, request);
		// 3. MyView 반환
		
		// 4. myView.render() 호출
		myView.render(request, response);
		// 5. myView.render() 내부에서 "viewPath 데이터 필드"를 통해 JSP로 포워드 한다.
	}
	
	
	// 1. URL 매핑 정보에서 컨트롤러 조회
	private ControllerV2 getController(String requestURI, HttpServletResponse response) {
		ControllerV2 controller = controllerMap.get(requestURI);
		if(controller == null)
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		
		return controller;
	}
	
	
	
}
