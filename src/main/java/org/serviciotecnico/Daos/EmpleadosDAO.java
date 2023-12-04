package org.serviciotecnico.Daos;

import org.serviciotecnico.entities.Empleado;

import java.util.List;

public interface EmpleadosDAO {
  //Create
  public void save(Empleado empleado);
  //Read
  public Empleado getByID(int id);
  //Update
  public void update(Empleado empleado);
  //Delete
  public void delete(Empleado empleado);
  //Get All
  public List<Empleado> getAll();

  //Get usuario
  public List<Empleado> getByNombreApellido(String nombre, String apellido);

  List<Empleado> getByCuil(int cuil);
}