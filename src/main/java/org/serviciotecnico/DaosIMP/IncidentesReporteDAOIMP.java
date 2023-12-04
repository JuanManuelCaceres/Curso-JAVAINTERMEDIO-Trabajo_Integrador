package org.serviciotecnico.DaosIMP;


import org.serviciotecnico.Daos.IncidentesReporteDAO;
import org.serviciotecnico.entities.IncidentesReporte;
import org.serviciotecnico.entities.Reporte;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class IncidentesReporteDAOIMP implements IncidentesReporteDAO {
  private final EntityManager entityManager;

  public IncidentesReporteDAOIMP(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public void save(IncidentesReporte incidentesReporte) {
    try {
      entityManager.persist(incidentesReporte);
    } catch (Exception e){
      e.printStackTrace();
    }
  }

  @Override
  public IncidentesReporte getByID(int id) {
    return entityManager.find(IncidentesReporte.class,id);
  }

  @Override
  public void update(IncidentesReporte incidentesReporte) {
    entityManager.merge(incidentesReporte);
  }

  @Override
  public void delete(IncidentesReporte incidentesReporte) {
    entityManager.remove(incidentesReporte);
  }

  @Override
  public List<IncidentesReporte> getAll() {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery cq = cb.createQuery(IncidentesReporte.class);
    Root root = cq.from(IncidentesReporte.class);
    cq.select(root);
    TypedQuery<IncidentesReporte> tq = entityManager.createQuery(cq);
    return tq.getResultList();
  }
  @Override
  public List<IncidentesReporte> getAllByReporte(Reporte reporte) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery cq = cb.createQuery(IncidentesReporte.class);
    Root root = cq.from(IncidentesReporte.class);
    cq.select(root).where(cb.equal(root.get("reporte"),reporte));
    TypedQuery<IncidentesReporte> tq = entityManager.createQuery(cq);
    return tq.getResultList();
  }
}
