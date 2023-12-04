package org.serviciotecnico;

import org.serviciotecnico.Daos.*;
import org.serviciotecnico.DaosIMP.*;
import org.serviciotecnico.entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

public class Main {
  public static EntityManager getEntityManager() {
    EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPA_PU");
    EntityManager manager = factory.createEntityManager();
    return manager;
  }


  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    boolean conectar = true;
    System.out.println("Cargando datos de la base de datos...");

    //Creando entidades para manejar los objetos

    EntityManager em = getEntityManager();
    AreaDAO areaDAO = new AreaDAOIMP(em);
    ClientesDAO clientesDAO = new ClienteDAOIMP(em);
    EmpleadosDAO empleadosDAO = new EmpleadosDAOIMP(em);
    EspecialidadDAO especialidadDAO = new EspecialidadDAOIMP(em);
    IncidenteDAO incidenteDAO = new IncidenteDAOIMP(em);
    IncidentesReporteDAO incidentesReporteDAO = new IncidentesReporteDAOIMP(em);
    ReporteDAO reporteDAO = new ReporteDAOIMP(em);
    SoporteDAO soporteDAO = new SoporteDAOIMP(em);
    ServicioDAO servicioDAO = new ServicioDAOIMP(em);
    TecnicoDAO tecnicoDAO = new TecnicoDAOIMP(em);
    EntityTransaction tx = em.getTransaction();
    System.out.println("Base de datos cargada correctamente");
    System.out.println("");
    System.out.println("Sistema General - Servicio Tecnico S.R.L");

    //Comprobacion de inicio de sesion
    System.out.println("0- Cerrar Programa    1- Iniciar Sesion");
    int accion = in.nextInt();
    System.out.println("");
    while (accion != 0 && accion != 1) {
      System.out.println("Opcion incorrecta");

      System.out.println("0- Cerrar Programa    1- Iniciar Sesion");
      accion = in.nextInt();
      System.out.println("");
    }
    if (accion == 0) {
      System.exit(1);
    }

    while (conectar) {
      //Revisar si hay que dejarlo o no
      tx.begin();

      System.out.print("Ingrese ID de usuario: ");
      int idSesion = in.nextInt();

      Empleado empleadoActual = (Empleado) new EmpleadosDAOIMP(em).getByID(idSesion);
      tx.commit();

      System.out.println("Iniciando Sesion.. \n");
      System.out.println("Bienvenido " + empleadoActual.getNombre());
      System.out.println("Area de trabajo: " + empleadoActual.getArea().getNombre() + "\n");

      boolean iniciarSesion = true;

      while (iniciarSesion) {

        switch (empleadoActual.getArea().getNombre()) {
          case "RRHH":{

            System.out.println("Bienvenido al Area Recursos Humanos");
            System.out.println("Lista de Acciones");
            System.out.println(
                    "1- Alta Nuevo Empleado\n" +
                            "2- Baja Empleado\n" +
                            "3- Buscar Empleado\n" +
                            "4- Ver lista de Empleados\n" +
                            "5- Modificar Empleado\n" +
                            "6- Area de Tecnicos\n" +
                            "7- Informes \n"+
                            "0- Cerrar Sesion");

            System.out.print("Opcion: ");
            int opcionRRHH = in.nextInt();


            switch (opcionRRHH) {
              case 1:
                boolean continuarRH1 = true;
                in.nextLine();

                while (continuarRH1) {
                  String respuestaRH;

                  System.out.println("Registro de nuevo empleado");
                  System.out.print("Nombre: ");
                  String nombre = in.nextLine();
                  System.out.print("Apellido: ");
                  String apellido = in.nextLine();
                  System.out.print("CUIL/CUIT: ");
                  long cuit = in.nextLong();

                  System.out.print("Area (id): ");
                  int area_id = in.nextInt();

                  tx.begin();
                  Area area = areaDAO.getByID(area_id);

                  Empleado empleado = new Empleado(nombre, apellido, cuit, area);

                  //Hacemos el create
                  empleadosDAO.save(empleado);

                  tx.commit();

                  in.nextLine();

                  System.out.println("Desea cargar nuevo usuario? (y/n): ");
                  respuestaRH = in.nextLine();

                  while (!respuestaRH.equals("n") && !respuestaRH.equals("y")) {

                    System.out.println("Opcion icorrecta.");
                    System.out.println("Desea cargar nuevo usuario? (y/n): ");
                    respuestaRH = in.nextLine();
                  }
                  if (respuestaRH.equals("n")) {
                    continuarRH1 = false;
                  }
                }
                break;

              case 2: {
                System.out.print("Ingrese ID: ");
                int empleadoBajaId = in.nextInt();
                tx.begin();
                empleadosDAO.delete(empleadosDAO.getByID(empleadoBajaId));
                tx.commit();
              }
              break;

              case 3: {
                System.out.print(
                        "Buscar por:\n" +
                                "1- Id   2- Apellido y Nombre   3- CUIL/CUIT\n" +
                                "Opcion: ");
                int opcionBuscarEmpleado = in.nextInt();
                tx.begin();

                switch (opcionBuscarEmpleado) {
                  case 1:
                    System.out.print("Ingrese ID del Empleado: ");
                    int idBuscarEmpleado = in.nextInt();
                    Empleado empleadoBuscado = empleadosDAO.getByID(idBuscarEmpleado);
                    System.out.println("");
                    System.out.println(
                            "Datos del Empleado\n" +
                                    "Nombre:   " + empleadoBuscado.getNombre() + "\n" +
                                    "Apellido: " + empleadoBuscado.getApellido() + "\n" +
                                    "Area de trabajo: " + areaDAO.getByID(empleadoBuscado.getArea().getId()).getNombre() + "\n" +
                                    "CUIL/CUIT: " + empleadoBuscado.getCuil());
                    System.out.println();
                    break;
                  case 2:
                    in.nextLine();
                    List<Empleado> listaEmpleados = empleadosDAO.getAll();

                    System.out.print("Nombre: ");
                    String nombreEmpleadoBuscado = in.nextLine();
                    System.out.print("Apellido: ");
                    String apellidoEmpleadoBuscado = in.nextLine();
                    System.out.println("Empleado buscado: " + nombreEmpleadoBuscado + " " + apellidoEmpleadoBuscado + "\n");

                    List<Empleado> listaBusqueda = empleadosDAO.getByNombreApellido(nombreEmpleadoBuscado, apellidoEmpleadoBuscado);

                    for (Empleado e : listaBusqueda) {
                      System.out.println(
                              "Datos del Empleado\n" +
                                      "Nombre:   " + e.getNombre() + "\n" +
                                      "Apellido: " + e.getApellido() + "\n" +
                                      "Area de trabajo: " + areaDAO.getByID(e.getArea().getId()).getNombre() + "\n" +
                                      "CUIL/CUIT: " + e.getCuil());
                    }
                    System.out.println();
                    break;

                  case 3:
                    System.out.print("Ingrese CUIL/CUIT: ");
                    int cuil = in.nextInt();

                    List<Empleado> listaBusquedaCuil = empleadosDAO.getByCuil(cuil);

                    for (Empleado e : listaBusquedaCuil) {
                      System.out.println(
                              "Datos del Empleado\n" +
                                      "Nombre:   " + e.getNombre() + "\n" +
                                      "Apellido: " + e.getApellido() + "\n" +
                                      "Area de trabajo: " + areaDAO.getByID(e.getArea().getId()).getNombre() + "\n" +
                                      "CUIL/CUIT: " + e.getCuil());
                    }
                    System.out.println();

                    break;


                }

                tx.commit();

              }
              break;

              case 4: {
                tx.begin();
                List<Empleado> listaEmpleados = empleadosDAO.getAll();
                System.out.println(" ID \t Nombre y Apellido");
                for (Empleado empleado : listaEmpleados) {
                  System.out.println(empleado.getId() + "\t" + empleado.getNombre() + " " + empleado.getApellido());
                }
                System.out.println();
                in.nextLine();
                System.out.print("Pulse \"Enter\" para continuar");
                String enter = in.nextLine();

                System.out.println("");
                tx.commit();
              }
              break;

              case 5: {
                tx.begin();
                in.nextLine();
                System.out.println("Modificar Empleado");
                System.out.print("Nombre: ");
                String nombreEmpleadoMod = in.nextLine();
                System.out.print("Apellido: ");
                String apellidoEmpleadoMod = in.nextLine();


                Empleado empleadosMod = empleadosDAO.getByNombreApellido(nombreEmpleadoMod, apellidoEmpleadoMod).get(0);

                boolean continuarModificacion = true;
                while (continuarModificacion) {
                  System.out.println(
                          "1- Modificar Nombre\n" +
                                  "2- Modificar Apellido\n" +
                                  "3- Modificar CUIT/CUIL\n" +
                                  "4- Modificar Area\n" +
                                  "0- Terminar");
                  System.out.println("Opcion: ");
                  int opcionModEmpleado = in.nextInt();

                  switch (opcionModEmpleado) {
                    case 0:
                      continuarModificacion = false;
                      break;

                    case 1:
                      in.nextLine();
                      System.out.print("Nuevo nombre: ");
                      String nuevoNombre = in.nextLine();
                      empleadosMod.setNombre(nuevoNombre);
                      break;

                    case 2:
                      in.nextLine();
                      System.out.print("Nuevo apellido: ");
                      String nuevoApellido = in.nextLine();
                      empleadosMod.setApellido(nuevoApellido);
                      break;

                    case 3:
                      in.nextLine();
                      System.out.print("Nuevo CUIL/CUIT: ");
                      int nuevoCuil = in.nextInt();
                      empleadosMod.setCuil(nuevoCuil);
                      break;

                    case 4:
                      in.nextLine();
                      System.out.print("Nuevo Area: ");
                      String nombreNuevaArea = in.nextLine();
                      Area nuevaArea = areaDAO.getByNombre(nombreNuevaArea).get(0);
                      empleadosMod.setArea(nuevaArea);
                      break;
                  }
                }
                tx.commit();
              }
              break;

              case 6:
                System.out.println("Seccion Control de Tecnicos Especialzados");
                boolean continuarSecTecnicos = true;
                while (continuarSecTecnicos) {
                  System.out.println(
                          "1- Nuevo Tecnico\n" +
                                  "2- Dar de baja Tecnico\n" +
                                  "3- Buscar Tecnico\n" +
                                  "4- Ver listado de Tecnicos\n" +
                                  "5- Modificar Tecnico\n" +
                                  "0- Terminar");
                  System.out.println("Opcion: ");
                  int opcionSecTecnicos = in.nextInt();

                  switch (opcionSecTecnicos) {
                    case 0:
                      continuarSecTecnicos = false;
                      break;
                    case 1:
                      boolean continuarSecTec1 = true;
                      in.nextLine();

                      while (continuarSecTec1) {
                        String respuestaSecTec;

                        System.out.println("Registro de nuevo tecnico");
                        System.out.print("Nombre: ");
                        String nombre = in.nextLine();
                        System.out.print("Apellido: ");
                        String apellido = in.nextLine();
                        System.out.print("CUIL/CUIT: ");
                        int cuit = in.nextInt();

                        Tecnico nuevoTecnico = new Tecnico(nombre, apellido, cuit, empleadoActual, empleadoActual);
                        tx.begin();
                        //Hacemos el create
                        tecnicoDAO.save(nuevoTecnico);

                        //Carga de las especialidades
                        List<Especialidad> especialidadesDisponibles = especialidadDAO.getAll();
                        tx.commit();
                        System.out.println(
                                "   Especialidades Disponibles\n" +
                                        "---------------------------------");
                        System.out.println("Id \t  + Especialidad");
                        for (Especialidad especialidad : especialidadesDisponibles) {
                          System.out.println(especialidad.getId() + "\t" + especialidad.getNombre());
                        }
                        System.out.print("ID Especialidad (0:Terminar): ");
                        int idAgregarEspecialidad = in.nextInt();
                        tx.begin();
                        while (idAgregarEspecialidad != 0) {

                          try {
                            Especialidad nuevaEspecialidad = especialidadDAO.getByID(idAgregarEspecialidad);
                            if (!nuevoTecnico.getEspecialidades().contains(nuevaEspecialidad)) {
                              nuevoTecnico.addEspecialidad(nuevaEspecialidad);
                              nuevaEspecialidad.addTecnico(nuevoTecnico);
                              especialidadDAO.update(nuevaEspecialidad);
                              tecnicoDAO.update(nuevoTecnico);
                            } else {
                              System.out.println("Ingrese una especialidad distinta");
                            }
                          } catch (Exception e) {
                            System.out.println("Error en la base de datos");
                          }
                          System.out.print("ID Especialidad (0:Terminar): ");
                          idAgregarEspecialidad = in.nextInt();
                        }
                        tx.commit();

                        in.nextLine();

                        System.out.println("Desea cargar nuevo Tecnico(y/n): ");

                        respuestaSecTec = in.nextLine();

                        while (!respuestaSecTec.equals("n") && !respuestaSecTec.equals("y")) {

                          System.out.println("Opcion icorrecta.");
                          System.out.println("Desea cargar nuevo usuario? (y/n): ");
                          respuestaSecTec = in.nextLine();
                        }
                        if (respuestaSecTec.equals("n")) {
                          continuarSecTec1 = false;
                        }
                      }
                      break;
                    case 2:
                      System.out.print("Ingrese ID: ");
                      int tecnicoBajaId = in.nextInt();
                      tx.begin();
                      tecnicoDAO.delete(tecnicoDAO.getByID(tecnicoBajaId));
                      tx.commit();
                      break;
                    case 3:
                      System.out.print(
                              "Buscar por:\n" +
                                      "1- Id   2- Apellido y Nombre   3- CUIL/CUIT\n" +
                                      "Opcion: ");
                      int opcionBuscarTecnico = in.nextInt();
                      tx.begin();

                      switch (opcionBuscarTecnico) {
                        case 1:

                          System.out.print("Ingrese ID del Tecnico: ");
                          int idBuscarETecnico = in.nextInt();
                          Tecnico tecnicoBuscado = tecnicoDAO.getByID(idBuscarETecnico);
                          System.out.println("");
                          System.out.println(
                                  "Datos del Empleado\n" +
                                          "Nombre:   " + tecnicoBuscado.getNombre() + "\n" +
                                          "Apellido: " + tecnicoBuscado.getApellido() + "\n" +
                                          "CUIL/CUIT: " + tecnicoBuscado.getCuil() + "\n" +
                                          "Especialidades\n-------------");
                          for (Especialidad especialidad : tecnicoBuscado.getEspecialidades()) {
                            System.out.println(especialidad.getNombre() + " - " + especialidad.getDescripcion());
                          }
                          System.out.print("Presione Enter para continuar");
                          in.nextLine();
                          String enter = in.nextLine();
                          System.out.println();
                          break;
                        case 2:
                          in.nextLine();

                          System.out.print("Nombre: ");
                          String nombreTecnicoBuscado = in.nextLine();
                          System.out.print("Apellido: ");
                          String apellidoTecnicoBuscado = in.nextLine();
                          System.out.println("Tecnico buscado: " + nombreTecnicoBuscado + " " + apellidoTecnicoBuscado + "\n");

                          List<Tecnico> listaBusqueda = tecnicoDAO.getByNombreApellido(nombreTecnicoBuscado, apellidoTecnicoBuscado);

                          for (Tecnico tecnico : listaBusqueda) {
                            System.out.println(
                                    "Datos del Empleado\n" +
                                            "Nombre:   " + tecnico.getNombre() + "\n" +
                                            "Apellido: " + tecnico.getApellido() + "\n" +
                                            "CUIL/CUIT: " + tecnico.getCuil() + "\n" +
                                            "Especialidades\n-------------");
                            for (Especialidad especialidad : tecnico.getEspecialidades()) {
                              System.out.println(especialidad.getNombre() + " - " + especialidad.getDescripcion());
                            }
                          }
                          System.out.print("Presione Enter para continuar");
                          in.nextLine();
                          enter = in.nextLine();
                          System.out.println();
                          break;

                        case 3:
                          System.out.print("Ingrese CUIL/CUIT: ");
                          int cuil = in.nextInt();

                          List<Tecnico> listaBusquedaCuil = tecnicoDAO.getByCuil(cuil);

                          for (Tecnico tecnico : listaBusquedaCuil) {
                            System.out.println(
                                    "Datos del Empleado\n" +
                                            "Nombre:    " + tecnico.getNombre() + "\n" +
                                            "Apellido:  " + tecnico.getApellido() + "\n" +
                                            "CUIL/CUIT: " + tecnico.getCuil() + "\n" +
                                            "Especialidades\n-------------");
                            for (Especialidad especialidad : tecnico.getEspecialidades()) {
                              System.out.println(especialidad.getNombre() + " - " + especialidad.getDescripcion());
                            }
                            ;
                          }
                          System.out.print("Presione Enter para continuar");
                          in.nextLine();
                          enter = in.nextLine();
                          System.out.println();

                          break;


                      }

                      tx.commit();


                      break;

                    case 4:
                      tx.begin();
                      List<Tecnico> listaTecnicos = tecnicoDAO.getAll();
                      System.out.println(" ID \t Nombre y Apellido");
                      for (Tecnico tecnico : listaTecnicos) {
                        System.out.println(tecnico.getId() + "\t" + tecnico.getNombre() + " " + tecnico.getApellido());
                      }
                      System.out.println();
                      in.nextLine();
                      System.out.print("Pulse \"Enter\" para continuar");
                      String enter = in.nextLine();

                      System.out.println("");
                      tx.commit();
                      break;

                    case 5:
                      tx.begin();
                      in.nextLine();
                      System.out.println("Modificar Empleado");
                      System.out.print("Nombre: ");
                      String nombreTecnicoMod = in.nextLine();
                      System.out.print("Apellido: ");
                      String apellidoTecnicoMod = in.nextLine();


                      Tecnico tecnicoMod = tecnicoDAO.getByNombreApellido(nombreTecnicoMod, apellidoTecnicoMod).get(0);

                      boolean continuarModificacionTenico = true;

                      while (continuarModificacionTenico) {
                        System.out.println(
                                "1- Modificar Nombre\n" +
                                        "2- Modificar Apellido\n" +
                                        "3- Modificar CUIT/CUIL\n" +
                                        "4- Agregar Especialidades\n" +
                                        "5- Quitar Especialidad\n" +
                                        "0- Terminar");
                        System.out.println("Opcion: ");
                        int opcionModTecnico = in.nextInt();

                        switch (opcionModTecnico) {
                          case 0:
                            continuarModificacionTenico = false;
                            break;

                          case 1:
                            in.nextLine();
                            System.out.print("Nuevo nombre: ");
                            String nuevoNombre = in.nextLine();
                            tecnicoMod.setNombre(nuevoNombre);
                            break;

                          case 2:
                            in.nextLine();
                            System.out.print("Nuevo apellido: ");
                            String nuevoApellido = in.nextLine();
                            tecnicoMod.setApellido(nuevoApellido);
                            break;

                          case 3:
                            in.nextLine();
                            System.out.print("Nuevo CUIL/CUIT: ");
                            int nuevoCuil = in.nextInt();
                            tecnicoMod.setCuil(nuevoCuil);
                            break;

                          case 4:
                            in.nextLine();
                            //Carga de las especialidades
                            List<Especialidad> especialidadesDisponibles = especialidadDAO.getAll();

                            System.out.println(
                                    "   Especialidades Disponibles\n" +
                                            "---------------------------------");
                            System.out.println("Id \t  + Especialidad");
                            for (Especialidad especialidad : especialidadesDisponibles) {
                              System.out.println(especialidad.getId() + "\t" + especialidad.getNombre());
                            }
                            System.out.print("ID Especialidad (0:Terminar): ");
                            int idAgregarEspecialidad = in.nextInt();

                            while (idAgregarEspecialidad != 0) {
                              try {
                                Especialidad nuevaEspecialidadAgregar = especialidadDAO.getByID(idAgregarEspecialidad);
                                if (!tecnicoMod.getEspecialidades().contains(nuevaEspecialidadAgregar)) {

                                  tecnicoMod.addEspecialidad(nuevaEspecialidadAgregar);
                                  nuevaEspecialidadAgregar.addTecnico(tecnicoMod);

                                  especialidadDAO.update(nuevaEspecialidadAgregar);
                                  tecnicoDAO.update(tecnicoMod);

                                } else {
                                  System.out.println("Ingrese una especialidad distinta");
                                }
                              } catch (Exception e) {
                                System.out.println("Error en la base de datos");
                              }
                              System.out.print("ID Especialidad (0:Terminar): ");
                              idAgregarEspecialidad = in.nextInt();
                            }
                            break;
                          case 5:
                            in.nextLine();
                            //Carga de especialidades del tecnico
                            System.out.println("     Especialidades");
                            System.out.println(" ID      Descripcion");
                            for (Especialidad especialidad : tecnicoMod.getEspecialidades()) {
                              System.out.println(especialidad.getId() + "\t" + especialidad.getNombre() + " - " + especialidad.getDescripcion());
                            }
                            System.out.print("Ingrese ID de Especialidad a quitar(0:Terminar): ");
                            int quitarEspecialidad = in.nextInt();

                            while (quitarEspecialidad != 0) {
                              Especialidad especialidadMod = especialidadDAO.getByID(quitarEspecialidad);
                              tecnicoMod.getEspecialidades().remove(especialidadMod);
                              especialidadMod.getTecnicos().remove(tecnicoMod);
                              tecnicoDAO.update(tecnicoMod);
                              especialidadDAO.update(especialidadMod);

                              System.out.print("Ingrese ID de Especialidad a quitar(0:Terminar): ");
                              quitarEspecialidad = in.nextInt();
                            }

                            System.out.println();
                            in.nextLine();
                            System.out.print("Pulse \"Enter\" para continuar");
                            enter = in.nextLine();

                            System.out.println("");

                            break;
                        }
                      }
                      tx.commit();
                      break;
                  }

                }
                break;
              case 7: {
                //Seccion Informes
                System.out.println(
                        "1- Ver Informe Diario\n" +
                                "2- Tecnico con mas incidentes Resueltos\n" +
                                "3- Tecnico con mas incidentes Resueltos por Especialidad\n" +
                                "4- Tecnico mas rapido\n" +
                                "0- Terminar");

                System.out.print("Opcion(0:Terminar): ");
                int opcionRRHHInformes = in.nextInt();

                while (opcionRRHHInformes != 0) {

                  switch (opcionRRHHInformes) {
                    case 1: {
                      tx.begin();
                      LocalDate hoy = LocalDate.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth());

                      List<Reporte> reportesDiarios = reporteDAO.getAll().stream().filter(reporte -> reporte.getFechaAlta().equals(hoy)).collect(Collectors.toList());

                      //Comprobacion de que existen reportes
                      reportesDiarios.forEach(System.out::println);

                      //Impresion de reportes
                      for (Reporte reporte : reportesDiarios) {
                        System.out.println(
                                "ID: " + reporte.getId() + "\n" +
                                        "Cliente: " + reporte.getCliente().getRazonSocial() + "\n" +
                                        "Desripcion: " + reporte.getDescripcion() + "\n" +
                                        ((reporte.getResuelto() == 0)
                                                ? "Fecha de Resolucion Estimada: " + reporte.getFecha_resolucion_estimada()
                                                : "Fecha de Resoluicion: " + reporte.getFecha_resolucion()) + "\n" +
                                        "Lista de Incidentes asociados\n");


                        for (IncidentesReporte incidente : incidentesReporteDAO.getAllByReporte(reporte)) {
                          System.out.println(
                                  "\t\tDescripcion: " + incidente.getIncidente().getDescripcion() + "\n" +
                                          "\t\tTecnico Responsable:" + incidente.getTecnico().getNombre() + " " + incidente.getTecnico().getApellido() + "\n" +
                                          "\t\tEstado: " + incidente.getDescripcion() + "\n" +
                                          "\t\tFecha de resolucion: " + incidente.getResolucion());

                        }

                        System.out.println("\n" + "------------------------------------------" + "\n");
                      }

                      tx.commit();
                    }break;

                    case 2: {
                      //Tecnico con mas problemas resueltos en los N dias
                      tx.begin();
                      System.out.print("Indique cantidad de dias: ");
                      int diasAntes = in.nextInt();

                      Tecnico tecnico = tecnicoDAO.getAll().stream().
                              max(Comparator.comparing(t -> t.getIncidentesReportes().stream()
                                      .filter(i->i.getResuelto()==1
                                             &&i.getResolucion()!=null
                                              &&(i.getResolucion().isBefore(LocalDate.now().plusDays(1).atStartOfDay()))
                                              &&(i.getResolucion().isAfter(LocalDate.now().minusDays(diasAntes).atStartOfDay())))
                                      .count())).get();

                      System.out.println("\n"+"El tecnico con mas incidentes resueltos en los ultimos " + diasAntes + " dias es: " + tecnico.getNombre() + " " + tecnico.getApellido());
                      System.out.println();
                      in.nextLine();
                      System.out.println("Presione enter para continuar");
                      String enter = in.nextLine();
                      tx.commit();

                    }break;

                    case 3: {//Tecnico que mas resolvio para una especialidad
                      tx.begin();
                      System.out.print("Indique cantidad de dias: ");
                      int diasAntes = in.nextInt();

                      System.out.println("Lista de Especialidades");
                      List<Especialidad> listaEspecialidades = especialidadDAO.getAll();
                      for (Especialidad especialidad : listaEspecialidades) {
                        System.out.println("ID: " + especialidad.getId() + "\t Descripción: " + especialidad.getNombre() + " - " + especialidad.getDescripcion());
                      }
                      System.out.println("Ingrese ID Especialidad: ");
                      int idEspecialidad = in.nextInt();

                      Tecnico tecnico = tecnicoDAO.getAll().stream()
                              .filter(t -> t.getEspecialidades().contains(especialidadDAO.getByID(idEspecialidad)))
                              .max(Comparator.comparing(t -> t.getIncidentesReportes().stream()
                                      .filter(i->i.getResuelto()==1
                                              &&i.getResolucion()!=null
                                              &&(i.getResolucion().isBefore(LocalDate.now().plusDays(1).atStartOfDay()))
                                              &&(i.getResolucion().isAfter(LocalDate.now().minusDays(diasAntes).atStartOfDay())))
                                      .count())).get();

                      System.out.println("El tecnico con mas Incidentes resueltos de la especialidad "
                              + especialidadDAO.getByID(idEspecialidad).getNombre() + " - " + especialidadDAO.getByID(idEspecialidad).getDescripcion() +
                              "en los ultimos " + diasAntes + " es: " + tecnico.getNombre() + " " + tecnico.getApellido());


                      in.nextLine();
                      System.out.println("Presione enter para continuar");
                      String enter = in.nextLine();

                      tx.commit();

                    }
                    break;

                    case 4: {//Tecnico Mas rapido
                      tx.begin();

                      Map<Tecnico, Double> listaTecnicosDuraciones = tecnicoDAO.getAll().stream()
                              .collect(Collectors.toMap(t -> t,
                                      t -> {
                                        Double promedio = t.getIncidentesReportes().stream()
                                                .filter(i -> i.getResuelto() == 1 &&
                                                        i.getResolucion() != null
                                                )
                                                .mapToLong(i -> {
                                                  LocalDateTime fechaInicio = i.getReporte().getFechaAlta();
                                                  LocalDateTime fechaFin = i.getResolucion();

                                                 return Duration.between(fechaInicio, fechaFin).toMinutes();
                                                })
                                                .average().getAsDouble();


                                        return promedio;

                                      }
                              ));

                      Tecnico tecnicoMasRapido = listaTecnicosDuraciones.entrySet().stream().
                              min(Map.Entry.comparingByValue()).get().getKey();

                      System.out.println(
                              "El tecnico mas rapdido es: "+tecnicoMasRapido.getNombre()+" "+tecnicoMasRapido.getApellido()+"\n"+
                              "Con un promedio de: "+listaTecnicosDuraciones.get(tecnicoMasRapido)+" minutos por incidente");


                      tx.commit();
                    }break;
                  }

                  System.out.println(
                          "1- Ver Informe Diario\n" +
                                  "2- Tecnico con mas incidentes Resueltos\n" +
                                  "3- Tecnico con mas incidentes Resueltos por Especialidad\n" +
                                  "4- Tecnico mas rapido\n" +
                                  "0- Terminar");

                  System.out.print("Opcion(0:Terminar): ");
                  opcionRRHHInformes = in.nextInt();
                }

              }break;
              case 0:
                iniciarSesion = !iniciarSesion;
                conectar=false;
                break;
          }}break;

          case "Comercial": {

            System.out.println("Bienvenido al Area Comercial");
            System.out.println("Lista de Acciones");
            System.out.println(
                    "1- Alta Nuevo Cliente\n" +
                            "2- Baja Cliente\n" +
                            "3- Buscar Cliente\n" +
                            "4- Ver lista de Clientes\n" +
                            "5- Modificar Cliente\n" +
                            "0- Cerrar Sesion");

            System.out.print("Opcion: ");
            int opcionComercial = in.nextInt();
            in.nextLine();
            switch (opcionComercial) {
              case 0: {
                iniciarSesion = false;
                conectar = false;
              }
              break;

              case 1: { //Alta Nuevo Cliente
                System.out.print("Razon Social: ");

                String razonSocial = in.nextLine();

                System.out.print("CUIT: ");
                int cuit = in.nextInt();
                Cliente nuevoCliente = new Cliente(razonSocial, cuit);

                tx.begin();
                clientesDAO.save(nuevoCliente);
                tx.commit();

                tx.begin();
                boolean contratarSoporte = true;
                while (contratarSoporte) {
                  System.out.println("Lista de Soportes disponibles");

                  List<Soporte> listaSoportes = soporteDAO.getAll();

                  //Imprime Lista de Soportes
                  System.out.println(" ID \t" + " Nombre");
                  for (Soporte s : listaSoportes) {
                    System.out.println(" " + s.getId() + "\t\t" + s.getNombre());
                  }

                  System.out.println("");
                  System.out.println("Id Soporte: ");
                  int idSoporte = in.nextInt();

                  //Imprime lista de servicios filtrados por id de soporte
                  List<Integer> servicios = new ArrayList<>();
                  List<Servicio> listaServicios =
                          servicioDAO.getAll()
                                  .stream()
                                  .filter(s -> s.getSoporte().getId() == idSoporte)
                                  .collect(Collectors.toList());

                  System.out.println(" ID \t" + " Nombre");
                  for (Servicio s : listaServicios) {
                    System.out.println(" " + s.getId() + "\t\t" + s.getNombre());
                  }


                  //Loop para contratar mas de un servicio
                  System.out.print("ID Servicio a contratar(0:terminar): ");
                  int servContratar = in.nextInt();

                  while (servContratar != 0) {
                    Servicio servicio = servicioDAO.getByID(servContratar);
                    servicio.addCliente(nuevoCliente);
                    nuevoCliente.addServicio(servicio);
                    servicioDAO.update(servicio);
                    clientesDAO.update(nuevoCliente);
                    System.out.print("ID Servicio a contratar(0:terminar): ");
                    servContratar = in.nextInt();
                  }
                  //Consume el caracter del bucle anterior
                  in.nextLine();

                  //Verificacion de salida
                  System.out.print("Contratar otro soporte(y/n): ");
                  String respuComer1 = in.next();

                  while (!respuComer1.equals("n") && !respuComer1.equals("y")) {

                    System.out.println("Opcion icorrecta.");
                    System.out.println("Contratar otro Servicio? (y/n): ");
                    respuComer1 = in.nextLine();
                  }
                  if (respuComer1.equals("n")) {
                    contratarSoporte = false;
                  }
                }

                tx.commit();
              }
              break;

              case 2: {//Baja Cliente
                System.out.print("Ingrese CUIT: ");
                int cuitBaja = in.nextInt();

                tx.begin();
                Cliente clienteBaja = clientesDAO.getByCUIT(cuitBaja);
                clientesDAO.delete(clienteBaja);

                System.out.println("Cliente: " + clienteBaja.getRazonSocial() + " eliminado de la base de datos");
                System.out.println("");
                tx.commit();
              }
              break;

              case 3: {//Buscar Cliente y sus datos asociados
                System.out.print("Ingrese CUIT: ");
                int cuitBuscado = in.nextInt();
                tx.begin();
                Cliente clienteBuscado = clientesDAO.getByCUIT(cuitBuscado);

                System.out.println("\n" + "Datos del cliente");
                System.out.println(
                        "Razón Social: " + clienteBuscado.getRazonSocial() + "\n" +
                                "CUIT: " + clienteBuscado.getCuit() + "\n" +
                                "ID Cliente: " + clienteBuscado.getId()
                );


                boolean continuarBuscarCliente = true;
                while (continuarBuscarCliente) {
                  System.out.println("Lista de Acciones\n" +
                          "1- Ver Servicios Contratados\n" +
                          "2- Ver Reportes Activos\n" +
                          "3- Ver Reportes Finalizados\n"
                          + "0-Terminar"
                  );
                  System.out.print("Opcion: ");
                  int opcionBuscarCliente = in.nextInt();

                  switch (opcionBuscarCliente) {
                    case 0:
                      continuarBuscarCliente = false;
                      break;
                    case 1: {
                      System.out.println(
                              "Lista de Servicios Contratados\n" +
                                      "-------------------------------\n" +
                                      "ID \t Nombre");

                      for (Servicio servicio : clienteBuscado.getServicios()) {
                        System.out.println(servicio.getId() + "\t" + servicio.getNombre());
                      }
                      System.out.println("-------------------------------\n");
                      System.out.println("Presione Enter para continuar");
                      String enter = in.nextLine();
                    }
                    break;
                    case 2: {
                      List<Reporte> reportesActivos = reporteDAO.getReportesByCliente(clienteBuscado);
                      System.out.println(
                              "Lista de Reportes Activos\n" +
                                      "Cantidad total de reportes activos: " + reportesActivos.size() +
                                      "-------------------------------\n");


                      for (Reporte reporte : reportesActivos) {
                        System.out.println(
                                "ID: " + reporte.getId() + "\n" +
                                        "Desripcion: " + reporte.getDescripcion() + "\n" +
                                        "Fecha de Resolucion Estimada: " + reporte.getFecha_resolucion_estimada() + "\n" +
                                        "Lista de Incidentes asociados\n");


                        for (IncidentesReporte incidente : incidentesReporteDAO.getAllByReporte(reporte)) {
                          System.out.println(
                                  "\t\tDescripcion: " + incidente.getIncidente().getDescripcion() + "\n" +
                                          "\t\tTecnico Responsable:" + incidente.getTecnico().getNombre() + " " + incidente.getTecnico().getApellido() + "\n" +
                                          "\t\tEstado: " + incidente.getDescripcion() + "\n" +
                                          "\t\tFecha de resolucion: " + incidente.getResolucion());

                        }

                        System.out.println("\n" + "------------------------------------------" + "\n");

                      }
                    }
                    break;
                    case 3: {
                      System.out.println(
                              "Lista de Reportes Finalizados\n" +
                                      "-------------------------------\n");
                      List<Reporte> reportesFinalizados = reporteDAO.getReportesFinalizadosByCliente(clienteBuscado);

                      for (Reporte reporte : reportesFinalizados) {
                        System.out.println(
                                "ID: " + reporte.getId() + "\n" +
                                        "Desripcion: " + reporte.getDescripcion() + "\n" +
                                        "Fecha de Resolucion: " + reporte.getFecha_resolucion() + "\n" +
                                        "Lista de Incidentes asociados\n");
                        for (IncidentesReporte incidente : incidentesReporteDAO.getAllByReporte(reporte)) {
                          System.out.println(
                                  "\t\tDescripcion: " + incidente.getIncidente().getDescripcion() + "\n" +
                                          "\t\tTecnico Responsable:" + incidente.getTecnico().getNombre() + " " + incidente.getTecnico().getApellido() + "\n" +
                                          "\t\tEstado: " + incidente.getDescripcion() + "\n" +
                                          "\t\tFecha de resolucion: " + incidente.getResolucion());
                          System.out.println("");

                        }
                        System.out.println("\n" + "------------------------------------------" + "\n");
                      }
                      break;
                    }

                  }

                }
                tx.commit();
              }
              break;

              case 4: {
                //Ver todos los clientes
                tx.begin();
                List<Cliente> listaClientes = clientesDAO.getAll();
                System.out.println("Lista de Clientes");
                System.out.println("ID           CUIT           Razon Social");
                for (Cliente cliente : listaClientes) {
                  System.out.println(cliente.getId() + "      " + cliente.getCuit() + "         " + cliente.getRazonSocial());
                }
                tx.commit();
                System.out.print("Presione Enter para continuar");
                String enter = in.nextLine();
              }
              break;

              case 5: {//Modificar Cliente
                tx.begin();

                System.out.print("Ingrese CUIT de cliente: ");
                int cuitClienteMod = in.nextInt();

                Cliente clienteMod = clientesDAO.getByCUIT(cuitClienteMod);

                boolean continuarModCliente = true;

                while (continuarModCliente) {
                  System.out.println("Lista de Acciones");
                  System.out.println(
                          "1- Modificar Razon Social\n" +
                                  "2- Modificar CUIT\n" +
                                  "3- Alta Servicio\n" +
                                  "4- Baja Servicio\n" +
                                  "0- Terminar");

                  System.out.println("Opcion: ");
                  int opcionModCliente = in.nextInt();

                  switch (opcionModCliente) {
                    case 0:
                      continuarModCliente = false;
                      break;

                    case 1:
                      in.nextLine();
                      System.out.print("Ingrese nueva Razon Social: ");
                      String nuevaRazonSocial = in.nextLine();
                      clienteMod.setRazonSocial(nuevaRazonSocial);
                      clientesDAO.update(clienteMod);

                      break;
                    case 2:
                      System.out.println("Ingrese Nuevo CUIT: ");
                      int nuevoCuit = in.nextInt();
                      clienteMod.setCuit(nuevoCuit);
                      clientesDAO.update(clienteMod);
                      break;

                    case 3:
                      boolean contratarSoporte = true;
                      while (contratarSoporte) {
                        System.out.println("Lista de Soportes disponibles");

                        List<Soporte> listaSoportes = soporteDAO.getAll();

                        //Imprime Lista de Soportes
                        System.out.println(" ID \t" + " Nombre");
                        for (Soporte s : listaSoportes) {
                          System.out.println(" " + s.getId() + "\t\t" + s.getNombre());
                        }

                        System.out.println("");
                        System.out.println("Id Soporte: ");
                        int idSoporte = in.nextInt();

                        //Imprime lista de servicios filtrados por id de soporte
                        List<Integer> servicios = new ArrayList<>();
                        List<Servicio> listaServicios =
                                servicioDAO.getAll()
                                        .stream()
                                        .filter(s -> s.getSoporte().getId() == idSoporte)
                                        .collect(Collectors.toList());

                        System.out.println(" ID \t" + " Nombre");
                        for (Servicio s : listaServicios) {
                          System.out.println(" " + s.getId() + "\t\t" + s.getNombre());
                        }


                        //Loop para contratar mas de un servicio
                        System.out.print("ID Servicio a contratar(0:terminar): ");
                        int servContratar = in.nextInt();

                        while (servContratar != 0) {
                          Servicio servicio = servicioDAO.getByID(servContratar);
                          if (!clienteMod.getServicios().contains(servicio)) {
                            servicio.addCliente(clienteMod);
                            clienteMod.addServicio(servicio);
                            servicioDAO.update(servicio);
                            clientesDAO.update(clienteMod);
                          } else {
                            System.out.println("El servicio " + servicio.getNombre() + " ha sido contratado previamente");
                          }

                          System.out.print("ID Servicio a contratar(0:terminar): ");
                          servContratar = in.nextInt();

                        }
                        //Consume el caracter del bucle anterior
                        in.nextLine();

                        //Verificacion de salida
                        System.out.print("Contratar otro soporte(y/n): ");
                        String respuComer2 = in.next();

                        while (!respuComer2.equals("n") && !respuComer2.equals("y")) {

                          System.out.println("Opcion icorrecta.");
                          System.out.println("Desea cargar nuevo usuario? (y/n): ");
                          respuComer2 = in.nextLine();
                        }
                        if (respuComer2.equals("n")) {
                          contratarSoporte = false;
                        }
                      }
                      break;

                    case 4:
                      System.out.println("");
                      System.out.println("Lista de servicios contratados");
                      for (Soporte soporte : clienteMod.getServicios().stream().map(s -> s.getSoporte()).distinct().collect(Collectors.toList())) {
                        System.out.println("");
                        System.out.println("Soporte: " + soporte.getNombre());
                        for (Servicio servicio : clienteMod.getServicios().stream().filter(s -> s.getSoporte().equals(soporte)).collect(Collectors.toList())) {
                          System.out.println("ID: " + servicio.getId() + "\t Nombre: " + servicio.getNombre());
                        }
                        System.out.println("-----------------------------");
                      }

                      boolean continuarQuitar = true;
                      System.out.print("Ingrese ID servicio(0:terminar): ");
                      int idQuitarServicio = in.nextInt();
                      while (continuarQuitar) {
                        Servicio quitarServicio = servicioDAO.getByID(idQuitarServicio);

                        if (clienteMod.getServicios().contains(quitarServicio)) {
                          clienteMod.getServicios().remove(quitarServicio);
                          clientesDAO.update(clienteMod);
                          servicioDAO.update(quitarServicio);
                        } else {
                          System.out.println("El cliente no posee dicho servicio");
                        }
                        System.out.print("Ingrese ID servicio(0:terminar): ");
                        idQuitarServicio = in.nextInt();
                        if (idQuitarServicio == 0) {
                          continuarQuitar = false;
                        }
                      }

                      break;
                  }
                }
                tx.commit();
              }
              break;

            }
          }
          break;

          case "Mesa de Ayuda":
           boolean continuarMesaAyuda=true;

            while (continuarMesaAyuda) {
              System.out.println("Bienvenido a Nesa de Ayuda");
              System.out.println("Lista de Acciones");
              System.out.println(
                      "1- Nuevo Reporte\n" +
                              "2- Consultar Reporte\n" +
                              "3- Modificar Reporte\n" +
                              "0- Cerrar Sesion");
              System.out.print("Opcion: ");
              int opcionSecReporte = in.nextInt();


              switch (opcionSecReporte) {
                case 0:
                  iniciarSesion = false;
                  conectar = false;
                  continuarMesaAyuda=false;
                  break;
                case 1: {
                  //Crear Nuevo Reporte
                  tx.begin();
                  System.out.print("Ingrese CUIT: ");
                  int cuit = in.nextInt();

                  Cliente clienteReporte = clientesDAO.getByCUIT(cuit);

                  in.nextLine();
                  Reporte nuevoReporte = new Reporte();

                  System.out.println("Descripcion del problema:");
                  nuevoReporte.setDescripcion(in.nextLine());
                  nuevoReporte.setCliente(clienteReporte);
                  nuevoReporte.setFechaAlta(LocalDateTime.now());

                  System.out.println("Plataforma(Soporte): ");
                  String nombreSoporte = in.nextLine();

                  //Obtenemos lista filtrada por soporte
                  Soporte soporteReporte = soporteDAO.getByNombre(nombreSoporte);
                  List<Incidente> listaIncidentesDisponibles = incidenteDAO.getBySoporte(soporteReporte);

                  System.out.println("ID \t Descripcion");
                  for (Incidente incidente : listaIncidentesDisponibles) {
                    System.out.println(incidente.getId() + "\t  " + incidente.getNombre() + " - " + incidente.getDescripcion());
                  }

                  boolean agregarIncidentesNuevoReporte = true;
                  System.out.print("Id incidente(0:Terminar): ");
                  int incidenteId = in.nextInt();
                  while (agregarIncidentesNuevoReporte) {
                    Incidente incidente = incidenteDAO.getByID(incidenteId);
                    //Creamos el incidenteReporte
                    LocalDateTime fechaResolucionEstimada = nuevoReporte.getFechaAlta().plusHours(incidente.getTiempoMaximo());
                    in.nextLine();
                    System.out.println("Fecha de resolucion estimada: " + fechaResolucionEstimada);
                    System.out.print("Modificar fecha de resolucion estimada?(y/n): ");
                    String respuestaCambiarFecha = in.nextLine();
                    if (respuestaCambiarFecha.equals("y")) {
                      System.out.print("Fecha de resolución estimada (YYYY-MM-DDT): ");
                      String diaResolucionEstimado = in.nextLine();
                      System.out.print("Horario de Resolucion Estimado (HH:mm:ss): ");
                      String horarioResolucionEstimado = in.nextLine();
                      String strFechaResolucionEstimada = diaResolucionEstimado + " " + horarioResolucionEstimado;
                      // Parsea la cadena de fecha a LocalDateTime
                      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                      fechaResolucionEstimada = LocalDateTime.parse(strFechaResolucionEstimada, formatter);
                    }
                    nuevoReporte.setFecha_resolucion_estimada(fechaResolucionEstimada);
                    reporteDAO.save(nuevoReporte);

                    IncidentesReporte incidenteReporte = new IncidentesReporte();

                    nuevoReporte.getIncidentesReporte().add(incidenteReporte);
                    incidenteReporte.setIncidente(incidente);
                    incidenteReporte.setReporte(nuevoReporte);

                    //Asignacion del Tecnico Responsable
                    System.out.println("Lista de tecnicos disponibles");

                    //Filtramos aquellos tecnicos que tengan una especialidad que pueda resolver el incidente
                    List<Tecnico> tecnicosDisponibles = tecnicoDAO.getAll()
                            .stream()
                            .filter(tecnico -> tecnico.getEspecialidades().stream()
                                    .anyMatch(especialidadTecnico -> incidenteReporte.getIncidente().getEspecialidades()
                                            .contains(especialidadTecnico)))
                            .collect(Collectors.toList());


                    for (Tecnico tecnico : tecnicosDisponibles) {
                      System.out.println("ID: " + tecnico.getId() + " -- Nombre: " + tecnico.getNombre() + " " + tecnico.getApellido());
                    }
                    System.out.print(
                            "\n" +
                                    "Ingrese ID Tecnico: ");
                    int tecnicoId = in.nextInt();
                    Tecnico tecnico = tecnicoDAO.getByID(tecnicoId);
                    incidenteReporte.setTecnico(tecnicoDAO.getByID(tecnicoId));
                    tecnico.addIncidenteReporte(incidenteReporte);

                    //Actualizamos ambos registros
                    reporteDAO.update(nuevoReporte);
                    incidentesReporteDAO.update(incidenteReporte);
                    tecnicoDAO.update(tecnico);


                    System.out.print("Id incidente(0:Terminar): ");
                    incidenteId = in.nextInt();
                    if (incidenteId == 0) {
                      agregarIncidentesNuevoReporte = false;
                    }
                  }
                  in.nextLine();
                  if (((Supplier<Boolean>) () -> {
                    System.out.println("Desea agregar horas extras?(y/n): ");
                    String resp = in.nextLine();
                    return resp.equals("y");
                  }).get()) {
                    System.out.print("Horas extras estimadas: ");
                    int colchonHoras = in.nextInt();
                    nuevoReporte.setColchonHoras(colchonHoras);
                    reporteDAO.update(nuevoReporte);

                    //Agregar aviso a Tecnico

                  }
                  tx.commit();
                  em.refresh(nuevoReporte);

                }break;

                case 2:
                  tx.begin();
                  System.out.print("Ingrese ID Reporte: ");
                  int idReporteBuscado = in.nextInt();

                  Reporte reporte = reporteDAO.getByID(idReporteBuscado);

                  System.out.println(
                          "Cliente: " + reporte.getCliente().getRazonSocial() + "\n" +
                                  "Descripcion: " + reporte.getDescripcion() + "\n" +
                                  "Fecha de alta: " + reporte.getFechaAlta() + "\n" +
                                  ((Supplier<String>) () -> (reporte.getResuelto() == 1)
                                          ? "Fecha de Resolucion: " + reporte.getFecha_resolucion()
                                          : "Fecha Estimada de resolucion: " + reporte.getFecha_resolucion_estimada()).get() + "\n"
                  );
                  System.out.println("");
                  System.out.println("Incidentes asociados\n");
                  for (IncidentesReporte incidentesReporte : reporte.getIncidentesReporte()) {
                    System.out.println("\n" +
                            "Incidente: " + incidentesReporte.getIncidente().getNombre() + " " + incidentesReporte.getIncidente().getDescripcion() + "\n" +
                            "Tecnico:   " + incidentesReporte.getTecnico().getNombre() + " " + incidentesReporte.getTecnico().getApellido() + "\n" +
                            "Estado:    " + incidentesReporte.getDescripcion() + "\n" +
                            ((Supplier<String>) () -> (incidentesReporte.getResuelto() == 1
                                    ? "Fecha de Resolucion: " + incidentesReporte.getResolucion().toString()
                                    : "Fecha Estimada de Resolucion: " + reporte.getFecha_resolucion_estimada().toString())).get() + "\n"
                    );
                  }

                  in.nextLine();
                  System.out.println("Presione enter para continuar");
                  String enter = in.nextLine();
                  tx.commit();
                  break;

                case 3:

                  boolean continarSecMod = true;
                  System.out.print("ID reporte: ");
                  int idSelReporte = in.nextInt();

                  tx.begin();
                  while (continarSecMod) {

                    Reporte reporteMod = reporteDAO.getByID(idSelReporte);


                    System.out.println("Lista de Acciones");
                    System.out.println(
                            "1- Ver Reporte\n" +
                            "2- Agregar Incidentes\n" +
                            "3- Modificar Incidentes\n" +
                            "0- Terminar");

                    System.out.print("Opcion: ");
                    int opcionSecModReporte = in.nextInt();

                    switch (opcionSecModReporte) {
                      case 0:
                        continarSecMod = false;
                        continuarMesaAyuda=false;
                        break;

                      case 1:

                        reporteMod = reporteDAO.getByID(reporteMod.getId());

                        System.out.println("");
                        System.out.println("----------------------");
                        System.out.println("");
                        System.out.println(
                                "ID Reporte: " + reporteMod.getId() + "\n" +
                                        "Cliente:    " + reporteMod.getCliente().getRazonSocial() + "\n" +
                                        "Fecha Alta: " + reporteMod.getFechaAlta().toString() + "\n" +
                                        "Estado:     " +  ((reporteMod.getResuelto() == 1)
                                        ? "Resuelto\n"
                                        + "Fecha de Resolucion: " + reporteMod.getFecha_resolucion().toString()
                                        : "Sin Resolver\n" + "Fecha de Resolucion Estimada: " + reporteMod.getFecha_resolucion_estimada().toString()));

                        System.out.println("");
                        System.out.println("Lista de Incidentes Asociados\n");
                        List<IncidentesReporte> listaDeIncidentesAsociados = reporteMod.getIncidentesReporte();

                        for (IncidentesReporte incidente : listaDeIncidentesAsociados) {
                          System.out.println(
                                  "ID:        " + incidente.getId() + "\n" +
                                          "Incidente: " + incidente.getIncidente().getNombre() + " - " + incidente.getIncidente().getDescripcion() + "\n" +
                                          "Tecnico:   " + incidente.getTecnico().getNombre() +" "+incidente.getTecnico().getApellido() + "\n" +
                                          "Estado:    " + incidente.getDescripcion() + "\n" +
                                          ((incidente.getResuelto() == 1
                                                  ? "Fecha de resolucion: " + incidente.getResolucion().toString()
                                                  : "")) + "\n");
                        }

                        in.nextLine();
                        System.out.println("Presione Enter para continuar");
                        enter = in.nextLine();

                        break;
                      case 2:
                        if (reporteMod.equals(new Reporte())) {
                          System.out.println("Debe seleccionar un reporte");
                        } else {

                          List<Incidente> incidentesRelacionados = incidenteDAO.getBySoporte(reporteMod.getIncidentesReporte().get(0).getIncidente().getSoporte());

                          for (Incidente incidente : incidentesRelacionados) {
                            System.out.println(
                                    "ID: " + incidente.getId() + "\n" +
                                            "Incidente: " + incidente.getNombre() + " - " + incidente.getDescripcion() + "\n");
                          }

                          System.out.println("");

                          //Bucle para agregar incidentes
                          boolean agregarIncidentes = true;
                          System.out.print("Ingrese ID(0:terminar): ");
                          int idIncAgregar = in.nextInt();

                          while (agregarIncidentes) {
                            Incidente incidente = incidenteDAO.getByID(idIncAgregar);

                            //Creamos el incidenteReporte
                            LocalDateTime fechaResolucionEstimada = reporteMod.getFechaAlta().plusHours(incidente.getTiempoMaximo());
                            in.nextLine();
                            System.out.println("Fecha de resolucion estimada: " + fechaResolucionEstimada);
                            System.out.print("Modificar fecha de resolucion estimada?(y/n): ");
                            String respuestaCambiarFecha = in.nextLine();

                            if (respuestaCambiarFecha.equals("y")) {
                              System.out.print("Fecha de resolución estimada (YYYY-MM-DDT): ");
                              String diaResolucionEstimado = in.nextLine();
                              System.out.print("Horario de Resolucion Estimado (HH:mm:ss): ");
                              String horarioResolucionEstimado = in.nextLine();
                              String strFechaResolucionEstimada = diaResolucionEstimado + " " + horarioResolucionEstimado;
                              // Parsea la cadena de fecha a LocalDateTime
                              DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                              fechaResolucionEstimada = LocalDateTime.parse(strFechaResolucionEstimada, formatter);
                            }
                            reporteMod.setFecha_resolucion_estimada(fechaResolucionEstimada);
                            reporteDAO.update(reporteMod);

                            IncidentesReporte incidenteReporte = new IncidentesReporte();

                            incidenteReporte.setIncidente(incidente);
                            reporteMod.getIncidentesReporte().add(incidenteReporte);
                            incidenteReporte.setReporte(reporteMod);

                            //Asignacion del Tecnico Responsable
                            System.out.println("Lista de tecnicos disponibles");

                            //Filtramos aquellos tecnicos que tengan una especialidad que pueda resolver el incidente
                            List<Tecnico> tecnicosDisponibles = tecnicoDAO.getAll()
                                    .stream()
                                    .filter(tecnico -> tecnico.getEspecialidades().stream()
                                            .anyMatch(especialidadTecnico -> incidenteReporte.getIncidente().getEspecialidades()
                                                    .contains(especialidadTecnico)))
                                    .collect(Collectors.toList());


                            for (Tecnico tecnico : tecnicosDisponibles) {
                              System.out.println("ID: " + tecnico.getId() + " -- Nombre: " + tecnico.getNombre() + " " + tecnico.getApellido());
                            }
                            System.out.print(
                                    "\n" +
                                            "Ingrese ID Tecnico: ");
                            int tecnicoId = in.nextInt();
                            incidenteReporte.setTecnico(tecnicoDAO.getByID(tecnicoId));
                            incidenteReporte.setDescripcion("En espera");

                            reporteMod.addIncidente(incidenteReporte);

                            //Actualizamos ambos registros
                            incidentesReporteDAO.update(incidenteReporte);
                            reporteDAO.update(reporteMod);
                            System.out.print("Id incidente(0:Terminar): ");
                            int incidenteId = in.nextInt();
                            if (incidenteId == 0) {
                              agregarIncidentes = false;
                            }
                          }

                          //Actualiza el objeto en la cache
                          em.refresh(reporteMod);

                          //Avisar Tecnico

                          continarSecMod = false;

                        }
                        break;

                      case 3: {
                        tx.commit();


                        //Modificar Incidetes
                        System.out.println(
                                "1- Marcar como Resuelto" + "\n" +
                                        "2- Modificar Tecnico Responsable" + "\n" +
                                        "3- Modificar Estado" + "\n" +
                                        "4- Eliminar Incidente" + "\n"+
                                        "0- Terminar");

                        System.out.println("Opcion: ");
                        int opcionModIncidenteReporte = in.nextInt();
                        while (opcionModIncidenteReporte!=0) {
                          switch (opcionModIncidenteReporte) {
                            case 1: {
                              //Marcar icnidente como resuelto
                              tx.begin();
                              System.out.println("Seleccione ID incidente(0:Terminar): ");
                              int idIncidenteMod = in.nextInt();
                              IncidentesReporte incidente = new IncidentesReporte();

                              while (idIncidenteMod != 0) {//Seteamos atributos
                                incidente = reporteMod.getIncidenteReporteByID(idIncidenteMod);
                                incidente.setResuelto(1);
                                incidente.setDescripcion("Resuelto");
                                incidente.setResolucion(LocalDateTime.now());

                                System.out.println("Seleccione ID incidente(0:Terminar): ");
                                idIncidenteMod = in.nextInt();

                              }

                              //Actualizamos datos
                              incidentesReporteDAO.update(incidente);
                              reporteDAO.update(reporteMod);
                              tx.commit();
                              em.refresh(reporteMod);
                              em.refresh(incidente);
                            }
                            break;

                            case 2: {
                              //Reasignacion de Tecnico
                              tx.begin();
                              System.out.println("Seleccione ID incidente: ");
                              int idIncidenteMod = in.nextInt();

                              IncidentesReporte incidentesReporte = reporteMod.getIncidenteReporteByID(idIncidenteMod);

                              //Asignacion del Tecnico Responsable
                              System.out.println("Lista de tecnicos disponibles");

                              //Filtramos aquellos tecnicos que tengan una especialidad que pueda resolver el incidente
                              List<Tecnico> tecnicosDisponibles = tecnicoDAO.getAll()
                                      .stream()
                                      .filter(tecnico -> tecnico.getEspecialidades().stream()
                                              .anyMatch(especialidadTecnico -> incidentesReporte.getIncidente().getEspecialidades()
                                                      .contains(especialidadTecnico)))
                                      .collect(Collectors.toList());


                              for (Tecnico tecnico : tecnicosDisponibles) {
                                System.out.println("ID: " + tecnico.getId() + " -- Nombre: " + tecnico.getNombre() + " " + tecnico.getApellido());
                              }
                              System.out.print(
                                      "\n" +
                                              "Ingrese ID Tecnico: ");
                              int tecnicoId = in.nextInt();
                              incidentesReporte.setTecnico(tecnicoDAO.getByID(tecnicoId));

                              incidentesReporteDAO.update(incidentesReporte);
                              reporteDAO.update(reporteMod);
                              tx.commit();

                              em.refresh(incidentesReporte);
                              em.refresh(reporteMod);
                            }
                            break;

                            case 3: {
                              //Modificar Estado
                              tx.begin();
                              System.out.println("Seleccione ID incidente: ");
                              int idIncidenteMod = in.nextInt();
                              IncidentesReporte incidente = reporteMod.getIncidenteReporteByID(idIncidenteMod);
                              in.nextLine();
                              System.out.println("Nuevo Estado: ");
                              String nuevoEstado = in.nextLine();

                              incidente.setDescripcion(nuevoEstado);

                              incidentesReporteDAO.update(incidente);
                              reporteDAO.update(reporteMod);
                              tx.commit();
                              em.refresh(incidente);
                              em.refresh(reporteMod);
                            }
                            break;

                            case 4: {
                              //Eliminar Incidente
                              tx.begin();
                              System.out.println("Seleccione ID incidente: ");
                              int idIncidenteMod = in.nextInt();
                              IncidentesReporte incidente = reporteMod.getIncidenteReporteByID(idIncidenteMod);

                              reporteMod.getIncidentesReporte().remove(incidente);
                              incidentesReporteDAO.delete(incidente);

                              reporteDAO.update(reporteMod);
                              tx.commit();
                              em.refresh(reporteMod);
                            }
                            break;
                          }

                          System.out.println(
                                    "1- Marcar como Resuelto" + "\n" +
                                            "2- Modificar Tecnico Responsable" + "\n" +
                                            "3- Modificar Estado" + "\n" +
                                            "4- Eliminar Incidente" + "\n"+
                                            "0- Terminar");

                          System.out.println("Opcion: ");
                          opcionModIncidenteReporte = in.nextInt();
                        }
                        tx.begin();
                      }break;
                    }
                  }
                  tx.commit();
                  break;
              }
            }
          break;
        }
      }

      //Comprobacion de inicio de sesion
      System.out.println("0- Cerrar Programa    1- Iniciar Sesion");
      accion = in.nextInt();
      System.out.println("");
      while (accion != 0 && accion != 1) {
        System.out.println("Opcion incorrecta");

        System.out.println("0- Cerrar Programa    1- Iniciar Sesion");
        accion = in.nextInt();
        System.out.println("");
      }
      if (accion == 0) {
        System.exit(1);
      } else {
        conectar=true;
      }
    }
  }
}
