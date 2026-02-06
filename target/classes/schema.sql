-- Create Database
CREATE DATABASE IF NOT EXISTS cyclecare;
USE cyclecare;

-- Users Table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Cycle Data Table
CREATE TABLE IF NOT EXISTS cycle_data (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    start_date DATE NOT NULL,
    cycle_length INT NOT NULL,
    symptoms TEXT,
    flow_intensity VARCHAR(50),
    mood VARCHAR(100),
    notes TEXT,
    user_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Insert Sample User
INSERT INTO users (name, email, password) VALUES 
('Demo User', 'demo@cyclecare.com', 'password123');

-- Insert Sample Cycle Data
INSERT INTO cycle_data (start_date, cycle_length, symptoms, flow_intensity, mood, user_id) VALUES 
('2026-01-10', 28, 'Cramps, Fatigue', 'Medium', 'Normal', 1),
('2025-12-13', 29, 'Headache, Bloating', 'Heavy', 'Irritable', 1),
('2025-11-15', 28, 'Mild Cramps', 'Light', 'Happy', 1);
