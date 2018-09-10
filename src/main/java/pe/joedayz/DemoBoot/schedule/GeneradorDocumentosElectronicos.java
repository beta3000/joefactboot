package pe.joedayz.DemoBoot.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.joedayz.DemoBoot.domain.beans.DocumentoBean;
import pe.joedayz.DemoBoot.repository.jdbc.DocumentoElectronicoRepository;
import pe.joedayz.DemoBoot.service.DocumentoElectronicoService;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class GeneradorDocumentosElectronicos {

    private static final Logger logger = LoggerFactory.getLogger(GeneradorDocumentosElectronicos.class);

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Autowired
    private DocumentoElectronicoService documentoElectronicoService;

    @Autowired
    private DocumentoElectronicoRepository documentoElectronicoRepository;

    @Transactional
    public void generator() {

        logger.info("Generador Documentos :: Inicio de Ejecución - {}", dateTimeFormatter.format(LocalDateTime.now()));

        try {


            DocumentoBean documentoBean = documentoElectronicoRepository.pendienteDocElectronico();
            String iddoc = null;
            String tipodoc = null;
            String result = "x";
            if (documentoBean != null && documentoBean.getDocu_tipodocumento().trim() != null) {
                iddoc = documentoBean.getDocu_codigo();
                tipodoc = documentoBean.getDocu_tipodocumento().trim();

                guardarProcesoEstado(iddoc, "B", " | ".split("\\|", 0), "");

                switch (tipodoc) {
                    case "01":
                        result = documentoElectronicoService.generarXmlFacturaFirmadoYEnviarASunat(iddoc);
                        break;
                    case "03":
                        //result = BolElectronica.generarXMLZipiadoBoleta(iddoc, conn);
                        break;
                    case "07":   //Nota de Credito
                        //result = NCElectronica.generarXMLZipiadoNC(iddoc, conn);
                        break;
                    case "08":   //Nota de Debito
                        //result = NDElectronica.generarXMLZipiadoND(iddoc, conn);
                        break;
                    case "20":   //Retenciones
                        //result = RetElectronica.generarXMLZipiadoRetencion(iddoc, conn);
                        break;
                    case "RS":  //resumen
                        //result = ResElectronica.enviarResumenASunat(iddoc, conn);//enviarZipASunat
                        break;
                    case "RA":  //Anulacion
                        //result = ResumenBaja.enviarBajaASunat(iddoc, conn);//enviarZipASunat
                        break;

                    default:
                        result = "0100|Operacion nula";
                        break;
                }
            }
            if (!result.equals("x")) {

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    void guardarProcesoEstado(String trans, String proceso, String[] cdr, String error)
            throws SQLException {

        documentoElectronicoRepository.guardarProcesoEstado(trans, proceso, cdr, error);

        documentoElectronicoRepository.guardarSeguimiento(trans, proceso, cdr, error);
    }
}
