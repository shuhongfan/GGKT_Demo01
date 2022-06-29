/*
SQLyog Ultimate - MySQL GUI v8.2 
MySQL - 5.7.29-32-log : Database - glkt_acl
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`glkt_acl` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `glkt_acl`;

/*Table structure for table `admin` */

DROP TABLE IF EXISTS `admin`;

CREATE TABLE `admin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '会员id',
  `username` varchar(20) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(32) NOT NULL DEFAULT '' COMMENT '密码',
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机',
  `ware_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '仓库id（默认为：0为平台用户）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uname` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

/*Data for the table `admin` */

insert  into `admin`(`id`,`username`,`password`,`name`,`phone`,`ware_id`,`create_time`,`update_time`,`is_deleted`) values (1,'admin','96e79218965eb72c92a549dd5a330112','admin',NULL,0,'2021-05-31 18:08:43','2021-05-31 18:08:56',0),(2,'shangguigu','96e79218965eb72c92a549dd5a330112','shangguigu',NULL,0,'2021-06-01 08:46:22','2021-12-01 06:20:29',0),(3,'chengdu','96e79218965eb72c92a549dd5a330112','chengdu',NULL,1,'2021-06-18 17:18:29','2021-06-18 17:20:08',0),(4,'atguigu','e10adc3949ba59abbe56e057f20f883e','游客',NULL,0,'2021-09-27 09:37:39','2021-09-27 09:39:14',0);

/*Table structure for table `admin_login_log` */

DROP TABLE IF EXISTS `admin_login_log`;

CREATE TABLE `admin_login_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `admin_id` bigint(20) DEFAULT NULL,
  `ip` varchar(64) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `user_agent` varchar(100) DEFAULT NULL COMMENT '浏览器登录类型',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台用户登录日志表';

/*Data for the table `admin_login_log` */

/*Table structure for table `admin_role` */

DROP TABLE IF EXISTS `admin_role`;

CREATE TABLE `admin_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '角色id',
  `admin_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`),
  KEY `idx_role_id` (`role_id`),
  KEY `idx_user_id` (`admin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='用户角色';

/*Data for the table `admin_role` */

insert  into `admin_role`(`id`,`role_id`,`admin_id`,`create_time`,`update_time`,`is_deleted`) values (1,1,1,'2021-05-31 18:09:02','2021-05-31 18:09:02',0),(2,2,2,'2021-06-01 08:53:12','2021-12-01 06:21:40',1),(3,3,3,'2021-06-18 17:18:37','2021-06-18 17:18:37',0),(4,4,4,'2021-09-27 09:37:45','2021-09-27 09:37:45',0),(5,4,2,'2021-12-01 06:21:40','2021-12-01 06:21:40',0);

/*Table structure for table `permission` */

DROP TABLE IF EXISTS `permission`;

CREATE TABLE `permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `pid` bigint(20) NOT NULL DEFAULT '0' COMMENT '所属上级',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '名称',
  `code` varchar(50) DEFAULT NULL COMMENT '名称code',
  `to_code` varchar(100) DEFAULT NULL COMMENT '跳转页面code',
  `type` tinyint(3) NOT NULL DEFAULT '0' COMMENT '类型(1:菜单,2:按钮)',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态(0:禁止,1:正常)',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`),
  KEY `idx_pid` (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4 COMMENT='权限';

/*Data for the table `permission` */

insert  into `permission`(`id`,`pid`,`name`,`code`,`to_code`,`type`,`status`,`create_time`,`update_time`,`is_deleted`) values (1,0,'全部数据',NULL,NULL,1,NULL,'2021-05-31 18:05:37','2021-09-27 13:37:59',0),(2,1,'权限管理','Acl',NULL,1,NULL,'2021-05-31 18:05:37','2021-05-31 19:36:53',0),(3,2,'用户管理','User',NULL,1,NULL,'2021-05-31 18:05:37','2021-05-31 19:36:58',0),(4,2,'角色管理','Role',NULL,1,NULL,'2021-05-31 18:05:37','2021-05-31 19:37:02',0),(5,2,'菜单管理','Permission',NULL,1,NULL,'2021-05-31 18:05:37','2021-05-31 19:37:05',0),(6,3,'分配角色','btn.User.assgin',NULL,2,NULL,'2021-05-31 18:05:37','2021-06-01 08:35:35',0),(7,3,'添加','btn.User.add',NULL,2,NULL,'2021-05-31 18:05:37','2021-06-01 08:34:29',0),(8,3,'修改','btn.User.update',NULL,2,NULL,'2021-05-31 18:05:37','2021-06-01 08:34:45',0),(9,3,'删除','btn.User.remove',NULL,2,NULL,'2021-05-31 18:05:37','2021-06-01 08:34:38',0),(10,4,'修改','btn.Role.update',NULL,2,NULL,'2021-05-31 18:05:37','2021-06-01 08:36:20',0),(11,4,'分配权限','btn.Role.assgin','RoleAuth',2,NULL,'2021-05-31 18:05:37','2021-06-01 08:36:56',0),(12,4,'添加','btn.Role.add',NULL,2,NULL,'2021-05-31 18:05:37','2021-06-01 08:36:08',0),(13,4,'删除','btn.Role.remove',NULL,2,NULL,'2021-05-31 18:05:37','2021-06-01 08:36:32',0),(14,4,'角色权限','role.acl',NULL,2,NULL,'2021-05-31 18:05:37','2021-06-01 08:37:22',1),(15,5,'查看','btn.permission.list',NULL,2,NULL,'2021-05-31 18:05:37','2021-05-31 19:32:52',0),(16,5,'添加','btn.Permission.add',NULL,2,NULL,'2021-05-31 18:05:37','2021-06-01 08:37:39',0),(17,5,'修改','btn.Permission.update',NULL,2,NULL,'2021-05-31 18:05:37','2021-06-01 08:37:47',0),(18,5,'删除','btn.Permission.remove',NULL,2,NULL,'2021-05-31 18:05:37','2021-06-01 08:37:54',0),(19,1,'订单管理','Order',NULL,1,NULL,'2021-06-18 16:38:51','2021-06-18 16:48:22',0),(20,19,'订单列表','OrderInfo','',1,NULL,'2021-06-18 16:39:21','2021-06-18 16:42:36',0),(23,1,'点播管理','Vod',NULL,1,NULL,'2021-06-18 16:45:55','2021-11-19 11:39:47',0),(24,23,'课程分类管理','Subject',NULL,1,NULL,'2021-06-18 16:46:44','2021-11-19 11:40:12',0),(25,23,'讲师列表','TeacherList','',1,NULL,'2021-06-18 16:48:01','2021-12-01 06:06:50',0),(26,23,'课程列表','CourseList','',1,NULL,'2021-06-18 16:50:11','2021-12-01 06:08:49',0),(27,25,'添加讲师','','TeacherCreate',2,NULL,'2021-06-18 16:52:12','2021-12-01 06:11:18',0),(28,25,'编辑讲师','','TeacherEdit',2,NULL,'2021-06-18 16:53:04','2021-12-01 06:09:34',0),(29,26,'发布课程','','CourseInfo',2,NULL,'2021-06-18 16:53:22','2021-12-01 06:10:13',0),(30,27,'编辑课程','','CourseInfoEdit',2,NULL,'2021-06-18 16:54:34','2021-12-01 06:10:35',0),(31,27,'编辑大纲',NULL,'CourseChapterEdit',2,NULL,'2021-06-18 16:56:42','2021-12-01 06:10:50',0),(32,27,'课程统计',NULL,'CourseChart',2,NULL,'2021-06-18 16:56:57','2021-12-01 06:11:01',0),(36,1,'营销活动管理','Activity',NULL,1,NULL,'2021-06-18 17:04:15','2021-06-18 17:04:15',0),(40,36,'优惠券列表','CouponInfo',NULL,1,NULL,'2021-06-18 17:06:41','2021-06-18 17:07:18',0),(41,40,'添加',NULL,'CouponInfoAdd',2,NULL,'2021-06-18 17:06:57','2021-06-18 17:07:22',0),(42,40,'修改',NULL,'CouponInfoEdit',2,NULL,'2021-06-18 17:07:11','2021-06-18 17:07:25',0),(43,40,'详情',NULL,'CouponInfoShow',2,NULL,'2021-06-18 17:07:49','2021-12-01 06:12:31',0),(45,1,'直播管理','Live',NULL,1,NULL,'2021-06-18 17:08:44','2021-12-01 06:13:25',0),(46,45,'直播列表','liveCourseList','',1,NULL,'2021-06-18 17:09:23','2021-12-01 06:13:50',0),(47,45,'直播配置',NULL,'liveCourseConfig',2,NULL,'2021-06-18 17:09:28','2021-12-01 06:14:21',0),(48,45,'观看记录',NULL,'liveVisitor',2,NULL,'2021-06-18 17:09:43','2021-12-01 06:14:29',0),(49,1,'菜单管理','Wechat',NULL,1,NULL,'2021-06-18 17:15:44','2021-12-01 06:14:50',0),(50,49,'菜单列表','Menu','',1,NULL,'2021-06-18 17:16:02','2021-12-01 06:14:57',0),(100,1,'全部','btn.all',NULL,2,NULL,'2021-09-27 13:35:24','2021-09-27 13:40:09',0);

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(20) NOT NULL DEFAULT '' COMMENT '角色名称',
  `role_code` varchar(20) DEFAULT NULL COMMENT '角色编码',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='角色';

/*Data for the table `role` */

insert  into `role`(`id`,`role_name`,`role_code`,`remark`,`create_time`,`update_time`,`is_deleted`) values (1,'系统管理员','SYSTEM',NULL,'2021-05-31 18:09:18','2021-05-31 18:09:18',0),(2,'平台管理员',NULL,NULL,'2021-06-01 08:38:40','2021-06-18 17:13:17',0),(3,'运营管理员',NULL,NULL,'2021-06-18 17:12:21','2021-12-01 06:21:14',0),(4,'游客',NULL,NULL,'2021-09-27 09:37:13','2021-09-27 09:37:13',0);

/*Table structure for table `role_permission` */

DROP TABLE IF EXISTS `role_permission`;

CREATE TABLE `role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL DEFAULT '0',
  `permission_id` bigint(11) NOT NULL DEFAULT '0',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`),
  KEY `idx_role_id` (`role_id`),
  KEY `idx_permission_id` (`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 COMMENT='角色权限';

/*Data for the table `role_permission` */

insert  into `role_permission`(`id`,`role_id`,`permission_id`,`create_time`,`update_time`,`is_deleted`) values (3,4,1,'2021-12-01 06:25:27','2021-12-01 06:25:27',0),(4,4,2,'2021-12-01 06:25:27','2021-12-01 06:25:27',0),(5,4,3,'2021-12-01 06:25:27','2021-12-01 06:25:27',0),(6,4,6,'2021-12-01 06:25:27','2021-12-01 06:25:27',0),(7,4,7,'2021-12-01 06:25:27','2021-12-01 06:25:27',0),(8,4,8,'2021-12-01 06:25:27','2021-12-01 06:25:27',0),(9,4,9,'2021-12-01 06:25:27','2021-12-01 06:25:27',0),(10,4,4,'2021-12-01 06:25:27','2021-12-01 06:25:27',0),(11,4,10,'2021-12-01 06:25:27','2021-12-01 06:25:27',0),(12,4,11,'2021-12-01 06:25:27','2021-12-01 06:25:27',0),(13,4,12,'2021-12-01 06:25:27','2021-12-01 06:25:27',0),(14,4,13,'2021-12-01 06:25:27','2021-12-01 06:25:27',0),(15,4,5,'2021-12-01 06:25:27','2021-12-01 06:25:27',0),(16,4,15,'2021-12-01 06:25:27','2021-12-01 06:25:27',0),(17,4,16,'2021-12-01 06:25:27','2021-12-01 06:25:27',0),(18,4,17,'2021-12-01 06:25:27','2021-12-01 06:25:27',0),(19,4,18,'2021-12-01 06:25:27','2021-12-01 06:25:27',0),(20,4,19,'2021-12-01 06:25:27','2021-12-01 06:25:27',0),(21,4,20,'2021-12-01 06:25:28','2021-12-01 06:25:28',0),(22,4,23,'2021-12-01 06:25:28','2021-12-01 06:25:28',0),(23,4,24,'2021-12-01 06:25:28','2021-12-01 06:25:28',0),(24,4,25,'2021-12-01 06:25:28','2021-12-01 06:25:28',0),(25,4,27,'2021-12-01 06:25:28','2021-12-01 06:25:28',0),(26,4,30,'2021-12-01 06:25:28','2021-12-01 06:25:28',0),(27,4,31,'2021-12-01 06:25:28','2021-12-01 06:25:28',0),(28,4,32,'2021-12-01 06:25:28','2021-12-01 06:25:28',0),(29,4,28,'2021-12-01 06:25:28','2021-12-01 06:25:28',0),(30,4,26,'2021-12-01 06:25:28','2021-12-01 06:25:28',0),(31,4,29,'2021-12-01 06:25:28','2021-12-01 06:25:28',0),(32,4,36,'2021-12-01 06:25:28','2021-12-01 06:25:28',0),(33,4,40,'2021-12-01 06:25:28','2021-12-01 06:25:28',0),(34,4,41,'2021-12-01 06:25:28','2021-12-01 06:25:28',0),(35,4,42,'2021-12-01 06:25:28','2021-12-01 06:25:28',0),(36,4,43,'2021-12-01 06:25:28','2021-12-01 06:25:28',0),(37,4,45,'2021-12-01 06:25:28','2021-12-01 06:25:28',0),(38,4,46,'2021-12-01 06:25:28','2021-12-01 06:25:28',0),(39,4,47,'2021-12-01 06:25:28','2021-12-01 06:25:28',0),(40,4,48,'2021-12-01 06:25:28','2021-12-01 06:25:28',0),(41,4,49,'2021-12-01 06:25:28','2021-12-01 06:25:28',0),(42,4,50,'2021-12-01 06:25:28','2021-12-01 06:25:28',0);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
