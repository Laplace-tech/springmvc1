package hello.springmvc.basic.response;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import groovy.util.logging.Slf4j;
import hello.springmvc.basic.HelloData;
import jakarta.servlet.http.HttpServletResponse;

/*
 * [HTTP API, 메세지 바디에 직접 입력]
 * 
 * HTTP API를 제공하는 경우에는 HTML이 아니라 데이터를 전달해야 하므로
 * HTTP 메세지 바디에 JSON 형식으로 데이터를 실어보낼 것이다.
 * 
 * +(HTML 이나 뷰 템플릿을 사용해도 HTTP 응답 메세지 바디에 HTML 데이터가 담겨서 전달될 것이다.
 * 	 하지만, 여기서는 정적 리소스나 뷰 템플릿을 거치지 않고, 직접 HTTP 응답 메세지 바디에 작성하는 경우를 말한다. 
 * 
 */

@Slf4j
//@Controller
@RestController
/*
 * @Controller 대신에 @RestController를 사용하면, 해당 컨트롤러 안의 모든 메서드에 @ResponseBody가 
 * 적용되는 효과가 있다, 따라서 뷰 템플릿으로 디스패치 아니라, HTTP 메세지 바디에 직접 데이터를 입력한다.
 * 이름 그대로 REST API(HTTP API)를 만들 때 사용하는 컨트롤러 이다.
 */
public class ResponseBodyController {

	/*
	 * 서블릿을 직접 다룰 때 처럼 HttpServletResponse 객체를 통해서 HTTP 메세지 바디에 
	 * 직접 OK 응답 메세지를 전달한다. 
	 */
	@GetMapping("/response-body-string-v1")
	public void responseBodyV1(HttpServletResponse response) throws IOException {
		response.getWriter().write("responseBodyV1 : OK");
	}
	
	/*
	 * @return : ResponseEntity<T> (HTTP Status code 추가)
	 * 
	 * ResponseEntity 엔티티는 HttpEntity를 상속 받았는데, 
	 * HttpEntity는 HTTP 메세지의 헤더, 바디 정보를 가지고 있다.
	 * 여기에 더해서 HTTP 응답 코드를 설정할 수 있다. 
	 */
	@GetMapping("/response-body-string-v2")
	public ResponseEntity<String> responseBodyV2() {
		return new ResponseEntity<String>("responseBodyV2 : OK", HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping("/response-body-string-v3")
	public String responseBodyV3() {
		return "responseBodyV3 : OK";
	}
	
	// ResponseEntity를 반환한다. HTTP 메세지 컨버터를 통해서 JSON 형식으로 변환되어 반환된다.
	@GetMapping("/response-body-json-v1")
	public ResponseEntity<HelloData> responseBodyJsonV1() {
		HelloData helloData = new HelloData();
		helloData.setUsername("Martha Meitner");
		helloData.setAge(37);
		
		return new ResponseEntity<>(helloData, HttpStatus.OK);
	}
	
	/*
	 * ResponseEntity<T>는 HTTP 응답 코드를 설정할 수 있는데, 
	 * @ResponseBody를 사용하면 이런 것을 설정하기 까다롭다.
	 * 이때, @ResponseStatus(HttpStatus.OK) 애너테이션을 사용하면 응답코드도 설정할 수 있다.
	 */
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@GetMapping("/response-body-json-v2")
	public HelloData responseBodyJsonV2() {
		HelloData helloData = new HelloData();
		helloData.setUsername("Martha Meitner");
		helloData.setAge(36);
		
		return helloData;
	}
	
}
