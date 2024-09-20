package com.kafka.stream.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Order {
    private String storeName;
    private String  type;
    private BigDecimal amount;
    private LocalDateTime timeStamp;
}
