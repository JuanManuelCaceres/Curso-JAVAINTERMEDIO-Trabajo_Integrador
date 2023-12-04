package org.serviciotecnico.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="Incidentes")
public class Incidente {
  @Id
  @Column(name="id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name="nombre")
  private String nombre;

  @Column(name="descripcion")
  private String descripcion;

  @Column(name="tiempo_maximo")
  private int tiempoMaximo;

  @ManyToOne
  @JoinColumn(name="id_soporte", referencedColumnName="id")
  private Soporte soporte;

  @ManyToMany(mappedBy="incidentes")
  private List<Especialidad> especialidades;

  public Incidente(){
    super();
  }

  public Incidente(int id, String nombre, String descripcion, int tiempoMaximo, Soporte soporte, List<Especialidad> especialidades) {
    this.id = id;
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.tiempoMaximo = tiempoMaximo;
    this.soporte = soporte;
    this.especialidades = especialidades;
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

  public int getTiempoMaximo() {
    return tiempoMaximo;
  }

  public void setTiempoMaximo(int tiempoMaximo) {
    this.tiempoMaximo = tiempoMaximo;
  }

  public Soporte getSoporte() {
    return soporte;
  }

  public void setSoporte(Soporte soporte) {
    this.soporte = soporte;
  }

  public List<Especialidad> getEspecialidades() {
    return especialidades;
  }

  public void setEspecialidades(List<Especialidad> especialidades) {
    this.especialidades = especialidades;
  }


}
