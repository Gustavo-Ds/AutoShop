-- V1__create_initial_schema.sql
-- Migration inicial para criacao do esquema AutoShop no PostgreSQL

CREATE TABLE users (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

CREATE TABLE addresses (
    id UUID PRIMARY KEY,
    street VARCHAR(150) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(2) NOT NULL,
    neighborhood VARCHAR(100) NOT NULL,
    cep VARCHAR(10) NOT NULL,
    number VARCHAR(20) NOT NULL,
    complement VARCHAR(100)
);

CREATE TABLE customers (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL UNIQUE,
    address_id UUID NOT NULL,
    phone VARCHAR(20) NOT NULL,
    CONSTRAINT fk_customers_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_customers_address FOREIGN KEY (address_id) REFERENCES addresses(id)
);

CREATE TABLE vehicles (
    id UUID PRIMARY KEY,
    customer_id UUID NOT NULL,
    license_plate VARCHAR(10) NOT NULL UNIQUE,
    model VARCHAR(50) NOT NULL,
    brand VARCHAR(50) NOT NULL,
    chassis VARCHAR(30) NOT NULL UNIQUE,
    year INT NOT NULL,
    CONSTRAINT fk_vehicles_customer FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE
);

CREATE TABLE service_orders (
    id UUID PRIMARY KEY,
    order_number BIGINT UNIQUE,
    vehicle_id UUID NOT NULL,
    mechanic_id UUID NOT NULL,
    opened_by_id UUID,
    closed_by_id UUID,
    service VARCHAR(150) NOT NULL,
    description TEXT,
    opening_date TIMESTAMP NOT NULL,
    closing_date TIMESTAMP,
    status VARCHAR(50) NOT NULL,
    final_value NUMERIC(10, 2),
    CONSTRAINT fk_service_orders_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicles(id) ON DELETE CASCADE,
    CONSTRAINT fk_service_orders_mechanic FOREIGN KEY (mechanic_id) REFERENCES users(id),
    CONSTRAINT fk_service_orders_opened_by FOREIGN KEY (opened_by_id) REFERENCES users(id),
    CONSTRAINT fk_service_orders_closed_by FOREIGN KEY (closed_by_id) REFERENCES users(id)
);

-- Indices para otimizacao de consultas de relacionamento
CREATE INDEX idx_customers_user_id ON customers(user_id);
CREATE INDEX idx_customers_address_id ON customers(address_id);
CREATE INDEX idx_vehicles_customer_id ON vehicles(customer_id);
CREATE INDEX idx_service_orders_vehicle_id ON service_orders(vehicle_id);
CREATE INDEX idx_service_orders_mechanic_id ON service_orders(mechanic_id);
CREATE INDEX idx_service_orders_order_number ON service_orders(order_number);
