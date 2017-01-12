CREATE TABLE `Item` (
  `id` char(36) NOT NULL,
  `payload` LONGTEXT NULL,
  `updateTimestamp` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)) engine 'InnoDB';