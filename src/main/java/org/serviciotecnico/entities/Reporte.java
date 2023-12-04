package org.serviciotecnico.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Reportes")
public class Reporte {
  @Id
  @Column(name="id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name="descripcion")
  private String descripcion;

  @Column(name="fecha_alta")
  private LocalDateTime fechaAlta;

  @Column(name="fecha_resolucion_estimada")
  private LocalDateTime fecha_resolucion_estimada;

  @Column(name="fecha_resolucion")
  private LocalDateTime fecha_resolucion;

  @Column(name="colchon_horas")
  private Integer colchonHoras;

  @Column(name="resuelto")
  private int resuelto;

  @Column(name="observaciones")
  private String observaciones;

  @ManyToOne
  @JoinColumn(name="Clientes_id",referencedColumnName = "id")
  private Cliente cliente;

  @OneToMany(mappedBy = "reporte")
  private List<IncidentesReporte> incidentesReporte=new ArrayList<>();


  public Reporte(){
    super();
    this.colchonHoras=0;
  }

  public Reporte( String descripcion, LocalDateTime fechaAlta, LocalDateTime fecha_resolucion_estimada, LocalDateTime fecha_resolucion, int colchonHoras, int resuelto, String observaciones, Cliente cliente) {
    this.descripcion = descripcion;
    this.fechaAlta = fechaAlta;
    this.fecha_resolucion_estimada = fecha_resolucion_estimada;
    this.fecha_resolucion = fecha_resolucion;
    this.colchonHoras = colchonHoras;
    this.resuelto = resuelto;
    this.observaciones = observaciones;
    this.cliente = cliente;
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

  public LocalDateTime getFechaAlta() {
    return fechaAlta;
  }

  public void setFechaAlta(LocalDateTime fechaAlta) {
    this.fechaAlta = fechaAlta;
  }

  public LocalDateTime getFecha_resolucion_estimada() {
    return fecha_resolucion_estimada;
  }

  public void setFecha_resolucion_estimada(LocalDateTime fecha_resolucion_estimada) {
    this.fecha_resolucion_estimada = fecha_resolucion_estimada;
  }

  public LocalDateTime getFecha_resolucion() {
    return fecha_resolucion;
  }

  public void setFecha_resolucion(LocalDateTime fecha_resolucion) {
    this.fecha_resolucion = fecha_resolucion;
  }

  public int getColchonHoras() {
    return colchonHoras;
  }

  public void setColchonHoras(int colchonHoras) {
    this.colchonHoras = colchonHoras;
  }

  public int getResuelto() {
    return resuelto;
  }

  public void setResuelto(int resuelto) {
    this.resuelto = resuelto;
  }

  public String getObservaciones() {
    return observaciones;
  }

  public void setObservaciones(String observaciones) {
    this.observaciones = observaciones;
  }

  public Cliente getCliente() {
    return cliente;
  }

  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
  }

  public void setColchonHoras(Integer colchonHoras) {
    this.colchonHoras = colchonHoras;
  }

  public List<IncidentesReporte> getIncidentesReporte() {
    return incidentesReporte;
  }

  public void setIncidentesReporte(List<IncidentesReporte> incidentesReporte) {
    this.incidentesReporte = incidentesReporte;
  }
  public void addIncidente(IncidentesReporte incidenteReporte){
    this.getIncidentesReporte().add(incidenteReporte);
  }

  public IncidentesReporte getIncidenteReporteByID(int id) {
    for (IncidentesReporte incidete : this.getIncidentesReporte()
    ) {
      if (incidete.getId() == id) {
        return incidete;
      }
    }
    return null;
  }
}