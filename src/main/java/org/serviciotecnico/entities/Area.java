package org.serviciotecnico.entities;


import javax.persistence.*;
import java.util.List;

@Entity
@Table (name="Areas")
public class Area{
  @Id
  @Column(name="id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name="nombre")
  private String nombre;

  @Column(name="descripcion")
  private String descripcion;

  @OneToMany
  private List<Empleado> empleados;

  public Area(){}

  public Area(int id, String nombre, String descripcion, List<Empleado> empleados) {
    this.id = id;
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.empleados = empleados;
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

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public List<Empleado> getEmpleados() {
    return empleados;
  }

  public void setEmpleados(List<Empleado> empleados) {
    this.empleados = empleados;
  }
}