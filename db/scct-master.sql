/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80032
 Source Host           : localhost:3306
 Source Schema         : scct-master

 Target Server Type    : MySQL
 Target Server Version : 80032
 File Encoding         : 65001

 Date: 16/11/2023 13:29:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for infra_data_source_config
-- ----------------------------
DROP TABLE IF EXISTS `infra_data_source_config`;
CREATE TABLE `infra_data_source_config`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键编号',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '参数名称',
  `url` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数据源连接',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '密码',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '数据源配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of infra_data_source_config
-- ----------------------------
INSERT INTO `infra_data_source_config` VALUES (1, 'tenant-a', 'jdbc:mysql://127.0.0.1:3306/ruoyi-vue-pro-tenant-a?allowMultiQueries=true&useUnicode=true&useSSL=false&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&autoReconnect=true&nullCatalogMeansCurrent=true', 'root', 'epmpRAaR2T0FWW9J6yYSBQ==', '1', '2022-11-12 08:57:37', '1', '2023-11-03 11:14:54', b'0');
INSERT INTO `infra_data_source_config` VALUES (12, 'tenant-b', 'jdbc:mysql://127.0.0.1:3306/ruoyi-vue-pro-tenant-b?allowMultiQueries=true&useUnicode=true&useSSL=false&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&autoReconnect=true&nullCatalogMeansCurrent=true', 'root', 'epmpRAaR2T0FWW9J6yYSBQ==', '1', '2023-02-27 22:18:54', '1', '2023-11-03 14:40:34', b'0');
INSERT INTO `infra_data_source_config` VALUES (13, 'tenant-c', 'jdbc:mysql://127.0.0.1:3306/ruoyi-vue-pro-tenant-c?allowMultiQueries=true&useUnicode=true&useSSL=false&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&autoReconnect=true&nullCatalogMeansCurrent=true', 'root', 'epmpRAaR2T0FWW9J6yYSBQ==', '1', '2023-11-03 11:15:54', '1', '2023-11-03 11:36:47', b'0');
INSERT INTO `infra_data_source_config` VALUES (15, 'tenant-d', 'jdbc:mysql://127.0.0.1:3306/ruoyi-vue-pro-tenant-d?allowMultiQueries=true&useUnicode=true&useSSL=false&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&autoReconnect=true&nullCatalogMeansCurrent=true', 'root', 'epmpRAaR2T0FWW9J6yYSBQ==', NULL, '2023-11-09 11:06:57', NULL, '2023-11-09 11:06:57', b'0');
INSERT INTO `infra_data_source_config` VALUES (16, 'tenant-e', 'jdbc:mysql://127.0.0.1:3306/ruoyi-vue-pro-tenant-e?allowMultiQueries=true&useUnicode=true&useSSL=false&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&autoReconnect=true&nullCatalogMeansCurrent=true', 'root', 'epmpRAaR2T0FWW9J6yYSBQ==', NULL, '2023-11-09 11:24:04', NULL, '2023-11-09 11:24:04', b'0');
INSERT INTO `infra_data_source_config` VALUES (17, 'tenant-test11', 'jdbc:mysql://182.92.7.143:3306/test11?allowMultiQueries=true&useUnicode=true&useSSL=false&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&autoReconnect=true&nullCatalogMeansCurrent=true', 'huwentao', 'qzacSebYsurabxuukGydSQ==', NULL, '2023-11-09 11:34:12', NULL, '2023-11-09 11:34:12', b'0');

-- ----------------------------
-- Table structure for system_login_log
-- ----------------------------
DROP TABLE IF EXISTS `system_login_log`;
CREATE TABLE `system_login_log`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `log_type` int(0) NOT NULL COMMENT '日志类型',
  `trace_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '链路追踪编号',
  `user_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '用户编号',
  `user_type` tinyint(0) NOT NULL DEFAULT 0 COMMENT '用户类型',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '用户账号',
  `result` tinyint(0) NOT NULL COMMENT '登陆结果',
  `user_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户 IP',
  `user_agent` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '浏览器 UA',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2633 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '系统访问记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_login_log
-- ----------------------------
INSERT INTO `system_login_log` VALUES (2628, 100, '', 1, 1, 'admin', 10, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36', NULL, '2023-11-08 11:00:43', NULL, '2023-11-08 11:00:43', b'0', 0);
INSERT INTO `system_login_log` VALUES (2629, 100, '', 1, 1, 'admin', 10, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36', NULL, '2023-11-08 11:00:52', NULL, '2023-11-08 11:00:52', b'0', 0);
INSERT INTO `system_login_log` VALUES (2630, 100, '', 1, 1, 'admin', 0, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36', NULL, '2023-11-08 11:02:21', NULL, '2023-11-08 11:02:21', b'0', 0);
INSERT INTO `system_login_log` VALUES (2631, 100, '', 1, 1, 'admin', 0, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36', NULL, '2023-11-08 11:07:10', NULL, '2023-11-08 11:07:10', b'0', 0);
INSERT INTO `system_login_log` VALUES (2632, 100, '', 1, 1, 'admin', 0, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36', NULL, '2023-11-08 11:07:14', NULL, '2023-11-08 11:07:14', b'0', 0);
INSERT INTO `system_login_log` VALUES (2633, 100, '', 1702284907614134273, 1, 'admin', 0, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36', NULL, '2023-11-08 11:08:42', NULL, '2023-11-08 11:08:42', b'0', 0);

-- ----------------------------
-- Table structure for system_menu
-- ----------------------------
DROP TABLE IF EXISTS `system_menu`;
CREATE TABLE `system_menu`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜单名称',
  `permission` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '权限标识',
  `type` tinyint(0) NOT NULL COMMENT '菜单类型',
  `sort` int(0) NOT NULL DEFAULT 0 COMMENT '显示顺序',
  `parent_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '父菜单ID',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '路由地址',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '组件路径',
  `component_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '组件名',
  `status` tinyint(0) NOT NULL DEFAULT 0 COMMENT '菜单状态',
  `visible` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否可见',
  `keep_alive` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否缓存',
  `always_show` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否总是显示',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2397 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_menu
-- ----------------------------

-- ----------------------------
-- Table structure for system_role
-- ----------------------------
DROP TABLE IF EXISTS `system_role`;
CREATE TABLE `system_role`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色名称',
  `code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色权限字符串',
  `sort` int(0) NOT NULL COMMENT '显示顺序',
  `data_scope` tinyint(0) NOT NULL DEFAULT 1 COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `data_scope_dept_ids` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '数据范围(指定部门数组)',
  `status` tinyint(0) NOT NULL COMMENT '角色状态（0正常 1停用）',
  `type` tinyint(0) NOT NULL COMMENT '角色类型',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 130 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_role
-- ----------------------------

-- ----------------------------
-- Table structure for system_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `system_role_menu`;
CREATE TABLE `system_role_menu`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '自增编号',
  `role_id` bigint(0) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(0) NOT NULL COMMENT '菜单ID',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3396 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_role_menu
-- ----------------------------
INSERT INTO `system_role_menu` VALUES (3198, 121, 1, NULL, '2023-11-15 17:12:00', NULL, '2023-11-15 17:12:00', b'0', 2207);
INSERT INTO `system_role_menu` VALUES (3199, 121, 1036, NULL, '2023-11-15 17:12:00', NULL, '2023-11-15 17:12:00', b'0', 2207);
INSERT INTO `system_role_menu` VALUES (3200, 121, 1037, NULL, '2023-11-15 17:12:00', NULL, '2023-11-15 17:12:00', b'0', 2207);
INSERT INTO `system_role_menu` VALUES (3201, 121, 1038, NULL, '2023-11-15 17:12:00', NULL, '2023-11-15 17:12:00', b'0', 2207);
INSERT INTO `system_role_menu` VALUES (3202, 121, 1039, NULL, '2023-11-15 17:12:00', NULL, '2023-11-15 17:12:00', b'0', 2207);
INSERT INTO `system_role_menu` VALUES (3203, 121, 100, NULL, '2023-11-15 17:12:00', NULL, '2023-11-15 17:12:00', b'0', 2207);
INSERT INTO `system_role_menu` VALUES (3204, 121, 101, NULL, '2023-11-15 17:12:00', NULL, '2023-11-15 17:12:00', b'0', 2207);
INSERT INTO `system_role_menu` VALUES (3205, 121, 102, NULL, '2023-11-15 17:12:00', NULL, '2023-11-15 17:12:00', b'0', 2207);
INSERT INTO `system_role_menu` VALUES (3206, 121, 1063, NULL, '2023-11-15 17:12:00', NULL, '2023-11-15 17:12:00', b'0', 2207);
INSERT INTO `system_role_menu` VALUES (3207, 121, 103, NULL, '2023-11-15 17:12:00', NULL, '2023-11-15 17:12:00', b'0', 2207);
INSERT INTO `system_role_menu` VALUES (3208, 121, 1064, NULL, '2023-11-15 17:12:00', NULL, '2023-11-15 17:12:00', b'0', 2207);
INSERT INTO `system_role_menu` VALUES (3209, 121, 1001, NULL, '2023-11-15 17:12:00', NULL, '2023-11-15 17:12:00', b'0', 2207);
INSERT INTO `system_role_menu` VALUES (3210, 121, 1065, NULL, '2023-11-15 17:12:00', NULL, '2023-11-15 17:12:00', b'0', 2207);
INSERT INTO `system_role_menu` VALUES (3211, 121, 1002, NULL, '2023-11-15 17:12:00', NULL, '2023-11-15 17:12:00', b'0', 2207);
INSERT INTO `system_role_menu` VALUES (3212, 121, 1003, NULL, '2023-11-15 17:12:00', NULL, '2023-11-15 17:12:00', b'0', 2207);
INSERT INTO `system_role_menu` VALUES (3213, 121, 107, NULL, '2023-11-15 17:12:00', NULL, '2023-11-15 17:12:00', b'0', 2207);
INSERT INTO `system_role_menu` VALUES (3214, 121, 1004, NULL, '2023-11-15 17:12:00', NULL, '2023-11-15 17:12:00', b'0', 2207);
INSERT INTO `system_role_menu` VALUES (3215, 121, 1005, NULL, '2023-11-15 17:12:00', NULL, '2023-11-15 17:12:00', b'0', 2207);
INSERT INTO `system_role_menu` VALUES (3216, 121, 1006, NULL, '2023-11-15 17:12:00', NULL, '2023-11-15 17:12:00', b'0', 2207);
INSERT INTO `system_role_menu` VALUES (3217, 121, 1007, NULL, '2023-11-15 17:12:00', NULL, '2023-11-15 17:12:00', b'0', 2207);
INSERT INTO `system_role_menu` VALUES (3218, 121, 1008, NULL, '2023-11-15 17:12:00', NULL, '2023-11-15 17:12:00', b'0', 2207);
INSERT INTO `system_role_menu` VALUES (3219, 121, 1009, NULL, '2023-11-15 17:12:00', NULL, '2023-11-15 17:12:00', b'0', 2207);
INSERT INTO `system_role_menu` VALUES (3220, 121, 1010, NULL, '2023-11-15 17:12:00', NULL, '2023-11-15 17:12:00', b'0', 2207);
INSERT INTO `system_role_menu` VALUES (3221, 121, 1011, NULL, '2023-11-15 17:12:00', NULL, '2023-11-15 17:12:00', b'0', 2207);
INSERT INTO `system_role_menu` VALUES (3222, 121, 1012, NULL, '2023-11-15 17:12:00', NULL, '2023-11-15 17:12:00', b'0', 2207);
INSERT INTO `system_role_menu` VALUES (3223, 121, 1013, NULL, '2023-11-15 17:12:00', NULL, '2023-11-15 17:12:00', b'0', 2207);
INSERT INTO `system_role_menu` VALUES (3224, 121, 1014, NULL, '2023-11-15 17:12:00', NULL, '2023-11-15 17:12:00', b'0', 2207);
INSERT INTO `system_role_menu` VALUES (3225, 121, 1015, NULL, '2023-11-15 17:12:00', NULL, '2023-11-15 17:12:00', b'0', 2207);
INSERT INTO `system_role_menu` VALUES (3226, 121, 1016, NULL, '2023-11-15 17:12:00', NULL, '2023-11-15 17:12:00', b'0', 2207);
INSERT INTO `system_role_menu` VALUES (3227, 121, 1017, NULL, '2023-11-15 17:12:00', NULL, '2023-11-15 17:12:00', b'0', 2207);
INSERT INTO `system_role_menu` VALUES (3228, 121, 1018, NULL, '2023-11-15 17:12:00', NULL, '2023-11-15 17:12:00', b'0', 2207);
INSERT INTO `system_role_menu` VALUES (3229, 121, 1019, NULL, '2023-11-15 17:12:00', NULL, '2023-11-15 17:12:00', b'0', 2207);
INSERT INTO `system_role_menu` VALUES (3230, 121, 1020, NULL, '2023-11-15 17:12:00', NULL, '2023-11-15 17:12:00', b'0', 2207);
INSERT INTO `system_role_menu` VALUES (3231, 122, 1, NULL, '2023-11-16 10:28:25', NULL, '2023-11-16 10:28:25', b'0', 2209);
INSERT INTO `system_role_menu` VALUES (3232, 122, 1036, NULL, '2023-11-16 10:28:25', NULL, '2023-11-16 10:28:25', b'0', 2209);
INSERT INTO `system_role_menu` VALUES (3233, 122, 1037, NULL, '2023-11-16 10:28:25', NULL, '2023-11-16 10:28:25', b'0', 2209);
INSERT INTO `system_role_menu` VALUES (3234, 122, 1038, NULL, '2023-11-16 10:28:25', NULL, '2023-11-16 10:28:25', b'0', 2209);
INSERT INTO `system_role_menu` VALUES (3235, 122, 1039, NULL, '2023-11-16 10:28:25', NULL, '2023-11-16 10:28:25', b'0', 2209);
INSERT INTO `system_role_menu` VALUES (3236, 122, 100, NULL, '2023-11-16 10:28:25', NULL, '2023-11-16 10:28:25', b'0', 2209);
INSERT INTO `system_role_menu` VALUES (3237, 122, 101, NULL, '2023-11-16 10:28:25', NULL, '2023-11-16 10:28:25', b'0', 2209);
INSERT INTO `system_role_menu` VALUES (3238, 122, 102, NULL, '2023-11-16 10:28:25', NULL, '2023-11-16 10:28:25', b'0', 2209);
INSERT INTO `system_role_menu` VALUES (3239, 122, 1063, NULL, '2023-11-16 10:28:25', NULL, '2023-11-16 10:28:25', b'0', 2209);
INSERT INTO `system_role_menu` VALUES (3240, 122, 103, NULL, '2023-11-16 10:28:25', NULL, '2023-11-16 10:28:25', b'0', 2209);
INSERT INTO `system_role_menu` VALUES (3241, 122, 1064, NULL, '2023-11-16 10:28:25', NULL, '2023-11-16 10:28:25', b'0', 2209);
INSERT INTO `system_role_menu` VALUES (3242, 122, 1001, NULL, '2023-11-16 10:28:25', NULL, '2023-11-16 10:28:25', b'0', 2209);
INSERT INTO `system_role_menu` VALUES (3243, 122, 1065, NULL, '2023-11-16 10:28:25', NULL, '2023-11-16 10:28:25', b'0', 2209);
INSERT INTO `system_role_menu` VALUES (3244, 122, 1002, NULL, '2023-11-16 10:28:25', NULL, '2023-11-16 10:28:25', b'0', 2209);
INSERT INTO `system_role_menu` VALUES (3245, 122, 1003, NULL, '2023-11-16 10:28:25', NULL, '2023-11-16 10:28:25', b'0', 2209);
INSERT INTO `system_role_menu` VALUES (3246, 122, 107, NULL, '2023-11-16 10:28:25', NULL, '2023-11-16 10:28:25', b'0', 2209);
INSERT INTO `system_role_menu` VALUES (3247, 122, 1004, NULL, '2023-11-16 10:28:25', NULL, '2023-11-16 10:28:25', b'0', 2209);
INSERT INTO `system_role_menu` VALUES (3248, 122, 1005, NULL, '2023-11-16 10:28:25', NULL, '2023-11-16 10:28:25', b'0', 2209);
INSERT INTO `system_role_menu` VALUES (3249, 122, 1006, NULL, '2023-11-16 10:28:25', NULL, '2023-11-16 10:28:25', b'0', 2209);
INSERT INTO `system_role_menu` VALUES (3250, 122, 1007, NULL, '2023-11-16 10:28:25', NULL, '2023-11-16 10:28:25', b'0', 2209);
INSERT INTO `system_role_menu` VALUES (3251, 122, 1008, NULL, '2023-11-16 10:28:25', NULL, '2023-11-16 10:28:25', b'0', 2209);
INSERT INTO `system_role_menu` VALUES (3252, 122, 1009, NULL, '2023-11-16 10:28:25', NULL, '2023-11-16 10:28:25', b'0', 2209);
INSERT INTO `system_role_menu` VALUES (3253, 122, 1010, NULL, '2023-11-16 10:28:25', NULL, '2023-11-16 10:28:25', b'0', 2209);
INSERT INTO `system_role_menu` VALUES (3254, 122, 1011, NULL, '2023-11-16 10:28:25', NULL, '2023-11-16 10:28:25', b'0', 2209);
INSERT INTO `system_role_menu` VALUES (3255, 122, 1012, NULL, '2023-11-16 10:28:25', NULL, '2023-11-16 10:28:25', b'0', 2209);
INSERT INTO `system_role_menu` VALUES (3256, 122, 1013, NULL, '2023-11-16 10:28:25', NULL, '2023-11-16 10:28:25', b'0', 2209);
INSERT INTO `system_role_menu` VALUES (3257, 122, 1014, NULL, '2023-11-16 10:28:25', NULL, '2023-11-16 10:28:25', b'0', 2209);
INSERT INTO `system_role_menu` VALUES (3258, 122, 1015, NULL, '2023-11-16 10:28:25', NULL, '2023-11-16 10:28:25', b'0', 2209);
INSERT INTO `system_role_menu` VALUES (3259, 122, 1016, NULL, '2023-11-16 10:28:25', NULL, '2023-11-16 10:28:25', b'0', 2209);
INSERT INTO `system_role_menu` VALUES (3260, 122, 1017, NULL, '2023-11-16 10:28:25', NULL, '2023-11-16 10:28:25', b'0', 2209);
INSERT INTO `system_role_menu` VALUES (3261, 122, 1018, NULL, '2023-11-16 10:28:25', NULL, '2023-11-16 10:28:25', b'0', 2209);
INSERT INTO `system_role_menu` VALUES (3262, 122, 1019, NULL, '2023-11-16 10:28:25', NULL, '2023-11-16 10:28:25', b'0', 2209);
INSERT INTO `system_role_menu` VALUES (3263, 122, 1020, NULL, '2023-11-16 10:28:25', NULL, '2023-11-16 10:28:25', b'0', 2209);
INSERT INTO `system_role_menu` VALUES (3264, 123, 1, NULL, '2023-11-16 10:33:57', NULL, '2023-11-16 10:33:57', b'0', 2210);
INSERT INTO `system_role_menu` VALUES (3265, 123, 1036, NULL, '2023-11-16 10:33:57', NULL, '2023-11-16 10:33:57', b'0', 2210);
INSERT INTO `system_role_menu` VALUES (3266, 123, 1037, NULL, '2023-11-16 10:33:57', NULL, '2023-11-16 10:33:57', b'0', 2210);
INSERT INTO `system_role_menu` VALUES (3267, 123, 1038, NULL, '2023-11-16 10:33:57', NULL, '2023-11-16 10:33:57', b'0', 2210);
INSERT INTO `system_role_menu` VALUES (3268, 123, 1039, NULL, '2023-11-16 10:33:57', NULL, '2023-11-16 10:33:57', b'0', 2210);
INSERT INTO `system_role_menu` VALUES (3269, 123, 100, NULL, '2023-11-16 10:33:57', NULL, '2023-11-16 10:33:57', b'0', 2210);
INSERT INTO `system_role_menu` VALUES (3270, 123, 101, NULL, '2023-11-16 10:33:57', NULL, '2023-11-16 10:33:57', b'0', 2210);
INSERT INTO `system_role_menu` VALUES (3271, 123, 102, NULL, '2023-11-16 10:33:57', NULL, '2023-11-16 10:33:57', b'0', 2210);
INSERT INTO `system_role_menu` VALUES (3272, 123, 1063, NULL, '2023-11-16 10:33:57', NULL, '2023-11-16 10:33:57', b'0', 2210);
INSERT INTO `system_role_menu` VALUES (3273, 123, 103, NULL, '2023-11-16 10:33:57', NULL, '2023-11-16 10:33:57', b'0', 2210);
INSERT INTO `system_role_menu` VALUES (3274, 123, 1064, NULL, '2023-11-16 10:33:57', NULL, '2023-11-16 10:33:57', b'0', 2210);
INSERT INTO `system_role_menu` VALUES (3275, 123, 1001, NULL, '2023-11-16 10:33:57', NULL, '2023-11-16 10:33:57', b'0', 2210);
INSERT INTO `system_role_menu` VALUES (3276, 123, 1065, NULL, '2023-11-16 10:33:57', NULL, '2023-11-16 10:33:57', b'0', 2210);
INSERT INTO `system_role_menu` VALUES (3277, 123, 1002, NULL, '2023-11-16 10:33:57', NULL, '2023-11-16 10:33:57', b'0', 2210);
INSERT INTO `system_role_menu` VALUES (3278, 123, 1003, NULL, '2023-11-16 10:33:57', NULL, '2023-11-16 10:33:57', b'0', 2210);
INSERT INTO `system_role_menu` VALUES (3279, 123, 107, NULL, '2023-11-16 10:33:57', NULL, '2023-11-16 10:33:57', b'0', 2210);
INSERT INTO `system_role_menu` VALUES (3280, 123, 1004, NULL, '2023-11-16 10:33:57', NULL, '2023-11-16 10:33:57', b'0', 2210);
INSERT INTO `system_role_menu` VALUES (3281, 123, 1005, NULL, '2023-11-16 10:33:57', NULL, '2023-11-16 10:33:57', b'0', 2210);
INSERT INTO `system_role_menu` VALUES (3282, 123, 1006, NULL, '2023-11-16 10:33:57', NULL, '2023-11-16 10:33:57', b'0', 2210);
INSERT INTO `system_role_menu` VALUES (3283, 123, 1007, NULL, '2023-11-16 10:33:57', NULL, '2023-11-16 10:33:57', b'0', 2210);
INSERT INTO `system_role_menu` VALUES (3284, 123, 1008, NULL, '2023-11-16 10:33:57', NULL, '2023-11-16 10:33:57', b'0', 2210);
INSERT INTO `system_role_menu` VALUES (3285, 123, 1009, NULL, '2023-11-16 10:33:57', NULL, '2023-11-16 10:33:57', b'0', 2210);
INSERT INTO `system_role_menu` VALUES (3286, 123, 1010, NULL, '2023-11-16 10:33:57', NULL, '2023-11-16 10:33:57', b'0', 2210);
INSERT INTO `system_role_menu` VALUES (3287, 123, 1011, NULL, '2023-11-16 10:33:57', NULL, '2023-11-16 10:33:57', b'0', 2210);
INSERT INTO `system_role_menu` VALUES (3288, 123, 1012, NULL, '2023-11-16 10:33:57', NULL, '2023-11-16 10:33:57', b'0', 2210);
INSERT INTO `system_role_menu` VALUES (3289, 123, 1013, NULL, '2023-11-16 10:33:57', NULL, '2023-11-16 10:33:57', b'0', 2210);
INSERT INTO `system_role_menu` VALUES (3290, 123, 1014, NULL, '2023-11-16 10:33:57', NULL, '2023-11-16 10:33:57', b'0', 2210);
INSERT INTO `system_role_menu` VALUES (3291, 123, 1015, NULL, '2023-11-16 10:33:57', NULL, '2023-11-16 10:33:57', b'0', 2210);
INSERT INTO `system_role_menu` VALUES (3292, 123, 1016, NULL, '2023-11-16 10:33:57', NULL, '2023-11-16 10:33:57', b'0', 2210);
INSERT INTO `system_role_menu` VALUES (3293, 123, 1017, NULL, '2023-11-16 10:33:57', NULL, '2023-11-16 10:33:57', b'0', 2210);
INSERT INTO `system_role_menu` VALUES (3294, 123, 1018, NULL, '2023-11-16 10:33:57', NULL, '2023-11-16 10:33:57', b'0', 2210);
INSERT INTO `system_role_menu` VALUES (3295, 123, 1019, NULL, '2023-11-16 10:33:57', NULL, '2023-11-16 10:33:57', b'0', 2210);
INSERT INTO `system_role_menu` VALUES (3296, 123, 1020, NULL, '2023-11-16 10:33:57', NULL, '2023-11-16 10:33:57', b'0', 2210);
INSERT INTO `system_role_menu` VALUES (3297, 124, 1, NULL, '2023-11-16 10:36:26', NULL, '2023-11-16 10:36:26', b'0', 2211);
INSERT INTO `system_role_menu` VALUES (3298, 124, 1036, NULL, '2023-11-16 10:36:26', NULL, '2023-11-16 10:36:26', b'0', 2211);
INSERT INTO `system_role_menu` VALUES (3299, 124, 1037, NULL, '2023-11-16 10:36:26', NULL, '2023-11-16 10:36:26', b'0', 2211);
INSERT INTO `system_role_menu` VALUES (3300, 124, 1038, NULL, '2023-11-16 10:36:27', NULL, '2023-11-16 10:36:27', b'0', 2211);
INSERT INTO `system_role_menu` VALUES (3301, 124, 1039, NULL, '2023-11-16 10:36:27', NULL, '2023-11-16 10:36:27', b'0', 2211);
INSERT INTO `system_role_menu` VALUES (3302, 124, 100, NULL, '2023-11-16 10:36:27', NULL, '2023-11-16 10:36:27', b'0', 2211);
INSERT INTO `system_role_menu` VALUES (3303, 124, 101, NULL, '2023-11-16 10:36:27', NULL, '2023-11-16 10:36:27', b'0', 2211);
INSERT INTO `system_role_menu` VALUES (3304, 124, 102, NULL, '2023-11-16 10:36:27', NULL, '2023-11-16 10:36:27', b'0', 2211);
INSERT INTO `system_role_menu` VALUES (3305, 124, 1063, NULL, '2023-11-16 10:36:27', NULL, '2023-11-16 10:36:27', b'0', 2211);
INSERT INTO `system_role_menu` VALUES (3306, 124, 103, NULL, '2023-11-16 10:36:27', NULL, '2023-11-16 10:36:27', b'0', 2211);
INSERT INTO `system_role_menu` VALUES (3307, 124, 1064, NULL, '2023-11-16 10:36:27', NULL, '2023-11-16 10:36:27', b'0', 2211);
INSERT INTO `system_role_menu` VALUES (3308, 124, 1001, NULL, '2023-11-16 10:36:27', NULL, '2023-11-16 10:36:27', b'0', 2211);
INSERT INTO `system_role_menu` VALUES (3309, 124, 1065, NULL, '2023-11-16 10:36:27', NULL, '2023-11-16 10:36:27', b'0', 2211);
INSERT INTO `system_role_menu` VALUES (3310, 124, 1002, NULL, '2023-11-16 10:36:27', NULL, '2023-11-16 10:36:27', b'0', 2211);
INSERT INTO `system_role_menu` VALUES (3311, 124, 1003, NULL, '2023-11-16 10:36:27', NULL, '2023-11-16 10:36:27', b'0', 2211);
INSERT INTO `system_role_menu` VALUES (3312, 124, 107, NULL, '2023-11-16 10:36:27', NULL, '2023-11-16 10:36:27', b'0', 2211);
INSERT INTO `system_role_menu` VALUES (3313, 124, 1004, NULL, '2023-11-16 10:36:27', NULL, '2023-11-16 10:36:27', b'0', 2211);
INSERT INTO `system_role_menu` VALUES (3314, 124, 1005, NULL, '2023-11-16 10:36:27', NULL, '2023-11-16 10:36:27', b'0', 2211);
INSERT INTO `system_role_menu` VALUES (3315, 124, 1006, NULL, '2023-11-16 10:36:27', NULL, '2023-11-16 10:36:27', b'0', 2211);
INSERT INTO `system_role_menu` VALUES (3316, 124, 1007, NULL, '2023-11-16 10:36:27', NULL, '2023-11-16 10:36:27', b'0', 2211);
INSERT INTO `system_role_menu` VALUES (3317, 124, 1008, NULL, '2023-11-16 10:36:27', NULL, '2023-11-16 10:36:27', b'0', 2211);
INSERT INTO `system_role_menu` VALUES (3318, 124, 1009, NULL, '2023-11-16 10:36:27', NULL, '2023-11-16 10:36:27', b'0', 2211);
INSERT INTO `system_role_menu` VALUES (3319, 124, 1010, NULL, '2023-11-16 10:36:27', NULL, '2023-11-16 10:36:27', b'0', 2211);
INSERT INTO `system_role_menu` VALUES (3320, 124, 1011, NULL, '2023-11-16 10:36:27', NULL, '2023-11-16 10:36:27', b'0', 2211);
INSERT INTO `system_role_menu` VALUES (3321, 124, 1012, NULL, '2023-11-16 10:36:27', NULL, '2023-11-16 10:36:27', b'0', 2211);
INSERT INTO `system_role_menu` VALUES (3322, 124, 1013, NULL, '2023-11-16 10:36:27', NULL, '2023-11-16 10:36:27', b'0', 2211);
INSERT INTO `system_role_menu` VALUES (3323, 124, 1014, NULL, '2023-11-16 10:36:27', NULL, '2023-11-16 10:36:27', b'0', 2211);
INSERT INTO `system_role_menu` VALUES (3324, 124, 1015, NULL, '2023-11-16 10:36:27', NULL, '2023-11-16 10:36:27', b'0', 2211);
INSERT INTO `system_role_menu` VALUES (3325, 124, 1016, NULL, '2023-11-16 10:36:27', NULL, '2023-11-16 10:36:27', b'0', 2211);
INSERT INTO `system_role_menu` VALUES (3326, 124, 1017, NULL, '2023-11-16 10:36:27', NULL, '2023-11-16 10:36:27', b'0', 2211);
INSERT INTO `system_role_menu` VALUES (3327, 124, 1018, NULL, '2023-11-16 10:36:27', NULL, '2023-11-16 10:36:27', b'0', 2211);
INSERT INTO `system_role_menu` VALUES (3328, 124, 1019, NULL, '2023-11-16 10:36:27', NULL, '2023-11-16 10:36:27', b'0', 2211);
INSERT INTO `system_role_menu` VALUES (3329, 124, 1020, NULL, '2023-11-16 10:36:27', NULL, '2023-11-16 10:36:27', b'0', 2211);
INSERT INTO `system_role_menu` VALUES (3330, 125, 1, NULL, '2023-11-16 10:36:43', NULL, '2023-11-16 10:36:43', b'0', 2212);
INSERT INTO `system_role_menu` VALUES (3331, 125, 1036, NULL, '2023-11-16 10:36:43', NULL, '2023-11-16 10:36:43', b'0', 2212);
INSERT INTO `system_role_menu` VALUES (3332, 125, 1037, NULL, '2023-11-16 10:36:43', NULL, '2023-11-16 10:36:43', b'0', 2212);
INSERT INTO `system_role_menu` VALUES (3333, 125, 1038, NULL, '2023-11-16 10:36:43', NULL, '2023-11-16 10:36:43', b'0', 2212);
INSERT INTO `system_role_menu` VALUES (3334, 125, 1039, NULL, '2023-11-16 10:36:43', NULL, '2023-11-16 10:36:43', b'0', 2212);
INSERT INTO `system_role_menu` VALUES (3335, 125, 100, NULL, '2023-11-16 10:36:43', NULL, '2023-11-16 10:36:43', b'0', 2212);
INSERT INTO `system_role_menu` VALUES (3336, 125, 101, NULL, '2023-11-16 10:36:43', NULL, '2023-11-16 10:36:43', b'0', 2212);
INSERT INTO `system_role_menu` VALUES (3337, 125, 102, NULL, '2023-11-16 10:36:43', NULL, '2023-11-16 10:36:43', b'0', 2212);
INSERT INTO `system_role_menu` VALUES (3338, 125, 1063, NULL, '2023-11-16 10:36:43', NULL, '2023-11-16 10:36:43', b'0', 2212);
INSERT INTO `system_role_menu` VALUES (3339, 125, 103, NULL, '2023-11-16 10:36:43', NULL, '2023-11-16 10:36:43', b'0', 2212);
INSERT INTO `system_role_menu` VALUES (3340, 125, 1064, NULL, '2023-11-16 10:36:43', NULL, '2023-11-16 10:36:43', b'0', 2212);
INSERT INTO `system_role_menu` VALUES (3341, 125, 1001, NULL, '2023-11-16 10:36:43', NULL, '2023-11-16 10:36:43', b'0', 2212);
INSERT INTO `system_role_menu` VALUES (3342, 125, 1065, NULL, '2023-11-16 10:36:43', NULL, '2023-11-16 10:36:43', b'0', 2212);
INSERT INTO `system_role_menu` VALUES (3343, 125, 1002, NULL, '2023-11-16 10:36:43', NULL, '2023-11-16 10:36:43', b'0', 2212);
INSERT INTO `system_role_menu` VALUES (3344, 125, 1003, NULL, '2023-11-16 10:36:43', NULL, '2023-11-16 10:36:43', b'0', 2212);
INSERT INTO `system_role_menu` VALUES (3345, 125, 107, NULL, '2023-11-16 10:36:43', NULL, '2023-11-16 10:36:43', b'0', 2212);
INSERT INTO `system_role_menu` VALUES (3346, 125, 1004, NULL, '2023-11-16 10:36:43', NULL, '2023-11-16 10:36:43', b'0', 2212);
INSERT INTO `system_role_menu` VALUES (3347, 125, 1005, NULL, '2023-11-16 10:36:43', NULL, '2023-11-16 10:36:43', b'0', 2212);
INSERT INTO `system_role_menu` VALUES (3348, 125, 1006, NULL, '2023-11-16 10:36:43', NULL, '2023-11-16 10:36:43', b'0', 2212);
INSERT INTO `system_role_menu` VALUES (3349, 125, 1007, NULL, '2023-11-16 10:36:43', NULL, '2023-11-16 10:36:43', b'0', 2212);
INSERT INTO `system_role_menu` VALUES (3350, 125, 1008, NULL, '2023-11-16 10:36:43', NULL, '2023-11-16 10:36:43', b'0', 2212);
INSERT INTO `system_role_menu` VALUES (3351, 125, 1009, NULL, '2023-11-16 10:36:43', NULL, '2023-11-16 10:36:43', b'0', 2212);
INSERT INTO `system_role_menu` VALUES (3352, 125, 1010, NULL, '2023-11-16 10:36:43', NULL, '2023-11-16 10:36:43', b'0', 2212);
INSERT INTO `system_role_menu` VALUES (3353, 125, 1011, NULL, '2023-11-16 10:36:43', NULL, '2023-11-16 10:36:43', b'0', 2212);
INSERT INTO `system_role_menu` VALUES (3354, 125, 1012, NULL, '2023-11-16 10:36:43', NULL, '2023-11-16 10:36:43', b'0', 2212);
INSERT INTO `system_role_menu` VALUES (3355, 125, 1013, NULL, '2023-11-16 10:36:43', NULL, '2023-11-16 10:36:43', b'0', 2212);
INSERT INTO `system_role_menu` VALUES (3356, 125, 1014, NULL, '2023-11-16 10:36:43', NULL, '2023-11-16 10:36:43', b'0', 2212);
INSERT INTO `system_role_menu` VALUES (3357, 125, 1015, NULL, '2023-11-16 10:36:43', NULL, '2023-11-16 10:36:43', b'0', 2212);
INSERT INTO `system_role_menu` VALUES (3358, 125, 1016, NULL, '2023-11-16 10:36:43', NULL, '2023-11-16 10:36:43', b'0', 2212);
INSERT INTO `system_role_menu` VALUES (3359, 125, 1017, NULL, '2023-11-16 10:36:43', NULL, '2023-11-16 10:36:43', b'0', 2212);
INSERT INTO `system_role_menu` VALUES (3360, 125, 1018, NULL, '2023-11-16 10:36:43', NULL, '2023-11-16 10:36:43', b'0', 2212);
INSERT INTO `system_role_menu` VALUES (3361, 125, 1019, NULL, '2023-11-16 10:36:43', NULL, '2023-11-16 10:36:43', b'0', 2212);
INSERT INTO `system_role_menu` VALUES (3362, 125, 1020, NULL, '2023-11-16 10:36:43', NULL, '2023-11-16 10:36:43', b'0', 2212);
INSERT INTO `system_role_menu` VALUES (3363, 126, 1, NULL, '2023-11-16 10:42:46', NULL, '2023-11-16 10:42:46', b'0', 2213);
INSERT INTO `system_role_menu` VALUES (3364, 126, 1036, NULL, '2023-11-16 10:42:46', NULL, '2023-11-16 10:42:46', b'0', 2213);
INSERT INTO `system_role_menu` VALUES (3365, 126, 1037, NULL, '2023-11-16 10:42:46', NULL, '2023-11-16 10:42:46', b'0', 2213);
INSERT INTO `system_role_menu` VALUES (3366, 126, 1038, NULL, '2023-11-16 10:42:46', NULL, '2023-11-16 10:42:46', b'0', 2213);
INSERT INTO `system_role_menu` VALUES (3367, 126, 1039, NULL, '2023-11-16 10:42:46', NULL, '2023-11-16 10:42:46', b'0', 2213);
INSERT INTO `system_role_menu` VALUES (3368, 126, 100, NULL, '2023-11-16 10:42:46', NULL, '2023-11-16 10:42:46', b'0', 2213);
INSERT INTO `system_role_menu` VALUES (3369, 126, 101, NULL, '2023-11-16 10:42:46', NULL, '2023-11-16 10:42:46', b'0', 2213);
INSERT INTO `system_role_menu` VALUES (3370, 126, 102, NULL, '2023-11-16 10:42:46', NULL, '2023-11-16 10:42:46', b'0', 2213);
INSERT INTO `system_role_menu` VALUES (3371, 126, 1063, NULL, '2023-11-16 10:42:46', NULL, '2023-11-16 10:42:46', b'0', 2213);
INSERT INTO `system_role_menu` VALUES (3372, 126, 103, NULL, '2023-11-16 10:42:46', NULL, '2023-11-16 10:42:46', b'0', 2213);
INSERT INTO `system_role_menu` VALUES (3373, 126, 1064, NULL, '2023-11-16 10:42:46', NULL, '2023-11-16 10:42:46', b'0', 2213);
INSERT INTO `system_role_menu` VALUES (3374, 126, 1001, NULL, '2023-11-16 10:42:46', NULL, '2023-11-16 10:42:46', b'0', 2213);
INSERT INTO `system_role_menu` VALUES (3375, 126, 1065, NULL, '2023-11-16 10:42:46', NULL, '2023-11-16 10:42:46', b'0', 2213);
INSERT INTO `system_role_menu` VALUES (3376, 126, 1002, NULL, '2023-11-16 10:42:46', NULL, '2023-11-16 10:42:46', b'0', 2213);
INSERT INTO `system_role_menu` VALUES (3377, 126, 1003, NULL, '2023-11-16 10:42:46', NULL, '2023-11-16 10:42:46', b'0', 2213);
INSERT INTO `system_role_menu` VALUES (3378, 126, 107, NULL, '2023-11-16 10:42:46', NULL, '2023-11-16 10:42:46', b'0', 2213);
INSERT INTO `system_role_menu` VALUES (3379, 126, 1004, NULL, '2023-11-16 10:42:46', NULL, '2023-11-16 10:42:46', b'0', 2213);
INSERT INTO `system_role_menu` VALUES (3380, 126, 1005, NULL, '2023-11-16 10:42:46', NULL, '2023-11-16 10:42:46', b'0', 2213);
INSERT INTO `system_role_menu` VALUES (3381, 126, 1006, NULL, '2023-11-16 10:42:46', NULL, '2023-11-16 10:42:46', b'0', 2213);
INSERT INTO `system_role_menu` VALUES (3382, 126, 1007, NULL, '2023-11-16 10:42:46', NULL, '2023-11-16 10:42:46', b'0', 2213);
INSERT INTO `system_role_menu` VALUES (3383, 126, 1008, NULL, '2023-11-16 10:42:46', NULL, '2023-11-16 10:42:46', b'0', 2213);
INSERT INTO `system_role_menu` VALUES (3384, 126, 1009, NULL, '2023-11-16 10:42:46', NULL, '2023-11-16 10:42:46', b'0', 2213);
INSERT INTO `system_role_menu` VALUES (3385, 126, 1010, NULL, '2023-11-16 10:42:46', NULL, '2023-11-16 10:42:46', b'0', 2213);
INSERT INTO `system_role_menu` VALUES (3386, 126, 1011, NULL, '2023-11-16 10:42:46', NULL, '2023-11-16 10:42:46', b'0', 2213);
INSERT INTO `system_role_menu` VALUES (3387, 126, 1012, NULL, '2023-11-16 10:42:46', NULL, '2023-11-16 10:42:46', b'0', 2213);
INSERT INTO `system_role_menu` VALUES (3388, 126, 1013, NULL, '2023-11-16 10:42:46', NULL, '2023-11-16 10:42:46', b'0', 2213);
INSERT INTO `system_role_menu` VALUES (3389, 126, 1014, NULL, '2023-11-16 10:42:46', NULL, '2023-11-16 10:42:46', b'0', 2213);
INSERT INTO `system_role_menu` VALUES (3390, 126, 1015, NULL, '2023-11-16 10:42:46', NULL, '2023-11-16 10:42:46', b'0', 2213);
INSERT INTO `system_role_menu` VALUES (3391, 126, 1016, NULL, '2023-11-16 10:42:46', NULL, '2023-11-16 10:42:46', b'0', 2213);
INSERT INTO `system_role_menu` VALUES (3392, 126, 1017, NULL, '2023-11-16 10:42:46', NULL, '2023-11-16 10:42:46', b'0', 2213);
INSERT INTO `system_role_menu` VALUES (3393, 126, 1018, NULL, '2023-11-16 10:42:46', NULL, '2023-11-16 10:42:46', b'0', 2213);
INSERT INTO `system_role_menu` VALUES (3394, 126, 1019, NULL, '2023-11-16 10:42:46', NULL, '2023-11-16 10:42:46', b'0', 2213);
INSERT INTO `system_role_menu` VALUES (3395, 126, 1020, NULL, '2023-11-16 10:42:46', NULL, '2023-11-16 10:42:46', b'0', 2213);

-- ----------------------------
-- Table structure for system_tenant
-- ----------------------------
DROP TABLE IF EXISTS `system_tenant`;
CREATE TABLE `system_tenant`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '租户编号',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '租户名',
  `contact_user_id` bigint(0) NULL DEFAULT NULL COMMENT '联系人的用户编号',
  `contact_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '联系人',
  `contact_mobile` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '联系手机',
  `status` tinyint(0) NOT NULL DEFAULT 0 COMMENT '租户状态（0正常 1停用）',
  `domain` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '绑定域名',
  `package_id` bigint(0) NOT NULL COMMENT '租户套餐编号',
  `expire_time` datetime(0) NOT NULL COMMENT '过期时间',
  `account_count` int(0) NOT NULL COMMENT '账号数量',
  `data_source_config_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '数据源配置编号',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2216 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '租户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_tenant
-- ----------------------------
INSERT INTO `system_tenant` VALUES (1, '芋道源码', NULL, '芋艿', '17321315478', 0, 'https://www.iocoder.cn', 0, '2099-02-19 17:14:16', 9999, 1, '1', '2021-01-05 17:03:47', '1', '2023-02-26 00:58:40', b'0');
INSERT INTO `system_tenant` VALUES (2, '测试租户', 126, '阿2', NULL, 0, 'http://www.baidu.com', 111, '2023-02-25 00:00:00', 100, 1, '1', '2023-02-26 00:02:05', '1', '2023-02-27 22:05:43', b'0');
INSERT INTO `system_tenant` VALUES (121, '小租户', 110, '小王2', '15601691300', 0, 'http://www.iocoder.cn', 111, '2024-03-11 00:00:00', 20, 0, '1', '2022-02-22 00:56:14', '1', '2022-05-17 10:03:59', b'0');
INSERT INTO `system_tenant` VALUES (2171, '租户a', 124, '土豆', NULL, 0, 'http://www.iocoder.cn', 112, '2023-03-01 00:00:00', 100, 1, '1', '2023-02-27 22:15:29', '1', '2023-02-27 22:28:10', b'0');
INSERT INTO `system_tenant` VALUES (2172, '租户b', 123, '阿毛', NULL, 0, 'http://www.iocoder.cn', 112, '2023-02-22 00:00:00', 100, 12, '1', '2023-02-27 22:23:36', '1', '2023-02-27 22:23:36', b'0');
INSERT INTO `system_tenant` VALUES (2173, '土豆租户', 125, '土豆', NULL, 0, 'https://www.iocoder.cn', 112, '2023-03-31 00:00:00', 50, 1, '1', '2023-03-01 21:56:37', '1', '2023-03-01 21:56:37', b'0');
INSERT INTO `system_tenant` VALUES (2174, 'hwt租户', 124, '胡文涛', '15213849570', 0, 'https://pre-educloud.wenxiang.cn/', 111, '2023-11-04 00:00:00', 10, 13, '1', '2023-11-03 11:25:02', '1', '2023-11-03 13:23:29', b'0');
INSERT INTO `system_tenant` VALUES (2175, '12321', 125, '123', '123', 0, 'http://www.baidu.com', 111, '2023-11-04 00:00:00', 2, 14, '1', '2023-11-03 13:33:06', '1', '2023-11-03 15:53:49', b'0');
INSERT INTO `system_tenant` VALUES (2176, '1234132', 124, '12341234', '1431234', 0, 'https://www.baidu.com', 111, '2023-11-04 00:00:00', 2, 14, '1', '2023-11-03 15:54:54', '1', '2023-11-03 15:54:55', b'0');
INSERT INTO `system_tenant` VALUES (2206, '11', NULL, '12312', '12', 0, '12', 111, '2023-11-24 00:00:00', 12, 16, NULL, '2023-11-14 14:51:43', NULL, '2023-11-14 14:51:43', b'0');
INSERT INTO `system_tenant` VALUES (2207, '21321', NULL, '1231231232', '123', 0, '213213', 111, '2023-12-01 00:00:00', 123, 12, NULL, '2023-11-15 17:11:54', NULL, '2023-11-15 17:11:54', b'0');
INSERT INTO `system_tenant` VALUES (2209, '芋道', NULL, '芋艿', '15601691300', 1, 'https://www.iocoder.cn', 111, '1970-01-21 00:15:01', 1024, 13, NULL, '2023-11-16 10:28:25', NULL, '2023-11-16 10:28:25', b'0');
INSERT INTO `system_tenant` VALUES (2210, '芋道', NULL, '芋艿', '15601691300', 1, 'https://www.iocoder.cn', 111, '1970-01-21 00:15:01', 1024, 13, NULL, '2023-11-16 10:33:57', NULL, '2023-11-16 10:33:57', b'0');
INSERT INTO `system_tenant` VALUES (2211, '芋道', 126, '芋艿', '15601691300', 1, 'https://www.iocoder.cn', 111, '1970-01-21 00:15:01', 1024, 13, NULL, '2023-11-16 10:36:26', NULL, '2023-11-16 10:36:26', b'0');
INSERT INTO `system_tenant` VALUES (2212, '芋道', 127, '芋艿', '15601691300', 1, 'https://www.iocoder.cn', 111, '1970-01-21 00:15:01', 1024, 13, NULL, '2023-11-16 10:36:43', NULL, '2023-11-16 10:36:43', b'0');
INSERT INTO `system_tenant` VALUES (2213, '芋道', 128, '芋艿', '15601691300', 1, 'https://www.iocoder.cn', 111, '1970-01-21 00:15:01', 1024, 13, NULL, '2023-11-16 10:42:46', NULL, '2023-11-16 10:42:46', b'0');
INSERT INTO `system_tenant` VALUES (2214, '芋道', 129, '芋艿', '15601691300', 1, 'https://www.iocoder.cn', 111, '1970-01-21 00:15:01', 1024, 13, NULL, '2023-11-16 10:51:47', NULL, '2023-11-16 10:51:47', b'0');
INSERT INTO `system_tenant` VALUES (2215, '芋道', 130, '芋艿', '15601691300', 1, 'https://www.iocoder.cn', 111, '1970-01-21 00:15:01', 1024, 13, NULL, '2023-11-16 11:49:37', NULL, '2023-11-16 11:49:37', b'0');

-- ----------------------------
-- Table structure for system_tenant_package
-- ----------------------------
DROP TABLE IF EXISTS `system_tenant_package`;
CREATE TABLE `system_tenant_package`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '套餐编号',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '套餐名',
  `status` tinyint(0) NOT NULL DEFAULT 0 COMMENT '租户状态（0正常 1停用）',
  `remark` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '备注',
  `menu_ids` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '关联的菜单编号',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 114 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '租户套餐表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_tenant_package
-- ----------------------------
INSERT INTO `system_tenant_package` VALUES (111, '普通套餐', 0, '小功能', '[1,1036,1037,1038,1039,100,101,102,1063,103,1064,1001,1065,1002,1003,107,1004,1005,1006,1007,1008,1009,1010,1011,1012,1013,1014,1015,1016,1017,1018,1019,1020]', '1', '2022-02-22 00:54:00', '1', '2023-02-26 00:56:53', b'0');
INSERT INTO `system_tenant_package` VALUES (112, '萌新套餐', 0, '', '[1,2,1031,1032,1033,1034,1035,1050,1051,1052,1053,1054,1056,1057,1058,1059,1060,1066,1067,1070,1071,1072,1073,1074,1075,1076,1077,1078,1082,1083,1084,1085,1086,1087,1088,1089,1090,1091,1092,1117,100,1126,1127,1128,1129,106,1130,1131,1132,1133,110,1134,111,1135,112,1136,113,1137,2161,114,115,116,1150,1161,1162,1163,1164,1165,1166,1173,1174,1175,1176,1177,1178,1179,1180,1181,1182,1183,1184,1237,1238,1239,1240,1241,1242,1243,1255,1256,1001,1257,1002,1258,1003,1259,1004,1260,1005,1006,1007]', '1', '2023-02-26 00:53:09', '1', '2023-02-27 22:28:30', b'0');

-- ----------------------------
-- Table structure for system_user_role
-- ----------------------------
DROP TABLE IF EXISTS `system_user_role`;
CREATE TABLE `system_user_role`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '自增编号',
  `user_id` bigint(0) NOT NULL COMMENT '用户ID',
  `role_id` bigint(0) NOT NULL COMMENT '角色ID',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted` bit(1) NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户和角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_user_role
-- ----------------------------
INSERT INTO `system_user_role` VALUES (29, 126, 124, NULL, '2023-11-16 10:36:32', NULL, '2023-11-16 10:36:32', b'0', 2211);
INSERT INTO `system_user_role` VALUES (30, 127, 125, NULL, '2023-11-16 10:36:57', NULL, '2023-11-16 10:36:57', b'0', 2212);
INSERT INTO `system_user_role` VALUES (31, 128, 126, NULL, '2023-11-16 10:42:53', NULL, '2023-11-16 10:42:53', b'0', 2213);

-- ----------------------------
-- Table structure for system_users
-- ----------------------------
DROP TABLE IF EXISTS `system_users`;
CREATE TABLE `system_users`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户账号',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '密码',
  `nickname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户昵称',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `dept_id` bigint(0) NULL DEFAULT NULL COMMENT '部门ID',
  `post_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '岗位编号数组',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '用户邮箱',
  `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '手机号码',
  `sex` tinyint(0) NULL DEFAULT 0 COMMENT '用户性别',
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '头像地址',
  `status` tinyint(0) NOT NULL DEFAULT 0 COMMENT '帐号状态（0正常 1停用）',
  `login_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_username`(`username`, `update_time`, `tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1702284907614134280 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_users
-- ----------------------------
INSERT INTO `system_users` VALUES (1702284907614134273, 'admin0', 'dd4b21e9ef71e1291183a46b913ae6f2', '胡文涛0', '管理员', 103, '[1]', 'aoteman@126.com', '15612345678', 1, 'http://test.yudao.iocoder.cn/e1fdd7271685ec143a0900681606406621717a666ad0b2798b096df41422b32f.png', 0, '127.0.0.1', '2023-11-06 09:17:58', 'admin', '2021-01-05 17:03:47', NULL, '2023-11-08 11:49:16', b'0', 0);
INSERT INTO `system_users` VALUES (1702284907614134279, 'admin1', 'dd4b21e9ef71e1291183a46b913ae6f2', '胡文涛1', '管理员', 103, '[1]', 'aoteman@126.com', '15612345678', 1, 'http://test.yudao.iocoder.cn/e1fdd7271685ec143a0900681606406621717a666ad0b2798b096df41422b32f.png', 0, '127.0.0.1', '2023-11-06 09:17:58', 'admin', '2021-01-05 17:03:47', NULL, '2023-11-08 11:49:07', b'0', 1);

SET FOREIGN_KEY_CHECKS = 1;
