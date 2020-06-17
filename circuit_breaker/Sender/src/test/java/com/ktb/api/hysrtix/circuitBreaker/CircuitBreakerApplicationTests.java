package com.ktb.api.hysrtix.circuitBreaker;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CircuitBreakerApplicationTests {

	@Test
	public void contextLoads() {
		System.out.println(genUser("Hello"));
	}

	private String genUser(String prefixUser) {
		Random rand = new Random();
		return prefixUser + "_" + rand.nextInt(99);
	}

}
