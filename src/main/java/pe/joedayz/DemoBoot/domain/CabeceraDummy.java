package pe.joedayz.DemoBoot.domain;


import javax.persistence.*;

@Entity
public class CabeceraDummy {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "docu_codigo")
    private Long docuCodigo;

    @Column(name = "empr_nroruc")
    private String emprNroruc;

    public Long getDocuCodigo() {
        return docuCodigo;
    }

    public void setDocuCodigo(Long docuCodigo) {
        this.docuCodigo = docuCodigo;
    }

    public String getEmprNroruc() {
        return emprNroruc;
    }

    public void setEmprNroruc(String emprNroruc) {
        this.emprNroruc = emprNroruc;
    }
}
