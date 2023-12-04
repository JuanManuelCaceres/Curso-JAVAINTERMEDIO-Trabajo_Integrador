package org.serviciotecnico.entities;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="incidentes_x_reportes")
public class IncidentesReporte {
  @Id
  @Column(name="id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column
  private String descripcion;

  @Column
  private int resuelto;

  @Column(name="fecha_resolucion")
  private LocalDateTime resolucion;

  @ManyToOne
  @JoinColumn(name="id_reporte",referencedColumnName="id")
  private Reporte reporte;

  @ManyToOne
  @JoinColumn(name="id_incidente",referencedColumnName="id")
  private Incidente incidente;

  @ManyToOne
  @JoinColumn(name="id_tecnico",referencedColumnName="id")
  private Tecnico tecnico;

  public IncidentesReporte(){
    super();
  }
  public IncidentesReporte(String descIncidenteReporte, Reporte reporte, Incidente incidente){
    this.descripcion=descIncidenteReporte;
    this.reporte=reporte;
    this.incidente=incidente;
  }
  public IncidentesReporte( String descripcion, int resuelto, LocalDateTime resolucion, Reporte reporte, Incidente incidente, Tecnico tecnico) {

    this.descripcion = descripcion;
    this.resuelto = resuelto;
    this.resolucion = resolucion;
    this.reporte = reporte;
    this.incidente = incidente;
  }

  public IncidentesReporte(String descripcion, Reporte reporte, Incidente incidente, Tecnico tecnico) {
    this.descripcion = descripcion;
    this.reporte = reporte;
    this.incidente = incidente;
    this.tecnico = tecnico;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public int getResuelto() {
    return resuelto;
  }

  public void setResuelto(int resuelto) {
    this.resuelto = resuelto;
  }

  public LocalDateTime getResolucion() {
    return resolucion;
  }

  public void setResolucion(LocalDateTime resolucion) {
    this.resolucion = resolucion;
  }

  public Reporte getReporte() {
    return reporte;
  }

  public void setReporte(Reporte reporte) {
    this.reporte = reporte;
  }

  public Incidente getIncidente() {
    return incidente;
  }

  public void setIncidente(Incidente incidente) {
    this.incidente = incidente;
  }

  public Tecnico getTecnico() {
    return tecnico;
  }

  public void setTecnico(Tecnico tecnico) {
    this.tecnico = tecnico;
  }





}
