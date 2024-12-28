package hello.springmvc.basic.requestmapping;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * 회원 관리 API
 *
 * 회원 목록 조회: GET /users
 * 회원 등록: POST /users
 * 회원 조회: GET /users/{userId}
 * 회원 수정: PATCH /users/{userId}
 * 회원 삭제: DELETE /users/{userId}
 * 
 */

@RestController
@RequestMapping("/mapping/users")
/*
 * @RequestMapping("/mapping/users") 
 * 	ㄴ> 클래스 레벨에 매핑 정보를 두면 메서드 레벨에서 해당 정보를 조합해서 사용
 */
public class MappingClassController {
	
	/*
	 * GET : /mapping/users
	 */
	@GetMapping
	public String showAllUsers() {
		return "showAllUsers()";
	}
	
	/*
	 * POST : /mapping/users
	 */
	@PostMapping("{username}")
	public String addUser(@PathVariable("username") String username) {
		return "addUser() -> username : " + username;
	}
	
	/*
	 * GET : /mapping/users/{userId}
	 */
	@GetMapping("/{username}")
	public String findUser(@PathVariable("username") String username) {
		return "findUser() -> username : " + username;
	}
	
	/*
	 * PATCH : /mapping/users/{userId}
	 */
	@PatchMapping("/{username}")
	public String updateUser(@PathVariable("username") String username) {
		return "updateUser() -> username : " + username;
	}
	
	/*
	 * DELETE : /mapping/users/{userId}
	 */
	@DeleteMapping("/{username}")
	public String deleteUser(@PathVariable("username") String username) {
		return "deleteUser() -> username : " + username;
	}
	
}
