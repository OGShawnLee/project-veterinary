DROP DATABASE Veterinary;
CREATE DATABASE Veterinary;
USE Veterinary;

/* Creación de la Estructura de la Base de Datos */

CREATE TABLE Owner
(
    email              VARCHAR(128) PRIMARY KEY,
    name               VARCHAR(32)  NOT NULL,
    paternal_last_name VARCHAR(32)  NOT NULL,
    maternal_last_name VARCHAR(32)  NOT NULL,
    street             VARCHAR(128) NOT NULL,
    colony             VARCHAR(128) NOT NULL,
    number             INT          NOT NULL
);

CREATE TABLE Phone
(
    phone_number VARCHAR(16)  NOT NULL PRIMARY KEY,
    id_owner     VARCHAR(128) NOT NULL,
    FOREIGN KEY (id_owner) REFERENCES Owner (email) ON DELETE CASCADE
);

CREATE TABLE Staff
(
    display_name       VARCHAR(32) PRIMARY KEY,
    name               VARCHAR(32)                                       NOT NULL,
    paternal_last_name VARCHAR(32)                                       NOT NULL,
    maternal_last_name VARCHAR(32)                                       NOT NULL,
    street             VARCHAR(128)                                      NOT NULL,
    colony             VARCHAR(128)                                      NOT NULL,
    number             INT                                               NOT NULL,
    phone_number       VARCHAR(16)                                       NOT NULL,
    role               ENUM ('SECRETARY', 'VETERINARY', 'STOCK_MANAGER') NOT NULL
);

CREATE TABLE Account
(
    email              VARCHAR(128) PRIMARY KEY,
    display_name       VARCHAR(32)                                                        NOT NULL,
    encrypted_password VARCHAR(64)                                                        NOT NULL,
    role               ENUM ('ADMINISTRATOR', 'SECRETARY', 'VETERINARY', 'STOCK_MANAGER') NOT NULL,
    FOREIGN KEY (display_name) REFERENCES Staff (display_name) ON DELETE CASCADE
);

CREATE TABLE StockManager
(
    display_name VARCHAR(32),
    id_voter     INT PRIMARY KEY,
    FOREIGN KEY (display_name) REFERENCES Staff (display_name) ON DELETE CASCADE
);

CREATE TABLE Veterinarian
(
    display_name           VARCHAR(32),
    professional_license   VARCHAR(10) PRIMARY KEY,
    emergency_phone_number VARCHAR(10) NOT NULL,
    FOREIGN KEY (display_name) REFERENCES Staff (display_name) ON DELETE CASCADE
);

CREATE TABLE Secretary
(
    display_name VARCHAR(32),
    id_voter     INT PRIMARY KEY,
    FOREIGN KEY (display_name) REFERENCES Staff (display_name) ON DELETE CASCADE
);

CREATE TABLE Pet
(
    id_pet     INT AUTO_INCREMENT PRIMARY KEY,
    id_owner   VARCHAR(128)        NOT NULL,
    name       VARCHAR(32)         NOT NULL,
    species    enum ('DOG', 'CAT') NOT NULL,
    breed      VARCHAR(32)         NOT NULL,
    colour     VARCHAR(32)         NOT NULL,
    weight     DECIMAL(5, 2)       NOT NULL,
    height     DECIMAL(5, 2)       NOT NULL,
    birth_date DATE                NOT NULL,
    FOREIGN KEY (id_owner) REFERENCES Owner (email) ON DELETE CASCADE
);

CREATE TABLE Appointment
(
    id_appointment  INT AUTO_INCREMENT PRIMARY KEY,
    id_pet          INT         NOT NULL,
    id_veterinarian VARCHAR(10) NOT NULL,
    status          enum (
        'SCHEDULED',
        'CANCELLED',
        'IN_PROGRESS',
        'RESCHEDULED',
        'COMPLETED',
        'NO_SHOW'
        )                       NOT NULL,
    reason          enum (
        'GENERAL_CHECKUP',
        'VACCINATION',
        'PARASITE_CONTROL',
        'SURGERY',
        'EMERGENCY',
        'WEIGHT_CONTROL',
        'DENTAL_CHECKUP',
        'POST_SURGERY_CHECKUP',
        'SPECIALIZED_CONSULTATION',
        'BEHAVIORAL_CONSULTATION',
        'OTHER'
        )                       NOT NULL,
    date_time       DATETIME    NOT NULL,
    FOREIGN KEY (id_pet) REFERENCES Pet (id_pet) ON DELETE CASCADE,
    FOREIGN KEY (id_veterinarian) REFERENCES Veterinarian (professional_license) ON DELETE CASCADE
);

CREATE TABLE Product
(
    id_product  INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(32)                            NOT NULL,
    description TEXT                                   NOT NULL,
    kind        enum ('FOOD', 'MEDICINE', 'ACCESSORY') NOT NULL,
    stock       INT                                    NOT NULL,
    species     enum ('DOG', 'CAT', 'BOTH')            NOT NULL,
    price       DECIMAL(10, 2)                         NOT NULL,
    brand       VARCHAR(64)                            NOT NULL,
    is_sold_out BOOLEAN                                NOT NULL DEFAULT FALSE
);

CREATE TABLE Sale
(
    id_sale    INT AUTO_INCREMENT PRIMARY KEY,
    id_product INT          NOT NULL,
    id_owner   VARCHAR(128) NOT NULL,
    bought_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    quantity   INT          NOT NULL,
    FOREIGN KEY (id_product) REFERENCES Product (id_product) ON DELETE CASCADE,
    FOREIGN KEY (id_owner) REFERENCES Owner (email) ON DELETE CASCADE
);

CREATE TABLE Sickness
(
    id_sickness  INT AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(32)                    NOT NULL,
    description  TEXT                           NOT NULL,
    species      enum ('DOG', 'CAT', 'BOTH')    NOT NULL,
    danger_level enum ('LOW', 'MEDIUM', 'HIGH') NOT NULL
);

CREATE TABLE Diagnosis
(
    id_diagnosis   INT AUTO_INCREMENT PRIMARY KEY,
    id_sickness    INT      NOT NULL,
    id_appointment INT      NOT NULL,
    date_time      DATETIME NOT NULL,
    FOREIGN KEY (id_sickness) REFERENCES Sickness (id_sickness) ON DELETE CASCADE,
    FOREIGN KEY (id_appointment) REFERENCES Appointment (id_appointment) ON DELETE CASCADE
);

/* Administración de Usuarios */

# CREATE USER vet_user@localhost IDENTIFIED BY 'v3Ter1n4#$*';
GRANT ALL ON Veterinary.* TO vet_user@localhost;

/* Creación de Disparadores */

DROP TRIGGER IF EXISTS update_account_role;
CREATE TRIGGER update_account_role
    AFTER UPDATE
    ON Staff
    FOR EACH ROW
BEGIN
    IF OLD.role <> NEW.role THEN
        UPDATE Account
        SET role = NEW.role
        WHERE display_name = NEW.display_name;
    END IF;
END;

DROP TRIGGER IF EXISTS delete_account_on_delete_staff;
CREATE TRIGGER delete_account_on_delete_staff
    BEFORE DELETE
    ON Staff
    FOR EACH ROW
BEGIN
    DELETE
    FROM Account
    WHERE display_name = OLD.display_name;
END;

/* Creación de Procedimientos */

DROP PROCEDURE IF EXISTS create_staff;
CREATE PROCEDURE create_staff(
    IN in_email VARCHAR(128),
    IN in_display_name VARCHAR(32),
    IN in_name VARCHAR(32),
    IN in_paternal_last_name VARCHAR(32),
    IN in_maternal_last_name VARCHAR(32),
    IN in_street VARCHAR(32),
    IN in_colony VARCHAR(32),
    IN in_number INT,
    IN in_phone_number VARCHAR(10),
    IN in_encrypted_password VARCHAR(64),
    IN in_role ENUM ('SECRETARY', 'VETERINARY', 'STOCK_MANAGER')
)
BEGIN
    START TRANSACTION;
    INSERT INTO Staff (display_name, name, paternal_last_name, maternal_last_name, street, colony, number, phone_number,
                       role)
    VALUES (in_display_name,
            in_name,
            in_paternal_last_name,
            in_maternal_last_name,
            in_street,
            in_colony,
            in_number,
            in_phone_number,
            in_role);
    INSERT INTO Account (email, display_name, encrypted_password, role)
    VALUES (in_email,
            in_display_name,
            in_encrypted_password,
            in_role);
    COMMIT;
END;

DROP PROCEDURE IF EXISTS create_veterinarian;
CREATE PROCEDURE create_veterinarian(
    IN in_email VARCHAR(128),
    IN in_display_name VARCHAR(32),
    IN in_name VARCHAR(32),
    IN in_paternal_last_name VARCHAR(32),
    IN in_maternal_last_name VARCHAR(32),
    IN in_street VARCHAR(32),
    IN in_colony VARCHAR(32),
    IN in_number INT,
    IN in_phone_number VARCHAR(10),
    IN in_professional_license VARCHAR(10),
    IN in_emergency_phone_number VARCHAR(10),
    IN in_encrypted_password VARCHAR(64)
)
BEGIN
    START TRANSACTION;
    CALL create_staff(
            in_email,
            in_display_name,
            in_name,
            in_paternal_last_name,
            in_maternal_last_name,
            in_street,
            in_colony,
            in_number,
            in_phone_number,
            in_encrypted_password,
            'VETERINARY'
         );
    INSERT INTO Veterinarian (display_name, professional_license, emergency_phone_number)
    VALUES (in_display_name,
            in_professional_license,
            in_emergency_phone_number);
    COMMIT;
END;

DROP PROCEDURE IF EXISTS create_stock_manager;
CREATE PROCEDURE create_stock_manager(
    IN in_email VARCHAR(128),
    IN in_display_name VARCHAR(32),
    IN in_name VARCHAR(32),
    IN in_paternal_last_name VARCHAR(32),
    IN in_maternal_last_name VARCHAR(32),
    IN in_street VARCHAR(32),
    IN in_colony VARCHAR(32),
    IN in_number INT,
    IN in_phone_number VARCHAR(10),
    IN in_encrypted_password VARCHAR(64),
    IN in_id_voter INT
)
BEGIN
    START TRANSACTION;
    CALL create_staff(
            in_email,
            in_display_name,
            in_name,
            in_paternal_last_name,
            in_maternal_last_name,
            in_street,
            in_colony,
            in_number,
            in_phone_number,
            in_encrypted_password,
            'STOCK_MANAGER'
         );
    INSERT INTO StockManager (display_name, id_voter)
    VALUES (in_display_name, in_id_voter);
    COMMIT;
END;

DROP PROCEDURE IF EXISTS create_secretary;
CREATE PROCEDURE create_secretary(
    IN in_email VARCHAR(128),
    IN in_display_name VARCHAR(32),
    IN in_name VARCHAR(32),
    IN in_paternal_last_name VARCHAR(32),
    IN in_maternal_last_name VARCHAR(32),
    IN in_street VARCHAR(32),
    IN in_colony VARCHAR(32),
    IN in_number INT,
    IN in_phone_number VARCHAR(10),
    IN in_encrypted_password VARCHAR(64),
    IN in_id_voter INT
)
BEGIN
    START TRANSACTION;
    CALL create_staff(
            in_email,
            in_display_name,
            in_name,
            in_paternal_last_name,
            in_maternal_last_name,
            in_street,
            in_colony,
            in_number,
            in_phone_number,
            in_encrypted_password,
            'SECRETARY'
         );
    INSERT INTO Secretary (display_name, id_voter)
    VALUES (in_display_name, in_id_voter);
    COMMIT;
END;