package hello.servlet.basic.request;

import java.io.IOException;
import java.util.Enumeration;
import java.util.stream.Stream;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * 1. 파라미터 전송 기능
 * http://localhost:8080/request-param?username=MarthaMeitner&age=34
 * 
 * 2. 동일한 파라미터 전송 가능
 * http://localhost:8080/request-param?username=Martha&username=Meitner&age=34
 * 
 */
@WebServlet(name = "requestParamServlet", urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {
	
	/* "파라메터"
	 * HTTP 요청에서 클라이언트가 서버로 데이터를 전달하는 방식 중 하나로,
	 * URL의 쿼리 스트링(쿼리 파라미터) 또는 HTTP 요청 바디(폼 데이터)에 포함되어 전송됨. 
	 * 
	 * 1. 쿼리 파라미터 : 데이터를 URL에 포함하여 전달, 주로 GET 요청에서 사용.
	 * 2. 폼 데이터 : HTML <form> 태그를 통해 전달되는 데이터.
	 *  Content-Type : application/x-www-form-urlencoded 형식으로 전송해야 함.
	 *  주로 POST 요청에서 사용
	 *  
	 */

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("[전체 파라미터 조회] - start");
		request.getParameterNames().asIterator()
				.forEachRemaining(paramName -> System.out.println(paramName + " : " + request.getParameter(paramName)));
		System.out.println("[전체 파라미터 조회] - end");
		System.out.println();

		System.out.println("[단일 파라미터 조회]");
		String username = request.getParameter("username");
		System.out.println("request.getParameter(username) : " + username);

		String age = request.getParameter("age");
		System.out.println("request.getParameter(age) : " + age);
		System.out.println();

		System.out.println("[이름이 같은 복수 파라미터 조회]");
		System.out.println("request.getParameterValues(username)");
		Stream.of(request.getParameterValues("username")).forEach(name -> System.out.println("username : " + name));

		response.getWriter().write("ok");
	}

}
