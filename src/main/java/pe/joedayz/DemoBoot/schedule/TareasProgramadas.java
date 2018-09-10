package pe.joedayz.DemoBoot.schedule;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TareasProgramadas {

    private static final Logger logger = LoggerFactory.getLogger(TareasProgramadas.class);


    @Autowired
    private GeneradorDocumentosElectronicos generadorDocumentosElectronicos;



    @Scheduled(cron = "0/2 * * * * ?")
    public synchronized void procesarDocumentosElectronicos() {

        try {
            generadorDocumentosElectronicos.generator();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
