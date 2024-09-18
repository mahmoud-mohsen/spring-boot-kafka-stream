package com.kafka.stream.topology;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.stream.model.Greeting;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@Slf4j
public class GreetingTopology {
    private final ObjectMapper objectMapper;
    public static String greeting_topic = "greeting";
    public static String greeting_output_topic = "greeting_output";

    public GreetingTopology(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Autowired
    public void process(StreamsBuilder streamsBuilder) {
        KStream<String, Greeting> stream = streamsBuilder.stream(greeting_topic, Consumed.with(Serdes.String(), new JsonSerde<>(Greeting.class, objectMapper)));
        KStream<String, Greeting> processedStream = stream.mapValues((s, greeting) -> {
            return new Greeting(greeting.getMessage().toUpperCase(Locale.ROOT), greeting.getTimeStamp());
        });
        processedStream.print(Printed.<String, Greeting>toSysOut().withLabel("greeting_processedStream"));
        processedStream.to(greeting_output_topic, Produced.with(Serdes.String(), new JsonSerde<>(Greeting.class, objectMapper)));
    }

}
