package com.kafka.stream.model;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Greeting {
    private String  message;
    private LocalDateTime timeStamp;
}
