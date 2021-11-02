package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

//    MemberService memberService = new MemberService();
//    MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    // MemberService 에서 선언해준 memberRepository 값과 현재 테스트 파일에서 선언해준 memberRepository 는 인스턴스 값이 서로 다르다.
    // 정확하게는 MemberService 클래스 타입으로 선언한 memberSerivce 객체를 통해 활용되는 memberRepository 변수 값과 테스트 파일에서 선언된 memberRepository 값이 다르다.
    // 똑같이 MemoryMemberRepository 클래스로 객체를 만들었다고 해도, MemberService 의 경우 변수의 타입이 MemberRepository 클래스 이고
    // 현재 파일의 경우 변수 타입이 MemoryMemberRepository 이다.
    // 인스턴스에 대한 참고 자료 : https://gmlwjd9405.github.io/2018/09/17/class-object-instance.html (링크의 내용을 보면 인스턴스가 다르다는 말은 객체의 클래스 타입이 다르다고 이해하는것이 맞는것 같다.)
    // 물론 지금이야 clearStore 메소드의 경우 메소드 내부의 store 변수가 static(Map) 으로 선언되어 있기 때문에 문제가 없다.
    // 왜냐하면 static 으로 선언된 변수는 어플리케이션 실행전 컴파일 단계에서 store 변수가 메모리에 미리 적재됨과 동시에
    // 같은 이름을 가지고 같은 클래스로 만들어진 객체 변수가 인스턴스가 다르더라도(MemberRepository, MemoryMemberRepository) 인스턴스 끼리 같은 주소를 공유하기 때문이다.(선언된 클래스 타입이 달라도 static 키워드 덕분에 같은 메모리 주소를 공유하게 된다.)
    // 그러나 static 이 아니게 되는 경우 서로 다른 데이터베이스(메모리) 로 인식이 되면서 결국 문제가 발생하게 된다.
    // 같은 메모리 주소를 공유하지 않게 되므로 서로 다른 데이터베이스(메모리) 로 인식을 해버리기 때문이다.

    // 같은 Repository 를 가지고 테스트를 해야하는데 동일한 메모리 주소를 공유하고 있을지언정 인스턴스가 서로 다르기 때문에
    // 서로 다른 Repository 를 가지고 테스트를 하고 있다고 봐도 무방하다.
    // 여기서 두 변수를 같은 인스턴스를 활용하는 것으로 바꾸려면 코드를 아래와 같이 작성한다.

    // MemberService 에서 memberRepository 변수에 대한 생성자를 만들어 준 후 테스트 파일에서 아래와 같이 코드를 작성한다.
    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository); // MemberService 객체 내부에서 memberRepository 객체 변수를 따로 생성하는 것이 아니라
        // 생성자를 통해 외부에서 데이터를 받아와서 해당 변수를 활용하도록 하면 static 키워드 없이도 같은 메모리 주소를 활용하도록 할 수 있다.
        // 테스트 파일에서 생성해준 memberRepository 객체 변수를 그대로 가져가서 활용하므로 서로 다른 변수 2개가 있는것이 아니라, 객체 변수를 하나만 선언해서 활용하는 것이다.
        // 애초에 사용하는 변수가 하나이기 때문에 서로 다른 변수가 되어 메모리 주소까지 서로 다른 공간을 쓰게 되는 불상사를 막을 수 있게 된다.

        // 이와 같은 경우를 DI(Dependency Injection) 라고 한다.
        // 아마 프로젝트에서 @AutoWired 어노테이션을 통해 의존성을 주입해준걸 이런 방식으로 코딩해 줄 수 있는것 같다.
    }


    @AfterEach // 각종 테스트 케이스 메소드들의 수행이 끝날때 마다 이 메소드를 수행하라는 뜻의 어노테이션(call-back 메소드)
    public void afterEach(){
        memberRepository.clearStore();
    }

    // 테스트 코드 메소드의 경우 굳이 영어가 아니라 한글로 해도 무방하다.
    // 테스트의 경우 영어권 사람들과 협업을 하는것이 아니라면 실제 사용되는 코드와 달리 한글로도 많이 작성하는 편이다.
    // 테스트 코드는 빌드 될 때 실제 코드에 포함되지 않는다.
    @Test
    void 회원가입() {
        // 테스트 코드를 작성할때 추천하는 양식 - given, when, then
        // 테스트 코드를 작성할 때 내용이 길어질 경우 각 역할을 하는 코드들을 위와 같은 주석들을 통해 분단하여
        // 각 역할을 하는 코드들을 좀 더 직관적으로 명확하게 확인할 수 있다.

        // given
        Member member = new Member();
        member.setName("spring");

        // when
        Long saveId = memberService.join(member);

        // then
        // Junit의 Asserstion 이 아닌 assertj 의 Assertion 을 활용한다.
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName()); // add on static : 단축키

        // 위와 같이 코드를 작성한 후 테스트를 수행하면 잘 동작하는 것을 확인할 수 있다.
    }
    
    // 위의 코드의 문제점? 너무 단순함
    // 테스트는 정상 동작을 테스트 하는것도 중요하지만 예외 처리에 대한 테스트를 하는 것도 굉장히 중요하다.
    // 위의 join 메소드 테스트는 동일한 이름의 회원가입에 대한 예외처리가 되어 있지 않으므로 반쪽짜리 테스트이다.

    @Test
    public void 중복_회원_예외(){
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring"); // 동일한 이름 중복처리 예외 테스트
        // when
        memberService.join(member1);


//        // 익셉션을 처리하기 위해 try - catch 문을 사용해 줄 수도 있다.
//        try {
//            memberService.join(member2); // 동일한 이름의 회원이 가입 하려고 하므로 validateDuplicateMember 메소드를 통해 익셉션이 발생한다.
//            fail(); // 익셉션이 발생하지 않고 코드가 정상적으로 동작하게 될 경우를 위해 fail() 메소드를 사용한다.
//        }catch (IllegalStateException e){
//            // 예외가 발생하면 정상적으로 수행되는 코드 구간
//            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다."); // 오류 메세지가 validateDuplicateMember 메소드에 적혀있는 전달 메세지와 같은지 확인한다.
//            // 메세지 내용이 다를 경우 테스트가 실패한다.
//        }

        // try- catch 문을 사용하지 않고도 예외처리 테스트를 할 수 있도록 문법을 제공해주는 것이 있다.
//        assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        // 람다식을 통해 실행되는 join 메소드에서 IllegalStateException 익셉션이 throw 되는지 확인한다.
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다."); // 오류 전달 메세지도 확인가능하다.

        // then
    }
    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}