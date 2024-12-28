package hello.servlet.web.frontcontroller.v4.controller;

import java.util.Map;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v4.ControllerV4;

public class MemberSaveControllerV4 implements ControllerV4 {

	private final MemberRepository memberRepository = MemberRepository.getInstance();

	@Override
	public String process(Map<String, String> paramMap, Map<String, Object> model) {
		/*
		 * paramMap 사용 : request의 파라미터 정보들이 <paramName, value> 형식으로 제공됨
		 */
		String username = paramMap.get("username");
		int age = Integer.valueOf(paramMap.get("age"));
	
		
		Member member = new Member(username, age);
		memberRepository.save(member);	
		
		/*
		 * model 사용, 여기에 담기는 데이터를 기반으로 MyView 에서 뷰를 띄우기 전에 
		 * 모델에 객체를 입힌다. 
		 *   ㄴ-> <key, value> -> request.setAttribute(key, value);
		 */
		model.put("member", member);
		
		/*
		 * 뷰 리졸버에서 전체 URL를 조합할 때 사용함.
		 */
		return "save-result";
	}

}
