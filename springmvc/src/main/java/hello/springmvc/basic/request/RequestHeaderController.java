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
	 * 1. DispatcherServlet이 요청 수신
	 * 		GET : localhost:8080/headers 요청이 오면 Spring MVC의 
	 * 		DispatcherSerlvet이 가장 먼저 받습니다. @RestController와 @GetMapping을 보고
	 * 		아래의 메서드를 호출한다.
	 * 
	 * 2. 파라미터 바인딩 & DI (자동 주입)
	 * 		스프링이 아래의 파라미터 객체들을 자동으로 주입한다.
	 */
	@GetMapping("/headers")
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
