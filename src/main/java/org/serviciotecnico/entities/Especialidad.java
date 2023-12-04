package org.serviciotecnico.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="Especialidad")
public class Especialidad{
  @Id
  @Column(name="id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name="nombre")
  private String nombre;

  @Column(name="descripcion")
  private String descripcion;

  @ManyToMany(mappedBy="especialidades")
  private List<Tecnico> tecnicos;

  @ManyToMany
  @JoinTable(
          name = "incidentes_x_especialidad",
          joinColumns = @JoinColumn(name = "id_especialidad"),
          inverseJoinColumns = @JoinColumn(name = "id_incidentes")
  )
  private List<Incidente> incidentes;

  public Especialidad() {
    super();
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

  public List<Tecnico> getTecnicos() {
    return tecnicos;
  }

  public void setTecnicos(List<Tecnico> tecnicos) {
    this.tecnicos = tecnicos;
  }

  public List<Incidente> getIncidentes() {
    return incidentes;
  }

  public void setIncidentes(List<Incidente> incidentes) {
    this.incidentes = incidentes;
  }
  public void addTecnico(Tecnico tecnico){
    this.tecnicos.add(tecnico);
  }
}