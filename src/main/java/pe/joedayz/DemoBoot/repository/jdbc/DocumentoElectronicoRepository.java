package pe.joedayz.DemoBoot.repository.jdbc;

import pe.joedayz.DemoBoot.domain.beans.DocumentoBean;
import pe.joedayz.DemoBoot.domain.beans.Leyenda;

import java.sql.SQLException;
import java.util.List;

public interface DocumentoElectronicoRepository {

        DocumentoBean pendienteDocElectronico();

        void guardarProcesoEstado(String trans, String proceso, String[] cdr, String error) throws SQLException ;

        void guardarSeguimiento(String trans, String proceso, String[] cdr, String error) throws SQLException ;

        DocumentoBean cargarDocElectronico(String pdocu_codigo);

        List<DocumentoBean> cargarDetDocElectronico(String pdocu_codigo) throws SQLException;

        List<DocumentoBean> cargarDetDocElectronicoAnticipo(String pdocu_codigo) throws SQLException;

        List<Leyenda> cargarDetDocElectronicoLeyenda(String pdocu_codigo) throws SQLException;


}
