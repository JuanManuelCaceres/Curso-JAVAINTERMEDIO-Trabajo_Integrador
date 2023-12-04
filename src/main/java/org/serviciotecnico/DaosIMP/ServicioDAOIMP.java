package org.serviciotecnico.DaosIMP;

import org.serviciotecnico.Daos.ServicioDAO;
import org.serviciotecnico.entities.Servicio;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class ServicioDAOIMP implements ServicioDAO {

  private final EntityManager entityManager;

  public ServicioDAOIMP(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public void save(Servicio servicio) {
    try {
      entityManager.persist(servicio);
    } catch (Exception e){
      e.printStackTrace();
    }
  }

  @Override
  public Servicio getByID(int id) {
    return entityManager.find(Servicio.class,id);
  }

  @Override
  public void update(Servicio servicio) {
    entityManager.merge(servicio);
  }

  @Override
  public void delete(Servicio servicio) {
    entityManager.remove(servicio);
  }

  @Override
  public List<Servicio> getAll() {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery cq = cb.createQuery(Servicio.class);
    Root root = cq.from(Servicio.class);
    cq.select(root);
    TypedQuery<Servicio> tq = entityManager.createQuery(cq);
    return tq.getResultList();
  }
}
