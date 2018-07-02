/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50720
 Source Host           : localhost:3306
 Source Schema         : framework

 Target Server Type    : MySQL
 Target Server Version : 50720
 File Encoding         : 65001

 Date: 02/07/2018 10:06:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for log_adm_model
-- ----------------------------
DROP TABLE IF EXISTS `log_adm_model`;
CREATE TABLE `log_adm_model`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_time` datetime(0) NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `updata_time` datetime(0) NULL DEFAULT NULL,
  `adm_event` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `adm_model` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `adm_opt_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `note` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for schedule_job
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job`;
CREATE TABLE `schedule_job`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cron_expression` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `job_group` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `job_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `job_status` int(11) NULL DEFAULT NULL,
  `note` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_67tap4qhtscm3q6wvvpwou36g`(`job_group`) USING BTREE,
  UNIQUE INDEX `UK_edewmwn4jbwdr1d6v9v4m2c42`(`job_name`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_function
-- ----------------------------
DROP TABLE IF EXISTS `sys_function`;
CREATE TABLE `sys_function`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_time` datetime(0) NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `updata_time` datetime(0) NULL DEFAULT NULL,
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_show_left` bit(1) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sort` int(11) NULL DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_f5usmox0bmk2bx3dso74eye2e`(`name`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_time` datetime(0) NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `updata_time` datetime(0) NULL DEFAULT NULL,
  `available` bit(1) NULL DEFAULT NULL,
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `parent_id` bigint(20) NULL DEFAULT NULL,
  `permission` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `resource_type` enum('menu','button') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sort` int(11) NULL DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `level` int(11) NULL DEFAULT NULL,
  `show_left` bit(1) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKq4uf7xhrxa5dnbpf4a47rqvha`(`parent_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 36 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (6, NULL, NULL, NULL, b'1', NULL, '角色管理', NULL, '', 'menu', 1, '', 0, b'1');
INSERT INTO `sys_permission` VALUES (7, NULL, NULL, NULL, b'1', NULL, '列表', 6, '', 'menu', 1, '/sys/role/rolelist', 1, b'1');
INSERT INTO `sys_permission` VALUES (5, '2000-01-01 00:00:00', '删除', '2000-01-01 00:00:00', b'1', NULL, '权限删除', 1, 'sys:permission:remove', 'button', 3, '/sys/permission/remove', 1, b'0');
INSERT INTO `sys_permission` VALUES (4, '2000-01-01 00:00:00', '跳转', '2000-01-01 00:00:00', b'1', NULL, '跳转添加/修改', 1, 'sys:permission:modify', 'button', 2, '/sys/permission/modify', 1, b'0');
INSERT INTO `sys_permission` VALUES (3, '2000-01-01 00:00:00', '添加', '2000-01-01 00:00:00', b'1', NULL, '权限添加', 1, 'sys:permission:save', 'button', 1, '/sys/permission/save', 1, b'0');
INSERT INTO `sys_permission` VALUES (1, '2000-01-01 00:00:00', '权限', '2000-01-01 00:00:00', b'1', NULL, '权限管理', NULL, '', 'menu', 0, '', 0, b'1');
INSERT INTO `sys_permission` VALUES (2, '2000-01-01 00:00:00', '列表', '2000-01-01 00:00:00', b'1', NULL, '权限列表', 1, 'sys:permission:list', 'menu', 0, '/sys/permission/list', 1, b'1');
INSERT INTO `sys_permission` VALUES (9, NULL, NULL, NULL, b'1', NULL, '跳转', 6, '', 'button', 2, '/sys/role/modify', 1, b'0');
INSERT INTO `sys_permission` VALUES (29, NULL, NULL, NULL, b'1', NULL, '用户管理', NULL, '/sys/user/userList', 'menu', 2, '/sys/user/userList', 0, b'1');
INSERT INTO `sys_permission` VALUES (30, NULL, NULL, NULL, b'1', NULL, '列表', 29, '/sys/user/userList', 'menu', 0, '/sys/user/userList', 1, b'1');
INSERT INTO `sys_permission` VALUES (31, NULL, NULL, NULL, b'1', NULL, '设置角色', 29, '/sys/user/setrole', 'menu', 1, '/sys/user/setrole', 1, b'0');
INSERT INTO `sys_permission` VALUES (32, NULL, NULL, NULL, b'1', NULL, '保存', 29, '/sys/user/save', 'menu', 2, '/sys/user/save', 1, b'0');
INSERT INTO `sys_permission` VALUES (33, NULL, NULL, NULL, b'1', NULL, '更新', 29, '/sys/user/up', 'menu', 3, '/sys/user/up', 1, b'0');
INSERT INTO `sys_permission` VALUES (34, NULL, NULL, NULL, b'1', NULL, '删除', 29, '/sys/user/remove', 'menu', 4, '/sys/user/remove', 1, b'0');
INSERT INTO `sys_permission` VALUES (35, NULL, NULL, NULL, b'1', NULL, '重置密码', 29, '/sys/user/resetpwd', 'button', 5, '/sys/user/resetpwd', 1, b'0');

-- ----------------------------
-- Table structure for sys_permission_children
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission_children`;
CREATE TABLE `sys_permission_children`  (
  `sys_permission_id` bigint(20) NOT NULL,
  `children_id` bigint(20) NOT NULL,
  UNIQUE INDEX `UK_pqyxmmlax3ko3u62t5cmb4pm2`(`children_id`) USING BTREE,
  INDEX `FK64r036g9bl3c8brn7ha3gscfy`(`sys_permission_id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of sys_permission_children
-- ----------------------------
INSERT INTO `sys_permission_children` VALUES (6, 9);
INSERT INTO `sys_permission_children` VALUES (6, 7);
INSERT INTO `sys_permission_children` VALUES (1, 5);
INSERT INTO `sys_permission_children` VALUES (1, 4);
INSERT INTO `sys_permission_children` VALUES (1, 3);
INSERT INTO `sys_permission_children` VALUES (1, 2);
INSERT INTO `sys_permission_children` VALUES (29, 34);
INSERT INTO `sys_permission_children` VALUES (29, 32);
INSERT INTO `sys_permission_children` VALUES (29, 30);
INSERT INTO `sys_permission_children` VALUES (29, 31);
INSERT INTO `sys_permission_children` VALUES (29, 33);
INSERT INTO `sys_permission_children` VALUES (29, 35);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_time` datetime(0) NULL DEFAULT NULL,
  `updata_time` datetime(0) NULL DEFAULT NULL,
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `available` bit(1) NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_plpigyqwsqfn7mn66npgf9ftp`(`code`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '2000-01-01 00:00:00', '2000-01-01 00:00:00', 'admin', '管理员', b'1', '这是系统管理员');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `permission_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  INDEX `FK9q28ewrhntqeipl1t04kh1be7`(`role_id`) USING BTREE,
  INDEX `FKomxrs8a388bknvhjokh440waq`(`permission_id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_time` datetime(0) NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `updata_time` datetime(0) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `salt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `state` tinyint(4) NOT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_51bvuyvihefoh4kp5syh2jpi4`(`username`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, NULL, NULL, NULL, '111', '5pJ0LHPSATR06/7ZBgdrp3CygvdWw6aR7cRtFXK1z3DDjw/bXLk5/Wx6EZ319Te8zNLm14ZVGCNrDtq/f4K5FQ==', NULL, 0, 'admin');
INSERT INTO `sys_user` VALUES (4, NULL, NULL, NULL, '32222', 'kCNbuDeudNt3lXqruiqhrX5b6bpmcPnmYuXxUBIi66q/xhciyNmaan18QXDqdlfXdvGgarkob9HSjHNxI7OkUg==', NULL, 0, '3');
INSERT INTO `sys_user` VALUES (5, NULL, NULL, NULL, '3333', 'IYrjg3Tm9U0bo7lBrUcUeIpWLlNPmKEu99LJ3IsKj8BwJUMN5vL+IyTcXdqV3bDM3Lk29/vyvLXwXZ0dyXuD+g==', NULL, 1, '3333');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `uid` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  INDEX `FKhh52n8vd4ny9ff4x9fb8v65qx`(`role_id`) USING BTREE,
  INDEX `FKput17v9wwg8wiukw8ykroaaag`(`uid`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);

SET FOREIGN_KEY_CHECKS = 1;
