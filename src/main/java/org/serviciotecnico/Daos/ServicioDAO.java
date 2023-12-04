package org.serviciotecnico.Daos;

import org.serviciotecnico.entities.Servicio;

import java.util.List;

public interface ServicioDAO {
  //Create
  public void save(Servicio servicio);
  //Read
  public Servicio getByID(int id);
  //Update
  public void update(Servicio servicio);
  //Delete
  public void delete(Servicio servicio);
  //Get All
  public List<Servicio> getAll();
}
