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

 Date: 30/03/2023 12:10:03
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
INSERT INTO `book` VALUES ('1', '12', '112', '未借出', 0);
INSERT INTO `book` VALUES ('10', '10', '10', '已借出', 0);
INSERT INTO `book` VALUES ('11', '11', '11', '已借出', 0);
INSERT INTO `book` VALUES ('12', '12', '12', '已借出', 0);
INSERT INTO `book` VALUES ('123', '123', '123', '未借出', 1);
INSERT INTO `book` VALUES ('1231', '1231', '1231', '未借出', 1);
INSERT INTO `book` VALUES ('1232', '1232', '1232', '未借出', 0);
INSERT INTO `book` VALUES ('12333', '12333', '1233', '未借出', 1);
INSERT INTO `book` VALUES ('1234', '123', '123', '未借出', 0);
INSERT INTO `book` VALUES ('13', '13', '13', '已借出', 0);
INSERT INTO `book` VALUES ('2', '2', '2', '未借出', 1);
INSERT INTO `book` VALUES ('3', '3', '3', '已借出', 0);
INSERT INTO `book` VALUES ('333', '4444', '4444', '未借出', 0);
INSERT INTO `book` VALUES ('4', '4', '4', '未借出', 1);
INSERT INTO `book` VALUES ('5', '5', '5', '已借出', 0);
INSERT INTO `book` VALUES ('6', '6', '6', '未借出', 0);
INSERT INTO `book` VALUES ('7', '7', '7', '未借出', 1);
INSERT INTO `book` VALUES ('8', '8', '8', '未借出', 0);
INSERT INTO `book` VALUES ('9', '9', '9', '未借出', 1);

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
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of borrow
-- ----------------------------
INSERT INTO `borrow` VALUES (6, 'admin', '12', '2023-03-26 15:33:13', '2023-03-26 16:56:49', 0);
INSERT INTO `borrow` VALUES (8, 'admin', '13', '2023-03-26 16:04:22', '2023-03-26 17:00:23', 0);
INSERT INTO `borrow` VALUES (9, 'admin', '2', '2023-03-26 16:39:13', '2023-03-26 17:00:29', 0);
INSERT INTO `borrow` VALUES (10, 'admin', '8', '2023-03-26 16:40:34', '2023-03-28 21:25:13', 0);
INSERT INTO `borrow` VALUES (11, 'admin', '9', '2023-03-26 16:43:41', '2023-03-28 20:39:35', 0);
INSERT INTO `borrow` VALUES (12, 'admin', '10', '2023-03-26 17:00:14', '2023-03-26 17:00:16', 1);
INSERT INTO `borrow` VALUES (13, 'admin', '10', '2023-03-26 17:00:18', '2023-03-26 17:00:43', 1);
INSERT INTO `borrow` VALUES (14, 'admin', '12', '2023-03-26 17:00:24', '2023-03-26 21:15:17', 1);
INSERT INTO `borrow` VALUES (15, 'admin', '11', '2023-03-26 17:00:44', '2023-03-26 21:14:04', 0);
INSERT INTO `borrow` VALUES (16, '2222', '1', '2023-03-26 21:05:34', '2023-03-26 21:14:02', 0);
INSERT INTO `borrow` VALUES (17, '2222', '3', '2023-03-26 21:05:45', NULL, 0);
INSERT INTO `borrow` VALUES (18, '19856523691', '10', '2023-03-26 21:10:55', '2023-03-26 21:13:00', 0);
INSERT INTO `borrow` VALUES (19, 'admin', '11', '2023-03-26 21:15:21', NULL, 0);
INSERT INTO `borrow` VALUES (20, '1211', '1', '2023-03-27 12:57:18', '2023-03-27 12:59:50', 0);
INSERT INTO `borrow` VALUES (21, '123456', '10', '2023-03-27 13:10:16', '2023-03-27 13:10:43', 0);
INSERT INTO `borrow` VALUES (22, '11111', '12', '2023-03-27 18:25:36', NULL, 0);
INSERT INTO `borrow` VALUES (23, '1211', '13', '2023-03-27 18:31:31', '2023-03-27 18:32:20', 0);
INSERT INTO `borrow` VALUES (24, '11111', '1', '2023-03-27 18:38:19', '2023-03-27 18:38:59', 0);
INSERT INTO `borrow` VALUES (25, '123321', '10', '2023-03-28 20:29:38', NULL, 0);
INSERT INTO `borrow` VALUES (26, '123321', '333', '2023-03-28 20:29:55', '2023-03-28 20:30:06', 0);
INSERT INTO `borrow` VALUES (27, '123', '13', '2023-03-28 20:38:23', NULL, 0);
INSERT INTO `borrow` VALUES (28, 'admin', '1234', '2023-03-28 20:39:09', '2023-03-28 20:39:12', 0);
INSERT INTO `borrow` VALUES (29, '123', '5', '2023-03-28 20:40:10', NULL, 0);
INSERT INTO `borrow` VALUES (30, '123', '1234', '2023-03-28 20:40:18', '2023-03-28 20:40:39', 0);
INSERT INTO `borrow` VALUES (31, '123', '1', '2023-03-28 20:45:38', '2023-03-28 20:45:47', 0);
INSERT INTO `borrow` VALUES (32, '1231233', '1', '2023-03-28 21:12:15', '2023-03-28 21:12:25', 0);
INSERT INTO `borrow` VALUES (33, '789', '1234', '2023-03-28 21:22:11', '2023-03-28 21:22:23', 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `uid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `uname` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `uphone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `uidentity` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `deleted` int(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('111', '111', '698d51a19d8a121ce581499d7b701668', '111', 'admin', 0);
INSERT INTO `user` VALUES ('11111', '11111', 'b0baee9d279d34fa1dfd71aadb908c3f', '11111', 'admin', 0);
INSERT INTO `user` VALUES ('1111111', '1111111', '7fa8282ad93047a4d6fe6111c93b308a', '1111111', 'user', 1);
INSERT INTO `user` VALUES ('1211', '1211', '285ab9448d2751ee57ece7f762c39095', '1211', 'user', 1);
INSERT INTO `user` VALUES ('12111', '12', 'c20ad4d76fe97759aa27a0c99bff6710', '12', 'user', 1);
INSERT INTO `user` VALUES ('12121', '12', 'c20ad4d76fe97759aa27a0c99bff6710', '12', 'user', 0);
INSERT INTO `user` VALUES ('123', '1233', '202cb962ac59075b964b07152d234b70', '1233', 'user', 0);
INSERT INTO `user` VALUES ('123123', '123123', '4297f44b13955235245b2497399d7a93', '123123', 'user', 0);
INSERT INTO `user` VALUES ('1231233', '123', '202cb962ac59075b964b07152d234b70', '123', 'user', 1);
INSERT INTO `user` VALUES ('123321', '123321', '202cb962ac59075b964b07152d234b70', '123321', 'user', 0);
INSERT INTO `user` VALUES ('12333', '123', '202cb962ac59075b964b07152d234b70', '123', 'user', 0);
INSERT INTO `user` VALUES ('1234', '1234', '81dc9bdb52d04dc20036dbd8313ed055', '1234', 'user', 1);
INSERT INTO `user` VALUES ('12345', '12345', '827ccb0eea8a706c4c34a16891f84e7b', '12345', 'user', 0);
INSERT INTO `user` VALUES ('123456', '123', '202cb962ac59075b964b07152d234b70', '123', 'user', 0);
INSERT INTO `user` VALUES ('19856523691', 'tao huang', 'b570111b6769140c3af8d0b059230779', '19856523691', 'user', 0);
INSERT INTO `user` VALUES ('2222', '2222', '934b535800b1cba8f96a5d72f72f1611', '2222', 'user', 0);
INSERT INTO `user` VALUES ('333', '333', '310dcbbf4cce62f762a2aaa148d556bd', '333', 'user', 0);
INSERT INTO `user` VALUES ('789', '123', '202cb962ac59075b964b07152d234b70', '123', 'user', 0);
INSERT INTO `user` VALUES ('admin', 'admin', '21232f297a57a5a743894a0e4a801fc3', 'admin', 'admin', 0);

SET FOREIGN_KEY_CHECKS = 1;
