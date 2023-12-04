package org.serviciotecnico.DaosIMP;

import org.serviciotecnico.Daos.TecnicoDAO;
import org.serviciotecnico.entities.Incidente;
import org.serviciotecnico.entities.Tecnico;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class TecnicoDAOIMP implements TecnicoDAO {
  private final EntityManager entityManager;

  public TecnicoDAOIMP(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public void save(Tecnico tecnico) {
    try {
      entityManager.persist(tecnico);
    } catch (Exception e){
      e.printStackTrace();
    }
  }

  @Override
  public Tecnico getByID(int id) {
    return entityManager.find(Tecnico.class,id);
  }

  @Override
  public void update(Tecnico tecnico) {
    entityManager.merge(tecnico);
  }

  @Override
  public void delete(Tecnico tecnico) {
    entityManager.remove(tecnico);
  }

  @Override
  public List<Tecnico> getAll() {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery cq = cb.createQuery(Tecnico.class);
    Root root = cq.from(Tecnico.class);
    cq.select(root);
    TypedQuery<Tecnico> tq = entityManager.createQuery(cq);
    return tq.getResultList();
  }

  @Override
  public List<Tecnico> getByNombreApellido(String nombre, String apellido) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery cq = cb.createQuery(Tecnico.class);
    Root root = cq.from(Tecnico.class);
    cq.select(root).where(
            cb.equal(root.get("nombre"),nombre),
            cb.equal(root.get("apellido"),apellido));
    TypedQuery<Tecnico> tq = entityManager.createQuery(cq);
    return tq.getResultList();
  }

  @Override
  public List<Tecnico> getByCuil(int cuil) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery cq = cb.createQuery(Tecnico.class);
    Root root = cq.from(Tecnico.class);
    cq.select(root).where(
            cb.equal(root.get("cuil_cuit"),cuil));
    TypedQuery<Tecnico> tq = entityManager.createQuery(cq);
    return tq.getResultList();
  }

  @Override
  public List<Tecnico> getByIncidentes(Incidente incidente) {
    return null;
  }


}
