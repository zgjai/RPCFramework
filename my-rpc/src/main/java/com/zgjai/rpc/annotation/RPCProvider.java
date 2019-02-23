package com.zgjai.rpc.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Rpc provider annotation
 * 服务提供者注解，实现服务自动发布
 * 服务提供者所需信息：
 * 1. 服务提供者接口类
 * 2. 服务版本
 * 3. 服务注册地址
 * 4. 服务注册端口号
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface RPCProvider {

    Class<?> serviceInterface();

    String serviceVersion() default "1.0.0";

    String serviceHost() default "localhost";

    int servicePort() default 8088;

}
