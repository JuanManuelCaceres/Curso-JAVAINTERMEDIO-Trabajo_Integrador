package org.serviciotecnico.DaosIMP;

import org.serviciotecnico.Daos.EspecialidadDAO;
import org.serviciotecnico.entities.Especialidad;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class EspecialidadDAOIMP implements EspecialidadDAO {

  private final EntityManager entityManager;

  public EspecialidadDAOIMP(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public void save(Especialidad especialidad) {
    try {
      entityManager.persist(especialidad);
    } catch (Exception e){
      e.printStackTrace();
    }
  }

  @Override
  public Especialidad getByID(int id) {
    return entityManager.find(Especialidad.class,id);
  }

  @Override
  public void update(Especialidad especialidad) {
    entityManager.merge(especialidad);
  }

  @Override
  public void delete(Especialidad especialidad) {
    entityManager.remove(especialidad);
  }

  @Override
  public List<Especialidad> getAll() {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery cq = cb.createQuery(Especialidad.class);
    Root root = cq.from(Especialidad.class);
    cq.select(root);
    TypedQuery<Especialidad> tq = entityManager.createQuery(cq);
    return tq.getResultList();
  }


}
