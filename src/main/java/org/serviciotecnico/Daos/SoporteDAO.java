package org.serviciotecnico.Daos;

import org.serviciotecnico.entities.Soporte;

import java.util.List;

public interface SoporteDAO {
  //Create
  public void save(Soporte soporte);
  //Read
  public Soporte getByID(int id);
  //Update
  public void update(Soporte soporte);
  //Delete
  public void delete(Soporte soporte);
  //Get All
  public List<Soporte> getAll();
  //GetBy Nombre
  public Soporte getByNombre(String nombre);
}
