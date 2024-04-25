ALTER TABLE doctors MODIFY COLUMN id BIGINT;


CREATE TABLE appointments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    doctor_id BIGINT,
    user_id BIGINT,
    appointment_date DATETIME,
    FOREIGN KEY (doctor_id) REFERENCES doctors(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

