CREATE TABLE customers (
  id BIGSERIAL PRIMARY KEY,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  email VARCHAR(100) UNIQUE NOT NULL,
  date_of_birth DATE NOT NULL,
  license_number VARCHAR(30) NOT NULL
);