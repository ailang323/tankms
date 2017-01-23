package com.bytecompute.tankms.messaging;

/**
 * Created by Longer on 2016/11/30.
 */
public interface MessageChannel<E> {

    void putMessage(E t);

    Object getMessage();

    boolean commit();
}
