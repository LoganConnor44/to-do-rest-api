package com.loganconnor44;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//@SpringBootApplication commented out because this is the updated
//annotation for the three below - but intellij isn't working properly with it
// maybe with an update to intellij this will work but for the moment I'll just add all separately
@EnableAutoConfiguration
@Configuration
@ComponentScan("com.loganconnor44")
public class ToDoRestAPIApplication {
    public static void main(String[] args) {
        SpringApplication.run(ToDoRestAPIApplication.class, args);
    }
}