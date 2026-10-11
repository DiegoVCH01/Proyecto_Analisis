package com.gestiontalento.gestion_talento_ti.model.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "criterio_vacante")
public class CriterioVacante {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_criterio")
  private Integer idCriterio;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_vacante", nullable = false)
  private Vacante vacante;

  @Column(name = "nombre", nullable = false, length = 100)
  private String nombre;

  @Column(name = "descripcion", length = 300)
  private String descripcion;

  @Column(name = "obligatorio", nullable = false)
  private Boolean obligatorio;

  @Column(name = "peso", precision = 5, scale = 2)
  private BigDecimal peso;

  public CriterioVacante() {}

  @PrePersist
  public void prePersist() {
    if (obligatorio == null) {
      obligatorio = true;
    }
  }

  public Integer getIdCriterio() {
    return idCriterio;
  }

  public void setIdCriterio(Integer idCriterio) {
    this.idCriterio = idCriterio;
  }

  public Vacante getVacante() {
    return vacante;
  }

  public void setVacante(Vacante vacante) {
    this.vacante = vacante;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public Boolean getObligatorio() {
    return obligatorio;
  }

  public void setObligatorio(Boolean obligatorio) {
    this.obligatorio = obligatorio;
  }

  public BigDecimal getPeso() {
    return peso;
  }

  public void setPeso(BigDecimal peso) {
    this.peso = peso;
  }
}
