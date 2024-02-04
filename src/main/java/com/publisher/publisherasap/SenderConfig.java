package com.publisher.publisherasap;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SenderConfig {

	@Value("${fila.create}")
	private String nameCreate;

	@Bean(name = "queueCreate")
	public Queue queueCreate() {
		return new Queue(nameCreate, true);
	}

	public String getName() {
		return this.nameCreate;
	}
}
