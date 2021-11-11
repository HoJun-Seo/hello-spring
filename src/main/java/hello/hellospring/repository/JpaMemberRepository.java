package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{

    // JPA 는 EntityManager 로 모든게 동작한다.
    // bulid.gradle 파일에 spring-boot-starter-data-jpa 의존성을 작성해 주면
    // 스프링 부트가 알아서 EntityManager 클래스 객체를 만들어서 스프링 빈으로 등록해둔다.
    // application.properties 파일에 DataSource 관련 설정을 작성했을때
    // DataSource 클래스 객체를 스프링 부트가 스프링 빈으로 등록을 해준것과 같은 이치이다.
    private final EntityManager em;
    // EntityManager 클래스는 DataSource 와 같은 클래스들을 내부적으로 지니고 있어서
    // 데이터베이스 연결과 같은 작업도 알아서 다 처리해준다.

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    // save 메소드 작성은 내일부터
    @Override
    public Member save(Member member) {
        return null;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Member> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<Member> findAll() {
        return null;
    }
}
