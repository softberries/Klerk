-- -----------------------------------------------------
-- Table `product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `product` ;

CREATE  TABLE IF NOT EXISTS `product` (
  `id` BIGINT  NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(400) NOT NULL ,
  `code` VARCHAR(45) NOT NULL ,
  `description` TEXT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) ,
  UNIQUE INDEX `code_UNIQUE` (`code` ASC) )
;


-- -----------------------------------------------------
-- Table `person`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `person` ;

CREATE  TABLE IF NOT EXISTS `person` (
  `id` BIGINT  NOT NULL AUTO_INCREMENT ,
  `firstName` VARCHAR(45) NULL ,
  `lastName` VARCHAR(45) NULL ,
  `telephone` VARCHAR(45) NULL ,
  `mobile` VARCHAR(45) NULL ,
  `email` VARCHAR(45) NULL ,
  `www` VARCHAR(45) NULL ,
  PRIMARY KEY (`id`) )
;


-- -----------------------------------------------------
-- Table `company`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `company` ;

CREATE  TABLE IF NOT EXISTS `company` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(400) NOT NULL ,
  `vatid` VARCHAR(45) NOT NULL ,
  `telephone` VARCHAR(45) NULL ,
  `mobile` VARCHAR(45) NULL ,
  `email` VARCHAR(200) NULL ,
  `www` VARCHAR(200) NULL ,
  PRIMARY KEY (`id`) )
;


-- -----------------------------------------------------
-- Table `document`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `document` ;

CREATE  TABLE IF NOT EXISTS `document` (
  `id` BIGINT  NOT NULL AUTO_INCREMENT ,
  `title` VARCHAR(400) NOT NULL ,
  `notes` TEXT NULL ,
  `createdDate` DATETIME NOT NULL ,
  `transactionDate` DATETIME NOT NULL ,
  `dueDate` DATETIME NULL ,
  `placeCreated` VARCHAR(200) NULL ,
  `creator_id` BIGINT  NOT NULL ,
  `buyer_id` BIGINT  NOT NULL ,
  `seller_id` BIGINT  NOT NULL ,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`creator_id`) REFERENCES `person`(`id`),
  FOREIGN KEY (`buyer_id`) REFERENCES `company`(`id`),
  FOREIGN KEY (`seller_id`) REFERENCES `company`(`id`) )
;


-- -----------------------------------------------------
-- Table `address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `address` ;

CREATE  TABLE IF NOT EXISTS `address` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `country` VARCHAR(200) NULL ,
  `city` VARCHAR(220) NULL ,
  `street` VARCHAR(220) NULL ,
  `postCode` VARCHAR(100) NULL ,
  `houseNumber` VARCHAR(100) NULL ,
  `flatNumber` VARCHAR(100) NULL ,
  `notes` TEXT NULL ,
  `main` VARCHAR(45) NULL ,
  `person_id` BIGINT ,
  `company_id` BIGINT ,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`person_id`) REFERENCES `person`(`id`),
  FOREIGN KEY (`company_id`) REFERENCES `company`(`id`)
  )
;


-- -----------------------------------------------------
-- Table `documentitem`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `documentitem` ;

CREATE  TABLE IF NOT EXISTS `documentitem` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `priceNetSingle` VARCHAR(45) NULL ,
  `priceGrossSingle` VARCHAR(45) NULL ,
  `priceTaxSingle` VARCHAR(45) NULL ,
  `priceNetAll` VARCHAR(45) NULL ,
  `priceGrossAll` VARCHAR(45) NULL ,
  `priceTaxAll` VARCHAR(45) NULL ,
  `taxValue` VARCHAR(45) NULL ,
  `quantity` VARCHAR(45) NULL ,
  `product_id` BIGINT  NOT NULL ,
  `product_name` VARCHAR(400)  NOT NULL ,
  `document_id` BIGINT  NOT NULL ,
  FOREIGN KEY (`product_id`) REFERENCES `product`(`id`),
  FOREIGN KEY (`document_id`) REFERENCES `document`(`id`),
  PRIMARY KEY (`id`)  )
;

