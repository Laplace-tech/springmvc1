package hello.servlet.basic.request;

import java.io.IOException;
import java.util.stream.Stream;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "requestHeaderServlet", urlPatterns = "/request-header")
public class RequestHeaderServlet extends HttpServlet {

	/*
	 * service : 서블릿의 핵심 메서드로, "요청이 들어올 때마다 호출됨" 
	 * HttpServletRequest 객체를 통해 HTTP 요청 정보를 읽어오고, : Get
	 * HttpServletResponse 객체를 통해 응답을 작성함. : Set
	 */
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		printStartLine(request);
		printHeaders(request);
		printHeaderUtils(request);
		printEtc(request);
	}

	/*
	 * Start Line : HTTP 메시지의 첫 번째 줄로, 요청이나 응답 메세지의 핵심 정보를 요약. ㄴ> GET
	 * /request-header HTTP/1.1
	 */
	private void printStartLine(HttpServletRequest request) {
		System.out.println("--- Request-Line (Start) ---");

		// request.getMethod() : HTTP 메서드를 반환함 (GET, POST, PUT, DELETE)
		System.out.println("request.getMethod() : " + request.getMethod());

		// request.getProtocol() : 요청에 사용된 프로토콜과 버전을 반환함 (HTTP/1.1, HTTP2)
		System.out.println("request.getProtocol() : " + request.getProtocol());

		// request.getScheme() : 사용된 스킴(프로토콜)을 반환함 (http, https)
		System.out.println("request.getScheme() : " + request.getScheme());

		// request.getRequestURL() : 클라이언트가 요청한 전체 URL을 반환함
		System.out.println("request.getRequestURL() : " + request.getRequestURL());

		// request.getRequestURI : 클라리언트가 요청한 리소스의 경로 부분만 반환함
		System.out.println("request.getRequestURI() : " + request.getRequestURI());

		// request.getQueryString() : 요청 URL 뒤에 붙는 쿼리 스트링을 반환함 (username=Marthar)
		System.out.println("request.getQueryString() : " + request.getQueryString());

		// request.isSecure() : 요청이 HTTPS를 통해 들어왔는지 여부를 반환함.
		System.out.println("request.isSecure() : " + request.isSecure());

		System.out.println("--- Request-Line (End) ---");
		System.out.println();
	}

	/*
	 * HTTP 요청의 헤더 정보를 출력함.
	 */
	private void printHeaders(HttpServletRequest request) {
		System.out.println("--- Headers (Start) ---");

		request.getHeaderNames().asIterator()
				.forEachRemaining(headerName -> System.out.println(headerName + " : " + request.getHeader(headerName)));

		System.out.println("--- Headers (End) ---");
		System.out.println();
	}

	// Header 편리한 주소
	private void printHeaderUtils(HttpServletRequest request) {
		System.out.println("--- Header 편의 조회 start ---");
		
		System.out.println("[Host 편의 조회]");
		// request.getServerName() : 서버 이름 or IP 주소
		System.out.println("request.getServerName() : " + request.getServerName());
		// request.getServerPort() : 서버 포트 번호
		System.out.println("request.getServerPort() : " + request.getServerPort());
		System.out.println();
		
		System.out.println("[Accept-Language 편의 조회]");
		// request.getLocales() : 클라이언트의 언어 목록을 반환함
		request.getLocales().asIterator() 
			.forEachRemaining(locale -> System.out.println("locale : " + locale));
		// request.getLocale() : 가장 선호하는 언어를 반환함
		System.out.println("request.getLocale() : " + request.getLocale());
		System.out.println();
		
		System.out.println("[cookie 편의 조회]");
		// cookie.getName() : 쿠키 이름, cookie.getValue() : 쿠키 값
		if(request.getCookies() != null)
			Stream.of(request.getCookies())
				.forEach(cookie -> System.out.println(cookie.getName() + " : " + cookie.getValue()));
		System.out.println();
		
		System.out.println("[Content 편의 조회]");
		// request.getContentType() : 요청 본문의 MIME 타입
		System.out.println("request.getContentType() : " + request.getContentType());
		// request.getContentLength() : 요청 본문의 길이. 길이를 알 수 없다면 -1 반환
		System.out.println("request.getContentLength() : " + request.getContentLength());
		// request.getCharacterEncoding() : 요청 본문의 문자 인코딩 방식 (기본값은 UTF-8)
		System.out.println("request.getCharacterEncoding() : " + request.getCharacterEncoding());
		
		System.out.println("--- Header 편의 조회 start ---");
		System.out.println();
	}

	//기타 정보
	private void printEtc(HttpServletRequest request) {
	    System.out.println("--- 기타 조회 start ---");
	    System.out.println("[Remote 정보]");
	    System.out.println("request.getRemoteHost() = " + request.getRemoteHost()); //
	    System.out.println("request.getRemoteAddr() = " + request.getRemoteAddr()); //
	    System.out.println("request.getRemotePort() = " + request.getRemotePort()); //
	    System.out.println();
	    
	    System.out.println("[Local 정보]");
	    System.out.println("request.getLocalName() = " + request.getLocalName()); //
	    System.out.println("request.getLocalAddr() = " + request.getLocalAddr()); //
	    System.out.println("request.getLocalPort() = " + request.getLocalPort()); //\
	    
	    System.out.println("--- 기타 조회 end ---");
	    System.out.println();
	}
}
