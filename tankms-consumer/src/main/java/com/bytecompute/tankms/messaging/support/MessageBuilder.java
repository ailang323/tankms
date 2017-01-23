package com.bytecompute.tankms.messaging.support;

import com.bytecompute.tankms.messaging.Message;
import com.bytecompute.tankms.messaging.MessageChannel;

/**
 * Created by Longer on 2016/11/30.
 */
public final class MessageBuilder<T> {

    private final T inferredType;

    private MessageChannel messageChannel;

    public MessageBuilder(T inferredType, MessageChannel messageChannel) {
        this.inferredType = inferredType;
        this.messageChannel = messageChannel;
    }

    public <K, T> Message<K, T> createMessage(K messageKey) {
        return new GenericJsonToObjectMessage(messageKey, this.inferredType, this.messageChannel);
    }

    public static <T> MessageBuilder<T> build(T inferredType, MessageChannel messageChannel) {
        return new MessageBuilder(inferredType, messageChannel);
    }
}
