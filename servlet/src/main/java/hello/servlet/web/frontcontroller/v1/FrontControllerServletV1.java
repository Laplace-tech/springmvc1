package hello.servlet.web.frontcontroller.v1;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.format.annotation.DurationFormat.Unit;

import hello.servlet.web.frontcontroller.v1.controller.MemberFormControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberListControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberSaveControllerV1;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "frontControllerServletV1", urlPatterns = "/front-controller/v1/*")
/*
 * urlPatterns = "/front-controller/v1/*" 
 * 	/front-controller/v1/로 시작하는 모든 요청이 이 서블릿으로 들어온다. 
 */
public class FrontControllerServletV1 extends HttpServlet {

	private Map<String, ControllerV1> controllerMap = new HashMap<>();

	public FrontControllerServletV1() {
		initControllerMap();
	}
	
	private void initControllerMap() {
		controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
		controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
		controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		System.out.println("Request URI : " + requestURI);
		
		ControllerV1 controller = getController(requestURI, response);
		if(controller == null) {
			return;
		}
		
		// 2. 컨트롤러 로직 호출.
		controller.process(request, response);
		// 3. 컨트롤러 내부에서 JSP 포워드
	}
	
	
	/*
	 * 1. URL 매핑 정보에서 컨트롤러 조회 : 서블릿에 수신된 리퀘스트의 URL을 보고
	 * 		이를 처리할 수 있는 컨트롤러가 있는지 룩업함
	 */
	private ControllerV1 getController(String requestURI, HttpServletResponse response) {
		ControllerV1 controller = controllerMap.get(requestURI);
		if(controller == null)
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		
		return controller;
	}
	
	
}
