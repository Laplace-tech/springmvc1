package hello.servlet.web.frontcontroller.v3;

import java.io.IOException;
import java.util.Map;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

public interface ControllerV3 {

	/*
	 * ControllerV2 : MyView process(HttpServletRequest request, 
	 * 		HttpServletRequest response) throws ServletException, IOException;
	 * 
	 * ControllerV2와 아래 ControllerV3를 비교해보자.
	 * 	
	 * 	MyView가 ModelView로 바뀌었고 process 메서드의 파라메터가 paramMap으로 바뀌었다.
	 * 	V2에서 HttpServletRequest의 용도는 첫 번째로 "리퀘스트로 들어온 파라메터를 꺼내고"
	 * 	두 번째로는 "뷰에 전달하기 위한 데이터를 넣어놓는 것"이었다.
	 * 
	 *  그리고 HttpServletResponse는 쓰지도 않는다.
	 *  
	 *  그래서 말인데 이 역할을 paramMap으로 대신하자. 그렇게 해서 리퀘스트 바디에 JSON 형식으로
	 *  존재하는 데이터들을 맵으로 옮겨서 디스패처 서블릿에서 컨트롤러로 넘길 수 있다 -> controller.process(paramMap)
	 * 
	 *  Question : 그럼 뷰에 띄우기 위한 객체 데이터는요? 
	 *  	ㄴ> 그래서 ModelView를 만듬. ModelView에는 기존에 MyView에 있던 "viewPath 처리를 위한
	 *  		 *논리 이름을 담고 있고 : String viewName" (ModelView 객체 생성시 생성자에 인수로 제공해야 함)
	 *  		"모델 데이터 전달을 위한 필드도 존재함 : Map<String, Object> modelMap"
	 *  
	 *  		Controller 는 request.setAttribute로 모델 데이터를 저장하는 대신
	 *  		ModelView 객체를 생성하여 그 내부의 modelMap<String, Object>에 
	 *  		저장하면 향후 MyView의 render() 메서드에서 이를 기반으로
	 *  		실제 requestServlet을 사용하여 setAttribute함.
	 */
	ModelView process(Map<String, String> paramMap);
	
}
