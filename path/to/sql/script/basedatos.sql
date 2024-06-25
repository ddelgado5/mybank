/*
 Navicat Premium Data Transfer

 Source Server         : local devsu
 Source Server Type    : MySQL
 Source Server Version : 80028 (8.0.28)
 Source Host           : localhost:3306
 Source Schema         : basedatos

 Target Server Type    : MySQL
 Target Server Version : 80028 (8.0.28)
 File Encoding         : 65001

 Date: 24/06/2024 01:00:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cuentas
-- ----------------------------
DROP TABLE IF EXISTS `cuentas`;
CREATE TABLE `cuentas`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `estado` bit(1) NOT NULL,
  `numero_cueta` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `saldo_inicial` double NOT NULL,
  `tipo_cuenta` enum('AHORROS','CORRIENTE') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `cliente_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKdyn23des50pxq2bptn5xa86n4`(`cliente_id` ASC) USING BTREE,
  CONSTRAINT `FKdyn23des50pxq2bptn5xa86n4` FOREIGN KEY (`cliente_id`) REFERENCES `persona` (`cliente_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for movimientos
-- ----------------------------
DROP TABLE IF EXISTS `movimientos`;
CREATE TABLE `movimientos`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `fecha` datetime(6) NULL DEFAULT NULL,
  `saldo` double NULL DEFAULT NULL,
  `tipo_movimiento` enum('DEBITO','CREDITO') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `valor` double NOT NULL,
  `numero_cuenta` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKo5koqjuegcyto02t8ytlf3y80`(`numero_cuenta` ASC) USING BTREE,
  CONSTRAINT `FKo5koqjuegcyto02t8ytlf3y80` FOREIGN KEY (`numero_cuenta`) REFERENCES `cuentas` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for persona
-- ----------------------------
DROP TABLE IF EXISTS `persona`;
CREATE TABLE `persona`  (
  `dtype` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `cliente_id` int NOT NULL AUTO_INCREMENT,
  `direccion` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `edad` tinyint NOT NULL,
  `genero` enum('MASCULINO','FEMENINO','NO_IDENTIFICA') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `identificacion` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `nombre` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `telefono` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `contrasena` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `estado` bit(1) NULL DEFAULT NULL,
  PRIMARY KEY (`cliente_id`) USING BTREE,
  UNIQUE INDEX `UK_r5vsms84ih2viwd6tatk9o5pq`(`identificacion` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
