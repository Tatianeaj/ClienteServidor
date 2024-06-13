package server;


import jakarta.persistence.*;

@Entity
@Table(name = "Vaga")


public class Vaga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vaga", nullable=false)
    private int idVaga;
    @Column(name = "nome_vaga")
    private String nomeVaga;
    @Column(name = "faixa_salarial")
    private int faixaSalarial;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "estado")
    private String estado;

    @ManyToOne
    @JoinColumn(name = "id_empresa")
    private Empresa idEmpresa;

    public Vaga(String nomeVaga, int faixaSalarial, String descricao, String estado, Empresa idEmpresa) {
        this.nomeVaga = nomeVaga;
        this.faixaSalarial = faixaSalarial;
        this.descricao = descricao;
        this.estado = estado;
        this.idEmpresa = idEmpresa;
    }

    public Vaga() {

    }

    public String getNomeVaga() {
        return nomeVaga;
    }

    public void setNomeVaga(String nomeVaga) {
        this.nomeVaga = nomeVaga;
    }

    public int getFaixaSalarial() {
        return faixaSalarial;
    }

    public void setFaixaSalarial(int faixaSalarial) {
        this.faixaSalarial = faixaSalarial;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getIdVaga() {
        return idVaga;
    }

    public void setIdVaga(int idVaga) {
        this.idVaga = idVaga;
    }

    public Empresa getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Empresa idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
