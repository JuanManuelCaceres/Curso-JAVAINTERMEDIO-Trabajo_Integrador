package org.serviciotecnico.DaosIMP;

import org.serviciotecnico.Daos.IncidenteDAO;
import org.serviciotecnico.entities.Incidente;
import org.serviciotecnico.entities.Soporte;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class IncidenteDAOIMP implements IncidenteDAO {
  private final EntityManager entityManager;

  public IncidenteDAOIMP(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public void save(Incidente incidente) {
    try {
      entityManager.persist(incidente);
    } catch (Exception e){
      e.printStackTrace();
    }
  }

  @Override
  public Incidente getByID(int id) {
    return entityManager.find(Incidente.class,id);
  }

  @Override
  public void update(Incidente incidente) {
    entityManager.merge(incidente);
  }

  @Override
  public void delete(Incidente incidente) {
    entityManager.remove(incidente);
  }

  @Override
  public List<Incidente> getAll() {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery cq = cb.createQuery(Incidente.class);
    Root root = cq.from(Incidente.class);
    cq.select(root);
    TypedQuery<Incidente> tq = entityManager.createQuery(cq);
    return tq.getResultList();
  }

  @Override
  public List<Incidente> getBySoporte(Soporte soporte) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery cq = cb.createQuery(Incidente.class);
    Root root = cq.from(Incidente.class);
    cq.select(root).where(cb.equal(root.get("soporte"),soporte));
    TypedQuery<Incidente> tq = entityManager.createQuery(cq);
    return tq.getResultList();
  }
}
