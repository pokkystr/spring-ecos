package com.ktb.api.hysrtix.circuitBreaker;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@EnableHystrixDashboard
@EnableHystrix
@EnableCircuitBreaker
@SpringBootApplication
@RestController
public class SenderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SenderApplication.class, args);
    }

    @GetMapping("/sample")
    @HystrixCommand(fallbackMethod = "hystrixFallBack",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "6000")})
    public String sample(@RequestParam("user") String user) {

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> resp = restTemplate.getForEntity("http://localhost:8081/api/receiver/random/sleep?user=" + genUser(user), String.class);
        System.out.println("Response " + resp.getBody());

        return resp.getBody();
    }

    private String genUser(String prefixUser) {
        Random rand = new Random();
        return prefixUser + "_" + rand.nextInt(99);
    }

    public String hystrixFallBack(String user) {
        System.out.println("Hystrix Fall Back ......." + user);
        return user + " Please try again !!!";
    }

}
