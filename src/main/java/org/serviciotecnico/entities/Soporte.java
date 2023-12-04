package org.serviciotecnico.entities;

import javax.persistence.*;

@Entity
@Table(name="Soporte")
public class Soporte{
  @Id
  @Column(name="id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name="nombre")
  private String nombre;

  @Column(name="tipo")
  private String tipo;


  public Soporte() {

  }

  public Soporte(int id, String nombre, String tipo) {
    this.id = id;
    this.nombre = nombre;
    this.tipo = tipo;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }
}
