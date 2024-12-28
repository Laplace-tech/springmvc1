package hello.servlet.web.springmvc.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
/*
 * @Controller
 * 
 * 내부에 @Component 애너테이션이 있어서 컴포넌트 스캔의 대상이 됨.
 * 따라서, 스프링이 자동으로 스프링 빈으로 등록한다. 
 * 
 * 스프링 MVC에서 애너테이션 기반 컨트롤러로 인식한다. 
 * 즉, RequestMappingHandlerMapping에서 매핑 정보를 인식한다.
 * 
 */
public class SpringMemberFormControllerV1 {

	/*
	 * @RequestMapping
	 * 
	 * 요청 정보를 매핑한다. 해당 URL이 호출되면 이 메서드가 호출된다.
	 * 애너테이션 기반으로 동작하기 때문에, 메서드의 이름은 알잘딱깔센 아무렇게나 지어라.
	 * 
	 */
	@RequestMapping("/springmvc/v1/members/new-form")
	public ModelAndView process() {
		return new ModelAndView("new-form");
	}
	
}

