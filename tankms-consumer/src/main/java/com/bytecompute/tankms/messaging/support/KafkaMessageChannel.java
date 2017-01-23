package com.bytecompute.tankms.messaging.support;

import ch.qos.logback.core.util.CachingDateFormatter;
import com.bytecompute.tankms.messaging.MessageChannel;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.Acknowledgment;

/**
 * Created by Longer on 2016/11/30.
 */
public class KafkaMessageChannel implements MessageChannel<ConsumerRecord> {

    private static final Logger logger = LoggerFactory.getLogger(KafkaMessageChannel.class);

    private static final CachingDateFormatter cachingDateFormatter = new CachingDateFormatter("yyyy-MM-dd HH:mm:ss");

    private ConsumerRecord kvConsumerRecord;

    private Acknowledgment acknowledgment;

    public KafkaMessageChannel(Acknowledgment acknowledgment) {
        this.acknowledgment = acknowledgment;
    }

    @Override
    public void putMessage(ConsumerRecord kvConsumerRecord) {
        this.kvConsumerRecord = kvConsumerRecord;
    }

    @Override
    public Object getMessage() {
        return kvConsumerRecord.value();
    }

    @Override
    public boolean commit() {
        if (null == acknowledgment) {
            return true;
        }
        acknowledgment.acknowledge();

        logger.info(cachingDateFormatter.format(System.currentTimeMillis()) + "-commit-" + kvConsumerRecord.toString());
        return true;
    }
}
