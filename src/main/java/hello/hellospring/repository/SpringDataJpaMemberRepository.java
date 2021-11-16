package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// 인터페이스가 상속을 받을 경우 클래스와 마찬가지로 extends 를 활용한다.
// 인터페이스는 다중 상속이 가능하다.
// JpaRepository 인터페이스를 상속 받고 있으면 Spring Data JPA 가 자동으로 구현체를 만들어 줌과 동시에 해당 인터페이스를 스프링 빈에 등록해준다.
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    @Override
    Optional<Member> findByName(String name); // 뭔가 코드를 구현할 것 없이 이 코드 한 줄만으로 끝이다.
}
