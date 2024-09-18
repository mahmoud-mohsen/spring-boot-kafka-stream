package com.kafka.stream.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Map;

/**
 * Custom exception handler to handle the exceptions that can happened in case of producing the kafka event
 */
@Slf4j
public class ProductionExceptionHandler implements org.apache.kafka.streams.errors.ProductionExceptionHandler {


    @Override
    public ProductionExceptionHandlerResponse handle(ProducerRecord<byte[], byte[]> producerRecord, Exception e) {
        log.error("Exception happened in kafka producing: {}, with record: {}", e.getMessage(), producerRecord, e);

        return ProductionExceptionHandlerResponse.CONTINUE;
    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
