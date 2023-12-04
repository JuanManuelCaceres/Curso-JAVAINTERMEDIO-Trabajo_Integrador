package org.serviciotecnico.DaosIMP;

import org.serviciotecnico.Daos.SoporteDAO;
import org.serviciotecnico.entities.Soporte;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class SoporteDAOIMP implements SoporteDAO {
  private final EntityManager entityManager;

  public SoporteDAOIMP(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public void save(Soporte soporte) {
    try {
      entityManager.persist(soporte);
    } catch (Exception e){
      e.printStackTrace();
    }
  }

  @Override
  public Soporte getByID(int id) {
    return entityManager.find(Soporte.class, id);
  }

  @Override
  public void update(Soporte soporte) {
    entityManager.merge(soporte);
  }

  @Override
  public void delete(Soporte soporte) {
    entityManager.remove(soporte);
  }

  @Override
  public List<Soporte> getAll() {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery cq = cb.createQuery(Soporte.class);
    Root root = cq.from(Soporte.class);
    cq.select(root);
    TypedQuery<Soporte> tq = entityManager.createQuery(cq);
    return tq.getResultList();
  }

  @Override
  public Soporte getByNombre(String nombre) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery cq = cb.createQuery(Soporte.class);
    Root root = cq.from(Soporte.class);
    cq.select(root).where(cb.equal(root.get("nombre"),nombre));
    TypedQuery<Soporte> tq = entityManager.createQuery(cq);
    return tq.getResultList().get(0);
  }
}
