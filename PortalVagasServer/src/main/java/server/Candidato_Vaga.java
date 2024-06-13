package server;

import jakarta.persistence.*;

@Entity
@Table(name = "candidato_vaga", schema = "recrutamento")
public class Candidato_Vaga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_candidato_vaga", nullable = false)
    private Integer id;

    @Column(name = "visualizou", nullable = false)
    private Boolean visualizou = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_candidato", nullable = false)
    private Candidato idCandidato;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_vaga", nullable = false)
    private Vaga idVaga;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getVisualizou() {
        return visualizou;
    }

    public void setVisualizou(Boolean visualizou) {
        this.visualizou = visualizou;
    }

    public Candidato getIdCandidato() {
        return idCandidato;
    }

    public void setIdCandidato(Candidato idCandidato) {
        this.idCandidato = idCandidato;
    }

    public Vaga getIdVaga() {
        return idVaga;
    }

    public void setIdVaga(Vaga idVaga) {
        this.idVaga = idVaga;
    }

}