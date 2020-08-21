package com.bookstore.notification.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumerService {
	Logger log = LoggerFactory.getLogger(NotificationConsumerService.class);

	@Autowired
	private JavaMailSender javaMailSender;

	@KafkaListener(topics = "book_order_topic", groupId = "consumer-group")
	public String sendEmailToCustomer(String message) {
		sendEmail(message);
		return null;
	}

	void sendEmail(String message) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo("bageshsharma2050@gmail.com");
		msg.setSubject("Order Placed at Bookstore");
		msg.setText("Hello,\n Your order is placed. \n You can expect delivery in next week.");
		javaMailSender.send(msg);
	}
}