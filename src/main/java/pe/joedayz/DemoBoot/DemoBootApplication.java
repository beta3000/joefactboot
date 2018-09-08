package pe.joedayz.DemoBoot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import pe.joedayz.DemoBoot.domain.CabeceraDummy;
import pe.joedayz.DemoBoot.repository.CabeceraDummyRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableScheduling
public class DemoBootApplication {


	@Autowired
	private CabeceraDummyRepository cabeceraDummyRepository;

	public static void main(String[] args) {
		SpringApplication.run(DemoBootApplication.class, args);
	}


	@PostConstruct
	public void init(){

		List<CabeceraDummy> list = new ArrayList<>();


		CabeceraDummy cabeceraDummy1 = new CabeceraDummy();
		cabeceraDummy1.setDocuCodigo(1l);
		cabeceraDummy1.setEmprNroruc("10257235250");
		list.add(cabeceraDummy1);

		CabeceraDummy cabeceraDummy2 = new CabeceraDummy();
        cabeceraDummy2.setDocuCodigo(2l);
        cabeceraDummy2.setEmprNroruc("10411910890");
		list.add(cabeceraDummy2);

		cabeceraDummyRepository.save(list);


	}


}
