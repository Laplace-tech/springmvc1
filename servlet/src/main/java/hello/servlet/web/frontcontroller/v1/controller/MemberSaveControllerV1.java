package hello.servlet.web.frontcontroller.v1.controller;

import java.io.IOException;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.v1.ControllerV1;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MemberSaveControllerV1 implements ControllerV1 {

	private final MemberRepository memberRepository = MemberRepository.getInstance();
	
	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 컨트롤러의 비즈니스 로직
		String username = request.getParameter("username");
		int age = Integer.valueOf(request.getParameter("age"));
		
		Member member = new Member(username, age);
		memberRepository.save(member);
		
		// 2. 뷰로 데이터를 전달하기 위해 모델에 넣어둠
		request.setAttribute("member", member);
		
		// 3. JSP로 포워드
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/save-result.jsp");
		dispatcher.forward(request, response);
	}

	
	
}
