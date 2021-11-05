package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 회원 가입
    public Long join(Member member){
        // 중복 회원 검증 메소드
        validateDuplicateMember(member); // 메소드 추출 단축키 활용 (Ctrl + Alt + M)
        // 단축키 : Ctrl + Shift + T

        memberRepository.save(member);
        return member.getId();
    }



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
        result.ifPresent(m -> {
            throw  new IllegalStateException("이미 존재하는 회원입니다.");

            // 여기서 Ctrl + Alt + M 단축키를 활용하면 코드를 메소드로 따로 뽑아내는것이 가능하다.
        });
    }
}
