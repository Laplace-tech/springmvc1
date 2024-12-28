package hello.servlet.web.frontcontroller.v4;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "frontControllerServletV4", urlPatterns = "/front-controller/v4/*")
public class FrontControllerServletV4 extends HttpServlet {

	private Map<String, ControllerV4> controllerMap = new HashMap<>();

	public FrontControllerServletV4() {
		initControllerMap();
	}

	private void initControllerMap() {
		controllerMap.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
		controllerMap.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
		controllerMap.put("/front-controller/v4/members", new MemberListControllerV4());
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String requestURI = request.getRequestURI();

		// 1.컨트롤러 조회
		ControllerV4 controller = controllerMap.get(requestURI);
		if (controller == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		// 2-0-1. "이미 수신된 리퀘스트의 파라미터"를 담는 paramMap 
		// 2-0-2. "데이터를 입힐 모델 객체들의 정보"를 담는 model
		Map<String, String> paramMap = createParamMap(request);
		Map<String, Object> model = new HashMap<>();
		
		// 2. 위 둘을 갖고 controller 호츌 : controller.process(paramMap, model); 
		String viewName = controller.process(paramMap, model);
		// 3. viewName 반환
		
		// 4. viewResolver 호출.
		MyView view = viewResolver(viewName);
		// 5. viewResolver 반환 : myView 에는 완성된 viewPath가 있음
		
		// 6. render(model..) 호출 시, model 맵에 담긴 데이터를 토대로 실제 모델에 데이터를 입힘.
		//   							ㄴ> request.setAttribute(key, value)
		view.render(model, request, response);
		
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
