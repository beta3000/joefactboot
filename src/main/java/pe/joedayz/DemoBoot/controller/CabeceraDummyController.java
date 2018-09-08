package pe.joedayz.DemoBoot.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.joedayz.DemoBoot.domain.CabeceraDummy;
import pe.joedayz.DemoBoot.repository.CabeceraDummyRepository;

@RestController
public class CabeceraDummyController {


    @Autowired
    private CabeceraDummyRepository cabeceraDummyRepository;


    @RequestMapping("/dummys")
    public Iterable<CabeceraDummy> getCabeceras(){

        return cabeceraDummyRepository.findAll();

    }
}
