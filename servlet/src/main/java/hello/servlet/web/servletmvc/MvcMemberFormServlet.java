package hello.servlet.web.servletmvc;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "mvcMemberFormServlet", urlPatterns = "/servlet-mvc/members/new-form")
public class MvcMemberFormServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		String viewPath = "/WEB-INF/views/new-form.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
		dispatcher.forward(request, response);
		/*
		 * 포워드 VS 리다이렉트
		 * 
		 * dispatcher.forward() : 서블릿 간 이동, JSP로 포워딩
		 * 
		 * 포워드(forward)는 서버 내부에서 처리하는 방식이며, URL을 변경하지 않고 클라이언트가
		 * 이를 인식할 수 없다. 주로 서버간의 데이터 처리나 JSP 뷰 렌더링을 위해 사용함
		 * 즉, 클라이언트는 URL이 변경되지 않고, 동일한 URL로 계속 작업을 이어나간다.
		 * 브라우저의 주소표시줄 URL은 변하지 않는다. 요청이 서버 내부적으로 다른 리소스로 전달된다.
		 * 
		 * response.sendRediect() : 클라이언트에게 새로운 요청을 보냄
		 * 
		 * 클라이언트에게 HTTP 응답을 보내고, 클라이언트는 새로운 URL로 요청을 다시 보낸다.
		 * 클라이언트는 URL 변경을 인식한다. 클라이언트를 다른 페이지로 이동하도록 유도할 때 사용
		 */
	}
	
	
	
}
