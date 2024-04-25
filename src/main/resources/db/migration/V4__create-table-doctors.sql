CREATE TABLE doctors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    last_name VARCHAR(50),
    crm VARCHAR(20),
    specialty VARCHAR(50),
    active TINYINT(1)
);