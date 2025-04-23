DROP DATABASE Veterinary;

CREATE DATABASE Veterinary;

USE Veterinary;

/* Creación de la Estructura de la Base de Datos */

CREATE TABLE Owner (
  email VARCHAR(128) PRIMARY KEY,
  name VARCHAR(32) NOT NULL,
  paternal_last_name VARCHAR(32) NOT NULL,
  maternal_last_name VARCHAR(32) NOT NULL,
  street VARCHAR(32) NOT NULL,
  colony VARCHAR(32) NOT NULL,
  number INT NOT NULL
);

CREATE TABLE Phone (
  id_phone INT AUTO_INCREMENT PRIMARY KEY,
  id_owner VARCHAR(128) NOT NULL,
  phone_number VARCHAR(10) NOT NULL,
  FOREIGN KEY (id_owner) REFERENCES Owner(email) ON DELETE CASCADE
);

CREATE TABLE Staff (
  display_name VARCHAR(32) PRIMARY KEY,
  name VARCHAR(32) NOT NULL,
  paternal_last_name VARCHAR(32) NOT NULL,
  maternal_last_name VARCHAR(32) NOT NULL,
  street VARCHAR(32) NOT NULL,
  colony VARCHAR(32) NOT NULL,
  number INT NOT NULL,
  encripted_password VARCHAR(64) NOT NULL,
  phone_number VARCHAR(10) NOT NULL
);

CREATE TABLE Veterinarian (
  display_name VARCHAR(32),
  professional_license VARCHAR(10) PRIMARY KEY,
  emergency_phone_number VARCHAR(10) NOT NULL,
  FOREIGN KEY (display_name) REFERENCES Staff(display_name) ON DELETE CASCADE
);

CREATE TABLE Secretary (
  display_name VARCHAR(32),
  id_voter INT PRIMARY KEY,
  FOREIGN KEY (display_name) REFERENCES Staff(display_name) ON DELETE CASCADE
);

CREATE TABLE Pet (
  id_pet INT AUTO_INCREMENT PRIMARY KEY,
  id_owner VARCHAR(128) NOT NULL,
  name VARCHAR(32) NOT NULL,
  species enum('Dog', 'Cat') NOT NULL,
  breed VARCHAR(32) NOT NULL,
  colour VARCHAR(32) NOT NULL,
  weight DECIMAL(5, 2) NOT NULL,
  height DECIMAL(5, 2) NOT NULL,
  birth_date DATE NOT NULL,
  FOREIGN KEY (id_owner) REFERENCES Owner(email) ON DELETE CASCADE
);

CREATE TABLE Appointment (
  id_appointment INT AUTO_INCREMENT PRIMARY KEY,
  id_pet INT NOT NULL,
  id_veterinarian VARCHAR(10) NOT NULL,
  status enum (
    'Scheduled',
    'Cancelled',
    'In Progress',
    'Rescheduled',
    'Completed',
    'No Show'
  ) NOT NULL,
  reason enum(
    'General Checkup',
    'Vaccination',
    'Parasite Control',
    'Surgery',
    'Emergency',
    'Weight Control',
    'Dental Checkup',
    'Post surgery Checkup',
    'Specialized Consultation',
    'Behavioral Consultation',
    'Other'
  ) NOT NULL,
  date_time DATETIME NOT NULL,
  FOREIGN KEY (id_pet) REFERENCES Pet(id_pet) ON DELETE CASCADE,
  FOREIGN KEY (id_veterinarian) REFERENCES Veterinarian(professional_license) ON DELETE CASCADE
);

CREATE TABLE Product (
  id_product INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(32) NOT NULL,
  description TEXT NOT NULL,
  kind enum('Food', 'Medicine', 'Accessory') NOT NULL,
  stock INT NOT NULL,
  species enum('Dog', 'Cat', 'Both') NOT NULL,
  price DECIMAL(10, 2) NOT NULL,
  brand VARCHAR(32) NOT NULL,
  is_sold_out BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE Sale (
  id_sale INT AUTO_INCREMENT PRIMARY KEY,
  id_product INT NOT NULL,
  id_owner VARCHAR(128) NOT NULL,
  bought_at DATETIME NOT NULL,
  quantity INT NOT NULL,
  FOREIGN KEY (id_product) REFERENCES Product(id_product) ON DELETE CASCADE,
  FOREIGN KEY (id_owner) REFERENCES Owner(email) ON DELETE CASCADE
);

CREATE TABLE Sickness (
  id_sickness INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(32) NOT NULL,
  description TEXT NOT NULL,
  species enum('Dog', 'Cat', 'Both') NOT NULL,
  danger_level enum('Low', 'Medium', 'High') NOT NULL
);

CREATE TABLE Diagnosis (
  id_diagnosis INT AUTO_INCREMENT PRIMARY KEY,
  id_sickness INT NOT NULL,
  id_appointment INT NOT NULL,
  date_time DATETIME NOT NULL,
  FOREIGN KEY (id_sickness) REFERENCES Sickness(id_sickness) ON DELETE CASCADE,
  FOREIGN KEY (id_appointment) REFERENCES Appointment(id_appointment) ON DELETE CASCADE
);

/* Administración de Usuarios */

CREATE USER vet_user@localhost IDENTIFIED BY 'v3Ter1n4#$*';
GRANT ALL ON Veterinary.* TO vet_user@localhost;