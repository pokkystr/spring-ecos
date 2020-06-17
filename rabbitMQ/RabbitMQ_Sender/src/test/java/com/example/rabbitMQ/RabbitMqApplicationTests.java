package com.example.rabbitMQ;

import com.example.rabbitMQ.amqp.MessageSender;
import com.example.rabbitMQ.bean.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMqApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	private MessageSender sender;

	@Test
	public void test_sendMessage_topic() {
		Random a = new Random();

		Customer customer = null;
		for (int i = 0; i < 1000; i++) {
			customer = new Customer();
			customer.setAge(31);
			customer.setEmail("mail.com");
			customer.setFirstName("pigke");
			customer.setLastName("Kulk");
			customer.setId(a.nextLong());
			sender.sendTopicMessage(customer);
		}
	}


	@Test
	public void test_sendMessage_Direct() {
		Random a = new Random();

		Customer customer = null;
		for (int i = 0; i < 1000; i++) {
			customer = new Customer();
			customer.setAge(31);
			customer.setEmail("mail.com");
			customer.setFirstName("pigke");
			customer.setLastName("Kulk");
			customer.setId(a.nextLong());
			sender.sendDirectMessage(customer);
		}
	}

}
