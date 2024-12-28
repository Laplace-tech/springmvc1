package hello.springmvc.basic.request;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hello.springmvc.basic.HelloData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/*
 * 1. @RequestParam 
 * - 쿼리 파라미터나 Form Data 에서 데이터를 추출하여 메서드 파라미터로 매핑.
 *   적용 컨버터: **컨버터 사용 안 함** (스프링 내부적으로 기본적으로 처리)
 * 		요청 : /example?name=John 또는 name=John (x-www-form-urlencoded)
 * 		결과: 메서드 파라미터에 바로 매핑.
 * 
 * 2. @ModelAttribute 
 * - 요청에서 파라미터를 받아 객체를 생성하고, 객체의 필드에 값을 설정.
 *   적용 컨버터: **컨버터 사용 안 함** (스프링이 내부적으로 파라미터 바인딩 처리)
 * 		요청: /example?username=John&age=30 (GET) 또는 username=John&age=30 (POST, x-www-form-urlencoded)
 * 		결과: 필드 username에 John, age에 30 값 설정.
 *   추가: 기본적으로 NoArgsConstructor와 Setter를 통해 값을 매핑.
 * 
 * -----------------------------------------------------------------------------
 * 스프링 MVC는 다음의 경우에 HTTP 메시지 컨버터를 적용한다.
 *
 * HTTP 요청: @RequestBody , HttpEntity(RequestEntity)
 * HTTP 응답: @ResponseBody , HttpEntity(ResponseEntity)
 * 
 * 1. @RequestBody 
 * 
 * - 기본형 : 요청 본문에 있는 데이터를 그대로 가져와 문자열이나 단순 값으로 변환.
 *   적용 컨버터: StringHttpMessageConverter
 * 		요청: {"key": "value"}
 * 		결과: {"key": "value"} (그대로 반환)
 * 
  * - 참조형 : 요청 본문(JSON)을 Java 객체로 매핑.
 *   적용 컨버터: MappingJackson2HttpMessageConverter (application/json)
 * 		요청: POST : {"username": "Martha Meitner", "age": 30}
 * 		결과: JSON → 객체로 매핑 → JSON 응답 
 * 
 *   추가: @RequestBody는 생략 불가! 생략 시 @ModelAttribute가 적용되어 쿼리 파라미터 방식으로 동작.
 *
 * 4. @ResponseBody 
 * 
 * - 기본형(Primitive): 값을 HTTP 응답 본문에 문자열로 그대로 삽입.
 *   적용 컨버터: StringHttpMessageConverter
 * 
 * - 참조형(Object): Java 객체를 JSON 형식으로 변환해 HTTP 응답 본문에 삽입.
 *   적용 컨버터: MappingJackson2HttpMessageConverter
 * 		예: HelloData 객체를 {"username": "John", "age": 30} 형식의 JSON으로 변환.
 */


@Slf4j
@Controller
public class RequestParamController {

	/*
	 * HTTP 요청 파라메터
	 * 	ㄴ> 클라이언트에서 서버로 요청 데이터를 전달할 때는 주로 다음 3가지 방법을 사용한다.
	 * 
	 *  1. GET - 쿼리 파라미터 : /url?username=Marthar&age=32
	 *  
	 *		메세지 바디 없이, URL의 쿼리 파라메터에 데이터를 포함해서 전달
	 *	    예)  검색, 필터, 페이징 등등..
	 *
	 *  2. POST - HTML Form
	 *  	Content-Type : application/x-www-form-urlencoded
	 *  	
	 *  	메세지 바디에 쿼리 파라메터 형식으로 전달 : username=Marthar Meitner&age=32
	 *  	예) 회원 가입, 상품 주문, HTML Form 사용
	 *  
	 *  3. HTTP 메세지 body 에 데이터를 직접 담아서 요청
	 *  	
	 *  	HTTP API에서 주로 사용, JSON
	 *  	데이터 형식은 주로 JSON
	 *  	POST, PUT, PATCH
	 */
	
	/*
	 * [리퀘스트 파라메터 전송 예시] 
	 * 
	 * 1. GET, 쿼리 파라메터 전송 예시 : /request-param-v1?username=Mathar&age=34
	 * 
	 * 2. POST, HTML Form 전송 예시 
	 * 
	 * 	POST /request-param-v1...
	 * 	Content-Type : application/x-www-form-urlencoded
	 * 
	 *  body -> username=Marthar Meitner&age=20
	 */
	
	@RequestMapping("/request-param-v1")
	public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
		request.getParameterNames().asIterator()
				.forEachRemaining(paramName -> log.info("<{}, {}>", paramName, request.getParameter(paramName)));
		
		String username = request.getParameter("username");
		Long age = Long.parseLong(request.getParameter("age"));
		
		log.info("username = {}, age = {}", username, age);
		
		response.getWriter().write(String.format("username : %s, age : %d", username, age));
	}

	/* 
	 * @RequestParam 사용
	 *  - 파라메터 이름으로 바인딩
	 *  
	 * @ResponseBody 추가
	 * 	- view 조회를 무시하고, HTTP message body 에 직접 해당 내용 입력
	 */
	@ResponseBody
	@RequestMapping("/request-param-v2")
	public String requestParamV2(@RequestParam("username") String memberName, @RequestParam("age") Long memberAge) {
		log.info("username = {}, age = {}", memberName, memberAge);
		return String.format("username : %s, memberAge : %d", memberName, memberAge);
	}

	/*
	 * @RequestParam.required = true or false
	 * 	파라메터 필수 여부
	 * 	기본 값이 파라메터 필수 (true) 이다.
	 * 
	 * 	필수 값 (required = true)이 비어있다면?
	 * 		ㄴ> Required parameter 'username' is not present.
	 * 
	 * 주의! : 파라메터 이름만 있고 값이 없는 이딴 경우 : "/request-param?username="
	 *   -> 빈 문자열로 통과된다.
	 *   
	 * 주의! : 기본형(primitive)에 null 입력 요청 
	 * 		ㄴ> Optional int parameter 'age' is present 
	 * 		   but "cannot be translated into a null value" 
	 *  	   due to being declared as a "primitive type."
	 */
	
	@ResponseBody
	@RequestMapping("/request-param-required")
	public String requestParamRequired(
			@RequestParam(name = "username", required = true) String username,
			@RequestParam(name = "age", required = false) Integer age) {
		log.info("username = {}, age = {}", username, age);
		return String.format("username : %s, age : %d", username, age);
	}
	
	@ResponseBody
	@RequestMapping("/request-param-default")
	public String requestParamDefault(
			@RequestParam(name = "username", defaultValue = "Guest") String username,
			@RequestParam(name = "age", defaultValue = "-1") Integer age) {
		log.info("username = {}, age = {}", username, age);
		return String.format("username : %s, age : %d", username, age);
	}
	
	/*
	 * @RequestParam Map, MultiValueMap
	 * Map(key=value)
	 * MultiValueMap(key=[value1, value2, ...] ex) (key=userIds, value=[id1, id2])
	 */
	@ResponseBody
	@RequestMapping("/request-param-map")
	public String requestParamDefault(@RequestParam MultiValueMap<String, Object> paramMap) {
		paramMap.keySet().stream().forEach(paramName -> log.info("<{}, {}>", paramName, paramMap.get(paramName)));
		return paramMap.toString();
	}
	
	/*
	 * @ModelAttribute 사용
	 * 	ㄴ> model.addAttribute(helloDate) 코드도 함께 자동 적용된다.
	 */
	@ResponseBody
	@RequestMapping("/model-attribute-v1")
	public HelloData modelAttributeV1(@ModelAttribute HelloData helloData) {
		
		/*
		 * 스프링 MVC는 @ModelAttribute가 있으면 다음을 실행한다.
		 * 
		 *  1. HelloData 객체를 생성한다 : @NoArgsConstructor
		 *  2. 리퀘스트 파라메터의 paramName 으로 객체의 프로퍼티를 찾는다. 
		 *  	그리고, 그 해당 프로퍼티의 @Setter를 호출해서 파라미터 값을 바인딩한다.
		 *  3. 프로퍼티의 값을 변경하면 @Setter가 호출되고, 조회하면 @Getter가 호출된다
		 *  
		 */
		
		log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());
		return helloData;
	}
		
	@ResponseBody
	@RequestMapping("/model-attribute-v2")
	public HelloData modelAttributeV2(HelloData helloData) {
		log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());
		return helloData;
	}
	
	
}
