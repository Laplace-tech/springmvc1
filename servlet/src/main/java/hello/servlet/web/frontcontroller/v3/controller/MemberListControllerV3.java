package hello.servlet.web.frontcontroller.v3.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;

public class MemberListControllerV3 implements ControllerV3 {

	private final MemberRepository memberRepository = MemberRepository.getInstance();
	
	@Override
	public ModelView process(Map<String, String> paramMap) {
		List<Member> memberList = memberRepository.findAll();
		
		/*
		 * ModelView 객체에는 향후 뷰 리졸버에서 쓰일 String viewName 필드와
		 */
		ModelView mv = new ModelView("members");
		/*
		 * 모델 객체를 관리하는 modelMap<String, Object> 가 존재한다.
		 */
		mv.getModel().put("members", memberList);

		return mv;
	}
}
