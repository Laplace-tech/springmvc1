package hello.springmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringmvcApplication {

	/*
	 * 스프링 MVC 작동흐름
	 * 	1. 클라이언트 요청
	 * 	
	 * 	2. DispatcherServlet : 스프링의 FrontController로, 모든 요청은 DispatcherServlet이 
	 * 		가장 먼저 받는다.
	 * 	
	 * 	3. HandlerMapping : DisplatcherServlet은 어떤 컨트롤러가 이 요청을 처리할 수 있는지
	 * 		HandlerMapping을 통해서 찾는다. -> @RequestMapping("/hello-basic")
	 * 	
	 * 	4. Handler Adapter : 요청에 대응하는 매핑을 찾으면 그 컨트롤러 메서드를 실행할 수 있도록 
	 * 		HandlerAdapter가 호출된다.
	 * 	
	 * 	5. Controller 실행 : 매핑된 메서드가 실행된다.
	 * 	
	 * 	6. ViewResolver (뷰 리졸버)
	 * 		@Controller의 경우에는 리턴 값 "helloBasic"을 뷰 이름으로 판단하여
	 * 		ViewResolver가 JSP 등으로 렌더링한다 
	 */
	
	public static void main(String[] args) {
		SpringApplication.run(SpringmvcApplication.class, args);
	}

}
