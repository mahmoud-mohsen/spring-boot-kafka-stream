package com.kafka.stream.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.errors.StreamsUncaughtExceptionHandler;
import org.apache.kafka.streams.processor.ProcessorContext;

import java.util.Map;

/**
 * Custom exception handler to handle the exceptions that can happened in case of processing kafka event
 */
@Slf4j
public class ProcessingExceptionHandler implements StreamsUncaughtExceptionHandler {

    @Override
    public StreamThreadExceptionResponse handle(Throwable e) {
        log.error("Exception happened in kafka processing: {}", e.getMessage(), e);
        return StreamThreadExceptionResponse.SHUTDOWN_CLIENT;
    }
}
