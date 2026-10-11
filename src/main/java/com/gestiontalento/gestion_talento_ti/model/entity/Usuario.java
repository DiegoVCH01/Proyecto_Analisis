package com.gestiontalento.gestion_talento_ti.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuario")
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_usuario")
  private Integer idUsuario;

  @Column(name = "nombres", nullable = false, length = 80)
  private String nombres;

  @Column(name = "apellidos", nullable = false, length = 80)
  private String apellidos;

  @Column(name = "correo", nullable = false, unique = true, length = 150)
  private String correo;

  @Column(name = "password_hash", nullable = false, length = 255)
  private String passwordHash;

  @Column(name = "rol", nullable = false, length = 20)
  private String rol;

  @Column(name = "estado", nullable = false, length = 20)
  private String estado;

  @Column(name = "fecha_registro", nullable = false)
  private LocalDateTime fechaRegistro;

  public Usuario() {}

  @PrePersist
  public void prePersist() {
    if (estado == null) {
      estado = "ACTIVO";
    }

    if (fechaRegistro == null) {
      fechaRegistro = LocalDateTime.now();
    }
  }

  public Integer getIdUsuario() {
    return idUsuario;
  }

  public void setIdUsuario(Integer idUsuario) {
    this.idUsuario = idUsuario;
  }

  public String getNombres() {
    return nombres;
  }

  public void setNombres(String nombres) {
    this.nombres = nombres;
  }

  public String getApellidos() {
    return apellidos;
  }

  public void setApellidos(String apellidos) {
    this.apellidos = apellidos;
  }

  public String getCorreo() {
    return correo;
  }

  public void setCorreo(String correo) {
    this.correo = correo;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public String getRol() {
    return rol;
  }

  public void setRol(String rol) {
    this.rol = rol;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public LocalDateTime getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(LocalDateTime fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }
}
