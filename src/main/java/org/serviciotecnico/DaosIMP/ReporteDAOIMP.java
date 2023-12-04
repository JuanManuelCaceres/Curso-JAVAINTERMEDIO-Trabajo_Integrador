package org.serviciotecnico.DaosIMP;

import org.serviciotecnico.Daos.ReporteDAO;
import org.serviciotecnico.entities.Cliente;
import org.serviciotecnico.entities.IncidentesReporte;
import org.serviciotecnico.entities.Reporte;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class ReporteDAOIMP implements ReporteDAO {
  private final EntityManager entityManager;

  public ReporteDAOIMP(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public void save(Reporte reporte) {
    try {
      entityManager.persist(reporte);
    } catch (Exception e){
      e.printStackTrace();
    }
  }

  @Override
  public Reporte getByID(int id) {
    return entityManager.find(Reporte.class,id);
  }

  @Override
  public void update(Reporte reporte) {
    entityManager.merge(reporte);
  }

  @Override
  public void delete(Reporte reporte) {
    entityManager.remove(reporte);
  }

  @Override
  public List<Reporte> getAll() {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery cq = cb.createQuery(Reporte.class);
    Root root = cq.from(Reporte.class);
    cq.select(root);
    TypedQuery<Reporte> tq = entityManager.createQuery(cq);
    return tq.getResultList();
  }

  @Override
  public List<Reporte> getReportesByCliente(Cliente cliente){
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery cq = cb.createQuery(Reporte.class);
    Root root = cq.from(Reporte.class);
    cq.select(root).where(
            cb.equal(root.get("cliente"),cliente),
            cb.equal(root.get("resuelto"),0));
    TypedQuery<Reporte> tq = entityManager.createQuery(cq);
    return tq.getResultList();
  }
  @Override
  public List<Reporte> getReportesFinalizadosByCliente(Cliente cliente){
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery cq = cb.createQuery(Reporte.class);
    Root root = cq.from(Reporte.class);
    cq.select(root).where(
            cb.equal(root.get("cliente"),cliente),
            cb.equal(root.get("resuelto"),1));
    TypedQuery<Reporte> tq = entityManager.createQuery(cq);
    return tq.getResultList();
  }
}
