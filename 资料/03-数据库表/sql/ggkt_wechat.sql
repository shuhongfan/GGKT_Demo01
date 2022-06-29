/*
SQLyog Ultimate - MySQL GUI v8.2 
MySQL - 5.7.29-32-log : Database - glkt_wechat
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`glkt_wechat` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `glkt_wechat`;

/*Table structure for table `menu` */

DROP TABLE IF EXISTS `menu`;

CREATE TABLE `menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级id',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `type` varchar(10) DEFAULT NULL COMMENT '类型',
  `url` varchar(100) DEFAULT NULL COMMENT '网页 链接，用户点击菜单可打开链接',
  `meun_key` varchar(20) DEFAULT NULL COMMENT '菜单KEY值，用于消息接口推送',
  `sort` tinyint(3) DEFAULT NULL COMMENT '排序',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='订单明细 订单明细';

/*Data for the table `menu` */

insert  into `menu`(`id`,`parent_id`,`name`,`type`,`url`,`meun_key`,`sort`,`create_time`,`update_time`,`is_deleted`) values (1,0,'直播',NULL,NULL,NULL,1,'2021-11-24 08:41:53','2021-11-24 08:44:55',0),(2,0,'课程',NULL,NULL,NULL,2,'2021-11-24 08:41:57','2021-11-25 01:33:52',0),(3,0,'我的',NULL,NULL,NULL,3,'2021-11-24 08:42:00','2021-11-25 01:34:16',0),(4,3,'关于我们','click',NULL,'aboutUs',10,'2021-11-24 08:42:05','2021-11-24 08:45:00',0),(5,1,'微服务架构演进','view','/liveInfo/3','',2,'2021-11-24 10:29:12','2021-11-25 01:26:13',0),(6,1,'大数据Spark全面分析','view','/liveInfo/2','',4,'2021-11-24 10:29:24','2021-11-25 01:27:05',0),(7,2,'后端开发','view','/course/1','',1,'2021-11-24 10:31:48','2021-11-25 01:27:06',0),(8,2,'大数据','view','/course/14','',2,'2021-11-24 10:31:59','2021-11-25 01:27:07',0),(9,3,'我的订单','view','/order','',1,'2021-11-25 01:19:25','2021-11-25 01:27:07',0),(10,3,'我的课程','view','/myCourse','',2,'2021-11-25 01:26:51','2021-11-25 01:26:51',0),(11,1,'全部列表','view','/live','',6,'2021-11-25 01:41:47','2021-11-25 01:41:47',0),(12,3,'我的优惠券','view','/coupon',NULL,3,'2021-11-26 08:52:27','2021-11-26 08:52:40',0),(13,1,'11月26日晚8点电商分享','view','/liveInfo/8','',1,'2021-11-26 09:21:39','2021-11-26 09:21:39',0);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
