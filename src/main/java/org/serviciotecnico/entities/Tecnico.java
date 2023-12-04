package org.serviciotecnico.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="tecnicos")
public class Tecnico{
  @Id
  @Column(name="id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name="nombre")
  private String nombre;

  @Column(name="apellido")
  private String apellido;

  @Column(name="CUIL_CUIT")
  private int cuil_cuit;

  @ManyToOne
  @JoinColumn(name="id_empleado_alta", referencedColumnName="id")
  private Empleado empleado_alta;

  @ManyToOne
  @JoinColumn(name="id_empleado_modificacion", referencedColumnName="id")
  private Empleado empleado_modificacion;

  @ManyToMany
  @JoinTable(
          name = "tecnicos_x_especialidades",
          joinColumns = @JoinColumn(name = "id_tecnico"),
          inverseJoinColumns = @JoinColumn(name = "id_especialidad")
  )
  private List<Especialidad> especialidades=new ArrayList<>();

  @OneToMany(mappedBy = "tecnico")
  private List<IncidentesReporte> incidentesReportes;

    public Tecnico(String nombre, String apellido, int cuit){
    super();
  }
  public Tecnico( String nombre, String apellido, int cuil, Empleado empleado_alta, Empleado empleado_modificacion) {
    super();
    this.nombre = nombre;
    this.apellido = apellido;
    this.cuil_cuit = cuil;
    this.empleado_alta= empleado_alta;
    this.empleado_modificacion = empleado_modificacion;
  }

  public Tecnico() {
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

  public String getApellido() {
    return apellido;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public int getCuil() {
    return cuil_cuit;
  }

  public void setCuil(int cuil) {
    this.cuil_cuit = cuil;
  }

  public Empleado getEmpleado_alta() {
    return empleado_alta;
  }

  public void setEmpleado_alta(Empleado empleado_alta) {
    this.empleado_alta = empleado_alta;
  }

  public Empleado getEmpleado_modificacion() {
    return empleado_modificacion;
  }

  public void setEmpleado_modificacion(Empleado empleado_modificacion) {
    this.empleado_modificacion = empleado_modificacion;
  }

  public List<Especialidad> getEspecialidades() {
    return especialidades;
  }

  public void setEspecialidades(List<Especialidad> especialidades) {
    this.especialidades = especialidades;
  }

  public void addEspecialidad(Especialidad especialidad){
    this.especialidades.add(especialidad);
  }

  public int getCuil_cuit() {
    return cuil_cuit;
  }

  public void setCuil_cuit(int cuil_cuit) {
    this.cuil_cuit = cuil_cuit;
  }

  public List<IncidentesReporte> getIncidentesReportes() {
    return incidentesReportes;
  }

  public void setIncidentesReportes(List<IncidentesReporte> incidentesReportes) {
    this.incidentesReportes = incidentesReportes;
  }

  public void addIncidenteReporte(IncidentesReporte incidenteReporte) {
  }
}
