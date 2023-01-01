package com.example.customerDatabase1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.JmsListener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class CustomerDatabase1Application {

	public static void main(String[] args) {
		SpringApplication.run(CustomerDatabase1Application.class, args);
	}

	@JmsListener(destination = "Queue")
	public void handle(Message message) {

		Date receiveTime = new Date();

		if (message instanceof TextMessage) {
			TextMessage tm = (TextMessage) message;
			try {
				System.out.println(
						"Message Received at " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(receiveTime)
								+ " with message content of: " + tm.getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println(message.toString());
		}
	}
}
