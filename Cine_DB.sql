CREATE DATABASE IF NOT EXISTS Cine_DB;

USE Cine_DB;

CREATE TABLE IF NOT EXISTS Cartelera (
    -- 'id' es un número entero que se incrementa solo. Es la llave primaria.
    id INT AUTO_INCREMENT PRIMARY KEY,

    -- 'titulo' es un texto de hasta 150 caracteres.
    titulo VARCHAR(150),

    -- 'director' es un texto de hasta 50 caracteres.
    director VARCHAR(50),

    -- 'Anio' es un número entero y no puede dejarse vacío (no nulo).
    anio INT NOT NULL,

    -- 'duracion' es un número entero para los minutos.
    duracion INT,

    -- 'genero' solo puede contener uno de los valores de la lista.
    genero ENUM('Comedia', 'Drama', 'Acción', 'Ciencia Ficción', 'Terror', 'Suspenso', 'Aventura')
);

ALTER TABLE Cartelera RENAME COLUMN año TO anio