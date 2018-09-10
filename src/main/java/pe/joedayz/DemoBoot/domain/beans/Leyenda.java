package pe.joedayz.DemoBoot.domain.beans;

public class Leyenda {

    private String idLeyenda;
    private String docuCodigo;
    private String leyendaCodigo;
    private String leyendaTexto;

    public Leyenda() {
    }

    public String getIdLeyenda() {
        return idLeyenda;
    }

    public void setIdLeyenda(String idLeyenda) {
        this.idLeyenda = idLeyenda;
    }

    public String getDocuCodigo() {
        return docuCodigo;
    }

    public void setDocuCodigo(String docuCodigo) {
        this.docuCodigo = docuCodigo;
    }

    public String getLeyendaCodigo() {
        return leyendaCodigo;
    }

    public void setLeyendaCodigo(String leyendaCodigo) {
        this.leyendaCodigo = leyendaCodigo;
    }

    public String getLeyendaTexto() {
        return leyendaTexto;
    }

    public void setLeyendaTexto(String leyendaTexto) {
        this.leyendaTexto = leyendaTexto;
    }
}
