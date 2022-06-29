/*
SQLyog Ultimate - MySQL GUI v8.2 
MySQL - 5.7.29-32-log : Database - glkt_live
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`glkt_live` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `glkt_live`;

/*Table structure for table `live_course` */

DROP TABLE IF EXISTS `live_course`;

CREATE TABLE `live_course` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `subject_id` bigint(20) DEFAULT NULL COMMENT '分类Id',
  `course_name` varchar(64) NOT NULL DEFAULT '' COMMENT '直播名称',
  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '直播开始时间',
  `end_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '直播结束时间',
  `cover` varchar(255) DEFAULT NULL,
  `course_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '课程id',
  `teacher_id` bigint(20) DEFAULT NULL COMMENT '主播老师id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COMMENT='直播课程表';

/*Data for the table `live_course` */

insert  into `live_course`(`id`,`subject_id`,`course_name`,`start_time`,`end_time`,`cover`,`course_id`,`teacher_id`,`create_time`,`update_time`,`is_deleted`) values (1,NULL,'Spring Cloud源码讲解','2021-11-07 17:00:00','2021-10-28 17:00:00','https://online-teach-file.oss-cn-beijing.aliyuncs.com/cover/2021/08/09/f5ada6ba-8d12-4c00-8ad9-6a521f71b0da.jpg',2687659,2,'2021-10-27 08:11:33','2021-11-22 13:25:21',0),(2,NULL,'大数据Spark全面分析','2021-11-27 17:00:00','2021-10-28 17:00:00','https://online-teach-file.oss-cn-beijing.aliyuncs.com/cover/2021/08/09/f5ada6ba-8d12-4c00-8ad9-6a521f71b0da.jpg',2671785,1,'2021-10-27 08:50:18','2021-11-22 13:25:27',0),(3,NULL,'微服务架构演进','2021-12-28 16:00:00','2021-10-29 16:00:00','https://online-teach-file.oss-cn-beijing.aliyuncs.com/cover/2021/08/09/f5ada6ba-8d12-4c00-8ad9-6a521f71b0da.jpg',2679789,1,'2021-10-28 07:42:04','2021-11-22 13:25:36',0),(4,NULL,'JAVA新特性全面讲解','2021-11-08 14:30:00','2021-11-09 14:30:00','https://online-teach-file.oss-cn-beijing.aliyuncs.com/cover/2021/08/09/f5ada6ba-8d12-4c00-8ad9-6a521f71b0da.jpg',2687659,4,'2021-11-08 06:00:43','2021-11-09 07:44:03',0),(5,NULL,'Spring MVC讲解','2021-11-09 15:04:00','2021-11-10 15:00:00','https://online-teach-file.oss-cn-beijing.aliyuncs.com/cover/2021/08/09/f5ada6ba-8d12-4c00-8ad9-6a521f71b0da.jpg',2689593,2,'2021-11-09 07:40:37','2021-11-22 13:31:03',0),(6,NULL,'Mysql源码级讲解','2021-11-22 21:25:00','2021-11-23 21:25:00','http://47.93.148.192:9000/gmall/20211122/1504320cbe2b246514.jpg',2709689,5,'2021-11-22 13:29:18','2021-11-22 13:30:24',0),(7,NULL,'Spark讲解','2021-11-23 18:30:00','2021-11-23 22:00:00','https://cdn.uviewui.com/uview/swiper/1.jpg',2711543,6,'2021-11-23 10:40:44','2021-11-23 10:40:44',0),(8,NULL,'11月26日晚8点电商分享','2021-11-26 17:00:00','2021-11-27 17:00:00','https://online-teach-file.oss-cn-beijing.aliyuncs.com/cover/2021/08/09/2829c8d1-f16f-44a4-96cd-d13b451a8d56.jpg',2716727,6,'2021-11-26 09:19:39','2021-11-26 09:20:58',0);

/*Table structure for table `live_course_account` */

DROP TABLE IF EXISTS `live_course_account`;

CREATE TABLE `live_course_account` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `live_course_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '直播课程id',
  `zhubo_account` varchar(20) DEFAULT NULL COMMENT '主播账号（欢拓系统的主播id）',
  `zhubo_password` varchar(32) DEFAULT NULL COMMENT '主播密码',
  `zhubo_key` varchar(64) NOT NULL DEFAULT '' COMMENT '主播登录秘钥',
  `admin_key` varchar(32) NOT NULL DEFAULT '' COMMENT '助教登录秘钥',
  `user_key` varchar(32) NOT NULL DEFAULT '' COMMENT '学生登录秘钥',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COMMENT='直播课程账号表（受保护信息）';

/*Data for the table `live_course_account` */

insert  into `live_course_account`(`id`,`live_course_id`,`zhubo_account`,`zhubo_password`,`zhubo_key`,`admin_key`,`user_key`,`create_time`,`update_time`,`is_deleted`) values (1,1,'582523',NULL,'2518','6240','8447','2021-10-27 08:11:33','2021-10-27 08:11:33',0),(2,2,'582549','000000','1325','6632','8582','2021-10-27 08:50:30','2021-10-27 08:50:30',0),(3,3,'582825','111111','1758','5167','8643','2021-10-28 07:42:04','2021-10-28 07:42:04',0),(4,4,'582549','000000','3800','4233','8874','2021-11-08 06:00:43','2021-11-08 06:04:27',0),(5,5,'582523','111111','3060','4868','8963','2021-11-09 07:40:37','2021-11-09 07:40:37',0),(6,6,'582825','111111','3977','4548','8658','2021-11-22 13:29:18','2021-11-22 13:29:18',0),(7,7,'589813','111111','1921','4328','8302','2021-11-23 10:40:44','2021-11-23 10:40:44',0),(8,8,'589813','111111','3108','6824','8717','2021-11-26 09:19:39','2021-11-26 09:19:39',0);

/*Table structure for table `live_course_config` */

DROP TABLE IF EXISTS `live_course_config`;

CREATE TABLE `live_course_config` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `live_course_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '直播课程id',
  `page_view_mode` tinyint(3) NOT NULL DEFAULT '0' COMMENT '界面模式 1全屏模式 0二分屏 2课件模式',
  `number_enable` tinyint(3) NOT NULL DEFAULT '0' COMMENT '是否开启 观看人数 0否 1是',
  `store_enable` tinyint(3) NOT NULL DEFAULT '0' COMMENT '商城是否开启 0未开启 1开启',
  `store_type` tinyint(3) NOT NULL DEFAULT '1' COMMENT '1商品列表,2商城链接,3商城二维码',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COMMENT='直播课程配置表';

/*Data for the table `live_course_config` */

insert  into `live_course_config`(`id`,`live_course_id`,`page_view_mode`,`number_enable`,`store_enable`,`store_type`,`create_time`,`update_time`,`is_deleted`) values (1,2,1,1,1,1,'2021-10-28 07:27:23','2021-10-28 07:27:23',0),(2,3,1,1,1,1,'2021-10-28 07:42:29','2021-10-28 07:42:29',0),(3,4,1,1,1,1,'2021-11-08 06:12:04','2021-11-08 06:12:04',0),(4,5,1,1,1,1,'2021-11-09 07:54:01','2021-11-09 07:54:01',0),(5,6,1,1,1,1,'2021-11-22 13:32:13','2021-11-22 13:32:13',0),(6,7,1,1,1,1,'2021-11-23 10:42:01','2021-11-23 10:42:01',0),(7,8,1,1,1,1,'2021-11-26 09:20:07','2021-11-26 09:20:07',0);

/*Table structure for table `live_course_description` */

DROP TABLE IF EXISTS `live_course_description`;

CREATE TABLE `live_course_description` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `live_course_id` bigint(20) DEFAULT NULL,
  `description` text COMMENT '课程简介',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COMMENT='课程简介';

/*Data for the table `live_course_description` */

insert  into `live_course_description`(`id`,`live_course_id`,`description`,`create_time`,`update_time`,`is_deleted`) values (1,1,'<p>本套Java视频完全针对零基础学员，课堂实录，自发布以来，好评如潮！Java视频中注重与学生互动，讲授幽默诙谐、细致入微，覆盖Java基础所有核心知识点，同类Java视频中也是代码量大、案例多、实战性强的。同时，本Java视频教程注重技术原理剖析，深入JDK源码，辅以代码实战贯穿始终，用实践驱动理论，并辅以必要的代码练习。</p>\n<p>------------------------------------</p>\n<p>视频特点：</p>\n<p>通过学习本Java视频教程，大家能够真正将Java基础知识学以致用、活学活用，构架Java编程思想，牢牢掌握\"源码级\"的Javase核心技术，并为后续JavaWeb等技术的学习奠定扎实基础。<br /><br />1.通俗易懂，细致入微：每个知识点高屋建瓴，深入浅出，简洁明了的说明问题<br />2.具实战性：全程真正代码实战，涵盖上百个企业应用案例及练习<br />3.深入：源码分析，更有 Java 反射、动态代理的实际应用等<br />4.登录尚硅谷官网，技术讲师免费在线答疑</p>\n','2021-11-08 01:42:07','2021-11-08 01:42:18',0),(2,2,'<p>本套Java视频完全针对零基础学员，课堂实录，自发布以来，好评如潮！Java视频中注重与学生互动，讲授幽默诙谐、细致入微，覆盖Java基础所有核心知识点，同类Java视频中也是代码量大、案例多、实战性强的。同时，本Java视频教程注重技术原理剖析，深入JDK源码，辅以代码实战贯穿始终，用实践驱动理论，并辅以必要的代码练习。</p>\n<p>------------------------------------</p>\n<p>视频特点：</p>\n<p>通过学习本Java视频教程，大家能够真正将Java基础知识学以致用、活学活用，构架Java编程思想，牢牢掌握\"源码级\"的Javase核心技术，并为后续JavaWeb等技术的学习奠定扎实基础。<br /><br />1.通俗易懂，细致入微：每个知识点高屋建瓴，深入浅出，简洁明了的说明问题<br />2.具实战性：全程真正代码实战，涵盖上百个企业应用案例及练习<br />3.深入：源码分析，更有 Java 反射、动态代理的实际应用等<br />4.登录尚硅谷官网，技术讲师免费在线答疑</p>\n','2021-11-08 01:42:25','2021-11-08 01:42:30',0),(14,3,'<p>本套Java视频完全针对零基础学员，课堂实录，自发布以来，好评如潮！Java视频中注重与学生互动，讲授幽默诙谐、细致入微，覆盖Java基础所有核心知识点，同类Java视频中也是代码量大、案例多、实战性强的。同时，本Java视频教程注重技术原理剖析，深入JDK源码，辅以代码实战贯穿始终，用实践驱动理论，并辅以必要的代码练习。</p>\n<p>------------------------------------</p>\n<p>视频特点：</p>\n<p>通过学习本Java视频教程，大家能够真正将Java基础知识学以致用、活学活用，构架Java编程思想，牢牢掌握\"源码级\"的Javase核心技术，并为后续JavaWeb等技术的学习奠定扎实基础。<br /><br />1.通俗易懂，细致入微：每个知识点高屋建瓴，深入浅出，简洁明了的说明问题<br />2.具实战性：全程真正代码实战，涵盖上百个企业应用案例及练习<br />3.深入：源码分析，更有 Java 反射、动态代理的实际应用等<br />4.登录尚硅谷官网，技术讲师免费在线答疑</p>\n','2021-11-08 01:42:34','2021-11-08 01:42:34',0),(15,4,'<p>本套Java视频完全针对零基础学员，课堂实录，自发布以来，好评如潮！Java视频中注重与学生互动，讲授幽默诙谐、细致入微，覆盖Java基础所有核心知识点，同类Java视频中也是代码量大、案例多、实战性强的。同时，本Java视频教程注重技术原理剖析，深入JDK源码，辅以代码实战贯穿始终，用实践驱动理论，并辅以必要的代码练习。</p>\n<p>------------------------------------</p>\n<p>视频特点：</p>\n<p>通过学习本Java视频教程，大家能够真正将Java基础知识学以致用、活学活用，构架Java编程思想，牢牢掌握\"源码级\"的Javase核心技术，并为后续JavaWeb等技术的学习奠定扎实基础。<br /><br />1.通俗易懂，细致入微：每个知识点高屋建瓴，深入浅出，简洁明了的说明问题<br />2.具实战性：全程真正代码实战，涵盖上百个企业应用案例及练习<br />3.深入：源码分析，更有 Java 反射、动态代理的实际应用等<br />4.登录尚硅谷官网，技术讲师免费在线答疑</p>','2021-11-08 06:00:43','2021-11-08 06:24:59',0),(16,5,'本套Java视频完全针对零基础学员，课堂实录，自发布以来，好评如潮！Java视频中注重与学生互动，讲授幽默诙谐、细致入微，覆盖Java基础所有核心知识点，同类Java视频中也是代码量大、案例多、实战性强的。同时，本Java视频教程注重技术原理剖析，深入JDK源码，辅以代码实战贯穿始终，用实践驱动理论，并辅以必要的代码练习。','2021-11-09 07:40:37','2021-11-09 07:40:37',0),(17,6,' 数据库就像一棵常青的技能树，无论是初级程序员还是CTO、首席架构师都能从中汲取足够的技术养料。菜鸟往往积累单点技术，如 DML、DDL、存储过程和函数、约束、索引的数据结构，老鸟则需要吃透底层原理，数据库事务ACID如何实现？锁机制与MVCC又是怎么回事？分布式场景下数据库怎么优化保持高性能？\n      知道怎么用是一方面，知道为什么则是更为稀缺的能力。程序员核心能力中至关重要的一点：精通数据库。精通意味着：第一，形成知识网，更灵活地应对突发问题；第二，懂底层原理，更自由地应对复杂多变的业务场景。','2021-11-22 13:29:18','2021-11-22 13:29:18',0),(18,7,'Spring4.0是 Spring 推出的一个重大版本升级，进一步加强了 Spring 作为 Java 领域第一开源平台的地位。Spring4.0 引入了众多 Java 开发者期盼的新特性，如泛型依赖注入、SpEL、校验及格式化框架、Rest风格的 WEB 编程模型等。这些新功能实用性强、易用性高，可大幅降低 JavaEE 开发的难度，同时有效提升应用开发的优雅性。','2021-11-23 10:40:44','2021-11-23 10:40:44',0),(19,8,' 数据库就像一棵常青的技能树，无论是初级程序员还是CTO、首席架构师都能从中汲取足够的技术养料。菜鸟往往积累单点技术，如 DML、DDL、存储过程和函数、约束、索引的数据结构，老鸟则需要吃透底层原理，数据库事务ACID如何实现？锁机制与MVCC又是怎么回事？分布式场景下数据库怎么优化保持高性能？\n      知道怎么用是一方面，知道为什么则是更为稀缺的能力。程序员核心能力中至关重要的一点：精通数据库。精通意味着：第一，形成知识网，更灵活地应对突发问题；第二，懂底层原理，更自由地应对复杂多变的业务场景。\n','2021-11-26 09:19:39','2021-11-26 09:21:18',0);

/*Table structure for table `live_course_goods` */

DROP TABLE IF EXISTS `live_course_goods`;

CREATE TABLE `live_course_goods` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `live_course_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '直播课程id',
  `goods_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '推荐点播课程id',
  `name` varchar(100) DEFAULT NULL COMMENT '商品名称',
  `img` varchar(255) DEFAULT NULL COMMENT '图片',
  `price` decimal(10,0) DEFAULT NULL COMMENT '商品现价',
  `originalPrice` decimal(10,0) DEFAULT NULL COMMENT '商品原价',
  `tab` tinyint(3) DEFAULT NULL COMMENT '商品标签',
  `url` varchar(255) DEFAULT NULL COMMENT '商品链接',
  `putaway` varchar(255) DEFAULT NULL COMMENT '商品状态：0下架，1上架，2推荐',
  `pay` tinyint(3) DEFAULT NULL COMMENT '购买模式(1,链接购买 2,二维码购买)',
  `qrcode` varchar(255) DEFAULT NULL COMMENT '商品二维码',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COMMENT='直播课程关联推荐表';

/*Data for the table `live_course_goods` */

insert  into `live_course_goods`(`id`,`live_course_id`,`goods_id`,`name`,`img`,`price`,`originalPrice`,`tab`,`url`,`putaway`,`pay`,`qrcode`,`create_time`,`update_time`,`is_deleted`) values (1,2,1,'小米1','http://47.93.148.192:9000/gmall/20211027/235.jpg','10','10',1,'http://item.atguigu.cn/1.html','1',1,'','2021-10-28 07:29:36','2021-10-28 08:54:17',0),(2,3,1,'小米2','http://47.93.148.192:9000/gmall/20211027/235.jpg','10','10',1,'http://item.atguigu.cn/2.html','1',1,'','2021-10-28 07:42:30','2021-10-28 08:52:54',0),(3,4,18,'Java精品课程','https://online-teach-file.oss-cn-beijing.aliyuncs.com/cover/2021/08/09/e4ee03d7-52bd-41ca-99f9-04dc23250a71.jpg','22800','22800',1,'http://item.gmall.com/118.html','1',1,'','2021-11-08 06:12:05','2021-11-08 06:12:05',0),(4,4,8,'大数据Scala入门到精通（新版）','https://online-teach-file.oss-cn-beijing.aliyuncs.com/cover/2021/08/10/f2cd321f-6378-4e92-8515-0b8f42f2770b.jpg','0','0',1,'http://item.gmall.com/18.html','1',1,'','2021-11-08 06:12:05','2021-11-08 06:12:05',0),(5,5,15,'  14417人 分享 收藏 SpringMVC','https://online-teach-file.oss-cn-beijing.aliyuncs.com/cover/2021/08/09/273ddd90-5ef7-40e5-9ffd-86e8175fc229.jpg','22800','22800',1,'http://glkt.atguigu.cn/#/courseInfo/15','1',1,'','2021-11-09 07:54:01','2021-11-09 07:54:01',0),(6,6,19,'JAVA之Mysql基础','http://47.93.148.192:9000/gmall/20211122/1504320cbe2b246514.jpg','1000','1000',1,'http://glkt.atguigu.cn/#/courseInfo/19','1',1,'','2021-11-22 13:32:13','2021-11-22 13:32:13',0),(7,6,18,'Java精品课程','https://online-teach-file.oss-cn-beijing.aliyuncs.com/cover/2021/08/09/e4ee03d7-52bd-41ca-99f9-04dc23250a71.jpg','22800','22800',1,'http://glkt.atguigu.cn/#/courseInfo/18','1',1,'','2021-11-22 13:32:13','2021-11-22 13:32:13',0),(8,7,19,'JAVA之Mysql基础','http://47.93.148.192:9000/gmall/20211122/1504320cbe2b246514.jpg','1000','1000',1,'http://glkt.atguigu.cn/#/courseInfo/19','1',1,'','2021-11-23 10:42:01','2021-11-23 10:42:01',0),(9,7,15,'  14417人 分享 收藏 SpringMVC','https://online-teach-file.oss-cn-beijing.aliyuncs.com/cover/2021/08/09/273ddd90-5ef7-40e5-9ffd-86e8175fc229.jpg','22800','22800',1,'http://glkt.atguigu.cn/#/courseInfo/15','1',1,'','2021-11-23 10:42:01','2021-11-23 10:42:01',0),(10,8,19,'JAVA之Mysql基础','http://47.93.148.192:9000/gmall/20211122/1504320cbe2b246514.jpg','1000','1000',1,'http://glkt.atguigu.cn/#/courseInfo/19','1',1,'','2021-11-26 09:20:07','2021-11-26 09:20:07',0),(11,8,15,'  14417人 分享 收藏 SpringMVC','https://online-teach-file.oss-cn-beijing.aliyuncs.com/cover/2021/08/09/273ddd90-5ef7-40e5-9ffd-86e8175fc229.jpg','22800','22800',1,'http://glkt.atguigu.cn/#/courseInfo/15','1',1,'','2021-11-26 09:20:07','2021-11-26 09:20:07',0);

/*Table structure for table `live_visitor` */

DROP TABLE IF EXISTS `live_visitor`;

CREATE TABLE `live_visitor` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `live_course_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '直播课程id',
  `course_name` varchar(100) DEFAULT NULL,
  `user_id` varchar(50) NOT NULL DEFAULT '0' COMMENT '来访者用户id',
  `nick_name` varchar(100) DEFAULT NULL COMMENT '昵称',
  `join_time` varchar(30) DEFAULT NULL COMMENT '进入时间',
  `leave_time` varchar(30) DEFAULT NULL COMMENT '离开的时间',
  `location` varchar(50) DEFAULT NULL COMMENT '用户地理位置',
  `duration` bigint(20) DEFAULT NULL COMMENT '用户停留的时间(单位：秒)',
  `duration_time` varchar(30) DEFAULT NULL COMMENT '用户停留时间(时分秒)',
  `live_visitor_id` varchar(50) DEFAULT NULL COMMENT '平台来访者id，去重使用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_live_visitor_id` (`live_visitor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COMMENT='直播来访者记录表';

/*Data for the table `live_visitor` */

insert  into `live_visitor`(`id`,`live_course_id`,`course_name`,`user_id`,`nick_name`,`join_time`,`leave_time`,`location`,`duration`,`duration_time`,`live_visitor_id`,`create_time`,`update_time`,`is_deleted`) values (6,1,'Spring Cloud深入源码讲解','1','晴天','2021-11-08 14:41:43','2021-11-08 14:48:05','中国北京北京',382,'00:06:22','220172045','2021-11-11 08:01:42','2021-11-11 08:01:42',0),(7,1,'Spring Cloud深入源码讲解','zb_xid_508954845','钟老师','2021-11-08 14:41:44','2021-11-08 14:47:47','中国四川成都',363,'00:06:03','220172043','2021-11-11 08:01:42','2021-11-11 08:01:42',0),(8,1,'Spring Cloud深入源码讲解','4','晴天','2021-11-08 14:10:06','2021-11-08 14:25:23','中国四川成都',917,'00:15:17','220171079','2021-11-11 08:01:42','2021-11-11 08:01:42',0),(9,1,'Spring Cloud深入源码讲解','zb_xid_508954845','钟老师','2021-11-08 14:04:41','2021-11-08 14:35:09','中国四川成都',1828,'00:30:28','220171077','2021-11-11 08:01:42','2021-11-11 08:01:42',0);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
