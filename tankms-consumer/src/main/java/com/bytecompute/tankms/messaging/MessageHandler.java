package com.bytecompute.tankms.messaging;

/**
 * Created by Longer on 2016/11/30.
 */
public interface MessageHandler<K, V> {

    void handler(Message<K, V> message);
}
