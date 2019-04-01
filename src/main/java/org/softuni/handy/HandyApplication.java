package org.softuni.handy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class HandyApplication {

    public static void main(String[] args) {
        SpringApplication.run(HandyApplication.class, args);
    }

}
