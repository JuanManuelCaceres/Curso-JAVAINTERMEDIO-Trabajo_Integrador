package org.serviciotecnico.Daos;

import org.serviciotecnico.entities.Area;


import java.util.List;

public interface AreaDAO {
  //Create
  public void save(Area area);
  //Read
  public Area getByID(int id);
  //Update
  public void update(Area area);
  //Delete
  public void delete(Area area);
  //Get All
  public List<Area> getAll();

  List<Area> getByNombre(String nombreNuevaArea);
}
