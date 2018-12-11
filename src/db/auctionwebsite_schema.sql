DROP SCHEMA IF EXISTS `auctionwebsite` ;

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';


CREATE SCHEMA IF NOT EXISTS `auctionwebsite` DEFAULT CHARACTER SET utf8 ;

USE `auctionwebsite` ;

-- -----------------------------------------------------
-- Table `auctionwebsite`.`Admin`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auctionwebsite`.`Admin` (
  `userId` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(16) NOT NULL,
  `password` VARCHAR(32) NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `address` VARCHAR(255) NOT NULL,
  `description` VARCHAR(2000) NOT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE INDEX `Admin_userId_UNIQUE` (`userId` ASC),
  UNIQUE INDEX `Admin_username_UNIQUE` (`username` ASC));

  
-- -----------------------------------------------------
-- Table `auctionwebsite`.`AuctionTimings`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auctionwebsite`.`AuctionTimings` (
  `auctionDate` DATE,
  `startTime` VARCHAR(5) NOT NULL,
  `endTime` VARCHAR(5) NOT NULL);



-- -----------------------------------------------------
-- Table `auctionwebsite`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auctionwebsite`.`User` (
  `userId` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(16) NOT NULL,
  `password` VARCHAR(32) NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `address` VARCHAR(255) NOT NULL,
  `description` VARCHAR(2000) NOT NULL,
  `active` boolean NOT NULL,
  `securityQuestion` VARCHAR(100) NOT NULL,
  `securityAnswer` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE INDEX `User_userId_UNIQUE` (`userId` ASC),
  UNIQUE INDEX `User_username_UNIQUE` (`username` ASC));
  
  
-- -----------------------------------------------------
-- Table `auctionwebsite`.`CreditCard`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auctionwebsite`.`CreditCard` (
  `cardId` INT NOT NULL AUTO_INCREMENT,
  `userId` INT NOT NULL,
  `number` VARCHAR(19) NOT NULL,
  `expDate` DATETIME NOT NULL,
  `cvv` VARCHAR(4) NOT NULL,
  PRIMARY KEY (`cardId`),
  UNIQUE INDEX `CreditCard_cardId_UNIQUE` (`cardId` ASC),
  INDEX `CreditCard_userId_idx` (`userId` ASC),
  CONSTRAINT `fk_CreditCard_User_userId`
    FOREIGN KEY (`userId`)
    REFERENCES `auctionwebsite`.`User` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
	
-- -----------------------------------------------------
-- Table `auctionwebsite`.`Product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auctionwebsite`.`Product` (
  `prodId` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `description` VARCHAR(2000) NULL,
  `price` DOUBLE NOT NULL,
  `isSold` TINYINT NOT NULL,
  `photo` MEDIUMBLOB NOT NULL,
  `bidStartTime` VARCHAR(5) NOT NULL,
  `bidEndTime` VARCHAR(5) NOT NULL,
  `bidDate` DATE NOT NULL,
  PRIMARY KEY (`prodId`),
  UNIQUE INDEX `Product_prodId_UNIQUE` (`prodId` ASC));


-- -----------------------------------------------------
-- Table `auctionwebsite`.`ProductBid`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auctionwebsite`.`ProductBid` (
  `prodId` INT NOT NULL,
  `userId` INT NOT NULL,
  `bid` DOUBLE NOT NULL,
  CONSTRAINT `fk_ProuctBid_User_userId`
    FOREIGN KEY (`userId`)
    REFERENCES `auctionwebsite`.`User` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ProuctBid_Prod_prodId`
    FOREIGN KEY (`prodId`)
    REFERENCES `auctionwebsite`.`Product` (`prodId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
	
-- -----------------------------------------------------
-- Table `auctionwebsite`.`Inventory`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auctionwebsite`.`Inventory` (
  `invnId` INT NOT NULL AUTO_INCREMENT,
  `userId` INT NOT NULL,
  PRIMARY KEY (`invnId`),
  INDEX `Inventory_id_idx` (`userId` ASC),
  UNIQUE INDEX `Inventory_invnId_UNIQUE` (`invnId` ASC),
  CONSTRAINT `fk_Inventory_User_userId`
    FOREIGN KEY (`userId`)
    REFERENCES `auctionwebsite`.`User` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

	
	
-- -----------------------------------------------------
-- Table `auctionwebsite`.`InventoryProduct`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auctionwebsite`.`InventoryProduct` (
  `invnId` INT NOT NULL,
  `prodId` INT NOT NULL,
  PRIMARY KEY (`invnId`, `prodId`),
  INDEX `InventoryProduct_prodId_idx` (`prodId` ASC),
  CONSTRAINT `fk_InventoryProduct_Inventory_invnId`
    FOREIGN KEY (`invnId`)
    REFERENCES `auctionwebsite`.`Inventory` (`invnId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_InventoryProduct_Product_prodId`
    FOREIGN KEY (`prodId`)
    REFERENCES `auctionwebsite`.`Product` (`prodId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `auctionwebsite`.`Transaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auctionwebsite`.`Transaction` (
  `trxnId` INT NOT NULL AUTO_INCREMENT,
  `userId` INT NOT NULL,
  `date` DATETIME NOT NULL,
  `price` DOUBLE NOT NULL,
  PRIMARY KEY (`trxnId`),
  UNIQUE INDEX `Transaction_trxnId_UNIQUE` (`trxnId` ASC),
  INDEX `Transaction_userId_idx` (`userId` ASC),
  CONSTRAINT `fk_Transaction_User_userId`
    FOREIGN KEY (`userId`)
    REFERENCES `auctionwebsite`.`User` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `auctionwebsite`.`TransactionProduct`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auctionwebsite`.`TransactionProduct` (
  `trxnId` INT NOT NULL,
  `prodId` INT NOT NULL,
  PRIMARY KEY (`trxnId`, `prodId`),
  INDEX `TransactionProduct_prodId_idx` (`prodId` ASC),
  CONSTRAINT `fk_TransactionProduct_Transaction_txnId`
    FOREIGN KEY (`trxnId`)
    REFERENCES `auctionwebsite`.`Transaction` (`trxnId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TransactionProduct_Product_prodId`
    FOREIGN KEY (`prodId`)
    REFERENCES `auctionwebsite`.`Product` (`prodId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


INSERT INTO `auctionwebsite`.`AuctionTimings` (`startTime`, `endTime`) VALUES ('08:00', '06:00');

INSERT INTO `auctionwebsite`.`Admin` VALUES (10000, 'kalyan', 'kalyan', 'kalyan', 'dallas', 'Website Owner');


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;