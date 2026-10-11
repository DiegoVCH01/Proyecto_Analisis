package com.gestiontalento.gestion_talento_ti.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "historial_estado_postulacion")
public class HistorialEstadoPostulacion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_historial")
  private Integer idHistorial;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_postulacion", nullable = false)
  private Postulacion postulacion;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_estado", nullable = false)
  private EstadoPostulacion estado;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_usuario_cambio")
  private Usuario usuarioCambio;

  @Column(name = "fecha_cambio", nullable = false)
  private LocalDateTime fechaCambio;

  @Column(name = "observacion", length = 500)
  private String observacion;

  public HistorialEstadoPostulacion() {}

  @PrePersist
  public void prePersist() {
    if (fechaCambio == null) {
      fechaCambio = LocalDateTime.now();
    }
  }

  public Integer getIdHistorial() {
    return idHistorial;
  }

  public void setIdHistorial(Integer idHistorial) {
    this.idHistorial = idHistorial;
  }

  public Postulacion getPostulacion() {
    return postulacion;
  }

  public void setPostulacion(Postulacion postulacion) {
    this.postulacion = postulacion;
  }

  public EstadoPostulacion getEstado() {
    return estado;
  }

  public void setEstado(EstadoPostulacion estado) {
    this.estado = estado;
  }

  public Usuario getUsuarioCambio() {
    return usuarioCambio;
  }

  public void setUsuarioCambio(Usuario usuarioCambio) {
    this.usuarioCambio = usuarioCambio;
  }

  public LocalDateTime getFechaCambio() {
    return fechaCambio;
  }

  public void setFechaCambio(LocalDateTime fechaCambio) {
    this.fechaCambio = fechaCambio;
  }

  public String getObservacion() {
    return observacion;
  }

  public void setObservacion(String observacion) {
    this.observacion = observacion;
  }
}
