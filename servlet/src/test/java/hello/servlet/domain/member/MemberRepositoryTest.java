package hello.servlet.domain.member;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberRepositoryTest {

	@Autowired
	private MemberRepository memberRepository;
	
	@AfterEach
	void afterEach() {
		this.memberRepository.clearStore();
	}
	
	@Test
	void save() {
		// given
		Member member = new Member("Martha Meitner", 34);
		
		// when
		Member savedMember = this.memberRepository.save(member);
		
		// then 
		Member findMember = this.memberRepository.findById(savedMember.getId());
		assertThat(findMember).isEqualTo(savedMember);
	}
	
	@Test
	void findAll() {
		// given
		Member member1 = new Member("Romi", 34);
		Member member2 = new Member("Anna", 36);
		
		memberRepository.save(member1);
		memberRepository.save(member2);
		
		// when
		List<Member> result = memberRepository.findAll();
		
		// then
		assertThat(result.size()).isEqualTo(2);
		assertThat(result).contains(member1, member2);
	}
	
}
