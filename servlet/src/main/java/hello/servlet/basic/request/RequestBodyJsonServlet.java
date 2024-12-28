package hello.servlet.basic.request;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import hello.servlet.basic.HelloData;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * http://localhost:8080/request-body-json
 * 
 * JSON 형식 전송
 * Content-Type : application/json
 * message body : {
 *					"username" : "Marthar Meitner",
 *   				"age" : 35
 *				  }
 * 
 * 클라이언트와 서버 간의 데이터를 교환할 때 사용하는 구조화된 데이터 형식.
 * 주로, RESTful API에서 사용함.
 * 
 */
@WebServlet(name = "requestBodyJsonServlet", urlPatterns = "/request-body-json")
public class RequestBodyJsonServlet extends HttpServlet {

	/*
	 * JSON 데이터를 파싱하려면 JSON 변환 라이브러리를 사용하는 것이 필수적임.
	 * Spring Boot는 기본적으로 Jackson 라이브러리를 제공함.
	 * ObjectMapper를 사용하여 JSON 데이터를 자바 객체로 변환할 수 있음.
	 */
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		/*  
		 * 1. ServletInputStream으로 JSON 데이터 읽기
		 *   request.getInputStream()으로 요청 메세지 바디를 바이트 스트림으로 읽어옴.
		 *   StreamUtils.copyToString()을 사용해 UTF-8 문자셋으로 문자열로 반환함.
		 */
		ServletInputStream inputStream = request.getInputStream();
		// URL 쿼리 딸때는 getParam()을 쓰고 메세지 바디 딸려면 ServletInputStream을 씀.
		String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
		
		// 예: {"username": "Marthar Meitner", "age": 35}
		System.out.println("messageBody : " + messageBody);
		
		/*
		 * 2. Jaskson의 ObjectMapper로 JSON -> Java 객체 변환
		 * 	 ObjectMapper는 Jaskson 라이브러리가 제공하는 JSON 파싱 도구
		 * 	 objectMapper.readValue(messageBody, HelloData.class)를 통해 
		 * 	 JSON 데이터를 HelloData 클래스의 인스턴스로 변환함.
		 */
		HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
		System.out.println("helloData.username : " + helloData.getUsername());
		System.out.println("helloData.age : " + helloData.getAge());
		
		// 클라이언트에게 메세지 반환
		response.getWriter().write(helloData.toString());
	}
	
}
