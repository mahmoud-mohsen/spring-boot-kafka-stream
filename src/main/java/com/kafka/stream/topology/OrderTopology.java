package com.kafka.stream.topology;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.stream.model.AmountTransaction;
import com.kafka.stream.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class OrderTopology {
    private final ObjectMapper objectMapper;
    public static String store_topic = "store";
    public static String greeting_output_topic = "greeting_output";

    public OrderTopology(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Autowired
    public void process(StreamsBuilder streamsBuilder) {
        KStream<String, Order> orderKStream = streamsBuilder
                .stream(store_topic,
                        Consumed.with(Serdes.String(), new JsonSerde<>(Order.class, objectMapper)));
        orderKStream.print(Printed.<String, Order>toSysOut().withLabel("orderKStream"));

        aggregate(orderKStream, "store");
    }


    private static void aggregate(KStream<String, Order> kTable, String type) {

        Initializer<AmountTransaction> transactionInitializer = AmountTransaction::new;

        Aggregator<String, Order, AmountTransaction> aggregator = (s, order, transaction) -> new AmountTransaction(order.getStoreName(), transaction.getAmount().add(order.getAmount()), LocalDateTime.now());

        KTable<String, AmountTransaction> aggregate = kTable
                .map((s, order) -> KeyValue.pair(order.getStoreName(), order))
                .groupByKey(Grouped.with(Serdes.String(), new JsonSerde<>(Order.class)))
                .aggregate(transactionInitializer, aggregator
                        , Materialized.<String, AmountTransaction, KeyValueStore<Bytes, byte[]>>as(type)
                                .withKeySerde(Serdes.String())
                                .withValueSerde(new JsonSerde<>(AmountTransaction.class)));

        aggregate.toStream().print(Printed.<String, AmountTransaction>toSysOut().withLabel(type));
    }
}
