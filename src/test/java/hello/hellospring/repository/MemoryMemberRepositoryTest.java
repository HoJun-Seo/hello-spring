package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*; // static 경로 추가

// 굳이 public 예약어를 사용하지 않아도 된다, 굳이 다른데서 불러와서 사용할 필요가 없기 때문
class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach // 각종 테스트 케이스 메소드들의 수행이 끝날때 마다 이 메소드를 수행하라는 뜻의 어노테이션(call-back 메소드)
    public void afterEach(){
        repository.clearStore();
    }

    @Test
    public void save(){
        Member member = new Member();
        member.setName("spring");

        repository.save(member);
        Member result = repository.findById(member.getId()).get();// Optional 이 반환 자료형일 경우 get() 메소드를 사용해 줄 수 있다.
//        Assertions.assertEquals(result, member); // org.junit.jupiter.api
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member(); // shift + f6 단축키 : 중복되는 이름의 변수가 있을 경우 해당 변수가 사용된 곳 모두 한번에 변수의 이름을 바꿔줄 수 있다.
        member2.setName("spring2");
        repository.save(member2);

        //findByName 메소드가 잘 동작하는지 확인한다.
        Member result = repository.findByName("spring1").get();
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();
        assertThat(result.size()).isEqualTo(2);
    }
}
