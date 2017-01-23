package com.bytecompute.tankms.messaging;

import com.bytecompute.tankms.annotation.TopicRouter;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Longer on 2016/12/1.
 */
public class TopicMessageHandlerBeanPostProcessor<K, V> implements BeanPostProcessor, Ordered, BeanFactoryAware, SmartInitializingSingleton {

    private Map<String, MessageHandler> messageHandlers = new ConcurrentHashMap<>();

    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void afterSingletonsInstantiated() {

    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> targetClass = AopUtils.getTargetClass(bean);
        setMessageHandlers(bean, targetClass);
        return bean;
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }

    public Map<String, MessageHandler> getMessageHandlers() {
        return messageHandlers;
    }

    private void setMessageHandlers(Object bean, Class<?> clazz) {
        Class<?>[] clazzs = clazz.getInterfaces();
        for (Class<?> typeClazz : clazzs) {
            if (typeClazz.isAssignableFrom(MessageHandler.class)) {
                TopicRouter topicRouter = AnnotationUtils.findAnnotation(clazz, TopicRouter.class);
                // 只注册标注TopicRouter的MessageHandler实例
                if (null != topicRouter) {
                    String routerKey = topicRouter.name();
                    messageHandlers.put(routerKey, (MessageHandler) bean);
                }
            }
        }
    }
}
