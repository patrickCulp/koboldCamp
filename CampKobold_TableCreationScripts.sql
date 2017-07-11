DROP SCHEMA IF EXISTS `KoboldCamp`;
CREATE SCHEMA `KoboldCamp` ;
USE `KoboldCamp`;
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Table structure for table `users`
--
DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
 `user_id` int(11) NOT NULL AUTO_INCREMENT,
 `username` varchar(70) NOT NULL UNIQUE,
 `password` varchar(20) NOT NULL,
 `enabled` tinyint(1) NOT NULL,
 PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;
--
-- Dumping data for table `users`
--
INSERT INTO `users` (`user_id`, `username`, `password`, `enabled`) VALUES
	(1, "test_admin", "password", 1),
	(2, "test_employee", "password", 1),
	(3, "test_user", "password", 1);
--
-- Table structure for table `authorities`
--
CREATE TABLE IF NOT EXISTS `authorities` (
 `username` varchar(70) NOT NULL,
 `authority` varchar(20) NOT NULL,
 KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
--
-- Dumping data for table `authorities`
--
INSERT INTO `authorities` (`username`, `authority`) VALUES
("test_admin", "ROLE_ADMIN"),
("test_admin", "ROLE_EMPLOYEE"),
("test_admin", "ROLE_USER"),
("test_employee", "ROLE_EMPLOYEE"),
("test_employee", "ROLE_USER"),
("test_user", "ROLE_USER");
--
-- Constraints for table `authorities`
--
ALTER TABLE `authorities`
 ADD CONSTRAINT `authorities_fk_username` FOREIGN KEY (`username`) REFERENCES `users` (`username`);

--
-- Table structure for table `user_profiles`
--
CREATE TABLE IF NOT EXISTS `user_profiles` (
	`user_id` int(11) NOT NULL,
	`first_name` VARCHAR(20) NOT NULL,
	`last_name` VARCHAR(40) NOT NULL,
	`email` VARCHAR(70) NOT NULL,
	`phone` VARCHAR(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;
--
-- Constraints for table `user_profiles`
--
ALTER TABLE `user_profiles`
 ADD CONSTRAINT `user_profile_fk_userid` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Dumping data for table `user_profiles`
--
INSERT INTO `user_profiles` (`user_id`, `first_name`, `last_name`, `email`, `phone`) VALUES
(1, "Jenny", "Toothea", "bigadmin@koboldcamp.com", "867-5309"),
(2, "Gop", "Nalse", "campminion@koboldcamp.com", "123-4567"),
(3, "Qrag", "Vromka", "supercamper@lovetocamp.com", "678-3344");
--
-- Table structure for table `categories`
--
CREATE TABLE IF NOT EXISTS `categories` (
	`category_id` int(11) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(50) NOT NULL,
	PRIMARY KEY (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

--
-- Dumping data for table `categories`
--
INSERT INTO `categories` (`category_id`, `name`) VALUES
(1, "Backpacks"),
(2, "Sleeping Bags"),
(3, "Camping Stoves"),
(4, "Paddling Gear"),
(5, "Tents");

--
-- Table structure for table `assets`
--
CREATE TABLE IF NOT EXISTS `assets` (
	`asset_id` int(11) NOT NULL AUTO_INCREMENT,
	`category_id` int(11) NOT NULL,
	`brand` VARCHAR(20) NOT NULL,
	`description` VARCHAR(250) NOT NULL,
	PRIMARY KEY (`asset_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

--
-- Constraints for table `assets`
--
ALTER TABLE `assets`
 ADD CONSTRAINT `assets_fk_categoriyid` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`);

--
-- Dumping data for table `user_profiles`
--
INSERT INTO `assets` (`asset_id`, `category_id`, `brand`, `description`) VALUES
( 1, 1 , "camelCases", "Camel colored backpacks for hiking."),
( 2, 5 , "AwesomeTents", "Top notch tent, in rainbow colors!"),
( 3, 5 , "IckyTents", "Gnobbly, leaky, but still technically a tent.");

--
-- Table structure for table `asset_statuses`
--
CREATE TABLE IF NOT EXISTS `asset_statuses` (
	`status_id` int(11) NOT NULL AUTO_INCREMENT,
	`status` VARCHAR(20) NOT NULL,
	PRIMARY KEY (`status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

--
-- Dumping data for table `asset_statuses`
--
INSERT INTO `asset_statuses` (`status_id`, `status`) VALUES
(1, "AVAILABLE"),
(2, "CHECKED OUT"),
(3, "BROKEN"),
(4, "LOST"),
(5, "UNDER REPAIRS");

--
-- Dumping data for table `asset_records`
--

CREATE TABLE IF NOT EXISTS `asset_records` (
	`record_id` int(11) NOT NULL AUTO_INCREMENT,
	`asset_id` int(11) NOT NULL,
	`employee_id` int(11) NOT NULL,
	`member_id` int(11) NOT NULL,
	`status_id` int(11) NOT NULL,
	`record_date` DATE NOT NULL,
	`note` VARCHAR(500) NOT NULL,
	PRIMARY KEY (`record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;
--
-- Constraints for table `asset_records`
--
ALTER TABLE `asset_records`
 ADD CONSTRAINT `asset_record_fk_assets` FOREIGN KEY (`asset_id`) REFERENCES `assets` (`asset_id`),
 ADD CONSTRAINT `asset_record_fk_employees` FOREIGN KEY (`employee_id`) REFERENCES `users` (`user_id`),
 ADD CONSTRAINT `asset_record_fk_members` FOREIGN KEY (`member_id`) REFERENCES `users` (`user_id`),
 ADD CONSTRAINT `asset_record_fk_statuses` FOREIGN KEY (`status_id`) REFERENCES `asset_statuses` (`status_id`);


INSERT INTO `asset_records` (`record_id`, `asset_id`, `employee_id`, `member_id`, `status_id`, `record_date`, `note`) VALUES
( 1, 3, 2, 3, 2, '2012-01-01',"Gop is going camping! Needed a tent.");