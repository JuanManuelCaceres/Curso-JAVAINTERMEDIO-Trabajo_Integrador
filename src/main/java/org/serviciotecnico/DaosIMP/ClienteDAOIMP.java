package org.serviciotecnico.DaosIMP;

import org.serviciotecnico.Daos.ClientesDAO;
import org.serviciotecnico.entities.Cliente;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class ClienteDAOIMP implements ClientesDAO {
  private final EntityManager entityManager;

  public ClienteDAOIMP(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public void save(Cliente cliente) {
    try {
      entityManager.persist(cliente);
    } catch (Exception e){
      e.printStackTrace();
    }
  }

  @Override
  public Cliente getByID(int id) {
    return entityManager.find(Cliente.class, id);
  }

  @Override
  public void update(Cliente cliente) {
    entityManager.merge(cliente);
  }

  @Override
  public void delete(Cliente cliente) {
    entityManager.remove(cliente);
  }

  @Override
  public List<Cliente> getAll() {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery cq = cb.createQuery(Cliente.class);
    Root root = cq.from(Cliente.class);
    cq.select(root);
    TypedQuery<Cliente> tq = entityManager.createQuery(cq);
    return tq.getResultList();

  }

  @Override
  public Cliente getByCUIT(int cuitBaja) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery cq = cb.createQuery(Cliente.class);
    Root root = cq.from(Cliente.class);
    cq.select(root).where(cb.equal(root.get("cuit"),cuitBaja));
    TypedQuery<Cliente> tq = entityManager.createQuery(cq);
    return tq.getResultList().get(0);

  }


}
