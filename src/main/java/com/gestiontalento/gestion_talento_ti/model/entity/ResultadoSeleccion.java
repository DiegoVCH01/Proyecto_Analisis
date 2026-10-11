package com.gestiontalento.gestion_talento_ti.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "resultado_seleccion",
    uniqueConstraints = {
      @UniqueConstraint(name = "uq_resultado_postulacion", columnNames = "id_postulacion")
    })
public class ResultadoSeleccion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_resultado")
  private Integer idResultado;

  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_postulacion", nullable = false, unique = true)
  private Postulacion postulacion;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_reclutador", nullable = false)
  private Usuario reclutador;

  @Column(name = "resultado", nullable = false, length = 30)
  private String resultado;

  @Column(name = "fecha_resultado", nullable = false)
  private LocalDateTime fechaResultado;

  @Column(name = "observaciones", columnDefinition = "TEXT")
  private String observaciones;

  public ResultadoSeleccion() {}

  @PrePersist
  public void prePersist() {
    if (fechaResultado == null) {
      fechaResultado = LocalDateTime.now();
    }
  }

  public Integer getIdResultado() {
    return idResultado;
  }

  public void setIdResultado(Integer idResultado) {
    this.idResultado = idResultado;
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

  public String getResultado() {
    return resultado;
  }

  public void setResultado(String resultado) {
    this.resultado = resultado;
  }

  public LocalDateTime getFechaResultado() {
    return fechaResultado;
  }

  public void setFechaResultado(LocalDateTime fechaResultado) {
    this.fechaResultado = fechaResultado;
  }

  public String getObservaciones() {
    return observaciones;
  }

  public void setObservaciones(String observaciones) {
    this.observaciones = observaciones;
  }
}
