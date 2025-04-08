/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

CREATE DATABASE IF NOT EXISTS `webtracnghiemdb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `webtracnghiemdb`;

CREATE TABLE IF NOT EXISTS `blog` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `categories` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `courses` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` text,
  `image_url` varchar(255) DEFAULT NULL,
  `is_active` bit(1) NOT NULL,
  `price` double NOT NULL,
  `study_program` text,
  `title` varchar(255) NOT NULL,
  `category_id` bigint DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK72l5dj585nq7i6xxv1vj51lyn` (`category_id`),
  KEY `FK8touovikvcn48dgkvr6iprwck` (`created_by`),
  CONSTRAINT `FK72l5dj585nq7i6xxv1vj51lyn` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`),
  CONSTRAINT `FK8touovikvcn48dgkvr6iprwck` FOREIGN KEY (`created_by`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `enrollments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `enrollment_date` datetime(6) NOT NULL,
  `is_active` bit(1) NOT NULL,
  `course_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKho8mcicp4196ebpltdn9wl6co` (`course_id`),
  KEY `FKpiplninahhg6m5xjoxmb68hli` (`user_id`),
  CONSTRAINT `FKho8mcicp4196ebpltdn9wl6co` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`),
  CONSTRAINT `FKpiplninahhg6m5xjoxmb68hli` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `exam-test-result-detail` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_answer` varchar(255) DEFAULT NULL,
  `exam_test_result_id` int NOT NULL,
  `question_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK25vgwliumendrdosin5vc81c` (`exam_test_result_id`),
  KEY `FK5ow96fhpd0nb1qc4y944pc6br` (`question_id`),
  CONSTRAINT `FK25vgwliumendrdosin5vc81c` FOREIGN KEY (`exam_test_result_id`) REFERENCES `exam_test_result` (`result_id`) ON DELETE CASCADE,
  CONSTRAINT `FK5ow96fhpd0nb1qc4y944pc6br` FOREIGN KEY (`question_id`) REFERENCES `exam_question` (`question_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=801 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `exam_question` (
  `question_id` int NOT NULL AUTO_INCREMENT,
  `audio3url` varchar(255) DEFAULT NULL,
  `correct_answer` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `number` int DEFAULT NULL,
  `optiona` varchar(255) DEFAULT NULL,
  `optionb` varchar(255) DEFAULT NULL,
  `optionc` varchar(255) DEFAULT NULL,
  `optiond` varchar(255) DEFAULT NULL,
  `paragraph` text,
  `part` int DEFAULT NULL,
  `question` varchar(255) DEFAULT NULL,
  `script` varchar(10000) DEFAULT NULL,
  `exam_test_id` int NOT NULL,
  PRIMARY KEY (`question_id`),
  KEY `FK7124f3grqeuwsofn9kc2cw697` (`exam_test_id`),
  CONSTRAINT `FK7124f3grqeuwsofn9kc2cw697` FOREIGN KEY (`exam_test_id`) REFERENCES `exam_test` (`exam_test_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1401 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `exam_test` (
  `exam_test_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`exam_test_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `exam_test_result` (
  `result_id` int NOT NULL AUTO_INCREMENT,
  `correct_questions` int DEFAULT NULL,
  `do_exam_date` datetime(6) DEFAULT NULL,
  `incorrect_questions` int DEFAULT NULL,
  `time-taken` int DEFAULT NULL,
  `unchoosen_questions` int DEFAULT NULL,
  `exam_test_id` int NOT NULL,
  `id` bigint NOT NULL,
  PRIMARY KEY (`result_id`),
  KEY `FK7x7h92e4fwfui5pggxd1hl604` (`exam_test_id`),
  KEY `FKnt1l84pqx3wlax1puv2cqdjv5` (`id`),
  CONSTRAINT `FK7x7h92e4fwfui5pggxd1hl604` FOREIGN KEY (`exam_test_id`) REFERENCES `exam_test` (`exam_test_id`) ON DELETE CASCADE,
  CONSTRAINT `FKnt1l84pqx3wlax1puv2cqdjv5` FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `exercise` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `correct_answer` varchar(255) DEFAULT NULL,
  `is_correct` bit(1) NOT NULL,
  `optiona` varchar(255) DEFAULT NULL,
  `optionb` varchar(255) DEFAULT NULL,
  `optionc` varchar(255) DEFAULT NULL,
  `optiond` varchar(255) DEFAULT NULL,
  `question` text,
  `lesson_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKoohf1t296xtx5kt0waihyna1k` (`lesson_id`),
  CONSTRAINT `FKoohf1t296xtx5kt0waihyna1k` FOREIGN KEY (`lesson_id`) REFERENCES `lesson` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `flash_card` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `answer` varchar(255) DEFAULT NULL,
  `question` varchar(255) DEFAULT NULL,
  `list_flashcards_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtaayxnj2m8d7weguwlmpyvt5y` (`list_flashcards_id`),
  CONSTRAINT `FKtaayxnj2m8d7weguwlmpyvt5y` FOREIGN KEY (`list_flashcards_id`) REFERENCES `list_flash_cards` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `lesson` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` text,
  `description` text,
  `image_url` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKosjr0j8s26q7eyim4yeqh2w0e` (`category_id`),
  KEY `FKk0jsiwfotnul5xtjbd2og4j4h` (`created_by`),
  CONSTRAINT `FKk0jsiwfotnul5xtjbd2og4j4h` FOREIGN KEY (`created_by`) REFERENCES `user` (`id`),
  CONSTRAINT `FKosjr0j8s26q7eyim4yeqh2w0e` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `list_flash_cards` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKc0iolur4vs2uwcl1hddcxamov` (`user_id`),
  CONSTRAINT `FKc0iolur4vs2uwcl1hddcxamov` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `paid_lessons` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` text,
  `image_url` varchar(255) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `video_url` varchar(255) DEFAULT NULL,
  `course_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKii79a6y25r46lvacdivbwxkvl` (`course_id`),
  CONSTRAINT `FKii79a6y25r46lvacdivbwxkvl` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `questions` (
  `questionid` int NOT NULL AUTO_INCREMENT,
  `audiourl` varchar(255) DEFAULT NULL,
  `imageurl` varchar(255) DEFAULT NULL,
  `question_number` int NOT NULL,
  `question_text` text,
  `partid` int DEFAULT NULL,
  PRIMARY KEY (`questionid`),
  KEY `FKklebdrc0itdd2x4gfbim1hvhx` (`partid`),
  CONSTRAINT `FKklebdrc0itdd2x4gfbim1hvhx` FOREIGN KEY (`partid`) REFERENCES `parts` (`partid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(250) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(100) DEFAULT NULL,
  `date_of_birth` varchar(255) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `password` varchar(250) DEFAULT NULL,
  `phone` varchar(10) DEFAULT NULL,
  `provider` varchar(50) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`),
  UNIQUE KEY `UK_589idila9li6a4arw1t8ht1gx` (`phone`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `user_role` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
