package ru.gofc.smart_home;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"ru.gofc.smart_home"})
public class EventsServer {
    public static void main(String[] args) {
        SpringApplication.run(EventsServer.class, args);
    }
}
