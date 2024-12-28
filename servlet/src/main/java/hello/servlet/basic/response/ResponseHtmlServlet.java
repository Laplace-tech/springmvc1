package hello.servlet.basic.response;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "responseHtmlServlet", urlPatterns = "/response-html")
public class ResponseHtmlServlet extends HttpServlet {

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		// 응답의 Content-Type을 text/html로 설정하여 브라우저에게 HTML 문서임을 알림
		response.setCharacterEncoding("utf-8");
		// 다국어 문자를 출력하도록 utf-8로 설정

		/*
		 * 클라이언트로 데이터를 전송하도록 PrintWriter 객체를 가져옴 : getWriter();
		 */
		PrintWriter writer = response.getWriter();
		writer.println("<html>");
		writer.println("<body>");
		writer.println("<div>안녕?</div>");
		writer.println("</body>");
		writer.println("</html>");

	}

}
