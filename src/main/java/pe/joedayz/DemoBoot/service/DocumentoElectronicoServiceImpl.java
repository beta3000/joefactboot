package pe.joedayz.DemoBoot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.joedayz.DemoBoot.domain.beans.DocumentoBean;
import pe.joedayz.DemoBoot.domain.beans.Leyenda;
import pe.joedayz.DemoBoot.repository.jdbc.DocumentoElectronicoRepository;
import pe.joedayz.DemoBoot.util.Util;

import java.util.List;

@Service
public class DocumentoElectronicoServiceImpl implements DocumentoElectronicoService{

    private static final Logger logger = LoggerFactory.getLogger(DocumentoElectronicoServiceImpl.class);

    @Autowired
    private DocumentoElectronicoRepository documentoElectronicoRepository;

    static {
        org.apache.xml.security.Init.init();
    }




    public String generarXmlFacturaFirmadoYEnviarASunat(String iddocument){

        String resultado = "";
        String nrodoc = iddocument;
        String unidadEnvio;
        String pathXMLFile;

        try{

            DocumentoBean items = documentoElectronicoRepository.cargarDocElectronico(nrodoc);
            List<DocumentoBean> detdocelec = documentoElectronicoRepository.cargarDetDocElectronico(nrodoc);
            List<DocumentoBean> anticipos = documentoElectronicoRepository.cargarDetDocElectronicoAnticipo(nrodoc);
            List<Leyenda> leyendas = documentoElectronicoRepository.cargarDetDocElectronicoLeyenda(nrodoc);
            unidadEnvio = Util.getPathZipFilesEnvio(items.getEmpr_nroruc());
            documentoElectronicoRepository.guardarProcesoEstado(nrodoc, "P", " | ".split("\\|", 0), "");

            if (items != null) {
                pathXMLFile = unidadEnvio + items.getEmpr_nroruc() + "-01-" + items.getDocu_numero() + ".xml";

                //======================crear XML =======================
                resultado = creaXml(items, detdocelec, anticipos, leyendas, unidadEnvio);

            }
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return resultado;
    }

    private  String creaXml(DocumentoBean items, List<DocumentoBean> detdocelec, List<DocumentoBean> anticipos, List<Leyenda> leyendas, String unidadEnvio) {

        //TODO: Crear el Xml 
        return "";
    }
}
