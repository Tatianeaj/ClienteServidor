package server;

import jakarta.persistence.*;

@Entity
@Table(name = "Competencia")


public class Competencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_competencia", nullable=false)
    private int id;
    @Column(name = "competencia")
    private String competencia;

    // getters e setters

    public Competencia(String competencia) {
        this.competencia = competencia;
    }

    public Competencia() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompetencia() {
        return competencia;
    }

    public void setCompetencia(String competencia) {
        this.competencia = competencia;
    }

}
