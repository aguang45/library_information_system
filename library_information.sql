/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost:3306
 Source Schema         : library_information

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 08/06/2023 19:08:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `bid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `bname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `bauthor` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `bstatus` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `deleted` int(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`bid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES ('1', 'Java从入门到放弃', 'Java', '未借出', 0);
INSERT INTO `book` VALUES ('122', '122', '122', '未借出', 0);
INSERT INTO `book` VALUES ('1232', '1232', '1232', '已借出', 0);
INSERT INTO `book` VALUES ('2', 'Java从入门到月入过万', 'Java', '未借出', 0);
INSERT INTO `book` VALUES ('333', '4444', '4444', '已借出', 0);
INSERT INTO `book` VALUES ('6', '6', '6', '未借出', 0);
INSERT INTO `book` VALUES ('8', '8', '8', '已借出', 0);
INSERT INTO `book` VALUES ('asd', 's', 'ss', '已借出', 0);

-- ----------------------------
-- Table structure for borrow
-- ----------------------------
DROP TABLE IF EXISTS `borrow`;
CREATE TABLE `borrow`  (
  `id` bigint(30) NOT NULL AUTO_INCREMENT,
  `uid` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `bid` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `bdate` datetime(0) NOT NULL,
  `rdate` datetime(0) NULL DEFAULT NULL,
  `deleted` int(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `bid`(`bid`) USING BTREE,
  INDEX `uid`(`uid`) USING BTREE,
  CONSTRAINT `bid` FOREIGN KEY (`bid`) REFERENCES `book` (`bid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `uid` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 49 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of borrow
-- ----------------------------
INSERT INTO `borrow` VALUES (38, 'admin', '1', '2023-06-08 18:56:58', '2023-06-08 18:57:22', 0);
INSERT INTO `borrow` VALUES (39, 'admin', '1232', '2023-06-08 18:57:18', NULL, 0);
INSERT INTO `borrow` VALUES (40, '123', '1', '2023-06-08 18:57:50', '2023-06-08 18:57:56', 0);
INSERT INTO `borrow` VALUES (41, '123', '333', '2023-06-08 18:57:52', NULL, 0);
INSERT INTO `borrow` VALUES (42, 'user', '1', '2023-06-08 18:58:09', '2023-06-08 18:58:13', 0);
INSERT INTO `borrow` VALUES (43, 'user', 'asd', '2023-06-08 18:58:11', NULL, 0);
INSERT INTO `borrow` VALUES (44, 'admin', '2', '2023-06-08 19:04:20', '2023-06-08 19:04:23', 0);
INSERT INTO `borrow` VALUES (45, 'user', '2', '2023-06-08 19:04:41', '2023-06-08 19:04:44', 0);
INSERT INTO `borrow` VALUES (46, '111', '1', '2023-06-08 19:05:09', '2023-06-08 19:05:12', 0);
INSERT INTO `borrow` VALUES (47, '111', '2', '2023-06-08 19:05:10', '2023-06-08 19:05:12', 0);
INSERT INTO `borrow` VALUES (48, '111', '8', '2023-06-08 19:05:16', NULL, 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `uid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `uname` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `uphone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `uidentity` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `deleted` int(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('111', '111', '$2a$10$/UDxapdV3c.EYdhUqFJAgen2oKUT8UwwWpi4.cwWJRR71f4qzLayG', '111', 'user', 0);
INSERT INTO `user` VALUES ('123', '123', '$2a$10$cDHaCbIFjAeE1b0dOzJLbec.IeNeXdMfYLiDLE4Q72nTZy33d38Mi', '123', 'user', 0);
INSERT INTO `user` VALUES ('admin', 'admin', '$2a$10$.tmuHsAxyorOlZ.CUN6d3uUixa/6qyOIZ5veiqpSj8r0TMcrWFhaC', 'admin', 'admin', 0);
INSERT INTO `user` VALUES ('user', 'user', '$2a$10$9GHyJ/491SWuvgTWM/bRJOxa7SDpShD6a3UKmLT8cwrYxES19Stl2', '1234', 'user', 0);

SET FOREIGN_KEY_CHECKS = 1;
