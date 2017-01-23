package com.bytecompute.tankms;

import com.bytecompute.tankms.messaging.TopicMessageHandlerBeanPostProcessor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;

/**
 * Created by Longer on 2016/12/1.
 */
@Configurable
public class TankMessageServiceBootstrapConfiguration {

    @SuppressWarnings("rawtypes")
    @Bean(name = TankMessageServiceConfigUtils.TOPIC_HANDLER_ANNOTATION_PROCESSOR_BEAN_NAME)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public TopicMessageHandlerBeanPostProcessor topicHandlerAnnotationProcessor() {
        return new TopicMessageHandlerBeanPostProcessor();
    }
}
