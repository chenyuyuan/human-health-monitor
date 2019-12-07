/*
 Navicat Premium Data Transfer

 Source Server         : hhm
 Source Server Type    : MySQL
 Source Server Version : 50713
 Source Host           : 140.143.232.52:3306
 Source Schema         : health_monitor

 Target Server Type    : MySQL
 Target Server Version : 50713
 File Encoding         : 65001

 Date: 07/12/2019 16:52:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `adminId` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `pwd` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `adminGroup` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `loginState` bit(1) NOT NULL,
  PRIMARY KEY (`adminId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('admin', '12345abc', 'root', b'1');

-- ----------------------------
-- Table structure for adminLog
-- ----------------------------
DROP TABLE IF EXISTS `adminLog`;
CREATE TABLE `adminLog`  (
  `adminLogId` int(11) NOT NULL AUTO_INCREMENT,
  `adminId` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `logType` varchar(12) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `writeTime` datetime(0) NOT NULL,
  `detail` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`adminLogId`) USING BTREE,
  INDEX `adminLog_admin__fk`(`adminId`) USING BTREE,
  CONSTRAINT `adminLog_admin__fk` FOREIGN KEY (`adminId`) REFERENCES `admin` (`adminId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 896 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of adminLog
-- ----------------------------
INSERT INTO `adminLog` VALUES (631, 'admin', 'login', '2019-04-21 21:09:42', NULL);
INSERT INTO `adminLog` VALUES (632, 'admin', 'logout', '2019-04-21 21:10:25', NULL);
INSERT INTO `adminLog` VALUES (633, 'admin', 'login', '2019-04-21 22:57:00', NULL);
INSERT INTO `adminLog` VALUES (634, 'admin', 'logout', '2019-04-21 22:57:43', NULL);
INSERT INTO `adminLog` VALUES (635, 'admin', 'login', '2019-04-22 10:40:35', NULL);
INSERT INTO `adminLog` VALUES (636, 'admin', 'logout', '2019-04-22 10:41:24', NULL);
INSERT INTO `adminLog` VALUES (637, 'admin', 'login', '2019-04-22 10:44:52', NULL);
INSERT INTO `adminLog` VALUES (638, 'admin', 'logout', '2019-04-22 10:45:14', NULL);
INSERT INTO `adminLog` VALUES (639, 'admin', 'login', '2019-04-22 10:50:30', NULL);
INSERT INTO `adminLog` VALUES (640, 'admin', 'logout', '2019-04-22 10:50:43', NULL);
INSERT INTO `adminLog` VALUES (641, 'admin', 'login', '2019-04-22 10:51:31', NULL);
INSERT INTO `adminLog` VALUES (642, 'admin', 'logout', '2019-04-22 10:52:02', NULL);
INSERT INTO `adminLog` VALUES (643, 'admin', 'login', '2019-04-22 13:39:16', NULL);
INSERT INTO `adminLog` VALUES (644, 'admin', 'logout', '2019-04-22 13:39:48', NULL);
INSERT INTO `adminLog` VALUES (645, 'admin', 'login', '2019-04-22 13:43:00', NULL);
INSERT INTO `adminLog` VALUES (646, 'admin', 'logout', '2019-04-22 13:43:28', NULL);
INSERT INTO `adminLog` VALUES (647, 'admin', 'login', '2019-04-22 14:01:40', NULL);
INSERT INTO `adminLog` VALUES (648, 'admin', 'logout', '2019-04-22 14:01:58', NULL);
INSERT INTO `adminLog` VALUES (649, 'admin', 'login', '2019-04-22 14:02:53', NULL);
INSERT INTO `adminLog` VALUES (650, 'admin', 'logout', '2019-04-22 14:03:47', NULL);
INSERT INTO `adminLog` VALUES (651, 'admin', 'login', '2019-04-22 14:22:22', NULL);
INSERT INTO `adminLog` VALUES (652, 'admin', 'logout', '2019-04-22 14:23:30', NULL);
INSERT INTO `adminLog` VALUES (653, 'admin', 'login', '2019-04-22 14:27:05', NULL);
INSERT INTO `adminLog` VALUES (654, 'admin', 'logout', '2019-04-22 14:27:56', NULL);
INSERT INTO `adminLog` VALUES (655, 'admin', 'login', '2019-04-22 14:38:34', NULL);
INSERT INTO `adminLog` VALUES (656, 'admin', 'logout', '2019-04-22 14:38:45', NULL);
INSERT INTO `adminLog` VALUES (657, 'admin', 'login', '2019-04-22 14:41:21', NULL);
INSERT INTO `adminLog` VALUES (658, 'admin', 'logout', '2019-04-22 14:42:14', NULL);
INSERT INTO `adminLog` VALUES (659, 'admin', 'login', '2019-04-22 14:45:50', NULL);
INSERT INTO `adminLog` VALUES (660, 'admin', 'logout', '2019-04-22 14:46:11', NULL);
INSERT INTO `adminLog` VALUES (661, 'admin', 'login', '2019-04-22 14:49:43', NULL);
INSERT INTO `adminLog` VALUES (662, 'admin', 'logout', '2019-04-22 14:49:54', NULL);
INSERT INTO `adminLog` VALUES (663, 'admin', 'login', '2019-04-22 14:53:07', NULL);
INSERT INTO `adminLog` VALUES (664, 'admin', 'logout', '2019-04-22 14:53:28', NULL);
INSERT INTO `adminLog` VALUES (665, 'admin', 'login', '2019-04-22 14:55:52', NULL);
INSERT INTO `adminLog` VALUES (666, 'admin', 'logout', '2019-04-22 14:56:09', NULL);
INSERT INTO `adminLog` VALUES (667, 'admin', 'login', '2019-04-22 15:02:05', NULL);
INSERT INTO `adminLog` VALUES (668, 'admin', 'logout', '2019-04-22 15:02:19', NULL);
INSERT INTO `adminLog` VALUES (669, 'admin', 'login', '2019-04-22 15:03:33', NULL);
INSERT INTO `adminLog` VALUES (670, 'admin', 'logout', '2019-04-22 15:03:42', NULL);
INSERT INTO `adminLog` VALUES (671, 'admin', 'login', '2019-04-22 15:06:12', NULL);
INSERT INTO `adminLog` VALUES (672, 'admin', 'logout', '2019-04-22 15:06:35', NULL);
INSERT INTO `adminLog` VALUES (673, 'admin', 'login', '2019-04-22 15:37:26', NULL);
INSERT INTO `adminLog` VALUES (674, 'admin', 'logout', '2019-04-22 15:37:42', NULL);
INSERT INTO `adminLog` VALUES (675, 'admin', 'login', '2019-04-22 16:03:52', NULL);
INSERT INTO `adminLog` VALUES (676, 'admin', 'logout', '2019-04-22 16:04:06', NULL);
INSERT INTO `adminLog` VALUES (677, 'admin', 'login', '2019-04-22 16:07:00', NULL);
INSERT INTO `adminLog` VALUES (678, 'admin', 'logout', '2019-04-22 16:07:47', NULL);
INSERT INTO `adminLog` VALUES (679, 'admin', 'login', '2019-04-22 16:14:38', NULL);
INSERT INTO `adminLog` VALUES (680, 'admin', 'logout', '2019-04-22 16:15:45', NULL);
INSERT INTO `adminLog` VALUES (681, 'admin', 'login', '2019-04-22 18:34:52', NULL);
INSERT INTO `adminLog` VALUES (682, 'admin', 'logout', '2019-04-22 18:35:30', NULL);
INSERT INTO `adminLog` VALUES (683, 'admin', 'login', '2019-04-22 18:37:27', NULL);
INSERT INTO `adminLog` VALUES (684, 'admin', 'login', '2019-04-22 19:01:29', NULL);
INSERT INTO `adminLog` VALUES (685, 'admin', 'logout', '2019-04-22 19:01:55', NULL);
INSERT INTO `adminLog` VALUES (686, 'admin', 'login', '2019-04-22 19:03:15', NULL);
INSERT INTO `adminLog` VALUES (687, 'admin', 'logout', '2019-04-22 19:03:50', NULL);
INSERT INTO `adminLog` VALUES (688, 'admin', 'login', '2019-04-22 19:07:46', NULL);
INSERT INTO `adminLog` VALUES (689, 'admin', 'logout', '2019-04-22 19:08:24', NULL);
INSERT INTO `adminLog` VALUES (690, 'admin', 'login', '2019-04-22 19:13:21', NULL);
INSERT INTO `adminLog` VALUES (691, 'admin', 'logout', '2019-04-22 19:13:30', NULL);
INSERT INTO `adminLog` VALUES (692, 'admin', 'login', '2019-04-22 19:19:48', NULL);
INSERT INTO `adminLog` VALUES (693, 'admin', 'login', '2019-04-22 19:30:43', NULL);
INSERT INTO `adminLog` VALUES (694, 'admin', 'logout', '2019-04-22 19:31:13', NULL);
INSERT INTO `adminLog` VALUES (695, 'admin', 'login', '2019-04-22 19:34:12', NULL);
INSERT INTO `adminLog` VALUES (696, 'admin', 'login', '2019-04-22 19:36:26', NULL);
INSERT INTO `adminLog` VALUES (697, 'admin', 'logout', '2019-04-22 19:36:43', NULL);
INSERT INTO `adminLog` VALUES (698, 'admin', 'login', '2019-04-22 19:39:41', NULL);
INSERT INTO `adminLog` VALUES (699, 'admin', 'login', '2019-04-22 19:41:29', NULL);
INSERT INTO `adminLog` VALUES (700, 'admin', 'logout', '2019-04-22 19:52:11', NULL);
INSERT INTO `adminLog` VALUES (701, 'admin', 'login', '2019-04-22 20:00:09', NULL);
INSERT INTO `adminLog` VALUES (702, 'admin', 'logout', '2019-04-22 20:00:43', NULL);
INSERT INTO `adminLog` VALUES (703, 'admin', 'login', '2019-04-22 20:02:55', NULL);
INSERT INTO `adminLog` VALUES (704, 'admin', 'login', '2019-04-22 20:05:18', NULL);
INSERT INTO `adminLog` VALUES (705, 'admin', 'login', '2019-04-22 20:16:57', NULL);
INSERT INTO `adminLog` VALUES (706, 'admin', 'logout', '2019-04-22 20:17:37', NULL);
INSERT INTO `adminLog` VALUES (707, 'admin', 'login', '2019-04-22 20:19:23', NULL);
INSERT INTO `adminLog` VALUES (708, 'admin', 'login', '2019-04-22 20:40:36', NULL);
INSERT INTO `adminLog` VALUES (709, 'admin', 'login', '2019-04-22 20:44:04', NULL);
INSERT INTO `adminLog` VALUES (710, 'admin', 'login', '2019-04-22 21:03:56', NULL);
INSERT INTO `adminLog` VALUES (711, 'admin', 'login', '2019-04-22 21:05:15', NULL);
INSERT INTO `adminLog` VALUES (712, 'admin', 'logout', '2019-04-22 21:06:12', NULL);
INSERT INTO `adminLog` VALUES (713, 'admin', 'login', '2019-04-22 21:12:45', NULL);
INSERT INTO `adminLog` VALUES (714, 'admin', 'logout', '2019-04-22 21:23:44', NULL);
INSERT INTO `adminLog` VALUES (715, 'admin', 'login', '2019-04-22 21:27:25', NULL);
INSERT INTO `adminLog` VALUES (716, 'admin', 'logout', '2019-04-22 21:40:26', NULL);
INSERT INTO `adminLog` VALUES (717, 'admin', 'login', '2019-04-23 16:29:59', NULL);
INSERT INTO `adminLog` VALUES (718, 'admin', 'logout', '2019-04-23 16:30:34', NULL);
INSERT INTO `adminLog` VALUES (719, 'admin', 'login', '2019-04-23 16:33:24', NULL);
INSERT INTO `adminLog` VALUES (720, 'admin', 'logout', '2019-04-23 16:34:17', NULL);
INSERT INTO `adminLog` VALUES (721, 'admin', 'login', '2019-04-23 16:39:05', NULL);
INSERT INTO `adminLog` VALUES (722, 'admin', 'logout', '2019-04-23 16:47:07', NULL);
INSERT INTO `adminLog` VALUES (723, 'admin', 'login', '2019-04-23 17:12:25', NULL);
INSERT INTO `adminLog` VALUES (724, 'admin', 'login', '2019-04-23 17:15:48', NULL);
INSERT INTO `adminLog` VALUES (725, 'admin', 'login', '2019-04-23 17:19:11', NULL);
INSERT INTO `adminLog` VALUES (726, 'admin', 'logout', '2019-04-23 17:20:08', NULL);
INSERT INTO `adminLog` VALUES (727, 'admin', 'login', '2019-04-23 17:21:30', NULL);
INSERT INTO `adminLog` VALUES (728, 'admin', 'login', '2019-04-23 17:23:21', NULL);
INSERT INTO `adminLog` VALUES (729, 'admin', 'login', '2019-04-23 17:33:05', NULL);
INSERT INTO `adminLog` VALUES (730, 'admin', 'login', '2019-04-23 17:34:12', NULL);
INSERT INTO `adminLog` VALUES (731, 'admin', 'login', '2019-04-23 17:38:05', NULL);
INSERT INTO `adminLog` VALUES (732, 'admin', 'logout', '2019-04-23 17:38:30', NULL);
INSERT INTO `adminLog` VALUES (733, 'admin', 'login', '2019-04-23 17:40:31', NULL);
INSERT INTO `adminLog` VALUES (734, 'admin', 'login', '2019-04-23 17:45:31', NULL);
INSERT INTO `adminLog` VALUES (735, 'admin', 'logout', '2019-04-23 17:47:57', NULL);
INSERT INTO `adminLog` VALUES (736, 'admin', 'login', '2019-04-23 18:33:15', NULL);
INSERT INTO `adminLog` VALUES (737, 'admin', 'logout', '2019-04-23 18:43:02', NULL);
INSERT INTO `adminLog` VALUES (738, 'admin', 'login', '2019-04-23 20:10:35', NULL);
INSERT INTO `adminLog` VALUES (739, 'admin', 'login', '2019-04-23 21:15:23', NULL);
INSERT INTO `adminLog` VALUES (740, 'admin', 'login', '2019-04-23 21:17:04', NULL);
INSERT INTO `adminLog` VALUES (741, 'admin', 'login', '2019-04-23 21:17:04', NULL);
INSERT INTO `adminLog` VALUES (742, 'admin', 'login', '2019-04-23 21:18:26', NULL);
INSERT INTO `adminLog` VALUES (743, 'admin', 'logout', '2019-04-23 21:18:28', NULL);
INSERT INTO `adminLog` VALUES (744, 'admin', 'login', '2019-04-23 21:23:53', NULL);
INSERT INTO `adminLog` VALUES (745, 'admin', 'logout', '2019-04-23 21:24:16', NULL);
INSERT INTO `adminLog` VALUES (746, 'admin', 'login', '2019-04-24 16:46:31', NULL);
INSERT INTO `adminLog` VALUES (747, 'admin', 'logout', '2019-04-24 16:47:45', NULL);
INSERT INTO `adminLog` VALUES (748, 'admin', 'login', '2019-04-24 16:52:56', NULL);
INSERT INTO `adminLog` VALUES (749, 'admin', 'logout', '2019-04-24 16:52:59', NULL);
INSERT INTO `adminLog` VALUES (750, 'admin', 'login', '2019-04-24 16:58:23', NULL);
INSERT INTO `adminLog` VALUES (751, 'admin', 'logout', '2019-04-24 16:58:48', NULL);
INSERT INTO `adminLog` VALUES (752, 'admin', 'login', '2019-04-24 17:01:46', NULL);
INSERT INTO `adminLog` VALUES (753, 'admin', 'logout', '2019-04-24 17:01:48', NULL);
INSERT INTO `adminLog` VALUES (754, 'admin', 'login', '2019-04-24 21:12:00', NULL);
INSERT INTO `adminLog` VALUES (755, 'admin', 'logout', '2019-04-24 21:12:44', NULL);
INSERT INTO `adminLog` VALUES (756, 'admin', 'login', '2019-04-24 21:20:54', NULL);
INSERT INTO `adminLog` VALUES (757, 'admin', 'logout', '2019-04-24 21:20:56', NULL);
INSERT INTO `adminLog` VALUES (758, 'admin', 'login', '2019-04-25 14:39:17', NULL);
INSERT INTO `adminLog` VALUES (759, 'admin', 'login', '2019-04-25 14:51:06', NULL);
INSERT INTO `adminLog` VALUES (760, 'admin', 'logout', '2019-04-25 14:51:09', NULL);
INSERT INTO `adminLog` VALUES (761, 'admin', 'login', '2019-04-25 15:33:50', NULL);
INSERT INTO `adminLog` VALUES (762, 'admin', 'login', '2019-04-25 16:03:40', NULL);
INSERT INTO `adminLog` VALUES (763, 'admin', 'logout', '2019-04-25 16:04:33', NULL);
INSERT INTO `adminLog` VALUES (764, 'admin', 'login', '2019-04-25 16:06:04', NULL);
INSERT INTO `adminLog` VALUES (765, 'admin', 'logout', '2019-04-25 16:06:26', NULL);
INSERT INTO `adminLog` VALUES (766, 'admin', 'login', '2019-04-25 16:09:45', NULL);
INSERT INTO `adminLog` VALUES (767, 'admin', 'logout', '2019-04-25 16:10:05', NULL);
INSERT INTO `adminLog` VALUES (768, 'admin', 'login', '2019-04-25 16:10:48', NULL);
INSERT INTO `adminLog` VALUES (769, 'admin', 'logout', '2019-04-25 16:11:04', NULL);
INSERT INTO `adminLog` VALUES (770, 'admin', 'login', '2019-04-25 16:12:59', NULL);
INSERT INTO `adminLog` VALUES (771, 'admin', 'login', '2019-04-25 16:12:59', NULL);
INSERT INTO `adminLog` VALUES (772, 'admin', 'logout', '2019-04-25 16:13:13', NULL);
INSERT INTO `adminLog` VALUES (773, 'admin', 'login', '2019-04-25 16:15:47', NULL);
INSERT INTO `adminLog` VALUES (774, 'admin', 'logout', '2019-04-25 16:16:01', NULL);
INSERT INTO `adminLog` VALUES (775, 'admin', 'login', '2019-04-25 16:28:00', NULL);
INSERT INTO `adminLog` VALUES (776, 'admin', 'logout', '2019-04-25 16:28:15', NULL);
INSERT INTO `adminLog` VALUES (777, 'admin', 'login', '2019-04-25 16:30:45', NULL);
INSERT INTO `adminLog` VALUES (778, 'admin', 'logout', '2019-04-25 16:30:55', NULL);
INSERT INTO `adminLog` VALUES (779, 'admin', 'login', '2019-04-25 16:33:16', NULL);
INSERT INTO `adminLog` VALUES (780, 'admin', 'logout', '2019-04-25 16:33:31', NULL);
INSERT INTO `adminLog` VALUES (781, 'admin', 'login', '2019-04-25 16:34:59', NULL);
INSERT INTO `adminLog` VALUES (782, 'admin', 'logout', '2019-04-25 16:35:26', NULL);
INSERT INTO `adminLog` VALUES (783, 'admin', 'login', '2019-04-25 16:36:51', NULL);
INSERT INTO `adminLog` VALUES (784, 'admin', 'logout', '2019-04-25 16:37:04', NULL);
INSERT INTO `adminLog` VALUES (785, 'admin', 'login', '2019-04-25 19:02:13', NULL);
INSERT INTO `adminLog` VALUES (786, 'admin', 'login', '2019-04-25 19:05:13', NULL);
INSERT INTO `adminLog` VALUES (787, 'admin', 'logout', '2019-04-25 19:05:15', NULL);
INSERT INTO `adminLog` VALUES (788, 'admin', 'login', '2019-04-26 19:41:18', NULL);
INSERT INTO `adminLog` VALUES (789, 'admin', 'login', '2019-04-26 19:43:53', NULL);
INSERT INTO `adminLog` VALUES (790, 'admin', 'logout', '2019-04-26 19:44:56', NULL);
INSERT INTO `adminLog` VALUES (791, 'admin', 'login', '2019-04-26 20:53:36', NULL);
INSERT INTO `adminLog` VALUES (792, 'admin', 'logout', '2019-04-26 20:54:41', NULL);
INSERT INTO `adminLog` VALUES (793, 'admin', 'login', '2019-04-26 21:43:52', NULL);
INSERT INTO `adminLog` VALUES (794, 'admin', 'login', '2019-04-26 21:48:33', NULL);
INSERT INTO `adminLog` VALUES (795, 'admin', 'logout', '2019-04-26 21:53:01', NULL);
INSERT INTO `adminLog` VALUES (796, 'admin', 'logout', '2019-04-26 21:53:52', NULL);
INSERT INTO `adminLog` VALUES (797, 'admin', 'login', '2019-04-27 07:54:14', NULL);
INSERT INTO `adminLog` VALUES (798, 'admin', 'login', '2019-04-27 08:39:57', NULL);
INSERT INTO `adminLog` VALUES (799, 'admin', 'logout', '2019-04-27 08:41:32', NULL);
INSERT INTO `adminLog` VALUES (800, 'admin', 'logout', '2019-04-27 08:41:43', NULL);
INSERT INTO `adminLog` VALUES (801, 'admin', 'login', '2019-04-27 09:18:10', NULL);
INSERT INTO `adminLog` VALUES (802, 'admin', 'logout', '2019-04-27 09:20:57', NULL);
INSERT INTO `adminLog` VALUES (803, 'admin', 'login', '2019-04-27 09:45:12', NULL);
INSERT INTO `adminLog` VALUES (804, 'admin', 'logout', '2019-04-27 09:47:44', NULL);
INSERT INTO `adminLog` VALUES (805, 'admin', 'login', '2019-04-27 09:56:39', NULL);
INSERT INTO `adminLog` VALUES (806, 'admin', 'logout', '2019-04-27 10:01:44', NULL);
INSERT INTO `adminLog` VALUES (807, 'admin', 'logout', '2019-04-27 10:01:47', NULL);
INSERT INTO `adminLog` VALUES (808, 'admin', 'login', '2019-05-27 19:21:29', NULL);
INSERT INTO `adminLog` VALUES (809, 'admin', 'logout', '2019-05-27 19:27:01', NULL);
INSERT INTO `adminLog` VALUES (810, 'admin', 'login', '2019-05-30 03:35:49', NULL);
INSERT INTO `adminLog` VALUES (811, 'admin', 'login', '2019-05-30 03:35:49', NULL);
INSERT INTO `adminLog` VALUES (812, 'admin', 'logout', '2019-05-30 03:38:53', NULL);
INSERT INTO `adminLog` VALUES (813, 'admin', 'logout', '2019-05-30 03:44:11', NULL);
INSERT INTO `adminLog` VALUES (814, 'admin', 'login', '2019-05-30 16:20:26', NULL);
INSERT INTO `adminLog` VALUES (815, 'admin', 'logout', '2019-05-30 16:26:52', NULL);
INSERT INTO `adminLog` VALUES (816, 'admin', 'login', '2019-05-30 20:07:25', NULL);
INSERT INTO `adminLog` VALUES (817, 'admin', 'logout', '2019-05-30 20:11:39', NULL);
INSERT INTO `adminLog` VALUES (818, 'admin', 'login', '2019-05-30 20:20:56', NULL);
INSERT INTO `adminLog` VALUES (819, 'admin', 'logout', '2019-05-30 20:21:46', NULL);
INSERT INTO `adminLog` VALUES (820, 'admin', 'logout', '2019-05-30 20:22:01', NULL);
INSERT INTO `adminLog` VALUES (821, 'admin', 'logout', '2019-05-30 20:23:06', NULL);
INSERT INTO `adminLog` VALUES (822, 'admin', 'login', '2019-05-30 20:41:33', NULL);
INSERT INTO `adminLog` VALUES (823, 'admin', 'logout', '2019-05-30 20:42:13', NULL);
INSERT INTO `adminLog` VALUES (824, 'admin', 'login', '2019-05-30 21:22:28', NULL);
INSERT INTO `adminLog` VALUES (825, 'admin', 'logout', '2019-05-30 21:22:35', NULL);
INSERT INTO `adminLog` VALUES (826, 'admin', 'login', '2019-05-30 21:22:58', NULL);
INSERT INTO `adminLog` VALUES (827, 'admin', 'logout', '2019-05-30 21:26:49', NULL);
INSERT INTO `adminLog` VALUES (828, 'admin', 'login', '2019-05-31 02:39:49', NULL);
INSERT INTO `adminLog` VALUES (829, 'admin', 'login', '2019-05-31 02:58:16', NULL);
INSERT INTO `adminLog` VALUES (830, 'admin', 'logout', '2019-05-31 02:59:11', NULL);
INSERT INTO `adminLog` VALUES (831, 'admin', 'login', '2019-05-31 03:05:18', NULL);
INSERT INTO `adminLog` VALUES (832, 'admin', 'logout', '2019-05-31 03:06:36', NULL);
INSERT INTO `adminLog` VALUES (833, 'admin', 'login', '2019-05-31 06:04:34', NULL);
INSERT INTO `adminLog` VALUES (834, 'admin', 'logout', '2019-05-31 06:06:12', NULL);
INSERT INTO `adminLog` VALUES (835, 'admin', 'login', '2019-05-31 07:03:30', NULL);
INSERT INTO `adminLog` VALUES (836, 'admin', 'logout', '2019-05-31 07:03:40', NULL);
INSERT INTO `adminLog` VALUES (837, 'admin', 'login', '2019-05-31 08:38:16', NULL);
INSERT INTO `adminLog` VALUES (838, 'admin', 'logout', '2019-05-31 08:41:29', NULL);
INSERT INTO `adminLog` VALUES (839, 'admin', 'login', '2019-06-05 14:35:46', NULL);
INSERT INTO `adminLog` VALUES (840, 'admin', 'logout', '2019-06-05 14:52:46', NULL);
INSERT INTO `adminLog` VALUES (841, 'admin', 'login', '2019-06-06 00:39:40', NULL);
INSERT INTO `adminLog` VALUES (842, 'admin', 'login', '2019-06-06 20:34:13', NULL);
INSERT INTO `adminLog` VALUES (843, 'admin', 'login', '2019-06-06 20:59:10', NULL);
INSERT INTO `adminLog` VALUES (844, 'admin', 'login', '2019-06-06 21:38:47', NULL);
INSERT INTO `adminLog` VALUES (845, 'admin', 'login', '2019-06-10 00:58:05', NULL);
INSERT INTO `adminLog` VALUES (846, 'admin', 'login', '2019-06-10 02:52:53', NULL);
INSERT INTO `adminLog` VALUES (847, 'admin', 'login', '2019-06-10 04:43:18', NULL);
INSERT INTO `adminLog` VALUES (848, 'admin', 'login', '2019-06-10 06:49:16', NULL);
INSERT INTO `adminLog` VALUES (849, 'admin', 'login', '2019-06-10 07:55:04', NULL);
INSERT INTO `adminLog` VALUES (850, 'admin', 'login', '2019-06-10 09:01:16', NULL);
INSERT INTO `adminLog` VALUES (851, 'admin', 'login', '2019-06-10 18:00:46', NULL);
INSERT INTO `adminLog` VALUES (852, 'admin', 'logout', '2019-06-10 18:00:51', NULL);
INSERT INTO `adminLog` VALUES (853, 'admin', 'login', '2019-06-11 07:01:48', NULL);
INSERT INTO `adminLog` VALUES (854, 'admin', 'logout', '2019-06-11 07:50:31', NULL);
INSERT INTO `adminLog` VALUES (855, 'admin', 'login', '2019-06-11 07:50:37', NULL);
INSERT INTO `adminLog` VALUES (856, 'admin', 'login', '2019-06-11 07:51:44', NULL);
INSERT INTO `adminLog` VALUES (857, 'admin', 'login', '2019-06-11 16:44:38', NULL);
INSERT INTO `adminLog` VALUES (858, 'admin', 'logout', '2019-06-11 16:50:01', NULL);
INSERT INTO `adminLog` VALUES (859, 'admin', 'login', '2019-06-11 18:42:45', NULL);
INSERT INTO `adminLog` VALUES (860, 'admin', 'logout', '2019-06-11 20:30:58', NULL);
INSERT INTO `adminLog` VALUES (861, 'admin', 'login', '2019-06-12 02:38:42', NULL);
INSERT INTO `adminLog` VALUES (862, 'admin', 'login', '2019-06-12 07:35:13', NULL);
INSERT INTO `adminLog` VALUES (863, 'admin', 'login', '2019-06-14 15:01:36', NULL);
INSERT INTO `adminLog` VALUES (864, 'admin', 'logout', '2019-06-14 15:29:04', NULL);
INSERT INTO `adminLog` VALUES (865, 'admin', 'login', '2019-06-21 15:42:10', NULL);
INSERT INTO `adminLog` VALUES (866, 'admin', 'login', '2019-07-04 19:04:10', NULL);
INSERT INTO `adminLog` VALUES (867, 'admin', 'login', '2019-07-06 10:54:30', NULL);
INSERT INTO `adminLog` VALUES (868, 'admin', 'logout', '2019-07-06 10:58:02', NULL);
INSERT INTO `adminLog` VALUES (869, 'admin', 'logout', '2019-07-06 11:15:08', NULL);
INSERT INTO `adminLog` VALUES (870, 'admin', 'login', '2019-07-06 12:08:56', NULL);
INSERT INTO `adminLog` VALUES (871, 'admin', 'login', '2019-07-06 16:12:59', NULL);
INSERT INTO `adminLog` VALUES (872, 'admin', 'logout', '2019-07-06 16:15:39', NULL);
INSERT INTO `adminLog` VALUES (873, 'admin', 'login', '2019-07-22 10:34:06', NULL);
INSERT INTO `adminLog` VALUES (874, 'admin', 'logout', '2019-07-22 10:34:28', NULL);
INSERT INTO `adminLog` VALUES (875, 'admin', 'login', '2019-07-23 11:05:16', NULL);
INSERT INTO `adminLog` VALUES (876, 'admin', 'logout', '2019-07-23 11:05:24', NULL);
INSERT INTO `adminLog` VALUES (877, 'admin', 'login', '2019-07-24 15:16:40', NULL);
INSERT INTO `adminLog` VALUES (878, 'admin', 'logout', '2019-07-24 15:24:14', NULL);
INSERT INTO `adminLog` VALUES (879, 'admin', 'login', '2019-07-28 15:24:25', NULL);
INSERT INTO `adminLog` VALUES (880, 'admin', 'logout', '2019-07-28 15:24:36', NULL);
INSERT INTO `adminLog` VALUES (881, 'admin', 'login', '2019-07-29 10:38:23', NULL);
INSERT INTO `adminLog` VALUES (882, 'admin', 'login', '2019-07-29 16:58:02', NULL);
INSERT INTO `adminLog` VALUES (883, 'admin', 'login', '2019-07-30 13:23:04', NULL);
INSERT INTO `adminLog` VALUES (884, 'admin', 'logout', '2019-07-30 13:30:58', NULL);
INSERT INTO `adminLog` VALUES (885, 'admin', 'login', '2019-08-22 14:08:29', NULL);
INSERT INTO `adminLog` VALUES (886, 'admin', 'logout', '2019-08-22 14:08:52', NULL);
INSERT INTO `adminLog` VALUES (887, 'admin', 'login', '2019-08-22 14:28:13', NULL);
INSERT INTO `adminLog` VALUES (888, 'admin', 'login', '2019-09-21 23:50:36', NULL);
INSERT INTO `adminLog` VALUES (889, 'admin', 'logout', '2019-09-21 23:55:47', NULL);
INSERT INTO `adminLog` VALUES (890, 'admin', 'logout', '2019-09-21 23:55:43', NULL);
INSERT INTO `adminLog` VALUES (891, 'admin', 'logout', '2019-09-21 23:55:39', NULL);
INSERT INTO `adminLog` VALUES (892, 'admin', 'logout', '2019-09-21 23:55:44', NULL);
INSERT INTO `adminLog` VALUES (893, 'admin', 'logout', '2019-09-21 23:55:46', NULL);
INSERT INTO `adminLog` VALUES (894, 'admin', 'logout', '2019-09-21 23:55:26', NULL);
INSERT INTO `adminLog` VALUES (895, 'admin', 'login', '2019-10-10 18:12:36', NULL);

-- ----------------------------
-- Table structure for alarmLog
-- ----------------------------
DROP TABLE IF EXISTS `alarmLog`;
CREATE TABLE `alarmLog`  (
  `alarmLogId` bigint(20) NOT NULL AUTO_INCREMENT,
  `objectId` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `eqpId` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `alarmType` varchar(12) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `alarmValue` float NOT NULL,
  `writeTime` datetime(0) NOT NULL,
  `detail` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`alarmLogId`) USING BTREE,
  INDEX `alarmLog_object__fk`(`objectId`) USING BTREE,
  INDEX `alarmLog_equipment__fk`(`eqpId`) USING BTREE,
  CONSTRAINT `alarmLog_equipment__fk` FOREIGN KEY (`eqpId`) REFERENCES `equipment` (`eqpId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `alarmLog_object__fk` FOREIGN KEY (`objectId`) REFERENCES `object` (`objectId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 315 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of alarmLog
-- ----------------------------
INSERT INTO `alarmLog` VALUES (1, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 06:55:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (2, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 06:56:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (3, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 06:57:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (4, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 06:58:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (5, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 06:59:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (6, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 14:55:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (7, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 14:56:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (8, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 14:57:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (9, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 14:58:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (10, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 14:59:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (11, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 15:55:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (12, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 15:56:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (13, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 15:57:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (14, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 15:58:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (15, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 15:59:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (16, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 15:59:55', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (17, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 16:55:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (18, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 16:56:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (19, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 16:57:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (20, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 16:58:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (21, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 16:59:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (22, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 16:59:55', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (23, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 16:59:56', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (24, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 17:55:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (25, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 17:56:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (26, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 17:57:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (27, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 17:58:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (28, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 17:59:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (29, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 17:59:55', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (30, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 17:59:56', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (31, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 17:59:57', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (32, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 18:55:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (33, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 18:56:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (34, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 18:57:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (35, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 18:58:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (36, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 18:59:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (37, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 18:59:55', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (38, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 18:59:56', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (39, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 19:55:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (40, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 19:56:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (41, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 19:57:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (42, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 19:58:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (43, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 19:59:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (44, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 19:59:55', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (45, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 20:55:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (46, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 20:56:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (47, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 20:57:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (48, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 20:58:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (49, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 20:59:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (50, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 21:55:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (51, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 21:56:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (52, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 21:57:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (53, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 21:58:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (54, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 21:59:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (55, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 21:59:08', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (56, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 22:55:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (57, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 22:56:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (58, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 22:57:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (59, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 22:58:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (60, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 22:59:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (61, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 22:59:08', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (62, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 22:59:09', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (63, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 23:55:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (64, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 23:56:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (65, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 23:57:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (66, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 23:58:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (67, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 23:59:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (68, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 23:59:08', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (69, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 23:59:09', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (70, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 23:59:10', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (71, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 00:55:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (72, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 00:56:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (73, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 00:57:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (74, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 00:58:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (75, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 00:59:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (76, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 00:59:08', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (77, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 00:59:09', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (78, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 01:55:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (79, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 01:56:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (80, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 01:57:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (81, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 01:58:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (82, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 01:59:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (83, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 01:59:08', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (84, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 02:55:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (85, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 02:56:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (86, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 02:57:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (87, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 02:58:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (88, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 02:59:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (89, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 03:55:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (90, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 03:56:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (91, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 03:57:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (92, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 03:58:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (93, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 03:59:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (94, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 03:59:08', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (95, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 04:55:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (96, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 04:56:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (97, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 04:57:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (98, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 04:58:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (99, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 04:59:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (100, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 04:59:08', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (101, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 04:59:09', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (102, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 05:55:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (103, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 05:56:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (104, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 05:57:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (105, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 05:58:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (106, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 05:59:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (107, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 05:59:08', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (108, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 05:59:09', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (109, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 05:59:10', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (110, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 06:55:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (111, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 06:56:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (112, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 06:57:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (113, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 06:58:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (114, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 06:59:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (115, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 06:59:08', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (116, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 06:59:09', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (117, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 07:55:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (118, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 07:56:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (119, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 07:57:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (120, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 07:58:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (121, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 07:59:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (122, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 07:59:08', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (123, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 08:55:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (124, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 08:56:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (125, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 08:57:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (126, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 08:58:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (127, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 08:59:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (128, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 09:55:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (129, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 09:56:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (130, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 09:57:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (131, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 09:58:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (132, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 09:59:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (133, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 09:59:08', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (134, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 10:55:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (135, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 10:56:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (136, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 10:57:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (137, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 10:58:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (138, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 10:59:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (139, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 10:59:08', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (140, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 10:59:09', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (141, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 11:55:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (142, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 11:56:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (143, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 11:57:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (144, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 11:58:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (145, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 11:59:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (146, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 11:59:08', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (147, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 11:59:09', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (148, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 11:59:10', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (149, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 12:55:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (150, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 12:56:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (151, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 12:57:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (152, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 12:58:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (153, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 12:59:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (154, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 12:59:08', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (155, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 12:59:09', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (156, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 13:55:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (157, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 13:56:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (158, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 13:57:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (159, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 13:58:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (160, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 13:59:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (161, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 13:59:08', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (162, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 14:55:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (163, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 14:56:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (164, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 14:57:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (165, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 14:58:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (166, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 14:59:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (167, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 15:55:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (168, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 15:56:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (169, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 15:57:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (170, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 15:58:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (171, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 15:59:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (172, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 15:59:08', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (173, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 16:55:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (174, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 16:56:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (175, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 16:57:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (176, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 16:58:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (177, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 16:59:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (178, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 16:59:08', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (179, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 16:59:09', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (180, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 17:55:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (181, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 17:56:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (182, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 17:57:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (183, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 17:58:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (184, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 17:59:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (185, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 17:59:08', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (186, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 17:59:09', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (187, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 17:59:10', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (188, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 18:55:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (189, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 18:56:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (190, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 18:57:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (191, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 18:58:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (192, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 18:59:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (193, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 18:59:08', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (194, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 18:59:09', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (195, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 19:55:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (196, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 19:56:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (197, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 19:57:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (198, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 19:58:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (199, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 19:59:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (200, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 19:59:08', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (201, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 20:55:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (202, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 20:56:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (203, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 20:57:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (204, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 20:58:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (205, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 20:59:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (206, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 21:55:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (207, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 21:56:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (208, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 21:57:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (209, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 21:58:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (210, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 21:59:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (211, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 21:59:08', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (212, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 22:55:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (213, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 22:56:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (214, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 22:57:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (215, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 22:58:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (216, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 22:59:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (217, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 22:59:08', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (218, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 22:59:09', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (219, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 23:55:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (220, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 23:56:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (221, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 23:57:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (222, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 23:58:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (223, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 23:59:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (224, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 23:59:08', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (225, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 23:59:09', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (226, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-18 23:59:10', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (227, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 07:55:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (228, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 07:56:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (229, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 07:57:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (230, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 07:58:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (231, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 07:59:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (232, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 07:59:08', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (233, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 08:55:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (234, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 08:56:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (235, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 08:57:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (236, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 08:58:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (237, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 08:59:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (238, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 08:59:08', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (239, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 08:59:09', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (240, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 09:55:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (241, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 09:56:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (242, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 09:57:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (243, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 09:58:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (244, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 09:59:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (245, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 09:59:08', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (246, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 09:59:09', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (247, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 09:59:10', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (248, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 10:55:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (249, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 10:56:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (250, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 10:57:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (251, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 10:58:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (252, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 10:59:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (253, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 10:59:08', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (254, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 10:59:09', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (255, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 11:55:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (256, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 11:56:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (257, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 11:57:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (258, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 11:58:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (259, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 11:59:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (260, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 11:59:08', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (261, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 12:55:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (262, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-17 12:56:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (263, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-26 12:57:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (264, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-26 12:58:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (265, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-26 12:59:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (266, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-26 13:55:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (267, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-26 13:56:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (268, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-26 13:57:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (269, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-26 13:58:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (270, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-26 13:59:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (271, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-26 13:55:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (276, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-26 14:55:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (277, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-26 14:56:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (278, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-26 14:57:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (279, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-26 14:58:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (280, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-26 14:59:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (281, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-26 15:55:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (282, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-26 15:56:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (283, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-26 15:57:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (284, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-26 15:58:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (285, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-26 15:59:07', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (286, 'hitwhob001', 'A00030101', '体温', 37.5, '2019-04-26 15:59:08', '体温超出最高警戒值');
INSERT INTO `alarmLog` VALUES (287, 'hitwhob001', 'A00020201', '血压高压', 147, '2019-05-31 06:03:24', '血压高压高于设定最高警戒值');
INSERT INTO `alarmLog` VALUES (288, 'hitwhob001', 'A00030101', '体温', 40.26, '2019-05-31 08:27:10', '心率高于设定最高警戒值');
INSERT INTO `alarmLog` VALUES (289, 'hitwhob001', 'A00020201', '血压高压', 164, '2019-05-31 08:29:09', '血压高压高于设定最高警戒值');
INSERT INTO `alarmLog` VALUES (290, 'hitwhob001', 'A00020201', '心率', 218, '2019-05-31 08:29:10', '心率高于设定最高警戒值');
INSERT INTO `alarmLog` VALUES (291, 'hitwhob001', 'A00020201', '血压高压', 146, '2019-05-31 08:29:19', '血压高压高于设定最高警戒值');
INSERT INTO `alarmLog` VALUES (292, 'hitwhob001', 'A00020201', '血压高压', 150, '2019-05-31 08:29:29', '血压高压高于设定最高警戒值');
INSERT INTO `alarmLog` VALUES (293, 'hitwhob001', 'A00020201', '血压高压', 150, '2019-05-31 08:29:39', '血压高压高于设定最高警戒值');
INSERT INTO `alarmLog` VALUES (294, 'hitwhob001', 'A00030101', '体温', 37.32, '2019-05-31 08:32:49', '心率高于设定最高警戒值');
INSERT INTO `alarmLog` VALUES (295, 'hitwhob001', 'A00030101', '体温', 35.47, '2019-05-31 08:33:09', '体温低于设定最高警戒值');
INSERT INTO `alarmLog` VALUES (296, 'hitwhob001', 'A00020201', '血压高压', 144, '2019-05-31 08:34:39', '血压高压高于设定最高警戒值');
INSERT INTO `alarmLog` VALUES (297, 'hitwhob001', 'A00020201', '血压高压', 143, '2019-05-31 08:35:19', '血压高压高于设定最高警戒值');
INSERT INTO `alarmLog` VALUES (298, 'hitwhob001', 'A00030101', '体温', 39.6, '2019-06-11 21:09:19', '心率高于设定最高警戒值');
INSERT INTO `alarmLog` VALUES (299, 'hitwhob001', 'A00030101', '体温', 38.53, '2019-06-11 21:09:27', '心率高于设定最高警戒值');
INSERT INTO `alarmLog` VALUES (300, 'hitwhob001', 'A00030101', '体温', 39.29, '2019-06-21 15:12:04', '心率高于设定最高警戒值');
INSERT INTO `alarmLog` VALUES (301, 'hitwhob001', 'A00030101', '体温', 30.51, '2019-07-06 12:57:11', '体温低于设定最高警戒值');
INSERT INTO `alarmLog` VALUES (302, 'hitwhob001', 'A00030101', '体温', 30.47, '2019-07-06 12:57:20', '体温低于设定最高警戒值');
INSERT INTO `alarmLog` VALUES (303, 'hitwhob001', 'A00030101', '体温', 30.97, '2019-07-06 12:57:30', '体温低于设定最高警戒值');
INSERT INTO `alarmLog` VALUES (304, 'hitwhob001', 'A00030101', '体温', 30.51, '2019-07-06 12:57:40', '体温低于设定最高警戒值');
INSERT INTO `alarmLog` VALUES (305, 'hitwhob001', 'A00030101', '体温', 30.79, '2019-07-06 12:57:50', '体温低于设定最高警戒值');
INSERT INTO `alarmLog` VALUES (306, 'hitwhob001', 'A00030101', '体温', 30.79, '2019-07-06 12:58:00', '体温低于设定最高警戒值');
INSERT INTO `alarmLog` VALUES (307, 'hitwhob001', 'A00030101', '体温', 30.79, '2019-07-06 12:58:10', '体温低于设定最高警戒值');
INSERT INTO `alarmLog` VALUES (308, 'hitwhob001', 'A00030101', '体温', 30.79, '2019-07-06 12:58:46', '体温低于设定最高警戒值');
INSERT INTO `alarmLog` VALUES (309, 'hitwhob001', 'A00030101', '体温', 30.79, '2019-07-06 12:58:56', '体温低于设定最高警戒值');
INSERT INTO `alarmLog` VALUES (310, 'hitwhob001', 'A00030101', '体温', 30.86, '2019-07-06 13:00:26', '体温低于设定最高警戒值');
INSERT INTO `alarmLog` VALUES (311, 'hitwhob001', 'A00030101', '体温', 30.01, '2019-07-06 13:00:46', '体温低于设定最高警戒值');
INSERT INTO `alarmLog` VALUES (312, 'hitwhob001', 'A00030101', '体温', 30.01, '2019-07-06 13:00:56', '体温低于设定最高警戒值');
INSERT INTO `alarmLog` VALUES (313, 'hitwhob001', 'A00030101', '体温', 30.01, '2019-07-06 13:01:05', '体温低于设定最高警戒值');
INSERT INTO `alarmLog` VALUES (314, 'hitwhob001', 'A00030101', '体温', 30.01, '2019-07-06 13:01:16', '体温低于设定最高警戒值');

-- ----------------------------
-- Table structure for alarmNormalValue
-- ----------------------------
DROP TABLE IF EXISTS `alarmNormalValue`;
CREATE TABLE `alarmNormalValue`  (
  `valueId` int(11) NOT NULL AUTO_INCREMENT,
  `valueName` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `eqpType` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `indexSeq` int(11) NOT NULL,
  `serial` int(11) NOT NULL,
  `valueType` bit(1) NOT NULL,
  `value` float NOT NULL,
  `measurementUnit` varchar(12) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`valueId`) USING BTREE,
  INDEX `alarmNormalValue_equipmentType_eqpType_fk`(`eqpType`) USING BTREE,
  CONSTRAINT `alarmNormalValue_equipmentType_eqpType_fk` FOREIGN KEY (`eqpType`) REFERENCES `equipmentType` (`eqpType`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of alarmNormalValue
-- ----------------------------
INSERT INTO `alarmNormalValue` VALUES (1, '体温最高警戒值', 'Temperature01', 2, 1, b'1', 37, '摄氏度(℃)');
INSERT INTO `alarmNormalValue` VALUES (2, '体温最低警戒值', 'Temperature01', 2, 2, b'0', 36, '摄氏度(℃)');
INSERT INTO `alarmNormalValue` VALUES (3, '环境温度最高警戒值', 'Temperature01', 1, 3, b'1', 50, '摄氏度(℃)');
INSERT INTO `alarmNormalValue` VALUES (4, '环境温度最低警戒值', 'Temperature01', 1, 4, b'0', -30, '摄氏度(℃)');
INSERT INTO `alarmNormalValue` VALUES (5, '血氧浓度最低警戒值', 'BloodOxygen01', 1, 1, b'0', 94, '%');
INSERT INTO `alarmNormalValue` VALUES (6, '收缩压最高警戒值', 'BloodPressure01', 2, 1, b'1', 140, 'mmHg');
INSERT INTO `alarmNormalValue` VALUES (7, '收缩压最低警戒值', 'BloodPressure01', 2, 2, b'0', 90, 'mmHg');
INSERT INTO `alarmNormalValue` VALUES (8, '舒张压最高警戒值', 'BloodPressure01', 3, 3, b'1', 90, 'mmHg');
INSERT INTO `alarmNormalValue` VALUES (9, '舒张压最低警戒值', 'BloodPressure01', 3, 4, b'0', 60, 'mmHg');
INSERT INTO `alarmNormalValue` VALUES (10, '心率最高警戒值', 'BloodPressure01', 1, 5, b'1', 160, '次/分钟');
INSERT INTO `alarmNormalValue` VALUES (11, '心率最低警戒值', 'BloodPressure01', 1, 6, b'0', 50, '次/分钟');

-- ----------------------------
-- Table structure for alarmSpecialValue
-- ----------------------------
DROP TABLE IF EXISTS `alarmSpecialValue`;
CREATE TABLE `alarmSpecialValue`  (
  `valueId` int(11) NOT NULL AUTO_INCREMENT,
  `valueName` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `eqpId` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `indexSeq` int(11) NOT NULL,
  `serial` int(11) NOT NULL,
  `valueType` bit(1) NOT NULL,
  `value` float NOT NULL,
  `measurementUnit` varchar(12) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`valueId`) USING BTREE,
  INDEX `alarmSpecialValue_equipment_eqpId_fk`(`eqpId`) USING BTREE,
  CONSTRAINT `alarmSpecialValue_equipment_eqpId_fk` FOREIGN KEY (`eqpId`) REFERENCES `equipment` (`eqpId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of alarmSpecialValue
-- ----------------------------
INSERT INTO `alarmSpecialValue` VALUES (18, '收缩压最高警戒值', 'A00020201', 2, 1, b'1', 140, 'mmHg');
INSERT INTO `alarmSpecialValue` VALUES (19, '收缩压最低警戒值', 'A00020201', 2, 2, b'0', 90, 'mmHg');
INSERT INTO `alarmSpecialValue` VALUES (20, '舒张压最高警戒值', 'A00020201', 3, 3, b'1', 90, 'mmHg');
INSERT INTO `alarmSpecialValue` VALUES (21, '舒张压最低警戒值', 'A00020201', 3, 4, b'0', 60, 'mmHg');
INSERT INTO `alarmSpecialValue` VALUES (22, '心率最高警戒值', 'A00020201', 1, 5, b'1', 170, '次/分钟');
INSERT INTO `alarmSpecialValue` VALUES (23, '心率最低警戒值', 'A00020201', 1, 6, b'0', 60, '次/分钟');

-- ----------------------------
-- Table structure for equipment
-- ----------------------------
DROP TABLE IF EXISTS `equipment`;
CREATE TABLE `equipment`  (
  `eqpId` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `objectId` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `eqpName` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `eqpType` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `netmaskId` int(11) NOT NULL,
  `deviceSerial` int(11) NOT NULL,
  `special` bit(1) NOT NULL,
  `registerDate` date NOT NULL,
  PRIMARY KEY (`eqpId`) USING BTREE,
  INDEX `equipment_object__fk`(`objectId`) USING BTREE,
  INDEX `equipment_equipmentType_eqpType_fk`(`eqpType`) USING BTREE,
  INDEX `equipment_netmaskInfo__fk`(`netmaskId`) USING BTREE,
  CONSTRAINT `equipment_equipmentType_eqpType_fk` FOREIGN KEY (`eqpType`) REFERENCES `equipmentType` (`eqpType`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `equipment_netmaskInfo__fk` FOREIGN KEY (`netmaskId`) REFERENCES `netmaskInfo` (`netmaskId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `equipment_object__fk` FOREIGN KEY (`objectId`) REFERENCES `object` (`objectId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of equipment
-- ----------------------------
INSERT INTO `equipment` VALUES ('A00030101', 'hitwhob001', 'hitwhob001_温度设备', 'Temperature01', 1, 1, b'0', '2019-03-02');
INSERT INTO `equipment` VALUES ('A000304', 'hitwhob001', 'yuyuan', 'Temperature01', 1, 0, b'0', '2019-11-19');
INSERT INTO `equipment` VALUES ('A000403', 'hitwh001', 'yuyuan', 'BloodOxygen01', 1, 0, b'0', '2019-11-16');
INSERT INTO `equipment` VALUES ('A00060302', 'hitwhob002', 'hitwhob001_血氧设备', 'BloodOxygen01', 1, 2, b'0', '2019-03-02');
INSERT INTO `equipment` VALUES ('A000801', 'hitwhob001', 'hitwhob001_温度设备', 'Temperature01', 1, 0, b'0', '2019-11-17');

-- ----------------------------
-- Table structure for equipmentIndex
-- ----------------------------
DROP TABLE IF EXISTS `equipmentIndex`;
CREATE TABLE `equipmentIndex`  (
  `indexId` int(11) NOT NULL AUTO_INCREMENT,
  `eqpType` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `indexSeq` int(11) NOT NULL,
  `indexName` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `measurementUnit` varchar(12) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`indexId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of equipmentIndex
-- ----------------------------
INSERT INTO `equipmentIndex` VALUES (1, 'Temperature01', 1, '环境温度', '摄氏度(℃)');
INSERT INTO `equipmentIndex` VALUES (2, 'Temperature01', 2, '体温', '摄氏度(℃)');
INSERT INTO `equipmentIndex` VALUES (3, 'BloodOxygen01', 1, '血氧饱和度', '%');
INSERT INTO `equipmentIndex` VALUES (4, 'BloodPressure01', 1, '心率', '次/分钟');
INSERT INTO `equipmentIndex` VALUES (5, 'BloodPressure01', 2, '收缩压', 'mmHg');
INSERT INTO `equipmentIndex` VALUES (6, 'BloodPressure01', 3, '舒张压', 'mmHg');
INSERT INTO `equipmentIndex` VALUES (29, 'Temperature02', 1, '体温', '摄氏度(℃)');
INSERT INTO `equipmentIndex` VALUES (30, 'Temperature02', 2, '环境温度', '摄氏度(℃)');

-- ----------------------------
-- Table structure for equipmentType
-- ----------------------------
DROP TABLE IF EXISTS `equipmentType`;
CREATE TABLE `equipmentType`  (
  `eqpType` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `stringMatch` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `indexTotal` int(11) NOT NULL,
  `alarmSerialMax` int(11) NOT NULL,
  `introduction` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL,
  PRIMARY KEY (`eqpType`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of equipmentType
-- ----------------------------
INSERT INTO `equipmentType` VALUES ('BloodOxygen01', 'AXXXX03', 1, 1, 'BloodOxygen01型是一种能够检测血氧浓度的设备，采用LoRa无线通信协议传输数据。');
INSERT INTO `equipmentType` VALUES ('BloodPressure01', 'AXXXX02', 3, 6, 'BloodPressure01型是一种能够检测血压（包括收缩压和舒张压）以及心率的设备，采用LoRa无线通信协议传输数据。');
INSERT INTO `equipmentType` VALUES ('Mattress01', 'AXXXX01', 1, 1, 'Matress01型是床垫');
INSERT INTO `equipmentType` VALUES ('Temperature01', 'AXXXX04', 2, 4, 'Temperature01型是一种能够检测体温和环境温度的设备，采用LoRa无线通信协议传输数据。');

-- ----------------------------
-- Table structure for health_data
-- ----------------------------
DROP TABLE IF EXISTS `health_data`;
CREATE TABLE `health_data`  (
  `id` int(11) NOT NULL,
  `eqp_id` int(11) NOT NULL,
  `netmask_id` int(11) NOT NULL,
  `object_id` int(11) NULL DEFAULT NULL,
  `time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `sendtime` datetime(0) NOT NULL,
  `e_temp` int(11) NULL DEFAULT -1 COMMENT '1 (X100)',
  `b_temp` int(11) NULL DEFAULT -1 COMMENT '2 (X100)',
  `heart_rate` int(11) NULL DEFAULT -1 COMMENT '3',
  `s_pressure` int(11) NULL DEFAULT -1 COMMENT '4',
  `d_pressure` int(11) NULL DEFAULT -1 COMMENT '5',
  `blood_oxygen` int(11) NULL DEFAULT -1 COMMENT '6',
  `breath` int(11) NULL DEFAULT -1 COMMENT '7',
  `act` int(11) NULL DEFAULT -1 COMMENT '8',
  `data_type` int(11) NULL DEFAULT NULL COMMENT '8*n位长的二进制数对应的十进制的值，用于注释哪一位有数据',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mainPic
-- ----------------------------
DROP TABLE IF EXISTS `mainPic`;
CREATE TABLE `mainPic`  (
  `picId` int(11) NOT NULL AUTO_INCREMENT,
  `picUrl` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `newsId` int(11) NOT NULL,
  `newsHead` varchar(48) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `publishDate` date NOT NULL,
  PRIMARY KEY (`picId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mainPic
-- ----------------------------
INSERT INTO `mainPic` VALUES (1, 'images/1561103329720', 32, '春季养生知多少-1', '2019-03-11');
INSERT INTO `mainPic` VALUES (2, 'images/1552874130707.jpg', 22, '关于合理膳食的最新建议-6', '2019-03-11');
INSERT INTO `mainPic` VALUES (3, 'images/1552874172249.jpg', 8, '多吃蔬菜的好处-8', '2019-03-11');

-- ----------------------------
-- Table structure for netmaskInfo
-- ----------------------------
DROP TABLE IF EXISTS `netmaskInfo`;
CREATE TABLE `netmaskInfo`  (
  `netmaskId` int(11) NOT NULL,
  `netmaskName` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `ipAddress` varchar(15) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `comProtocol` varchar(8) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `state` varchar(4) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `port` int(16) NOT NULL,
  PRIMARY KEY (`netmaskId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of netmaskInfo
-- ----------------------------
INSERT INTO `netmaskInfo` VALUES (1, '哈工大威海网关1号', '111.111.111.111', 'MODBUS', '正常', 0);

-- ----------------------------
-- Table structure for news
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news`  (
  `newsId` int(11) NOT NULL AUTO_INCREMENT,
  `newsType` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `newsHead` varchar(48) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `newsContent` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `publishDate` date NOT NULL,
  `viewCount` int(11) NULL DEFAULT 0,
  PRIMARY KEY (`newsId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 48 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of news
-- ----------------------------
INSERT INTO `news` VALUES (1, '健康阅读', '多吃蔬菜的好处-1', '多吃蔬菜可以补充维生素、矿物质、纤维素。\r\n\r\n1.可以补充维生素\r\n\r\n很多蔬菜中都含有大量的维生素，比如青菜、番茄等，并且有利于我们对维生素的吸收，能有效的提高我们的免疫力。\r\n\r\n2.可以补充矿物质\r\n\r\n蔬菜中富含有钙、铁、铜等矿物质，这些物质能有效调节人体酸碱平衡，其中钙元素更是能促进人体生长，适合多吃。\r\n\r\n3.可以补充纤维素\r\n\r\n蔬菜中也含有大量的纤维素，纤维素能刺激胃液分泌和肠道蠕动，增加食物与消化液的接触面积，有助于人体消化吸收食物，促进代谢废物排出，并防止便秘。\r\n\r\n扩展资料：\r\n\r\n吃蔬菜的禁忌：\r\n1、餐前吃西红柿\r\n\r\n我们在吃完饭之后，一定要注意不要立刻使用西红柿，因为西红柿中含有很多的胃酸，这种物质与食物相混合之后会降低十五中的酸度，严重的话有可能会造成胃扩张。\r\n\r\n2、吃未炒熟的豆芽菜\r\n\r\n豆芽这种蔬菜在食用前一定要炒熟，因为不熟的豆芽牙尖上会有一些有毒物质，进入人体之后，有可能造成人体中毒。豆还会出现恶心、呕吐、腹泻、头晕等不适反应。\r\n\r\n3、频繁吃菠菜\r\n\r\n因为菠菜中含有大量的草酸，我们食用过量的话，这些草酸会严重影响我们对营养的吸收，还会造成人体内酸过量，不宜多吃，以免造成更严重的问题。\r\n参考资料：人民网：多吃蔬菜会带来哪些好处？', '2019-03-09', 1);
INSERT INTO `news` VALUES (2, '健康阅读', '多吃蔬菜的好处-2', '多吃蔬菜可以补充维生素、矿物质、纤维素。\r\n\r\n1.可以补充维生素\r\n\r\n很多蔬菜中都含有大量的维生素，比如青菜、番茄等，并且有利于我们对维生素的吸收，能有效的提高我们的免疫力。\r\n\r\n2.可以补充矿物质\r\n\r\n蔬菜中富含有钙、铁、铜等矿物质，这些物质能有效调节人体酸碱平衡，其中钙元素更是能促进人体生长，适合多吃。\r\n\r\n3.可以补充纤维素\r\n\r\n蔬菜中也含有大量的纤维素，纤维素能刺激胃液分泌和肠道蠕动，增加食物与消化液的接触面积，有助于人体消化吸收食物，促进代谢废物排出，并防止便秘。\r\n\r\n扩展资料：\r\n\r\n吃蔬菜的禁忌：\r\n1、餐前吃西红柿\r\n\r\n我们在吃完饭之后，一定要注意不要立刻使用西红柿，因为西红柿中含有很多的胃酸，这种物质与食物相混合之后会降低十五中的酸度，严重的话有可能会造成胃扩张。\r\n\r\n2、吃未炒熟的豆芽菜\r\n\r\n豆芽这种蔬菜在食用前一定要炒熟，因为不熟的豆芽牙尖上会有一些有毒物质，进入人体之后，有可能造成人体中毒。豆还会出现恶心、呕吐、腹泻、头晕等不适反应。\r\n\r\n3、频繁吃菠菜\r\n\r\n因为菠菜中含有大量的草酸，我们食用过量的话，这些草酸会严重影响我们对营养的吸收，还会造成人体内酸过量，不宜多吃，以免造成更严重的问题。\r\n参考资料：人民网：多吃蔬菜会带来哪些好处？', '2019-03-09', 2);
INSERT INTO `news` VALUES (3, '健康阅读', '多吃蔬菜的好处-3', '多吃蔬菜可以补充维生素、矿物质、纤维素。\r\n\r\n1.可以补充维生素\r\n\r\n很多蔬菜中都含有大量的维生素，比如青菜、番茄等，并且有利于我们对维生素的吸收，能有效的提高我们的免疫力。\r\n\r\n2.可以补充矿物质\r\n\r\n蔬菜中富含有钙、铁、铜等矿物质，这些物质能有效调节人体酸碱平衡，其中钙元素更是能促进人体生长，适合多吃。\r\n\r\n3.可以补充纤维素\r\n\r\n蔬菜中也含有大量的纤维素，纤维素能刺激胃液分泌和肠道蠕动，增加食物与消化液的接触面积，有助于人体消化吸收食物，促进代谢废物排出，并防止便秘。\r\n\r\n扩展资料：\r\n\r\n吃蔬菜的禁忌：\r\n1、餐前吃西红柿\r\n\r\n我们在吃完饭之后，一定要注意不要立刻使用西红柿，因为西红柿中含有很多的胃酸，这种物质与食物相混合之后会降低十五中的酸度，严重的话有可能会造成胃扩张。\r\n\r\n2、吃未炒熟的豆芽菜\r\n\r\n豆芽这种蔬菜在食用前一定要炒熟，因为不熟的豆芽牙尖上会有一些有毒物质，进入人体之后，有可能造成人体中毒。豆还会出现恶心、呕吐、腹泻、头晕等不适反应。\r\n\r\n3、频繁吃菠菜\r\n\r\n因为菠菜中含有大量的草酸，我们食用过量的话，这些草酸会严重影响我们对营养的吸收，还会造成人体内酸过量，不宜多吃，以免造成更严重的问题。\r\n参考资料：人民网：多吃蔬菜会带来哪些好处？', '2019-03-09', 0);
INSERT INTO `news` VALUES (4, '健康阅读', '多吃蔬菜的好处-4', '多吃蔬菜可以补充维生素、矿物质、纤维素。\r\n\r\n1.可以补充维生素\r\n\r\n很多蔬菜中都含有大量的维生素，比如青菜、番茄等，并且有利于我们对维生素的吸收，能有效的提高我们的免疫力。\r\n\r\n2.可以补充矿物质\r\n\r\n蔬菜中富含有钙、铁、铜等矿物质，这些物质能有效调节人体酸碱平衡，其中钙元素更是能促进人体生长，适合多吃。\r\n\r\n3.可以补充纤维素\r\n\r\n蔬菜中也含有大量的纤维素，纤维素能刺激胃液分泌和肠道蠕动，增加食物与消化液的接触面积，有助于人体消化吸收食物，促进代谢废物排出，并防止便秘。\r\n\r\n扩展资料：\r\n\r\n吃蔬菜的禁忌：\r\n1、餐前吃西红柿\r\n\r\n我们在吃完饭之后，一定要注意不要立刻使用西红柿，因为西红柿中含有很多的胃酸，这种物质与食物相混合之后会降低十五中的酸度，严重的话有可能会造成胃扩张。\r\n\r\n2、吃未炒熟的豆芽菜\r\n\r\n豆芽这种蔬菜在食用前一定要炒熟，因为不熟的豆芽牙尖上会有一些有毒物质，进入人体之后，有可能造成人体中毒。豆还会出现恶心、呕吐、腹泻、头晕等不适反应。\r\n\r\n3、频繁吃菠菜\r\n\r\n因为菠菜中含有大量的草酸，我们食用过量的话，这些草酸会严重影响我们对营养的吸收，还会造成人体内酸过量，不宜多吃，以免造成更严重的问题。\r\n参考资料：人民网：多吃蔬菜会带来哪些好处？', '2019-03-09', 0);
INSERT INTO `news` VALUES (5, '健康阅读', '多吃蔬菜的好处-5', '多吃蔬菜可以补充维生素、矿物质、纤维素。\r\n\r\n1.可以补充维生素\r\n\r\n很多蔬菜中都含有大量的维生素，比如青菜、番茄等，并且有利于我们对维生素的吸收，能有效的提高我们的免疫力。\r\n\r\n2.可以补充矿物质\r\n\r\n蔬菜中富含有钙、铁、铜等矿物质，这些物质能有效调节人体酸碱平衡，其中钙元素更是能促进人体生长，适合多吃。\r\n\r\n3.可以补充纤维素\r\n\r\n蔬菜中也含有大量的纤维素，纤维素能刺激胃液分泌和肠道蠕动，增加食物与消化液的接触面积，有助于人体消化吸收食物，促进代谢废物排出，并防止便秘。\r\n\r\n扩展资料：\r\n\r\n吃蔬菜的禁忌：\r\n1、餐前吃西红柿\r\n\r\n我们在吃完饭之后，一定要注意不要立刻使用西红柿，因为西红柿中含有很多的胃酸，这种物质与食物相混合之后会降低十五中的酸度，严重的话有可能会造成胃扩张。\r\n\r\n2、吃未炒熟的豆芽菜\r\n\r\n豆芽这种蔬菜在食用前一定要炒熟，因为不熟的豆芽牙尖上会有一些有毒物质，进入人体之后，有可能造成人体中毒。豆还会出现恶心、呕吐、腹泻、头晕等不适反应。\r\n\r\n3、频繁吃菠菜\r\n\r\n因为菠菜中含有大量的草酸，我们食用过量的话，这些草酸会严重影响我们对营养的吸收，还会造成人体内酸过量，不宜多吃，以免造成更严重的问题。\r\n参考资料：人民网：多吃蔬菜会带来哪些好处？', '2019-03-09', 7);
INSERT INTO `news` VALUES (6, '健康阅读', '多吃蔬菜的好处-6', '多吃蔬菜可以补充维生素、矿物质、纤维素。\r\n\r\n1.可以补充维生素\r\n\r\n很多蔬菜中都含有大量的维生素，比如青菜、番茄等，并且有利于我们对维生素的吸收，能有效的提高我们的免疫力。\r\n\r\n2.可以补充矿物质\r\n\r\n蔬菜中富含有钙、铁、铜等矿物质，这些物质能有效调节人体酸碱平衡，其中钙元素更是能促进人体生长，适合多吃。\r\n\r\n3.可以补充纤维素\r\n\r\n蔬菜中也含有大量的纤维素，纤维素能刺激胃液分泌和肠道蠕动，增加食物与消化液的接触面积，有助于人体消化吸收食物，促进代谢废物排出，并防止便秘。\r\n\r\n扩展资料：\r\n\r\n吃蔬菜的禁忌：\r\n1、餐前吃西红柿\r\n\r\n我们在吃完饭之后，一定要注意不要立刻使用西红柿，因为西红柿中含有很多的胃酸，这种物质与食物相混合之后会降低十五中的酸度，严重的话有可能会造成胃扩张。\r\n\r\n2、吃未炒熟的豆芽菜\r\n\r\n豆芽这种蔬菜在食用前一定要炒熟，因为不熟的豆芽牙尖上会有一些有毒物质，进入人体之后，有可能造成人体中毒。豆还会出现恶心、呕吐、腹泻、头晕等不适反应。\r\n\r\n3、频繁吃菠菜\r\n\r\n因为菠菜中含有大量的草酸，我们食用过量的话，这些草酸会严重影响我们对营养的吸收，还会造成人体内酸过量，不宜多吃，以免造成更严重的问题。\r\n参考资料：人民网：多吃蔬菜会带来哪些好处？', '2019-03-09', 1);
INSERT INTO `news` VALUES (7, '健康阅读', '多吃蔬菜的好处-7', '多吃蔬菜可以补充维生素、矿物质、纤维素。\r\n\r\n1.可以补充维生素\r\n\r\n很多蔬菜中都含有大量的维生素，比如青菜、番茄等，并且有利于我们对维生素的吸收，能有效的提高我们的免疫力。\r\n\r\n2.可以补充矿物质\r\n\r\n蔬菜中富含有钙、铁、铜等矿物质，这些物质能有效调节人体酸碱平衡，其中钙元素更是能促进人体生长，适合多吃。\r\n\r\n3.可以补充纤维素\r\n\r\n蔬菜中也含有大量的纤维素，纤维素能刺激胃液分泌和肠道蠕动，增加食物与消化液的接触面积，有助于人体消化吸收食物，促进代谢废物排出，并防止便秘。\r\n\r\n扩展资料：\r\n\r\n吃蔬菜的禁忌：\r\n1、餐前吃西红柿\r\n\r\n我们在吃完饭之后，一定要注意不要立刻使用西红柿，因为西红柿中含有很多的胃酸，这种物质与食物相混合之后会降低十五中的酸度，严重的话有可能会造成胃扩张。\r\n\r\n2、吃未炒熟的豆芽菜\r\n\r\n豆芽这种蔬菜在食用前一定要炒熟，因为不熟的豆芽牙尖上会有一些有毒物质，进入人体之后，有可能造成人体中毒。豆还会出现恶心、呕吐、腹泻、头晕等不适反应。\r\n\r\n3、频繁吃菠菜\r\n\r\n因为菠菜中含有大量的草酸，我们食用过量的话，这些草酸会严重影响我们对营养的吸收，还会造成人体内酸过量，不宜多吃，以免造成更严重的问题。\r\n参考资料：人民网：多吃蔬菜会带来哪些好处？', '2019-03-09', 0);
INSERT INTO `news` VALUES (8, '健康阅读', '多吃蔬菜的好处-8', '多吃蔬菜可以补充维生素、矿物质、纤维素。\r\n\r\n1.可以补充维生素\r\n\r\n很多蔬菜中都含有大量的维生素，比如青菜、番茄等，并且有利于我们对维生素的吸收，能有效的提高我们的免疫力。\r\n\r\n2.可以补充矿物质\r\n\r\n蔬菜中富含有钙、铁、铜等矿物质，这些物质能有效调节人体酸碱平衡，其中钙元素更是能促进人体生长，适合多吃。\r\n\r\n3.可以补充纤维素\r\n\r\n蔬菜中也含有大量的纤维素，纤维素能刺激胃液分泌和肠道蠕动，增加食物与消化液的接触面积，有助于人体消化吸收食物，促进代谢废物排出，并防止便秘。\r\n\r\n扩展资料：\r\n\r\n吃蔬菜的禁忌：\r\n1、餐前吃西红柿\r\n\r\n我们在吃完饭之后，一定要注意不要立刻使用西红柿，因为西红柿中含有很多的胃酸，这种物质与食物相混合之后会降低十五中的酸度，严重的话有可能会造成胃扩张。\r\n\r\n2、吃未炒熟的豆芽菜\r\n\r\n豆芽这种蔬菜在食用前一定要炒熟，因为不熟的豆芽牙尖上会有一些有毒物质，进入人体之后，有可能造成人体中毒。豆还会出现恶心、呕吐、腹泻、头晕等不适反应。\r\n\r\n3、频繁吃菠菜\r\n\r\n因为菠菜中含有大量的草酸，我们食用过量的话，这些草酸会严重影响我们对营养的吸收，还会造成人体内酸过量，不宜多吃，以免造成更严重的问题。\r\n参考资料：人民网：多吃蔬菜会带来哪些好处？', '2019-03-09', 19);
INSERT INTO `news` VALUES (9, '健康阅读', '多吃蔬菜的好处-9', '多吃蔬菜可以补充维生素、矿物质、纤维素。\r\n\r\n1.可以补充维生素\r\n\r\n很多蔬菜中都含有大量的维生素，比如青菜、番茄等，并且有利于我们对维生素的吸收，能有效的提高我们的免疫力。\r\n\r\n2.可以补充矿物质\r\n\r\n蔬菜中富含有钙、铁、铜等矿物质，这些物质能有效调节人体酸碱平衡，其中钙元素更是能促进人体生长，适合多吃。\r\n\r\n3.可以补充纤维素\r\n\r\n蔬菜中也含有大量的纤维素，纤维素能刺激胃液分泌和肠道蠕动，增加食物与消化液的接触面积，有助于人体消化吸收食物，促进代谢废物排出，并防止便秘。\r\n\r\n扩展资料：\r\n\r\n吃蔬菜的禁忌：\r\n1、餐前吃西红柿\r\n\r\n我们在吃完饭之后，一定要注意不要立刻使用西红柿，因为西红柿中含有很多的胃酸，这种物质与食物相混合之后会降低十五中的酸度，严重的话有可能会造成胃扩张。\r\n\r\n2、吃未炒熟的豆芽菜\r\n\r\n豆芽这种蔬菜在食用前一定要炒熟，因为不熟的豆芽牙尖上会有一些有毒物质，进入人体之后，有可能造成人体中毒。豆还会出现恶心、呕吐、腹泻、头晕等不适反应。\r\n\r\n3、频繁吃菠菜\r\n\r\n因为菠菜中含有大量的草酸，我们食用过量的话，这些草酸会严重影响我们对营养的吸收，还会造成人体内酸过量，不宜多吃，以免造成更严重的问题。\r\n参考资料：人民网：多吃蔬菜会带来哪些好处？', '2019-03-09', 0);
INSERT INTO `news` VALUES (10, '健康阅读', '多吃蔬菜的好处-10', '多吃蔬菜可以补充维生素、矿物质、纤维素。\r\n\r\n1.可以补充维生素\r\n\r\n很多蔬菜中都含有大量的维生素，比如青菜、番茄等，并且有利于我们对维生素的吸收，能有效的提高我们的免疫力。\r\n\r\n2.可以补充矿物质\r\n\r\n蔬菜中富含有钙、铁、铜等矿物质，这些物质能有效调节人体酸碱平衡，其中钙元素更是能促进人体生长，适合多吃。\r\n\r\n3.可以补充纤维素\r\n\r\n蔬菜中也含有大量的纤维素，纤维素能刺激胃液分泌和肠道蠕动，增加食物与消化液的接触面积，有助于人体消化吸收食物，促进代谢废物排出，并防止便秘。\r\n\r\n扩展资料：\r\n\r\n吃蔬菜的禁忌：\r\n1、餐前吃西红柿\r\n\r\n我们在吃完饭之后，一定要注意不要立刻使用西红柿，因为西红柿中含有很多的胃酸，这种物质与食物相混合之后会降低十五中的酸度，严重的话有可能会造成胃扩张。\r\n\r\n2、吃未炒熟的豆芽菜\r\n\r\n豆芽这种蔬菜在食用前一定要炒熟，因为不熟的豆芽牙尖上会有一些有毒物质，进入人体之后，有可能造成人体中毒。豆还会出现恶心、呕吐、腹泻、头晕等不适反应。\r\n\r\n3、频繁吃菠菜\r\n\r\n因为菠菜中含有大量的草酸，我们食用过量的话，这些草酸会严重影响我们对营养的吸收，还会造成人体内酸过量，不宜多吃，以免造成更严重的问题。\r\n参考资料：人民网：多吃蔬菜会带来哪些好处？', '2019-03-09', 0);
INSERT INTO `news` VALUES (11, '健康阅读', '多吃蔬菜的好处-11', '多吃蔬菜可以补充维生素、矿物质、纤维素。\r\n\r\n1.可以补充维生素\r\n\r\n很多蔬菜中都含有大量的维生素，比如青菜、番茄等，并且有利于我们对维生素的吸收，能有效的提高我们的免疫力。\r\n\r\n2.可以补充矿物质\r\n\r\n蔬菜中富含有钙、铁、铜等矿物质，这些物质能有效调节人体酸碱平衡，其中钙元素更是能促进人体生长，适合多吃。\r\n\r\n3.可以补充纤维素\r\n\r\n蔬菜中也含有大量的纤维素，纤维素能刺激胃液分泌和肠道蠕动，增加食物与消化液的接触面积，有助于人体消化吸收食物，促进代谢废物排出，并防止便秘。\r\n\r\n扩展资料：\r\n\r\n吃蔬菜的禁忌：\r\n1、餐前吃西红柿\r\n\r\n我们在吃完饭之后，一定要注意不要立刻使用西红柿，因为西红柿中含有很多的胃酸，这种物质与食物相混合之后会降低十五中的酸度，严重的话有可能会造成胃扩张。\r\n\r\n2、吃未炒熟的豆芽菜\r\n\r\n豆芽这种蔬菜在食用前一定要炒熟，因为不熟的豆芽牙尖上会有一些有毒物质，进入人体之后，有可能造成人体中毒。豆还会出现恶心、呕吐、腹泻、头晕等不适反应。\r\n\r\n3、频繁吃菠菜\r\n\r\n因为菠菜中含有大量的草酸，我们食用过量的话，这些草酸会严重影响我们对营养的吸收，还会造成人体内酸过量，不宜多吃，以免造成更严重的问题。\r\n参考资料：人民网：多吃蔬菜会带来哪些好处？', '2019-03-09', 1);
INSERT INTO `news` VALUES (12, '健康阅读', '多吃蔬菜的好处-12', '多吃蔬菜可以补充维生素、矿物质、纤维素。\r\n\r\n1.可以补充维生素\r\n\r\n很多蔬菜中都含有大量的维生素，比如青菜、番茄等，并且有利于我们对维生素的吸收，能有效的提高我们的免疫力。\r\n\r\n2.可以补充矿物质\r\n\r\n蔬菜中富含有钙、铁、铜等矿物质，这些物质能有效调节人体酸碱平衡，其中钙元素更是能促进人体生长，适合多吃。\r\n\r\n3.可以补充纤维素\r\n\r\n蔬菜中也含有大量的纤维素，纤维素能刺激胃液分泌和肠道蠕动，增加食物与消化液的接触面积，有助于人体消化吸收食物，促进代谢废物排出，并防止便秘。\r\n\r\n扩展资料：\r\n\r\n吃蔬菜的禁忌：\r\n1、餐前吃西红柿\r\n\r\n我们在吃完饭之后，一定要注意不要立刻使用西红柿，因为西红柿中含有很多的胃酸，这种物质与食物相混合之后会降低十五中的酸度，严重的话有可能会造成胃扩张。\r\n\r\n2、吃未炒熟的豆芽菜\r\n\r\n豆芽这种蔬菜在食用前一定要炒熟，因为不熟的豆芽牙尖上会有一些有毒物质，进入人体之后，有可能造成人体中毒。豆还会出现恶心、呕吐、腹泻、头晕等不适反应。\r\n\r\n3、频繁吃菠菜\r\n\r\n因为菠菜中含有大量的草酸，我们食用过量的话，这些草酸会严重影响我们对营养的吸收，还会造成人体内酸过量，不宜多吃，以免造成更严重的问题。\r\n参考资料：人民网：多吃蔬菜会带来哪些好处？', '2019-03-09', 2);
INSERT INTO `news` VALUES (13, '健康阅读', '多吃蔬菜的好处-13', '多吃蔬菜可以补充维生素、矿物质、纤维素。\r\n\r\n1.可以补充维生素\r\n\r\n很多蔬菜中都含有大量的维生素，比如青菜、番茄等，并且有利于我们对维生素的吸收，能有效的提高我们的免疫力。\r\n\r\n2.可以补充矿物质\r\n\r\n蔬菜中富含有钙、铁、铜等矿物质，这些物质能有效调节人体酸碱平衡，其中钙元素更是能促进人体生长，适合多吃。\r\n\r\n3.可以补充纤维素\r\n\r\n蔬菜中也含有大量的纤维素，纤维素能刺激胃液分泌和肠道蠕动，增加食物与消化液的接触面积，有助于人体消化吸收食物，促进代谢废物排出，并防止便秘。\r\n\r\n扩展资料：\r\n\r\n吃蔬菜的禁忌：\r\n1、餐前吃西红柿\r\n\r\n我们在吃完饭之后，一定要注意不要立刻使用西红柿，因为西红柿中含有很多的胃酸，这种物质与食物相混合之后会降低十五中的酸度，严重的话有可能会造成胃扩张。\r\n\r\n2、吃未炒熟的豆芽菜\r\n\r\n豆芽这种蔬菜在食用前一定要炒熟，因为不熟的豆芽牙尖上会有一些有毒物质，进入人体之后，有可能造成人体中毒。豆还会出现恶心、呕吐、腹泻、头晕等不适反应。\r\n\r\n3、频繁吃菠菜\r\n\r\n因为菠菜中含有大量的草酸，我们食用过量的话，这些草酸会严重影响我们对营养的吸收，还会造成人体内酸过量，不宜多吃，以免造成更严重的问题。\r\n参考资料：人民网：多吃蔬菜会带来哪些好处？', '2019-03-09', 5);
INSERT INTO `news` VALUES (14, '健康阅读', '多吃蔬菜的好处-14', '多吃蔬菜可以补充维生素、矿物质、纤维素。\r\n\r\n1.可以补充维生素\r\n\r\n很多蔬菜中都含有大量的维生素，比如青菜、番茄等，并且有利于我们对维生素的吸收，能有效的提高我们的免疫力。\r\n\r\n2.可以补充矿物质\r\n\r\n蔬菜中富含有钙、铁、铜等矿物质，这些物质能有效调节人体酸碱平衡，其中钙元素更是能促进人体生长，适合多吃。\r\n\r\n3.可以补充纤维素\r\n\r\n蔬菜中也含有大量的纤维素，纤维素能刺激胃液分泌和肠道蠕动，增加食物与消化液的接触面积，有助于人体消化吸收食物，促进代谢废物排出，并防止便秘。\r\n\r\n扩展资料：\r\n\r\n吃蔬菜的禁忌：\r\n1、餐前吃西红柿\r\n\r\n我们在吃完饭之后，一定要注意不要立刻使用西红柿，因为西红柿中含有很多的胃酸，这种物质与食物相混合之后会降低十五中的酸度，严重的话有可能会造成胃扩张。\r\n\r\n2、吃未炒熟的豆芽菜\r\n\r\n豆芽这种蔬菜在食用前一定要炒熟，因为不熟的豆芽牙尖上会有一些有毒物质，进入人体之后，有可能造成人体中毒。豆还会出现恶心、呕吐、腹泻、头晕等不适反应。\r\n\r\n3、频繁吃菠菜\r\n\r\n因为菠菜中含有大量的草酸，我们食用过量的话，这些草酸会严重影响我们对营养的吸收，还会造成人体内酸过量，不宜多吃，以免造成更严重的问题。\r\n参考资料：人民网：多吃蔬菜会带来哪些好处？', '2019-03-09', 2);
INSERT INTO `news` VALUES (17, '最新消息', '关于合理膳食的最新建议-1', '合理膳食也叫平衡膳食，是指按照不同年龄、身体活动和能量的需要设置的膳食模式，这个模式推荐的食物种类、数量和比例，能最大程度地满足不同年龄阶段、不同能量水平的健康人群的营养与健康需要。\r\n\r\n中国居民膳食指南\r\n\r\n一 食物多样，谷类为主\r\n\r\n二 吃动平衡，健康体重\r\n\r\n三 多吃蔬果、奶类、大豆\r\n\r\n四 适量吃鱼、禽、蛋、瘦肉\r\n\r\n五 少盐少油，控糖限酒\r\n\r\n六 杜绝浪费，兴新食尚\r\n\r\n01\r\n\r\n食物多样，谷类为主\r\n\r\n每天的膳食应包括谷薯类、蔬菜水果类、畜禽鱼蛋奶类、大豆坚果类等食物。建议平均每天摄入12种以上食物，每周25种以上。谷类为主是平衡膳食模式的重要特征，每天摄入谷薯类食物250-400克，其中全谷物和杂豆类50-150克，薯类50-100克；膳食中碳水化合物提供的能量应占总能量的50%以上。\r\n\r\n02\r\n\r\n吃动平衡，健康体重\r\n\r\n各个年龄段人群都应该坚持天天运动、维持能量平衡、保持健康体重。体重过低和过高均易增加疾病的发生风险。推荐每周应至少进行5天中等强度身体活动，累计150分钟以上；坚持日常身体活动，平均每天主动身体活动6000步；尽量减少久坐时间，每小时起来动一动，动则有益。\r\n\r\n03\r\n\r\n多吃蔬果、奶类、大豆\r\n\r\n蔬菜、水果、奶类和大豆及制品是平衡膳食的重要组成部分，坚果是膳食的有益补充。蔬菜和水果是维生素、矿物质、膳食纤维和植物化学物的重要来源，奶类和大豆类富含钙、优质蛋白质和B族维生素，对降低慢性病的发病风险具有重要作用。提倡餐餐有蔬菜，推荐每天摄入300-500克，深色蔬菜应占1/2。天天吃水果，推荐每天摄入200-350克的新鲜水果，果汁不能代替鲜果。吃各种奶制品，摄入量相当于每天液态奶300克。经常吃豆制品，每天相当于大豆25 克以上，适量吃坚果。\r\n\r\n04\r\n\r\n适量吃鱼、禽、蛋、瘦肉\r\n\r\n鱼、禽、蛋和瘦肉可提供人体所需要的优质蛋白质、维生素A、B族维生素等，有些也含有较高的脂肪和胆固醇。动物性食物优选鱼和禽类，鱼和禽类脂肪含量相对较低，鱼类含有较多的不饱和脂肪酸；蛋类各种营养成分齐全；吃畜肉应选择瘦肉，瘦肉脂肪含量较低。过多食用烟熏和腌制肉类可增加肿瘤的发生风险，应当少吃。推荐每周吃鱼280-525克，畜禽肉280-525克，蛋类280-350克，平均每天摄入鱼、禽、蛋和瘦肉总量120-200克。\r\n\r\n\r\n\r\n', '2019-03-10', 0);
INSERT INTO `news` VALUES (18, '最新消息', '关于合理膳食的最新建议-2', '合理膳食也叫平衡膳食，是指按照不同年龄、身体活动和能量的需要设置的膳食模式，这个模式推荐的食物种类、数量和比例，能最大程度地满足不同年龄阶段、不同能量水平的健康人群的营养与健康需要。\r\n\r\n中国居民膳食指南\r\n\r\n一 食物多样，谷类为主\r\n\r\n二 吃动平衡，健康体重\r\n\r\n三 多吃蔬果、奶类、大豆\r\n\r\n四 适量吃鱼、禽、蛋、瘦肉\r\n\r\n五 少盐少油，控糖限酒\r\n\r\n六 杜绝浪费，兴新食尚\r\n\r\n01\r\n\r\n食物多样，谷类为主\r\n\r\n每天的膳食应包括谷薯类、蔬菜水果类、畜禽鱼蛋奶类、大豆坚果类等食物。建议平均每天摄入12种以上食物，每周25种以上。谷类为主是平衡膳食模式的重要特征，每天摄入谷薯类食物250-400克，其中全谷物和杂豆类50-150克，薯类50-100克；膳食中碳水化合物提供的能量应占总能量的50%以上。\r\n\r\n02\r\n\r\n吃动平衡，健康体重\r\n\r\n各个年龄段人群都应该坚持天天运动、维持能量平衡、保持健康体重。体重过低和过高均易增加疾病的发生风险。推荐每周应至少进行5天中等强度身体活动，累计150分钟以上；坚持日常身体活动，平均每天主动身体活动6000步；尽量减少久坐时间，每小时起来动一动，动则有益。\r\n\r\n03\r\n\r\n多吃蔬果、奶类、大豆\r\n\r\n蔬菜、水果、奶类和大豆及制品是平衡膳食的重要组成部分，坚果是膳食的有益补充。蔬菜和水果是维生素、矿物质、膳食纤维和植物化学物的重要来源，奶类和大豆类富含钙、优质蛋白质和B族维生素，对降低慢性病的发病风险具有重要作用。提倡餐餐有蔬菜，推荐每天摄入300-500克，深色蔬菜应占1/2。天天吃水果，推荐每天摄入200-350克的新鲜水果，果汁不能代替鲜果。吃各种奶制品，摄入量相当于每天液态奶300克。经常吃豆制品，每天相当于大豆25 克以上，适量吃坚果。\r\n\r\n04\r\n\r\n适量吃鱼、禽、蛋、瘦肉\r\n\r\n鱼、禽、蛋和瘦肉可提供人体所需要的优质蛋白质、维生素A、B族维生素等，有些也含有较高的脂肪和胆固醇。动物性食物优选鱼和禽类，鱼和禽类脂肪含量相对较低，鱼类含有较多的不饱和脂肪酸；蛋类各种营养成分齐全；吃畜肉应选择瘦肉，瘦肉脂肪含量较低。过多食用烟熏和腌制肉类可增加肿瘤的发生风险，应当少吃。推荐每周吃鱼280-525克，畜禽肉280-525克，蛋类280-350克，平均每天摄入鱼、禽、蛋和瘦肉总量120-200克。\r\n\r\n\r\n\r\n', '2019-03-10', 0);
INSERT INTO `news` VALUES (19, '最新消息', '关于合理膳食的最新建议-3', '合理膳食也叫平衡膳食，是指按照不同年龄、身体活动和能量的需要设置的膳食模式，这个模式推荐的食物种类、数量和比例，能最大程度地满足不同年龄阶段、不同能量水平的健康人群的营养与健康需要。\r\n\r\n中国居民膳食指南\r\n\r\n一 食物多样，谷类为主\r\n\r\n二 吃动平衡，健康体重\r\n\r\n三 多吃蔬果、奶类、大豆\r\n\r\n四 适量吃鱼、禽、蛋、瘦肉\r\n\r\n五 少盐少油，控糖限酒\r\n\r\n六 杜绝浪费，兴新食尚\r\n\r\n01\r\n\r\n食物多样，谷类为主\r\n\r\n每天的膳食应包括谷薯类、蔬菜水果类、畜禽鱼蛋奶类、大豆坚果类等食物。建议平均每天摄入12种以上食物，每周25种以上。谷类为主是平衡膳食模式的重要特征，每天摄入谷薯类食物250-400克，其中全谷物和杂豆类50-150克，薯类50-100克；膳食中碳水化合物提供的能量应占总能量的50%以上。\r\n\r\n02\r\n\r\n吃动平衡，健康体重\r\n\r\n各个年龄段人群都应该坚持天天运动、维持能量平衡、保持健康体重。体重过低和过高均易增加疾病的发生风险。推荐每周应至少进行5天中等强度身体活动，累计150分钟以上；坚持日常身体活动，平均每天主动身体活动6000步；尽量减少久坐时间，每小时起来动一动，动则有益。\r\n\r\n03\r\n\r\n多吃蔬果、奶类、大豆\r\n\r\n蔬菜、水果、奶类和大豆及制品是平衡膳食的重要组成部分，坚果是膳食的有益补充。蔬菜和水果是维生素、矿物质、膳食纤维和植物化学物的重要来源，奶类和大豆类富含钙、优质蛋白质和B族维生素，对降低慢性病的发病风险具有重要作用。提倡餐餐有蔬菜，推荐每天摄入300-500克，深色蔬菜应占1/2。天天吃水果，推荐每天摄入200-350克的新鲜水果，果汁不能代替鲜果。吃各种奶制品，摄入量相当于每天液态奶300克。经常吃豆制品，每天相当于大豆25 克以上，适量吃坚果。\r\n\r\n04\r\n\r\n适量吃鱼、禽、蛋、瘦肉\r\n\r\n鱼、禽、蛋和瘦肉可提供人体所需要的优质蛋白质、维生素A、B族维生素等，有些也含有较高的脂肪和胆固醇。动物性食物优选鱼和禽类，鱼和禽类脂肪含量相对较低，鱼类含有较多的不饱和脂肪酸；蛋类各种营养成分齐全；吃畜肉应选择瘦肉，瘦肉脂肪含量较低。过多食用烟熏和腌制肉类可增加肿瘤的发生风险，应当少吃。推荐每周吃鱼280-525克，畜禽肉280-525克，蛋类280-350克，平均每天摄入鱼、禽、蛋和瘦肉总量120-200克。\r\n\r\n合理膳食也叫平衡膳食，是指按照不同年龄、身体活动和能量的需要设置的膳食模式，这个模式推荐的食物种类、数量和比例，能最大程度地满足不同年龄阶段、不同能量水平的健康人群的营养与健康需要。\r\n\r\n中国居民膳食指南\r\n\r\n一 食物多样，谷类为主\r\n\r\n二 吃动平衡，健康体重\r\n\r\n三 多吃蔬果、奶类、大豆\r\n\r\n四 适量吃鱼、禽、蛋、瘦肉\r\n\r\n五 少盐少油，控糖限酒\r\n\r\n六 杜绝浪费，兴新食尚\r\n\r\n01\r\n\r\n食物多样，谷类为主\r\n\r\n每天的膳食应包括谷薯类、蔬菜水果类、畜禽鱼蛋奶类、大豆坚果类等食物。建议平均每天摄入12种以上食物，每周25种以上。谷类为主是平衡膳食模式的重要特征，每天摄入谷薯类食物250-400克，其中全谷物和杂豆类50-150克，薯类50-100克；膳食中碳水化合物提供的能量应占总能量的50%以上。\r\n\r\n02\r\n\r\n吃动平衡，健康体重\r\n\r\n各个年龄段人群都应该坚持天天运动、维持能量平衡、保持健康体重。体重过低和过高均易增加疾病的发生风险。推荐每周应至少进行5天中等强度身体活动，累计150分钟以上；坚持日常身体活动，平均每天主动身体活动6000步；尽量减少久坐时间，每小时起来动一动，动则有益。\r\n\r\n03\r\n\r\n多吃蔬果、奶类、大豆\r\n\r\n蔬菜、水果、奶类和大豆及制品是平衡膳食的重要组成部分，坚果是膳食的有益补充。蔬菜和水果是维生素、矿物质、膳食纤维和植物化学物的重要来源，奶类和大豆类富含钙、优质蛋白质和B族维生素，对降低慢性病的发病风险具有重要作用。提倡餐餐有蔬菜，推荐每天摄入300-500克，深色蔬菜应占1/2。天天吃水果，推荐每天摄入200-350克的新鲜水果，果汁不能代替鲜果。吃各种奶制品，摄入量相当于每天液态奶300克。经常吃豆制品，每天相当于大豆25 克以上，适量吃坚果。\r\n\r\n04\r\n\r\n适量吃鱼、禽、蛋、瘦肉\r\n\r\n鱼、禽、蛋和瘦肉可提供人体所需要的优质蛋白质、维生素A、B族维生素等，有些也含有较高的脂肪和胆固醇。动物性食物优选鱼和禽类，鱼和禽类脂肪含量相对较低，鱼类含有较多的不饱和脂肪酸；蛋类各种营养成分齐全；吃畜肉应选择瘦肉，瘦肉脂肪含量较低。过多食用烟熏和腌制肉类可增加肿瘤的发生风险，应当少吃。推荐每周吃鱼280-525克，畜禽肉280-525克，蛋类280-350克，平均每天摄入鱼、禽、蛋和瘦肉总量120-200克。\r\n\r\n\r\n合理膳食也叫平衡膳食，是指按照不同年龄、身体活动和能量的需要设置的膳食模式，这个模式推荐的食物种类、数量和比例，能最大程度地满足不同年龄阶段、不同能量水平的健康人群的营养与健康需要。\r\n\r\n中国居民膳食指南\r\n\r\n一 食物多样，谷类为主\r\n\r\n二 吃动平衡，健康体重\r\n\r\n三 多吃蔬果、奶类、大豆\r\n\r\n四 适量吃鱼、禽、蛋、瘦肉\r\n\r\n五 少盐少油，控糖限酒\r\n\r\n六 杜绝浪费，兴新食尚\r\n\r\n01\r\n\r\n食物多样，谷类为主\r\n\r\n每天的膳食应包括谷薯类、蔬菜水果类、畜禽鱼蛋奶类、大豆坚果类等食物。建议平均每天摄入12种以上食物，每周25种以上。谷类为主是平衡膳食模式的重要特征，每天摄入谷薯类食物250-400克，其中全谷物和杂豆类50-150克，薯类50-100克；膳食中碳水化合物提供的能量应占总能量的50%以上。\r\n\r\n02\r\n\r\n吃动平衡，健康体重\r\n\r\n各个年龄段人群都应该坚持天天运动、维持能量平衡、保持健康体重。体重过低和过高均易增加疾病的发生风险。推荐每周应至少进行5天中等强度身体活动，累计150分钟以上；坚持日常身体活动，平均每天主动身体活动6000步；尽量减少久坐时间，每小时起来动一动，动则有益。\r\n\r\n03\r\n\r\n多吃蔬果、奶类、大豆\r\n\r\n蔬菜、水果、奶类和大豆及制品是平衡膳食的重要组成部分，坚果是膳食的有益补充。蔬菜和水果是维生素、矿物质、膳食纤维和植物化学物的重要来源，奶类和大豆类富含钙、优质蛋白质和B族维生素，对降低慢性病的发病风险具有重要作用。提倡餐餐有蔬菜，推荐每天摄入300-500克，深色蔬菜应占1/2。天天吃水果，推荐每天摄入200-350克的新鲜水果，果汁不能代替鲜果。吃各种奶制品，摄入量相当于每天液态奶300克。经常吃豆制品，每天相当于大豆25 克以上，适量吃坚果。\r\n\r\n04\r\n\r\n适量吃鱼、禽、蛋、瘦肉\r\n\r\n鱼、禽、蛋和瘦肉可提供人体所需要的优质蛋白质、维生素A、B族维生素等，有些也含有较高的脂肪和胆固醇。动物性食物优选鱼和禽类，鱼和禽类脂肪含量相对较低，鱼类含有较多的不饱和脂肪酸；蛋类各种营养成分齐全；吃畜肉应选择瘦肉，瘦肉脂肪含量较低。过多食用烟熏和腌制肉类可增加肿瘤的发生风险，应当少吃。推荐每周吃鱼280-525克，畜禽肉280-525克，蛋类280-350克，平均每天摄入鱼、禽、蛋和瘦肉总量120-200克。\r\n\r\n\r\n\r\n合理膳食也叫平衡膳食，是指按照不同年龄、身体活动和能量的需要设置的膳食模式，这个模式推荐的食物种类、数量和比例，能最大程度地满足不同年龄阶段、不同能量水平的健康人群的营养与健康需要。\r\n\r\n中国居民膳食指南\r\n\r\n一 食物多样，谷类为主\r\n\r\n二 吃动平衡，健康体重\r\n\r\n三 多吃蔬果、奶类、大豆\r\n\r\n四 适量吃鱼、禽、蛋、瘦肉\r\n\r\n五 少盐少油，控糖限酒\r\n\r\n六 杜绝浪费，兴新食尚\r\n\r\n01\r\n\r\n食物多样，谷类为主\r\n\r\n每天的膳食应包括谷薯类、蔬菜水果类、畜禽鱼蛋奶类、大豆坚果类等食物。建议平均每天摄入12种以上食物，每周25种以上。谷类为主是平衡膳食模式的重要特征，每天摄入谷薯类食物250-400克，其中全谷物和杂豆类50-150克，薯类50-100克；膳食中碳水化合物提供的能量应占总能量的50%以上。\r\n\r\n02\r\n\r\n吃动平衡，健康体重\r\n\r\n各个年龄段人群都应该坚持天天运动、维持能量平衡、保持健康体重。体重过低和过高均易增加疾病的发生风险。推荐每周应至少进行5天中等强度身体活动，累计150分钟以上；坚持日常身体活动，平均每天主动身体活动6000步；尽量减少久坐时间，每小时起来动一动，动则有益。\r\n\r\n03\r\n\r\n多吃蔬果、奶类、大豆\r\n\r\n蔬菜、水果、奶类和大豆及制品是平衡膳食的重要组成部分，坚果是膳食的有益补充。蔬菜和水果是维生素、矿物质、膳食纤维和植物化学物的重要来源，奶类和大豆类富含钙、优质蛋白质和B族维生素，对降低慢性病的发病风险具有重要作用。提倡餐餐有蔬菜，推荐每天摄入300-500克，深色蔬菜应占1/2。天天吃水果，推荐每天摄入200-350克的新鲜水果，果汁不能代替鲜果。吃各种奶制品，摄入量相当于每天液态奶300克。经常吃豆制品，每天相当于大豆25 克以上，适量吃坚果。\r\n\r\n04\r\n\r\n适量吃鱼、禽、蛋、瘦肉\r\n\r\n鱼、禽、蛋和瘦肉可提供人体所需要的优质蛋白质、维生素A、B族维生素等，有些也含有较高的脂肪和胆固醇。动物性食物优选鱼和禽类，鱼和禽类脂肪含量相对较低，鱼类含有较多的不饱和脂肪酸；蛋类各种营养成分齐全；吃畜肉应选择瘦肉，瘦肉脂肪含量较低。过多食用烟熏和腌制肉类可增加肿瘤的发生风险，应当少吃。推荐每周吃鱼280-525克，畜禽肉280-525克，蛋类280-350克，平均每天摄入鱼、禽、蛋和瘦肉总量120-200克。\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n', '2019-03-10', 0);
INSERT INTO `news` VALUES (20, '最新消息', '关于合理膳食的最新建议-4', '合理膳食也叫平衡膳食，是指按照不同年龄、身体活动和能量的需要设置的膳食模式，这个模式推荐的食物种类、数量和比例，能最大程度地满足不同年龄阶段、不同能量水平的健康人群的营养与健康需要。\r\n\r\n中国居民膳食指南\r\n\r\n一 食物多样，谷类为主\r\n\r\n二 吃动平衡，健康体重\r\n\r\n三 多吃蔬果、奶类、大豆\r\n\r\n四 适量吃鱼、禽、蛋、瘦肉\r\n\r\n五 少盐少油，控糖限酒\r\n\r\n六 杜绝浪费，兴新食尚\r\n\r\n01\r\n\r\n食物多样，谷类为主\r\n\r\n每天的膳食应包括谷薯类、蔬菜水果类、畜禽鱼蛋奶类、大豆坚果类等食物。建议平均每天摄入12种以上食物，每周25种以上。谷类为主是平衡膳食模式的重要特征，每天摄入谷薯类食物250-400克，其中全谷物和杂豆类50-150克，薯类50-100克；膳食中碳水化合物提供的能量应占总能量的50%以上。\r\n\r\n02\r\n\r\n吃动平衡，健康体重\r\n\r\n各个年龄段人群都应该坚持天天运动、维持能量平衡、保持健康体重。体重过低和过高均易增加疾病的发生风险。推荐每周应至少进行5天中等强度身体活动，累计150分钟以上；坚持日常身体活动，平均每天主动身体活动6000步；尽量减少久坐时间，每小时起来动一动，动则有益。\r\n\r\n03\r\n\r\n多吃蔬果、奶类、大豆\r\n\r\n蔬菜、水果、奶类和大豆及制品是平衡膳食的重要组成部分，坚果是膳食的有益补充。蔬菜和水果是维生素、矿物质、膳食纤维和植物化学物的重要来源，奶类和大豆类富含钙、优质蛋白质和B族维生素，对降低慢性病的发病风险具有重要作用。提倡餐餐有蔬菜，推荐每天摄入300-500克，深色蔬菜应占1/2。天天吃水果，推荐每天摄入200-350克的新鲜水果，果汁不能代替鲜果。吃各种奶制品，摄入量相当于每天液态奶300克。经常吃豆制品，每天相当于大豆25 克以上，适量吃坚果。\r\n\r\n04\r\n\r\n适量吃鱼、禽、蛋、瘦肉\r\n\r\n鱼、禽、蛋和瘦肉可提供人体所需要的优质蛋白质、维生素A、B族维生素等，有些也含有较高的脂肪和胆固醇。动物性食物优选鱼和禽类，鱼和禽类脂肪含量相对较低，鱼类含有较多的不饱和脂肪酸；蛋类各种营养成分齐全；吃畜肉应选择瘦肉，瘦肉脂肪含量较低。过多食用烟熏和腌制肉类可增加肿瘤的发生风险，应当少吃。推荐每周吃鱼280-525克，畜禽肉280-525克，蛋类280-350克，平均每天摄入鱼、禽、蛋和瘦肉总量120-200克。\r\n\r\n\r\n\r\n', '2019-03-10', 0);
INSERT INTO `news` VALUES (21, '最新消息', '关于合理膳食的最新建议-5', '合理膳食也叫平衡膳食，是指按照不同年龄、身体活动和能量的需要设置的膳食模式，这个模式推荐的食物种类、数量和比例，能最大程度地满足不同年龄阶段、不同能量水平的健康人群的营养与健康需要。\r\n\r\n中国居民膳食指南\r\n\r\n一 食物多样，谷类为主\r\n\r\n二 吃动平衡，健康体重\r\n\r\n三 多吃蔬果、奶类、大豆\r\n\r\n四 适量吃鱼、禽、蛋、瘦肉\r\n\r\n五 少盐少油，控糖限酒\r\n\r\n六 杜绝浪费，兴新食尚\r\n\r\n01\r\n\r\n食物多样，谷类为主\r\n\r\n每天的膳食应包括谷薯类、蔬菜水果类、畜禽鱼蛋奶类、大豆坚果类等食物。建议平均每天摄入12种以上食物，每周25种以上。谷类为主是平衡膳食模式的重要特征，每天摄入谷薯类食物250-400克，其中全谷物和杂豆类50-150克，薯类50-100克；膳食中碳水化合物提供的能量应占总能量的50%以上。\r\n\r\n02\r\n\r\n吃动平衡，健康体重\r\n\r\n各个年龄段人群都应该坚持天天运动、维持能量平衡、保持健康体重。体重过低和过高均易增加疾病的发生风险。推荐每周应至少进行5天中等强度身体活动，累计150分钟以上；坚持日常身体活动，平均每天主动身体活动6000步；尽量减少久坐时间，每小时起来动一动，动则有益。\r\n\r\n03\r\n\r\n多吃蔬果、奶类、大豆\r\n\r\n蔬菜、水果、奶类和大豆及制品是平衡膳食的重要组成部分，坚果是膳食的有益补充。蔬菜和水果是维生素、矿物质、膳食纤维和植物化学物的重要来源，奶类和大豆类富含钙、优质蛋白质和B族维生素，对降低慢性病的发病风险具有重要作用。提倡餐餐有蔬菜，推荐每天摄入300-500克，深色蔬菜应占1/2。天天吃水果，推荐每天摄入200-350克的新鲜水果，果汁不能代替鲜果。吃各种奶制品，摄入量相当于每天液态奶300克。经常吃豆制品，每天相当于大豆25 克以上，适量吃坚果。\r\n\r\n04\r\n\r\n适量吃鱼、禽、蛋、瘦肉\r\n\r\n鱼、禽、蛋和瘦肉可提供人体所需要的优质蛋白质、维生素A、B族维生素等，有些也含有较高的脂肪和胆固醇。动物性食物优选鱼和禽类，鱼和禽类脂肪含量相对较低，鱼类含有较多的不饱和脂肪酸；蛋类各种营养成分齐全；吃畜肉应选择瘦肉，瘦肉脂肪含量较低。过多食用烟熏和腌制肉类可增加肿瘤的发生风险，应当少吃。推荐每周吃鱼280-525克，畜禽肉280-525克，蛋类280-350克，平均每天摄入鱼、禽、蛋和瘦肉总量120-200克。\r\n\r\n\r\n\r\n', '2019-03-10', 2);
INSERT INTO `news` VALUES (22, '最新消息', '关于合理膳食的最新建议-6', '合理膳食也叫平衡膳食，是指按照不同年龄、身体活动和能量的需要设置的膳食模式，这个模式推荐的食物种类、数量和比例，能最大程度地满足不同年龄阶段、不同能量水平的健康人群的营养与健康需要。\r\n\r\n中国居民膳食指南\r\n\r\n一 食物多样，谷类为主\r\n\r\n二 吃动平衡，健康体重\r\n\r\n三 多吃蔬果、奶类、大豆\r\n\r\n四 适量吃鱼、禽、蛋、瘦肉\r\n\r\n五 少盐少油，控糖限酒\r\n\r\n六 杜绝浪费，兴新食尚\r\n\r\n01\r\n\r\n食物多样，谷类为主\r\n\r\n每天的膳食应包括谷薯类、蔬菜水果类、畜禽鱼蛋奶类、大豆坚果类等食物。建议平均每天摄入12种以上食物，每周25种以上。谷类为主是平衡膳食模式的重要特征，每天摄入谷薯类食物250-400克，其中全谷物和杂豆类50-150克，薯类50-100克；膳食中碳水化合物提供的能量应占总能量的50%以上。\r\n\r\n02\r\n\r\n吃动平衡，健康体重\r\n\r\n各个年龄段人群都应该坚持天天运动、维持能量平衡、保持健康体重。体重过低和过高均易增加疾病的发生风险。推荐每周应至少进行5天中等强度身体活动，累计150分钟以上；坚持日常身体活动，平均每天主动身体活动6000步；尽量减少久坐时间，每小时起来动一动，动则有益。\r\n\r\n03\r\n\r\n多吃蔬果、奶类、大豆\r\n\r\n蔬菜、水果、奶类和大豆及制品是平衡膳食的重要组成部分，坚果是膳食的有益补充。蔬菜和水果是维生素、矿物质、膳食纤维和植物化学物的重要来源，奶类和大豆类富含钙、优质蛋白质和B族维生素，对降低慢性病的发病风险具有重要作用。提倡餐餐有蔬菜，推荐每天摄入300-500克，深色蔬菜应占1/2。天天吃水果，推荐每天摄入200-350克的新鲜水果，果汁不能代替鲜果。吃各种奶制品，摄入量相当于每天液态奶300克。经常吃豆制品，每天相当于大豆25 克以上，适量吃坚果。\r\n\r\n04\r\n\r\n适量吃鱼、禽、蛋、瘦肉\r\n\r\n鱼、禽、蛋和瘦肉可提供人体所需要的优质蛋白质、维生素A、B族维生素等，有些也含有较高的脂肪和胆固醇。动物性食物优选鱼和禽类，鱼和禽类脂肪含量相对较低，鱼类含有较多的不饱和脂肪酸；蛋类各种营养成分齐全；吃畜肉应选择瘦肉，瘦肉脂肪含量较低。过多食用烟熏和腌制肉类可增加肿瘤的发生风险，应当少吃。推荐每周吃鱼280-525克，畜禽肉280-525克，蛋类280-350克，平均每天摄入鱼、禽、蛋和瘦肉总量120-200克。\r\n\r\n\r\n\r\n', '2019-03-10', 32);
INSERT INTO `news` VALUES (23, '最新消息', '关于合理膳食的最新建议-7', '合理膳食也叫平衡膳食，是指按照不同年龄、身体活动和能量的需要设置的膳食模式，这个模式推荐的食物种类、数量和比例，能最大程度地满足不同年龄阶段、不同能量水平的健康人群的营养与健康需要。\r\n\r\n中国居民膳食指南\r\n\r\n一 食物多样，谷类为主\r\n\r\n二 吃动平衡，健康体重\r\n\r\n三 多吃蔬果、奶类、大豆\r\n\r\n四 适量吃鱼、禽、蛋、瘦肉\r\n\r\n五 少盐少油，控糖限酒\r\n\r\n六 杜绝浪费，兴新食尚\r\n\r\n01\r\n\r\n食物多样，谷类为主\r\n\r\n每天的膳食应包括谷薯类、蔬菜水果类、畜禽鱼蛋奶类、大豆坚果类等食物。建议平均每天摄入12种以上食物，每周25种以上。谷类为主是平衡膳食模式的重要特征，每天摄入谷薯类食物250-400克，其中全谷物和杂豆类50-150克，薯类50-100克；膳食中碳水化合物提供的能量应占总能量的50%以上。\r\n\r\n02\r\n\r\n吃动平衡，健康体重\r\n\r\n各个年龄段人群都应该坚持天天运动、维持能量平衡、保持健康体重。体重过低和过高均易增加疾病的发生风险。推荐每周应至少进行5天中等强度身体活动，累计150分钟以上；坚持日常身体活动，平均每天主动身体活动6000步；尽量减少久坐时间，每小时起来动一动，动则有益。\r\n\r\n03\r\n\r\n多吃蔬果、奶类、大豆\r\n\r\n蔬菜、水果、奶类和大豆及制品是平衡膳食的重要组成部分，坚果是膳食的有益补充。蔬菜和水果是维生素、矿物质、膳食纤维和植物化学物的重要来源，奶类和大豆类富含钙、优质蛋白质和B族维生素，对降低慢性病的发病风险具有重要作用。提倡餐餐有蔬菜，推荐每天摄入300-500克，深色蔬菜应占1/2。天天吃水果，推荐每天摄入200-350克的新鲜水果，果汁不能代替鲜果。吃各种奶制品，摄入量相当于每天液态奶300克。经常吃豆制品，每天相当于大豆25 克以上，适量吃坚果。\r\n\r\n04\r\n\r\n适量吃鱼、禽、蛋、瘦肉\r\n\r\n鱼、禽、蛋和瘦肉可提供人体所需要的优质蛋白质、维生素A、B族维生素等，有些也含有较高的脂肪和胆固醇。动物性食物优选鱼和禽类，鱼和禽类脂肪含量相对较低，鱼类含有较多的不饱和脂肪酸；蛋类各种营养成分齐全；吃畜肉应选择瘦肉，瘦肉脂肪含量较低。过多食用烟熏和腌制肉类可增加肿瘤的发生风险，应当少吃。推荐每周吃鱼280-525克，畜禽肉280-525克，蛋类280-350克，平均每天摄入鱼、禽、蛋和瘦肉总量120-200克。\r\n\r\n\r\n合理膳食也叫平衡膳食，是指按照不同年龄、身体活动和能量的需要设置的膳食模式，这个模式推荐的食物种类、数量和比例，能最大程度地满足不同年龄阶段、不同能量水平的健康人群的营养与健康需要。\r\n\r\n中国居民膳食指南\r\n\r\n一 食物多样，谷类为主\r\n\r\n二 吃动平衡，健康体重\r\n\r\n三 多吃蔬果、奶类、大豆\r\n\r\n四 适量吃鱼、禽、蛋、瘦肉\r\n\r\n五 少盐少油，控糖限酒\r\n\r\n六 杜绝浪费，兴新食尚\r\n\r\n01\r\n\r\n食物多样，谷类为主\r\n\r\n每天的膳食应包括谷薯类、蔬菜水果类、畜禽鱼蛋奶类、大豆坚果类等食物。建议平均每天摄入12种以上食物，每周25种以上。谷类为主是平衡膳食模式的重要特征，每天摄入谷薯类食物250-400克，其中全谷物和杂豆类50-150克，薯类50-100克；膳食中碳水化合物提供的能量应占总能量的50%以上。\r\n\r\n02\r\n\r\n吃动平衡，健康体重\r\n\r\n各个年龄段人群都应该坚持天天运动、维持能量平衡、保持健康体重。体重过低和过高均易增加疾病的发生风险。推荐每周应至少进行5天中等强度身体活动，累计150分钟以上；坚持日常身体活动，平均每天主动身体活动6000步；尽量减少久坐时间，每小时起来动一动，动则有益。\r\n\r\n03\r\n\r\n多吃蔬果、奶类、大豆\r\n\r\n蔬菜、水果、奶类和大豆及制品是平衡膳食的重要组成部分，坚果是膳食的有益补充。蔬菜和水果是维生素、矿物质、膳食纤维和植物化学物的重要来源，奶类和大豆类富含钙、优质蛋白质和B族维生素，对降低慢性病的发病风险具有重要作用。提倡餐餐有蔬菜，推荐每天摄入300-500克，深色蔬菜应占1/2。天天吃水果，推荐每天摄入200-350克的新鲜水果，果汁不能代替鲜果。吃各种奶制品，摄入量相当于每天液态奶300克。经常吃豆制品，每天相当于大豆25 克以上，适量吃坚果。\r\n\r\n04\r\n\r\n适量吃鱼、禽、蛋、瘦肉\r\n\r\n鱼、禽、蛋和瘦肉可提供人体所需要的优质蛋白质、维生素A、B族维生素等，有些也含有较高的脂肪和胆固醇。动物性食物优选鱼和禽类，鱼和禽类脂肪含量相对较低，鱼类含有较多的不饱和脂肪酸；蛋类各种营养成分齐全；吃畜肉应选择瘦肉，瘦肉脂肪含量较低。过多食用烟熏和腌制肉类可增加肿瘤的发生风险，应当少吃。推荐每周吃鱼280-525克，畜禽肉280-525克，蛋类280-350克，平均每天摄入鱼、禽、蛋和瘦肉总量120-200克。\r\n\r\n\r\n合理膳食也叫平衡膳食，是指按照不同年龄、身体活动和能量的需要设置的膳食模式，这个模式推荐的食物种类、数量和比例，能最大程度地满足不同年龄阶段、不同能量水平的健康人群的营养与健康需要。\r\n\r\n中国居民膳食指南\r\n\r\n一 食物多样，谷类为主\r\n\r\n二 吃动平衡，健康体重\r\n\r\n三 多吃蔬果、奶类、大豆\r\n\r\n四 适量吃鱼、禽、蛋、瘦肉\r\n\r\n五 少盐少油，控糖限酒\r\n\r\n六 杜绝浪费，兴新食尚\r\n\r\n01\r\n\r\n食物多样，谷类为主\r\n\r\n每天的膳食应包括谷薯类、蔬菜水果类、畜禽鱼蛋奶类、大豆坚果类等食物。建议平均每天摄入12种以上食物，每周25种以上。谷类为主是平衡膳食模式的重要特征，每天摄入谷薯类食物250-400克，其中全谷物和杂豆类50-150克，薯类50-100克；膳食中碳水化合物提供的能量应占总能量的50%以上。\r\n\r\n02\r\n\r\n吃动平衡，健康体重\r\n\r\n各个年龄段人群都应该坚持天天运动、维持能量平衡、保持健康体重。体重过低和过高均易增加疾病的发生风险。推荐每周应至少进行5天中等强度身体活动，累计150分钟以上；坚持日常身体活动，平均每天主动身体活动6000步；尽量减少久坐时间，每小时起来动一动，动则有益。\r\n\r\n03\r\n\r\n多吃蔬果、奶类、大豆\r\n\r\n蔬菜、水果、奶类和大豆及制品是平衡膳食的重要组成部分，坚果是膳食的有益补充。蔬菜和水果是维生素、矿物质、膳食纤维和植物化学物的重要来源，奶类和大豆类富含钙、优质蛋白质和B族维生素，对降低慢性病的发病风险具有重要作用。提倡餐餐有蔬菜，推荐每天摄入300-500克，深色蔬菜应占1/2。天天吃水果，推荐每天摄入200-350克的新鲜水果，果汁不能代替鲜果。吃各种奶制品，摄入量相当于每天液态奶300克。经常吃豆制品，每天相当于大豆25 克以上，适量吃坚果。\r\n\r\n04\r\n\r\n适量吃鱼、禽、蛋、瘦肉\r\n\r\n鱼、禽、蛋和瘦肉可提供人体所需要的优质蛋白质、维生素A、B族维生素等，有些也含有较高的脂肪和胆固醇。动物性食物优选鱼和禽类，鱼和禽类脂肪含量相对较低，鱼类含有较多的不饱和脂肪酸；蛋类各种营养成分齐全；吃畜肉应选择瘦肉，瘦肉脂肪含量较低。过多食用烟熏和腌制肉类可增加肿瘤的发生风险，应当少吃。推荐每周吃鱼280-525克，畜禽肉280-525克，蛋类280-350克，平均每天摄入鱼、禽、蛋和瘦肉总量120-200克。\r\n\r\n\r\n合理膳食也叫平衡膳食，是指按照不同年龄、身体活动和能量的需要设置的膳食模式，这个模式推荐的食物种类、数量和比例，能最大程度地满足不同年龄阶段、不同能量水平的健康人群的营养与健康需要。\r\n\r\n中国居民膳食指南\r\n\r\n一 食物多样，谷类为主\r\n\r\n二 吃动平衡，健康体重\r\n\r\n三 多吃蔬果、奶类、大豆\r\n\r\n四 适量吃鱼、禽、蛋、瘦肉\r\n\r\n五 少盐少油，控糖限酒\r\n\r\n六 杜绝浪费，兴新食尚\r\n\r\n01\r\n\r\n食物多样，谷类为主\r\n\r\n每天的膳食应包括谷薯类、蔬菜水果类、畜禽鱼蛋奶类、大豆坚果类等食物。建议平均每天摄入12种以上食物，每周25种以上。谷类为主是平衡膳食模式的重要特征，每天摄入谷薯类食物250-400克，其中全谷物和杂豆类50-150克，薯类50-100克；膳食中碳水化合物提供的能量应占总能量的50%以上。\r\n\r\n02\r\n\r\n吃动平衡，健康体重\r\n\r\n各个年龄段人群都应该坚持天天运动、维持能量平衡、保持健康体重。体重过低和过高均易增加疾病的发生风险。推荐每周应至少进行5天中等强度身体活动，累计150分钟以上；坚持日常身体活动，平均每天主动身体活动6000步；尽量减少久坐时间，每小时起来动一动，动则有益。\r\n\r\n03\r\n\r\n多吃蔬果、奶类、大豆\r\n\r\n蔬菜、水果、奶类和大豆及制品是平衡膳食的重要组成部分，坚果是膳食的有益补充。蔬菜和水果是维生素、矿物质、膳食纤维和植物化学物的重要来源，奶类和大豆类富含钙、优质蛋白质和B族维生素，对降低慢性病的发病风险具有重要作用。提倡餐餐有蔬菜，推荐每天摄入300-500克，深色蔬菜应占1/2。天天吃水果，推荐每天摄入200-350克的新鲜水果，果汁不能代替鲜果。吃各种奶制品，摄入量相当于每天液态奶300克。经常吃豆制品，每天相当于大豆25 克以上，适量吃坚果。\r\n\r\n\r\n\r\n', '2019-03-10', 0);
INSERT INTO `news` VALUES (24, '最新消息', '关于合理膳食的最新建议-8', '合理膳食也叫平衡膳食，是指按照不同年龄、身体活动和能量的需要设置的膳食模式，这个模式推荐的食物种类、数量和比例，能最大程度地满足不同年龄阶段、不同能量水平的健康人群的营养与健康需要。\r\n\r\n中国居民膳食指南\r\n\r\n一 食物多样，谷类为主\r\n\r\n二 吃动平衡，健康体重\r\n\r\n三 多吃蔬果、奶类、大豆\r\n\r\n四 适量吃鱼、禽、蛋、瘦肉\r\n\r\n五 少盐少油，控糖限酒\r\n\r\n六 杜绝浪费，兴新食尚\r\n\r\n01\r\n\r\n食物多样，谷类为主\r\n\r\n每天的膳食应包括谷薯类、蔬菜水果类、畜禽鱼蛋奶类、大豆坚果类等食物。建议平均每天摄入12种以上食物，每周25种以上。谷类为主是平衡膳食模式的重要特征，每天摄入谷薯类食物250-400克，其中全谷物和杂豆类50-150克，薯类50-100克；膳食中碳水化合物提供的能量应占总能量的50%以上。\r\n\r\n02\r\n\r\n吃动平衡，健康体重\r\n\r\n各个年龄段人群都应该坚持天天运动、维持能量平衡、保持健康体重。体重过低和过高均易增加疾病的发生风险。推荐每周应至少进行5天中等强度身体活动，累计150分钟以上；坚持日常身体活动，平均每天主动身体活动6000步；尽量减少久坐时间，每小时起来动一动，动则有益。\r\n\r\n03\r\n\r\n多吃蔬果、奶类、大豆\r\n\r\n蔬菜、水果、奶类和大豆及制品是平衡膳食的重要组成部分，坚果是膳食的有益补充。蔬菜和水果是维生素、矿物质、膳食纤维和植物化学物的重要来源，奶类和大豆类富含钙、优质蛋白质和B族维生素，对降低慢性病的发病风险具有重要作用。提倡餐餐有蔬菜，推荐每天摄入300-500克，深色蔬菜应占1/2。天天吃水果，推荐每天摄入200-350克的新鲜水果，果汁不能代替鲜果。吃各种奶制品，摄入量相当于每天液态奶300克。经常吃豆制品，每天相当于大豆25 克以上，适量吃坚果。\r\n\r\n04\r\n\r\n适量吃鱼、禽、蛋、瘦肉\r\n\r\n鱼、禽、蛋和瘦肉可提供人体所需要的优质蛋白质、维生素A、B族维生素等，有些也含有较高的脂肪和胆固醇。动物性食物优选鱼和禽类，鱼和禽类脂肪含量相对较低，鱼类含有较多的不饱和脂肪酸；蛋类各种营养成分齐全；吃畜肉应选择瘦肉，瘦肉脂肪含量较低。过多食用烟熏和腌制肉类可增加肿瘤的发生风险，应当少吃。推荐每周吃鱼280-525克，畜禽肉280-525克，蛋类280-350克，平均每天摄入鱼、禽、蛋和瘦肉总量120-200克。\r\n', '2019-03-10', 0);
INSERT INTO `news` VALUES (25, '最新消息', '关于合理膳食的最新建议-9', '合理膳食也叫平衡膳食，是指按照不同年龄、身体活动和能量的需要设置的膳食模式，这个模式推荐的食物种类、数量和比例，能最大程度地满足不同年龄阶段、不同能量水平的健康人群的营养与健康需要。\r\n\r\n中国居民膳食指南\r\n\r\n一 食物多样，谷类为主\r\n\r\n二 吃动平衡，健康体重\r\n\r\n三 多吃蔬果、奶类、大豆\r\n\r\n四 适量吃鱼、禽、蛋、瘦肉\r\n\r\n五 少盐少油，控糖限酒\r\n\r\n六 杜绝浪费，兴新食尚\r\n\r\n01\r\n\r\n食物多样，谷类为主\r\n\r\n每天的膳食应包括谷薯类、蔬菜水果类、畜禽鱼蛋奶类、大豆坚果类等食物。建议平均每天摄入12种以上食物，每周25种以上。谷类为主是平衡膳食模式的重要特征，每天摄入谷薯类食物250-400克，其中全谷物和杂豆类50-150克，薯类50-100克；膳食中碳水化合物提供的能量应占总能量的50%以上。\r\n\r\n02\r\n\r\n吃动平衡，健康体重\r\n\r\n各个年龄段人群都应该坚持天天运动、维持能量平衡、保持健康体重。体重过低和过高均易增加疾病的发生风险。推荐每周应至少进行5天中等强度身体活动，累计150分钟以上；坚持日常身体活动，平均每天主动身体活动6000步；尽量减少久坐时间，每小时起来动一动，动则有益。\r\n\r\n03\r\n\r\n多吃蔬果、奶类、大豆\r\n\r\n蔬菜、水果、奶类和大豆及制品是平衡膳食的重要组成部分，坚果是膳食的有益补充。蔬菜和水果是维生素、矿物质、膳食纤维和植物化学物的重要来源，奶类和大豆类富含钙、优质蛋白质和B族维生素，对降低慢性病的发病风险具有重要作用。提倡餐餐有蔬菜，推荐每天摄入300-500克，深色蔬菜应占1/2。天天吃水果，推荐每天摄入200-350克的新鲜水果，果汁不能代替鲜果。吃各种奶制品，摄入量相当于每天液态奶300克。经常吃豆制品，每天相当于大豆25 克以上，适量吃坚果。\r\n\r\n04\r\n\r\n适量吃鱼、禽、蛋、瘦肉\r\n\r\n鱼、禽、蛋和瘦肉可提供人体所需要的优质蛋白质、维生素A、B族维生素等，有些也含有较高的脂肪和胆固醇。动物性食物优选鱼和禽类，鱼和禽类脂肪含量相对较低，鱼类含有较多的不饱和脂肪酸；蛋类各种营养成分齐全；吃畜肉应选择瘦肉，瘦肉脂肪含量较低。过多食用烟熏和腌制肉类可增加肿瘤的发生风险，应当少吃。推荐每周吃鱼280-525克，畜禽肉280-525克，蛋类280-350克，平均每天摄入鱼、禽、蛋和瘦肉总量120-200克。\r\n', '2019-03-10', 0);
INSERT INTO `news` VALUES (26, '最新消息', '关于合理膳食的最新建议-10', '合理膳食也叫平衡膳食，是指按照不同年龄、身体活动和能量的需要设置的膳食模式，这个模式推荐的食物种类、数量和比例，能最大程度地满足不同年龄阶段、不同能量水平的健康人群的营养与健康需要。\r\n\r\n中国居民膳食指南\r\n\r\n一 食物多样，谷类为主\r\n\r\n二 吃动平衡，健康体重\r\n\r\n三 多吃蔬果、奶类、大豆\r\n\r\n四 适量吃鱼、禽、蛋、瘦肉\r\n\r\n五 少盐少油，控糖限酒\r\n\r\n六 杜绝浪费，兴新食尚\r\n\r\n01\r\n\r\n食物多样，谷类为主\r\n\r\n每天的膳食应包括谷薯类、蔬菜水果类、畜禽鱼蛋奶类、大豆坚果类等食物。建议平均每天摄入12种以上食物，每周25种以上。谷类为主是平衡膳食模式的重要特征，每天摄入谷薯类食物250-400克，其中全谷物和杂豆类50-150克，薯类50-100克；膳食中碳水化合物提供的能量应占总能量的50%以上。\r\n\r\n02\r\n\r\n吃动平衡，健康体重\r\n\r\n各个年龄段人群都应该坚持天天运动、维持能量平衡、保持健康体重。体重过低和过高均易增加疾病的发生风险。推荐每周应至少进行5天中等强度身体活动，累计150分钟以上；坚持日常身体活动，平均每天主动身体活动6000步；尽量减少久坐时间，每小时起来动一动，动则有益。\r\n\r\n03\r\n\r\n多吃蔬果、奶类、大豆\r\n\r\n蔬菜、水果、奶类和大豆及制品是平衡膳食的重要组成部分，坚果是膳食的有益补充。蔬菜和水果是维生素、矿物质、膳食纤维和植物化学物的重要来源，奶类和大豆类富含钙、优质蛋白质和B族维生素，对降低慢性病的发病风险具有重要作用。提倡餐餐有蔬菜，推荐每天摄入300-500克，深色蔬菜应占1/2。天天吃水果，推荐每天摄入200-350克的新鲜水果，果汁不能代替鲜果。吃各种奶制品，摄入量相当于每天液态奶300克。经常吃豆制品，每天相当于大豆25 克以上，适量吃坚果。\r\n\r\n04\r\n\r\n适量吃鱼、禽、蛋、瘦肉\r\n\r\n鱼、禽、蛋和瘦肉可提供人体所需要的优质蛋白质、维生素A、B族维生素等，有些也含有较高的脂肪和胆固醇。动物性食物优选鱼和禽类，鱼和禽类脂肪含量相对较低，鱼类含有较多的不饱和脂肪酸；蛋类各种营养成分齐全；吃畜肉应选择瘦肉，瘦肉脂肪含量较低。过多食用烟熏和腌制肉类可增加肿瘤的发生风险，应当少吃。推荐每周吃鱼280-525克，畜禽肉280-525克，蛋类280-350克，平均每天摄入鱼、禽、蛋和瘦肉总量120-200克。\r\n', '2019-03-10', 0);
INSERT INTO `news` VALUES (27, '最新消息', '关于合理膳食的最新建议-11', '合理膳食也叫平衡膳食，是指按照不同年龄、身体活动和能量的需要设置的膳食模式，这个模式推荐的食物种类、数量和比例，能最大程度地满足不同年龄阶段、不同能量水平的健康人群的营养与健康需要。\r\n\r\n中国居民膳食指南\r\n\r\n一 食物多样，谷类为主\r\n\r\n二 吃动平衡，健康体重\r\n\r\n三 多吃蔬果、奶类、大豆\r\n\r\n四 适量吃鱼、禽、蛋、瘦肉\r\n\r\n五 少盐少油，控糖限酒\r\n\r\n六 杜绝浪费，兴新食尚\r\n\r\n01\r\n\r\n食物多样，谷类为主\r\n\r\n每天的膳食应包括谷薯类、蔬菜水果类、畜禽鱼蛋奶类、大豆坚果类等食物。建议平均每天摄入12种以上食物，每周25种以上。谷类为主是平衡膳食模式的重要特征，每天摄入谷薯类食物250-400克，其中全谷物和杂豆类50-150克，薯类50-100克；膳食中碳水化合物提供的能量应占总能量的50%以上。\r\n\r\n02\r\n\r\n吃动平衡，健康体重\r\n\r\n各个年龄段人群都应该坚持天天运动、维持能量平衡、保持健康体重。体重过低和过高均易增加疾病的发生风险。推荐每周应至少进行5天中等强度身体活动，累计150分钟以上；坚持日常身体活动，平均每天主动身体活动6000步；尽量减少久坐时间，每小时起来动一动，动则有益。\r\n\r\n03\r\n\r\n多吃蔬果、奶类、大豆\r\n\r\n蔬菜、水果、奶类和大豆及制品是平衡膳食的重要组成部分，坚果是膳食的有益补充。蔬菜和水果是维生素、矿物质、膳食纤维和植物化学物的重要来源，奶类和大豆类富含钙、优质蛋白质和B族维生素，对降低慢性病的发病风险具有重要作用。提倡餐餐有蔬菜，推荐每天摄入300-500克，深色蔬菜应占1/2。天天吃水果，推荐每天摄入200-350克的新鲜水果，果汁不能代替鲜果。吃各种奶制品，摄入量相当于每天液态奶300克。经常吃豆制品，每天相当于大豆25 克以上，适量吃坚果。\r\n\r\n04\r\n\r\n适量吃鱼、禽、蛋、瘦肉\r\n\r\n鱼、禽、蛋和瘦肉可提供人体所需要的优质蛋白质、维生素A、B族维生素等，有些也含有较高的脂肪和胆固醇。动物性食物优选鱼和禽类，鱼和禽类脂肪含量相对较低，鱼类含有较多的不饱和脂肪酸；蛋类各种营养成分齐全；吃畜肉应选择瘦肉，瘦肉脂肪含量较低。过多食用烟熏和腌制肉类可增加肿瘤的发生风险，应当少吃。推荐每周吃鱼280-525克，畜禽肉280-525克，蛋类280-350克，平均每天摄入鱼、禽、蛋和瘦肉总量120-200克。\r\n', '2019-03-10', 0);
INSERT INTO `news` VALUES (28, '最新消息', '关于合理膳食的最新建议-12', '合理膳食也叫平衡膳食，是指按照不同年龄、身体活动和能量的需要设置的膳食模式，这个模式推荐的食物种类、数量和比例，能最大程度地满足不同年龄阶段、不同能量水平的健康人群的营养与健康需要。\r\n\r\n中国居民膳食指南\r\n\r\n一 食物多样，谷类为主\r\n\r\n二 吃动平衡，健康体重\r\n\r\n三 多吃蔬果、奶类、大豆\r\n\r\n四 适量吃鱼、禽、蛋、瘦肉\r\n\r\n五 少盐少油，控糖限酒\r\n\r\n六 杜绝浪费，兴新食尚\r\n\r\n01\r\n\r\n食物多样，谷类为主\r\n\r\n每天的膳食应包括谷薯类、蔬菜水果类、畜禽鱼蛋奶类、大豆坚果类等食物。建议平均每天摄入12种以上食物，每周25种以上。谷类为主是平衡膳食模式的重要特征，每天摄入谷薯类食物250-400克，其中全谷物和杂豆类50-150克，薯类50-100克；膳食中碳水化合物提供的能量应占总能量的50%以上。\r\n\r\n02\r\n\r\n吃动平衡，健康体重\r\n\r\n各个年龄段人群都应该坚持天天运动、维持能量平衡、保持健康体重。体重过低和过高均易增加疾病的发生风险。推荐每周应至少进行5天中等强度身体活动，累计150分钟以上；坚持日常身体活动，平均每天主动身体活动6000步；尽量减少久坐时间，每小时起来动一动，动则有益。\r\n\r\n03\r\n\r\n多吃蔬果、奶类、大豆\r\n\r\n蔬菜、水果、奶类和大豆及制品是平衡膳食的重要组成部分，坚果是膳食的有益补充。蔬菜和水果是维生素、矿物质、膳食纤维和植物化学物的重要来源，奶类和大豆类富含钙、优质蛋白质和B族维生素，对降低慢性病的发病风险具有重要作用。提倡餐餐有蔬菜，推荐每天摄入300-500克，深色蔬菜应占1/2。天天吃水果，推荐每天摄入200-350克的新鲜水果，果汁不能代替鲜果。吃各种奶制品，摄入量相当于每天液态奶300克。经常吃豆制品，每天相当于大豆25 克以上，适量吃坚果。\r\n\r\n04\r\n\r\n适量吃鱼、禽、蛋、瘦肉\r\n\r\n鱼、禽、蛋和瘦肉可提供人体所需要的优质蛋白质、维生素A、B族维生素等，有些也含有较高的脂肪和胆固醇。动物性食物优选鱼和禽类，鱼和禽类脂肪含量相对较低，鱼类含有较多的不饱和脂肪酸；蛋类各种营养成分齐全；吃畜肉应选择瘦肉，瘦肉脂肪含量较低。过多食用烟熏和腌制肉类可增加肿瘤的发生风险，应当少吃。推荐每周吃鱼280-525克，畜禽肉280-525克，蛋类280-350克，平均每天摄入鱼、禽、蛋和瘦肉总量120-200克。\r\n', '2019-03-10', 1);
INSERT INTO `news` VALUES (29, '最新消息', '关于合理膳食的最新建议-13', '合理膳食也叫平衡膳食，是指按照不同年龄、身体活动和能量的需要设置的膳食模式，这个模式推荐的食物种类、数量和比例，能最大程度地满足不同年龄阶段、不同能量水平的健康人群的营养与健康需要。\r\n\r\n中国居民膳食指南\r\n\r\n一 食物多样，谷类为主\r\n\r\n二 吃动平衡，健康体重\r\n\r\n三 多吃蔬果、奶类、大豆\r\n\r\n四 适量吃鱼、禽、蛋、瘦肉\r\n\r\n五 少盐少油，控糖限酒\r\n\r\n六 杜绝浪费，兴新食尚\r\n\r\n01\r\n\r\n食物多样，谷类为主\r\n\r\n每天的膳食应包括谷薯类、蔬菜水果类、畜禽鱼蛋奶类、大豆坚果类等食物。建议平均每天摄入12种以上食物，每周25种以上。谷类为主是平衡膳食模式的重要特征，每天摄入谷薯类食物250-400克，其中全谷物和杂豆类50-150克，薯类50-100克；膳食中碳水化合物提供的能量应占总能量的50%以上。\r\n\r\n02\r\n\r\n吃动平衡，健康体重\r\n\r\n各个年龄段人群都应该坚持天天运动、维持能量平衡、保持健康体重。体重过低和过高均易增加疾病的发生风险。推荐每周应至少进行5天中等强度身体活动，累计150分钟以上；坚持日常身体活动，平均每天主动身体活动6000步；尽量减少久坐时间，每小时起来动一动，动则有益。\r\n\r\n03\r\n\r\n多吃蔬果、奶类、大豆\r\n\r\n蔬菜、水果、奶类和大豆及制品是平衡膳食的重要组成部分，坚果是膳食的有益补充。蔬菜和水果是维生素、矿物质、膳食纤维和植物化学物的重要来源，奶类和大豆类富含钙、优质蛋白质和B族维生素，对降低慢性病的发病风险具有重要作用。提倡餐餐有蔬菜，推荐每天摄入300-500克，深色蔬菜应占1/2。天天吃水果，推荐每天摄入200-350克的新鲜水果，果汁不能代替鲜果。吃各种奶制品，摄入量相当于每天液态奶300克。经常吃豆制品，每天相当于大豆25 克以上，适量吃坚果。\r\n\r\n04\r\n\r\n适量吃鱼、禽、蛋、瘦肉\r\n\r\n鱼、禽、蛋和瘦肉可提供人体所需要的优质蛋白质、维生素A、B族维生素等，有些也含有较高的脂肪和胆固醇。动物性食物优选鱼和禽类，鱼和禽类脂肪含量相对较低，鱼类含有较多的不饱和脂肪酸；蛋类各种营养成分齐全；吃畜肉应选择瘦肉，瘦肉脂肪含量较低。过多食用烟熏和腌制肉类可增加肿瘤的发生风险，应当少吃。推荐每周吃鱼280-525克，畜禽肉280-525克，蛋类280-350克，平均每天摄入鱼、禽、蛋和瘦肉总量120-200克。\r\n', '2019-03-10', 2);
INSERT INTO `news` VALUES (30, '最新消息', '关于合理膳食的最新建议-14', '合理膳食也叫平衡膳食，是指按照不同年龄、身体活动和能量的需要设置的膳食模式，这个模式推荐的食物种类、数量和比例，能最大程度地满足不同年龄阶段、不同能量水平的健康人群的营养与健康需要。\r\n\r\n中国居民膳食指南\r\n\r\n一 食物多样，谷类为主\r\n\r\n二 吃动平衡，健康体重\r\n\r\n三 多吃蔬果、奶类、大豆\r\n\r\n四 适量吃鱼、禽、蛋、瘦肉\r\n\r\n五 少盐少油，控糖限酒\r\n\r\n六 杜绝浪费，兴新食尚\r\n\r\n01\r\n\r\n食物多样，谷类为主\r\n\r\n每天的膳食应包括谷薯类、蔬菜水果类、畜禽鱼蛋奶类、大豆坚果类等食物。建议平均每天摄入12种以上食物，每周25种以上。谷类为主是平衡膳食模式的重要特征，每天摄入谷薯类食物250-400克，其中全谷物和杂豆类50-150克，薯类50-100克；膳食中碳水化合物提供的能量应占总能量的50%以上。\r\n\r\n02\r\n\r\n吃动平衡，健康体重\r\n\r\n各个年龄段人群都应该坚持天天运动、维持能量平衡、保持健康体重。体重过低和过高均易增加疾病的发生风险。推荐每周应至少进行5天中等强度身体活动，累计150分钟以上；坚持日常身体活动，平均每天主动身体活动6000步；尽量减少久坐时间，每小时起来动一动，动则有益。\r\n\r\n03\r\n\r\n多吃蔬果、奶类、大豆\r\n\r\n蔬菜、水果、奶类和大豆及制品是平衡膳食的重要组成部分，坚果是膳食的有益补充。蔬菜和水果是维生素、矿物质、膳食纤维和植物化学物的重要来源，奶类和大豆类富含钙、优质蛋白质和B族维生素，对降低慢性病的发病风险具有重要作用。提倡餐餐有蔬菜，推荐每天摄入300-500克，深色蔬菜应占1/2。天天吃水果，推荐每天摄入200-350克的新鲜水果，果汁不能代替鲜果。吃各种奶制品，摄入量相当于每天液态奶300克。经常吃豆制品，每天相当于大豆25 克以上，适量吃坚果。\r\n\r\n04\r\n\r\n适量吃鱼、禽、蛋、瘦肉\r\n\r\n鱼、禽、蛋和瘦肉可提供人体所需要的优质蛋白质、维生素A、B族维生素等，有些也含有较高的脂肪和胆固醇。动物性食物优选鱼和禽类，鱼和禽类脂肪含量相对较低，鱼类含有较多的不饱和脂肪酸；蛋类各种营养成分齐全；吃畜肉应选择瘦肉，瘦肉脂肪含量较低。过多食用烟熏和腌制肉类可增加肿瘤的发生风险，应当少吃。推荐每周吃鱼280-525克，畜禽肉280-525克，蛋类280-350克，平均每天摄入鱼、禽、蛋和瘦肉总量120-200克。\r\n', '2019-03-10', 2);
INSERT INTO `news` VALUES (31, '最新消息', '关于合理膳食的最新建议-15', '合理膳食也叫平衡膳食，是指按照不同年龄、身体活动和能量的需要设置的膳食模式，这个模式推荐的食物种类、数量和比例，能最大程度地满足不同年龄阶段、不同能量水平的健康人群的营养与健康需要。\r\n\r\n中国居民膳食指南\r\n\r\n一 食物多样，谷类为主\r\n\r\n二 吃动平衡，健康体重\r\n\r\n三 多吃蔬果、奶类、大豆\r\n\r\n四 适量吃鱼、禽、蛋、瘦肉\r\n\r\n五 少盐少油，控糖限酒\r\n\r\n六 杜绝浪费，兴新食尚\r\n\r\n01\r\n\r\n食物多样，谷类为主\r\n\r\n每天的膳食应包括谷薯类、蔬菜水果类、畜禽鱼蛋奶类、大豆坚果类等食物。建议平均每天摄入12种以上食物，每周25种以上。谷类为主是平衡膳食模式的重要特征，每天摄入谷薯类食物250-400克，其中全谷物和杂豆类50-150克，薯类50-100克；膳食中碳水化合物提供的能量应占总能量的50%以上。\r\n\r\n02\r\n\r\n吃动平衡，健康体重\r\n\r\n各个年龄段人群都应该坚持天天运动、维持能量平衡、保持健康体重。体重过低和过高均易增加疾病的发生风险。推荐每周应至少进行5天中等强度身体活动，累计150分钟以上；坚持日常身体活动，平均每天主动身体活动6000步；尽量减少久坐时间，每小时起来动一动，动则有益。\r\n\r\n03\r\n\r\n多吃蔬果、奶类、大豆\r\n\r\n蔬菜、水果、奶类和大豆及制品是平衡膳食的重要组成部分，坚果是膳食的有益补充。蔬菜和水果是维生素、矿物质、膳食纤维和植物化学物的重要来源，奶类和大豆类富含钙、优质蛋白质和B族维生素，对降低慢性病的发病风险具有重要作用。提倡餐餐有蔬菜，推荐每天摄入300-500克，深色蔬菜应占1/2。天天吃水果，推荐每天摄入200-350克的新鲜水果，果汁不能代替鲜果。吃各种奶制品，摄入量相当于每天液态奶300克。经常吃豆制品，每天相当于大豆25 克以上，适量吃坚果。\r\n\r\n04\r\n\r\n适量吃鱼、禽、蛋、瘦肉\r\n\r\n鱼、禽、蛋和瘦肉可提供人体所需要的优质蛋白质、维生素A、B族维生素等，有些也含有较高的脂肪和胆固醇。动物性食物优选鱼和禽类，鱼和禽类脂肪含量相对较低，鱼类含有较多的不饱和脂肪酸；蛋类各种营养成分齐全；吃畜肉应选择瘦肉，瘦肉脂肪含量较低。过多食用烟熏和腌制肉类可增加肿瘤的发生风险，应当少吃。推荐每周吃鱼280-525克，畜禽肉280-525克，蛋类280-350克，平均每天摄入鱼、禽、蛋和瘦肉总量120-200克。\r\n', '2019-03-10', 15);
INSERT INTO `news` VALUES (32, '热点新闻', '春季养生知多少-1', '春季养生是一种养生活动，即春季运动强身健体，应遵循养阳防风的原则。春季，人体阳气顺应自然，向上向外疏发，因此要注意保卫体内的阳气，凡有损阳气的情况都应避免。\r\n\r\n养肝为先\r\n\r\n肝脏是人体的一个重要器官，它具有调节气血，帮助脾胃消化食物、吸收营养的功能以及调畅情志、疏理气机的作用。因此，春季养肝得法，将带来整年的健康安寿。\r\n中医养生学理论认为“春与肝相应”，意思是说春季的气候特点与人体的肝脏有着密切的关系。早春时节，专家指出，养肝护肝要从饮食、起居、锻炼和情志四方面入手。\r\n\r\n饮食\r\n\r\n春季饮食要根据个人体质进行选择，普通健康人群不主张大量的进补。身体特别虚弱的人可以适量食用海参、冬虫夏草等补品。\r\n对于健康人群而言，春季饮食要注意清淡，不要过度食用干燥、辛辣的食物。\r\n同时，因为此时阳气上升容易伤阴，因此要特别注重养阴，可以多选用百合、山药、莲子、枸杞等食物。\r\n对于“吃肝补肝”的说法，李志红表示，可以适量吃一些猪肝，但一定要保证饮食卫生。\r\n\r\n起居\r\n\r\n春天的三个月，是自然界万物复苏，各种生物欣欣向荣的季节。人们也要顺应自然界的规律，早睡早起，起床后要全身放松，在庭院中悠闲地散步以舒畅自己的情志。\r\n人们从冬季已经习惯了的“早睡晚起”，过渡到春季的“晚睡早起”要有一个逐渐适应的过程，不要太急于转变，而要顺应自然界的昼夜时间变化而逐步转变自己的睡眠习惯。\r\n\r\n锻炼\r\n\r\n春季的运动养生保健是恢复身体“元气”的最佳时节。由于寒冷的冬季限制人们的运动锻炼，使机体的体温调节中枢和内脏器官的功能，都有不同程度的减弱，特别是全身的肌肉和韧带，更需要锻炼以增强其运动功能。春季人们应该进行适当的运动，如散步、慢跑、体操、太极拳等，保持体内的生机，增强免疫力与抗病能力。\r\n不过，春天的气候呈现温差大、风大的特点，要注意防风御寒，因此在遇到强风时要适当地减少外出锻炼，以免风大伤肝。\r\n\r\n情志\r\n\r\n中医理论认为肝属木，与春季相应，生理特性为“喜条达而恶抑郁”，故有“大怒伤肝”之说。肝的生理特点是喜欢舒展、条畅的情绪而不喜欢抑郁、烦闷。\r\n专家指出，在春季保健重点是保持自己的心情舒畅，努力做到不着急、不生气、不发怒，以保证肝的舒畅条达。\r\n春季养生，情绪上要乐观，不宜抑郁或发怒，不要过分劳累，以免加重肝脏负担。有肝脏疾患的人，要做到心宽、心静。在繁忙浮躁和充满诱惑的尘世纷扰下，要做到“恬然不动其心”，就能保持机体内环境的稳定，防止心理疾病的发生。\r\n\r\n\r\n', '2019-03-10', 15);
INSERT INTO `news` VALUES (33, '热点新闻', '春季养生知多少-2', '春季养生是一种养生活动，即春季运动强身健体，应遵循养阳防风的原则。春季，人体阳气顺应自然，向上向外疏发，因此要注意保卫体内的阳气，凡有损阳气的情况都应避免。\r\n\r\n养肝为先\r\n\r\n肝脏是人体的一个重要器官，它具有调节气血，帮助脾胃消化食物、吸收营养的功能以及调畅情志、疏理气机的作用。因此，春季养肝得法，将带来整年的健康安寿。\r\n中医养生学理论认为“春与肝相应”，意思是说春季的气候特点与人体的肝脏有着密切的关系。早春时节，专家指出，养肝护肝要从饮食、起居、锻炼和情志四方面入手。\r\n\r\n饮食\r\n\r\n春季饮食要根据个人体质进行选择，普通健康人群不主张大量的进补。身体特别虚弱的人可以适量食用海参、冬虫夏草等补品。\r\n对于健康人群而言，春季饮食要注意清淡，不要过度食用干燥、辛辣的食物。\r\n同时，因为此时阳气上升容易伤阴，因此要特别注重养阴，可以多选用百合、山药、莲子、枸杞等食物。\r\n对于“吃肝补肝”的说法，李志红表示，可以适量吃一些猪肝，但一定要保证饮食卫生。\r\n\r\n起居\r\n\r\n春天的三个月，是自然界万物复苏，各种生物欣欣向荣的季节。人们也要顺应自然界的规律，早睡早起，起床后要全身放松，在庭院中悠闲地散步以舒畅自己的情志。\r\n人们从冬季已经习惯了的“早睡晚起”，过渡到春季的“晚睡早起”要有一个逐渐适应的过程，不要太急于转变，而要顺应自然界的昼夜时间变化而逐步转变自己的睡眠习惯。\r\n\r\n锻炼\r\n\r\n春季的运动养生保健是恢复身体“元气”的最佳时节。由于寒冷的冬季限制人们的运动锻炼，使机体的体温调节中枢和内脏器官的功能，都有不同程度的减弱，特别是全身的肌肉和韧带，更需要锻炼以增强其运动功能。春季人们应该进行适当的运动，如散步、慢跑、体操、太极拳等，保持体内的生机，增强免疫力与抗病能力。\r\n不过，春天的气候呈现温差大、风大的特点，要注意防风御寒，因此在遇到强风时要适当地减少外出锻炼，以免风大伤肝。\r\n\r\n情志\r\n\r\n中医理论认为肝属木，与春季相应，生理特性为“喜条达而恶抑郁”，故有“大怒伤肝”之说。肝的生理特点是喜欢舒展、条畅的情绪而不喜欢抑郁、烦闷。\r\n专家指出，在春季保健重点是保持自己的心情舒畅，努力做到不着急、不生气、不发怒，以保证肝的舒畅条达。\r\n春季养生，情绪上要乐观，不宜抑郁或发怒，不要过分劳累，以免加重肝脏负担。有肝脏疾患的人，要做到心宽、心静。在繁忙浮躁和充满诱惑的尘世纷扰下，要做到“恬然不动其心”，就能保持机体内环境的稳定，防止心理疾病的发生。\r\n\r\n\r\n', '2019-03-10', 0);
INSERT INTO `news` VALUES (34, '热点新闻', '春季养生知多少-3', '春季养生是一种养生活动，即春季运动强身健体，应遵循养阳防风的原则。春季，人体阳气顺应自然，向上向外疏发，因此要注意保卫体内的阳气，凡有损阳气的情况都应避免。\r\n\r\n养肝为先\r\n\r\n肝脏是人体的一个重要器官，它具有调节气血，帮助脾胃消化食物、吸收营养的功能以及调畅情志、疏理气机的作用。因此，春季养肝得法，将带来整年的健康安寿。\r\n中医养生学理论认为“春与肝相应”，意思是说春季的气候特点与人体的肝脏有着密切的关系。早春时节，专家指出，养肝护肝要从饮食、起居、锻炼和情志四方面入手。\r\n\r\n饮食\r\n\r\n春季饮食要根据个人体质进行选择，普通健康人群不主张大量的进补。身体特别虚弱的人可以适量食用海参、冬虫夏草等补品。\r\n对于健康人群而言，春季饮食要注意清淡，不要过度食用干燥、辛辣的食物。\r\n同时，因为此时阳气上升容易伤阴，因此要特别注重养阴，可以多选用百合、山药、莲子、枸杞等食物。\r\n对于“吃肝补肝”的说法，李志红表示，可以适量吃一些猪肝，但一定要保证饮食卫生。\r\n\r\n起居\r\n\r\n春天的三个月，是自然界万物复苏，各种生物欣欣向荣的季节。人们也要顺应自然界的规律，早睡早起，起床后要全身放松，在庭院中悠闲地散步以舒畅自己的情志。\r\n人们从冬季已经习惯了的“早睡晚起”，过渡到春季的“晚睡早起”要有一个逐渐适应的过程，不要太急于转变，而要顺应自然界的昼夜时间变化而逐步转变自己的睡眠习惯。\r\n\r\n锻炼\r\n\r\n春季的运动养生保健是恢复身体“元气”的最佳时节。由于寒冷的冬季限制人们的运动锻炼，使机体的体温调节中枢和内脏器官的功能，都有不同程度的减弱，特别是全身的肌肉和韧带，更需要锻炼以增强其运动功能。春季人们应该进行适当的运动，如散步、慢跑、体操、太极拳等，保持体内的生机，增强免疫力与抗病能力。\r\n不过，春天的气候呈现温差大、风大的特点，要注意防风御寒，因此在遇到强风时要适当地减少外出锻炼，以免风大伤肝。\r\n\r\n情志\r\n\r\n中医理论认为肝属木，与春季相应，生理特性为“喜条达而恶抑郁”，故有“大怒伤肝”之说。肝的生理特点是喜欢舒展、条畅的情绪而不喜欢抑郁、烦闷。\r\n专家指出，在春季保健重点是保持自己的心情舒畅，努力做到不着急、不生气、不发怒，以保证肝的舒畅条达。\r\n春季养生，情绪上要乐观，不宜抑郁或发怒，不要过分劳累，以免加重肝脏负担。有肝脏疾患的人，要做到心宽、心静。在繁忙浮躁和充满诱惑的尘世纷扰下，要做到“恬然不动其心”，就能保持机体内环境的稳定，防止心理疾病的发生。\r\n\r\n\r\n', '2019-03-10', 0);
INSERT INTO `news` VALUES (35, '热点新闻', '春季养生知多少-4', '春季养生是一种养生活动，即春季运动强身健体，应遵循养阳防风的原则。春季，人体阳气顺应自然，向上向外疏发，因此要注意保卫体内的阳气，凡有损阳气的情况都应避免。\r\n\r\n养肝为先\r\n\r\n肝脏是人体的一个重要器官，它具有调节气血，帮助脾胃消化食物、吸收营养的功能以及调畅情志、疏理气机的作用。因此，春季养肝得法，将带来整年的健康安寿。\r\n中医养生学理论认为“春与肝相应”，意思是说春季的气候特点与人体的肝脏有着密切的关系。早春时节，专家指出，养肝护肝要从饮食、起居、锻炼和情志四方面入手。\r\n\r\n饮食\r\n\r\n春季饮食要根据个人体质进行选择，普通健康人群不主张大量的进补。身体特别虚弱的人可以适量食用海参、冬虫夏草等补品。\r\n对于健康人群而言，春季饮食要注意清淡，不要过度食用干燥、辛辣的食物。\r\n同时，因为此时阳气上升容易伤阴，因此要特别注重养阴，可以多选用百合、山药、莲子、枸杞等食物。\r\n对于“吃肝补肝”的说法，李志红表示，可以适量吃一些猪肝，但一定要保证饮食卫生。\r\n\r\n起居\r\n\r\n春天的三个月，是自然界万物复苏，各种生物欣欣向荣的季节。人们也要顺应自然界的规律，早睡早起，起床后要全身放松，在庭院中悠闲地散步以舒畅自己的情志。\r\n人们从冬季已经习惯了的“早睡晚起”，过渡到春季的“晚睡早起”要有一个逐渐适应的过程，不要太急于转变，而要顺应自然界的昼夜时间变化而逐步转变自己的睡眠习惯。\r\n\r\n锻炼\r\n\r\n春季的运动养生保健是恢复身体“元气”的最佳时节。由于寒冷的冬季限制人们的运动锻炼，使机体的体温调节中枢和内脏器官的功能，都有不同程度的减弱，特别是全身的肌肉和韧带，更需要锻炼以增强其运动功能。春季人们应该进行适当的运动，如散步、慢跑、体操、太极拳等，保持体内的生机，增强免疫力与抗病能力。\r\n不过，春天的气候呈现温差大、风大的特点，要注意防风御寒，因此在遇到强风时要适当地减少外出锻炼，以免风大伤肝。\r\n\r\n情志\r\n\r\n中医理论认为肝属木，与春季相应，生理特性为“喜条达而恶抑郁”，故有“大怒伤肝”之说。肝的生理特点是喜欢舒展、条畅的情绪而不喜欢抑郁、烦闷。\r\n专家指出，在春季保健重点是保持自己的心情舒畅，努力做到不着急、不生气、不发怒，以保证肝的舒畅条达。\r\n春季养生，情绪上要乐观，不宜抑郁或发怒，不要过分劳累，以免加重肝脏负担。有肝脏疾患的人，要做到心宽、心静。在繁忙浮躁和充满诱惑的尘世纷扰下，要做到“恬然不动其心”，就能保持机体内环境的稳定，防止心理疾病的发生。\r\n\r\n\r\n', '2019-03-10', 0);
INSERT INTO `news` VALUES (36, '热点新闻', '春季养生知多少-5', '春季养生是一种养生活动，即春季运动强身健体，应遵循养阳防风的原则。春季，人体阳气顺应自然，向上向外疏发，因此要注意保卫体内的阳气，凡有损阳气的情况都应避免。\r\n\r\n养肝为先\r\n\r\n肝脏是人体的一个重要器官，它具有调节气血，帮助脾胃消化食物、吸收营养的功能以及调畅情志、疏理气机的作用。因此，春季养肝得法，将带来整年的健康安寿。\r\n中医养生学理论认为“春与肝相应”，意思是说春季的气候特点与人体的肝脏有着密切的关系。早春时节，专家指出，养肝护肝要从饮食、起居、锻炼和情志四方面入手。\r\n\r\n饮食\r\n\r\n春季饮食要根据个人体质进行选择，普通健康人群不主张大量的进补。身体特别虚弱的人可以适量食用海参、冬虫夏草等补品。\r\n对于健康人群而言，春季饮食要注意清淡，不要过度食用干燥、辛辣的食物。\r\n同时，因为此时阳气上升容易伤阴，因此要特别注重养阴，可以多选用百合、山药、莲子、枸杞等食物。\r\n对于“吃肝补肝”的说法，李志红表示，可以适量吃一些猪肝，但一定要保证饮食卫生。\r\n\r\n起居\r\n\r\n春天的三个月，是自然界万物复苏，各种生物欣欣向荣的季节。人们也要顺应自然界的规律，早睡早起，起床后要全身放松，在庭院中悠闲地散步以舒畅自己的情志。\r\n人们从冬季已经习惯了的“早睡晚起”，过渡到春季的“晚睡早起”要有一个逐渐适应的过程，不要太急于转变，而要顺应自然界的昼夜时间变化而逐步转变自己的睡眠习惯。\r\n\r\n锻炼\r\n\r\n春季的运动养生保健是恢复身体“元气”的最佳时节。由于寒冷的冬季限制人们的运动锻炼，使机体的体温调节中枢和内脏器官的功能，都有不同程度的减弱，特别是全身的肌肉和韧带，更需要锻炼以增强其运动功能。春季人们应该进行适当的运动，如散步、慢跑、体操、太极拳等，保持体内的生机，增强免疫力与抗病能力。\r\n不过，春天的气候呈现温差大、风大的特点，要注意防风御寒，因此在遇到强风时要适当地减少外出锻炼，以免风大伤肝。\r\n\r\n情志\r\n\r\n中医理论认为肝属木，与春季相应，生理特性为“喜条达而恶抑郁”，故有“大怒伤肝”之说。肝的生理特点是喜欢舒展、条畅的情绪而不喜欢抑郁、烦闷。\r\n专家指出，在春季保健重点是保持自己的心情舒畅，努力做到不着急、不生气、不发怒，以保证肝的舒畅条达。\r\n春季养生，情绪上要乐观，不宜抑郁或发怒，不要过分劳累，以免加重肝脏负担。有肝脏疾患的人，要做到心宽、心静。在繁忙浮躁和充满诱惑的尘世纷扰下，要做到“恬然不动其心”，就能保持机体内环境的稳定，防止心理疾病的发生。\r\n\r\n\r\n', '2019-03-10', 27);
INSERT INTO `news` VALUES (37, '热点新闻', '春季养生知多少-6', '春季养生是一种养生活动，即春季运动强身健体，应遵循养阳防风的原则。春季，人体阳气顺应自然，向上向外疏发，因此要注意保卫体内的阳气，凡有损阳气的情况都应避免。\r\n\r\n养肝为先\r\n\r\n肝脏是人体的一个重要器官，它具有调节气血，帮助脾胃消化食物、吸收营养的功能以及调畅情志、疏理气机的作用。因此，春季养肝得法，将带来整年的健康安寿。\r\n中医养生学理论认为“春与肝相应”，意思是说春季的气候特点与人体的肝脏有着密切的关系。早春时节，专家指出，养肝护肝要从饮食、起居、锻炼和情志四方面入手。\r\n\r\n饮食\r\n\r\n春季饮食要根据个人体质进行选择，普通健康人群不主张大量的进补。身体特别虚弱的人可以适量食用海参、冬虫夏草等补品。\r\n对于健康人群而言，春季饮食要注意清淡，不要过度食用干燥、辛辣的食物。\r\n同时，因为此时阳气上升容易伤阴，因此要特别注重养阴，可以多选用百合、山药、莲子、枸杞等食物。\r\n对于“吃肝补肝”的说法，李志红表示，可以适量吃一些猪肝，但一定要保证饮食卫生。\r\n\r\n起居\r\n\r\n春天的三个月，是自然界万物复苏，各种生物欣欣向荣的季节。人们也要顺应自然界的规律，早睡早起，起床后要全身放松，在庭院中悠闲地散步以舒畅自己的情志。\r\n人们从冬季已经习惯了的“早睡晚起”，过渡到春季的“晚睡早起”要有一个逐渐适应的过程，不要太急于转变，而要顺应自然界的昼夜时间变化而逐步转变自己的睡眠习惯。\r\n\r\n锻炼\r\n\r\n春季的运动养生保健是恢复身体“元气”的最佳时节。由于寒冷的冬季限制人们的运动锻炼，使机体的体温调节中枢和内脏器官的功能，都有不同程度的减弱，特别是全身的肌肉和韧带，更需要锻炼以增强其运动功能。春季人们应该进行适当的运动，如散步、慢跑、体操、太极拳等，保持体内的生机，增强免疫力与抗病能力。\r\n不过，春天的气候呈现温差大、风大的特点，要注意防风御寒，因此在遇到强风时要适当地减少外出锻炼，以免风大伤肝。\r\n\r\n情志\r\n\r\n中医理论认为肝属木，与春季相应，生理特性为“喜条达而恶抑郁”，故有“大怒伤肝”之说。肝的生理特点是喜欢舒展、条畅的情绪而不喜欢抑郁、烦闷。\r\n专家指出，在春季保健重点是保持自己的心情舒畅，努力做到不着急、不生气、不发怒，以保证肝的舒畅条达。\r\n春季养生，情绪上要乐观，不宜抑郁或发怒，不要过分劳累，以免加重肝脏负担。有肝脏疾患的人，要做到心宽、心静。在繁忙浮躁和充满诱惑的尘世纷扰下，要做到“恬然不动其心”，就能保持机体内环境的稳定，防止心理疾病的发生。\r\n\r\n\r\n', '2019-03-10', 0);
INSERT INTO `news` VALUES (38, '热点新闻', '春季养生知多少-7', '春季养生是一种养生活动，即春季运动强身健体，应遵循养阳防风的原则。春季，人体阳气顺应自然，向上向外疏发，因此要注意保卫体内的阳气，凡有损阳气的情况都应避免。\r\n\r\n养肝为先\r\n\r\n肝脏是人体的一个重要器官，它具有调节气血，帮助脾胃消化食物、吸收营养的功能以及调畅情志、疏理气机的作用。因此，春季养肝得法，将带来整年的健康安寿。\r\n中医养生学理论认为“春与肝相应”，意思是说春季的气候特点与人体的肝脏有着密切的关系。早春时节，专家指出，养肝护肝要从饮食、起居、锻炼和情志四方面入手。\r\n\r\n饮食\r\n\r\n春季饮食要根据个人体质进行选择，普通健康人群不主张大量的进补。身体特别虚弱的人可以适量食用海参、冬虫夏草等补品。\r\n对于健康人群而言，春季饮食要注意清淡，不要过度食用干燥、辛辣的食物。\r\n同时，因为此时阳气上升容易伤阴，因此要特别注重养阴，可以多选用百合、山药、莲子、枸杞等食物。\r\n对于“吃肝补肝”的说法，李志红表示，可以适量吃一些猪肝，但一定要保证饮食卫生。\r\n\r\n起居\r\n\r\n春天的三个月，是自然界万物复苏，各种生物欣欣向荣的季节。人们也要顺应自然界的规律，早睡早起，起床后要全身放松，在庭院中悠闲地散步以舒畅自己的情志。\r\n人们从冬季已经习惯了的“早睡晚起”，过渡到春季的“晚睡早起”要有一个逐渐适应的过程，不要太急于转变，而要顺应自然界的昼夜时间变化而逐步转变自己的睡眠习惯。\r\n\r\n锻炼\r\n\r\n春季的运动养生保健是恢复身体“元气”的最佳时节。由于寒冷的冬季限制人们的运动锻炼，使机体的体温调节中枢和内脏器官的功能，都有不同程度的减弱，特别是全身的肌肉和韧带，更需要锻炼以增强其运动功能。春季人们应该进行适当的运动，如散步、慢跑、体操、太极拳等，保持体内的生机，增强免疫力与抗病能力。\r\n不过，春天的气候呈现温差大、风大的特点，要注意防风御寒，因此在遇到强风时要适当地减少外出锻炼，以免风大伤肝。\r\n\r\n情志\r\n\r\n中医理论认为肝属木，与春季相应，生理特性为“喜条达而恶抑郁”，故有“大怒伤肝”之说。肝的生理特点是喜欢舒展、条畅的情绪而不喜欢抑郁、烦闷。\r\n专家指出，在春季保健重点是保持自己的心情舒畅，努力做到不着急、不生气、不发怒，以保证肝的舒畅条达。\r\n春季养生，情绪上要乐观，不宜抑郁或发怒，不要过分劳累，以免加重肝脏负担。有肝脏疾患的人，要做到心宽、心静。在繁忙浮躁和充满诱惑的尘世纷扰下，要做到“恬然不动其心”，就能保持机体内环境的稳定，防止心理疾病的发生。\r\n\r\n\r\n', '2019-03-10', 0);
INSERT INTO `news` VALUES (39, '热点新闻', '春季养生知多少-8', '春季养生是一种养生活动，即春季运动强身健体，应遵循养阳防风的原则。春季，人体阳气顺应自然，向上向外疏发，因此要注意保卫体内的阳气，凡有损阳气的情况都应避免。\r\n\r\n养肝为先\r\n\r\n肝脏是人体的一个重要器官，它具有调节气血，帮助脾胃消化食物、吸收营养的功能以及调畅情志、疏理气机的作用。因此，春季养肝得法，将带来整年的健康安寿。\r\n中医养生学理论认为“春与肝相应”，意思是说春季的气候特点与人体的肝脏有着密切的关系。早春时节，专家指出，养肝护肝要从饮食、起居、锻炼和情志四方面入手。\r\n\r\n饮食\r\n\r\n春季饮食要根据个人体质进行选择，普通健康人群不主张大量的进补。身体特别虚弱的人可以适量食用海参、冬虫夏草等补品。\r\n对于健康人群而言，春季饮食要注意清淡，不要过度食用干燥、辛辣的食物。\r\n同时，因为此时阳气上升容易伤阴，因此要特别注重养阴，可以多选用百合、山药、莲子、枸杞等食物。\r\n对于“吃肝补肝”的说法，李志红表示，可以适量吃一些猪肝，但一定要保证饮食卫生。\r\n\r\n起居\r\n\r\n春天的三个月，是自然界万物复苏，各种生物欣欣向荣的季节。人们也要顺应自然界的规律，早睡早起，起床后要全身放松，在庭院中悠闲地散步以舒畅自己的情志。\r\n人们从冬季已经习惯了的“早睡晚起”，过渡到春季的“晚睡早起”要有一个逐渐适应的过程，不要太急于转变，而要顺应自然界的昼夜时间变化而逐步转变自己的睡眠习惯。\r\n\r\n锻炼\r\n\r\n春季的运动养生保健是恢复身体“元气”的最佳时节。由于寒冷的冬季限制人们的运动锻炼，使机体的体温调节中枢和内脏器官的功能，都有不同程度的减弱，特别是全身的肌肉和韧带，更需要锻炼以增强其运动功能。春季人们应该进行适当的运动，如散步、慢跑、体操、太极拳等，保持体内的生机，增强免疫力与抗病能力。\r\n不过，春天的气候呈现温差大、风大的特点，要注意防风御寒，因此在遇到强风时要适当地减少外出锻炼，以免风大伤肝。\r\n\r\n情志\r\n\r\n中医理论认为肝属木，与春季相应，生理特性为“喜条达而恶抑郁”，故有“大怒伤肝”之说。肝的生理特点是喜欢舒展、条畅的情绪而不喜欢抑郁、烦闷。\r\n专家指出，在春季保健重点是保持自己的心情舒畅，努力做到不着急、不生气、不发怒，以保证肝的舒畅条达。\r\n春季养生，情绪上要乐观，不宜抑郁或发怒，不要过分劳累，以免加重肝脏负担。有肝脏疾患的人，要做到心宽、心静。在繁忙浮躁和充满诱惑的尘世纷扰下，要做到“恬然不动其心”，就能保持机体内环境的稳定，防止心理疾病的发生。\r\n\r\n\r\n', '2019-03-10', 4);
INSERT INTO `news` VALUES (40, '热点新闻', '春季养生知多少-9', '春季养生是一种养生活动，即春季运动强身健体，应遵循养阳防风的原则。春季，人体阳气顺应自然，向上向外疏发，因此要注意保卫体内的阳气，凡有损阳气的情况都应避免。\r\n\r\n养肝为先\r\n\r\n肝脏是人体的一个重要器官，它具有调节气血，帮助脾胃消化食物、吸收营养的功能以及调畅情志、疏理气机的作用。因此，春季养肝得法，将带来整年的健康安寿。\r\n中医养生学理论认为“春与肝相应”，意思是说春季的气候特点与人体的肝脏有着密切的关系。早春时节，专家指出，养肝护肝要从饮食、起居、锻炼和情志四方面入手。\r\n\r\n饮食\r\n\r\n春季饮食要根据个人体质进行选择，普通健康人群不主张大量的进补。身体特别虚弱的人可以适量食用海参、冬虫夏草等补品。\r\n对于健康人群而言，春季饮食要注意清淡，不要过度食用干燥、辛辣的食物。\r\n同时，因为此时阳气上升容易伤阴，因此要特别注重养阴，可以多选用百合、山药、莲子、枸杞等食物。\r\n对于“吃肝补肝”的说法，李志红表示，可以适量吃一些猪肝，但一定要保证饮食卫生。\r\n\r\n起居\r\n\r\n春天的三个月，是自然界万物复苏，各种生物欣欣向荣的季节。人们也要顺应自然界的规律，早睡早起，起床后要全身放松，在庭院中悠闲地散步以舒畅自己的情志。\r\n人们从冬季已经习惯了的“早睡晚起”，过渡到春季的“晚睡早起”要有一个逐渐适应的过程，不要太急于转变，而要顺应自然界的昼夜时间变化而逐步转变自己的睡眠习惯。\r\n\r\n锻炼\r\n\r\n春季的运动养生保健是恢复身体“元气”的最佳时节。由于寒冷的冬季限制人们的运动锻炼，使机体的体温调节中枢和内脏器官的功能，都有不同程度的减弱，特别是全身的肌肉和韧带，更需要锻炼以增强其运动功能。春季人们应该进行适当的运动，如散步、慢跑、体操、太极拳等，保持体内的生机，增强免疫力与抗病能力。\r\n不过，春天的气候呈现温差大、风大的特点，要注意防风御寒，因此在遇到强风时要适当地减少外出锻炼，以免风大伤肝。\r\n\r\n情志\r\n\r\n中医理论认为肝属木，与春季相应，生理特性为“喜条达而恶抑郁”，故有“大怒伤肝”之说。肝的生理特点是喜欢舒展、条畅的情绪而不喜欢抑郁、烦闷。\r\n专家指出，在春季保健重点是保持自己的心情舒畅，努力做到不着急、不生气、不发怒，以保证肝的舒畅条达。\r\n春季养生，情绪上要乐观，不宜抑郁或发怒，不要过分劳累，以免加重肝脏负担。有肝脏疾患的人，要做到心宽、心静。在繁忙浮躁和充满诱惑的尘世纷扰下，要做到“恬然不动其心”，就能保持机体内环境的稳定，防止心理疾病的发生。\r\n\r\n\r\n', '2019-03-10', 0);
INSERT INTO `news` VALUES (41, '热点新闻', '春季养生知多少-10', '春季养生是一种养生活动，即春季运动强身健体，应遵循养阳防风的原则。春季，人体阳气顺应自然，向上向外疏发，因此要注意保卫体内的阳气，凡有损阳气的情况都应避免。\r\n\r\n养肝为先\r\n\r\n肝脏是人体的一个重要器官，它具有调节气血，帮助脾胃消化食物、吸收营养的功能以及调畅情志、疏理气机的作用。因此，春季养肝得法，将带来整年的健康安寿。\r\n中医养生学理论认为“春与肝相应”，意思是说春季的气候特点与人体的肝脏有着密切的关系。早春时节，专家指出，养肝护肝要从饮食、起居、锻炼和情志四方面入手。\r\n\r\n饮食\r\n\r\n春季饮食要根据个人体质进行选择，普通健康人群不主张大量的进补。身体特别虚弱的人可以适量食用海参、冬虫夏草等补品。\r\n对于健康人群而言，春季饮食要注意清淡，不要过度食用干燥、辛辣的食物。\r\n同时，因为此时阳气上升容易伤阴，因此要特别注重养阴，可以多选用百合、山药、莲子、枸杞等食物。\r\n对于“吃肝补肝”的说法，李志红表示，可以适量吃一些猪肝，但一定要保证饮食卫生。\r\n\r\n起居\r\n\r\n春天的三个月，是自然界万物复苏，各种生物欣欣向荣的季节。人们也要顺应自然界的规律，早睡早起，起床后要全身放松，在庭院中悠闲地散步以舒畅自己的情志。\r\n人们从冬季已经习惯了的“早睡晚起”，过渡到春季的“晚睡早起”要有一个逐渐适应的过程，不要太急于转变，而要顺应自然界的昼夜时间变化而逐步转变自己的睡眠习惯。\r\n\r\n锻炼\r\n\r\n春季的运动养生保健是恢复身体“元气”的最佳时节。由于寒冷的冬季限制人们的运动锻炼，使机体的体温调节中枢和内脏器官的功能，都有不同程度的减弱，特别是全身的肌肉和韧带，更需要锻炼以增强其运动功能。春季人们应该进行适当的运动，如散步、慢跑、体操、太极拳等，保持体内的生机，增强免疫力与抗病能力。\r\n不过，春天的气候呈现温差大、风大的特点，要注意防风御寒，因此在遇到强风时要适当地减少外出锻炼，以免风大伤肝。\r\n\r\n情志\r\n\r\n中医理论认为肝属木，与春季相应，生理特性为“喜条达而恶抑郁”，故有“大怒伤肝”之说。肝的生理特点是喜欢舒展、条畅的情绪而不喜欢抑郁、烦闷。\r\n专家指出，在春季保健重点是保持自己的心情舒畅，努力做到不着急、不生气、不发怒，以保证肝的舒畅条达。\r\n春季养生，情绪上要乐观，不宜抑郁或发怒，不要过分劳累，以免加重肝脏负担。有肝脏疾患的人，要做到心宽、心静。在繁忙浮躁和充满诱惑的尘世纷扰下，要做到“恬然不动其心”，就能保持机体内环境的稳定，防止心理疾病的发生。\r\n\r\n\r\n', '2019-03-10', 0);
INSERT INTO `news` VALUES (42, '热点新闻', '春季养生知多少-12', '春季养生是一种养生活动，即春季运动强身健体，应遵循养阳防风的原则。春季，人体阳气顺应自然，向上向外疏发，因此要注意保卫体内的阳气，凡有损阳气的情况都应避免。\r\n\r\n养肝为先\r\n\r\n肝脏是人体的一个重要器官，它具有调节气血，帮助脾胃消化食物、吸收营养的功能以及调畅情志、疏理气机的作用。因此，春季养肝得法，将带来整年的健康安寿。\r\n中医养生学理论认为“春与肝相应”，意思是说春季的气候特点与人体的肝脏有着密切的关系。早春时节，专家指出，养肝护肝要从饮食、起居、锻炼和情志四方面入手。\r\n\r\n饮食\r\n\r\n春季饮食要根据个人体质进行选择，普通健康人群不主张大量的进补。身体特别虚弱的人可以适量食用海参、冬虫夏草等补品。\r\n对于健康人群而言，春季饮食要注意清淡，不要过度食用干燥、辛辣的食物。\r\n同时，因为此时阳气上升容易伤阴，因此要特别注重养阴，可以多选用百合、山药、莲子、枸杞等食物。\r\n对于“吃肝补肝”的说法，李志红表示，可以适量吃一些猪肝，但一定要保证饮食卫生。\r\n\r\n起居\r\n\r\n春天的三个月，是自然界万物复苏，各种生物欣欣向荣的季节。人们也要顺应自然界的规律，早睡早起，起床后要全身放松，在庭院中悠闲地散步以舒畅自己的情志。\r\n人们从冬季已经习惯了的“早睡晚起”，过渡到春季的“晚睡早起”要有一个逐渐适应的过程，不要太急于转变，而要顺应自然界的昼夜时间变化而逐步转变自己的睡眠习惯。\r\n\r\n锻炼\r\n\r\n春季的运动养生保健是恢复身体“元气”的最佳时节。由于寒冷的冬季限制人们的运动锻炼，使机体的体温调节中枢和内脏器官的功能，都有不同程度的减弱，特别是全身的肌肉和韧带，更需要锻炼以增强其运动功能。春季人们应该进行适当的运动，如散步、慢跑、体操、太极拳等，保持体内的生机，增强免疫力与抗病能力。\r\n不过，春天的气候呈现温差大、风大的特点，要注意防风御寒，因此在遇到强风时要适当地减少外出锻炼，以免风大伤肝。\r\n\r\n情志\r\n\r\n中医理论认为肝属木，与春季相应，生理特性为“喜条达而恶抑郁”，故有“大怒伤肝”之说。肝的生理特点是喜欢舒展、条畅的情绪而不喜欢抑郁、烦闷。\r\n专家指出，在春季保健重点是保持自己的心情舒畅，努力做到不着急、不生气、不发怒，以保证肝的舒畅条达。\r\n春季养生，情绪上要乐观，不宜抑郁或发怒，不要过分劳累，以免加重肝脏负担。有肝脏疾患的人，要做到心宽、心静。在繁忙浮躁和充满诱惑的尘世纷扰下，要做到“恬然不动其心”，就能保持机体内环境的稳定，防止心理疾病的发生。\r\n\r\n\r\n', '2019-03-10', 0);
INSERT INTO `news` VALUES (43, '热点新闻', '春季养生知多少-13', '春季养生是一种养生活动，即春季运动强身健体，应遵循养阳防风的原则。春季，人体阳气顺应自然，向上向外疏发，因此要注意保卫体内的阳气，凡有损阳气的情况都应避免。\r\n\r\n养肝为先\r\n\r\n肝脏是人体的一个重要器官，它具有调节气血，帮助脾胃消化食物、吸收营养的功能以及调畅情志、疏理气机的作用。因此，春季养肝得法，将带来整年的健康安寿。\r\n中医养生学理论认为“春与肝相应”，意思是说春季的气候特点与人体的肝脏有着密切的关系。早春时节，专家指出，养肝护肝要从饮食、起居、锻炼和情志四方面入手。\r\n\r\n饮食\r\n\r\n春季饮食要根据个人体质进行选择，普通健康人群不主张大量的进补。身体特别虚弱的人可以适量食用海参、冬虫夏草等补品。\r\n对于健康人群而言，春季饮食要注意清淡，不要过度食用干燥、辛辣的食物。\r\n同时，因为此时阳气上升容易伤阴，因此要特别注重养阴，可以多选用百合、山药、莲子、枸杞等食物。\r\n对于“吃肝补肝”的说法，李志红表示，可以适量吃一些猪肝，但一定要保证饮食卫生。\r\n\r\n起居\r\n\r\n春天的三个月，是自然界万物复苏，各种生物欣欣向荣的季节。人们也要顺应自然界的规律，早睡早起，起床后要全身放松，在庭院中悠闲地散步以舒畅自己的情志。\r\n人们从冬季已经习惯了的“早睡晚起”，过渡到春季的“晚睡早起”要有一个逐渐适应的过程，不要太急于转变，而要顺应自然界的昼夜时间变化而逐步转变自己的睡眠习惯。\r\n\r\n锻炼\r\n\r\n春季的运动养生保健是恢复身体“元气”的最佳时节。由于寒冷的冬季限制人们的运动锻炼，使机体的体温调节中枢和内脏器官的功能，都有不同程度的减弱，特别是全身的肌肉和韧带，更需要锻炼以增强其运动功能。春季人们应该进行适当的运动，如散步、慢跑、体操、太极拳等，保持体内的生机，增强免疫力与抗病能力。\r\n不过，春天的气候呈现温差大、风大的特点，要注意防风御寒，因此在遇到强风时要适当地减少外出锻炼，以免风大伤肝。\r\n\r\n情志\r\n\r\n中医理论认为肝属木，与春季相应，生理特性为“喜条达而恶抑郁”，故有“大怒伤肝”之说。肝的生理特点是喜欢舒展、条畅的情绪而不喜欢抑郁、烦闷。\r\n专家指出，在春季保健重点是保持自己的心情舒畅，努力做到不着急、不生气、不发怒，以保证肝的舒畅条达。\r\n春季养生，情绪上要乐观，不宜抑郁或发怒，不要过分劳累，以免加重肝脏负担。有肝脏疾患的人，要做到心宽、心静。在繁忙浮躁和充满诱惑的尘世纷扰下，要做到“恬然不动其心”，就能保持机体内环境的稳定，防止心理疾病的发生。\r\n\r\n\r\n', '2019-03-10', 2);
INSERT INTO `news` VALUES (44, '热点新闻', '春季养生知多少-14', '春季养生是一种养生活动，即春季运动强身健体，应遵循养阳防风的原则。春季，人体阳气顺应自然，向上向外疏发，因此要注意保卫体内的阳气，凡有损阳气的情况都应避免。\r\n\r\n养肝为先\r\n\r\n肝脏是人体的一个重要器官，它具有调节气血，帮助脾胃消化食物、吸收营养的功能以及调畅情志、疏理气机的作用。因此，春季养肝得法，将带来整年的健康安寿。\r\n中医养生学理论认为“春与肝相应”，意思是说春季的气候特点与人体的肝脏有着密切的关系。早春时节，专家指出，养肝护肝要从饮食、起居、锻炼和情志四方面入手。\r\n\r\n饮食\r\n\r\n春季饮食要根据个人体质进行选择，普通健康人群不主张大量的进补。身体特别虚弱的人可以适量食用海参、冬虫夏草等补品。\r\n对于健康人群而言，春季饮食要注意清淡，不要过度食用干燥、辛辣的食物。\r\n同时，因为此时阳气上升容易伤阴，因此要特别注重养阴，可以多选用百合、山药、莲子、枸杞等食物。\r\n对于“吃肝补肝”的说法，李志红表示，可以适量吃一些猪肝，但一定要保证饮食卫生。\r\n\r\n起居\r\n\r\n春天的三个月，是自然界万物复苏，各种生物欣欣向荣的季节。人们也要顺应自然界的规律，早睡早起，起床后要全身放松，在庭院中悠闲地散步以舒畅自己的情志。\r\n人们从冬季已经习惯了的“早睡晚起”，过渡到春季的“晚睡早起”要有一个逐渐适应的过程，不要太急于转变，而要顺应自然界的昼夜时间变化而逐步转变自己的睡眠习惯。\r\n\r\n锻炼\r\n\r\n春季的运动养生保健是恢复身体“元气”的最佳时节。由于寒冷的冬季限制人们的运动锻炼，使机体的体温调节中枢和内脏器官的功能，都有不同程度的减弱，特别是全身的肌肉和韧带，更需要锻炼以增强其运动功能。春季人们应该进行适当的运动，如散步、慢跑、体操、太极拳等，保持体内的生机，增强免疫力与抗病能力。\r\n不过，春天的气候呈现温差大、风大的特点，要注意防风御寒，因此在遇到强风时要适当地减少外出锻炼，以免风大伤肝。\r\n\r\n情志\r\n\r\n中医理论认为肝属木，与春季相应，生理特性为“喜条达而恶抑郁”，故有“大怒伤肝”之说。肝的生理特点是喜欢舒展、条畅的情绪而不喜欢抑郁、烦闷。\r\n专家指出，在春季保健重点是保持自己的心情舒畅，努力做到不着急、不生气、不发怒，以保证肝的舒畅条达。\r\n春季养生，情绪上要乐观，不宜抑郁或发怒，不要过分劳累，以免加重肝脏负担。有肝脏疾患的人，要做到心宽、心静。在繁忙浮躁和充满诱惑的尘世纷扰下，要做到“恬然不动其心”，就能保持机体内环境的稳定，防止心理疾病的发生。\r\n\r\n\r\n', '2019-03-10', 0);
INSERT INTO `news` VALUES (45, '热点新闻', '春季养生知多少-15', '春季养生是一种养生活动，即春季运动强身健体，应遵循养阳防风的原则。春季，人体阳气顺应自然，向上向外疏发，因此要注意保卫体内的阳气，凡有损阳气的情况都应避免。\r\n\r\n养肝为先\r\n\r\n肝脏是人体的一个重要器官，它具有调节气血，帮助脾胃消化食物、吸收营养的功能以及调畅情志、疏理气机的作用。因此，春季养肝得法，将带来整年的健康安寿。\r\n中医养生学理论认为“春与肝相应”，意思是说春季的气候特点与人体的肝脏有着密切的关系。早春时节，专家指出，养肝护肝要从饮食、起居、锻炼和情志四方面入手。\r\n\r\n饮食\r\n\r\n春季饮食要根据个人体质进行选择，普通健康人群不主张大量的进补。身体特别虚弱的人可以适量食用海参、冬虫夏草等补品。\r\n对于健康人群而言，春季饮食要注意清淡，不要过度食用干燥、辛辣的食物。\r\n同时，因为此时阳气上升容易伤阴，因此要特别注重养阴，可以多选用百合、山药、莲子、枸杞等食物。\r\n对于“吃肝补肝”的说法，李志红表示，可以适量吃一些猪肝，但一定要保证饮食卫生。\r\n\r\n起居\r\n\r\n春天的三个月，是自然界万物复苏，各种生物欣欣向荣的季节。人们也要顺应自然界的规律，早睡早起，起床后要全身放松，在庭院中悠闲地散步以舒畅自己的情志。\r\n人们从冬季已经习惯了的“早睡晚起”，过渡到春季的“晚睡早起”要有一个逐渐适应的过程，不要太急于转变，而要顺应自然界的昼夜时间变化而逐步转变自己的睡眠习惯。\r\n\r\n锻炼\r\n\r\n春季的运动养生保健是恢复身体“元气”的最佳时节。由于寒冷的冬季限制人们的运动锻炼，使机体的体温调节中枢和内脏器官的功能，都有不同程度的减弱，特别是全身的肌肉和韧带，更需要锻炼以增强其运动功能。春季人们应该进行适当的运动，如散步、慢跑、体操、太极拳等，保持体内的生机，增强免疫力与抗病能力。\r\n不过，春天的气候呈现温差大、风大的特点，要注意防风御寒，因此在遇到强风时要适当地减少外出锻炼，以免风大伤肝。\r\n\r\n情志\r\n\r\n中医理论认为肝属木，与春季相应，生理特性为“喜条达而恶抑郁”，故有“大怒伤肝”之说。肝的生理特点是喜欢舒展、条畅的情绪而不喜欢抑郁、烦闷。\r\n专家指出，在春季保健重点是保持自己的心情舒畅，努力做到不着急、不生气、不发怒，以保证肝的舒畅条达。\r\n春季养生，情绪上要乐观，不宜抑郁或发怒，不要过分劳累，以免加重肝脏负担。有肝脏疾患的人，要做到心宽、心静。在繁忙浮躁和充满诱惑的尘世纷扰下，要做到“恬然不动其心”，就能保持机体内环境的稳定，防止心理疾病的发生。\r\n\r\n\r\n', '2019-03-10', 4);
INSERT INTO `news` VALUES (46, '健康阅读', '多吃蔬菜的好处-15', '多吃蔬菜可以补充维生素、矿物质、纤维素。\r\n\r\n1.可以补充维生素\r\n\r\n很多蔬菜中都含有大量的维生素，比如青菜、番茄等，并且有利于我们对维生素的吸收，能有效的提高我们的免疫力。\r\n\r\n2.可以补充矿物质\r\n\r\n蔬菜中富含有钙、铁、铜等矿物质，这些物质能有效调节人体酸碱平衡，其中钙元素更是能促进人体生长，适合多吃。\r\n\r\n3.可以补充纤维素\r\n\r\n蔬菜中也含有大量的纤维素，纤维素能刺激胃液分泌和肠道蠕动，增加食物与消化液的接触面积，有助于人体消化吸收食物，促进代谢废物排出，并防止便秘。\r\n\r\n扩展资料：\r\n\r\n吃蔬菜的禁忌：\r\n1、餐前吃西红柿\r\n\r\n我们在吃完饭之后，一定要注意不要立刻使用西红柿，因为西红柿中含有很多的胃酸，这种物质与食物相混合之后会降低十五中的酸度，严重的话有可能会造成胃扩张。\r\n\r\n2、吃未炒熟的豆芽菜\r\n\r\n豆芽这种蔬菜在食用前一定要炒熟，因为不熟的豆芽牙尖上会有一些有毒物质，进入人体之后，有可能造成人体中毒。豆还会出现恶心、呕吐、腹泻、头晕等不适反应。\r\n\r\n3、频繁吃菠菜\r\n\r\n因为菠菜中含有大量的草酸，我们食用过量的话，这些草酸会严重影响我们对营养的吸收，还会造成人体内酸过量，不宜多吃，以免造成更严重的问题。\r\n参考资料：人民网：多吃蔬菜会带来哪些好处？', '2019-03-15', 10);
INSERT INTO `news` VALUES (47, '最新消息', '关于合理膳食的最新建议-16', '合理膳食也叫平衡膳食，是指按照不同年龄、身体活动和能量的需要设置的膳食模式，这个模式推荐的食物种类、数量和比例，能最大程度地满足不同年龄阶段、不同能量水平的健康人群的营养与健康需要。\r\n\r\n中国居民膳食指南\r\n\r\n一 食物多样，谷类为主\r\n\r\n二 吃动平衡，健康体重\r\n\r\n三 多吃蔬果、奶类、大豆\r\n\r\n四 适量吃鱼、禽、蛋、瘦肉\r\n\r\n五 少盐少油，控糖限酒\r\n\r\n六 杜绝浪费，兴新食尚\r\n\r\n01\r\n\r\n食物多样，谷类为主\r\n\r\n每天的膳食应包括谷薯类、蔬菜水果类、畜禽鱼蛋奶类、大豆坚果类等食物。建议平均每天摄入12种以上食物，每周25种以上。谷类为主是平衡膳食模式的重要特征，每天摄入谷薯类食物250-400克，其中全谷物和杂豆类50-150克，薯类50-100克；膳食中碳水化合物提供的能量应占总能量的50%以上。\r\n\r\n02\r\n\r\n吃动平衡，健康体重\r\n\r\n各个年龄段人群都应该坚持天天运动、维持能量平衡、保持健康体重。体重过低和过高均易增加疾病的发生风险。推荐每周应至少进行5天中等强度身体活动，累计150分钟以上；坚持日常身体活动，平均每天主动身体活动6000步；尽量减少久坐时间，每小时起来动一动，动则有益。\r\n\r\n03\r\n\r\n多吃蔬果、奶类、大豆\r\n\r\n蔬菜、水果、奶类和大豆及制品是平衡膳食的重要组成部分，坚果是膳食的有益补充。蔬菜和水果是维生素、矿物质、膳食纤维和植物化学物的重要来源，奶类和大豆类富含钙、优质蛋白质和B族维生素，对降低慢性病的发病风险具有重要作用。提倡餐餐有蔬菜，推荐每天摄入300-500克，深色蔬菜应占1/2。天天吃水果，推荐每天摄入200-350克的新鲜水果，果汁不能代替鲜果。吃各种奶制品，摄入量相当于每天液态奶300克。经常吃豆制品，每天相当于大豆25 克以上，适量吃坚果。\r\n\r\n04\r\n\r\n适量吃鱼、禽、蛋、瘦肉\r\n\r\n鱼、禽、蛋和瘦肉可提供人体所需要的优质蛋白质、维生素A、B族维生素等，有些也含有较高的脂肪和胆固醇。动物性食物优选鱼和禽类，鱼和禽类脂肪含量相对较低，鱼类含有较多的不饱和脂肪酸；蛋类各种营养成分齐全；吃畜肉应选择瘦肉，瘦肉脂肪含量较低。过多食用烟熏和腌制肉类可增加肿瘤的发生风险，应当少吃。推荐每周吃鱼280-525克，畜禽肉280-525克，蛋类280-350克，平均每天摄入鱼、禽、蛋和瘦肉总量120-200克。', '2019-04-02', 8);

-- ----------------------------
-- Table structure for object
-- ----------------------------
DROP TABLE IF EXISTS `object`;
CREATE TABLE `object`  (
  `objectId` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `userId` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `objectName` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `pwd` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `sex` char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `birthDate` date NOT NULL,
  `registerDate` date NOT NULL,
  `objectTel` varchar(28) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `lastLoginTime` datetime(0) NULL DEFAULT NULL,
  `lastLogoutTime` datetime(0) NULL DEFAULT NULL,
  `loginState` bit(1) NOT NULL,
  PRIMARY KEY (`objectId`) USING BTREE,
  INDEX `object_user__fk`(`userId`) USING BTREE,
  CONSTRAINT `object_user__fk` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of object
-- ----------------------------
INSERT INTO `object` VALUES ('hitwh001', 'hitwh001', '哈工大威海1号', '12345abc', '男', '1993-11-25', '2019-01-17', '18463100658', '2019-03-28 02:19:01', NULL, b'0');
INSERT INTO `object` VALUES ('hitwh002', 'hitwh002', '哈工大威海2号', '12345abc', '男', '1993-11-25', '2019-01-17', '18463100658', '2019-03-28 02:19:01', NULL, b'0');
INSERT INTO `object` VALUES ('hitwh003', 'hitwh003', '哈工大威海3号', '12345abc', '男', '1993-11-25', '2019-01-17', '18463100658', '2019-03-28 02:19:01', NULL, b'0');
INSERT INTO `object` VALUES ('hitwh004', 'hitwh004', '哈工大威海4号', '12345abc', '男', '1993-11-25', '2019-01-17', '18463100658', '2019-03-28 02:19:01', NULL, b'0');
INSERT INTO `object` VALUES ('hitwh005', 'hitwh005', '哈工大威海5号', '12345abc', '男', '1993-11-25', '2019-01-17', '18463100658', '2019-03-28 02:19:01', NULL, b'0');
INSERT INTO `object` VALUES ('hitwh006', 'hitwh006', '哈工大威海6号', '12345abc', '男', '1993-11-25', '2019-01-17', '18463100658', '2019-03-28 02:19:01', NULL, b'0');
INSERT INTO `object` VALUES ('hitwh007', 'hitwh007', '哈工大威海7号', '12345abc', '男', '1993-11-25', '2019-01-17', '18463100658', '2019-03-28 02:19:01', NULL, b'0');
INSERT INTO `object` VALUES ('hitwh008', 'hitwh008', '哈工大威海8号', '12345abc', '男', '1993-11-25', '2019-01-17', '18463100658', '2019-03-28 02:19:01', NULL, b'0');
INSERT INTO `object` VALUES ('hitwh009', 'hitwh009', '哈工大威海9号', '12345abc', '男', '1993-11-25', '2019-01-17', '18463100658', '2019-03-28 02:19:01', NULL, b'0');
INSERT INTO `object` VALUES ('hitwh010', 'hitwh010', '哈工大威海10号', '12345abc', '男', '1993-11-25', '2019-01-17', '18463100658', '2019-03-28 02:19:01', NULL, b'0');
INSERT INTO `object` VALUES ('hitwh011', 'hitwh011', '哈工大威海11号', '12345abc', '男', '1993-11-25', '2019-01-17', '18463100658', '2019-03-28 02:19:01', NULL, b'0');
INSERT INTO `object` VALUES ('hitwh012', 'hitwh012', '哈工大威海12号', '12345abc', '男', '1993-11-25', '2019-01-17', '18463100658', '2019-03-28 02:19:01', NULL, b'0');
INSERT INTO `object` VALUES ('hitwh013', 'hitwh013', '哈工大威海13号', '12345abc', '男', '1993-11-25', '2019-01-17', '18463100658', '2019-03-28 02:19:01', NULL, b'0');
INSERT INTO `object` VALUES ('hitwh014', 'hitwh014', '哈工大威海14号', '12345abc', '男', '1993-11-25', '2019-01-17', '18463100658', '2019-03-28 02:19:01', NULL, b'0');
INSERT INTO `object` VALUES ('hitwh015', 'hitwh015', '哈工大威海15号', '12345abc', '男', '1993-11-25', '2019-01-17', '18463100658', '2019-03-28 02:19:01', NULL, b'0');
INSERT INTO `object` VALUES ('hitwh016', 'hitwh016', '哈工大威海16号', '12345abc', '男', '1993-11-26', '2019-03-28', '18463100658', '2019-03-28 02:19:01', NULL, b'0');
INSERT INTO `object` VALUES ('hitwhob001', 'hitwh001', '哈工大威海_监测对象1号', '12345abc', '女', '1978-05-22', '2019-02-20', '13371184957', '2019-10-16 20:39:14', '2019-10-12 10:35:01', b'1');
INSERT INTO `object` VALUES ('hitwhob002', 'hitwh001', '哈工大威海_监测对象2号', '12345abc', '男', '1978-03-11', '2019-02-20', '13371184957', '2019-09-22 12:29:35', '2019-09-22 11:24:08', b'1');
INSERT INTO `object` VALUES ('hitwhob003', 'hitwh001', '哈工大威海_监测对象3号', '12345abc', '男', '1978-03-01', '2019-02-20', '13371184957', '2019-05-30 20:35:56', '2019-05-30 20:36:10', b'0');
INSERT INTO `object` VALUES ('hitwhob004', 'hitwh001', '哈工大威海_监测对象4号', '12345abc', '男', '1978-03-01', '2019-02-20', '13371184957', '2019-03-28 02:19:01', NULL, b'0');
INSERT INTO `object` VALUES ('hitwhob005', 'hitwh001', '哈工大威海_监测对象5号', '12345abc', '男', '1978-03-11', '2019-02-20', '13371184957', '2019-03-28 02:19:01', NULL, b'0');
INSERT INTO `object` VALUES ('hitwhob006', 'hitwh002', '哈工大威海_监测对象6号', '12345abc', '男', '1978-03-01', '2019-02-20', '13371184957', '2019-03-28 02:19:01', NULL, b'0');
INSERT INTO `object` VALUES ('hitwhob007', 'hitwh002', '哈工大威海_监测对象7号', '12345abc', '男', '1978-03-01', '2019-02-20', '13371184957', '2019-03-28 02:19:01', NULL, b'0');
INSERT INTO `object` VALUES ('hitwhob008', 'hitwh003', '哈工大威海_监测对象8号', '12345abc', '男', '1978-03-01', '2019-02-20', '13371184957', '2019-03-28 02:19:01', NULL, b'0');
INSERT INTO `object` VALUES ('hitwhob009', 'hitwh001', '哈工大威海_监测对象9号', '12345abc', '男', '1978-05-22', '2019-02-20', '13371184959', '2019-03-28 02:19:01', NULL, b'0');
INSERT INTO `object` VALUES ('hitwhob010', 'hitwh001', '哈工大威海_监测对象10号', '12345abc', '男', '1978-03-11', '2019-02-20', '13371184957', '2019-03-28 02:19:01', NULL, b'0');
INSERT INTO `object` VALUES ('hitwhob011', 'hitwh001', '哈工大威海_监测对象11号', '12345abc', '男', '1978-03-01', '2019-02-20', '13371184957', '2019-03-28 02:19:01', NULL, b'0');
INSERT INTO `object` VALUES ('hitwhob012', 'hitwh001', '哈工大威海_监测对象12号', '12345abc', '男', '1978-03-01', '2019-02-20', '13371184957', '2019-03-28 02:19:01', NULL, b'0');
INSERT INTO `object` VALUES ('hitwhob013', 'hitwh001', '哈工大威海_监测对象13号', '12345abc', '男', '1978-03-11', '2019-02-20', '13371184957', '2019-03-28 02:19:01', NULL, b'0');
INSERT INTO `object` VALUES ('hitwhob014', 'hitwh001', '哈工大威海_监测对象14号', '12345abc', '男', '1978-05-22', '2019-02-20', '13371184959', '2019-03-28 02:19:01', NULL, b'0');
INSERT INTO `object` VALUES ('hitwhob015', 'hitwh001', '哈工大威海_监测对象15号', '12345abc', '男', '1978-03-11', '2019-02-20', '13371184957', '2019-03-28 02:19:01', NULL, b'0');
INSERT INTO `object` VALUES ('hitwhob016', 'hitwh001', '哈工大威海_监测对象16号', '12345abc', '男', '1978-03-01', '2019-02-20', '13371184957', '2019-03-28 02:19:01', NULL, b'0');
INSERT INTO `object` VALUES ('hitwhob017', 'hitwh001', '哈工大威海_监测对象17号', '12345abc', '男', '1978-03-01', '2019-02-20', '13371184957', '2019-03-28 02:19:01', NULL, b'0');
INSERT INTO `object` VALUES ('hitwhob018', 'hitwh001', '哈工大威海_监测对象18号', '12345abc', '男', '1978-03-11', '2019-02-20', '13371184957', '2019-03-28 02:19:01', NULL, b'0');
INSERT INTO `object` VALUES ('hitwhob019', 'hitwh001', '哈工大威海_监测对象十九号', '12345abc', '女', '1998-01-22', '2019-06-13', '13371184958', NULL, NULL, b'0');
INSERT INTO `object` VALUES ('hitwhob020', 'hitwh001', '哈工大威海监测对象20号', '12345abc', '男', '1993-11-28', '2019-06-22', '13371184957', NULL, NULL, b'0');

-- ----------------------------
-- Table structure for objectLog
-- ----------------------------
DROP TABLE IF EXISTS `objectLog`;
CREATE TABLE `objectLog`  (
  `objectLogId` bigint(20) NOT NULL AUTO_INCREMENT,
  `objectId` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `logType` varchar(12) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `writeTime` datetime(0) NOT NULL,
  `detail` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`objectLogId`) USING BTREE,
  INDEX `objectLog_object__fk`(`objectId`) USING BTREE,
  CONSTRAINT `objectLog_object__fk` FOREIGN KEY (`objectId`) REFERENCES `object` (`objectId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 333 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of objectLog
-- ----------------------------
INSERT INTO `objectLog` VALUES (1, 'hitwhob001', 'login', '2019-02-27 22:19:00', NULL);
INSERT INTO `objectLog` VALUES (2, 'hitwhob001', 'login', '2019-02-28 11:43:07', NULL);
INSERT INTO `objectLog` VALUES (3, 'hitwhob001', 'login', '2019-02-28 14:01:29', NULL);
INSERT INTO `objectLog` VALUES (4, 'hitwhob001', 'login', '2019-03-04 15:34:20', NULL);
INSERT INTO `objectLog` VALUES (5, 'hitwhob001', 'login', '2019-03-07 19:07:59', NULL);
INSERT INTO `objectLog` VALUES (6, 'hitwhob001', 'login', '2019-03-07 19:10:37', NULL);
INSERT INTO `objectLog` VALUES (7, 'hitwhob001', 'login', '2019-03-07 19:15:20', NULL);
INSERT INTO `objectLog` VALUES (8, 'hitwhob001', 'login', '2019-03-07 19:19:13', NULL);
INSERT INTO `objectLog` VALUES (9, 'hitwhob001', 'login', '2019-03-07 20:14:12', NULL);
INSERT INTO `objectLog` VALUES (10, 'hitwhob001', 'login', '2019-03-07 20:35:09', NULL);
INSERT INTO `objectLog` VALUES (11, 'hitwhob001', 'login', '2019-03-07 20:36:44', NULL);
INSERT INTO `objectLog` VALUES (12, 'hitwhob001', 'login', '2019-03-07 20:40:44', NULL);
INSERT INTO `objectLog` VALUES (13, 'hitwhob001', 'login', '2019-03-07 20:41:25', NULL);
INSERT INTO `objectLog` VALUES (14, 'hitwhob001', 'login', '2019-03-07 21:12:29', NULL);
INSERT INTO `objectLog` VALUES (15, 'hitwhob001', 'login', '2019-03-07 21:17:38', NULL);
INSERT INTO `objectLog` VALUES (16, 'hitwhob001', 'login', '2019-03-07 21:40:33', NULL);
INSERT INTO `objectLog` VALUES (17, 'hitwhob001', 'login', '2019-03-07 21:43:25', NULL);
INSERT INTO `objectLog` VALUES (18, 'hitwhob001', 'login', '2019-03-08 11:32:10', NULL);
INSERT INTO `objectLog` VALUES (19, 'hitwhob001', 'login', '2019-03-08 20:51:09', NULL);
INSERT INTO `objectLog` VALUES (20, 'hitwhob001', 'login', '2019-03-10 12:16:51', NULL);
INSERT INTO `objectLog` VALUES (21, 'hitwhob001', 'login', '2019-03-12 11:44:25', NULL);
INSERT INTO `objectLog` VALUES (22, 'hitwhob001', 'login', '2019-03-12 11:47:06', NULL);
INSERT INTO `objectLog` VALUES (23, 'hitwhob001', 'login', '2019-03-12 14:44:10', NULL);
INSERT INTO `objectLog` VALUES (24, 'hitwhob001', 'login', '2019-03-12 14:55:15', NULL);
INSERT INTO `objectLog` VALUES (25, 'hitwhob001', 'login', '2019-03-14 20:27:18', NULL);
INSERT INTO `objectLog` VALUES (26, 'hitwhob001', 'login', '2019-03-15 15:08:05', NULL);
INSERT INTO `objectLog` VALUES (27, 'hitwhob001', 'login', '2019-03-17 20:14:20', NULL);
INSERT INTO `objectLog` VALUES (28, 'hitwhob001', 'login', '2019-03-17 21:06:05', NULL);
INSERT INTO `objectLog` VALUES (29, 'hitwhob001', 'login', '2019-03-18 09:54:14', NULL);
INSERT INTO `objectLog` VALUES (30, 'hitwhob001', 'login', '2019-03-27 12:19:01', NULL);
INSERT INTO `objectLog` VALUES (31, 'hitwhob001', 'login', '2019-03-27 21:22:05', NULL);
INSERT INTO `objectLog` VALUES (32, 'hitwhob001', 'login', '2019-03-28 15:04:52', NULL);
INSERT INTO `objectLog` VALUES (33, 'hitwhob001', 'login', '2019-03-29 22:12:36', NULL);
INSERT INTO `objectLog` VALUES (34, 'hitwhob001', 'login', '2019-03-30 15:29:26', NULL);
INSERT INTO `objectLog` VALUES (35, 'hitwhob001', 'login', '2019-03-30 22:20:15', NULL);
INSERT INTO `objectLog` VALUES (36, 'hitwhob001', 'login', '2019-03-31 09:36:15', NULL);
INSERT INTO `objectLog` VALUES (37, 'hitwhob001', 'login', '2019-04-02 21:41:08', NULL);
INSERT INTO `objectLog` VALUES (38, 'hitwhob001', 'login', '2019-04-03 14:27:32', NULL);
INSERT INTO `objectLog` VALUES (39, 'hitwhob001', 'login', '2019-04-03 14:27:57', NULL);
INSERT INTO `objectLog` VALUES (40, 'hitwhob001', 'login', '2019-04-21 21:48:19', NULL);
INSERT INTO `objectLog` VALUES (41, 'hitwhob001', 'login', '2019-04-21 21:52:15', NULL);
INSERT INTO `objectLog` VALUES (42, 'hitwhob001', 'logout', '2019-04-21 21:52:39', NULL);
INSERT INTO `objectLog` VALUES (43, 'hitwhob001', 'login', '2019-04-22 18:38:18', NULL);
INSERT INTO `objectLog` VALUES (44, 'hitwhob002', 'login', '2019-04-22 18:38:21', NULL);
INSERT INTO `objectLog` VALUES (45, 'hitwhob002', 'logout', '2019-04-22 18:38:34', NULL);
INSERT INTO `objectLog` VALUES (46, 'hitwhob001', 'login', '2019-04-22 18:45:02', NULL);
INSERT INTO `objectLog` VALUES (47, 'hitwhob002', 'login', '2019-04-22 18:45:26', NULL);
INSERT INTO `objectLog` VALUES (48, 'hitwhob002', 'logout', '2019-04-22 18:45:46', NULL);
INSERT INTO `objectLog` VALUES (49, 'hitwhob001', 'logout', '2019-04-22 18:46:38', NULL);
INSERT INTO `objectLog` VALUES (50, 'hitwhob001', 'login', '2019-04-22 21:13:36', NULL);
INSERT INTO `objectLog` VALUES (51, 'hitwhob001', 'logout', '2019-04-22 21:13:53', NULL);
INSERT INTO `objectLog` VALUES (52, 'hitwhob001', 'login', '2019-04-22 21:42:36', NULL);
INSERT INTO `objectLog` VALUES (53, 'hitwhob001', 'login', '2019-04-25 14:46:36', NULL);
INSERT INTO `objectLog` VALUES (54, 'hitwhob001', 'logout', '2019-04-25 14:49:32', NULL);
INSERT INTO `objectLog` VALUES (55, 'hitwhob001', 'login', '2019-04-25 15:34:56', NULL);
INSERT INTO `objectLog` VALUES (56, 'hitwhob001', 'login', '2019-04-25 16:43:24', NULL);
INSERT INTO `objectLog` VALUES (57, 'hitwhob002', 'login', '2019-04-25 16:43:49', NULL);
INSERT INTO `objectLog` VALUES (58, 'hitwhob002', 'logout', '2019-04-25 16:43:58', NULL);
INSERT INTO `objectLog` VALUES (59, 'hitwhob001', 'login', '2019-04-25 18:16:35', NULL);
INSERT INTO `objectLog` VALUES (60, 'hitwhob001', 'login', '2019-04-25 18:19:01', NULL);
INSERT INTO `objectLog` VALUES (61, 'hitwhob001', 'logout', '2019-04-25 18:19:48', NULL);
INSERT INTO `objectLog` VALUES (62, 'hitwhob001', 'login', '2019-04-25 18:21:01', NULL);
INSERT INTO `objectLog` VALUES (63, 'hitwhob001', 'logout', '2019-04-25 18:21:29', NULL);
INSERT INTO `objectLog` VALUES (64, 'hitwhob001', 'login', '2019-04-25 18:33:48', NULL);
INSERT INTO `objectLog` VALUES (65, 'hitwhob001', 'logout', '2019-04-25 18:35:03', NULL);
INSERT INTO `objectLog` VALUES (66, 'hitwhob001', 'login', '2019-04-25 18:39:20', NULL);
INSERT INTO `objectLog` VALUES (67, 'hitwhob001', 'logout', '2019-04-25 18:40:09', NULL);
INSERT INTO `objectLog` VALUES (68, 'hitwhob001', 'logout', '2019-04-25 18:40:05', NULL);
INSERT INTO `objectLog` VALUES (69, 'hitwhob001', 'logout', '2019-04-25 18:40:02', NULL);
INSERT INTO `objectLog` VALUES (70, 'hitwhob001', 'login', '2019-04-25 18:40:32', NULL);
INSERT INTO `objectLog` VALUES (71, 'hitwhob001', 'logout', '2019-04-25 18:44:54', NULL);
INSERT INTO `objectLog` VALUES (72, 'hitwhob001', 'login', '2019-04-25 18:57:30', NULL);
INSERT INTO `objectLog` VALUES (73, 'hitwhob001', 'login', '2019-04-26 17:54:45', NULL);
INSERT INTO `objectLog` VALUES (74, 'hitwhob001', 'login', '2019-04-26 17:59:00', NULL);
INSERT INTO `objectLog` VALUES (75, 'hitwhob001', 'login', '2019-04-26 18:09:47', NULL);
INSERT INTO `objectLog` VALUES (76, 'hitwhob001', 'login', '2019-04-26 18:13:25', NULL);
INSERT INTO `objectLog` VALUES (77, 'hitwhob001', 'logout', '2019-04-26 18:13:33', NULL);
INSERT INTO `objectLog` VALUES (78, 'hitwhob001', 'login', '2019-04-26 18:15:19', NULL);
INSERT INTO `objectLog` VALUES (79, 'hitwhob001', 'logout', '2019-04-26 18:15:58', NULL);
INSERT INTO `objectLog` VALUES (80, 'hitwhob001', 'login', '2019-04-26 18:27:10', NULL);
INSERT INTO `objectLog` VALUES (81, 'hitwhob001', 'logout', '2019-04-26 18:28:02', NULL);
INSERT INTO `objectLog` VALUES (82, 'hitwhob001', 'login', '2019-04-26 18:29:52', NULL);
INSERT INTO `objectLog` VALUES (83, 'hitwhob001', 'login', '2019-04-26 18:32:05', NULL);
INSERT INTO `objectLog` VALUES (84, 'hitwhob001', 'login', '2019-04-26 18:38:27', NULL);
INSERT INTO `objectLog` VALUES (85, 'hitwhob001', 'logout', '2019-04-26 18:39:41', NULL);
INSERT INTO `objectLog` VALUES (86, 'hitwhob001', 'login', '2019-04-26 19:25:58', NULL);
INSERT INTO `objectLog` VALUES (87, 'hitwhob001', 'logout', '2019-04-26 19:26:46', NULL);
INSERT INTO `objectLog` VALUES (88, 'hitwhob001', 'login', '2019-04-26 19:31:18', NULL);
INSERT INTO `objectLog` VALUES (89, 'hitwhob001', 'logout', '2019-04-26 19:33:24', NULL);
INSERT INTO `objectLog` VALUES (90, 'hitwhob001', 'login', '2019-04-26 19:33:35', NULL);
INSERT INTO `objectLog` VALUES (91, 'hitwhob001', 'logout', '2019-04-26 19:37:20', NULL);
INSERT INTO `objectLog` VALUES (92, 'hitwhob001', 'login', '2019-04-26 20:19:16', NULL);
INSERT INTO `objectLog` VALUES (93, 'hitwhob001', 'logout', '2019-04-26 20:21:31', NULL);
INSERT INTO `objectLog` VALUES (94, 'hitwhob001', 'login', '2019-04-26 20:36:34', NULL);
INSERT INTO `objectLog` VALUES (95, 'hitwhob001', 'logout', '2019-04-26 20:43:01', NULL);
INSERT INTO `objectLog` VALUES (96, 'hitwhob001', 'login', '2019-04-26 21:47:00', NULL);
INSERT INTO `objectLog` VALUES (97, 'hitwhob001', 'logout', '2019-04-26 21:47:16', NULL);
INSERT INTO `objectLog` VALUES (98, 'hitwhob001', 'login', '2019-04-27 07:56:35', NULL);
INSERT INTO `objectLog` VALUES (99, 'hitwhob001', 'logout', '2019-04-27 07:56:37', NULL);
INSERT INTO `objectLog` VALUES (100, 'hitwhob001', 'login', '2019-04-27 07:56:50', NULL);
INSERT INTO `objectLog` VALUES (101, 'hitwhob001', 'logout', '2019-04-27 07:58:19', NULL);
INSERT INTO `objectLog` VALUES (102, 'hitwhob001', 'login', '2019-04-27 08:17:35', NULL);
INSERT INTO `objectLog` VALUES (103, 'hitwhob001', 'logout', '2019-04-27 08:20:45', NULL);
INSERT INTO `objectLog` VALUES (104, 'hitwhob001', 'login', '2019-04-27 08:33:04', NULL);
INSERT INTO `objectLog` VALUES (105, 'hitwhob001', 'logout', '2019-04-27 08:37:06', NULL);
INSERT INTO `objectLog` VALUES (106, 'hitwhob001', 'login', '2019-04-27 09:14:14', NULL);
INSERT INTO `objectLog` VALUES (107, 'hitwhob001', 'logout', '2019-04-27 09:16:22', NULL);
INSERT INTO `objectLog` VALUES (108, 'hitwhob001', 'login', '2019-04-27 09:18:50', NULL);
INSERT INTO `objectLog` VALUES (109, 'hitwhob001', 'logout', '2019-04-27 09:18:58', NULL);
INSERT INTO `objectLog` VALUES (110, 'hitwhob001', 'login', '2019-04-27 09:40:38', NULL);
INSERT INTO `objectLog` VALUES (111, 'hitwhob001', 'logout', '2019-04-27 09:42:48', NULL);
INSERT INTO `objectLog` VALUES (112, 'hitwhob001', 'login', '2019-04-27 09:46:23', NULL);
INSERT INTO `objectLog` VALUES (113, 'hitwhob001', 'logout', '2019-04-27 09:48:03', NULL);
INSERT INTO `objectLog` VALUES (114, 'hitwhob001', 'login', '2019-04-27 09:49:23', NULL);
INSERT INTO `objectLog` VALUES (115, 'hitwhob001', 'logout', '2019-04-27 09:55:52', NULL);
INSERT INTO `objectLog` VALUES (116, 'hitwhob001', 'login', '2019-04-27 09:57:58', NULL);
INSERT INTO `objectLog` VALUES (117, 'hitwhob001', 'logout', '2019-04-27 09:58:16', NULL);
INSERT INTO `objectLog` VALUES (118, 'hitwhob001', 'login', '2019-05-24 16:51:04', NULL);
INSERT INTO `objectLog` VALUES (119, 'hitwhob001', 'logout', '2019-05-24 16:53:15', NULL);
INSERT INTO `objectLog` VALUES (120, 'hitwhob001', 'login', '2019-05-25 14:11:38', NULL);
INSERT INTO `objectLog` VALUES (121, 'hitwhob001', 'logout', '2019-05-25 14:18:10', NULL);
INSERT INTO `objectLog` VALUES (122, 'hitwhob001', 'login', '2019-05-25 15:49:10', NULL);
INSERT INTO `objectLog` VALUES (123, 'hitwhob001', 'logout', '2019-05-25 15:49:31', NULL);
INSERT INTO `objectLog` VALUES (124, 'hitwhob001', 'login', '2019-05-25 16:37:14', NULL);
INSERT INTO `objectLog` VALUES (125, 'hitwhob001', 'logout', '2019-05-25 16:40:51', NULL);
INSERT INTO `objectLog` VALUES (126, 'hitwhob001', 'login', '2019-05-25 16:41:43', NULL);
INSERT INTO `objectLog` VALUES (127, 'hitwhob001', 'logout', '2019-05-25 16:42:46', NULL);
INSERT INTO `objectLog` VALUES (128, 'hitwhob001', 'login', '2019-05-25 19:04:24', NULL);
INSERT INTO `objectLog` VALUES (129, 'hitwhob001', 'login', '2019-05-25 19:29:36', NULL);
INSERT INTO `objectLog` VALUES (130, 'hitwhob001', 'logout', '2019-05-25 19:31:57', NULL);
INSERT INTO `objectLog` VALUES (131, 'hitwhob001', 'login', '2019-05-25 21:18:14', NULL);
INSERT INTO `objectLog` VALUES (132, 'hitwhob001', 'logout', '2019-05-25 21:20:02', NULL);
INSERT INTO `objectLog` VALUES (133, 'hitwhob001', 'login', '2019-05-26 16:34:57', NULL);
INSERT INTO `objectLog` VALUES (134, 'hitwhob001', 'logout', '2019-05-26 16:38:03', NULL);
INSERT INTO `objectLog` VALUES (135, 'hitwhob001', 'login', '2019-05-27 09:31:48', NULL);
INSERT INTO `objectLog` VALUES (136, 'hitwhob001', 'logout', '2019-05-27 09:33:40', NULL);
INSERT INTO `objectLog` VALUES (137, 'hitwhob001', 'login', '2019-05-27 14:03:21', NULL);
INSERT INTO `objectLog` VALUES (138, 'hitwhob001', 'logout', '2019-05-27 14:06:45', NULL);
INSERT INTO `objectLog` VALUES (139, 'hitwhob001', 'login', '2019-05-27 14:07:02', NULL);
INSERT INTO `objectLog` VALUES (140, 'hitwhob001', 'logout', '2019-05-27 14:13:26', NULL);
INSERT INTO `objectLog` VALUES (141, 'hitwhob001', 'login', '2019-05-27 14:13:42', NULL);
INSERT INTO `objectLog` VALUES (142, 'hitwhob001', 'logout', '2019-05-27 14:13:46', NULL);
INSERT INTO `objectLog` VALUES (143, 'hitwhob001', 'login', '2019-05-27 14:59:29', NULL);
INSERT INTO `objectLog` VALUES (144, 'hitwhob001', 'login', '2019-05-27 15:12:01', NULL);
INSERT INTO `objectLog` VALUES (145, 'hitwhob001', 'login', '2019-05-27 15:20:46', NULL);
INSERT INTO `objectLog` VALUES (146, 'hitwhob001', 'login', '2019-05-27 15:45:50', NULL);
INSERT INTO `objectLog` VALUES (147, 'hitwhob001', 'login', '2019-05-27 15:55:48', NULL);
INSERT INTO `objectLog` VALUES (148, 'hitwhob001', 'logout', '2019-05-27 16:03:10', NULL);
INSERT INTO `objectLog` VALUES (149, 'hitwhob001', 'login', '2019-05-27 19:15:16', NULL);
INSERT INTO `objectLog` VALUES (150, 'hitwhob001', 'logout', '2019-05-27 19:18:45', NULL);
INSERT INTO `objectLog` VALUES (151, 'hitwhob001', 'login', '2019-05-29 18:28:29', NULL);
INSERT INTO `objectLog` VALUES (152, 'hitwhob001', 'logout', '2019-05-29 18:33:06', NULL);
INSERT INTO `objectLog` VALUES (153, 'hitwhob001', 'login', '2019-05-29 18:50:11', NULL);
INSERT INTO `objectLog` VALUES (154, 'hitwhob001', 'logout', '2019-05-29 18:54:24', NULL);
INSERT INTO `objectLog` VALUES (155, 'hitwhob001', 'login', '2019-05-29 18:57:46', NULL);
INSERT INTO `objectLog` VALUES (156, 'hitwhob001', 'logout', '2019-05-29 19:01:11', NULL);
INSERT INTO `objectLog` VALUES (157, 'hitwhob001', 'login', '2019-05-29 19:20:34', NULL);
INSERT INTO `objectLog` VALUES (158, 'hitwhob001', 'login', '2019-05-29 19:24:07', NULL);
INSERT INTO `objectLog` VALUES (159, 'hitwhob001', 'logout', '2019-05-29 19:24:49', NULL);
INSERT INTO `objectLog` VALUES (160, 'hitwhob001', 'login', '2019-05-29 20:50:20', NULL);
INSERT INTO `objectLog` VALUES (161, 'hitwhob001', 'logout', '2019-05-29 20:50:53', NULL);
INSERT INTO `objectLog` VALUES (162, 'hitwhob001', 'login', '2019-05-29 20:52:46', NULL);
INSERT INTO `objectLog` VALUES (163, 'hitwhob001', 'logout', '2019-05-29 20:53:27', NULL);
INSERT INTO `objectLog` VALUES (164, 'hitwhob001', 'login', '2019-05-29 20:54:45', NULL);
INSERT INTO `objectLog` VALUES (165, 'hitwhob001', 'logout', '2019-05-29 20:55:07', NULL);
INSERT INTO `objectLog` VALUES (166, 'hitwhob001', 'login', '2019-05-29 20:55:31', NULL);
INSERT INTO `objectLog` VALUES (167, 'hitwhob001', 'logout', '2019-05-29 20:55:53', NULL);
INSERT INTO `objectLog` VALUES (168, 'hitwhob001', 'login', '2019-05-29 21:58:34', NULL);
INSERT INTO `objectLog` VALUES (169, 'hitwhob001', 'logout', '2019-05-29 21:59:20', NULL);
INSERT INTO `objectLog` VALUES (170, 'hitwhob001', 'login', '2019-05-29 22:15:13', NULL);
INSERT INTO `objectLog` VALUES (171, 'hitwhob001', 'login', '2019-05-29 22:15:15', NULL);
INSERT INTO `objectLog` VALUES (172, 'hitwhob001', 'logout', '2019-05-29 22:17:45', NULL);
INSERT INTO `objectLog` VALUES (173, 'hitwhob001', 'login', '2019-05-29 22:17:53', NULL);
INSERT INTO `objectLog` VALUES (174, 'hitwhob001', 'logout', '2019-05-29 22:18:24', NULL);
INSERT INTO `objectLog` VALUES (175, 'hitwhob001', 'login', '2019-05-29 22:22:50', NULL);
INSERT INTO `objectLog` VALUES (176, 'hitwhob001', 'logout', '2019-05-29 22:23:27', NULL);
INSERT INTO `objectLog` VALUES (177, 'hitwhob001', 'login', '2019-05-29 22:23:45', NULL);
INSERT INTO `objectLog` VALUES (178, 'hitwhob001', 'logout', '2019-05-29 22:25:00', NULL);
INSERT INTO `objectLog` VALUES (179, 'hitwhob001', 'login', '2019-05-29 22:26:46', NULL);
INSERT INTO `objectLog` VALUES (180, 'hitwhob001', 'logout', '2019-05-29 22:27:37', NULL);
INSERT INTO `objectLog` VALUES (181, 'hitwhob001', 'login', '2019-05-29 22:34:01', NULL);
INSERT INTO `objectLog` VALUES (182, 'hitwhob001', 'logout', '2019-05-29 22:34:37', NULL);
INSERT INTO `objectLog` VALUES (183, 'hitwhob001', 'login', '2019-05-29 22:36:14', NULL);
INSERT INTO `objectLog` VALUES (184, 'hitwhob001', 'logout', '2019-05-29 22:38:22', NULL);
INSERT INTO `objectLog` VALUES (185, 'hitwhob001', 'login', '2019-05-29 22:39:33', NULL);
INSERT INTO `objectLog` VALUES (186, 'hitwhob001', 'logout', '2019-05-29 22:40:17', NULL);
INSERT INTO `objectLog` VALUES (187, 'hitwhob001', 'login', '2019-05-29 22:47:14', NULL);
INSERT INTO `objectLog` VALUES (188, 'hitwhob001', 'logout', '2019-05-29 22:47:58', NULL);
INSERT INTO `objectLog` VALUES (189, 'hitwhob001', 'login', '2019-05-29 22:50:21', NULL);
INSERT INTO `objectLog` VALUES (190, 'hitwhob001', 'logout', '2019-05-29 22:50:53', NULL);
INSERT INTO `objectLog` VALUES (191, 'hitwhob001', 'login', '2019-05-29 23:30:40', NULL);
INSERT INTO `objectLog` VALUES (192, 'hitwhob001', 'logout', '2019-05-29 23:32:12', NULL);
INSERT INTO `objectLog` VALUES (193, 'hitwhob001', 'login', '2019-05-30 00:10:43', NULL);
INSERT INTO `objectLog` VALUES (194, 'hitwhob001', 'logout', '2019-05-30 00:14:22', NULL);
INSERT INTO `objectLog` VALUES (195, 'hitwhob001', 'login', '2019-05-30 00:24:55', NULL);
INSERT INTO `objectLog` VALUES (196, 'hitwhob001', 'logout', '2019-05-30 00:25:38', NULL);
INSERT INTO `objectLog` VALUES (197, 'hitwhob001', 'login', '2019-05-30 03:27:10', NULL);
INSERT INTO `objectLog` VALUES (198, 'hitwhob001', 'logout', '2019-05-30 03:29:13', NULL);
INSERT INTO `objectLog` VALUES (199, 'hitwhob001', 'login', '2019-05-30 03:34:29', NULL);
INSERT INTO `objectLog` VALUES (200, 'hitwhob001', 'login', '2019-05-30 03:34:29', NULL);
INSERT INTO `objectLog` VALUES (201, 'hitwhob001', 'logout', '2019-05-30 03:35:38', NULL);
INSERT INTO `objectLog` VALUES (202, 'hitwhob001', 'login', '2019-05-30 04:36:15', NULL);
INSERT INTO `objectLog` VALUES (203, 'hitwhob001', 'logout', '2019-05-30 04:36:39', NULL);
INSERT INTO `objectLog` VALUES (204, 'hitwhob002', 'login', '2019-05-30 04:36:59', NULL);
INSERT INTO `objectLog` VALUES (205, 'hitwhob002', 'logout', '2019-05-30 04:37:15', NULL);
INSERT INTO `objectLog` VALUES (206, 'hitwhob001', 'login', '2019-05-30 13:12:08', NULL);
INSERT INTO `objectLog` VALUES (207, 'hitwhob001', 'logout', '2019-05-30 13:15:20', NULL);
INSERT INTO `objectLog` VALUES (208, 'hitwhob001', 'login', '2019-05-30 13:25:16', NULL);
INSERT INTO `objectLog` VALUES (209, 'hitwhob001', 'logout', '2019-05-30 13:27:26', NULL);
INSERT INTO `objectLog` VALUES (210, 'hitwhob001', 'login', '2019-05-30 13:50:21', NULL);
INSERT INTO `objectLog` VALUES (211, 'hitwhob001', 'logout', '2019-05-30 13:52:44', NULL);
INSERT INTO `objectLog` VALUES (212, 'hitwhob001', 'login', '2019-05-30 14:35:35', NULL);
INSERT INTO `objectLog` VALUES (213, 'hitwhob001', 'logout', '2019-05-30 14:56:14', NULL);
INSERT INTO `objectLog` VALUES (214, 'hitwhob001', 'login', '2019-05-30 15:42:19', NULL);
INSERT INTO `objectLog` VALUES (215, 'hitwhob001', 'login', '2019-05-30 15:49:28', NULL);
INSERT INTO `objectLog` VALUES (216, 'hitwhob001', 'logout', '2019-05-30 15:50:05', NULL);
INSERT INTO `objectLog` VALUES (217, 'hitwhob001', 'login', '2019-05-30 19:57:36', NULL);
INSERT INTO `objectLog` VALUES (218, 'hitwhob001', 'logout', '2019-05-30 20:00:37', NULL);
INSERT INTO `objectLog` VALUES (219, 'hitwhob001', 'login', '2019-05-30 20:33:28', NULL);
INSERT INTO `objectLog` VALUES (220, 'hitwhob001', 'logout', '2019-05-30 20:33:58', NULL);
INSERT INTO `objectLog` VALUES (221, 'hitwhob002', 'login', '2019-05-30 20:34:06', NULL);
INSERT INTO `objectLog` VALUES (222, 'hitwhob002', 'logout', '2019-05-30 20:35:48', NULL);
INSERT INTO `objectLog` VALUES (223, 'hitwhob003', 'login', '2019-05-30 20:35:56', NULL);
INSERT INTO `objectLog` VALUES (224, 'hitwhob003', 'logout', '2019-05-30 20:36:10', NULL);
INSERT INTO `objectLog` VALUES (225, 'hitwhob001', 'login', '2019-05-30 21:02:47', NULL);
INSERT INTO `objectLog` VALUES (226, 'hitwhob001', 'logout', '2019-05-30 21:07:51', NULL);
INSERT INTO `objectLog` VALUES (227, 'hitwhob001', 'login', '2019-05-30 21:09:08', NULL);
INSERT INTO `objectLog` VALUES (228, 'hitwhob001', 'logout', '2019-05-30 21:11:20', NULL);
INSERT INTO `objectLog` VALUES (229, 'hitwhob001', 'logout', '2019-05-30 21:14:14', NULL);
INSERT INTO `objectLog` VALUES (230, 'hitwhob002', 'login', '2019-05-30 21:35:56', NULL);
INSERT INTO `objectLog` VALUES (231, 'hitwhob002', 'login', '2019-05-30 21:39:24', NULL);
INSERT INTO `objectLog` VALUES (232, 'hitwhob002', 'login', '2019-05-30 21:46:23', NULL);
INSERT INTO `objectLog` VALUES (233, 'hitwhob001', 'login', '2019-05-30 21:48:27', NULL);
INSERT INTO `objectLog` VALUES (234, 'hitwhob001', 'logout', '2019-05-30 21:48:59', NULL);
INSERT INTO `objectLog` VALUES (235, 'hitwhob001', 'login', '2019-05-30 21:49:04', NULL);
INSERT INTO `objectLog` VALUES (236, 'hitwhob001', 'logout', '2019-05-30 21:49:08', NULL);
INSERT INTO `objectLog` VALUES (237, 'hitwhob002', 'login', '2019-05-30 21:49:16', NULL);
INSERT INTO `objectLog` VALUES (238, 'hitwhob002', 'logout', '2019-05-30 21:50:05', NULL);
INSERT INTO `objectLog` VALUES (239, 'hitwhob001', 'login', '2019-05-30 22:41:54', NULL);
INSERT INTO `objectLog` VALUES (240, 'hitwhob001', 'logout', '2019-05-30 22:47:58', NULL);
INSERT INTO `objectLog` VALUES (241, 'hitwhob001', 'login', '2019-05-31 02:30:29', NULL);
INSERT INTO `objectLog` VALUES (242, 'hitwhob001', 'logout', '2019-05-31 02:33:50', NULL);
INSERT INTO `objectLog` VALUES (243, 'hitwhob001', 'login', '2019-05-31 02:40:54', NULL);
INSERT INTO `objectLog` VALUES (244, 'hitwhob001', 'logout', '2019-05-31 02:41:09', NULL);
INSERT INTO `objectLog` VALUES (245, 'hitwhob001', 'login', '2019-05-31 03:26:34', NULL);
INSERT INTO `objectLog` VALUES (246, 'hitwhob001', 'logout', '2019-05-31 03:30:39', NULL);
INSERT INTO `objectLog` VALUES (247, 'hitwhob001', 'login', '2019-05-31 05:57:47', NULL);
INSERT INTO `objectLog` VALUES (248, 'hitwhob001', 'logout', '2019-05-31 06:03:38', NULL);
INSERT INTO `objectLog` VALUES (249, 'hitwhob001', 'login', '2019-05-31 06:37:06', NULL);
INSERT INTO `objectLog` VALUES (250, 'hitwhob001', 'logout', '2019-05-31 06:38:13', NULL);
INSERT INTO `objectLog` VALUES (251, 'hitwhob001', 'login', '2019-05-31 08:26:00', NULL);
INSERT INTO `objectLog` VALUES (252, 'hitwhob001', 'logout', '2019-05-31 08:36:01', NULL);
INSERT INTO `objectLog` VALUES (253, 'hitwhob001', 'login', '2019-06-04 19:06:04', NULL);
INSERT INTO `objectLog` VALUES (254, 'hitwhob001', 'login', '2019-06-04 19:19:01', NULL);
INSERT INTO `objectLog` VALUES (255, 'hitwhob001', 'login', '2019-06-05 14:56:23', NULL);
INSERT INTO `objectLog` VALUES (256, 'hitwhob001', 'login', '2019-06-06 07:01:55', NULL);
INSERT INTO `objectLog` VALUES (257, 'hitwhob001', 'login', '2019-06-10 22:14:58', NULL);
INSERT INTO `objectLog` VALUES (258, 'hitwhob001', 'login', '2019-06-11 16:55:52', NULL);
INSERT INTO `objectLog` VALUES (259, 'hitwhob001', 'logout', '2019-06-11 17:13:41', NULL);
INSERT INTO `objectLog` VALUES (260, 'hitwhob001', 'login', '2019-06-21 10:31:47', NULL);
INSERT INTO `objectLog` VALUES (261, 'hitwhob001', 'logout', '2019-06-21 10:32:47', NULL);
INSERT INTO `objectLog` VALUES (262, 'hitwhob001', 'login', '2019-06-21 14:37:56', NULL);
INSERT INTO `objectLog` VALUES (263, 'hitwhob001', 'login', '2019-06-21 15:06:19', NULL);
INSERT INTO `objectLog` VALUES (264, 'hitwhob001', 'logout', '2019-06-21 15:22:09', NULL);
INSERT INTO `objectLog` VALUES (265, 'hitwhob001', 'login', '2019-06-21 19:19:58', NULL);
INSERT INTO `objectLog` VALUES (266, 'hitwhob001', 'login', '2019-06-21 20:30:27', NULL);
INSERT INTO `objectLog` VALUES (267, 'hitwhob001', 'logout', '2019-06-21 20:30:55', NULL);
INSERT INTO `objectLog` VALUES (268, 'hitwhob001', 'login', '2019-06-22 10:41:42', NULL);
INSERT INTO `objectLog` VALUES (269, 'hitwhob001', 'login', '2019-06-28 14:36:01', NULL);
INSERT INTO `objectLog` VALUES (270, 'hitwhob001', 'login', '2019-07-06 11:31:33', NULL);
INSERT INTO `objectLog` VALUES (271, 'hitwhob001', 'login', '2019-07-06 12:04:35', NULL);
INSERT INTO `objectLog` VALUES (272, 'hitwhob001', 'login', '2019-07-06 12:22:35', NULL);
INSERT INTO `objectLog` VALUES (273, 'hitwhob001', 'logout', '2019-07-06 12:25:53', NULL);
INSERT INTO `objectLog` VALUES (274, 'hitwhob001', 'login', '2019-07-06 12:35:49', NULL);
INSERT INTO `objectLog` VALUES (275, 'hitwhob001', 'login', '2019-07-06 12:48:43', NULL);
INSERT INTO `objectLog` VALUES (276, 'hitwhob001', 'logout', '2019-07-06 12:50:05', NULL);
INSERT INTO `objectLog` VALUES (277, 'hitwhob001', 'login', '2019-07-06 12:56:58', NULL);
INSERT INTO `objectLog` VALUES (278, 'hitwhob001', 'logout', '2019-07-06 12:58:15', NULL);
INSERT INTO `objectLog` VALUES (279, 'hitwhob001', 'login', '2019-07-06 15:41:17', NULL);
INSERT INTO `objectLog` VALUES (280, 'hitwhob001', 'login', '2019-07-06 16:16:10', NULL);
INSERT INTO `objectLog` VALUES (281, 'hitwhob001', 'login', '2019-07-22 10:11:32', NULL);
INSERT INTO `objectLog` VALUES (282, 'hitwhob001', 'logout', '2019-07-22 10:11:37', NULL);
INSERT INTO `objectLog` VALUES (283, 'hitwhob001', 'login', '2019-07-22 10:33:23', NULL);
INSERT INTO `objectLog` VALUES (284, 'hitwhob001', 'logout', '2019-07-22 10:33:43', NULL);
INSERT INTO `objectLog` VALUES (285, 'hitwhob001', 'login', '2019-07-23 11:02:47', NULL);
INSERT INTO `objectLog` VALUES (286, 'hitwhob001', 'logout', '2019-07-23 11:03:42', NULL);
INSERT INTO `objectLog` VALUES (287, 'hitwhob001', 'login', '2019-07-23 11:05:29', NULL);
INSERT INTO `objectLog` VALUES (288, 'hitwhob001', 'logout', '2019-07-23 11:05:35', NULL);
INSERT INTO `objectLog` VALUES (289, 'hitwhob001', 'login', '2019-07-24 15:48:00', NULL);
INSERT INTO `objectLog` VALUES (290, 'hitwhob001', 'logout', '2019-07-24 15:48:16', NULL);
INSERT INTO `objectLog` VALUES (291, 'hitwhob001', 'login', '2019-07-24 15:48:26', NULL);
INSERT INTO `objectLog` VALUES (292, 'hitwhob001', 'logout', '2019-07-24 15:48:32', NULL);
INSERT INTO `objectLog` VALUES (293, 'hitwhob001', 'login', '2019-07-28 15:25:10', NULL);
INSERT INTO `objectLog` VALUES (294, 'hitwhob001', 'login', '2019-09-21 23:35:22', NULL);
INSERT INTO `objectLog` VALUES (295, 'hitwhob001', 'logout', '2019-09-21 23:40:06', NULL);
INSERT INTO `objectLog` VALUES (296, 'hitwhob001', 'login', '2019-09-21 23:49:54', NULL);
INSERT INTO `objectLog` VALUES (297, 'hitwhob001', 'logout', '2019-09-21 23:50:24', NULL);
INSERT INTO `objectLog` VALUES (298, 'hitwhob001', 'login', '2019-09-22 09:00:15', NULL);
INSERT INTO `objectLog` VALUES (299, 'hitwhob001', 'logout', '2019-09-22 09:00:40', NULL);
INSERT INTO `objectLog` VALUES (300, 'hitwhob001', 'login', '2019-09-22 09:08:45', NULL);
INSERT INTO `objectLog` VALUES (301, 'hitwhob001', 'logout', '2019-09-22 09:08:49', NULL);
INSERT INTO `objectLog` VALUES (302, 'hitwhob001', 'login', '2019-09-22 09:09:36', NULL);
INSERT INTO `objectLog` VALUES (303, 'hitwhob001', 'logout', '2019-09-22 09:10:18', NULL);
INSERT INTO `objectLog` VALUES (304, 'hitwhob002', 'login', '2019-09-22 09:10:52', NULL);
INSERT INTO `objectLog` VALUES (305, 'hitwhob002', 'login', '2019-09-22 09:11:34', NULL);
INSERT INTO `objectLog` VALUES (306, 'hitwhob002', 'logout', '2019-09-22 09:12:48', NULL);
INSERT INTO `objectLog` VALUES (307, 'hitwhob001', 'login', '2019-09-22 11:03:27', NULL);
INSERT INTO `objectLog` VALUES (308, 'hitwhob001', 'logout', '2019-09-22 11:04:11', NULL);
INSERT INTO `objectLog` VALUES (309, 'hitwhob001', 'login', '2019-09-22 11:05:55', NULL);
INSERT INTO `objectLog` VALUES (310, 'hitwhob001', 'logout', '2019-09-22 11:06:41', NULL);
INSERT INTO `objectLog` VALUES (311, 'hitwhob002', 'login', '2019-09-22 11:06:45', NULL);
INSERT INTO `objectLog` VALUES (312, 'hitwhob002', 'logout', '2019-09-22 11:07:17', NULL);
INSERT INTO `objectLog` VALUES (313, 'hitwhob001', 'login', '2019-09-22 11:16:15', NULL);
INSERT INTO `objectLog` VALUES (314, 'hitwhob001', 'login', '2019-09-22 11:18:40', NULL);
INSERT INTO `objectLog` VALUES (315, 'hitwhob001', 'login', '2019-09-22 11:22:45', NULL);
INSERT INTO `objectLog` VALUES (316, 'hitwhob001', 'login', '2019-09-22 11:22:45', NULL);
INSERT INTO `objectLog` VALUES (317, 'hitwhob001', 'logout', '2019-09-22 11:23:12', NULL);
INSERT INTO `objectLog` VALUES (318, 'hitwhob002', 'login', '2019-09-22 11:23:36', NULL);
INSERT INTO `objectLog` VALUES (319, 'hitwhob002', 'logout', '2019-09-22 11:24:08', NULL);
INSERT INTO `objectLog` VALUES (320, 'hitwhob002', 'login', '2019-09-22 12:04:48', NULL);
INSERT INTO `objectLog` VALUES (321, 'hitwhob002', 'login', '2019-09-22 12:29:35', NULL);
INSERT INTO `objectLog` VALUES (322, 'hitwhob001', 'login', '2019-10-10 17:23:43', NULL);
INSERT INTO `objectLog` VALUES (323, 'hitwhob001', 'logout', '2019-10-10 17:23:48', NULL);
INSERT INTO `objectLog` VALUES (324, 'hitwhob001', 'login', '2019-10-12 10:05:10', NULL);
INSERT INTO `objectLog` VALUES (325, 'hitwhob001', 'logout', '2019-10-12 10:06:32', NULL);
INSERT INTO `objectLog` VALUES (326, 'hitwhob001', 'login', '2019-10-12 10:07:40', NULL);
INSERT INTO `objectLog` VALUES (327, 'hitwhob001', 'logout', '2019-10-12 10:13:39', NULL);
INSERT INTO `objectLog` VALUES (328, 'hitwhob001', 'login', '2019-10-12 10:15:18', NULL);
INSERT INTO `objectLog` VALUES (329, 'hitwhob001', 'login', '2019-10-12 10:31:02', NULL);
INSERT INTO `objectLog` VALUES (330, 'hitwhob001', 'logout', '2019-10-12 10:35:01', NULL);
INSERT INTO `objectLog` VALUES (331, 'hitwhob001', 'login', '2019-10-12 10:37:17', NULL);
INSERT INTO `objectLog` VALUES (332, 'hitwhob001', 'login', '2019-10-16 20:39:14', NULL);

-- ----------------------------
-- Table structure for objectResourceUse
-- ----------------------------
DROP TABLE IF EXISTS `objectResourceUse`;
CREATE TABLE `objectResourceUse`  (
  `objectId` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `yearMonth` varchar(8) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `beginDate` date NOT NULL,
  `endDate` date NOT NULL,
  `msgCount` int(11) NOT NULL,
  `onlineTimeLength` int(11) NOT NULL,
  `dataCount` int(11) NOT NULL,
  PRIMARY KEY (`objectId`, `yearMonth`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of objectResourceUse
-- ----------------------------
INSERT INTO `objectResourceUse` VALUES ('hitwh001', '2019-05', '2019-05-01', '2019-05-31', 0, 10, 0);
INSERT INTO `objectResourceUse` VALUES ('hitwh001', '2019-06', '2019-06-01', '2019-06-30', 0, 122, 0);
INSERT INTO `objectResourceUse` VALUES ('hitwh001', '2019-07', '2019-07-01', '2019-07-31', 0, 39, 0);
INSERT INTO `objectResourceUse` VALUES ('hitwh001', '2019-08', '2019-08-01', '2019-08-31', 0, 19, 0);
INSERT INTO `objectResourceUse` VALUES ('hitwh001', '2019-09', '2019-09-01', '2019-09-30', 0, 8, 0);
INSERT INTO `objectResourceUse` VALUES ('hitwh001', '2019-10', '2019-10-01', '2019-10-31', 0, 16, 0);
INSERT INTO `objectResourceUse` VALUES ('hitwh001', '2019-11', '2019-11-01', '2019-11-30', 0, 0, 0);
INSERT INTO `objectResourceUse` VALUES ('hitwh001', '2019-12', '2019-12-01', '2019-12-31', 0, 0, 0);
INSERT INTO `objectResourceUse` VALUES ('hitwhob001', '2018-04', '2018-04-01', '2018-04-30', 24, 121, 2301);
INSERT INTO `objectResourceUse` VALUES ('hitwhob001', '2018-05', '2018-05-01', '2018-05-31', 16, 70, 1828);
INSERT INTO `objectResourceUse` VALUES ('hitwhob001', '2018-06', '2018-05-01', '2018-06-30', 14, 156, 2839);
INSERT INTO `objectResourceUse` VALUES ('hitwhob001', '2018-07', '2018-07-01', '2018-07-31', 9, 231, 3840);
INSERT INTO `objectResourceUse` VALUES ('hitwhob001', '2018-08', '2018-08-01', '2018-08-31', 15, 156, 2782);
INSERT INTO `objectResourceUse` VALUES ('hitwhob001', '2018-09', '2018-09-01', '2018-09-30', 21, 278, 4712);
INSERT INTO `objectResourceUse` VALUES ('hitwhob001', '2018-10', '2018-10-01', '2018-10-31', 36, 233, 3910);
INSERT INTO `objectResourceUse` VALUES ('hitwhob001', '2018-11', '2018-11-01', '2018-11-30', 12, 178, 3218);
INSERT INTO `objectResourceUse` VALUES ('hitwhob001', '2018-12', '2018-12-01', '2018-12-31', 4, 124, 2871);
INSERT INTO `objectResourceUse` VALUES ('hitwhob001', '2019-01', '2019-01-01', '2019-01-31', 7, 90, 1890);
INSERT INTO `objectResourceUse` VALUES ('hitwhob001', '2019-02', '2019-02-01', '2019-02-28', 13, 109, 2088);
INSERT INTO `objectResourceUse` VALUES ('hitwhob001', '2019-03', '2019-03-01', '2019-03-31', 17, 198, 3461);
INSERT INTO `objectResourceUse` VALUES ('hitwhob001', '2019-04', '2019-04-01', '2019-04-30', 13, 231, 3821);
INSERT INTO `objectResourceUse` VALUES ('hitwhob001', '2019-05', '2019-05-01', '2019-05-31', 39, 240, 3907);
INSERT INTO `objectResourceUse` VALUES ('hitwhob001', '2019-06', '2019-06-01', '2019-06-30', 6, 33, 1352);
INSERT INTO `objectResourceUse` VALUES ('hitwhob001', '2019-07', '2019-07-01', '2019-07-31', 28, 5, 1907);
INSERT INTO `objectResourceUse` VALUES ('hitwhob001', '2019-09', '2019-09-01', '2019-09-30', 0, 4, 0);
INSERT INTO `objectResourceUse` VALUES ('hitwhob001', '2019-10', '2019-10-01', '2019-10-31', 0, 9, 125);
INSERT INTO `objectResourceUse` VALUES ('hitwhob002', '2019-05', '2019-05-01', '2019-05-31', 0, 1, 0);
INSERT INTO `objectResourceUse` VALUES ('hitwhob002', '2019-09', '2019-09-01', '2019-09-30', 0, 1, 0);
INSERT INTO `objectResourceUse` VALUES ('hitwhob003', '2019-05', '2019-05-01', '2019-05-31', 0, 0, 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `userId` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `userName` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `pwd` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `sex` char(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `registerDate` date NOT NULL,
  `birthDate` date NOT NULL,
  `userTel` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `userGroup` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `lastLoginTime` datetime(0) NULL DEFAULT NULL,
  `lastLogoutTime` datetime(0) NULL DEFAULT NULL,
  `loginState` bit(1) NOT NULL,
  PRIMARY KEY (`userId`) USING BTREE,
  UNIQUE INDEX `user_userId_uindex`(`userId`) USING BTREE,
  UNIQUE INDEX `user_userName_uindex`(`userName`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('hitwh001', '哈工大威海一号', '12345abc', '女', '2019-01-17', '1994-08-20', '18463100659', '个人', '2019-12-05 19:31:43', '2019-10-12 10:31:01', b'1');
INSERT INTO `user` VALUES ('hitwh002', '哈工大威海2号', '12345abc', '男', '2019-01-17', '1993-11-25', '18463100658', '个人', '2019-04-25 16:43:35', '2019-04-25 16:43:45', b'0');
INSERT INTO `user` VALUES ('hitwh003', '哈工大威海3号', '12345abc', '男', '2019-01-17', '1993-11-25', '18463100658', '个人', '2019-04-22 18:38:05', '2019-04-22 18:39:33', b'0');
INSERT INTO `user` VALUES ('hitwh004', '哈工大威海4号', '12345abc', '男', '2019-01-17', '1993-11-25', '18463100658', '个人', '2019-03-16 11:15:26', '2019-04-22 18:39:33', b'0');
INSERT INTO `user` VALUES ('hitwh005', '哈工大威海5号', '12345abc', '男', '2019-01-17', '1993-11-25', '18463100658', '个人', '2019-03-16 11:15:26', '2019-04-22 18:39:33', b'0');
INSERT INTO `user` VALUES ('hitwh006', '哈工大威海6号', '12345abc', '男', '2019-01-17', '1993-11-25', '18463100658', '个人', '2019-03-16 11:15:26', '2019-04-22 18:39:33', b'0');
INSERT INTO `user` VALUES ('hitwh007', '哈工大威海7号', '12345abc', '男', '2019-01-17', '1993-11-25', '18463100658', '个人', '2019-03-07 01:49:52', '2019-04-22 18:39:33', b'0');
INSERT INTO `user` VALUES ('hitwh008', '哈工大威海8号', '12345abc', '男', '2019-01-17', '1993-11-25', '18463100658', '个人', '2019-03-07 01:43:18', '2019-04-22 18:39:33', b'0');
INSERT INTO `user` VALUES ('hitwh009', '哈工大威海9号', '12345abc', '男', '2019-01-17', '1993-11-25', '18463100658', '个人', '2019-03-16 11:15:26', '2019-04-22 18:39:33', b'0');
INSERT INTO `user` VALUES ('hitwh010', '哈工大威海10号', '12345abc', '男', '2019-01-17', '1993-11-25', '18463100658', '个人', '2019-03-16 11:15:26', '2019-04-22 18:39:33', b'0');
INSERT INTO `user` VALUES ('hitwh011', '哈工大威海11号', '12345abc', '男', '2019-01-17', '1993-11-25', '18463100658', '个人', '2019-03-16 11:15:26', '2019-04-22 18:39:33', b'0');
INSERT INTO `user` VALUES ('hitwh012', '哈工大威海12号', '12345abc', '男', '2019-01-17', '1993-11-25', '18463100658', '个人', '2019-03-07 01:49:52', '2019-04-22 18:39:33', b'0');
INSERT INTO `user` VALUES ('hitwh013', '哈工大威海13号', '12345abc', '男', '2019-01-17', '1993-11-25', '18463100658', '个人', '2019-03-07 01:43:18', '2019-04-22 18:39:33', b'0');
INSERT INTO `user` VALUES ('hitwh014', '哈工大威海14号', '12345abc', '男', '2019-01-17', '1993-11-25', '18463100658', '个人', '2019-03-16 11:15:26', '2019-04-22 18:39:33', b'0');
INSERT INTO `user` VALUES ('hitwh015', '哈工大威海15号', '12345abc', '男', '2019-01-17', '1993-11-25', '18463100658', '个人', '2019-03-16 11:15:26', '2019-04-22 18:39:33', b'0');
INSERT INTO `user` VALUES ('hitwh016', '哈工大威海16号', '12345abc', '男', '2019-03-28', '1993-11-26', '18463100658', '个人', '2019-03-28 02:22:09', '2019-04-22 18:39:33', b'0');

-- ----------------------------
-- Table structure for userLog
-- ----------------------------
DROP TABLE IF EXISTS `userLog`;
CREATE TABLE `userLog`  (
  `useLogId` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `logType` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `writeTime` datetime(0) NOT NULL,
  `detail` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`useLogId`) USING BTREE,
  INDEX `userLog_user__fk`(`userId`) USING BTREE,
  CONSTRAINT `userLog_user__fk` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1167 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of userLog
-- ----------------------------
INSERT INTO `userLog` VALUES (109, 'hitwh001', 'login', '2019-03-04 15:38:48', NULL);
INSERT INTO `userLog` VALUES (110, 'hitwh001', 'login', '2019-03-04 15:38:48', NULL);
INSERT INTO `userLog` VALUES (111, 'hitwh001', 'login', '2019-03-04 15:38:48', NULL);
INSERT INTO `userLog` VALUES (112, 'hitwh001', 'login', '2019-03-04 15:42:14', NULL);
INSERT INTO `userLog` VALUES (113, 'hitwh001', 'login', '2019-03-04 15:44:00', NULL);
INSERT INTO `userLog` VALUES (114, 'hitwh001', 'login', '2019-03-04 15:48:36', NULL);
INSERT INTO `userLog` VALUES (115, 'hitwh001', 'login', '2019-03-04 19:45:04', NULL);
INSERT INTO `userLog` VALUES (116, 'hitwh001', 'login', '2019-03-04 21:06:34', NULL);
INSERT INTO `userLog` VALUES (117, 'hitwh001', 'login', '2019-03-04 21:10:25', NULL);
INSERT INTO `userLog` VALUES (118, 'hitwh001', 'login', '2019-03-04 21:12:20', NULL);
INSERT INTO `userLog` VALUES (119, 'hitwh001', 'login', '2019-03-04 21:56:43', NULL);
INSERT INTO `userLog` VALUES (120, 'hitwh001', 'login', '2019-03-04 21:58:56', NULL);
INSERT INTO `userLog` VALUES (121, 'hitwh001', 'login', '2019-03-04 22:10:59', NULL);
INSERT INTO `userLog` VALUES (122, 'hitwh001', 'login', '2019-03-04 22:15:50', NULL);
INSERT INTO `userLog` VALUES (123, 'hitwh001', 'login', '2019-03-04 22:19:10', NULL);
INSERT INTO `userLog` VALUES (124, 'hitwh001', 'login', '2019-03-04 22:20:54', NULL);
INSERT INTO `userLog` VALUES (125, 'hitwh001', 'login', '2019-03-05 10:05:23', NULL);
INSERT INTO `userLog` VALUES (126, 'hitwh001', 'login', '2019-03-05 10:15:04', NULL);
INSERT INTO `userLog` VALUES (127, 'hitwh001', 'login', '2019-03-05 10:31:22', NULL);
INSERT INTO `userLog` VALUES (128, 'hitwh001', 'login', '2019-03-05 13:37:14', NULL);
INSERT INTO `userLog` VALUES (129, 'hitwh001', 'login', '2019-03-05 13:39:26', NULL);
INSERT INTO `userLog` VALUES (130, 'hitwh001', 'login', '2019-03-05 13:43:15', NULL);
INSERT INTO `userLog` VALUES (131, 'hitwh001', 'login', '2019-03-05 14:11:17', NULL);
INSERT INTO `userLog` VALUES (132, 'hitwh001', 'login', '2019-03-05 14:33:34', NULL);
INSERT INTO `userLog` VALUES (133, 'hitwh001', 'login', '2019-03-05 14:36:47', NULL);
INSERT INTO `userLog` VALUES (134, 'hitwh001', 'login', '2019-03-05 14:51:07', NULL);
INSERT INTO `userLog` VALUES (135, 'hitwh001', 'login', '2019-03-05 15:04:06', NULL);
INSERT INTO `userLog` VALUES (136, 'hitwh001', 'login', '2019-03-05 15:05:06', NULL);
INSERT INTO `userLog` VALUES (137, 'hitwh001', 'login', '2019-03-05 15:07:33', NULL);
INSERT INTO `userLog` VALUES (138, 'hitwh001', 'login', '2019-03-05 15:08:57', NULL);
INSERT INTO `userLog` VALUES (139, 'hitwh001', 'login', '2019-03-05 15:10:17', NULL);
INSERT INTO `userLog` VALUES (140, 'hitwh001', 'login', '2019-03-05 15:12:06', NULL);
INSERT INTO `userLog` VALUES (141, 'hitwh001', 'login', '2019-03-05 15:13:28', NULL);
INSERT INTO `userLog` VALUES (142, 'hitwh001', 'login', '2019-03-05 15:14:53', NULL);
INSERT INTO `userLog` VALUES (143, 'hitwh001', 'login', '2019-03-05 16:42:32', NULL);
INSERT INTO `userLog` VALUES (144, 'hitwh001', 'login', '2019-03-05 16:45:36', NULL);
INSERT INTO `userLog` VALUES (145, 'hitwh001', 'login', '2019-03-05 16:48:21', NULL);
INSERT INTO `userLog` VALUES (146, 'hitwh001', 'login', '2019-03-05 20:13:40', NULL);
INSERT INTO `userLog` VALUES (147, 'hitwh001', 'login', '2019-03-05 20:17:51', NULL);
INSERT INTO `userLog` VALUES (148, 'hitwh001', 'login', '2019-03-05 20:20:21', NULL);
INSERT INTO `userLog` VALUES (149, 'hitwh001', 'login', '2019-03-05 20:36:55', NULL);
INSERT INTO `userLog` VALUES (150, 'hitwh001', 'login', '2019-03-05 20:40:08', NULL);
INSERT INTO `userLog` VALUES (151, 'hitwh001', 'login', '2019-03-05 21:02:37', NULL);
INSERT INTO `userLog` VALUES (152, 'hitwh001', 'login', '2019-03-05 21:04:48', NULL);
INSERT INTO `userLog` VALUES (153, 'hitwh001', 'login', '2019-03-05 21:08:26', NULL);
INSERT INTO `userLog` VALUES (154, 'hitwh001', 'login', '2019-03-05 21:08:26', NULL);
INSERT INTO `userLog` VALUES (155, 'hitwh001', 'login', '2019-03-05 21:10:38', NULL);
INSERT INTO `userLog` VALUES (156, 'hitwh001', 'login', '2019-03-05 21:42:08', NULL);
INSERT INTO `userLog` VALUES (157, 'hitwh001', 'login', '2019-03-05 21:51:52', NULL);
INSERT INTO `userLog` VALUES (158, 'hitwh001', 'login', '2019-03-05 21:55:50', NULL);
INSERT INTO `userLog` VALUES (159, 'hitwh001', 'login', '2019-03-05 21:57:10', NULL);
INSERT INTO `userLog` VALUES (160, 'hitwh001', 'login', '2019-03-05 21:58:49', NULL);
INSERT INTO `userLog` VALUES (161, 'hitwh001', 'login', '2019-03-05 22:01:19', NULL);
INSERT INTO `userLog` VALUES (162, 'hitwh001', 'login', '2019-03-05 22:04:02', NULL);
INSERT INTO `userLog` VALUES (163, 'hitwh001', 'login', '2019-03-05 22:07:41', NULL);
INSERT INTO `userLog` VALUES (164, 'hitwh001', 'login', '2019-03-05 22:09:00', NULL);
INSERT INTO `userLog` VALUES (165, 'hitwh001', 'login', '2019-03-05 22:11:22', NULL);
INSERT INTO `userLog` VALUES (166, 'hitwh001', 'login', '2019-03-05 22:14:43', NULL);
INSERT INTO `userLog` VALUES (167, 'hitwh001', 'login', '2019-03-06 09:30:05', NULL);
INSERT INTO `userLog` VALUES (168, 'hitwh001', 'login', '2019-03-06 09:39:29', NULL);
INSERT INTO `userLog` VALUES (169, 'hitwh001', 'login', '2019-03-06 09:45:53', NULL);
INSERT INTO `userLog` VALUES (170, 'hitwh001', 'login', '2019-03-06 09:59:02', NULL);
INSERT INTO `userLog` VALUES (171, 'hitwh001', 'login', '2019-03-06 10:02:12', NULL);
INSERT INTO `userLog` VALUES (172, 'hitwh001', 'login', '2019-03-06 10:03:08', NULL);
INSERT INTO `userLog` VALUES (173, 'hitwh001', 'login', '2019-03-06 10:18:39', NULL);
INSERT INTO `userLog` VALUES (174, 'hitwh001', 'login', '2019-03-06 10:21:19', NULL);
INSERT INTO `userLog` VALUES (175, 'hitwh001', 'login', '2019-03-06 10:42:48', NULL);
INSERT INTO `userLog` VALUES (176, 'hitwh001', 'login', '2019-03-06 10:47:30', NULL);
INSERT INTO `userLog` VALUES (177, 'hitwh001', 'login', '2019-03-06 10:51:44', NULL);
INSERT INTO `userLog` VALUES (178, 'hitwh001', 'login', '2019-03-06 11:34:17', NULL);
INSERT INTO `userLog` VALUES (179, 'hitwh002', 'login', '2019-03-06 11:38:06', NULL);
INSERT INTO `userLog` VALUES (180, 'hitwh002', 'login', '2019-03-06 11:39:52', NULL);
INSERT INTO `userLog` VALUES (181, 'hitwh002', 'login', '2019-03-06 11:41:44', NULL);
INSERT INTO `userLog` VALUES (182, 'hitwh003', 'login', '2019-03-06 11:43:18', NULL);
INSERT INTO `userLog` VALUES (183, 'hitwh001', 'login', '2019-03-06 11:44:32', NULL);
INSERT INTO `userLog` VALUES (184, 'hitwh001', 'login', '2019-03-06 11:48:18', NULL);
INSERT INTO `userLog` VALUES (185, 'hitwh002', 'login', '2019-03-06 11:48:34', NULL);
INSERT INTO `userLog` VALUES (186, 'hitwh002', 'login', '2019-03-06 11:49:52', NULL);
INSERT INTO `userLog` VALUES (187, 'hitwh001', 'login', '2019-03-06 11:57:53', NULL);
INSERT INTO `userLog` VALUES (188, 'hitwh001', 'login', '2019-03-06 12:01:38', NULL);
INSERT INTO `userLog` VALUES (189, 'hitwh001', 'login', '2019-03-06 13:19:22', NULL);
INSERT INTO `userLog` VALUES (190, 'hitwh001', 'login', '2019-03-06 14:22:39', NULL);
INSERT INTO `userLog` VALUES (191, 'hitwh001', 'login', '2019-03-06 14:28:55', NULL);
INSERT INTO `userLog` VALUES (192, 'hitwh001', 'login', '2019-03-06 14:36:23', NULL);
INSERT INTO `userLog` VALUES (193, 'hitwh001', 'login', '2019-03-06 14:36:23', NULL);
INSERT INTO `userLog` VALUES (194, 'hitwh001', 'login', '2019-03-06 14:46:44', NULL);
INSERT INTO `userLog` VALUES (195, 'hitwh001', 'login', '2019-03-06 14:58:05', NULL);
INSERT INTO `userLog` VALUES (196, 'hitwh001', 'login', '2019-03-06 15:02:48', NULL);
INSERT INTO `userLog` VALUES (197, 'hitwh001', 'login', '2019-03-06 15:20:40', NULL);
INSERT INTO `userLog` VALUES (198, 'hitwh001', 'login', '2019-03-06 16:17:22', NULL);
INSERT INTO `userLog` VALUES (199, 'hitwh001', 'login', '2019-03-06 19:51:56', NULL);
INSERT INTO `userLog` VALUES (200, 'hitwh001', 'login', '2019-03-06 19:59:28', NULL);
INSERT INTO `userLog` VALUES (201, 'hitwh001', 'login', '2019-03-06 20:01:15', NULL);
INSERT INTO `userLog` VALUES (202, 'hitwh001', 'login', '2019-03-06 20:07:44', NULL);
INSERT INTO `userLog` VALUES (203, 'hitwh001', 'login', '2019-03-06 20:17:57', NULL);
INSERT INTO `userLog` VALUES (204, 'hitwh001', 'login', '2019-03-06 20:21:37', NULL);
INSERT INTO `userLog` VALUES (205, 'hitwh001', 'login', '2019-03-06 20:30:57', NULL);
INSERT INTO `userLog` VALUES (206, 'hitwh001', 'login', '2019-03-06 20:39:40', NULL);
INSERT INTO `userLog` VALUES (207, 'hitwh001', 'login', '2019-03-06 20:57:14', NULL);
INSERT INTO `userLog` VALUES (208, 'hitwh001', 'login', '2019-03-06 21:00:33', NULL);
INSERT INTO `userLog` VALUES (209, 'hitwh001', 'login', '2019-03-06 21:00:33', NULL);
INSERT INTO `userLog` VALUES (210, 'hitwh001', 'login', '2019-03-06 21:08:12', NULL);
INSERT INTO `userLog` VALUES (211, 'hitwh001', 'login', '2019-03-07 11:58:13', NULL);
INSERT INTO `userLog` VALUES (212, 'hitwh001', 'login', '2019-03-07 12:05:06', NULL);
INSERT INTO `userLog` VALUES (213, 'hitwh001', 'login', '2019-03-07 12:16:01', NULL);
INSERT INTO `userLog` VALUES (214, 'hitwh001', 'login', '2019-03-07 12:47:26', NULL);
INSERT INTO `userLog` VALUES (215, 'hitwh001', 'login', '2019-03-07 13:14:50', NULL);
INSERT INTO `userLog` VALUES (216, 'hitwh001', 'login', '2019-03-07 14:38:50', NULL);
INSERT INTO `userLog` VALUES (217, 'hitwh001', 'login', '2019-03-07 14:43:56', NULL);
INSERT INTO `userLog` VALUES (218, 'hitwh001', 'login', '2019-03-07 14:47:47', NULL);
INSERT INTO `userLog` VALUES (219, 'hitwh001', 'login', '2019-03-07 14:49:09', NULL);
INSERT INTO `userLog` VALUES (220, 'hitwh001', 'login', '2019-03-07 14:53:13', NULL);
INSERT INTO `userLog` VALUES (221, 'hitwh001', 'login', '2019-03-07 14:59:36', NULL);
INSERT INTO `userLog` VALUES (222, 'hitwh001', 'login', '2019-03-07 16:13:40', NULL);
INSERT INTO `userLog` VALUES (223, 'hitwh001', 'login', '2019-03-07 16:24:30', NULL);
INSERT INTO `userLog` VALUES (224, 'hitwh001', 'login', '2019-03-07 16:28:19', NULL);
INSERT INTO `userLog` VALUES (225, 'hitwh001', 'login', '2019-03-07 16:29:20', NULL);
INSERT INTO `userLog` VALUES (226, 'hitwh001', 'login', '2019-03-07 16:33:45', NULL);
INSERT INTO `userLog` VALUES (227, 'hitwh001', 'login', '2019-03-07 16:36:13', NULL);
INSERT INTO `userLog` VALUES (228, 'hitwh001', 'login', '2019-03-07 17:53:38', NULL);
INSERT INTO `userLog` VALUES (229, 'hitwh001', 'login', '2019-03-07 17:56:07', NULL);
INSERT INTO `userLog` VALUES (230, 'hitwh001', 'login', '2019-03-07 17:58:26', NULL);
INSERT INTO `userLog` VALUES (231, 'hitwh001', 'login', '2019-03-07 18:13:53', NULL);
INSERT INTO `userLog` VALUES (232, 'hitwh001', 'login', '2019-03-07 18:15:54', NULL);
INSERT INTO `userLog` VALUES (233, 'hitwh001', 'login', '2019-03-07 18:22:05', NULL);
INSERT INTO `userLog` VALUES (234, 'hitwh001', 'login', '2019-03-07 18:22:46', NULL);
INSERT INTO `userLog` VALUES (235, 'hitwh001', 'login', '2019-03-07 18:28:10', NULL);
INSERT INTO `userLog` VALUES (236, 'hitwh001', 'login', '2019-03-07 18:29:16', NULL);
INSERT INTO `userLog` VALUES (237, 'hitwh001', 'login', '2019-03-07 18:37:49', NULL);
INSERT INTO `userLog` VALUES (238, 'hitwh001', 'login', '2019-03-07 18:39:10', NULL);
INSERT INTO `userLog` VALUES (239, 'hitwh001', 'login', '2019-03-07 18:40:22', NULL);
INSERT INTO `userLog` VALUES (240, 'hitwh001', 'login', '2019-03-07 18:42:06', NULL);
INSERT INTO `userLog` VALUES (241, 'hitwh001', 'login', '2019-03-07 18:43:05', NULL);
INSERT INTO `userLog` VALUES (242, 'hitwh001', 'login', '2019-03-07 18:43:39', NULL);
INSERT INTO `userLog` VALUES (243, 'hitwh001', 'login', '2019-03-07 18:47:49', NULL);
INSERT INTO `userLog` VALUES (244, 'hitwh001', 'login', '2019-03-07 19:07:39', NULL);
INSERT INTO `userLog` VALUES (245, 'hitwh001', 'login', '2019-03-07 19:14:50', NULL);
INSERT INTO `userLog` VALUES (246, 'hitwh001', 'login', '2019-03-08 11:31:14', NULL);
INSERT INTO `userLog` VALUES (247, 'hitwh001', 'login', '2019-03-08 13:24:29', NULL);
INSERT INTO `userLog` VALUES (248, 'hitwh001', 'login', '2019-03-08 18:44:47', NULL);
INSERT INTO `userLog` VALUES (249, 'hitwh001', 'login', '2019-03-10 12:16:05', NULL);
INSERT INTO `userLog` VALUES (250, 'hitwh001', 'login', '2019-03-12 11:37:44', NULL);
INSERT INTO `userLog` VALUES (251, 'hitwh001', 'login', '2019-03-12 11:40:15', NULL);
INSERT INTO `userLog` VALUES (252, 'hitwh001', 'login', '2019-03-12 11:43:57', NULL);
INSERT INTO `userLog` VALUES (253, 'hitwh001', 'login', '2019-03-12 11:46:26', NULL);
INSERT INTO `userLog` VALUES (254, 'hitwh001', 'login', '2019-03-12 14:29:08', NULL);
INSERT INTO `userLog` VALUES (255, 'hitwh001', 'login', '2019-03-12 14:43:43', NULL);
INSERT INTO `userLog` VALUES (256, 'hitwh001', 'login', '2019-03-12 14:54:20', NULL);
INSERT INTO `userLog` VALUES (257, 'hitwh001', 'login', '2019-03-13 10:28:08', NULL);
INSERT INTO `userLog` VALUES (258, 'hitwh001', 'login', '2019-03-13 10:41:46', NULL);
INSERT INTO `userLog` VALUES (259, 'hitwh001', 'login', '2019-03-13 13:23:45', NULL);
INSERT INTO `userLog` VALUES (260, 'hitwh001', 'login', '2019-03-14 20:25:25', NULL);
INSERT INTO `userLog` VALUES (261, 'hitwh001', 'login', '2019-03-14 20:44:01', NULL);
INSERT INTO `userLog` VALUES (262, 'hitwh001', 'login', '2019-03-15 11:54:35', NULL);
INSERT INTO `userLog` VALUES (263, 'hitwh001', 'login', '2019-03-15 11:56:27', NULL);
INSERT INTO `userLog` VALUES (264, 'hitwh001', 'login', '2019-03-15 14:59:06', NULL);
INSERT INTO `userLog` VALUES (265, 'hitwh001', 'login', '2019-03-15 15:06:12', NULL);
INSERT INTO `userLog` VALUES (266, 'hitwh001', 'login', '2019-03-15 15:23:59', NULL);
INSERT INTO `userLog` VALUES (267, 'hitwh001', 'login', '2019-03-15 18:41:09', NULL);
INSERT INTO `userLog` VALUES (268, 'hitwh001', 'login', '2019-03-15 21:15:26', NULL);
INSERT INTO `userLog` VALUES (269, 'hitwh001', 'login', '2019-03-17 13:59:06', NULL);
INSERT INTO `userLog` VALUES (270, 'hitwh001', 'login', '2019-03-17 14:05:54', NULL);
INSERT INTO `userLog` VALUES (271, 'hitwh001', 'login', '2019-03-17 14:10:43', NULL);
INSERT INTO `userLog` VALUES (272, 'hitwh001', 'login', '2019-03-17 14:15:43', NULL);
INSERT INTO `userLog` VALUES (273, 'hitwh001', 'login', '2019-03-17 14:21:10', NULL);
INSERT INTO `userLog` VALUES (274, 'hitwh001', 'login', '2019-03-17 14:25:57', NULL);
INSERT INTO `userLog` VALUES (275, 'hitwh001', 'login', '2019-03-17 14:28:15', NULL);
INSERT INTO `userLog` VALUES (276, 'hitwh001', 'login', '2019-03-17 14:30:26', NULL);
INSERT INTO `userLog` VALUES (277, 'hitwh001', 'login', '2019-03-17 14:31:14', NULL);
INSERT INTO `userLog` VALUES (278, 'hitwh001', 'login', '2019-03-17 14:34:18', NULL);
INSERT INTO `userLog` VALUES (279, 'hitwh001', 'login', '2019-03-17 14:34:33', NULL);
INSERT INTO `userLog` VALUES (280, 'hitwh001', 'login', '2019-03-17 14:55:32', NULL);
INSERT INTO `userLog` VALUES (281, 'hitwh001', 'login', '2019-03-17 14:57:35', NULL);
INSERT INTO `userLog` VALUES (282, 'hitwh001', 'login', '2019-03-17 15:01:12', NULL);
INSERT INTO `userLog` VALUES (283, 'hitwh001', 'login', '2019-03-17 15:03:18', NULL);
INSERT INTO `userLog` VALUES (284, 'hitwh001', 'login', '2019-03-17 15:11:17', NULL);
INSERT INTO `userLog` VALUES (285, 'hitwh001', 'login', '2019-03-17 15:11:48', NULL);
INSERT INTO `userLog` VALUES (286, 'hitwh001', 'login', '2019-03-17 15:17:30', NULL);
INSERT INTO `userLog` VALUES (287, 'hitwh001', 'login', '2019-03-17 15:22:15', NULL);
INSERT INTO `userLog` VALUES (288, 'hitwh001', 'login', '2019-03-17 15:27:36', NULL);
INSERT INTO `userLog` VALUES (289, 'hitwh001', 'login', '2019-03-17 15:28:57', NULL);
INSERT INTO `userLog` VALUES (290, 'hitwh001', 'login', '2019-03-17 15:29:20', NULL);
INSERT INTO `userLog` VALUES (291, 'hitwh001', 'login', '2019-03-17 15:34:22', NULL);
INSERT INTO `userLog` VALUES (292, 'hitwh001', 'login', '2019-03-17 15:36:13', NULL);
INSERT INTO `userLog` VALUES (293, 'hitwh001', 'login', '2019-03-17 15:38:40', NULL);
INSERT INTO `userLog` VALUES (294, 'hitwh001', 'login', '2019-03-17 15:39:40', NULL);
INSERT INTO `userLog` VALUES (295, 'hitwh001', 'login', '2019-03-17 15:40:45', NULL);
INSERT INTO `userLog` VALUES (296, 'hitwh001', 'login', '2019-03-17 15:42:19', NULL);
INSERT INTO `userLog` VALUES (297, 'hitwh001', 'login', '2019-03-17 15:47:20', NULL);
INSERT INTO `userLog` VALUES (298, 'hitwh001', 'login', '2019-03-17 15:49:19', NULL);
INSERT INTO `userLog` VALUES (299, 'hitwh001', 'login', '2019-03-17 16:01:59', NULL);
INSERT INTO `userLog` VALUES (300, 'hitwh001', 'login', '2019-03-17 16:04:51', NULL);
INSERT INTO `userLog` VALUES (301, 'hitwh001', 'login', '2019-03-17 16:06:27', NULL);
INSERT INTO `userLog` VALUES (302, 'hitwh001', 'login', '2019-03-17 16:07:20', NULL);
INSERT INTO `userLog` VALUES (303, 'hitwh001', 'login', '2019-03-17 16:11:36', NULL);
INSERT INTO `userLog` VALUES (304, 'hitwh001', 'login', '2019-03-17 16:19:05', NULL);
INSERT INTO `userLog` VALUES (305, 'hitwh001', 'login', '2019-03-17 16:23:01', NULL);
INSERT INTO `userLog` VALUES (306, 'hitwh001', 'login', '2019-03-17 16:26:20', NULL);
INSERT INTO `userLog` VALUES (307, 'hitwh001', 'login', '2019-03-17 16:27:46', NULL);
INSERT INTO `userLog` VALUES (308, 'hitwh001', 'login', '2019-03-17 16:30:29', NULL);
INSERT INTO `userLog` VALUES (309, 'hitwh001', 'login', '2019-03-17 16:32:31', NULL);
INSERT INTO `userLog` VALUES (310, 'hitwh001', 'login', '2019-03-17 16:34:05', NULL);
INSERT INTO `userLog` VALUES (311, 'hitwh001', 'login', '2019-03-17 16:36:02', NULL);
INSERT INTO `userLog` VALUES (312, 'hitwh001', 'login', '2019-03-17 16:36:41', NULL);
INSERT INTO `userLog` VALUES (313, 'hitwh001', 'login', '2019-03-17 16:39:14', NULL);
INSERT INTO `userLog` VALUES (314, 'hitwh001', 'login', '2019-03-17 16:43:49', NULL);
INSERT INTO `userLog` VALUES (315, 'hitwh001', 'login', '2019-03-17 16:50:37', NULL);
INSERT INTO `userLog` VALUES (316, 'hitwh001', 'login', '2019-03-17 18:48:23', NULL);
INSERT INTO `userLog` VALUES (317, 'hitwh001', 'login', '2019-03-17 18:54:23', NULL);
INSERT INTO `userLog` VALUES (318, 'hitwh001', 'login', '2019-03-17 18:58:33', NULL);
INSERT INTO `userLog` VALUES (319, 'hitwh001', 'login', '2019-03-17 18:59:00', NULL);
INSERT INTO `userLog` VALUES (320, 'hitwh001', 'login', '2019-03-17 19:04:16', NULL);
INSERT INTO `userLog` VALUES (321, 'hitwh001', 'login', '2019-03-17 19:07:45', NULL);
INSERT INTO `userLog` VALUES (322, 'hitwh001', 'login', '2019-03-17 19:09:58', NULL);
INSERT INTO `userLog` VALUES (323, 'hitwh001', 'login', '2019-03-17 19:12:15', NULL);
INSERT INTO `userLog` VALUES (324, 'hitwh001', 'login', '2019-03-17 19:19:38', NULL);
INSERT INTO `userLog` VALUES (325, 'hitwh001', 'login', '2019-03-17 19:20:01', NULL);
INSERT INTO `userLog` VALUES (326, 'hitwh001', 'login', '2019-03-17 19:29:54', NULL);
INSERT INTO `userLog` VALUES (327, 'hitwh001', 'login', '2019-03-17 19:32:45', NULL);
INSERT INTO `userLog` VALUES (328, 'hitwh001', 'login', '2019-03-17 19:37:29', NULL);
INSERT INTO `userLog` VALUES (329, 'hitwh001', 'login', '2019-03-17 19:40:07', NULL);
INSERT INTO `userLog` VALUES (330, 'hitwh001', 'login', '2019-03-17 19:43:33', NULL);
INSERT INTO `userLog` VALUES (331, 'hitwh001', 'login', '2019-03-17 19:48:46', NULL);
INSERT INTO `userLog` VALUES (332, 'hitwh001', 'login', '2019-03-17 20:06:59', NULL);
INSERT INTO `userLog` VALUES (333, 'hitwh001', 'login', '2019-03-17 20:07:02', NULL);
INSERT INTO `userLog` VALUES (334, 'hitwh001', 'login', '2019-03-17 20:13:05', NULL);
INSERT INTO `userLog` VALUES (335, 'hitwh001', 'login', '2019-03-17 21:04:53', NULL);
INSERT INTO `userLog` VALUES (336, 'hitwh001', 'login', '2019-03-18 09:53:52', NULL);
INSERT INTO `userLog` VALUES (337, 'hitwh001', 'login', '2019-03-18 09:58:57', NULL);
INSERT INTO `userLog` VALUES (338, 'hitwh001', 'login', '2019-03-27 12:18:50', NULL);
INSERT INTO `userLog` VALUES (339, 'hitwh016', 'login', '2019-03-27 12:22:09', NULL);
INSERT INTO `userLog` VALUES (340, 'hitwh001', 'login', '2019-03-27 12:30:05', NULL);
INSERT INTO `userLog` VALUES (341, 'hitwh001', 'login', '2019-03-27 14:35:08', NULL);
INSERT INTO `userLog` VALUES (342, 'hitwh001', 'login', '2019-03-27 21:15:51', NULL);
INSERT INTO `userLog` VALUES (343, 'hitwh001', 'login', '2019-03-27 21:20:22', NULL);
INSERT INTO `userLog` VALUES (344, 'hitwh001', 'login', '2019-03-28 14:57:36', NULL);
INSERT INTO `userLog` VALUES (345, 'hitwh001', 'login', '2019-03-29 16:31:38', NULL);
INSERT INTO `userLog` VALUES (346, 'hitwh001', 'login', '2019-03-29 17:50:39', NULL);
INSERT INTO `userLog` VALUES (347, 'hitwh001', 'login', '2019-03-29 18:07:07', NULL);
INSERT INTO `userLog` VALUES (348, 'hitwh001', 'login', '2019-03-29 21:33:17', NULL);
INSERT INTO `userLog` VALUES (349, 'hitwh001', 'login', '2019-03-29 22:03:36', NULL);
INSERT INTO `userLog` VALUES (350, 'hitwh001', 'login', '2019-03-30 12:19:45', NULL);
INSERT INTO `userLog` VALUES (351, 'hitwh001', 'login', '2019-03-30 13:24:18', NULL);
INSERT INTO `userLog` VALUES (352, 'hitwh001', 'login', '2019-03-30 15:28:25', NULL);
INSERT INTO `userLog` VALUES (353, 'hitwh001', 'login', '2019-03-30 15:31:56', NULL);
INSERT INTO `userLog` VALUES (354, 'hitwh001', 'login', '2019-03-30 21:47:14', NULL);
INSERT INTO `userLog` VALUES (355, 'hitwh001', 'login', '2019-03-31 09:46:57', NULL);
INSERT INTO `userLog` VALUES (356, 'hitwh001', 'login', '2019-03-31 09:53:00', NULL);
INSERT INTO `userLog` VALUES (357, 'hitwh001', 'login', '2019-03-31 11:24:14', NULL);
INSERT INTO `userLog` VALUES (358, 'hitwh001', 'login', '2019-03-31 11:48:01', NULL);
INSERT INTO `userLog` VALUES (359, 'hitwh001', 'login', '2019-03-31 12:38:37', NULL);
INSERT INTO `userLog` VALUES (360, 'hitwh001', 'login', '2019-03-31 12:38:38', NULL);
INSERT INTO `userLog` VALUES (361, 'hitwh001', 'login', '2019-03-31 12:38:39', NULL);
INSERT INTO `userLog` VALUES (362, 'hitwh001', 'login', '2019-03-31 12:38:41', NULL);
INSERT INTO `userLog` VALUES (363, 'hitwh001', 'login', '2019-03-31 12:38:41', NULL);
INSERT INTO `userLog` VALUES (364, 'hitwh001', 'login', '2019-03-31 12:38:41', NULL);
INSERT INTO `userLog` VALUES (365, 'hitwh001', 'login', '2019-04-02 18:49:08', NULL);
INSERT INTO `userLog` VALUES (366, 'hitwh001', 'login', '2019-04-02 21:39:02', NULL);
INSERT INTO `userLog` VALUES (367, 'hitwh001', 'login', '2019-04-03 14:22:23', NULL);
INSERT INTO `userLog` VALUES (368, 'hitwh001', 'login', '2019-04-21 20:18:10', NULL);
INSERT INTO `userLog` VALUES (369, 'hitwh001', 'login', '2019-04-21 20:35:00', NULL);
INSERT INTO `userLog` VALUES (370, 'hitwh001', 'login', '2019-04-21 21:09:31', NULL);
INSERT INTO `userLog` VALUES (371, 'hitwh001', 'login', '2019-04-21 21:29:48', NULL);
INSERT INTO `userLog` VALUES (372, 'hitwh001', 'logout', '2019-04-21 21:30:23', NULL);
INSERT INTO `userLog` VALUES (373, 'hitwh001', 'login', '2019-04-22 18:37:59', NULL);
INSERT INTO `userLog` VALUES (374, 'hitwh002', 'login', '2019-04-22 18:38:01', NULL);
INSERT INTO `userLog` VALUES (375, 'hitwh003', 'login', '2019-04-22 18:38:05', NULL);
INSERT INTO `userLog` VALUES (376, 'hitwh003', 'logout', '2019-04-22 18:39:33', NULL);
INSERT INTO `userLog` VALUES (377, 'hitwh002', 'login', '2019-04-22 18:44:47', NULL);
INSERT INTO `userLog` VALUES (378, 'hitwh001', 'login', '2019-04-22 18:44:51', NULL);
INSERT INTO `userLog` VALUES (379, 'hitwh001', 'logout', '2019-04-22 18:46:48', NULL);
INSERT INTO `userLog` VALUES (380, 'hitwh002', 'login', '2019-04-22 18:47:49', NULL);
INSERT INTO `userLog` VALUES (381, 'hitwh002', 'logout', '2019-04-22 18:47:51', NULL);
INSERT INTO `userLog` VALUES (382, 'hitwh001', 'login', '2019-04-22 21:13:32', NULL);
INSERT INTO `userLog` VALUES (383, 'hitwh001', 'logout', '2019-04-22 21:14:00', NULL);
INSERT INTO `userLog` VALUES (384, 'hitwh001', 'login', '2019-04-22 21:40:40', NULL);
INSERT INTO `userLog` VALUES (385, 'hitwh001', 'logout', '2019-04-22 21:42:32', NULL);
INSERT INTO `userLog` VALUES (386, 'hitwh001', 'login', '2019-04-23 20:10:50', NULL);
INSERT INTO `userLog` VALUES (387, 'hitwh001', 'logout', '2019-04-23 20:15:49', NULL);
INSERT INTO `userLog` VALUES (388, 'hitwh001', 'login', '2019-04-23 20:23:17', NULL);
INSERT INTO `userLog` VALUES (389, 'hitwh001', 'login', '2019-04-23 20:26:00', NULL);
INSERT INTO `userLog` VALUES (390, 'hitwh001', 'login', '2019-04-23 20:27:08', NULL);
INSERT INTO `userLog` VALUES (391, 'hitwh001', 'login', '2019-04-23 20:30:44', NULL);
INSERT INTO `userLog` VALUES (392, 'hitwh001', 'login', '2019-04-23 20:54:46', NULL);
INSERT INTO `userLog` VALUES (393, 'hitwh001', 'login', '2019-04-23 20:59:41', NULL);
INSERT INTO `userLog` VALUES (394, 'hitwh001', 'login', '2019-04-23 21:00:37', NULL);
INSERT INTO `userLog` VALUES (395, 'hitwh001', 'login', '2019-04-23 21:03:02', NULL);
INSERT INTO `userLog` VALUES (396, 'hitwh001', 'login', '2019-04-23 21:05:54', NULL);
INSERT INTO `userLog` VALUES (397, 'hitwh001', 'login', '2019-04-23 21:06:59', NULL);
INSERT INTO `userLog` VALUES (398, 'hitwh001', 'login', '2019-04-23 21:08:40', NULL);
INSERT INTO `userLog` VALUES (399, 'hitwh001', 'login', '2019-04-23 21:12:32', NULL);
INSERT INTO `userLog` VALUES (400, 'hitwh001', 'login', '2019-04-23 21:24:19', NULL);
INSERT INTO `userLog` VALUES (401, 'hitwh001', 'login', '2019-04-23 21:27:41', NULL);
INSERT INTO `userLog` VALUES (402, 'hitwh001', 'login', '2019-04-23 21:32:42', NULL);
INSERT INTO `userLog` VALUES (403, 'hitwh001', 'login', '2019-04-23 21:32:59', NULL);
INSERT INTO `userLog` VALUES (404, 'hitwh001', 'login', '2019-04-23 21:34:34', NULL);
INSERT INTO `userLog` VALUES (405, 'hitwh001', 'login', '2019-04-23 21:35:33', NULL);
INSERT INTO `userLog` VALUES (406, 'hitwh001', 'login', '2019-04-23 21:36:25', NULL);
INSERT INTO `userLog` VALUES (407, 'hitwh001', 'login', '2019-04-23 21:39:54', NULL);
INSERT INTO `userLog` VALUES (408, 'hitwh001', 'login', '2019-04-23 21:42:14', NULL);
INSERT INTO `userLog` VALUES (409, 'hitwh001', 'login', '2019-04-23 21:44:46', NULL);
INSERT INTO `userLog` VALUES (410, 'hitwh001', 'logout', '2019-04-23 21:44:59', NULL);
INSERT INTO `userLog` VALUES (411, 'hitwh001', 'login', '2019-04-23 21:46:56', NULL);
INSERT INTO `userLog` VALUES (412, 'hitwh001', 'login', '2019-04-23 21:48:56', NULL);
INSERT INTO `userLog` VALUES (413, 'hitwh001', 'logout', '2019-04-23 21:49:14', NULL);
INSERT INTO `userLog` VALUES (414, 'hitwh001', 'login', '2019-04-23 21:50:35', NULL);
INSERT INTO `userLog` VALUES (415, 'hitwh001', 'login', '2019-04-23 21:52:34', NULL);
INSERT INTO `userLog` VALUES (416, 'hitwh001', 'login', '2019-04-23 22:03:43', NULL);
INSERT INTO `userLog` VALUES (417, 'hitwh001', 'login', '2019-04-23 22:18:22', NULL);
INSERT INTO `userLog` VALUES (418, 'hitwh001', 'login', '2019-04-23 22:19:28', NULL);
INSERT INTO `userLog` VALUES (419, 'hitwh001', 'login', '2019-04-23 22:21:29', NULL);
INSERT INTO `userLog` VALUES (420, 'hitwh001', 'login', '2019-04-23 22:59:26', NULL);
INSERT INTO `userLog` VALUES (421, 'hitwh001', 'login', '2019-04-23 23:03:27', NULL);
INSERT INTO `userLog` VALUES (422, 'hitwh001', 'login', '2019-04-23 23:12:19', NULL);
INSERT INTO `userLog` VALUES (423, 'hitwh001', 'login', '2019-04-23 23:16:40', NULL);
INSERT INTO `userLog` VALUES (424, 'hitwh001', 'login', '2019-04-23 23:23:45', NULL);
INSERT INTO `userLog` VALUES (425, 'hitwh001', 'login', '2019-04-23 23:24:36', NULL);
INSERT INTO `userLog` VALUES (426, 'hitwh001', 'logout', '2019-04-23 23:25:18', NULL);
INSERT INTO `userLog` VALUES (427, 'hitwh001', 'login', '2019-04-23 23:35:28', NULL);
INSERT INTO `userLog` VALUES (428, 'hitwh001', 'login', '2019-04-24 10:35:30', NULL);
INSERT INTO `userLog` VALUES (429, 'hitwh001', 'login', '2019-04-24 11:27:12', NULL);
INSERT INTO `userLog` VALUES (430, 'hitwh001', 'login', '2019-04-24 11:29:14', NULL);
INSERT INTO `userLog` VALUES (431, 'hitwh001', 'logout', '2019-04-24 11:29:52', NULL);
INSERT INTO `userLog` VALUES (432, 'hitwh001', 'login', '2019-04-24 11:33:52', NULL);
INSERT INTO `userLog` VALUES (433, 'hitwh001', 'logout', '2019-04-24 11:34:28', NULL);
INSERT INTO `userLog` VALUES (434, 'hitwh001', 'login', '2019-04-24 11:36:18', NULL);
INSERT INTO `userLog` VALUES (435, 'hitwh001', 'logout', '2019-04-24 11:36:47', NULL);
INSERT INTO `userLog` VALUES (436, 'hitwh001', 'login', '2019-04-24 11:39:51', NULL);
INSERT INTO `userLog` VALUES (437, 'hitwh001', 'logout', '2019-04-24 11:40:18', NULL);
INSERT INTO `userLog` VALUES (438, 'hitwh001', 'login', '2019-04-24 12:17:08', NULL);
INSERT INTO `userLog` VALUES (439, 'hitwh001', 'login', '2019-04-24 12:19:50', NULL);
INSERT INTO `userLog` VALUES (440, 'hitwh001', 'logout', '2019-04-24 12:20:38', NULL);
INSERT INTO `userLog` VALUES (441, 'hitwh001', 'login', '2019-04-24 12:58:42', NULL);
INSERT INTO `userLog` VALUES (442, 'hitwh001', 'login', '2019-04-24 13:01:33', NULL);
INSERT INTO `userLog` VALUES (443, 'hitwh001', 'logout', '2019-04-24 13:01:58', NULL);
INSERT INTO `userLog` VALUES (444, 'hitwh001', 'login', '2019-04-24 14:07:52', NULL);
INSERT INTO `userLog` VALUES (445, 'hitwh001', 'login', '2019-04-24 14:09:29', NULL);
INSERT INTO `userLog` VALUES (446, 'hitwh001', 'login', '2019-04-24 14:11:24', NULL);
INSERT INTO `userLog` VALUES (447, 'hitwh001', 'login', '2019-04-24 14:15:10', NULL);
INSERT INTO `userLog` VALUES (448, 'hitwh001', 'login', '2019-04-24 14:16:53', NULL);
INSERT INTO `userLog` VALUES (449, 'hitwh001', 'logout', '2019-04-24 14:17:24', NULL);
INSERT INTO `userLog` VALUES (450, 'hitwh001', 'login', '2019-04-24 14:25:08', NULL);
INSERT INTO `userLog` VALUES (451, 'hitwh001', 'logout', '2019-04-24 14:27:20', NULL);
INSERT INTO `userLog` VALUES (452, 'hitwh001', 'login', '2019-04-24 15:07:51', NULL);
INSERT INTO `userLog` VALUES (453, 'hitwh001', 'logout', '2019-04-24 15:08:10', NULL);
INSERT INTO `userLog` VALUES (454, 'hitwh001', 'login', '2019-04-24 15:11:27', NULL);
INSERT INTO `userLog` VALUES (455, 'hitwh001', 'logout', '2019-04-24 15:12:07', NULL);
INSERT INTO `userLog` VALUES (456, 'hitwh001', 'login', '2019-04-24 16:22:32', NULL);
INSERT INTO `userLog` VALUES (457, 'hitwh001', 'logout', '2019-04-24 16:24:13', NULL);
INSERT INTO `userLog` VALUES (458, 'hitwh001', 'login', '2019-04-24 16:30:03', NULL);
INSERT INTO `userLog` VALUES (459, 'hitwh001', 'logout', '2019-04-24 16:31:04', NULL);
INSERT INTO `userLog` VALUES (460, 'hitwh001', 'login', '2019-04-24 16:35:09', NULL);
INSERT INTO `userLog` VALUES (461, 'hitwh001', 'logout', '2019-04-24 16:35:42', NULL);
INSERT INTO `userLog` VALUES (462, 'hitwh001', 'login', '2019-04-24 16:37:38', NULL);
INSERT INTO `userLog` VALUES (463, 'hitwh001', 'login', '2019-04-24 16:45:06', NULL);
INSERT INTO `userLog` VALUES (464, 'hitwh001', 'logout', '2019-04-24 16:45:34', NULL);
INSERT INTO `userLog` VALUES (465, 'hitwh001', 'login', '2019-04-24 16:53:03', NULL);
INSERT INTO `userLog` VALUES (466, 'hitwh001', 'logout', '2019-04-24 16:53:48', NULL);
INSERT INTO `userLog` VALUES (467, 'hitwh001', 'login', '2019-04-24 16:57:02', NULL);
INSERT INTO `userLog` VALUES (468, 'hitwh001', 'logout', '2019-04-24 16:57:31', NULL);
INSERT INTO `userLog` VALUES (469, 'hitwh001', 'login', '2019-04-24 17:01:53', NULL);
INSERT INTO `userLog` VALUES (470, 'hitwh001', 'logout', '2019-04-24 17:02:09', NULL);
INSERT INTO `userLog` VALUES (471, 'hitwh001', 'login', '2019-04-24 18:52:30', NULL);
INSERT INTO `userLog` VALUES (472, 'hitwh001', 'logout', '2019-04-24 18:53:00', NULL);
INSERT INTO `userLog` VALUES (473, 'hitwh001', 'login', '2019-04-24 18:53:47', NULL);
INSERT INTO `userLog` VALUES (474, 'hitwh001', 'logout', '2019-04-24 18:54:11', NULL);
INSERT INTO `userLog` VALUES (475, 'hitwh001', 'login', '2019-04-24 18:55:17', NULL);
INSERT INTO `userLog` VALUES (476, 'hitwh001', 'logout', '2019-04-24 18:55:34', NULL);
INSERT INTO `userLog` VALUES (477, 'hitwh001', 'login', '2019-04-24 18:58:31', NULL);
INSERT INTO `userLog` VALUES (478, 'hitwh001', 'login', '2019-04-24 19:03:05', NULL);
INSERT INTO `userLog` VALUES (479, 'hitwh001', 'logout', '2019-04-24 19:03:16', NULL);
INSERT INTO `userLog` VALUES (480, 'hitwh001', 'login', '2019-04-24 19:04:36', NULL);
INSERT INTO `userLog` VALUES (481, 'hitwh001', 'login', '2019-04-24 19:08:53', NULL);
INSERT INTO `userLog` VALUES (482, 'hitwh001', 'logout', '2019-04-24 19:09:13', NULL);
INSERT INTO `userLog` VALUES (483, 'hitwh001', 'login', '2019-04-24 19:12:34', NULL);
INSERT INTO `userLog` VALUES (484, 'hitwh001', 'login', '2019-04-24 19:13:55', NULL);
INSERT INTO `userLog` VALUES (485, 'hitwh001', 'login', '2019-04-24 19:17:40', NULL);
INSERT INTO `userLog` VALUES (486, 'hitwh001', 'logout', '2019-04-24 19:17:49', NULL);
INSERT INTO `userLog` VALUES (487, 'hitwh001', 'login', '2019-04-24 19:18:31', NULL);
INSERT INTO `userLog` VALUES (488, 'hitwh001', 'login', '2019-04-24 19:20:04', NULL);
INSERT INTO `userLog` VALUES (489, 'hitwh001', 'login', '2019-04-24 19:20:53', NULL);
INSERT INTO `userLog` VALUES (490, 'hitwh001', 'logout', '2019-04-24 19:21:48', NULL);
INSERT INTO `userLog` VALUES (491, 'hitwh001', 'login', '2019-04-24 19:22:38', NULL);
INSERT INTO `userLog` VALUES (492, 'hitwh001', 'login', '2019-04-24 19:25:07', NULL);
INSERT INTO `userLog` VALUES (493, 'hitwh001', 'logout', '2019-04-24 19:25:56', NULL);
INSERT INTO `userLog` VALUES (494, 'hitwh001', 'login', '2019-04-24 19:27:02', NULL);
INSERT INTO `userLog` VALUES (495, 'hitwh001', 'logout', '2019-04-24 19:27:41', NULL);
INSERT INTO `userLog` VALUES (496, 'hitwh001', 'login', '2019-04-24 19:29:04', NULL);
INSERT INTO `userLog` VALUES (497, 'hitwh001', 'logout', '2019-04-24 19:29:27', NULL);
INSERT INTO `userLog` VALUES (498, 'hitwh001', 'login', '2019-04-24 19:40:17', NULL);
INSERT INTO `userLog` VALUES (499, 'hitwh001', 'logout', '2019-04-24 19:40:48', NULL);
INSERT INTO `userLog` VALUES (500, 'hitwh001', 'login', '2019-04-24 20:10:49', NULL);
INSERT INTO `userLog` VALUES (501, 'hitwh001', 'logout', '2019-04-24 20:11:29', NULL);
INSERT INTO `userLog` VALUES (502, 'hitwh001', 'login', '2019-04-24 20:12:59', NULL);
INSERT INTO `userLog` VALUES (503, 'hitwh001', 'logout', '2019-04-24 20:13:18', NULL);
INSERT INTO `userLog` VALUES (504, 'hitwh001', 'login', '2019-04-24 20:13:20', NULL);
INSERT INTO `userLog` VALUES (505, 'hitwh001', 'logout', '2019-04-24 20:13:27', NULL);
INSERT INTO `userLog` VALUES (506, 'hitwh001', 'login', '2019-04-24 20:13:30', NULL);
INSERT INTO `userLog` VALUES (507, 'hitwh001', 'login', '2019-04-24 20:16:42', NULL);
INSERT INTO `userLog` VALUES (508, 'hitwh001', 'login', '2019-04-24 20:20:38', NULL);
INSERT INTO `userLog` VALUES (509, 'hitwh001', 'login', '2019-04-24 20:22:40', NULL);
INSERT INTO `userLog` VALUES (510, 'hitwh001', 'login', '2019-04-24 20:24:47', NULL);
INSERT INTO `userLog` VALUES (511, 'hitwh001', 'logout', '2019-04-24 20:25:15', NULL);
INSERT INTO `userLog` VALUES (512, 'hitwh001', 'login', '2019-04-24 20:25:41', NULL);
INSERT INTO `userLog` VALUES (513, 'hitwh001', 'login', '2019-04-24 20:36:56', NULL);
INSERT INTO `userLog` VALUES (514, 'hitwh001', 'logout', '2019-04-24 20:37:20', NULL);
INSERT INTO `userLog` VALUES (515, 'hitwh001', 'login', '2019-04-24 20:48:26', NULL);
INSERT INTO `userLog` VALUES (516, 'hitwh001', 'login', '2019-04-24 20:48:45', NULL);
INSERT INTO `userLog` VALUES (517, 'hitwh001', 'logout', '2019-04-24 20:50:19', NULL);
INSERT INTO `userLog` VALUES (518, 'hitwh001', 'login', '2019-04-24 20:52:27', NULL);
INSERT INTO `userLog` VALUES (519, 'hitwh001', 'logout', '2019-04-24 20:52:44', NULL);
INSERT INTO `userLog` VALUES (520, 'hitwh001', 'login', '2019-04-24 20:52:45', NULL);
INSERT INTO `userLog` VALUES (521, 'hitwh001', 'logout', '2019-04-24 20:52:58', NULL);
INSERT INTO `userLog` VALUES (522, 'hitwh001', 'login', '2019-04-24 21:05:15', NULL);
INSERT INTO `userLog` VALUES (523, 'hitwh001', 'logout', '2019-04-24 21:06:23', NULL);
INSERT INTO `userLog` VALUES (524, 'hitwh001', 'login', '2019-04-24 21:08:32', NULL);
INSERT INTO `userLog` VALUES (525, 'hitwh001', 'logout', '2019-04-24 21:09:16', NULL);
INSERT INTO `userLog` VALUES (526, 'hitwh001', 'login', '2019-04-24 21:11:31', NULL);
INSERT INTO `userLog` VALUES (527, 'hitwh001', 'logout', '2019-04-24 21:11:56', NULL);
INSERT INTO `userLog` VALUES (528, 'hitwh001', 'login', '2019-04-24 21:21:01', NULL);
INSERT INTO `userLog` VALUES (529, 'hitwh001', 'login', '2019-04-24 21:21:57', NULL);
INSERT INTO `userLog` VALUES (530, 'hitwh001', 'logout', '2019-04-24 21:23:01', NULL);
INSERT INTO `userLog` VALUES (531, 'hitwh001', 'login', '2019-04-24 21:32:52', NULL);
INSERT INTO `userLog` VALUES (532, 'hitwh001', 'logout', '2019-04-24 21:33:32', NULL);
INSERT INTO `userLog` VALUES (533, 'hitwh001', 'login', '2019-04-24 21:49:21', NULL);
INSERT INTO `userLog` VALUES (534, 'hitwh001', 'logout', '2019-04-24 21:50:04', NULL);
INSERT INTO `userLog` VALUES (535, 'hitwh001', 'login', '2019-04-24 21:50:56', NULL);
INSERT INTO `userLog` VALUES (536, 'hitwh001', 'logout', '2019-04-24 21:51:35', NULL);
INSERT INTO `userLog` VALUES (537, 'hitwh001', 'login', '2019-04-24 21:57:10', NULL);
INSERT INTO `userLog` VALUES (538, 'hitwh001', 'logout', '2019-04-24 21:57:46', NULL);
INSERT INTO `userLog` VALUES (539, 'hitwh001', 'login', '2019-04-24 22:12:12', NULL);
INSERT INTO `userLog` VALUES (540, 'hitwh001', 'logout', '2019-04-24 22:14:31', NULL);
INSERT INTO `userLog` VALUES (541, 'hitwh001', 'login', '2019-04-25 14:40:11', NULL);
INSERT INTO `userLog` VALUES (542, 'hitwh002', 'login', '2019-04-25 14:45:51', NULL);
INSERT INTO `userLog` VALUES (543, 'hitwh002', 'logout', '2019-04-25 14:46:04', NULL);
INSERT INTO `userLog` VALUES (544, 'hitwh001', 'login', '2019-04-25 14:49:48', NULL);
INSERT INTO `userLog` VALUES (545, 'hitwh001', 'logout', '2019-04-25 14:50:02', NULL);
INSERT INTO `userLog` VALUES (546, 'hitwh001', 'login', '2019-04-25 15:34:41', NULL);
INSERT INTO `userLog` VALUES (547, 'hitwh002', 'login', '2019-04-25 16:43:35', NULL);
INSERT INTO `userLog` VALUES (548, 'hitwh002', 'logout', '2019-04-25 16:43:45', NULL);
INSERT INTO `userLog` VALUES (549, 'hitwh001', 'login', '2019-04-25 18:40:40', NULL);
INSERT INTO `userLog` VALUES (550, 'hitwh001', 'logout', '2019-04-25 18:42:14', NULL);
INSERT INTO `userLog` VALUES (551, 'hitwh001', 'login', '2019-04-25 18:45:53', NULL);
INSERT INTO `userLog` VALUES (552, 'hitwh001', 'logout', '2019-04-25 18:47:26', NULL);
INSERT INTO `userLog` VALUES (553, 'hitwh001', 'login', '2019-04-25 18:52:25', NULL);
INSERT INTO `userLog` VALUES (554, 'hitwh001', 'login', '2019-04-25 19:05:30', NULL);
INSERT INTO `userLog` VALUES (555, 'hitwh001', 'logout', '2019-04-25 19:05:53', NULL);
INSERT INTO `userLog` VALUES (556, 'hitwh001', 'login', '2019-04-25 19:21:03', NULL);
INSERT INTO `userLog` VALUES (557, 'hitwh001', 'login', '2019-04-25 19:28:17', NULL);
INSERT INTO `userLog` VALUES (558, 'hitwh001', 'login', '2019-04-25 19:32:38', NULL);
INSERT INTO `userLog` VALUES (559, 'hitwh001', 'login', '2019-04-25 19:33:40', NULL);
INSERT INTO `userLog` VALUES (560, 'hitwh001', 'logout', '2019-04-25 19:33:55', NULL);
INSERT INTO `userLog` VALUES (561, 'hitwh001', 'login', '2019-04-25 19:36:35', NULL);
INSERT INTO `userLog` VALUES (562, 'hitwh001', 'login', '2019-04-25 19:38:27', NULL);
INSERT INTO `userLog` VALUES (563, 'hitwh001', 'login', '2019-04-25 19:41:19', NULL);
INSERT INTO `userLog` VALUES (564, 'hitwh001', 'login', '2019-04-25 19:43:42', NULL);
INSERT INTO `userLog` VALUES (565, 'hitwh001', 'login', '2019-04-25 19:46:22', NULL);
INSERT INTO `userLog` VALUES (566, 'hitwh001', 'login', '2019-04-25 19:47:33', NULL);
INSERT INTO `userLog` VALUES (567, 'hitwh001', 'login', '2019-04-25 19:48:30', NULL);
INSERT INTO `userLog` VALUES (568, 'hitwh001', 'logout', '2019-04-25 19:48:47', NULL);
INSERT INTO `userLog` VALUES (569, 'hitwh001', 'login', '2019-04-25 19:52:29', NULL);
INSERT INTO `userLog` VALUES (570, 'hitwh001', 'login', '2019-04-25 20:19:24', NULL);
INSERT INTO `userLog` VALUES (571, 'hitwh001', 'logout', '2019-04-25 20:25:31', NULL);
INSERT INTO `userLog` VALUES (572, 'hitwh001', 'login', '2019-04-25 20:30:55', NULL);
INSERT INTO `userLog` VALUES (573, 'hitwh001', 'login', '2019-04-25 20:34:56', NULL);
INSERT INTO `userLog` VALUES (574, 'hitwh001', 'login', '2019-04-25 20:39:50', NULL);
INSERT INTO `userLog` VALUES (575, 'hitwh001', 'login', '2019-04-25 20:41:11', NULL);
INSERT INTO `userLog` VALUES (576, 'hitwh001', 'login', '2019-04-25 20:50:22', NULL);
INSERT INTO `userLog` VALUES (577, 'hitwh001', 'logout', '2019-04-25 20:52:36', NULL);
INSERT INTO `userLog` VALUES (578, 'hitwh001', 'login', '2019-04-25 21:32:56', NULL);
INSERT INTO `userLog` VALUES (579, 'hitwh001', 'login', '2019-04-25 21:36:50', NULL);
INSERT INTO `userLog` VALUES (580, 'hitwh001', 'login', '2019-04-25 21:39:09', NULL);
INSERT INTO `userLog` VALUES (581, 'hitwh001', 'logout', '2019-04-25 21:40:03', NULL);
INSERT INTO `userLog` VALUES (582, 'hitwh001', 'login', '2019-04-25 21:50:49', NULL);
INSERT INTO `userLog` VALUES (583, 'hitwh001', 'login', '2019-04-25 21:59:35', NULL);
INSERT INTO `userLog` VALUES (584, 'hitwh001', 'login', '2019-04-25 22:06:32', NULL);
INSERT INTO `userLog` VALUES (585, 'hitwh001', 'logout', '2019-04-25 22:07:21', NULL);
INSERT INTO `userLog` VALUES (586, 'hitwh001', 'login', '2019-04-25 22:10:12', NULL);
INSERT INTO `userLog` VALUES (587, 'hitwh001', 'login', '2019-04-25 22:12:31', NULL);
INSERT INTO `userLog` VALUES (588, 'hitwh001', 'logout', '2019-04-25 22:15:03', NULL);
INSERT INTO `userLog` VALUES (589, 'hitwh001', 'login', '2019-04-25 23:05:20', NULL);
INSERT INTO `userLog` VALUES (590, 'hitwh001', 'login', '2019-04-25 23:06:31', NULL);
INSERT INTO `userLog` VALUES (591, 'hitwh001', 'login', '2019-04-25 23:08:02', NULL);
INSERT INTO `userLog` VALUES (592, 'hitwh001', 'login', '2019-04-25 23:09:12', NULL);
INSERT INTO `userLog` VALUES (593, 'hitwh001', 'logout', '2019-04-25 23:10:00', NULL);
INSERT INTO `userLog` VALUES (594, 'hitwh001', 'login', '2019-04-25 23:19:56', NULL);
INSERT INTO `userLog` VALUES (595, 'hitwh001', 'login', '2019-04-25 23:21:31', NULL);
INSERT INTO `userLog` VALUES (596, 'hitwh001', 'logout', '2019-04-25 23:21:49', NULL);
INSERT INTO `userLog` VALUES (597, 'hitwh001', 'login', '2019-04-25 23:35:54', NULL);
INSERT INTO `userLog` VALUES (598, 'hitwh001', 'login', '2019-04-25 23:37:05', NULL);
INSERT INTO `userLog` VALUES (599, 'hitwh001', 'login', '2019-04-25 23:38:02', NULL);
INSERT INTO `userLog` VALUES (600, 'hitwh001', 'login', '2019-04-25 23:38:16', NULL);
INSERT INTO `userLog` VALUES (601, 'hitwh001', 'login', '2019-04-25 23:38:34', NULL);
INSERT INTO `userLog` VALUES (602, 'hitwh001', 'login', '2019-04-25 23:39:08', NULL);
INSERT INTO `userLog` VALUES (603, 'hitwh001', 'login', '2019-04-25 23:45:36', NULL);
INSERT INTO `userLog` VALUES (604, 'hitwh001', 'login', '2019-04-25 23:46:41', NULL);
INSERT INTO `userLog` VALUES (605, 'hitwh001', 'login', '2019-04-25 23:49:56', NULL);
INSERT INTO `userLog` VALUES (606, 'hitwh001', 'login', '2019-04-25 23:50:49', NULL);
INSERT INTO `userLog` VALUES (607, 'hitwh001', 'login', '2019-04-25 23:52:26', NULL);
INSERT INTO `userLog` VALUES (608, 'hitwh001', 'login', '2019-04-25 23:55:12', NULL);
INSERT INTO `userLog` VALUES (609, 'hitwh001', 'login', '2019-04-26 00:03:53', NULL);
INSERT INTO `userLog` VALUES (610, 'hitwh001', 'logout', '2019-04-26 00:04:32', NULL);
INSERT INTO `userLog` VALUES (611, 'hitwh001', 'login', '2019-04-26 00:08:47', NULL);
INSERT INTO `userLog` VALUES (612, 'hitwh001', 'login', '2019-04-26 00:08:54', NULL);
INSERT INTO `userLog` VALUES (613, 'hitwh001', 'login', '2019-04-26 00:10:05', NULL);
INSERT INTO `userLog` VALUES (614, 'hitwh001', 'login', '2019-04-26 00:13:02', NULL);
INSERT INTO `userLog` VALUES (615, 'hitwh001', 'login', '2019-04-26 00:15:20', NULL);
INSERT INTO `userLog` VALUES (616, 'hitwh001', 'login', '2019-04-26 00:16:18', NULL);
INSERT INTO `userLog` VALUES (617, 'hitwh001', 'login', '2019-04-26 00:17:21', NULL);
INSERT INTO `userLog` VALUES (618, 'hitwh001', 'login', '2019-04-26 00:18:49', NULL);
INSERT INTO `userLog` VALUES (619, 'hitwh001', 'login', '2019-04-26 09:30:56', NULL);
INSERT INTO `userLog` VALUES (620, 'hitwh001', 'logout', '2019-04-26 09:31:36', NULL);
INSERT INTO `userLog` VALUES (621, 'hitwh001', 'login', '2019-04-26 09:37:32', NULL);
INSERT INTO `userLog` VALUES (622, 'hitwh001', 'login', '2019-04-26 09:39:11', NULL);
INSERT INTO `userLog` VALUES (623, 'hitwh001', 'login', '2019-04-26 09:40:30', NULL);
INSERT INTO `userLog` VALUES (624, 'hitwh001', 'login', '2019-04-26 09:41:45', NULL);
INSERT INTO `userLog` VALUES (625, 'hitwh001', 'logout', '2019-04-26 09:42:09', NULL);
INSERT INTO `userLog` VALUES (626, 'hitwh001', 'login', '2019-04-26 09:43:37', NULL);
INSERT INTO `userLog` VALUES (627, 'hitwh001', 'logout', '2019-04-26 09:43:58', NULL);
INSERT INTO `userLog` VALUES (628, 'hitwh001', 'login', '2019-04-26 10:52:07', NULL);
INSERT INTO `userLog` VALUES (629, 'hitwh001', 'login', '2019-04-26 12:42:47', NULL);
INSERT INTO `userLog` VALUES (630, 'hitwh001', 'login', '2019-04-26 12:44:33', NULL);
INSERT INTO `userLog` VALUES (631, 'hitwh001', 'login', '2019-04-26 12:50:16', NULL);
INSERT INTO `userLog` VALUES (632, 'hitwh001', 'login', '2019-04-26 12:54:05', NULL);
INSERT INTO `userLog` VALUES (633, 'hitwh001', 'login', '2019-04-26 13:01:29', NULL);
INSERT INTO `userLog` VALUES (634, 'hitwh001', 'login', '2019-04-26 13:03:41', NULL);
INSERT INTO `userLog` VALUES (635, 'hitwh001', 'login', '2019-04-26 13:08:30', NULL);
INSERT INTO `userLog` VALUES (636, 'hitwh001', 'login', '2019-04-26 13:13:25', NULL);
INSERT INTO `userLog` VALUES (637, 'hitwh001', 'login', '2019-04-26 13:15:08', NULL);
INSERT INTO `userLog` VALUES (638, 'hitwh001', 'login', '2019-04-26 13:22:58', NULL);
INSERT INTO `userLog` VALUES (639, 'hitwh001', 'login', '2019-04-26 13:24:57', NULL);
INSERT INTO `userLog` VALUES (640, 'hitwh001', 'login', '2019-04-26 13:26:52', NULL);
INSERT INTO `userLog` VALUES (641, 'hitwh001', 'login', '2019-04-26 13:36:16', NULL);
INSERT INTO `userLog` VALUES (642, 'hitwh001', 'login', '2019-04-26 13:38:37', NULL);
INSERT INTO `userLog` VALUES (643, 'hitwh001', 'logout', '2019-04-26 13:38:59', NULL);
INSERT INTO `userLog` VALUES (644, 'hitwh001', 'login', '2019-04-26 13:44:22', NULL);
INSERT INTO `userLog` VALUES (645, 'hitwh001', 'login', '2019-04-26 13:48:02', NULL);
INSERT INTO `userLog` VALUES (646, 'hitwh001', 'login', '2019-04-26 13:50:16', NULL);
INSERT INTO `userLog` VALUES (647, 'hitwh001', 'login', '2019-04-26 13:56:34', NULL);
INSERT INTO `userLog` VALUES (648, 'hitwh001', 'login', '2019-04-26 13:59:12', NULL);
INSERT INTO `userLog` VALUES (649, 'hitwh001', 'login', '2019-04-26 14:03:07', NULL);
INSERT INTO `userLog` VALUES (650, 'hitwh001', 'login', '2019-04-26 14:04:05', NULL);
INSERT INTO `userLog` VALUES (651, 'hitwh001', 'login', '2019-04-26 14:05:33', NULL);
INSERT INTO `userLog` VALUES (652, 'hitwh001', 'logout', '2019-04-26 14:06:03', NULL);
INSERT INTO `userLog` VALUES (653, 'hitwh001', 'login', '2019-04-26 14:07:39', NULL);
INSERT INTO `userLog` VALUES (654, 'hitwh001', 'login', '2019-04-26 14:16:31', NULL);
INSERT INTO `userLog` VALUES (655, 'hitwh001', 'login', '2019-04-26 14:17:58', NULL);
INSERT INTO `userLog` VALUES (656, 'hitwh001', 'login', '2019-04-26 14:21:07', NULL);
INSERT INTO `userLog` VALUES (657, 'hitwh001', 'login', '2019-04-26 14:24:29', NULL);
INSERT INTO `userLog` VALUES (658, 'hitwh001', 'login', '2019-04-26 14:26:05', NULL);
INSERT INTO `userLog` VALUES (659, 'hitwh001', 'login', '2019-04-26 14:27:41', NULL);
INSERT INTO `userLog` VALUES (660, 'hitwh001', 'login', '2019-04-26 14:29:00', NULL);
INSERT INTO `userLog` VALUES (661, 'hitwh001', 'login', '2019-04-26 14:30:29', NULL);
INSERT INTO `userLog` VALUES (662, 'hitwh001', 'logout', '2019-04-26 14:30:49', NULL);
INSERT INTO `userLog` VALUES (663, 'hitwh001', 'login', '2019-04-26 14:32:22', NULL);
INSERT INTO `userLog` VALUES (664, 'hitwh001', 'login', '2019-04-26 14:45:53', NULL);
INSERT INTO `userLog` VALUES (665, 'hitwh001', 'logout', '2019-04-26 14:46:53', NULL);
INSERT INTO `userLog` VALUES (666, 'hitwh001', 'login', '2019-04-26 14:47:50', NULL);
INSERT INTO `userLog` VALUES (667, 'hitwh001', 'logout', '2019-04-26 14:48:35', NULL);
INSERT INTO `userLog` VALUES (668, 'hitwh001', 'login', '2019-04-26 14:49:58', NULL);
INSERT INTO `userLog` VALUES (669, 'hitwh001', 'logout', '2019-04-26 14:50:52', NULL);
INSERT INTO `userLog` VALUES (670, 'hitwh001', 'login', '2019-04-26 14:57:14', NULL);
INSERT INTO `userLog` VALUES (671, 'hitwh001', 'login', '2019-04-26 14:59:29', NULL);
INSERT INTO `userLog` VALUES (672, 'hitwh001', 'login', '2019-04-26 15:01:05', NULL);
INSERT INTO `userLog` VALUES (673, 'hitwh001', 'login', '2019-04-26 15:02:54', NULL);
INSERT INTO `userLog` VALUES (674, 'hitwh001', 'logout', '2019-04-26 15:03:23', NULL);
INSERT INTO `userLog` VALUES (675, 'hitwh001', 'login', '2019-04-26 15:19:50', NULL);
INSERT INTO `userLog` VALUES (676, 'hitwh001', 'login', '2019-04-26 15:20:09', NULL);
INSERT INTO `userLog` VALUES (677, 'hitwh001', 'login', '2019-04-26 15:20:44', NULL);
INSERT INTO `userLog` VALUES (678, 'hitwh001', 'login', '2019-04-26 15:21:35', NULL);
INSERT INTO `userLog` VALUES (679, 'hitwh001', 'logout', '2019-04-26 15:23:08', NULL);
INSERT INTO `userLog` VALUES (680, 'hitwh001', 'login', '2019-04-26 15:26:43', NULL);
INSERT INTO `userLog` VALUES (681, 'hitwh001', 'login', '2019-04-26 15:30:57', NULL);
INSERT INTO `userLog` VALUES (682, 'hitwh001', 'login', '2019-04-26 15:32:56', NULL);
INSERT INTO `userLog` VALUES (683, 'hitwh001', 'login', '2019-04-26 15:50:40', NULL);
INSERT INTO `userLog` VALUES (684, 'hitwh001', 'login', '2019-04-26 15:53:12', NULL);
INSERT INTO `userLog` VALUES (685, 'hitwh001', 'logout', '2019-04-26 15:53:49', NULL);
INSERT INTO `userLog` VALUES (686, 'hitwh001', 'login', '2019-04-26 15:55:27', NULL);
INSERT INTO `userLog` VALUES (687, 'hitwh001', 'logout', '2019-04-26 15:56:17', NULL);
INSERT INTO `userLog` VALUES (688, 'hitwh001', 'login', '2019-04-26 16:06:42', NULL);
INSERT INTO `userLog` VALUES (689, 'hitwh001', 'logout', '2019-04-26 16:07:22', NULL);
INSERT INTO `userLog` VALUES (690, 'hitwh001', 'login', '2019-04-26 16:08:17', NULL);
INSERT INTO `userLog` VALUES (691, 'hitwh001', 'logout', '2019-04-26 16:08:39', NULL);
INSERT INTO `userLog` VALUES (692, 'hitwh001', 'login', '2019-04-26 16:13:50', NULL);
INSERT INTO `userLog` VALUES (693, 'hitwh001', 'login', '2019-04-26 16:20:27', NULL);
INSERT INTO `userLog` VALUES (694, 'hitwh001', 'logout', '2019-04-26 16:20:51', NULL);
INSERT INTO `userLog` VALUES (695, 'hitwh001', 'login', '2019-04-26 16:21:51', NULL);
INSERT INTO `userLog` VALUES (696, 'hitwh001', 'logout', '2019-04-26 16:22:43', NULL);
INSERT INTO `userLog` VALUES (697, 'hitwh001', 'login', '2019-04-26 16:32:29', NULL);
INSERT INTO `userLog` VALUES (698, 'hitwh001', 'logout', '2019-04-26 16:33:27', NULL);
INSERT INTO `userLog` VALUES (699, 'hitwh001', 'login', '2019-04-26 16:34:37', NULL);
INSERT INTO `userLog` VALUES (700, 'hitwh001', 'login', '2019-04-26 16:41:03', NULL);
INSERT INTO `userLog` VALUES (701, 'hitwh001', 'login', '2019-04-26 16:44:53', NULL);
INSERT INTO `userLog` VALUES (702, 'hitwh001', 'login', '2019-04-26 16:50:59', NULL);
INSERT INTO `userLog` VALUES (703, 'hitwh001', 'logout', '2019-04-26 16:51:56', NULL);
INSERT INTO `userLog` VALUES (704, 'hitwh001', 'login', '2019-04-26 18:59:43', NULL);
INSERT INTO `userLog` VALUES (705, 'hitwh001', 'logout', '2019-04-26 19:00:23', NULL);
INSERT INTO `userLog` VALUES (706, 'hitwh001', 'login', '2019-04-26 19:11:59', NULL);
INSERT INTO `userLog` VALUES (707, 'hitwh001', 'logout', '2019-04-26 19:14:12', NULL);
INSERT INTO `userLog` VALUES (708, 'hitwh001', 'login', '2019-04-26 19:37:28', NULL);
INSERT INTO `userLog` VALUES (709, 'hitwh001', 'logout', '2019-04-26 19:41:11', NULL);
INSERT INTO `userLog` VALUES (710, 'hitwh001', 'login', '2019-04-26 19:42:19', NULL);
INSERT INTO `userLog` VALUES (711, 'hitwh001', 'logout', '2019-04-26 19:42:50', NULL);
INSERT INTO `userLog` VALUES (712, 'hitwh001', 'login', '2019-04-26 20:14:19', NULL);
INSERT INTO `userLog` VALUES (713, 'hitwh001', 'logout', '2019-04-26 20:19:07', NULL);
INSERT INTO `userLog` VALUES (714, 'hitwh001', 'login', '2019-04-26 20:34:56', NULL);
INSERT INTO `userLog` VALUES (715, 'hitwh001', 'logout', '2019-04-26 20:36:12', NULL);
INSERT INTO `userLog` VALUES (716, 'hitwh001', 'login', '2019-04-26 20:43:08', NULL);
INSERT INTO `userLog` VALUES (717, 'hitwh001', 'logout', '2019-04-26 20:48:27', NULL);
INSERT INTO `userLog` VALUES (718, 'hitwh001', 'login', '2019-04-26 20:48:36', NULL);
INSERT INTO `userLog` VALUES (719, 'hitwh001', 'logout', '2019-04-26 20:53:29', NULL);
INSERT INTO `userLog` VALUES (720, 'hitwh001', 'login', '2019-04-26 21:33:39', NULL);
INSERT INTO `userLog` VALUES (721, 'hitwh001', 'logout', '2019-04-26 21:43:39', NULL);
INSERT INTO `userLog` VALUES (722, 'hitwh001', 'login', '2019-04-26 21:46:45', NULL);
INSERT INTO `userLog` VALUES (723, 'hitwh001', 'login', '2019-04-26 21:48:18', NULL);
INSERT INTO `userLog` VALUES (724, 'hitwh001', 'logout', '2019-04-26 21:48:22', NULL);
INSERT INTO `userLog` VALUES (725, 'hitwh001', 'login', '2019-04-26 21:53:58', NULL);
INSERT INTO `userLog` VALUES (726, 'hitwh001', 'login', '2019-04-27 07:54:56', NULL);
INSERT INTO `userLog` VALUES (727, 'hitwh001', 'logout', '2019-04-27 07:55:28', NULL);
INSERT INTO `userLog` VALUES (728, 'hitwh001', 'login', '2019-04-27 07:58:42', NULL);
INSERT INTO `userLog` VALUES (729, 'hitwh001', 'logout', '2019-04-27 07:59:32', NULL);
INSERT INTO `userLog` VALUES (730, 'hitwh001', 'login', '2019-04-27 08:37:13', NULL);
INSERT INTO `userLog` VALUES (731, 'hitwh001', 'logout', '2019-04-27 08:39:49', NULL);
INSERT INTO `userLog` VALUES (732, 'hitwh001', 'login', '2019-04-27 09:16:47', NULL);
INSERT INTO `userLog` VALUES (733, 'hitwh001', 'logout', '2019-04-27 09:17:55', NULL);
INSERT INTO `userLog` VALUES (734, 'hitwh001', 'login', '2019-04-27 09:18:45', NULL);
INSERT INTO `userLog` VALUES (735, 'hitwh001', 'logout', '2019-04-27 09:19:00', NULL);
INSERT INTO `userLog` VALUES (736, 'hitwh001', 'login', '2019-04-27 09:42:56', NULL);
INSERT INTO `userLog` VALUES (737, 'hitwh001', 'logout', '2019-04-27 09:44:17', NULL);
INSERT INTO `userLog` VALUES (738, 'hitwh001', 'login', '2019-04-27 09:44:27', NULL);
INSERT INTO `userLog` VALUES (739, 'hitwh001', 'logout', '2019-04-27 09:45:04', NULL);
INSERT INTO `userLog` VALUES (740, 'hitwh001', 'login', '2019-04-27 09:45:37', NULL);
INSERT INTO `userLog` VALUES (741, 'hitwh001', 'login', '2019-04-27 09:45:45', NULL);
INSERT INTO `userLog` VALUES (742, 'hitwh001', 'logout', '2019-04-27 09:46:01', NULL);
INSERT INTO `userLog` VALUES (743, 'hitwh001', 'logout', '2019-04-27 09:46:04', NULL);
INSERT INTO `userLog` VALUES (744, 'hitwh001', 'login', '2019-04-27 09:46:16', NULL);
INSERT INTO `userLog` VALUES (745, 'hitwh001', 'logout', '2019-04-27 09:48:06', NULL);
INSERT INTO `userLog` VALUES (746, 'hitwh001', 'login', '2019-04-27 09:48:39', NULL);
INSERT INTO `userLog` VALUES (747, 'hitwh001', 'logout', '2019-04-27 09:48:41', NULL);
INSERT INTO `userLog` VALUES (748, 'hitwh001', 'login', '2019-04-27 09:48:49', NULL);
INSERT INTO `userLog` VALUES (749, 'hitwh001', 'logout', '2019-04-27 09:48:51', NULL);
INSERT INTO `userLog` VALUES (750, 'hitwh001', 'login', '2019-04-27 09:55:56', NULL);
INSERT INTO `userLog` VALUES (751, 'hitwh001', 'logout', '2019-04-27 09:56:36', NULL);
INSERT INTO `userLog` VALUES (752, 'hitwh001', 'login', '2019-04-27 09:57:47', NULL);
INSERT INTO `userLog` VALUES (753, 'hitwh001', 'logout', '2019-04-27 09:58:14', NULL);
INSERT INTO `userLog` VALUES (754, 'hitwh001', 'login', '2019-05-11 19:15:13', NULL);
INSERT INTO `userLog` VALUES (755, 'hitwh001', 'login', '2019-05-11 19:16:14', NULL);
INSERT INTO `userLog` VALUES (756, 'hitwh001', 'logout', '2019-05-11 19:16:58', NULL);
INSERT INTO `userLog` VALUES (757, 'hitwh001', 'login', '2019-05-11 19:17:51', NULL);
INSERT INTO `userLog` VALUES (758, 'hitwh001', 'login', '2019-05-11 19:24:35', NULL);
INSERT INTO `userLog` VALUES (759, 'hitwh001', 'logout', '2019-05-11 19:33:22', NULL);
INSERT INTO `userLog` VALUES (760, 'hitwh001', 'login', '2019-05-19 21:45:30', NULL);
INSERT INTO `userLog` VALUES (761, 'hitwh001', 'login', '2019-05-20 20:13:14', NULL);
INSERT INTO `userLog` VALUES (762, 'hitwh001', 'login', '2019-05-20 21:49:46', NULL);
INSERT INTO `userLog` VALUES (763, 'hitwh001', 'logout', '2019-05-20 22:02:40', NULL);
INSERT INTO `userLog` VALUES (764, 'hitwh001', 'login', '2019-05-21 15:13:30', NULL);
INSERT INTO `userLog` VALUES (765, 'hitwh001', 'logout', '2019-05-21 15:15:31', NULL);
INSERT INTO `userLog` VALUES (766, 'hitwh001', 'logout', '2019-05-21 15:15:53', NULL);
INSERT INTO `userLog` VALUES (767, 'hitwh001', 'login', '2019-05-21 16:21:37', NULL);
INSERT INTO `userLog` VALUES (768, 'hitwh001', 'logout', '2019-05-21 16:25:57', NULL);
INSERT INTO `userLog` VALUES (769, 'hitwh001', 'login', '2019-05-21 17:11:55', NULL);
INSERT INTO `userLog` VALUES (770, 'hitwh001', 'logout', '2019-05-21 17:15:00', NULL);
INSERT INTO `userLog` VALUES (771, 'hitwh001', 'login', '2019-05-21 22:20:53', NULL);
INSERT INTO `userLog` VALUES (772, 'hitwh001', 'login', '2019-05-21 22:24:29', NULL);
INSERT INTO `userLog` VALUES (773, 'hitwh001', 'login', '2019-05-22 14:44:01', NULL);
INSERT INTO `userLog` VALUES (774, 'hitwh001', 'logout', '2019-05-22 14:45:38', NULL);
INSERT INTO `userLog` VALUES (775, 'hitwh001', 'login', '2019-05-22 14:51:55', NULL);
INSERT INTO `userLog` VALUES (776, 'hitwh001', 'logout', '2019-05-22 14:54:12', NULL);
INSERT INTO `userLog` VALUES (777, 'hitwh001', 'logout', '2019-05-22 14:54:24', NULL);
INSERT INTO `userLog` VALUES (778, 'hitwh001', 'logout', '2019-05-22 14:54:30', NULL);
INSERT INTO `userLog` VALUES (779, 'hitwh001', 'logout', '2019-05-22 14:54:50', NULL);
INSERT INTO `userLog` VALUES (780, 'hitwh001', 'login', '2019-05-22 14:56:08', NULL);
INSERT INTO `userLog` VALUES (781, 'hitwh001', 'logout', '2019-05-22 15:01:09', NULL);
INSERT INTO `userLog` VALUES (782, 'hitwh001', 'login', '2019-05-22 16:10:59', NULL);
INSERT INTO `userLog` VALUES (783, 'hitwh001', 'logout', '2019-05-22 16:13:17', NULL);
INSERT INTO `userLog` VALUES (784, 'hitwh001', 'login', '2019-05-22 16:55:24', NULL);
INSERT INTO `userLog` VALUES (785, 'hitwh001', 'logout', '2019-05-22 16:56:37', NULL);
INSERT INTO `userLog` VALUES (786, 'hitwh001', 'login', '2019-05-22 16:59:29', NULL);
INSERT INTO `userLog` VALUES (787, 'hitwh001', 'logout', '2019-05-22 17:01:49', NULL);
INSERT INTO `userLog` VALUES (788, 'hitwh001', 'login', '2019-05-22 17:03:24', NULL);
INSERT INTO `userLog` VALUES (789, 'hitwh001', 'login', '2019-05-22 17:06:58', NULL);
INSERT INTO `userLog` VALUES (790, 'hitwh001', 'logout', '2019-05-22 17:09:57', NULL);
INSERT INTO `userLog` VALUES (791, 'hitwh001', 'login', '2019-05-24 13:37:38', NULL);
INSERT INTO `userLog` VALUES (792, 'hitwh001', 'logout', '2019-05-24 13:38:50', NULL);
INSERT INTO `userLog` VALUES (793, 'hitwh001', 'login', '2019-05-24 13:39:50', NULL);
INSERT INTO `userLog` VALUES (794, 'hitwh001', 'login', '2019-05-24 16:54:47', NULL);
INSERT INTO `userLog` VALUES (795, 'hitwh001', 'logout', '2019-05-24 16:55:47', NULL);
INSERT INTO `userLog` VALUES (796, 'hitwh001', 'login', '2019-05-25 15:42:55', NULL);
INSERT INTO `userLog` VALUES (797, 'hitwh001', 'login', '2019-05-25 16:22:43', NULL);
INSERT INTO `userLog` VALUES (798, 'hitwh001', 'logout', '2019-05-25 16:36:55', NULL);
INSERT INTO `userLog` VALUES (799, 'hitwh001', 'login', '2019-05-25 16:41:02', NULL);
INSERT INTO `userLog` VALUES (800, 'hitwh001', 'logout', '2019-05-25 16:41:35', NULL);
INSERT INTO `userLog` VALUES (801, 'hitwh001', 'login', '2019-05-26 13:28:46', NULL);
INSERT INTO `userLog` VALUES (802, 'hitwh001', 'logout', '2019-05-26 13:30:00', NULL);
INSERT INTO `userLog` VALUES (803, 'hitwh001', 'login', '2019-05-26 16:39:07', NULL);
INSERT INTO `userLog` VALUES (804, 'hitwh001', 'logout', '2019-05-26 16:39:12', NULL);
INSERT INTO `userLog` VALUES (805, 'hitwh001', 'login', '2019-05-26 16:55:53', NULL);
INSERT INTO `userLog` VALUES (806, 'hitwh001', 'logout', '2019-05-26 16:57:09', NULL);
INSERT INTO `userLog` VALUES (807, 'hitwh001', 'login', '2019-05-27 09:27:02', NULL);
INSERT INTO `userLog` VALUES (808, 'hitwh001', 'logout', '2019-05-27 09:31:28', NULL);
INSERT INTO `userLog` VALUES (809, 'hitwh001', 'login', '2019-05-27 14:01:50', NULL);
INSERT INTO `userLog` VALUES (810, 'hitwh001', 'logout', '2019-05-27 14:03:02', NULL);
INSERT INTO `userLog` VALUES (811, 'hitwh001', 'login', '2019-05-27 14:13:56', NULL);
INSERT INTO `userLog` VALUES (812, 'hitwh001', 'login', '2019-05-27 16:03:18', NULL);
INSERT INTO `userLog` VALUES (813, 'hitwh001', 'logout', '2019-05-27 16:05:27', NULL);
INSERT INTO `userLog` VALUES (814, 'hitwh001', 'login', '2019-05-27 19:18:54', NULL);
INSERT INTO `userLog` VALUES (815, 'hitwh001', 'logout', '2019-05-27 19:21:23', NULL);
INSERT INTO `userLog` VALUES (816, 'hitwh001', 'login', '2019-05-28 16:59:55', NULL);
INSERT INTO `userLog` VALUES (817, 'hitwh001', 'logout', '2019-05-28 16:59:59', NULL);
INSERT INTO `userLog` VALUES (818, 'hitwh001', 'login', '2019-05-29 18:33:41', NULL);
INSERT INTO `userLog` VALUES (819, 'hitwh001', 'logout', '2019-05-29 18:34:21', NULL);
INSERT INTO `userLog` VALUES (820, 'hitwh001', 'login', '2019-05-29 18:39:01', NULL);
INSERT INTO `userLog` VALUES (821, 'hitwh001', 'logout', '2019-05-29 18:42:29', NULL);
INSERT INTO `userLog` VALUES (822, 'hitwh001', 'login', '2019-05-29 18:45:30', NULL);
INSERT INTO `userLog` VALUES (823, 'hitwh001', 'logout', '2019-05-29 18:49:19', NULL);
INSERT INTO `userLog` VALUES (824, 'hitwh001', 'login', '2019-05-29 19:01:31', NULL);
INSERT INTO `userLog` VALUES (825, 'hitwh001', 'logout', '2019-05-29 19:04:25', NULL);
INSERT INTO `userLog` VALUES (826, 'hitwh001', 'login', '2019-05-29 19:26:11', NULL);
INSERT INTO `userLog` VALUES (827, 'hitwh001', 'logout', '2019-05-29 19:30:16', NULL);
INSERT INTO `userLog` VALUES (828, 'hitwh001', 'login', '2019-05-29 21:30:43', NULL);
INSERT INTO `userLog` VALUES (829, 'hitwh001', 'login', '2019-05-29 21:37:27', NULL);
INSERT INTO `userLog` VALUES (830, 'hitwh001', 'logout', '2019-05-29 21:38:02', NULL);
INSERT INTO `userLog` VALUES (831, 'hitwh001', 'login', '2019-05-29 21:39:34', NULL);
INSERT INTO `userLog` VALUES (832, 'hitwh001', 'logout', '2019-05-29 21:40:32', NULL);
INSERT INTO `userLog` VALUES (833, 'hitwh001', 'login', '2019-05-29 23:43:59', NULL);
INSERT INTO `userLog` VALUES (834, 'hitwh001', 'logout', '2019-05-29 23:44:58', NULL);
INSERT INTO `userLog` VALUES (835, 'hitwh001', 'login', '2019-05-29 23:49:32', NULL);
INSERT INTO `userLog` VALUES (836, 'hitwh001', 'logout', '2019-05-29 23:50:25', NULL);
INSERT INTO `userLog` VALUES (837, 'hitwh001', 'login', '2019-05-29 23:51:52', NULL);
INSERT INTO `userLog` VALUES (838, 'hitwh001', 'logout', '2019-05-29 23:52:40', NULL);
INSERT INTO `userLog` VALUES (839, 'hitwh001', 'login', '2019-05-30 00:14:34', NULL);
INSERT INTO `userLog` VALUES (840, 'hitwh001', 'login', '2019-05-30 00:25:48', NULL);
INSERT INTO `userLog` VALUES (841, 'hitwh001', 'logout', '2019-05-30 00:27:43', NULL);
INSERT INTO `userLog` VALUES (842, 'hitwh001', 'login', '2019-05-30 03:29:22', NULL);
INSERT INTO `userLog` VALUES (843, 'hitwh001', 'logout', '2019-05-30 03:33:41', NULL);
INSERT INTO `userLog` VALUES (844, 'hitwh001', 'login', '2019-05-30 04:30:34', NULL);
INSERT INTO `userLog` VALUES (845, 'hitwh001', 'logout', '2019-05-30 04:32:09', NULL);
INSERT INTO `userLog` VALUES (846, 'hitwh001', 'login', '2019-05-30 04:32:16', NULL);
INSERT INTO `userLog` VALUES (847, 'hitwh001', 'logout', '2019-05-30 04:32:24', NULL);
INSERT INTO `userLog` VALUES (848, 'hitwh001', 'login', '2019-05-30 13:17:39', NULL);
INSERT INTO `userLog` VALUES (849, 'hitwh001', 'login', '2019-05-30 14:58:12', NULL);
INSERT INTO `userLog` VALUES (850, 'hitwh001', 'logout', '2019-05-30 15:06:43', NULL);
INSERT INTO `userLog` VALUES (851, 'hitwh001', 'login', '2019-05-30 15:50:11', NULL);
INSERT INTO `userLog` VALUES (852, 'hitwh001', 'login', '2019-05-30 16:05:30', NULL);
INSERT INTO `userLog` VALUES (853, 'hitwh001', 'login', '2019-05-30 16:13:59', NULL);
INSERT INTO `userLog` VALUES (854, 'hitwh001', 'logout', '2019-05-30 16:20:20', NULL);
INSERT INTO `userLog` VALUES (855, 'hitwh001', 'login', '2019-05-30 16:34:46', NULL);
INSERT INTO `userLog` VALUES (856, 'hitwh001', 'login', '2019-05-30 18:39:14', NULL);
INSERT INTO `userLog` VALUES (857, 'hitwh001', 'logout', '2019-05-30 18:53:17', NULL);
INSERT INTO `userLog` VALUES (858, 'hitwh001', 'login', '2019-05-30 20:04:27', NULL);
INSERT INTO `userLog` VALUES (859, 'hitwh001', 'logout', '2019-05-30 20:06:38', NULL);
INSERT INTO `userLog` VALUES (860, 'hitwh001', 'login', '2019-05-30 20:31:21', NULL);
INSERT INTO `userLog` VALUES (861, 'hitwh001', 'logout', '2019-05-30 20:33:20', NULL);
INSERT INTO `userLog` VALUES (862, 'hitwh001', 'login', '2019-05-30 20:53:29', NULL);
INSERT INTO `userLog` VALUES (863, 'hitwh001', 'logout', '2019-05-30 21:00:22', NULL);
INSERT INTO `userLog` VALUES (864, 'hitwh001', 'login', '2019-05-30 21:14:35', NULL);
INSERT INTO `userLog` VALUES (865, 'hitwh001', 'login', '2019-05-30 21:16:00', NULL);
INSERT INTO `userLog` VALUES (866, 'hitwh001', 'logout', '2019-05-30 21:21:45', NULL);
INSERT INTO `userLog` VALUES (867, 'hitwh001', 'login', '2019-05-30 21:34:53', NULL);
INSERT INTO `userLog` VALUES (868, 'hitwh001', 'logout', '2019-05-30 21:35:48', NULL);
INSERT INTO `userLog` VALUES (869, 'hitwh001', 'login', '2019-05-30 21:50:13', NULL);
INSERT INTO `userLog` VALUES (870, 'hitwh001', 'logout', '2019-05-30 21:51:01', NULL);
INSERT INTO `userLog` VALUES (871, 'hitwh001', 'login', '2019-05-31 02:40:45', NULL);
INSERT INTO `userLog` VALUES (872, 'hitwh001', 'logout', '2019-05-31 02:41:28', NULL);
INSERT INTO `userLog` VALUES (873, 'hitwh001', 'login', '2019-05-31 06:38:36', NULL);
INSERT INTO `userLog` VALUES (874, 'hitwh001', 'login', '2019-05-31 07:03:48', NULL);
INSERT INTO `userLog` VALUES (875, 'hitwh001', 'login', '2019-05-31 07:33:18', NULL);
INSERT INTO `userLog` VALUES (876, 'hitwh001', 'login', '2019-05-31 07:37:49', NULL);
INSERT INTO `userLog` VALUES (877, 'hitwh001', 'login', '2019-05-31 07:45:20', NULL);
INSERT INTO `userLog` VALUES (878, 'hitwh001', 'login', '2019-05-31 08:09:41', NULL);
INSERT INTO `userLog` VALUES (879, 'hitwh001', 'login', '2019-05-31 08:11:48', NULL);
INSERT INTO `userLog` VALUES (880, 'hitwh001', 'login', '2019-05-31 08:36:17', NULL);
INSERT INTO `userLog` VALUES (881, 'hitwh001', 'logout', '2019-05-31 08:38:04', NULL);
INSERT INTO `userLog` VALUES (882, 'hitwh001', 'login', '2019-06-02 11:31:06', NULL);
INSERT INTO `userLog` VALUES (883, 'hitwh001', 'logout', '2019-06-02 11:34:10', NULL);
INSERT INTO `userLog` VALUES (884, 'hitwh001', 'login', '2019-06-02 13:52:50', NULL);
INSERT INTO `userLog` VALUES (885, 'hitwh001', 'logout', '2019-06-02 14:00:03', NULL);
INSERT INTO `userLog` VALUES (886, 'hitwh001', 'login', '2019-06-02 14:01:55', NULL);
INSERT INTO `userLog` VALUES (887, 'hitwh001', 'login', '2019-06-02 14:13:07', NULL);
INSERT INTO `userLog` VALUES (888, 'hitwh001', 'logout', '2019-06-02 14:13:23', NULL);
INSERT INTO `userLog` VALUES (889, 'hitwh001', 'login', '2019-06-02 14:13:31', NULL);
INSERT INTO `userLog` VALUES (890, 'hitwh001', 'login', '2019-06-02 14:19:11', NULL);
INSERT INTO `userLog` VALUES (891, 'hitwh001', 'logout', '2019-06-02 14:21:02', NULL);
INSERT INTO `userLog` VALUES (892, 'hitwh001', 'login', '2019-06-02 14:32:47', NULL);
INSERT INTO `userLog` VALUES (893, 'hitwh001', 'login', '2019-06-02 14:35:05', NULL);
INSERT INTO `userLog` VALUES (894, 'hitwh001', 'logout', '2019-06-02 14:37:02', NULL);
INSERT INTO `userLog` VALUES (895, 'hitwh001', 'login', '2019-06-02 14:42:48', NULL);
INSERT INTO `userLog` VALUES (896, 'hitwh001', 'logout', '2019-06-02 14:48:13', NULL);
INSERT INTO `userLog` VALUES (897, 'hitwh001', 'login', '2019-06-02 15:04:47', NULL);
INSERT INTO `userLog` VALUES (898, 'hitwh001', 'login', '2019-06-03 11:09:22', NULL);
INSERT INTO `userLog` VALUES (899, 'hitwh001', 'login', '2019-06-03 14:30:19', NULL);
INSERT INTO `userLog` VALUES (900, 'hitwh001', 'login', '2019-06-03 14:47:22', NULL);
INSERT INTO `userLog` VALUES (901, 'hitwh001', 'login', '2019-06-03 15:21:45', NULL);
INSERT INTO `userLog` VALUES (902, 'hitwh001', 'logout', '2019-06-03 15:33:56', NULL);
INSERT INTO `userLog` VALUES (903, 'hitwh001', 'login', '2019-06-03 15:54:34', NULL);
INSERT INTO `userLog` VALUES (904, 'hitwh001', 'login', '2019-06-05 14:52:55', NULL);
INSERT INTO `userLog` VALUES (905, 'hitwh001', 'logout', '2019-06-05 14:56:08', NULL);
INSERT INTO `userLog` VALUES (906, 'hitwh001', 'login', '2019-06-06 06:30:50', NULL);
INSERT INTO `userLog` VALUES (907, 'hitwh001', 'logout', '2019-06-06 07:01:48', NULL);
INSERT INTO `userLog` VALUES (908, 'hitwh001', 'login', '2019-06-08 15:03:24', NULL);
INSERT INTO `userLog` VALUES (909, 'hitwh001', 'login', '2019-06-10 13:01:00', NULL);
INSERT INTO `userLog` VALUES (910, 'hitwh001', 'login', '2019-06-10 16:47:42', NULL);
INSERT INTO `userLog` VALUES (911, 'hitwh001', 'login', '2019-06-10 18:01:02', NULL);
INSERT INTO `userLog` VALUES (912, 'hitwh001', 'logout', '2019-06-10 18:03:21', NULL);
INSERT INTO `userLog` VALUES (913, 'hitwh001', 'login', '2019-06-10 19:05:21', NULL);
INSERT INTO `userLog` VALUES (914, 'hitwh001', 'login', '2019-06-10 20:03:03', NULL);
INSERT INTO `userLog` VALUES (915, 'hitwh001', 'login', '2019-06-10 20:41:16', NULL);
INSERT INTO `userLog` VALUES (916, 'hitwh001', 'login', '2019-06-10 21:25:23', NULL);
INSERT INTO `userLog` VALUES (917, 'hitwh001', 'login', '2019-06-11 16:50:08', NULL);
INSERT INTO `userLog` VALUES (918, 'hitwh001', 'logout', '2019-06-11 16:55:44', NULL);
INSERT INTO `userLog` VALUES (919, 'hitwh001', 'login', '2019-06-11 20:31:06', NULL);
INSERT INTO `userLog` VALUES (920, 'hitwh001', 'logout', '2019-06-11 20:42:13', NULL);
INSERT INTO `userLog` VALUES (921, 'hitwh001', 'login', '2019-06-11 21:05:49', NULL);
INSERT INTO `userLog` VALUES (922, 'hitwh001', 'logout', '2019-06-11 21:27:10', NULL);
INSERT INTO `userLog` VALUES (923, 'hitwh001', 'login', '2019-06-11 21:31:05', NULL);
INSERT INTO `userLog` VALUES (924, 'hitwh001', 'logout', '2019-06-11 21:38:33', NULL);
INSERT INTO `userLog` VALUES (925, 'hitwh001', 'login', '2019-06-11 21:39:14', NULL);
INSERT INTO `userLog` VALUES (926, 'hitwh001', 'logout', '2019-06-11 21:42:13', NULL);
INSERT INTO `userLog` VALUES (927, 'hitwh001', 'login', '2019-06-11 22:51:26', NULL);
INSERT INTO `userLog` VALUES (928, 'hitwh001', 'login', '2019-06-11 23:29:46', NULL);
INSERT INTO `userLog` VALUES (929, 'hitwh001', 'login', '2019-06-12 02:35:36', NULL);
INSERT INTO `userLog` VALUES (930, 'hitwh001', 'logout', '2019-06-12 02:38:39', NULL);
INSERT INTO `userLog` VALUES (931, 'hitwh001', 'login', '2019-06-12 07:35:13', NULL);
INSERT INTO `userLog` VALUES (932, 'hitwh001', 'logout', '2019-06-12 07:36:08', NULL);
INSERT INTO `userLog` VALUES (933, 'hitwh001', 'login', '2019-06-21 15:22:23', NULL);
INSERT INTO `userLog` VALUES (934, 'hitwh001', 'login', '2019-06-21 15:32:37', NULL);
INSERT INTO `userLog` VALUES (935, 'hitwh001', 'logout', '2019-06-21 15:42:01', NULL);
INSERT INTO `userLog` VALUES (936, 'hitwh001', 'login', '2019-06-21 15:43:57', NULL);
INSERT INTO `userLog` VALUES (937, 'hitwh001', 'logout', '2019-06-21 15:44:39', NULL);
INSERT INTO `userLog` VALUES (938, 'hitwh001', 'login', '2019-06-21 20:31:00', NULL);
INSERT INTO `userLog` VALUES (939, 'hitwh001', 'login', '2019-07-04 19:05:54', NULL);
INSERT INTO `userLog` VALUES (940, 'hitwh001', 'login', '2019-07-06 10:58:13', NULL);
INSERT INTO `userLog` VALUES (941, 'hitwh001', 'login', '2019-07-06 11:15:17', NULL);
INSERT INTO `userLog` VALUES (942, 'hitwh001', 'login', '2019-07-06 12:01:03', NULL);
INSERT INTO `userLog` VALUES (943, 'hitwh001', 'logout', '2019-07-06 12:08:49', NULL);
INSERT INTO `userLog` VALUES (944, 'hitwh001', 'login', '2019-07-06 12:26:24', NULL);
INSERT INTO `userLog` VALUES (945, 'hitwh001', 'logout', '2019-07-06 12:31:03', NULL);
INSERT INTO `userLog` VALUES (946, 'hitwh001', 'login', '2019-07-06 12:50:20', NULL);
INSERT INTO `userLog` VALUES (947, 'hitwh001', 'login', '2019-07-06 12:58:33', NULL);
INSERT INTO `userLog` VALUES (948, 'hitwh001', 'login', '2019-07-09 10:00:39', NULL);
INSERT INTO `userLog` VALUES (949, 'hitwh001', 'login', '2019-07-09 10:33:41', NULL);
INSERT INTO `userLog` VALUES (950, 'hitwh001', 'login', '2019-07-22 10:11:43', NULL);
INSERT INTO `userLog` VALUES (951, 'hitwh001', 'login', '2019-07-22 10:34:32', NULL);
INSERT INTO `userLog` VALUES (952, 'hitwh001', 'login', '2019-07-22 10:55:04', NULL);
INSERT INTO `userLog` VALUES (953, 'hitwh001', 'login', '2019-07-22 11:00:12', NULL);
INSERT INTO `userLog` VALUES (954, 'hitwh001', 'login', '2019-07-22 11:02:30', NULL);
INSERT INTO `userLog` VALUES (955, 'hitwh001', 'login', '2019-07-22 11:27:40', NULL);
INSERT INTO `userLog` VALUES (956, 'hitwh001', 'login', '2019-07-22 11:31:00', NULL);
INSERT INTO `userLog` VALUES (957, 'hitwh001', 'login', '2019-07-22 11:43:51', NULL);
INSERT INTO `userLog` VALUES (958, 'hitwh001', 'login', '2019-07-23 11:04:11', NULL);
INSERT INTO `userLog` VALUES (959, 'hitwh001', 'logout', '2019-07-23 11:05:11', NULL);
INSERT INTO `userLog` VALUES (960, 'hitwh001', 'login', '2019-07-23 11:05:37', NULL);
INSERT INTO `userLog` VALUES (961, 'hitwh001', 'login', '2019-07-23 11:12:35', NULL);
INSERT INTO `userLog` VALUES (962, 'hitwh001', 'login', '2019-07-24 15:24:19', NULL);
INSERT INTO `userLog` VALUES (963, 'hitwh001', 'logout', '2019-07-24 15:47:49', NULL);
INSERT INTO `userLog` VALUES (964, 'hitwh001', 'login', '2019-07-24 15:47:52', NULL);
INSERT INTO `userLog` VALUES (965, 'hitwh001', 'logout', '2019-07-24 15:47:59', NULL);
INSERT INTO `userLog` VALUES (966, 'hitwh001', 'login', '2019-07-24 15:48:35', NULL);
INSERT INTO `userLog` VALUES (967, 'hitwh001', 'login', '2019-07-28 15:20:14', NULL);
INSERT INTO `userLog` VALUES (968, 'hitwh001', 'logout', '2019-07-28 15:24:22', NULL);
INSERT INTO `userLog` VALUES (969, 'hitwh001', 'login', '2019-07-28 15:24:43', NULL);
INSERT INTO `userLog` VALUES (970, 'hitwh001', 'logout', '2019-07-28 15:25:07', NULL);
INSERT INTO `userLog` VALUES (971, 'hitwh001', 'login', '2019-07-29 10:40:52', NULL);
INSERT INTO `userLog` VALUES (972, 'hitwh001', 'login', '2019-07-29 10:45:15', NULL);
INSERT INTO `userLog` VALUES (973, 'hitwh001', 'login', '2019-07-29 10:46:33', NULL);
INSERT INTO `userLog` VALUES (974, 'hitwh001', 'login', '2019-07-29 13:12:33', NULL);
INSERT INTO `userLog` VALUES (975, 'hitwh001', 'login', '2019-07-30 13:30:59', NULL);
INSERT INTO `userLog` VALUES (976, 'hitwh001', 'login', '2019-08-01 15:19:50', NULL);
INSERT INTO `userLog` VALUES (977, 'hitwh001', 'login', '2019-08-10 16:24:34', NULL);
INSERT INTO `userLog` VALUES (978, 'hitwh001', 'login', '2019-08-15 15:30:57', NULL);
INSERT INTO `userLog` VALUES (979, 'hitwh001', 'login', '2019-08-16 12:02:53', NULL);
INSERT INTO `userLog` VALUES (980, 'hitwh001', 'login', '2019-08-16 13:22:48', NULL);
INSERT INTO `userLog` VALUES (981, 'hitwh001', 'login', '2019-08-16 13:24:23', NULL);
INSERT INTO `userLog` VALUES (982, 'hitwh001', 'login', '2019-08-16 15:04:45', NULL);
INSERT INTO `userLog` VALUES (983, 'hitwh001', 'login', '2019-08-20 19:28:37', NULL);
INSERT INTO `userLog` VALUES (984, 'hitwh001', 'login', '2019-08-22 14:07:34', NULL);
INSERT INTO `userLog` VALUES (985, 'hitwh001', 'logout', '2019-08-22 14:08:21', NULL);
INSERT INTO `userLog` VALUES (986, 'hitwh001', 'login', '2019-08-22 14:08:53', NULL);
INSERT INTO `userLog` VALUES (987, 'hitwh001', 'logout', '2019-08-22 14:28:09', NULL);
INSERT INTO `userLog` VALUES (988, 'hitwh001', 'login', '2019-08-26 16:58:52', NULL);
INSERT INTO `userLog` VALUES (989, 'hitwh001', 'login', '2019-09-11 17:04:55', NULL);
INSERT INTO `userLog` VALUES (990, 'hitwh001', 'login', '2019-09-11 17:06:40', NULL);
INSERT INTO `userLog` VALUES (991, 'hitwh001', 'login', '2019-09-21 23:49:32', NULL);
INSERT INTO `userLog` VALUES (992, 'hitwh001', 'logout', '2019-09-21 23:49:42', NULL);
INSERT INTO `userLog` VALUES (993, 'hitwh001', 'login', '2019-09-21 23:55:52', NULL);
INSERT INTO `userLog` VALUES (994, 'hitwh001', 'logout', '2019-09-22 00:01:17', NULL);
INSERT INTO `userLog` VALUES (995, 'hitwh001', 'login', '2019-09-22 08:10:18', NULL);
INSERT INTO `userLog` VALUES (996, 'hitwh001', 'logout', '2019-09-22 08:10:57', NULL);
INSERT INTO `userLog` VALUES (997, 'hitwh001', 'login', '2019-09-22 08:57:35', NULL);
INSERT INTO `userLog` VALUES (998, 'hitwh001', 'logout', '2019-09-22 09:00:11', NULL);
INSERT INTO `userLog` VALUES (999, 'hitwh001', 'login', '2019-09-22 09:08:39', NULL);
INSERT INTO `userLog` VALUES (1000, 'hitwh001', 'login', '2019-09-22 09:08:40', NULL);
INSERT INTO `userLog` VALUES (1001, 'hitwh001', 'login', '2019-09-22 09:08:40', NULL);
INSERT INTO `userLog` VALUES (1002, 'hitwh001', 'login', '2019-09-22 09:08:40', NULL);
INSERT INTO `userLog` VALUES (1003, 'hitwh001', 'login', '2019-09-22 09:08:40', NULL);
INSERT INTO `userLog` VALUES (1004, 'hitwh001', 'login', '2019-09-22 09:08:42', NULL);
INSERT INTO `userLog` VALUES (1005, 'hitwh001', 'login', '2019-09-22 09:08:55', NULL);
INSERT INTO `userLog` VALUES (1006, 'hitwh001', 'logout', '2019-09-22 09:09:32', NULL);
INSERT INTO `userLog` VALUES (1007, 'hitwh001', 'login', '2019-09-22 09:40:16', NULL);
INSERT INTO `userLog` VALUES (1008, 'hitwh001', 'logout', '2019-09-22 09:40:33', NULL);
INSERT INTO `userLog` VALUES (1009, 'hitwh001', 'login', '2019-09-22 11:04:15', NULL);
INSERT INTO `userLog` VALUES (1010, 'hitwh001', 'logout', '2019-09-22 11:05:51', NULL);
INSERT INTO `userLog` VALUES (1011, 'hitwh001', 'login', '2019-09-22 14:40:30', NULL);
INSERT INTO `userLog` VALUES (1012, 'hitwh001', 'login', '2019-09-29 14:02:59', NULL);
INSERT INTO `userLog` VALUES (1013, 'hitwh001', 'login', '2019-09-29 14:09:23', NULL);
INSERT INTO `userLog` VALUES (1014, 'hitwh001', 'login', '2019-10-01 23:28:21', NULL);
INSERT INTO `userLog` VALUES (1015, 'hitwh001', 'login', '2019-10-01 23:34:43', NULL);
INSERT INTO `userLog` VALUES (1016, 'hitwh001', 'login', '2019-10-01 23:34:47', NULL);
INSERT INTO `userLog` VALUES (1017, 'hitwh001', 'login', '2019-10-01 23:34:51', NULL);
INSERT INTO `userLog` VALUES (1018, 'hitwh001', 'login', '2019-10-10 17:12:46', NULL);
INSERT INTO `userLog` VALUES (1019, 'hitwh001', 'logout', '2019-10-10 17:22:58', NULL);
INSERT INTO `userLog` VALUES (1020, 'hitwh001', 'login', '2019-10-12 10:13:44', NULL);
INSERT INTO `userLog` VALUES (1021, 'hitwh001', 'login', '2019-10-12 10:24:34', NULL);
INSERT INTO `userLog` VALUES (1022, 'hitwh001', 'logout', '2019-10-12 10:31:01', NULL);
INSERT INTO `userLog` VALUES (1023, 'hitwh001', 'login', '2019-10-12 10:35:03', NULL);
INSERT INTO `userLog` VALUES (1024, 'hitwh001', 'login', '2019-10-16 20:10:34', NULL);
INSERT INTO `userLog` VALUES (1025, 'hitwh001', 'login', '2019-10-16 20:37:02', NULL);
INSERT INTO `userLog` VALUES (1026, 'hitwh001', 'login', '2019-10-16 20:37:02', NULL);
INSERT INTO `userLog` VALUES (1027, 'hitwh001', 'login', '2019-10-18 14:39:14', NULL);
INSERT INTO `userLog` VALUES (1028, 'hitwh001', 'login', '2019-10-19 15:55:22', NULL);
INSERT INTO `userLog` VALUES (1029, 'hitwh001', 'login', '2019-10-19 16:28:47', NULL);
INSERT INTO `userLog` VALUES (1030, 'hitwh001', 'login', '2019-10-19 16:30:35', NULL);
INSERT INTO `userLog` VALUES (1031, 'hitwh001', 'login', '2019-10-19 16:33:53', NULL);
INSERT INTO `userLog` VALUES (1032, 'hitwh001', 'login', '2019-10-19 16:34:53', NULL);
INSERT INTO `userLog` VALUES (1033, 'hitwh001', 'login', '2019-10-19 16:35:46', NULL);
INSERT INTO `userLog` VALUES (1034, 'hitwh001', 'login', '2019-10-19 16:35:59', NULL);
INSERT INTO `userLog` VALUES (1035, 'hitwh001', 'login', '2019-10-19 16:36:50', NULL);
INSERT INTO `userLog` VALUES (1036, 'hitwh001', 'login', '2019-10-19 16:46:42', NULL);
INSERT INTO `userLog` VALUES (1037, 'hitwh001', 'login', '2019-10-19 16:47:37', NULL);
INSERT INTO `userLog` VALUES (1038, 'hitwh001', 'login', '2019-10-19 16:55:04', NULL);
INSERT INTO `userLog` VALUES (1039, 'hitwh001', 'login', '2019-10-19 16:56:13', NULL);
INSERT INTO `userLog` VALUES (1040, 'hitwh001', 'login', '2019-10-20 14:51:49', NULL);
INSERT INTO `userLog` VALUES (1041, 'hitwh001', 'login', '2019-10-25 00:51:34', NULL);
INSERT INTO `userLog` VALUES (1042, 'hitwh001', 'login', '2019-10-25 00:55:26', NULL);
INSERT INTO `userLog` VALUES (1043, 'hitwh001', 'login', '2019-10-26 12:04:45', NULL);
INSERT INTO `userLog` VALUES (1044, 'hitwh001', 'login', '2019-10-26 12:08:45', NULL);
INSERT INTO `userLog` VALUES (1045, 'hitwh001', 'login', '2019-10-26 12:15:33', NULL);
INSERT INTO `userLog` VALUES (1046, 'hitwh001', 'login', '2019-10-26 12:31:33', NULL);
INSERT INTO `userLog` VALUES (1047, 'hitwh001', 'login', '2019-10-26 12:34:14', NULL);
INSERT INTO `userLog` VALUES (1048, 'hitwh001', 'login', '2019-10-26 12:38:06', NULL);
INSERT INTO `userLog` VALUES (1049, 'hitwh001', 'login', '2019-10-26 14:06:37', NULL);
INSERT INTO `userLog` VALUES (1050, 'hitwh001', 'login', '2019-10-26 14:29:02', NULL);
INSERT INTO `userLog` VALUES (1051, 'hitwh001', 'login', '2019-10-26 14:36:07', NULL);
INSERT INTO `userLog` VALUES (1052, 'hitwh001', 'login', '2019-10-26 14:51:29', NULL);
INSERT INTO `userLog` VALUES (1053, 'hitwh001', 'login', '2019-10-26 14:54:37', NULL);
INSERT INTO `userLog` VALUES (1054, 'hitwh001', 'login', '2019-10-26 14:57:08', NULL);
INSERT INTO `userLog` VALUES (1055, 'hitwh001', 'login', '2019-10-26 15:23:36', NULL);
INSERT INTO `userLog` VALUES (1056, 'hitwh001', 'login', '2019-10-26 15:41:29', NULL);
INSERT INTO `userLog` VALUES (1057, 'hitwh001', 'login', '2019-10-26 15:46:22', NULL);
INSERT INTO `userLog` VALUES (1058, 'hitwh001', 'login', '2019-10-26 15:57:43', NULL);
INSERT INTO `userLog` VALUES (1059, 'hitwh001', 'login', '2019-10-26 16:03:42', NULL);
INSERT INTO `userLog` VALUES (1060, 'hitwh001', 'login', '2019-10-26 16:41:45', NULL);
INSERT INTO `userLog` VALUES (1061, 'hitwh001', 'login', '2019-10-26 16:47:21', NULL);
INSERT INTO `userLog` VALUES (1062, 'hitwh001', 'login', '2019-10-26 17:01:17', NULL);
INSERT INTO `userLog` VALUES (1063, 'hitwh001', 'login', '2019-10-26 17:08:43', NULL);
INSERT INTO `userLog` VALUES (1064, 'hitwh001', 'login', '2019-10-26 17:30:33', NULL);
INSERT INTO `userLog` VALUES (1065, 'hitwh001', 'login', '2019-10-26 17:31:37', NULL);
INSERT INTO `userLog` VALUES (1066, 'hitwh001', 'login', '2019-10-26 17:31:44', NULL);
INSERT INTO `userLog` VALUES (1067, 'hitwh001', 'login', '2019-10-26 17:40:57', NULL);
INSERT INTO `userLog` VALUES (1068, 'hitwh001', 'login', '2019-10-26 17:50:06', NULL);
INSERT INTO `userLog` VALUES (1069, 'hitwh001', 'login', '2019-10-26 18:01:29', NULL);
INSERT INTO `userLog` VALUES (1070, 'hitwh001', 'login', '2019-10-26 21:56:15', NULL);
INSERT INTO `userLog` VALUES (1071, 'hitwh001', 'login', '2019-10-31 23:33:15', NULL);
INSERT INTO `userLog` VALUES (1072, 'hitwh001', 'login', '2019-10-31 23:37:07', NULL);
INSERT INTO `userLog` VALUES (1073, 'hitwh001', 'login', '2019-10-31 23:47:13', NULL);
INSERT INTO `userLog` VALUES (1074, 'hitwh001', 'login', '2019-10-31 23:48:34', NULL);
INSERT INTO `userLog` VALUES (1075, 'hitwh001', 'login', '2019-10-31 23:51:50', NULL);
INSERT INTO `userLog` VALUES (1076, 'hitwh001', 'login', '2019-10-31 23:56:34', NULL);
INSERT INTO `userLog` VALUES (1077, 'hitwh001', 'login', '2019-10-31 23:59:31', NULL);
INSERT INTO `userLog` VALUES (1078, 'hitwh001', 'login', '2019-11-01 00:07:58', NULL);
INSERT INTO `userLog` VALUES (1079, 'hitwh001', 'login', '2019-11-01 00:09:17', NULL);
INSERT INTO `userLog` VALUES (1080, 'hitwh001', 'login', '2019-11-01 00:10:06', NULL);
INSERT INTO `userLog` VALUES (1081, 'hitwh001', 'login', '2019-11-01 00:14:12', NULL);
INSERT INTO `userLog` VALUES (1082, 'hitwh001', 'login', '2019-11-04 18:01:08', NULL);
INSERT INTO `userLog` VALUES (1083, 'hitwh001', 'login', '2019-11-04 18:14:49', NULL);
INSERT INTO `userLog` VALUES (1084, 'hitwh001', 'login', '2019-11-05 18:29:45', NULL);
INSERT INTO `userLog` VALUES (1085, 'hitwh001', 'login', '2019-11-05 18:42:43', NULL);
INSERT INTO `userLog` VALUES (1086, 'hitwh001', 'login', '2019-11-13 16:27:00', NULL);
INSERT INTO `userLog` VALUES (1087, 'hitwh001', 'login', '2019-11-13 16:28:25', NULL);
INSERT INTO `userLog` VALUES (1088, 'hitwh001', 'login', '2019-11-16 14:30:46', NULL);
INSERT INTO `userLog` VALUES (1089, 'hitwh001', 'login', '2019-11-16 20:54:36', NULL);
INSERT INTO `userLog` VALUES (1090, 'hitwh001', 'login', '2019-11-16 21:00:23', NULL);
INSERT INTO `userLog` VALUES (1091, 'hitwh001', 'login', '2019-11-16 21:02:56', NULL);
INSERT INTO `userLog` VALUES (1092, 'hitwh001', 'login', '2019-11-16 21:03:58', NULL);
INSERT INTO `userLog` VALUES (1093, 'hitwh001', 'login', '2019-11-16 21:05:31', NULL);
INSERT INTO `userLog` VALUES (1094, 'hitwh001', 'login', '2019-11-16 21:16:10', NULL);
INSERT INTO `userLog` VALUES (1095, 'hitwh001', 'login', '2019-11-16 21:17:45', NULL);
INSERT INTO `userLog` VALUES (1096, 'hitwh001', 'login', '2019-11-16 21:37:20', NULL);
INSERT INTO `userLog` VALUES (1097, 'hitwh001', 'login', '2019-11-16 21:46:53', NULL);
INSERT INTO `userLog` VALUES (1098, 'hitwh001', 'login', '2019-11-16 23:16:56', NULL);
INSERT INTO `userLog` VALUES (1099, 'hitwh001', 'login', '2019-11-16 23:21:26', NULL);
INSERT INTO `userLog` VALUES (1100, 'hitwh001', 'login', '2019-11-16 23:30:17', NULL);
INSERT INTO `userLog` VALUES (1101, 'hitwh001', 'login', '2019-11-16 23:32:54', NULL);
INSERT INTO `userLog` VALUES (1102, 'hitwh001', 'login', '2019-11-16 23:34:54', NULL);
INSERT INTO `userLog` VALUES (1103, 'hitwh001', 'login', '2019-11-16 23:40:31', NULL);
INSERT INTO `userLog` VALUES (1104, 'hitwh001', 'login', '2019-11-16 23:50:56', NULL);
INSERT INTO `userLog` VALUES (1105, 'hitwh001', 'login', '2019-11-16 23:54:57', NULL);
INSERT INTO `userLog` VALUES (1106, 'hitwh001', 'login', '2019-11-16 23:56:46', NULL);
INSERT INTO `userLog` VALUES (1107, 'hitwh001', 'login', '2019-11-17 00:00:10', NULL);
INSERT INTO `userLog` VALUES (1108, 'hitwh001', 'login', '2019-11-17 00:03:37', NULL);
INSERT INTO `userLog` VALUES (1109, 'hitwh001', 'login', '2019-11-17 00:18:45', NULL);
INSERT INTO `userLog` VALUES (1110, 'hitwh001', 'login', '2019-11-17 00:20:11', NULL);
INSERT INTO `userLog` VALUES (1111, 'hitwh001', 'login', '2019-11-17 00:25:16', NULL);
INSERT INTO `userLog` VALUES (1112, 'hitwh001', 'login', '2019-11-17 00:30:39', NULL);
INSERT INTO `userLog` VALUES (1113, 'hitwh001', 'login', '2019-11-17 00:33:04', NULL);
INSERT INTO `userLog` VALUES (1114, 'hitwh001', 'login', '2019-11-17 00:37:20', NULL);
INSERT INTO `userLog` VALUES (1115, 'hitwh001', 'login', '2019-11-17 00:41:33', NULL);
INSERT INTO `userLog` VALUES (1116, 'hitwh001', 'login', '2019-11-17 00:46:57', NULL);
INSERT INTO `userLog` VALUES (1117, 'hitwh001', 'login', '2019-11-17 00:47:20', NULL);
INSERT INTO `userLog` VALUES (1118, 'hitwh001', 'login', '2019-11-17 00:51:32', NULL);
INSERT INTO `userLog` VALUES (1119, 'hitwh001', 'login', '2019-11-17 00:56:29', NULL);
INSERT INTO `userLog` VALUES (1120, 'hitwh001', 'login', '2019-11-17 01:00:37', NULL);
INSERT INTO `userLog` VALUES (1121, 'hitwh001', 'login', '2019-11-17 01:46:47', NULL);
INSERT INTO `userLog` VALUES (1122, 'hitwh001', 'login', '2019-11-17 01:50:45', NULL);
INSERT INTO `userLog` VALUES (1123, 'hitwh001', 'login', '2019-11-17 01:57:52', NULL);
INSERT INTO `userLog` VALUES (1124, 'hitwh001', 'login', '2019-11-17 02:06:49', NULL);
INSERT INTO `userLog` VALUES (1125, 'hitwh001', 'login', '2019-11-17 02:10:13', NULL);
INSERT INTO `userLog` VALUES (1126, 'hitwh001', 'login', '2019-11-17 02:11:52', NULL);
INSERT INTO `userLog` VALUES (1127, 'hitwh001', 'login', '2019-11-17 02:19:06', NULL);
INSERT INTO `userLog` VALUES (1128, 'hitwh001', 'login', '2019-11-17 22:11:53', NULL);
INSERT INTO `userLog` VALUES (1129, 'hitwh001', 'login', '2019-11-17 22:16:58', NULL);
INSERT INTO `userLog` VALUES (1130, 'hitwh001', 'login', '2019-11-17 22:51:53', NULL);
INSERT INTO `userLog` VALUES (1131, 'hitwh001', 'login', '2019-11-17 23:11:09', NULL);
INSERT INTO `userLog` VALUES (1132, 'hitwh001', 'login', '2019-11-18 11:57:39', NULL);
INSERT INTO `userLog` VALUES (1133, 'hitwh001', 'login', '2019-11-18 23:00:57', NULL);
INSERT INTO `userLog` VALUES (1134, 'hitwh001', 'login', '2019-11-19 00:09:06', NULL);
INSERT INTO `userLog` VALUES (1135, 'hitwh001', 'login', '2019-11-19 00:10:02', NULL);
INSERT INTO `userLog` VALUES (1136, 'hitwh001', 'login', '2019-11-19 15:56:46', NULL);
INSERT INTO `userLog` VALUES (1137, 'hitwh001', 'login', '2019-11-19 16:03:59', NULL);
INSERT INTO `userLog` VALUES (1138, 'hitwh001', 'login', '2019-11-19 16:37:44', NULL);
INSERT INTO `userLog` VALUES (1139, 'hitwh001', 'login', '2019-11-19 16:55:25', NULL);
INSERT INTO `userLog` VALUES (1140, 'hitwh001', 'login', '2019-11-19 17:11:42', NULL);
INSERT INTO `userLog` VALUES (1141, 'hitwh001', 'login', '2019-11-19 17:25:17', NULL);
INSERT INTO `userLog` VALUES (1142, 'hitwh001', 'login', '2019-11-19 17:29:33', NULL);
INSERT INTO `userLog` VALUES (1143, 'hitwh001', 'login', '2019-11-19 17:38:51', NULL);
INSERT INTO `userLog` VALUES (1144, 'hitwh001', 'login', '2019-11-19 17:39:56', NULL);
INSERT INTO `userLog` VALUES (1145, 'hitwh001', 'login', '2019-11-19 17:46:14', NULL);
INSERT INTO `userLog` VALUES (1146, 'hitwh001', 'login', '2019-11-19 17:49:16', NULL);
INSERT INTO `userLog` VALUES (1147, 'hitwh001', 'login', '2019-11-19 17:53:24', NULL);
INSERT INTO `userLog` VALUES (1148, 'hitwh001', 'login', '2019-11-19 17:55:24', NULL);
INSERT INTO `userLog` VALUES (1149, 'hitwh001', 'login', '2019-11-19 17:59:24', NULL);
INSERT INTO `userLog` VALUES (1150, 'hitwh001', 'login', '2019-11-19 17:59:25', NULL);
INSERT INTO `userLog` VALUES (1151, 'hitwh001', 'login', '2019-11-19 18:00:29', NULL);
INSERT INTO `userLog` VALUES (1152, 'hitwh001', 'login', '2019-11-19 19:01:09', NULL);
INSERT INTO `userLog` VALUES (1153, 'hitwh001', 'login', '2019-11-20 15:13:13', NULL);
INSERT INTO `userLog` VALUES (1154, 'hitwh001', 'login', '2019-11-20 15:18:04', NULL);
INSERT INTO `userLog` VALUES (1155, 'hitwh001', 'login', '2019-11-20 15:56:57', NULL);
INSERT INTO `userLog` VALUES (1156, 'hitwh001', 'login', '2019-11-20 16:40:46', NULL);
INSERT INTO `userLog` VALUES (1157, 'hitwh001', 'login', '2019-11-20 16:48:56', NULL);
INSERT INTO `userLog` VALUES (1158, 'hitwh001', 'login', '2019-11-20 16:50:12', NULL);
INSERT INTO `userLog` VALUES (1159, 'hitwh001', 'login', '2019-11-20 16:52:48', NULL);
INSERT INTO `userLog` VALUES (1160, 'hitwh001', 'login', '2019-11-20 16:59:56', NULL);
INSERT INTO `userLog` VALUES (1161, 'hitwh001', 'login', '2019-11-20 17:21:04', NULL);
INSERT INTO `userLog` VALUES (1162, 'hitwh001', 'login', '2019-11-20 18:22:11', NULL);
INSERT INTO `userLog` VALUES (1163, 'hitwh001', 'login', '2019-11-30 23:15:05', NULL);
INSERT INTO `userLog` VALUES (1164, 'hitwh001', 'login', '2019-12-04 16:52:41', NULL);
INSERT INTO `userLog` VALUES (1165, 'hitwh001', 'login', '2019-12-05 13:16:06', NULL);
INSERT INTO `userLog` VALUES (1166, 'hitwh001', 'login', '2019-12-05 19:31:43', NULL);

SET FOREIGN_KEY_CHECKS = 1;
