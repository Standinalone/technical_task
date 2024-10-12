CREATE TABLE `image_file_names` (
  `fish_id` int NOT NULL,
  `image_file_name` varchar(255)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
ALTER TABLE `image_file_names` ADD CONSTRAINT `FK_image_file_names_fish` FOREIGN KEY (fish_id) REFERENCES `fish` (id);

INSERT INTO `image_file_names` (fish_id, image_file_name)
SELECT id, image_file_name
FROM fish;
ALTER TABLE `fish` DROP COLUMN `image_file_name`;