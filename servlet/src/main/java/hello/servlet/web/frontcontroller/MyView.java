package hello.servlet.web.frontcontroller;

import java.io.IOException;
import java.util.Map;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MyView {

	private String viewPath;

	public void render(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		dispatcherForward(this.viewPath, request, response);
	}

	// 모델 정보를 함께 받는 새로운 render()를 정의
	public void render(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		modelToRequestAttribute(model, request);
		dispatcherForward(this.viewPath, request, response);
	}

	// 모델의 데이터를 꺼내어 request에 담아둔다.
	private void modelToRequestAttribute(Map<String, Object> model, HttpServletRequest request) {
		model.forEach((key, value) -> request.setAttribute(key, value));
	}
	
	private void dispatcherForward(String viewPath, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
		dispatcher.forward(request, response);
	}
}
