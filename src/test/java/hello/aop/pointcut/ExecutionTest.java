package hello.aop.pointcut;

import hello.aop.order.aop.member.annotation.MemberService;
import hello.aop.order.aop.member.annotation.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.*;

@Slf4j
public class ExecutionTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void printMethod() {
        log.info("helloMethod={}", helloMethod);
    }

    @Test
    void exactMatch() {
        //public java.lang.String hello.aop.order.aop.member.annotation.MemberServiceImpl.hello(java.lang.String)
        pointcut.setExpression("execution(public String hello.aop.order.aop.member.annotation.MemberServiceImpl.hello(String))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void allMatch() {
        pointcut.setExpression("execution(* *(..)) ");
        assertThat(pointcut.matches(helloMethod, MemberService.class)).isTrue();
    }

    @Test
    void nameMatch() {
        pointcut.setExpression("execution(* hello(..)) ");
        assertThat(pointcut.matches(helloMethod, MemberService.class)).isTrue();
    }

    @Test
    void nameMatchStar1() {
        pointcut.setExpression("execution(* hell*(..)) ");
        assertThat(pointcut.matches(helloMethod, MemberService.class)).isTrue();
    }

    @Test
    void nameMatchStar2() {
        pointcut.setExpression("execution(* *ell*(..)) ");
        assertThat(pointcut.matches(helloMethod, MemberService.class)).isTrue();
    }

    @Test
    void nameMatchFalse() {
        pointcut.setExpression("execution(* nono(..)) ");
        assertThat(pointcut.matches(helloMethod, MemberService.class)).isFalse();
    }

    @Test
    void packageExactMatch1() {
        pointcut.setExpression("execution(* hello.aop.order.aop.member.*.*(..)) ");
        assertThat(pointcut.matches(helloMethod, MemberService.class)).isTrue();
    }

    @Test
    void packageExactSubPackage1() {
        pointcut.setExpression("execution(* hello.aop.order.aop.member..*.*(..)) ");
        assertThat(pointcut.matches(helloMethod, MemberService.class)).isTrue();
    }

    @Test
    void packageExactSubPackage2() {
        pointcut.setExpression("execution(* hello.aop..*.*(..)) ");
        assertThat(pointcut.matches(helloMethod, MemberService.class)).isTrue();
    }
}
