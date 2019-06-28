package com.loganconnor44;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.loganconnor44")
public class ToDoRestAPIApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToDoRestAPIApplication.class, args);
    }
}