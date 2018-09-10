package pe.joedayz.DemoBoot.repository.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import pe.joedayz.DemoBoot.domain.beans.DocumentoBean;
import pe.joedayz.DemoBoot.domain.beans.Leyenda;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DocumentoElectronicoJdbcRepository implements DocumentoElectronicoRepository {


    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentoElectronicoJdbcRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public DocumentoElectronicoJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    private static final String SEARCH_DOCUMENTOS_ELECTRONICOS_PENDIENTES = "select * "
            + " from facturaelectronica.cabecera "
            + " where  docu_proce_status = 'N' "
            + " order by docu_codigo LIMIT 1 ";


    private static final String SEARCH_DOCUMENTO_ELECTRONICO_BY_CODIGO =  "SELECT DOCU_CODIGO,"
     + " EMPR_RAZONSOCIAL,"
     +  " EMPR_UBIGEO,"
     + " EMPR_NOMBRECOMERCIAL,"
    + " EMPR_DIRECCION,"
    + " EMPR_PROVINCIA,"
    + " EMPR_DEPARTAMENTO,"
    + " EMPR_DISTRITO,"
    + " EMPR_PAIS,"
    + " EMPR_NRORUC,"
    + " EMPR_TIPODOC,"
    + " CLIE_NUMERO,"
    + " CLIE_TIPODOC,"
    + " CLIE_NOMBRE,"
    + " DOCU_FECHA,"
    + " DOCU_TIPODOCUMENTO,"
    + " DOCU_NUMERO,"
    + " DOCU_MONEDA,"
    + " DOCU_GRAVADA  as  DOCU_GRAVADA,"
    + " DOCU_INAFECTA  as  DOCU_INAFECTA,"
    + " DOCU_EXONERADA  as  DOCU_EXONERADA,"
    + " DOCU_GRATUITA  as  DOCU_GRATUITA,"
    + " DOCU_DESCUENTO  as  DOCU_DESCUENTO,"
    + " DOCU_SUBTOTAL  as  DOCU_SUBTOTAL,"
    + " DOCU_TOTAL  as  DOCU_TOTAL,"
    + " DOCU_IGV  as  DOCU_IGV,"
    + " TASA_IGV,"
    + " DOCU_ISC,"
    + " TASA_ISC,"
    + " DOCU_OTROSTRIBUTOS  as  DOCU_OTROSTRIBUTOS,"
    + " TASA_OTROSTRIBUTOS,"
    + " RETE_REGI," // 01 TASA 3%
    + " RETE_TASA," // 3%
    + " RETE_TOTAL_ELEC,"
    + " RETE_TOTAL_RETE,"
    + " DOCU_OTROSCARGOS  as  DOCU_OTROSCARGOS,"
    + " DOCU_PERCEPCION  as  DOCU_PERCEPCION,"
    + " NOTA_MOTIVO,"
    + " NOTA_SUSTENTO,"
    + " NOTA_TIPODOC,"
    + " NOTA_DOCUMENTO, "
    + " docu_enviaws, "
    + " idExterno, "
    + " clie_correo_cpe1, "
    + " clie_correo_cpe2, "
    + " clie_correo_cpe0, "
    // anticipos documenotos / 04 anticipos
    + " docu_tipo_operacion,  "
    + " docu_anticipo_total "
    + " FROM cabecera"
    + " WHERE  DOCU_CODIGO = :docuCodigo";


    private static final String SEARCH_DETALLE_DOCUMENTO_ELECTRONICO_BY_CODIGO = "SELECT  DOCU_CODIGO, "
    + " DOCU_MONEDA,"
    + " ITEM_MONEDA,"
    + " ITEM_ORDEN,"
    + " ITEM_UNIDAD,"
    + " ITEM_CANTIDAD,"
    + " ITEM_CODPRODUCTO,"
    + " ITEM_DESCRIPCION,"
    + " ITEM_AFECTACION,"
    + " ITEM_PVENTA, "
    + " item_pventa_nohonerosa,"
    + " ITEM_TI_SUBTOTAL,"
    + " ITEM_TI_IGV, "
    // retenciones
    + " rete_rela_tipo_docu, "
    + " rete_rela_nume_docu, "
    + " rete_rela_fech_docu, "
    + " rete_rela_tipo_moneda, "
    + " rete_rela_total_original, "
    + " rete_rela_fecha_pago, "
    + " rete_rela_numero_pago, "
    + " rete_rela_importe_pagado_original, "
    + " rete_rela_tipo_moneda_pago, "
    + " rete_importe_retenido_nacional, "
    + " rete_importe_neto_nacional, "
    + " rete_tipo_moneda_referencia, "
    + " rete_tipo_moneda_objetivo, "
    + " rete_tipo_moneda_tipo_cambio, "
    + " rete_tipo_moneda_fecha "
    // Retenciones
    + " FROM detalle"
    + " WHERE DOCU_CODIGO = :docuCodigo";


    private static final String SEARCH_DETALLE_DOCUMENTO_ELECTRONICO_ANTICIPO_BY_CODIGO="select docu_codigo "
            + "docu_anticipo_prepago, "
            + "docu_anticipo_docu_tipo, "
            + "docu_anticipo_docu_numero, "
            + "docu_anticipo_tipo_docu_emi, "
            + "docu_anticipo_numero_docu_emi "
            + "  from anticipos "
            + "  where docu_codigo= :docuCodigo";


    private static final String SEARCH_DETALLE_DOCUMENTO_ELECTRONICO_LEYENDA_BY_CODIGO = "select leyenda_codigo, "
            + " leyenda_texto "
            + " from  leyenda "
            + " where docu_codigo= :docuCodigo";


    @Override
    public DocumentoBean pendienteDocElectronico() {

        Map<String, String> queryParams = new HashMap<>();

        List<DocumentoBean> searchResults = jdbcTemplate.query(SEARCH_DOCUMENTOS_ELECTRONICOS_PENDIENTES,
                queryParams,
                new BeanPropertyRowMapper<>(DocumentoBean.class)
        );

        if (searchResults.size() > 0) {
            return searchResults.get(0);
        }

        return null;
    }

    @Override
    public void guardarProcesoEstado(String trans, String proceso, String[] cdr, String error) throws SQLException {


        Map<String, Object> queryParams = new HashMap<>();

        StringBuilder sql = new StringBuilder();
        sql.append(" update ");
        sql.append(" cabecera ");
        sql.append(" set docu_proce_status = '" + proceso + "',");
        sql.append(" docu_proce_fecha = now(), ");
        sql.append(" cdr = '" + cdr[0] + "',");
        sql.append(" cdr_nota = '"+ cdr[1] + "',");
        sql.append(" cdr_observacion =  '" +  error + "'");
        sql.append(" where docu_codigo = " + trans );


        jdbcTemplate.update(sql.toString(), queryParams);

    }

    @Override
    public void guardarSeguimiento(String trans, String proceso, String[] cdr, String error) throws SQLException {

        Map<String, Object> queryParams = new HashMap<>();
        StringBuilder sql = new StringBuilder();
        sql.append(" insert into seguimiento ");
        sql.append(" ( estado_seguimiento, docu_codigo, cdr_code, cdr_nota, cdr_observacion ) ");
        sql.append(" values ( ");
        sql.append(" '" + proceso + "',");
        sql.append(" " + trans + ",");
        sql.append(" '" + cdr[0] + "',");
        sql.append(" '" + cdr[1] + "',");
        sql.append(" '" + error+ "')");


        jdbcTemplate.update(sql.toString(), queryParams);

    }

    @Override
    public DocumentoBean cargarDocElectronico(String pdocu_codigo) {

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("docuCodigo", pdocu_codigo);

        List<DocumentoBean> searchResults = jdbcTemplate.query(SEARCH_DOCUMENTO_ELECTRONICO_BY_CODIGO, queryParams,
                new BeanPropertyRowMapper<>(DocumentoBean.class));


        if (searchResults.size() > 0) {
            return searchResults.get(0);
        }

        return null;
    }

    @Override
    public List<DocumentoBean> cargarDetDocElectronico(String pdocu_codigo) throws SQLException {

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("docuCodigo", pdocu_codigo);

        List<DocumentoBean> searchResults = jdbcTemplate.query(SEARCH_DETALLE_DOCUMENTO_ELECTRONICO_BY_CODIGO, queryParams,
                new BeanPropertyRowMapper<>(DocumentoBean.class));



        return searchResults;
    }

    @Override
    public List<DocumentoBean> cargarDetDocElectronicoAnticipo(String pdocu_codigo) throws SQLException {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("docuCodigo", pdocu_codigo);

        List<DocumentoBean> searchResults = jdbcTemplate.query(SEARCH_DETALLE_DOCUMENTO_ELECTRONICO_ANTICIPO_BY_CODIGO, queryParams,
                new BeanPropertyRowMapper<>(DocumentoBean.class));



        return searchResults;
    }

    @Override
    public List<Leyenda> cargarDetDocElectronicoLeyenda(String pdocu_codigo) throws SQLException {


        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("docuCodigo", pdocu_codigo);

        List<Leyenda> searchResults = jdbcTemplate.query(SEARCH_DETALLE_DOCUMENTO_ELECTRONICO_LEYENDA_BY_CODIGO, queryParams,
                new BeanPropertyRowMapper<>(Leyenda.class));



        return searchResults;
    }
}
