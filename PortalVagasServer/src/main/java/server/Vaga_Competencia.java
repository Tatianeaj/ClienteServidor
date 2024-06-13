package server;

import jakarta.persistence.*;

@Entity
@Table(name = "vaga_competencia", schema = "recrutamento")
public class Vaga_Competencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vaga_competencia", nullable = false)
    private Integer id;

    @Column(name = "tempo", nullable = false)
    private Integer tempo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_competencia", nullable = false)
    private Competencia idCompetencia;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_vaga", nullable = false)
    private Vaga id_vaga;

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

    public Competencia getIdCompetencia() {
        return idCompetencia;
    }

    public void setIdCompetencia(Competencia idCompetencia) {
        this.idCompetencia = idCompetencia;
    }

    public Vaga getIdVaga() {
        return id_vaga;
    }

    public void setIdVaga(Vaga idVaga) {
        this.id_vaga = idVaga;
    }

}