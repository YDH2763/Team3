DROP DATABASE IF EXISTS OMOK; 
CREATE DATABASE OMOK;

USE OMOK;

DROP TABLE IF EXISTS `RESULT`;

CREATE TABLE `RESULT` (
	`RE_NUM`	INT PRIMARY KEY AUTO_INCREMENT	NOT NULL,
	`RE_WINNER`	ENUM("BLACK", "WHITE", "DRAW") NOT	NULL,
	`RE_DATE`	DATETIME DEFAULT CURRENT_TIMESTAMP NOT	NULL,
	`RE_RO_ID`	INT	NOT NULL
);

DROP TABLE IF EXISTS `ROOM`;

CREATE TABLE `ROOM` (
	`RO_ID`	INT PRIMARY KEY AUTO_INCREMENT	NOT NULL,
	`RO_NUM`	INT NOT	NULL,
	`RO_B_U_NAME`	VARCHAR(10)	NULL,
	`RO_W_U_NAME`	VARCHAR(10)	NULL
);

DROP TABLE IF EXISTS `SCORE`;

CREATE TABLE `SCORE` (
	`S_NUM`	INT PRIMARY KEY AUTO_INCREMENT	NOT NULL,
	`S_POSITION`	ENUM("BLACK", "WHITE") NOT	NULL,
	`S_COUNT`	INT DEFAULT 0 NOT	NULL,
	`S_WIN`	INT DEFAULT 0 NOT	NULL,
	`S_LOSE`	INT DEFAULT 0 NOT	NULL,
	`S_DRAW`	INT DEFAULT 0 NOT	NULL,
	`S_U_NAME`	VARCHAR(10)	NULL
);

DROP TABLE IF EXISTS `GIBO`;

CREATE TABLE `GIBO` (
	`G_ID`	INT PRIMARY KEY AUTO_INCREMENT	NOT NULL,
	`G_COUNT`	INT NOT	NULL,
	`G_X`	INT NOT	NULL,
	`G_Y`	INT NOT	NULL,
	`G_RO_ID`	INT	NOT NULL
);

DROP TABLE IF EXISTS `USER`;

CREATE TABLE `USER` (
	`U_NAME`	VARCHAR(10) PRIMARY KEY NOT	NULL,
	`U_PW`	VARCHAR(20) NOT	NULL,
	`resultU_DEL`	ENUM("Y", "N") DEFAULT "N" NOT	NULL
);

ALTER TABLE `RESULT` ADD CONSTRAINT `FK_ROOM_TO_RESULT_1` FOREIGN KEY (
	`RE_RO_ID`
)
REFERENCES `ROOM` (
	`RO_ID`
);

ALTER TABLE `ROOM` ADD CONSTRAINT `FK_USER_TO_ROOM_1` FOREIGN KEY (
	`RO_B_U_NAME`
)
REFERENCES `USER` (
	`U_NAME`
);

ALTER TABLE `ROOM` ADD CONSTRAINT `FK_USER_TO_ROOM_2` FOREIGN KEY (
	`RO_W_U_NAME`
)
REFERENCES `USER` (
	`U_NAME`
);

ALTER TABLE `SCORE` ADD CONSTRAINT `FK_USER_TO_SCORE_1` FOREIGN KEY (
	`S_U_NAME`
)
REFERENCES `USER` (
	`U_NAME`
);

ALTER TABLE `GIBO` ADD CONSTRAINT `FK_ROOM_TO_GIBO_1` FOREIGN KEY (
	`G_RO_ID`
)
REFERENCES `ROOM` (
	`RO_ID`
);

