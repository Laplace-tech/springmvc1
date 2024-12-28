package hello.servlet.web.frontcontroller.v2.controller;

import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.ControllerV2;
import jakarta.servlet.http.HttpServletRequest;

public class MemberFormControllerV2 implements ControllerV2{

	@Override
	public MyView process(HttpServletRequest request, HttpServletRequest response) {
		return new MyView("/WEB-INF/views/new-form.jsp");
	}

}
