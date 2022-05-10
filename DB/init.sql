DROP DATABASE IF EXISTS `dockerby`;
CREATE DATABASE `dockerby`;

CREATE TABLE `dockerby`.`language` (
  `language_id` BIGINT NOT NULL,
  `language_name` VARCHAR(60) NOT NULL,
  PRIMARY KEY (`language_id`));

CREATE TABLE `dockerby`.`setting_config` (
  `setting_config_id` BIGINT NOT NULL,
  `language_id` BIGINT NULL,
  `setting_config_name` VARCHAR(60) NOT NULL,
  `group_code` VARCHAR(255) NOT NULL,
  `option` VARCHAR(255) NULL,
  PRIMARY KEY (`setting_config_id`),
  INDEX `fk-language-setting_config_idx` (`language_id` ASC),
  CONSTRAINT `fk-language-setting_config`
    FOREIGN KEY (`language_id`)
    REFERENCES `dockerby`.`language` (`language_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `dockerby`.`version` (
  `version_id` BIGINT NOT NULL AUTO_INCREMENT,
  `language_id` BIGINT NULL,
  `input_version` VARCHAR(45) NOT NULL,
  `docker_version` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`version_id`),
  INDEX `fk-language_id-version_idx` (`language_id` ASC),
  CONSTRAINT `fk-language_id-version`
    FOREIGN KEY (`language_id`)
    REFERENCES `dockerby`.`language` (`language_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `dockerby`.`build_tool` (
  `build_tool_id` BIGINT NOT NULL AUTO_INCREMENT,
  `build_tool_name` VARCHAR(45) NOT NULL,
  `setting_config_id` BIGINT NULL,
  PRIMARY KEY (`build_tool_id`),
  INDEX `fk-setting_config-build_tool_idx` (`setting_config_id` ASC),
  CONSTRAINT `fk-setting_config-build_tool`
    FOREIGN KEY (`setting_config_id`)
    REFERENCES `dockerby`.`setting_config` (`setting_config_id`)
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
  `last_success_date` DATETIME NULL,
  `last_fail_date` DATETIME NULL,
  `last_duration` VARCHAR(255) NULL,
  PRIMARY KEY (`project_id`));

CREATE TABLE `dockerby`.`gitlab_config` (
  `gitlab_config_id` BIGINT NOT NULL AUTO_INCREMENT,
  `host_url` VARCHAR(255) NOT NULL,
  `secret_token` VARCHAR(255) NOT NULL,
  `repository_url` VARCHAR(255) NOT NULL,
  `branch_name` VARCHAR(255) NOT NULL,
  `git_project_id` BIGINT NULL,
  `gitlab_access_token_id` BIGINT NULL,
  `project_id` BIGINT NULL,
  PRIMARY KEY (`gitlab_config_id`),
  INDEX `fk-gitlab_access_token-gitlab_config_idx` (`gitlab_access_token_id` ASC),
  INDEX `fk-project-gitlab_config_idx` (`project_id` ASC),
  CONSTRAINT `fk-gitlab_access_token-gitlab_config`
    FOREIGN KEY (`gitlab_access_token_id`)
    REFERENCES `dockerby`.`gitlab_access_token` (`gitlab_access_token_id`)
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

CREATE TABLE `dockerby`.`SPRING_SESSION`(
    PRIMARY_ID CHAR(36) NOT NULL,
    SESSION_ID CHAR(36) NOT NULL,
    CREATION_TIME BIGINT NOT NULL,
    LAST_ACCESS_TIME BIGINT NOT NULL,
    MAX_INACTIVE_INTERVAL INT NOT NULL,
    EXPIRY_TIME BIGINT NOT NULL,
    PRINCIPAL_NAME VARCHAR(100),
    CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)
);

CREATE UNIQUE INDEX SPRING_SESSION_IX1 ON `dockerby`.`SPRING_SESSION` (SESSION_ID);
CREATE INDEX SPRING_SESSION_IX2 ON `dockerby`.`SPRING_SESSION` (EXPIRY_TIME);
CREATE INDEX SPRING_SESSION_IX3 ON `dockerby`.`SPRING_SESSION` (PRINCIPAL_NAME);

CREATE TABLE `dockerby`.`SPRING_SESSION_ATTRIBUTES` (
    SESSION_PRIMARY_ID CHAR(36) NOT NULL,
    ATTRIBUTE_NAME VARCHAR(200) NOT NULL,
    ATTRIBUTE_BYTES BLOB  NOT NULL,
    CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
    CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION(PRIMARY_ID) ON DELETE CASCADE
);



INSERT INTO `dockerby`.`language`(`language_id`,`language_name`) VALUES (1,"Java");
INSERT INTO `dockerby`.`language`(`language_id`,`language_name`) VALUES (2,"Python");
INSERT INTO `dockerby`.`language`(`language_id`,`language_name`) VALUES (3,"Node");
INSERT INTO `dockerby`.`language`(`language_id`,`language_name`) VALUES (4,"MySQL");
INSERT INTO `dockerby`.`language`(`language_id`,`language_name`) VALUES (5,"Mongo");
-- INSERT INTO `dockerby`.`language`(`language_id`,`language_name`) VALUES (6,"Redis");
INSERT INTO `dockerby`.`language`(`language_id`,`language_name`) VALUES (7,"mariadb");

INSERT INTO `dockerby`.`setting_config`(`setting_config_id`,`setting_config_name`,`group_code`,`option`,`language_id`) VALUES (1,"SpringBoot","Framework",null,1);
INSERT INTO `dockerby`.`setting_config`(`setting_config_id`,`setting_config_name`,`group_code`,`option`,`language_id`) VALUES (2,"Vue","Framework",null,3);
INSERT INTO `dockerby`.`setting_config`(`setting_config_id`,`setting_config_name`,`group_code`,`option`,`language_id`) VALUES (3,"React","Framework",null,3);
INSERT INTO `dockerby`.`setting_config`(`setting_config_id`,`setting_config_name`,`group_code`,`option`,`language_id`) VALUES (4,"Next","Framework",null,3);
INSERT INTO `dockerby`.`setting_config`(`setting_config_id`,`setting_config_name`,`group_code`,`option`,`language_id`) VALUES (5,"Django","Framework",null,2);

INSERT INTO `dockerby`.`setting_config`(`setting_config_id`,`setting_config_name`,`group_code`,`option`,`language_id`) VALUES (6,"MySQL","Dbms","mysql",4);
INSERT INTO `dockerby`.`setting_config`(`setting_config_id`,`setting_config_name`,`group_code`,`option`,`language_id`) VALUES (7,"Mongo","Dbms","mongo",5);
-- INSERT INTO `dockerby`.`setting_config`(`setting_config_id`,`setting_config_name`,`group_code`,`option`,`language_id`) VALUES (8,"Redis","Dbms",null,6);
INSERT INTO `dockerby`.`setting_config`(`setting_config_id`,`setting_config_name`,`group_code`,`option`,`language_id`) VALUES (9,"mariadb","Dbms","maria",7);

INSERT INTO `dockerby`.`build_tool`(`build_tool_name`,`setting_config_id`) VALUES ("Gradle",1);
INSERT INTO `dockerby`.`build_tool`(`build_tool_name`,`setting_config_id`) VALUES ("Maven",1);
INSERT INTO `dockerby`.`build_tool`(`build_tool_name`,`setting_config_id`) VALUES ("Yes",2);
INSERT INTO `dockerby`.`build_tool`(`build_tool_name`,`setting_config_id`) VALUES ("Yes",3);

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

INSERT INTO `dockerby`.`version`(`input_version`,`docker_version`,`language_id`) VALUES ("4.0","mongo:4.0",5);
INSERT INTO `dockerby`.`version`(`input_version`,`docker_version`,`language_id`) VALUES ("4.2","mongo:4.2",5);
INSERT INTO `dockerby`.`version`(`input_version`,`docker_version`,`language_id`) VALUES ("4.4","mongo:4.4",5);
INSERT INTO `dockerby`.`version`(`input_version`,`docker_version`,`language_id`) VALUES ("5.0","mongo:5.0",5);

-- INSERT INTO `dockerby`.`version`(`input_version`,`docker_version`,`language_id`) VALUES ("5.0","redis:5.0",6);
-- INSERT INTO `dockerby`.`version`(`input_version`,`docker_version`,`language_id`) VALUES ("6.0","redis:6.0",6);
-- INSERT INTO `dockerby`.`version`(`input_version`,`docker_version`,`language_id`) VALUES ("6.2","redis:6.2",6);
-- INSERT INTO `dockerby`.`version`(`input_version`,`docker_version`,`language_id`) VALUES ("7.0","redis:7.0",6);

INSERT INTO `dockerby`.`version`(`input_version`,`docker_version`,`language_id`) VALUES ("10","mariadb:10",7);
INSERT INTO `dockerby`.`version`(`input_version`,`docker_version`,`language_id`) VALUES ("10.1","mariadb:10.1",7);
INSERT INTO `dockerby`.`version`(`input_version`,`docker_version`,`language_id`) VALUES ("10.2","mariadb:10.2",7);
INSERT INTO `dockerby`.`version`(`input_version`,`docker_version`,`language_id`) VALUES ("10.3","mariadb:10.3",7);
INSERT INTO `dockerby`.`version`(`input_version`,`docker_version`,`language_id`) VALUES ("10.4","mariadb:10.4",7);
INSERT INTO `dockerby`.`version`(`input_version`,`docker_version`,`language_id`) VALUES ("10.5","mariadb:10.5",7);
INSERT INTO `dockerby`.`version`(`input_version`,`docker_version`,`language_id`) VALUES ("10.6","mariadb:10.6",7);
INSERT INTO `dockerby`.`version`(`input_version`,`docker_version`,`language_id`) VALUES ("10.7","mariadb:10.7",7);