package hello.servlet.basic;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*  
 * ***** 서블릿 컨테이너(Servlet Container) *****
 * 
 * 1. 서블릿 생명주기 관리 : 서블릿의 생성, 초기화, 호출, 종료를 관리
 * 2. HTTP 요청 및 응답 처리 : 클라이언트의 HTTP 요청을 분석하고, 응답 메세지를 생성.
 * 3. 서블릿과 웹 서버 간 통신 중재 : HTTP 요청을 서블릿으로 전달하고, 처리 결과를 웹 브라우저에 반환
 * 
 * ***** 내장 톰캣 서버의 생성 및 초기화 *****
 * 
 * 스프링 부트 애플리케이션이 실행되면...
 * 1. 톰캣 서버 시작(생성) : 스프링 부트는 내장된 톰캣 서버를 실행한다.
 * 2. 서블릿 등록(초기화) : 애플리케이션 코드에서 정의된 @WebServlet을 서블릿 컨테이너에 등록한다
 * 
 * ***** 서블릿 컨테이너의 요청-응답 처리 흐름 *****
 * 
 * 1. HTTP 요청 수신
 *     사용자가 브라우저에서 URL에 접속함. (ex : http://localhost:8080/hello?username=Martha)
 *     웹 브라우저는 HTTP 요청 메세지를 WAS로 전송함. 
 *     
 * 2. HTTP 요청 파싱 및 서블릿 호출
 *    
 *     서블릿 컨테이너는 수신된 HTTP 요청을 분석하고 
 *     "요청 URL(/hello)이 매핑된 서블릿(HelloServlet)을 찾아 호출한다."
 *      이 과정에서 *HttpServletRequest*와 *HttpServletResponse* 객체가 생성된다.

 *         HttpServletRequest : 클라이언트의 요청 정보를 담음.(요청 파라미터, 헤더, URL)
 *         HttpServletReponse : 클라이언트로 보낼 응답 정보를 담음.(헤더, 상태코드, 바디) 
 *         
 * 3. 서블릿 처리 : 매핑된 서블릿의 service() 메서드가 실행된다.
 * 
 * 4. HTTP 응답 생성 : 서블릿 컨테이너는 HttpServletReponse 객체에 들어있는 내용을 기반으로 
 * 					 HTTP 응답 메세지를 생성.
 * 
 * 5. HTTP 응답 반환 : 서블릿 컨테이너는 생성한 HTTP 응답 메세지를 클라이언트(웹 브라우저)로 전송함.
 * 
 */

@WebServlet(name = "helloServlet", urlPatterns = "/hello")
/*
 * @WebServlet : 서블릿 클래스를 특정 URL에 매핑하는데 사용.
 * name = "helloServlet" : 서블릿의 이름
 * urlPatterns : 이 서블릿이 처리할 URL 매핑 패턴
 */

/* 
 * 
 */
public class HelloServlet extends HttpServlet {
	/*
	 * class HttpServlet 클래스 상속 : 자바 서블릿 API에서 제공하는 기본 서블릿 클래스
	 * 	ㄴ> service()를 재정의 하여 HTTP 요청을 처리함.
	 */

	@Override
	/*
	 * HTTP 요청을(Get Request) 받아 처리하고 응답을(Set Response) 생성하는 메서드
	 * HTTP 요청을 통해 mapping 된 URL이 호출되면 서블릿 컨테이너가 service() 메서드를 실행시킨다.
	 * 즉, 클라이언트가 /hello URL로 요청을 보내면 service() 메서드가 호출됨.
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("HelloServlet.service");
		System.out.println("request : " + request);
		System.out.println("response : " + response);
		
		String username = request.getParameter("username"); // 요청 파라미터 처리
		System.out.println("username : " + username);
		
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write("hello " + username); // 응답 데이터 작성
	}

}
