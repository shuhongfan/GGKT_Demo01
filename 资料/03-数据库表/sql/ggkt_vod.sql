/*
SQLyog Ultimate - MySQL GUI v8.2 
MySQL - 5.7.29-32-log : Database - glkt_vod
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`glkt_vod` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `glkt_vod`;

/*Table structure for table `chapter` */

DROP TABLE IF EXISTS `chapter`;

CREATE TABLE `chapter` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `course_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '课程ID',
  `title` varchar(50) NOT NULL COMMENT '章节名称',
  `sort` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '显示排序',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_course_id` (`course_id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='课程';

/*Data for the table `chapter` */

insert  into `chapter`(`id`,`course_id`,`title`,`sort`,`create_time`,`update_time`,`is_deleted`) values (1,18,'第七章：I/O流',7,'2021-11-05 03:05:51','2021-12-01 05:48:44',0),(2,2,'第一章节',0,'2021-11-05 03:05:51','2021-11-05 03:38:14',0),(3,3,'第一章',0,'2021-11-05 03:05:51','2021-11-05 03:38:24',0),(4,4,'第一章',0,'2021-11-05 03:05:51','2021-11-05 03:38:34',0),(5,14,'第一章：HTML',0,'2021-11-05 03:05:51','2021-11-05 03:05:51',0),(6,14,'第二章：CSS',0,'2021-11-05 03:05:51','2021-11-05 03:05:51',0),(7,5,'第一章',0,'2021-11-05 03:05:51','2021-11-05 03:38:50',0),(8,15,'第一章',0,'2021-11-05 03:05:51','2021-11-05 03:39:03',0),(9,6,'数据仓库基础',0,'2021-11-05 03:05:51','2021-11-05 03:39:14',0),(10,7,'课程基础',0,'2021-11-05 03:05:51','2021-11-05 03:39:26',0),(11,8,'介绍及原理',0,'2021-11-05 03:05:51','2021-11-05 03:39:38',0),(12,1,'spark介绍',0,'2021-11-05 03:05:51','2021-11-05 03:39:50',0),(15,18,'第一章：Java入门',1,'2021-11-05 03:05:51','2021-12-01 05:48:35',0),(16,18,'第二章：控制台输入和输出',2,'2021-11-05 03:05:51','2021-12-01 05:48:36',0),(17,18,'第三章：控制流',3,'2021-11-05 03:05:51','2021-12-01 05:48:36',0),(18,18,'第四章：类的定义',4,'2021-11-05 03:05:51','2021-12-01 05:48:38',0),(19,18,'第五章：数组',5,'2021-11-05 03:05:51','2021-12-01 05:48:41',0),(20,18,'第六章：继承',6,'2021-11-05 03:05:51','2021-12-01 05:48:42',0),(65,19,'入门及安装配置',0,'2021-11-22 11:10:56','2021-11-22 11:10:56',0),(66,19,'mysql原理',1,'2021-11-22 11:11:17','2021-11-22 11:11:17',0);

/*Table structure for table `comment` */

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `course_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '课程id',
  `teacher_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '讲师id',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '会员id',
  `nickname` varchar(50) DEFAULT NULL COMMENT '会员昵称',
  `avatar` varchar(255) DEFAULT NULL COMMENT '会员头像',
  `content` varchar(500) DEFAULT NULL COMMENT '评论内容',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_course_id` (`course_id`),
  KEY `idx_teacher_id` (`teacher_id`),
  KEY `idx_member_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评论';

/*Data for the table `comment` */

/*Table structure for table `course` */

DROP TABLE IF EXISTS `course`;

CREATE TABLE `course` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `teacher_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '课程讲师ID',
  `subject_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '课程专业ID',
  `subject_parent_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '课程专业父级ID',
  `title` varchar(50) NOT NULL COMMENT '课程标题',
  `price` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '课程销售价格，设置为0则可免费观看',
  `lesson_num` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '总课时',
  `duration_sum` int(11) NOT NULL DEFAULT '0' COMMENT '视频总时长（秒）',
  `cover` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '课程封面图片路径',
  `buy_count` bigint(10) unsigned NOT NULL DEFAULT '0' COMMENT '销售数量',
  `view_count` bigint(10) unsigned NOT NULL DEFAULT '0' COMMENT '浏览数量',
  `status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '课程状态 0未发布 1已发布',
  `publish_time` datetime DEFAULT NULL COMMENT '课程发布时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_title` (`title`),
  KEY `idx_subject_id` (`subject_id`),
  KEY `idx_teacher_id` (`teacher_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='课程';

/*Data for the table `course` */

insert  into `course`(`id`,`teacher_id`,`subject_id`,`subject_parent_id`,`title`,`price`,`lesson_num`,`duration_sum`,`cover`,`buy_count`,`view_count`,`status`,`publish_time`,`create_time`,`update_time`,`is_deleted`) values (1,1,16,14,'Spark','21800.00',0,100000,'https://online-teach-file.oss-cn-beijing.aliyuncs.com/cover/2021/08/10/39e51c23-e2fa-4a8a-9239-3c0d5268f34d.jpg',1679,34634,1,'2021-11-05 11:30:14','2021-11-05 03:05:16','2021-11-24 06:37:39',0),(2,1,2,1,'java基础课程','19800.00',2,100000,'https://online-teach-file.oss-cn-beijing.aliyuncs.com/cover/2021/08/09/bd89ae0b-e660-47fd-9ed7-ad8733a46c65.jpg',5999,10048,0,NULL,'2021-11-05 03:05:16','2021-11-24 06:37:37',0),(3,1,15,14,'尚硅谷大数据技术之Flume（2019新版）','20800.00',0,100000,'https://online-teach-file.oss-cn-beijing.aliyuncs.com/cover/2021/08/09/806572e0-6d85-485e-8784-d2cb4716a881.jpg',4234,23780,0,NULL,'2021-11-05 03:05:16','2021-11-24 06:37:36',0),(4,2,15,14,'尚硅谷大数据技术之HBase（2019新版）','19800.00',0,100000,'https://online-teach-file.oss-cn-beijing.aliyuncs.com/cover/2021/08/09/a16c5694-3037-4330-b1c5-438052081fcb.jpg',6675,90383,0,NULL,'2021-11-05 03:05:16','2021-11-24 06:37:34',0),(5,1,1,2,'尚硅谷大数据技术之Kafka（2019新版）','22800.00',0,100000,'https://online-teach-file.oss-cn-beijing.aliyuncs.com/cover/2021/08/09/f5ada6ba-8d12-4c00-8ad9-6a521f71b0da.jpg',2349,13546,0,NULL,'2021-11-05 03:05:16','2021-11-24 06:37:33',0),(6,2,15,14,'尚硅谷大数据项目之电商数仓','21800.00',0,100000,'https://online-teach-file.oss-cn-beijing.aliyuncs.com/cover/2021/08/10/dd2da284-b37f-4818-ba9a-c55667837f5a.jpg',898,8977,0,NULL,'2021-11-05 03:05:16','2021-11-24 06:37:32',0),(7,2,15,14,'尚硅谷大数据技术之Sqoop','23800.00',0,100000,'https://online-teach-file.oss-cn-beijing.aliyuncs.com/cover/2021/08/10/9452b057-6ad6-4600-891e-b168083fee4d.jpg',1286,45695,0,NULL,'2021-11-05 03:05:16','2021-11-24 06:37:31',0),(8,1,16,14,'大数据Scala入门到精通（新版）','0.00',0,100000,'https://online-teach-file.oss-cn-beijing.aliyuncs.com/cover/2021/08/10/f2cd321f-6378-4e92-8515-0b8f42f2770b.jpg',900,15496,0,NULL,'2021-11-05 03:05:16','2021-11-24 06:37:13',0),(14,1,4,3,'XHTML CSS2 JS整站制作教程课程学习','21800.00',3,100000,'https://online-teach-file.oss-cn-beijing.aliyuncs.com/cover/2021/08/09/2829c8d1-f16f-44a4-96cd-d13b451a8d56.jpg',3452,21010,0,NULL,'2021-11-05 03:05:16','2021-11-24 06:37:28',0),(15,2,2,1,'  14417人 分享 收藏 SpringMVC','22800.00',23,100000,'https://online-teach-file.oss-cn-beijing.aliyuncs.com/cover/2021/08/09/273ddd90-5ef7-40e5-9ffd-86e8175fc229.jpg',892,9107,0,NULL,'2021-11-05 03:05:16','2021-11-24 06:37:24',0),(18,2,2,1,'Java精品课程','22800.00',20,100000,'https://online-teach-file.oss-cn-beijing.aliyuncs.com/cover/2021/08/09/e4ee03d7-52bd-41ca-99f9-04dc23250a71.jpg',6784,67629,1,'2021-11-19 14:16:19','2021-11-05 03:05:16','2021-11-24 06:37:25',0),(19,1,2,1,'JAVA之Mysql基础','1000.00',10,100201,'http://47.93.148.192:9000/gmall/20211122/1504320cbe2b246514.jpg',0,155,1,'2021-11-25 09:57:42','2021-11-22 11:09:22','2021-11-25 01:57:39',0);

/*Table structure for table `course_collect` */

DROP TABLE IF EXISTS `course_collect`;

CREATE TABLE `course_collect` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `course_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '课程讲师ID',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '会员ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='课程收藏';

/*Data for the table `course_collect` */

/*Table structure for table `course_description` */

DROP TABLE IF EXISTS `course_description`;

CREATE TABLE `course_description` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `course_id` bigint(20) DEFAULT NULL,
  `description` text COMMENT '课程简介',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COMMENT='课程简介';

/*Data for the table `course_description` */

insert  into `course_description`(`id`,`course_id`,`description`,`create_time`,`update_time`,`is_deleted`) values (1,1,'<p>本套Java视频完全针对零基础学员，课堂实录，自发布以来，好评如潮！Java视频中注重与学生互动，讲授幽默诙谐、细致入微，覆盖Java基础所有核心知识点，同类Java视频中也是代码量大、案例多、实战性强的。同时，本Java视频教程注重技术原理剖析，深入JDK源码，辅以代码实战贯穿始终，用实践驱动理论，并辅以必要的代码练习。</p>','2021-11-05 03:29:33','2021-11-08 11:27:01',0),(2,2,'<p>本套Java视频完全针对零基础学员，课堂实录，自发布以来，好评如潮！Java视频中注重与学生互动，讲授幽默诙谐、细致入微，覆盖Java基础所有核心知识点，同类Java视频中也是代码量大、案例多、实战性强的。同时，本Java视频教程注重技术原理剖析，深入JDK源码，辅以代码实战贯穿始终，用实践驱动理论，并辅以必要的代码练习。</p>','2021-11-05 03:30:07','2021-11-08 11:27:04',0),(3,3,'<p>本套Java视频完全针对零基础学员，课堂实录，自发布以来，好评如潮！Java视频中注重与学生互动，讲授幽默诙谐、细致入微，覆盖Java基础所有核心知识点，同类Java视频中也是代码量大、案例多、实战性强的。同时，本Java视频教程注重技术原理剖析，深入JDK源码，辅以代码实战贯穿始终，用实践驱动理论，并辅以必要的代码练习。</p>','2021-11-05 03:30:47','2021-11-08 11:27:07',0),(4,4,'<p>本套Java视频完全针对零基础学员，课堂实录，自发布以来，好评如潮！Java视频中注重与学生互动，讲授幽默诙谐、细致入微，覆盖Java基础所有核心知识点，同类Java视频中也是代码量大、案例多、实战性强的。同时，本Java视频教程注重技术原理剖析，深入JDK源码，辅以代码实战贯穿始终，用实践驱动理论，并辅以必要的代码练习。</p>\n<p>------------------------------------</p>\n<p>视频特点：</p>\n<p>通过学习本Java视频教程，大家能够真正将Java基础知识学以致用、活学活用，构架Java编程思想，牢牢掌握\"源码级\"的Javase核心技术，并为后续JavaWeb等技术的学习奠定扎实基础。<br /><br />1.通俗易懂，细致入微：每个知识点高屋建瓴，深入浅出，简洁明了的说明问题<br />2.具实战性：全程真正代码实战，涵盖上百个企业应用案例及练习<br />3.深入：源码分析，更有 Java 反射、动态代理的实际应用等<br />4.登录尚硅谷官网，技术讲师免费在线答疑</p>\n','2021-11-05 03:33:18','2021-11-08 11:25:23',0),(5,5,'<p>本套Java视频完全针对零基础学员，课堂实录，自发布以来，好评如潮！Java视频中注重与学生互动，讲授幽默诙谐、细致入微，覆盖Java基础所有核心知识点，同类Java视频中也是代码量大、案例多、实战性强的。同时，本Java视频教程注重技术原理剖析，深入JDK源码，辅以代码实战贯穿始终，用实践驱动理论，并辅以必要的代码练习。</p>','2021-11-05 03:41:05','2021-11-08 11:27:10',0),(6,6,'<p>本套Java视频完全针对零基础学员，课堂实录，自发布以来，好评如潮！Java视频中注重与学生互动，讲授幽默诙谐、细致入微，覆盖Java基础所有核心知识点，同类Java视频中也是代码量大、案例多、实战性强的。同时，本Java视频教程注重技术原理剖析，深入JDK源码，辅以代码实战贯穿始终，用实践驱动理论，并辅以必要的代码练习。</p>','2021-11-05 03:41:27','2021-11-08 11:27:12',0),(7,7,'<p>本套Java视频完全针对零基础学员，课堂实录，自发布以来，好评如潮！Java视频中注重与学生互动，讲授幽默诙谐、细致入微，覆盖Java基础所有核心知识点，同类Java视频中也是代码量大、案例多、实战性强的。同时，本Java视频教程注重技术原理剖析，深入JDK源码，辅以代码实战贯穿始终，用实践驱动理论，并辅以必要的代码练习。</p>','2021-11-05 03:41:43','2021-11-08 11:27:22',0),(8,8,'<p>本套Java视频完全针对零基础学员，课堂实录，自发布以来，好评如潮！Java视频中注重与学生互动，讲授幽默诙谐、细致入微，覆盖Java基础所有核心知识点，同类Java视频中也是代码量大、案例多、实战性强的。同时，本Java视频教程注重技术原理剖析，深入JDK源码，辅以代码实战贯穿始终，用实践驱动理论，并辅以必要的代码练习。</p>\n<p>------------------------------------</p>\n<p>视频特点：</p>\n<p>通过学习本Java视频教程，大家能够真正将Java基础知识学以致用、活学活用，构架Java编程思想，牢牢掌握\"源码级\"的Javase核心技术，并为后续JavaWeb等技术的学习奠定扎实基础。<br /><br />1.通俗易懂，细致入微：每个知识点高屋建瓴，深入浅出，简洁明了的说明问题<br />2.具实战性：全程真正代码实战，涵盖上百个企业应用案例及练习<br />3.深入：源码分析，更有 Java 反射、动态代理的实际应用等<br />4.登录尚硅谷官网，技术讲师免费在线答疑</p>','2021-11-05 03:42:01','2021-11-08 11:25:30',0),(9,14,'<p>本套Java视频完全针对零基础学员，课堂实录，自发布以来，好评如潮！Java视频中注重与学生互动，讲授幽默诙谐、细致入微，覆盖Java基础所有核心知识点，同类Java视频中也是代码量大、案例多、实战性强的。同时，本Java视频教程注重技术原理剖析，深入JDK源码，辅以代码实战贯穿始终，用实践驱动理论，并辅以必要的代码练习。</p>','2021-11-05 03:42:16','2021-11-08 11:27:24',0),(10,15,'<p>本套Java视频完全针对零基础学员，课堂实录，自发布以来，好评如潮！Java视频中注重与学生互动，讲授幽默诙谐、细致入微，覆盖Java基础所有核心知识点，同类Java视频中也是代码量大、案例多、实战性强的。同时，本Java视频教程注重技术原理剖析，深入JDK源码，辅以代码实战贯穿始终，用实践驱动理论，并辅以必要的代码练习。</p>','2021-11-05 03:42:32','2021-11-08 11:27:26',0),(11,18,'<p>本套Java视频完全针对零基础学员，课堂实录，自发布以来，好评如潮！Java视频中注重与学生互动，讲授幽默诙谐、细致入微，覆盖Java基础所有核心知识点，同类Java视频中也是代码量大、案例多、实战性强的。同时，本Java视频教程注重技术原理剖析，深入JDK源码，辅以代码实战贯穿始终，用实践驱动理论，并辅以必要的代码练习。</p>','2021-11-05 03:42:51','2021-11-08 11:27:28',0),(12,19,' 数据库就像一棵常青的技能树，无论是初级程序员还是CTO、首席架构师都能从中汲取足够的技术养料。菜鸟往往积累单点技术，如 DML、DDL、存储过程和函数、约束、索引的数据结构，老鸟则需要吃透底层原理，数据库事务ACID如何实现？锁机制与MVCC又是怎么回事？分布式场景下数据库怎么优化保持高性能？\n      知道怎么用是一方面，知道为什么则是更为稀缺的能力。程序员核心能力中至关重要的一点：精通数据库。精通意味着：第一，形成知识网，更灵活地应对突发问题；第二，懂底层原理，更自由地应对复杂多变的业务场景。','2021-11-22 11:09:22','2021-11-22 11:09:22',0);

/*Table structure for table `subject` */

DROP TABLE IF EXISTS `subject`;

CREATE TABLE `subject` (
  `id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '主键',
  `title` varchar(10) NOT NULL COMMENT '类别名称',
  `parent_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '父ID',
  `sort` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '排序字段',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='课程科目';

/*Data for the table `subject` */

insert  into `subject`(`id`,`title`,`parent_id`,`sort`,`create_time`,`update_time`,`is_deleted`) values (1,'后端开发',0,1,'2019-09-29 15:47:25','2019-09-29 15:47:25',0),(2,'Java',1,1,'2019-09-29 15:47:25','2019-09-29 15:47:25',0),(3,'前端开发',0,3,'2019-09-29 15:47:25','2019-09-29 15:47:25',0),(4,'JavaScript',3,4,'2019-09-29 15:47:25','2019-09-29 15:47:25',0),(5,'云计算',0,5,'2019-09-29 15:47:25','2021-11-12 07:13:22',1),(6,'Docker',5,5,'2019-09-29 15:47:25','2019-09-29 15:47:25',0),(7,'Linux',5,6,'2019-09-29 15:47:25','2019-09-29 15:47:25',0),(8,'系统/运维',0,7,'2019-09-29 15:47:25','2021-11-12 07:12:54',1),(9,'Linux',8,7,'2019-09-29 15:47:25','2019-09-29 15:47:25',0),(10,'Windows',8,8,'2019-09-29 15:47:25','2019-09-29 15:47:25',0),(11,'数据库',0,9,'2019-09-29 15:47:25','2019-09-29 15:47:25',0),(12,'MySQL',11,9,'2019-09-29 15:47:25','2019-09-29 15:47:25',0),(13,'MongoDB',11,10,'2019-09-29 15:47:25','2019-09-29 15:47:25',0),(14,'大数据',0,11,'2019-09-29 15:47:25','2019-09-29 15:47:25',0),(15,'Hadoop',14,11,'2019-09-29 15:47:25','2019-09-29 15:47:25',0),(16,'Spark',14,12,'2019-09-29 15:47:25','2019-09-29 15:47:25',0),(17,'人工智能',0,13,'2019-09-29 15:47:25','2021-11-12 03:30:34',1),(18,'Python',17,13,'2019-09-29 15:47:25','2019-09-29 15:47:25',0),(19,'编程语言',0,14,'2019-09-29 15:47:25','2021-11-12 03:29:04',1),(20,'Java',19,14,'2019-09-29 15:47:25','2019-09-29 15:47:25',0),(21,'Python',19,2,'2019-09-30 16:19:22','2019-09-30 16:19:22',0),(22,'HTML/CSS',19,3,'2019-09-30 16:19:22','2019-09-30 16:19:22',0);

/*Table structure for table `teacher` */

DROP TABLE IF EXISTS `teacher`;

CREATE TABLE `teacher` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(20) NOT NULL COMMENT '讲师姓名',
  `intro` varchar(500) NOT NULL DEFAULT '' COMMENT '讲师简介',
  `career` varchar(500) DEFAULT NULL COMMENT '讲师资历,一句话说明讲师',
  `level` int(10) unsigned NOT NULL COMMENT '头衔 1高级讲师 2首席讲师',
  `avatar` varchar(255) DEFAULT NULL COMMENT '讲师头像',
  `sort` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '排序',
  `join_date` date DEFAULT NULL COMMENT '入驻时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COMMENT='讲师';

/*Data for the table `teacher` */

insert  into `teacher`(`id`,`name`,`intro`,`career`,`level`,`avatar`,`sort`,`join_date`,`create_time`,`update_time`,`is_deleted`) values (1,'张老师','高级讲师','高级讲师',1,'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIB1WtJibSTqXvnJccFhOR1cSpVpdQ3BP5eTPCUO9CyI1feDefMoUFyA4E2C1oe2j8VMLrtAyBricvA/132',0,'2021-11-02','2021-11-05 03:18:36','2021-11-08 01:50:46',0),(2,'李老师','高级讲师','高级讲师',1,'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIB1WtJibSTqXvnJccFhOR1cSpVpdQ3BP5eTPCUO9CyI1feDefMoUFyA4E2C1oe2j8VMLrtAyBricvA/132',0,'2021-11-02','2021-11-05 03:18:51','2021-11-08 01:50:49',0),(4,'钟老师','高级讲师','高级讲师',1,'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIB1WtJibSTqXvnJccFhOR1cSpVpdQ3BP5eTPCUO9CyI1feDefMoUFyA4E2C1oe2j8VMLrtAyBricvA/132',0,'2021-11-02','2021-11-08 05:51:21','2021-11-08 06:24:28',0),(5,'钱老师','钱老师','钱老师',1,'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eo8uwBUP6f5JtibWlkmpPAVjsSsibMaFupwFRyo2Vr5Gkc33uctiasfOFgZADd5X1NYP82bKYjMDbFnA/132',3,'2021-11-01','2021-11-22 13:26:58','2021-11-23 10:26:54',0),(6,'宋老师','宋老师','宋老师',1,'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eo8uwBUP6f5JtibWlkmpPAVjsSsibMaFupwFRyo2Vr5Gkc33uctiasfOFgZADd5X1NYP82bKYjMDbFnA/132',4,'2021-11-02','2021-11-23 10:25:58','2021-11-23 10:26:53',0);

/*Table structure for table `video` */

DROP TABLE IF EXISTS `video`;

CREATE TABLE `video` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `course_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '课程ID',
  `chapter_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '章节ID',
  `title` varchar(50) NOT NULL COMMENT '节点名称',
  `video_source_id` varchar(100) DEFAULT NULL COMMENT '云端视频资源',
  `video_original_name` varchar(100) DEFAULT NULL COMMENT '原始文件名称',
  `sort` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '排序字段',
  `play_count` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '播放次数',
  `is_free` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否可以试听：0收费 1免费',
  `duration` float NOT NULL DEFAULT '0' COMMENT '视频时长（秒）',
  `size` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '视频源文件大小（字节）',
  `version` bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
  `status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_course_id` (`course_id`),
  KEY `idx_chapter_id` (`chapter_id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='课程视频';

/*Data for the table `video` */

insert  into `video`(`id`,`course_id`,`chapter_id`,`title`,`video_source_id`,`video_original_name`,`sort`,`play_count`,`is_free`,`duration`,`size`,`version`,`status`,`create_time`,`update_time`,`is_deleted`) values (1,18,16,'第一节','3b71d85d93554e7dbb59becdf823f63d','',0,0,1,0,0,1,0,'2021-11-05 03:06:04','2021-12-03 10:17:54',0),(2,14,5,'html基础','3b71d85d93554e7dbb59becdf823f63d','',0,0,1,0,0,1,0,'2021-11-05 03:06:04','2021-12-03 10:17:55',0),(4,18,26,'IO高级','3b71d85d93554e7dbb59becdf823f63d','视频',1,0,1,0,0,1,0,'2021-11-05 03:06:04','2021-12-03 10:17:56',0),(5,18,16,'IO流基础','3b71d85d93554e7dbb59becdf823f63d','视频.mp4',0,0,1,0,0,1,0,'2021-11-05 03:06:04','2021-12-03 10:17:56',0),(6,2,2,'第一课时','3b71d85d93554e7dbb59becdf823f63d','eae2b847ef8503b81f5d5593d769dde2.mp4',0,0,0,0,0,1,0,'2021-11-05 03:06:04','2021-12-03 10:17:58',0),(7,2,2,'第二课时','3b71d85d93554e7dbb59becdf823f63d','eae2b847ef8503b81f5d5593d769dde2.mp4',0,0,0,0,0,1,0,'2021-11-05 03:06:04','2021-12-03 10:17:57',0),(8,2,2,'第三课时','3b71d85d93554e7dbb59becdf823f63d','eae2b847ef8503b81f5d5593d769dde2.mp4',0,0,0,0,0,1,0,'2021-11-05 03:06:04','2021-12-03 10:18:20',0),(9,2,2,'第四课时','3b71d85d93554e7dbb59becdf823f63d','eae2b847ef8503b81f5d5593d769dde2.mp4',0,0,0,0,0,1,0,'2021-11-05 03:06:04','2021-12-03 10:18:20',0),(10,2,2,'第五课时','3b71d85d93554e7dbb59becdf823f63d','eae2b847ef8503b81f5d5593d769dde2.mp4',5,0,0,0,0,1,0,'2021-11-05 03:06:04','2021-12-03 10:18:19',0),(11,3,3,'01-尚硅谷-Flume（课程介绍）','3b71d85d93554e7dbb59becdf823f63d','01-尚硅谷-Flume（课程介绍）.avi',0,0,1,0,0,1,0,'2021-11-05 03:06:04','2021-12-03 10:18:21',0),(12,3,3,'02-尚硅谷-Flume（概念）','3b71d85d93554e7dbb59becdf823f63d','02-尚硅谷-Flume（概念）.avi',1,0,0,0,0,1,0,'2021-11-05 03:06:04','2021-12-03 10:18:21',0),(13,3,3,'03-尚硅谷-Flume（概念）','3b71d85d93554e7dbb59becdf823f63d','03-尚硅谷-Flume（架构）.avi',2,0,0,0,0,1,0,'2021-11-05 03:06:04','2021-12-03 10:18:22',0),(14,4,4,'01_尚硅谷_HBase_课程介绍','3b71d85d93554e7dbb59becdf823f63d','',0,0,1,0,0,1,0,'2021-11-05 03:06:04','2021-12-03 10:18:23',0),(15,4,4,'02_尚硅谷_HBase_介绍','3b71d85d93554e7dbb59becdf823f63d','',0,0,0,0,0,1,0,'2021-11-05 03:06:04','2021-12-03 10:18:23',0),(16,4,4,'03_尚硅谷_HBase_特点','3b71d85d93554e7dbb59becdf823f63d','',0,0,0,0,0,1,0,'2021-11-05 03:06:04','2021-12-03 10:18:24',0),(17,5,7,'01_尚硅谷_Kafka_课程介绍','3b71d85d93554e7dbb59becdf823f63d','',0,0,1,0,0,1,0,'2021-11-05 03:06:04','2021-12-03 10:18:16',0),(18,5,7,'02_尚硅谷_Kafka_消息队列介绍','3b71d85d93554e7dbb59becdf823f63d','',0,0,0,0,0,1,0,'2021-11-05 03:06:04','2021-12-03 10:18:15',0),(19,5,7,'03_尚硅谷_Kafka_概念','3b71d85d93554e7dbb59becdf823f63d','',0,0,0,0,0,1,0,'2021-11-05 03:06:04','2021-12-03 10:18:14',0),(20,14,6,'CSS基础','3b71d85d93554e7dbb59becdf823f63d','',0,0,0,0,0,1,0,'2021-11-05 03:06:04','2021-12-03 10:18:14',0),(21,15,8,'spring mvc基础','3b71d85d93554e7dbb59becdf823f63d','',0,0,1,0,0,1,0,'2021-11-05 03:06:04','2021-12-03 10:18:13',0),(22,15,8,'spring mvc集成','3b71d85d93554e7dbb59becdf823f63d','',0,0,0,0,0,1,0,'2021-11-05 03:06:04','2021-12-03 10:18:13',0),(23,6,9,'01_数仓项目_课程介绍','3b71d85d93554e7dbb59becdf823f63d','',0,0,0,0,0,1,0,'2021-11-05 03:06:04','2021-12-03 10:18:12',0),(24,6,9,'02_数仓项目_采集课程介绍','3b71d85d93554e7dbb59becdf823f63d','',0,0,1,0,0,1,0,'2021-11-05 03:06:04','2021-12-03 10:18:12',0),(25,6,9,'03_数仓项目_数仓概念_业务数据','3b71d85d93554e7dbb59becdf823f63d','',0,0,0,0,0,1,0,'2021-11-05 03:06:04','2021-12-03 10:18:11',0),(26,7,10,'01_尚硅谷_Sqoop_课程介绍','3b71d85d93554e7dbb59becdf823f63d','',0,0,0,0,0,1,0,'2021-11-05 03:06:04','2021-12-03 10:18:10',0),(27,7,10,'02_尚硅谷_Sqoop_安装','3b71d85d93554e7dbb59becdf823f63d','',0,0,1,0,0,1,0,'2021-11-05 03:06:04','2021-12-03 10:18:10',0),(28,7,10,'03_尚硅谷_Sqoop_原理','3b71d85d93554e7dbb59becdf823f63d','',0,0,1,0,0,1,0,'2021-11-05 03:06:04','2021-12-03 10:18:09',0),(29,8,11,'01 - Scala - 语言介绍','3b71d85d93554e7dbb59becdf823f63d','',0,0,1,0,0,1,0,'2021-11-05 03:06:04','2021-12-03 10:18:09',0),(30,8,11,'02 - Scala - map集合','3b71d85d93554e7dbb59becdf823f63d','',0,0,0,0,0,1,0,'2021-11-05 03:06:04','2021-12-03 10:18:08',0),(31,1,12,'01_Spark之课程体系介绍','3b71d85d93554e7dbb59becdf823f63d','',0,0,1,0,0,1,0,'2021-11-05 03:06:04','2021-12-03 10:18:08',0),(32,1,12,'02_Spark之课程原理','3b71d85d93554e7dbb59becdf823f63d','',0,0,0,0,0,1,0,'2021-11-05 03:06:04','2021-12-03 10:18:07',0),(33,18,15,'第一节：Java简介','3b71d85d93554e7dbb59becdf823f63d','1',1,1000,1,100,0,1,0,'2021-11-05 03:06:04','2021-12-01 05:45:14',0),(34,18,15,'第二节：表达式和赋值语句','3b71d85d93554e7dbb59becdf823f63d','7 - How Do I Find Time for My ',2,999,1,100,0,1,0,'2021-11-05 03:06:04','2021-12-03 10:18:06',0),(35,18,15,'第三节：String类','3b71d85d93554e7dbb59becdf823f63d','eae2b847ef8503b81f5d5593d769dde2.mp4',3,888,0,100,0,1,0,'2021-11-05 03:06:04','2021-12-01 05:45:16',0),(36,18,15,'第四节：程序风格','3b71d85d93554e7dbb59becdf823f63d','00-day10总结.avi',4,666,0,100,0,1,0,'2021-11-05 03:06:04','2021-12-01 05:45:17',0),(37,19,65,'mysql安装','3b71d85d93554e7dbb59becdf823f63d',NULL,0,0,1,0,0,1,0,'2021-11-22 11:12:11','2021-12-03 10:18:02',0),(38,19,65,'mysql配置','3b71d85d93554e7dbb59becdf823f63d','3.msyql配置.avi',1,0,1,0,0,1,0,'2021-11-22 11:15:52','2021-12-03 10:18:03',0),(39,19,66,'mysql内部结构','3b71d85d93554e7dbb59becdf823f63d','4.mysql的内部结构.avi',0,0,1,0,0,1,0,'2021-11-22 11:17:34','2021-12-03 10:18:04',0),(40,19,66,'认识索引','3b71d85d93554e7dbb59becdf823f63d','8.认识索引.avi',1,0,0,0,0,1,0,'2021-11-22 11:18:45','2021-12-03 10:18:05',0),(41,19,66,'导入测试数据','a4b66c13cfaf4071bdb7ce988e7d7444','13.导入50w条记录.avi',0,0,0,201.2,0,1,0,'2021-12-16 16:15:59','2021-12-16 16:15:59',0);

/*Table structure for table `video_visitor` */

DROP TABLE IF EXISTS `video_visitor`;

CREATE TABLE `video_visitor` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `course_id` bigint(20) DEFAULT NULL COMMENT '课程id',
  `video_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '视频id',
  `user_id` varchar(50) NOT NULL DEFAULT '0' COMMENT '来访者用户id',
  `nick_name` varchar(100) DEFAULT NULL COMMENT '昵称',
  `join_time` varchar(30) DEFAULT NULL COMMENT '进入时间',
  `leave_time` varchar(30) DEFAULT NULL COMMENT '离开的时间',
  `duration` bigint(20) DEFAULT NULL COMMENT '用户停留的时间(单位：秒)',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COMMENT='视频来访者记录表';

/*Data for the table `video_visitor` */

insert  into `video_visitor`(`id`,`course_id`,`video_id`,`user_id`,`nick_name`,`join_time`,`leave_time`,`duration`,`create_time`,`update_time`,`is_deleted`) values (1,19,37,'1','晴天','2021-11-22 21:39:27.089',NULL,4,'2021-11-22 13:39:27','2021-11-22 13:39:27',0),(2,19,38,'1','晴天','2021-11-20 21:39:32.071','2021-11-22 21:44:24.026',696,'2021-11-22 13:39:32','2021-11-25 02:12:41',0),(3,7,26,'1','晴天','2021-11-23 18:09:46.899','2021-11-23 18:10:13.004',5,'2021-11-23 10:09:47','2021-11-23 10:09:47',0),(4,19,38,'24','简','2021-11-23 18:14:53.542',NULL,2,'2021-11-23 10:14:53','2021-11-23 10:14:53',0),(5,19,37,'27','******','2021-11-23 18:54:29.848','2021-11-23 18:54:34.864',3,'2021-11-23 10:54:29','2021-11-23 10:54:29',0),(6,19,37,'1','晴天','2021-11-21 18:55:12.082','2021-11-23 18:56:16.536',33,'2021-11-23 10:55:12','2021-11-25 02:02:25',0),(7,19,38,'1','晴天','2021-11-22 18:55:13.395','2021-11-23 18:56:45.709',706,'2021-11-23 10:55:13','2021-11-25 02:02:31',0),(8,19,37,'1','晴天','2021-11-23 18:56:13.368','2021-11-23 18:58:18.665',13,'2021-11-23 10:56:13','2021-11-23 10:56:13',0),(9,19,39,'1','晴天','2021-11-23 18:56:21.513','2021-11-23 18:56:43.032',313,'2021-11-23 10:56:21','2021-11-25 02:02:12',0),(10,19,40,'1','晴天','2021-11-21 18:56:32.061','2021-11-23 18:56:37.113',202,'2021-11-23 10:56:32','2021-11-25 02:02:55',0),(11,19,40,'1','晴天','2021-11-23 18:58:23.67','2021-11-23 19:38:19.261',114,'2021-11-23 10:58:23','2021-11-23 10:58:23',0),(12,19,40,'1','晴天','2021-11-24 10:11:44.531','2021-11-24 10:11:44.531',115,'2021-11-24 02:11:58','2021-11-25 02:03:06',0),(13,19,38,'1','晴天','2021-11-26 09:11:55.905','2021-11-26 21:05:35.152',943,'2021-11-26 01:12:12','2021-11-26 01:37:30',0),(14,4,15,'1','晴天','2021-11-26 17:07:25.349','2021-11-26 17:07:45.149',304,'2021-11-26 09:07:25','2021-11-26 09:07:25',0),(15,8,30,'1','晴天','2021-11-26 17:14:35.189',NULL,2,'2021-11-26 09:14:35','2021-11-26 09:14:35',0),(16,19,37,'29',NULL,'2021-11-26 18:38:39.719',NULL,3,'2021-11-26 10:38:39','2021-11-26 10:38:39',0),(17,19,38,'1','晴天','2021-11-27 08:02:41.382',NULL,944,'2021-11-27 00:02:41','2021-11-27 00:02:41',0),(18,18,33,'1','晴天','2021-12-01 13:49:37.599',NULL,11,'2021-12-01 05:49:36','2021-12-01 05:49:36',0),(19,18,33,'1','晴天','2021-12-01 13:49:32.6',NULL,6,'2021-12-01 05:49:36','2021-12-01 05:49:36',0),(20,18,33,'1','晴天','2021-12-01 13:49:27.665',NULL,1,'2021-12-01 05:49:37','2021-12-01 05:49:37',0),(21,18,34,'1','晴天','2021-12-01 13:52:02.601','2021-12-01 13:52:14.295',13,'2021-12-01 05:52:02','2021-12-01 05:52:02',0),(22,19,38,'1','晴天','2021-12-16 09:26:31.258',NULL,1,'2021-12-16 09:26:31','2021-12-16 09:26:31',0),(23,19,37,'27','我是','2021-12-28 11:42:31.06',NULL,95,'2021-12-28 11:42:31','2021-12-28 11:42:31',0);

/*Table structure for table `video_visitor_max_view` */

DROP TABLE IF EXISTS `video_visitor_max_view`;

/*!50001 DROP VIEW IF EXISTS `video_visitor_max_view` */;
/*!50001 DROP TABLE IF EXISTS `video_visitor_max_view` */;

/*!50001 CREATE TABLE  `video_visitor_max_view`(
 `max_id` bigint(20) unsigned ,
 `course_id` bigint(20) ,
 `video_id` bigint(20) ,
 `user_id` varchar(50) 
)*/;

/*View structure for view video_visitor_max_view */

/*!50001 DROP TABLE IF EXISTS `video_visitor_max_view` */;
/*!50001 DROP VIEW IF EXISTS `video_visitor_max_view` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`shangguigu`@`%` SQL SECURITY DEFINER VIEW `video_visitor_max_view` AS select max(`video_visitor`.`id`) AS `max_id`,`video_visitor`.`course_id` AS `course_id`,`video_visitor`.`video_id` AS `video_id`,`video_visitor`.`user_id` AS `user_id` from `video_visitor` group by `video_visitor`.`course_id`,`video_visitor`.`video_id`,`video_visitor`.`user_id` */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
