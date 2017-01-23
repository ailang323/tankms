package com.bytecompute.tankms.consumer;

import ch.qos.logback.core.util.CachingDateFormatter;

import com.bytecompute.tankms.TankMessageServiceConfigUtils;
import com.bytecompute.tankms.messaging.Message;
import com.bytecompute.tankms.messaging.MessageChannel;
import com.bytecompute.tankms.messaging.MessageHandler;
import com.bytecompute.tankms.messaging.TopicMessageHandlerBeanPostProcessor;
import com.bytecompute.tankms.messaging.support.KafkaMessageChannel;
import com.bytecompute.tankms.messaging.support.MessageBuilder;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.support.Acknowledgment;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by Longer on 2016/11/16.
 */
public class KafkaMessageListenerAdapter<K, V> implements MessageListener<K, V>, AcknowledgingMessageListener<K, V> {

    private static final Logger logger = LoggerFactory.getLogger(KafkaMessageListenerAdapter.class);

    private static final CachingDateFormatter cachingDateFormatter = new CachingDateFormatter("yyyy-MM-dd HH:mm:ss");

    private MessageChannel messageChannel;

    @Resource(name = TankMessageServiceConfigUtils.TOPIC_HANDLER_ANNOTATION_PROCESSOR_BEAN_NAME)
    private TopicMessageHandlerBeanPostProcessor<K, V> topicMessageHandlerBeanPostProcessor;

    private Map<String, MessageHandler> messageHandlers;

    public void initMessageHandlers() {
        messageHandlers = topicMessageHandlerBeanPostProcessor.getMessageHandlers();
    }

    @Override
    public void onMessage(ConsumerRecord<K, V> data, Acknowledgment acknowledgment) {
        logger.info(cachingDateFormatter.format(System.currentTimeMillis()) + "-" + data.toString());
        // router topic
        String topic = data.topic();

        MessageHandler<K, V> messageHandler = messageHandlers.get(topic);
        if (null == messageHandler) {
            // TODO：需要处理 未找到注册的MessageHandler
            throw new RuntimeException("not found MessagHandler Instance");
        }
        // 获取运行时泛型
        Type messageType = ((ParameterizedType) messageHandler.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
        // create MessageChannel , MessageBuilder
        messageChannel = new KafkaMessageChannel(acknowledgment);
        messageChannel.putMessage(data);
        Message message = MessageBuilder.build(messageType, messageChannel).createMessage(data.key());
        messageHandler.handler(message);
    }

    @Override
    public void onMessage(ConsumerRecord<K, V> data) {
        onMessage(data, null);
    }
}
