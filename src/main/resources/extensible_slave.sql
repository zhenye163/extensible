/*
Navicat MySQL Data Transfer

Source Server         : 192.168.139.141_3306
Source Server Version : 50721
Source Host           : 192.168.139.141:3306
Source Database       : extensible_slave

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-09-21 18:24:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for classroom
-- ----------------------------
DROP TABLE IF EXISTS `classroom`;
CREATE TABLE `classroom` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `grade` int(8) DEFAULT NULL COMMENT '年级',
  `class_no` int(8) DEFAULT NULL COMMENT '班号',
  `chinese_teacher_id` int(11) DEFAULT NULL COMMENT '语文老师id',
  `math_teacher_id` int(11) DEFAULT NULL COMMENT '数学老师id',
  `english_teacher_id` int(11) DEFAULT NULL COMMENT '英语老师id',
  `operate_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间',
  `flag` tinyint(1) DEFAULT NULL COMMENT '（0：未删除，1：已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of classroom
-- ----------------------------
INSERT INTO `classroom` VALUES ('1', '1', '1', '1', '2', '3', '2018-09-21 18:03:55', '0');
INSERT INTO `classroom` VALUES ('2', '1', '2', '4', '5', '6', '2018-09-21 18:03:55', '0');
INSERT INTO `classroom` VALUES ('3', '1', '3', '1', '5', '3', '2018-09-21 18:03:55', '0');
INSERT INTO `classroom` VALUES ('4', '1', '4', '4', '2', '6', '2018-09-21 18:03:55', '0');
INSERT INTO `classroom` VALUES ('5', '2', '1', '7', '8', '9', '2018-09-21 18:03:55', '0');
INSERT INTO `classroom` VALUES ('6', '2', '2', '10', '11', '12', '2018-09-21 18:03:55', '0');
INSERT INTO `classroom` VALUES ('7', '2', '3', '7', '11', '12', '2018-09-21 18:03:55', '0');
INSERT INTO `classroom` VALUES ('8', '3', '1', '13', '14', '15', '2018-09-21 18:03:55', '0');
INSERT INTO `classroom` VALUES ('9', '3', '2', '16', '17', '18', '2018-09-21 18:03:55', '0');
INSERT INTO `classroom` VALUES ('10', '3', '3', '16', '14', '15', '2018-09-21 18:03:55', '0');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(32) DEFAULT NULL COMMENT '名称',
  `sex` tinyint(1) DEFAULT NULL COMMENT '性别（0：男、1：女）',
  `age` int(8) DEFAULT NULL COMMENT '年龄',
  `classroom_id` int(11) DEFAULT NULL COMMENT '属于哪个班级',
  `operate_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间',
  `flag` tinyint(1) DEFAULT NULL COMMENT '（0：未删除，1：已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('1', '学1_master', '0', '16', '1', '2018-09-21 18:04:08', '0');
INSERT INTO `student` VALUES ('2', '学2', '0', '16', '1', '2018-09-21 18:04:08', '0');
INSERT INTO `student` VALUES ('3', '学3', '1', '15', '1', '2018-09-21 18:04:08', '0');
INSERT INTO `student` VALUES ('4', '学4', '1', '16', '1', '2018-09-21 18:04:08', '0');
INSERT INTO `student` VALUES ('5', '学5', '1', '16', '2', '2018-09-21 18:04:08', '0');
INSERT INTO `student` VALUES ('6', '学6', '0', '16', '2', '2018-09-21 18:04:08', '0');
INSERT INTO `student` VALUES ('7', '学7', '1', '17', '2', '2018-09-21 18:04:08', '0');
INSERT INTO `student` VALUES ('8', '学8', '0', '17', '2', '2018-09-21 18:04:08', '0');
INSERT INTO `student` VALUES ('9', '学9', '1', '16', '3', '2018-09-21 18:04:08', '0');
INSERT INTO `student` VALUES ('10', '学10', '1', '16', '3', '2018-09-21 18:04:08', '0');
INSERT INTO `student` VALUES ('11', '学11', '1', '15', '3', '2018-09-21 18:04:08', '0');
INSERT INTO `student` VALUES ('12', '学12', '0', '17', '3', '2018-09-21 18:04:08', '0');
INSERT INTO `student` VALUES ('13', '学13', '0', '17', '4', '2018-09-21 18:04:08', '0');
INSERT INTO `student` VALUES ('14', '学14', '0', '17', '4', '2018-09-21 18:04:08', '0');
INSERT INTO `student` VALUES ('15', '学15', '1', '18', '4', '2018-09-21 18:04:08', '0');
INSERT INTO `student` VALUES ('16', '学16', '1', '19', '4', '2018-09-21 18:04:08', '0');
INSERT INTO `student` VALUES ('17', '学17', '0', '17', '5', '2018-09-21 18:04:08', '0');
INSERT INTO `student` VALUES ('18', '学18', '1', '17', '5', '2018-09-21 18:04:08', '0');
INSERT INTO `student` VALUES ('19', '学19', '0', '17', '5', '2018-09-21 18:04:08', '0');
INSERT INTO `student` VALUES ('20', '学20', '1', '16', '5', '2018-09-21 18:04:08', '0');
INSERT INTO `student` VALUES ('21', '学21', '1', '17', '6', '2018-09-21 18:04:08', '0');
INSERT INTO `student` VALUES ('22', '学22', '1', '17', '6', '2018-09-21 18:04:08', '0');
INSERT INTO `student` VALUES ('23', '学23', '0', '19', '6', '2018-09-21 18:04:08', '0');
INSERT INTO `student` VALUES ('24', '学24', '1', '17', '6', '2018-09-21 18:04:08', '0');
INSERT INTO `student` VALUES ('25', '学25', '0', '16', '7', '2018-09-21 18:04:08', '0');
INSERT INTO `student` VALUES ('26', '学26', '1', '18', '7', '2018-09-21 18:04:08', '0');
INSERT INTO `student` VALUES ('27', '学27', '0', '17', '7', '2018-09-21 18:04:08', '0');
INSERT INTO `student` VALUES ('28', '学28', '1', '16', '7', '2018-09-21 18:04:08', '0');
INSERT INTO `student` VALUES ('29', '学29', '0', '18', '8', '2018-09-21 18:04:08', '0');
INSERT INTO `student` VALUES ('30', '学30', '1', '17', '8', '2018-09-21 18:04:08', '0');
INSERT INTO `student` VALUES ('31', '学31', '0', '18', '8', '2018-09-21 18:04:08', '0');
INSERT INTO `student` VALUES ('32', '学32', '1', '17', '8', '2018-09-21 18:04:08', '0');
INSERT INTO `student` VALUES ('33', '学33', '0', '19', '9', '2018-09-21 18:04:08', '0');
INSERT INTO `student` VALUES ('34', '学34', '1', '18', '9', '2018-09-21 18:04:08', '0');
INSERT INTO `student` VALUES ('35', '学35', '0', '18', '9', '2018-09-21 18:04:08', '0');
INSERT INTO `student` VALUES ('36', '学36', '1', '17', '9', '2018-09-21 18:04:08', '0');
INSERT INTO `student` VALUES ('37', '学37', '0', '18', '10', '2018-09-21 18:04:08', '0');
INSERT INTO `student` VALUES ('38', '学38', '1', '19', '10', '2018-09-21 18:04:08', '0');
INSERT INTO `student` VALUES ('39', '学', '0', '18', '10', '2018-09-21 18:04:08', '0');
INSERT INTO `student` VALUES ('40', '学', '1', '18', '10', '2018-09-21 18:04:08', '0');

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(32) DEFAULT NULL COMMENT '名称',
  `sex` tinyint(1) DEFAULT NULL COMMENT '性别（0：男、1：女）',
  `age` int(8) DEFAULT NULL COMMENT '年龄',
  `subject` varchar(32) DEFAULT NULL COMMENT '所授学科',
  `operate_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间',
  `flag` tinyint(1) DEFAULT NULL COMMENT '（0：未删除，1：已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES ('1', '语1', '0', '31', '语文', '2018-09-21 18:04:28', '0');
INSERT INTO `teacher` VALUES ('2', '数1', '0', '31', '数学', '2018-09-21 18:04:28', '0');
INSERT INTO `teacher` VALUES ('3', '英1', '1', '33', '英语', '2018-09-21 18:04:28', '0');
INSERT INTO `teacher` VALUES ('4', '语2', '1', '35', '语文', '2018-09-21 18:04:28', '0');
INSERT INTO `teacher` VALUES ('5', '数2', '0', '37', '数学', '2018-09-21 18:04:28', '0');
INSERT INTO `teacher` VALUES ('6', '英2', '1', '31', '英语', '2018-09-21 18:04:28', '0');
INSERT INTO `teacher` VALUES ('7', '语3', '0', '35', '语文', '2018-09-21 18:04:28', '0');
INSERT INTO `teacher` VALUES ('8', '数3', '1', '26', '数学', '2018-09-21 18:04:28', '0');
INSERT INTO `teacher` VALUES ('9', '英3', '0', '41', '英语', '2018-09-21 18:04:28', '0');
INSERT INTO `teacher` VALUES ('10', '语4', '1', '27', '语文', '2018-09-21 18:04:28', '0');
INSERT INTO `teacher` VALUES ('11', '数4', '0', '47', '数学', '2018-09-21 18:04:28', '0');
INSERT INTO `teacher` VALUES ('12', '英4', '0', '32', '英语', '2018-09-21 18:04:28', '0');
INSERT INTO `teacher` VALUES ('13', '语5', '1', '30', '语文', '2018-09-21 18:04:28', '0');
INSERT INTO `teacher` VALUES ('14', '数5', '1', '35', '数学', '2018-09-21 18:04:28', '0');
INSERT INTO `teacher` VALUES ('15', '英5', '1', '29', '英语', '2018-09-21 18:04:28', '0');
INSERT INTO `teacher` VALUES ('16', '语6', '0', '43', '语文', '2018-09-21 18:04:28', '0');
INSERT INTO `teacher` VALUES ('17', '数6', '0', '42', '数学', '2018-09-21 18:04:28', '0');
INSERT INTO `teacher` VALUES ('18', '英6', '1', '38', '英语', '2018-09-21 18:04:28', '0');
