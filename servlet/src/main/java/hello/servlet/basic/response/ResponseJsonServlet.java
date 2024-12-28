package hello.servlet.basic.response;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import hello.servlet.basic.HelloData;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "responseJsonServlet", urlPatterns = "/response-json")
public class ResponseJsonServlet extends HttpServlet {

	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		/*
		 * HTTP 응답으로 JSON을 반환할 때는 "content-type을 application/json" 으로 지정
		 * objectMapper.writeValueAsString() 를 사용하여 객체를 JSON 문자로 변경할 수 있음.
		 */
		response.setHeader("content-type", "application/json");
		response.setCharacterEncoding("utf-8");
		
		HelloData data = new HelloData();
		data.setUsername("Martha Meitner");
		data.setAge(32);
		
		/*
		 * data 객체를 JSON 형식으로 변환 
		 */
		String result = objectMapper.writeValueAsString(data);
		response.getWriter().write(result);
	}

}
