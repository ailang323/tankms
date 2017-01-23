package com.bytecompute.tankms.messaging;

/**
 * Created by Longer on 2016/11/30.
 */
public interface Message<K, V> {

    K getMessageKey();

    V getData();

    boolean commit();
}
