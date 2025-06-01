package hello.springmvc.basic.requestmapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
/*
 * @Controller 는 반환 값이 String 이면 뷰 이름으로 인식된다. 
 * 그래서 뷰를 찾고 뷰가 렌더링 된다.
 * 
 * @RestController 는 반환 값으로 뷰를 찾는 것이 아니라, 메세지 바디에 바로 입력한다.
 * 따라서 실행 결과로 ok 메세지를 받을 수 있다.
 */
public class MappingController {

	/*
	 * @RequestMapping("/hello-basic")
	 *  "/hello-basic" URL 호출이 오면 이 메서드가 실행되도록 매핑한다.
	 *  @RequestMapping 에 method 속성으로 HTTP 메서드를 지정하지 않으면
	 *  HTTP 메서드와 무관하게 호출된다. GET, HEAD, POST, PUT, PATCH, DELETE .... 
	 */
	@RequestMapping("/hello-basic")
	public String helloBasic() {
		log.info("helloBasic");
		return "helloBasic";
	}
	
	/*
	 * @RequestMapping 애너테이션을 사용할 때, method 속성을 지정하면
	 * 그 특정 HTTP 메서드에 대해서만 요청을 처리하도록 제한할 수 있다.
	 * 
	 * 그렇기 때문에 해당 엔드포인트에 지정한 메서드가 아닌 다른 요청을 보내면 
	 * 405 Method Not Allowed 상태코드가 반환된다. 
	 * 	ㄴ> i.e. "요청한 메서드는 허용되지 않음" : Request method 'POST' is not supported
	 */
	@RequestMapping(value = "/mapping-get-v1", method = RequestMethod.GET)
	public String mappingGetV1() {
		log.info("mappingGetV1");
		return "mappingGetV1";
	}
	
	/*
	 * 축약 애너테이션 
	 *  ㄴ> 코드 내부에서 @RequestMapping과 method 를 지정해서 사용
	 *  
	 *  @RequestMapping(method = RequestMethod.GET)
	 *   public @interface GetMapping { ... }
	 */
	@GetMapping("/mapping-get-v2")
	public String mappingGetV2() {
		log.info("mappingGetV2");
		return "mappingGetV2";
	}
	
	/*
	 * PathVariable(경로 변수) 사용
	 * 
	 * @PathVariable을 사용할 때, @GetMapping과 @PathVariable을 함께 사용하는 경우,
	 * @PathVariable의 변수 이름(String userId)을 메서드 파라미터({userId})와 동일하게 만들면, 
	 * @PathVariable("userId")와 같은 명시적 이름을 생략할 수 있다.
	 * 
	 * @GetMapping("/mapping/{userId}")
	 * 	(@PathVariable("userId") String userId) => ***(@PathVariable String userId)***
	 */
	@GetMapping("/mapping/{username}")
	public String mappingPath(@PathVariable("username") String username) {
		log.info("mappingPath : username = {}", username);
		return String.format("mappingPath : username = %s", username);
	}
	
	/*
	 * PathVariable 사용 다중
	 */
	@GetMapping("/mapping/users/{username}/orders/{orderId}")
	public String mappingPath(@PathVariable("username") String username, 
							  @PathVariable("orderId") Long orderId) {
		log.info("mappingPath : username = {}, orderId = {}", username, orderId);
		return String.format("mappingPath : username = %s, orderId = %d", username, orderId);
	}
	
	/*
	 * @GetMapping(value = "/mapping-param", params = "mode=debug")
	 * 1. 특정 요청 파라메터가 있을 때만 메서드가 실행된다.
	 * 2. params = "mode=debug"는 요청 URL에 mode=debug 라는 쿼리 파라메터가 포함되어 있어야 함.
	 * 		ㄴ> GET : localhost:8080/mapping-param?mode=debug 
	 */
	@GetMapping(value = "/mapping-param", params = "mode=debug")
	public String mappingParam() {
		log.info("mappingParam");
		return "mappingParam";
	}
	
	/*
	 * @GetMapping(value = "/mapping-header", headers = "mode=debug")
	 * 1. 특정 HTTP 헤더가 있을 때만 메서드가 실행된다.
	 * 2. headers = "mode=debug"는 요청 헤더에 "mode 라는 이름의 헤더"가 "debug 값으로 포함"되있어야 함.
	 * 		ㄴ> GET : localhost:8080/mapping-header
	 * 					mode : debug
	 */
	@GetMapping(value = "/mapping-header", headers = "mode=debug")
	public String mappingHeader() {
		log.info("mappingHeader");
		return "mappingHeader";
	}
	
	
	/*
	 * @PostMapping(value = "/mapping-consume", consumes = "application/json")
	 * 1. 요청 본문의 Content-Type을 기반으로 요청을 제한한다.
	 * 2. consumes = "application/json" 는 요청 본문이 JSON 형식이어야 매핑된다
	 * 		ㄴ> POST : localhost:8080/mapping-consume
	 * 					Content-Type : application/json
	 * 
	 * 			{
	 * 				"key" : "value"
	 * 			}
	 */
	@PostMapping(value = "/mapping-consume", consumes = "application/json")
	public String mappingConsumes() {
		log.info("mappingConsumes");
		return "mappingConsumes";
	}
	
	/*
	 * @PostMapping(value = "/mapping-produce", produces = "text/html")
	 * 1. 클라이언트가 HTML 응답을 원할 떄 주로 사용
	 * 2. produces = "text/html"은 클라이언트의 요청에서 Accept 헤더가 text/html로 
	 * 		설정되어 있어야 매핑된다.
	 * 			ㄴ> POST : /mapping-produce
	 * 			   Accept : text/html
	 */
	@PostMapping(value = "/mapping-produce", produces = "text/html")
	public String mappingProduces() {
	    log.info("mappingProduces");
	    return "mappingProduces";
	}
	
	
}

