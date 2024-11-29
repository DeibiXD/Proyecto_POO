-- Create the database
CREATE DATABASE proyecto_poo;
USE proyecto_poo;

-- Table: Direccion
CREATE TABLE Direccion (
    idDireccion INT PRIMARY KEY AUTO_INCREMENT,
    pais VARCHAR(20),
    departamento VARCHAR(50),
    ciudad VARCHAR(50),
    colonia VARCHAR(70),
    referencia VARCHAR(100),
    dni VARCHAR(20),
    FOREIGN KEY (dni) REFERENCES Cliente(dni) ON DELETE CASCADE
);

-- Hacer esta primero
CREATE TABLE Cliente (
    dni VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(100),
    apellido VARCHAR(100),
    telefono VARCHAR(20),
    correo VARCHAR(20),
    sueldo DECIMAL(14,2)
);

-- Table: Prestamos
CREATE TABLE Prestamos (
    idPrestamo INT PRIMARY KEY AUTO_INCREMENT,
    monto DECIMAL(14,2),
    plazo INT,
    tasa_interes DECIMAL(14,2),
    cuota DECIMAL(14,2),
    estado CHAR(1),
    tipoPrestamo CHAR(1)
);

-- Table: cliente_prestamos (Relationship table for Cliente and Prestamos)
CREATE TABLE cliente_prestamos (
    dni VARCHAR(20),
    idPrestamo INT,
    PRIMARY KEY (dni, idPrestamo),
    FOREIGN KEY (dni) REFERENCES Cliente(dni) ON DELETE CASCADE,
    FOREIGN KEY (idPrestamo) REFERENCES Prestamos(idPrestamo) ON DELETE CASCADE
);

-- Table: tabla_amortizacion
CREATE TABLE tabla_amortizacion (
    idPrestamo INT,
    numeroCuota INT,
    interes DECIMAL(14,2),
    capital DECIMAL(14,2),
    saldo DECIMAL(14,2),
    fechaVencimiento DATE,
    PRIMARY KEY (idPrestamo, numeroCuota),
    FOREIGN KEY (idPrestamo) REFERENCES Prestamos(idPrestamo) ON DELETE CASCADE
);

SELECT * FROM Cliente