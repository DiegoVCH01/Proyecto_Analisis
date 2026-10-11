package com.gestiontalento.gestion_talento_ti.model.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "evaluacion_preseleccion",
    uniqueConstraints = {
      @UniqueConstraint(name = "uq_evaluacion_postulacion", columnNames = "id_postulacion")
    })
public class EvaluacionPreseleccion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_evaluacion")
  private Integer idEvaluacion;

  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_postulacion", nullable = false, unique = true)
  private Postulacion postulacion;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_reclutador", nullable = false)
  private Usuario reclutador;

  @Column(name = "fecha_evaluacion", nullable = false)
  private LocalDateTime fechaEvaluacion;

  @Column(name = "puntaje_total", precision = 5, scale = 2)
  private BigDecimal puntajeTotal;

  @Column(name = "resultado", nullable = false, length = 20)
  private String resultado;

  @Column(name = "observaciones", columnDefinition = "TEXT")
  private String observaciones;

  public EvaluacionPreseleccion() {}

  @PrePersist
  public void prePersist() {
    if (fechaEvaluacion == null) {
      fechaEvaluacion = LocalDateTime.now();
    }

    if (resultado == null) {
      resultado = "PENDIENTE";
    }
  }

  public Integer getIdEvaluacion() {
    return idEvaluacion;
  }

  public void setIdEvaluacion(Integer idEvaluacion) {
    this.idEvaluacion = idEvaluacion;
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

  public LocalDateTime getFechaEvaluacion() {
    return fechaEvaluacion;
  }

  public void setFechaEvaluacion(LocalDateTime fechaEvaluacion) {
    this.fechaEvaluacion = fechaEvaluacion;
  }

  public BigDecimal getPuntajeTotal() {
    return puntajeTotal;
  }

  public void setPuntajeTotal(BigDecimal puntajeTotal) {
    this.puntajeTotal = puntajeTotal;
  }

  public String getResultado() {
    return resultado;
  }

  public void setResultado(String resultado) {
    this.resultado = resultado;
  }

  public String getObservaciones() {
    return observaciones;
  }

  public void setObservaciones(String observaciones) {
    this.observaciones = observaciones;
  }
}
