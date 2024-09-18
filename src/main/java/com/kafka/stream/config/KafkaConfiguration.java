package com.kafka.stream.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kafka.stream.topology.GreetingTopology;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class KafkaConfiguration {
    @Autowired
    KafkaProperties kafkaProperties;

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    /**
     * In case we need to handle the exception using the spring boot deserialization exception handler and take action in case the exception has been happened
     */
//    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
//    public KafkaStreamsConfiguration kStreamsConfigs() {
//
//        Map<String, Object> properties = kafkaProperties.buildStreamsProperties(null);
//        properties.put(StreamsConfig.DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG, RecoveringDeserializationExceptionHandler.class);
//        properties.put(RecoveringDeserializationExceptionHandler.KSTREAM_DESERIALIZATION_RECOVERER, recoverer());
//
//        return new KafkaStreamsConfiguration(properties);
//    }

//    private ConsumerRecordRecoverer recoverer() {
//        return (consumerRecord, e) -> {
//            log.error("Exception in Consuming Record :: {} with message:: {}", consumerRecord, e.getMessage(), e);
//        };
//    }
    @Bean
    public NewTopic greetingTopic() {
        return new NewTopic(GreetingTopology.greeting_topic, 1, (short) 1);
    }

    @Bean
    public NewTopic greetingOutputTopic() {
        return new NewTopic(GreetingTopology.greeting_output_topic, 1, (short) 1);
    }
}
