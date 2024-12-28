package hello.servlet.web.frontcontroller.v2;

import java.io.IOException;

import hello.servlet.web.frontcontroller.MyView;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

public interface ControllerV2 {

	/*
	 * 기존의 ControllerV1에서는 process() 에서 Dispatcher 객체를 생성하여
	 * 직접 JSP로 포워드 했으나, V2부터는 뷰 경로(View Path)를 담은 MyView 객체를 만들어 
	 * 자신을 호출한 클래스(FrontController : 디스패처 서블릿)로 반환한다.
	 * 
	 * 이를 반환받은 프론트 컨트롤러 또는 디스패처 서블릿은 MyView의 render()를 통해 JSP로 포워딩함.
	 */
	MyView process(HttpServletRequest request, HttpServletRequest response) throws ServletException, IOException;
	
}
