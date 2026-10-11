package com.gestiontalento.gestion_talento_ti.model.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "vacante")
public class Vacante {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_vacante")
  private Integer idVacante;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_reclutador", nullable = false)
  private Usuario reclutador;

  @Column(name = "titulo", nullable = false, length = 150)
  private String titulo;

  @Column(name = "descripcion", nullable = false, columnDefinition = "TEXT")
  private String descripcion;

  @Column(name = "area", length = 100)
  private String area;

  @Column(name = "ubicacion", length = 150)
  private String ubicacion;

  @Column(name = "modalidad", nullable = false, length = 20)
  private String modalidad;

  @Column(name = "estado", nullable = false, length = 20)
  private String estado;

  @Column(name = "fecha_publicacion")
  private LocalDate fechaPublicacion;

  @Column(name = "fecha_cierre")
  private LocalDate fechaCierre;

  @Column(name = "fecha_registro", nullable = false)
  private LocalDateTime fechaRegistro;

  public Vacante() {}

  @PrePersist
  public void prePersist() {
    if (estado == null) {
      estado = "BORRADOR";
    }

    if (fechaRegistro == null) {
      fechaRegistro = LocalDateTime.now();
    }
  }

  public Integer getIdVacante() {
    return idVacante;
  }

  public void setIdVacante(Integer idVacante) {
    this.idVacante = idVacante;
  }

  public Usuario getReclutador() {
    return reclutador;
  }

  public void setReclutador(Usuario reclutador) {
    this.reclutador = reclutador;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getArea() {
    return area;
  }

  public void setArea(String area) {
    this.area = area;
  }

  public String getUbicacion() {
    return ubicacion;
  }

  public void setUbicacion(String ubicacion) {
    this.ubicacion = ubicacion;
  }

  public String getModalidad() {
    return modalidad;
  }

  public void setModalidad(String modalidad) {
    this.modalidad = modalidad;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public LocalDate getFechaPublicacion() {
    return fechaPublicacion;
  }

  public void setFechaPublicacion(LocalDate fechaPublicacion) {
    this.fechaPublicacion = fechaPublicacion;
  }

  public LocalDate getFechaCierre() {
    return fechaCierre;
  }

  public void setFechaCierre(LocalDate fechaCierre) {
    this.fechaCierre = fechaCierre;
  }

  public LocalDateTime getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(LocalDateTime fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }
}
