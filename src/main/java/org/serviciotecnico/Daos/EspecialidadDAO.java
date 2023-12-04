package org.serviciotecnico.Daos;

import org.serviciotecnico.entities.Cliente;
import org.serviciotecnico.entities.Especialidad;

import java.util.List;

public interface EspecialidadDAO {
  //Create
  public void save(Especialidad especialidad);
  //Read
  public Especialidad getByID(int id);
  //Update
  public void update(Especialidad especialidad);
  //Delete
  public void delete(Especialidad especialidad);
  //Get All
  public List<Especialidad> getAll();
}
