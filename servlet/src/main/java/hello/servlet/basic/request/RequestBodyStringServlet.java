package hello.servlet.basic.request;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.util.StreamUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * ***** POST HTML Form 데이터 *****
 * 
 * 1. HTML Form 전송 방식 : 사용자가 웹 폼을 통해 데이터를 전송할 때, content-type은 
 * 	    ** application/x-www-form-urlencoded ** 로 설정됨
 * 	    이 방식은 ** 데이터를 HTTP 메세지 바디에 포함 **시켜서 서버로 보내는 방식이다.
 * 
 * 2. 쿼리 파라미터와의 차이점 
 * 	GET 방식은 URL의 쿼리 문자열에 데이터를 포함시킨다. : /url?username=Marthar&age=34
 *  ** POST 방식은 데이터를 HTTP 메세지 바디에 담아 전송한다 **
 *  클라이언트 측에서는 content-type에 따라 서버로 전달되는 방식이 달라진다.
 * 
 * 3. Content-Type과 메세지 바디
 *  GET 요청에서는 content-type이 없고, URL을 통해 쿼리 파라메터로 데이터를 저장한다.
 *  ** POST 요청에서는 content-type을 명시하여 데이터 형식을 지정해야 한다. **
 *  대표적으로, ** application/x-www-form-urlencoded가 있다 ** 
 *  ***********************************************************************
 *  *폼 데이터를 이 방식으로 전송하면 HTTP 메세지 바디에 쿼리 파라미터 형식으로 데이터를 포함시킨다.*
 *  ***********************************************************************
 */

@WebServlet(name = "requestBodyStringServlet", urlPatterns = "/request-body-string")
public class RequestBodyStringServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		ServletInputStream inputStream = request.getInputStream();
		String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
		
		System.out.println("messageBody : " + messageBody);
		response.getWriter().write("you input : " + messageBody);
	}
	
	
}
