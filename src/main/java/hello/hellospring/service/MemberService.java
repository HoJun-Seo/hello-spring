package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {

//    private final MemberRepository memberRepository = new MemoryMemberRepository();

    // 테스트 파일에서 서로 다른 인스턴스를 사용하는 두 변수(memberRepository)가 같은 인스턴스를 사용하게끔 하는 방법,
    private final MemberRepository memberRepository;
    // 생성자를 만들어서 memberRepository 변수의 데이터를 외부에서 받아오도록 만든다.
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 회원 가입
    public Long join(Member member){
        // 중복 회원 검증 메소드
        validateDuplicateMember(member); // 메소드 추출 단축키 활용 (Ctrl + Alt + M)
        // 검증 메소드를 작성하였으니, 메소드에 작성된 코드 대로 익셉션이 잘 발생하는지 검증을 해봐야 한다.
        // 그러므로 회원 서비스 기능들에 대한 테스트를 수행해보자.
        // 단축키 : Ctrl + Shift + T

        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 이쯤 되서 알 수 있는것
     * Repository 의 경우 getid, findbyname 등 메소드들의 이름이 데이터베이스 접근적인 느낌이 강한 반면
     * Service 의 경우 join validateDuplicateMember 등 메소드의 네이밍이 비즈니스 로직에 가까운 느낌이 강하다.
     * Service 클래스의 경우 네이밍을 비즈니스 로직에 가깝게 작성해주는 것이 좋다.
     * 다른 개발자 들과 협업을 하면서 사람이 바뀌는 경우가 생길 수 있는데, 그런 경우라도 메소드의 이름을 잘 정해두면
     * 이제 막 프로젝트에 투입되어서 프로젝트에 대한 숙련도가 부족한 개발자 라고 해도 쉽게 본인이 찾고자 하는 메소드를 찾아낼 수 있게 된다.
     * ** - Service 는 비즈니스에 의존적이게 설계하고 Repository 는 좀 더 기계적인 느낌(데이터베이스 접근) 으로 설계하는 것이 좋다.(데이터 검색, 추출 등)
     * @return
     */

    // 전체 회원 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    // id 값 기준 회원 조회
    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }

    private void validateDuplicateMember(Member member) {
        // 같은 이름이 있는 중복 회원 X(동명이인 허용되지 않음) 인 로직을 만드는 경우
        Optional<Member> result = memberRepository.findByName(member.getName());
        result.ifPresent(m -> { // 반환값이 존재하는 경우 - 이미 같은 이름의 회원이 존재하는 경우
            // 과거에는 if 조건문을 이용해서 null 값인지 아닌지를 판단했지만
            // 요즘은 반환값이 null 일 가능성이 있으면 Optional 로 한번 감싸서 반환해준다.
            // 이런거 없이 그냥 꺼내고 싶으면 get() 메소드로 꺼내도 무방하다.
            // 추가적으로 orElseGet() 메소드도 꽤 많이 사용된다.
            throw  new IllegalStateException("이미 존재하는 회원입니다.");

            // 여기서 Ctrl + Alt + M 단축키를 활용하면 코드를 메소드로 따로 뽑아내는것이 가능하다.
        });
    }
}
