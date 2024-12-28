package hello.springmvc.basic.request;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RequestBodyStringController {

	/*
	 * 리퀘스트 파라메터와 다르게, HTTP 메세지 바디를 통해 데이터가 직접 넘어오는 경우는
	 * @RequestParam, @ModelAttribute를 사용할 수 없다.
	 * 	
	 * HTTP 메세지 바디의 데이터를 InputStream을 사용해서 직접 읽을 수 있다.
	 */
	@PostMapping("/request-body-string-v1")
	public void requestBodyStringV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		ServletInputStream inputStream = request.getInputStream();
		String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
		
		log.info("messageBody = {}", messageBody);
		// messageBody = Anna Viktorovna Choe
		
		response.getWriter().write("messageBody : " + messageBody);
	}
	
	/**
	* InputStream(Reader): HTTP 요청 메시지 바디의 내용을 직접 조회
	* OutputStream(Writer): HTTP 응답 메시지의 바디에 직접 결과 출력
	*/
	@PostMapping("/request-body-string-v2")
	public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {
	    String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
	    log.info("messageBody = {}", messageBody);
	    responseWriter.write("messageBody : " + messageBody);
	}
	
	/*
	 * HttpEntity: HTTP header, body 정보를 편라하게 조회 
	 * - 메시지 바디 정보를 직접 조회(@RequestParam X, @ModelAttribute X) 
	 * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
	 *
	 * 응답에서도 HttpEntity 사용 가능 
	 * - 메시지 바디 정보 직접 반환(view 조회X) 
	 * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
	 */
	@PostMapping("/request-body-string-v3")
	public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) {
		String messageBody = httpEntity.getBody();
		log.info("messageBody = {}", messageBody);
		return new HttpEntity<>("messageBody : " + messageBody);
	}
	
	/*
	 * @RequestBody : 그냥 HTTP 메세지 바디에 있는거 그대로 끌어온다고!
	 * - 메세지 바디 정보를 직접 조회(@RequestParam X, @ModelAttribute X)
	 * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용 
	 * 
	 * @ResponseBody : 뷰로 안넘기고 그대로 HTTP 응답 메세지 바디에 쳐넣는다고!
	 * - 메세지 바디 정보를 직접 반환(view 를 조회하지 않음)
	 * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
	 */
	@ResponseBody
	@PostMapping("/request-body-string-v4")
	public String requestBodyStringV4(@RequestBody String messageBody) {
		log.info("messageBody : {}", messageBody);
		return "messageBody : " + messageBody;
	}
	
}
