package hello.servlet.web.springmvc.v3;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;

/*
 * MemberController"V3"
 * 
 * 1. Model 객체 도입
 * 	ㄴ> Model 객체를 통해 데이터를 뷰 템플릿에 전달.
 * 	   개발자가 직접 ModelAndView를 생성할 필요 없이, 스프링이 제공해주는 Model 파라미터에
 * 	   데이터를 추가하면 스프링이 알아서 처리.
 * 
 * 2. ViewName(논리 이름) 직접 반환 "Not ModelAndView"
 * 	ㄴ> 컨트롤러 메서드는 논리적인 뷰 이름을 String 형태로 반환한다
 * 	   이후 스프링이 ViewResolver를 통해 논리 뷰 이름을 실제 물리 뷰 경로로 변환한다.
 * 
 * 3. 클래스 수준에서의 @RequestMapping 사용
 * 
 * 4. 메서드 수준에서의 @GetMapping, @PostMapping 사용
 *  ㄴ> HTTP 메서드(GET,POST 등)를 더 세분화하여 매핑 가능.
 *  	ㄴ> 이들도 내부적으로 @RequestMapping을 사용한다.
 *  
 *  ex : @GetMapping("/new-form") : @RequestMapping(value = "/new-form", method = RequestMethod.GET)

 *  
 * 5. request.getParameter 대신에 @RequestParam을 사용
 * 	ㄴ> HTTP 요청 파라미터를 간단하게 매핑한다. 
 		+(옵션 지정 가능 : required, defaultValue)
 		
 */
@RequestMapping("/springmvc/v3/members")
@Controller
public class SpringMemberControllerV3 {

	/*
	 * [동작 흐름]
	 *  
	 * 1. 클라이언트가 URL에 요청. 
	 * 2. 디스패처 서블릿이 요청을 RequestMappingHandlerMapping을 통해 매핑.
	 * 3. 핸들러 어댑터(RequestMappingHandlerAdapter)가 실행.
	 * 4. 컨트롤러 메서드가 호출되고 논리 뷰 이름 반환. 
	 * 5. 뷰 리졸버(ViewResolver)가 물리 뷰 경로로 변환.
	 * 6. 최종적으로 JSP 템플릿이 렌더링.
	 */
	
	private MemberRepository memberRepository = MemberRepository.getInstance();

//	@RequestMapping(value = "/new-form", method = RequestMethod.GET)
	@GetMapping("/new-form")
	public String showNewForm() {
		return "new-form";
	}

	@PostMapping("/save")
	public String save(@RequestParam("username") String username, @RequestParam("age") int age, Model model) {

		Member member = new Member(username, age);
		System.out.println("member : " + member);

		memberRepository.save(member);

		model.addAttribute("member", member);

		return "save-result";
	}

	@GetMapping("")
	public String showAllMembers(Model model) {
		List<Member> members = memberRepository.findAll();
		model.addAttribute("members", members);

		return "members";
	}

}
