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
        em.persist(member); // 이렇게 한 줄만 써줘도
        // JPA 가 insert 쿼리 부터 id 값 세팅까지 전부 다 해준다.
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id); // 기본키가 검색 조건일 경우 파라미터로 넘어온 id 값을 기준으로 객체 값을 찾아준다.
        // select 쿼리에 where 조건 값으로 id 를 세팅해서 객체를 검색한 후 반환해준다.
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        // 기본키가 검색 조건이 아닌 경우 JPQL 을 사용해서 select 쿼리를 작성해준다.
        // JPQL 은 테이블이 아닌 객체를 중심으로 작성하는 쿼리이며, 이와 같이 쿼리를 작성하면
        // JPA 가 이를 SQL 로 번역하여 데이터베이스로 전달해준다.
        return em.createQuery("select m from Member m", Member.class).getResultList();
        // m : 검색하고자 하는 객체에 대한 별칭(alias)
        // SQL 에서 * 이나 컬럼 명을 기준으로 검색하는 것과 다르게, JPQL 에서는 객체 그 자체를 대상으로 검색한다.

    }
}
