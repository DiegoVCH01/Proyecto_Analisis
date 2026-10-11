package com.gestiontalento.gestion_talento_ti.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "perfil_candidato")
public class PerfilCandidato {

  @Id
  @Column(name = "id_usuario")
  private Integer idUsuario;

  @OneToOne(fetch = FetchType.LAZY)
  @MapsId
  @JoinColumn(name = "id_usuario")
  private Usuario usuario;

  @Column(name = "telefono", length = 20)
  private String telefono;

  @Column(name = "linkedin_url", length = 255)
  private String linkedinUrl;

  @Column(name = "github_url", length = 255)
  private String githubUrl;

  @Column(name = "resumen_profesional", columnDefinition = "TEXT")
  private String resumenProfesional;

  @Column(name = "anios_experiencia", nullable = false)
  private Integer aniosExperiencia;

  public PerfilCandidato() {}

  @PrePersist
  public void prePersist() {
    if (aniosExperiencia == null) {
      aniosExperiencia = 0;
    }
  }

  public Integer getIdUsuario() {
    return idUsuario;
  }

  public Usuario getUsuario() {
    return usuario;
  }

  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public String getLinkedinUrl() {
    return linkedinUrl;
  }

  public void setLinkedinUrl(String linkedinUrl) {
    this.linkedinUrl = linkedinUrl;
  }

  public String getGithubUrl() {
    return githubUrl;
  }

  public void setGithubUrl(String githubUrl) {
    this.githubUrl = githubUrl;
  }

  public String getResumenProfesional() {
    return resumenProfesional;
  }

  public void setResumenProfesional(String resumenProfesional) {
    this.resumenProfesional = resumenProfesional;
  }

  public Integer getAniosExperiencia() {
    return aniosExperiencia;
  }

  public void setAniosExperiencia(Integer aniosExperiencia) {
    this.aniosExperiencia = aniosExperiencia;
  }
}
