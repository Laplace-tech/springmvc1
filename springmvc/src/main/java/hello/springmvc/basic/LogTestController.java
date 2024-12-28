package hello.springmvc.basic;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/*
 * ***** 로깅의 필요성 *****
 * 
 * System.out.println()으로 디버깅 정보를 출력하는 것은 문제가 많다.
 * 	ㄴ> 성능 저하 : 출력이 많아질수록 시스템 성능에 영향을 미침.
 * 	ㄴ> 가독성 저하 : 모든 출력이 콘솔에 혼재되어 구조적인 관리가 어렵다.
 * 	ㄴ> 레벨 구분 불가 : 로그의 중요도를 구분할 수 없다.
 * 	ㄴ> 파일 저장 어려움 : 콘솔 출력은 기본적으로 로그 파일에 저장되지 않음. 
 * 
 *  
 */

/*
 * **로그 출력 포멧**
 * 
 * 시간 : 2024-12-22T14:59:54.549+09:00 
 * 로그 레벨 : DEBUG
 * 프로세스 ID : 9412 
 * 쓰레드 이름 : [springmvc] [nio-8080-exec-2]
 * 클래스 이름 : h.springmvc.basic.LogTestController
 * 로그 메세지 : debug log = Spring
 */

/*
 * @Controller VS @RestController
 * 
 * @Controller는 반환 값이 String 이면 항상 뷰 이름으로 인식된다.
 * 그래서 뷰를 찾고 뷰가 렌더링 된다. 
 * 
 * @RestController는 반환 값으로 뷰를 찾는 것이 아니라, 
 * HTTP 메세지 바디에 바로 입력한다. 따라서 실행 결과로 "OK" 메세지를 받을 수 있다.
 */
@Slf4j
@RestController
public class LogTestController {

	/*
	 * HTTP 메서드 모두 허용
	 *  ㄴ> GET, POST, PUT, DELETE...
	 */
	@RequestMapping("/log-test")
	public String logTest() {
		
		String name = "Spring";
		
		log.trace("trace log = {}", name);
		log.debug("debug log = {}", name);
		log.info("info log = {}", name);
		log.warn("warn log = {}", name);
		log.error("error log = {}", name);
		
		return "ok";
	}
	
}
