package com.bookstore.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class BookOrderController {

	/*
	 * @Value("${spring.kafka.templatebook_order_topic}") private String topicName;
	 */

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@GetMapping(value = "/book-order")
	public String getOrderDetails() {
		return "Order 1";
	}

	@GetMapping(value = "/placeorder")
	public String placeOrder() {

		String msg = "Your order number is 3589y345y46";

		kafkaTemplate.send(kafkaTemplate.getDefaultTopic(), msg);

		return "Order 1";
	}
}
