package com.bytecompute.tankms.messaging.support;

import com.bytecompute.tankms.messaging.Message;
import com.bytecompute.tankms.messaging.MessageChannel;
import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by Longer on 2016/11/30.
 */
public class GenericJsonToObjectMessage<K, V> implements Message<K, V>, Serializable {

    private Gson gson;

    private MessageChannel messageChannel;

    private final K messageKey;

    private final V data;

    public GenericJsonToObjectMessage(K messageKey, V data, MessageChannel messageChannel) {
        this.messageKey = messageKey;
        this.data = data;
        this.messageChannel = messageChannel;
        this.gson = new Gson();
    }

    @Override
    public K getMessageKey() {
        return this.messageKey;
    }

    @Override
    public V getData() {
        Object json = this.messageChannel.getMessage();
        if (null == json) {
            return null;
        }
        // parse String
        if (data instanceof String) {
            return (V) json.toString();
        }
        // parse Object Entity
        return gson.fromJson((String) json, (Class<V>) data);
    }

    @Override
    public boolean commit() {
        return messageChannel.commit();
    }
}
