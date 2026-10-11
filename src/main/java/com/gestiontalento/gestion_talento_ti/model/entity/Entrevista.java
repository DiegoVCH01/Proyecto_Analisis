package com.gestiontalento.gestion_talento_ti.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "entrevista")
public class Entrevista {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_entrevista")
  private Integer idEntrevista;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_postulacion", nullable = false)
  private Postulacion postulacion;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_reclutador", nullable = false)
  private Usuario reclutador;

  @Column(name = "tipo", nullable = false, length = 30)
  private String tipo;

  @Column(name = "fecha_hora", nullable = false)
  private LocalDateTime fechaHora;

  @Column(name = "modalidad", nullable = false, length = 20)
  private String modalidad;

  @Column(name = "enlace_reunion", length = 500)
  private String enlaceReunion;

  @Column(name = "estado", nullable = false, length = 20)
  private String estado;

  @Column(name = "observaciones", columnDefinition = "TEXT")
  private String observaciones;

  public Entrevista() {}

  @PrePersist
  public void prePersist() {
    if (tipo == null) {
      tipo = "GENERAL";
    }

    if (estado == null) {
      estado = "PROGRAMADA";
    }
  }

  public Integer getIdEntrevista() {
    return idEntrevista;
  }

  public void setIdEntrevista(Integer idEntrevista) {
    this.idEntrevista = idEntrevista;
  }

  public Postulacion getPostulacion() {
    return postulacion;
  }

  public void setPostulacion(Postulacion postulacion) {
    this.postulacion = postulacion;
  }

  public Usuario getReclutador() {
    return reclutador;
  }

  public void setReclutador(Usuario reclutador) {
    this.reclutador = reclutador;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public LocalDateTime getFechaHora() {
    return fechaHora;
  }

  public void setFechaHora(LocalDateTime fechaHora) {
    this.fechaHora = fechaHora;
  }

  public String getModalidad() {
    return modalidad;
  }

  public void setModalidad(String modalidad) {
    this.modalidad = modalidad;
  }

  public String getEnlaceReunion() {
    return enlaceReunion;
  }

  public void setEnlaceReunion(String enlaceReunion) {
    this.enlaceReunion = enlaceReunion;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public String getObservaciones() {
    return observaciones;
  }

  public void setObservaciones(String observaciones) {
    this.observaciones = observaciones;
  }
}
