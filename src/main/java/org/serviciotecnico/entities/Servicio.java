package org.serviciotecnico.entities;



import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="Servicios")
public class Servicio {
  @Id
  @Column(name="id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name="nombre")
  private String nombre;

  @ManyToOne
  @JoinColumn(name="id_soporte",referencedColumnName="id")
  private Soporte soporte;

  public Servicio( String nombre, Soporte soporte) {
    this.nombre = nombre;
    this.soporte = soporte;

  }

  @ManyToMany(mappedBy = "servicios")
  private List<Cliente> clientes;

  public Servicio() {
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

  public Soporte getSoporte() {
    return soporte;
  }

  public void setSoporte(Soporte soporte) {
    this.soporte = soporte;
  }

  public void addCliente(Cliente cliente){
    this.clientes.add(cliente);
  }
}
