package org.serviciotecnico.entities;

import javax.persistence.*;

@Entity
@Table (name="Empleados")
public class Empleado {
  @Id
  @Column(name="id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name="nombre")
  private String nombre;

  @Column(name="apellido")
  private String apellido;

  @Column(name="CUIL_CUIT")
  private long cuil_cuit;

  @ManyToOne
  @JoinColumn (name="Areas_id", referencedColumnName = "id")
  private Area area;

  public Empleado(){}
  public Empleado(String nombre, String apellido, long cuil_cuit, Area area) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.cuil_cuit = cuil_cuit;
    this.area = area;
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

  public String getApellido() {
    return apellido;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public long getCuil() {
    return cuil_cuit;
  }

  public void setCuil(int cuil) {
    this.cuil_cuit = cuil;
  }

  public Area getArea() {
    return area;
  }

  public void setArea(Area area) {
    this.area = area;
  }
}