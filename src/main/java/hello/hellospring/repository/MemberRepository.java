package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    // Optional 이란? : java8 에서부터 추가된 기능, 간단하게 말하자면 findById 와 같은 메소드를 통해 반환값을 가져오는데, 결과가 null 일 경우가 있을수 있다. 요즘은 null 값이 돌아온다고
    // 그를 그대로 받기보다는 Optional 로 감싸서 반환해주는 것을 선호 한다고 한다.
    List<Member> findAll();
}
