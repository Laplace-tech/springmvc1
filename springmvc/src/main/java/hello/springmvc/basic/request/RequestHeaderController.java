package hello.springmvc.basic.request;

import java.util.Locale;

import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class RequestHeaderController {

	/*
	 * 1. HttpServletRequest request
	 * 
	 * 서블릿 컨테이너(Tomcat)에서 생성한 요청 객체이다.
	 * 이 객체는 요청에 포함된 URI, 파라메터, 헤더, 바디 등의 정보를 제공한다. 
	 * 여기서는 객체의 메모리 주소가 출력된다.
	 * 
	 * request=org.apache.catalina.connector.RequestFacade@4dfe4135
	 *  
	 * 2. HttpServletResponse response
	 * 
	 * 서블릿 컨테이너에서 생성된 응답 객체이다.
	 * 여기서는 비동기 응답(StandardServletAsyncWebRequest)을 다루는 객체이며, 
	 * 메모리 주소(4339a6fa)가 출력되었다.
	 *   
	 * 3. HttpMethod httpMethod
	 * 요청 메서드 : httpMethod = GET
	 * 
	 * 4. Locale locale
	 * 
	 * 요청 헤더의 Accept-Language 값을 기반으로 ko_KR 로케일이 결정되었다.
	 */
	@GetMapping("headers")
	public String headers(HttpServletRequest request, 
			HttpServletResponse response, 
			HttpMethod httpMethod,
			Locale locale,
			@RequestHeader MultiValueMap<String, String> headerMap,
			@RequestHeader("host") String host,
			@CookieValue(value = "myCookie", required = false) String cookie) {

		log.info("request={}", request);
		log.info("response={}", response);
		log.info("httpMethod={}", httpMethod);
		log.info("locale={}", locale);
		log.info("headerMap={}", headerMap);
		log.info("header host={}", host);
		log.info("myCookie={}", cookie);
		return "ok";
	}

}
