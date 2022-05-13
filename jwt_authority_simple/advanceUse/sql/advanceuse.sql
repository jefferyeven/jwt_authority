/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 50730
Source Host           : localhost:3306
Source Database       : advanceuse

Target Server Type    : MYSQL
Target Server Version : 50730
File Encoding         : 65001

Date: 2022-05-13 19:20:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `urlauthorities`
-- ----------------------------
DROP TABLE IF EXISTS `urlauthorities`;
CREATE TABLE `urlauthorities` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(40) DEFAULT NULL,
  `authorities` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of urlauthorities
-- ----------------------------

-- ----------------------------
-- Table structure for `users`
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `authorities` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', 'admin', '123456', '[\"admin\",\"user\"]');
INSERT INTO `users` VALUES ('2', 'user', '123456', '[\"user\"]');
INSERT INTO `users` VALUES ('3', 'vip', '123456', '[\"user\",\"vip\"]');
INSERT INTO `users` VALUES ('4', 'anoy', '123456', '[\"anoy\"]');
INSERT INTO `users` VALUES ('5', 'test,anoy', '123,123456', '[\"anoy\"]');
