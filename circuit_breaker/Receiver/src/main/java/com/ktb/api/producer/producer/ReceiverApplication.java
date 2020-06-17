package com.ktb.api.producer.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@SpringBootApplication
@RestController
public class ReceiverApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReceiverApplication.class, args);
    }

    @GetMapping("/random/sleep")
    public String sampleSleep(@RequestParam("user") String user) {
        boolean isSleep = randomlyRunLong();
        if (isSleep) {
            System.out.println("User " + user + " Is Sleep....");
            sleep();
        } else {
            System.out.println("User " + user + " Is Awake!! ");
        }

        return isSleep ? user + " is Sleep" : user + " is awake";
    }

    private boolean randomlyRunLong() {
        Random rand = new Random();
        boolean status = false;
        int randomNum = rand.nextInt(3) + 1;
        if (randomNum == 3) {
            status = true;
        }
        return status;
    }

    private void sleep() {
        try {
            Thread thread = new Thread();
            thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
