package hello.servlet.web.frontcontroller.v3.controller;

import java.util.Map;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;

public class MemberSaveControllerV3 implements ControllerV3 {

	private final MemberRepository memberRepository = MemberRepository.getInstance();

	@Override
	public ModelView process(Map<String, String> paramMap) {
		String username = paramMap.get("username");
		int age = Integer.valueOf(paramMap.get("age"));
		
		Member member = new Member(username, age);
		memberRepository.save(member);
		
		/*
		 * ModelView 객체에는 JSP 포워딩을 위한 String viewName과
		 */
		ModelView mv = new ModelView("save-result");
		/*
		 * 모델 전달을 위한 Map<String, Object> modelMap이 선언되어 있다.
		 */
		mv.getModel().put("member", member);
		return mv;
	}

}
