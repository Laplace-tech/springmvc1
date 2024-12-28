package hello.servlet.web.frontcontroller.v1.controller;

import java.io.IOException;
import java.util.List;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.v1.ControllerV1;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MemberListControllerV1 implements ControllerV1 {

	private final MemberRepository memberRepository = MemberRepository.getInstance();
	
	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 데이터 객체를 가져오는 컨트롤러의 비즈니스 로직
		List<Member> memberList = memberRepository.findAll();
		
		// 2. 뷰에 전달하기 위한 데이터를 모델에 넣어둠.
		request.setAttribute("members", memberList);
		
		// 3. JSP로 포워드
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/members.jsp");
		dispatcher.forward(request, response);
	}

}
