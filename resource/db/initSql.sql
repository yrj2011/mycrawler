
DROP SCHEMA IF EXISTS `mycrawler` ;
CREATE SCHEMA IF NOT EXISTS `mycrawler` DEFAULT CHARACTER SET utf8 ;



-- -----------------------------------------------------
-- Table `mycrawler`.`ipproxy`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mycrawler`.`ipproxy` ;

CREATE TABLE IF NOT EXISTS `mycrawler`.`ipproxy` (
  `id` VARCHAR(64) NOT NULL,
  `ip` VARCHAR(64) NOT NULL,
  `port` int NOT NULL,
  `type` VARCHAR(16)  NULL,
  `available` int(1) null  default 0,
  `version` INT NOT NULL AUTO_INCREMENT,
  `createdUser` VARCHAR(30) NULL,
  `createdApp` VARCHAR(30) NULL,
  `createdDate` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `lastUpdatedUser` VARCHAR(30) NULL,
  `lastUpdatedApp` VARCHAR(30) NULL,
  `lastUpdatedDate` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `mycrawler`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mycrawler`.`user` ;

CREATE TABLE IF NOT EXISTS `mycrawler`.`user` (
  `id` VARCHAR(64) NOT NULL,
  `name` VARCHAR(64) NOT NULL,
  `sex` int NOT NULL default 1,
  `age` int(2)  NULL ,
  `state` int(1) NULL default 0,
  `version` INT NOT NULL DEFAULT 0,
  `createdUser` VARCHAR(30) NULL,
  `createdApp` VARCHAR(30) NULL,
  `createdDate` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `lastUpdatedUser` VARCHAR(30) NULL,
  `lastUpdatedApp` VARCHAR(30) NULL,
  `lastUpdatedDate` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;