package com.gestiontalento.gestion_talento_ti.model.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(
    name = "detalle_evaluacion",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "uq_detalle_evaluacion_criterio",
          columnNames = {"id_evaluacion", "id_criterio"})
    })
public class DetalleEvaluacion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_detalle")
  private Integer idDetalle;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_evaluacion", nullable = false)
  private EvaluacionPreseleccion evaluacion;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_criterio", nullable = false)
  private CriterioVacante criterio;

  @Column(name = "cumple", nullable = false)
  private Boolean cumple;

  @Column(name = "puntaje", precision = 5, scale = 2)
  private BigDecimal puntaje;

  @Column(name = "observacion", length = 500)
  private String observacion;

  public DetalleEvaluacion() {}

  public Integer getIdDetalle() {
    return idDetalle;
  }

  public void setIdDetalle(Integer idDetalle) {
    this.idDetalle = idDetalle;
  }

  public EvaluacionPreseleccion getEvaluacion() {
    return evaluacion;
  }

  public void setEvaluacion(EvaluacionPreseleccion evaluacion) {
    this.evaluacion = evaluacion;
  }

  public CriterioVacante getCriterio() {
    return criterio;
  }

  public void setCriterio(CriterioVacante criterio) {
    this.criterio = criterio;
  }

  public Boolean getCumple() {
    return cumple;
  }

  public void setCumple(Boolean cumple) {
    this.cumple = cumple;
  }

  public BigDecimal getPuntaje() {
    return puntaje;
  }

  public void setPuntaje(BigDecimal puntaje) {
    this.puntaje = puntaje;
  }

  public String getObservacion() {
    return observacion;
  }

  public void setObservacion(String observacion) {
    this.observacion = observacion;
  }
}
