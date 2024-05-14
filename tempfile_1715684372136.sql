-- 创建category表
CREATE TABLE `category` (
    `id` INT AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 创建comment表
CREATE TABLE `comment` (
    `id` INT AUTO_INCREMENT,
    `content` TEXT NOT NULL,
    `creator` VARCHAR(255) NOT NULL,
    `create_time` DATETIME NOT NULL,
    `update_time` DATETIME NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 创建article表
CREATE TABLE `article` (
    `id` INT AUTO_INCREMENT,
    `title` VARCHAR(255) NOT NULL,
    `content` TEXT,
    `create_time` DATETIME NOT NULL,
    `update_time` DATETIME NOT NULL,
    `delete_time` DATETIME,
    `status` TINYINT NOT NULL, -- 状态
    `creator` VARCHAR(255) NOT NULL,
    `category_id` INT, -- 逻辑外键，关联category表
    `comment_id` INT, -- 逻辑外键，关联comment表
    `article_like` INT DEFAULT 0,
    `article_view` INT DEFAULT 0,
    `article_comment_count` INT DEFAULT 0,
    `article_view_power` TINYINT NOT NULL, -- 浏览权限
    `delete_status` TINYINT NOT NULL, -- 删除状态
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_article_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
    CONSTRAINT `fk_article_comment` FOREIGN KEY (`comment_id`) REFERENCES `comment` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
