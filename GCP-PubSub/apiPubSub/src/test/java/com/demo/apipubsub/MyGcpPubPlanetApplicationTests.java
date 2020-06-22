package com.demo.apipubsub;

import com.demo.apipubsub.service.impl.DemoSubscriptServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyGcpPubPlanetApplicationTests {

	@Autowired
	private DemoSubscriptServiceImpl pubSubBiding;


	@Test
	public void contextLoads() {
//		System.out.println(pubSubBiding.token());
	}

}
