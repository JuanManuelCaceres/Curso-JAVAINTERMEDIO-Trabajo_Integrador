package org.serviciotecnico.Daos;


import org.serviciotecnico.entities.Cliente;
import org.serviciotecnico.entities.Reporte;

import java.util.List;

public interface ReporteDAO {
  //Create
  public void save(Reporte reporte);
  //Read
  public Reporte getByID(int id);
  //Update
  public void update(Reporte reporte);
  //Delete
  public void delete(Reporte reporte);
  //Get All
  public List<Reporte> getAll();

  List<Reporte> getReportesByCliente(Cliente cliente);

  List<Reporte> getReportesFinalizadosByCliente(Cliente cliente);
}
