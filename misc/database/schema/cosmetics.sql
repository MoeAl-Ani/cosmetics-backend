-- MySQL Script generated by MySQL Workbench
-- su 22. marraskuuta 2020 15.39.04
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema cosmetics
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `cosmetics` ;

-- -----------------------------------------------------
-- Schema cosmetics
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `cosmetics` DEFAULT CHARACTER SET utf8mb4 ;
USE `cosmetics` ;

-- -----------------------------------------------------
-- Table `permission`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `permission` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` ENUM('GLOBAL_CREATE', 'GLOBAL_READ', 'GLOBAL_UPDATE', 'GLOBAL_DELETE') NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `language`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `language` (
  `id` INT NOT NULL,
  `language_code` VARCHAR(2) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NULL,
  `last_name` VARCHAR(45) NULL,
  `email` VARCHAR(45) NOT NULL,
  `phone_number` VARCHAR(25) NOT NULL,
  `language_id` INT NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  INDEX `fk_user_language_id_idx` (`language_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_language_id`
    FOREIGN KEY (`language_id`)
    REFERENCES `language` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `address` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `country` VARCHAR(5) NOT NULL,
  `city` VARCHAR(45) NOT NULL,
  `street` VARCHAR(45) NOT NULL,
  `zip` VARCHAR(45) NULL,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  PRIMARY KEY (`id`),
  INDEX `address_city_name_idx` (`city` ASC) VISIBLE,
  INDEX `address_street_idx` (`street` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `category` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `product` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `price` DOUBLE NOT NULL,
  `available` TINYINT(1) NOT NULL DEFAULT 0,
  `discount` DOUBLE NOT NULL DEFAULT 0.0,
  `category_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `product_available_idx` (`available` ASC) VISIBLE,
  INDEX `fk_product_category_id_idx` (`category_id` ASC) VISIBLE,
  CONSTRAINT `fk_product_category_id`
    FOREIGN KEY (`category_id`)
    REFERENCES `category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `product_translation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `product_translation` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `product_id` INT UNSIGNED NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `description` VARCHAR(255) NOT NULL,
  `language_id` INT NOT NULL,
  UNIQUE INDEX `prodcut_translation_pr_id_lang_id_unique` (`product_id` ASC, `language_id` ASC) VISIBLE,
  INDEX `fk_product_translation_language_id_idx` (`language_id` ASC) VISIBLE,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_product_translation_product_id`
    FOREIGN KEY (`product_id`)
    REFERENCES `product` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_translation_language_id`
    FOREIGN KEY (`language_id`)
    REFERENCES `language` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `order_details`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `order_details` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` INT UNSIGNED NOT NULL,
  `order_reference` VARCHAR(255) NOT NULL,
  `net_amount` DOUBLE NOT NULL,
  `address_id` BIGINT UNSIGNED NULL,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `fk_order_details_user_id_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_order_details_address_id_idx` (`address_id` ASC) VISIBLE,
  CONSTRAINT `fk_order_details_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_details_address_id`
    FOREIGN KEY (`address_id`)
    REFERENCES `address` (`id`)
    ON DELETE SET NULL
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `order_product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `order_product` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `order_id` BIGINT UNSIGNED NOT NULL,
  `product_id` INT UNSIGNED NOT NULL,
  `quantity` INT NOT NULL,
  `comment` VARCHAR(255) NULL,
  UNIQUE INDEX `order_product_pr_id_o_id_unique` (`order_id` ASC, `product_id` ASC) VISIBLE,
  INDEX `fk_order_product_pr_id_idx` (`product_id` ASC) VISIBLE,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_order_product_o_id`
    FOREIGN KEY (`order_id`)
    REFERENCES `order_details` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_product_pr_id`
    FOREIGN KEY (`product_id`)
    REFERENCES `product` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `payment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `payment` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `transaction_id` VARCHAR(255) NOT NULL,
  `order_id` BIGINT UNSIGNED NOT NULL,
  `type` VARCHAR(25) NOT NULL,
  `selected_method` INT NULL,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `payment_order_id_unique` (`order_id` ASC) VISIBLE,
  CONSTRAINT `fk_payment_o_id`
    FOREIGN KEY (`order_id`)
    REFERENCES `order_details` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `payment_status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `payment_status` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `payment_id` BIGINT UNSIGNED NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `fk_payment_status_payment_id_idx` (`payment_id` ASC) VISIBLE,
  CONSTRAINT `fk_payment_status_payment_id`
    FOREIGN KEY (`payment_id`)
    REFERENCES `payment` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `payment_refund`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `payment_refund` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `payment_id` BIGINT UNSIGNED NOT NULL,
  `refund_token` VARCHAR(255) NOT NULL,
  `refund_status` VARCHAR(15) NOT NULL,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `fk_payment_refund_payment_id_idx` (`payment_id` ASC) VISIBLE,
  UNIQUE INDEX `refund_token_UNIQUE` (`refund_token` ASC) VISIBLE,
  UNIQUE INDEX `payment_id_UNIQUE` (`payment_id` ASC) VISIBLE,
  CONSTRAINT `fk_payment_refund_payment_id`
    FOREIGN KEY (`payment_id`)
    REFERENCES `payment` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `product_image`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `product_image` (
  `id` INT UNSIGNED NOT NULL,
  `image_url` VARCHAR(255) NULL,
  `product_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_product_image_product_id_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_product_image_product_id`
    FOREIGN KEY (`product_id`)
    REFERENCES `product` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `category_translation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `category_translation` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `category_id` INT UNSIGNED NOT NULL,
  `name` VARCHAR(25) NULL,
  `language_id` INT NOT NULL,
  INDEX `fk_category_translation_language_id_idx` (`language_id` ASC) VISIBLE,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `category_translation_lang_id_cat_id_unique` (`category_id` ASC, `language_id` ASC) VISIBLE,
  CONSTRAINT `fk_category_translation_language_id`
    FOREIGN KEY (`language_id`)
    REFERENCES `language` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_category_translation_category_id`
    FOREIGN KEY (`category_id`)
    REFERENCES `category` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `permission`
-- -----------------------------------------------------
START TRANSACTION;
USE `cosmetics`;
INSERT INTO `permission` (`id`, `name`) VALUES (1, 'GLOBAL_CREATE');
INSERT INTO `permission` (`id`, `name`) VALUES (2, 'GLOBAL_READ');
INSERT INTO `permission` (`id`, `name`) VALUES (3, 'GLOBAL_UPDATE');
INSERT INTO `permission` (`id`, `name`) VALUES (4, 'GLOBAL_DELETE');

COMMIT;


-- -----------------------------------------------------
-- Data for table `language`
-- -----------------------------------------------------
START TRANSACTION;
USE `cosmetics`;
INSERT INTO `language` (`id`, `language_code`) VALUES (1, 'EN');
INSERT INTO `language` (`id`, `language_code`) VALUES (2, 'AR');
INSERT INTO `language` (`id`, `language_code`) VALUES (3, 'KU');
INSERT INTO `language` (`id`, `language_code`) VALUES (4, 'FI');

COMMIT;


-- -----------------------------------------------------
-- Data for table `category`
-- -----------------------------------------------------
START TRANSACTION;
USE `cosmetics`;
INSERT INTO `category` (`id`) VALUES (1);
INSERT INTO `category` (`id`) VALUES (2);

COMMIT;


-- -----------------------------------------------------
-- Data for table `product`
-- -----------------------------------------------------
START TRANSACTION;
USE `cosmetics`;
INSERT INTO `product` (`id`, `price`, `available`, `discount`, `category_id`) VALUES (1, 10.0, true, 0.0, 1);
INSERT INTO `product` (`id`, `price`, `available`, `discount`, `category_id`) VALUES (2, 15.0, true, 0.0, 1);
INSERT INTO `product` (`id`, `price`, `available`, `discount`, `category_id`) VALUES (3, 13.5, false, 0.0, 2);
INSERT INTO `product` (`id`, `price`, `available`, `discount`, `category_id`) VALUES (4, 11.0, true, 10.0, 2);
INSERT INTO `product` (`id`, `price`, `available`, `discount`, `category_id`) VALUES (5, 12.0, true, 12.5, 2);

COMMIT;


-- -----------------------------------------------------
-- Data for table `product_translation`
-- -----------------------------------------------------
START TRANSACTION;
USE `cosmetics`;
INSERT INTO `product_translation` (`id`, `product_id`, `name`, `description`, `language_id`) VALUES (1, 1, 'makeup1', 'some makeup 1', 1);
INSERT INTO `product_translation` (`id`, `product_id`, `name`, `description`, `language_id`) VALUES (2, 1, 'anotherlang', 'some1', 2);
INSERT INTO `product_translation` (`id`, `product_id`, `name`, `description`, `language_id`) VALUES (3, 2, 'makeup2', 'some makeup 2', 1);
INSERT INTO `product_translation` (`id`, `product_id`, `name`, `description`, `language_id`) VALUES (4, 2, 'another2lang', 'some2', 2);
INSERT INTO `product_translation` (`id`, `product_id`, `name`, `description`, `language_id`) VALUES (5, 3, 'makeup3', 'some makeup 3', 1);
INSERT INTO `product_translation` (`id`, `product_id`, `name`, `description`, `language_id`) VALUES (6, 3, 'another3lang', 'some3', 2);
INSERT INTO `product_translation` (`id`, `product_id`, `name`, `description`, `language_id`) VALUES (7, 4, 'makeup4', 'some makeup 4', 1);
INSERT INTO `product_translation` (`id`, `product_id`, `name`, `description`, `language_id`) VALUES (8, 4, 'another4lang', 'some4', 2);
INSERT INTO `product_translation` (`id`, `product_id`, `name`, `description`, `language_id`) VALUES (9, 5, 'makeup5', 'some makeup 5', 1);
INSERT INTO `product_translation` (`id`, `product_id`, `name`, `description`, `language_id`) VALUES (10, 5, 'another5lang', 'some5', 2);

COMMIT;


-- -----------------------------------------------------
-- Data for table `product_image`
-- -----------------------------------------------------
START TRANSACTION;
USE `cosmetics`;
INSERT INTO `product_image` (`id`, `image_url`, `product_id`) VALUES (1, 'https://infotamia.ams3.digitaloceanspaces.com/cosmetics/gJa12nkAKIBb39FN.jpg', 1);
INSERT INTO `product_image` (`id`, `image_url`, `product_id`) VALUES (2, 'https://infotamia.ams3.digitaloceanspaces.com/cosmetics/Cm6AnANNTtUgpk34.jpg', 2);
INSERT INTO `product_image` (`id`, `image_url`, `product_id`) VALUES (3, 'https://infotamia.ams3.digitaloceanspaces.com/cosmetics/IbUAv49GXFkpP1wO.jpg', 3);
INSERT INTO `product_image` (`id`, `image_url`, `product_id`) VALUES (4, 'https://infotamia.ams3.digitaloceanspaces.com/cosmetics/JVJghf8spruqUjeO.jpg', 4);
INSERT INTO `product_image` (`id`, `image_url`, `product_id`) VALUES (5, 'https://infotamia.ams3.digitaloceanspaces.com/cosmetics/xGTuyI2NhxJKqVsW.jpg', 5);

COMMIT;


-- -----------------------------------------------------
-- Data for table `category_translation`
-- -----------------------------------------------------
START TRANSACTION;
USE `cosmetics`;
INSERT INTO `category_translation` (`id`, `category_id`, `name`, `language_id`) VALUES (1, 1, 'Skin', 1);
INSERT INTO `category_translation` (`id`, `category_id`, `name`, `language_id`) VALUES (2, 1, 'Skin Translated', 2);
INSERT INTO `category_translation` (`id`, `category_id`, `name`, `language_id`) VALUES (3, 2, 'Face Mask', 1);
INSERT INTO `category_translation` (`id`, `category_id`, `name`, `language_id`) VALUES (4, 2, 'Face Mask Translated', 2);

COMMIT;
