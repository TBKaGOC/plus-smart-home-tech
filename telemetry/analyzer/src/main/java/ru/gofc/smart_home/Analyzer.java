package ru.gofc.smart_home;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.gofc.smart_home.hub.HubEventProcessor;
import ru.gofc.smart_home.snapshot.SnapshotProcessor;

@SpringBootApplication
public class Analyzer {
    public static void main(String[] args) {
        ConfigurableApplicationContext context =
                SpringApplication.run(Analyzer.class, args);

        final HubEventProcessor hubEventProcessor =
                context.getBean(HubEventProcessor.class);
        final SnapshotProcessor snapshotProcessor =
                context.getBean(SnapshotProcessor.class);

        // запускаем в отдельном потоке обработчик событий
        // от пользовательских хабов
        Thread hubEventsThread = new Thread(hubEventProcessor);
        hubEventsThread.setName("HubEventHandlerThread");
        hubEventsThread.start();

        // В текущем потоке начинаем обработку
        // снимков состояния датчиков
        snapshotProcessor.run();
    }
}
