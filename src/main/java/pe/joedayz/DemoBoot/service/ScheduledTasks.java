package pe.joedayz.DemoBoot.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Component
public class ScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");


    //Los métodos que se van a ejecutar como tareas programadas, deben ser void
    //y sin argumentos

    @Scheduled(fixedRate = 2000)
    public void scheduleTaskWithFixedRate() {

        logger.info("Método con Tiempo Fijo - Tiempo de Ejecución {}", dateTimeFormatter.format(LocalDateTime.now()));

    }

    @Scheduled(fixedDelay = 2000)
    public void scheduleTaskWithFixedDelay() {
        logger.info("Método con  Delay  :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException ex) {
            logger.error("Ran into an error {}", ex);
            throw new IllegalStateException(ex);
        }
    }


    //@Scheduled(cron = "[Seconds] [Minutes] [Hours] [Day of month] [Month] [Day of week] [Year]")
    @Scheduled(cron = "0 * * * * ?")   //cada minuto
    /*
            Ejemplos:

            @Scheduled(cron = "0 0 12 * * ?")  12 AM
            @Scheduled(cron = "0 15 10 * * ? 2018")   Todos los dias a las 10:15 del 2018
            @Scheduled(cron = "0/20 * * * * ?")  Cada 20 segundos

     */
    public void scheduleTaskWithCronExpression() {
        logger.info("Cron Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));

    }
}
