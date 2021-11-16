package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect // 이 어노테이션이 있어야 이 클래스를 AOP 로 사용 가능해진다.
public class TimeTraceAop {

    // hellospring 패키지 하위에 있는 클래스들에 모두 적용시킨다는 뜻
    // SpringConfig 클래스는 제외시킨다.(순환 참조오류 방지)
    @Around("execution(* hello.hellospring..*(..)) && !target(hello.hellospring.SpringConfig)")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("START : " + joinPoint.toString());
        try {
            return joinPoint.proceed(); // 다음 메소드로 진행시켜 주는 메소드
        }finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END : " + joinPoint.toString() + " " + timeMs + "ms");
        }
    }
}
