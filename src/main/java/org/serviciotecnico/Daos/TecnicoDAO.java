package org.serviciotecnico.Daos;

import org.serviciotecnico.entities.Incidente;
import org.serviciotecnico.entities.Tecnico;

import java.util.List;

public interface TecnicoDAO {
  //Create
  public void save(Tecnico tecnico);
  //Read
  public Tecnico getByID(int id);
  //Update
  public void update(Tecnico tecnico);
  //Delete
  public void delete(Tecnico tecnico);
  //Get All
  public List<Tecnico> getAll();

  List<Tecnico> getByNombreApellido(String nombreTecnicoBuscado, String apellidoTecnicoBuscado);

  List<Tecnico> getByCuil(int cuil);

  List<Tecnico> getByIncidentes(Incidente incidente);
}
