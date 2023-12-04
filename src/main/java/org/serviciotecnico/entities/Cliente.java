package org.serviciotecnico.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Clientes")
public class Cliente {
  @Id
  @Column(name="id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name="razon_social")
  private String razonSocial;

  @Column(name="CUIT")
  private int cuit;

  @ManyToMany
  @JoinTable(name = "clientes_x_servicios",
          joinColumns = @JoinColumn(name = "Clientes_id"),
          inverseJoinColumns = @JoinColumn(name = "Servicios_id"))
  private List<Servicio> servicios;

  public Cliente(){}
  public Cliente(String razonSocial, int cuit) {
    this.razonSocial = razonSocial;
    this.cuit = cuit;
    this.servicios = new ArrayList<>();
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getRazonSocial() {
    return razonSocial;
  }

  public void setRazonSocial(String razonSocial) {
    this.razonSocial = razonSocial;
  }

  public int getCuit() {
    return cuit;
  }

  public void setCuit(int cuit) {
    this.cuit = cuit;
  }

  public List<Servicio> getServicios() {
    return servicios;
  }

  public void setServicios(List<Servicio> servicios) {
    this.servicios = servicios;
  }

  public void addServicio(Servicio servicio) {
      this.servicios.add(servicio);
  }
}