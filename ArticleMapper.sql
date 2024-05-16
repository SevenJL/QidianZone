CREATE TABLE `tag` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL
);

CREATE TABLE `article_tag` (
  `article_id` INT NOT NULL,
  `tag_id` INT NOT NULL,
  PRIMARY KEY (`article_id`, `tag_id`),
  FOREIGN KEY (`article_id`) REFERENCES `article`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`tag_id`) REFERENCES `tag`(`id`) ON DELETE CASCADE
);


