/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : qiange

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2017-09-17 21:20:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for qg_engine
-- ----------------------------
DROP TABLE IF EXISTS `qg_engine`;
CREATE TABLE `qg_engine` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL COMMENT '搜索引擎名称',
  `url` varchar(255) DEFAULT NULL COMMENT '搜索地址',
  `search_logo` varchar(255) DEFAULT NULL COMMENT '搜索图标',
  `orderby` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='搜索引擎表';

-- ----------------------------
-- Records of qg_engine
-- ----------------------------
INSERT INTO `qg_engine` VALUES ('1', 'google', 'https://www.google.com/search?source=hp&btnG=Google+搜索&q=', 'search-google.png', '1');
INSERT INTO `qg_engine` VALUES ('2', '百度', 'https://www.baidu.com/s?wd=', 'search-baidu.png', '2');
INSERT INTO `qg_engine` VALUES ('3', 'csdn资源', 'http://download.csdn.net/search/0/10/0/2/1/', 'search-csdn.png', '3');

-- ----------------------------
-- Table structure for qg_folder
-- ----------------------------
DROP TABLE IF EXISTS `qg_folder`;
CREATE TABLE `qg_folder` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `fname` varchar(32) DEFAULT NULL COMMENT '名称',
  `uid` int(11) DEFAULT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qg_folder_tag
-- ----------------------------
DROP TABLE IF EXISTS `qg_folder_tag`;
CREATE TABLE `qg_folder_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `folder_id` int(11) DEFAULT NULL COMMENT '文件夹ID',
  `tag_id` int(11) DEFAULT NULL COMMENT '标签ID',
  `orderby` int(4) DEFAULT NULL COMMENT '顺序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET=utf8 COMMENT='文件夹标签关联表';

-- ----------------------------
-- Table structure for qg_role
-- ----------------------------
DROP TABLE IF EXISTS `qg_role`;
CREATE TABLE `qg_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role` varchar(32) DEFAULT NULL COMMENT '角色名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qg_tag
-- ----------------------------
DROP TABLE IF EXISTS `qg_tag`;
CREATE TABLE `qg_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `tname` varchar(32) DEFAULT NULL COMMENT '标签名称',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标路径',
  `url` varchar(255) DEFAULT NULL COMMENT '标签地址',
  `type` varchar(1) DEFAULT NULL COMMENT '类型 1:图标 2:文字',
  `bg_color` varchar(32) DEFAULT NULL COMMENT '图标颜色',
  `top_name` varchar(20) DEFAULT NULL COMMENT '头文字',
  `auth` int(2) DEFAULT NULL COMMENT '权限1公开,2私有',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=utf8 COMMENT='标签表';

-- ----------------------------
-- Table structure for qg_user
-- ----------------------------
DROP TABLE IF EXISTS `qg_user`;
CREATE TABLE `qg_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(32) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `rid` int(11) DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qg_user
-- ----------------------------
INSERT INTO `qg_user` VALUES ('1', 'admin', 'szu4090', null);
