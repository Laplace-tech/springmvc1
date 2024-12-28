package hello.servlet.web.frontcontroller.v2.controller;

import java.io.IOException;
import java.util.List;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.ControllerV2;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

public class MemberListControllerV2 implements ControllerV2 {

	private final MemberRepository memberRepository = MemberRepository.getInstance();
	
	@Override
	public MyView process(HttpServletRequest request, HttpServletRequest response)
			throws ServletException, IOException {
		// 1. 비즈니스 로직
		List<Member> memberList = memberRepository.findAll();
		
		// 2. 모델 데이터 설정
		request.setAttribute("members", memberList);
		
		return new MyView("/WEB-INF/views/members.jsp");
	}

}
