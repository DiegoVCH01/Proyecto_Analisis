package com.gestiontalento.gestion_talento_ti.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "curriculum_vitae")
public class CurriculumVitae {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_curriculum")
  private Integer idCurriculum;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_candidato", nullable = false)
  private Usuario candidato;

  @Column(name = "nombre_archivo", nullable = false, length = 200)
  private String nombreArchivo;

  @Column(name = "archivo_url", nullable = false, length = 500)
  private String archivoUrl;

  @Column(name = "fecha_registro", nullable = false)
  private LocalDateTime fechaRegistro;

  @Column(name = "estado", nullable = false, length = 20)
  private String estado;

  public CurriculumVitae() {}

  @PrePersist
  public void prePersist() {
    if (fechaRegistro == null) {
      fechaRegistro = LocalDateTime.now();
    }

    if (estado == null) {
      estado = "ACTIVO";
    }
  }

  public Integer getIdCurriculum() {
    return idCurriculum;
  }

  public void setIdCurriculum(Integer idCurriculum) {
    this.idCurriculum = idCurriculum;
  }

  public Usuario getCandidato() {
    return candidato;
  }

  public void setCandidato(Usuario candidato) {
    this.candidato = candidato;
  }

  public String getNombreArchivo() {
    return nombreArchivo;
  }

  public void setNombreArchivo(String nombreArchivo) {
    this.nombreArchivo = nombreArchivo;
  }

  public String getArchivoUrl() {
    return archivoUrl;
  }

  public void setArchivoUrl(String archivoUrl) {
    this.archivoUrl = archivoUrl;
  }

  public LocalDateTime getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(LocalDateTime fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }
}
