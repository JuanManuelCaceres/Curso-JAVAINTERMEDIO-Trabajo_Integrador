package org.serviciotecnico.Daos;

import org.serviciotecnico.entities.IncidentesReporte;
import org.serviciotecnico.entities.Reporte;

import java.util.List;

public interface IncidentesReporteDAO {
  //Create
  public void save(IncidentesReporte incidentesReporte);
  //Read
  public IncidentesReporte getByID(int id);
  //Update
  public void update(IncidentesReporte incidentesReporte);
  //Delete
  public void delete(IncidentesReporte incidentesReporte);
  //Get All
  public List<IncidentesReporte> getAll();

  List<IncidentesReporte> getAllByReporte(Reporte reporte);
}
