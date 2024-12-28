package hello.servlet.web.frontcontroller.v2.controller;

import java.io.IOException;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.ControllerV2;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class MemberSaveControllerV2 implements ControllerV2{

	private final MemberRepository memberRepository = MemberRepository.getInstance();

	@Override
	public MyView process(HttpServletRequest request, HttpServletRequest response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		int age = Integer.valueOf(request.getParameter("age"));
		
		Member member = new Member(username, age);
		memberRepository.save(member);
		
		request.setAttribute("member", member);
		
		/*
		 * 각 컨트롤러는 복잡한 dispatcher.forward()를 직접 생성해서 호출하지 않아도 됨
		 * 단순히 MyView 객체를 생성하고 그에 대한 생성자의 파라메터로 viewPath만 넣고 반환하면 됨.
		 */
		return new MyView("/WEB-INF/views/save-result.jsp");
	}

	

}
