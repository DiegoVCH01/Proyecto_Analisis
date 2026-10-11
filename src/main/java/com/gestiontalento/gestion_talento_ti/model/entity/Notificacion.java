package com.gestiontalento.gestion_talento_ti.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificacion")
public class Notificacion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_notificacion")
  private Integer idNotificacion;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id_usuario", nullable = false)
  private Usuario usuario;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_postulacion")
  private Postulacion postulacion;

  @Column(name = "tipo", nullable = false, length = 30)
  private String tipo;

  @Column(name = "asunto", nullable = false, length = 150)
  private String asunto;

  @Column(name = "mensaje", nullable = false, columnDefinition = "TEXT")
  private String mensaje;

  @Column(name = "canal", nullable = false, length = 20)
  private String canal;

  @Column(name = "estado_envio", nullable = false, length = 20)
  private String estadoEnvio;

  @Column(name = "fecha_envio", nullable = false)
  private LocalDateTime fechaEnvio;

  public Notificacion() {}

  @PrePersist
  public void prePersist() {
    if (canal == null) {
      canal = "SISTEMA";
    }

    if (estadoEnvio == null) {
      estadoEnvio = "PENDIENTE";
    }

    if (fechaEnvio == null) {
      fechaEnvio = LocalDateTime.now();
    }
  }

  public Integer getIdNotificacion() {
    return idNotificacion;
  }

  public void setIdNotificacion(Integer idNotificacion) {
    this.idNotificacion = idNotificacion;
  }

  public Usuario getUsuario() {
    return usuario;
  }

  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }

  public Postulacion getPostulacion() {
    return postulacion;
  }

  public void setPostulacion(Postulacion postulacion) {
    this.postulacion = postulacion;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public String getAsunto() {
    return asunto;
  }

  public void setAsunto(String asunto) {
    this.asunto = asunto;
  }

  public String getMensaje() {
    return mensaje;
  }

  public void setMensaje(String mensaje) {
    this.mensaje = mensaje;
  }

  public String getCanal() {
    return canal;
  }

  public void setCanal(String canal) {
    this.canal = canal;
  }

  public String getEstadoEnvio() {
    return estadoEnvio;
  }

  public void setEstadoEnvio(String estadoEnvio) {
    this.estadoEnvio = estadoEnvio;
  }

  public LocalDateTime getFechaEnvio() {
    return fechaEnvio;
  }

  public void setFechaEnvio(LocalDateTime fechaEnvio) {
    this.fechaEnvio = fechaEnvio;
  }
}
