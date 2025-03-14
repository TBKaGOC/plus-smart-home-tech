package ru.gofc.smart_home;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.gofc.smart_home.snapshot.AggregatorStarter;

@SpringBootApplication
public class Aggregator {
    private static AggregatorStarter starter;

    public static void main(String[] args) {
        SpringApplication.run(Aggregator.class, args);

        starter.start();
    }

    @Autowired
    public static void setStarter(AggregatorStarter aggregatorStarter) {
        starter = aggregatorStarter;
    }
}
