package com.gestiontalento.gestion_talento_ti.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "postulacion",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "uq_postulacion_candidato_vacante",
          columnNames = {"id_candidato", "id_vacante"})
    })
public class Postulacion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_postulacion")
  private Integer idPostulacion;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_candidato", nullable = false)
  private Usuario candidato;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_vacante", nullable = false)
  private Vacante vacante;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_curriculum", nullable = false)
  private CurriculumVitae curriculum;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_estado", nullable = false)
  private EstadoPostulacion estado;

  @Column(name = "fecha_postulacion", nullable = false)
  private LocalDateTime fechaPostulacion;

  @Column(name = "fecha_actualizacion", nullable = false)
  private LocalDateTime fechaActualizacion;

  public Postulacion() {}

  @PrePersist
  public void prePersist() {
    LocalDateTime ahora = LocalDateTime.now();

    if (fechaPostulacion == null) {
      fechaPostulacion = ahora;
    }

    if (fechaActualizacion == null) {
      fechaActualizacion = ahora;
    }
  }

  @PreUpdate
  public void preUpdate() {
    fechaActualizacion = LocalDateTime.now();
  }

  public Integer getIdPostulacion() {
    return idPostulacion;
  }

  public void setIdPostulacion(Integer idPostulacion) {
    this.idPostulacion = idPostulacion;
  }

  public Usuario getCandidato() {
    return candidato;
  }

  public void setCandidato(Usuario candidato) {
    this.candidato = candidato;
  }

  public Vacante getVacante() {
    return vacante;
  }

  public void setVacante(Vacante vacante) {
    this.vacante = vacante;
  }

  public CurriculumVitae getCurriculum() {
    return curriculum;
  }

  public void setCurriculum(CurriculumVitae curriculum) {
    this.curriculum = curriculum;
  }

  public EstadoPostulacion getEstado() {
    return estado;
  }

  public void setEstado(EstadoPostulacion estado) {
    this.estado = estado;
  }

  public LocalDateTime getFechaPostulacion() {
    return fechaPostulacion;
  }

  public void setFechaPostulacion(LocalDateTime fechaPostulacion) {
    this.fechaPostulacion = fechaPostulacion;
  }

  public LocalDateTime getFechaActualizacion() {
    return fechaActualizacion;
  }

  public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
    this.fechaActualizacion = fechaActualizacion;
  }
}
