package ru.gofc.smart_home;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.gofc.smart_home.snapshot.AggregatorStarter;

@SpringBootApplication
public class Aggregator {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Aggregator.class, args);

        AggregatorStarter starter = context.getBean(AggregatorStarter.class);

        starter.start();
    }
}
