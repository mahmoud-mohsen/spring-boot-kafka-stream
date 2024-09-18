package com.kafka.stream.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.ProcessorContext;

import java.util.Map;

@Slf4j
public class DeserializationExceptionHandler implements org.apache.kafka.streams.errors.DeserializationExceptionHandler {

    int errorCount = 0;

    @Override
    public DeserializationHandlerResponse handle(ProcessorContext processorContext, ConsumerRecord<byte[], byte[]> consumerRecord, Exception e) {
        log.error("Exception happened in deserialization: {}, with record: {}", e.getMessage(), consumerRecord, e);
        if (errorCount < 3) {
            errorCount++;
            return DeserializationHandlerResponse.CONTINUE;
        }

        return DeserializationHandlerResponse.FAIL;
    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
