package com.bookstore.notification.config;

import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
public class KafkaProperties {

	@Value("${spring.kafka.consumer.bootstrap-servers}")
	private String kafkaBootServer;
	@Value("${spring.kafka.consumer.key-deserializer}")
	private String keyDeserializer;
	@Value("${spring.kafka.consumer.value-deserializer}")
	private String valueDeserializer;
	@Value("${spring.kafka.template.default-topic}")
	private String topic;
	@Value("${spring.kafka.consumer.group-id}")
	private String groupId;

	public Properties getConsumerProp() {
		Properties consProp = new Properties();
		consProp.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootServer);
		consProp.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer);
		consProp.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializer);
		consProp.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		return consProp;
	}
}
