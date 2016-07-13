/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : frame

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2016-07-13 20:39:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_area
-- ----------------------------
DROP TABLE IF EXISTS `sys_area`;
CREATE TABLE `sys_area` (
  `id` varchar(40) NOT NULL,
  `parent_id` varchar(40) DEFAULT NULL,
  `parent_ids` varchar(2000) DEFAULT NULL COMMENT '所有父级编号',
  `code` varchar(100) DEFAULT NULL COMMENT '区域编码',
  `name` varchar(100) NOT NULL COMMENT '区域名称',
  `type` char(1) DEFAULT NULL COMMENT '区域类型',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` bigint(20) DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` bigint(20) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记(0活null 正常 1,删除)',
  `icon` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sys_area_parent_id` (`parent_id`) USING BTREE,
  KEY `sys_area_parent_ids` (`parent_ids`(255)) USING BTREE,
  KEY `sys_area_del_flag` (`del_flag`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='区域表';

-- ----------------------------
-- Records of sys_area
-- ----------------------------

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `label` varchar(100) NOT NULL DEFAULT '',
  `value` varchar(200) DEFAULT NULL,
  `del_Flag` char(1) DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES ('17', 'LOGIN_ERROR_COUNT', '4', '0', null, null, '2,admin', '2016-05-23 14:45:53');
INSERT INTO `sys_config` VALUES ('18', 'LOGIN_UNLOCK_TIME', '30', '0', null, null, '2,admin', '2016-05-23 14:45:53');
INSERT INTO `sys_config` VALUES ('19', 'PWD_FIRST_LOGIN_MOD', '0', '0', null, null, '2,admin', '2016-05-23 14:45:53');
INSERT INTO `sys_config` VALUES ('20', 'PWD_NEXT_MOD_TIME', '0', '0', null, null, '2,admin', '2016-05-23 14:45:53');
INSERT INTO `sys_config` VALUES ('21', 'LOGIN_CAPTCHA_ON', '0', '0', null, null, '2,admin', '2016-05-23 14:45:53');
INSERT INTO `sys_config` VALUES ('22', 'PWD_STRONG_VERIFCATION', '1', '0', null, null, '2,admin', '2016-05-23 14:45:53');
INSERT INTO `sys_config` VALUES ('24', 'FILE_SAVE_ROOT_PATH', 'E:/dcs', '0', null, null, '2,超级管理员', '2015-11-10 16:51:46');
INSERT INTO `sys_config` VALUES ('26', 'ATTACH_SIZE_LIMIT', '200', '0', '2,超级管理员', '2015-09-18 15:07:24', '2,超级管理员', '2015-11-10 16:51:46');
INSERT INTO `sys_config` VALUES ('27', 'FAMILY_MEMEBER_LIMIT', '10', '0', null, null, null, null);
INSERT INTO `sys_config` VALUES ('28', 'GALLERY_CONFIG_SECTION', '1', '0', '2,admin', '2016-04-26 09:01:52', null, '2016-04-26 09:01:52');
INSERT INTO `sys_config` VALUES ('29', 'GALLERY_CONFIG_DISLINE', '1', '0', '2,admin', '2016-04-26 09:01:52', null, '2016-04-26 09:01:52');

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` varchar(40) NOT NULL COMMENT '编号',
  `label` varchar(100) NOT NULL COMMENT '标签名',
  `value` varchar(100) NOT NULL COMMENT '数据值',
  `type` varchar(100) NOT NULL COMMENT '类型',
  `description` varchar(100) NOT NULL COMMENT '描述',
  `sort` int(11) NOT NULL DEFAULT '1' COMMENT '排序（升序）',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `status` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sys_dict_value` (`value`) USING BTREE,
  KEY `sys_dict_label` (`label`) USING BTREE,
  KEY `sys_dict_del_flag` (`del_flag`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='字典表';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('1', '正常', '0', 'del_flag', '删除标记', '10', '1', '20130527080000', '2,超级管理员', '20150901091950', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('2', '删除', '1', 'del_flag', '删除标记', '20', '1', '20130527080000', '1', '1467684679444', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('3', '显示', '1', 'show_hide', '显示/隐藏', '10', '1', '20130527080000', '1', '20130527080000', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('4', '隐藏', '0', 'show_hide', '显示/隐藏', '20', '1', '20130527080000', '1', '20130527080000', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('5', '是', '1', 'yes_no', '是/否', '10', '1', '20130527080000', '1', '20130527080000', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('6', '否', '0', 'yes_no', '是/否', '20', '1', '20130527080000', '1', '20130527080000', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('17', '国家', '1', 'sys_area_type', '区域类型', '10', '1', '20130527080000', '1', '20130527080000', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('18', '省份、直辖市', '2', 'sys_area_type', '区域类型', '20', '1', '20130527080000', '1', '20130527080000', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('19', '地市', '3', 'sys_area_type', '区域类型', '30', '1', '20130527080000', '1', '20130527080000', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('20', '区县', '4', 'sys_area_type', '区域类型', '40', '1', '20130527080000', '1', '20130527080000', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('22', '部门', '2', 'sys_office_type', '机构类型', '70', '1', '20130527080000', '1', '1467945611074', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('23', '一级', '1', 'sys_office_grade', '机构等级', '10', '1', '20130527080000', '1', '20130527080000', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('24', '二级', '2', 'sys_office_grade', '机构等级', '20', '1', '20130527080000', '1', '20130527080000', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('25', '三级', '3', 'sys_office_grade', '机构等级', '30', '1', '20130527080000', '1', '20130527080000', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('26', '四级', '4', 'sys_office_grade', '机构等级', '40', '1', '20130527080000', '1', '20130527080000', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('27', '所有数据', '1', 'sys_data_scope', '数据范围', '10', '1', '20130527080000', '1', '20130527080000', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('28', '所在学校数据 ', '2', 'sys_data_scope', '数据范围', '20', '1', '20130527080000', '1', '20130527080000', null, '1', '1');
INSERT INTO `sys_dict` VALUES ('29', '所在公司数据', '3', 'sys_data_scope', '数据范围', '30', '1', '20130527080000', '1', '20130527080000', null, '1', '1');
INSERT INTO `sys_dict` VALUES ('30', '所在部门及以下数据', '4', 'sys_data_scope', '数据范围', '40', '1', '20130527080000', '1', '20130527080000', null, '1', '1');
INSERT INTO `sys_dict` VALUES ('31', '所在部门数据', '5', 'sys_data_scope', '数据范围', '50', '1', '20130527080000', '1', '20130527080000', null, '1', '1');
INSERT INTO `sys_dict` VALUES ('32', '仅本人数据', '8', 'sys_data_scope', '数据范围', '90', '1', '20130527080000', '1', '20130527080000', null, '1', '1');
INSERT INTO `sys_dict` VALUES ('33', '按明细设置', '9', 'sys_data_scope', '数据范围', '100', '1', '20130527080000', '1', '20130527080000', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('34', '系统管理', '1', 'sys_user_type', '用户类型', '10', '1', '20130527080000', '1', '1467945574452', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('35', '部门经理', '2', 'sys_user_type', '用户类型', '20', '1', '20130527080000', '1', '20130527080000', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('36', '普通用户', '3', 'sys_user_type', '用户类型', '30', '1', '20130527080000', '1', '20130527080000', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('62', '操作日志', '1', 'sys_log_type', '日志类型', '30', '1', '20130603080000', '1', '20130603080000', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('63', '异常日志', '2', 'sys_log_type', '日志类型', '40', '1', '20130603080000', '1', '20130603080000', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('64', '单表增删改查', 'single', 'prj_template_type', '代码模板', '10', '1', '20130603080000', '2,超级管理员', '20150901103059', null, '1', '1');
INSERT INTO `sys_dict` VALUES ('65', '所有entity和dao', 'entityAndDao', 'prj_template_type', '代码模板', '20', '1', '20130603080000', '1', '20130603080000', null, '1', '1');
INSERT INTO `sys_dict` VALUES ('66', '公司', '1', '0', '', '1', null, '20150110221543', '2,超级管理员', '20150901104157', null, '1', '1');
INSERT INTO `sys_dict` VALUES ('67', '公开', '1', 'secrets_level', '', '1', '2,超级管理员', '20150805232503', null, '20150805232503', null, '1', '1');
INSERT INTO `sys_dict` VALUES ('68', '保密', '2', 'secrets_level', '', '3', '2,超级管理员', '20150805232524', null, '20150805232524', null, '1', '1');
INSERT INTO `sys_dict` VALUES ('69', 'admin', 'sss', 'prj_template_type', '', '1', '2,超级管理员', '20150901102956', '2,超级管理员', '20150901104628', null, '1', '1');
INSERT INTO `sys_dict` VALUES ('70', '公司', '1', 'sys_office_type', '', '1', '2,超级管理员', '20150901110116', null, '1467945610463', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('71', '公共课', '0', 'course_type', '公共课', '10', '2,超级管理员', '20150908145519', null, '20150908145519', null, '1', '1');
INSERT INTO `sys_dict` VALUES ('72', '专业课', '1', 'course_type', '专业课', '20', '2,超级管理员', '20150908145547', null, '20150908145547', null, '1', '1');
INSERT INTO `sys_dict` VALUES ('73', '一年制', '1', 'class_length', '一年制', '1', '2,超级管理员', '20150908150612', null, '20150908150612', null, '1', '1');
INSERT INTO `sys_dict` VALUES ('74', '两年制', '2', 'class_length', '两年制', '2', '2,超级管理员', '20150908150706', '2,超级管理员', '20150908150802', null, '1', '1');
INSERT INTO `sys_dict` VALUES ('75', '三年制', '3', 'class_length', '三年制', '3', '2,超级管理员', '20150908150730', null, '20150908150730', null, '1', '1');
INSERT INTO `sys_dict` VALUES ('76', '一级职称', '10', 'teacher_title', '一级职称', '10', '2,超级管理员', '20150908152402', '2,超级管理员', '20150908153356', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('77', '二级职称', '20', 'teacher_title', '二级职称', '20', '2,超级管理员', '20150908152430', '2,超级管理员', '20150908153435', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('78', '三级职称', '30', 'teacher_title', '三级职称', '30', '2,超级管理员', '20150908152503', '2,超级管理员', '20150908153458', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('79', '在职', '1', 'teacher_status', '教师状态', '10', '2,超级管理员', '20150908154057', null, '20150908154057', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('80', '不在职', '0', 'teacher_status', '教师状态', '20', '2,超级管理员', '20150908154131', null, '20150908154131', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('81', '教师', '1', 'common_post', '教师', '1', '2,超级管理员', '20150908160618', null, '20150908160618', null, '1', '1');
INSERT INTO `sys_dict` VALUES ('82', '教务科主任', '2', 'common_post', '教务科主任', '2', '2,超级管理员', '20150908160652', null, '20150908160652', null, '1', '1');
INSERT INTO `sys_dict` VALUES ('83', '教务科副主任', '3', 'common_post', '教务科副主任', '3', '2,超级管理员', '20150908160722', null, '20150908160722', null, '1', '1');
INSERT INTO `sys_dict` VALUES ('84', '男', 'm', 'common_sex', '男', '1', '2,超级管理员', '20150908161209', '2,超级管理员', '20150908161527', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('85', '女', 'w', 'common_sex', '女', '2', '2,超级管理员', '20150908161240', '2,超级管理员', '20150908161542', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('87', '启用', '1', 'common_status', '启用', '1', '2,超级管理员', '20150908164122', null, '20150908164122', null, '0', '0');
INSERT INTO `sys_dict` VALUES ('88', '禁用', '0', 'common_status', '禁用', '2', '2,超级管理员', '20150908164628', null, '20150908164628', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('179', '汉族', '1', 'student_nation ', '', '1', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('180', '朝鲜族', '2', 'student_nation ', '', '2', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('181', '满族', '3', 'student_nation ', '', '3', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('182', '侗族', '4', 'student_nation ', '', '4', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('183', '瑶族', '5', 'student_nation ', '', '5', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('184', '白族', '6', 'student_nation ', '', '6', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('185', '土家族', '7', 'student_nation ', '', '7', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('186', '哈尼族', '8', 'student_nation ', '', '8', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('187', '哈萨克族', '9', 'student_nation ', '', '9', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('188', '傣族', '10', 'student_nation ', '', '10', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('189', '黎族', '11', 'student_nation ', '', '11', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('190', '蒙古族', '12', 'student_nation ', '', '12', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('191', '傈僳族', '13', 'student_nation ', '', '13', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('192', '佤族', '14', 'student_nation ', '', '14', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('193', '畲族', '15', 'student_nation ', '', '15', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('194', '高山族', '16', 'student_nation ', '', '16', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('195', '拉祜族', '17', 'student_nation ', '', '17', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('196', '水族', '18', 'student_nation ', '', '18', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('197', '东乡族', '19', 'student_nation ', '', '19', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('198', '纳西族', '20', 'student_nation ', '', '20', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('199', '景颇族', '21', 'student_nation ', '', '21', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('200', '柯尔克孜族', '22', 'student_nation ', '', '22', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('201', '回族', '23', 'student_nation ', '', '23', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('202', '土族', '24', 'student_nation ', '', '24', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('203', '达斡尔族', '25', 'student_nation ', '', '25', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('204', '仫佬族', '26', 'student_nation ', '', '26', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('205', '羌族', '27', 'student_nation ', '', '27', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('206', '布朗族', '28', 'student_nation ', '', '28', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('207', '撒拉族', '29', 'student_nation ', '', '29', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('208', '毛南族', '30', 'student_nation ', '', '30', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('209', '仡佬族', '31', 'student_nation ', '', '31', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('210', '锡伯族', '32', 'student_nation ', '', '32', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('211', '阿昌族', '33', 'student_nation ', '', '33', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('212', '藏族', '34', 'student_nation ', '', '34', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('213', '普米族', '35', 'student_nation ', '', '35', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('214', '塔吉克族', '36', 'student_nation ', '', '36', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('215', '怒族', '37', 'student_nation ', '', '37', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('216', '乌孜别克族', '38', 'student_nation ', '', '38', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('217', '俄罗斯族', '39', 'student_nation ', '', '39', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('218', '鄂温克族', '40', 'student_nation ', '', '40', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('219', '德昂族', '41', 'student_nation ', '', '41', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('220', '保安族', '42', 'student_nation ', '', '42', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('221', '裕固族', '43', 'student_nation ', '', '43', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('222', '京族', '44', 'student_nation ', '', '44', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('223', '维吾尔族', '45', 'student_nation ', '', '45', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('224', '塔塔尔族', '46', 'student_nation ', '', '46', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('225', '独龙族', '47', 'student_nation ', '', '47', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('226', '鄂伦春族', '48', 'student_nation ', '', '48', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('227', '赫哲族', '49', 'student_nation ', '', '49', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('228', '门巴族', '50', 'student_nation ', '', '50', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('229', '珞巴族', '51', 'student_nation ', '', '51', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('230', '基诺族', '52', 'student_nation ', '', '52', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('231', '苗族', '53', 'student_nation ', '', '53', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('232', '彝族', '54', 'student_nation ', '', '54', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('233', '壮族', '55', 'student_nation ', '', '55', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('234', '布依族', '56', 'student_nation ', '', '56', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('235', '穿青人族', '57', 'student_nation ', '', '57', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('236', '其他', '58', 'student_nation ', '', '58', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('237', '外国血统中国籍人士', '59', 'student_nation ', '', '59', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('238', '中国共产主义青年团团员', '1', 'student_politicalStatus', '大多数学生的政治面貌', '1', '2,超级管理员', '20150909185900', '2,超级管理员', '20160315114211', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('239', '中国共产党党员', '3', 'student_politicalStatus', '', '3', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('240', '中国共产党预备党员', '4', 'student_politicalStatus', '', '4', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('241', '中国国民党革命委员会会员', '5', 'student_politicalStatus', '', '5', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('242', '中国民主同盟盟员', '6', 'student_politicalStatus', '', '6', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('243', '中国民主建国会会员', '7', 'student_politicalStatus', '', '7', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('244', '中国民主促进会会员', '8', 'student_politicalStatus', '', '8', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('245', '中国农工民主党党员', '9', 'student_politicalStatus', '', '9', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('246', '中国致公党党员', '10', 'student_politicalStatus', '', '10', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('247', '九三学社社员', '11', 'student_politicalStatus', '', '11', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('248', '台湾民主自治同盟盟员', '12', 'student_politicalStatus', '', '12', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('249', '无党派民主人士', '13', 'student_politicalStatus', '', '13', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('250', '群众', '2', 'student_politicalStatus', '', '2', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('251', '健康或良好', '1', 'student_health', '', '1', '2,超级管理员', '20150909185900', null, null, null, '1', '1');
INSERT INTO `sys_dict` VALUES ('252', '一般或较弱', '2', 'student_health', '', '2', '2,超级管理员', '20150909185900', null, null, null, '1', '1');
INSERT INTO `sys_dict` VALUES ('253', '有慢性病', '3', 'student_health', '', '3', '2,超级管理员', '20150909185900', null, null, null, '1', '1');
INSERT INTO `sys_dict` VALUES ('254', '残疾', '4', 'student_health', '', '4', '2,超级管理员', '20150909185900', null, null, null, '1', '1');
INSERT INTO `sys_dict` VALUES ('255', '应届', '1', 'student_source', '', '1', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('256', '非应届', '2', 'student_source', '', '2', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('257', '非联合办学', '1', 'student_commonType', '', '1', '2,超级管理员', '20150909185900', null, null, null, '1', '1');
INSERT INTO `sys_dict` VALUES ('258', '跨省联合办学', '2', 'student_commonType', '', '2', '2,超级管理员', '20150909185900', null, null, null, '1', '1');
INSERT INTO `sys_dict` VALUES ('259', '城乡联合办学', '3', 'student_commonType', '', '3', '2,超级管理员', '20150909185900', null, null, null, '1', '1');
INSERT INTO `sys_dict` VALUES ('260', '东西部联合办学', '4', 'student_commonType', '', '4', '2,超级管理员', '20150909185900', null, null, null, '1', '1');
INSERT INTO `sys_dict` VALUES ('261', '其他联合办学', '5', 'student_commonType', '', '5', '2,超级管理员', '20150909185900', null, null, null, '1', '1');
INSERT INTO `sys_dict` VALUES ('262', '东西联合办学并且城乡联合办学', '6', 'student_commonType', '', '6', '2,超级管理员', '20150909185900', null, null, null, '1', '1');
INSERT INTO `sys_dict` VALUES ('263', '农业户口', '1', 'student_householdType', '', '1', '2,超级管理员', '20150909185900', null, null, null, '1', '1');
INSERT INTO `sys_dict` VALUES ('264', '非农业户口', '2', 'student_householdType', '', '2', '2,超级管理员', '20150909185900', null, null, null, '1', '1');
INSERT INTO `sys_dict` VALUES ('265', '统一招生', '1', 'student_recruitment', '', '1', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('266', '自主招生', '2', 'student_recruitment', '', '2', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('267', '正常', '1', 'student_status', '正常', '1', '2,超级管理员', '20150909185900', null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('268', '已毕业', '0', 'student_status', '已毕业', '10', '2,超级管理员', '20150909185900', '2,超级管理员', '20150921000116', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('269', '正常', '1', 'class_status', '正常', '10', '2,超级管理员', '20150911175251', null, '20150911175251', null, '1', '1');
INSERT INTO `sys_dict` VALUES ('270', '已毕业', '0', 'class_status', '已毕业', '20', '2,超级管理员', '20150911175328', null, '20150911175328', null, '1', '1');
INSERT INTO `sys_dict` VALUES ('271', '秋期', '1', 'class_semester', '秋期', '1', '2,超级管理员', '20150917165532', null, '20150917165532', null, '1', '1');
INSERT INTO `sys_dict` VALUES ('272', '春期', '2', 'class_semester', '春期', '2', '2,超级管理员', '20150917165604', null, '20150917165604', null, '1', '1');
INSERT INTO `sys_dict` VALUES ('273', '退学', '2', 'student_status', '退学', '2', '2,超级管理员', '20150921000200', null, '20150921000200', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('274', '转学', '3', 'student_status', '转学', '3', '2,超级管理员', '20150921000226', null, '20150921000226', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('275', '休学', '4', 'student_status', '休学', '4', '2,超级管理员', '20150921000256', null, '20150921000256', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('276', '开除', '5', 'student_status', '开除', '5', '2,超级管理员', '20150921000317', null, '20150921000317', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('277', '应届初中毕业生', '1', 'student_enrollObj', '', '1', '2,超级管理员', '20150921182643', null, '20150921182643', null, '1', '1');
INSERT INTO `sys_dict` VALUES ('278', '应届高中毕业生', '2', 'student_enrollObj', '应届高中毕业生', '2', '2,超级管理员', '20150921182730', null, '20150921182730', null, '1', '1');
INSERT INTO `sys_dict` VALUES ('279', '公告', '1', 'article_type', '', '1', '2,超级管理员', '20150921190254', null, '20150921190254', null, '1', '1');
INSERT INTO `sys_dict` VALUES ('280', '通知', '2', 'article_type', '', '2', '2,超级管理员', '20150921190304', null, '20150921190304', null, '1', '1');
INSERT INTO `sys_dict` VALUES ('281', '父子', '1', 'family_memeber_relation', '', '1', '2,超级管理员', '20150922164206', null, '20150922164206', null, '1', '1');
INSERT INTO `sys_dict` VALUES ('282', '父女', '2', 'family_memeber_relation', '', '2', '2,超级管理员', '20150922164224', null, '20150922164224', null, '1', '1');
INSERT INTO `sys_dict` VALUES ('283', '母子', '3', 'family_memeber_relation', '', '3', '2,超级管理员', '20150922164241', null, '20150922164241', null, '1', '1');
INSERT INTO `sys_dict` VALUES ('284', '母女', '4', 'family_memeber_relation', '', '4', '2,超级管理员', '20150922164301', null, '20150922164301', null, '1', '1');
INSERT INTO `sys_dict` VALUES ('285', '哥哥', '5', 'family_memeber_relation', '', '5', '2,超级管理员', '20150922164351', null, '20150922164351', null, '1', '1');
INSERT INTO `sys_dict` VALUES ('286', '弟弟', '6', 'family_memeber_relation', '', '6', '2,超级管理员', '20150922164412', null, '20150922164412', null, '1', '1');
INSERT INTO `sys_dict` VALUES ('287', '姐姐', '7', 'family_memeber_relation', '', '7', '2,超级管理员', '20150922164430', null, '20150922164430', null, '1', '1');
INSERT INTO `sys_dict` VALUES ('288', '妹妹', '8', 'family_memeber_relation', '', '8', '2,超级管理员', '20150922164444', null, '20150922164444', null, '1', '1');
INSERT INTO `sys_dict` VALUES ('289', '奖励', '1', 'type_common_type', '', '2', '2,超级管理员', '20150922164444', '', '20150922164444', '', '0', '0');
INSERT INTO `sys_dict` VALUES ('290', '惩罚', '0', 'type_common_type', '', '2', '2,超级管理员', '20150922164444', '', '20150922164444', '', '0', '1');
INSERT INTO `sys_dict` VALUES ('292', '入学年提前年数', '5', 'entrance_before', '入学年提前年数', '1', '2,超级管理员', '20151015202012', null, '20151015202012', null, '1', '1');
INSERT INTO `sys_dict` VALUES ('293', '入学年往后年数', '5', 'entrance_after', '入学年往后年数', '1', '2,超级管理员', '20151015202123', null, '20151015202123', null, '1', '1');
INSERT INTO `sys_dict` VALUES ('294', '班长', '1', 'class_cadre', '班长', '1', '2,超级管理员', '20151019142020', '2,超级管理员', '20151019175435', null, '1', '1');
INSERT INTO `sys_dict` VALUES ('295', '副班长', '2', 'class_cadre', '副班长', '2', '2,超级管理员', '20151019142111', '2,超级管理员', '20151019142212', null, '1', '1');
INSERT INTO `sys_dict` VALUES ('296', '学习委员', '3', 'class_cadre', '学习委员', '3', '2,超级管理员', '20151019142248', null, '20151019142248', null, '1', '1');
INSERT INTO `sys_dict` VALUES ('297', '学生会主席', '1', 'school_cadre', '学生会主席', '1', '2,超级管理员', '20151019142742', null, '20151019142742', null, '1', '1');
INSERT INTO `sys_dict` VALUES ('298', '学生会副主席', '2', 'school_cadre', '学生会副主席', '2', '2,超级管理员', '20151019143034', null, '20151019143034', null, '1', '1');
INSERT INTO `sys_dict` VALUES ('299', '文艺体育部部长', '3', 'school_cadre', '文艺体育部部长', '3', '2,超级管理员', '20151019143139', null, '20151019143139', null, '1', '1');
INSERT INTO `sys_dict` VALUES ('300', '平时成绩', '1', 'score_type', '作业成绩 月考成绩', '1', '2,超级管理员', '20160218165520', '2,超级管理员', '20160223135152', null, '1', '1');
INSERT INTO `sys_dict` VALUES ('301', '考试成绩', '2', 'score_type', '考试成绩包括 期中考试 和期末考试', '2', '2,超级管理员', '20160218165520', '2,超级管理员', '20160223135124', '', '1', '1');
INSERT INTO `sys_dict` VALUES ('302', '100分制', '1', 'point_system', '100分制', '1', '2,超级管理员', '20160218165520', '', '20160218165520', '', '1', '1');
INSERT INTO `sys_dict` VALUES ('303', '150分制', '2', 'point_system', '150分制', '2', '2,超级管理员', '20160218165520', '', '20160218165520', '', '1', '1');
INSERT INTO `sys_dict` VALUES ('304', '3', '3', '0', '', '1', null, null, null, null, null, '1', '1');
INSERT INTO `sys_dict` VALUES ('305', 'vvvvvvv', 'vvvvvvv', '222222', 'ssss', '1', null, null, null, null, null, '1', '1');
INSERT INTO `sys_dict` VALUES ('306', 'eeeee', 'eeeee', '222222', 'eeee', '2', null, null, null, null, null, '1', '1');
INSERT INTO `sys_dict` VALUES ('307', 'gggg', 'gggg', '222222', '222', '222', null, null, null, null, null, '1', '1');
INSERT INTO `sys_dict` VALUES ('308', 'hhh', 'hhh', '222222', 'hhhh', '5', null, null, null, null, null, '1', '1');
INSERT INTO `sys_dict` VALUES ('309', 'nnn', 'nnn', '222222', 'nnnn', '5', null, null, null, null, null, '1', '1');
INSERT INTO `sys_dict` VALUES ('310', 'ttt', 'tt', '222222', 'tt', '9', null, null, null, null, null, '1', '1');
INSERT INTO `sys_dict` VALUES ('311', 'qqqq', 'qq', '222222', 'q', '8', null, null, null, null, null, '1', '1');
INSERT INTO `sys_dict` VALUES ('312', '泸天华中学', 'p1', 'pugao', '', '1', null, null, null, null, null, '1', '1');
INSERT INTO `sys_dict` VALUES ('313', '叙永一中', 'p2', 'pugao', '', '1', null, null, null, null, null, '1', '1');
INSERT INTO `sys_dict` VALUES ('314', '叙永二中', 'p3', 'pugao', '', '1', null, null, null, null, null, '1', '1');
INSERT INTO `sys_dict` VALUES ('315', '泸县五中', 'p4', 'pugao', '', '1', null, null, null, null, null, '1', '1');
INSERT INTO `sys_dict` VALUES ('316', '泸县一中', 'p5', 'pugao', '', '1', null, null, null, null, null, '1', '1');
INSERT INTO `sys_dict` VALUES ('317', '其他普高', 'p0', 'pugao', '', '1', null, null, null, null, null, '1', '1');
INSERT INTO `sys_dict` VALUES ('318', '江南职高', 'z1', 'zhigao', '', '1', null, null, null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('319', '树风职高', 'z2', 'zhigao', '', '2', null, null, null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('320', '江阳职高', 'z3', 'zhigao', '', '3', null, null, null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('321', '叙永职高', 'z4', 'zhigao', '', '4', null, null, null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('322', '合江县菜坝职高', 'z5', 'zhigao', '', '5', null, null, null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('323', '古蔺县职业高中', 'z6', 'zhigao', '', '6', null, null, null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('324', '其它', 'z7', 'zhigao', '', '7', null, null, null, null, null, '0', '1');
INSERT INTO `sys_dict` VALUES ('325', '登录日志', '0', 'sys_log_type', '用户登录日志类型', '1', '1,超级管理员', '20160419101044', null, '20160419101044', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('326', '800V', '800V', 'voltage', '', '1', null, '20160623143855', null, '20160623143855', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('327', '220v', '220v', 'voltage', '', '2', null, '20160623143918', null, '20160623143918', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('328', '框架应用', '1', 'apptype', '', '1', null, '20160624114622', '2,超级管理员', '20160624114846', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('329', 'APK应用', '2', 'apptype', '', '2', null, '20160624114651', '2,超级管理员', '20160624114852', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('330', 'H5应用', '3', 'apptype', '', '3', null, '20160624114712', '2,超级管理员', '20160624114903', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('331', 'zz', 'zz', 'del_flag', '', '1', null, '20160627113442', null, '20160627113442', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('332', 'xx', 'xx', 'del_flag', '', '1', null, '20160627113442', null, '20160627113442', null, '0', '1');
INSERT INTO `sys_dict` VALUES ('333', 'cc', 'cc', 'del_flag', '', '2', null, '20160627113442', null, '20160627113442', null, '0', '1');

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `parent_id` bigint(20) NOT NULL COMMENT '父级编号',
  `parent_ids` varchar(2000) DEFAULT NULL COMMENT '所有父级编号',
  `code` varchar(100) DEFAULT NULL COMMENT '区域编码',
  `name` varchar(100) NOT NULL COMMENT '区域名称',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记(0活null 正常 1,删除)',
  `sort` int(11) DEFAULT '1',
  `status` varchar(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=80 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES ('1', '2', '0,2,', 'del_flag', '删除标记', '2,超级管理员', '2015-09-01 08:57:11', '2,超级管理员', '2015-09-01 08:57:45', '', '0', '1', '0');
INSERT INTO `sys_dict_type` VALUES ('2', '0', '0,', 'sys', '系统管理', '2,超级管理员', '2015-09-01 08:57:33', null, '2015-09-01 08:57:33', '', '0', '2', '1');
INSERT INTO `sys_dict_type` VALUES ('3', '2', '0,2,', 'prj_template_type', '代码模板', '2,超级管理员', '2015-09-01 08:58:31', null, '2015-09-01 08:58:31', '', '0', '1', '0');
INSERT INTO `sys_dict_type` VALUES ('6', '2', '0,2,', 'sys_user_type', '用户类型', '2,超级管理员', '2015-09-01 10:47:27', null, '2015-09-01 10:47:27', '', '0', '1', '1');
INSERT INTO `sys_dict_type` VALUES ('7', '2', '0,2,', 'sys_area_type', '区域类型', '2,超级管理员', '2015-09-01 10:54:59', null, '2015-09-01 10:54:59', '', '0', '1', '1');
INSERT INTO `sys_dict_type` VALUES ('8', '2', '0,2,', 'sys_office_grade', '机构等级', '2,超级管理员', '2015-09-01 10:55:45', null, '2015-09-01 10:55:45', '', '0', '6', '1');
INSERT INTO `sys_dict_type` VALUES ('9', '2', '0,2,', 'sys_data_scope', '数据范围', '2,超级管理员', '2015-09-01 10:55:58', null, '2015-09-01 10:55:58', '', '0', '1', '1');
INSERT INTO `sys_dict_type` VALUES ('10', '0', '0,', 'button', '按钮/标签', '2,超级管理员', '2015-09-01 10:57:14', null, '2015-09-01 10:57:14', '', '0', '1', '1');
INSERT INTO `sys_dict_type` VALUES ('11', '10', '0,10,', 'show_hide', '显示/隐藏', '2,超级管理员', '2015-09-01 10:57:32', null, '2015-09-01 10:57:32', '', '0', '1', '1');
INSERT INTO `sys_dict_type` VALUES ('12', '10', '0,10,', 'yes_no', '是/否', '2,超级管理员', '2015-09-01 10:58:02', null, '2015-09-01 10:58:02', '', '0', '1', '0');
INSERT INTO `sys_dict_type` VALUES ('13', '2', '0,2,', 'sys_office_type', '机构类型', '2,超级管理员', '2015-09-01 11:00:46', null, '2015-09-01 11:00:46', '', '0', '3', '1');
INSERT INTO `sys_dict_type` VALUES ('71', '0', '0,', 'where_abouts', '去向分类', '2,超级管理员', '2015-09-01 11:00:46', null, '2015-09-01 11:00:46', null, '1', '1', '1');
INSERT INTO `sys_dict_type` VALUES ('72', '71', '0,71,', 'in_school', '本校', '2,超级管理员', '2015-09-01 11:00:46', null, '2015-09-01 11:00:46', null, '1', '1', '1');
INSERT INTO `sys_dict_type` VALUES ('73', '71', '71,', 'zhigao', '职高', '2,超级管理员', '2015-09-01 11:00:46', null, '2015-09-01 11:00:46', '', '1', '1', '1');
INSERT INTO `sys_dict_type` VALUES ('74', '71', '71,', 'pugao', '普高', '2,超级管理员', '2015-09-01 11:00:46', null, '2015-09-01 11:00:46', '', '1', '1', '1');
INSERT INTO `sys_dict_type` VALUES ('75', '71', '0,71,', 'out_school', '辍学', '2,超级管理员', '2015-09-01 11:00:46', null, '2015-09-01 11:00:46', null, '1', '1', '1');
INSERT INTO `sys_dict_type` VALUES ('76', '2', 'null2,', 'sys_log_type', '日志类型', '1,超级管理员', '2016-04-19 10:09:37', '1,超级管理员', '2016-04-19 10:09:49', '', '0', '1', '1');
INSERT INTO `sys_dict_type` VALUES ('77', '0', 'null0,', 'base_info', '基础信息', null, '2016-06-23 14:36:26', null, '2016-06-23 14:36:47', '', '0', '1', '1');
INSERT INTO `sys_dict_type` VALUES ('78', '77', 'null77,', 'voltage', '电压等级', null, '2016-06-23 14:37:01', null, '2016-06-23 14:38:10', '', '0', '1', '1');
INSERT INTO `sys_dict_type` VALUES ('79', '2', 'null2,', 'apptype', '应用类型', '2,超级管理员', '2016-06-24 11:44:43', '2,超级管理员', '2016-06-24 11:44:54', '', '0', '1', '1');

-- ----------------------------
-- Table structure for sys_id_card
-- ----------------------------
DROP TABLE IF EXISTS `sys_id_card`;
CREATE TABLE `sys_id_card` (
  `ID` varchar(40) NOT NULL,
  `PROVINCE` varchar(10) DEFAULT NULL COMMENT '省代号',
  `CITY` varchar(10) DEFAULT NULL COMMENT '城市代号',
  `ZONE` varchar(200) DEFAULT NULL COMMENT '行政区代号',
  `NAME` varchar(200) DEFAULT NULL COMMENT '具体行政地址',
  `CREATE_BY` varchar(100) DEFAULT NULL COMMENT '创建人',
  `CREATE_DATE` bigint(20) DEFAULT NULL,
  `UPDATE_BY` varchar(100) DEFAULT NULL COMMENT '修改人',
  `UPDATE_DATE` bigint(20) DEFAULT NULL,
  `DEL_FLAG` varchar(1) DEFAULT NULL COMMENT '删除标识',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='行政区地址';

-- ----------------------------
-- Records of sys_id_card
-- ----------------------------

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` varchar(40) NOT NULL,
  `type` char(1) DEFAULT '1' COMMENT '日志类型',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` bigint(20) DEFAULT NULL,
  `remote_addr` varchar(255) DEFAULT NULL COMMENT '操作IP地址',
  `user_agent` varchar(255) DEFAULT NULL COMMENT '用户代理',
  `request_uri` varchar(255) DEFAULT NULL COMMENT '请求URI',
  `method` varchar(100) DEFAULT NULL COMMENT '操作方式',
  `params` text COMMENT '操作提交的数据',
  `exception` text COMMENT '异常信息',
  `description` text COMMENT '描述',
  PRIMARY KEY (`id`),
  KEY `sys_log_create_by` (`create_by`) USING BTREE,
  KEY `sys_log_request_uri` (`request_uri`) USING BTREE,
  KEY `sys_log_type` (`type`) USING BTREE,
  KEY `sys_log_create_date` (`create_date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日志表';

-- ----------------------------
-- Records of sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` varchar(40) NOT NULL,
  `name` varchar(64) DEFAULT NULL COMMENT '资源名称',
  `common` char(1) DEFAULT '0' COMMENT '是否是公共资源(0.不是 1.是)',
  `icon` varchar(64) DEFAULT NULL COMMENT '图标',
  `sort` int(11) DEFAULT '1' COMMENT '排序号',
  `parent_id` varchar(40) DEFAULT NULL,
  `type` char(1) DEFAULT '0' COMMENT '类型(0.菜单 1.按钮)',
  `url` varchar(255) DEFAULT NULL COMMENT '链接',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `status` char(1) DEFAULT '0' COMMENT '状态(0.正常 1.禁用)',
  `parent_ids` varchar(2000) DEFAULT NULL COMMENT '父级集合',
  `create_date` bigint(20) DEFAULT NULL,
  `update_date` bigint(20) DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `del_flag` char(1) DEFAULT '0',
  `permission_str` varchar(100) DEFAULT NULL,
  `module` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('adb37bd4c22f4f3489158ecaf3d4ccca', '后台管理', '0', 'fa fa-cogs', '100', '0', '0', '', '', '1', '0,', null, '1467454423313', null, null, '0', null, 'sys');
INSERT INTO `sys_menu` VALUES ('b883a3ab077387efc8c44d1801d614e1', '菜单配置', '0', 'fa fa-list', '1', 'adb37bd4c22f4f3489158ecaf3d4ccca', '0', 'sys/menu', '', '1', 'adb37bd4c22f4f3489158ecaf3d4ccca,', null, '1467456136349', null, null, '0', null, 'sys');
INSERT INTO `sys_menu` VALUES ('c1469e5325d12cb2a3ef7b9db73a1417', '日志查询', '1', '', '9', 'adb37bd4c22f4f3489158ecaf3d4ccca', '0', 'sys/log', '', '1', 'adb37bd4c22f4f3489158ecaf3d4ccca,', '1467456284953', '1467605173250', null, 'c8ecffc19afa8645cc0d839e319b66e1,超级管理员', '0', null, null);
INSERT INTO `sys_menu` VALUES ('b1dedfec8e8107da9db18dd718a84d8d', '基本信息', '1', 'fa fa-database', '1', '0', '0', '', '', '1', '0,', '1467454392714', '1467681788229', null, null, '0', null, null);
INSERT INTO `sys_menu` VALUES ('b7807dd600e608e492de748594be76e6', '应用管理', '1', 'fa fa-fire', '5', '0', '0', '', '', '1', '0,', '1467454392714', '1467681803936', null, null, '0', null, null);
INSERT INTO `sys_menu` VALUES ('eb35e909861afb068f370cf6ec93fe59', '系统监控', '1', 'fa fa-dashboard', '101', '0', '0', '', '', '1', '0,', '1467454392729', '1467681842408', null, 'c8ecffc19afa8645cc0d839e319b66e1,超级管理员', '0', null, null);
INSERT INTO `sys_menu` VALUES ('de9a454878f72efaef4bb633e63e40a6', '单位管理', '1', '', '4', 'adb37bd4c22f4f3489158ecaf3d4ccca', '0', 'sys/office', '', '1', 'adb37bd4c22f4f3489158ecaf3d4ccca,', '1467455922617', '1467458368820', 'c8ecffc19afa8645cc0d839e319b66e1,超级管理员', null, '0', null, null);
INSERT INTO `sys_menu` VALUES ('941f4012c06492effc61509358993cf6', '用户管理', '1', '', '6', 'adb37bd4c22f4f3489158ecaf3d4ccca', '0', 'sys/user', '', '1', 'adb37bd4c22f4f3489158ecaf3d4ccca,', '1467455922617', '1467605186378', 'c8ecffc19afa8645cc0d839e319b66e1,超级管理员', 'c8ecffc19afa8645cc0d839e319b66e1,超级管理员', '0', null, null);
INSERT INTO `sys_menu` VALUES ('8d7f33eacfadee9389486a1a8a2fcbfe', '区域管理', '1', '', '2', 'adb37bd4c22f4f3489158ecaf3d4ccca', '0', 'sys/area', '', '0', 'adb37bd4c22f4f3489158ecaf3d4ccca,', '1467455922633', '1467619746508', 'c8ecffc19afa8645cc0d839e319b66e1,超级管理员', null, '0', null, null);
INSERT INTO `sys_menu` VALUES ('a3cdbaa523a4ed69b9c06621cc24c51b', '数据字典', '1', '', '3', 'adb37bd4c22f4f3489158ecaf3d4ccca', '0', 'sys/dict', '', '1', 'adb37bd4c22f4f3489158ecaf3d4ccca,', '1467455922633', '1467458351670', 'c8ecffc19afa8645cc0d839e319b66e1,超级管理员', null, '0', null, null);
INSERT INTO `sys_menu` VALUES ('8f58ef8b40b73af8c2123459c477a695', '单位类型', '1', '', '8', 'adb37bd4c22f4f3489158ecaf3d4ccca', '0', 'sys/unit', '', '1', 'adb37bd4c22f4f3489158ecaf3d4ccca,', '1467455947386', '1467456344248', 'c8ecffc19afa8645cc0d839e319b66e1,超级管理员', null, '0', null, null);
INSERT INTO `sys_menu` VALUES ('8166a2de1c1ec197c24cf4b6feae99db', '角色管理', '1', '', '5', 'adb37bd4c22f4f3489158ecaf3d4ccca', '0', 'sys/role', '', '1', 'adb37bd4c22f4f3489158ecaf3d4ccca,', '1467456046144', '1467458382421', null, null, '0', null, null);
INSERT INTO `sys_menu` VALUES ('f7ae1b6ca991f97a9d4784fd3e1e2003', 'jvm监控', '1', 'fa fa-line-chart', '1', 'eb35e909861afb068f370cf6ec93fe59', '0', 'sys/monitor/jvm', '', '1', 'eb35e909861afb068f370cf6ec93fe59,', '1467458570444', '1467682117184', 'c8ecffc19afa8645cc0d839e319b66e1,超级管理员', 'c8ecffc19afa8645cc0d839e319b66e1,超级管理员', '0', null, null);
INSERT INTO `sys_menu` VALUES ('f0ebc5eb3895cb32c311eec7491d6d7c', '执行sql', '1', 'fa fa-ellppmis-h', '2', 'eb35e909861afb068f370cf6ec93fe59', '0', 'sys/monitor/db/sql', '', '1', 'eb35e909861afb068f370cf6ec93fe59,', '1467458570446', '1467682127289', 'c8ecffc19afa8645cc0d839e319b66e1,超级管理员', 'c8ecffc19afa8645cc0d839e319b66e1,超级管理员', '0', null, null);
INSERT INTO `sys_menu` VALUES ('fafe43bed6487367b1e63592a9951b66', '数据库监控', '1', 'fa fa-paper-plane-o', '3', 'eb35e909861afb068f370cf6ec93fe59', '0', 'sys/monitor/db/druid', '', '1', 'eb35e909861afb068f370cf6ec93fe59,', '1467458570446', '1467682138135', 'c8ecffc19afa8645cc0d839e319b66e1,超级管理员', 'c8ecffc19afa8645cc0d839e319b66e1,超级管理员', '0', null, null);

-- ----------------------------
-- Table structure for sys_office
-- ----------------------------
DROP TABLE IF EXISTS `sys_office`;
CREATE TABLE `sys_office` (
  `id` varchar(40) NOT NULL,
  `parent_id` varchar(40) DEFAULT NULL,
  `parent_ids` varchar(2000) DEFAULT NULL COMMENT '所有父级编号',
  `area_id` varchar(40) DEFAULT NULL,
  `code` varchar(100) DEFAULT NULL COMMENT '区域编码',
  `name` varchar(100) DEFAULT NULL COMMENT '机构名称',
  `type` varchar(40) DEFAULT NULL,
  `grade` char(1) DEFAULT NULL COMMENT '机构等级',
  `address` varchar(255) DEFAULT NULL COMMENT '联系地址',
  `zip_code` varchar(100) DEFAULT NULL COMMENT '邮政编码',
  `master` varchar(100) DEFAULT NULL COMMENT '负责人',
  `phone` varchar(200) DEFAULT NULL COMMENT '电话',
  `fax` varchar(200) DEFAULT NULL COMMENT '传真',
  `email` varchar(200) DEFAULT NULL COMMENT '邮箱',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `icon` varchar(64) DEFAULT NULL,
  `create_date` bigint(20) DEFAULT NULL,
  `update_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sys_office_parent_id` (`parent_id`) USING BTREE,
  KEY `sys_office_parent_ids` (`parent_ids`(255)) USING BTREE,
  KEY `sys_office_del_flag` (`del_flag`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='机构表';

-- ----------------------------
-- Records of sys_office
-- ----------------------------
INSERT INTO `sys_office` VALUES ('c5a9615ce7b8dbbbd471f4fb728d3a22', null, null, null, 'SC001', '四川电力公司', '801105877e125147fca22e1b94555126', null, 'aaaaaaaaa', 'aaaaaaaaa', 'aaaaaaaaa', 'aaaaaaaaa', null, 'aaaaaaaaa', null, null, null, '0', null, '1467600063329', '1468392378620');
INSERT INTO `sys_office` VALUES ('96d0d253efc2d649fb1dd77c744b0abf', null, null, null, 'YN001', '云南电力公司', '801105877e125147fca22e1b94555126', null, 'bbbbbbb', 'bbbbbbb', 'bbbbbbb', 'bbbbbbb', null, 'bbbbbbb', null, null, null, '0', null, '1467600079939', '1468392373599');
INSERT INTO `sys_office` VALUES ('c8a8101944e79ebf8b4189b8378076c9', null, null, null, 'GZ001', '贵州电力公司', '801105877e125147fca22e1b94555126', null, 'cccccccccc', 'cccccccccc', 'cccccccccc', 'cccccccccc', null, 'cccccccccc', null, null, null, '0', null, '1467600099044', '1468392397155');
INSERT INTO `sys_office` VALUES ('d9d0ba70b10ea957a285e20af51a2f92', null, null, null, 'SC002', '四川设计院', '9a19d494bdc79d49a9ba25189a5a59dd', null, 'qqqqq', 'qqqq', 'qqq', 'qq', null, 'qqq', null, null, null, '0', null, '1467684718266', '1468392416136');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(40) NOT NULL,
  `office_id` varchar(40) DEFAULT NULL,
  `name` varchar(100) NOT NULL COMMENT '角色名称',
  `data_scope` char(1) DEFAULT NULL COMMENT '数据范围',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` bigint(20) DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` bigint(20) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `level` varchar(10) NOT NULL DEFAULT '4' COMMENT '角色等级',
  PRIMARY KEY (`id`),
  KEY `sys_role_del_flag` (`del_flag`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('bb7eaca6c1da68de8b2b8e98f8a4d53f', null, '普通用户', '2', null, '1467683031741', null, '1467683031741', '', '0', '4');
INSERT INTO `sys_role` VALUES ('c538fb599c9d171fd74e5ab72877d764', null, '管理员', '2', null, '1467597988579', null, '1467681101084', '11', '0', '4');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(40) NOT NULL AUTO_INCREMENT,
  `role_id` varchar(40) DEFAULT NULL,
  `menu_id` varchar(40) DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `create_date` bigint(20) DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `update_date` bigint(20) DEFAULT NULL,
  `del_flag` char(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1502 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('1494', 'c538fb599c9d171fd74e5ab72877d764', 'b1dedfec8e8107da9db18dd718a84d8d', null, null, null, null, '0');
INSERT INTO `sys_role_menu` VALUES ('1495', 'c538fb599c9d171fd74e5ab72877d764', 'b0d360f60ac9beeaeceea5bf3bda4a31', null, null, null, null, '0');
INSERT INTO `sys_role_menu` VALUES ('1496', 'c538fb599c9d171fd74e5ab72877d764', '97d362e33089fbfc9770763d19524451', null, null, null, null, '0');
INSERT INTO `sys_role_menu` VALUES ('1497', 'c538fb599c9d171fd74e5ab72877d764', 'b9bdbb5a4a09cad08db7aebe43c3f44e', null, null, null, null, '0');
INSERT INTO `sys_role_menu` VALUES ('1498', 'c538fb599c9d171fd74e5ab72877d764', 'e3576e9ce0458b2dde1d94cc02082b98', null, null, null, null, '0');
INSERT INTO `sys_role_menu` VALUES ('1499', 'c538fb599c9d171fd74e5ab72877d764', 'c3fac6fc5a08a6dcbe9bb57a81585722', null, null, null, null, '0');
INSERT INTO `sys_role_menu` VALUES ('1500', 'c538fb599c9d171fd74e5ab72877d764', 'e353afb23dddac1281f07ee44efd4cf3', null, null, null, null, '0');
INSERT INTO `sys_role_menu` VALUES ('1501', 'c538fb599c9d171fd74e5ab72877d764', '9f8d7a5699bd7a87a1b17485a7a80b7b', null, null, null, null, '0');

-- ----------------------------
-- Table structure for sys_role_office
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_office`;
CREATE TABLE `sys_role_office` (
  `role_id` varchar(40) DEFAULT NULL,
  `office_id` varchar(40) DEFAULT NULL,
  `id` varchar(40) NOT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `del_flag` char(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='角色-机构';

-- ----------------------------
-- Records of sys_role_office
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(40) NOT NULL,
  `unit_id` varchar(40) DEFAULT NULL COMMENT '所在单位',
  `username` varchar(50) NOT NULL COMMENT '登录名',
  `password` varchar(40) NOT NULL COMMENT '密码',
  `name` varchar(100) DEFAULT NULL COMMENT '姓名',
  `sex` varchar(10) DEFAULT NULL,
  `email` varchar(200) DEFAULT '' COMMENT '邮箱',
  `phone` varchar(20) DEFAULT NULL COMMENT '电话',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机',
  `user_type` char(1) DEFAULT '0' COMMENT '用户类型(0.普通 1.系统超级管理员)',
  `login_ip` varchar(30) DEFAULT NULL COMMENT '最后登陆IP',
  `login_date` bigint(20) DEFAULT NULL COMMENT '最后登陆时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` bigint(20) DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` bigint(20) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `status` int(1) DEFAULT '0' COMMENT '0-禁用，1-启用，2-待审核，3-审核未通过',
  `last_login_date` bigint(20) DEFAULT NULL,
  `last_login_ip` varchar(100) DEFAULT NULL,
  `last_login_error_msg` varchar(200) DEFAULT NULL COMMENT '上一次登录错误消息',
  `error_count` int(11) DEFAULT '0' COMMENT '错误次数',
  `next_login_date` bigint(20) DEFAULT NULL,
  `next_mod_pwd_date` bigint(20) DEFAULT NULL,
  `hx_uid` varchar(40) DEFAULT '' COMMENT '环信UID',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像地址',
  `address` text COMMENT '地址',
  `birth_day` varchar(255) DEFAULT NULL COMMENT '出生年月',
  `marital_status` varchar(255) DEFAULT NULL COMMENT '婚姻状况',
  `other_contact` varchar(255) DEFAULT NULL COMMENT '紧急联系人',
  `graduation_date` varchar(255) DEFAULT NULL COMMENT '毕业时间',
  `home_address` text COMMENT '家庭住址',
  `sign` text COMMENT '签名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('c8ecffc19afa8645cc0d839e319b66e1', '96d0d253efc2d649fb1dd77c744b0abf', 'admin', '7b2e9f54cdff413fcde01f330af6896c3cd7e6cd', '超级管理员', null, '', null, null, '1', null, null, null, null, null, null, null, '0', '1', null, null, null, '0', null, null, '', null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(40) NOT NULL AUTO_INCREMENT,
  `role_id` varchar(40) DEFAULT NULL,
  `user_id` varchar(40) DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `create_date` bigint(20) DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `update_date` bigint(20) DEFAULT NULL,
  `del_flag` char(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('16', 'c538fb599c9d171fd74e5ab72877d764', 'a26acb5782c66ef791efe748f9895c16', null, null, null, null, '0');
INSERT INTO `sys_user_role` VALUES ('17', 'c538fb599c9d171fd74e5ab72877d764', 'c8ecffc19afa8645cc0d839e319b66e1', null, null, null, null, '0');
INSERT INTO `sys_user_role` VALUES ('18', 'c538fb599c9d171fd74e5ab72877d764', 'cc0d6bcc1fd3a606c1815b726fc863c8', null, null, null, null, '0');
INSERT INTO `sys_user_role` VALUES ('19', 'c538fb599c9d171fd74e5ab72877d764', 'e93e7870de370ff5b522cd68f23c20ad', null, null, null, null, '0');

-- ----------------------------
-- Table structure for task_definition
-- ----------------------------
DROP TABLE IF EXISTS `task_definition`;
CREATE TABLE `task_definition` (
  `id` varchar(40) NOT NULL,
  `name` varchar(500) DEFAULT NULL,
  `cron` varchar(200) DEFAULT NULL,
  `bean_class` varchar(200) DEFAULT NULL,
  `bean_name` varchar(200) DEFAULT NULL,
  `method_name` varchar(200) DEFAULT NULL,
  `is_start` tinyint(1) DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of task_definition
-- ----------------------------
