DROP DATABASE IF EXISTS `dockerby`;
CREATE DATABASE `dockerby`;

CREATE TABLE `dockerby`.`language` (
  `language_id` BIGINT NOT NULL,
  `name` VARCHAR(60) NOT NULL,
  PRIMARY KEY (`language_id`));

CREATE TABLE `dockerby`.`framework_type` (
  `framework_type_id` BIGINT NOT NULL,
  `framework_name` VARCHAR(60) NOT NULL,
  `language_id` BIGINT NULL,
  PRIMARY KEY (`framework_type_id`),
  INDEX `fk-language-framework_type_idx` (`language_id` ASC),
  CONSTRAINT `fk-language-framework_type`
    FOREIGN KEY (`language_id`)
    REFERENCES `dockerby`.`language` (`language_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `dockerby`.`version` (
  `version_id` BIGINT NOT NULL AUTO_INCREMENT,
  `input_version` VARCHAR(45) NOT NULL,
  `docker_version` VARCHAR(45) NOT NULL,
  `language_id` BIGINT NULL,
  PRIMARY KEY (`version_id`),
  INDEX `fk-language_id-version_idx` (`language_id` ASC),
  CONSTRAINT `fk-language_id-version`
    FOREIGN KEY (`language_id`)
    REFERENCES `dockerby`.`language` (`language_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `dockerby`.`build_tool` (
  `build_tool_id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `framework_type_id` BIGINT NULL,
  PRIMARY KEY (`build_tool_id`),
  INDEX `fk-framework_type-build_tool_idx` (`framework_type_id` ASC),
  CONSTRAINT `fk-framework_type-build_tool`
    FOREIGN KEY (`framework_type_id`)
    REFERENCES `dockerby`.`framework_type` (`framework_type_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `dockerby`.`gitlab_access_token` (
  `gitlab_access_token_id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL,
  `access_token` VARCHAR(255) NULL,
  PRIMARY KEY (`gitlab_access_token_id`));

CREATE TABLE `dockerby`.`project` (
  `project_id` BIGINT NOT NULL AUTO_INCREMENT,
  `project_name` VARCHAR(255) NOT NULL,
  `state_type` VARCHAR(255) NULL,
  `regist_date` DATETIME NULL,
  `last_modified_date` DATETIME NULL,
  PRIMARY KEY (`project_id`));

CREATE TABLE `dockerby`.`gitlab_config` (
  `gitlab_config_id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `host_url` VARCHAR(255) NOT NULL,
  `secret_token` VARCHAR(255) NOT NULL,
  `repository_url` VARCHAR(255) NOT NULL,
  `branch_name` VARCHAR(255) NOT NULL,
  `git_project_id` BIGINT NULL,
  `gitlab_account_id` BIGINT NULL,
  `gitlab_access_token_id` BIGINT NULL,
  `project_id` BIGINT NULL,
  PRIMARY KEY (`gitlab_config_id`),
  INDEX `fk-gitlab_account-gitlab_config_idx` (`gitlab_account_id` ASC),
  INDEX `fk-gitlab_access_token-gitlab_config_idx` (`gitlab_access_token_id` ASC),
  INDEX `fk-project-gitlab_config_idx` (`project_id` ASC),
  CONSTRAINT `fk-gitlab_access_token-gitlab_config`
    FOREIGN KEY (`gitlab_access_token_id`)
    REFERENCES `dockerby`.`gitlab_access_token` (`gitlab_access_token_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk-gitlab_account-gitlab_config`
    FOREIGN KEY (`gitlab_account_id`)
    REFERENCES `dockerby`.`gitlab_account` (`gitlab_account_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk-project-gitlab_config`
    FOREIGN KEY (`project_id`)
    REFERENCES `dockerby`.`project` (`project_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `dockerby`.`user` (
  `user_id` BIGINT NOT NULL AUTO_INCREMENT,
  `principal` VARCHAR(60) NOT NULL,
  `credential` VARCHAR(60) NOT NULL,
  `name` VARCHAR(60) NOT NULL,
  `regist_date` DATETIME NULL,
  `last_modified_date` DATETIME NULL,
  PRIMARY KEY (`user_id`));

CREATE TABLE `dockerby`.`config_history` (
  `config_history_id` BIGINT NOT NULL AUTO_INCREMENT,
  `msg` VARCHAR(255) NULL,
  `regist_date` DATETIME NULL,
  `project_id` BIGINT NULL,
  `user_info_id` BIGINT NULL,
  PRIMARY KEY (`config_history_id`),
  INDEX `fk-project-config_history_idx` (`project_id` ASC),
  INDEX `fk-user-config_history_idx` (`user_info_id` ASC),
  CONSTRAINT `fk-project-config_history`
    FOREIGN KEY (`project_id`)
    REFERENCES `dockerby`.`project` (`project_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk-user-config_history`
    FOREIGN KEY (`user_info_id`)
    REFERENCES `dockerby`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `dockerby`.`build_history` (
  `build_history_id` BIGINT NOT NULL AUTO_INCREMENT,
  `state_type` VARCHAR(60) NULL,
  `regist_date` DATETIME NULL,
  `msg` VARCHAR(255) NULL,
  `project_id` BIGINT NULL,
  PRIMARY KEY (`build_history_id`),
  INDEX `fk-project-build_history_idx` (`project_id` ASC),
  CONSTRAINT `fk-project-build_history`
    FOREIGN KEY (`project_id`)
    REFERENCES `dockerby`.`project` (`project_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `dockerby`.`build_state` (
  `build_state_id` BIGINT NOT NULL AUTO_INCREMENT,
  `build_number` INT NULL,
  `build_type` VARCHAR(60) NULL,
  `state_type` VARCHAR(60) NULL,
  `regist_date` DATETIME NULL,
  `last_modified_date` DATETIME NULL,
  `project_id` BIGINT NULL,
  PRIMARY KEY (`build_state_id`),
  INDEX `fk-project-build_state_idx` (`project_id` ASC),
  CONSTRAINT `fk-project-build_state`
    FOREIGN KEY (`project_id`)
    REFERENCES `dockerby`.`project` (`project_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `dockerby`.`webhook_history` (
  `webhook_history_id` BIGINT NOT NULL AUTO_INCREMENT,
  `event_kind` VARCHAR(255) NULL,
  `username` VARCHAR(255) NULL,
  `git_http_url` VARCHAR(255) NULL,
  `default_branch` VARCHAR(255) NULL,
  `repository_name` VARCHAR(255) NULL,
  `build_state_id` BIGINT NULL,
  PRIMARY KEY (`webhook_history_id`),
  INDEX `fk-build_state-webhook_history_idx` (`build_state_id` ASC),
  CONSTRAINT `fk-build_state-webhook_history`
    FOREIGN KEY (`build_state_id`)
    REFERENCES `dockerby`.`build_state` (`build_state_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);



INSERT INTO `dockerby`.`language`(`language_id`,`name`) VALUES (1,"Java");
INSERT INTO `dockerby`.`language`(`language_id`,`name`) VALUES (2,"Python");
INSERT INTO `dockerby`.`language`(`language_id`,`name`) VALUES (3,"Node");
INSERT INTO `dockerby`.`language`(`language_id`,`name`) VALUES (4,"MySQL");

INSERT INTO `dockerby`.`framework_type`(`framework_type_id`,`framework_name`,`language_id`) VALUES (1,"SpringBoot",1);
INSERT INTO `dockerby`.`framework_type`(`framework_type_id`,`framework_name`,`language_id`) VALUES (2,"Vue",3);
INSERT INTO `dockerby`.`framework_type`(`framework_type_id`,`framework_name`,`language_id`) VALUES (3,"React",3);
INSERT INTO `dockerby`.`framework_type`(`framework_type_id`,`framework_name`,`language_id`) VALUES (4,"Next",3);
INSERT INTO `dockerby`.`framework_type`(`framework_type_id`,`framework_name`,`language_id`) VALUES (5,"Django",2);
INSERT INTO `dockerby`.`framework_type`(`framework_type_id`,`framework_name`,`language_id`) VALUES (6,"MySQL",4);

INSERT INTO `dockerby`.`build_tool`(`name`,`framework_type_id`) VALUES ("Gradle",1);
INSERT INTO `dockerby`.`build_tool`(`name`,`framework_type_id`) VALUES ("Maven",1);
INSERT INTO `dockerby`.`build_tool`(`name`,`framework_type_id`) VALUES ("Yes",2);
INSERT INTO `dockerby`.`build_tool`(`name`,`framework_type_id`) VALUES ("Yes",3);

INSERT INTO `dockerby`.`version`(`input_version`,`docker_version`,`language_id`) VALUES ("7","openjdk:7-jdk",1);
INSERT INTO `dockerby`.`version`(`input_version`,`docker_version`,`language_id`) VALUES ("8","openjdk:8-jdk",1);
INSERT INTO `dockerby`.`version`(`input_version`,`docker_version`,`language_id`) VALUES ("9","openjdk:9-jdk",1);
INSERT INTO `dockerby`.`version`(`input_version`,`docker_version`,`language_id`) VALUES ("10","openjdk:10-jdk",1);
INSERT INTO `dockerby`.`version`(`input_version`,`docker_version`,`language_id`) VALUES ("11","openjdk:11-jdk",1);
INSERT INTO `dockerby`.`version`(`input_version`,`docker_version`,`language_id`) VALUES ("12","openjdk:12-jdk",1);
INSERT INTO `dockerby`.`version`(`input_version`,`docker_version`,`language_id`) VALUES ("13","openjdk:13-jdk",1);
INSERT INTO `dockerby`.`version`(`input_version`,`docker_version`,`language_id`) VALUES ("14","openjdk:14-jdk",1);
INSERT INTO `dockerby`.`version`(`input_version`,`docker_version`,`language_id`) VALUES ("15","openjdk:15-jdk",1);
INSERT INTO `dockerby`.`version`(`input_version`,`docker_version`,`language_id`) VALUES ("16","openjdk:16-jdk",1);
INSERT INTO `dockerby`.`version`(`input_version`,`docker_version`,`language_id`) VALUES ("17","openjdk:17-jdk",1);
INSERT INTO `dockerby`.`version`(`input_version`,`docker_version`,`language_id`) VALUES ("18","openjdk:18-jdk",1);

INSERT INTO `dockerby`.`version`(`input_version`,`docker_version`,`language_id`) VALUES ("3.5","python:3.5",2);
INSERT INTO `dockerby`.`version`(`input_version`,`docker_version`,`language_id`) VALUES ("3.6","python:3.6",2);
INSERT INTO `dockerby`.`version`(`input_version`,`docker_version`,`language_id`) VALUES ("3.7","python:3.7",2);
INSERT INTO `dockerby`.`version`(`input_version`,`docker_version`,`language_id`) VALUES ("3.8","python:3.8",2);
INSERT INTO `dockerby`.`version`(`input_version`,`docker_version`,`language_id`) VALUES ("3.9","python:3.9",2);
INSERT INTO `dockerby`.`version`(`input_version`,`docker_version`,`language_id`) VALUES ("3.10","python:3.10",2);

INSERT INTO `dockerby`.`version`(`input_version`,`docker_version`,`language_id`) VALUES ("10","node:10.23",3);
INSERT INTO `dockerby`.`version`(`input_version`,`docker_version`,`language_id`) VALUES ("12","node:12.22",3);
INSERT INTO `dockerby`.`version`(`input_version`,`docker_version`,`language_id`) VALUES ("14","node:14.19",3);
INSERT INTO `dockerby`.`version`(`input_version`,`docker_version`,`language_id`) VALUES ("16","node:16.14",3);
INSERT INTO `dockerby`.`version`(`input_version`,`docker_version`,`language_id`) VALUES ("17","node:17.9",3);

INSERT INTO `dockerby`.`version`(`input_version`,`docker_version`,`language_id`) VALUES ("5.5","mysql:5.5",4);
INSERT INTO `dockerby`.`version`(`input_version`,`docker_version`,`language_id`) VALUES ("5.6","mysql:5.6",4);
INSERT INTO `dockerby`.`version`(`input_version`,`docker_version`,`language_id`) VALUES ("5.7","mysql:5.7",4);
INSERT INTO `dockerby`.`version`(`input_version`,`docker_version`,`language_id`) VALUES ("8.0","mysql:8.0",4);