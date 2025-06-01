package hello.springmvc.basic.request;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import hello.springmvc.basic.HelloData;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/*
 * Content-Type : application/json
 * 
 * {
 * 		"username" : "Martha Meitner",
 * 		"age" : 37
 * }
 */
@Slf4j
@Controller
public class RequestBodyJsonController {

	private ObjectMapper objectMapper = new ObjectMapper();
	
	/*
	 * HttpServletRequest를 사용해서 직접 HTTP 메세지 바디에 데이터를 읽어와 문자로 변환한다.
	 */
	@PostMapping("/request-body-json-v1")
	public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		ServletInputStream inputStream = request.getInputStream();
		String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
		
		/*
		 * {
    	 *   "username" : "Marthar Meitner",
         *   "age" : 39
		 * }
		 * 
		 * ObjectMapper를 통해 자바 객체로 변환한다.
		 */
		log.info("given messageBody : {}", messageBody);
		HelloData data = objectMapper.readValue(messageBody, HelloData.class);
		
		// username : Marthar Meitner, age : 39
		log.info("username : {}, age : {}", data.getUsername(), data.getAge());
		
		response.getWriter().write(data.toString());
	}
	
	/*
	 * 
	 */
	@ResponseBody
	@PostMapping("/request-body-json-v2")
	public HelloData requestBodyJsonV2(@RequestBody String messageBody) throws IOException {

		log.info("messageBody : {}", messageBody);
		
		/*
		 * String messageBody
		 * 
		 * 		 -> {
    	 *   		  "username" : "Marthar Meitner",
         *  	 	  "age" : 39
		 * 	  	    }
		 * 
		 * ObjectMapper : String(messageBody) -> HelloData
		 */
		
		HelloData data = objectMapper.readValue(messageBody, HelloData.class);
		log.info("username={}, age={}", data.getUsername(), data.getAge());
		
		return data;
	}
	
	/*
	 * @RequestBody 생략 불가능!!! (@ModelAttribute 가 적용되버림 : 요청 파라메터)
	 * 
	 * 스프링은 @ModelAttribute, @RequestParam 생략 시 다음과 같은 규칙을 적용한다.
	 *   String, Integer 같은 단순 타입 : @RequestParam
	 *   그 외의 나머지 : @ModelAttribute
	 *   
	 *   따라서 아래와 같은 경우에 @RequestBody를 생략하면 @ModelAttribute가 적용되어 버린다.
	 *   JSON 형식의 데이터를 MappingJackson2HttpMessageConverter 가 해당 클래스 형식으로
	 *   변환시켜줘야 하는데 @ModelAttribute가 적용되면 StringHttpMessageConverter 이 호출되버림
	 * 
	 * HttpMessageConverter 사용 -> *MappingJackson2HttpMessageConverter (Content-Type : application/json)
	 */
	
	/*
	 * [동작 과정]
	 * 1. 클라이언트가 JSON 데이터를 POST 요청의 Body 에 담아 전송
	 * 2. 스프링의 MappingJackson2HttpMessageConverter가 JSON 데이터를 HelloData 객체로 변환
	 * 3. 변환된 HelloData 객체를 컨트롤러로 전달. 
	 */
	@ResponseBody
	@PostMapping("/request-body-json-v3")
	public HelloData requestBodyJsonV3(@RequestBody HelloData data) {
	    log.info("username={}, age={}", data.getUsername(), data.getAge());
	    return data;
	}

	/*
	 * [동작 과정]
	 * 1. 클라이언트가 JSON 데이터를 POST 요청의 Body 에 담아 전송
	 * 2. 스프링의 MappingJackson2HttpMessageConverter가 JSON 데이터를 HelloData 객체로 변환
	 * 3. HttpEntity<HelloData>를 통해 변환된 HttpEntity 객체를 컨트롤러로 전달. 
	 */
	@ResponseBody
	@PostMapping("/request-body-json-v4")
	public HelloData requestBodyJsonV4(HttpEntity<HelloData> httpEntity) {
		HelloData data = httpEntity.getBody();
		log.info("username={}, age={}", data.getUsername(), data.getAge());
		return data;
	}
	
	/*
	 * ERROR! : @ModelAttribute로 인식
	 */
	@ResponseBody
	@PostMapping("/request-body-json-v5")
	public HelloData requestBodyJsonV5(HelloData data) {
	    log.info("username={}, age={}", data.getUsername(), data.getAge());
	    return data;
	}
}
