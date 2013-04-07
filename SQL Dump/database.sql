/*
SQLyog Ultimate v9.02 
MySQL - 5.5.19 : Database - styles
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`styles` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `styles`;

/*Table structure for table `caja` */

DROP TABLE IF EXISTS `caja`;

CREATE TABLE `caja` (
  `concepto` varchar(50) DEFAULT NULL,
  `debe` decimal(8,2) DEFAULT NULL,
  `haber` decimal(8,2) DEFAULT NULL,
  `fecha` date DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `clientes` */

DROP TABLE IF EXISTS `clientes`;

CREATE TABLE `clientes` (
  `idclientes` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(20) DEFAULT '""',
  `apellido` varchar(20) DEFAULT '""',
  `celular` varchar(20) DEFAULT '""',
  `telefono` varchar(20) DEFAULT '""',
  `direccion` varchar(30) DEFAULT '""',
  `mail` varchar(45) DEFAULT '""',
  `orden` int(2) DEFAULT '0',
  `particular` varchar(30) DEFAULT '""',
  PRIMARY KEY (`idclientes`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

/*Table structure for table `cobradores` */

DROP TABLE IF EXISTS `cobradores`;

CREATE TABLE `cobradores` (
  `idcobradores` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(20) DEFAULT NULL,
  `apellido` varchar(20) DEFAULT NULL,
  `celular` varchar(30) DEFAULT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `direccion` varchar(45) DEFAULT NULL,
  `mail` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`idcobradores`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Table structure for table `cuota` */

DROP TABLE IF EXISTS `cuota`;

CREATE TABLE `cuota` (
  `idcuota` int(11) NOT NULL AUTO_INCREMENT,
  `fecha_caducacion` date DEFAULT NULL,
  `monto_cuota` decimal(8,2) DEFAULT NULL,
  `pagada` tinyint(1) DEFAULT NULL,
  `fk_deuda` int(11) NOT NULL,
  `nrocuota` mediumint(3) NOT NULL,
  PRIMARY KEY (`idcuota`,`fk_deuda`),
  KEY `fk_deuda` (`fk_deuda`),
  CONSTRAINT `fk_deuda` FOREIGN KEY (`fk_deuda`) REFERENCES `deudas` (`iddeudas`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=latin1;

/*Table structure for table `deudas` */

DROP TABLE IF EXISTS `deudas`;

CREATE TABLE `deudas` (
  `iddeudas` int(11) NOT NULL AUTO_INCREMENT,
  `monto_prestamo` decimal(10,2) DEFAULT NULL,
  `cuotas` smallint(6) DEFAULT NULL,
  `fecha_generada` date DEFAULT NULL,
  `fk_cliente` int(11) NOT NULL,
  PRIMARY KEY (`iddeudas`,`fk_cliente`),
  KEY `cliente` (`fk_cliente`),
  CONSTRAINT `cliente` FOREIGN KEY (`fk_cliente`) REFERENCES `clientes` (`idclientes`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=koi8r COLLATE=koi8r_bin;

/*Table structure for table `pago` */

DROP TABLE IF EXISTS `pago`;

CREATE TABLE `pago` (
  `idpago` int(11) NOT NULL AUTO_INCREMENT,
  `monto` decimal(7,2) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `fk_cuota` int(11) DEFAULT NULL,
  `fk_cobrador` int(11) DEFAULT NULL,
  PRIMARY KEY (`idpago`),
  KEY `fk_cuota` (`fk_cuota`),
  KEY `fk_cobrador` (`fk_cobrador`),
  CONSTRAINT `fk_cobrador` FOREIGN KEY (`fk_cobrador`) REFERENCES `cobradores` (`idcobradores`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_cuota` FOREIGN KEY (`fk_cuota`) REFERENCES `cuota` (`idcuota`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=latin1;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
