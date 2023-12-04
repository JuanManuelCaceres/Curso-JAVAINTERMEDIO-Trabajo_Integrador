package org.serviciotecnico.DaosIMP;

import org.serviciotecnico.Daos.EmpleadosDAO;
import org.serviciotecnico.entities.Empleado;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class EmpleadosDAOIMP implements EmpleadosDAO {

  private final EntityManager entityManager;

  public EmpleadosDAOIMP(EntityManager entityManager) {
    this.entityManager = entityManager;
  }


  @Override
  public void save(Empleado empleado) {
    try {
      entityManager.persist(empleado);
    }catch (Exception e){
      e.printStackTrace();
    };
  }

  @Override
  public Empleado getByID(int id) {
    return entityManager.find(Empleado.class,id);
  }

  @Override
  public void update(Empleado empleado) {
    entityManager.merge(empleado);
  }

  @Override
  public void delete(Empleado empleado) {
    entityManager.remove(empleado);
  }

  @Override
  public List<Empleado> getAll() {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery cq = cb.createQuery(Empleado.class);
    Root root = cq.from(Empleado.class);
    cq.select(root);
    TypedQuery<Empleado> tq = entityManager.createQuery(cq);
    return tq.getResultList();

  }

  @Override
  public List<Empleado> getByNombreApellido(String nombre, String apellido) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery cq = cb.createQuery(Empleado.class);
    Root root = cq.from(Empleado.class);
    cq.select(root).where(
            cb.equal(root.get("nombre"),nombre),
            cb.equal(root.get("apellido"),apellido)
    );
    TypedQuery<Empleado> tq = entityManager.createQuery(cq);
    return tq.getResultList();
  }

  @Override
  public List<Empleado> getByCuil(int cuil) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery cq = cb.createQuery(Empleado.class);
    Root root = cq.from(Empleado.class);
    cq.select(root).where(
            cb.equal(root.get("cuil_cuit"),cuil)
    );
    TypedQuery<Empleado> tq = entityManager.createQuery(cq);
    return tq.getResultList();
  }
}
