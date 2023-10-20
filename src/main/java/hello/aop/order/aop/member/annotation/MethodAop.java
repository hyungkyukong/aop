package hello.aop.order.aop.member.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
public @interface MethodAop {
    String value();
}
