package hello.springmvc.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/*
 * HTTP 응답 = {정적 리소스, 뷰 템플릿, HTTP 메세지 사용}
 * 
 * 스프링 서버에서 응답 데이터를 만드는 방법은 크게 3가지다.
 * 
 * 1. 정적 리소스 : 웹 브라우저에 정적인 HTML을 제공할 때는, 정적 리소스를 사용한다.
 *  
 *   정적 리소스 경로 : src/main/resource/static
 * 
 * 	 다음 경로에 파일이 들어있으면 src/main/resource/static/basic/hello-form.html
 *   웹 브라우저에서 다음과 같이 실행해주면 된다 : http://localhost:8080/basic/hello-form.html
 * 
 *   정적 리소스는 해당 파일을 변경 없이 그대로 렌더링 하는 것이다.
 * 
 * 2. 뷰 템플릿 : 웹 브라우저에 동적인 HTML을 제공할 때는 뷰 템플릿을 사용한다.
 * 
 * 	 뷰 템플릿 기본 경로 : src/main/resources/templates
 *   뷰 템플릿 생성 : src/main/resources/templates/response/hello.html
 * 
 *   뷰 템플릿을 거쳐서 HTML이 생성되고, 뷰가 응답을 만들어서 전달한다. 일반적으로 HTML을 동적으로
 *   생성하는 용도로 사용하지만 다른 것들도 가능하다.
 *   
 * 3. HTTP 메세지 사용 : HTTP API를 제공하는 경우에는 HTML이 아니라 데이터를 전달해야 하므로,
 * 		HTTP 메세지 바디에 JSON 같은 형식으로 데이터를 실어 보낸다.
 * 
 * 
 */

@Controller
public class ResponseViewController {

	@GetMapping("/response-view-v1")
	public ModelAndView responseViewV1() {
		ModelAndView mav = new ModelAndView("response/hello");
		mav.addObject("data", "responseViewV1");
		return mav;
	}
	
	/*
	 * String 을 반환하는 경우 - View(@Controller) or HTTP 메세지(@RestController)
	 * 
	 * @ResponseBody가 없으면 response/hello 라는 논리 이름으로 뷰 리졸버가 실행되어서 
	 * 이에 맞는 뷰를 찾고, 렌더링 한다. 만약 @ResponseBody가 있으면 뷰 리졸버를 실행하지 않고
	 * HTTP 메세지 바디에 직접 "response/hello" 라는 문자가 입력된다.
	 * 
	 */
	@GetMapping("/response-view-v2")
	public String responseViewV2(Model model) {
		model.addAttribute("data", "responseViewV2");
		return "response/hello";
	}
	
	/*
	 * 매핑 메서드가 "Void" 를 반환하는 경우
	 * 
	 * @Controller를 클래스 레벨에서 사용하고, HttpServletResponse, OutputStream 같은
	 * HTTP 메세지 바디를 처리하는 파라메터가 없으면 "요청 URL" 를 참고해서 논리 뷰 이름으로 사용함,
	 * 
	 * 참고로 이 방식은 명시성이 너무 떨어지고 이렇게 딱 맞는 경우도 많이 없어서, *권장하지 않는다
	 */
	@GetMapping("/response/hello")
	public void responseViewV3(Model model) {
		model.addAttribute("data", "responseViewV3");
	}
}
