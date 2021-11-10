package hello.hellospring;

import hello.hellospring.repository.JdbcMemberRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
        // 파라미터로 넘어온 dataSource 값을 SpringConfig 클래스에 의존성 주입 해준다.
        // dataSource 의 경우 스프링 부트가 application.properties 에서 데이터베이스 연결에 대해 작성되어 있는 내용을 보고
        // 자동으로 스프링 컨테이너에 스프링 빈으로서 DataSource 객체를 등록해 두기 때문에
        // DataSource 객체를 개발자가 따로 스프링 빈에 등록해 줄 필요 없이 곧바로 의존성 주입을 해주는 것이 가능하다.
    }

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository(){
//        return new MemoryMemberRepository();
        return new JdbcMemberRepository(dataSource);
    }
}
