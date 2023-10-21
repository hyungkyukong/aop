package hello.aop.pointcut;

import hello.aop.order.aop.member.annotation.MemberService;
import hello.aop.order.aop.member.annotation.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class WithinTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void withinExact() {
        //public java.lang.String hello.aop.order.aop.member.annotation.MemberServiceImpl.hello(java.lang.String)
        pointcut.setExpression("within (hello.aop.order.aop.member.annotation.MemberServiceImpl)");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void withinStar() {
        pointcut.setExpression("within (hello.aop.order.aop.member.annotation.*Service*)");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void withinSubPackage() {
        //public java.lang.String hello.aop.order.aop.member.annotation.MemberServiceImpl.hello(java.lang.String)
        pointcut.setExpression("within (hello.aop..*)");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("타겟의 타입에만 직접 사용, 인터페이스를 선정하면 안된다.")
    void withinSuperTypeFalse() {
        //public java.lang.String hello.aop.order.aop.member.annotation.MemberServiceImpl.hello(java.lang.String)
        pointcut.setExpression("within (hello.aop.order.aop.member.annotation.MemberService)");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    @DisplayName("execution은 타입기반, 인터페이스 선정 가능")
    void executionSuperTypeTrue() {
        //public java.lang.String hello.aop.order.aop.member.annotation.MemberServiceImpl.hello(java.lang.String)
        pointcut.setExpression("execution (* hello.aop.order.aop.member.annotation.MemberService.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

}
