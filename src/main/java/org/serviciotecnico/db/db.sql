SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema servicio_tecnico
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema servicio_tecnico
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `servicio_tecnico` DEFAULT CHARACTER SET utf8 ;
USE `servicio_tecnico` ;

-- Table `servicio_tecnico`.`Areas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `servicio_tecnico`.`Areas` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `descripcion` VARCHAR(255) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `servicio_tecnico`.`Empleados`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `servicio_tecnico`.`Empleados` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(28) NOT NULL,
  `apellido` VARCHAR(18) NOT NULL,
  `CUIL_CUIT` INT NOT NULL,
  `Areas_id` INT NOT NULL,
  PRIMARY KEY (`id`, `Areas_id`),
    CONSTRAINT `fk_Empleados_Areas`
    FOREIGN KEY (`Areas_id`)
    REFERENCES `servicio_tecnico`.`Areas` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `servicio_tecnico`.`Soporte`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `servicio_tecnico`.`Soporte` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NULL,
  `tipo` VARCHAR(5) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `servicio_tecnico`.`Incidentes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `servicio_tecnico`.`Incidentes` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(80) NOT NULL,
  `descripcion` VARCHAR(255) NULL,
  `id_soporte` INT NOT NULL,
  `tiempo_maximo` INT NULL,
  PRIMARY KEY (`id`),
  
  CONSTRAINT `fk_Incidentes_Soporte1`
    FOREIGN KEY (`id_soporte`)
    REFERENCES `servicio_tecnico`.`Soporte` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `servicio_tecnico`.`Tecnicos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `servicio_tecnico`.`Tecnicos` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(28) NOT NULL,
  `apellido` VARCHAR(18) NOT NULL,
  `CUIL_CUIT` INT NOT NULL,
  `id_empleado_alta` INT NOT NULL,
  `id_empleado_modificacion` INT NOT NULL,
  PRIMARY KEY (`id`, `id_empleado_modificacion`, `id_empleado_alta`),
  
  CONSTRAINT `fk_Tecnicos_Empleados1`
    FOREIGN KEY (`id_empleado_alta`)
    REFERENCES `servicio_tecnico`.`Empleados` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Tecnicos_Empleados2`
    FOREIGN KEY (`id_empleado_modificacion`)
    REFERENCES `servicio_tecnico`.`Empleados` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `servicio_tecnico`.`Especialidad`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `servicio_tecnico`.`Especialidad` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `descripcion` VARCHAR(255) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `servicio_tecnico`.`Tecnicos_x_Especialidades`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `servicio_tecnico`.`Tecnicos_x_Especialidades` (
  `id_tecnico` INT NOT NULL,
  `id_especialidad` INT NOT NULL,
  PRIMARY KEY (`id_tecnico`, `id_especialidad`),
  CONSTRAINT `fk_Tecnicos_has_Especialidades_Tecnicos1`
    FOREIGN KEY (`id_tecnico`)
    REFERENCES `servicio_tecnico`.`Tecnicos` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Tecnicos_has_Especialidades_Especialidades1`
    FOREIGN KEY (`id_especialidad`)
    REFERENCES `servicio_tecnico`.`Especialidad` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `servicio_tecnico`.`Incidentes_x_Especialidad`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `servicio_tecnico`.`Incidentes_x_Especialidad` (
  `id_incidentes` INT NOT NULL,
  `id_especialidad` INT NOT NULL,
  PRIMARY KEY (`id_incidentes`, `id_especialidad`),
  CONSTRAINT `fk_Incidentes_has_Especialidades_Incidentes1`
    FOREIGN KEY (`id_incidentes`)
    REFERENCES `servicio_tecnico`.`Incidentes` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Incidentes_has_Especialidades_Especialidades1`
    FOREIGN KEY (`id_especialidad`)
    REFERENCES `servicio_tecnico`.`Especialidad` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `servicio_tecnico`.`Clientes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `servicio_tecnico`.`Clientes` (
  `id` integer NOT NULL AUTO_INCREMENT,
  `razon_social` VARCHAR(45) NOT NULL,
  `CUIT` int NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `servicio_tecnico`.`Servicios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `servicio_tecnico`.`Servicios` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NULL,
  `id_soporte` INT NOT NULL,
  PRIMARY KEY (`id`, `id_soporte`),
  
  CONSTRAINT `fk_Servicios_Soporte1`
    FOREIGN KEY (`id_soporte`)
    REFERENCES `servicio_tecnico`.`Soporte` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `servicio_tecnico`.`Clientes_x_Servicios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `servicio_tecnico`.`Clientes_x_Servicios` (
  `Servicios_id` INT NOT NULL,
  `Clientes_id` INT NOT NULL,
  PRIMARY KEY (`Servicios_id`, `Clientes_id`),
 CONSTRAINT `fk_Servicios_has_Clientes_Servicios1`
    FOREIGN KEY (`Servicios_id` )
    REFERENCES `servicio_tecnico`.`Servicios` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Servicios_has_Clientes_Clientes1`
    FOREIGN KEY (`Clientes_id`)
    REFERENCES `servicio_tecnico`.`Clientes` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `servicio_tecnico`.`Reportes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `servicio_tecnico`.`Reportes` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `descripcion` VARCHAR(45) NOT NULL,
  `fecha_alta` DATETIME NOT NULL,
  `fecha_resolucion_estimada` DATETIME NOT NULL,
  `fecha_resolucion` DATETIME NULL,
  `colchon_horas` INT NULL,
  `resuelto` BIT(1) NULL,
  `observaciones` VARCHAR(255) NULL,
  `Clientes_id` INT NOT NULL,
  PRIMARY KEY (`id`, `Clientes_id`),
 
  CONSTRAINT `fk_Reportes_Clientes1`
    FOREIGN KEY (`Clientes_id`)
    REFERENCES `servicio_tecnico`.`Clientes` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `servicio_tecnico`.`Incidentes_x_Reportes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `servicio_tecnico`.`Incidentes_x_Reportes` (
  `id` int not null Auto_increment,
  `id_reporte` INT NOT NULL,
  `id_incidente` INT NOT NULL,
  `id_tecnico` INT NOT NULL,
  `descripcion` VARCHAR(140) NULL,
  `resuelto` BINARY NULL,
  `fecha_resolucion` DATETIME NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_Reportes_has_Incidentes_Reportes1`
    FOREIGN KEY (`id_reporte`)
    REFERENCES `servicio_tecnico`.`Reportes` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Reportes_has_Incidentes_Incidentes1`
    FOREIGN KEY (`id_incidente`)
    REFERENCES `servicio_tecnico`.`Incidentes` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Incidentes_x_Reportes_Tecnicos1`
    FOREIGN KEY (`id_tecnico`)
    REFERENCES `servicio_tecnico`.`Tecnicos` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


-- Insertar más datos en la tabla 'Areas'
INSERT INTO `Areas` (`nombre`, `descripcion`) VALUES
  ('RRHH', 'Recursos Humanos'),
  ('Operador', 'Departamento Técnico'),
  ('Comercial', 'Área Comercial'),
  ('Mesa de Ayuda', 'Mesa de Ayuda');

-- Insertar más datos en la tabla 'Empleados'
INSERT INTO `Empleados` (`nombre`, `apellido`, `CUIL_CUIT`, `Areas_id`) VALUES
  ('Juan', 'Pérez', 12345678, 1),
  ('Ana', 'Gómez', 23456789, 2),
  ('Carlos', 'López', 34567890, 3),
  ('Laura', 'Martínez', 45678901, 4),
  ('Diego', 'Fernández', 56789012, 1),
  ('María', 'Rodríguez', 67890123, 2),
  ('Javier', 'Gutiérrez', 78901234, 3),
  ('Carmen', 'Sánchez', 89012345, 4),
  ('Raúl', 'Díaz', 90123456, 1),
  ('Elena', 'Romero', 12340987, 2);

-- Insertar más datos en la tabla 'Soporte'
INSERT INTO `Soporte` (`nombre`, `tipo`) VALUES
  ('Windows', 'SO'),
  ('Linux', 'SO'),
  ('MacOS', 'SO'),
  ('Android', 'SO'),
  ('GestionAPP', 'APP'),
  ('EComerce', 'APP'),
  ('BlogAPP', 'APP');

-- Insertar más datos en la tabla 'Incidentes'
INSERT INTO `Incidentes` (`nombre`, `descripcion`, `id_soporte`, `tiempo_maximo`) VALUES
  ('Error de conexión', 'Problema con la conexión a Internet', 1, 4),
  ('Problema de rendimiento', 'La aplicación se ejecuta lentamente', 2, 8),
  ('Fallo crítico', 'Error grave en el sistema', 3, 12),
  ('Problema de seguridad', 'Vulnerabilidad detectada en el software', 4, 6),
  ('Fallo en la instalación', 'Error al instalar el programa', 5, 10),
  ('Error de correo electrónico', 'Problema con el envío/recepción de correos', 6, 6),
  ('Problema con la aplicación móvil', 'Funciones no disponibles en la app', 7, 8),
  ('Error en el sitio web', 'El sitio web no carga correctamente', 1, 8),
  ('Problema con actualización', 'Error al actualizar el software', 2, 10),
  ('Fallo en el sistema de informes', 'Problema con la generación de informes', 3, 12);

-- Insertar más datos en la tabla 'Especialidad'
INSERT INTO `Especialidad` (`nombre`, `descripcion`) VALUES
  ('Redes', 'Especialidad en redes y conectividad'),
  ('Desarrollo', 'Desarrollo de software'),
  ('Seguridad', 'Seguridad informática'),
  ('Instalación', 'Instalación y configuración de software'),
  ('Correo', 'Gestión de correo electrónico'),
  ('Móviles', 'Soporte para aplicaciones móviles'),
  ('Web', 'Desarrollo y mantenimiento de sitios web'),
  ('Actualización', 'Actualización de software'),
  ('Informe', 'Generación de informes y estadísticas'),
  ('Soporte Técnico', 'Asistencia técnica general');

-- Insertar datos en la tabla 'Tecnicos'
INSERT INTO `Tecnicos` (`nombre`, `apellido`, `CUIL_CUIT`, `id_empleado_alta`, `id_empleado_modificacion`)
VALUES
  ('Juan', 'Gómez', 12345678, 1, 1),
  ('María', 'López', 23456789, 1, 5),
  ('Carlos', 'Martínez', 34567890, 5, 9),
  ('Laura', 'Rodríguez', 45678901, 9, 9),
  ('Pedro', 'Fernández', 56789012, 5, 5),
  ('Ana', 'Sánchez', 67890123, 5, 1),
  ('Miguel', 'Díaz', 78901234, 1, 1),
  ('Lucía', 'Gutiérrez', 89012345, 5, 5),
  ('Fernando', 'Ramírez', 90123456, 9, 9),
  ('Sofía', 'Pérez', 12340123, 1, 1);

-- Insertar datos en la tabla 'Tecnicos_x_Especialidades'
INSERT INTO `Tecnicos_x_Especialidades` (`id_tecnico`, `id_especialidad`)
VALUES
  (1, 1),
  (1, 2),
  (2, 3),
  (2, 4),
  (3, 5),
  (3, 6),
  (4, 7),
  (4, 8),
  (5, 9),
  (5, 10),
  (6, 1),
  (6, 2),
  (7, 3),
  (7, 4),
  (8, 5),
  (8, 6),
  (9, 7),
  (9, 8),
  (10, 9),
  (10, 10);

-- Insertar más datos en la tabla 'Incidentes_x_Especialidad'
INSERT INTO `Incidentes_x_Especialidad` (`id_incidentes`, `id_especialidad`) VALUES
  (1, 1),
  (2, 2),
  (3, 3),
  (4, 4),
  (5, 5),
  (6, 6),
  (7, 7),
  (8, 8),
  (9, 9),
  (10, 10);

-- Insertar datos en la tabla 'Clientes'
INSERT INTO `Clientes` (`razon_social`, `CUIT`)
VALUES
  ('Tech Solutions S.A.', 1234567890),
  ('Innovate Tech Group S.R.L.', 235678901),
  ('Digital Dynamics C.A.', 345789012),
  ('Epic Systems S.A.', 456789013),
  ('Data Nexus S.R.L.', 567890123),
  ('Global Innovations C.A.', 678901234),
  ('InfoTech Solutions S.A.', 789012346),
  ('Future Systems S.R.L.', 890123456),
  ('Precision Technologies C.A.', 902345679),
  ('Smart Solutions S.A.', 1234012456);

-- Insertar datos en la tabla 'Servicios'
INSERT INTO `Servicios` ( `nombre`, `id_soporte`)
VALUES
  ('Soporte Instalación Windows', 1),
  ('Soporte Configuración Windows', 1),
  ('Soporte Solución de Problemas Windows', 1),

  ('Soporte Instalación Linux', 2),
  ('Soporte Configuración Linux', 2),
  ('Soporte Solución de Problemas Linux', 2),

  ('Soporte Instalación MacOS', 3),
  ('Soporte Configuración MacOS', 3),
  ('Soporte Solución de Problemas MacOS', 3),

  ('Soporte Instalación Android', 4),
  ('Soporte Configuración Android', 4),
  ('Soporte Solución de Problemas Android', 4),

  ('Gestión de Aplicaciones Empresariales', 5),
  ('Soporte Desarrollo E-Commerce', 6),
  ('Soporte Blog APP Personalizado', 7),

  ('Soporte General de Sistemas', 7),
  ('Mantenimiento Preventivo General', 7),
  ('Servicio Consultoría Tecnológica', 7);

-- Insertar datos en la tabla 'Clientes_x_Servicios'
INSERT INTO `Clientes_x_Servicios` (`Servicios_id`, `Clientes_id`)
VALUES
  (1, 1),  -- Cliente 1 contrata Soporte Instalación Windows
  (2, 2),  -- Cliente 2 contrata Soporte Configuración Windows
  (3, 3),  -- Cliente 3 contrata Soporte Solución de Problemas Windows

  (4, 4),  -- Cliente 4 contrata Soporte Instalación Linux
  (5, 5),  -- Cliente 5 contrata Soporte Configuración Linux
  (6, 6),  -- Cliente 6 contrata Soporte Solución de Problemas Linux

  (7, 7),  -- Cliente 7 contrata Soporte Instalación MacOS
  (8,  8),  -- Cliente 8 contrata Soporte Configuración MacOS
  (9, 9),  -- Cliente 9 contrata Soporte Solución de Problemas MacOS

  (10, 10),  -- Cliente 10 contrata Soporte Instalación Android
  (11, 1),   -- Cliente 1 contrata Soporte Configuración Android
  (12, 2),   -- Cliente 2 contrata Soporte Solución de Problemas Android

  (13, 3),   -- Cliente 3 contrata Gestión de Aplicaciones Empresariales
  (14, 4),   -- Cliente 4 contrata Soporte Desarrollo E-Commerce
  (15, 5),   -- Cliente 5 contrata Soporte Blog APP Personalizado

  (16, 6),   -- Cliente 6 contrata Soporte General de Sistemas
  (17, 7),   -- Cliente 7 contrata Mantenimiento Preventivo General
  (18, 8);   -- Cliente 8 contrata Servicio Consultoría Tecnológica

-- Insertar datos en la tabla 'Reportes'
INSERT INTO `Reportes` (`descripcion`, `fecha_alta`, `fecha_resolucion_estimada`, `fecha_resolucion`, `colchon_horas`, `resuelto`, `observaciones`, `Clientes_id`)
VALUES
  ('Problemas con Windows', '2023-11-26 10:00:00', '2023-11-30 12:00:00', '2023-11-29 11:30:00', 5, 1, 'Problema resuelto satisfactoriamente.', 1),
  ('Configuración Linux', '2023-11-26 11:30:00', '2023-12-01 14:00:00', '2023-11-30 15:45:00', 3, 1, 'Configuración aplicada con éxito.', 2),
  ('Error en MacOS', '2023-11-27 09:45:00', '2023-12-02 11:30:00', '2023-12-01 10:15:00', 8, 1, 'Problema de software resuelto.', 3),
  ('Problemas Android', '2023-11-28 08:15:00', '2023-12-03 10:30:00', '2023-12-02 09:00:00', 6, 1, 'Actualización realizada con éxito.', 4),
  ('Gestión de Aplicaciones', '2023-11-28 14:20:00', '2023-12-04 16:45:00', '2023-12-03 15:30:00', 4, 1, 'Aplicaciones gestionadas correctamente.', 5),
  ('Desarrollo E-Commerce', '2023-11-29 10:45:00', '2023-12-05 13:15:00', '2023-12-04 12:00:00', 7, 1, 'Desarrollo exitoso.', 6),
  ('Soporte Blog APP', '2023-11-29 16:30:00', '2023-12-06 09:00:00', '2023-12-05 08:45:00', 9, 1, 'Problema de soporte resuelto.', 7),
  ('Problemas Generales', '2023-11-30 12:45:00', '2023-12-07 15:30:00', '2023-12-06 14:15:00', 2, 1, 'Problema general resuelto.', 8),
  ('Mantenimiento Preventivo', '2023-11-30 14:00:00', '2023-12-08 16:15:00', '2023-12-07 15:00:00', 4, 1, 'Mantenimiento completo.', 9),
  ('Consulta Tecnológica', '2023-12-01 09:30:00', '2023-12-09 11:45:00', '2023-12-08 10:30:00', 6, 1, 'Consulta satisfactoria.', 10),

  -- Agregar 30 reportes adicionales (hasta alcanzar un total de 40)
  ('Problema de Red', '2023-12-02 08:00:00', '2023-12-10 10:30:00', NULL, NULL, 0, 'En espera de resolución.', 1),
  ('Error de Software', '2023-12-02 12:15:00', '2023-12-11 14:45:00', NULL, NULL, 0, 'En espera de resolución.', 2),
  ('Problema con Aplicación Móvil', '2023-12-03 10:30:00', '2023-12-12 12:45:00', NULL, NULL, 0, 'En espera de resolución.', 3),
  ('Configuración de Sistema', '2023-12-03 15:00:00', '2023-12-13 09:30:00', NULL, NULL, 0, 'En espera de resolución.', 4),
  ('Incidente de Seguridad', '2023-12-04 11:45:00', '2023-12-14 15:15:00', NULL, NULL, 0, 'En espera de resolución.', 5),
  ('Soporte Técnico Especializado', '2023-12-04 14:30:00', '2023-12-15 16:30:00', NULL, NULL, 0, 'En espera de resolución.', 6),
  ('Consulta de Hardware', '2023-12-05 09:00:00', '2023-12-16 11:00:00', NULL, NULL, 0, 'En espera de resolución.', 7),
  ('Problema de Conectividad', '2023-12-05 13:30:00', '2023-12-17 14:45:00', NULL, NULL, 0, 'En espera de resolución.', 8),
  ('Actualización de Software', '2023-12-06 10:15:00', '2023-12-18 12:30:00', NULL, NULL, 0, 'En espera de resolución.', 9),
  ('Soporte en Línea', '2023-12-06 15:30:00', '2023-12-19 16:45:00', NULL, NULL, 0, 'En espera de resolución.', 10),
  ('Problema de Rendimiento', '2023-12-07 11:00:00', '2023-12-20 09:15:00', NULL, NULL, 0, 'En espera de resolución.', 1),
  ('Asistencia Remota', '2023-12-07 14:45:00', '2023-12-21 11:30:00', NULL, NULL, 0, 'En espera de resolución.', 2),
  ('Configuración de Redes', '2023-12-08 10:30:00', '2023-12-22 14:15:00', NULL, NULL, 0, 'En espera de resolución.', 3),
  ('Problema de Seguridad Informática', '2023-12-08 13:15:00', '2023-12-23 16:30:00', NULL, NULL, 0, 'En espera de resolución.', 4),
  ('Soporte para Aplicación Web', '2023-12-09 08:30:00', '2023-12-24 09:45:00', NULL, NULL, 0, 'En espera de resolución.', 5),
  ('Consulta de Software', '2023-12-09 12:45:00', '2023-12-25 12:00:00', NULL, NULL, 0, 'En espera de resolución.', 6),
  ('Problema con Dispositivo Móvil', '2023-12-10 09:15:00', '2023-12-26 14:30:00', NULL, NULL, 0, 'En espera de resolución.', 7),
  ('Configuración de Dispositivos', '2023-12-10 13:30:00', '2023-12-27 15:45:00', NULL, NULL, 0, 'En espera de resolución.', 8),
  ('Asesoramiento Tecnológico', '2023-12-11 10:00:00', '2023-12-28 10:15:00', NULL, NULL, 0, 'En espera de resolución.', 9),
  ('Problema de Hardware', '2023-12-11 14:15:00', '2023-12-29 11:30:00', NULL, NULL, 0, 'En espera de resolución.', 10),
  ('Soporte para Dispositivos Móviles', '2023-12-12 11:30:00', '2023-12-30 14:45:00', NULL, NULL, 0, 'En espera de resolución.', 1),
  ('Configuración de Software', '2023-12-12 14:45:00', '2023-12-31 16:00:00', NULL, NULL, 0, 'En espera de resolución.', 2),
  ('Problema de Compatibilidad', '2023-12-13 09:30:00', '2024-01-01 09:45:00', NULL, NULL, 0, 'En espera de resolución.', 3),
  ('Mantenimiento de Equipos', '2023-12-13 12:45:00', '2024-01-02 12:00:00', NULL, NULL, 0, 'En espera de resolución.', 4),
  ('Consulta de Redes', '2023-12-14 10:15:00', '2024-01-03 13:15:00', NULL, NULL, 0, 'En espera de resolución.', 5),
  ('Problema de Actualización', '2023-12-14 13:30:00', '2024-01-04 14:45:00', NULL, NULL, 0, 'En espera de resolución.', 6),
  ('Soporte Técnico Especializado', '2023-12-15 09:00:00', '2024-01-05 16:00:00', NULL, NULL, 0, 'En espera de resolución.', 7),
  ('Configuración de Seguridad', '2023-12-15 12:15:00', '2024-01-06 09:30:00', NULL, NULL, 0, 'En espera de resolución.', 8),
  ('Asistencia en Línea', '2023-12-16 08:30:00', '2024-01-07 11:45:00', NULL, NULL, 0, 'En espera de resolución.', 9),
  ('Problema de Hardware', '2023-12-16 11:45:00', '2024-01-08 14:00:00', NULL, NULL, 0, 'En espera de resolución.', 10);

-- Insertar datos en la tabla 'Incidentes_x_Reportes'
INSERT INTO `Incidentes_x_Reportes` (`id_reporte`, `id_incidente`, `id_tecnico`, `descripcion`, `resuelto`, `fecha_resolucion`)
VALUES
  -- Reporte 1 con 2 incidentes
  (1, 1, 1, 'Resuelto', 1, '2023-11-26 10:30:00'),
  (1, 2, 2, 'Resuelto', 1, '2023-11-26 11:00:00'),

  -- Reporte 2 con 1 incidente
  (2, 3, 3, 'Resuelto', 1, '2023-11-26 12:00:00'),

  -- Reporte 3 con 2 incidentes
  (3, 4, 4, 'Resuelto', 1, '2023-11-27 10:30:00'),
  (3, 5, 5, 'Resuelto', 1, '2023-11-27 11:00:00'),

  -- Reporte 4 con 1 incidente
  (4, 6, 6, 'Resuelto', 1, '2023-11-27 12:30:00'),

  -- Reporte 5 con 2 incidentes
  (5, 7, 7, 'Resuelto', 1, '2023-11-28 09:00:00'),
  (5, 8, 8, 'Resuelto', 1, '2023-11-28 09:30:00'),

  -- Reporte 6 con 1 incidente
  (6, 9, 9, 'Resuelto', 1, '2023-11-28 10:30:00'),

  -- Reporte 7 con 2 incidentes
  (7, 10, 10, 'Resuelto', 1, '2023-11-29 08:00:00'),
  (7, 1, 1, 'Resuelto', 1, '2023-11-29 08:30:00'),

  -- Reporte 8 con 1 incidente
  (8, 2, 2, 'Resuelto', 1, '2023-11-29 09:30:00'),

  -- Reporte 9 con 2 incidentes
  (9, 3, 3, 'Resuelto', 1, '2023-11-30 08:00:00'),
  (9, 4, 4, 'Resuelto', 1, '2023-11-30 08:30:00'),

  -- Reporte 10 con 1 incidente
  (10, 5, 5, 'Resuelto', 1, '2023-11-30 09:30:00'),

  -- Reportes adicionales con 1 incidente cada uno (hasta alcanzar 40 reportes)
  (11, 6, 6, 'En espera', 0, NULL),
  (12, 7, 7, 'En progreso', 0, NULL),
  (13, 8, 8, 'En espera', 0,  NULL),
  (14, 9, 9, 'En progreso', 0, NULL),
  (15, 10, 10, 'En espera', 0, NULL),
  (16, 1, 1, 'En progreso', 0, NULL),
  (17, 2, 2, 'En espera', 0, NULL),
  (18, 3, 3, 'En progreso', 0, NULL),
  (19, 4, 4, 'En espera', 0, NULL),
  (20, 5, 5, 'En progreso', 0, NULL),
  (21, 6, 6, 'En espera', 0, NULL),
  (22, 7, 7, 'En progreso', 0, NULL),
  (23, 8, 8, 'En espera', 0, NULL),
  (24, 9, 9, 'En progreso', 0, NULL),
  (25, 10, 10, 'En espera', 0, NULL),
  (26, 1, 1, 'En progreso', 0, NULL),
  (27, 2, 2, 'En espera', 0, NULL),
  (28, 3, 3, 'En progreso', 0, NULL),
  (29, 4, 4, 'En espera', 0, NULL),
  (30, 5, 5, 'En progreso', 0, NULL),
  (31, 6, 6, 'En espera', 0, NULL),
  (32, 7, 7, 'En progreso', 0, NULL),
  (33, 8, 8, 'En espera', 0, NULL),
  (34, 9, 9, 'En progreso', 0, NULL),
  (35, 10, 10, 'En espera', 0, NULL),
  (36, 1, 1, 'En progreso', 0, NULL),
  (37, 2, 2, 'En espera', 0, NULL),
  (38, 3, 3, 'En progreso', 0, NULL),
  (39, 4, 4, 'En espera', 0, NULL),
  (40, 5, 5, 'En progreso', 0, NULL);
