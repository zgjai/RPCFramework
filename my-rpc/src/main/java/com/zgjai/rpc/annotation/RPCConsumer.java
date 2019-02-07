package com.zgjai.rpc.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Rpc consumer annotation
 * 服务消费者注解，实现服务自动注入
 * 服务消费者所需信息：
 * 1. 服务版本
 * 2. 服务注册地址
 * 3. 服务注册端口
 * 4. 服务调用超时时间
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Component
public @interface RPCConsumer {

    String serviceVersion() default "1.0.0";

    String serviceHost() default "localhost";

    int servicePort() default 8088;

    int timeout();

}
