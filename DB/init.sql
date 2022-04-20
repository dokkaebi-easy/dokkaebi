DROP DATABASE IF EXISTS `dockerby`;
CREATE DATABASE `dockerby`;

CREATE TABLE `dockerby`.`version` (
  `version_id` BIGINT NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(45) NOT NULL,
  `input_version` VARCHAR(45) NOT NULL,
  `docker_version` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`version_id`));

INSERT INTO `dockerby`.`version`(`type`,`input_version`,`docker_version`) VALUES ("java","11","openjdk:11-jdk");