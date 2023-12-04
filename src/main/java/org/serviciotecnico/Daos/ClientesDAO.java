package org.serviciotecnico.Daos;

import org.serviciotecnico.entities.Cliente;

import java.util.List;

public interface ClientesDAO {
  //Create
  public void save(Cliente cliente);
  //Read
  public Cliente getByID(int id);
  //Update
  public void update(Cliente cliente);
  //Delete
  public void delete(Cliente cliente);
  //Get All
  public List<Cliente> getAll();

  Cliente getByCUIT(int cuitBaja);
}
