package hello.hellospring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloSpringApplication {

	// 웹 어플리케이션 메인 메소드 실행
	public static void main(String[] args) {
		SpringApplication.run(HelloSpringApplication.class, args);
	}
	/*
	SpringApplication.run 메소드에 현재 메인 메소드를 포함하고 있는 클래스를 파라미터로 넘겨주고 실행시킨다.
	-> @SpringBootApplication 어노테이션을 통해 스프링 부트 어플리케이션이 실행된다.
	-> 스프링 부트 어플리케이션은 톰캣 서버를 내장하고 있다. 해당 서버를 자체적으로 띄우면서 스프링 부트가 서버에 같이 올라온다.
	 */

}
