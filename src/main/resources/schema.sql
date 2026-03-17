CREATE TABLE IF NOT EXISTS quantity_measurement_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    operation_type VARCHAR(50) NOT NULL,
    left_value DOUBLE,
    left_unit VARCHAR(50),
    right_value DOUBLE,
    right_unit VARCHAR(50),
    result_value DOUBLE,
    result_unit VARCHAR(50),
    scalar_result DOUBLE,
    error BOOLEAN NOT NULL,
    error_message VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);
