package ru.gofc.smart_home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.gofc.smart_home.snapshot.AggregatorStarter;

@SpringBootApplication
public class Aggregator {
    @Autowired
    private static AggregatorStarter starter;

    public static void main(String[] args) {
        SpringApplication.run(Aggregator.class, args);

        starter.start();
    }
}
