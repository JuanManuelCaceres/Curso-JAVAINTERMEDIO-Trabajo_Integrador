package org.serviciotecnico.Daos;

import org.serviciotecnico.entities.Incidente;
import org.serviciotecnico.entities.Soporte;

import java.util.List;

public interface IncidenteDAO {
  //Create
  public void save(Incidente incidente);
  //Read
  public Incidente getByID(int id);
  //Update
  public void update(Incidente incidente);
  //Delete
  public void delete(Incidente incidente);
  //Get All
  public List<Incidente> getAll();

  List<Incidente> getBySoporte(Soporte soporte);
}
