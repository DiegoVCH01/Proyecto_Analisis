package com.gestiontalento.gestion_talento_ti.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "estado_postulacion")
public class EstadoPostulacion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_estado")
  private Integer idEstado;

  @Column(name = "nombre", nullable = false, unique = true, length = 50)
  private String nombre;

  @Column(name = "descripcion", length = 250)
  private String descripcion;

  public EstadoPostulacion() {}

  public Integer getIdEstado() {
    return idEstado;
  }

  public void setIdEstado(Integer idEstado) {
    this.idEstado = idEstado;
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
}
