package com.kafka.stream.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AmountTransaction {
    private String name;
    private BigDecimal amount;
    private LocalDateTime timeStamp;
}
