package hello.springmvc.basic;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class HelloData {

	private String username;
	private int age;

	public String toString() {
		return String.format("username : %s, age : %d", username, age);
	}
	
}
