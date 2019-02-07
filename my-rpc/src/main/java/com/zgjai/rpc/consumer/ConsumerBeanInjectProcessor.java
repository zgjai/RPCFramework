package com.zgjai.rpc.consumer;

import java.lang.reflect.Field;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import com.zgjai.rpc.annotation.RPCConsumer;

/**
 * Created by zhangguijiang on 2019/2/7.
 */
@Component
public class ConsumerBeanInjectProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            // 如果该域被RPCConsumer注解，则注入proxy类
            if (field.isAnnotationPresent(RPCConsumer.class)) {
                if (!field.getType().isInterface()) {
                    throw new BeanCreationException("RPC consumer must be used at interface");
                }
                RPCConsumer consumer = field.getAnnotation(RPCConsumer.class);
                Object proxy = ConsumerProxy.getProxy(field.getType(), consumer.serviceHost(), consumer.servicePort(),
                        consumer.serviceVersion(), consumer.timeout());
                try {
                    // 注入代理对象
                    field.set(bean, proxy);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return bean;
    }
}
