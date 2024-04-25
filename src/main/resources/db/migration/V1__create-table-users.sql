CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    name VARCHAR(50),
    last_name VARCHAR(50),

    email VARCHAR(100),
    password VARCHAR(50),

    phone VARCHAR(50)
);