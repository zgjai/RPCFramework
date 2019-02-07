package com.zgjai.rpc.provider;

import com.zgjai.rpc.annotation.RPCProvider;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * Created by zhangguijiang on 2019/2/7.
 */
@Component
public class ProviderBeanPublisher implements BeanPostProcessor {

    private RegisterCenter registerCenter = RegisterCenter.getInstance();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class clazz = bean.getClass();
        if (!clazz.isAnnotationPresent(RPCProvider.class)) {
            return bean;
        }
        RPCProvider provider = (RPCProvider) clazz.getAnnotation(RPCProvider.class);
        if (!provider.serviceInterface().isAssignableFrom(bean.getClass())) {
            throw new BeanCreationException(bean.getClass().getName() + " haven't implement "
                    + provider.serviceInterface());
        }
        registerCenter.registerAndPublishService(provider.serviceInterface().getName(), provider.serviceVersion(), provider.serviceHost(),
                provider.servicePort(), bean);
        return bean;
    }
}
