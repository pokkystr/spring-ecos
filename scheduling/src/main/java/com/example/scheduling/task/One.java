package com.example.scheduling.task;


import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
public class One {

    @Scheduled(cron = "${contab}")
    public void taskOne() {
        System.out.println(" loop cron - " + LocalDateTime.now());
    }
}
