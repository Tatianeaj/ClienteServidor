package server;

import jakarta.persistence.*;


@Entity
@Table(name = "Candidato_Competencia", schema = "recrutamento")

public class Candidato_Competencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_candidato_competencia", nullable = false)
    private Integer id;

    @Column(name = "tempo", nullable = false)
    private Integer tempo;

    @ManyToOne
    @JoinColumn(name = "id_candidato", nullable = false)
    private Candidato id_candidato;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_competencia", nullable = false)
    private Competencia id_competencia;

    //@OneToMany(mappedBy = "id_candidato")
    //private List<CandidatoCompetencia> competencias;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTempo() {
        return tempo;
    }

    public void setTempo(Integer tempo) {
        this.tempo = tempo;
    }

    public Candidato getIdCandidato() {
        return id_candidato;
    }

    public void setIdCandidato(Candidato idCandidato) {
        this.id_candidato = idCandidato;
    }

    public Competencia getIdCompetencia() {
        return id_competencia;
    }

    public void setIdCompetencia(Competencia idCompetencia) {
        this.id_competencia = idCompetencia;
    }

}