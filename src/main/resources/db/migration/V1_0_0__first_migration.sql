CREATE TABLE `user` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    `mail` varchar(255) NOT NULL,
    `password` varchar(255) NOT NULL,
    `user_token` varchar(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `TOKEN_UNIQUE` (`user_token`),
    UNIQUE KEY `MAIL_UNIQUE` (`mail`)
);


CREATE TABLE `phone` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `city_code` int NOT NULL,
    `country_code` varchar(255) NOT NULL,
    `number` bigint NOT NULL,
    `id_user` int DEFAULT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_phone_user` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`)
);

CREATE TABLE `session` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `created` datetime NOT NULL,
    `due_date` datetime NOT NULL,
    `is_active` boolean DEFAULT NULL,
    `session_token` varchar(255) NOT NULL,
    `id_user` int DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `SESSION_TOKEN_UNIQUE` (`session_token`),
    CONSTRAINT `fk_session_user` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`)
)