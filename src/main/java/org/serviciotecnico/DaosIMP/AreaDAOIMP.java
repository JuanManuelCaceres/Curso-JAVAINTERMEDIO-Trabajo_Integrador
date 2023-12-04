package org.serviciotecnico.DaosIMP;

import org.serviciotecnico.Daos.AreaDAO;
import org.serviciotecnico.entities.Area;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class AreaDAOIMP implements AreaDAO {

  private final EntityManager entityManager;

  public AreaDAOIMP(EntityManager entityManager) {
    this.entityManager = entityManager;
  }
  @Override
  public void save(Area area) {
    try {
      entityManager.persist(area);
    } catch (Exception e){
      e.printStackTrace();
    }
  }

  @Override
  public Area getByID(int id) {
    return entityManager.find(Area.class, id);
  }

  @Override
  public void update(Area area) {
    entityManager.merge(area);
  }

  @Override
  public void delete(Area area) {
    entityManager.remove(area);
  }

  @Override
  public List<Area> getAll() {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery cq = cb.createQuery(Area.class);
    Root root = cq.from(Area.class);
    cq.select(root);
    TypedQuery<Area> tq = entityManager.createQuery(cq);
    return tq.getResultList();

  }

  @Override
  public List<Area> getByNombre(String nombreArea) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery cq = cb.createQuery(Area.class);
    Root root = cq.from(Area.class);
    cq.select(root).where(cb.equal(root.get("nombre"),nombreArea));
    TypedQuery<Area> tq = entityManager.createQuery(cq);

    return tq.getResultList();
  }

}



