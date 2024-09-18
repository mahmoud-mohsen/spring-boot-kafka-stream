package com.kafka.stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@SpringBootApplication
@EnableKafkaStreams
public class SpringBootKafkaStreamApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootKafkaStreamApplication.class, args);
	}

}
