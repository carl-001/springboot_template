/*
Navicat MySQL Data Transfer

Source Server         : bozhou
Source Server Version : 50519
Source Host           : localhost:3306
Source Database       : globalpro

Target Server Type    : MYSQL
Target Server Version : 50519
File Encoding         : 65001

Date: 2019-04-16 17:01:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sys_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `logId` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '主键id',
  `logTitle` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '日志标题',
  `ip` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '操作IP地址',
  `userName` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '操作用户昵称',
  `requstUrl` varchar(1024) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '请求URI',
  `httpMethod` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '操作方式',
  `classMethod` varchar(1024) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '请求类型方法',
  `sessionId` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'SessionId',
  `response` varchar(3120) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '返回内容',
  `useTime` int(11) DEFAULT NULL COMMENT '方法执行时间',
  `browser` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '浏览器信息',
  `area` varchar(1024) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '地区',
  `province` varchar(1024) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '省',
  `city` varchar(1024) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '市',
  `params` varchar(3120) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '参数',
  `created_by` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新人',
  `ppdated_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`logId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='系统日志';

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES ('efd6d2c2f0ff4ef18392427661b70e7a', '查询系统日志列表', '127.0.0.1', 'null', 'http://localhost:8889/api/system/SysLog/getlist', 'POST', 'com.global.api.controller.system.SysLogController.list', '80d1fd11-ee6a-4e15-be11-1c4637dab08f', null, null, null, null, null, null, '[{\"data\":{},\"sign\":\"string\",\"timestap\":0,\"token\":\"string\"},1,1,\"org.apache.shiro.web.servlet.ShiroHttpServletRequest@76239d47\"]', null, '2019-04-16 17:01:02', null, null);

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `roleId` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '主键',
  `roleName` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '角色名',
  `status` varchar(1) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '状态 0-已删除，1-可用',
  `remarks` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `created_by` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='系统角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('07eeaf47f3164013adedac9a9145fe81', '测试3修改', '1', '测试修改', '', null, '', null);
INSERT INTO `sys_role` VALUES ('1', '超级管理员', '1', null, null, null, null, null);
INSERT INTO `sys_role` VALUES ('2', '测试数据', '1', null, null, null, null, null);
INSERT INTO `sys_role` VALUES ('3', '测试数据2', '1', null, null, null, null, null);

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `userId` varchar(36) NOT NULL,
  `userName` varchar(100) DEFAULT NULL,
  `loginName` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `salt` varchar(36) DEFAULT NULL,
  `version` int(10) DEFAULT NULL,
  `created_by` varchar(36) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `update_by` varchar(36) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('91abfb8ac9964cae89ac010cade7e49d', 'admin', 'admin', '18944c16a2664503d33b0cbcb876fb285778d16e1681364e72123251c5e87c08', '98736f9f922d6115a254', '0', '', null, '', null, '1');
